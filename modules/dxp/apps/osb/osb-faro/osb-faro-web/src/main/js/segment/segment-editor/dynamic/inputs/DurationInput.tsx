import autobind from 'autobind-decorator';
import DurationInput from 'shared/components/DurationInput';
import Form from 'shared/components/form';
import getCN from 'classnames';
import React from 'react';
import {ISegmentEditorInputBase} from '../utils/types';
import {isValid} from '../utils/utils';

interface ISegmentDurationInputProps extends ISegmentEditorInputBase {
	touched: boolean;
	valid: boolean;
	value: string | number;
}

export default class SegmentDurationInput extends React.Component<ISegmentDurationInputProps> {
	_completedAnalytics = false;

	componentDidUpdate() {
		const {
			id,
			property: {entityName, type},
			valid
		} = this.props;

		if (!id && valid && !this._completedAnalytics) {
			this._completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	}

	@autobind
	handleBlur() {
		const {onChange, value} = this.props;

		onChange({touched: true, valid: isValid(value)});
	}

	@autobind
	handleChange(value) {
		const {onChange} = this.props;

		onChange({valid: isValid(value), value});
	}

	render() {
		const {
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName},
			touched,
			valid,
			value
		} = this.props;

		const showError = !valid && touched;

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className='display-value' label shrink>
						{displayValue}
					</Form.GroupItem>

					<OperatorDropdown />

					<Form.GroupItem
						className={getCN({'has-error': showError})}
						shrink
					>
						<DurationInput
							onBlur={this.handleBlur}
							onChange={this.handleChange}
							value={value}
						/>
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
