/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CollectionLayoutDataItem} from '../../types/layout_data/CollectionLayoutDataItem';
import {MappingFieldFieldSet} from '../actions/addMappingFields';
export interface CollectionFilter {
	configuration: Record<string, unknown>;
	key: string;
	label: string;
}
declare const _default: {
	getCollectionEditConfigurationUrl(body: {
		collectionKey: string;
		itemId: string;
		segmentsExperienceId: string;
	}): Promise<{
		url: string;
	}>;
	getCollectionField(options: {
		activePage: number;
		classNameId: string;
		classPK?: string | null;
		collection: CollectionLayoutDataItem['config']['collection'];
		displayAllItems: CollectionLayoutDataItem['config']['displayAllItems'];
		displayAllPages: CollectionLayoutDataItem['config']['displayAllPages'];
		externalReferenceCode?: string | null;
		languageId: string;
		listItemStyle:
			| CollectionLayoutDataItem['config']['listItemStyle']
			| null;
		listStyle: CollectionLayoutDataItem['config']['listStyle'];
		numberOfItems: CollectionLayoutDataItem['config']['numberOfItems'];
		numberOfItemsPerPage: CollectionLayoutDataItem['config']['numberOfItemsPerPage'];
		numberOfPages: CollectionLayoutDataItem['config']['numberOfPages'];
		paginationType: CollectionLayoutDataItem['config']['paginationType'];
		segmentsExperienceId: string;
		showAllItems: CollectionLayoutDataItem['config']['showAllItems'] | null;
		templateKey: CollectionLayoutDataItem['config']['templateKey'] | null;
	}): Promise<unknown>;
	getCollectionFilters(): Promise<Record<string, CollectionFilter>>;
	getCollectionItemCount({
		classNameId,
		classPK,
		collection,
	}: {
		classNameId: string;
		classPK: string;
		collection: CollectionLayoutDataItem['config']['collection'];
	}): Promise<{
		totalNumberOfItems: number;
	}>;
	getCollectionMappingFields(body: {
		itemSubtype: string;
		itemType: string;
	}): Promise<{
		mappingFields: MappingFieldFieldSet;
	}>;
	getCollectionSupportedFilters(
		collections: Array<{
			collectionId: string;
			layoutObjectReference: CollectionLayoutDataItem['config']['collection'];
		}>
	): Promise<Record<string, string[]>>;
	getCollectionVariations(classPK: string): Promise<string[]>;
	getCollectionWarningMessage(options: {
		layoutDataItemId: string;
		segmentsExperienceId: string;
	}): Promise<{
		warningMessage: string;
	}>;
};
export default _default;
