/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {API} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';
import {Node, isNode} from 'react-flow-renderer';

import {objectFieldInitialValues} from '../../ObjectField/EditObjectField';
import {EditObjectFieldContent} from '../../ObjectField/EditObjectFieldContent';
import {useObjectFieldForm} from '../../ObjectField/useObjectFieldForm';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';

import './RightSidebarObjectFieldDetails.scss';

export function RightSidebarObjectFieldDetails() {
	const [loading, setLoading] = useState(false);

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

	const onSubmit = async () => {};

	const {errors, handleChange, setValues, values} = useObjectFieldForm({
		forbiddenChars,
		forbiddenLastChars,
		forbiddenNames,
		initialValues: objectFieldInitialValues,
		onSubmit,
	});

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedField) {
				setLoading(true);
				const ObjectFieldResponse = await API.getObjectField(
					selectedField?.id as number
				);
				setValues(ObjectFieldResponse);
				setLoading(false);
			}
		};
		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedField]);

	return (
		<div onBlur={onSubmit}>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>{selectedField?.label}</span>

				<ClayButtonWithIcon
					aria-label="Trash"
					displayType="secondary"
					symbol="trash"
					title="Trash"
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				{loading ? (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				) : (
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
							selectedNode.data?.storageType === 'default' ?? true
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
				)}
			</div>
		</div>
	);
}
