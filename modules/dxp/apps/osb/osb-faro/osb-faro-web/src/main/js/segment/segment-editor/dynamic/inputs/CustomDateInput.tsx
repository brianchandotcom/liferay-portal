import autobind from 'autobind-decorator';
import DateInput from './DateInput';
import Form from 'shared/components/form';
import React from 'react';
import {ClaySelectWithOption} from '@clayui/select';
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

const DATE_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.Date];

export default class CustomDateInput extends React.Component<ISegmentEditorCustomInputBase> {
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
					options={DATE_OPERATORS.map(({key, label}) => ({
						label,
						value: key
					}))}
					value={getOperator(value, 0)}
				/>
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
