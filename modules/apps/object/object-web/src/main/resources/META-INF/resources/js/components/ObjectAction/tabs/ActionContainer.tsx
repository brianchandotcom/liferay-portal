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

import {ActionError} from '..';
import {ClayCheckbox, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	API,
	Card,
	CodeEditor,
	CustomItem,
	Input,
	SelectWithOption,
	SidebarCategory,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import PredefinedValuesTable from '../PredefinedValuesTable';

import './ActionBuilder.scss';
import {
	fetchObjectDefinitionFields,
	fetchObjectDefinitions,
} from '../fetchUtil';
import {WarningStates} from './ActionBuilder';

export type ObjectsOptionsList = Array<
	(
		| React.ComponentProps<typeof ClaySelect.Option>
		| React.ComponentProps<typeof ClaySelect.OptGroup>
	) & {
		options?: Array<React.ComponentProps<typeof ClaySelect.Option>>;
		type?: 'group';
	}
>;

interface ActionContainerProps {
	currentObjectDefinitionFields: ObjectField[];
	errors: ActionError;
	newObjectActionExecutors: CustomItem<string>[];
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	objectFieldsMap: Map<string, ObjectField>;
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void;
	setValues: (values: Partial<ObjectAction>) => void;
	setWarningAlerts: (value: React.SetStateAction<WarningStates>) => void;
	systemObject: boolean;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}

export function ActionContainer({
	currentObjectDefinitionFields,
	errors,
	newObjectActionExecutors,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	objectFieldsMap,
	setCurrentObjectDefinitionFields,
	setValues,
	setWarningAlerts,
	systemObject,
	validateExpressionURL,
	values,
}: ActionContainerProps) {
	const [notificationTemplates, setNotificationTemplates] = useState<
		CustomItem<number>[]
	>([]);

	const [relationships, setRelationships] = useState<
		ObjectDefinitionsRelationship[]
	>([]);

	const [objectsOptions, setObjectOptions] = useState<ObjectsOptionsList>([]);

	const notificationTemplateLabel = useMemo(() => {
		return notificationTemplates.find(
			({value}) => value === values.parameters?.notificationTemplateId
		)?.label;
	}, [notificationTemplates, values.parameters]);

	const actionExecutors = useMemo(() => {
		const executors = new Map<string, string>();

		newObjectActionExecutors.forEach(({label, value}) => {
			value && executors.set(value, label);
		});

		return executors;
	}, [newObjectActionExecutors]);

	const isValidField = ({
		businessType,
		objectFieldSettings,
		system,
	}: ObjectField) => {
		const userRelationship = !!objectFieldSettings?.find(
			({name, value}) =>
				name === 'objectDefinition1ShortName' && value === 'User'
		);

		if (businessType === 'Relationship' && userRelationship) {
			return true;
		}

		return (
			businessType !== 'Aggregation' &&
			businessType !== 'Formula' &&
			businessType !== 'Relationship' &&
			!system
		);
	};

	const updateParameters = async (value: string) => {
		const [externalReferenceCode, definitionIdValue] = value.split(',');

		const definitionId = Number(definitionIdValue);

		const object = relationships.find(
			(relationship) =>
				relationship.externalReferenceCode === externalReferenceCode
		);

		const parameters: ObjectActionParameters = {
			objectDefinitionExternalReferenceCode: externalReferenceCode,
			objectDefinitionId: definitionId,
			predefinedValues: [],
		};

		if (object?.related) {
			parameters.relatedObjectEntries = false;
		}
		const items = await API.getObjectFieldsByExternalReferenceCode(
			externalReferenceCode
		);

		const validFields: ObjectField[] = [];

		items.forEach((field) => {
			if (isValidField(field)) {
				validFields.push(field);

				if (
					field.required &&
					values.objectActionExecutorKey === 'add-object-entry'
				) {
					(parameters.predefinedValues as PredefinedValue[]).push({
						inputAsValue: false,
						label: field.label,
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
				...(values.objectActionExecutorKey === 'add-object-entry' && {
					...parameters,
				}),
			},
		});

		setWarningAlerts((previousWarnings) => ({
			...previousWarnings,
			mandatoryRelationships: items.some(
				(field) =>
					field.businessType === 'Relationship' &&
					field.required === true
			),
		}));
	};

	useEffect(() => {
		if (values.objectActionExecutorKey === 'notification') {
			const makeFetch = async () => {
				const NotificationTemplatesResponse = await API.getNotificationTemplates();

				let notificationArray: NotificationTemplate[] = NotificationTemplatesResponse;

				if (systemObject) {
					notificationArray = NotificationTemplatesResponse.filter(
						(notificationTemplate) =>
							notificationTemplate.type !== 'userNotification'
					);
				}

				setNotificationTemplates(
					notificationArray.map(({id, name, type}) => ({
						label: name,
						type,
						value: id,
					}))
				);
			};

			makeFetch();
		}

		if (values.objectActionExecutorKey === 'add-object-entry') {
			fetchObjectDefinitions(
				objectDefinitionsRelationshipsURL,
				values,
				setRelationships,
				setObjectOptions
			);

			fetchObjectDefinitionFields(
				objectDefinitionId,
				objectDefinitionExternalReferenceCode,
				values,
				isValidField,
				setCurrentObjectDefinitionFields,
				setValues
			);
		}
		else if (values.objectActionExecutorKey === 'update-object-entry') {
			updateParameters(objectDefinitionExternalReferenceCode);
			fetchObjectDefinitionFields(
				objectDefinitionId,
				objectDefinitionExternalReferenceCode,
				values,
				isValidField,
				setCurrentObjectDefinitionFields,
				setValues
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		values.objectActionExecutorKey,
		systemObject,
		objectDefinitionId,
		objectDefinitionExternalReferenceCode,
		objectDefinitionsRelationshipsURL,
	]);

	return (
		<Card title={Liferay.Language.get('action')}>
			<Card
				title={Liferay.Language.get('then[object]')}
				viewMode="inline"
			>
				<div className="lfr-object__action-builder-then">
					<SingleSelect
						error={errors.objectActionExecutorKey}
						onChange={({value}) => {
							if (value === 'add-object-entry') {
								fetchObjectDefinitions(
									objectDefinitionsRelationshipsURL,
									values,
									setRelationships,
									setObjectOptions
								);
							}
							setValues({
								objectActionExecutorKey: value,
								parameters: {},
							});
						}}
						options={
							Liferay.FeatureFlags['LPS-153714']
								? newObjectActionExecutors
								: objectActionExecutors
						}
						placeholder={Liferay.Language.get('choose-an-action')}
						value={actionExecutors.get(
							values.objectActionExecutorKey ?? ''
						)}
					/>

					{values.objectActionExecutorKey === 'add-object-entry' && (
						<>
							on
							<SelectWithOption
								aria-label={Liferay.Language.get(
									'choose-an-object'
								)}
								error={
									errors.objectDefinitionExternalReferenceCode
								}
								onChange={({target: {value}}) =>
									updateParameters(value)
								}
								options={objectsOptions}
								value={
									values.parameters
										?.objectDefinitionExternalReferenceCode
										? `${values.parameters.objectDefinitionExternalReferenceCode},${values.parameters.objectDefinitionId}`
										: ''
								}
							/>
							{values.parameters?.relatedObjectEntries !==
								undefined && (
								<>
									<ClayCheckbox
										checked={
											values.parameters
												.relatedObjectEntries === true
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
						<SingleSelect<CustomItem<number>>
							className="lfr-object__action-builder-notification-then"
							error={errors.objectActionExecutorKey}
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
							value={notificationTemplateLabel}
						>
							{notificationTemplates.map(
								(option) =>
									Liferay.FeatureFlags['LPS-162133'] &&
									option.type && (
										<ClayLabel
											displayType={
												option.type === 'email'
													? 'success'
													: 'info'
											}
										>
											{option.type === 'email'
												? Liferay.Language.get('email')
												: Liferay.Language.get(
														'user-notification'
												  )}
										</ClayLabel>
									)
							)}
						</SingleSelect>
					)}
				</div>
			</Card>

			{(values.objectActionExecutorKey === 'add-object-entry' ||
				values.objectActionExecutorKey === 'update-object-entry') &&
				values.parameters?.objectDefinitionExternalReferenceCode && (
					<PredefinedValuesTable
						currentObjectDefinitionFields={
							currentObjectDefinitionFields
						}
						disableRequiredChecked={
							values.objectActionExecutorKey ===
							'update-object-entry'
						}
						errors={
							errors.predefinedValues as {
								[key: string]: string;
							}
						}
						objectFieldsMap={objectFieldsMap}
						setValues={setValues}
						title={
							values.objectActionExecutorKey ===
							'update-object-entry'
								? Liferay.Language.get('values')
								: ''
						}
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
					mode="groovy"
					onChange={(script, lineCount) =>
						setValues({
							parameters: {
								...values.parameters,
								lineCount,
								script,
							},
						})
					}
					sidebarElements={objectActionCodeEditorElements.filter(
						(element) => element.label === 'Fields'
					)}
					value={values.parameters?.script ?? ''}
				/>
			)}
		</Card>
	);
}
