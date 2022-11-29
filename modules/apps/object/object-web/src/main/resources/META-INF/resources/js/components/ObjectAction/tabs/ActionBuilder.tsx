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
import ClayLabel from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	API,
	Card,
	CodeEditor,
	CustomItem,
	ExpressionBuilder,
	Input,
	InputLocalized,
	SelectWithOption,
	SidebarCategory,
	SingleSelect,
	invalidateRequired,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import PredefinedValuesTable from '../PredefinedValuesTable';

import './ActionBuilder.scss';
import {ActionError} from '../index';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

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
	isApproved,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	setValues,
	validateExpressionURL,
	values,
}: IProps) {
	const [newObjectActionExecutors, setNewObjectActionExecutors] = useState<
		CustomItem[]
	>(objectActionExecutors);

	const [notificationTemplates, setNotificationTemplates] = useState<
		CustomItem<number>[]
	>([]);

	const [objectsOptions, setObjectOptions] = useState<ObjectsOptionsList>([]);

	const notificationTemplateLabel = useMemo(() => {
		return notificationTemplates.find(
			({value}) => value === values.parameters?.notificationTemplateId
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

	const [warningAlerts, setWarningAlerts] = useState<WarningStates>({
		mandatoryRelationships: false,
		requiredFields: false,
	});

	const [errorAlert, setErrorAlert] = useState(false);

	const fetchObjectDefinitions = async () => {
		const relationships = await API.fetchJSON<
			ObjectDefinitionsRelationship[]
		>(objectDefinitionsRelationshipsURL);

		const relatedObjects: SelectItem[] = [];
		const unrelatedObjects: SelectItem[] = [];

		relationships?.forEach((object) => {
			const {id, label} = object;

			const target = object.related ? relatedObjects : unrelatedObjects;

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

		fillSelect(Liferay.Language.get('unrelated-objects'), unrelatedObjects);

		setObjectOptions(objectsOptionsList);
		setRelationships(relationships);
	};

	const actionExecutors = useMemo(() => {
		const executors = new Map<string, string>();

		newObjectActionExecutors.forEach(({label, value}) => {
			value && executors.set(value, label);
		});

		return executors;
	}, [newObjectActionExecutors]);

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
		if (values.objectActionTriggerKey === 'onAfterDelete') {
			newObjectActionExecutors.map((action) => {
				if (action.value === 'update-object-entry') {
					action.disabled = true;
					action.popover = {
						body:
							Liferay.Language.get(
								'it-is-not-possible-to-create-an-update-action-with-an-on-after-delete-trigger'
							) +
							' ' +
							Liferay.Language.get(
								'please-change-the-action-trigger'
							),
						header: Liferay.Language.get('action-not-allowed'),
					};
				}
			});

			if (values.objectActionExecutorKey === 'update-object-entry') {
				setErrorAlert(true);
			}

			setNewObjectActionExecutors(newObjectActionExecutors);
		}
		else if (
			values.objectActionTriggerKey === 'onAfterAdd' ||
			values.objectActionTriggerKey === 'onAfterUpdate'
		) {
			newObjectActionExecutors.map((action) => {
				if (action.value === 'update-object-entry') {
					delete action.disabled;
					delete action.popover;
				}
			});

			if (values.objectActionExecutorKey === 'update-object-entry') {
				setErrorAlert(false);
			}

			setNewObjectActionExecutors(newObjectActionExecutors);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectActionTriggerKey]);

	useEffect(() => {
		if (values.objectActionExecutorKey === 'notification') {
			API.getNotificationTemplates().then((items) => {
				const notificationsArray = items.map(({id, name, type}) => ({
					label: name,
					type,
					value: id,
				}));

				setNotificationTemplates(notificationsArray);
			});
		}
	}, [values]);

	const handleSave = (conditionExpression?: string) => {
		setValues({conditionExpression});
	};

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

	const fetchObjectDefinitionFields = async () => {
		let validFields: ObjectField[] = [];
		let definitionId = objectDefinitionId;

		if (values.objectActionExecutorKey === 'add-object-entry') {
			definitionId = values?.parameters?.objectDefinitionId as number;
		}

		if (definitionId) {
			const items = await API.getObjectFields(definitionId);

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

		validFields.forEach(({label, name, required}) => {
			if (predefinedValuesMap.has(name)) {
				const field = predefinedValuesMap.get(name);

				newPredefinedValues.push(field as PredefinedValue);
			}
			else if (
				required &&
				values.objectActionExecutorKey === 'add-object-entry'
			) {
				newPredefinedValues.push({
					inputAsValue: false,
					label,
					name,
					value: '',
				});
			}
		});
		setValues({
			parameters: {
				...values.parameters,
				objectDefinitionId: definitionId,
				predefinedValues: newPredefinedValues,
			},
		});
	};

	const updateParameters = async (objectDefinitionId: number) => {
		const object = relationships.find(({id}) => id === objectDefinitionId);

		const parameters: ObjectActionParameters = {
			objectDefinitionId,
			predefinedValues: [],
		};

		if (object?.related) {
			parameters.relatedObjectEntries = false;
		}
		const items = await API.getObjectFields(objectDefinitionId);

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

	const handleSelectObject = async ({
		target: {value},
	}: React.ChangeEvent<HTMLSelectElement>) => {
		const objectDefinitionId = parseInt(value, 10);

		updateParameters(objectDefinitionId);
	};

	useEffect(() => {
		if (values.objectActionExecutorKey === 'add-object-entry') {
			fetchObjectDefinitions();
			fetchObjectDefinitionFields();
		}
		else if (values.objectActionExecutorKey === 'update-object-entry') {
			updateParameters(objectDefinitionId);
			fetchObjectDefinitionFields();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectActionExecutorKey]);

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

		setWarningAlerts((previousWarnings) => ({
			...previousWarnings,
			requiredFields: hasEmptyValues,
		}));
	}, [
		values.parameters?.predefinedValues,
		objectFieldsMap,
		setWarningAlerts,
	]);

	const closeWarningAlert = (warning: string) => {
		setWarningAlerts((previousWarnings) => ({
			...previousWarnings,
			[warning]: false,
		}));
	};

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

			{errorAlert && (
				<ClayAlert
					className="lfr-objects__side-panel-content-container"
					displayType="danger"
					onClose={() => setErrorAlert(false)}
					title={`${Liferay.Language.get('error')}:`}
				>
					{Liferay.Language.get(
						'it-is-not-possible-to-create-an-update-action-with-an-on-after-delete-trigger'
					)}
				</ClayAlert>
			)}

			<Card title={Liferay.Language.get('trigger')}>
				<Card
					title={Liferay.Language.get('when[object]')}
					viewMode="inline"
				>
					<SingleSelect
						disabled={isApproved}
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

			<Card
				disabled={values.objectActionTriggerKey === 'standalone'}
				title={Liferay.Language.get('condition')}
			>
				<ClayForm.Group>
					<ClayToggle
						disabled={
							values.objectActionTriggerKey === 'standalone'
						}
						label={Liferay.Language.get('enable-condition')}
						name="condition"
						onToggle={(enable) =>
							setValues({
								conditionExpression: enable ? '' : undefined,
							})
						}
						toggled={!(values.conditionExpression === undefined)}
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
						onChange={({target: {value}}) =>
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

			{warningAlerts.requiredFields && (
				<ClayAlert
					className="lfr-objects__side-panel-content-container"
					displayType="warning"
					onClose={() => closeWarningAlert('requiredFields')}
					title={`${Liferay.Language.get('warning')}:`}
				>
					{Liferay.Language.get(
						'required-fields-must-have-predefined-values'
					)}
				</ClayAlert>
			)}

			{warningAlerts.mandatoryRelationships && (
				<ClayAlert
					className="lfr-objects__side-panel-content-container"
					displayType="warning"
					onClose={() => closeWarningAlert('mandatoryRelationships')}
					title={`${Liferay.Language.get('warning')}:`}
				>
					{Liferay.Language.get(
						'the-selected-object-definition-has-mandatory-relationship-fields'
					)}
				</ClayAlert>
			)}

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
									fetchObjectDefinitions();
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
													? Liferay.Language.get(
															'email'
													  )
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
					values.parameters?.objectDefinitionId && (
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

			{Liferay.FeatureFlags['LPS-148804'] &&
				values.objectActionTriggerKey === 'standalone' && (
					<Card title={Liferay.Language.get('error-message')}>
						<InputLocalized
							error={errors.errorMessage}
							label={Liferay.Language.get('message')}
							name="label"
							onChange={(value) =>
								setValues({
									errorMessage: value,
								})
							}
							required
							translations={
								values.errorMessage ?? {[defaultLanguageId]: ''}
							}
						/>
					</Card>
				)}
		</>
	);
}

interface IProps {
	errors: ActionError;
	isApproved: boolean;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	setValues: (values: Partial<ObjectAction>) => void;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}

interface SelectItem {
	label: string;
	value: number;
}

interface WarningStates {
	mandatoryRelationships: boolean;
	requiredFields: boolean;
}
