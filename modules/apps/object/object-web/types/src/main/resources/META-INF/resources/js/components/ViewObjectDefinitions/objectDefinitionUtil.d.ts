/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {DropDownItems, TAction} from '../ModelBuilder/types';
import {ModalImportProperties} from './ViewObjectDefinitions';
declare type DeleteObjectDefinitionProps = {
	baseResourceURL: string;
	handleDeleteObjectDefinition?: (value: DeletedObjectDefinition) => void;
	handleShowDeleteObjectDefinitionModal?: () => void;
	objectDefinitionId: number;
	objectDefinitionName: string;
	onAfterDeleteObjectDefinition?: () => void;
};
declare type ObjectDefinitionNodeActionsProps = {
	baseResourceURL: string;
	dispatch: React.Dispatch<TAction>;
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionUpdateResourcePermission: boolean;
	objectDefinitionId: number;
	objectDefinitionName: string;
	objectDefinitionPermissionsURL: string;
	objectFoldersLenght: number;
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
	onAfterDeleteObjectDefinition,
}: DeleteObjectDefinitionProps): Promise<
	| {
			hasObjectRelationship: boolean;
			id: number;
			name: string;
			objectEntriesCount: number;
	  }
	| undefined
>;
export declare function deleteRelationship(
	id: number,
	reloadAfterDeletion?: boolean
): Promise<void>;
export declare function getDbTableName({
	baseResourceURL,
	objectDefinitionId,
}: {
	baseResourceURL: string;
	objectDefinitionId: number;
}): Promise<string>;
export declare function getObjectDefinitionNodeActions({
	baseResourceURL,
	dispatch,
	hasObjectDefinitionDeleteResourcePermission,
	hasObjectDefinitionManagePermissionsResourcePermission,
	hasObjectDefinitionUpdateResourcePermission,
	objectDefinitionId,
	objectDefinitionName,
	objectDefinitionPermissionsURL,
	objectFoldersLenght,
}: ObjectDefinitionNodeActionsProps): DropDownItems[];
interface GetObjectFolderActionsProps {
	actions?: {
		objectDefinitionActions: Actions;
		objectFolderActions: Actions;
	};
	baseResourceURL: string;
	importObjectDefinitionURL: string;
	objectFolderExternalReferenceCode: string;
	objectFolderId: number;
	objectFolderPermissionsURL: string;
	portletNamespace: string;
	setModalImportProperties: (
		value: SetStateAction<ModalImportProperties>
	) => void;
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void;
}
export declare function getObjectFolderActions({
	actions,
	baseResourceURL,
	importObjectDefinitionURL,
	objectFolderExternalReferenceCode,
	objectFolderId,
	objectFolderPermissionsURL,
	portletNamespace,
	setModalImportProperties,
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
	baseResourceURL: string,
	currentObjectFolderName: string
): Promise<{
	objectFolders: ObjectFolder[];
	selectedObjectFolderName: string;
}>;
export declare function normalizeName(str: string): string;
export {};
