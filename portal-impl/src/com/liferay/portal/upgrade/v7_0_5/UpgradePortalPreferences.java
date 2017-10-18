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

package com.liferay.portal.upgrade.v7_0_5;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Michael Bowerman
 */
public class UpgradePortalPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select companyId from Company");
			ResultSet rs = ps.executeQuery()) {

			upgradePortalPreferences(PortletKeys.PREFS_OWNER_ID_DEFAULT);

			while (rs.next()) {
				upgradePortalPreferences(rs.getLong("companyId"));
			}
		}
	}

	protected void upgradePortalPreferences(long companyId) throws Exception {
		String sql = StringBundler.concat(
			"select portalPreferencesId, preferences from PortalPreferences ",
			"where ownerId = ", String.valueOf(companyId), " and ownerType = ",
			String.valueOf(PortletKeys.PREFS_OWNER_TYPE_COMPANY));

		try (PreparedStatement ps1 = connection.prepareStatement(sql);
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				String preferences = rs.getString("preferences");

				Document document = SAXReaderUtil.read(preferences);

				Element portletPreferencesElement = document.getRootElement();

				boolean updatedDocument = false;

				for (String obsoletePortalPreference :
						_OBSOLETE_PORTAL_PREFERENCES) {

					XPath xPath = SAXReaderUtil.createXPath(
						"/portlet-preferences/preference[name='" +
							obsoletePortalPreference + "']");

					List<Node> nodes = xPath.selectNodes(document);

					for (Node node : nodes) {
						Element element = (Element)node;

						if (_log.isWarnEnabled()) {
							Element valueElement = element.element("value");

							String value = valueElement.getStringValue();

							StringBundler sb = new StringBundler(10);

							sb.append("Detected a value of \"");
							sb.append(value);
							sb.append("\" for portal property ");
							sb.append(obsoletePortalPreference);
							sb.append(" stored in portal preferences. ");
							sb.append("Storing this property in portal ");
							sb.append("preferences is no longer supported; ");
							sb.append("please set this property to this ");
							sb.append("value in portal-ext.properties if you ");
							sb.append("wish to retain it.");

							_log.warn(sb.toString());
						}

						portletPreferencesElement.remove(element);

						updatedDocument = true;
					}
				}

				if (updatedDocument) {
					try (PreparedStatement ps2 = connection.prepareStatement(
							"update PortalPreferences set preferences = ? " +
								"where portalPreferencesId = ?")) {

						ps2.setString(1, document.asXML());
						ps2.setLong(2, rs.getLong("portalPreferencesId"));

						ps2.executeUpdate();
					}
				}
			}
		}
	}

	private static final String[] _OBSOLETE_PORTAL_PREFERENCES = {
		PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
		PropsKeys.AUTO_DEPLOY_DEPLOY_DIR, PropsKeys.AUTO_DEPLOY_DEST_DIR,
		PropsKeys.AUTO_DEPLOY_ENABLED, PropsKeys.AUTO_DEPLOY_INTERVAL,
		PropsKeys.AUTO_DEPLOY_JBOSS_PREFIX,
		PropsKeys.AUTO_DEPLOY_TOMCAT_CONF_DIR,
		PropsKeys.AUTO_DEPLOY_TOMCAT_LIB_DIR, PropsKeys.AUTO_DEPLOY_UNPACK_WAR,
		PropsKeys.PLUGIN_NOTIFICATIONS_ENABLED,
		PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED,
		PropsKeys.PLUGIN_REPOSITORIES_TRUSTED,
		PropsKeys.PLUGIN_REPOSITORIES_UNTRUSTED
	};

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortalPreferences.class);

}