/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	LeftSidebarItemType,
	ObjectDefinitionNode,
	TAction,
	TState,
} from '../types';
export declare function objectFolderReducer(
	state: TState,
	action: TAction
): {
	objectDefinitionNodes: any;
	leftSidebarItems: LeftSidebarItemType[];
	objectDefinitions: ObjectDefinition[];
	objectFolders: ObjectFolder[];
	rightSidebarType:
		| 'objectDefinitionDetails'
		| 'objectRelationshipDetails'
		| 'empty';
	selectedDefinitionNode: ObjectDefinitionNode;
	selectedFolderERC: string;
	selectedObjectRelationship: ObjectRelationship;
};
