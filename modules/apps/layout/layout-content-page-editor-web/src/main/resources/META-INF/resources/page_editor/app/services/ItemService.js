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
	 * @param {object} options.parentId A parent to be added
	 * @param {object} options.segmentsExperienceId
	 * @param {string} options.type item type
	 * @return {Promise<object>}
	 */
	addItem({
		config,
		itemConfig,
		parentId,
		position,
		segmentsExperienceId,
		type
	}) {
		const {addItemURL} = config;

		return serviceFetch(config, addItemURL, {
			config: JSON.stringify(itemConfig),
			parentId,
			position,
			segmentsExperienceId,
			type
		});
	}
};
