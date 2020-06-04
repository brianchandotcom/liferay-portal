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
import com.liferay.app.builder.service.base.AppBuilderAppDataRecordLinkLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderAppDataRecordLink",
	service = AopService.class
)
public class AppBuilderAppDataRecordLinkLocalServiceImpl
	extends AppBuilderAppDataRecordLinkLocalServiceBaseImpl {

	@Override
	public AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long companyId, long appBuilderAppId, long ddlRecordId) {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			appBuilderAppDataRecordLinkPersistence.create(
				counterLocalService.increment());

		appBuilderAppDataRecordLink.setCompanyId(companyId);
		appBuilderAppDataRecordLink.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppDataRecordLink.setDdlRecordId(ddlRecordId);

		return appBuilderAppDataRecordLinkPersistence.update(
			appBuilderAppDataRecordLink);
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

}