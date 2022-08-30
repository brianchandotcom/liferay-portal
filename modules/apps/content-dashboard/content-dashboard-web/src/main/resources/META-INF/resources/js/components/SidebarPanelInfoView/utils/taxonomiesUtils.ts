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
 * Returns the value lowercased in case of string
 * @param {string|*} value A string or boolean
 * @returns {string|*} Value lowercased or value
 */
const _formatValueForCompare = (value: string | unknown): string | unknown =>
	typeof value === 'string' ? value.toLowerCase() : value;

/**
 * Returns the count of total categories in a collections of vocabularies
 * @param {array} vocabularies A collection of vocabularies from a Content Dashboard Item.
 * @returns {number} The total sum of every categories array inside each vocabulary
 */
const getCategoriesCountFromVocabularies = (
	vocabularies: Vocabulary[]
): number =>
	vocabularies.reduce((total, {categories}) => total + categories.length, 0);

/**
 * Divides the array in two arrays, grouped by type
 * @param {Object} paramsObject
 * @param {array} paramsObject.array A collection of vocabularies from a Content Dashboard Item.
 * @param {string} paramsObject.type A string representing the type
 * @param {string} paramsObject.key A string representing the property key to access the type of the item
 * @returns {array[][]} An array containing two arrays
 */
const groupVocabulariesBy = ({
	array,
	key,
	value,
}: {
	array: Vocabulary[];
	key: keyof Vocabulary;
	value: boolean;
}): [Vocabulary[], Vocabulary[]] =>
	array.reduce(
		([isFromType, isNotFromType], item) => {
			const currentItemValue = _formatValueForCompare(item[key]);
			const valueToCompare = _formatValueForCompare(value);

			(currentItemValue === valueToCompare
				? isFromType
				: isNotFromType
			).push(item);

			return [isFromType, isNotFromType];
		},
		[[] as Vocabulary[], [] as Vocabulary[]]
	);

/**
 * Sorts an array by a given criteria, being the value of this criteria a string
 * If no key is present the sorting applies directly over each item, assuming they are strings
 * @param {Object} paramsObject
 * @param {array} paramsObject.array A collection of vocabularies from a Content Dashboard Item.
 * @param {string} [paramsObject.key = ''] A string representing the property
 * @returns {array} An array sorted by a property
 */
const sortByStrings = ({
	array,
	key,
}: {
	array: Vocabulary[];
	key: keyof Vocabulary;
}) => {
	return array.sort((a, b) => {
		const _getValue = (
			value: Vocabulary
		): string | boolean | Vocabulary | string[] =>
			key && key in value ? value[key] : value;

		const firstValue = _getValue(a);
		const secondValue = _getValue(b);

		return (firstValue as string).localeCompare(secondValue as string);
	});
};

export {getCategoriesCountFromVocabularies, groupVocabulariesBy, sortByStrings};
