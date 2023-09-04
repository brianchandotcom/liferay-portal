/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {
	API,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';
import {Node, isNode, useStore} from 'react-flow-renderer';

import {objectFieldInitialValues} from '../../ObjectField/EditObjectField';
import {EditObjectFieldContent} from '../../ObjectField/EditObjectFieldContent';
import {useObjectFieldForm} from '../../ObjectField/useObjectFieldForm';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './RightSidebarObjectFieldDetails.scss';

export function RightSidebarObjectFieldDetails() {
	const [loading, setLoading] = useState(false);
	const store = useStore();

	const [
		{
			elements,
			filterOperators,
			forbiddenChars,
			forbiddenLastChars,
			forbiddenNames,
			objectWebLearnResources,
			workflowStatusJSONArray,
		},
		dispatch,
	] = useFolderContext();

	const selectedNode = elements.find((element) => {
		if (isNode(element)) {
			return (element as Node<ObjectDefinitionNodeData>).data
				?.nodeSelected;
		}
	}) as Node<ObjectDefinitionNodeData>;

	const selectedField = selectedNode.data?.objectFields.find(
		(field) => field.selected
	);

	const {
		errors,
		handleChange,
		handleValidate,
		setValues,
		values,
	} = useObjectFieldForm({
		forbiddenChars,
		forbiddenLastChars,
		forbiddenNames,
		initialValues: objectFieldInitialValues,
		onSubmit: () => {},
	});

	const onSubmit = async () => {
		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			setLoading(true);
			const {id, ...objectField} = values;
			const {edges, nodes} = store.getState();

			delete objectField.defaultValue;
			delete objectField.listTypeDefinitionId;
			delete objectField.system;

			try {
				const updatedFieldResponse = await API.save<ObjectField>(
					`/o/object-admin/v1.0/object-fields/${id}`,
					objectField
				);

				dispatch({
					payload: {
						edges,
						nodes,
						selectedNode,
						updatedField: updatedFieldResponse,
					},
					type: TYPES.UPDATE_OBJECT_FIELD,
				});

				openToast({
					message: Liferay.Language.get(
						'the-object-field-was-updated-successfully'
					),
				});

				setTimeout(() => setLoading(false), 500);
			}
			catch (error) {
				openToast({
					message: (error as Error).message,
					type: 'danger',
				});
			}
		}
	};

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedField) {
				const objectFieldResponse = await API.getObjectField(
					selectedField?.id as number
				);

				setValues(objectFieldResponse);
			}
		};

		makeFetch();

		return () => {
			setValues({});
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedField]);

	return (
		<>
			{loading ? (
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			) : (
				<div onBlur={onSubmit}>
					<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
						<span>
							{getLocalizableLabel(
								selectedNode.data
									?.defaultLanguageId as Liferay.Language.Locale,
								selectedField?.label,
								selectedField?.name
							)}
						</span>

						<ClayButtonWithIcon
							aria-label="Trash"
							displayType="secondary"
							symbol="trash"
							title="Trash"
						/>
					</div>

					<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
						<EditObjectFieldContent
							containerWrapper={ClayPanel}
							creationLanguageId={
								selectedNode.data?.defaultLanguageId ?? 'en_US'
							}
							errors={errors}
							filterOperators={filterOperators}
							handleChange={handleChange}
							isApproved={
								selectedNode.data?.status.label === 'approved'
							}
							isDefaultStorageType={
								selectedNode.data?.storageType === 'default' ??
								true
							}
							learnResources={objectWebLearnResources}
							modelBuilder
							objectDefinitionExternalReferenceCode={
								selectedNode.data?.externalReferenceCode ?? ''
							}
							objectFieldTypes={[]}
							objectName={selectedNode.data?.name as string}
							objectRelationshipId={0}
							readOnly={
								!selectedNode.data
									?.hasObjectDefinitionUpdateResourcePermission ??
								false
							}
							readOnlySidebarElements={[]}
							setValues={setValues}
							sidebarElements={[]}
							values={values}
							workflowStatusJSONArray={workflowStatusJSONArray}
						/>
					</div>
				</div>
			)}
		</>
	);
}
