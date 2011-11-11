/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class CompanySharepointStorageImpl extends BaseSharepointStorageImpl {

	@Override
	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree foldersTree = new Tree();

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", new Long(sharepointRequest.getUserId()));

		List<Group> groups = GroupLocalServiceUtil.search(
			sharepointRequest.getCompanyId(), null, null, groupParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Group userGroup = GroupLocalServiceUtil.getUserGroup(
			sharepointRequest.getCompanyId(), sharepointRequest.getUserId());

		groups.add(userGroup);

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(
				sharepointRequest.getUserId());

		for (Organization organization : organizations) {
			groups.add(organization.getGroup());
		}

		for (Group group : groups) {
			String path = getGroupPath(group);

			foldersTree.addChild(getFolderTree(path));
		}

		foldersTree.addChild(getFolderTree(StringPool.BLANK));

		return foldersTree;
	}

	protected String getGroupPath(Group group) throws Exception {
		StringBundler sb = new StringBundler(5);

		String name = group.getName();

		if (group.isUser()) {
			User user = UserServiceUtil.getUserById(group.getClassPK());

			name = user.getFullName();
		}
		else if (group.isOrganization()) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					group.getOrganizationId());

			name = organization.getName();
		}

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_BRACKET);
		sb.append(group.getGroupId());
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

}