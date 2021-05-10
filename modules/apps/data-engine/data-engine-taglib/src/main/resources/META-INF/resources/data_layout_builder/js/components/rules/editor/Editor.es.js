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

import './Editor.scss';

import {ClayModalProvider} from '@clayui/modal';
import {
	PageProvider as FieldProvider,
	useFieldTypesResource,
} from 'data-engine-js-components-web';
import RulesSupport from 'dynamic-data-mapping-form-builder/js/components/RuleBuilder/RulesSupport.es';
import React, {useEffect, useReducer} from 'react';

import {Actions} from './Actions.es';
import {Conditions} from './Conditions.es';
import {ACTIONS_TYPES} from './actionsTypes.es';
import {ACTION_TARGET_SHAPE, DEFAULT_RULE, RIGHT_TYPES} from './config.es';

const CONFIG_DATA = {
	actions: {
		component: Actions,
		expression: Liferay.Language.get('do'),
		name: Liferay.Language.get('actions'),
	},
	conditions: {
		component: Conditions,
		expression: Liferay.Language.get('if'),
		name: Liferay.Language.get('condition'),
	},
};

const normalizeValue = (value, right) => {
	switch (right.type) {
		case 'json':
			return JSON.stringify(value);
		case 'field':
		case 'list':
		case 'option':
			return value[0];
		default:
			return value;
	}
};

const reducer = (state, action) => {
	switch (action.type) {
		case ACTIONS_TYPES.ADD_ACTION: {
			const {actions, conditions} = state.ifStatement;

			actions.push({
				action: '',
				target: '',
			});

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.ADD_CONDITION: {
			const {actions, conditions} = state.ifStatement;

			conditions.push({
				operands: [{type: '', value: ''}],
				operator: '',
			});

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_ACTION_AUTOFILL_PARAMETER: {
			const {actions, conditions} = state.ifStatement;
			const {id, loc, type, value} = action.payload;

			const parameters = actions[loc][type];

			actions[loc] = {
				...actions[loc],
				[type]: parameters ? {...parameters} : {},
			};

			actions[loc][type][id] = value;

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_ACTION_CALCULATE: {
			const {actions, conditions} = state.ifStatement;
			const {loc, value} = action.payload;

			actions[loc] = {
				...actions[loc],
				expression: value,
			};

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_ACTION_TARGET: {
			const {actions, conditions} = state.ifStatement;
			const {loc, value, ...otherPayloads} = action.payload;

			actions[loc] = {
				...actions[loc],
				...otherPayloads,
				label: value,
				target: value,
			};

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_ACTION: {
			const {actions, conditions} = state.ifStatement;
			const {loc, value} = action.payload;

			const shape = ACTION_TARGET_SHAPE[value] ?? {};

			actions[loc] = {
				...shape,
				action: value,
				target: '',
			};

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_BINARY_OPERATOR: {
			const {actions, conditions} = state.ifStatement;
			const {loc, value} = action.payload;

			const {
				operands: [left],
			} = conditions[loc];

			conditions[loc] = {
				...conditions[loc],
				operands: [
					left,
					{
						type:
							value === 'value'
								? RIGHT_TYPES[left.field.type] ?? 'string'
								: 'field',
						value: '',
					},
				],
			};

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_IDENTIFIER_LEFT: {
			const {actions, conditions} = state.ifStatement;
			const {fields, loc, value} = action.payload;

			let left = {
				label: 'user',
				repeatable: false,
				type: 'user',
				value: 'user',
			};

			if (value !== 'user') {
				const field = fields.find(({fieldName}) => fieldName === value);

				left = {
					field,
					label: field.label,
					repeatable: field.repeatable,
					source: field.pageIndex,
					type: 'field',
					value,
				};
			}

			conditions[loc] = {
				operands: [left],
				operator: '',
			};

			return {
				...state,
				fieldSourcePage:
					typeof left.source === 'number' ? left.source : null,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_IDENTIFIER_RIGHT: {
			const {actions, conditions} = state.ifStatement;
			const {loc, value} = action.payload;

			const {
				operands: [left, right],
			} = conditions[loc];

			const newValue = normalizeValue(value, right);

			const otherProps = left.type === 'user' ? {label: newValue} : {};

			conditions[loc] = {
				...conditions[loc],
				operands: [
					left,
					{
						...right,
						...otherProps,
						value: newValue,
					},
				],
			};

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.CHANGE_LOGICAL_OPERATOR: {
			const {value} = action.payload;

			return {
				...state,
				logicalOperator: value,
			};
		}
		case ACTIONS_TYPES.CHANGE_OPERATOR: {
			const {actions, conditions} = state.ifStatement;
			const {loc, value} = action.payload;

			const {
				operands: [left, right],
			} = conditions[loc];

			const newRight =
				left.type === 'user'
					? {
							type: 'list',
							value: '',
					  }
					: right;

			const operands = [left];

			const {isBinaryOperator, operator} = value;

			if (isBinaryOperator && newRight) {
				operands.push(newRight);
			}

			conditions[loc] = {
				operands,
				operator,
			};

			return {
				...state,
				ifStatement: {
					actions,
					conditions,
				},
			};
		}
		case ACTIONS_TYPES.DELETE_CONDITION: {
			const {actions, conditions} = state.ifStatement;
			const {loc} = action.payload;

			return {
				...state,
				ifStatement: {
					actions,
					conditions: conditions.filter(
						(condition, index) => index !== loc
					),
				},
			};
		}
		case ACTIONS_TYPES.DELETE_ACTION: {
			const {actions, conditions} = state.ifStatement;
			const {loc} = action.payload;

			return {
				...state,
				ifStatement: {
					actions: actions.filter((action, index) => index !== loc),
					conditions,
				},
			};
		}
		default:
			return state;
	}
};

const transformConditions = ({operator, operands: [left, right]}, fields) => {

	// When the left operator is a type `user` the backend does not return
	// the two operators, only the right operator.

	if (left?.type === 'list') {
		right = left;
		left = {
			label: 'user',
			repeatable: false,
			type: 'user',
			value: 'user',
		};
	}

	const operands = [
		{
			...left,
			field: fields.find(({fieldName}) => fieldName === left.value),
		},
	];

	if (right) {
		operands.push(right);
	}

	return {
		operands,
		operator,
	};
};

const transformActions = ({target, ...otherProps}, dataProvider) => {
	if (otherProps.ddmDataProviderInstanceUUID) {
		target = dataProvider.find(
			({uuid}) => uuid === otherProps.ddmDataProviderInstanceUUID
		)?.id;
	}

	return {
		target,
		...otherProps,
	};
};

const init = ({
	dataProvider,
	fields,
	rule: {actions, conditions, logicalOperator, name, ...otherRule},
}) => {

	// Maintains the naming compatibility of Forms but uses the new
	// Data Engine nomenclature.

	if (otherRule['logical-operator']) {
		logicalOperator = otherRule['logical-operator'];
	}

	return {
		fieldSourcePage: null,
		ifStatement: {
			actions: actions.map((action) =>
				transformActions(action, dataProvider)
			),
			conditions: conditions.map((condition) =>
				transformConditions(condition, fields)
			),
		},
		logicalOperator,
		name,
		panels: ['conditions', 'actions'],
	};
};

export function Editor({
	allowActions = [
		'show',
		'enable',
		'require',
		'auto-fill',
		'calculate',
		'jump-to-page',
	],
	dataProvider,
	fields,
	onChange,
	onValidator,
	rule = DEFAULT_RULE,
	...otherProps
}) {
	const [state, dispatch] = useReducer(
		reducer,
		{dataProvider, fields, rule},
		init
	);

	const {resource: fieldTypes} = useFieldTypesResource();

	useEffect(() => {
		const {ifStatement, logicalOperator, name} = state;
		const {actions, conditions} = ifStatement;

		const newRule = {
			actions,
			conditions: conditions.map(
				({operands: [left, ...otherOperands], ...otherProps}) => {
					const newLeft = {...left};

					delete newLeft.field;

					return {
						...otherProps,
						operands: [newLeft, ...otherOperands],
					};
				}
			),
			logicalOperator,
			name,
		};

		onValidator(
			RulesSupport.isActionsValid(actions) &&
				RulesSupport.isConditionsValid(newRule.conditions)
		);
		onChange(newRule);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [state]);

	return (
		<ClayModalProvider>
			<FieldProvider value={{fieldTypes}}>
				{state.panels.map((key) => {
					const {component: Component, ...otherData} = CONFIG_DATA[
						key
					];

					return (
						<Component
							{...otherProps}
							{...otherData}
							{...{[key]: state.ifStatement[key]}}
							allowActions={allowActions}
							dataProvider={dataProvider}
							dispatch={dispatch}
							fields={fields}
							key={key}
							state={state}
						/>
					);
				})}
			</FieldProvider>
		</ClayModalProvider>
	);
}
