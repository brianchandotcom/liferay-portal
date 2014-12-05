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

package com.liferay.portal.security.exportimport;

import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Raymond Augé
 */
public class UserExporterUtil {

	public static void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		_getInstance().exportUser(contact, contactExpandoAttributes);
	}

	public static void exportUser(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		_getInstance().exportUser(userId, userGroupId, userOperation);
	}

	public static void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		_getInstance().exportUser(user, userExpandoAttributes);
	}

	private static UserExporter _getInstance() {
		return _instance._serviceTracker.getService();
	}

	private UserExporterUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(UserExporter.class);

		_serviceTracker.open();
	}

	private static final UserExporterUtil _instance = new UserExporterUtil();

	private final ServiceTracker<UserExporter, UserExporter> _serviceTracker;

}