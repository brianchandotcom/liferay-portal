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

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.util;

import com.liferay.portal.kernel.util.StringUtil;
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

		_validateRelease(recorder, sourceConnection, targetConnection);

		_validatePartitionedTables(
			recorder, sourceConnection, targetConnection);

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
					" is not present in target database");
		}

		if (!targetPartitionedTableNames.isEmpty()) {
			for (String destionationPartitionedTableName :
					targetPartitionedTableNames) {

				recorder.registerWarning(
					"Table " + destionationPartitionedTableName +
						" is not present in source database");
			}
		}
	}

	private static void _validateRelease(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		_validateReleaseState(recorder, sourceConnection, targetConnection);

		Map<String, Release> targetReleasesMap = DatabaseUtil.getReleasesMap(
			targetConnection);

		List<Release> sourceReleases = DatabaseUtil.getReleases(
			sourceConnection);

		List<String> missingTargetModules = new ArrayList<>();
		List<String> missingTargetServiceModules = new ArrayList<>();

		List<String> missingSourceModules = new ArrayList<>();
		List<String> lowerVersionModules = new ArrayList<>();
		List<String> higherVersionModules = new ArrayList<>();
		List<String> unverifiedSourceModules = new ArrayList<>();
		List<String> unverifiedTargetModules = new ArrayList<>();

		for (Release sourceRelease : sourceReleases) {
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

		for (Release destionationRelease : targetReleasesMap.values()) {
			String targetServletContextName =
				destionationRelease.getServletContextName();

			if (targetServletContextName.endsWith(".service")) {
				missingTargetServiceModules.add(targetServletContextName);
			}
			else {
				missingTargetModules.add(targetServletContextName);
			}
		}

		recorder.registerErrors(
			"needs to be installed in the source database before the migration",
			missingTargetServiceModules);
		recorder.registerErrors(
			"needs to be upgraded in source database before the migration",
			lowerVersionModules);
		recorder.registerErrors(
			"needs to be upgraded in target database before the migration",
			higherVersionModules);
		recorder.registerErrors(
			"needs to be verified in the source database before the migration",
			unverifiedSourceModules);
		recorder.registerErrors(
			"needs to be verified in the target database before the migration",
			unverifiedTargetModules);
		recorder.registerWarnings(
			"is not present in the source database", missingTargetModules);
		recorder.registerWarnings(
			"is not present in the target database", missingSourceModules);
	}

	private static void _validateReleaseState(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		String message = "has a failed Release state in the ? database";

		List<String> failedServletContextNames =
			DatabaseUtil.getFailedServletContextNames(sourceConnection);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				StringUtil.replace(message, '?', "source"),
				failedServletContextNames);
		}

		failedServletContextNames = DatabaseUtil.getFailedServletContextNames(
			targetConnection);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				StringUtil.replace(message, '?', "target"),
				failedServletContextNames);
		}
	}

	private static void _validateWebId(
			Recorder recorder, Connection sourceConnection,
			Connection targetConnection)
		throws Exception {

		String sourceWebId = DatabaseUtil.getWebId(sourceConnection);

		if (DatabaseUtil.hasWebId(targetConnection, sourceWebId)) {
			recorder.registerError(
				"WebId " + sourceWebId + " already exists in target database");
		}
	}

}