/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const PADDING_MARKER = '\u00abpad\u00bb';

/**
 * The default padding string: a valid JS statement and reasonably unlikely
 * to exist organically anywhere in the liferay-portal repo.
 *
 * "\u00ab" and "\u00bb" are "Left-Pointing Double Angle Quotation
 * "Mark" («) and Right-Pointing Double Angle Quotation Mark" (»)
 * "respectively.
 */
const DEFAULT_PADDING = `void 0; /* ${PADDING_MARKER} */`;

const PADDING_LINE = /(?:\s*void\s+0;?\s*)?\/\*\s*\u00abpad\u00bb\s*\*\/\n?/g;

/**
 * Prefixes `string` with lines containing the `padding` string such that the
 * contents of string start at `startLine`.
 *
 * This so that problems found with Prettier and ESLint use line numbers
 * corresponding to the original source file from which `string` was
 * extracted.
 */
function padLines(string, startLine, padding = DEFAULT_PADDING) {
	return `${padding}\n`.repeat(startLine) + string;
}

padLines.PADDING_MARKER = PADDING_MARKER;
padLines.PADDING_LINE = PADDING_LINE;

export default padLines;
