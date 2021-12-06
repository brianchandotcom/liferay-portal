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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
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
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			upgradePortalPreferences(PortletKeys.PREFS_OWNER_ID_DEFAULT);

			while (resultSet.next()) {
				upgradePortalPreferences(resultSet.getLong("companyId"));
			}
		}
	}

	protected void upgradePortalPreferences(long companyId) throws Exception {
		String sql = StringBundler.concat(
			"select portalPreferencesId, preferences from PortalPreferences ",
			"where ownerId = ", companyId, " and ownerType = ",
			PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String preferences = resultSet.getString("preferences");

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

							_log.warn(
								StringBundler.concat(
									"Detected a value of \"", value,
									"\" for portal property ",
									obsoletePortalPreference,
									" stored in portal preferences. Storing ",
									"this property in portal preferences is ",
									"no longer supported; please set this ",
									"property to this value in ",
									"portal-ext.properties if you wish to ",
									"retain it."));
						}

						portletPreferencesElement.remove(element);

						updatedDocument = true;
					}
				}

				if (updatedDocument) {
					try (PreparedStatement preparedStatement2 =
							connection.prepareStatement(
								"update PortalPreferences set preferences = " +
									"? where portalPreferencesId = ?")) {

						preparedStatement2.setString(1, document.asXML());
						preparedStatement2.setLong(
							2, resultSet.getLong("portalPreferencesId"));

						preparedStatement2.executeUpdate();
					}
				}
			}
		}
	}

	private static final String[] _OBSOLETE_PORTAL_PREFERENCES = {
		PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
		PropsKeys.AUTO_DEPLOY_DEPLOY_DIR, "auto.deploy.dest.dir",
		PropsKeys.AUTO_DEPLOY_ENABLED, PropsKeys.AUTO_DEPLOY_INTERVAL,
		"auto.deploy.jboss.prefix", PropsKeys.AUTO_DEPLOY_TOMCAT_CONF_DIR,
		"auto.deploy.tomcat.lib.dir", "auto.deploy.unpack.war",
		"plugin.notifications.enabled", "plugin.notifications.packages.ignored",
		"plugin.repositories.trusted", "plugin.repositories.untrusted"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortalPreferences.class);

}