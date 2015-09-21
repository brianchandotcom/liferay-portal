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

package com.liferay.users.admin.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class UserStagedModelDataHandler
	extends BaseStagedModelDataHandler<User> {

	public static final String[] CLASS_NAMES = {User.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User user = UserLocalServiceUtil.fetchUserByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (user != null) {
			deleteStagedModel(user);
		}
	}

	@Override
	public void deleteStagedModel(User user) throws PortalException {
		UserLocalServiceUtil.deleteUser(user);
	}

	@Override
	public List<User> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<User> users = new ArrayList<>();

		users.add(
			UserLocalServiceUtil.fetchUserByUuidAndCompanyId(uuid, companyId));

		return users;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(User user) {
		return user.getFullName();
	}

	@Override
	protected void doExportStagedModel(
		PortletDataContext portletDataContext, User user) {
	}

	@Override
	protected void doImportStagedModel(
		PortletDataContext portletDataContext, User user) {
	}

}