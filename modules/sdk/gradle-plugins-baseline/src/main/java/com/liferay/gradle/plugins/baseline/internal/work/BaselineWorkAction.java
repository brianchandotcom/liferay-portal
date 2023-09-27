/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.baseline.internal.work;

import com.liferay.gradle.plugins.baseline.Baseline;

import java.io.File;

import org.gradle.api.GradleException;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkAction;

/**
 * @author Peter Shin
 */
public abstract class BaselineWorkAction
	implements WorkAction<BaselineWorkParameters> {

	@Override
	public void execute() {
		BaselineWorkParameters baselineWorkParameters = getParameters();

		try {
			Baseline baseline = new Baseline();

			RegularFileProperty bndFileRegularFileProperty =
				baselineWorkParameters.getBndFile();

			RegularFile bndFileRegularFile = bndFileRegularFileProperty.get();

			baseline.setBndFile(bndFileRegularFile.getAsFile());

			Property<Boolean> forceCalculatedVersionProperty =
				baselineWorkParameters.getForceCalculatedVersion();

			baseline.setForceCalculatedVersion(
				forceCalculatedVersionProperty.get());

			baseline.setForcePackageInfo(true);

			Property<Boolean> ignoreExcessiveVersionIncreasesProperty =
				baselineWorkParameters.getIgnoreExcessiveVersionIncreases();

			baseline.setIgnoreExcessiveVersionIncreases(
				ignoreExcessiveVersionIncreasesProperty.get());

			RegularFileProperty logFileRegularFileProperty =
				baselineWorkParameters.getLogFile();

			RegularFile logFileRegularFile = logFileRegularFileProperty.get();

			baseline.setLogFile(logFileRegularFile.getAsFile());

			RegularFileProperty newJarFileRegularFileProperty =
				baselineWorkParameters.getNewJarFile();

			RegularFile newJarFileRegularFile =
				newJarFileRegularFileProperty.get();

			File newJarFile = newJarFileRegularFile.getAsFile();

			baseline.setNewJarFile(newJarFile);

			RegularFileProperty oldJarFileRegularFileProperty =
				baselineWorkParameters.getOldJarFile();

			RegularFile oldJarFileRegularFile =
				oldJarFileRegularFileProperty.get();

			File oldJarFile = oldJarFileRegularFile.getAsFile();

			baseline.setOldJarFile(oldJarFile);

			Property<Boolean> reportDiffProperty =
				baselineWorkParameters.getReportDiff();

			baseline.setReportDiff(reportDiffProperty.get());

			Property<Boolean> reportOnlyDirtyPackagesProperty =
				baselineWorkParameters.getReportOnlyDirtyPackages();

			baseline.setReportOnlyDirtyPackages(
				reportOnlyDirtyPackagesProperty.get());

			DirectoryProperty sourceDirDirectoryProperty =
				baselineWorkParameters.getSourceDir();

			Directory sourceDirDirectory = sourceDirDirectoryProperty.get();

			baseline.setSourceDir(sourceDirDirectory.getAsFile());

			boolean match = baseline.execute();

			if (!match) {
				StringBuilder sb = new StringBuilder();

				sb.append("Semantic versioning is incorrect while checking ");
				sb.append(newJarFile);
				sb.append(" against ");
				sb.append(oldJarFile);

				Property<Boolean> ignoreFailuresProperty =
					baselineWorkParameters.getIgnoreFailures();

				if (ignoreFailuresProperty.get()) {
					System.out.println(sb.toString());
				}
				else {
					throw new GradleException(sb.toString());
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}