/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migration.validator.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.Company;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.InstanceData;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.Release;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.validator.Recorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luis Ortiz
 */
public class Validator {

	public static boolean isSingleCompany(InstanceData instanceData) {
		if (instanceData.getCompanyId() != null) {
			return true;
		}

		return false;
	}

	public static Recorder validateDatabases(
		InstanceData sourceInstanceData, InstanceData targetInstanceData) {

		Recorder recorder = new Recorder();

		_validatePartitionedTables(
			recorder, sourceInstanceData, targetInstanceData);
		_validateRelease(recorder, sourceInstanceData, targetInstanceData);
		_validateCompany(recorder, sourceInstanceData, targetInstanceData);

		return recorder;
	}

	private static List<String> _getFailedServletContextNames(
		InstanceData instanceData) {

		List<String> failedServletContextNames = new ArrayList<>();

		for (Release release : instanceData.getReleases()) {
			if (release.getState() != 0) {
				failedServletContextNames.add(release.getServletContextName());
			}
		}

		return failedServletContextNames;
	}

	private static Map<String, Release> _getReleasesMap(
		InstanceData instanceData) {

		Map<String, Release> releasesMap = new HashMap<>();

		for (Release release : instanceData.getReleases()) {
			releasesMap.put(release.getServletContextName(), release);
		}

		return releasesMap;
	}

	private static void _validateCompany(
		Recorder recorder, InstanceData sourceInstanceData,
		InstanceData targetInstanceData) {

		Company sourceCompany = null;

		for (Company company : sourceInstanceData.getCompanies()) {
			if (Objects.equals(
					company.getCompanyId(),
					sourceInstanceData.getCompanyId())) {

				sourceCompany = company;

				break;
			}
		}

		for (Company company : targetInstanceData.getCompanies()) {
			if (Objects.equals(company.getWebId(), sourceCompany.getWebId())) {
				recorder.registerWarning(
					StringBundler.concat(
						"Web ID ", sourceCompany.getWebId(),
						" already exists in the target database. Please ",
						"change it during migration."));
			}

			if (Objects.equals(
					company.getCompanyName(), sourceCompany.getCompanyName())) {

				recorder.registerWarning(
					StringBundler.concat(
						"Company name ", sourceCompany.getCompanyName(),
						" already exists in the target database. Please ",
						"change it during migration."));
			}

			if (Objects.equals(
					company.getVirtualHostName(),
					sourceCompany.getVirtualHostName())) {

				recorder.registerWarning(
					StringBundler.concat(
						"Virtual host ", sourceCompany.getVirtualHostName(),
						" already exists in the target database. Please ",
						"change it during migration."));
			}

			if (Objects.equals(
					company.getCompanyId(), sourceCompany.getCompanyId())) {

				recorder.registerError(
					StringBundler.concat(
						"Company ID ", sourceCompany.getCompanyId(),
						" already exists in the target database"));
			}
		}
	}

	private static void _validatePartitionedTables(
		Recorder recorder, InstanceData sourceInstanceData,
		InstanceData targetInstanceData) {

		List<String> sourcePartitionedTableNames = new ArrayList<>(
			sourceInstanceData.getTableNames());
		List<String> targetPartitionedTableNames = new ArrayList<>(
			targetInstanceData.getTableNames());

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
		Recorder recorder, InstanceData sourceInstanceData,
		InstanceData targetInstanceData) {

		_validateReleaseState(recorder, sourceInstanceData, targetInstanceData);

		List<String> higherVersionModules = new ArrayList<>();
		List<String> lowerVersionModules = new ArrayList<>();
		List<String> missingSourceModules = new ArrayList<>();
		List<String> missingTargetModules = new ArrayList<>();
		List<String> missingTargetServiceModules = new ArrayList<>();
		Map<String, Release> targetReleasesMap = _getReleasesMap(
			targetInstanceData);
		List<String> unverifiedSourceModules = new ArrayList<>();
		List<String> unverifiedTargetModules = new ArrayList<>();

		for (Release sourceRelease : sourceInstanceData.getReleases()) {
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
		Recorder recorder, InstanceData sourceInstanceData,
		InstanceData targetInstanceData) {

		String message = "has a failed release state in the %s database";

		List<String> failedServletContextNames = _getFailedServletContextNames(
			sourceInstanceData);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				String.format(message, "source"), failedServletContextNames);
		}

		failedServletContextNames = _getFailedServletContextNames(
			targetInstanceData);

		if (!failedServletContextNames.isEmpty()) {
			recorder.registerErrors(
				String.format(message, "target"), failedServletContextNames);
		}
	}

}