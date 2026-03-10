/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const HEAVY_MULTIPLICATION_X = '\u2716';

export default function logError(errorMessages, captureOutput = false) {
	const filesWithErrors = Object.keys(errorMessages);

	let allOutput = '';

	filesWithErrors.forEach((filepath) => {
		const messages = errorMessages[filepath];

		let errors = 0;
		let fixableErrorCount = 0;
		let fixableWarningCount = 0;
		let output = '';
		let warnings = 0;

		output += color.UNDERLINE + filepath + color.RESET + '\n';

		messages.forEach(({column, fix, line, message, ruleId, severity}) => {
			if (severity === 2) {
				errors++;

				if (fix) {
					fixableErrorCount++;
				}
			}
			else {
				warnings++;

				if (fix) {
					fixableWarningCount++;
				}
			}

			const type =
				(severity === 2
					? color.RED + 'error'
					: color.YELLOW + 'warning') + color.RESET;

			const label = ruleId || 'syntax-error';

			output += `  ${line}:${column}  ${type}  ${message}  ${label}\n`;
		});

		output += '\n';

		const total = errors + warnings;

		if (total) {
			const summary = [
				color.BOLD,
				errors ? color.RED : color.YELLOW,
				HEAVY_MULTIPLICATION_X,
				' ',
				pluralize('problem', total),
				' ',
				`(${pluralize('error', errors)}, `,
				`${pluralize('warning', warnings)})`,
				color.RESET,
			];

			if (fixableErrorCount || fixableWarningCount) {
				summary.push(
					'\n',
					color.BOLD,
					errors ? color.RED : color.YELLOW,
					'  ',
					pluralize('error', fixableErrorCount),
					' and ',
					pluralize('warning', fixableWarningCount),
					' potentially fixable with the `--fix` option.',
					color.RESET
				);
			}

			output += summary.join('');
		}

		if (captureOutput) {
			allOutput += `${output}\n\n`;
		}
		else {
			console.log(`${output}\n`);
		}
	});

	return captureOutput ? allOutput : undefined;
}

function pluralize(word, count) {
	return `${count} ${word}${count === 1 ? '' : 's'}`;
}

function color(name) {
	if (process.stdout.isTTY) {
		return (
			{
				BOLD: '\x1b[1m',
				RED: '\x1b[31m',
				RESET: '\x1b[0m',
				UNDERLINE: '\x1b[4m',
				YELLOW: '\x1b[33m',
			}[name] || ''
		);
	}
	else {
		return '';
	}
}

color.BOLD = color('BOLD');
color.RED = color('RED');
color.RESET = color('RESET');
color.UNDERLINE = color('UNDERLINE');
color.YELLOW = color('YELLOW');
