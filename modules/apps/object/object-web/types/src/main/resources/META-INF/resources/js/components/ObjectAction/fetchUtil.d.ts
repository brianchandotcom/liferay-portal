/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type ObjectsOptionsList = {
	items: ObjectOptionsListItem[];
	label: string;
}[];
export declare type ObjectOptionsListItem = {
	isSystemObjectDefinition: boolean;
	label?: string;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
};
interface FetchObjectDefinitionsProps {
	objectDefinitionsRelationshipsURL: string;
	setAddObjectEntryDefinitions: (values: AddObjectEntryDefinitions[]) => void;
	setObjectOptions: (values: ObjectsOptionsList) => void;
}
export declare function fetchObjectDefinitions({
	objectDefinitionsRelationshipsURL,
	setAddObjectEntryDefinitions,
	setObjectOptions,
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
