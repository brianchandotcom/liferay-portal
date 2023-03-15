import autobind from 'autobind-decorator';
import DateTimeInput from './DateTimeInput';
import Form from 'shared/components/form';
import React from 'react';
import {ClaySelectWithOption} from '@clayui/select';
import {
	getCompleteDate,
	getOperator,
	setCompleteDate,
	setOperator
} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {PropertyTypes, SUPPORTED_OPERATORS_MAP} from '../utils/constants';

const DATE_TIME_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.DateTime];

export default class CustomDateTimeInput extends React.Component<ISegmentEditorCustomInputBase> {
	@autobind
	handleDateChange(newDate) {
		const {onChange, value} = this.props;

		onChange({value: setCompleteDate(value, newDate.value)});
	}

	@autobind
	handleOperatorChange(event) {
		const {onChange, value} = this.props;

		onChange({value: setOperator(value, 0, event.target.value)});
	}

	@autobind
	renderOperatorDropdown() {
		const {value} = this.props;

		return (
			<Form.GroupItem className='operator' shrink>
				<ClaySelectWithOption
					className='criterion-input operator-input'
					onChange={this.handleOperatorChange}
					options={DATE_TIME_OPERATORS.map(({key, label}) => ({
						label,
						value: key
					}))}
					value={getOperator(value, 0)}
				/>
			</Form.GroupItem>
		);
	}

	render() {
		const {timeZoneId, value, ...otherProps} = this.props;

		return (
			<DateTimeInput
				{...otherProps}
				onChange={this.handleDateChange}
				operatorRenderer={this.renderOperatorDropdown}
				timeZoneId={timeZoneId}
				value={getCompleteDate(value)}
			/>
		);
	}
}
