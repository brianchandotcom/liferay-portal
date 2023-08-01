/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SetStateAction} from 'react';
import {ViewObjectDefinitionsModals} from './ViewObjectDefinitions';
declare type folderAction = {
	href: string;
	method: string;
};
declare type folderActions = {
	delete?: folderAction;
	get?: folderAction;
	permissions?: folderAction;
	update?: folderAction;
};
export declare function deleteFolder(
	id: number,
	folderName: string
): Promise<void>;
export declare function deleteObjectDefinition(
	id: number,
	objectDefinitionName: string
): Promise<void>;
export declare function getFolderActions(
	id: number,
	objectFolderPermissionsURL: string,
	setShowModal: (value: SetStateAction<ViewObjectDefinitionsModals>) => void,
	actions?: folderActions
): (
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
export declare function normalizeName(str: string): string;
export {};
