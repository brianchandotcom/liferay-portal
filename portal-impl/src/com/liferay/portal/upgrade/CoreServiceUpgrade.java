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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.CoreUpgradeProcessRegistry;
import com.liferay.portal.upgrade.v7_1.UpgradeProcessRegistry;

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
public class CoreServiceUpgrade extends UpgradeProcess {

	public static Version getLatestSchemaVersion() throws Exception {
		initializeUpgradeProcesses();

		return _upgradeProcesses.lastKey();
	}

	public static Version getRequiredSchemaVersion() throws Exception {
		initializeUpgradeProcesses();

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

	public static boolean isInLatestSchemaVersion() throws Exception {
		return getLatestSchemaVersion().equals(getCurrentSchemaVersion());
	}

	public static boolean isInRequiredSchemaVersion() throws Exception {
		if (getRequiredSchemaVersion().compareTo(getCurrentSchemaVersion()) <=
				0) {

			return true;
		}

		return false;
	}

	public CoreServiceUpgrade() throws Exception {
		initializeUpgradeProcesses();
	}

	protected static Version getCurrentSchemaVersion() throws SQLException {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select schemaVersion from Release_ where servletContextName " +
					"= ?");) {

			ps.setString(1, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String schemaVersion = rs.getString("schemaVersion");

					if (Version.isVersion(schemaVersion)) {
						return new Version(schemaVersion);
					}
				}
			}
		}

		return null;
	}

	protected static void initializeSchemaVersion() throws SQLException {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"update Release_ set schemaVersion = ? where " +
					"servletContextName = ? and buildNumber < 7100")) {

			ps.setString(1, _INITIAL_SCHEMA_VERSION);
			ps.setString(2, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			ps.execute();
		}
	}

	protected static void initializeUpgradeProcesses() throws Exception {
		if (_upgradeProcesses != null) {
			return;
		}

		_upgradeProcesses = new TreeMap<>();

		_upgradeProcesses.put(
			new Version(_INITIAL_SCHEMA_VERSION), new DummyUpgradeProcess());

		for (Class<?> coreUpgradeProcessRegistry :
				_CORE_UPGRADE_PROCESS_REGISTRIES) {

			CoreUpgradeProcessRegistry registry =
				(CoreUpgradeProcessRegistry)
					coreUpgradeProcessRegistry.newInstance();

			registry.registerUpgradeProcesses(_upgradeProcesses);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		initializeSchemaVersion();

		for (Version pendingSchemaVersion :
				getPendingSchemaVersions(getCurrentSchemaVersion())) {

			upgrade(_upgradeProcesses.get(pendingSchemaVersion));

			updateSchemaVersion(pendingSchemaVersion);
		}

		clearIndexesCache();
	}

	protected Set<Version> getPendingSchemaVersions(Version fromSchemaVersion) {
		SortedMap<Version, UpgradeProcess> pendingUpgradeProcesses =
			new TreeMap<>(_upgradeProcesses);

		if (fromSchemaVersion != null) {
			pendingUpgradeProcesses = _upgradeProcesses.tailMap(
				fromSchemaVersion, false);
		}

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

	private static final Class<?>[] _CORE_UPGRADE_PROCESS_REGISTRIES =
		{UpgradeProcessRegistry.class};

	private static final String _INITIAL_SCHEMA_VERSION = "0.1.0";

	private static TreeMap<Version, UpgradeProcess> _upgradeProcesses;

}