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

import com.liferay.gradle.plugins.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCssTask extends BasePortalToolsTask {

	public BuildCssTask() {
		_portalWebConfiguration = GradleUtil.addConfiguration(
			project, _PORTAL_WEB_CONFIGURATION_NAME);

		_portalWebConfiguration.setDescription(
			"The portal-web configuration used for compiling CSS files.");
		_portalWebConfiguration.setVisible(false);

		GradleUtil.executeIfEmpty(
			_portalWebConfiguration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addPortalWebDependencies();
				}

			});
	}

	@Override
	public void exec() {
		copyPortalCommon();

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>(_cssDirNames.size() + 2);

		for (int i = 0; i < _cssDirNames.size(); i++) {
			String cssDirName = _cssDirNames.get(i);

			args.add("sass.dir." + i + "=/" + cssDirName);
		}

		args.add("sass.docroot.dir=" + project.getProjectDir());

		File cssPortalCommonDir = new File(getTmpDir(), "html/css/common");

		args.add("sass.portal.common.dir=" + cssPortalCommonDir);

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

	public List<String> getCssDirNames() {
		return _cssDirNames;
	}

	@InputFiles
	@SkipWhenEmpty
	public Iterable<File> getCssFiles() {
		if (_cssDirNames.isEmpty()) {
			return Collections.emptyList();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", project.getProjectDir());
		args.put("exclude", "**/.sass-cache/**");

		for (String cssDirName : _cssDirNames) {
			args.put("include", cssDirName + "/**/*.css");
		}

		return project.fileTree(args);
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.SassToCssBuilder";
	}

	@OutputDirectory
	public File getTmpDir() {
		return _tmpDir;
	}

	public boolean isLegacy() {
		return _legacy;
	}

	public void setLegacy(boolean legacy) {
		_legacy = legacy;
	}

	public void setTmpDir(File tmpDir) {
		_tmpDir = tmpDir;
	}

	@Override
	protected void addDependencies() {
		super.addDependencies();

		if (!isLegacy()) {
			addDependency(
				"com.liferay", "com.liferay.sass.compiler", "1.0.0-SNAPSHOT");
		}

		addDependency("com.liferay", "com.liferay.rtl.css", "1.0.0-SNAPSHOT");
		addDependency("com.liferay", "com.liferay.ruby.gems", "1.0.0-SNAPSHOT");
		addDependency("com.liferay.portal", "util-slf4j", "default");
		addDependency("javax.portlet", "portlet-api", "2.0");
		addDependency("org.apache.ant", "ant", "1.8.2");
		addDependency("org.jruby", "jruby-complete", "1.6.5");
		addDependency("org.springframework", "spring-web", "3.2.10.RELEASE");
		addDependency("struts", "struts", "1.2.9");
	}

	protected void addPortalWebDependencies() {
		GradleUtil.addDependency(
			project, _PORTAL_WEB_CONFIGURATION_NAME, "com.liferay.portal",
			"portal-web", "default");
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
				FileTree fileTree = project.zipTree(
					_portalWebConfiguration.getSingleFile());

				CopySpec fileTreeCopySpec = copySpec.from(fileTree);

				fileTreeCopySpec.include("html/css/**/*", "html/themes/**/*");

				copySpec.into(tmpDir);
			}

		};

		project.copy(closure);
	}

	@Override
	protected String getToolName() {
		return "SassToCssBuilder";
	}

	private static final String _PORTAL_WEB_CONFIGURATION_NAME = "portalWeb";

	private final List<String> _cssDirNames = new ArrayList<>();
	private boolean _legacy;
	private final Configuration _portalWebConfiguration;
	private File _tmpDir;

}