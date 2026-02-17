/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getPaddedReplacement from './getPaddedReplacement.mjs';
import lex from './lex.mjs';
import {
	getCloseTagReplacement,
	getOpenTagReplacement,
	getSelfClosingTagReplacement,
} from './tagReplacements.mjs';
import toFiller from './toFiller.mjs';

/**
 * Filler character used in scriptlet placeholders.
 *
 * Unicode name is "LATIN SMALL LETTER PHI" and glyph is: "ɸ"
 */
export const SCRIPTLET_CONTENT = '\u0278';

/**
 * Takes a source string and substitutes valid placeholder JavaScript for any
 * JSP tags.
 *
 * Assumes basically "well-formed" input (eg. nothings crazy like tags which
 * open strings but don't close them etc).
 */
function substituteTags(source) {
	let expressionCount = 0;

	let output = '';

	const tags = [];

	const tokens = lex(source).tokens;

	for (let i = 0; i < tokens.length; i++) {
		const {contents, name} = tokens[i];

		// Provisionally assume that "contents" corresponds to a tag that we're
		// going to substitute.

		tags.push(contents);

		if (name === 'CUSTOM_ACTION') {
			output += getSelfClosingTagReplacement(contents);
		}
		else if (name === 'CUSTOM_ACTION_END') {
			output += getCloseTagReplacement(contents);
		}
		else if (name === 'CUSTOM_ACTION_START') {

			// Special case 1: scan ahead to detect actions that have no
			// non-whitespace children.

			let j = i + 1;
			let outer = contents;
			let text = '';
			let comment = false;

			// Special case 2: scan ahead to detect single-line open
			// tags that have nothing after them on the same line.

			let last;

			while (j < tokens.length) {
				const token = tokens[j];

				if (token.name === 'TEMPLATE_TEXT') {
					outer += token.contents;
					text += token.contents;

					if (
						last === undefined &&
						token.contents.match(/^\s*[\r\n]/)
					) {
						last = true;
					}
				}
				else if (token.name === 'CUSTOM_ACTION') {
					outer += token.contents;
				}
				else if (
					token.name === 'CUSTOM_ACTION_END' &&
					text.match(/^\s+$/)
				) {
					outer += token.contents;
					comment = true;
					break;
				}
				else {
					break;
				}

				j++;
			}

			if (comment) {
				tags[tags.length - 1] = outer;
				output += toFiller(outer);
				i = j;
			}
			else {
				output += getOpenTagReplacement(contents, !!last);
			}
		}
		else if (name === 'EL_EXPRESSION') {
			output += getPaddedReplacement(contents, `EL_${expressionCount++}`);
		}
		else if (name === 'JSP_COMMENT') {
			output += toFiller(contents);
		}
		else if (name === 'JSP_DECLARATION') {
			output += toFiller(contents);
		}
		else if (name === 'JSP_DIRECTIVE') {
			output += toFiller(contents);
		}
		else if (name === 'JSP_EXPRESSION') {
			output += getPaddedReplacement(contents, 'JSP_EXPR');
		}
		else if (name === 'JSP_SCRIPTLET') {
			output += toFiller(contents, SCRIPTLET_CONTENT);
		}
		else if (name === 'PORTLET_NAMESPACE') {
			output += getPaddedReplacement(contents, 'PORTLET_NAMESPACE');
		}
		else if (name === 'TEMPLATE_TEXT') {
			tags.pop();
			output += contents;
		}
		else {
			throw new Error(`Unexpected token: ${name}`);
		}
	}

	return [output, tags];
}

export default substituteTags;
