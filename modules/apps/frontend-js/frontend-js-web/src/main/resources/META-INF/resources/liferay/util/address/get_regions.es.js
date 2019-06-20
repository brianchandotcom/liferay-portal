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

import {isFunction, isString} from 'metal';

/**
 * Returns a list of regions by country
 * @callback callback
 * @param {!string} selectKey The selected region ID
 * @return {array} Array of regions by country
 */
export default function getRegions(callback, selectKey) {
	if (!isFunction(callback)) {
		throw new TypeError('Parameter callback must be a function');
	}

	if (!isString(selectKey)) {
		throw new TypeError('Parameter selectKey must be a string');
	}

	Liferay.Service(
		'/region/get-regions',
		{
			active: true,
			countryId: parseInt(selectKey, 10)
		},
		callback
	);
}
