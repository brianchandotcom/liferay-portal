import autobind from 'autobind-decorator';
import React from 'react';
import {ClaySelectWithOption} from '@clayui/select';
import {ISegmentEditorInputBase} from '../../utils/types';
import {TIME_PERIOD_OPTIONS} from '../../utils/constants';

interface ITimePeriodInputProps extends ISegmentEditorInputBase {
	value: string;
}

export default class TimePeriodInput extends React.Component<ITimePeriodInputProps> {
	@autobind
	handleTimePeriodChange(event) {
		const {value} = event.target;
		const {onChange} = this.props;

		onChange(value);
	}

	render() {
		const {value} = this.props;

		return (
			<ClaySelectWithOption
				className='operator-input'
				data-testid='clay-select'
				onChange={this.handleTimePeriodChange}
				options={TIME_PERIOD_OPTIONS}
				value={value}
			/>
		);
	}
}
