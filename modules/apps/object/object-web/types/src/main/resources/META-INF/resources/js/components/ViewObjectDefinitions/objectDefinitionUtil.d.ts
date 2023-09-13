/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {DropDownItems} from '../ModelBuilder/types';
import {DeletedObjectDefinition} from './ViewObjectDefinitions';
declare type DeleteObjectDefinitionProps = {
	baseResourceURL: string;
	handleShowDeleteObjectDefinitionModal: () => void;
	objectDefinitionId: number;
	objectDefinitionName: string;
	setDeletedObjectDefinition: (value: DeletedObjectDefinition) => void;
	status: string;
};
declare type ObjectDefinitionNodeActionsProps = {
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
declare type ObjectFolderAction = {
	href: string;
	method: string;
};
declare type ObjectFolderActions = {
	delete?: ObjectFolderAction;
	get?: ObjectFolderAction;
	permissions?: ObjectFolderAction;
	update?: ObjectFolderAction;
};
export declare function deleteObjectFolder(
	id: number,
	objectFolderName: string
): Promise<void>;
export declare function deleteObjectDefinitionToast(
	id: number,
	objectDefinitionName: string
): Promise<void>;
export declare function deleteObjectDefinition({
	baseResourceURL,
	handleShowDeleteObjectDefinitionModal,
	objectDefinitionId,
	objectDefinitionName,
	setDeletedObjectDefinition,
	status,
}: DeleteObjectDefinitionProps): Promise<void>;
export declare function deleteRelationship(id: number): Promise<void>;
export declare function getObjectDefinitionNodeActions({
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
}: ObjectDefinitionNodeActionsProps): DropDownItems[];
export declare function getObjectFolderActions(
	id: number,
	objectFolderPermissionsURL: string,
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void,
	actions?: ObjectFolderActions
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
