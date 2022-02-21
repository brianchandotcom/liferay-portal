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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leon Chi
 */
public abstract class BaseLocalizedColumnUpgradeProcess extends UpgradeProcess {

	/**
	 *   @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *          #upgradeLocalizedColumn(
	 *          	ResourceBundleLoader, String, String, String, String,
	 *          	String, long[])}
	 */
	@Deprecated
	protected void upgradeLocalizedColumn(
			ResourceBundleLoader resourceBundleLoader, Class<?> tableClass,
			String columnName, String originalContent,
			String localizationMapKey, String localizationXMLKey,
			long[] companyIds)
		throws SQLException {

		try {
			upgradeLocalizedColumn(
				resourceBundleLoader, getTableName(tableClass), columnName,
				originalContent, localizationMapKey, localizationXMLKey,
				companyIds);
		}
		catch (Exception exception) {
			throw new SQLException(exception);
		}
	}

	protected void upgradeLocalizedColumn(
			ResourceBundleLoader resourceBundleLoader, String tableName,
			String columnName, String originalContent,
			String localizationMapKey, String localizationXMLKey,
			long[] companyIds)
		throws SQLException {

		try {
			if (!hasColumnType(tableName, columnName, "TEXT null") &&
				!_alteredTableNameColumnNames.contains(
					tableName + StringPool.POUND + columnName)) {

				alterColumnType(tableName, columnName, "TEXT null");

				_alteredTableNameColumnNames.add(
					tableName + StringPool.POUND + columnName);
			}

			Class<?> clazz = getClass();

			CompanyLocalServiceUtil.forEachCompanyId(
				companyId -> _upgrade(
					new AggregateResourceBundleLoader(
						new ClassResourceBundleLoader(
							"content.Language", clazz.getClassLoader()),
						resourceBundleLoader),
					tableName, columnName, originalContent, localizationMapKey,
					localizationXMLKey, companyId),
				companyIds);
		}
		catch (Exception exception) {
			throw new SQLException(exception);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #upgradeLocalizedColumn(ResourceBundleLoader, Class, String,
	 *             String, String, String, long[])}
	 */
	@Deprecated
	protected void upgradeLocalizedColumn(
			com.liferay.portal.kernel.util.ResourceBundleLoader
				resourceBundleLoader,
			Class<?> tableClass, String columnName, String originalContent,
			String localizationMapKey, String localizationXMLKey,
			long[] companyIds)
		throws SQLException {
	}

	private String _getLocalizationXML(
			String localizationMapKey, String localizationXMLKey,
			long companyId, ResourceBundleLoader resourceBundleLoader)
		throws SQLException {

		Long originalCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(companyId);

		try {
			Map<Locale, String> localizationMap =
				ResourceBundleUtil.getLocalizationMap(
					resourceBundleLoader, localizationMapKey);

			return LocalizationUtil.updateLocalization(
				localizationMap, "", localizationXMLKey,
				UpgradeProcessUtil.getDefaultLanguageId(companyId));
		}
		finally {
			CompanyThreadLocal.setCompanyId(originalCompanyId);
		}
	}

	private void _upgrade(
			ResourceBundleLoader resourceBundleLoader, String tableName,
			String columnName, String originalContent,
			String localizationMapKey, String localizationXMLKey,
			long companyId)
		throws SQLException {

		String localizationXML = _getLocalizationXML(
			localizationMapKey, localizationXMLKey, companyId,
			resourceBundleLoader);

		String sql = StringBundler.concat(
			"update ", tableName, " set ", columnName, " = ? where ",
			columnName, " like ? and companyId = ?");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setString(1, localizationXML);
			preparedStatement.setString(2, originalContent);
			preparedStatement.setLong(3, companyId);

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}
	}

	private static final Set<String> _alteredTableNameColumnNames =
		new HashSet<>();

}