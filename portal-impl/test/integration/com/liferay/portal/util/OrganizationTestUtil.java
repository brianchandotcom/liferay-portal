/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Alberto Chaparro
 */
public class OrganizationTestUtil {

	public static Organization addOrganization() throws Exception {
		return addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			ServiceTestUtil.randomString(),
			OrganizationConstants.TYPE_REGULAR_ORGANIZATION, false);
	}

	public static Organization addOrganization(boolean site) throws Exception {
		return addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			ServiceTestUtil.randomString(),
			OrganizationConstants.TYPE_REGULAR_ORGANIZATION, site);
	}

	public static Organization addOrganization(
			long parentOrganizationId, String name, String type, boolean site)
		throws Exception {

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), false, null);

		return OrganizationLocalServiceUtil.addOrganization(
			user.getUserId(), parentOrganizationId, name, type, false, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			site, ServiceTestUtil.getServiceContext());
	}

}