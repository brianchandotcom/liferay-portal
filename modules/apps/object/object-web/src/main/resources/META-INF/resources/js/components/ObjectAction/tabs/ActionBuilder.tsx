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
import ClayForm, {ClayCheckbox, ClaySelect, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	Card,
	CodeEditor,
	CustomItem,
	ExpressionBuilder,
	FormCustomSelect,
	Input,
	SelectWithOption,
	invalidateRequired,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import PredefinedValuesTable from '../PredefinedValuesTable';

import './ActionBuilder.scss';
import {fetchJSON, getObjectFields} from '../../../utils/api';
import {ActionError} from '../index';

type ObjectsOptionsList = Array<
	(
		| React.ComponentProps<typeof ClaySelect.Option>
		| React.ComponentProps<typeof ClaySelect.OptGroup>
	) & {
		options?: Array<React.ComponentProps<typeof ClaySelect.Option>>;
		type?: 'group';
	}
>;

export default function ActionBuilder({
	errors,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionsRelationshipsURL,
	setValues,
	setWarningAlert,
	validateExpressionURL,
	values,
}: IProps) {
	const [notificationTemplates, setNotificationTemplates] = useState<any[]>(
		[]
	);

	const [objectsOptions, setObjectOptions] = useState<ObjectsOptionsList>([]);

	const notificationTemplateId = useMemo(() => {
		return notificationTemplates.find(
			(notificationTemplate) =>
				notificationTemplate.value ===
				values.parameters?.notificationTemplateId
		)?.label;
	}, [notificationTemplates, values.parameters]);

	const [relationships, setRelationships] = useState<
		ObjectDefinitionsRelationship[]
	>([]);

	const [
		currentObjectDefinitionFields,
		setCurrentObjectDefinitionFields,
	] = useState<ObjectField[]>([]);

	const [infoAlert, setInfoAlert] = useState(true);

	const fetchObjectDefinitions = async () => {
		const relationships = await fetchJSON<ObjectDefinitionsRelationship[]>(
			objectDefinitionsRelationshipsURL
		);

		const relatedObjects: SelectItem[] = [];
		const nonRelatedObjects: SelectItem[] = [];

		relationships?.forEach((object) => {
			const {id, label} = object;

			const target = object.related ? relatedObjects : nonRelatedObjects;

			target.push({label, value: id});
		});

		const objectsOptionsList = [];

		if (!values.parameters?.objectDefinitionId) {
			objectsOptionsList.push({
				disabled: true,
				label: Liferay.Language.get('choose-an-object'),
				selected: true,
				value: '',
			});
		}
		const fillSelect = (label: string, options: SelectItem[]) => {
			if (options.length) {
				objectsOptionsList.push({label, options, type: 'group'});
			}
		};

		fillSelect(Liferay.Language.get('related-objects'), relatedObjects);

		fillSelect(
			Liferay.Language.get('non-related-objects'),
			nonRelatedObjects
		);

		setObjectOptions(objectsOptionsList);
		setRelationships(relationships);
	};

	const actionExecutors = useMemo(() => {
		const executors = new Map<string, string>();

		objectActionExecutors.forEach(({label, value}) => {
			value && executors.set(value, label);
		});

		return executors;
	}, [objectActionExecutors]);

	const actionTriggers = useMemo(() => {
		const triggers = new Map<string, string>();

		objectActionTriggers.forEach(({label, value}) => {
			value && triggers.set(value, label);
		});

		return triggers;
	}, [objectActionTriggers]);

	const objectFieldsMap = useMemo(() => {
		const fields = new Map<string, ObjectField>();

		currentObjectDefinitionFields.forEach((field) => {
			fields.set(field.name, field);
		});

		return fields;
	}, [currentObjectDefinitionFields]);

	useEffect(() => {
		if (values.objectActionExecutorKey === 'notification') {
			const makeFetch = async () => {
				const response = await fetch(
					'/o/notification/v1.0/notification-templates',
					{
						method: 'GET',
					}
				);

				const {items} = (await response.json()) as any;

				const notificationsArray = items.map(
					(item: TNotificationTemplate) => {
						return {
							label: item.name,
							value: item.id,
						};
					}
				);

				setNotificationTemplates(notificationsArray);
			};

			makeFetch();
		}
	}, [values]);

	const handleSave = (conditionExpression?: string) => {
		setValues({conditionExpression});
	};

	const isValidField = ({businessType, system}: ObjectField) =>
		businessType !== 'Aggregation' &&
		businessType !== 'Relationship' &&
		!system;

	const fetchObjectDefinitionFields = async () => {
		let validFields: ObjectField[] = [];

		if (values.parameters?.objectDefinitionId) {
			const items = await getObjectFields(
				values.parameters.objectDefinitionId
			);

			validFields = items.filter(isValidField);
		}

		setCurrentObjectDefinitionFields(validFields);

		const {
			predefinedValues = [],
		} = values.parameters as ObjectActionParameters;

		const predefinedValuesMap = new Map<string, PredefinedValue>();

		predefinedValues.forEach((field) => {
			predefinedValuesMap.set(field.name, field);
		});

		const newPredefinedValues: PredefinedValue[] = [];

		validFields.forEach(({name, required}) => {
			if (predefinedValuesMap.has(name)) {
				const field = predefinedValuesMap.get(name);

				newPredefinedValues.push(field as PredefinedValue);
			}
			else if (required) {
				newPredefinedValues.push({
					inputAsValue: false,
					name,
					value: '',
				});
			}
		});
		setValues({
			parameters: {
				...values.parameters,
				predefinedValues: newPredefinedValues,
			},
		});
	};

	const handleSelectObject = async ({
		target: {value},
	}: React.ChangeEvent<HTMLSelectElement>) => {
		const objectDefinitionId = parseInt(value, 10);

		const object = relationships.find(({id}) => id === objectDefinitionId);

		const parameters: ObjectActionParameters = {
			objectDefinitionId,
			predefinedValues: [],
		};

		if (object?.related) {
			parameters.relatedObjectEntries = false;
		}

		const items = await getObjectFields(objectDefinitionId);

		const validFields: ObjectField[] = [];

		items.forEach((field) => {
			if (isValidField(field)) {
				validFields.push(field);

				if (field.required) {
					(parameters.predefinedValues as PredefinedValue[]).push({
						inputAsValue: false,
						name: field.name,
						value: '',
					});
				}
			}
		});

		setCurrentObjectDefinitionFields(validFields);

		const normalizedParameters = {...values.parameters};

		delete normalizedParameters.relatedObjectEntries;

		setValues({
			parameters: {
				...normalizedParameters,
				...parameters,
			},
		});
	};

	useEffect(() => {
		if (values.objectActionExecutorKey === 'add-object-entry') {
			fetchObjectDefinitions();
			fetchObjectDefinitionFields();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const predefinedValues = values.parameters?.predefinedValues;

		const requiredFields = predefinedValues
			? predefinedValues.filter(
					({name}) => objectFieldsMap.get(name)?.required
			  )
			: [];

		const hasEmptyValues = requiredFields?.some((item) =>
			invalidateRequired(item.value)
		);

		setWarningAlert(hasEmptyValues);
	}, [values.parameters?.predefinedValues, objectFieldsMap, setWarningAlert]);

	return (
		<>
			{infoAlert && (
				<ClayAlert
					className="lfr-objects__side-panel-content-container"
					displayType="info"
					onClose={() => setInfoAlert(false)}
					title={`${Liferay.Language.get('info')}:`}
				>
					{Liferay.Language.get(
						'create-conditions-and-predefined-values-using-expressions'
					) + ' '}

					<a
						className="alert-link"
						href="https://learn.liferay.com/dxp/latest/en/building-applications/objects/creating-and-managing-objects/expression-builder-validations-reference.html"
						target="_blank"
					>
						{Liferay.Language.get('click-here-for-documentation')}
					</a>
				</ClayAlert>
			)}

			<Card title={Liferay.Language.get('trigger')}>
				<Card
					title={Liferay.Language.get('when[object]')}
					viewMode="inline"
				>
					<FormCustomSelect
						error={errors.objectActionTriggerKey}
						onChange={({value}) =>
							setValues({
								conditionExpression: undefined,
								objectActionTriggerKey: value,
							})
						}
						options={objectActionTriggers}
						placeholder={Liferay.Language.get('choose-a-trigger')}
						value={actionTriggers.get(
							values.objectActionTriggerKey ?? ''
						)}
					/>
				</Card>
			</Card>

			{['onAfterAdd', 'onAfterDelete', 'onAfterUpdate'].some(
				(key) => key === values.objectActionTriggerKey
			) && (
				<Card title={Liferay.Language.get('condition')}>
					<ClayForm.Group>
						<ClayToggle
							label={Liferay.Language.get('enable-condition')}
							name="condition"
							onToggle={(enable) =>
								setValues({
									conditionExpression: enable
										? ''
										: undefined,
								})
							}
							toggled={
								!(values.conditionExpression === undefined)
							}
						/>
					</ClayForm.Group>

					{values.conditionExpression !== undefined && (
						<ExpressionBuilder
							error={errors.conditionExpression}
							feedbackMessage={Liferay.Language.get(
								'use-expressions-to-create-a-condition'
							)}
							label={Liferay.Language.get('expression-builder')}
							name="conditionExpression"
							onChange={({target: {value}}: any) =>
								setValues({conditionExpression: value})
							}
							onOpenModal={() => {
								const parentWindow = Liferay.Util.getOpener();

								parentWindow.Liferay.fire(
									'openExpressionBuilderModal',
									{
										onSave: handleSave,
										required: true,
										source: values.conditionExpression,
										validateExpressionURL,
									}
								);
							}}
							placeholder={Liferay.Language.get(
								'create-an-expression'
							)}
							value={values.conditionExpression as string}
						/>
					)}
				</Card>
			)}

			<Card title={Liferay.Language.get('action')}>
				<Card
					title={Liferay.Language.get('then[object]')}
					viewMode="inline"
				>
					<div className="lfr-object__action-builder-then">
						<FormCustomSelect
							error={errors.objectActionExecutorKey}
							onChange={({value}) => {
								if (value === 'add-object-entry') {
									fetchObjectDefinitions();
								}
								setValues({
									objectActionExecutorKey: value,
									parameters: {},
								});
							}}
							options={objectActionExecutors}
							placeholder={Liferay.Language.get(
								'choose-an-action'
							)}
							value={actionExecutors.get(
								values.objectActionExecutorKey ?? ''
							)}
						/>

						{values.objectActionExecutorKey ===
							'add-object-entry' && (
							<>
								on
								<SelectWithOption
									aria-label={Liferay.Language.get(
										'choose-an-object'
									)}
									error={errors.objectDefinitionId}
									onChange={handleSelectObject}
									options={objectsOptions}
									value={
										values.parameters?.objectDefinitionId
									}
								/>
								{values.parameters?.relatedObjectEntries !==
									undefined && (
									<>
										<ClayCheckbox
											checked={
												values.parameters
													.relatedObjectEntries ===
												true
											}
											disabled={false}
											label={Liferay.Language.get(
												'also-relate-entries'
											)}
											onChange={({target: {checked}}) => {
												setValues({
													parameters: {
														...values.parameters,
														relatedObjectEntries: checked,
													},
												});
											}}
										/>
										<ClayTooltipProvider>
											<div
												data-tooltip-align="top"
												title={Liferay.Language.get(
													'automatically-relate-object-entries-involved-in-the-action'
												)}
											>
												<ClayIcon
													className=".lfr-object__action-builder-tooltip-icon"
													symbol="question-circle-full"
												/>
											</div>
										</ClayTooltipProvider>
									</>
								)}
							</>
						)}

						{values.objectActionExecutorKey === 'notification' && (
							<FormCustomSelect
								className="lfr-object__action-builder-notification-then"
								error={errors.objectActionExecutorKey}
								label={Liferay.Language.get('notification')}
								onChange={({value}) => {
									setValues({
										parameters: {
											...values.parameters,
											notificationTemplateId: value,
										},
									});
								}}
								options={notificationTemplates}
								required
								value={notificationTemplateId}
							/>
						)}
					</div>
				</Card>

				{values.objectActionExecutorKey === 'add-object-entry' &&
					values.parameters?.objectDefinitionId && (
						<PredefinedValuesTable
							currentObjectDefinitionFields={
								currentObjectDefinitionFields
							}
							errors={errors.predefinedValues as any}
							objectFieldsMap={objectFieldsMap}
							setValues={setValues}
							validateExpressionURL={validateExpressionURL}
							values={values}
						/>
					)}

				{values.objectActionExecutorKey === 'webhook' && (
					<>
						<Input
							error={errors.url}
							label={Liferay.Language.get('url')}
							name="url"
							onChange={({target: {value}}) => {
								setValues({
									parameters: {
										...values.parameters,
										url: value,
									},
								});
							}}
							required
							value={values.parameters?.url}
						/>

						<Input
							label={Liferay.Language.get('secret')}
							name="secret"
							onChange={({target: {value}}) => {
								setValues({
									parameters: {
										...values.parameters,
										secret: value,
									},
								});
							}}
							value={values.parameters?.secret}
						/>
					</>
				)}

				{values.objectActionExecutorKey === 'groovy' && (
					<CodeEditor
						error={errors.script}
						fixed
						mode="groovy"
						onChange={(script) =>
							setValues({
								parameters: {
									...values.parameters,
									script,
								},
							})
						}
						value={values.parameters?.script ?? ''}
					/>
				)}
			</Card>
		</>
	);
}

interface IProps {
	errors: ActionError;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionsRelationshipsURL: string;
	setValues: (values: Partial<ObjectAction>) => void;
	setWarningAlert: (value: boolean) => void;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}

interface SelectItem {
	label: string;
	value: number;
}

type TNotificationTemplate = {
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	from: string;
	fromName: LocalizedValue<string>;
	id: number;
	name: string;
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
};
