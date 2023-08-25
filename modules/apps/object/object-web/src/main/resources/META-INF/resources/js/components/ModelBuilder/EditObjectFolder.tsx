/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import {TDeletionType} from '../ObjectRelationship/EditRelationship';
import {ModalAddObjectDefinition} from '../ViewObjectDefinitions/ModalAddObjectDefinition';
import {ModalEditFolder} from '../ViewObjectDefinitions/ModalEditFolder';
import {ViewObjectDefinitionsModals} from '../ViewObjectDefinitions/ViewObjectDefinitions';
import Diagram from './Diagram/Diagram';
import Header from './Header/Header';
import LeftSidebar from './LeftSidebar/LeftSidebar';
import {useFolderContext} from './ModelBuilderContext/objectFolderContext';
import {TYPES} from './ModelBuilderContext/typesEnum';
import {RightSideBar} from './RightSidebar/index';

interface EditObjectFolder {
	companyKeyValuePair: KeyValuePair[];
	deletionTypes: TDeletionType[];
	folderName: string;
	siteKeyValuePair: KeyValuePair[];
}
export default function EditObjectFolder({
	companyKeyValuePair,
	deletionTypes,
	folderName,
	siteKeyValuePair,
}: EditObjectFolder) {
	const [
		{rightSidebarType, selectedFolder, storages, viewApiURL},
		dispatch,
	] = useFolderContext();

	const [showModal, setShowModal] = useState<ViewObjectDefinitionsModals>({
		addFolder: false,
		addObjectDefinition: false,
		bindToRootObjectDefinition: false,
		deleteFolder: false,
		deleteObjectDefinition: false,
		deletionNotAllowed: false,
		editERC: false,
		editFolder: false,
		moveObjectDefinition: false,
		redirectEditObjectDefinition: false,
		unbindFromRootObjectDefinition: false,
	});

	useEffect(() => {
		const makeFetch = async () => {
			const folderResponse = await API.getAllFolders();

			const currentFolder = folderResponse.find(
				(folder) => folder.name === folderName
			) as ObjectFolder;

			const objectFoldersWithDefinitions: ObjectFolder[] = await Promise.all(
				folderResponse.map(async (folder) => {
					const folderDefinitions: ObjectDefinitionNodeData[] = [];

					const folderDefinitionsResponse = await API.getObjectDefinitions(
						`filter=objectFolderExternalReferenceCode eq '${folder.externalReferenceCode}'`
					);

					const linkedObjectDefinitions: ObjectDefinition[] = [];

					await Promise.all(
						folder.objectFolderItems
							.filter(
								(folderItem) =>
									folderItem.linkedObjectDefinition
							)
							.map(async (folderItem) => {
								const response = await API.getObjectDefinitionByExternalReferenceCode(
									folderItem.objectDefinitionExternalReferenceCode
								);

								linkedObjectDefinitions.push(response);
							})
					);

					const updateFoldersLinkedDefinitions = ({
						isLinked,
						objectDefinitions,
					}: {
						isLinked: boolean;
						objectDefinitions: ObjectDefinition[];
					}) => {
						objectDefinitions.forEach((objectDefinition) => {
							const folderItem = folder.objectFolderItems.find(
								(folderItem) =>
									folderItem.objectDefinitionExternalReferenceCode ===
									objectDefinition.externalReferenceCode
							);

							if (folderItem) {
								folderDefinitions.push({
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
									label: getLocalizableLabel(
										objectDefinition.defaultLanguageId,
										objectDefinition.label,
										objectDefinition.name
									),
									linkedDefinition: isLinked,
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

					updateFoldersLinkedDefinitions({
						isLinked: false,
						objectDefinitions: folderDefinitionsResponse,
					});

					updateFoldersLinkedDefinitions({
						isLinked: true,
						objectDefinitions: linkedObjectDefinitions,
					});

					return {
						...folder,
						definitions: folderDefinitions,
					};
				})
			);

			dispatch({
				payload: {
					objectFolders: objectFoldersWithDefinitions,
					selectedFolder: currentFolder,
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
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								addObjectDefinition: false,
							})
						)
					}
					objectFolderExternalReferenceCode={
						selectedFolder.externalReferenceCode
					}
					onAfterSubmit={(newObjectDefinition) => {
						dispatch({
							payload: {
								newObjectDefinition,
								selectedFolderName: selectedFolder.name,
							},
							type: TYPES.ADD_NEW_NODE_TO_FOLDER,
						});
					}}
					reload={false}
					storages={storages}
				/>
			)}

			{showModal.editFolder && (
				<ModalEditFolder
					externalReferenceCode={selectedFolder.externalReferenceCode}
					folderID={selectedFolder.id}
					handleOnClose={() => {
						setShowModal(
							(previousState: ViewObjectDefinitionsModals) => ({
								...previousState,
								editFolder: false,
							})
						);
					}}
					initialLabel={selectedFolder.label}
					name={selectedFolder.name}
				/>
			)}

			<Header
				folder={selectedFolder}
				hasDraftObjectDefinitions={false}
				setShowModal={setShowModal}
			/>
			<div className="lfr-objects__model-builder-diagram-container">
				<LeftSidebar
					selectedFolderName={selectedFolder.name}
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
