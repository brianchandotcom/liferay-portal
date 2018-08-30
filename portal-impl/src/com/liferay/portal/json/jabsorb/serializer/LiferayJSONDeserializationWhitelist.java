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

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsUtil;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Tomas Polesovsky
 */
public class LiferayJSONDeserializationWhitelist {

	public LiferayJSONDeserializationWhitelist() {
		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		_osgiAllowedClassNames = serviceTrackerMapFactory.openSingleValueMap(
			null, PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES);
	}

	public boolean isWhitelisted(String className) {
		boolean whitelisted = false;

		if (_registeredClassNames.contains(className)) {
			whitelisted = true;
		}
		else if (_osgiAllowedClassNames.containsKey(className)) {
			whitelisted = true;
		}
		else if (ArrayUtil.contains(
					 _JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES, className)) {

			whitelisted = true;
		}

		if (!whitelisted && _log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Unable to deserialize ", className,
					" due to security restrictions. To allow deserialization, ",
					"please use ",
					PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES, "=",
					className,
					" as the portal property or as OSGi service property."));
		}

		return whitelisted;
	}

	public void register(String... classeNames) {
		Collections.addAll(_registeredClassNames, classeNames);
	}

	private static final String[] _JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES =
		PropsUtil.getArray(
			PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES);

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJSONDeserializationWhitelist.class);

	private final ServiceTrackerMap<String, ?> _osgiAllowedClassNames;
	private final Set<String> _registeredClassNames =
		new CopyOnWriteArraySet<>();

}