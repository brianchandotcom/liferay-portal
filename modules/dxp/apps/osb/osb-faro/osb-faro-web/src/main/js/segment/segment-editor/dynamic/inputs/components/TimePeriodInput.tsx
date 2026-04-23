import autobind from 'autobind-decorator';
import React from 'react';
import {Option, Picker} from '@clayui/core';
import {TIME_PERIOD_OPTIONS} from '../../utils/constants';

interface ITimePeriodInputProps {
	onChange: (value: string) => void;
	value: string;
}

export default class TimePeriodInput extends React.Component<ITimePeriodInputProps> {
	@autobind
	handleTimePeriodChange(value: React.Key) {
		const {onChange} = this.props;

		onChange(String(value));
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
