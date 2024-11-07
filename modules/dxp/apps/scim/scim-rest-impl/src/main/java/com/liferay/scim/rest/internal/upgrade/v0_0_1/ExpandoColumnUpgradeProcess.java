/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.upgrade.v0_0_1;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Christian Moura
 */
public class ExpandoColumnUpgradeProcess extends UpgradeProcess {

	public ExpandoColumnUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
		_expandoColumnLocalService = expandoColumnLocalService;
		_expandoTableLocalService = expandoTableLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				try (LoggingTimer loggingTimer = new LoggingTimer(
						String.valueOf(companyId))) {

					_updateExpandoColumn(companyId, User.class.getName());
					_updateExpandoColumn(companyId, UserGroup.class.getName());
				}
			});
	}

	private void _updateExpandoColumn(long companyId, String className) {
		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			companyId, _classNameLocalService.getClassNameId(className),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (expandoTable == null) {
			return;
		}

		ExpandoColumn expandoColumn = _expandoColumnLocalService.fetchColumn(
			expandoTable.getTableId(), "scimClientId");

		if (expandoColumn == null) {
			return;
		}

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.PROPERTY_HIDDEN, Boolean.TRUE.toString());

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		_expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;
	private final ExpandoColumnLocalService _expandoColumnLocalService;
	private final ExpandoTableLocalService _expandoTableLocalService;

}