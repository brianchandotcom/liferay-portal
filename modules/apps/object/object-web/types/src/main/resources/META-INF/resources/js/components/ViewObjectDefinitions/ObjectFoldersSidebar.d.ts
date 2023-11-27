/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {ModalImportObjectDefinitionInfo} from './ViewObjectDefinitions';
interface ObjectFoldersSidebarProps {
	baseResourceURL: string;
	objectDefinitionsActions: Actions;
	objectFoldersRequestInfo: ObjectFoldersRequestInfo;
	selectedObjectFolder: ObjectFolder;
	setModalImportObjectDefinitionInfo: (
		value: ModalImportObjectDefinitionInfo
	) => void;
	setSelectedObjectFolder: (
		value: SetStateAction<Partial<ObjectFolder>>
	) => void;
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void;
}
export default function ObjectFoldersSideBar({
	baseResourceURL,
	objectDefinitionsActions,
	objectFoldersRequestInfo,
	selectedObjectFolder,
	setModalImportObjectDefinitionInfo,
	setSelectedObjectFolder,
	setShowModal,
}: ObjectFoldersSidebarProps): JSX.Element;
export {};
