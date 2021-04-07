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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {Context as ModalContext} from '@clayui/modal';
import {FieldStateless} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useContext, useMemo} from 'react';

import Calculator from './Calculator.es';
import Timeline from './Timeline.es';
import {ACTIONS_TYPES} from './actionsTypes.es';
import {ACTIONS_OPTIONS} from './config.es';

const ActionDefault = ({children}) => children;

const ActionContentCalculate = ({
	expression,
	fields,
	functionsURL,
	onChange,
	target,
}) => {
	const {resource} = useResource({
		fetch,
		link: location.origin + functionsURL,
	});

	const numericFields = useMemo(
		() => fields.filter(({type}) => type === 'numeric'),
		[fields]
	);

	const options = useMemo(
		() => numericFields.filter(({fieldName}) => fieldName !== target),
		[numericFields, target]
	);

	if (!resource) {
		return null;
	}

	return (
		<Calculator
			expression={expression}
			fields={numericFields}
			functions={resource}
			onChange={(newExpression) =>
				onChange({
					payload: {
						value: newExpression,
					},
					type: ACTIONS_TYPES.CHANGE_ACTION_CALCULATE,
				})
			}
			options={options}
		/>
	);
};

const ActionCalculate = ({children}) => (
	<>
		<Timeline.FormGroupItem className="form-group-item-label form-group-item-shrink">
			<h4>{Liferay.Language.get('choose-a-field-to-show-the-result')}</h4>
		</Timeline.FormGroupItem>
		{children}
	</>
);

const AUTOFILL_PARAMETER_TYPE = {
	list: ['checkbox_multiple', 'radio', 'select'],
	number: ['numeric'],
	text: ['checkbox_multiple', 'date', 'numeric', 'radio', 'select', 'text'],
};

const AutoFillParameter = ({fields, name, onChange, type, value}) => {
	const options = useMemo(() => {
		switch (type) {
			case 'list':
			case 'number':
			case 'text':
				return fields.filter((field) =>
					AUTOFILL_PARAMETER_TYPE[type].includes(field.type)
				);
			default:
				return [];
		}
	}, [fields, type]);

	return (
		<ClayLayout.Row className="mb-3">
			<ClayLayout.Col md={3}>
				<span>{name}</span>
			</ClayLayout.Col>
			<ClayLayout.Col md={9}>
				<FieldStateless
					onChange={onChange}
					options={options}
					placeholder={Liferay.Language.get('choose-an-option')}
					showEmptyOption={false}
					type="select"
					value={[value]}
				/>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const AutoFillPanel = ({
	description,
	fields,
	onChange,
	parameters,
	required,
	title,
	type,
	values,
}) => (
	<div className="data-provider-parameter-container">
		{required && (
			<div className="data-provider-label-container">
				<p className="data-provider-parameter-required-field">
					{Liferay.Language.get('required-field')}
				</p>
				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</div>
		)}
		<div className="data-provider-label-container">
			<p className="data-provider-parameter">
				<b>{title}</b>
			</p>
			<p className="data-provider-parameter-description">{description}</p>
		</div>
		{parameters.map(({id, ...otherProps}, index) => (
			<AutoFillParameter
				{...otherProps}
				fields={fields}
				id={id}
				key={index}
				onChange={(event) =>
					onChange({
						payload: {
							id,
							type,
							value: event.value[0],
						},
						type: ACTIONS_TYPES.CHANGE_ACTION_AUTOFILL_PARAMETER,
					})
				}
				value={values[id]}
			/>
		))}
	</div>
);

const ActionContentAutoFill = ({
	dataProviderInstanceParameterSettingsURL,
	fields,
	inputs,
	onChange,
	outputs,
	target,
}) => {
	const {resource} = useResource({
		fetch,
		link: location.origin + dataProviderInstanceParameterSettingsURL,
		variables: {
			ddmDataProviderInstanceId: target,
		},
	});

	const resourceInputs = resource?.inputs;
	const resourceOutputs = resource?.outputs;

	const hasRequiredInputs =
		resourceInputs?.some(({required}) => required) ?? false;

	return (
		<div className="action-rule-data-provider">
			<ClayLayout.Row>
				<ClayLayout.Col className="no-padding" md={12}>
					{resourceOutputs?.length === 0 && (
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{Liferay.Language.get('data-provider-error')}
						</ClayAlert>
					)}
					{resourceInputs?.length > 0 && (
						<AutoFillPanel
							description={Liferay.Language.get(
								'data-provider-parameter-input-description'
							)}
							fields={fields}
							onChange={onChange}
							parameters={resourceInputs}
							required={hasRequiredInputs}
							title={Liferay.Language.get(
								'data-provider-parameter-input'
							)}
							type="inputs"
							values={inputs}
						/>
					)}
					{resourceOutputs?.length > 0 && (
						<AutoFillPanel
							description={Liferay.Language.get(
								'data-provider-parameter-output-description'
							)}
							fields={fields}
							onChange={onChange}
							parameters={resourceOutputs}
							title={Liferay.Language.get(
								'data-provider-parameter-output'
							)}
							type="outputs"
							values={outputs}
						/>
					)}
				</ClayLayout.Col>
			</ClayLayout.Row>
		</div>
	);
};

const ActionAutoFill = ({children}) => (
	<>
		<Timeline.FormGroupItem className="form-group-item-label form-group-item-shrink">
			<h4>{Liferay.Language.get('from-data-provider')}</h4>
		</Timeline.FormGroupItem>
		{children}
	</>
);

const ACTIONS_TARGET_TYPE = {
	'auto-fill': ActionAutoFill,
	calculate: ActionCalculate,
	enable: ActionDefault,
	'jump-to-page': ActionDefault,
	require: ActionDefault,
	show: ActionDefault,
};

const ACTIONS_CONTENT_TYPE = {
	'auto-fill': ActionContentAutoFill,
	calculate: ActionContentCalculate,
};

function Target({
	action,
	conditions,
	dataProvider,
	fieldSourcePage,
	fields,
	onChange,
	pages,
	source,
	target,
	...otherProps
}) {
	const options = useMemo(() => {
		switch (action) {
			case 'auto-fill':
				return dataProvider;
			case 'calculate':
				return fields.filter(({type}) => type === 'numeric');
			case 'show':
			case 'require':
			case 'enable':
				return fields;
			case 'jump-to-page': {
				const startIndex = conditions.reduce(
					(prev, {operands: [left]}) => {
						return Math.max(prev, left.field?.pageIndex ?? 0);
					},
					0
				);

				return pages.filter((page, index) => index > startIndex);
			}
			default:
				return [];
		}
	}, [action, fields, dataProvider, pages, conditions]);

	const TargetComponent = ACTIONS_TARGET_TYPE[action];

	return (
		<TargetComponent>
			<Timeline.FormGroupItem>
				<FieldStateless
					{...otherProps}
					onChange={(event) => {
						const value = event.value[0];

						switch (action) {
							case 'auto-fill':
								onChange({
									ddmDataProviderInstanceUUID: dataProvider.find(
										({id}) => id === value
									).uuid,
									value,
								});
								break;
							case 'jump-to-page':
								onChange({
									source: source ?? fieldSourcePage,
									value,
								});
								break;
							default:
								onChange({value});
								break;
						}
					}}
					options={options}
					placeholder={Liferay.Language.get('choose-an-option')}
					showEmptyOption={false}
					type="select"
					value={[target]}
				/>
			</Timeline.FormGroupItem>
		</TargetComponent>
	);
}

const ActionBottomContent = ({action, target, ...otherProps}) => {
	const Component = ACTIONS_CONTENT_TYPE[action];

	return Component && target ? (
		<Component target={target} {...otherProps} />
	) : null;
};

export function Actions({
	actions,
	allowActions,
	dataProvider,
	dataProviderInstanceParameterSettingsURL,
	dispatch,
	expression,
	fields,
	functionsURL,
	name,
	pages,
	state: {fieldSourcePage, ifStatement},
}) {
	const [modal, openModal] = useContext(ModalContext);

	const options = useMemo(
		() =>
			ACTIONS_OPTIONS.filter(({value}) => {
				if (value === 'jump-to-page') {
					return pages.length > 1;
				}

				return allowActions.includes(value);
			}),
		[allowActions, pages]
	);

	return (
		<Timeline.List>
			<Timeline.Header title={name} />
			{actions.map(
				({action, source, target, ...otherActionsProps}, index) => (
					<Timeline.Item key={index}>
						<Timeline.Panel
							bottomContent={
								<ActionBottomContent
									action={action}
									dataProviderInstanceParameterSettingsURL={
										dataProviderInstanceParameterSettingsURL
									}
									fields={fields}
									functionsURL={functionsURL}
									onChange={({payload, type}) =>
										dispatch({
											payload: {
												...payload,
												loc: index,
											},
											type,
										})
									}
									target={target}
									{...otherActionsProps}
								/>
							}
							expression={expression}
						>
							<Timeline.FormGroupItem>
								<FieldStateless
									onChange={(event) =>
										dispatch({
											payload: {
												loc: index,
												value: event.value[0],
											},
											type: ACTIONS_TYPES.CHANGE_ACTION,
										})
									}
									options={options}
									placeholder={Liferay.Language.get(
										'choose-an-option'
									)}
									showEmptyOption={false}
									type="select"
									value={[action]}
								/>
							</Timeline.FormGroupItem>
							{action && (
								<Target
									action={action}
									conditions={ifStatement.conditions}
									dataProvider={dataProvider}
									fieldSourcePage={fieldSourcePage}
									fields={fields}
									onChange={(event) =>
										dispatch({
											payload: {
												...event,
												loc: index,
											},
											type:
												ACTIONS_TYPES.CHANGE_ACTION_TARGET,
										})
									}
									pages={pages}
									source={source}
									target={target}
								/>
							)}
						</Timeline.Panel>
						{actions.length > 1 && (
							<Timeline.ActionTrash
								onClick={() => {
									openModal({
										payload: {
											body: (
												<h4>
													{Liferay.Language.get(
														'are-you-sure-you-want-to-delete-this-action'
													)}
												</h4>
											),
											footer: [
												null,
												null,
												<ClayButton.Group
													key={3}
													spaced
												>
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
																	ACTIONS_TYPES.DELETE_ACTION,
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
												'delete-action'
											),
											size: 'sm',
										},
										type: 1,
									});
								}}
							/>
						)}
					</Timeline.Item>
				)
			)}
			<Timeline.ItemAction>
				<Timeline.IncrementButton
					onClick={() => dispatch({type: ACTIONS_TYPES.ADD_ACTION})}
				/>
			</Timeline.ItemAction>
		</Timeline.List>
	);
}
