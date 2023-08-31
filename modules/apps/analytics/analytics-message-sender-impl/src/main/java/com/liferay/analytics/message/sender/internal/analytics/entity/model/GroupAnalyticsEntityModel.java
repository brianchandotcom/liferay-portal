/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "analytics.entity.model.type=group",
	service = AnalyticsEntityModel.class
)
public class GroupAnalyticsEntityModel extends BaseAnalyticsEntityModel<Group> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) throws Exception {
		return TransformUtil.transformToLongArray(
			user.getSiteGroups(), Group::getGroupId);
	}

	@Override
	public Group getModel(long id) throws Exception {
		return _groupLocalService.getGroup(id);
	}

	@Override
	public String getModelClassName() {
		return Group.class.getName();
	}

	@Override
	public boolean isExcluded(Group group) {
		if (!group.isSite()) {
			return true;
		}

		return false;
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(property.eq(true));
			});

		return actionableDynamicQuery;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "groupId";
	}

	private static final List<String> _attributeNames =
		Collections.singletonList("name");

	@Reference
	private GroupLocalService _groupLocalService;

}