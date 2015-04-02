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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.InputDirectory;
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

		if (liferayExtension.isOsgiPlugin()) {
			_sassDocrootDir = project.file("src/META-INF/resources");
		}
		else {
			_sassDocrootDir = project.file("docroot");
		}
	}

	@Override
	public void exec() {
		copySassPortalCommon();

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>(3);

		args.add("sass.dir=/");

		File sassDocrootDir = getSassDocrootDir();

		args.add("sass.docroot.dir=" + sassDocrootDir.toString());

		File sassPortalCommonDir = new File(
			getPortalWebDir(), "html/css/common");

		args.add("sass.portal.common.dir=" + sassPortalCommonDir.toString());

		return args;
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.SassToCssBuilder";
	}

	@OutputDirectory
	public File getPortalWebDir() {
		return new File(liferayExtension.getTmpDir(), "portal-web");
	}

	@OutputDirectories
	public Iterable<File> getSassCacheDirs() {
		Set<File> sassCacheDirs = new HashSet<>();

		Iterable<File> sassFiles = getSassFiles();

		for (File sassFile : sassFiles) {
			File sassCacheDir = project.file(sassFile + "/../.sass-cache");

			sassCacheDirs.add(sassCacheDir);
		}

		return sassCacheDirs;
	}

	@InputDirectory
	public File getSassDocrootDir() {
		return _sassDocrootDir;
	}

	@InputFiles
	@SkipWhenEmpty
	public Iterable<File> getSassFiles() {
		Map<String, Object> args = new HashMap<>();

		args.put("dir", getSassDocrootDir());
		args.put("exclude", "**/.sass-cache/**");
		args.put("include", "**/*.css");

		return project.fileTree(args);
	}

	public boolean isLegacy() {
		return _legacy;
	}

	public void setLegacy(boolean legacy) {
		_legacy = legacy;
	}

	public void setSassDocrootDir(File sassDocrootDir) {
		_sassDocrootDir = sassDocrootDir;
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

	protected void copySassPortalCommon() {
		final File portalWebDir = getPortalWebDir();

		File portalWebHtmlDir = new File(portalWebDir, "html");

		if (portalWebHtmlDir.exists()) {
			return;
		}

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				FileTree fileTree = project.zipTree(
					_portalWebConfiguration.getSingleFile());

				CopySpec fileTreeCopySpec = copySpec.from(fileTree);

				fileTreeCopySpec.include("html/css/**/*", "html/themes/**/*");

				copySpec.into(portalWebDir);
			}

		};

		project.copy(closure);
	}

	@Override
	protected String getToolName() {
		return "SassToCssBuilder";
	}

	private static final String _PORTAL_WEB_CONFIGURATION_NAME = "portalWeb";

	private boolean _legacy;
	private final Configuration _portalWebConfiguration;
	private File _sassDocrootDir;

}