/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.go.task;

import com.liferay.gradle.plugins.go.GoExtension;
import com.liferay.gradle.plugins.go.internal.GoExecutor;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Peter Shin
 */
@CacheableTask
public class DownloadGoTask extends DefaultTask {

	public DownloadGoTask() {
		_goExecutor = new GoExecutor(getProject());

		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					File goDir = getGoDir();

					if ((goDir != null) && goDir.exists()) {
						return false;
					}

					return true;
				}

			});

		GoExtension goExtension = GradleUtil.getExtension(
			getProject(), GoExtension.class);

		setGoDir(goExtension.getGoDir());
		setGoUrl(goExtension.getGoUrl());
	}

	@TaskAction
	public void downloadGo() throws IOException {
		final File goDir = getGoDir();
		final Project project = getProject();

		final File goFile = _download(getGoUrl(), null);

		project.delete(goDir);

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					String goFileName = goFile.getName();

					copySpec.eachFile(new StripPathSegmentsAction(1));
					copySpec.setIncludeEmptyDirs(false);

					if (goFileName.endsWith(".zip")) {
						copySpec.from(project.zipTree(goFile));
					}
					else {
						copySpec.from(project.tarTree(goFile));
					}

					copySpec.into(goDir);
				}

			});
	}

	@OutputDirectory
	public File getGoDir() {
		return _goExecutor.getGoDir();
	}

	@Input
	public String getGoUrl() {
		return GradleUtil.toString(_goUrl);
	}

	public void setGoDir(Object goDir) {
		_goExecutor.setGoDir(goDir);
	}

	public void setGoUrl(Object goUrl) {
		_goUrl = goUrl;
	}

	private File _download(String url, File destinationFile)
		throws IOException {

		String protocol = url.substring(0, url.indexOf(':'));

		String proxyPassword = System.getProperty(protocol + ".proxyPassword");
		String proxyUser = System.getProperty(protocol + ".proxyUser");

		if (Validator.isNotNull(proxyPassword) &&
			Validator.isNotNull(proxyUser)) {

			Project project = getProject();

			String nonProxyHosts = System.getProperty(
				protocol + ".nonProxyHosts");
			String proxyHost = System.getProperty(protocol + ".proxyHost");
			String proxyPort = System.getProperty(protocol + ".proxyPort");

			AntBuilder antBuilder = project.getAnt();

			Map<String, String> args = new HashMap<>();

			args.put("nonproxyhosts", nonProxyHosts);
			args.put("proxyhost", proxyHost);
			args.put("proxypassword", proxyPassword);
			args.put("proxyport", proxyPort);
			args.put("proxyuser", proxyUser);

			antBuilder.invokeMethod("setproxy", args);
		}

		return FileUtil.get(getProject(), url, destinationFile);
	}

	private final GoExecutor _goExecutor;
	private Object _goUrl;

}