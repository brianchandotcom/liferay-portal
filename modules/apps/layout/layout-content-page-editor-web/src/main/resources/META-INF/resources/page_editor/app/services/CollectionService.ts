/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CollectionLayoutDataItem} from '../../types/layout_data/CollectionLayoutDataItem';
import {MappingFieldFieldSet} from '../actions/addMappingFields';
import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export interface CollectionFilter {
	configuration: Record<string, unknown>;
	key: string;
	label: string;
}

export default {
	getCollectionEditConfigurationUrl(body: {
		collectionKey: string;
		itemId: string;
		segmentsExperienceId: string;
	}) {
		return serviceFetch<{url: string}>(
			config.getEditCollectionConfigurationURL,
			{body}
		);
	},

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
	}) {
		const body: Omit<
			typeof options,
			'classPK' | 'collection' | 'externalReferenceCode'
		> & {
			classPK?: string;
			externalReferenceCode?: string;
			layoutObjectReference: string;
		} = {
			activePage: options.activePage,
			classNameId: options.classNameId,
			displayAllItems: options.displayAllItems,
			displayAllPages: options.displayAllPages,
			languageId: options.languageId,
			layoutObjectReference: JSON.stringify(options.collection),
			listItemStyle: options.listItemStyle,
			listStyle: options.listStyle,
			numberOfItems: options.numberOfItems,
			numberOfItemsPerPage: options.numberOfItemsPerPage,
			numberOfPages: options.numberOfPages,
			paginationType: options.paginationType,
			segmentsExperienceId: options.segmentsExperienceId,
			showAllItems: options.showAllItems,
			templateKey: options.templateKey,
		};

		if (options.classPK) {
			body.classPK = options.classPK;
		}

		if (options.externalReferenceCode) {
			body.externalReferenceCode = options.externalReferenceCode;
		}

		return serviceFetch(config.getCollectionFieldURL, {body});
	},

	getCollectionFilters() {
		return serviceFetch<Record<string, CollectionFilter>>(
			config.getCollectionFiltersURL,
			{}
		);
	},

	getCollectionItemCount({
		classNameId,
		classPK,
		collection,
	}: {
		classNameId: string;
		classPK: string;
		collection: CollectionLayoutDataItem['config']['collection'];
	}) {
		return serviceFetch<{totalNumberOfItems: number}>(
			config.getCollectionItemCountURL,
			{
				body: {
					classNameId,
					classPK,
					layoutObjectReference: JSON.stringify(collection),
				},
			}
		);
	},

	getCollectionMappingFields(body: {
		fieldName: string | undefined;
		itemSubtype: string;
		itemType: string;
	}) {
		return serviceFetch<{mappingFields: MappingFieldFieldSet[]}>(
			config.getCollectionMappingFieldsURL,
			{body}
		);
	},

	getCollectionSupportedFilters(
		collections: Array<{
			collectionId: string;
			layoutObjectReference: CollectionLayoutDataItem['config']['collection'];
		}>
	) {
		return serviceFetch<Record<string, string[]>>(
			config.getCollectionSupportedFiltersURL,
			{body: {collections: JSON.stringify(collections)}}
		);
	},

	getCollectionVariations(classPK: string) {
		return serviceFetch<string[]>(config.getCollectionVariationsURL, {
			body: {classPK},
		});
	},

	getCollectionWarningMessage(options: {
		layoutDataItemId: string;
		segmentsExperienceId: string;
	}) {
		return serviceFetch<{warningMessage: string}>(
			config.getCollectionWarningMessageURL,
			{
				body: {
					itemId: options.layoutDataItemId,
					segmentsExperienceId: options.segmentsExperienceId,
				},
			}
		);
	},
};
