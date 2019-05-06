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

package com.liferay.exportimport.internal.verify.system.event;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Akos Thurzo
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.systemevent.internal.verify",
	service = VerifyProcess.class
)
@Deprecated
public class SystemEventVerifyProcess extends VerifyProcess {

	protected void deleteInvalidSystemEvents() throws PortalException {
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

	@Override
	protected void doVerify() throws Exception {
		deleteInvalidSystemEvents();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SystemEventLocalService _systemEventLocalService;

}