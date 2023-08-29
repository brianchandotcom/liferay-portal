/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import React, {useEffect, useState} from 'react';

import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';

import './RightSidebarObjectFieldDetails.scss';

import {API} from '@liferay/object-js-components-web';
import {Node, isNode} from 'react-flow-renderer';

import {objectFieldInitialValues} from '../../ObjectField/EditObjectField';
import {BasicInfoTab} from '../../ObjectField/Tabs/BasicInfo/BasicInfoTab';
import {useObjectFieldForm} from '../../ObjectField/useObjectFieldForm';

export function RightSidebarObjectFieldDetails() {
	const [load, setLoad] = useState(false);

	const [
		{elements, filterOperators, workflowStatusJSONArray},
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
		initialValues: objectFieldInitialValues,
		onSubmit,
	});

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedField) {
				setLoad(true);
				const ObjectFieldResponse = await API.getObjectField(
					selectedField?.id as number
				);
				setValues(ObjectFieldResponse);
				setLoad(false);
			}
		};
		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedField]);

	return (
		<div onBlur={onSubmit}>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>{selectedField?.label}</span>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				{load ? (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				) : (
					<BasicInfoTab
						errors={errors}
						filterOperators={filterOperators}
						handleChange={handleChange}
						isApproved={
							selectedNode.data?.status.label === 'Approved'
						}
						isDefaultStorageType={
							selectedNode.data?.storageType === 'default'
						}
						objectDefinitionExternalReferenceCode={
							selectedNode.data?.externalReferenceCode as string
						}
						objectFieldTypes={[]}
						objectName={selectedNode.data?.name as string}
						objectRelationshipId={
							selectedField?.relationshipId as number
						}
						readOnly={
							selectedNode.data
								?.hasObjectDefinitionUpdateResourcePermission as boolean
						}
						setValues={setValues}
						values={values}
						workflowStatusJSONArray={workflowStatusJSONArray}
						wrapper={ClayPanel}
					/>
				)}
			</div>
		</div>
	);
}
