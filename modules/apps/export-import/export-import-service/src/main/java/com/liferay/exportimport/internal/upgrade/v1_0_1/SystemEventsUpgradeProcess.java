/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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