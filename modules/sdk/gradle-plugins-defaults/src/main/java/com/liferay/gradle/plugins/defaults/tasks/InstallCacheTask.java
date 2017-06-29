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

package com.liferay.gradle.plugins.defaults.tasks;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import groovy.lang.Closure;

import groovy.util.AntBuilder;
import groovy.util.CharsetToolkit;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.hash.HashValue;

/**
 * @author Andrea Di Giorgi
 */
public class InstallCacheTask extends DefaultTask {

	public InstallCacheTask() {
		Project project = getProject();

		Gradle gradle = project.getGradle();

		_cacheRootDir = new File(
			gradle.getGradleUserHomeDir(), "caches/modules-2/files-2.1");

		_mavenRootDir = new Callable<File>() {

			@Override
			public File call() throws Exception {
				return GradleUtil.getMavenLocalDir(getProject());
			}

		};
	}

	@Input
	public String getArtifactGroup() {
		return GradleUtil.toString(_artifactGroup);
	}

	@Input
	public String getArtifactName() {
		return GradleUtil.toString(_artifactName);
	}

	@Input
	public String getArtifactVersion() {
		return GradleUtil.toString(_artifactVersion);
	}

	@OutputDirectory
	public File getCacheDestinationDir() {
		CacheFormat cacheFormat = getCacheFormat();

		String groupDirName = getArtifactGroup();

		if (cacheFormat == CacheFormat.MAVEN) {
			groupDirName = groupDirName.replace('.', '/');
		}

		return new File(
			getCacheRootDir(),
			groupDirName + "/" + getArtifactName() + "/" +
				getArtifactVersion());
	}

	@Input
	public CacheFormat getCacheFormat() {
		return _cacheFormat;
	}

	@Input
	public File getCacheRootDir() {
		return GradleUtil.toFile(getProject(), _cacheRootDir);
	}

	@InputDirectory
	public File getMavenInputDir() {
		String artifactGroup = getArtifactGroup();

		return new File(
			getMavenRootDir(),
			artifactGroup.replace('.', '/') + "/" + getArtifactName() + "/" +
				getArtifactVersion());
	}

	@Input
	public File getMavenRootDir() {
		return GradleUtil.toFile(getProject(), _mavenRootDir);
	}

	@TaskAction
	public void installCache() throws IOException {
		_installCache("jar");
		_installCache("pom");
	}

	public void setArtifactGroup(Object artifactGroup) {
		_artifactGroup = artifactGroup;
	}

	public void setArtifactName(Object artifactName) {
		_artifactName = artifactName;
	}

	public void setArtifactVersion(Object artifactVersion) {
		_artifactVersion = artifactVersion;
	}

	public void setCacheFormat(CacheFormat cacheFormat) {
		_cacheFormat = cacheFormat;
	}

	public void setCacheRootDir(Object cacheRootDir) {
		_cacheRootDir = cacheRootDir;
	}

	public void setMavenRootDir(Object mavenRootDir) {
		_mavenRootDir = mavenRootDir;
	}

	public enum CacheFormat {

		GRADLE, MAVEN

	}

	private void _copy(final File file, final File destinationDir) {
		Project project = getProject();

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.from(file);
					copySpec.into(destinationDir);
				}

			});
	}

	private void _installCache(String extension) throws IOException {
		CacheFormat cacheFormat = getCacheFormat();

		File file = new File(
			getMavenInputDir(),
			getArtifactName() + "-" + getArtifactVersion() + "." + extension);

		if (!file.exists()) {
			throw new GradleException("Unable to find " + file);
		}

		if (extension.equals("pom")) {
			file = _normalizeTextFile(file);
		}

		File destinationDir = getCacheDestinationDir();

		if (cacheFormat == CacheFormat.GRADLE) {
			HashValue hashValue = HashUtil.sha1(file);

			String hash = hashValue.asHexString();

			hash = hash.replaceFirst("^0*", "");

			destinationDir = new File(destinationDir, hash);
		}

		_copy(file, destinationDir);
	}

	private File _normalizeTextFile(File file) throws IOException {
		Project project = getProject();

		final File tempFile = new File(getTemporaryDir(), file.getName());

		_copy(file, tempFile.getParentFile());

		CharsetToolkit charsetToolkit = new CharsetToolkit(tempFile);

		final Charset charset = charsetToolkit.getCharset();

		project.ant(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(AntBuilder antBuilder) {
					Map<String, Object> args = new HashMap<>();

					args.put("encoding", charset.name());
					args.put("eol", "lf");
					args.put("file", tempFile);
					args.put("fixlast", false);
					args.put("outputencoding", StandardCharsets.UTF_8);

					antBuilder.invokeMethod("fixcrlf", args);
				}

			});

		return tempFile;
	}

	private Object _artifactGroup;
	private Object _artifactName;
	private Object _artifactVersion;
	private CacheFormat _cacheFormat = CacheFormat.GRADLE;
	private Object _cacheRootDir;
	private Object _mavenRootDir;

}