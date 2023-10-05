/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {Elements, Node, isNode} from 'react-flow-renderer';

import {AccountRestrictionContainer} from '../../ObjectDetails/AccountRestrictionContainer';
import {ConfigurationContainer} from '../../ObjectDetails/ConfigurationContainer';
import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
import {EntryDisplayContainer} from '../../ObjectDetails/EntryDisplayContainer';
import {ObjectDataContainer} from '../../ObjectDetails/ObjectDataContainer';
import {ScopeContainer} from '../../ObjectDetails/ScopeContainer';
import {TranslationsContainer} from '../../ObjectDetails/TranslationsContainer';
import {useObjectDetailsForm} from '../../ObjectDetails/useObjectDetailsForm';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {nonRelationshipObjectFieldsInfo} from '../types';

import './RightSidebarObjectDefinitionDetails.scss';

interface RightSidebarObjectDefinitionDetailsProps {
	companyKeyValuePairs: KeyValuePair[];
	siteKeyValuePairs: KeyValuePair[];
}

function setAccountRelationshipFieldMandatory(
	values: Partial<ObjectDefinition>
) {
	const {objectFields} = values;

	const newObjectFields = objectFields?.map((objectField) => {
		if (objectField.name === values.accountEntryRestrictedObjectFieldName) {
			return {
				...objectField,
				required: true,
			};
		}

		return objectField;
	});

	return {
		...values,
		objectFields: newObjectFields,
	};
}

export function RightSidebarObjectDefinitionDetails({
	companyKeyValuePairs,
	siteKeyValuePairs,
}: RightSidebarObjectDefinitionDetailsProps) {
	const [
		nonRelationshipObjectFieldsInfo,
		setNonRelationshipObjectFieldsInfo,
	] = useState<nonRelationshipObjectFieldsInfo[]>();
	const [
		objectDefinitionDBTableName,
		setObjectDefinitionDBTableName,
	] = useState('');

	const [
		{
			baseResourceURL,
			elements,
			selectedObjectDefinitionNode,
			selectedObjectFolder,
		},
		dispatch,
	] = useObjectFolderContext();

	const {
		errors,
		handleChange,
		handleValidate,
		setValues,
		values,
	} = useObjectDetailsForm({
		initialValues: {
			defaultLanguageId: 'en_US',
			externalReferenceCode: '',
			id: 0,
			label: {},
			name: '',
			pluralLabel: {},
			titleObjectFieldName: '',
		},
		onSubmit: () => {},
	});

	const isRootDescendantNode =
		!!values.rootObjectDefinitionExternalReferenceCode &&
		values.externalReferenceCode !==
			values.rootObjectDefinitionExternalReferenceCode;

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedObjectDefinitionNode) {
				const selectedObjectDefinition = await API.getObjectDefinitionByExternalReferenceCode(
					selectedObjectDefinitionNode.data
						?.externalReferenceCode as string
				);

				const objectDefinitionInfoURL = createResourceURL(
					baseResourceURL,
					{
						objectDefinitionId:
							selectedObjectDefinitionNode.data?.id,
						p_p_resource_id:
							'/object_definitions/get_object_definition_info',
					}
				).href;

				const objectDefinitionInfoResponse = await API.fetchJSON<{
					tableName: string;
				}>(objectDefinitionInfoURL);

				setObjectDefinitionDBTableName(
					objectDefinitionInfoResponse.tableName
				);

				const newNonRelationshipObjectFieldsInfo = selectedObjectDefinition.objectFields
					.filter(
						(objectField) =>
							objectField.businessType !== 'Relationship'
					)
					.map((objectField) => ({
						label: objectField.label,
						name: objectField.name,
					})) as nonRelationshipObjectFieldsInfo[];

				setNonRelationshipObjectFieldsInfo(
					newNonRelationshipObjectFieldsInfo
				);
				setValues(selectedObjectDefinition);
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onSubmit = async (
		editedObjectDefinition?: Partial<ObjectDefinition>
	) => {
		const validationErrors = handleValidate(
			editedObjectDefinition ?? values
		);

		if (!Object.keys(validationErrors).length) {
			let objectDefinition = editedObjectDefinition ?? values;

			delete objectDefinition.objectRelationships;
			delete objectDefinition.objectActions;
			delete objectDefinition.objectLayouts;
			delete objectDefinition.objectViews;

			if (objectDefinition.accountEntryRestricted) {
				objectDefinition = setAccountRelationshipFieldMandatory(
					objectDefinition
				);
			}

			try {
				await API.putObjectDefinitionByExternalReferenceCode(
					objectDefinition
				);

				dispatch({
					payload: {
						updatedShowChangesSaved: true,
					},
					type: TYPES.SET_SHOW_CHANGES_SAVED,
				});
			}
			catch (error: unknown) {
				const {message} = error as Error;

				openToast({message, type: 'danger'});
			}

			let newObjectDefinition = {};

			const updatedElements = elements.map((element) => {
				if (
					isNode(element) &&
					(element as Node<ObjectDefinitionNodeData>).id ===
						objectDefinition.id?.toString()
				) {
					newObjectDefinition = {
						...element.data,
						label: objectDefinition.label,
						name: objectDefinition.name,
						pluralLabel: {
							[objectDefinition.defaultLanguageId!]: objectDefinition.pluralLabel,
						},
					};

					return {
						...element,
						data: newObjectDefinition,
					};
				}

				return element;
			}) as Elements<ObjectDefinitionNodeData>;

			dispatch({
				payload: {
					newElements: updatedElements,
				},
				type: TYPES.SET_ELEMENTS,
			});

			dispatch({
				payload: {
					currentObjectFolderName: selectedObjectFolder.name,
					updatedObjectDefinitionNode: newObjectDefinition,
				},
				type: TYPES.UPDATE_OBJECT_DEFINITION_NODE,
			});
		}
	};

	const nodeDetailsTitle = sub(
		Liferay.Language.get('x-details'),
		getLocalizableLabel(
			values.defaultLanguageId as Liferay.Language.Locale,
			values?.label,
			values?.name
		)
	);

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-details">
				<div
					className="lfr-objects__model-builder-right-sidebar-object-definition-node-details-title text-truncate"
					title={nodeDetailsTitle}
				>
					<span>{nodeDetailsTitle}</span>
				</div>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-content">
				<ObjectDataContainer
					dbTableName={objectDefinitionDBTableName}
					errors={errors}
					handleChange={handleChange}
					hasUpdateObjectDefinitionPermission={
						!!values.actions?.update
					}
					isApproved={values.status?.label === 'approved'}
					isLinkedObjectDefinition={
						selectedObjectDefinitionNode?.data
							?.linkedObjectDefinition ?? false
					}
					onSubmit={onSubmit}
					setValues={setValues}
					values={values as ObjectDefinition}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-content">
				<EntryDisplayContainer
					errors={errors}
					isLinkedObjectDefinition={
						selectedObjectDefinitionNode?.data
							?.linkedObjectDefinition ?? false
					}
					nonRelationshipObjectFieldsInfo={
						nonRelationshipObjectFieldsInfo ?? []
					}
					objectFields={values.objectFields ?? []}
					onSubmit={onSubmit}
					setValues={setValues}
					values={values as ObjectDefinition}
				/>

				<ScopeContainer
					companyKeyValuePairs={companyKeyValuePairs}
					errors={errors}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={values.status?.label === 'approved'}
					isLinkedObjectDefinition={
						selectedObjectDefinitionNode?.data
							?.linkedObjectDefinition ?? false
					}
					isRootDescendantNode={isRootDescendantNode}
					onSubmit={onSubmit}
					setValues={setValues}
					siteKeyValuePairs={siteKeyValuePairs}
					values={values as ObjectDefinition}
				/>
			</div>

			{values?.modifiable && (
				<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-content">
					<AccountRestrictionContainer
						errors={errors}
						isApproved={values?.status?.label === 'approved'}
						isLinkedObjectDefinition={
							selectedObjectDefinitionNode?.data
								?.linkedObjectDefinition ?? false
						}
						isRootDescendantNode={isRootDescendantNode}
						objectFields={
							(values?.objectFields as ObjectField[]) ?? []
						}
						onSubmit={onSubmit}
						setValues={setValues}
						values={values as ObjectDefinition}
					/>
				</div>
			)}

			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-content">
				<ConfigurationContainer
					hasUpdateObjectDefinitionPermission={
						!!values.actions?.update
					}
					isLinkedObjectDefinition={
						selectedObjectDefinitionNode?.data
							?.linkedObjectDefinition ?? false
					}
					isRootDescendantNode={isRootDescendantNode}
					onSubmit={onSubmit}
					setValues={setValues}
					values={values as ObjectDefinition}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-content">
				<TranslationsContainer
					onSubmit={onSubmit}
					setValues={setValues}
					values={values}
				/>
			</div>
		</>
	);
}
