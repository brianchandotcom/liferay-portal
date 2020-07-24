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

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.app.builder.service.base.AppBuilderAppLocalServiceBaseImpl;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
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
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
			long groupId, long companyId, long userId, boolean active,
			long ddlRecordSetId, long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<Locale, String> nameMap, String scope)
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
		appBuilderApp.setActive(active);
		appBuilderApp.setDdlRecordSetId(ddlRecordSetId);
		appBuilderApp.setDdmStructureId(ddmStructureId);
		appBuilderApp.setDdmStructureLayoutId(ddmStructureLayoutId);
		appBuilderApp.setDeDataListViewId(deDataListViewId);
		appBuilderApp.setNameMap(nameMap);
		appBuilderApp.setScope(scope);

		_appBuilderAppVersionLocalService.addAppBuilderAppVersion(
			groupId, companyId, userId, appBuilderApp.getAppBuilderAppId(),
			ddlRecordSetId, ddmStructureId, ddmStructureLayoutId);

		return appBuilderAppPersistence.update(appBuilderApp);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAppBuilderApp(long, long, long, boolean, long, long,
	 *             long, Map, String)}
	 */
	@Deprecated
	@Override
	public AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, boolean active,
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<Locale, String> nameMap)
		throws PortalException {

		return addAppBuilderApp(
			groupId, companyId, userId, active, ddmStructureId,
			ddmStructureLayoutId, deDataListViewId, nameMap,
			AppBuilderAppConstants.SCOPE_STANDARD);
	}

	@Override
	public AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, boolean active,
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<Locale, String> nameMap, String scope)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			groupId, ddmStructure.getStructureKey());

		return addAppBuilderApp(
			groupId, companyId, userId, active, ddlRecordSet.getRecordSetId(),
			ddmStructureId, ddmStructureLayoutId, deDataListViewId, nameMap,
			scope);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public AppBuilderApp deleteAppBuilderApp(long appBuilderAppId)
		throws PortalException {

		List<AppBuilderAppDeployment> appBuilderAppDeployments =
			_appBuilderAppDeploymentLocalService.getAppBuilderAppDeployments(
				appBuilderAppId);

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				appBuilderAppDeployments) {

			_appBuilderAppDeploymentLocalService.deleteAppBuilderAppDeployment(
				appBuilderAppDeployment.getAppBuilderAppDeploymentId());
		}

		return appBuilderAppPersistence.remove(appBuilderAppId);
	}

	@Override
	public void deleteAppBuilderApps(long ddmStructureId)
		throws PortalException {

		List<AppBuilderApp> appBuilderApps = getAppBuilderApps(ddmStructureId);

		for (AppBuilderApp appBuilderApp : appBuilderApps) {
			appBuilderAppLocalService.deleteAppBuilderApp(
				appBuilderApp.getAppBuilderAppId());
		}
	}

	@Override
	public List<Long> getAppBuilderAppIds(boolean active, String type) {
		return appBuilderAppFinder.findByA_T(active, type);
	}

	@Override
	public List<AppBuilderApp> getAppBuilderApps(long ddmStructureId) {
		return appBuilderAppPersistence.findByDDMStructureId(ddmStructureId);
	}

	@Override
	public List<AppBuilderApp> getAppBuilderApps(
		long companyId, boolean active) {

		return appBuilderAppPersistence.findByC_A(companyId, active);
	}

	@Override
	public List<AppBuilderApp> getAppBuilderApps(
		long companyId, boolean active, String scope) {

		return appBuilderAppPersistence.findByC_A_S(companyId, active, scope);
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
	public List<AppBuilderApp> getAppBuilderApps(
		long groupId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return appBuilderAppPersistence.findByG_S(
			groupId, scope, start, end, orderByComparator);
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

	@Override
	public int getAppBuilderAppsCount(long groupId, String scope) {
		return appBuilderAppPersistence.countByG_S(groupId, scope);
	}

	@Override
	public List<AppBuilderApp> getCompanyAppBuilderApps(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return appBuilderAppPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<AppBuilderApp> getCompanyAppBuilderApps(
		long companyId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return appBuilderAppPersistence.findByC_S(
			companyId, scope, start, end, orderByComparator);
	}

	@Override
	public int getCompanyAppBuilderAppsCount(long companyId) {
		return appBuilderAppPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getCompanyAppBuilderAppsCount(long companyId, String scope) {
		return appBuilderAppPersistence.countByC_S(companyId, scope);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AppBuilderApp updateAppBuilderApp(
			long userId, long appBuilderAppId, boolean active,
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<Locale, String> nameMap)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AppBuilderApp appBuilderApp = appBuilderAppPersistence.findByPrimaryKey(
			appBuilderAppId);

		if (!Objects.equals(
				appBuilderApp.getDdmStructureLayoutId(),
				ddmStructureLayoutId)) {

			_appBuilderAppVersionLocalService.addAppBuilderAppVersion(
				appBuilderApp.getGroupId(), appBuilderApp.getCompanyId(),
				userId, appBuilderApp.getAppBuilderAppId(),
				appBuilderApp.getDdlRecordSetId(), ddmStructureId,
				ddmStructureLayoutId);
		}

		appBuilderApp.setUserId(user.getUserId());
		appBuilderApp.setUserName(user.getFullName());
		appBuilderApp.setModifiedDate(new Date());
		appBuilderApp.setActive(active);
		appBuilderApp.setDdmStructureId(ddmStructureId);
		appBuilderApp.setDdmStructureLayoutId(ddmStructureLayoutId);
		appBuilderApp.setDeDataListViewId(deDataListViewId);
		appBuilderApp.setNameMap(nameMap);

		return appBuilderAppPersistence.update(appBuilderApp);
	}

	@Reference
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}