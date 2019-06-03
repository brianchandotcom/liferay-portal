import propTypes from 'prop-types';
import React from 'react';
import {jsDatetoYYYYMMDD} from '../../utils/utils.es';
import {PROPERTY_TYPES} from 'utils/constants.es';

class DateInput extends React.Component {
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
				value: props.value
			};
		}
		return returnVal;
	}

	_handleDateChange = event => {
		const value = event.target.value;

		this.setState({value});
	};

	_handleDateBlur = event => {
		const date = jsDatetoYYYYMMDD(event.target.value);

		if (date !== 'Invalid Date') {
			this.setState({value: date}, () => {
				this.props.onChange({
					type: PROPERTY_TYPES.DATE,
					value: date
				});
			});
		} else {
			this.setState({value: jsDatetoYYYYMMDD(new Date())}, () => {
				this.props.onChange({
					type: PROPERTY_TYPES.DATE,
					value: jsDatetoYYYYMMDD(new Date())
				});
			});
		}
	};

	render() {
		const {disabled} = this.props;
		const {value} = this.state;

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

export default DateInput;
