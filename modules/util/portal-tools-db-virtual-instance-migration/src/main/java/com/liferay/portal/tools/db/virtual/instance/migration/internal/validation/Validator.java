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

import com.liferay.portal.tools.db.virtual.instance.migration.internal.util.Database;
import com.liferay.portal.tools.db.virtual.instance.migration.internal.util.Version;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class Validator {

	public static boolean validateDatabases(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		boolean valid = true;

		if (!_validateReleaseTableState(sourceConnection)) {
			ValidatorRecorder.registerError(
				"Source database Release_ table has records with an invalid " +
					"state_");
			valid = false;
		}

		if (!_validateReleaseTableState(destinationConnection)) {
			ValidatorRecorder.registerError(
				"Destination database Release_ table has records with an " +
					"invalid state_");
			valid = false;
		}

		valid &= _validateReleaseTableModules(
			sourceConnection, destinationConnection);

		valid &= _checkTables(sourceConnection, destinationConnection);

		valid &= _checkWebIds(sourceConnection, destinationConnection);

		return valid;
	}

	private static boolean _checkTables(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		boolean valid = true;

		List<String> sourceTables = Database.getTables(sourceConnection);
		List<String> destinationTables = Database.getTables(
			destinationConnection);

		for (String tableName : sourceTables) {
			if (destinationTables.contains(tableName)) {
				destinationTables.remove(tableName);

				continue;
			}

			ValidatorRecorder.registerWarning(
				"Table " + tableName +
					" is not present in destination database");
			valid = false;
		}

		if (!destinationTables.isEmpty()) {
			for (String tableName : destinationTables) {
				ValidatorRecorder.registerWarning(
					"Table " + tableName +
						" is not present in source database");
			}

			valid = false;
		}

		return valid;
	}

	private static boolean _checkWebIds(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		String sourceWebId = Database.getWebId(sourceConnection);

		try (PreparedStatement preparedStatement =
				destinationConnection.prepareStatement(
					"select companyId from Company where webId = ?")) {

			preparedStatement.setString(1, sourceWebId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					ValidatorRecorder.registerWarning(
						"webId " + sourceWebId +
							" already exists in destination database");

					return false;
				}
			}
		}

		return true;
	}

	private static boolean _validateReleaseTableModules(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		boolean valid = true;

		try (PreparedStatement preparedStatement1 =
				sourceConnection.prepareStatement(
					"select servletContextName, schemaVersion, verified from " +
						" Release_");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			List<String> missingModules = new ArrayList<>();
			List<String> missingServiceModules = new ArrayList<>();
			List<String> lowerVersionModules = new ArrayList<>();
			List<String> higherVersionModules = new ArrayList<>();
			List<String> sourceUnverifiedModules = new ArrayList<>();
			List<String> destinationUnverifiedModules = new ArrayList<>();

			while (resultSet1.next()) {
				String sourceServletContextName = resultSet1.getString(1);
				Version sourceVersion = Version.parseVersion(
					resultSet1.getString(2));
				boolean sourceVerified = resultSet1.getBoolean(3);

				try (PreparedStatement preparedStatement2 =
						destinationConnection.prepareStatement(
							"select servletContextName, schemaVersion, " +
								"verified from Release_ where " +
									"servletContextName = ?")) {

					preparedStatement2.setString(1, sourceServletContextName);

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						if (!resultSet2.next()) {
							if (sourceServletContextName.endsWith(".service")) {
								missingServiceModules.add(
									sourceServletContextName);
							}
							else {
								missingModules.add(sourceServletContextName);
							}

							continue;
						}

						Version destinationVersion = Version.parseVersion(
							resultSet2.getString(2));
						boolean destinationVerified = resultSet2.getBoolean(3);

						if (sourceVersion.compareTo(destinationVersion) < 0) {
							lowerVersionModules.add(sourceServletContextName);
						}
						else if (sourceVersion.compareTo(destinationVersion) >
									0) {

							higherVersionModules.add(sourceServletContextName);
						}

						if (sourceVerified && !destinationVerified) {
							destinationUnverifiedModules.add(
								sourceServletContextName);
						}
						else if (!sourceVerified && destinationVerified) {
							sourceUnverifiedModules.add(
								sourceServletContextName);
						}
					}
				}
			}

			ValidatorRecorder.registerErrors(
				missingServiceModules,
				" will not be available in the destination");
			ValidatorRecorder.registerErrors(
				lowerVersionModules,
				" needs to be upgraded in source database before the " +
					"migration");
			ValidatorRecorder.registerErrors(
				higherVersionModules,
				" is in a lower version in destination database");
			ValidatorRecorder.registerErrors(
				sourceUnverifiedModules,
				" needs to be verified in the source before the migration");
			ValidatorRecorder.registerErrors(
				destinationUnverifiedModules,
				" needs to be verified in the destination before the " +
					"migration");
			ValidatorRecorder.registerWarnings(
				missingModules, " will not be available in the destination");

			if (!missingModules.isEmpty() || !missingServiceModules.isEmpty() ||
				!lowerVersionModules.isEmpty() ||
				!higherVersionModules.isEmpty() ||
				!sourceUnverifiedModules.isEmpty() ||
				!destinationUnverifiedModules.isEmpty()) {

				valid = false;
			}
		}

		return valid;
	}

	private static boolean _validateReleaseTableState(Connection connection)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName from Release_ where state_ != 0;");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return false;
			}
		}

		return true;
	}

}