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

package com.liferay.marketplace.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.exception.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;

import java.util.List;

/**
 * @author Peter Shin
 */
public class UpgradeExpando extends UpgradeProcess {

	public UpgradeExpando(
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
		_expandoTableLocalService = expandoTableLocalService;
		_expandoValueLocalService = expandoValueLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (long companyId : PortalUtil.getCompanyIds()) {
			updateMPExpandoColumns(companyId);
		}
	}

	protected void updateMPExpandoColumns(long companyId) throws Exception {
		ExpandoTable expandoTable = null;

		try {
			expandoTable = _expandoTableLocalService.getTable(
				companyId, User.class.getName(), "MP");
		}
		catch (NoSuchTableException nste) {
			return;
		}

		ExpandoColumn oldExpandoColumn = _expandoColumnLocalService.getColumn(
			companyId, User.class.getName(), expandoTable.getName(),
			"client-id");

		if (oldExpandoColumn == null) {
			return;
		}

		ExpandoColumn newExpandoColumn = _expandoColumnLocalService.getColumn(
			companyId, User.class.getName(), expandoTable.getName(),
			"clientID");

		if (newExpandoColumn == null) {
			newExpandoColumn = _expandoColumnLocalService.updateColumn(
				oldExpandoColumn.getColumnId(), "clientID",
				ExpandoColumnConstants.STRING);
		}

		List<ExpandoValue> expandoValues =
			_expandoValueLocalService.getColumnValues(
				oldExpandoColumn.getColumnId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ExpandoValue expandoValue : expandoValues) {
			_expandoValueLocalService.addValue(
				expandoValue.getCompanyId(), User.class.getName(),
				expandoTable.getName(), newExpandoColumn.getName(),
				expandoValue.getClassPK(), expandoValue.getString());
		}

		_expandoColumnLocalService.deleteColumn(oldExpandoColumn.getColumnId());
	}

	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;

}