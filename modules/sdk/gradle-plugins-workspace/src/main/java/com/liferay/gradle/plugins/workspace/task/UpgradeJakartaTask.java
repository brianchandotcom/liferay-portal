/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;

/**
 * @author Kyle Miho
 */
public class UpgradeJakartaTask extends FormatSourceTask {

	public UpgradeJakartaTask() {
		Project project = getProject();

		ObjectFactory objectFactory = project.getObjects();

		_jakartaTransformDependenciesFileProperty =
			objectFactory.fileProperty();
	}

	@Override
	public void exec() {
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

	private final RegularFileProperty _jakartaTransformDependenciesFileProperty;

}