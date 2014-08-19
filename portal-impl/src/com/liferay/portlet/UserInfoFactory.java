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
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

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
		HttpServletRequest request, Portlet portlet) {

		if (request.getRemoteUser() == null) {
			return null;
		}

		LinkedHashMap<String, String> userInfo =
			new LinkedHashMap<String, String>();

		try {
			User user = PortalUtil.getUser(request);

			userInfo = getUserInfo(user, userInfo, portlet);
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

		LinkedHashMap<String, String> userInfo =
			new LinkedHashMap<String, String>();

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

			// Portlet user attributes

			for (String attrName : portletApp.getUserAttributes()) {
				String attrValue = userAttributes.getValue(attrName);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		Map<String, String> unmodifiableUserInfo = Collections.unmodifiableMap(
			(Map<String, String>)userInfo.clone());

		// Custom user attributes

		Map<String, CustomUserAttributes> cuaInstances =
			new HashMap<String, CustomUserAttributes>();

		for (Map.Entry<String, String> entry :
				portletApp.getCustomUserAttributes().entrySet()) {

			String attrName = entry.getKey();
			String attrCustomClass = entry.getValue();

			CustomUserAttributes cua = cuaInstances.get(attrCustomClass);

			if (cua == null) {
				if (portletApp.isWARFile()) {
					PortletContextBag portletContextBag =
						PortletContextBagPool.get(
							portletApp.getServletContextName());

					Map<String, CustomUserAttributes> customUserAttributes =
						portletContextBag.getCustomUserAttributes();

					cua = customUserAttributes.get(attrCustomClass);

					cua = (CustomUserAttributes)cua.clone();
				}
				else {
					try {
						cua = (CustomUserAttributes)InstanceFactory.newInstance(
							attrCustomClass);
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

				cuaInstances.put(attrCustomClass, cua);
			}

			if (cua != null) {
				String attrValue = cua.getValue(attrName, unmodifiableUserInfo);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}

		return userInfo;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserInfoFactory.class);

}