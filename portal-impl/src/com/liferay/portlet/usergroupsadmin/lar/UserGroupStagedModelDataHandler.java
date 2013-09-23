/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usergroupsadmin.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;

/**
 * @author David Mendez Gonzalez
 */
public class UserGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<UserGroup> {

	public static final String[] CLASS_NAMES = {UserGroup.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UserGroup userGroup =
			UserGroupLocalServiceUtil.fetchUserGroupByUuidAndCompanyId(
				uuid, group.getCompanyId());

		if (userGroup != null) {
			UserGroupLocalServiceUtil.deleteUserGroup(userGroup);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(UserGroup userGroup) {
		return userGroup.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, UserGroup userGroup)
		throws Exception {

		Element userGroupElement = portletDataContext.getExportDataElement(
			userGroup);

		portletDataContext.addClassedModel(
			userGroupElement, ExportImportPathUtil.getModelPath(userGroup),
			userGroup);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, UserGroup userGroup)
		throws Exception {

		long userId = portletDataContext.getUserId(userGroup.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			userGroup);

		UserGroup existingUserGroup =
			UserGroupLocalServiceUtil.fetchUserGroupByUuidAndCompanyId(
				userGroup.getUuid(), portletDataContext.getCompanyId());

		if (existingUserGroup == null) {
			existingUserGroup = UserGroupLocalServiceUtil.fetchUserGroup(
				portletDataContext.getCompanyId(), userGroup.getName());
		}

		UserGroup importedUserGroup = null;

		if (existingUserGroup == null) {
			serviceContext.setUuid(userGroup.getUuid());

			importedUserGroup = UserGroupLocalServiceUtil.addUserGroup(
				userId, portletDataContext.getCompanyId(), userGroup.getName(),
				userGroup.getDescription(), serviceContext);
		}
		else {
			importedUserGroup = UserGroupLocalServiceUtil.updateUserGroup(
				portletDataContext.getCompanyId(),
				existingUserGroup.getUserGroupId(), userGroup.getName(),
				userGroup.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(userGroup, importedUserGroup);
	}

}