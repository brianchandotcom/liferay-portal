/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {ModalImportProperties} from './ViewObjectDefinitions';
interface ObjectFoldersSidebarProps {
	baseResourceURL: string;
	importObjectFolderURL: string;
	objectDefinitionsActions: Actions;
	objectFoldersRequestInfo: ObjectFoldersRequestInfo;
	portletNamespace: string;
	selectedObjectFolder: ObjectFolder;
	setModalImportProperties: (
		value: SetStateAction<ModalImportProperties>
	) => void;
	setSelectedObjectFolder: (
		value: SetStateAction<Partial<ObjectFolder>>
	) => void;
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void;
}
export default function ObjectFoldersSideBar({
	baseResourceURL,
	importObjectFolderURL,
	objectDefinitionsActions,
	objectFoldersRequestInfo,
	selectedObjectFolder,
	setModalImportProperties,
	setSelectedObjectFolder,
	setShowModal,
}: ObjectFoldersSidebarProps): JSX.Element;
export {};
