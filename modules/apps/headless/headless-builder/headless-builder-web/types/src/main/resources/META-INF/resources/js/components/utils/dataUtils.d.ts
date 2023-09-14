/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type LocalUIData =
	| APIApplicationUIData
	| APIEndpointUIData
	| APISchemaUIData;
interface AddObjectFieldsDataToProperties {
	apiSchema: APISchemaItem;
	objectDefinitions: ObjectDefinition[];
	schemaProperties: APISchemaPropertyItem[];
}
export declare function AddObjectFieldsDataToProperties({
	apiSchema,
	objectDefinitions,
	schemaProperties,
}: AddObjectFieldsDataToProperties): TreeViewItemData[];
export declare function hasDataChanged({
	fetchedEntityData,
	localUIData,
}: {
	fetchedEntityData: APIApplicationItem | APIEndpointItem | APISchemaItem;
	localUIData: LocalUIData;
}): boolean;
export declare function hasPropertiesDataChanged({
	fetchedPropertiesData,
	propertiesUIData,
}: {
	fetchedPropertiesData: APISchemaPropertyItem[];
	propertiesUIData: TreeViewItemData[];
}): boolean;
export declare function resetToFetched<FT extends LT, LT extends {}>({
	fetchedEntityData,
	localUIData,
}: {
	fetchedEntityData: FT;
	localUIData: LT;
}): LT;
export {};
