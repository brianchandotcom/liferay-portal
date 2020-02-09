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

import serviceFetch from './serviceFetch';

export default {
	/**
	 * Adds an item to layoutData
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.itemType item type
	 * @param {object} options.parentItemId Parent to be added to
	 * @param {object} options.position Position to be added to
	 * @param {object} options.segmentsExperienceId
	 * @return {Promise<object>}
	 */
	addItem({
		config,
		itemType,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId
	}) {
		const {addItemURL} = config;

		return serviceFetch(
			config,
			addItemURL,
			{
				body: {
					itemType,
					parentItemId,
					position,
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Remove an item inside layoutData
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {object} options.itemId id of the item to be removed
	 * @param {object} options.segmentsExperienceId
	 * @return {Promise<object>}
	 */
	deleteItem({config, itemId, onNetworkStatus, segmentsExperienceId}) {
		const {deleteItemURL} = config;

		return serviceFetch(
			config,
			deleteItemURL,
			{
				body: {
					itemId,
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Move an item inside layoutData
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {object} options.itemId id of the item to be moved
	 * @param {object} options.parentItemId id of the target parent
	 * @param {object} options.position position in the parent where the item is placed
	 * @param {object} options.segmentsExperienceId
	 * @return {Promise<object>}
	 */
	moveItem({
		config,
		itemId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId
	}) {
		const {moveItemURL} = config;

		return serviceFetch(
			config,
			moveItemURL,
			{
				body: {
					itemId,
					parentItemId,
					position,
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates a config into an item
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {object} options.itemConfig Updated item config
	 * @param {string} options.itemId id of the item to be updated
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @return {Promise<void>}
	 */
	updateItemConfig({
		config,
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId
	}) {
		const {updateItemConfigURL} = config;

		return serviceFetch(
			config,
			updateItemConfigURL,
			{
				body: {
					itemConfig: JSON.stringify(itemConfig),
					itemId,
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates layout's layoutData
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.segmentsExperienceId Current segmentsExperienceId
	 * @param {object} options.layoutData New layoutData
	 * @return {Promise<void>}
	 */
	updateLayoutData({
		config,
		layoutData,
		onNetworkStatus,
		segmentsExperienceId
	}) {
		const {updateLayoutPageTemplateDataURL} = config;

		return serviceFetch(
			config,
			updateLayoutPageTemplateDataURL,
			{
				body: {
					data: JSON.stringify(layoutData),
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates the number of columns of a row
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.itemId id of the item to be updated
	 * @param {number} options.numberOfColumns New number of columns
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @return {Promise<void>}
	 */
	updateRowColumns({
		config,
		itemId,
		numberOfColumns,
		onNetworkStatus,
		segmentsExperienceId
	}) {
		const {updateRowColumnsURL} = config;

		return serviceFetch(
			config,
			updateRowColumnsURL,
			{
				body: {
					itemId,
					numberOfColumns,
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	}
};
