/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "analytics.entity.model.type=userGroup",
	service = AnalyticsEntityModel.class
)
public class UserGroupAnalyticsEntityModel
	extends BaseAnalyticsEntityModel<UserGroup> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) {
		return user.getUserGroupIds();
	}

	@Override
	public UserGroup getModel(long id) throws Exception {
		return _userGroupLocalService.getUserGroup(id);
	}

	@Override
	public String getModelClassName() {
		return UserGroup.class.getName();
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return _userGroupLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userGroupId";
	}

	private static final List<String> _attributeNames =
		Collections.singletonList("name");

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}