/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Used to stand for non-whitespace characters.
 *
 * Unicode name is "BOX DRAWINGS LIGHT DIAGONAL CROSS" and glyph is: "╳"
 */
export const FILLER_CHAR = '\u2573';

/**
 * Used to stand for space characters in leading indent.
 *
 * Unicode name is "LATIN CAPITAL LETTER TURNED M" and glyph is: "Ɯ"
 */
export const SPACE_CHAR = '\u019c';

/**
 * Used to stand in for tab characters in leading indent.
 *
 * Unicode name is "LATIN CAPITAL LETTER T WITH HOOK" and glyph is: "Ƭ"
 */
export const TAB_CHAR = '\u01ac';

/**
 * Returns a copy of `string` with the same "shape", but containing only
 * `filler`.
 *
 * Used so that we can substitute source code with a same-shaped comment without
 * changing the relative position of anything else in the file.
 *
 * As filler inside the comments, we use Unicode code points that are
 * very unlikely to be used organically in the code base, and that we can
 * reliably identify afterwards and reverse the substitution.
 */
function toFiller(string, filler = FILLER_CHAR) {
	const VALID_STRING = /^[^\n\r]{0,2}(.+?)[^\n\r]{0,2}$/s;

	const {0: match, 1: body} = string.match(VALID_STRING) || {};

	if (!match) {
		throw new Error(
			`toFiller(): invalid string: ${JSON.stringify(string)}`
		);
	}

	// Special case: if `body` is just whitespace; must insert at least
	// one filler.

	let output = body.match(/^\s+$/) ? FILLER_CHAR : '';

	const LINE = /([ \t]*)([^\r\n]*)(\r?\n)?/g;

	while (true) {
		const {
			0: match,
			1: indent,
			2: contents,
			3: linebreak,
		} = LINE.exec(body) || {};

		if (match) {
			output += indent.replace(/./g, (char) => {
				return char[0] === '\t' ? TAB_CHAR : SPACE_CHAR;
			});

			output += contents.replace(/./g, filler);

			output += linebreak || '';
		}
		else {
			break;
		}
	}

	return `/*${output}*/`;
}

/**
 * Returns a RegExp that can be used to identify comments created with
 * `toFiller()` and the specified filler character.
 */
export function isFiller(char = FILLER_CHAR) {
	return new RegExp(`/\\*(?:\\s*[${char}${SPACE_CHAR}${TAB_CHAR}]\\s*)+\\*/`);
}

export default toFiller;
