/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * RegExp to recognize <script> and <aui:script> tags.
 *
 * Some peculiarities to note:
 *
 * - The use of non-greedy matches (eg. ".*?").
 * - The use of negative lookbehind (eg. "(?<!)") to avoid premature
 *   termination of the open-tag match: this stops a "%>" inside an JSP
 *   expression, or "/>" inside a "<portlet:namespace />" tag, inside
 *   attributes from cutting off the match too soon.
 */
const AUI_SCRIPT = /(<(aui:)?script(.*?)(?<![%/])>)(.*?)(<\/\2script>)/s;

const AUI_SCRIPT_G = new RegExp(AUI_SCRIPT.source, 'gs');

const NEWLINE = /\r?\n/;

/**
 * Simple RegExp to extract the "type" attribute. Doesn't attempt to deal with
 * escaped characters or anything crazy.
 */
const SCRIPT_TYPE = /\btype=(['"])(.*?)\1/;

/**
 * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script
 * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types#JavaScript_types
 */
const JS_TYPES = new Set([
	'application/ecmascript',
	'application/javascript',
	'application/x-ecmascript',
	'application/x-javascript',
	'module',
	'text/ecmascript',
	'text/javascript',
	'text/javascript1.0',
	'text/javascript1.1',
	'text/javascript1.2',
	'text/javascript1.3',
	'text/javascript1.4',
	'text/javascript1.5',
	'text/jscript',
	'text/livescript',
	'text/x-ecmascript',
	'text/x-javascript',
]);

/**
 * Extracts a series of script blocks from a JSP source string.
 */
function extractJS(source) {
	const blocks = [];

	if (AUI_SCRIPT.test(source)) {
		source.replace(
			AUI_SCRIPT_G,
			(
				match,
				openTag,
				tagNamespace,
				scriptAttributes,
				body,
				closeTag,
				offset,
				string
			) => {
				const type = scriptAttributes.match(SCRIPT_TYPE);

				if (type && !JS_TYPES.has(type[2])) {
					return;
				}

				if (body) {
					const prefix = string.slice(0, offset + openTag.length);

					const prefixLines = prefix.split(NEWLINE);

					const lastPrefixLine = getLast(prefixLines);

					const contentLines = body.split(NEWLINE);

					const lastContentLine = getLast(contentLines);

					// Handle edge case where the openTag and the content all
					// fit on one line (eg. <script>code();</script>).

					const lastLine =
						contentLines.length === 1
							? lastPrefixLine + lastContentLine
							: lastContentLine;

					blocks.push({
						closeTag,
						contents: body,
						match,
						openTag,

						// TODO: consider getting rid of end/start

						range: {
							end: {
								column: lastLine.length + 1,
								line:
									prefixLines.length +
									contentLines.length -
									1,
							},
							index: offset,
							length: match.length,
							start: {
								column: lastPrefixLine.length + 1,
								line: prefixLines.length,
							},
						},
						scriptAttributes,
						tagNamespace,
					});
				}
			}
		);
	}

	return blocks;
}

/**
 * Returns the last element in `array`.
 */
function getLast(array) {
	return array[array.length - 1];
}

export default extractJS;
