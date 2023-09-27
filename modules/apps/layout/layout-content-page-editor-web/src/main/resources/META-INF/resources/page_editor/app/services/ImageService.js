/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
