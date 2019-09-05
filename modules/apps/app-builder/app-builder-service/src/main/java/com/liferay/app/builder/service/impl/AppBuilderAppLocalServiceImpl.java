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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;
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
			Map<Locale, String> nameMap, int status)
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
		appBuilderApp.setStatus(status);

		return appBuilderAppPersistence.update(appBuilderApp);
	}

	@Override
	public List<Long> getAppBuilderAppIds(int status, String type) {
		return appBuilderAppFinder.findByS_T(status, type);
	}

	@Override
	public List<AppBuilderApp> getAppBuilderApps(long companyId, int status) {
		return appBuilderAppPersistence.findByC_S(companyId, status);
	}

	@Override
	public List<AppBuilderApp> getAppBuilderApps(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return appBuilderAppPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<AppBuilderApp> getAppBuilderApps(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return appBuilderAppPersistence.findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	@Override
	public int getAppBuilderAppsCount(long groupId) {
		return appBuilderAppPersistence.countByGroupId(groupId);
	}

	@Override
	public int getAppBuilderAppsCount(
		long groupId, long companyId, long ddmStructureId) {

		return appBuilderAppPersistence.countByG_C_D(
			groupId, companyId, ddmStructureId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AppBuilderApp updateAppBuilderApp(
			long userId, long appBuilderAppId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			Map<Locale, String> nameMap, int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AppBuilderApp appBuilderApp = appBuilderAppPersistence.findByPrimaryKey(
			appBuilderAppId);

		appBuilderApp.setUserId(user.getUserId());
		appBuilderApp.setUserName(user.getFullName());
		appBuilderApp.setModifiedDate(new Date());
		appBuilderApp.setDdmStructureId(ddmStructureId);
		appBuilderApp.setDdmStructureLayoutId(ddmStructureLayoutId);
		appBuilderApp.setDeDataListViewId(deDataListViewId);
		appBuilderApp.setNameMap(nameMap);
		appBuilderApp.setStatus(status);

		return appBuilderAppPersistence.update(appBuilderApp);
	}

}