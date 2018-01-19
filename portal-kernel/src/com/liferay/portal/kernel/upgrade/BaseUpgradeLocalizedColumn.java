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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

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
			String localizationMapKey, String localizationXMLKey,
			long[] companyIds)
		throws SQLException {

		Class<?> clazz = getClass();

		resourceBundleLoader = new AggregateResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(
				"content.Language", clazz.getClassLoader()),
			resourceBundleLoader);

		for (long companyId : companyIds) {
			_upgrade(
				resourceBundleLoader, tableName, columnName, originalContent,
				localizationMapKey, localizationXMLKey, companyId);
		}
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

			String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
				companyId);

			return LocalizationUtil.updateLocalization(
				localizationMap, "", localizationXMLKey, defaultLanguageId);
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

		String escapedlocalizationXML = localizationXML.replace(
			StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);

		String escapedOriginalContent = originalContent.replace(
			StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);

		String sql = StringBundler.concat(
			"update ", tableName, " set ", columnName, " = '",
			escapedlocalizationXML, "' where CAST_CLOB_TEXT(", columnName,
			") = '", escapedOriginalContent, "' and companyId = ",
			Long.toString(companyId));

		try {
			runSQL(sql);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

}