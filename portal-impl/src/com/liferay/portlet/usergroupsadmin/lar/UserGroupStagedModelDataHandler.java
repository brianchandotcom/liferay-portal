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

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;

/**
 * @author David Mendez Gonzalez
 */
public class UserGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<UserGroup> {

	public static final String NAMESPACE = "usergroup";

	@Override
	public String getClassName() {
		return UserGroup.class.getName();
	}

	protected void doExportStagedModel(
			PortletDataContext portletDataContext, UserGroup userGroup)
		throws Exception {

		Element userGroupElement =
			portletDataContext.getExportDataStagedModelElement(userGroup);

		portletDataContext.addClassedModel(
			userGroupElement, StagedModelPathUtil.getPath(userGroup), userGroup,
			UserGroupsAdminPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, UserGroup userGroup)
		throws Exception {

		long userId = portletDataContext.getUserId(userGroup.getUserUuid());

		long companyId = portletDataContext.getCompanyId();

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			userGroup, UserGroupsAdminPortletDataHandler.NAMESPACE);

		UserGroup existingUserGroup =
			UserGroupLocalServiceUtil.fetchUserGroupByUuidAndCompanyId(
				userGroup.getUuid(), companyId);

		if (existingUserGroup == null) {
			existingUserGroup = UserGroupLocalServiceUtil.fetchUserGroup(
				companyId, userGroup.getName());
		}

		UserGroup importedUserGroup = null;

		if (existingUserGroup == null) {
			serviceContext.setUuid(userGroup.getUuid());

			importedUserGroup = UserGroupLocalServiceUtil.addUserGroup(
				userId, companyId, userGroup.getName(),
				userGroup.getDescription(), serviceContext);
		}
		else {
			importedUserGroup = UserGroupLocalServiceUtil.updateUserGroup(
				companyId, existingUserGroup.getUserGroupId(),
				userGroup.getName(), userGroup.getDescription(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			userGroup, importedUserGroup,
			UserGroupsAdminPortletDataHandler.NAMESPACE);
	}

}