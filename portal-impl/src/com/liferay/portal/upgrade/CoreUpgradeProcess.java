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

package com.liferay.portal.upgrade;

import aQute.bnd.version.Version;

import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.CoreUpgradeProcessRegistry;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_1.UpgradeProcessRegistry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Alberto Chaparro
 */
public class CoreUpgradeProcess extends UpgradeProcess {

	public CoreUpgradeProcess() throws Exception {
		for (Class<?> coreUpgradeProcessRegistry :
				_CORE_UPGRADE_PROCESS_REGISTRIES) {

			CoreUpgradeProcessRegistry registry =
				(CoreUpgradeProcessRegistry)
					coreUpgradeProcessRegistry.newInstance();

			registry.registerUpgradeProcesses(upgradeProcesses);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (Version pendingSchemaVersion :
				getPendingSchemaVersions(getCoreSchemaVersion())) {

			Class<?> pendingUpgradeClass = upgradeProcesses.get(
				pendingSchemaVersion);

			upgrade(pendingUpgradeClass);

			updateCoreSchemaVersion(pendingSchemaVersion);
		}

		clearIndexesCache();
	}

	protected Version getCoreSchemaVersion() throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"select schemaVersion from Release_ where servletContextName " +
					"= ?");) {

			ps.setString(1, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String schemaVersion = rs.getString("schemaVersion");

					if (!_INITIAL_SCHEMA_VERSION.equals(schemaVersion) &&
						Version.isVersion(schemaVersion)) {

						return new Version(schemaVersion);
					}
				}
			}
		}

		return null;
	}

	protected Set<Version> getPendingSchemaVersions(Version fromSchemaVersion) {
		SortedMap<Version, Class<?>> pendingUpgradeProcesses = new TreeMap<>(
			upgradeProcesses);

		if (fromSchemaVersion != null) {
			pendingUpgradeProcesses = upgradeProcesses.tailMap(
				fromSchemaVersion, false);
		}

		return pendingUpgradeProcesses.keySet();
	}

	protected void updateCoreSchemaVersion(Version newSchemaVersion)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Release_ set schemaVersion = ? where " +
					"servletContextName = ?")) {

			ps.setString(1, newSchemaVersion.toString());
			ps.setString(2, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			ps.execute();
		}
	}

	protected static TreeMap<Version, Class<?>> upgradeProcesses =
		new TreeMap<>();

	private static final Class<?>[] _CORE_UPGRADE_PROCESS_REGISTRIES =
		{UpgradeProcessRegistry.class};

	private static final String _INITIAL_SCHEMA_VERSION = "7.1.0";

}