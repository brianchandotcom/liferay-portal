import autobind from 'autobind-decorator';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import React from 'react';
import {getPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {
	isKnown,
	isUnknown,
	PropertyTypes,
	RelationalOperators,
	SUPPORTED_OPERATORS_MAP
} from '../utils/constants';
import {isOfKnownType, isValid} from '../utils/utils';
import {Map} from 'immutable';
import {Option, Picker} from '@clayui/core';

const NUMBER_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.Number];

interface ICustomNumberInputProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

export default class CustomNumberInput extends React.Component<ICustomNumberInputProps> {
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

	getSelectedOperatorKey() {
		const criterionIMap = this.props.value.getIn(
			['criterionGroup', 'items', 0],
			Map()
		);

		const operatorName = criterionIMap.get('operatorName');
		const value = criterionIMap.get('value');

		let operatorKey = operatorName;

		const valueNull = value === null;

		if (operatorName === RelationalOperators.EQ && valueNull) {
			operatorKey = isUnknown;
		} else if (operatorName === RelationalOperators.NE && valueNull) {
			operatorKey = isKnown;
		}

		return NUMBER_OPERATORS.find(({key}) => key === operatorKey).key;
	}

	@autobind
	handleBlur() {
		const {onChange, value} = this.props;

		onChange({
			touched: true,
			valid: isValid(getPropertyValue(value, 'value', 0)),
			value
		});
	}

	@autobind
	handleOperatorChange(operator) {
		const {onChange, value: valueIMap} = this.props;

		let newVal = null;

		newVal = valueIMap.setIn(
			['criterionGroup', 'items', 0, 'operatorName'],
			NUMBER_OPERATORS.find(({key}) => key === operator).name
		);

		if (isOfKnownType(operator)) {
			newVal = newVal.setIn(
				['criterionGroup', 'items', 0, 'value'],
				null
			);
		} else if (getPropertyValue(valueIMap, 'value', 0) === null) {
			newVal = newVal.setIn(['criterionGroup', 'items', 0, 'value'], '');
		}

		onChange({
			valid: isValid(
				newVal.getIn(['criterionGroup', 'items', 0, 'value'])
			),
			value: newVal
		});
	}

	@autobind
	handleValueChange(event) {
		const {value} = event.target;

		const {onChange, value: valueIMap} = this.props;

		let numberVal: string | number = '';

		const valid = isValid(value);

		if (valid) {
			numberVal = value;
		}

		onChange({
			valid,
			value: valueIMap.setIn(
				['criterionGroup', 'items', 0, 'value'],
				numberVal
			)
		});
	}

	render() {
		const {
			className,
			displayValue,
			property: {entityName},
			touched,
			valid,
			value
		} = this.props;

		const selectedOperatorKey = this.getSelectedOperatorKey();
		const knownType = isOfKnownType(selectedOperatorKey);

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className='display-value' label shrink>
						{displayValue}
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<Picker
							items={NUMBER_OPERATORS.map(({key, label}) => ({
								label,
								value: key
							}))}
							onSelectionChange={this.handleOperatorChange}
							selectedKey={selectedOperatorKey}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>

					{!knownType && (
						<Form.GroupItem
							className={getCN(className, {
								'has-error': !valid && touched
							})}
							shrink
						>
							<Input
								data-testid='number-input'
								onBlur={this.handleBlur}
								onChange={this.handleValueChange}
								type={knownType ? 'text' : 'number'}
								value={getPropertyValue(value, 'value', 0)}
							/>
						</Form.GroupItem>
					)}
				</Form.Group>
			</div>
		);
	}
}
