/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Vocabulary} from '../Categorization';

/**
 * Returns the count of total categories in a collections of vocabularies
 * @param {array} vocabularies A collection of vocabularies from a Content Dashboard Item.
 * @returns {number} The total sum of every categories array inside each vocabulary
 */
declare const getCategoriesCountFromVocabularies: (
	vocabularies: Vocabulary[]
) => number;

/**
 * Divides the array in two arrays, grouped by type
 * @param {Object} paramsObject
 * @param {array} paramsObject.array A collection of vocabularies from a Content Dashboard Item.
 * @param {string} paramsObject.type A string representing the type
 * @param {string} paramsObject.key A string representing the property key to access the type of the item
 * @returns {array[][]} An array containing two arrays
 */
declare const groupVocabulariesBy: ({
	array,
	key,
	value,
}: {
	array: Vocabulary[];
	key: keyof Vocabulary;
	value: boolean;
}) => [Vocabulary[], Vocabulary[]];

/**
 * Sorts an array by a given criteria, being the value of this criteria a string
 * If no key is present the sorting applies directly over each item, assuming they are strings
 * @param {Object} paramsObject
 * @param {array} paramsObject.array A collection of vocabularies from a Content Dashboard Item.
 * @param {string} [paramsObject.key = ''] A string representing the property
 * @returns {array} An array sorted by a property
 */
declare const sortByStrings: ({
	array,
	key,
}: {
	array: Vocabulary[];
	key: keyof Vocabulary;
}) => Vocabulary[];
export {getCategoriesCountFromVocabularies, groupVocabulariesBy, sortByStrings};
