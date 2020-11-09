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
import ClayButton from '@clayui/button';
import React, {useReducer, useState} from 'react';

import Timeline from './Timeline.es';

const OPERATION_TYPE = {
	ACTIONS: 'actions',
	CONDITIONS: 'conditions',
};

const OPERATION_DATA = {
	[OPERATION_TYPE.ACTIONS]: {
		component: Action,
		expression: Liferay.Language.get('if'),
		name: Liferay.Language.get('actions'),
	},
	[OPERATION_TYPE.CONDITIONS]: {
		component: Condition,
		expression: Liferay.Language.get('do'),
		name: Liferay.Language.get('condition'),
	},
};

const getOperators = (onClick) => {
	return [
		{label: 'OR', onClick},
		{label: 'AND', onClick}
	]
};

function FieldOperator({operator, readOnly, onChange}) {
	return (
		<FieldStateless
			type="select"
			placeholder={Liferay.Language.get('choose-an-option')}
			showEmptyOption={false}
			readOnly={readOnly}
			onChange={(event) => onChange(event.value)}
			value={[operator]}
		/>
	);
}

function Condition({conditions, name, expression, state, dispatch}) {
	return (
		<Timeline.List>
			<Timeline.Header
				title={name}
				operator={state.logicalOperator}
				items={getOperators(() => {})}
				disabled={conditions.length === 1}
			/>
			{conditions.map(({operator, [left, right]}, index) => (
				<Timeline.Item key={index}>
					<Timeline.Condition expression={expression}>
						<FieldStateless
							type="select"
							placeholder={Liferay.Language.get('choose-an-option')}
							fixedOptions={[{
								dataType: 'user',
								label: Liferay.Language.get('user'),
								name: 'user',
								value: 'user',
							}]}
							showEmptyOption={false}
							onChange={(event) => setValue(event.value)}
							value={[left.value]}
						/>
						<FieldOperator
							readOnly={!!left.value}
							operator={operator}
							onChange={() => {}}
						/>
					</Timeline.Condition>
					{conditions.length > 1 && (
						<>
							<Timeline.Operator operator={state.logicalOperator} />
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
};

function Action({name, expression, state, dispatch}) {
	return (
		<Timeline.List>
			<Timeline.Header
				title={name}
			/>
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
	)
};

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
		operations: Object.keys(operations).map(key => ({key, [key]: operations[key]})),
	}
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

export default function RuleEditor({onCancel, onSubmit}) {
	return (
		<Timeline>
			<div className="liferay-ddm-form-rule-builder-header">
				<h2 className="form-builder-section-title text-default">
					{Liferay.Language.get('rule')}
				</h2>

				<h4 className="text-default">
					{Liferay.Language.get(
						'define-condition-and-action-to-change-fields-and-elements-on-the-form'
					)}
				</h4>
			</div>
			<Editor />
			<div className="liferay-ddm-form-rule-builder-footer">
				<ClayButton.Group spaced>
					<ClayButton displayType="primary" onClick={onSubmit}>
						{Liferay.Language.get('save')}
					</ClayButton>
					<ClayButton displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</Timeline>
	);
}
