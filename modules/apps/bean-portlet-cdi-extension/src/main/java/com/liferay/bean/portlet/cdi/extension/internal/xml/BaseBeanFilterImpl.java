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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.bean.portlet.cdi.extension.internal.BeanFilter;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public abstract class BaseBeanFilterImpl implements BeanFilter {

	@Override
	public Dictionary<String, Object> toDictionary() {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		Set<String> lifecycles = getLifecycles();

		if (!lifecycles.isEmpty()) {
			dictionary.put("filter.lifecycles", lifecycles);
		}

		dictionary.put("service.ranking:Integer", getOrdinal());

		Map<String, String> initParams = getInitParams();

		for (Map.Entry<String, String> entry : initParams.entrySet()) {
			String value = entry.getValue();

			if (value != null) {
				dictionary.put(
					"javax.portlet.init-param.".concat(entry.getKey()), value);
			}
		}

		return dictionary;
	}

}