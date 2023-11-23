/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import {createResourceURL, openModal, sub} from 'frontend-js-web';
import {SetStateAction} from 'react';

import {formatActionURL} from '../../utils/fds';
import {
	firstLetterUppercase,
	removeAllSpecialCharacters,
} from '../../utils/string';
import {DropDownItems} from '../ModelBuilder/types';
import {ModalImportObjectDefinitionInfo} from './ViewObjectDefinitions';

type DeleteObjectDefinitionProps = {
	baseResourceURL: string;
	handleDeleteObjectDefinition: (value: DeletedObjectDefinition) => void;
	handleShowDeleteObjectDefinitionModal: () => void;
	objectDefinitionId: number;
	objectDefinitionName: string;
};

type ObjectDefinitionNodeActionsProps = {
	baseResourceURL: string;
	handleDeleteObjectDefinition: (value: DeletedObjectDefinition) => void;
	handleShowDeleteObjectDefinitionModal: () => void;
	handleShowEditObjectDefinitionExternalReferenceCodeModal: () => void;
	handleShowRedirectObjectDefinitionModal: () => void;
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	objectDefinitionId: number;
	objectDefinitionName: string;
	objectDefinitionPermissionsURL: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
};

export async function deleteObjectFolder(id: number, objectFolderName: string) {
	await API.deleteObjectFolder(Number(id)).then(() => {
		Liferay.Util.openToast({
			message: sub(
				Liferay.Language.get('x-was-deleted-successfully'),
				`<strong>${Liferay.Util.escapeHTML(objectFolderName)}</strong>`
			),
		});
	});
}

export async function deleteObjectDefinitionToast(
	id: number,
	objectDefinitionName: string
) {
	await API.deleteObjectDefinition(Number(id)).then(() => {
		Liferay.Util.openToast({
			message: sub(
				Liferay.Language.get('x-was-deleted-successfully'),
				`<strong>${Liferay.Util.escapeHTML(
					objectDefinitionName
				)}</strong>`
			),
		});
	});
}

export async function deleteObjectDefinition({
	baseResourceURL,
	handleDeleteObjectDefinition,
	handleShowDeleteObjectDefinitionModal,
	objectDefinitionId,
	objectDefinitionName,
}: DeleteObjectDefinitionProps) {
	const url = createResourceURL(baseResourceURL, {
		objectDefinitionId,
		p_p_resource_id:
			'/object_definitions/get_object_definition_delete_info',
	}).href;

	const {
		hasObjectRelationship,
		objectEntriesCount,
		status,
	} = await API.fetchJSON<{
		hasObjectRelationship: boolean;
		objectEntriesCount: number;
		status: number;
	}>(url);

	if (status !== 0) {
		await deleteObjectDefinitionToast(
			objectDefinitionId,
			objectDefinitionName
		);
		setTimeout(() => window.location.reload(), 1000);

		return;
	}

	handleDeleteObjectDefinition({
		...{id: objectDefinitionId, name: objectDefinitionName},
		hasObjectRelationship,
		objectEntriesCount,
	});

	handleShowDeleteObjectDefinitionModal();
}

export async function deleteRelationship(
	id: number,
	reloadAfterDeletion?: boolean
) {
	try {
		await API.deleteObjectRelationship(id);

		Liferay.Util.openToast({
			message: Liferay.Language.get(
				'relationship-was-deleted-successfully'
			),
		});

		if (reloadAfterDeletion) {
			setTimeout(() => window.location.reload(), 1500);
		}
	}
	catch (error) {
		const errorMessage = (error as Error).message;

		openModal({
			bodyHTML: `<p>${errorMessage}</p>`,
			buttons: [
				{
					displayType: 'warning',
					label: Liferay.Language.get('done'),
					type: 'cancel',
				},
			],
			center: true,
			id: 'deleteRelationship',
			size: 'md',
			status: 'warning',
			title: Liferay.Language.get('deletion-not-allowed'),
		});
	}
}

export function getObjectDefinitionNodeActions({
	baseResourceURL,
	handleDeleteObjectDefinition,
	handleShowDeleteObjectDefinitionModal,
	handleShowEditObjectDefinitionExternalReferenceCodeModal,
	handleShowRedirectObjectDefinitionModal,
	hasObjectDefinitionDeleteResourcePermission,
	hasObjectDefinitionManagePermissionsResourcePermission,
	objectDefinitionId,
	objectDefinitionName,
	objectDefinitionPermissionsURL,
}: ObjectDefinitionNodeActionsProps) {
	const PermissionUrl = formatActionURL(
		objectDefinitionPermissionsURL,
		objectDefinitionId
	);

	const handleClickDeleteObjectDefinition = () => {
		deleteObjectDefinition({
			baseResourceURL,
			handleDeleteObjectDefinition,
			handleShowDeleteObjectDefinitionModal,
			objectDefinitionId,
			objectDefinitionName,
		});
	};

	const handleClickManagePermissions = (event: React.MouseEvent) => {
		event.stopPropagation();
		openModal({
			title: Liferay.Language.get('permissions'),
			url: PermissionUrl,
		});
	};

	const kebabOptions = [
		{
			label: sub(
				Liferay.Language.get('edit-in-x'),
				Liferay.Language.get('page view')
			),
			onClick: () => {
				handleShowRedirectObjectDefinitionModal();
			},
			symbolRight: 'shortcut',
		},
		{
			label: sub(
				Liferay.Language.get('edit-x'),
				Liferay.Language.get('erc')
			),
			onClick: () => {
				handleShowEditObjectDefinitionExternalReferenceCodeModal();
			},
			symbolLeft: 'info-panel-closed',
		},
		{type: 'divider'},
	] as DropDownItems[];

	if (hasObjectDefinitionManagePermissionsResourcePermission) {
		kebabOptions.push({
			label: sub(
				Liferay.Language.get('manage-x'),
				Liferay.Language.get('permissions')
			),
			onClick: handleClickManagePermissions,
			symbolLeft: 'users',
		});
	}

	if (hasObjectDefinitionDeleteResourcePermission) {
		kebabOptions.push({type: 'divider'});
		kebabOptions.push({
			label: sub(
				Liferay.Language.get('delete-x'),
				Liferay.Language.get('object')
			),
			onClick: handleClickDeleteObjectDefinition,
			symbolLeft: 'trash',
		});
	}

	return kebabOptions;
}

interface GetObjectFolderActionsProps {
	actions?: {
		objectDefinitionActions: Actions;
		objectFolderActions: Actions;
	};
	id: number;
	objectFolderPermissionsURL: string;
	setModalImportObjectDefinitionInfo: (
		value: ModalImportObjectDefinitionInfo
	) => void;
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void;
}

export function getObjectFolderActions({
	actions,
	id,
	objectFolderPermissionsURL,
	setModalImportObjectDefinitionInfo,
	setShowModal,
}: GetObjectFolderActionsProps) {
	const url = formatActionURL(objectFolderPermissionsURL, id);
	const kebabOptions = [];

	if (actions?.objectFolderActions?.update) {
		kebabOptions.unshift({
			label: Liferay.Language.get('edit-label-and-erc'),
			onClick: () =>
				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
					...previousState,
					editObjectFolder: true,
				})),
			symbolLeft: 'pencil',
			value: 'editObjectFolder',
		});

		kebabOptions.push({type: 'divider'});
	}

	kebabOptions.push({
		label: sub(
			Liferay.Language.get('export-x'),
			Liferay.Language.get('object-folder')
		),
		onClick: () => {},
		symbolLeft: 'export',
		value: 'exportObjectFolder',
	});

	if (actions?.objectDefinitionActions?.create) {
		kebabOptions.push({
			label: sub(
				Liferay.Language.get('import-x'),
				Liferay.Language.get('object-definition')
			),
			onClick: () => {
				setModalImportObjectDefinitionInfo({
					title: sub(
						Liferay.Language.get('import-x'),
						Liferay.Language.get('object-definition')
					),
					visible: true,
				});
			},
			symbolLeft: 'import',
			value: 'importObjectDefinition',
		});
		kebabOptions.push({type: 'divider'});
	}

	if (actions?.objectFolderActions.permissions) {
		kebabOptions.push({
			label: sub(
				Liferay.Language.get('x-permissions'),
				Liferay.Language.get('object-folder')
			),
			onClick: () => {
				openModal({
					title: Liferay.Language.get('permissions'),
					url,
				});
			},
			symbolLeft: 'password-policies',
			value: 'objectFolderPermissions',
		});
	}

	if (actions?.objectFolderActions.delete) {
		kebabOptions.push({type: 'divider'});
		kebabOptions.push({
			label: sub(
				Liferay.Language.get('delete-x'),
				Liferay.Language.get('object-folder')
			),
			onClick: () =>
				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
					...previousState,
					deleteObjectFolder: true,
				})),
			symbolLeft: 'trash',
			value: 'deleteObjectFolder',
		});
	}

	return kebabOptions;
}

export async function getUpdatedModelBuilderStructurePayload(
	currentObjectFolderName: string
) {
	const {items: objectFolders} = await API.getAllObjectFolders();

	const currentObjectFolder = objectFolders.find(
		(objectFolder) => objectFolder.name === currentObjectFolderName
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
							linkedObjectDefinition,
							objectFields: objectDefinition.objectFields.map(
								({
									businessType,
									externalReferenceCode,
									id,
									label,
									name,
									required,
								}) =>
									({
										businessType,
										externalReferenceCode,
										id,
										label,
										name,
										primaryKey: name === 'id',
										required,
										selected: false,
									} as ObjectFieldNodeRow)
							),
							selected: false,
							showAllObjectFields: false,
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

	return {
		objectFolders: objectFoldersWithObjectDefinitions,
		selectedObjectFolder: currentObjectFolder,
	};
}

export function normalizeName(str: string) {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	return removeAllSpecialCharacters(join);
}
