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

package com.liferay.app.builder.service.impl;

import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.app.builder.service.base.AppBuilderAppDataRecordLinkLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderAppDataRecordLink",
	service = AopService.class
)
public class AppBuilderAppDataRecordLinkLocalServiceImpl
	extends AppBuilderAppDataRecordLinkLocalServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAppBuilderAppDataRecordLink(long, long, long, long, long)}
	 */
	@Deprecated
	@Override
	public AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long companyId, long appBuilderAppId, long ddlRecordId) {

		AppBuilderAppVersion appBuilderAppVersion =
			_appBuilderAppVersionLocalService.fetchLatestAppBuilderAppVersion(
				appBuilderAppId);

		return addAppBuilderAppDataRecordLink(
			appBuilderAppVersion.getGroupId(), companyId, appBuilderAppId,
			appBuilderAppVersion.getAppBuilderAppVersionId(), ddlRecordId);
	}

	@Override
	public AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long groupId, long companyId, long appBuilderAppId,
		long appBuilderAppVersionId, long ddlRecordId) {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			appBuilderAppDataRecordLinkPersistence.create(
				counterLocalService.increment());

		appBuilderAppDataRecordLink.setGroupId(groupId);
		appBuilderAppDataRecordLink.setCompanyId(companyId);
		appBuilderAppDataRecordLink.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppDataRecordLink.setAppBuilderAppVersionId(
			appBuilderAppVersionId);
		appBuilderAppDataRecordLink.setDdlRecordId(ddlRecordId);

		return appBuilderAppDataRecordLinkPersistence.update(
			appBuilderAppDataRecordLink);
	}

	@Override
	public AppBuilderAppDataRecordLink
		fetchDDLRecordAppBuilderAppDataRecordLink(long ddlRecordId) {

		return appBuilderAppDataRecordLinkPersistence.fetchByDDLRecordId(
			ddlRecordId);
	}

	@Override
	public List<AppBuilderAppDataRecordLink> getAppBuilderAppDataRecordLinks(
		long appBuilderAppId) {

		return appBuilderAppDataRecordLinkPersistence.findByAppBuilderAppId(
			appBuilderAppId);
	}

	@Override
	public AppBuilderAppDataRecordLink getDDLRecordAppBuilderAppDataRecordLink(
			long ddlRecordId)
		throws PortalException {

		return appBuilderAppDataRecordLinkPersistence.findByDDLRecordId(
			ddlRecordId);
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

}