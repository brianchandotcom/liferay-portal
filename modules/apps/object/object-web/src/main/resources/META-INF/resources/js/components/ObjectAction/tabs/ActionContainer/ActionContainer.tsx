/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	Card,
	CodeEditor,
	CustomItem,
	Input,
	SidebarCategory,
} from '@liferay/object-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

import {ActionError} from '../..';
import PredefinedValuesTable from '../../PredefinedValuesTable';
import {fetchObjectDefinitionFields} from '../../fetchUtil';
import {WarningStates} from '../ActionBuilder';
import {ThenContainer} from './ThenContainer';
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
	const [addObjectEntryDefinitions, setAddObjectEntryDefinitions] = useState<
		AddObjectEntryDefinitions[]
	>([]);

	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();

	const isValidField = (
		{businessType, name, objectFieldSettings, system}: ObjectField,
		isObjectActionSystem?: boolean
	) => {
		const userRelationship = !!objectFieldSettings?.find(
			({name, value}) =>
				name === 'objectDefinition1ShortName' && value === 'User'
		);

		if (businessType === 'Relationship' && userRelationship) {
			return true;
		}

		return isObjectActionSystem
			? businessType !== 'Aggregation' &&
					businessType !== 'Formula' &&
					businessType !== 'Relationship' &&
					name !== 'creator' &&
					name !== 'createDate' &&
					name !== 'id' &&
					name !== 'modifiedDate' &&
					name !== 'status'
			: businessType !== 'Aggregation' &&
					businessType !== 'Formula' &&
					businessType !== 'Relationship' &&
					!system;
	};

	const updateParameters = useCallback(
		async (value: string) => {
			const [
				externalReferenceCode,
				definitionIdValue,
				isObjectSystem,
			] = value.split(',');

			const definitionId = Number(definitionIdValue);

			const isSystem = isObjectSystem === 'true';

			const object = addObjectEntryDefinitions.find(
				(definition) =>
					definition.externalReferenceCode === externalReferenceCode
			);

			const parameters: ObjectActionParameters = {
				objectDefinitionExternalReferenceCode: externalReferenceCode,
				objectDefinitionId: definitionId,
				predefinedValues: [],
				system: isSystem,
			};

			if (object?.related) {
				parameters.relatedObjectEntries = false;
			}
			const items = await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
				externalReferenceCode
			);

			const validFields: ObjectField[] = [];

			items.forEach((field) => {
				if (isValidField(field, isSystem)) {
					validFields.push(field);

					if (
						field.required &&
						values.objectActionExecutorKey === 'add-object-entry'
					) {
						const inputAsValue =
							field.businessType === 'DateTime' ? true : false;

						(parameters.predefinedValues as PredefinedValue[]).push(
							{
								businessType: field.businessType,
								inputAsValue,
								label: field.label,
								name: field.name,
								value: '',
							}
						);
					}
				}
			});

			setCurrentObjectDefinitionFields(validFields);

			const normalizedParameters = {...values.parameters};

			delete normalizedParameters.relatedObjectEntries;

			setValues({
				parameters: {
					...normalizedParameters,
					...(values.objectActionExecutorKey ===
						'add-object-entry' && {
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
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[
			addObjectEntryDefinitions,
			values.objectActionExecutorKey,
			values.parameters,
		]
	);

	useEffect(() => {
		if (values.objectActionExecutorKey === 'update-object-entry') {
			updateParameters(
				`${objectDefinitionExternalReferenceCode},${objectDefinitionId},${systemObject}`
			);
			fetchObjectDefinitionFields(
				objectDefinitionId,
				objectDefinitionExternalReferenceCode,
				systemObject,
				values,
				isValidField,
				setCurrentObjectDefinitionFields,
				setValues
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		objectDefinitionId,
		objectDefinitionExternalReferenceCode,
		objectDefinitionsRelationshipsURL,
		systemObject,
		values.objectActionExecutorKey,
	]);

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinition = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode
			);

			setCreationLanguageId(objectDefinition.defaultLanguageId);
		};

		makeFetch();
	}, [objectDefinitionExternalReferenceCode]);

	return (
		<Card title={Liferay.Language.get('action')}>
			<ThenContainer
				errors={errors}
				isValidField={isValidField}
				newObjectActionExecutors={newObjectActionExecutors}
				objectActionExecutors={objectActionExecutors}
				objectDefinitionExternalReferenceCode={
					objectDefinitionExternalReferenceCode
				}
				objectDefinitionId={objectDefinitionId}
				objectDefinitionsRelationshipsURL={
					objectDefinitionsRelationshipsURL
				}
				setAddObjectEntryDefinitions={setAddObjectEntryDefinitions}
				setCurrentObjectDefinitionFields={
					setCurrentObjectDefinitionFields
				}
				setValues={setValues}
				systemObject={systemObject}
				updateParameters={updateParameters}
				values={values}
			/>

			{(values.objectActionExecutorKey === 'add-object-entry' ||
				values.objectActionExecutorKey === 'update-object-entry') &&
				values.parameters?.objectDefinitionExternalReferenceCode && (
					<PredefinedValuesTable
						creationLanguageId={
							creationLanguageId as Liferay.Language.Locale
						}
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
