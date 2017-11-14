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
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leon Chi
 */
public abstract class BaseUpgradePrototype extends UpgradeProcess {

	protected void upgradePrototype(
			Class<?> clazz, ResourceBundleLoader resourceBundleLoader,
			String tableName, String name, String description, String nameKey,
			String descriptionKey)
		throws SQLException {

		resourceBundleLoader = new AggregateResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(
				"content.Language", clazz.getClassLoader()),
			resourceBundleLoader);

		StringBundler sb = new StringBundler(3);

		sb.append("select companyId from ");
		sb.append(tableName);
		sb.append(" where name = ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setString(1, name);

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

		Map<Locale, String> localizationMap =
			ResourceBundleUtil.getLocalizationMap(
				resourceBundleLoader, localizationMapKey);

		CompanyThreadLocal.setCompanyId(originalCompanyId);

		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		String xml = LocalizationUtil.updateLocalization(
			localizationMap, "", xmlKey, defaultLanguageId);

		return xml;
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

		StringBundler sb = new StringBundler(3);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set name = ?,description = ? where name = ? and ");
		sb.append("description = ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setString(1, nameXml);
			ps.setString(2, descriptionXml);
			ps.setString(3, name);
			ps.setString(4, description);

			ps.executeUpdate();
		}
	}

}