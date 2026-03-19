/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Prettier will trim empty first and last lines, but we need to keep them
 * around (to preserve the typical linebreak after an opening tag, and the
 * indent before a closing tag, which is often on a line of its own).
 */
function trim(string) {
	let prefix = '';

	let suffix = '';

	const trimmed = string.replace(
		/^\s*(\r\n|\n)|(?:\r?\n)([ \t]*$)/g,
		(match, leadingNewline, trailingHorizontalWhitespace) => {
			if (leadingNewline) {
				prefix = leadingNewline;
			}
			else if (trailingHorizontalWhitespace) {
				suffix = trailingHorizontalWhitespace;
			}

			return '';
		}
	);

	return {prefix, suffix, trimmed};
}

export default trim;
