/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/// <reference types="react" />

import {ClaySelect} from '@clayui/form';
export declare type ObjectsOptionsList = Array<
	(
		| React.ComponentProps<typeof ClaySelect.Option>
		| React.ComponentProps<typeof ClaySelect.OptGroup>
	) & {
		options?: Array<React.ComponentProps<typeof ClaySelect.Option>>;
		type?: 'group';
	}
>;
export declare function fetchObjectDefinitions(
	objectDefinitionsRelationshipsURL: string,
	values: Partial<ObjectAction>,
	setRelationships: (values: ObjectDefinitionsRelationship[]) => void,
	setObjectOptions: (values: ObjectsOptionsList) => void
): Promise<void>;
export declare function fetchObjectDefinitionFields(
	objectDefinitionId: number,
	objectDefinitionExternalReferenceCode: string,
	values: Partial<ObjectAction>,
	isValidField: ({
		businessType,
		objectFieldSettings,
		system,
	}: ObjectField) => void,
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void,
	setValues: (values: Partial<ObjectAction>) => void
): Promise<void>;
