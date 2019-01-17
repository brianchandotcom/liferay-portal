import React from 'react';
import propTypes from 'prop-types';
import ClaySelect from '../shared/ClaySelect.es';
import dateFns from 'date-fns';
import {PROPERTY_TYPES} from '../../utils/constants.es.js';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

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
	_handleDateChange = event => {
		const {onChange} = this.props;

		const value = event.target.value || dateFns.format(new Date(), INPUT_DATE_FORMAT);
		const iSOStringValue = dateFns.parse(value, INPUT_DATE_FORMAT).toISOString();

		onChange(iSOStringValue, PROPERTY_TYPES.DATE);
	}

	_renderDateType = () => {
		const {value} = this.props;

		const date = new Date(value);
		const domStringDate = dateFns.format(date, INPUT_DATE_FORMAT);

		return (<div className="criterion-input">
			<input
				className="form-control"
				onChange={this._handleDateChange}
				type="date"
				value={domStringDate}
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

		if (onChange && !isNaN(value)) {
			onChange(value.toString());
		}
	}

	_renderNumberType = () => {
		const {value} = this.props;

		return (
			<input
				className="criterion-input form-control"
				data-testid="simple-number"
				onChange={this._handleNumberChange}
				type="number"
				value={value}
			/>);
	}

	_getInputRenderer(type) {
		const renderers = {
			[PROPERTY_TYPES.BOOLEAN]: this._renderBooleanType,
			[PROPERTY_TYPES.DATE]: this._renderDateType,
			[PROPERTY_TYPES.NUMBER]: this._renderNumberType,
			[PROPERTY_TYPES.STRING]: this._renderStringType
		};

		return renderers[type] || renderers[PROPERTY_TYPES.STRING];
	}

	render() {
		const {type} = this.props;

		const inputRenderer = this._getInputRenderer(type);

		return inputRenderer();
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