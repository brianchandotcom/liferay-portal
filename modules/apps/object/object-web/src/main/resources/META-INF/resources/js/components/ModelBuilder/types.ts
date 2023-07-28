/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TYPES} from './ModelBuilderContext/typesEnum';

export type TAction = {
	payload: {
		objectFolders: ObjectFolder[];
	};
	type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE;
};

export type TState = {
	objectDefinitions: ObjectDefinition[];
	rightSidebarType:
		| 'objectDefinitionDetails'
		| 'objectRelationshipDetails'
		| 'empty';
	selectedDefinitionNode: DefinitionNode;
	selectedObjectRelationship: ObjectRelationship;
	objectFolders: ObjectFolder[];
};

export interface FieldNode extends ObjectField {
	selected: boolean;
}

export interface DefinitionNode extends ObjectDefinition {
	hasUpdateObjectDefinitionPermission: boolean;
	selected: boolean;
}
