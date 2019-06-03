import propTypes from 'prop-types';
import React from 'react';

class IntegerInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	_handleIntegerChange = event => {
		const value = parseInt(event.target.value, 10);

		if (!isNaN(value)) {
			this.props.onChange({value: value.toString()});
		}
	};

	render() {
		const {disabled, value} = this.props;

		return (
			<input
				className='criterion-input form-control'
				data-testid='integer-number'
				disabled={disabled}
				onChange={this._handleIntegerChange}
				type='number'
				value={value}
			/>
		);
	}
}

export default IntegerInput;
