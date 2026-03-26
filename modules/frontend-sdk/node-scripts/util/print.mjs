/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import indent from './indent.mjs';

/**
 * Prints messages to `stderr`
 *
 * @param {number} indentLevel level of indentation (honors line feeds)
 * @param {...*} things stuff to print
 */
export default function print(indentLevel, ...things) {
	process.stderr.write(indent(indentLevel * 4, things.join(' ')));
	process.stderr.write('\n');
}

export function printDuration(start, indentLevel, description) {
	const lapse = (Date.now() - start) / 1000;

	if (lapse < 60) {
		print(
			indentLevel,
			print.info('INFO:'),
			`${description} took ${lapse.toFixed(0)} seconds.\n`
		);
	}
	else {
		print(
			indentLevel,
			print.info('INFO:'),
			`${description} took ${(lapse / 60).toFixed(1)} minutes.\n`
		);
	}
}

print.bold = (thing) => BOLD + thing + RESET;
print.underline = (thing) => UNDERLINE + thing + RESET;

print.blue = (thing) => BLUE + thing + RESET;
print.green = (thing) => GREEN + thing + RESET;
print.red = (thing) => RED + thing + RESET;
print.yellow = (thing) => YELLOW + thing + RESET;

print.title = (thing) => print.bold(print.blue(thing));
print.subTitle = (thing) => print.blue(thing);

print.info = (thing) => print.bold(print.blue(thing));
print.success = (thing) => print.bold(print.green(thing));
print.warning = (thing) => print.bold(print.yellow(thing));
print.error = (thing) => print.bold(print.red(thing));

function isTTY(chars) {
	return process.stderr.isTTY ? chars : '';
}

const BLUE = isTTY('\x1b[34m');
const BOLD = isTTY('\x1b[1m');
const GREEN = isTTY('\x1b[32m');
const RED = isTTY('\x1b[31m');
const RESET = isTTY('\x1b[0m');
const UNDERLINE = isTTY('\x1b[4m');
const YELLOW = isTTY('\x1b[33m');
