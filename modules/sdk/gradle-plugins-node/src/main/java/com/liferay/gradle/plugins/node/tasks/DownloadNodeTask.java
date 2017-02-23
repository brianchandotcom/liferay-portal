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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.internal.NodeExecutor;
import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;
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
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class DownloadNodeTask extends DefaultTask {

	public DownloadNodeTask() {
		_nodeExecutor = new NodeExecutor(getProject());

		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					File nodeDir = getNodeDir();

					if ((nodeDir != null) && nodeDir.exists()) {
						return false;
					}

					return true;
				}

			});
	}

	@TaskAction
	public void downloadNode() throws IOException {
		final File nodeDir = getNodeDir();
		final Project project = getProject();

		final File nodeFile = _download(getNodeUrl(), null);

		project.delete(nodeDir);

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
					copySpec.from(project.tarTree(nodeFile));
					copySpec.into(nodeDir);
					copySpec.setIncludeEmptyDirs(false);
				}

			});

		if (OSDetector.isWindows()) {
			File nodeBinDir = new File(getNodeDir(), "bin");

			_download(getNodeExeUrl(), nodeBinDir);
		}
	}

	@OutputDirectory
	public File getNodeDir() {
		return _nodeExecutor.getNodeDir();
	}

	@Input
	public String getNodeExeUrl() {
		return GradleUtil.toString(_nodeExeUrl);
	}

	@Input
	public String getNodeUrl() {
		return GradleUtil.toString(_nodeUrl);
	}

	public void setNodeDir(Object nodeDir) {
		_nodeExecutor.setNodeDir(nodeDir);
	}

	public void setNodeExeUrl(Object nodeExeUrl) {
		_nodeExeUrl = nodeExeUrl;
	}

	public void setNodeUrl(Object nodeUrl) {
		_nodeUrl = nodeUrl;
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

	private final NodeExecutor _nodeExecutor;
	private Object _nodeExeUrl;
	private Object _nodeUrl;

}