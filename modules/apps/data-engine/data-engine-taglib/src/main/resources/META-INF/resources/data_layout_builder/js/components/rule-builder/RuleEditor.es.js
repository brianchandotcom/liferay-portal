/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {FieldStateless} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo, useReducer} from 'react';

import Timeline from './Timeline.es';

const OPERATION_TYPE = {
	ACTIONS: 'actions',
	CONDITIONS: 'conditions',
};

const OPERATION_DATA = {
	[OPERATION_TYPE.ACTIONS]: {
		component: Actions,
		expression: Liferay.Language.get('if'),
		name: Liferay.Language.get('actions'),
	},
	[OPERATION_TYPE.CONDITIONS]: {
		component: Conditions,
		expression: Liferay.Language.get('do'),
		name: Liferay.Language.get('condition'),
	},
};

const BINARY_OPERATOR = [
	'belongs-to',
	'contains',
	'equals-to',
	'greater-than-equals',
	'greater-than',
	'less-than-equals',
	'less-than',
	'not-contains',
	'not-equals-to',
];

const OPERATOR_TYPES = {
	double: 'number',
	integer: 'number',
	text: 'text',
	user: 'user',
};

const ACTIONS_TYPES = {
	CHANGE_OPERATOR: 'CHANGE_OPERATOR',
	ADD_IDENTIFIER_RIGHT: 'ADD_IDENTIFIER_RIGHT',
};

function FieldOperator({
	fields,
	left,
	onChange,
	operator,
	operatorsByType,
	readOnly,
	right,
}) {
	const options = useMemo(() => {
		const fieldId = left.value;

		if (!fieldId) {
			return [];
		}

		const field = fields.find(({value}) => value === fieldId);
		const dataType = field?.dataType ?? fieldId;
		const fieldType = OPERATOR_TYPES[dataType] ?? OPERATOR_TYPES.text;

		return operatorsByType[fieldType].map((operator) => ({
			...operator,
			value: operator.name,
		}));
	}, [left, fields, operatorsByType]);

	return (
		<>
			<FieldStateless
				onChange={(event) => onChange(event.value, 'operator')}
				options={options}
				placeholder={Liferay.Language.get('choose-an-option')}
				readOnly={readOnly}
				showEmptyOption={false}
				type="select"
				value={[operator]}
			/>
			{BINARY_OPERATOR[operator] && (
				<FieldStateless
					onChange={(event) => onChange(event.value, 'type')}
					options={[
						{
							label: Liferay.Language.get('value'),
							value: 'value',
						},
						{
							label: Liferay.Language.get('other-field'),
							value: 'field',
						},
					]}
					placeholder={Liferay.Language.get('choose-an-option')}
					showEmptyOption={false}
					type="select"
					value={[
						left.type === 'field'
							? 'field'
							: left.type
							? 'value'
							: '',
					]}
				/>
			)}
		</>
	);
}

function Conditions({conditions, dispatch, expression, name, state}) {
	const onOperatorChange = (value, type) => {
		switch (type) {
			case 'operator':
				dispatch({payload: value, type: ACTIONS_TYPES.CHANGE_OPERATOR});
				break;
			case 'type':
				dispatch({
					payload: {},
					type: ACTIONS_TYPES.ADD_IDENTIFIER_RIGHT,
				});
				break;
			default:
				break;
		}
	};

	return (
		<Timeline.List>
			<Timeline.Header
				disabled={conditions.length === 1}
				items={[
					{label: 'OR', onClick: () => {}},
					{label: 'AND', onClick: () => {}},
				]}
				operator={state.logicalOperator}
				title={name}
			/>
			{conditions.map(({operator, operands: [left, right]}, index) => (
				<Timeline.Item key={index}>
					<Timeline.Condition expression={expression}>
						<FieldStateless
							fixedOptions={[
								{
									dataType: 'user',
									label: Liferay.Language.get('user'),
									name: 'user',
									value: 'user',
								},
							]}
							onChange={(event) => setValue(event.value)}
							placeholder={Liferay.Language.get(
								'choose-an-option'
							)}
							showEmptyOption={false}
							type="select"
							value={[left.value]}
						/>
						<FieldOperator
							fields={state.fields}
							left={left}
							onChange={onOperatorChange}
							operator={operator}
							operatorsByType={state.operatorsByType}
							readOnly={!!left.value}
							right={right}
						/>
					</Timeline.Condition>
					{conditions.length > 1 && (
						<>
							<Timeline.Operator
								operator={state.logicalOperator}
							/>
							<Timeline.ActionTrash />
						</>
					)}
				</Timeline.Item>
			))}
			<Timeline.Item>
				<Timeline.IncrementButton />
			</Timeline.Item>
		</Timeline.List>
	);
}

function Actions({dispatch, expression, name, state}) {
	return (
		<Timeline.List>
			<Timeline.Header title={name} />
			<Timeline.Item>
				<Timeline.Condition expression={expression}>
					<input />
				</Timeline.Condition>
				<Timeline.ActionTrash />
			</Timeline.Item>
			<Timeline.Item>
				<Timeline.IncrementButton />
			</Timeline.Item>
		</Timeline.List>
	);
}

const reducer = (state, action) => {
	switch (action.type) {
		case value:
			break;
		default:
			break;
	}
};

const init = ({rule: {logicalOperator, ...operations}, ...otherProps}) => {
	return {
		...otherProps,
		logicalOperator,
		operations: Object.keys(operations).map((key) => ({
			key,
			[key]: operations[key],
		})),
	};
};

export function Editor(props) {
	const [state, dispatch] = useReducer(reducer, props, init);

	return state.operations.map(({key, ...otherProps}) => {
		const {component: Component, ...otherData} = OPERATION_DATA[key];

		return (
			<Component
				{...otherData}
				{...otherProps}
				dispatch={dispatch}
				key={key}
				state={state}
			/>
		);
	});
}
