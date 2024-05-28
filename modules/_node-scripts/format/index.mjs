/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ESLint} from 'eslint';
import fg from 'fast-glob';
import * as fs from 'fs/promises';
import path from 'path';
import prettier from 'prettier';
import stylelint from 'stylelint';

import {getRootDir} from '../util/constants.mjs';
import filterChangedFiles from '../util/filterChangedFiles.mjs';
import {readIgnoreFile} from '../util/readIgnoreFile.mjs';
import {ID_END, ID_START} from './jsp/getPaddedReplacement.mjs';
import processJSP from './jsp/processJSP.mjs';
import {SCRIPTLET_CONTENT} from './jsp/substituteTags.mjs';
import {BLOCK_CLOSE, BLOCK_OPEN} from './jsp/tagReplacements.mjs';
import {FILLER_CHAR, SPACE_CHAR, TAB_CHAR} from './jsp/toFiller.mjs';
import logError from './logError.mjs';

const PRETTIER_IGNORE_FILE = '.prettierignore';
const ESLINT_IGNORE_FILE = '.eslintignore';
const GIT_IGNORE_FILE = '.gitignore';

async function getFilesToCheck(rootDir) {
	const eslintIgnoreFilePath = path.join(rootDir, ESLINT_IGNORE_FILE);
	const prettierIgnoreFilePath = path.join(rootDir, PRETTIER_IGNORE_FILE);
	const gitIgnoreFilePath = path.join(rootDir, GIT_IGNORE_FILE);

	const eslintIgnores = readIgnoreFile(eslintIgnoreFilePath);
	const prettierIgnores = readIgnoreFile(prettierIgnoreFilePath);
	const gitIgnores = readIgnoreFile(gitIgnoreFilePath);

	return await fg(
		[
			'*.{css,graphql,js,mjs,scss,ts,tsx}',
			'**/*.{css,graphql,js,mjs,scss,ts,tsx}',
			'**/src/**/*.{jsp,jspf}',
		],
		{
			dot: true,
			ignore: [
				'**/src/test/**',
				'**/build_gradle/**',
				...gitIgnores,
				...eslintIgnores,
				...prettierIgnores,
			].map((ignore) => {
				if (ignore.startsWith('*') && !ignore.startsWith('**')) {
					ignore = `**/${ignore}`;
				}

				if (!ignore.startsWith('*')) {
					ignore = `**${ignore.startsWith('/') ? '' : '/'}${ignore}`;
				}

				if (!ignore.endsWith('**') && !ignore.includes('.')) {
					ignore = `${ignore}${ignore.endsWith('/') ? '' : '/'}**`;
				}

				return ignore;
			}),
		}
	);
}

const FALLBACK_FILE_PATH = '__fallback__.js';

export default async function format() {
	const fix = process.argv[3] !== '--check';

	const rootDir = await getRootDir();

	let filepaths = await getFilesToCheck(rootDir);

	filepaths = await filterChangedFiles(filepaths);

	const [eslintConfig, prettierConfig, stylelintConfig] = await Promise.all([
		getEslintConfig(rootDir),
		getPrettierConfig(rootDir),
		getStylelintConfig(rootDir),
	]);

	const eslintCLI = new ESLint({
		baseConfig: eslintConfig,
		fix: true,
		ignorePath: path.join(rootDir, ESLINT_IGNORE_FILE),
	});

	const badFiles = [];
	const errMessages = [];
	let checked = 0;
	let fixed = 0;

	async function formatWithPrettier(input, filepath, configOverride = {}) {
		return await prettier.format(input, {
			...prettierConfig,
			...configOverride,
			filepath,
		});
	}

	async function formatWithEslint(input, filepath) {
		const [lintResult = {}] = await eslintCLI.lintText(input, {
			filePath: filepath,
		});

		const {messages, output} = lintResult;

		if (messages?.length) {
			errMessages[filepath] = errMessages[filepath]
				? errMessages[filepath].push(...messages)
				: messages;
		}

		return output ?? input;
	}

	async function formatWithStyleLint(input, filepath) {
		const extName = path.extname(filepath);

		const {output, results} = await stylelint.lint({
			code: input,
			codeFilename: filepath,
			config: stylelintConfig,
			fix: true,
			syntax: extName.replace('.', ''),
		});

		if (results?.length) {
			results.forEach((result) => {
				if (result.warnings.length) {
					const messages = result.warnings.map(
						({column, line, rule, text}) => ({
							column,
							filepath,
							line,
							message: text,
							ruleId: rule,
							severity: 2,
						})
					);

					errMessages[filepath] = errMessages[filepath]
						? errMessages[filepath].push(...messages)
						: messages;
				}
			});
		}

		return output.endsWith('\n') ? output : `${output}\n`;
	}

	for (const filepath of filepaths) {
		checked++;

		const source = await fs.readFile(filepath, 'utf8');
		const extName = path.extname(filepath);

		let transformedContent = source;

		if (!source.length) {
			continue;
		}

		try {
			switch (extName) {
				case '.jsp':
				case '.jspf': {
					transformedContent = await processJSP(
						source,
						async (input) => {
							return await formatWithPrettier(
								input,
								FALLBACK_FILE_PATH,
								{
									commentIgnorePatterns: [
										BLOCK_CLOSE,
										BLOCK_OPEN,
										FILLER_CHAR,
										ID_END,
										ID_START,
										SCRIPTLET_CONTENT,
										SPACE_CHAR,
										TAB_CHAR,
									],
								}
							);
						}
					);

					if (!fix && transformedContent !== source) {
						const messages = [
							{
								column: 1,
								filepath,
								line: 1,
								message: 'Check failed',
								ruleId: 'Prettier',
								severity: 2,
							},
						];

						errMessages[filepath] = errMessages[filepath]
							? errMessages[filepath].push(...messages)
							: messages;
					}

					break;
				}
				case '.css':
				case '.scss': {
					transformedContent = await formatWithPrettier(
						transformedContent,
						filepath
					);
					transformedContent = await formatWithStyleLint(
						transformedContent,
						filepath
					);
					break;
				}
				default: {
					transformedContent = await formatWithPrettier(
						transformedContent,
						filepath
					);
					transformedContent = await formatWithEslint(
						transformedContent,
						filepath
					);
				}
			}
		}
		catch (error) {

			// eslint-disable-next-line no-console
			console.log(`${filepath}: ${error}`);
		}

		if (transformedContent !== source) {
			badFiles.push(filepath);

			if (fix) {
				fixed++;
			}
		}

		if (fixed) {
			await fs.writeFile(filepath, transformedContent);
		}
	}

	const files = (count) => (count === 1 ? 'file' : 'files');
	const have = (count) => (count === 1 ? 'has' : 'have');

	const summary = [`Format checked ${checked} ${files(checked)}`];

	if (Object.keys(errMessages).length) {
		logError(errMessages);
	}

	if (fixed) {
		summary.push(`fixed ${fixed} ${files(fixed)}`);
	}

	if (!fixed && badFiles.length) {
		const totalBad = badFiles.length;

		summary.push(
			`${totalBad} ${files(totalBad)} ${have(totalBad)} problems`
		);

		throw new Error(summary.join('\n') + '\n');
	}
	else {

		// eslint-disable-next-line no-console
		console.log(summary.join('\n'));
	}
}

async function getEslintConfig(rootDir) {
	const eslintConfigPath = path.join(rootDir, '.eslintrc.js');

	const {default: eslintConfig} = await import(eslintConfigPath);

	return eslintConfig;
}

async function getPrettierConfig(rootDir) {
	const prettierConfigPath = path.join(rootDir, '.prettierrc.js');

	const {default: prettierConfig} = await import(prettierConfigPath);

	return prettierConfig;
}

async function getStylelintConfig(rootDir) {
	const stylelintConfigPath = path.join(rootDir, '.stylelintrc.js');

	const {default: stylelintConfig} = await import(stylelintConfigPath);

	return stylelintConfig;
}
