/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useRef, useState} from 'react';

export const ITEM_ID_PROPERTY = '_inputSetItemId';

/**
 * Moves an item in an array. Does not mutate the original array.
 * @param {Array} list The list to move an item.
 * @param {number} from The index of the item being moved.
 * @param {number} to The new index that the item will be moved to.
 *  @return {Array} Array of items with new order.
 */
function move(list, from, to) {
	const listWithInserted = [
		...list.slice(0, to),
		list[from],
		...list.slice(to, list.length),
	];

	const updatedFrom = from > to ? from + 1 : from;

	return listWithInserted.filter((_, index) => index !== updatedFrom);
}

/**
 * Ensures that `initialValue` is an array, otherwise functions in
 * `useInputSets` will be incompatible. Also adds IDs to properly move and
 * delete items.
 * @param {*} initialValue
 * @return {Array}
 */
function prepareInitialValue(initialValue) {
	let parsedValue = initialValue;

	if (typeof initialValue === 'string' || initialValue instanceof String) {
		try {
			parsedValue = JSON.parse(initialValue);
		}
		catch (error) {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			return [];
		}
	}

	if (Array.isArray(parsedValue)) {

		// Add IDs

		return parsedValue.map((item, index) => ({
			[ITEM_ID_PROPERTY]: index,
			...item,
		}));
	}
	else {
		return [];
	}
}

function useInputSets(initialValue) {
	const preparedInitialValue = useMemo(
		() => prepareInitialValue(initialValue),
		[initialValue]
	);

	/**
	 * Used for assigning IDs to newly added items.
	 */
	const idCounterRef = useRef(preparedInitialValue.length);

	const [value, setValue] = useState(preparedInitialValue);

	/**
	 * A helper function to get the ID to be used as the `key` property in
	 * the `InputSets.Item` component.
	 * @param {object} inputSetItem
	 * @returns {number}
	 */
	const _getInputSetItemId = (inputSetItem) => {
		return inputSetItem[ITEM_ID_PROPERTY];
	};

	/**
	 * A helper function to pass in required props into the `InputSets.Item`
	 * component.
	 *
	 * Example:
	 * <InputSets.Item {...getInputSetItemProps(valueItem, valueIndex)}>
	 *
	 * @param {object} inputSetItem
	 * @returns {object}
	 */
	const _getInputSetItemProps = (inputSetItem, index) => {
		return {
			index,
			isLastItem: value.length - 1 === index,
			key: _getInputSetItemId(inputSetItem),
			onInputSetItemDelete: _handleInputSetItemDelete,
			onInputSetItemMove: _handleInputSetItemMove,
		};
	};

	/**
	 * Adds a new item to the end of the input sets.
	 * @param {object} newValue The new object to add.
	 */
	const _handleItemSetsAdd = (newValue = {}) => {
		setValue([
			...value,
			{
				...newValue,
				[ITEM_ID_PROPERTY]: idCounterRef.current++,
			},
		]);
	};

	/**
	 * Replaces the input sets with a new list and updates
	 * idCounterRef for the new length.
	 * @param {Array} newValue The new list for input sets.
	 */
	const _handleInputSetsChange = (newValue) => {
		const preparedValue = prepareInitialValue(newValue);

		setValue(preparedValue);

		idCounterRef.current = preparedValue.length;
	};

	/**
	 * Changes a single object property's value.
	 * @param {number} index The position of the item in the list.
	 * @param {string|object} newValue If this is a string, value will be used as
	 * 	the property name.
	 * 	If this is an object, this will be used as the new value to merge into
	 * 	the existing value.
	 */
	const _handleInputSetItemChange = (index, newValue) => {
		if (typeof newValue !== 'string') {
			setValue(
				value.map((item, i) =>
					i === index ? {...item, ...newValue} : item
				)
			);
		}
		else {
			return (event) => {
				setValue(
					value.map((item, i) =>
						i === index
							? {...item, [newValue]: event.target.value}
							: item
					)
				);
			};
		}
	};

	/**
	 * Deletes the item at the specified `index`. The exported
	 * `onInputSetItemDelete` should be passed into the `InputSets.Item`
	 * component.
	 * @param {number} index The index position to be removed.
	 * @returns
	 */
	const _handleInputSetItemDelete = (index) => () => {
		setValue(value.filter((_, i) => i !== index));
	};

	/**
	 * Moves a set item. The exported `onInputSetItemMove` should be passed into
	 * the `InputSets.Item` component.
	 * @param {number} from The current index position.
	 * @param {number} to The new index position to move to.
	 */
	const _handleInputSetItemMove = (from, to) => {
		setValue(move(value, from, to));
	};

	/**
	 * Similar to `_handleInputSetItemChange` except this will replace the
	 * entire object.
	 * @param {number} index
	 * @param {string} newValue
	 * @returns
	 */
	const _handleInputSetItemReplace = (index, newValue) => () => {
		setValue(
			value.map((item, i) =>
				i === index
					? {[ITEM_ID_PROPERTY]: item[ITEM_ID_PROPERTY], ...newValue}
					: item
			)
		);
	};

	return {
		getInputSetItemId: _getInputSetItemId,
		getInputSetItemProps: _getInputSetItemProps,
		onInputSetItemChange: _handleInputSetItemChange,
		onInputSetItemDelete: _handleInputSetItemDelete,
		onInputSetItemMove: _handleInputSetItemMove,
		onInputSetItemReplace: _handleInputSetItemReplace,
		onInputSetsAdd: _handleItemSetsAdd,
		onInputSetsChange: _handleInputSetsChange,
		value,
	};
}

export {useInputSets};
