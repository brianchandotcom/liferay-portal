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

import {ClayInput} from '@clayui/form';
import propTypes from 'prop-types';
import React from 'react';

/**
 * Input displayed for collection type properties. 2 inputs will be displayed
 * side by side. The resulting value will be a single string with an '='
 * character separating the key and value.
 * @function CollectionInput
 */
function CollectionInput({
	disabled,
	onChange,
	propertyLabel,
	value: initialValue,
}) {

	/**
	 * Updates the left-side of the '=' character in the value.
	 * @param {SyntheticEvent} event Input change event.
	 */
	const onKeyChange = (event) => {
		const {value} = stringToKeyValueObject(initialValue);

		onChange({value: `${event.target.value}=${value}`});
	};

	/**
	 * Updates the right-side of the '=' character in the value.
	 * @param {SyntheticEvent} event Input change event.
	 */
	const onValueChange = (event) => {
		const {key} = stringToKeyValueObject(initialValue);

		onChange({value: `${key}=${event.target.value}`});
	};

	/**
	 * Prevents an '=' character from being entered into the input.
	 * @param {SyntheticEvent} event Input keydown event.
	 */
	const onKeyDown = (event) => {
		if (event.key === '=') {
			event.preventDefault();
		}
	};

	/**
	 * Takes a string value in the format 'key=value' and returns an object
	 * with a key and value property. For example: {key: 'key', value: 'value'}
	 * @param {string} value A string with an '=' character.
	 * @returns {Object} Object with key and value properties.
	 */
	const stringToKeyValueObject = (stringValue) => {
		const [key = '', value = ''] =
			typeof stringValue === 'string' ? stringValue.split('=') : [];

		return {
			key,
			value,
		};
	};

	const {key, value} = stringToKeyValueObject(initialValue);

	return (
		<>
			<ClayInput
				aria-label={`${propertyLabel}: ${Liferay.Language.get(
					'input-a-key'
				)}`}
				className="criterion-input"
				data-testid="collection-key-input"
				disabled={disabled}
				onChange={onKeyChange}
				onKeyDown={onKeyDown}
				placeholder={Liferay.Language.get('key')}
				type="text"
				value={key}
			/>

			<ClayInput
				aria-label={`${propertyLabel}: ${Liferay.Language.get(
					'input-a-value'
				)}`}
				className="criterion-input"
				data-testid="collection-value-input"
				disabled={disabled}
				onChange={onValueChange}
				onKeyDown={onKeyDown}
				placeholder={Liferay.Language.get('value')}
				type="text"
				value={value}
			/>
		</>
	);
}

CollectionInput.propTypes = {
	disabled: propTypes.bool,
	onChange: propTypes.func.isRequired,
	propertyLabel: propTypes.string.isRequired,
	value: propTypes.string,
};

export default CollectionInput;
