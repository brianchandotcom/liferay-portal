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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayoutPrototype extends UpgradeProcess {

	public UpgradeLayoutPrototype(
		CompanyLocalService companyLocalService,
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_companyLocalService = companyLocalService;
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
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
			PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString())) {

			List<LayoutPrototype> layoutPrototypes =
				_layoutPrototypeLocalService.getLayoutPrototypes(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (LayoutPrototype layoutPrototype : layoutPrototypes) {
				Date createDate = layoutPrototype.getCreateDate();
				String nameXML = layoutPrototype.getName();

				Company company = _companyLocalService.getCompany(
					layoutPrototype.getCompanyId());

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(nameXML);

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					LocalizationUtil.getDefaultLanguageId(nameXML));

				ps.setString(1, PortalUUIDUtil.generate());
				ps.setLong(2, increment());
				ps.setLong(3, company.getGroupId());
				ps.setLong(4, layoutPrototype.getCompanyId());
				ps.setLong(5, layoutPrototype.getUserId());
				ps.setString(6, layoutPrototype.getUserName());
				ps.setDate(7, new java.sql.Date(createDate.getTime()));
				ps.setDate(8, new java.sql.Date(createDate.getTime()));
				ps.setLong(9, 0);
				ps.setString(10, nameMap.get(defaultLocale));
				ps.setInt(
					11, LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE);
				ps.setLong(12, layoutPrototype.getLayoutPrototypeId());
				ps.setInt(13, WorkflowConstants.STATUS_APPROVED);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeLayoutPrototype.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private final CompanyLocalService _companyLocalService;
	private final LayoutPrototypeLocalService _layoutPrototypeLocalService;

}