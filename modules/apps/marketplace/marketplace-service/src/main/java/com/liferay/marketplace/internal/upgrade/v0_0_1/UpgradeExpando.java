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

package com.liferay.marketplace.internal.upgrade.v0_0_1;

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

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
		try (LoggingTimer loggingTimer = new LoggingTimer(
				String.valueOf(companyId))) {

			ExpandoTable expandoTable = null;

			try {
				expandoTable = _expandoTableLocalService.getTable(
					companyId, User.class.getName(), "MP");
			}
			catch (NoSuchTableException nste) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nste, nste);
				}

				return;
			}

			ExpandoColumn oldExpandoColumn =
				_expandoColumnLocalService.getColumn(
					companyId, User.class.getName(), expandoTable.getName(),
					"client-id");

			if (oldExpandoColumn == null) {
				return;
			}

			ExpandoColumn newExpandoColumn =
				_expandoColumnLocalService.getColumn(
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

			_expandoColumnLocalService.deleteColumn(
				oldExpandoColumn.getColumnId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeExpando.class);

	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;

}