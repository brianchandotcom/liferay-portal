/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {DropDownItems} from '../ModelBuilder/types';
import {
	DeletedObjectDefinition,
	ViewObjectDefinitionsModals,
} from './ViewObjectDefinitions';
declare type DefinitionNodeActionsProps = {
	baseResourceURL: string;
	handleShowDeleteModal: () => void;
	handleShowEditERCModal: () => void;
	handleShowRedirectModal: () => void;
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
declare type DeleteObjectDefinitionProps = {
	baseResourceURL: string;
	handleShowDeleteModal: () => void;
	objectDefinitionId: number;
	objectDefinitionName: string;
	setDeletedObjectDefinition: (value: DeletedObjectDefinition) => void;
	status: string;
};
declare type FolderAction = {
	href: string;
	method: string;
};
declare type FolderActions = {
	delete?: FolderAction;
	get?: FolderAction;
	permissions?: FolderAction;
	update?: FolderAction;
};
export declare function deleteFolder(
	id: number,
	folderName: string
): Promise<void>;
export declare function deleteObjectDefinitionToast(
	id: number,
	objectDefinitionName: string
): Promise<void>;
export declare function deleteObjectDefinition({
	baseResourceURL,
	handleShowDeleteModal,
	objectDefinitionId,
	objectDefinitionName,
	setDeletedObjectDefinition,
	status,
}: DeleteObjectDefinitionProps): Promise<void>;
export declare function deleteRelationship(id: number): Promise<void>;
export declare function getDefinitionNodeActions({
	baseResourceURL,
	handleShowDeleteModal,
	handleShowEditERCModal,
	handleShowRedirectModal,
	hasObjectDefinitionDeleteResourcePermission,
	hasObjectDefinitionManagePermissionsResourcePermission,
	objectDefinitionId,
	objectDefinitionName,
	objectDefinitionPermissionsURL,
	setDeletedObjectDefinition,
	status,
}: DefinitionNodeActionsProps): DropDownItems[];
export declare function getFolderActions(
	id: number,
	objectFolderPermissionsURL: string,
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void,
	actions?: FolderActions
): (
	| {
			type: string;
			label?: undefined;
			onClick?: undefined;
			symbolLeft?: undefined;
			value?: undefined;
	  }
	| {
			label: string;
			onClick: () => void;
			symbolLeft: string;
			value: string;
			type?: undefined;
	  }
)[];
export declare function normalizeName(str: string): string;
export {};
