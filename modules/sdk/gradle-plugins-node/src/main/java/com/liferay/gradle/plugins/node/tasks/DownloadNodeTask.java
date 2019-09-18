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
import com.liferay.gradle.plugins.node.internal.util.DigestUtil;
import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.internal.util.NodePluginUtil;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
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
					String oldDigest = DigestUtil.getDigest(_getDigestFile());

					if (Objects.equals(oldDigest, _getDigest())) {
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
					String nodeFileName = nodeFile.getName();

					if (nodeFileName.endsWith(".exe")) {
						copySpec.from(nodeFile.getParentFile());
					}
					else {
						copySpec.eachFile(new StripPathSegmentsAction(1));
						copySpec.setIncludeEmptyDirs(false);

						if (nodeFileName.endsWith(".zip")) {
							copySpec.from(project.zipTree(nodeFile));
						}
						else {
							copySpec.from(project.tarTree(nodeFile));
						}
					}

					copySpec.into(nodeDir);
				}

			});

		String npmUrl = getNpmUrl();

		final File npmDir = NodePluginUtil.getNpmDir(nodeDir);

		if (Validator.isNotNull(npmUrl)) {
			final File npmFile = _download(npmUrl, null);

			project.delete(npmDir);

			project.copy(
				new Action<CopySpec>() {

					@Override
					public void execute(CopySpec copySpec) {
						copySpec.eachFile(new StripPathSegmentsAction(1));
						copySpec.from(project.tarTree(npmFile));
						copySpec.into(npmDir);
						copySpec.setIncludeEmptyDirs(false);
					}

				});
		}

		if (!OSDetector.isWindows()) {
			File binDir = new File(nodeDir, "bin");

			Path binDirPath = binDir.toPath();

			Path linkPath = binDirPath.resolve("npm");

			Files.deleteIfExists(linkPath);

			File linkTargetFile = new File(npmDir, "bin/npm-cli.js");

			Files.createSymbolicLink(linkPath, linkTargetFile.toPath());
		}

		String digest = _getDigest();
		File digestFile = _getDigestFile();

		FileUtil.write(digestFile, digest.getBytes(StandardCharsets.UTF_8));
	}

	@OutputDirectory
	public File getNodeDir() {
		return _nodeExecutor.getNodeDir();
	}

	@Input
	public String getNodeUrl() {
		return GradleUtil.toString(_nodeUrl);
	}

	@Input
	@Optional
	public String getNpmUrl() {
		return GradleUtil.toString(_npmUrl);
	}

	public void setNodeDir(Object nodeDir) {
		_nodeExecutor.setNodeDir(nodeDir);
	}

	public void setNodeUrl(Object nodeUrl) {
		_nodeUrl = nodeUrl;
	}

	public void setNpmUrl(Object npmUrl) {
		_npmUrl = npmUrl;
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

	private String _getDigest() {
		return DigestUtil.getDigest(getNodeUrl(), getNpmUrl());
	}

	private File _getDigestFile() {
		return new File(getNodeDir(), ".digest");
	}

	private final NodeExecutor _nodeExecutor;
	private Object _nodeUrl;
	private Object _npmUrl;

}