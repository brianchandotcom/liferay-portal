/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as fs from 'fs';

type textBlockByNumberOfWords = 50 | 100 | 200;

/**
 * Returns a string containing statically defined Lorem Ipsum text.
 *
 * The string can be composed of concatenated blocks of text or have each block wrapped in HTML paragraph tags.
 *
 * The text blocks are stored in .txt files within the "text-blocks" folder, named by word count.
 *
 * @param composition - An array defining which blocks of Lorem Ipsum text will be concatenated in the final string and their order.
 * @param htmlParagraphs - If true, returns each block wrapped in <p> tags within the final string.
 */

export default function getLoremIpsumText(
	composition: textBlockByNumberOfWords[],
	htmlParagraphs?: boolean
): string {
	let result = '';
	const textBlocks = {};
	const setOfTextBlocksKeys = new Set(composition);

	for (const key of setOfTextBlocksKeys) {
		textBlocks[key] = fs.readFileSync(
			`${__dirname}/text-blocks/${key}words.txt`,
			'utf8'
		);
	}

	for (let index = 0; index < composition.length; index++) {
		const loremBlock = composition[index];

		if (htmlParagraphs) {
			result = result + `<p>${textBlocks[loremBlock]}</p>`;
		}
		else {
			result = result + textBlocks[loremBlock];
		}

		if (index !== composition.length - 1) {
			result = result + ' ';
		}
	}

	return result;
}
