/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import {TDeletionType} from '../ObjectRelationship/EditRelationship';
import {ModalAddObjectDefinition} from '../ViewObjectDefinitions/ModalAddObjectDefinition';
import {ModalEditObjectFolder} from '../ViewObjectDefinitions/ModalEditObjectFolder';
import Diagram from './Diagram/Diagram';
import Header from './Header/Header';
import LeftSidebar from './LeftSidebar/LeftSidebar';
import {useObjectFolderContext} from './ModelBuilderContext/objectFolderContext';
import {TYPES} from './ModelBuilderContext/typesEnum';
import {RightSideBar} from './RightSidebar/index';

interface EditObjectFolder {
	companyKeyValuePair: KeyValuePair[];
	deletionTypes: TDeletionType[];
	objectFolderName: string;
	siteKeyValuePair: KeyValuePair[];
}
export default function EditObjectFolder({
	companyKeyValuePair,
	deletionTypes,
	objectFolderName,
	siteKeyValuePair,
}: EditObjectFolder) {
	const [
		{rightSidebarType, selectedObjectFolder, storages, viewApiURL},
		dispatch,
	] = useObjectFolderContext();

	const [showModal, setShowModal] = useState<ModelBuilderModals>({
		addObjectDefinition: false,
		addObjectFolder: false,
		addObjectRelationship: false,
		deleteObjectDefinition: false,
		deleteObjectFolder: false,
		editObjectDefinitionERC: false,
		editObjectFolder: false,
		moveObjectDefinition: false,
		redirectEditObjectDefinition: false,
	});

	useEffect(() => {
		const makeFetch = async () => {
			const objectFolderResponse = await API.getAllObjectFolders();

			const currentObjectFolder = objectFolderResponse.find(
				(objectFolder) => objectFolder.name === objectFolderName
			) as ObjectFolder;

			const objectFoldersWithObjectDefinitions: ObjectFolder[] = await Promise.all(
				objectFolderResponse.map(async (objectFolder) => {
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
								const response = await API.getObjectDefinitionByExternalReferenceCode(
									objectFolderItem.objectDefinitionExternalReferenceCode
								);

								linkedObjectDefinitions.push(response);
							})
					);

					const updateObjectFoldersLinkedObjectDefinition = ({
						linked,
						objectDefinitions,
					}: {
						linked: boolean;
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
									hasSelfRelationships: false,
									linked,
									nodeSelected: false,
									objectFields: objectDefinition.objectFields.map(
										(field) =>
											({
												businessType:
													field.businessType,
												externalReferenceCode:
													field.externalReferenceCode,
												label: getLocalizableLabel(
													objectDefinition.defaultLanguageId,
													field.label,
													field.name
												),
												name: field.name,
												primaryKey: field.name === 'id',
												required: field.required,
												selected: false,
											} as ObjectFieldNode)
									),
								});
							}
						});
					};

					updateObjectFoldersLinkedObjectDefinition({
						linked: false,
						objectDefinitions: objectDefinitionsFilteredByObjectFolder,
					});

					updateObjectFoldersLinkedObjectDefinition({
						linked: true,
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
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			{showModal.addObjectDefinition && (
				<ModalAddObjectDefinition
					apiURL={viewApiURL}
					handleOnClose={() =>
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							addObjectDefinition: false,
						}))
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
							type: TYPES.ADD_NEW_NODE_TO_OBJECT_FOLDER,
						});
					}}
					reload={false}
					storages={storages}
				/>
			)}

			{showModal.editObjectFolder && (
				<ModalEditObjectFolder
					externalReferenceCode={
						selectedObjectFolder.externalReferenceCode
					}
					handleOnClose={() => {
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							editFolder: false,
						}));
					}}
					initialLabel={selectedObjectFolder.label}
					name={selectedObjectFolder.name}
					objectFolderID={selectedObjectFolder.id}
				/>
			)}

			<Header
				hasDraftObjectDefinitions={false}
				objectFolder={selectedObjectFolder}
				setShowModal={setShowModal}
			/>
			<div className="lfr-objects__model-builder-diagram-container">
				<LeftSidebar
					selectedObjectFolderName={selectedObjectFolder.name}
					setShowModal={setShowModal}
				/>

				<Diagram setShowModal={setShowModal} />

				<RightSideBar.Root>
					{rightSidebarType === 'empty' && <RightSideBar.Empty />}

					{rightSidebarType === 'objectDefinitionDetails' && (
						<RightSideBar.ObjectDefinitionDetails
							companyKeyValuePair={companyKeyValuePair}
							siteKeyValuePair={siteKeyValuePair}
						/>
					)}

					{rightSidebarType === 'objectRelationshipDetails' && (
						<RightSideBar.ObjectRelationshipDetails
							deletionTypes={deletionTypes}
						/>
					)}
				</RightSideBar.Root>
			</div>
		</>
	);
}
