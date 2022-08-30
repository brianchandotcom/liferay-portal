/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
