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

package com.liferay.portlet.tck.bridge.setup;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Vernon Singleton
 */
public class ServiceUtil {

	public static Group addActiveOpenGroup(long userId, String name)
		throws Exception {

		boolean active = true;
		String friendlyURL =
			"/" + StringUtil.toLowerCase(name).replaceAll(" ", "-");
		boolean siteFlag = true;
		int type = GroupConstants.TYPE_SITE_OPEN;
		boolean manualMembership = false;
		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		return GroupLocalServiceUtil.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, (String)null, 0L,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, name, type,
			manualMembership, membershipRestriction, friendlyURL, siteFlag,
			active, new ServiceContext());
	}

	public static Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return LayoutLocalServiceUtil.addLayout(
			userId, groupId, privateLayout, parentLayoutId, name, title,
			description, type, hidden, friendlyURL, serviceContext);
	}

}