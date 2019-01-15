import React from 'react';
import propTypes from 'prop-types';
import ClaySelect from '../shared/ClaySelect.es';
import DatePicker from 'react-datepicker';

// import 'react-datepicker/dist/react-datepicker.css';

import {PROPERTY_TYPES} from '../../utils/constants.es.js';

class TypedInput extends React.Component {
	_handleChange = event => {
		const {onChange} = this.props;

		const value = event.target.value;

		if (onChange) {
			onChange(value);
		}
	}
	_renderStringType = () => {
		const {options, value} = this.props;

		return (options.length === 0) ?
			<input
				className="criterion-input form-control"
				data-testid="simple-string"
				onChange={this._handleChange}
				type="text"
				value={value}
			/> :
			<ClaySelect
				className="criterion-input form-control"
				data-testid="options-string"
				onChange={this._handleChange}
				options={options.map(
					o => ({
						label: o.label,
						value: o.value
					})
				)}
				selected={value}
			/>;
	}
	_handleDateChange = dateObject => {
		const {onChange} = this.props;
		const newValue = dateObject.toISOString();

		onChange(newValue, PROPERTY_TYPES.DATE);
	}
	_renderDateType = () => {
		const {value} = this.props;

		return (<div className="criterion-input">
			<DatePicker
				className="form-control"
				onChange={this._handleDateChange}
				selected={new Date(value)}
			/>
		</div>);
	}
	_renderBooleanType = () => {
		const {value} = this.props;

		return (
			<ClaySelect
				className="criterion-input form-control"
				data-testid="options-boolean"
				onChange={this._handleChange}
				options={[
					{
						label: 'TRUE',
						value: 'true'
					},
					{
						label: 'FALSE',
						value: 'false'
					}
				]}
				selected={value}
			/>);
	}
	_handleNumberChange = event => {
		const {onChange} = this.props;
		const value = parseInt(event.target.value, 10);

		if (onChange && value !== 'NaN') {
			onChange(value);
		}
	}
	_renderNumberType = () => {
		const {value} = this.props;

		return (
			<input
				className="criterion-input form-control"
				onChange={this._handleNumberChange}
				type="number"
				value={value}
			/>);
	}
	render() {
		const {type} = this.props;

		let render;
		switch (type) {
		case PROPERTY_TYPES.STRING:
			render = this._renderStringType();
			break;
		case PROPERTY_TYPES.DATE:
			render = this._renderDateType();
			break;
		case PROPERTY_TYPES.BOOLEAN:
			render = this._renderBooleanType();
			break;
		case PROPERTY_TYPES.NUMBER:
			render = this._renderNumberType();
			break;
		default:
			render = null;
		}
		return render;
	}
}

TypedInput.propTypes = {
	onChange: propTypes.func,
	options: propTypes.array,
	type: propTypes.string.isRequired,
	value: propTypes.oneOfType(
		[
			propTypes.string,
			propTypes.number
		]
	)
};

TypedInput.defaultProps = {
	options: []
};

export default TypedInput;