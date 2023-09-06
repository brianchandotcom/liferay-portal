/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Node} from 'react-flow-renderer';
import {LeftSidebarItemType, RightSidebarType, TAction, TState} from '../types';
export declare function ObjectFolderReducer(
	state: TState,
	action: TAction
):
	| {
			elements: Node<ObjectDefinitionNodeData>[];
			leftSidebarItems: LeftSidebarItemType[];
			selectedDefinitionNode: Node<ObjectDefinitionNodeData>;
			showChangesSaved: boolean;
			baseResourceURL: string;
			editObjectDefinitionURL: string;
			objectDefinitionPermissionsURL: string;
			objectDefinitions: ObjectDefinition[];
			objectFolders: ObjectFolder[];
			rightSidebarType: RightSidebarType;
			selectedObjectDefinitionNode: Node<ObjectDefinitionNodeData>;
			selectedObjectFolder: ObjectFolder;
			selectedObjectRelationship: ObjectRelationship;
			storages: LabelValueObject[];
			viewApiURL: string;
	  }
	| {
			elements: any;
			baseResourceURL: string;
			editObjectDefinitionURL: string;
			leftSidebarItems: LeftSidebarItemType[];
			objectDefinitionPermissionsURL: string;
			objectDefinitions: ObjectDefinition[];
			objectFolders: ObjectFolder[];
			rightSidebarType: RightSidebarType;
			selectedObjectDefinitionNode: Node<ObjectDefinitionNodeData>;
			selectedObjectFolder: ObjectFolder;
			selectedObjectRelationship: ObjectRelationship;
			showChangesSaved: boolean;
			storages: LabelValueObject[];
			viewApiURL: string;
	  };
