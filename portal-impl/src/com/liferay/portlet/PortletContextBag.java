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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.model.PortletApp;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.util.StringPlus;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURLGenerationListener;
import javax.portlet.filter.PortletFilter;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextBag {

	public PortletContextBag(String servletContextName) {
		_servletContextName = servletContextName;

		_dynamicCustomUserAttributes = ServiceTrackerCollections.singleValueMap(
			CustomUserAttributes.class,
			"(&(custom.user.attribute=*)(|(servlet.context.name=ALL)" +
				"(servlet.context.name=" + servletContextName + ")))",
			new ServiceReferenceMapper<String, CustomUserAttributes>() {

				@Override
				public void map(
					ServiceReference<CustomUserAttributes> serviceReference,
					ServiceReferenceMapper.Emitter<String> emitter) {

					List<String> customUserAttributeKeys = StringPlus.asList(
						serviceReference.getProperty("custom.user.attribute"));

					for (String customUserAttributeKey :
							customUserAttributeKeys) {

						emitter.emit(customUserAttributeKey);
					}

				}
			}
		);

		_dynamicCustomUserAttributes.open();
	}

	public void close() {
		_dynamicCustomUserAttributes.close();
	}

	public Map<String, CustomUserAttributes> getCustomUserAttributes() {
		return _customUserAttributes;
	}

	public Map<String, PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	public Map<String, PortletURLGenerationListener> getPortletURLListeners() {
		return _urlListeners;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public void populateUserInfo(
		LinkedHashMap<String, String> userInfo, PortletApp portletApp) {

		Map<String, String> unmodifiableUserInfo = Collections.unmodifiableMap(
			(Map<String, String>)userInfo.clone());

		// Custom user attributes

		Map<String, CustomUserAttributes> customUserAttributesMap =
			new HashMap<>();

		Map<String, String> customUserAttributesClassNames =
			portletApp.getCustomUserAttributes();

		for (Map.Entry<String, String> entry :
				customUserAttributesClassNames.entrySet()) {

			String userAttributeName = entry.getKey();
			String customUserAttributesClassName = entry.getValue();

			CustomUserAttributes customUserAttributes =
				customUserAttributesMap.get(customUserAttributesClassName);

			if (customUserAttributes == null) {
				if (portletApp.isWARFile()) {
					Map<String, CustomUserAttributes>
						portletContextBagCustomUserAttributes =
							getCustomUserAttributes();

					customUserAttributes =
						portletContextBagCustomUserAttributes.get(
							customUserAttributesClassName);

					if (customUserAttributes != null) {
						customUserAttributes =
							(CustomUserAttributes)customUserAttributes.clone();
					}
				}
				else {
					customUserAttributes = newInstance(
						customUserAttributesClassName);
				}
			}

			if (customUserAttributes != null) {
				customUserAttributesMap.put(
					customUserAttributesClassName, customUserAttributes);

				String attrValue = customUserAttributes.getValue(
					userAttributeName, unmodifiableUserInfo);

				if (attrValue != null) {
					userInfo.put(userAttributeName, attrValue);
				}
			}
		}

		for (String userAttributeName : _dynamicCustomUserAttributes.keySet()) {
			CustomUserAttributes customUserAttributes =
				_dynamicCustomUserAttributes.getService(userAttributeName);

			String attrValue = customUserAttributes.getValue(
				userAttributeName, unmodifiableUserInfo);

			if (attrValue != null) {
				userInfo.put(userAttributeName, attrValue);
			}
		}
	}

	private CustomUserAttributes newInstance(String className) {
		try {
			return (CustomUserAttributes)InstanceFactory.newInstance(className);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletContextBag.class);

	private final Map<String, CustomUserAttributes> _customUserAttributes =
		new HashMap<>();
	private final ServiceTrackerMap<String, CustomUserAttributes>
		_dynamicCustomUserAttributes;
	private final Map<String, PortletFilter> _portletFilters = new HashMap<>();
	private final String _servletContextName;
	private final Map<String, PortletURLGenerationListener> _urlListeners =
		new HashMap<>();

}