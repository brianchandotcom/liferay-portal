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

package com.liferay.site.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

/**
 * @author Jürgen Kappler
 */
public class ActionUtil {

	public static List<Group> getGroups(ResourceRequest request)
		throws Exception {

		long[] groupIds = ParamUtil.getLongValues(request, "rowIds");

		List<Group> groups = new ArrayList<>();

		for (long groupId : groupIds) {
			groups.add(GroupServiceUtil.getGroup(groupId));
		}

		return groups;
	}

}