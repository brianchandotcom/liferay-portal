/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';
import {
	Edge,
	Elements,
	FlowElement,
	Node,
	isEdge,
	isNode,
} from 'react-flow-renderer';

import {Scope} from '../ObjectDetails/EditObjectDetails';
import {ModalAddObjectDefinition} from '../ViewObjectDefinitions/ModalAddObjectDefinition';
import {ModalEditObjectFolder} from '../ViewObjectDefinitions/ModalEditObjectFolder';
import {getUpdatedModelBuilderStructurePayload} from '../ViewObjectDefinitions/objectDefinitionUtil';
import Diagram from './Diagram/Diagram';
import EditObjectFolderHeader from './EditObjectFolderHeader/EditObjectFolderHeader';
import {ModalPublishObjectDefinitions} from './EditObjectFolderHeader/ModalPublishObjectDefinitions';
import EmptyObjectFolderCard from './EmptyObjectFolderCard/EmptyObjectFolderCard';
import LeftSidebar from './LeftSidebar/LeftSidebar';
import {useObjectFolderContext} from './ModelBuilderContext/objectFolderContext';
import {TYPES} from './ModelBuilderContext/typesEnum';
import {RightSideBar} from './RightSidebar/index';

import './EditObjectFolder.scss';

import {
	API,
	ModalEditObjectDefinitionExternalReferenceCode,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';

import {formatActionURL} from '../../utils/fds';
import {ModalAddObjectField} from '../ObjectField/ModalAddObjectField';
import {ModalAddObjectRelationship} from '../ObjectRelationship/ModalAddObjectRelationship';
import {ModalDeleteObjectDefinition} from '../ViewObjectDefinitions/ModalDeleteObjectDefinition';
import {RedirectToEditObjectDetailsModal} from './ObjectDefinitionNode/RedirectToEditObjectDetailsModal';
import {ObjectRelationshipEdgeData} from './types';

interface EditObjectFolder {
	companies: Scope[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	sites: Scope[];
}

export default function EditObjectFolder({
	companies,
	objectRelationshipDeletionTypes,
	sites,
}: EditObjectFolder) {
	const [
		{
			baseResourceURL,
			deleteObjectDefinition,
			editObjectDefinitionURL,
			elements,
			isLoadingObjectFolder,
			modelBuilderModals,
			objectDefinitionsStorageTypes,
			objectFolderName,
			rightSidebarType,
			selectedObjectDefinitionNode,
			selectedObjectFolder,
			showChangesSaved,
		},
		dispatch,
	] = useObjectFolderContext();

	const [
		objectRelationshipParameterRequired,
		setObjectRelationshipParameterRequired,
	] = useState(false);

	const edges = elements.filter((element) => isEdge(element)) as Edge<
		ObjectRelationshipEdgeData
	>[];

	const nodes = elements.filter((element) => isNode(element)) as Node<
		ObjectDefinitionNodeData
	>[];

	const handleDeleteObjectDefinition = (
		deleteObjectDefinition: DeletedObjectDefinition
	) => {
		dispatch({
			payload: {
				newDeleteObjectDefinition: deleteObjectDefinition,
			},
			type: TYPES.SET_DELETE_OBJECT_DEFINITION,
		});
	};

	const updateModelBuilderStructure = async (
		newObjectRelationshipId: number
	) => {
		const payload = await getUpdatedModelBuilderStructurePayload(
			selectedObjectFolder.name
		);

		dispatch({
			payload: {
				...payload,
				rightSidebarType: 'objectRelationshipDetails',
				selectedObjectRelationshipId: newObjectRelationshipId,
			},
			type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE,
		});

		dispatch({
			payload: {
				selectedObjectRelationshipId: newObjectRelationshipId,
			},
			type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
		});
	};

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedObjectDefinitionNode) {
				const url = createResourceURL(baseResourceURL, {
					objectDefinitionId: selectedObjectDefinitionNode.data?.id,
					p_p_resource_id:
						'/object_definitions/get_object_relationship_info',
				}).href;

				const {parameterRequired} = await API.fetchJSON<{
					parameterRequired: boolean;
				}>(url);

				setObjectRelationshipParameterRequired(parameterRequired);
			}
		};

		makeFetch();
	}, [baseResourceURL, selectedObjectDefinitionNode]);

	useEffect(() => {
		dispatch({
			payload: {
				isLoadingObjectFolder: true,
			},
			type: TYPES.SET_LOADING_OBJECT_FOLDER,
		});

		const updateModelBuilderStructure = async () => {
			const payload = await getUpdatedModelBuilderStructurePayload(
				objectFolderName
			);

			dispatch({
				payload,
				type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE,
			});

			dispatch({
				payload: {
					isLoadingObjectFolder: false,
				},
				type: TYPES.SET_LOADING_OBJECT_FOLDER,
			});
		};

		updateModelBuilderStructure();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFolderName]);

	useEffect(() => {
		if (showChangesSaved) {
			setTimeout(() => {
				dispatch({
					payload: {updatedShowChangesSaved: false},
					type: TYPES.SET_SHOW_CHANGES_SAVED,
				});
			}, 5000);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [showChangesSaved]);

	return (
		<>
			{modelBuilderModals.addObjectDefinition && (
				<ModalAddObjectDefinition
					handleOnClose={() =>
						dispatch({
							payload: {
								modelBuilderModals: {
									...modelBuilderModals,
									addObjectDefinition: false,
								},
							},
							type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
						})
					}
					objectDefinitionsStorageTypes={
						objectDefinitionsStorageTypes
					}
					objectFolderExternalReferenceCode={
						selectedObjectFolder.externalReferenceCode
					}
					onAfterSubmit={(newObjectDefinition) => {
						dispatch({
							payload: {
								newObjectDefinition,
								objectDefinitionNodes: nodes,
								selectedObjectFolderName:
									selectedObjectFolder.name,
							},
							type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER,
						});
					}}
					reload={false}
				/>
			)}

			{modelBuilderModals.addObjectField &&
				selectedObjectDefinitionNode?.data && (
					<ModalAddObjectField
						baseResourceURL={baseResourceURL}
						creationLanguageId={
							selectedObjectDefinitionNode.data.defaultLanguageId
						}
						objectDefinitionExternalReferenceCode={
							selectedObjectDefinitionNode.data
								.externalReferenceCode
						}
						objectDefinitionName={
							selectedObjectDefinitionNode.data.name
						}
						onAfterSubmit={(newObjectField) => {
							dispatch({
								payload: {
									newObjectField,
									objectDefinitionExternalReferenceCode: selectedObjectDefinitionNode
										?.data?.externalReferenceCode as string,
									objectDefinitionNodes: nodes,
									objectRelationshipEdges: edges,
									selectedObjectDefinitionNode,
								},
								type: TYPES.ADD_OBJECT_FIELD,
							});

							openToast({
								message: Liferay.Language.get(
									'the-field-was-successfully-added'
								),
								type: 'success',
							});

							dispatch({
								payload: {
									modelBuilderModals: {
										...modelBuilderModals,
										addObjectField: false,
									},
								},
								type:
									TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							});

							dispatch({
								payload: {
									objectDefinitionExternalReferenceCode: selectedObjectDefinitionNode
										.data?.externalReferenceCode as string,
									showAllObjectFields: selectedObjectDefinitionNode
										.data?.showAllObjectFields as boolean,
								},
								type: TYPES.SET_SHOW_ALL_OBJECT_FIELDS,
							});
						}}
						setVisibility={() =>
							dispatch({
								payload: {
									modelBuilderModals: {
										...modelBuilderModals,
										addObjectField: false,
									},
								},
								type:
									TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							})
						}
					/>
				)}

			{modelBuilderModals.addObjectRelationship &&
				selectedObjectDefinitionNode?.data && (
					<ModalAddObjectRelationship
						baseResourceURL={baseResourceURL}
						handleOnClose={() => {
							dispatch({
								payload: {
									modelBuilderModals: {
										...modelBuilderModals,
										addObjectRelationship: false,
									},
								},
								type:
									TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							});
						}}
						objectDefinitionExternalReferenceCode1={
							selectedObjectDefinitionNode.data
								.externalReferenceCode
						}
						objectRelationshipParameterRequired={
							objectRelationshipParameterRequired
						}
						onAfterSubmit={(newObjectRelationshipId: number) =>
							updateModelBuilderStructure(newObjectRelationshipId)
						}
						reload={false}
					/>
				)}

			{modelBuilderModals.deleteObjectDefinition &&
				deleteObjectDefinition && (
					<ModalDeleteObjectDefinition
						handleDeleteObjectDefinition={() =>
							handleDeleteObjectDefinition
						}
						handleOnClose={() => {
							dispatch({
								payload: {
									modelBuilderModals: {
										...modelBuilderModals,
										deleteObjectDefinition: false,
									},
								},
								type:
									TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							});
						}}
						objectDefinition={deleteObjectDefinition}
					/>
				)}

			{modelBuilderModals.editObjectDefinitionExternalReferenceCode &&
				selectedObjectDefinitionNode?.data && (
					<ModalEditObjectDefinitionExternalReferenceCode
						handleOnClose={() => {
							dispatch({
								payload: {
									modelBuilderModals: {
										...modelBuilderModals,
										editObjectDefinitionExternalReferenceCode: false,
									},
								},
								type:
									TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							});
						}}
						helpMessage={Liferay.Language.get(
							'unique-key-for-referencing-the-object-definition'
						)}
						objectDefinitionExternalReferenceCode={
							selectedObjectDefinitionNode.data
								.externalReferenceCode
						}
						onGetEntity={() =>
							API.getObjectDefinitionById(
								selectedObjectDefinitionNode.data?.id as number
							)
						}
						onObjectDefinitionExternalReferenceCodeChange={(
							externalReferenceCode: string
						) => {
							const updatedElements = elements.map((element) => {
								if (
									isNode(element) &&
									(element as Node<ObjectDefinitionNodeData>)
										.data?.id ===
										selectedObjectDefinitionNode.data?.id
								) {
									return {
										...element,
										data: {
											...element.data,
											externalReferenceCode,
										},
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
						}}
						saveURL={`/o/object-admin/v1.0/object-definitions/${selectedObjectDefinitionNode.data.id}`}
					/>
				)}

			{modelBuilderModals.editObjectFolder && (
				<ModalEditObjectFolder
					externalReferenceCode={
						selectedObjectFolder.externalReferenceCode
					}
					handleOnClose={() => {
						dispatch({
							payload: {
								modelBuilderModals: {
									...modelBuilderModals,
									editObjectFolder: false,
								},
							},
							type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
						});
					}}
					id={selectedObjectFolder.id}
					initialLabel={selectedObjectFolder.label}
					name={selectedObjectFolder.name}
				/>
			)}

			{modelBuilderModals.publishObjectDefinitions && (
				<ModalPublishObjectDefinitions
					disableAutoClose={true}
					dispatch={dispatch}
					elements={elements}
					handleOnClose={() => {
						dispatch({
							payload: {
								modelBuilderModals: {
									...modelBuilderModals,
									publishObjectDefinitions: false,
								},
							},
							type: TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
						});
					}}
				/>
			)}

			{modelBuilderModals.redirectToEditObjectDefinitionDetails &&
				selectedObjectDefinitionNode?.data && (
					<RedirectToEditObjectDetailsModal
						handleOnClose={() => {
							dispatch({
								payload: {
									modelBuilderModals: {
										...modelBuilderModals,
										redirectToEditObjectDefinitionDetails: false,
									},
								},
								type:
									TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS,
							});
						}}
						viewObjectDetailsURL={formatActionURL(
							editObjectDefinitionURL,
							selectedObjectDefinitionNode.data.id
						)}
					/>
				)}

			<EditObjectFolderHeader
				hasDraftObjectDefinitions={elements.some(
					(element) =>
						(element as FlowElement<ObjectDefinitionNodeData>).data
							?.status?.code === 2
				)}
				selectedObjectFolder={selectedObjectFolder}
			/>

			<div className="lfr-objects__model-builder-content">
				<LeftSidebar />

				{!elements.length && !isLoadingObjectFolder && (
					<EmptyObjectFolderCard />
				)}

				<Diagram />

				<RightSideBar.Root>
					{rightSidebarType === 'empty' && <RightSideBar.Empty />}

					{rightSidebarType === 'objectDefinitionDetails' && (
						<RightSideBar.ObjectDefinitionDetails
							companies={companies}
							sites={sites}
						/>
					)}

					{rightSidebarType === 'objectFieldDetails' && (
						<RightSideBar.ObjectFieldDetails />
					)}

					{rightSidebarType === 'objectRelationshipDetails' && (
						<RightSideBar.ObjectRelationshipDetails
							objectRelationshipDeletionTypes={
								objectRelationshipDeletionTypes
							}
						/>
					)}
				</RightSideBar.Root>
			</div>
		</>
	);
}
