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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leon Chi
 */
public abstract class BaseUpgradeLayoutPrototype extends UpgradeProcess {

	protected void upgradePrototype(
			ResourceBundleLoader resourceBundleLoader, String tableName,
			String name, String description, String nameKey,
			String descriptionKey)
		throws SQLException {

		Class<?> clazz = getClass();

		resourceBundleLoader = new AggregateResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(
				"content.Language", clazz.getClassLoader()),
			resourceBundleLoader);

		String sql = "select distinct companyId from ".concat(tableName);

		sql = sql.concat(" where name = ? and description = ?");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, description);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				_upgrade(
					resourceBundleLoader, tableName, name, description, nameKey,
					descriptionKey, companyId);
			}
		}
	}

	private String _getLocalizationXml(
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
			String name, String description, String nameKey,
			String descriptionKey, long companyId)
		throws SQLException {

		String nameXml = _getLocalizationXml(
			nameKey, "Name", companyId, resourceBundleLoader);

		String descriptionXml = _getLocalizationXml(
			descriptionKey, "Description", companyId, resourceBundleLoader);

		String sql = "update ".concat(tableName);

		sql = sql.concat(
			" set name = ?, description = ? where name = ? and description = ?");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nameXml);
			ps.setString(2, descriptionXml);
			ps.setString(3, name);
			ps.setString(4, description);

			ps.executeUpdate();
		}
	}

}