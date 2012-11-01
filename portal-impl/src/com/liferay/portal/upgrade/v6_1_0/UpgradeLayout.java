/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select plid, name, title, typeSettings from Layout");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String typeSettings = rs.getString("typeSettings");

				updateLayout(plid, name, title, typeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateJavaScript(
		UnicodeProperties typeSettingsProperties, String javaScript1,
		String javaScript2, String javaScript3) {

		StringBundler sb = new StringBundler(6);

		if (Validator.isNotNull(javaScript1)) {
			sb.append("// Custom JavaScript 1\n\n");
			sb.append(javaScript1);

			typeSettingsProperties.remove("javascript-1");
		}

		if (Validator.isNotNull(javaScript2)) {
			sb.append("\n\n\n // Custom JavaScript 2\n\n");
			sb.append(javaScript2);

			typeSettingsProperties.remove("javascript-2");
		}

		if (Validator.isNotNull(javaScript3)) {
			sb.append("\n\n\n // Custom JavaScript 3\n\n");
			sb.append(javaScript3);

			typeSettingsProperties.remove("javascript-3");
		}

		String javascript = sb.toString();

		if (Validator.isNotNull(javascript)) {
			typeSettingsProperties.put("javascript", javascript);
		}
	}

	protected void updateLayout(
			long plid, String name, String title, String typeSettings)
		throws Exception {

		if (Validator.isNotNull(name)) {
			name = StringUtil.replace(
				name, new String[] {"<name", "</name>"},
				new String[] {"<Name", "</Name>"});

			updateName(plid, name);
		}

		if (Validator.isNotNull(title)) {
			title = StringUtil.replace(
				title, new String[] {"<title", "</title>"},
				new String[] {"<Title", "</Title>"});

			updateTitle(plid, title);
		}

		if (Validator.isNotNull(typeSettings)) {
			Locale defaultLocale = LocaleUtil.getDefault();
			String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

			UnicodeProperties typeSettingsProperties = new UnicodeProperties(
				true);

			typeSettingsProperties.load(typeSettings);

			String defaultDescription = typeSettingsProperties.getProperty(
				"meta-description_" + defaultLanguageId);

			if (Validator.isNotNull(defaultDescription)) {
				typeSettingsProperties = updateMetaField(
					plid, typeSettingsProperties, "meta-description_",
					"Description", "description");
			}

			String defaultKeywords = typeSettingsProperties.getProperty(
				"meta-keywords_" + defaultLanguageId);

			if (Validator.isNotNull(defaultKeywords)) {
				typeSettingsProperties = updateMetaField(
					plid, typeSettingsProperties, "meta-keywords_", "Keywords",
					"keywords");
			}

			String defaultRobots = typeSettingsProperties.getProperty(
				"meta-robots_" + defaultLanguageId);

			if (Validator.isNotNull(defaultRobots)) {
				typeSettingsProperties = updateMetaField(
					plid, typeSettingsProperties, "meta-robots_", "Robots",
					"robots");
			}

			String javaScript1 = typeSettingsProperties.getProperty(
				"javascript-1");
			String javaScript2 = typeSettingsProperties.getProperty(
				"javascript-2");
			String javaScript3 = typeSettingsProperties.getProperty(
				"javascript-3");

			if ((javaScript1 != null) || (javaScript2 != null) ||
				(javaScript3 != null)) {

				updateJavaScript(
					typeSettingsProperties, javaScript1, javaScript2,
					javaScript3);
			}

			updateTypeSettings(plid, typeSettingsProperties.toString());
		}
	}

	protected UnicodeProperties updateMetaField(
			long plid, UnicodeProperties typeSettingsProperties,
			String propertyName, String xmlName, String columName)
		throws Exception {

		String xml = null;

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String value = typeSettingsProperties.getProperty(
				propertyName + languageId);

			if (Validator.isNotNull(value)) {
				xml = LocalizationUtil.updateLocalization(
					xml, xmlName, value, languageId);

				typeSettingsProperties.remove(propertyName + languageId);
			}
		}

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set " + columName + " = ? where plid = " + plid);

			ps.setString(1, xml);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return typeSettingsProperties;
	}

	protected void updateName(long plid, String name) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set name = ? where plid = " + plid);

			ps.setString(1, name);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateTitle(long plid, String title) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set title = ? where plid = " + plid);

			ps.setString(1, title);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}