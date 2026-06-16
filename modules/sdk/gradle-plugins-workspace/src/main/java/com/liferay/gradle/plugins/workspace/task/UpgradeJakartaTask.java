/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.petra.string.StringBundler;
import com.liferay.release.util.ReleaseEntry;
import com.liferay.release.util.ReleaseUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.options.Option;
import org.gradle.api.tasks.options.OptionValues;

/**
 * @author Kyle Miho
 */
public class UpgradeJakartaTask extends FormatSourceTask {

	public UpgradeJakartaTask() {
		Project project = getProject();

		ObjectFactory objectFactory = project.getObjects();

		_jakartaTransformDependenciesFileProperty =
			objectFactory.fileProperty();
		_toVersionProperty = objectFactory.property(String.class);
	}

	@Override
	public void exec() {
		String toVersion = _toVersionProperty.getOrNull();

		if (toVersion != null) {
			List<String> toVersionValues = getToVersionValues();

			if (!toVersionValues.contains(toVersion)) {
				throw new GradleException(
					StringBundler.concat(
						toVersion,
						" is not a valid target Liferay version. Run \"blade ",
						"gw help --task upgradeJakarta\" to see a list of ",
						"valid Liferay versions."));
			}
		}

		RegularFile regularFile =
			_jakartaTransformDependenciesFileProperty.getOrNull();

		if (regularFile != null) {
			File file = regularFile.getAsFile();

			addSourceFormatterProperty(
				"jakarta.transform.dependencies.file.path",
				file.getAbsolutePath());
		}
		else {
			Logger logger = getLogger();

			if (logger.isInfoEnabled()) {
				logger.info(
					"Jakarta transform dependencies file not resolved; " +
						"proceeding without it.");
			}
		}

		super.exec();
	}

	@InputFile
	@Optional
	public RegularFileProperty getJakartaTransformDependenciesFile() {
		return _jakartaTransformDependenciesFileProperty;
	}

	@Input
	@Option(
		description = "The version of Liferay to target when upgrading the Jakarta source code.",
		option = "to-version"
	)
	@Optional
	public Property<String> getToVersion() {
		return _toVersionProperty;
	}

	@OptionValues("to-version")
	protected List<String> getToVersionValues() {
		List<String> toVersionValues = new ArrayList<>();

		List<ReleaseEntry> releaseEntries = ReleaseUtil.getReleaseEntries();

		for (ReleaseEntry releaseEntry : releaseEntries) {
			toVersionValues.add(releaseEntry.getTargetPlatformVersion());
		}

		return toVersionValues;
	}

	private final RegularFileProperty _jakartaTransformDependenciesFileProperty;
	private final Property<String> _toVersionProperty;

}