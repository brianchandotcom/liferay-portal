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

import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;
import com.liferay.portal.upgrade.v7_1_x.PortalUpgradeProcessRegistryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Alberto Chaparro
 */
public class PortalUpgradeProcess extends UpgradeProcess {

	public static Version getCurrentSchemaVersion(Connection connection)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"select schemaVersion from Release_ where servletContextName " +
					"= ?")) {

			ps.setString(1, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String schemaVersion = rs.getString("schemaVersion");

					return Version.parseVersion(schemaVersion);
				}
			}
		}

		return new Version(0, 0, 0);
	}

	public static Version getLatestSchemaVersion() {
		return _upgradeProcesses.lastKey();
	}

	public static Version getRequiredSchemaVersion() {
		NavigableSet<Version> reverseSchemaVersions =
			_upgradeProcesses.descendingKeySet();

		Iterator<Version> itr = reverseSchemaVersions.iterator();

		Version requiredSchemaVersion = itr.next();

		while (itr.hasNext()) {
			Version nextSchemaVersion = itr.next();

			if ((requiredSchemaVersion.getMajor() !=
					nextSchemaVersion.getMajor()) ||
				(requiredSchemaVersion.getMinor() !=
					nextSchemaVersion.getMinor())) {

				break;
			}

			requiredSchemaVersion = nextSchemaVersion;
		}

		return requiredSchemaVersion;
	}

	public static boolean isInLatestSchemaVersion(Connection connection)
		throws SQLException {

		Version latestSchemaVersion = getLatestSchemaVersion();

		if (latestSchemaVersion.equals(getCurrentSchemaVersion(connection))) {
			return true;
		}

		return false;
	}

	public static boolean isInRequiredSchemaVersion(Connection connection)
		throws SQLException {

		Version requiredSchemaVersion = getRequiredSchemaVersion();

		if (requiredSchemaVersion.compareTo(
				getCurrentSchemaVersion(connection)) <= 0) {

			return true;
		}

		return false;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_initializeSchemaVersion(connection);

		for (Version pendingSchemaVersion :
				getPendingSchemaVersions(getCurrentSchemaVersion(connection))) {

			upgrade(_upgradeProcesses.get(pendingSchemaVersion));

			updateSchemaVersion(pendingSchemaVersion);
		}

		clearIndexesCache();
	}

	protected Set<Version> getPendingSchemaVersions(Version fromSchemaVersion) {
		SortedMap<Version, UpgradeProcess> pendingUpgradeProcesses =
			_upgradeProcesses.tailMap(fromSchemaVersion, false);

		return pendingUpgradeProcesses.keySet();
	}

	protected void updateSchemaVersion(Version newSchemaVersion)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Release_ set schemaVersion = ? where " +
					"servletContextName = ?")) {

			ps.setString(1, newSchemaVersion.toString());
			ps.setString(2, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			ps.execute();
		}
	}

	private static void _initializeSchemaVersion(Connection connection)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Release_ set schemaVersion = ? where " +
					"servletContextName = ? and buildNumber < 7100")) {

			ps.setString(1, _initialSchemaVersion.toString());
			ps.setString(2, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			ps.execute();
		}
	}

	private static final Version _initialSchemaVersion = new Version(0, 1, 0);
	private static final TreeMap<Version, UpgradeProcess> _upgradeProcesses =
		new TreeMap<Version, UpgradeProcess>() {
			{
				put(_initialSchemaVersion, new DummyUpgradeProcess());
			}
		};

	static {
		PortalUpgradeProcessRegistry portalUpgradeProcessRegistry =
			new PortalUpgradeProcessRegistryImpl();

		portalUpgradeProcessRegistry.registerUpgradeProcesses(
			_upgradeProcesses);

		PortalUpgradeProcessRegistry v72xPortalUpgradeProcessRegistry =
			new com.liferay.portal.upgrade.v7_2_x.
				PortalUpgradeProcessRegistryImpl();

		v72xPortalUpgradeProcessRegistry.registerUpgradeProcesses(
			_upgradeProcesses);
	}

}