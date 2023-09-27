/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Context as ModalContext} from '@clayui/modal';
import {
	FieldStateless,
	FieldSupport,
	generateName,
} from 'data-engine-js-components-web';
import React, {useContext, useMemo} from 'react';

import Timeline from './Timeline.es';
import {ACTIONS_TYPES} from './actionsTypes.es';
import {OPERATOR_OPTIONS_TYPES, RIGHT_OPERAND_TYPES} from './config.es';

function getCheckboxOptions() {
	return [
		{
			label: Liferay.Language.get('true'),
			value: 'true',
		},
		{
			label: Liferay.Language.get('false'),
			value: 'false',
		},
	];
}

function FieldOperator({
	left,
	onChange,
	operator,
	operatorsByType,
	readOnly,
	right,
}) {
	const options = useMemo(() => {
		if (!left.value) {
			return [];
		}

		const dataType = left.field?.dataType ?? left.value;
		const fieldType =
			OPERATOR_OPTIONS_TYPES[dataType] ?? OPERATOR_OPTIONS_TYPES.text;

		return operatorsByType[fieldType].map((operator) => ({
			...operator,
			value: operator.name,
		}));
	}, [left, operatorsByType]);

	const isBinaryOperator = (operator) => {
		const option = options?.find(({value}) => value === operator);

		return option?.parameterClassNames?.length === 2;
	};

	return (
		<>
			<Timeline.FormGroupItem>
				<FieldStateless
					onChange={(event) => {
						const operator = event.value[0];

						onChange({
							payload: {
								isBinaryOperator: isBinaryOperator(operator),
								operator,
							},
							type: ACTIONS_TYPES.CHANGE_OPERATOR,
						});
					}}
					options={options}
					placeholder={Liferay.Language.get('choose-an-option')}
					readOnly={readOnly}
					showEmptyOption={false}
					type="select"
					value={[operator]}
				/>
			</Timeline.FormGroupItem>
			{isBinaryOperator(operator) && left.type !== 'user' && (
				<Timeline.FormGroupItem>
					<FieldStateless
						onChange={(event) =>
							onChange({
								payload: event.value[0],
								type: ACTIONS_TYPES.CHANGE_BINARY_OPERATOR,
							})
						}
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
							right?.type === 'field'
								? 'field'
								: right?.type
								? 'value'
								: '',
						]}
					/>
				</Timeline.FormGroupItem>
			)}
		</>
	);
}

function FieldLeft({fields, left, onChange}) {
	return (
		<Timeline.FormGroupItem>
			<FieldStateless
				fixedOptions={[
					{
						dataType: 'user',
						label: Liferay.Language.get('user'),
						name: 'user',
						value: 'user',
					},
				]}
				onChange={onChange}
				options={fields}
				placeholder={Liferay.Language.get('choose-an-option')}
				showEmptyOption={false}
				type="select"
				value={[left.value]}
			/>
		</Timeline.FormGroupItem>
	);
}

function evaluateFieldLeft(fieldLeft, value) {
	const props = {value};

	if (!fieldLeft) {
		return props;
	}

	const {editorConfig, name, options = [], type} = fieldLeft;

	switch (type) {
		case 'checkbox': {
			props.options = getCheckboxOptions();
			props.placeholder = Liferay.Language.get('choose-an-option');
			props.value = [value];
			break;
		}
		case 'checkbox_multiple':
		case 'radio':
		case 'select': {
			props.options = options;
			props.placeholder = Liferay.Language.get('choose-an-option');
			break;
		}
		case 'rich_text': {
			if (editorConfig) {
				const instanceId = FieldSupport.generateInstanceId();

				props.editorConfig = FieldSupport.updateEditorConfigInstanceId(
					editorConfig,
					instanceId
				);
				props.name = generateName(name, {instanceId});
			}
			break;
		}
		default:
			return props;
	}

	return props;
}

function FieldRight({fields, left, right, roles, ...otherProps}) {
	const props = useMemo(() => {
		switch (right.type) {
			case 'option':
				return {
					options: left.field?.options ?? [],
					placeholder: Liferay.Language.get('choose-an-option'),
					value: [right.value],
				};
			case 'json':
				return {
					columns: left.field?.columns ?? [],
					rows: left.field?.rows ?? [],
					value: right.value ? JSON.parse(right.value) : {},
				};
			case 'list':
				return {
					options: roles,
					value: [right.value],
				};
			case 'field':
				return {
					options: fields,
					value: [right.value],
				};
			default:
				return evaluateFieldLeft(left.field, right.value);
		}
	}, [left, right, roles, fields]);

	return (
		<Timeline.FormGroupItem>
			<FieldStateless
				{...otherProps}
				{...props}
				dataType={left.field?.dataType}
				showEmptyOption={false}
				type={
					left.type === 'user'
						? 'select'
						: RIGHT_OPERAND_TYPES[left.field.type] ??
						  RIGHT_OPERAND_TYPES[right.type] ??
						  left.field.type
				}
			/>
		</Timeline.FormGroupItem>
	);
}

export function Conditions({
	conditions,
	dispatch,
	expression,
	fields,
	name,
	operatorsByType,
	roles,
	state: {logicalOperator},
}) {
	const [modal, openModal] = useContext(ModalContext);

	const onChangeLogicalOperator = (value) =>
		dispatch({
			payload: {value},
			type: ACTIONS_TYPES.CHANGE_LOGICAL_OPERATOR,
		});

	return (
		<Timeline.List className="timeline-first">
			<Timeline.Header
				disabled={conditions.length === 1}
				items={[
					{label: 'OR', onClick: () => onChangeLogicalOperator('OR')},
					{
						label: 'AND',
						onClick: () => onChangeLogicalOperator('AND'),
					},
				]}
				operator={logicalOperator}
				title={name}
			/>

			{conditions.map(({operator, operands: [left, right]}, index) => (
				<Timeline.Item key={index}>
					<Timeline.Panel expression={expression}>
						<FieldLeft
							fields={fields}
							left={left}
							onChange={(event) =>
								dispatch({
									payload: {
										fields,
										loc: index,
										value: event.value[0],
									},
									type: ACTIONS_TYPES.CHANGE_IDENTIFIER_LEFT,
								})
							}
						/>

						<FieldOperator
							fields={fields}
							left={left}
							onChange={({payload, type}) =>
								dispatch({
									payload: {loc: index, value: payload},
									type,
								})
							}
							operator={operator}
							operatorsByType={operatorsByType}
							readOnly={!left.value}
							right={right}
						/>

						{right && right.type && (
							<FieldRight
								fields={fields}
								left={left}
								onChange={(event) =>
									dispatch({
										payload: {
											loc: index,
											value: event.value,
										},
										type:
											ACTIONS_TYPES.CHANGE_IDENTIFIER_RIGHT,
									})
								}
								right={right}
								roles={roles}
							/>
						)}
					</Timeline.Panel>

					{conditions.length > 1 && conditions.length - 1 > index && (
						<Timeline.Operator operator={logicalOperator} />
					)}

					{conditions.length > 1 && (
						<Timeline.ActionTrash
							onClick={() => {
								openModal({
									payload: {
										body: (
											<h4>
												{Liferay.Language.get(
													'are-you-sure-you-want-to-delete-this-condition'
												)}
											</h4>
										),
										footer: [
											null,
											null,
											<ClayButton.Group key={3} spaced>
												<ClayButton
													displayType="secondary"
													onClick={modal.onClose}
												>
													{Liferay.Language.get(
														'dismiss'
													)}
												</ClayButton>

												<ClayButton
													onClick={() => {
														dispatch({
															payload: {
																loc: index,
															},
															type:
																ACTIONS_TYPES.DELETE_CONDITION,
														});
														modal.onClose();
													}}
												>
													{Liferay.Language.get(
														'delete'
													)}
												</ClayButton>
											</ClayButton.Group>,
										],
										header: Liferay.Language.get(
											'delete-condition'
										),
										size: 'sm',
									},
									type: 1,
								});
							}}
						/>
					)}
				</Timeline.Item>
			))}

			<Timeline.ItemAction>
				<Timeline.IncrementButton
					onClick={() =>
						dispatch({type: ACTIONS_TYPES.ADD_CONDITION})
					}
				/>
			</Timeline.ItemAction>
		</Timeline.List>
	);
}
