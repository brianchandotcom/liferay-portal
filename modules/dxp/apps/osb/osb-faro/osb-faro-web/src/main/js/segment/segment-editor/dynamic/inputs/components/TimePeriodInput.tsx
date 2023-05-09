import autobind from 'autobind-decorator';
import React from 'react';
import {ISegmentEditorInputBase} from '../../utils/types';
import {Option, Picker} from '@clayui/core';
import {TIME_PERIOD_OPTIONS} from '../../utils/constants';

interface ITimePeriodInputProps extends ISegmentEditorInputBase {
	value: string;
}

export default class TimePeriodInput extends React.Component<ITimePeriodInputProps> {
	@autobind
	handleTimePeriodChange(value) {
		const {onChange} = this.props;

		onChange(value);
	}

	render() {
		const {value} = this.props;

		return (
			<Picker
				className='operator-input'
				data-testid='clay-select'
				items={TIME_PERIOD_OPTIONS}
				onSelectionChange={this.handleTimePeriodChange}
				selectedKey={value}
			>
				{({label, value}) => <Option key={value}>{label}</Option>}
			</Picker>
		);
	}
}
