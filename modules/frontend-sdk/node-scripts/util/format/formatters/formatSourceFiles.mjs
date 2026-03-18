/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import micromatch from 'micromatch';
import path from 'path';

import getNamedArguments from '../../getNamedArguments.mjs';
import {MODULES_DIR, PORTAL_DIR} from '../../locations.mjs';
import print from '../../print.mjs';
import runGitLsFiles from '../../runGitLsFiles.mjs';
import formatWithEslint from '../eslint/formatWithEslint.mjs';
import formatScriptTagsWithPrettier from '../jsp/formatScriptTagsWithPrettier.mjs';
import formatWithPrettier from '../prettier/formatWithPrettier.mjs';
import formatWithStylelint from '../stylelint/formatWithStylelint.mjs';
import getIgnorePatterns from '../util/getIgnorePatterns.mjs';

const EXTENSIONS = ['graphql', 'js', 'jsp', 'jspf', 'mjs', 'scss', 'ts', 'tsx'];

/**
 * @param {boolean} check whether to check or fix
 * @param {string[]} files list of portal relative path of files to check
 * @return {Promise<boolean>} true if all files are correctly formatted
 */
export default async function formatSourceFiles(check, files) {
	const {emitSuppressed} = getNamedArguments({
		emitSuppressed: '--emit-suppressed',
	});

	let checksPassed = true;

	const filePaths = await getFilePaths(files);

	print(
		1,
		false,
		print.subTitle(
			`> ${check ? 'Checking' : 'Formatting'} ${filePaths.length} source files with SF...\n`
		)
	);

	const [
		eslintIgnorePatterns,
		prettierIgnorePatterns,
		stylelintIgnorePatterns,
	] = await Promise.all([
		getIgnorePatterns(path.resolve(MODULES_DIR, '.eslintignore')),
		getIgnorePatterns(path.resolve(MODULES_DIR, '.prettierignore')),
		getIgnorePatterns(path.resolve(MODULES_DIR, '.stylelintignore')),
	]);

	for (const filePath of filePaths) {
		const source = await fs.readFile(filePath, 'utf8');

		if (!source.length) {
			continue;
		}

		const portalRelativeFilePath = path.relative(PORTAL_DIR, filePath);
		const modulesRelativeFilePath = path.relative(MODULES_DIR, filePath);

		const isIncluded = (ignorePatterns) =>
			!micromatch.isMatch(modulesRelativeFilePath, ignorePatterns, {
				dot: true,
			});

		let transformedContent = source;

		try {
			switch (path.extname(filePath)) {
				case '.jsp':
				case '.jspf': {
					if (isIncluded(prettierIgnorePatterns)) {
						transformedContent =
							await formatScriptTagsWithPrettier(source);
					}
					break;
				}

				case '.css':
				case '.scss': {
					if (isIncluded(prettierIgnorePatterns)) {
						transformedContent = await formatWithPrettier(
							transformedContent,
							filePath
						);
					}

					if (isIncluded(stylelintIgnorePatterns)) {
						const {errorsPresent, output} =
							await formatWithStylelint(
								transformedContent,
								filePath
							);

						if (!errorsPresent) {
							checksPassed = false;
						}

						transformedContent = output;
					}
					break;
				}

				default: {
					if (isIncluded(prettierIgnorePatterns)) {
						transformedContent = await formatWithPrettier(
							transformedContent,
							filePath
						);
					}

					if (isIncluded(eslintIgnorePatterns)) {
						const {errorsPresent, output} = await formatWithEslint(
							transformedContent,
							filePath,
							emitSuppressed
						);

						if (!errorsPresent) {
							checksPassed = false;
						}

						transformedContent = output;
					}
					break;
				}
			}
		}
		catch (error) {
			print(
				2,
				true,
				print.error('ERROR:'),
				'Unhandled error formatting file',
				print.underline(portalRelativeFilePath)
			);
			print(3, true, error, '\n');

			checksPassed = false;
		}

		if (transformedContent !== source) {
			if (!check) {
				await fs.writeFile(filePath, transformedContent);

				print(
					2,
					false,
					print.success('SUCCESS:'),
					'Formatted file',
					print.underline(portalRelativeFilePath),
					'\n'
				);
			}
			else {
				print(
					2,
					true,
					print.error('ERROR:'),
					'File',
					print.underline(portalRelativeFilePath),
					'has format errors.\n'
				);

				checksPassed = false;
			}
		}
	}

	return checksPassed;
}

/**
 *
 * @param {string[]} files portal relative path of files to check
 * @return {Promise<string[]>} absolute paths of files to check
 */
async function getFilePaths(files) {
	let filePaths;

	if (!files) {
		filePaths = await runGitLsFiles(
			[
				...EXTENSIONS.map((ext) => `*.${ext}`),
				...EXTENSIONS.map((ext) => `**/*.${ext}`),
			],
			process.cwd()
		);
	}
	else {
		filePaths = files.filter((file) => {
			for (const ext of EXTENSIONS) {
				if (file.endsWith(`.${ext}`)) {
					return true;
				}
			}

			return false;
		});
	}

	filePaths = filePaths.map((file) => path.resolve(PORTAL_DIR, file));

	return filePaths;
}
