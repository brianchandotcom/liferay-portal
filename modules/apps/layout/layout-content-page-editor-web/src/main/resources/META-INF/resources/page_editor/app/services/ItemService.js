import serviceFetch from './serviceFetch';

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

export default {
	/**
	 * Adds an item to layoutData
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.itemType item type
	 * @param {object} options.parentItemId A parent to be added
	 * @param {object} options.segmentsExperienceId
	 * @return {Promise<object>}
	 */
	addItem({
		config,
		itemConfig,
		itemType,
		parentItemId,
		position,
		segmentsExperienceId
	}) {
		const {addItemURL} = config;

		return serviceFetch(config, addItemURL, {
			itemConfig: JSON.stringify(itemConfig),
			itemType,
			parentItemId,
			position,
			segmentsExperienceId
		});
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
	moveItem({config, itemId, parentItemId, position, segmentsExperienceId}) {
		const {moveItemURL} = config;

		return serviceFetch(config, moveItemURL, {
			itemId,
			parentItemId,
			position,
			segmentsExperienceId
		});
	}
};
