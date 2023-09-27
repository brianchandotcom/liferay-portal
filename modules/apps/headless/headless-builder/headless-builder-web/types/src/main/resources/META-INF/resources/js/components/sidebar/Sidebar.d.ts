/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface SidebarProps {
	mainObjectDefinitionERC: string;
	objectDefinitions?: ObjectDefinitionsRelationshipTree;
	schemaUIData: APISchemaUIData;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}
export default function Sidebar({
	mainObjectDefinitionERC,
	objectDefinitions,
	schemaUIData,
	setSchemaUIData,
}: SidebarProps): JSX.Element;
export {};
