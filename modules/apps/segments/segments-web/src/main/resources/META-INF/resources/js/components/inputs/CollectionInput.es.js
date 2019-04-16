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
	}

	/**
	 * Updates the right-side of the '=' character in the value.
	 * @param {SyntheticEvent} event Input change event.
	 */
	_handleValueChange = event => {
		const {key} = this._stringToKeyValueObject(this.props.value);

		this.props.onChange({value: `${key}=${event.target.value}`});
	}

	/**
	 * Prevents an '=' character from being entered into the input.
	 * @param {SyntheticEvent} event Input keydown event.
	 */
	_handleKeyDown = event => {
		if (event.key === '=') {
			event.preventDefault();
		}
	}

	/**
	 * Takes a string value in the format 'key=value' and returns an object
	 * with a key and value property. For example: {key: 'key', value: 'value'}
	 * @param {string} value A string with an '=' character.
	 * @returns {Object} Object with key and value properties.
	 */
	_stringToKeyValueObject = value => {
		const valueArray = value.split('=');

		return {
			key: valueArray[0] || '',
			value: valueArray[1] || ''
		};
	}

	render() {
		const {key, value} = this._stringToKeyValueObject(this.props.value);

		return (
			<React.Fragment>
				<input
					className="criterion-input form-control"
					data-testid="collection-key-input"
					onChange={this._handleKeyChange}
					onKeyDown={this._handleKeyDown}
					placeholder={Liferay.Language.get('key')}
					type="text"
					value={key}
				/>

				<input
					className="criterion-input form-control"
					data-testid="collection-value-input"
					onChange={this._handleValueChange}
					onKeyDown={this._handleKeyDown}
					placeholder={Liferay.Language.get('value')}
					type="text"
					value={value}
				/>
			</React.Fragment>
		);
	}
}

export default CollectionInput;