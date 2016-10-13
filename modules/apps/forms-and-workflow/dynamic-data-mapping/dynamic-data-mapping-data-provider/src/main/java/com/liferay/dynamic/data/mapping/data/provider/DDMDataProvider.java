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

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luca Comin
 */
public interface DDMDataProvider {

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #getData(
	 *             DDMDataProviderRequest)}
	 */
	@Deprecated
	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException;

	public default DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		List<KeyValuePair> keyValuePairs = getData(
			ddmDataProviderRequest.getDDMDataProviderContext());

		List<Map<Object, Object>> data = new ArrayList<>();

		for (KeyValuePair keyValuePair : keyValuePairs) {
			Map<Object, Object> map = new HashMap<>();

			data.add(map);

			map.put(keyValuePair.getKey(), keyValuePair.getValue());
		}

		return new DDMDataProviderResponse(data);
	}

	public Class<?> getSettings();

}