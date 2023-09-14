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
import {DeletedObjectDefinition} from './ViewObjectDefinitions';

type DeleteObjectDefinitionProps = {
	baseResourceURL: string;
	handleShowDeleteObjectDefinitionModal: () => void;
	objectDefinitionId: number;
	objectDefinitionName: string;
	setDeletedObjectDefinition: (value: DeletedObjectDefinition) => void;
	status: string;
};

type ObjectDefinitionNodeActionsProps = {
	baseResourceURL: string;
	handleShowDeleteObjectDefinitionModal: () => void;
	handleShowEditObjectDefinitionExternalReferenceCodeModal: () => void;
	handleShowRedirectObjectDefinitionModal: () => void;
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	objectDefinitionId: number;
	objectDefinitionName: string;
	objectDefinitionPermissionsURL: string;
	setDeletedObjectDefinition: (value: DeletedObjectDefinition) => void;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
};

type ObjectFolderAction = {
	href: string;
	method: string;
};

type ObjectFolderActions = {
	delete?: ObjectFolderAction;
	get?: ObjectFolderAction;
	permissions?: ObjectFolderAction;
	update?: ObjectFolderAction;
};

export async function deleteObjectFolder(id: number, objectFolderName: string) {
	await API.deleteObjectFolder(Number(id)).then(() => {
		Liferay.Util.openToast({
			message: sub(
				Liferay.Language.get('x-was-deleted-successfully'),
				`<strong>${objectFolderName}</strong>`
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
				`<strong>${objectDefinitionName}</strong>`
			),
		});
	});
}

export async function deleteObjectDefinition({
	baseResourceURL,
	handleShowDeleteObjectDefinitionModal,
	objectDefinitionId,
	objectDefinitionName,
	setDeletedObjectDefinition,
	status,
}: DeleteObjectDefinitionProps) {
	const url = createResourceURL(baseResourceURL, {
		objectDefinitionId,
		p_p_resource_id:
			'/object_definitions/get_object_definition_delete_info',
	}).href;

	const {hasObjectRelationship, objectEntriesCount} = await API.fetchJSON<{
		hasObjectRelationship: boolean;
		objectEntriesCount: number;
	}>(url);

	if (status !== 'approved') {
		await deleteObjectDefinitionToast(
			objectDefinitionId,
			objectDefinitionName
		);
		setTimeout(() => window.location.reload(), 1000);

		return;
	}

	setDeletedObjectDefinition({
		...{id: objectDefinitionId, name: objectDefinitionName},
		hasObjectRelationship,
		objectEntriesCount,
	});

	handleShowDeleteObjectDefinitionModal();
}

export async function deleteRelationship(id: number) {
	try {
		await API.deleteObjectRelationship(id);

		Liferay.Util.openToast({
			message: Liferay.Language.get(
				'relationship-was-deleted-successfully'
			),
		});
	}
	catch (error) {
		Liferay.Util.openToast({
			message: (error as Error).message,
			type: 'danger',
		});
	}
}

export function getObjectDefinitionNodeActions({
	baseResourceURL,
	handleShowDeleteObjectDefinitionModal,
	handleShowEditObjectDefinitionExternalReferenceCodeModal,
	handleShowRedirectObjectDefinitionModal,
	hasObjectDefinitionDeleteResourcePermission,
	hasObjectDefinitionManagePermissionsResourcePermission,
	objectDefinitionId,
	objectDefinitionName,
	objectDefinitionPermissionsURL,
	setDeletedObjectDefinition,
	status,
}: ObjectDefinitionNodeActionsProps) {
	const PermissionUrl = formatActionURL(
		objectDefinitionPermissionsURL,
		objectDefinitionId
	);

	const handleClickDeleteObjectDefinition = (event: React.MouseEvent) => {
		event.stopPropagation();
		deleteObjectDefinition({
			baseResourceURL,
			handleShowDeleteObjectDefinitionModal,
			objectDefinitionId,
			objectDefinitionName,
			setDeletedObjectDefinition,
			status: status.label,
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
			onClick: (event: Event) => {
				event.stopPropagation();
				handleShowRedirectObjectDefinitionModal();
			},
			symbolRight: 'shortcut',
		},
		{
			label: sub(
				Liferay.Language.get('edit-x'),
				Liferay.Language.get('erc')
			),
			onClick: (event: Event) => {
				event.stopPropagation();
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

export function getObjectFolderActions(
	id: number,
	objectFolderPermissionsURL: string,
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void,
	actions?: ObjectFolderActions
) {
	const url = formatActionURL(objectFolderPermissionsURL, id);
	const kebabOptions = [];

	if (actions?.update) {
		kebabOptions.unshift({type: 'divider'});
		kebabOptions.unshift({
			label: Liferay.Language.get('edit-label-and-erc'),
			onClick: () =>
				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
					...previousState,
					editObjectFolder: true,
				})),
			symbolLeft: 'pencil',
			value: 'editFolder',
		});
	}

	if (actions?.permissions) {
		kebabOptions.push({
			label: Liferay.Language.get('folder-permissions'),
			onClick: () => {
				openModal({
					title: Liferay.Language.get('permissions'),
					url,
				});
			},
			symbolLeft: 'password-policies',
			value: 'folderPermissions',
		});
	}

	if (actions?.delete) {
		kebabOptions.push({type: 'divider'});
		kebabOptions.push({
			label: Liferay.Language.get('delete-folder'),
			onClick: () =>
				setShowModal((previousState: ViewObjectDefinitionsModals) => ({
					...previousState,
					deleteObjectFolder: true,
				})),
			symbolLeft: 'trash',
			value: 'deleteFolder',
		});
	}

	return kebabOptions;
}

export function normalizeName(str: string) {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	return removeAllSpecialCharacters(join);
}
