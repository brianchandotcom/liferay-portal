/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.base.SourceServiceBaseImpl;
import com.liferay.portal.reports.engine.console.service.permission.ReportsActionKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=reports",
		"json.web.service.context.path=Source"
	},
	service = AopService.class
)
public class SourceServiceImpl extends SourceServiceBaseImpl {

	@Override
	public Source addSource(
			long groupId, Map<Locale, String> nameMap, String driverClassName,
			String driverUrl, String driverUserName, String driverPassword,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ReportsActionKeys.ADD_SOURCE);

		return sourceLocalService.addSource(
			getUserId(), groupId, nameMap, driverClassName, driverUrl,
			driverUserName, driverPassword, serviceContext);
	}

	@Override
	public Source deleteSource(long sourceId) throws PortalException {
		_sourceModelResourcePermission.check(
			getPermissionChecker(), sourceId, ActionKeys.DELETE);

		return sourceLocalService.deleteSource(sourceId);
	}

	@Override
	public Source getSource(long sourceId) throws PortalException {
		_sourceModelResourcePermission.check(
			getPermissionChecker(), sourceId, ActionKeys.VIEW);

		return sourceLocalService.getSource(sourceId);
	}

	@Override
	public List<Source> getSources(
			long groupId, String name, String driverUrl, boolean andSearch,
			int start, int end, OrderByComparator<Source> orderByComparator)
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

		_sourceModelResourcePermission.check(
			getPermissionChecker(), sourceId, ActionKeys.UPDATE);

		return sourceLocalService.updateSource(
			sourceId, nameMap, driverClassName, driverUrl, driverUserName,
			driverPassword, serviceContext);
	}

	@Reference(
		target = "(resource.name=" + ReportsEngineConsoleConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.portal.reports.engine.console.model.Source)"
	)
	private ModelResourcePermission<Source> _sourceModelResourcePermission;

}