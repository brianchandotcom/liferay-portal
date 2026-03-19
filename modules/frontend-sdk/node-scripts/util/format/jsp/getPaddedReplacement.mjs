/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Valid character to end an identifier (has property "ID Continue")
 * which we can assume is very likely unused in liferay-portal.
 *
 * Unicode name is "MODIFIER LETTER LEFT HALF RING" and glyph is: "ʿ"
 *
 * @see: https://codepoints.net/U+02BF
 * @see https://mathiasbynens.be/notes/javascript-identifiers-es6
 */
export const ID_END = '\u02bf';

/**
 * Valid character to start an identifier (has property "ID Start") which we can
 * assume is very likely unused in liferay-portal.
 *
 * Unicode name is "MODIFIER LETTER RIGHT HALF RING" and glyph is: "ʾ"
 *
 * @see https://codepoints.net/U+02BE
 * @see https://mathiasbynens.be/notes/javascript-identifiers-es6
 */
export const ID_START = '\u02be';

/**
 * RegExp for matching an identifier that was created with
 * `getPaddedReplacement()`.
 */
export const IDENTIFIER = new RegExp(`${ID_START}[^${ID_END}]+${ID_END}`);

/**
 * Returns a best-effort equal-length substitution for "match" based on
 * "template". Rare but still valid characters are used at the beginning and end
 * of the replacement to make it easy to find the replacements again in the
 * source.
 *
 * If `template` is longer than `match`, the full `template` is returned.
 */
function getPaddedReplacement(match, template) {
	const paddingLength = Math.max(match.length - template.length - 2, 0);
	const padding = '_'.repeat(paddingLength);

	return ID_START + template + padding + ID_END;
}

export default getPaddedReplacement;
