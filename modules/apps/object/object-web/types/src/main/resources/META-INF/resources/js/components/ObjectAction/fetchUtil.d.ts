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

export declare type ObjectsOptionsList = {
	label: string;
	options: LabelValueObject[];
	type: string;
}[];
interface FetchObjectDefinitionsProps {
	objectDefinitionsRelationshipsURL: string;
	setAddObjectEntryDefinitions: (values: AddObjectEntryDefinitions[]) => void;
	setObjectOptions: (values: ObjectsOptionsList) => void;
	setSelectedObjectDefinition?: (value: string) => void;
	values: Partial<ObjectAction>;
}
export declare function fetchObjectDefinitions({
	objectDefinitionsRelationshipsURL,
	setAddObjectEntryDefinitions,
	setObjectOptions,
	setSelectedObjectDefinition,
	values,
}: FetchObjectDefinitionsProps): Promise<void>;
export declare function fetchObjectDefinitionFields(
	objectDefinitionId: number,
	objectDefinitionExternalReferenceCode: string,
	systemObject: boolean,
	values: Partial<ObjectAction>,
	isValidField: (
		{businessType, name, objectFieldSettings, system}: ObjectField,
		isObjectActionSystem?: boolean
	) => boolean,
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void,
	setValues: (values: Partial<ObjectAction>) => void
): Promise<void>;
export {};
