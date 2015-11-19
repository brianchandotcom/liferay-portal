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

package com.liferay.product.navigation.site.administration.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.SessionClicks;

import javax.servlet.http.HttpSession;

/**
 * @author Julio Camarero
 */
public class LatentGroupManager {

	public static Group getLatentGroup(HttpSession session) {
		long groupId = GetterUtil.getLong(
			SessionClicks.get(session, _LATENT_GROUP_KEY, null));

		if (groupId > 0) {
			return GroupLocalServiceUtil.fetchGroup(groupId);
		}

		return null;
	}

	public static void setLatentGroup(HttpSession session, Group group) {
		SessionClicks.put(
			session, _LATENT_GROUP_KEY, String.valueOf(group.getGroupId()));
	}

	private static final String _LATENT_GROUP_KEY =
		"com.liferay.product.navigation.site.administration.util_latentGroup";

}