import dateFns from 'date-fns';
import propTypes from 'prop-types';
import React from 'react';
import {PROPERTY_TYPES} from 'utils/constants.es';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

class DateTimeInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		value: propTypes.string
	};

	state = {};

	static getDerivedStateFromProps(props, state) {
		let returnVal = null;

		if (props.value != state.initialValue) {
			returnVal = {
				initialValue: props.value,
				value: dateFns.format(new Date(props.value), INPUT_DATE_FORMAT)
			};
		}
		return returnVal;
	}

	_handleDateChange = event => {
		const value = event.target.value;

		this.setState({value});
	};

	_handleDateBlur = event => {
		const date = dateFns.format(event.target.value, INPUT_DATE_FORMAT);

		if (date !== 'Invalid Date') {
			this.setState(
				{
					value: date
				},
				() => {
					this.props.onChange({
						type: PROPERTY_TYPES.DATE_TIME,
						value: dateFns
							.parse(date, INPUT_DATE_FORMAT)
							.toISOString()
					});
				}
			);
		} else {
			const resetDate = dateFns.format(new Date(), INPUT_DATE_FORMAT);

			this.setState(
				{
					value: resetDate
				},
				() => {
					this.props.onChange({
						type: PROPERTY_TYPES.DATE_TIME,
						value: dateFns
							.parse(resetDate, INPUT_DATE_FORMAT)
							.toISOString()
					});
				}
			);
		}
	};

	render() {
		const {value} = this.state;
		const {disabled} = this.props;

		return (
			<div className='criterion-input date-input'>
				<input
					className='form-control'
					data-testid='date-input'
					disabled={disabled}
					onBlur={this._handleDateBlur}
					onChange={this._handleDateChange}
					type='date'
					value={value}
				/>
			</div>
		);
	}
}

export default DateTimeInput;
