/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.util;

import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.Recorder;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.Release;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public class Validator {

	public static Recorder validateDatabases(
			Connection sourceConnection, Connection targetConnection)
		throws Exception {

		Recorder recorder = new Recorder();

		_validatePartitionedTables(
			recorder, sourceConnection, targetConnection);
		_validateRelease(recorder, sourceConnection, targetConnection);
		_validateWebId(recorder, sourceConnection, targetConnection);

		return recorder;
	}

	private static void _validatePartitionedTables(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		List<String> sourcePartitionedTableNames =
			DatabaseUtil.getPartitionedTableNames(sourceConnection);
		List<String> targetPartitionedTableNames =
			DatabaseUtil.getPartitionedTableNames(targetConnection);

		for (String sourcePartitionedTableName : sourcePartitionedTableNames) {
			if (targetPartitionedTableNames.contains(
					sourcePartitionedTableName)) {

				targetPartitionedTableNames.remove(sourcePartitionedTableName);

				continue;
			}

			recorder.registerWarning(
				"Table " + sourcePartitionedTableName +
					" is not present in the target database");
		}

		for (String targetPartitionedTableName : targetPartitionedTableNames) {
			recorder.registerWarning(
				"Table " + targetPartitionedTableName +
					" is not present in the source database");
		}
	}

	private static void _validateRelease(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		_validateReleaseState(recorder, sourceConnection, targetConnection);

		List<String> higherVersionModules = new ArrayList<>();
		List<String> lowerVersionModules = new ArrayList<>();
		List<String> missingSourceModules = new ArrayList<>();
		List<String> missingTargetModules = new ArrayList<>();
		List<String> missingTargetServiceModules = new ArrayList<>();
		Map<String, Release> targetReleasesMap = DatabaseUtil.getReleasesMap(
			targetConnection);
		List<String> unverifiedSourceModules = new ArrayList<>();
		List<String> unverifiedTargetModules = new ArrayList<>();

		for (Release sourceRelease :
				DatabaseUtil.getReleases(sourceConnection)) {

			String sourceServletContextName =
				sourceRelease.getServletContextName();

			Release targetRelease = targetReleasesMap.remove(
				sourceServletContextName);

			if (targetRelease == null) {
				missingSourceModules.add(sourceServletContextName);

				continue;
			}

			Version sourceVersion = sourceRelease.getSchemaVersion();
			Version targetVersion = targetRelease.getSchemaVersion();

			if (sourceVersion.compareTo(targetVersion) < 0) {
				lowerVersionModules.add(sourceServletContextName);
			}
			else if (sourceVersion.compareTo(targetVersion) > 0) {
				higherVersionModules.add(sourceServletContextName);
			}

			if (sourceRelease.getVerified() && !targetRelease.getVerified()) {
				unverifiedTargetModules.add(sourceServletContextName);
			}
			else if (!sourceRelease.getVerified() &&
					 targetRelease.getVerified()) {

				unverifiedSourceModules.add(sourceServletContextName);
			}
		}

		for (Release targetRelease : targetReleasesMap.values()) {
			String targetServletContextName =
				targetRelease.getServletContextName();

			if (targetServletContextName.endsWith(".service")) {
				missingTargetServiceModules.add(targetServletContextName);
			}
			else {
				missingTargetModules.add(targetServletContextName);
			}
		}

		recorder.registerErrors(
			"needs to be upgraded in the target database before the migration",
			higherVersionModules);
		recorder.registerErrors(
			"needs to be upgraded in the source database before the migration",
			lowerVersionModules);
		recorder.registerWarnings(
			"is not present in the target database", missingSourceModules);
		recorder.registerWarnings(
			"is not present in the source database", missingTargetModules);
		recorder.registerErrors(
			"needs to be installed in the source database before the migration",
			missingTargetServiceModules);
		recorder.registerErrors(
			"needs to be verified in the source database before the migration",
			unverifiedSourceModules);
		recorder.registerErrors(
			"needs to be verified in the target database before the migration",
			unverifiedTargetModules);
	}

	private static void _validateReleaseState(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		String message = "has a failed release state in the %s database";

		List<String> failedServletContextNames =
			DatabaseUtil.getFailedServletContextNames(sourceConnection);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				String.format(message, "source"), failedServletContextNames);
		}

		failedServletContextNames = DatabaseUtil.getFailedServletContextNames(
			targetConnection);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				String.format(message, "target"), failedServletContextNames);
		}
	}

	private static void _validateWebId(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		String sourceWebId = DatabaseUtil.getWebId(sourceConnection);

		if (DatabaseUtil.hasWebId(targetConnection, sourceWebId)) {
			recorder.registerError(
				"Web ID " + sourceWebId +
					" already exists in the target database");
		}
	}

}