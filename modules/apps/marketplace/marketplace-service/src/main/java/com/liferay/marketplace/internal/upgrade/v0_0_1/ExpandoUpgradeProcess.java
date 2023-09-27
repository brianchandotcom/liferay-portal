/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.util.List;

/**
 * @author Peter Shin
 */
public class ExpandoUpgradeProcess extends UpgradeProcess {

	public ExpandoUpgradeProcess(
		CompanyLocalService companyLocalService,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService) {

		_companyLocalService = companyLocalService;
		_expandoColumnLocalService = expandoColumnLocalService;
		_expandoTableLocalService = expandoTableLocalService;
		_expandoValueLocalService = expandoValueLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> _updateMPExpandoColumns(companyId));
	}

	private void _updateMPExpandoColumns(long companyId) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer(
				String.valueOf(companyId))) {

			ExpandoTable expandoTable = null;

			try {
				expandoTable = _expandoTableLocalService.getTable(
					companyId, User.class.getName(), "MP");
			}
			catch (NoSuchTableException noSuchTableException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchTableException);
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

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;

}