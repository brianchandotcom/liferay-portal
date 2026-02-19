/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export {options} from './options.mjs';
export {parsers} from './parsers.mjs';
export {printers} from './printers.mjs';

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
