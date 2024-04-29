/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface BaseAPISchemaPropertyProps {
	added: boolean;
	objectField: ObjectField;
	objectRelationshipName?: string;
	parentObjectDefinitionData: ParentObjectDefinitionProps;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}
interface ParentObjectDefinitionProps {
	modifiable?: boolean;
	objectDefinitionName: string;
	objectDefinitionERC: string;
}
export default function BaseAPISchemaProperty({
	added,
	objectField,
	objectRelationshipName,
	parentObjectDefinitionData,
	setSchemaUIData,
}: BaseAPISchemaPropertyProps): JSX.Element;
export {};
