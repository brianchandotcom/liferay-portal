/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Lexer from './Lexer.mjs';
import {CLOSE_TAG, OPEN_TAG} from './tagReplacements.mjs';

//
//
// Given `source` that has previously been processed by `substituteTags`,
// this function locates the placeholders that have been inserted to stand in
// for JSP opening and closing tags, and strips off the leading indents between
// them, effectively matching what Prettier would do anyway on seeing them.
//
// That is, given initial input:
//
//      <my:tag>
//          if (foo) {
//              alert('foo!');
//          }
//      </my:tag>
//
// `substituteTags()` will insert placeholders that are based on comments:
//
//      /* open placeholder */
//          if (foo) {
//              alert('foo!');
//          }
//      /* close placeholder */
//
// When piped through Prettier, the tag contents will get dedented:
//
//      /* open placeholder */
//      if (foo) {
//          alert('foo!');
//      }
//      /* close placeholder */
//
// So, this function does the same.

function stripIndents(source) {
	const lexer = new Lexer((api) => {
		const {choose, match} = api;

		const ANYTHING = match(/[\s\S]/);
		const CLOSE_TAG_REPLACEMENT = match(CLOSE_TAG);
		const NEWLINE = match(/\r?\n/);
		const OPEN_TAG_REPLACEMENT = match(OPEN_TAG);
		const WHITESPACE = match(/[ \t]+/);

		return choose({

			/* eslint-disable sort-keys */
			OPEN_TAG_REPLACEMENT,
			CLOSE_TAG_REPLACEMENT,
			NEWLINE,
			WHITESPACE,
			ANYTHING,

			/* eslint-enable sort-keys */
		});
	});

	let output = '';
	let indentLevel = 0;

	const tokens = [...lexer.lex(source)];

	for (let i = 0; i < tokens.length; i++) {
		const {contents, name} = tokens[i];

		switch (name) {
			case 'OPEN_TAG_REPLACEMENT':
				output += contents;
				indentLevel++;
				break;

			case 'CLOSE_TAG_REPLACEMENT':
				indentLevel--;
				output += contents;
				break;

			case 'NEWLINE':
				{
					output += contents;

					// Peek ahead to see if the next token is whitespace,
					// and trim it if necessary.

					const [maybeWhitespace, maybeCloseTag] = tokens.slice(
						i + 1,
						i + 3
					);

					if (
						maybeWhitespace &&
						maybeWhitespace.name === 'WHITESPACE'
					) {
						let trimCount = indentLevel;

						if (
							maybeCloseTag &&
							maybeCloseTag.name === 'CLOSE_TAG_REPLACEMENT'
						) {
							trimCount = indentLevel - 1;
						}

						output += maybeWhitespace.contents.replace(
							new RegExp(`^\t{0,${trimCount}}`),
							''
						);

						i++;
					}
				}

				break;

			case 'ANYTHING':
			case 'WHITESPACE':
				output += contents;
				break;

			default:
				throw new Error(`Unexpected token type: ${name}`);
		}
	}

	return output;
}

export default stripIndents;
