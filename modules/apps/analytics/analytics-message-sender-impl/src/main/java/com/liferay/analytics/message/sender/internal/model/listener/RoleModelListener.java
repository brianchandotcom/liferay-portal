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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class RoleModelListener extends BaseEntityModelListener<Role> {

	@Override
	public List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) {
		return user.getRoleIds();
	}

	@Override
	public String getModelClassName() {
		return Role.class.getName();
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
	protected Role getModel(long id) throws Exception {
		return _roleLocalService.getRole(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "roleId";
	}

	@Override
	protected boolean isExcluded(Role role) {
		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return false;
		}

		return true;
	}

	private static final List<String> _attributeNames =
		Collections.singletonList("name");

	@Reference
	private RoleLocalService _roleLocalService;

}