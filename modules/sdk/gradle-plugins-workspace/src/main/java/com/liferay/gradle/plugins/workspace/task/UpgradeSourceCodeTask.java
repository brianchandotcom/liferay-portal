/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.petra.string.StringBundler;
import com.liferay.release.util.ReleaseEntry;
import com.liferay.release.util.ReleaseUtil;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.options.Option;
import org.gradle.api.tasks.options.OptionValues;

/**
 * @author Kyle Miho
 */
public class UpgradeSourceCodeTask extends FormatSourceTask {

	public UpgradeSourceCodeTask() {
		Project project = getProject();

		ObjectFactory objectFactory = project.getObjects();

		toVersionProperty = objectFactory.property(String.class);
	}

	@Override
	public void exec() {
		String toVersion = toVersionProperty.getOrNull();

		List<String> toVersionValues = getToVersionValues();

		if (!toVersionValues.contains(toVersion)) {
			throw new GradleException(
				StringBundler.concat(
					toVersion, " is an invalid target Liferay version. Run ",
					"the command blade gw help --task upgradeSourceCode for a ",
					"list of valid Liferay versions."));
		}

		addSourceFormatterProperty("upgrade.to.release.version", toVersion);

		super.exec();
	}

	@Input
	@Option(
		description = "The version of Liferay to target when upgrading the source code.",
		option = "to-version"
	)
	public Property<String> getToVersionProperty() {
		return toVersionProperty;
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

	protected final Property<String> toVersionProperty;

}