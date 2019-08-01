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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.base.AppBuilderAppLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderApp",
	service = AopService.class
)
public class AppBuilderAppLocalServiceImpl
	extends AppBuilderAppLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			Map<Locale, String> nameMap, String settings)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AppBuilderApp appBuilderApp = appBuilderAppPersistence.create(
			counterLocalService.increment());

		appBuilderApp.setGroupId(groupId);
		appBuilderApp.setCompanyId(companyId);
		appBuilderApp.setUserId(user.getUserId());
		appBuilderApp.setUserName(user.getFullName());
		appBuilderApp.setCreateDate(new Date());
		appBuilderApp.setModifiedDate(new Date());
		appBuilderApp.setDdmStructureId(ddmStructureId);
		appBuilderApp.setDdmStructureLayoutId(ddmStructureLayoutId);
		appBuilderApp.setDeDataListViewId(deDataListViewId);
		appBuilderApp.setNameMap(nameMap);
		appBuilderApp.setSettings(settings);

		return appBuilderAppPersistence.update(appBuilderApp);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AppBuilderApp updateAppBuilderApp(
			long userId, long appId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			Map<Locale, String> nameMap, String settings)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AppBuilderApp appBuilderApp = appBuilderAppPersistence.findByPrimaryKey(
			appId);

		appBuilderApp.setUserId(user.getUserId());
		appBuilderApp.setUserName(user.getFullName());
		appBuilderApp.setModifiedDate(new Date());
		appBuilderApp.setDdmStructureId(ddmStructureId);
		appBuilderApp.setDdmStructureLayoutId(ddmStructureLayoutId);
		appBuilderApp.setDeDataListViewId(deDataListViewId);
		appBuilderApp.setNameMap(nameMap);
		appBuilderApp.setSettings(settings);

		return appBuilderAppPersistence.update(appBuilderApp);
	}

}