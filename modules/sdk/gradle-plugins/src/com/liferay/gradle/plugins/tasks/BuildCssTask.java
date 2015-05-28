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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.util.FileUtil;

import groovy.lang.Closure;

import java.io.File;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.process.internal.streams.SafeStreams;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCssTask extends BasePortalToolsTask {

	@Override
	public void exec() {
		copyPortalCommon();

		FileCollection rootDirs = getRootDirs();

		if (rootDirs == null) {
			return;
		}

		for (File dir : rootDirs) {
			super.setErrorOutput(SafeStreams.systemErr());
			super.setStandardOutput(SafeStreams.systemOut());

			doExec(getArgs(dir));
		}
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("sass.dir=/");

		File cssPortalCommonDir = new File(getTmpDir(), "html/css/common");

		args.add(
			"sass.portal.common.dir=" +
				FileUtil.getAbsolutePath(cssPortalCommonDir));

		return args;
	}

	@OutputDirectories
	public Iterable<File> getCssCacheDirs() {
		Set<File> cssCacheDirs = new HashSet<>();

		Iterable<File> cssFiles = getCssFiles();

		for (File cssFile : cssFiles) {
			File cssCacheDir = project.file(cssFile + "/../.sass-cache");

			cssCacheDirs.add(cssCacheDir);
		}

		return cssCacheDirs;
	}

	@InputFiles
	@SkipWhenEmpty
	public Iterable<File> getCssFiles() {
		FileCollection rootDirs = getRootDirs();

		if ((rootDirs == null) || rootDirs.isEmpty()) {
			return Collections.emptyList();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", project.getProjectDir());
		args.put("exclude", "**/.sass-cache/**");

		for (File dir : rootDirs) {
			args.put("include", project.relativePath(dir) + "/**/*.css");
		}

		return project.fileTree(args);
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.SassToCssBuilder";
	}

	@InputFile
	public File getPortalWebFile() {
		return _portalWebFile;
	}

	@InputFiles
	public FileCollection getRootDirs() {
		return _rootDirs;
	}

	@OutputDirectory
	public File getTmpDir() {
		return _tmpDir;
	}

	@Input
	public boolean isLegacy() {
		return _legacy;
	}

	@Override
	public JavaExec setErrorOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	public void setLegacy(boolean legacy) {
		_legacy = legacy;
	}

	public void setPortalWebFile(File portalWebFile) {
		_portalWebFile = portalWebFile;
	}

	public void setRootDirs(Object rootDirs) {
		_rootDirs = project.files(rootDirs);
	}

	@Override
	public JavaExec setStandardOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	public void setTmpDir(File tmpDir) {
		_tmpDir = tmpDir;
	}

	@Override
	protected void addDependencies() {
		super.addDependencies();

		if (!isLegacy()) {
			addDependency(
				"com.liferay", "com.liferay.sass.compiler.jni",
				"1.0.0-SNAPSHOT");
		}

		addDependency("com.liferay", "com.liferay.rtl.css", "1.0.0-SNAPSHOT");
		addDependency("com.liferay", "com.liferay.ruby.gems", "1.0.0-SNAPSHOT");
		addDependency(
			"com.liferay", "com.liferay.sass.compiler.ruby", "1.0.0-SNAPSHOT");
		addDependency("com.liferay.portal", "util-slf4j", "default");
		addDependency("javax.portlet", "portlet-api", "2.0");
		addDependency("org.apache.ant", "ant", "1.8.2");
		addDependency("org.jruby", "jruby-complete", "1.6.5");
		addDependency("org.springframework", "spring-web", "3.2.10.RELEASE");
		addDependency("struts", "struts", "1.2.9");
	}

	protected void copyPortalCommon() {
		final File tmpDir = getTmpDir();

		File htmlDir = new File(tmpDir, "html");

		if (htmlDir.exists()) {
			return;
		}

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				FileTree fileTree = project.zipTree(getPortalWebFile());

				CopySpec fileTreeCopySpec = copySpec.from(fileTree);

				fileTreeCopySpec.include("html/css/**/*", "html/themes/**/*");

				copySpec.into(tmpDir);
			}

		};

		project.copy(closure);
	}

	protected List<String> getArgs(File rootDir) {
		List<String> args = getArgs();

		args.add("sass.docroot.dir=" + FileUtil.getAbsolutePath(rootDir));

		return args;
	}

	@Override
	protected String getToolName() {
		return "SassToCssBuilder";
	}

	private boolean _legacy;
	private File _portalWebFile;
	private FileCollection _rootDirs;
	private File _tmpDir;

}