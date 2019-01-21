import React from 'react';
import propTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import ClaySelect from '../shared/ClaySelect.es';
import dateFns from 'date-fns';
import {BOOLEAN_OPTIONS, PROPERTY_TYPES} from '../../utils/constants.es.js';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

class TypedInput extends React.Component {
	static propTypes = {
		onChange: propTypes.func.isRequired,
		options: propTypes.array,
		selectEntity: propTypes.shape(
			{
				id: propTypes.string,
				title: propTypes.string,
				uri: propTypes.string
			}
		),
		type: propTypes.string.isRequired,
		value: propTypes.oneOfType(
			[
				propTypes.string,
				propTypes.number
			]
		)
	};

	static defaultProps = {
		options: []
	};

	constructor(props) {
		super(props);

		this.state = {
			decimal: props.value
		};
	}

	_getInputRenderer(type) {
		const renderers = {
			[PROPERTY_TYPES.BOOLEAN]: this._renderBooleanType,
			[PROPERTY_TYPES.DATE]: this._renderDateType,
			[PROPERTY_TYPES.DOUBLE]: this._renderDecimalType,
			[PROPERTY_TYPES.ID]: this._renderIdType,
			[PROPERTY_TYPES.INTEGER]: this._renderIntegerType,
			[PROPERTY_TYPES.STRING]: this._renderStringType
		};

		return renderers[type] || renderers[PROPERTY_TYPES.STRING];
	}

	_handleChange = event => {
		this.props.onChange(event.target.value);
	}

	_handleDateChange = event => {
		const value = event.target.value ||
			dateFns.format(new Date(), INPUT_DATE_FORMAT);

		const iSOStringValue = dateFns
			.parse(value, INPUT_DATE_FORMAT)
			.toISOString();

		this.props.onChange(iSOStringValue, PROPERTY_TYPES.DATE);
	}

	_handleDecimalBlur = event => {
		const value = Number.parseFloat(event.target.value).toFixed(2);

		this.setState(
			{
				decimal: value
			},
			this.props.onChange(value)
		);
	}

	_handleDecimalChange = event => {
		this.setState(
			{
				decimal: event.target.value
			}
		);
	}

	_handleIntegerChange = event => {
		const value = parseInt(event.target.value, 10);

		if (!isNaN(value)) {
			this.props.onChange(value.toString());
		}
	}

	_handleSelectEntity = () => {
		const {
			onChange,
			selectEntity: {id, title, uri}
		} = this.props;

		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				id,
				title,
				uri
			},
			event => {
				onChange(event.entityid);
			}
		);
	}

	_renderBooleanType = () => (
		<ClaySelect
			className="criterion-input form-control"
			data-testid="options-boolean"
			onChange={this._handleChange}
			options={BOOLEAN_OPTIONS}
			selected={this.props.value}
		/>
	);

	_renderDateType = () => {
		const date = new Date(this.props.value);

		const domStringDate = dateFns.format(date, INPUT_DATE_FORMAT);

		return (
			<div className="criterion-input date-input">
				<input
					className="form-control"
					data-testid="date-input"
					onChange={this._handleDateChange}
					type="date"
					value={domStringDate}
				/>
			</div>
		);
	}

	_renderDecimalType = () => (
		<input
			className="criterion-input form-control"
			data-testid="decimal-number"
			onBlur={this._handleDecimalBlur}
			onChange={this._handleDecimalChange}
			step="0.01"
			type="number"
			value={this.state.decimal}
		/>
	);

	_renderIntegerType = () => (
		<input
			className="criterion-input form-control"
			data-testid="integer-number"
			onChange={this._handleIntegerChange}
			type="number"
			value={this.props.value}
		/>
	);

	_renderIdType = () => {
		const {value} = this.props;

		return (
			<div className="criterion-input input-group select-entity-input">
				<div className="input-group-item input-group-prepend">
					<input type="hidden" value={value} />

					<input
						className="form-control"
						data-testid="entity-select-input"
						readOnly
						value={value}
					/>
				</div>

				<span className="input-group-append input-group-item input-group-item-shrink">
					<ClayButton
						label={Liferay.Language.get('select')}
						onClick={this._handleSelectEntity}
					/>
				</span>
			</div>
		);
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

	render() {
		const inputRenderer = this._getInputRenderer(this.props.type);

		return inputRenderer();
	}
}

export default TypedInput;