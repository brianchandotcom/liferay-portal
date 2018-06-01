/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.base.SourceServiceBaseImpl;
import com.liferay.portal.reports.engine.console.service.permission.AdminResourcePermissionChecker;
import com.liferay.portal.reports.engine.console.service.permission.ReportsActionKeys;
import com.liferay.portal.reports.engine.console.service.permission.SourcePermissionChecker;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
public class SourceServiceImpl extends SourceServiceBaseImpl {

	@Override
	public Source addSource(
			long groupId, Map<Locale, String> nameMap, String driverClassName,
			String driverUrl, String driverUserName, String driverPassword,
			ServiceContext serviceContext)
		throws PortalException {

		AdminResourcePermissionChecker.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ReportsActionKeys.ADD_SOURCE);

		return sourceLocalService.addSource(
			getUserId(), groupId, nameMap, driverClassName, driverUrl,
			driverUserName, driverPassword, serviceContext);
	}

	@Override
	public Source deleteSource(long sourceId) throws PortalException {
		SourcePermissionChecker.check(
			getPermissionChecker(), sourceId, ActionKeys.DELETE);

		return sourceLocalService.deleteSource(sourceId);
	}

	@Override
	public Source getSource(long sourceId) throws PortalException {
		SourcePermissionChecker.check(
			getPermissionChecker(), sourceId, ActionKeys.VIEW);

		return sourceLocalService.getSource(sourceId);
	}

	@Override
	public List<Source> getSources(
			long groupId, String name, String driverUrl, boolean andSearch,
			int start, int end, OrderByComparator orderByComparator)
		throws PortalException {

		return sourceFinder.filterFindByG_N_DU(
			groupId, name, driverUrl, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getSourcesCount(
		long groupId, String name, String driverUrl, boolean andSearch) {

		return sourceFinder.filterCountByG_N_DU(
			groupId, name, driverUrl, andSearch);
	}

	@Override
	public Source updateSource(
			long sourceId, Map<Locale, String> nameMap, String driverClassName,
			String driverUrl, String driverUserName, String driverPassword,
			ServiceContext serviceContext)
		throws PortalException {

		SourcePermissionChecker.check(
			getPermissionChecker(), sourceId, ActionKeys.UPDATE);

		return sourceLocalService.updateSource(
			sourceId, nameMap, driverClassName, driverUrl, driverUserName,
			driverPassword, serviceContext);
	}

}