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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Tomas Polesovsky
 */
public class LiferayJSONDeserializationWhitelist {

	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			registry.getFilter(
				StringBundler.concat(
					"(", PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES,
					"=*)")),
			new WhitelistServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public boolean isWhitelisted(String className) {
		boolean whitelisted = false;

		if (_registeredClassNames.contains(className)) {
			whitelisted = true;
		}
		else if (_osgiAllowedClassNames.contains(className)) {
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
					" due to security restrictions. To allow deserialization ",
					"please use ",
					PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES, "=",
					className,
					" as the portal property or as OSGi service property."));
		}

		return whitelisted;
	}

	public void register(String className) {
		_registeredClassNames.add(className);
	}

	private static final String[] _JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES =
		PropsUtil.getArray(
			PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES);

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJSONDeserializationWhitelist.class);

	private static final List<String> _osgiAllowedClassNames =
		new CopyOnWriteArrayList<>();

	private final List<String> _registeredClassNames =
		new CopyOnWriteArrayList<>();
	private ServiceTracker<Object, List<String>> _serviceTracker;

	private class WhitelistServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, List<String>> {

		@Override
		public List<String> addingService(
			ServiceReference<Object> serviceReference) {

			List<String> whitelist = StringPlus.asList(
				serviceReference.getProperty(
					PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES));

			for (String className : whitelist) {
				if (!_osgiAllowedClassNames.contains(className)) {
					_osgiAllowedClassNames.add(className);
				}
			}

			_osgiAllowedClassNamesList.add(whitelist);

			return whitelist;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, List<String> whitelist) {

			removedService(serviceReference, whitelist);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, List<String> whitelist) {

			_osgiAllowedClassNamesList.remove(whitelist);

			Set<String> allowedClassNames = new HashSet<>();

			for (List<String> osgiAllowedClassNames :
					_osgiAllowedClassNamesList) {

				allowedClassNames.addAll(osgiAllowedClassNames);
			}

			for (String className : whitelist) {
				if (!allowedClassNames.contains(className)) {
					_osgiAllowedClassNames.remove(className);
				}
			}
		}

		private final List<List<String>> _osgiAllowedClassNamesList =
			new ArrayList<>();

	}

}