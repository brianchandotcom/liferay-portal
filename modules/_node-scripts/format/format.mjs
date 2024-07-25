/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ESLint} from 'eslint';
import fg from 'fast-glob';
import * as fs from 'fs/promises';
import micromatch from 'micromatch';
import path from 'path';
import prettier from 'prettier';
import stylelint from 'stylelint';

import {getRootDir} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';
import {getGitModifiedFiles} from '../util/gitCommands.mjs';
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

const EXTENSIONS = ['graphql', 'js', 'jsp', 'jspf', 'mjs', 'scss', 'ts', 'tsx'];

async function getIgnoredFiles(rootDir) {
	const eslintIgnoreFilePath = path.join(rootDir, ESLINT_IGNORE_FILE);
	const prettierIgnoreFilePath = path.join(rootDir, PRETTIER_IGNORE_FILE);
	const gitIgnoreFilePath = path.join(rootDir, GIT_IGNORE_FILE);

	const [eslintIgnores, prettierIgnores, gitIgnores] = await Promise.all([
		readIgnoreFile(eslintIgnoreFilePath),
		readIgnoreFile(prettierIgnoreFilePath),
		readIgnoreFile(gitIgnoreFilePath),
	]);

	return [
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
	});
}

async function getFilesToCheck(rootDir, ignore = []) {
	const files = await fg(
		[
			'**/*.',
			'*.{graphql,js,mjs,scss,ts,tsx}',
			'**/*.{graphql,js,mjs,scss,ts,tsx}',
			'**/src/**/*.{jsp,jspf}',
		],
		{
			cwd: rootDir,
			dot: true,
			ignore,
		}
	);

	return files.map((filepath) => path.join(rootDir, filepath));
}

const FALLBACK_FILE_PATH = '__fallback__.js';

export default async function format(
	fix,
	{all, filePath} = {
		all: undefined,
		filePath: undefined,
	}
) {
	const rootDir = await getRootDir();
	const workspacesDir = path.join(rootDir, '..', 'workspaces');
	const playwrightDir = path.join(rootDir, 'test', 'playwright');

	const [rootIgnored, workspacesIgnored, playwrightIgnored] =
		await Promise.all([
			getIgnoredFiles(rootDir),
			getIgnoredFiles(workspacesDir),
			getIgnoredFiles(playwrightDir),
		]);

	let filepaths = [];

	if (all) {
		filepaths = (
			await Promise.all([
				getFilesToCheck(rootDir, rootIgnored),
				getFilesToCheck(workspacesDir, workspacesIgnored),
				getFilesToCheck(playwrightDir, playwrightIgnored),
			])
		).flat();
	}
	else if (filePath) {
		filepaths = [filePath];
	}
	else {
		const modifiedFiles = await getGitModifiedFiles();

		const filteredModifiedFiles = [];

		for (const file of modifiedFiles) {
			if (file.startsWith('modules/test/playwright/')) {
				filteredModifiedFiles.push(
					...micromatch(
						[file],
						EXTENSIONS.map((ext) => `**/*.${ext}`),
						{ignore: playwrightIgnored}
					)
				);
			}

			if (file.startsWith('modules/')) {
				filteredModifiedFiles.push(
					...micromatch(
						[file],
						EXTENSIONS.map((ext) => `**/*.${ext}`),
						{ignore: rootIgnored}
					)
				);
			}

			if (file.startsWith('workspaces/')) {
				filteredModifiedFiles.push(
					...micromatch(
						[file],
						EXTENSIONS.map((ext) => `**/*.${ext}`),
						{ignore: workspacesIgnored}
					)
				);
			}
		}

		filepaths = filteredModifiedFiles.map(

			// make sure the path is absolute

			(filepath) => path.join(rootDir, '..', filepath)
		);
	}

	if (!filepaths.length) {
		console.log('ℹ️ No files to format');

		return;
	}

	console.log(`📝 Formatting ${filepaths.length} files`);

	const [eslintConfig, prettierConfig, stylelintConfig] = await Promise.all([
		getEslintConfig(rootDir),
		getPrettierConfig(rootDir),
		getStylelintConfig(rootDir),
	]);

	const eslintCLI = new ESLint({
		baseConfig: eslintConfig,
		fix: true,
		ignorePath: path.join(rootDir, ESLINT_IGNORE_FILE),
		resolvePluginsRelativeTo: rootDir,
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
		if (!(await fileExists(filepath))) {
			continue;
		}

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
			console.log(`🚨 ${filepath}: ${error}`);
		}

		if (transformedContent !== source || errMessages[filepath]) {
			badFiles.push(filepath);

			if (fix && transformedContent !== source) {
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

	if (badFiles.length !== fixed) {
		const totalBad = badFiles.length;

		summary.push(
			`${totalBad} ${files(totalBad)} ${have(totalBad)} problems`
		);

		summary.push(
			badFiles
				.map((filepath) => `- ${path.relative(rootDir, filepath)}`)
				.join('\n')
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

	const {default: eslintConfig} = await import('file://' + eslintConfigPath);

	return eslintConfig;
}

async function getPrettierConfig(rootDir) {
	const prettierConfigPath = path.join(rootDir, '.prettierrc.js');

	const {default: prettierConfig} = await import(
		'file://' + prettierConfigPath
	);

	return prettierConfig;
}

async function getStylelintConfig(rootDir) {
	const stylelintConfigPath = path.join(rootDir, '.stylelintrc.js');

	const {default: stylelintConfig} = await import(
		'file://' + stylelintConfigPath
	);

	return stylelintConfig;
}
