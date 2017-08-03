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

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.service.base.DefinitionServiceBaseImpl;
import com.liferay.portal.reports.engine.console.service.permission.AdminResourcePermissionChecker;
import com.liferay.portal.reports.engine.console.service.permission.DefinitionPermissionChecker;

import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
public class DefinitionServiceImpl extends DefinitionServiceBaseImpl {

	@Override
	public Definition addDefinition(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long sourceId,
			String reportParameters, String fileName, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		AdminResourcePermissionChecker.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_DEFINITION);

		return definitionLocalService.addDefinition(
			getUserId(), groupId, nameMap, descriptionMap, sourceId,
			reportParameters, fileName, inputStream, serviceContext);
	}

	@Override
	public Definition deleteDefinition(long definitionId)
		throws PortalException {

		DefinitionPermissionChecker.check(
			getPermissionChecker(), definitionId, ActionKeys.DELETE);

		return definitionLocalService.deleteDefinition(definitionId);
	}

	@Override
	public Definition getDefinition(long definitionId) throws PortalException {
		DefinitionPermissionChecker.check(
			getPermissionChecker(), definitionId, ActionKeys.VIEW);

		return definitionLocalService.getDefinition(definitionId);
	}

	@Override
	public List<Definition> getDefinitions(
			long groupId, String definitionName, String description,
			String sourceId, String reportName, boolean andSearch, int start,
			int end, OrderByComparator orderByComparator)
		throws PortalException {

		return definitionFinder.filterFindByG_S_N_D_RN(
			groupId, definitionName, description, GetterUtil.getLong(sourceId),
			reportName, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getDefinitionsCount(
		long groupId, String definitionName, String description,
		String sourceId, String reportName, boolean andSearch) {

		return definitionFinder.filterCountByG_S_N_D_RN(
			groupId, definitionName, description, GetterUtil.getLong(sourceId),
			reportName, andSearch);
	}

	@Override
	public Definition updateDefinition(
			long definitionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long sourceId,
			String reportParameters, String fileName, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		DefinitionPermissionChecker.check(
			getPermissionChecker(), definitionId, ActionKeys.UPDATE);

		return definitionLocalService.updateDefinition(
			definitionId, nameMap, descriptionMap, sourceId, reportParameters,
			fileName, inputStream, serviceContext);
	}

}