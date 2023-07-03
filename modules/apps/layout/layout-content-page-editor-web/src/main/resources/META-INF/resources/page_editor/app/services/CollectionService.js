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

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Get a collection's configuration
	 * @param {object} options
	 * @param {object} options.collection
	 */
	getCollectionConfiguration(collection) {
		return serviceFetch(config.getCollectionConfigurationURL, {
			body: {
				collectionKey: collection.key,
			},
		});
	},

	/**
	 * Get the URL to edit configuration of a collection
	 * @param {object} options
	 * @param {string} options.collectionKey
	 * @param {string} options.itemId
	 * @param {string} options.segmentsExperienceId
	 */
	getCollectionEditConfigurationUrl({
		collectionKey,
		itemId,
		segmentsExperienceId,
	}) {
		return serviceFetch(config.getEditCollectionConfigurationURL, {
			body: {
				collectionKey,
				itemId,
				segmentsExperienceId,
			},
		});
	},

	/**
	 * Get an asset's value
	 * @param {object} options
	 * @param {string} options.listItemStyle
	 * @param {string} options.listStyle
	 * @param {function} options.onNetworkStatus
	 */
	getCollectionField({
		activePage,
		classNameId,
		classPK,
		collection,
		displayAllItems,
		displayAllPages,
		externalReferenceCode,
		languageId,
		listItemStyle,
		listStyle,
		numberOfItems,
		numberOfItemsPerPage,
		numberOfPages,
		onNetworkStatus,
		paginationType,
		segmentsExperienceId,
		showAllItems,
		templateKey,
	}) {
		return serviceFetch(
			config.getCollectionFieldURL,
			{
				body: {
					activePage,
					classNameId,
					classPK,
					displayAllItems,
					displayAllPages,
					externalReferenceCode,
					languageId,
					layoutObjectReference: JSON.stringify(collection),
					listItemStyle,
					listStyle,
					numberOfItems,
					numberOfItemsPerPage,
					numberOfPages,
					paginationType,
					segmentsExperienceId,
					showAllItems,
					templateKey,
				},
			},
			onNetworkStatus
		);
	},

	getCollectionFilters() {
		return serviceFetch(config.getCollectionFiltersURL, {}, () => {});
	},

	/**
	 * Get a collection item's count
	 * @param {object} options
	 * @param {string} options.classNameId
	 * @param {string} options.classPK
	 * @param {object} options.collection
	 * @param {function} options.onNetworkStatus
	 */
	getCollectionItemCount({
		classNameId,
		classPK,
		collection,
		onNetworkStatus,
	}) {
		return serviceFetch(
			config.getCollectionItemCountURL,
			{
				body: {
					classNameId,
					classPK,
					layoutObjectReference: JSON.stringify(collection),
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available collection mapping fields
	 * @param {object} options
	 * @param {string} options.fieldType Type of field to which we are mapping
	 * @param {string} options.itemSubtype Collection itemSubtype
	 * @param {string} options.itemType Collection itemType
	 * @param {function} options.onNetworkStatus
	 */
	getCollectionMappingFields({itemSubtype, itemType, onNetworkStatus}) {
		return serviceFetch(
			config.getCollectionMappingFieldsURL,
			{
				body: {
					itemSubtype,
					itemType,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * @param {Array<{collectionId: string}>} collections
	 * @returns {Promise<string[]>}
	 */
	getCollectionSupportedFilters(collections) {
		return serviceFetch(
			config.getCollectionSupportedFiltersURL,
			{body: {collections: JSON.stringify(collections)}},
			() => {}
		);
	},

	/**
	 * @param {string} classPK
	 * @returns {Promise<string[]>}
	 */
	getCollectionVariations(classPK) {
		return serviceFetch(
			config.getCollectionVariationsURL,
			{body: {classPK}},
			() => {}
		);
	},
};
