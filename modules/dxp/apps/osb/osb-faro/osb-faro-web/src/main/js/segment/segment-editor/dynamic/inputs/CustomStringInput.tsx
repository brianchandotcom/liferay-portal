import autobind from 'autobind-decorator';
import AutocompleteInput from 'shared/components/AutocompleteInput';
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

const TEXT_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.Text];

export interface ICustomStringInputProps extends ISegmentEditorCustomInputBase {
	autocomplete?: boolean;
	fieldValuesDataSourceFn?: () => Promise<string[]>;
	touched: boolean;
	valid: boolean;
}

export default class CustomStringInput extends React.Component<ICustomStringInputProps> {
	static defaultProps = {
		autocomplete: true
	};

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

		return TEXT_OPERATORS.find(({key}) => key === operatorKey).key;
	}

	@autobind
	handleBlur() {
		const {onChange, value: valueIMap} = this.props;

		onChange({
			touched: true,
			valid: isValid(getPropertyValue(valueIMap, 'value', 0)),
			value: valueIMap
		});
	}

	@autobind
	handleOperatorChange(operator) {
		const {onChange, value: valueIMap} = this.props;

		let newVal = null;

		newVal = valueIMap.setIn(
			['criterionGroup', 'items', 0, 'operatorName'],
			TEXT_OPERATORS.find(({key}) => key === operator).name
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
	handleValueChange(value) {
		const {onChange, value: valueIMap} = this.props;

		onChange({
			valid: isValid(value),
			value: valueIMap.setIn(
				['criterionGroup', 'items', 0, 'value'],
				value
			)
		});
	}

	render() {
		const {
			autocomplete,
			className,
			displayValue,
			fieldValuesDataSourceFn,
			property: {entityName, options = []},
			touched,
			valid,
			value: valueIMap
		} = this.props;

		const value = getPropertyValue(valueIMap, 'value', 0);

		const selectedOperatorKey = this.getSelectedOperatorKey();
		const knownType = isOfKnownType(selectedOperatorKey);

		const showError = !valid && touched;

		const sharedInputProps = {
			className: getCN(className, {
				'has-error': showError
			}),
			'data-testid': 'string-input',
			onBlur: this.handleBlur,
			value
		};

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
							items={TEXT_OPERATORS.map(({key, label}) => ({
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
						<Form.GroupItem>
							{options.length === 0 ? (
								autocomplete ? (
									<AutocompleteInput
										{...sharedInputProps}
										dataSourceFn={fieldValuesDataSourceFn}
										onChange={this.handleValueChange}
									/>
								) : (
									<Input
										{...sharedInputProps}
										autoComplete='nope'
										onChange={event => {
											this.handleValueChange(
												event.target.value
											);
										}}
									/>
								)
							) : (
								<Picker
									items={options.map(option => ({
										label: option.label,
										value: option.value
									}))}
									onBlur={this.handleBlur}
									onSelectionChange={this.handleValueChange}
									selectedKey={value}
								>
									{({label, value}) => (
										<Option key={value}>{label}</Option>
									)}
								</Picker>
							)}
						</Form.GroupItem>
					)}
				</Form.Group>
			</div>
		);
	}
}
