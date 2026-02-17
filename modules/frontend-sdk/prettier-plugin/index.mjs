/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

export {parsers} from './parsers.mjs';
export {printers} from './printers.mjs';
export {options} from './options.mjs';

export const defaultOptions = {
	bracketSpacing: false,
	endOfLine: 'lf',
	jsxSingleQuote: false,
	quoteProps: 'consistent',
	singleQuote: true,
	tabWidth: 4,
	trailingComma: 'es5',
	useTabs: true,
};
