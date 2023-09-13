/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {Node, isNode} from 'react-flow-renderer';

import {AccountRestrictionContainer} from '../../ObjectDetails/AccountRestrictionContainer';
import {ConfigurationContainer} from '../../ObjectDetails/ConfigurationContainer';
import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
import {EntryDisplayContainer} from '../../ObjectDetails/EntryDisplayContainer';
import {ObjectDataContainer} from '../../ObjectDetails/ObjectDataContainer';
import {ScopeContainer} from '../../ObjectDetails/ScopeContainer';
import {nonRelationshipObjectFieldsInfo} from '../types';

import './RightSidebarObjectDefinitionDetails.scss';
import {useObjectDetailsForm} from '../../ObjectDetails/useObjectDetailsForm';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
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
		{elements, selectedObjectFolder},
		dispatch,
	] = useObjectFolderContext();

	const selectedObjectDefinitionNode = elements.find((element) => {
		if (isNode(element)) {
			return (element as Node<ObjectDefinitionNodeData>).data?.selected;
		}
	}) as Node<ObjectDefinitionNodeData>;

	const [
		nonRelationshipObjectFieldsInfo,
		setNonRelationshipObjectFieldsInfo,
	] = useState<nonRelationshipObjectFieldsInfo[]>();

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
	}, [selectedObjectDefinitionNode]);

	const onSubmit = async () => {
		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			delete values.objectRelationships;
			delete values.objectActions;
			delete values.objectLayouts;
			delete values.objectViews;

			let objectDefinition = values;

			if (values.accountEntryRestricted) {
				objectDefinition = setAccountRelationshipFieldMandatory(values);
			}

			const updatedObjectDefinitionResponse = await API.putObjectDefinitionByExternalReferenceCode(
				objectDefinition
			);

			let newObjectDefinition = {};

			const updatedElements = elements.map((element) => {
				if (
					isNode(element) &&
					(element as Node<ObjectDefinitionNodeData>).id ===
						objectDefinition.id?.toString()
				) {
					newObjectDefinition = {
						...element.data,
						label: getLocalizableLabel(
							objectDefinition.defaultLanguageId!,
							objectDefinition.label,
							objectDefinition.name
						),
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
			});

			if (!updatedObjectDefinitionResponse.ok) {
				const {
					title,
				} = (await updatedObjectDefinitionResponse.json()) as {
					status: string;
					title: string;
				};

				openToast({
					message: title,
					type: 'danger',
				});

				return;
			}

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

			openToast({
				message: Liferay.Language.get(
					'the-object-was-saved-successfully'
				),
				type: 'success',
			});
		}
	};

	return (
		<div onBlur={onSubmit}>
			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-title">
				<span>
					{sub(
						Liferay.Language.get('x-details'),
						getLocalizableLabel(
							values.defaultLanguageId as Liferay.Language.Locale,
							values?.label,
							values?.name
						)
					)}
				</span>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-object-definition-node-content">
				<ObjectDataContainer
					dbTableName=""
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
					setValues={setValues}
					siteKeyValuePairs={siteKeyValuePairs}
					values={values as ObjectDefinition}
				/>
			</div>

			{(Liferay.FeatureFlags['LPS-167253']
				? values?.modifiable
				: !values?.system) && (
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
					setValues={setValues}
					values={values as ObjectDefinition}
				/>
			</div>
		</div>
	);
}
