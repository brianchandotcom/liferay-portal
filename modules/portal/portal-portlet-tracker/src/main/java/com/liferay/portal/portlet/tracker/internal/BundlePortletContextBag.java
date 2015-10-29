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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.osgi.service.tracker.map.ServiceRankingPropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.PortletContextBag;
import com.liferay.registry.util.StringPlus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class BundlePortletContextBag extends PortletContextBag {

	public BundlePortletContextBag(
		String portletId, String servletContextName,
		BundleContext bundleContext) {

		super(servletContextName);

		ServiceTrackerMap<String, CustomUserAttributes> serviceTrackerMap =
			null;

		try {
			serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
				bundleContext, CustomUserAttributes.class,
				"(&(custom.user.attribute=*)(|(javax.portlet.name=" +
					portletId + ")(javax.portlet.name=ALL)))",
				new CustomUserAttributesServiceReferenceMapper(),
				new ServiceRankingPropertyServiceReferenceComparator
					<CustomUserAttributes>());
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}

		_customUserAttributesMap = serviceTrackerMap;

		_customUserAttributesMap.open();
	}

	public void close() {
		_customUserAttributesMap.close();
	}

	@Override
	public Map<String, CustomUserAttributes> getCustomUserAttributes() {
		Map<String, CustomUserAttributes> customUserAttributesMap =
			new HashMap<>();

		customUserAttributesMap.putAll(super.getCustomUserAttributes());

		for (String key : _customUserAttributesMap.keySet()) {
			customUserAttributesMap.put(
				key, _customUserAttributesMap.getService(key));
		}

		return customUserAttributesMap;
	}

	private final ServiceTrackerMap<String, CustomUserAttributes>
		_customUserAttributesMap;

	private class CustomUserAttributesServiceReferenceMapper
		implements ServiceReferenceMapper<String, CustomUserAttributes> {

		@Override
		public void map(
			ServiceReference<CustomUserAttributes> serviceReference,
			Emitter<String> emitter) {

			List<String> customUserAttributeKeys = StringPlus.asList(
				serviceReference.getProperty("custom.user.attribute"));

			for (String customUserAttributeKey : customUserAttributeKeys) {
				emitter.emit(customUserAttributeKey);
			}
		}

	}

}