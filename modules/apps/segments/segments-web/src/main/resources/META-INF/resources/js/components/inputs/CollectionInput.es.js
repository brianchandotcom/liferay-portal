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

import propTypes from 'prop-types';
import React from 'react';

/**
 * Input displayed for collection type properties. 2 inputs will be displayed
 * side by side. The resulting value will be a single string with an '='
 * character separating the key and value.
 * @class CollectionInput
 * @extends {React.Component}
 */
class CollectionInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.string
	};

	/**
	 * Updates the left-side of the '=' character in the value.
	 * @param {SyntheticEvent} event Input change event.
	 */
	_handleKeyChange = event => {
		const {value} = this._stringToKeyValueObject(this.props.value);

		this.props.onChange({value: `${event.target.value}=${value}`});
	};

	/**
	 * Updates the right-side of the '=' character in the value.
	 * @param {SyntheticEvent} event Input change event.
	 */
	_handleValueChange = event => {
		const {key} = this._stringToKeyValueObject(this.props.value);

		this.props.onChange({value: `${key}=${event.target.value}`});
	};

	/**
	 * Prevents an '=' character from being entered into the input.
	 * @param {SyntheticEvent} event Input keydown event.
	 */
	_handleKeyDown = event => {
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
	_stringToKeyValueObject = stringValue => {
		const [key = '', value = ''] =
			typeof stringValue == 'string' ? stringValue.split('=') : [];

		return {
			key,
			value
		};
	};

	render() {
		const {disabled} = this.props;
		const {key, value} = this._stringToKeyValueObject(this.props.value);

		return (
			<>
				<input
					className="criterion-input form-control"
					data-testid="collection-key-input"
					disabled={disabled}
					onChange={this._handleKeyChange}
					onKeyDown={this._handleKeyDown}
					placeholder={Liferay.Language.get('key')}
					type="text"
					value={key}
				/>

				<input
					className="criterion-input form-control"
					data-testid="collection-value-input"
					disabled={disabled}
					onChange={this._handleValueChange}
					onKeyDown={this._handleKeyDown}
					placeholder={Liferay.Language.get('value')}
					type="text"
					value={value}
				/>
			</>
		);
	}
}

export default CollectionInput;
