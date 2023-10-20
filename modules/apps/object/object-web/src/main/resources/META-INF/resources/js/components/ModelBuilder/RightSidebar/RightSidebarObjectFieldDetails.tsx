/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Text} from '@clayui/core';
import ClayPanel from '@clayui/panel';
import {
	API,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {useStore} from 'react-flow-renderer';

import ModalDeletionNotAllowed from '../../ModalDeletionNotAllowed';
import {objectFieldInitialValues} from '../../ObjectField/EditObjectField';
import {EditObjectFieldContent} from '../../ObjectField/EditObjectFieldContent';
import {ModalDeleteObjectField} from '../../ObjectField/ModalDeleteObjectField';
import {useObjectFieldForm} from '../../ObjectField/useObjectFieldForm';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

import './RightSidebarObjectFieldDetails.scss';

export function RightSidebarObjectFieldDetails() {
	const [
		showDeletionObjectFieldModal,
		setShowDeletionObjectFieldModal,
	] = useState(false);
	const [
		showDeletionNotAllowedModal,
		setShowDeletionNotAllowedModal,
	] = useState<DeletionNotAllowedModal>();
	const [
		{
			baseResourceURL,
			filterOperators,
			forbiddenChars,
			forbiddenLastChars,
			forbiddenNames,
			objectWebLearnResources,
			selectedObjectDefinitionNode,
			selectedObjectField,
			workflowStatusJSONArray,
		},
		dispatch,
	] = useObjectFolderContext();
	const store = useStore();

	const {edges, nodes} = store.getState();

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
		const objectFieldModalDeletionModalUrl = createResourceURL(
			baseResourceURL,
			{
				objectFieldId: values.id,
				p_p_resource_id:
					'/object_definitions/get_object_field_delete_info',
			}
		).href;

		const showModalResponse = await API.fetchJSON<{
			deleteLastPublishedObjectDefinitionObjectField: boolean;
			deleteObjectFieldObjectValidationRuleSetting: boolean;
			showDeletionModal: boolean;
		}>(objectFieldModalDeletionModalUrl);

		if (showModalResponse.showDeletionModal) {
			setShowDeletionObjectFieldModal(
				showModalResponse.showDeletionModal
			);
		}
		else {
			setShowDeletionNotAllowedModal({
				deleteLastPublishedObjectDefinitionObjectField:
					showModalResponse.deleteLastPublishedObjectDefinitionObjectField,
				deleteObjectFieldObjectValidationRuleSetting:
					showModalResponse.deleteObjectFieldObjectValidationRuleSetting,
				showModal:
					!showModalResponse.deleteObjectFieldObjectValidationRuleSetting ||
					!showModalResponse.deleteLastPublishedObjectDefinitionObjectField,
			});
		}
	};

	const onSubmit = async (editedObjectField?: Partial<ObjectField>) => {
		const validationErrors = handleValidate(editedObjectField ?? values);

		if (validationErrors.defaultValue) {
			if (errors.defaultValue) {
				openToast({
					message: Liferay.Language.get(
						'please-fill-out-all-required-fields'
					),
					type: 'danger',
				});
			}
		}

		if (!Object.keys(validationErrors).length) {
			let objectField: Partial<ObjectField>;

			if (!editedObjectField) {
				objectField = values;
			}
			else {
				objectField = editedObjectField;
			}

			delete objectField.defaultValue;
			delete objectField.listTypeDefinitionId;
			delete objectField.system;

			try {
				const updatedObjectFieldResponse = await API.save<ObjectField>({
					item: objectField,
					returnValue: true,
					url: `/o/object-admin/v1.0/object-fields/${objectField.id}`,
				});

				if (
					selectedObjectDefinitionNode &&
					updatedObjectFieldResponse
				) {
					dispatch({
						payload: {
							objectDefinitionNodes: nodes,
							objectRelationshipEdges: edges,
							selectedObjectDefinitionNode,
							updatedObjectField: updatedObjectFieldResponse as ObjectField,
						},
						type: TYPES.UPDATE_OBJECT_FIELD_NODE_ROW,
					});

					dispatch({
						payload: {
							updatedShowChangesSaved: true,
						},
						type: TYPES.SET_SHOW_CHANGES_SAVED,
					});
				}
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
			if (selectedObjectField) {
				const objectFieldResponse = await API.getObjectField(
					selectedObjectField?.id as number
				);

				setValues(objectFieldResponse);
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (errors.defaultValue) {
			openToast({
				message: Liferay.Language.get(
					'please-fill-out-all-required-fields'
				),
				type: 'danger',
			});
		}
	}, [errors]);

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>{Liferay.Language.get('field-details')}</span>

				<div className="lfr-objects__model-builder-right-sidebar-definition-node-title-buttons-container">
					{!values.system &&
						values.businessType !== 'Relationship' && (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('delete')}
								className="lfr-objects__model-builder-right-sidebar-definition-node-title-delete-button"
								displayType="secondary"
								onClick={() =>
									handleTriggerDeleteObjectFieldModal()
								}
								symbol="trash"
								title={Liferay.Language.get('delete')}
							/>
						)}
				</div>
			</div>

			<div>
				<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
					<EditObjectFieldContent
						baseResourceURL={baseResourceURL}
						containerWrapper={ClayPanel}
						creationLanguageId={
							selectedObjectDefinitionNode?.data
								?.defaultLanguageId ?? 'en_US'
						}
						errors={errors}
						filterOperators={filterOperators}
						handleChange={handleChange}
						isApproved={
							selectedObjectDefinitionNode?.data?.status.label ===
							'approved'
						}
						isDefaultStorageType={
							selectedObjectDefinitionNode?.data?.storageType ===
								'default' ?? true
						}
						learnResources={objectWebLearnResources}
						modelBuilder
						objectDefinitionExternalReferenceCode={
							selectedObjectDefinitionNode?.data
								?.externalReferenceCode ?? ''
						}
						onSubmit={onSubmit}
						readOnly={
							!selectedObjectDefinitionNode?.data
								?.hasObjectDefinitionUpdateResourcePermission ??
							false
						}
						setValues={setValues}
						values={values}
						workflowStatusJSONArray={workflowStatusJSONArray}
					/>
				</div>
			</div>

			{showDeletionObjectFieldModal && (
				<ModalDeleteObjectField
					objectField={values as ObjectField}
					onAfterSubmit={() => {
						if (
							selectedObjectField &&
							selectedObjectDefinitionNode
						) {
							dispatch({
								payload: {
									objectDefinitionNodes: nodes,
									objectRelationshipEdges: edges,
									selectedObjectDefinitionNode,
									selectedObjectField,
								},
								type: TYPES.DELETE_OBJECT_FIELD,
							});
						}
					}}
					setModalVisibility={setShowDeletionObjectFieldModal}
				/>
			)}

			{showDeletionNotAllowedModal?.showModal && (
				<ModalDeletionNotAllowed
					content={
						showDeletionNotAllowedModal?.deleteObjectFieldObjectValidationRuleSetting ? (
							<Text>
								{sub(
									Liferay.Language.get(
										'x-is-the-only-field-of-the-published-object-definition-and-cannot-be-deleted'
									),
									`${getLocalizableLabel(
										selectedObjectDefinitionNode?.data
											?.defaultLanguageId as Liferay.Language.Locale,
										values.label,
										values.name
									)}`
								)}
							</Text>
						) : (
							<Text>
								{Liferay.Language.get(
									'this-field-cannot-be-deleted-as-it-is-used-in-a-unique-composite-key-validation'
								)}
							</Text>
						)
					}
					onVisibilityChange={() =>
						setShowDeletionNotAllowedModal({
							...showDeletionNotAllowedModal,
							showModal: false,
						})
					}
				/>
			)}
		</>
	);
}
