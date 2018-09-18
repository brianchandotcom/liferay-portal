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

package com.liferay.layout.page.template.internal.upgrade.v1_1_0;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayoutPrototype extends UpgradeProcess {

	public UpgradeLayoutPrototype(CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		upgradeLayoutPrototype();
	}

	protected void upgradeLayoutPrototype() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("insert into LayoutPageTemplateEntry (uuid_, ");
		sb.append("layoutPageTemplateEntryId, groupId, companyId, userId, ");
		sb.append("userName, createDate, modifiedDate, ");
		sb.append("layoutPageTemplateCollectionId, name, type_, ");
		sb.append("layoutPrototypeId, status) values (?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from LayoutPrototype");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long layoutPrototypeId = rs.getLong("layoutPrototypeId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				String nameXML = rs.getString("name");

				Company company = _companyLocalService.getCompany(companyId);

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(nameXML);

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					LocalizationUtil.getDefaultLanguageId(nameXML));

				ps2.setString(1, PortalUUIDUtil.generate());
				ps2.setLong(2, increment());
				ps2.setLong(3, company.getGroupId());
				ps2.setLong(4, companyId);
				ps2.setLong(5, userId);
				ps2.setString(6, userName);
				ps2.setTimestamp(7, createDate);
				ps2.setTimestamp(8, createDate);
				ps2.setLong(9, 0);
				ps2.setString(10, nameMap.get(defaultLocale));
				ps2.setInt(
					11, LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE);
				ps2.setLong(12, layoutPrototypeId);
				ps2.setInt(13, WorkflowConstants.STATUS_APPROVED);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeLayoutPrototype.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false, false);
	}

	private final CompanyLocalService _companyLocalService;

}