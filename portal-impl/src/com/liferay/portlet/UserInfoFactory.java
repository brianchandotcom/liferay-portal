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
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.CustomUserAttributes;
import com.liferay.portal.kernel.portlet.UserAttributes;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class UserInfoFactory {

	public static LinkedHashMap<String, String> getUserInfo(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		if (httpServletRequest.getRemoteUser() == null) {
			return null;
		}

		LinkedHashMap<String, String> userInfo = new LinkedHashMap<>();

		try {
			userInfo = getUserInfo(
				PortalUtil.getUser(httpServletRequest), userInfo, portlet);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return userInfo;
	}

	public static LinkedHashMap<String, String> getUserInfo(
		long userId, Portlet portlet) {

		if (userId <= 0) {
			return null;
		}

		LinkedHashMap<String, String> userInfo = new LinkedHashMap<>();

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			userInfo = getUserInfo(user, userInfo, portlet);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return userInfo;
	}

	public static LinkedHashMap<String, String> getUserInfo(
		User user, LinkedHashMap<String, String> userInfo, Portlet portlet) {

		PortletApp portletApp = portlet.getPortletApp();

		// Liferay user attributes

		try {
			UserAttributes userAttributes = new UserAttributes(user);

			// Mandatory user attributes

			userInfo.put(
				UserAttributes.LIFERAY_COMPANY_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_COMPANY_ID));

			userInfo.put(
				UserAttributes.LIFERAY_USER_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_USER_ID));

			userInfo.put(
				UserAttributes.USER_NAME_FULL,
				userAttributes.getValue(UserAttributes.USER_NAME_FULL));

			// Portlet user attributes

			for (String userAttributeName : portletApp.getUserAttributes()) {
				String userAttributeValue = userAttributes.getValue(
					userAttributeName);

				if (userAttributeValue != null) {
					userInfo.put(userAttributeName, userAttributeValue);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		Map<String, String> unmodifiableUserInfo = Collections.unmodifiableMap(
			(Map<String, String>)userInfo.clone());

		// Custom user attributes

		Map<String, CustomUserAttributes> customUserAttributesMap =
			new HashMap<>();

		Map<String, String> customUserAttributesClassNames =
			portletApp.getCustomUserAttributes();

		for (Map.Entry<String, String> entry :
				customUserAttributesClassNames.entrySet()) {

			String customUserAttributesClassName = entry.getValue();

			CustomUserAttributes customUserAttributes =
				customUserAttributesMap.get(customUserAttributesClassName);

			if (customUserAttributes == null) {
				if (portletApp.isWARFile()) {
					PortletContextBag portletContextBag =
						PortletContextBagPool.get(
							portletApp.getServletContextName());

					Map<String, CustomUserAttributes>
						portletContextBagCustomUserAttributes =
							portletContextBag.getCustomUserAttributes();

					customUserAttributes =
						portletContextBagCustomUserAttributes.get(
							customUserAttributesClassName);

					if (customUserAttributes != null) {
						customUserAttributes =
							(CustomUserAttributes)customUserAttributes.clone();
					}
				}
				else {
					customUserAttributes = _newInstance(
						customUserAttributesClassName);
				}

				if (customUserAttributes != null) {
					customUserAttributesMap.put(
						customUserAttributesClassName, customUserAttributes);
				}
			}

			if (customUserAttributes != null) {
				String userAttributeName = entry.getKey();

				String attrValue = customUserAttributes.getValue(
					userAttributeName, unmodifiableUserInfo);

				if (attrValue != null) {
					userInfo.put(userAttributeName, attrValue);
				}
			}
		}

		return userInfo;
	}

	private static CustomUserAttributes _newInstance(String className) {
		try {
			return (CustomUserAttributes)InstanceFactory.newInstance(className);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserInfoFactory.class);

}