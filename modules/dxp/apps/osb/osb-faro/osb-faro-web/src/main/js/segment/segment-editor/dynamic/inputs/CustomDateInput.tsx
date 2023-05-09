import autobind from 'autobind-decorator';
import DateInput from './DateInput';
import Form from 'shared/components/form';
import React from 'react';
import {
	getCompleteDate,
	getOperator,
	setCompleteDate,
	setOperator
} from '../utils/custom-inputs';
import {
	INPUT_DATE_FORMAT,
	PropertyTypes,
	SUPPORTED_OPERATORS_MAP
} from '../utils/constants';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {Option, Picker} from '@clayui/core';

const DATE_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.Date];

export default class CustomDateInput extends React.Component<ISegmentEditorCustomInputBase> {
	@autobind
	handleDateChange(newDate) {
		const {onChange, value} = this.props;

		onChange({value: setCompleteDate(value, newDate.value)});
	}

	@autobind
	handleOperatorChange(newValue) {
		const {onChange, value} = this.props;

		onChange({value: setOperator(value, 0, newValue)});
	}

	@autobind
	renderOperatorDropdown() {
		const {value} = this.props;

		return (
			<Form.GroupItem className='operator' shrink>
				<Picker
					className='criterion-input operator-input'
					items={DATE_OPERATORS.map(({key, label}) => ({
						label,
						value: key
					}))}
					onSelectionChange={this.handleOperatorChange}
					selectedKey={getOperator(value, 0)}
				>
					{({label, value}) => <Option key={value}>{label}</Option>}
				</Picker>
			</Form.GroupItem>
		);
	}

	render() {
		const {value, ...otherProps} = this.props;

		return (
			<DateInput
				{...otherProps}
				displayFormat={INPUT_DATE_FORMAT}
				onChange={this.handleDateChange}
				operatorRenderer={this.renderOperatorDropdown}
				value={getCompleteDate(value)}
			/>
		);
	}
}
