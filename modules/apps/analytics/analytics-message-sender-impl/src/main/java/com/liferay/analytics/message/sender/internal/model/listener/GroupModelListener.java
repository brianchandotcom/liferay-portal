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
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class GroupModelListener extends BaseEntityModelListener<Group> {

	@Override
	public List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) throws Exception {
		List<Group> groups = user.getSiteGroups();

		Stream<Group> stream = groups.stream();

		return stream.mapToLong(
			Group::getGroupId
		).toArray();
	}

	@Override
	public String getModelClassName() {
		return Group.class.getName();
	}

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		updateConfigurationProperties(
			group.getCompanyId(), "syncedGroupIds",
			String.valueOf(group.getGroupId()), "liferayAnalyticsGroupIds");
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
	protected Group getModel(long id) throws Exception {
		return _groupLocalService.getGroup(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "groupId";
	}

	@Override
	protected boolean isExcluded(Group group) {
		if (!group.isSite()) {
			return true;
		}

		return false;
	}

	private static final List<String> _attributeNames =
		Collections.singletonList("name");

	@Reference
	private GroupLocalService _groupLocalService;

}