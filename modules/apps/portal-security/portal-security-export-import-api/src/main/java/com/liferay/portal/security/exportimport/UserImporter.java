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

import com.liferay.portal.kernel.model.User;

/**
 * @author Michael C. Han
 */
public interface UserImporter {

	public long getLastImportTime() throws Exception;

	public User importUser(
			long ldapServerId, long companyId, String emailAddress,
			String screenName)
		throws Exception;

	public User importUser(
			long companyId, String emailAddress, String screenName)
		throws Exception;

	public User importUserByScreenName(long companyId, String screenName)
		throws Exception;

	public void importUsers() throws Exception;

	public void importUsers(long companyId) throws Exception;

	public void importUsers(long ldapServerId, long companyId) throws Exception;

}