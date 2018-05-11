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

package com.liferay.layout.page.template.internal.upgrade.v1_0_0;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
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
		LayoutPageTemplateCollectionLocalService
			layoutPageTemplateCollectionLocalService,
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_companyLocalService = companyLocalService;
		_layoutPageTemplateCollectionLocalService =
			layoutPageTemplateCollectionLocalService;
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
				"SELECT * FROM LayoutPrototype");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long layoutPrototypeId = rs.getLong("layoutPrototypeId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String nameXML = rs.getString("name");

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(nameXML);

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					LocalizationUtil.getDefaultLanguageId(nameXML));

				String name = nameMap.get(defaultLocale);

				Company company = _companyLocalService.getCompany(companyId);

				long layoutPageTemplateCollectionId =
					_getLayoutPageTemplateCollectionId(
						company, userId, defaultLocale);

				_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
					userId, company.getGroupId(),
					layoutPageTemplateCollectionId, name,
					LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE,
					new long[0], WorkflowConstants.STATUS_APPROVED,
					layoutPrototypeId, new ServiceContext());
			}
		}
	}

	private long _getLayoutPageTemplateCollectionId(
			Company company, long userId, Locale locale)
		throws Exception {

		if (_layoutPageTemplateCollection != null) {
			return _layoutPageTemplateCollection.
				getLayoutPageTemplateCollectionId();
		}

		String name = LanguageUtil.format(
			locale, "x-pages", LanguageUtil.get(locale, "application"));

		_layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					userId, company.getGroupId(), name, StringPool.BLANK, true,
					new ServiceContext());

		return _layoutPageTemplateCollection.
			getLayoutPageTemplateCollectionId();
	}

	private final CompanyLocalService _companyLocalService;
	private LayoutPageTemplateCollection _layoutPageTemplateCollection;
	private final LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}