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

package com.liferay.portal.tools.db.virtual.instance.migration.internal.validation;

import com.liferay.portal.tools.db.virtual.instance.migration.internal.Release;
import com.liferay.portal.tools.db.virtual.instance.migration.internal.util.Database;
import com.liferay.portal.tools.db.virtual.instance.migration.internal.util.Version;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class Validator {

	public static ValidatorRecorder validateDatabases(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		ValidatorRecorder recorder = new ValidatorRecorder();

		if (!_validateReleaseTableState(sourceConnection)) {
			recorder.registerError(
				"Source database Release_ table has records with an invalid " +
					"state_");
		}

		if (!_validateReleaseTableState(destinationConnection)) {
			recorder.registerError(
				"Destination database Release_ table has records with an " +
					"invalid state_");
		}

		_validateReleaseTableModules(
			sourceConnection, destinationConnection, recorder);

		_checkTables(sourceConnection, destinationConnection, recorder);

		_checkWebIds(sourceConnection, destinationConnection, recorder);

		return recorder;
	}

	private static void _checkTables(
			Connection sourceConnection, Connection destinationConnection,
			ValidatorRecorder recorder)
		throws SQLException {

		List<String> sourceTables = Database.getTables(sourceConnection);
		List<String> destinationTables = Database.getTables(
			destinationConnection);

		for (String tableName : sourceTables) {
			if (destinationTables.contains(tableName)) {
				destinationTables.remove(tableName);

				continue;
			}

			recorder.registerWarning(
				"Table " + tableName +
					" is not present in destination database");
		}

		if (!destinationTables.isEmpty()) {
			for (String tableName : destinationTables) {
				recorder.registerWarning(
					"Table " + tableName +
						" is not present in source database");
			}
		}
	}

	private static void _checkWebIds(
			Connection sourceConnection, Connection destinationConnection,
			ValidatorRecorder recorder)
		throws SQLException {

		String sourceWebId = Database.getWebId(sourceConnection);

		if (Database.hasWebId(destinationConnection, sourceWebId)) {
			recorder.registerWarning(
				"webId " + sourceWebId +
					" already exists in destination database");
		}
	}

	private static void _validateReleaseTableModules(
			Connection sourceConnection, Connection destinationConnection,
			ValidatorRecorder recorder)
		throws SQLException {

		List<Release> sourceReleaseEntries = Database.getReleaseEntries(
			sourceConnection);

		List<String> missingModules = new ArrayList<>();
		List<String> missingServiceModules = new ArrayList<>();
		List<String> lowerVersionModules = new ArrayList<>();
		List<String> higherVersionModules = new ArrayList<>();
		List<String> sourceUnverifiedModules = new ArrayList<>();
		List<String> destinationUnverifiedModules = new ArrayList<>();

		for (Release sourceRelease : sourceReleaseEntries) {
			String sourceServletContextName =
				sourceRelease.getServletContextName();

			Release destinationRelease = Database.getReleaseEntry(
				destinationConnection, sourceServletContextName);

			if (destinationRelease == null) {
				if (sourceServletContextName.endsWith(".service")) {
					missingServiceModules.add(sourceServletContextName);
				}
				else {
					missingModules.add(sourceServletContextName);
				}

				continue;
			}

			Version sourceVersion = sourceRelease.getSchemaVersion();
			Version destinationVersion = destinationRelease.getSchemaVersion();

			if (sourceVersion.compareTo(destinationVersion) < 0) {
				lowerVersionModules.add(sourceServletContextName);
			}
			else if (sourceVersion.compareTo(destinationVersion) > 0) {
				higherVersionModules.add(sourceServletContextName);
			}

			if (sourceRelease.getVerified() &&
				!destinationRelease.getVerified()) {

				destinationUnverifiedModules.add(sourceServletContextName);
			}
			else if (!sourceRelease.getVerified() &&
					 destinationRelease.getVerified()) {

				sourceUnverifiedModules.add(sourceServletContextName);
			}
		}

		recorder.registerErrors(
			missingServiceModules, " will not be available in the destination");
		recorder.registerErrors(
			lowerVersionModules,
			" needs to be upgraded in source database before the migration");
		recorder.registerErrors(
			higherVersionModules,
			" is in a lower version in destination database");
		recorder.registerErrors(
			sourceUnverifiedModules,
			" needs to be verified in the source before the migration");
		recorder.registerErrors(
			destinationUnverifiedModules,
			" needs to be verified in the destination before the migration");
		recorder.registerWarnings(
			missingModules, " will not be available in the destination");
	}

	private static boolean _validateReleaseTableState(Connection connection)
		throws SQLException {

		List<String> releaseEntries = Database.getInvalidStateServlets(
			connection);

		if (!releaseEntries.isEmpty()) {
			return false;
		}

		return true;
	}

}