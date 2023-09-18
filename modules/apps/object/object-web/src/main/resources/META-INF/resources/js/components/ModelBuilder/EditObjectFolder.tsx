/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';
import {FlowElement} from 'react-flow-renderer';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import {ModalAddObjectDefinition} from '../ViewObjectDefinitions/ModalAddObjectDefinition';
import {ModalEditObjectFolder} from '../ViewObjectDefinitions/ModalEditObjectFolder';
import Diagram from './Diagram/Diagram';
import EditObjectFolderHeader from './EditObjectFolderHeader/EditObjectFolderHeader';
import {ModalPublishObjectDefinitions} from './EditObjectFolderHeader/ModalPublishObjectDefinitions';
import LeftSidebar from './LeftSidebar/LeftSidebar';
import {useObjectFolderContext} from './ModelBuilderContext/objectFolderContext';
import {TYPES} from './ModelBuilderContext/typesEnum';
import {RightSideBar} from './RightSidebar/index';

interface EditObjectFolder {
	companyKeyValuePairs: KeyValuePair[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	siteKeyValuePairs: KeyValuePair[];
}

export default function EditObjectFolder({
	companyKeyValuePairs,
	objectRelationshipDeletionTypes,
	siteKeyValuePairs,
}: EditObjectFolder) {
	const [
		{
			elements,
			objectDefinitionsStorageTypes,
			objectFolderName,
			rightSidebarType,
			selectedObjectFolder,
		},
		dispatch,
	] = useObjectFolderContext();

	const [showModal, setShowModal] = useState<ModelBuilderModals>({
		addObjectDefinition: false,
		addObjectFolder: false,
		addObjectRelationship: false,
		deleteObjectDefinition: false,
		deleteObjectFolder: false,
		editObjectDefinitionExternalReferenceCode: false,
		editObjectFolder: false,
		moveObjectDefinition: false,
		publishObjectDefinitions: false,
		redirectToEditObjectDefinitionDetails: false,
	});

	useEffect(() => {
		dispatch({
			payload: {
				isLoadingObjectFolder: true,
			},
			type: TYPES.SET_LOADING_OBJECT_FOLDER,
		});

		const makeFetch = async () => {
			const objectFolders = await API.getAllObjectFolders();

			const currentObjectFolder = objectFolders.find(
				(objectFolder) => objectFolder.name === objectFolderName
			) as ObjectFolder;

			const objectFoldersWithObjectDefinitions: ObjectFolder[] = await Promise.all(
				objectFolders.map(async (objectFolder) => {
					const objectFolderWithObjectDefinitions: ObjectDefinitionNodeData[] = [];

					const objectDefinitionsFilteredByObjectFolder = await API.getObjectDefinitions(
						`filter=objectFolderExternalReferenceCode eq '${objectFolder.externalReferenceCode}'`
					);

					const linkedObjectDefinitions: ObjectDefinition[] = [];

					await Promise.all(
						objectFolder.objectFolderItems
							.filter(
								(objectFolderItem) =>
									objectFolderItem.linkedObjectDefinition
							)
							.map(async (objectFolderItem) => {
								linkedObjectDefinitions.push(
									await API.getObjectDefinitionByExternalReferenceCode(
										objectFolderItem.objectDefinitionExternalReferenceCode
									)
								);
							})
					);

					const updateObjectFolderObjectDefinitions = ({
						linkedObjectDefinition,
						objectDefinitions,
					}: {
						linkedObjectDefinition: boolean;
						objectDefinitions: ObjectDefinition[];
					}) => {
						objectDefinitions.forEach((objectDefinition) => {
							const objectFolderItem = objectFolder.objectFolderItems.find(
								(objectFolderItem) =>
									objectFolderItem.objectDefinitionExternalReferenceCode ===
									objectDefinition.externalReferenceCode
							);

							if (objectFolderItem) {
								objectFolderWithObjectDefinitions.push({
									...objectDefinition,
									hasObjectDefinitionDeleteResourcePermission: !!objectDefinition
										.actions.delete,
									hasObjectDefinitionManagePermissionsResourcePermission: !!objectDefinition
										.actions.permissions,
									hasObjectDefinitionUpdateResourcePermission: !!objectDefinition
										.actions.update,
									hasObjectDefinitionViewResourcePermission: !!objectDefinition
										.actions.get,
									hasSelfObjectRelationships: false,
									linkedObjectDefinition,
									objectFields: objectDefinition.objectFields.map(
										({
											businessType,
											externalReferenceCode,
											label,
											name,
											required,
										}) =>
											({
												businessType,
												externalReferenceCode,
												label: getLocalizableLabel(
													objectDefinition.defaultLanguageId,
													label,
													name
												),
												name,
												primaryKey: name === 'id',
												required,
												selected: false,
											} as ObjectFieldNode)
									),
									selected: false,
								});
							}
						});
					};

					updateObjectFolderObjectDefinitions({
						linkedObjectDefinition: false,
						objectDefinitions: objectDefinitionsFilteredByObjectFolder,
					});

					updateObjectFolderObjectDefinitions({
						linkedObjectDefinition: true,
						objectDefinitions: linkedObjectDefinitions,
					});

					return {
						...objectFolder,
						objectDefinitions: objectFolderWithObjectDefinitions,
					};
				})
			);

			dispatch({
				payload: {
					objectFolders: objectFoldersWithObjectDefinitions,
					selectedObjectFolder: currentObjectFolder,
				},
				type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE,
			});

			dispatch({
				payload: {
					isLoadingObjectFolder: false,
				},
				type: TYPES.SET_LOADING_OBJECT_FOLDER,
			});
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFolderName]);

	return (
		<>
			{showModal.addObjectDefinition && (
				<ModalAddObjectDefinition
					handleOnClose={() =>
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							addObjectDefinition: false,
						}))
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
								selectedObjectFolderName:
									selectedObjectFolder.name,
							},
							type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER,
						});
					}}
					reload={false}
				/>
			)}

			{showModal.editObjectFolder && (
				<ModalEditObjectFolder
					externalReferenceCode={
						selectedObjectFolder.externalReferenceCode
					}
					handleOnClose={() => {
						setShowModal((previousState) => ({
							...previousState,
							editObjectFolder: false,
						}));
					}}
					id={selectedObjectFolder.id}
					initialLabel={selectedObjectFolder.label}
					name={selectedObjectFolder.name}
				/>
			)}

			{showModal.publishObjectDefinitions && (
				<ModalPublishObjectDefinitions
					disableAutoClose={false}
					dispatch={dispatch}
					elements={elements}
					handleOnClose={() => {
						setShowModal((previousState) => ({
							...previousState,
							publishObjectDefinitions: false,
						}));
					}}
				/>
			)}

			<EditObjectFolderHeader
				hasDraftObjectDefinitions={elements.some(
					(element) =>
						(element as FlowElement<ObjectDefinitionNodeData>).data
							?.status?.code === 2
				)}
				selectedObjectFolder={selectedObjectFolder}
				setShowModal={setShowModal}
			/>
			<div className="lfr-objects__model-builder-diagram-container">
				<LeftSidebar setShowModal={setShowModal} />

				<Diagram setShowModal={setShowModal} />

				<RightSideBar.Root>
					{rightSidebarType === 'empty' && <RightSideBar.Empty />}

					{rightSidebarType === 'objectDefinitionDetails' && (
						<RightSideBar.ObjectDefinitionDetails
							companyKeyValuePairs={companyKeyValuePairs}
							siteKeyValuePairs={siteKeyValuePairs}
						/>
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
