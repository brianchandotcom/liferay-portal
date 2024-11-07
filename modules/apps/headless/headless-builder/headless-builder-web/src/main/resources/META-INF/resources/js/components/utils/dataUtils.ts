/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {beginStringWithForwardSlash} from './string';

type LocalUIData = APIApplicationUIData | APISchemaUIData;

interface AddObjectFieldsDataToProperties {
	apiSchema: APISchemaItem;
	objectDefinitions: ObjectDefinition[];
	schemaProperties: APISchemaPropertyItem[];
}

export function AddObjectFieldsDataToProperties({
	apiSchema,
	objectDefinitions,
	schemaProperties,
}: AddObjectFieldsDataToProperties) {
	const propertiesTreeViewItems = schemaProperties.map(
		({
			description,
			externalReferenceCode,
			id,
			name,
			objectFieldERC,
			objectRelationshipNames,
			r_apiPropertyToAPIProperties_l_apiPropertyId,
			type,
		}) => {
			if (type.key === 'record') {
				return {
					businessType: type.name,
					externalReferenceCode,
					id,
					name,
					type: 'trewViewItem',
				};
			}

			const objectRelationshipNamesArray =
				objectRelationshipNames?.split(',');

			const objectRelationshipName =
				objectRelationshipNamesArray?.[
					objectRelationshipNamesArray.length - 1
				];

			const mainObjectDefinition = objectDefinitions.find(
				(definition) =>
					definition.externalReferenceCode ===
					apiSchema.mainObjectDefinitionERC
			);

			let objectDefinitionId2: number;

			objectDefinitions.forEach((definition) => {
				definition.objectRelationships.forEach((relationship) => {
					{
						if (relationship.name === objectRelationshipName) {
							objectDefinitionId2 =
								relationship.objectDefinitionId2;
						}
					}
				});
			});

			const relatedObjectDefinition = objectDefinitions.find(
				(parentObjectDefinition) =>
					parentObjectDefinition.id === objectDefinitionId2
			);

			const parentObjectDefinition =
				relatedObjectDefinition ?? mainObjectDefinition;

			const currentObjectField =
				parentObjectDefinition?.objectFields.find(
					(objectField) =>
						objectField.externalReferenceCode === objectFieldERC
				);

			if (currentObjectField && parentObjectDefinition) {
				return {
					businessType: currentObjectField?.businessType!,
					...((description || description === '') && {
						description,
					}),
					externalReferenceCode,
					id,
					name,
					objectDefinitionName: parentObjectDefinition?.name!,
					objectFieldERC,
					objectFieldId: currentObjectField?.id!,
					objectFieldName: currentObjectField?.name!,
					...(objectRelationshipNames && {
						objectRelationshipNames,
					}),
					r_apiPropertyToAPIProperties_l_apiPropertyId,
					r_apiSchemaToAPIProperties_l_apiSchemaId: apiSchema.id,
					type: 'trewViewItem',
				};
			}
		}
	);

	return (
		propertiesTreeViewItems.length
			? propertiesTreeViewItems
			: schemaProperties
	) as TreeViewItemData[];
}

export function hasDataChanged({
	fetchedEntityData,
	localUIData,
}: {
	fetchedEntityData: APIApplicationItem | APISchemaItem;
	localUIData: Partial<LocalUIData>;
}) {
	for (const [key, value] of Object.entries(localUIData)) {
		if (fetchedEntityData?.[key as keyof LocalUIData] !== value) {
			return true;
		}
	}

	return false;
}

export function hasEndpointDataChanged({
	fetchedEndpointData,
	localUIData,
}: {
	fetchedEndpointData: APIEndpointItem;
	localUIData: Partial<APIEndpointUIData>;
}) {
	const {
		description,
		path,
		r_responseAPISchemaToAPIEndpoints_l_apiSchemaId,
		scope,
	} = fetchedEndpointData;

	const {
		description: uiDescription,
		parameter: uiParameter,
		path: uiPath,
		r_responseAPISchemaToAPIEndpoints_l_apiSchemaId:
			uiR_responseAPISchemaToAPIEndpoints_l_apiSchemaId,
		scope: uiScope,
	} = localUIData;

	const descriptionChanged =
		!(description === uiDescription) &&
		!(description === '' && uiDescription === undefined);

	const filtersArrayLengthChanged = !!(
		localUIData.apiEndpointToAPIFilters &&
		fetchedEndpointData.apiEndpointToAPIFilters &&
		fetchedEndpointData.apiEndpointToAPIFilters.length !==
			localUIData.apiEndpointToAPIFilters.length
	);

	const filtersContentChanged = !!(
		localUIData.apiEndpointToAPIFilters?.length &&
		fetchedEndpointData.apiEndpointToAPIFilters?.length &&
		fetchedEndpointData.apiEndpointToAPIFilters[0].oDataFilter !==
			localUIData.apiEndpointToAPIFilters[0].oDataFilter
	);

	const pathChanged = uiParameter
		? path !==
			beginStringWithForwardSlash(
				uiPath + '' + beginStringWithForwardSlash(uiParameter)
			)
		: path !== beginStringWithForwardSlash(uiPath);

	const schemaIdChanged =
		((r_responseAPISchemaToAPIEndpoints_l_apiSchemaId === 0 &&
			uiR_responseAPISchemaToAPIEndpoints_l_apiSchemaId) ||
			r_responseAPISchemaToAPIEndpoints_l_apiSchemaId !==
				uiR_responseAPISchemaToAPIEndpoints_l_apiSchemaId) &&
		!(
			r_responseAPISchemaToAPIEndpoints_l_apiSchemaId === 0 &&
			!uiR_responseAPISchemaToAPIEndpoints_l_apiSchemaId
		);

	const scopeKeyChanged = scope.key !== uiScope?.key;

	const sortsArrayLengthChanged = !!(
		localUIData.apiEndpointToAPISorts &&
		fetchedEndpointData.apiEndpointToAPISorts &&
		fetchedEndpointData.apiEndpointToAPISorts.length !==
			localUIData.apiEndpointToAPISorts.length
	);

	const sortsContentChanged = !!(
		localUIData.apiEndpointToAPISorts?.length &&
		fetchedEndpointData.apiEndpointToAPISorts?.length &&
		fetchedEndpointData.apiEndpointToAPISorts[0].oDataSort !==
			localUIData.apiEndpointToAPISorts[0].oDataSort
	);

	if (
		descriptionChanged ||
		filtersArrayLengthChanged ||
		filtersContentChanged ||
		pathChanged ||
		schemaIdChanged ||
		scopeKeyChanged ||
		sortsArrayLengthChanged ||
		sortsContentChanged
	) {
		return true;
	}

	return false;
}

export function hasPropertiesDataChanged({
	fetchedPropertiesData,
	propertiesUIData,
}: {
	fetchedPropertiesData: APISchemaPropertyItem[];
	propertiesUIData: TreeViewItemData[];
}) {
	if (propertiesUIData.length !== fetchedPropertiesData.length) {
		return true;
	}
	else {
		for (const property of propertiesUIData) {
			const matchedFetchedProperty = fetchedPropertiesData.find(
				({objectFieldERC, objectRelationshipNames}) =>
					objectRelationshipNames ===
						property.objectRelationshipNames &&
					objectFieldERC === property.objectFieldERC
			);

			if (
				!(
					matchedFetchedProperty &&
					(matchedFetchedProperty.description ===
						property.description ||
						(!matchedFetchedProperty.description &&
							property.description === '')) &&
					matchedFetchedProperty.name === property.name
				)
			) {
				return true;
			}
		}

		return false;
	}
}

export function resetToFetched<FT extends LT, LT extends {}>({
	fetchedEntityData,
	localUIData,
}: {
	fetchedEntityData: FT;
	localUIData: LT;
}) {
	const resetedData: {[key: string]: unknown} = {};

	for (const [key, _] of Object.entries(localUIData)) {
		if (fetchedEntityData[key as keyof LT]) {
			resetedData[key] = fetchedEntityData[key as keyof LT];
		}
	}

	return resetedData as LT;
}
