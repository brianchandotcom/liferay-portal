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
	 * Get an asset's value
	 * @param {object} options
	 * @param {string} options.layoutObjectReference
	 * @param {function} options.onNetworkStatus
	 */
	getCollectionField({
		collection,
		onNetworkStatus,
		segmentsExperienceId,
		size,
	}) {
		return serviceFetch(
			config.getCollectionFieldURL,
			{
				body: {
					layoutObjectReference: JSON.stringify(collection),
					segmentsExperienceId,
					size,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available collection mapping fields
	 * @param {object} options
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
};
