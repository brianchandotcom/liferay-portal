/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {DropDownItems} from '../ModelBuilder/types';
import {ModalImportObjectDefinitionInfo} from './ViewObjectDefinitions';
declare type DeleteObjectDefinitionProps = {
	baseResourceURL: string;
	handleDeleteObjectDefinition: (value: DeletedObjectDefinition) => void;
	handleShowDeleteObjectDefinitionModal: () => void;
	objectDefinitionId: number;
	objectDefinitionName: string;
};
declare type ObjectDefinitionNodeActionsProps = {
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
	handleDeleteObjectDefinition,
	handleShowDeleteObjectDefinitionModal,
	objectDefinitionId,
	objectDefinitionName,
}: DeleteObjectDefinitionProps): Promise<void>;
export declare function deleteRelationship(
	id: number,
	reloadAfterDeletion?: boolean
): Promise<void>;
export declare function getObjectDefinitionNodeActions({
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
}: ObjectDefinitionNodeActionsProps): DropDownItems[];
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
export declare function getObjectFolderActions({
	actions,
	id,
	objectFolderPermissionsURL,
	setModalImportObjectDefinitionInfo,
	setShowModal,
}: GetObjectFolderActionsProps): (
	| {
			label: string;
			onClick: () => void;
			symbolLeft: string;
			value: string;
			type?: undefined;
	  }
	| {
			type: string;
			label?: undefined;
			onClick?: undefined;
			symbolLeft?: undefined;
			value?: undefined;
	  }
)[];
export declare function getUpdatedModelBuilderStructurePayload(
	currentObjectFolderName: string
): Promise<{
	objectFolders: ObjectFolder[];
	selectedObjectFolder: ObjectFolder;
}>;
export declare function normalizeName(str: string): string;
export {};
