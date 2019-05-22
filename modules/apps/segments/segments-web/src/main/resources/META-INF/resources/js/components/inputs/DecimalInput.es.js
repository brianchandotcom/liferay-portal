import propTypes from 'prop-types';
import React from 'react';

class DecimalInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	_handleDecimalBlur = event => {
		const value = Number.parseFloat(event.target.value).toFixed(2);

		this.props.onChange({value});
	};

	_handleDecimalChange = event => {
		this.props.onChange({value: event.target.value});
	};

	render() {
		const {disabled, value} = this.props;

		return (
			<input
				className='criterion-input form-control'
				data-testid='decimal-number'
				disabled={disabled}
				onBlur={this._handleDecimalBlur}
				onChange={this._handleDecimalChange}
				step='0.01'
				type='number'
				value={value}
			/>
		);
	}
}

export default DecimalInput;
