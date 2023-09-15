/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface SidebarBodyProps {
	fectchedObjectDefinitions: ObjectDefinitionsRelationshipTree;
	navHistory: AddedObjectDefinition[][];
	schemaUIData: APISchemaUIData;
	searchKeyword: string;
	setNavHistory: Dispatch<SetStateAction<AddedObjectDefinition[][]>>;
	setOnBackClick: Dispatch<SetStateAction<voidReturn>>;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
	viewRelatedObjects: boolean;
}
export default function SidebarBody({
	fectchedObjectDefinitions,
	navHistory,
	schemaUIData,
	searchKeyword,
	setNavHistory,
	setOnBackClick,
	setSchemaUIData,
}: SidebarBodyProps): JSX.Element;
export {};
