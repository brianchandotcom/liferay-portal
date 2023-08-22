/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import {TDeletionType} from '../ObjectRelationship/EditRelationship';
import {ModalAddObjectDefinition} from '../ViewObjectDefinitions/ModalAddObjectDefinition';
import Diagram from './Diagram/Diagram';
import Header from './Header/Header';
import LeftSidebar from './LeftSidebar/LeftSidebar';
import {useFolderContext} from './ModelBuilderContext/objectFolderContext';
import {TYPES} from './ModelBuilderContext/typesEnum';
import {RightSideBar} from './RightSidebar/index';

interface EditObjectFolder {
	companyKeyValuePair: KeyValuePair[];
	deletionTypes: TDeletionType[];
	siteKeyValuePair: KeyValuePair[];
}
export default function EditObjectFolder({
	companyKeyValuePair,
	deletionTypes,
	siteKeyValuePair,
}: EditObjectFolder) {
	const [
		{rightSidebarType, selectedFolderERC, storages, viewApiURL},
		dispatch,
	] = useFolderContext();
	const [showModal, setShowModal] = useState(false);

	const [selectedFolderName, setSelectedFolderName] = useState('');

	useEffect(() => {
		const makeFetch = async () => {
			const folderResponse = await API.getAllFolders();

			setSelectedFolderName(
				folderResponse.find(
					(folder) =>
						folder.externalReferenceCode === selectedFolderERC
				)!.name
			);

			const objectFoldersWithDefinitions: ObjectFolder[] = await Promise.all(
				folderResponse.map(async (folder) => {
					const folderDefinitions: ObjectDefinitionNodeData[] = [];

					const folderDefinitionsResponse = await API.getObjectDefinitions(
						`filter=objectFolderExternalReferenceCode eq '${folder.externalReferenceCode}'`
					);

					const linkedObjectDefinitions: ObjectDefinition[] = [];

					await Promise.all(
						folder.objectFolderItems
							.filter((folderItem) => folderItem.linkedDefinition)
							.map(async (folderItem) => {
								const response = await API.getObjectDefinitionByExternalReferenceCode(
									folderItem.objectDefinitionExternalReferenceCode
								);

								linkedObjectDefinitions.push(response);
							})
					);

					folderDefinitionsResponse.forEach((folderDefinition) => {
						const folderItem = folder.objectFolderItems.find(
							(folderItem) =>
								folderItem.objectDefinitionExternalReferenceCode ===
								folderDefinition.externalReferenceCode
						);

						if (folderItem) {
							folderDefinitions.push({
								...folderDefinition,
								hasObjectDefinitionDeleteResourcePermission: !!folderDefinition
									.actions.delete,
								hasObjectDefinitionManagePermissionsResourcePermission: !!folderDefinition
									.actions.permissions,
								hasObjectDefinitionUpdateResourcePermission: !!folderDefinition
									.actions.update,
								hasObjectDefinitionViewResourcePermission: !!folderDefinition
									.actions.get,
								hasSelfRelationships: false,
								label: getLocalizableLabel(
									folderDefinition.defaultLanguageId,
									folderDefinition.label,
									folderDefinition.name
								),
								linkedDefinition: false,
								nodeSelected: false,
								objectFields: folderDefinition.objectFields.map(
									(field) =>
										({
											businessType: field.businessType,
											externalReferenceCode:
												field.externalReferenceCode,
											label: getLocalizableLabel(
												folderDefinition.defaultLanguageId,
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

					linkedObjectDefinitions.forEach((folderDefinition) => {
						const folderItem = folder.objectFolderItems.find(
							(folderItem) =>
								folderItem.objectDefinitionExternalReferenceCode ===
								folderDefinition.externalReferenceCode
						);

						if (folderItem) {
							folderDefinitions.push({
								...folderDefinition,
								hasObjectDefinitionDeleteResourcePermission: !!folderDefinition
									.actions.delete,
								hasObjectDefinitionManagePermissionsResourcePermission: !!folderDefinition
									.actions.permissions,
								hasObjectDefinitionUpdateResourcePermission: !!folderDefinition
									.actions.update,
								hasObjectDefinitionViewResourcePermission: !!folderDefinition
									.actions.get,
								hasSelfRelationships: false,
								label: getLocalizableLabel(
									folderDefinition.defaultLanguageId,
									folderDefinition.label,
									folderDefinition.name
								),
								linkedDefinition: true,
								nodeSelected: false,
								objectFields: folderDefinition.objectFields.map(
									(field) =>
										({
											businessType: field.businessType,
											externalReferenceCode:
												field.externalReferenceCode,
											label: getLocalizableLabel(
												folderDefinition.defaultLanguageId,
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

					return {
						...folder,
						definitions: folderDefinitions,
					};
				})
			);

			dispatch({
				payload: {objectFolders: objectFoldersWithDefinitions},
				type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE,
			});
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			{showModal && (
				<ModalAddObjectDefinition
					apiURL={viewApiURL}
					handleOnClose={() => {
						setShowModal(false);
					}}
					objectFolderExternalReferenceCode={selectedFolderERC}
					onAfterSubmit={(newObjectDefinition) => {
						dispatch({
							payload: {
								newObjectDefinition,
								selectedFolderName,
							},
							type: TYPES.ADD_NEW_NODE_TO_FOLDER,
						});
					}}
					reload={false}
					storages={storages}
				/>
			)}
			<Header
				folderExternalReferenceCode={selectedFolderERC}
				folderName={selectedFolderName}
				hasDraftObjectDefinitions={false}
			/>
			<div className="lfr-objects__model-builder-diagram-container">
				<LeftSidebar
					selectedFolderName={selectedFolderName}
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
