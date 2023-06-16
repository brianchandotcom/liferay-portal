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

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.recorder.Recorder;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.release.Release;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public class Validator {

	public static Recorder validateDatabases(
			Connection destinationConnection, Connection sourceConnection)
		throws Exception {

		Recorder recorder = new Recorder();

		_validateRelease(sourceConnection, destinationConnection, recorder);

		_validatePartitionedTables(
			sourceConnection, destinationConnection, recorder);

		_validateWebId(sourceConnection, destinationConnection, recorder);

		return recorder;
	}

	private static void _validatePartitionedTables(
			Connection sourceConnection, Connection destinationConnection,
			Recorder recorder)
		throws Exception {

		List<String> sourcePartitionedTableNames =
			DatabaseUtil.getPartitionedTableNames(sourceConnection);
		List<String> destinationPartitionedTableNames =
			DatabaseUtil.getPartitionedTableNames(destinationConnection);

		for (String sourcePartitionedTableName : sourcePartitionedTableNames) {
			if (destinationPartitionedTableNames.contains(
					sourcePartitionedTableName)) {

				destinationPartitionedTableNames.remove(
					sourcePartitionedTableName);

				continue;
			}

			recorder.registerWarning(
				"Table " + sourcePartitionedTableName +
					" is not present in destination database");
		}

		if (!destinationPartitionedTableNames.isEmpty()) {
			for (String destionationPartitionedTableName :
					destinationPartitionedTableNames) {

				recorder.registerWarning(
					"Table " + destionationPartitionedTableName +
						" is not present in source database");
			}
		}
	}

	private static void _validateRelease(
			Connection sourceConnection, Connection destinationConnection,
			Recorder recorder)
		throws Exception {

		_validateReleaseState(
			sourceConnection, destinationConnection, recorder);

		Map<String, Release> destinationReleasesMap =
			DatabaseUtil.getReleasesMap(destinationConnection);

		List<Release> sourceReleases = DatabaseUtil.getReleases(
			sourceConnection);

		List<String> missingDestinationModules = new ArrayList<>();
		List<String> missingDestinationServiceModules = new ArrayList<>();

		List<String> missingSourceModules = new ArrayList<>();
		List<String> lowerVersionModules = new ArrayList<>();
		List<String> higherVersionModules = new ArrayList<>();
		List<String> unverifiedSourceModules = new ArrayList<>();
		List<String> unverifiedDestinationModules = new ArrayList<>();

		for (Release sourceRelease : sourceReleases) {
			String sourceServletContextName =
				sourceRelease.getServletContextName();

			Release destinationRelease = destinationReleasesMap.remove(
				sourceServletContextName);

			if (destinationRelease == null) {
				missingSourceModules.add(sourceServletContextName);

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

				unverifiedDestinationModules.add(sourceServletContextName);
			}
			else if (!sourceRelease.getVerified() &&
					 destinationRelease.getVerified()) {

				unverifiedSourceModules.add(sourceServletContextName);
			}
		}

		for (Release destionationRelease : destinationReleasesMap.values()) {
			String destinationServletContextName =
				destionationRelease.getServletContextName();

			if (destinationServletContextName.endsWith(".service")) {
				missingDestinationServiceModules.add(
					destinationServletContextName);
			}
			else {
				missingDestinationModules.add(destinationServletContextName);
			}
		}

		recorder.registerErrors(
			missingDestinationServiceModules,
			"needs to be installed in the source database before the " +
				"migration");
		recorder.registerErrors(
			lowerVersionModules,
			"needs to be upgraded in source database before the migration");
		recorder.registerErrors(
			higherVersionModules,
			"needs to be upgraded in destination database before the " +
				"migration");
		recorder.registerErrors(
			unverifiedSourceModules,
			"needs to be verified in the source database before the migration");
		recorder.registerErrors(
			unverifiedDestinationModules,
			"needs to be verified in the destination database before the " +
				"migration");
		recorder.registerWarnings(
			missingDestinationModules, "is not present in the source database");
		recorder.registerWarnings(
			missingSourceModules, "is not present in the destination database");
	}

	private static void _validateReleaseState(
			Connection sourceConnection, Connection destinationConnection,
			Recorder recorder)
		throws Exception {

		String message = "has a failed Release state in the ? database";

		List<String> failedServletContextNames =
			DatabaseUtil.getFailedServletContextNames(sourceConnection);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				failedServletContextNames,
				StringUtil.replace(message, '?', "source"));
		}

		failedServletContextNames = DatabaseUtil.getFailedServletContextNames(
			destinationConnection);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				failedServletContextNames,
				StringUtil.replace(message, '?', "destination"));
		}
	}

	private static void _validateWebId(
			Connection sourceConnection, Connection destinationConnection,
			Recorder recorder)
		throws Exception {

		String sourceWebId = DatabaseUtil.getWebId(sourceConnection);

		if (DatabaseUtil.hasWebId(destinationConnection, sourceWebId)) {
			recorder.registerError(
				"WebId " + sourceWebId +
					" already exists in destination database");
		}
	}

}