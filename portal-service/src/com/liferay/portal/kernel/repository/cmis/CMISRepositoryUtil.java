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

package com.liferay.portal.kernel.repository.cmis;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Map;

/**
 * @author Alexander Chow
 */
public class CMISRepositoryUtil {

	public static void checkRepository(
		long repositoryId, Map<String, String> parameters,
		UnicodeProperties typeSettingsProperties, String typeSettingsKey) {

		try {
			PortalClassInvoker.invoke(
				_checkRepositoryMethodKey, repositoryId, parameters,
				typeSettingsProperties, typeSettingsKey);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static Session createSession(Map<String, String> parameters)
		throws RepositoryException {

		Session session = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_createSessionMethodKey, parameters);

			if (returnObj != null) {
				session = (Session)returnObj;
			}
		}
		catch (RepositoryException re) {
			throw re;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return session;
	}

	public static String getTypeSettingsValue(
		UnicodeProperties typeSettingsProperties, String typeSettingsKey) {

		String value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				_getTypeSettingsValueMethodKey, typeSettingsProperties,
				typeSettingsKey);

			if (returnObj != null) {
				value = (String)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.repository.cmis.CMISRepositoryUtil";

	private static final Log _log = LogFactoryUtil.getLog(
		CMISRepositoryUtil.class);

	private static final MethodKey _checkRepositoryMethodKey = new MethodKey(
		ClassResolverUtil.resolveByPortalClassLoader(_CLASS_NAME),
		"checkRepository", long.class, Map.class, UnicodeProperties.class,
		String.class);
	private static final MethodKey _createSessionMethodKey = new MethodKey(
		ClassResolverUtil.resolveByPortalClassLoader(_CLASS_NAME),
		"createSession", Map.class);
	private static final MethodKey _getTypeSettingsValueMethodKey =
		new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(_CLASS_NAME),
			"getTypeSettingsValue", UnicodeProperties.class, String.class);

}