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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Savinov
 */
public class LayoutPrototypeUpgradeProcess extends UpgradeProcess {

	public LayoutPrototypeUpgradeProcess(
		CompanyLocalService companyLocalService,
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_companyLocalService = companyLocalService;
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_upgradeLayoutPrototype();
	}

	private String _generateNewName(String name, Set<String> existingNames) {
		int i = 1;

		while (true) {
			String suffix = StringPool.DASH + i;

			String newName = name + suffix;

			if (newName.length() > _MAX_NAME_LENGTH) {
				String prefix = name.substring(
					0, _MAX_NAME_LENGTH - suffix.length());

				newName = prefix + suffix;
			}

			if (existingNames.contains(newName)) {
				i++;

				continue;
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Renaming duplicate layout prototype name \"", name,
						"\" to \"", newName, "\""));
			}

			return newName;
		}
	}

	private void _upgradeLayoutPrototype() throws Exception {
		Date date = new Date(System.currentTimeMillis());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into LayoutPageTemplateEntry (uuid_, ",
						"layoutPageTemplateEntryId, groupId, companyId, ",
						"userId, userName, createDate, modifiedDate, ",
						"layoutPageTemplateCollectionId, name, type_, ",
						"layoutPrototypeId, status) values (?, ?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?)"))) {

			Set<String> existingNames = new HashSet<>();

			List<LayoutPrototype> layoutPrototypes =
				_layoutPrototypeLocalService.getLayoutPrototypes(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (LayoutPrototype layoutPrototype : layoutPrototypes) {
				String nameXML = layoutPrototype.getName();

				Company company = _companyLocalService.getCompany(
					layoutPrototype.getCompanyId());

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(nameXML);

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					LocalizationUtil.getDefaultLanguageId(nameXML));

				String name = nameMap.get(defaultLocale);

				if (existingNames.contains(name)) {
					name = _generateNewName(name, existingNames);

					nameMap.put(defaultLocale, name);

					layoutPrototype.setNameMap(nameMap);

					layoutPrototype =
						_layoutPrototypeLocalService.updateLayoutPrototype(
							layoutPrototype);
				}

				if ((layoutPrototype.getCreateDate() == null) ||
					(layoutPrototype.getModifiedDate() == null)) {

					if (layoutPrototype.getCreateDate() == null) {
						layoutPrototype.setCreateDate(date);
					}

					if (layoutPrototype.getModifiedDate() == null) {
						layoutPrototype.setModifiedDate(date);
					}

					_layoutPrototypeLocalService.updateLayoutPrototype(
						layoutPrototype);
				}

				existingNames.add(name);

				preparedStatement.setString(1, layoutPrototype.getUuid());
				preparedStatement.setLong(2, increment());
				preparedStatement.setLong(3, company.getGroupId());
				preparedStatement.setLong(4, layoutPrototype.getCompanyId());
				preparedStatement.setLong(5, layoutPrototype.getUserId());
				preparedStatement.setString(6, layoutPrototype.getUserName());
				preparedStatement.setDate(7, date);
				preparedStatement.setDate(8, date);

				preparedStatement.setLong(9, 0);
				preparedStatement.setString(10, name);
				preparedStatement.setInt(
					11, LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE);
				preparedStatement.setLong(
					12, layoutPrototype.getLayoutPrototypeId());
				preparedStatement.setInt(13, WorkflowConstants.STATUS_APPROVED);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private void _upgradeSchema() throws Exception {
		String template = StringUtil.read(
			LayoutPrototypeUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final int _MAX_NAME_LENGTH = 75;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPrototypeUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final LayoutPrototypeLocalService _layoutPrototypeLocalService;

}