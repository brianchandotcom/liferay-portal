/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayPanel from '@clayui/panel';
import {
	API,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {Node, isNode, useStore} from 'react-flow-renderer';

import {objectFieldInitialValues} from '../../ObjectField/EditObjectField';
import {EditObjectFieldContent} from '../../ObjectField/EditObjectFieldContent';
import {ModalDeleteObjectField} from '../../ObjectField/ModalDeleteObjectField';
import {useObjectFieldForm} from '../../ObjectField/useObjectFieldForm';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './RightSidebarObjectFieldDetails.scss';

export function RightSidebarObjectFieldDetails() {
	const [{baseResourceURL}] = useFolderContext();
	const [showDeletionModal, setShowDeletionModal] = useState(false);
	const [
		showDeletionNotAllowedModal,
		setShowDeletionNotAllowedModal,
	] = useState<boolean>(false);
	const store = useStore();
	const {edges, nodes} = store.getState();

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

	const handleTriggerDeleteObjectFieldModal = async () => {
		const url = createResourceURL(baseResourceURL, {
			objectFieldId: values.id,
			p_p_resource_id: '/object_definitions/get_object_field_delete_info',
		}).href;

		const showModalResponse = await API.fetchJSON<{
			showDeletionModal: boolean;
			showDeletionNotAllowedModal: boolean;
		}>(url);

		setShowDeletionModal(true);

		setShowDeletionNotAllowedModal(
			showModalResponse.showDeletionNotAllowedModal
		);
	};

	const onSubmit = async () => {
		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			const {id, ...objectField} = values;

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
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedField]);

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>
					{getLocalizableLabel(
						selectedNode.data
							?.defaultLanguageId as Liferay.Language.Locale,
						selectedField?.label,
						selectedField?.name
					)}
				</span>

				<div className="lfr-objects__model-builder-right-sidebar-definition-node-title-buttons-container">
					<ClayButton
						aria-label="Save"
						className="lfr-objects__model-builder-right-sidebar-definition-node-title-save-button"
						displayType="primary"
						onClick={() => onSubmit()}
					>
						{Liferay.Language.get('save')}
					</ClayButton>

					{!values.system &&
						values.businessType !== 'Relationship' && (
							<ClayButtonWithIcon
								aria-label="Delete"
								className="lfr-objects__model-builder-right-sidebar-definition-node-title-delete-button"
								displayType="secondary"
								onClick={() =>
									handleTriggerDeleteObjectFieldModal()
								}
								symbol="trash"
								title="Delete"
							/>
						)}
				</div>
			</div>

			<div>
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
				</div>
			</div>

			{showDeletionModal && (
				<ModalDeleteObjectField
					objectField={values as ObjectField}
					onAfterSubmit={() => {
						if (selectedField) {
							dispatch({
								payload: {
									edges,
									nodes,
									selectedField,
									selectedNode,
								},
								type: TYPES.DELETE_OBJECT_FIELD,
							});
						}
					}}
					setModalVisibility={setShowDeletionModal}
					showDeletionNotAllowedModal={showDeletionNotAllowedModal}
				/>
			)}
		</>
	);
}
