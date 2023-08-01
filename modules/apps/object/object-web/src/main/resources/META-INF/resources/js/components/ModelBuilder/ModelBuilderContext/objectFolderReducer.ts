/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';

import {defaultLanguageId} from '../../../utils/constants';
import {
	LeftSidebarItemType,
	ObjectDefinitionNode,
	ObjectFieldNode,
	TAction,
	TState,
} from '../types';
import {TYPES} from './typesEnum';

function fieldsCustomSort(objectFields: ObjectFieldNode[]) {
	const fieldOrder = ['id', 'externalReferenceCode'];

	const compareFields = (a: ObjectFieldNode, b: ObjectFieldNode) => {
		const aIndex = fieldOrder.indexOf(a.name as string);
		const bIndex = fieldOrder.indexOf(b.name as string);

		if (aIndex !== -1 && bIndex !== -1) {
			return aIndex - bIndex;
		}
		else if (aIndex !== -1) {
			return -1;
		}
		else if (bIndex !== -1) {
			return 1;
		}

		if (a.required && !b.required) {
			return -1;
		}
		else if (!a.required && b.required) {
			return 1;
		}

		return 0;
	};

	return objectFields.sort(compareFields);
}

export function objectFolderReducer(state: TState, action: TAction) {
	switch (action.type) {
		case TYPES.CREATE_MODEL_BUILDER_STRUCTURE: {
			const {objectFolders} = action.payload;
			const {selectedFolderERC} = state;

			const newLeftSidebar = objectFolders.map((folder) => {
				const folderDefinitions = folder.definitions?.map(
					(definition) => {
						return {
							definitionName: definition.name,
							name: getLocalizableLabel(
								definition.defaultLanguageId,
								definition.label,
								definition.name
							),
							type: 'objectDefinition',
						};
					}
				);

				return {
					folderName: folder.name,
					name: getLocalizableLabel(
						defaultLanguageId,
						folder.label,
						folder.name
					),
					objectDefinitions: folderDefinitions,
					type: 'objectFolder',
				} as LeftSidebarItemType;
			});

			const currentFolder = objectFolders.find(
				(folder) => folder.externalReferenceCode === selectedFolderERC
			);

			let newObjectDefinitionNodes: ObjectDefinitionNode[] = [];

			if (currentFolder) {
				const positionColumn = {x: 1, y: 0};

				newObjectDefinitionNodes = currentFolder.definitions!.map(
					(objectDefinition, index) => {
						const objectFields = objectDefinition.objectFields.map(
							(field) => {
								return {
									businessType: field.businessType,
									externalReferenceCode:
										field.externalReferenceCode,
									label: getLocalizableLabel(
										objectDefinition.defaultLanguageId,
										field.label,
										field.name
									),
									name: field.name,
									primaryKey: field.name === 'id',
									selected: false,
								} as ObjectFieldNode;
							}
						);

						if (index % 4 === 0) {
							positionColumn.y++;
							positionColumn.x = 1;
						}

						positionColumn.x++;

						return {
							data: {
								creationLanguageId:
									objectDefinition.defaultLanguageId,
								hasObjectDefinitionDeleteResourcePermission: true,
								hasObjectDefinitionManagePermissionsResourcePermission: true,
								hasObjectDefinitionUpdateResourcePermission: true,
								hasObjectDefinitionViewResourcePermission: true,
								isLinkedNode: false,
								label: getLocalizableLabel(
									objectDefinition.defaultLanguageId,
									objectDefinition.label,
									objectDefinition.name
								),
								name: objectDefinition.name,
								nodeSelected: true,
								objectFields: fieldsCustomSort(objectFields),
								status: objectDefinition.status,
								system: objectDefinition.system,
							},
							id: objectDefinition.name,
							position: {
								x: positionColumn.x * 300,
								y: positionColumn.y * 400,
							},
							type: 'objectDefinition',
						} as ObjectDefinitionNode;
					}
				);
			}

			return {
				...state,
				leftSidebarItems: newLeftSidebar,
				objectDefinitionNodes: newObjectDefinitionNodes,
			};
		}
		default:
			return state;
	}
}
