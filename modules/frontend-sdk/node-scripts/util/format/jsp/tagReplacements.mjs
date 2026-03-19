/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {default as toFiller, isFiller} from './toFiller.mjs';

/**
 * Valid identifier character (has property "ID Start") which we can assume is
 * very likely unused in liferay-portal.
 *
 * Unicode name is "LATIN SMALL LETTER SQUAT REVERSED ESH" and glyph is: "ʅ"
 *
 * Used to create substitutions for JSP opening tags.
 */
const BLOCK_CLOSE = '\u0285';

/**
 * Valid identifier character (has property "ID Start") which we can assume is
 * very likely unused in liferay-portal.
 *
 * Unicode name is "LATIN SMALL LETTER ESH" and glyph is: "ʃ"
 *
 * Used to create substitutions for JSP opening tags.
 */
const BLOCK_OPEN = '\u0283';

const CLOSE_TAG = isFiller(BLOCK_CLOSE);

const OPEN_TAG = new RegExp(`${isFiller(BLOCK_OPEN).source}|//${BLOCK_OPEN}+`);

//
// Create a same-length substitution for the text of the opening tag, `tag`.
//
// For example:
//
//     <foo:tag attr="this">
//                            ...becomes:
//     /*ʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃ*/
//
// Even the shortest possible tag has a same-length substitution:
//
//     <a:b>
//               ...becomes:
//     /*ʃ*/
//
// If the tag spans multiple lines a best effort is made:
//
//     <foo-bar:tag
//         attr="word"
//     >
//                            ...becomes:
//     /*ʃʃʃʃʃʃʃʃʃ
//         ʃʃʃʃʃʃʃʃʃʃʃ
//     */
//
// If the tag doesn't span multiple lines, and the `last` parameter is passed to
// indicate that the tag is the last thing on its line, we instead transform it
// as follows:
//
//     <c:if test="<%= value %>">
//                                 ...becomes:
//     //ʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃ
//
// This is to work around a peculiarity in Prettier, where it will move
// C-style (/*...*/) comments before `else` blocks inside the blocks; eg. this:
//
//     if (a) {                        |           if (a) {
//         stuff();                    |               stuff();
//     }                               |           }
//     <c:if test="<%= value %>">      |           /*ʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃ*/
//         else {                      |               else {
//             other();                |                   other();
//         }                           |               }
//     </c:if>                         |           /*ʅʅʅ*/
//
// gets turned into this, which is obviously broken:
//
//     if (a) {                        |           if (a) {
//         stuff();                    |               stuff();
//     } else {                        |           } else {
//     <c:if test="<%= value %>">      |           /*ʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃʃ*/
//             other();                |                   other();
//         }                           |               }
//     </c:if>                         |           /*ʅʅʅ*/
//

function getOpenTagReplacement(tag, last = false) {
	if (last && !tag.match(/[\n\r]/)) {

		// Replace with a one-line (//) comment.

		if (tag.length < 2) {
			throw new Error(`Invalid (underlength) tag: ${tag}`);
		}

		return `//${BLOCK_OPEN.repeat(tag.length - 2)}`;
	}
	else {

		// Replace with a C-style (/*...*/) comment.

		return toFiller(tag, BLOCK_OPEN);
	}
}

//
//
// Create a same-length substitution for the text of the closing tag, `tag`.
//
// For example:
//
//     </foo:tag>
//                 ...becomes:
//     /*ʅʅʅʅʅʅ*/
//
// The shortest possible closing tag has a same-length substitution:
//
//     </a:b>
//                ...becomes:
//     /*ʅʅ*/
//

function getCloseTagReplacement(tag) {
	return toFiller(tag, BLOCK_CLOSE);
}

//
//
// Create a same-length substitution for the text of the self-closing tag,
// `tag`.
//
// For example:
//
//     <foo:bar/>
//                 ...becomes:
//     /*╳╳╳╳╳╳*/
//

function getSelfClosingTagReplacement(tag) {
	return toFiller(tag);
}

export {
	BLOCK_CLOSE,
	BLOCK_OPEN,
	CLOSE_TAG,
	OPEN_TAG,
	getCloseTagReplacement,
	getOpenTagReplacement,
	getSelfClosingTagReplacement,
};
