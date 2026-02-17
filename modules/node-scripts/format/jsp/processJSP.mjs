/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dedent from './dedent.mjs';
import extractJS from './extractJS.mjs';
import indent from './indent.mjs';
import padLines from './padLines.mjs';
import restoreTags from './restoreTags.mjs';
import stripIndents from './stripIndents.mjs';
import substituteTags from './substituteTags.mjs';
import trim from './trim.mjs';

const {PADDING_LINE} = padLines;

/**
 * Applies linting and formatting to the JSP `source` string.
 *
 * Currently, the only processable elements are script tags.
 */
async function processJSP(source, onFormat) {
	const blocks = extractJS(source);

	// TODO: may want to pass filename here too, but I am not sure.

	if (!blocks.length) {
		return source;
	}

	// TODO: lint for <(aui:)?script> not followed by newline (there are basically none in liferay-portal)

	const transformed = [];

	for (const block of blocks) {
		const {contents, openTag, range} = block;

		// Script content should be indented one tab more than the opening tag.

		const baseIndent = range.start.column - openTag.length;

		// Trim leading and trailing whitespace before Prettier eats it.

		const {prefix, suffix, trimmed} = trim(contents);

		// Strip base indent.

		const dedented = dedent(trimmed);

		// Turn JSP tags, expressions (etc) into (valid JS) placeholders.

		const [substituted, tags] = substituteTags(dedented);

		// Strip internal JSP-related indents.

		const stripped = stripIndents(substituted);

		// Adjust line numbers for better error reporting.

		const padded = padLines(stripped, range.start.line);

		// (Optionally) actually format.

		const formatted = onFormat ? await onFormat(padded) : padded;

		// Remove previously inserted padding lines.

		const unpadded = formatted.replace(PADDING_LINE, '');

		// Replace placeholders with their corresponding original JSP tokens.

		const restored = restoreTags(unpadded, tags);

		// Restore base indent.

		const indented = indent(restored, baseIndent);

		transformed.push({
			...block,
			contents:
				(prefix || '\n') +
				indented +
				(suffix || '\t'.repeat(baseIndent - 1)),
		});
	}

	let result = '';
	let lastIndex = 0;

	for (let i = 0; i < transformed.length; i++) {
		const {closeTag, contents, openTag, range} = transformed[i];
		const {index, length} = range;

		result +=
			source.slice(lastIndex, index) + openTag + contents + closeTag;

		lastIndex = index + length;
	}

	return result + source.slice(lastIndex);
}

export default processJSP;
