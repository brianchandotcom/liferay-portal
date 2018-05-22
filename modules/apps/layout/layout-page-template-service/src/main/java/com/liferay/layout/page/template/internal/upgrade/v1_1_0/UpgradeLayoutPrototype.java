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
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayoutPrototype extends UpgradeProcess {

	public UpgradeLayoutPrototype(
		CompanyLocalService companyLocalService,
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_companyLocalService = companyLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeLayoutPrototype();
	}

	protected void upgradeLayoutPrototype() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from LayoutPrototype");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long layoutPrototypeId = rs.getLong("layoutPrototypeId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String nameXML = rs.getString("name");

				LayoutPageTemplateEntry layoutPageTemplateEntry =
					_layoutPageTemplateEntryLocalService.
						fetchFirstLayoutPageTemplateEntry(layoutPrototypeId);

				if (layoutPageTemplateEntry != null) {
					continue;
				}

				Company company = _companyLocalService.getCompany(companyId);

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(nameXML);

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					LocalizationUtil.getDefaultLanguageId(nameXML));

				_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
					userId, company.getGroupId(), 0, nameMap.get(defaultLocale),
					LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE,
					layoutPrototypeId, WorkflowConstants.STATUS_APPROVED,
					new ServiceContext());
			}
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}