/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import * as liferayPlugin from '@liferay/prettier-plugin';

export default {
	bracketSpacing: false,
	endOfLine: 'lf',
	jsxSingleQuote: false,
	plugins: [liferayPlugin],
	quoteProps: 'consistent',
	singleQuote: true,
	tabWidth: 4,
	trailingComma: 'es5',
	useTabs: true,
};
