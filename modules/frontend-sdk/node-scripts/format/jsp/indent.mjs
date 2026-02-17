/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Indent every line of `text` (except for empty lines) by `count` units
 * of `whitespace`.
 */
function indent(text, count = 1, whitespace = '\t') {
	return text
		.split('\n')
		.map((line) => {
			if (line.length) {
				return `${whitespace.repeat(count)}${line}`;
			}
			else {
				return line;
			}
		})
		.join('\n');
}

export default indent;
