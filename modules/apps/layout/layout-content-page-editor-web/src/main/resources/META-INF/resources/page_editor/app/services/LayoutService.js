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

const layoutServiceFetch = (url, options, onNetworkStatus) => {
	return serviceFetch(url, options, onNetworkStatus, {
		requestGenerateDraft: true,
	});
};

export default {

	/**
	 * Adds an item to layoutData
	 * @param {object} options
	 * @param {string} options.itemType item type
	 * @param {function} options.onNetworkStatus
	 * @param {object} options.parentItemId Parent to be added to
	 * @param {object} options.position Position to be added to
	 * @param {object} options.segmentsExperienceId
	 * @return {Promise<object>}
	 */
	addItem({
		itemType,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.addItemURL,
			{
				body: {
					itemType,
					parentItemId,
					position,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Change the master layout associated to the page
	 * @param {object} options
	 * @param {object} options.masterLayoutPlid id of the master page
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<object>}
	 */
	changeMasterLayout({masterLayoutPlid, onNetworkStatus}) {
		return layoutServiceFetch(
			config.changeMasterLayoutURL,
			{
				body: {
					masterLayoutPlid,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Change the style book entry associated to the page
	 * @param {object} options
	 * @param {object} options.styleBookEntryId id of the style book entry
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<object>}
	 */
	changeStyleBookEntry({onNetworkStatus, styleBookEntryId}) {
		return layoutServiceFetch(
			config.changeStyleBookEntryURL,
			{
				body: {
					styleBookEntryId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * @param {object} layout
	 * @returns {Promise<{error: Error, friendlyURL: string}>}
	 */
	getLayoutFriendlyURL(layout) {
		return layoutServiceFetch(
			config.getLayoutFriendlyURL,
			{
				body: layout,
			},
			() => {}
		);
	},

	/**
	 * Marks an item for deletion
	 * @param {object} options
	 * @param {string} options.itemId id of the item to be updated
	 * @param {string} options.portletIds the list of non instanceable portlets Ids
	 * contained in the item
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	markItemForDeletion({
		itemId,
		onNetworkStatus,
		portletIds = [],
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.markItemForDeletionURL,
			{
				body: {
					itemId,
					portletIds,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Move an item inside layoutData
	 * @param {object} options
	 * @param {object} options.itemId id of the item to be moved
	 * @param {object} options.parentItemId id of the target parent
	 * @param {object} options.position position in the parent where the item is placed
	 * @param {object} options.segmentsExperienceId
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<object>}
	 */
	moveItem({
		itemId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.moveItemURL,
			{
				body: {
					itemId,
					parentItemId,
					position,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates a config into an item
	 * @param {object} options
	 * @param {Array<{fragmentEntryLinkId: string, editableValues: object}>} filterFragmentEntryLinks
	 * @param {object} options.itemConfig Updated item config
	 * @param {string} options.itemId id of the collection display to be updated
	 * @param {string} options.segmentsExperienceId Language id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	restoreCollectionDisplayConfig({
		filterFragmentEntryLinks,
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.restoreCollectionDisplayConfigURL,
			{
				body: {
					filterFragmentEntryLinks: JSON.stringify(
						filterFragmentEntryLinks
					),
					itemConfig: JSON.stringify(itemConfig),
					itemId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Unmarks an item for deletion
	 * @param {object} options
	 * @param {string} options.itemId id of the item to be updated
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	unmarkItemForDeletion({itemId, onNetworkStatus, segmentsExperienceId}) {
		return layoutServiceFetch(
			config.unmarkItemForDeletionURL,
			{
				body: {
					itemId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates a config into an item
	 * @param {object} options
	 * @param {object} options.itemConfig Updated item config
	 * @param {string} options.itemId id of the collection display to be updated
	 * @param {string} options.languageId Language id
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	updateCollectionDisplayConfig({
		itemConfig,
		itemId,
		languageId,
		onNetworkStatus,
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.updateCollectionDisplayConfigURL,
			{
				body: {
					itemConfig: JSON.stringify(itemConfig),
					itemId,
					languageId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates a config into an item
	 * @param {object} options
	 * @param {object} options.itemConfig Updated item config
	 * @param {string} options.itemId id of the item to be updated
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	updateItemConfig({
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.updateItemConfigURL,
			{
				body: {
					itemConfig: JSON.stringify(itemConfig),
					itemId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates layout's layoutData
	 * @param {object} options
	 * @param {string} options.segmentsExperienceId Current segmentsExperienceId
	 * @param {object} options.layoutData New layoutData
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	updateLayoutData({layoutData, onNetworkStatus, segmentsExperienceId}) {
		return layoutServiceFetch(
			config.updateLayoutPageTemplateDataURL,
			{
				body: {
					data: JSON.stringify(layoutData),
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates the number of columns of a row
	 * @param {object} options
	 * @param {string} options.itemId id of the item to be updated
	 * @param {number} options.numberOfColumns New number of columns
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	updateRowColumns({
		itemId,
		numberOfColumns,
		onNetworkStatus,
		segmentsExperienceId,
	}) {
		return layoutServiceFetch(
			config.updateRowColumnsURL,
			{
				body: {
					itemId,
					numberOfColumns,
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},
};
