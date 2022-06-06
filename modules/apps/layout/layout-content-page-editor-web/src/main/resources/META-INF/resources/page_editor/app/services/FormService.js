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
	 * Get available form mapping fields
	 * @param {object} options
	 * @param {string} options.classNameId Form classNameId
	 * @param {string} options.classTypeId Form classTypeId
	 * @param {function} options.onNetworkStatus
	 */
	getFormFields({classNameId, classTypeId, onNetworkStatus = () => {}}) {
		return serviceFetch(
			config.getFormFieldsURL,
			{
				body: {
					classNameId,
					classTypeId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get allowed field types for a given fragment entry
	 * @param {object} options
	 * @param {string} options.fragmentEntryKey
	 * @param {function} options.onNetworkStatus
	 */
	getFragmentEntryInputFieldTypes({
		fragmentEntryKey,
		onNetworkStatus = () => {},
	}) {
		return serviceFetch(
			config.getFragmentEntryInputFieldTypesURL,
			{
				body: {
					fragmentEntryKey,
				},
			},
			onNetworkStatus
		);
	},
};
