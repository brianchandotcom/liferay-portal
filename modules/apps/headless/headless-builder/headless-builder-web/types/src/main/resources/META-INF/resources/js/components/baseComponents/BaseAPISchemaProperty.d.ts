/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface BaseAPISchemaPropertyProps {
	added: boolean;
	objectDefinition: ObjectDefinitionProps;
	objectField: ObjectField;
	objectRelationshipName?: string;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}
interface ObjectDefinitionProps {
	externalReferenceCode: string;
	modifiable?: boolean;
	name: string;
}
export default function BaseAPISchemaProperty({
	added,
	objectDefinition,
	objectField,
	objectRelationshipName,
	setSchemaUIData,
}: BaseAPISchemaPropertyProps): JSX.Element;
export {};
