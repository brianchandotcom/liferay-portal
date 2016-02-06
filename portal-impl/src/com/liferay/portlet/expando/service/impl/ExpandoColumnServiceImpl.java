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

package com.liferay.portlet.expando.service.impl;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.permission.ExpandoColumnPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portlet.expando.service.base.ExpandoColumnServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnServiceImpl extends ExpandoColumnServiceBaseImpl {

	@Override
	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);

		return expandoColumnLocalService.addColumn(tableId, name, type);
	}

	@Override
	public ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);

		return expandoColumnLocalService.addColumn(
			tableId, name, type, defaultData);
	}

	@Override
	public void deleteColumn(long columnId) throws PortalException {
		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), columnId, ActionKeys.DELETE);

		expandoColumnLocalService.deleteColumn(columnId);
	}

	@Override
	public ExpandoColumn fetchExpandoColumn(long columnId)
		throws PortalException {

		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), columnId, ActionKeys.VIEW);

		return expandoColumnLocalService.fetchExpandoColumn(columnId);
	}

	@Override
	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException {

		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	@Override
	public ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException {

		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateColumn(
			columnId, name, type, defaultData);
	}

	@Override
	public ExpandoColumn updateTypeSettings(long columnId, String typeSettings)
		throws PortalException {

		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateTypeSettings(
			columnId, typeSettings);
	}

}