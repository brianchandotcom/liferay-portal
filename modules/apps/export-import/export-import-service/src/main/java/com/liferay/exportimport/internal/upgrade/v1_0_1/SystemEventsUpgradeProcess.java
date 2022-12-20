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

package com.liferay.exportimport.internal.upgrade.v1_0_1;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alberto Chaparro
 */
public class SystemEventsUpgradeProcess extends UpgradeProcess {

	public SystemEventsUpgradeProcess(
		GroupLocalService groupLocalService,
		SystemEventLocalService systemEventLocalService) {

		_groupLocalService = groupLocalService;
		_systemEventLocalService = systemEventLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property liveGroupIdProperty = PropertyFactoryUtil.forName(
					"liveGroupId");
				Property remoteStagingGroupCountProperty =
					PropertyFactoryUtil.forName("remoteStagingGroupCount");

				dynamicQuery.add(
					RestrictionsFactoryUtil.or(
						liveGroupIdProperty.ne(0L),
						remoteStagingGroupCountProperty.gt(0)));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(Group group) -> {
				long liveGroupId = group.getLiveGroupId();

				if (liveGroupId == 0) {
					liveGroupId = group.getGroupId();
				}

				if (!_systemEventLocalService.validateGroup(liveGroupId)) {
					_systemEventLocalService.deleteSystemEvents(liveGroupId);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private final GroupLocalService _groupLocalService;
	private final SystemEventLocalService _systemEventLocalService;

}