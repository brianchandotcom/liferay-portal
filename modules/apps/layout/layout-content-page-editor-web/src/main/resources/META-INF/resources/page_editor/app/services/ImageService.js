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
	 * Get available image configurations
	 * @param {object} options
	 * @param {string} options.fileEntryId File entry ID
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableImageConfigurations({fileEntryId, onNetworkStatus}) {
		return serviceFetch(
			config.getAvailableImageConfigurationsURL,
			{
				body: {
					fileEntryId,
				},
			},
			onNetworkStatus
		);
	},

	getFileEntry({fileEntryId, onNetworkStatus}) {
		return serviceFetch(
			config.getFileEntryURL,
			{
				body: {
					fileEntryId,
				},
			},
			onNetworkStatus
		);
	},
};
