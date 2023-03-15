import autobind from 'autobind-decorator';
import DateInput from 'shared/components/DateInput';
import React from 'react';
import Row from '../components/Row';

export default class DateInputKit extends React.Component {
	state = {
		value: ''
	};

	@autobind
	handleChange(value) {
		this.setState({
			value
		});
	}

	render() {
		const {value} = this.state;

		return (
			<div>
				<Row>
					<DateInput
						format='YYYY-MM-DD LT'
						onDateInputChange={this.handleChange}
						placeholder='Pick a date...'
						showTimeSelector
						value={value}
					/>
				</Row>

				<div>{`Selected Date: ${value}`}</div>
			</div>
		);
	}
}
