/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Example:
 * en-US: `123456` => `123,456`
 * es-ES: `123456` => `123.456`
 */

interface Options {
	compactThreshold?: number;
	useCompact?: boolean;
}

const DEFAULT_COMPACT_THRESHOLD: number = 10000;

export function numberFormat(
	languageTag: string,
	number: number,
	options: Options = {}
) {
	const {compactThreshold = DEFAULT_COMPACT_THRESHOLD, useCompact} = options;

	const formatOptions: Intl.NumberFormatOptions = {};

	if (useCompact && number >= compactThreshold) {
		formatOptions.notation = 'compact';
	}

	return Intl.NumberFormat(languageTag, formatOptions).format(number);
}
