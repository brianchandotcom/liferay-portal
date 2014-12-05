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

import com.liferay.portal.model.User;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class UserImporterUtil {

	public static User importUser(
			long ldapServerId, long companyId, String emailAddress,
			String screenName)
		throws Exception {

		return _getInstance().importUser(
			ldapServerId, companyId, emailAddress, screenName);
	}

	public static User importUser(
			long companyId, String emailAddress, String screenName)
		throws Exception {

		return _getInstance().importUser(companyId, emailAddress, screenName);
	}

	public static User importUserByScreenName(long companyId, String screenName)
		throws Exception {

		return _getInstance().importUserByScreenName(companyId, screenName);
	}

	public static void importUsers() throws Exception {
		_getInstance().importUsers();
	}

	public static void importUsers(long companyId) throws Exception {
		_getInstance().importUsers(companyId);
	}

	public static void importUsers(long ldapServerId, long companyId)
		throws Exception {

		_getInstance().importUsers(ldapServerId, companyId);
	}

	private static UserImporter _getInstance() {
		return _instance._serviceTracker.getService();
	}

	private UserImporterUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(UserImporter.class);

		_serviceTracker.open();
	}

	private static final UserImporterUtil _instance = new UserImporterUtil();

	private final ServiceTracker<UserImporter, UserImporter> _serviceTracker;

}