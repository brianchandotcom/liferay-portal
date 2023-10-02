/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "analytics.entity.model.type=role",
	service = AnalyticsEntityModel.class
)
public class RoleAnalyticsEntityModel extends BaseAnalyticsEntityModel<Role> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) {
		return user.getRoleIds();
	}

	@Override
	public Role getModel(long id) throws Exception {
		return _roleLocalService.getRole(id);
	}

	@Override
	public String getModelClassName() {
		return Role.class.getName();
	}

	@Override
	public boolean isExcluded(Role role) {
		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return false;
		}

		return true;
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			_roleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(
					nameProperty.ne(RoleConstants.ANALYTICS_ADMINISTRATOR));

				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(typeProperty.eq(RoleConstants.TYPE_REGULAR));
			});

		return actionableDynamicQuery;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "roleId";
	}

	private static final List<String> _attributeNames =
		Collections.singletonList("name");

	@Reference
	private RoleLocalService _roleLocalService;

}