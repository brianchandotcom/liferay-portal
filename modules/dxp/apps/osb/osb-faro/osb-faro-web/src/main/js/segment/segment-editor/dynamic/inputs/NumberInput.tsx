import autobind from 'autobind-decorator';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import React from 'react';
import {ISegmentEditorInputBase} from '../utils/types';
import {isNull} from 'lodash';
import {isValid} from '../utils/utils';

interface INumberInputProps extends ISegmentEditorInputBase {
	touched: boolean;
	valid: boolean;
	value: string | number;
}

export default class NumberInput extends React.Component<INumberInputProps> {
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
	handleChange(event) {
		const {value} = event.target;

		const {onChange} = this.props;

		let numberVal: string | number = '';

		if (isValid(value)) {
			numberVal = value;
		}

		onChange({valid: isValid(numberVal), value: numberVal});
	}

	render() {
		const {
			className,
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName},
			touched,
			valid,
			value
		} = this.props;

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

					{!isNull(value) && (
						<Form.GroupItem
							className={getCN(className, {
								'has-error': !valid && touched
							})}
							shrink
						>
							<Input
								data-testid='number-input'
								onBlur={this.handleBlur}
								onChange={this.handleChange}
								type='number'
								value={value}
							/>
						</Form.GroupItem>
					)}
				</Form.Group>
			</div>
		);
	}
}
