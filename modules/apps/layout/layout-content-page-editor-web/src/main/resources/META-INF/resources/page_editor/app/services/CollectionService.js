/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		const body = {
			activePage,
			classNameId,
			displayAllItems,
			displayAllPages,
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
		};

		if (classPK) {
			body.classPK = classPK;
		}

		if (externalReferenceCode) {
			body.externalReferenceCode = externalReferenceCode;
		}

		return serviceFetch(
			config.getCollectionFieldURL,
			{
				body,
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

	/**
	 * @param {object} options
	 * @param {string} options.segmentsExperienceId
	 * @param {string} options.layoutDataItemId
	 * @returns {Promise<{warningMessage: string}>}
	 */
	getCollectionWarningMessage({layoutDataItemId, segmentsExperienceId}) {
		return serviceFetch(
			config.getCollectionWarningMessageURL,
			{
				body: {
					itemId: layoutDataItemId,
					segmentsExperienceId,
				},
			},
			() => {}
		);
	},
};
