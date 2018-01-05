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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leon Chi
 */
public abstract class BaseUpgradeLocalizedColumn extends UpgradeProcess {

	protected void upgradeLocalizedColumn(
			ResourceBundleLoader resourceBundleLoader, String tableName,
			String columnName, String originalContent,
			String localizationMapKey, String xmlKey, long[] companyIds)
		throws SQLException {

		Class<?> clazz = getClass();

		resourceBundleLoader = new AggregateResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(
				"content.Language", clazz.getClassLoader()),
			resourceBundleLoader);

		for (long companyId : companyIds) {
			_upgrade(
				resourceBundleLoader, tableName, columnName, originalContent,
				localizationMapKey, xmlKey, companyId);
		}
	}

	private String _getLocalizationXML(
			String localizationMapKey, String xmlKey, long companyId,
			ResourceBundleLoader resourceBundleLoader)
		throws SQLException {

		Long originalCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(companyId);

		try {
			Map<Locale, String> localizationMap =
				ResourceBundleUtil.getLocalizationMap(
					resourceBundleLoader, localizationMapKey);

			String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
				companyId);

			return LocalizationUtil.updateLocalization(
				localizationMap, "", xmlKey, defaultLanguageId);
		}
		finally {
			CompanyThreadLocal.setCompanyId(originalCompanyId);
		}
	}

	private void _upgrade(
			ResourceBundleLoader resourceBundleLoader, String tableName,
			String columnName, String originalContent,
			String localizationMapKey, String xmlKey, long companyId)
		throws SQLException {

		String localizationXML = _getLocalizationXML(
			localizationMapKey, xmlKey, companyId, resourceBundleLoader);

		String sql = StringBundler.concat(
			"update ", tableName, " set ", columnName, " = ? where ",
			columnName, " = ? and companyId = ?");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, localizationXML);
			ps.setString(2, originalContent);
			ps.setLong(3, companyId);

			ps.executeUpdate();
		}
	}

}