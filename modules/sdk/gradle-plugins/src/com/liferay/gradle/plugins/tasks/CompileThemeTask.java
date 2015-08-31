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

import com.liferay.gradle.plugins.LiferayThemePlugin;
import com.liferay.gradle.util.ArrayUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CompileThemeTask extends DefaultTask {

	public CompileThemeTask() {
		_project = getProject();
	}

	@TaskAction
	public void compileTheme() {
		copyThemeParent();

		copyDiffs();
	}

	@InputDirectory
	@Optional
	public File getDiffsDir() {
		return GradleUtil.toFile(_project, _diffsDir);
	}

	@InputDirectory
	@Optional
	public File getFrontendThemesDir() {
		return GradleUtil.toFile(_project, _frontendThemesDir);
	}

	@InputFile
	@Optional
	public File getFrontendThemesFile() {
		return GradleUtil.toFile(_project, _frontendThemesFile);
	}

	@OutputDirectories
	public FileCollection getThemeDirs() {
		if (getDiffsDir() == null) {
			return _project.files();
		}

		List<File> themeDirs = new ArrayList<>(_THEME_DIR_NAMES.length);

		File themeRootDir = getThemeRootDir();

		for (String dirName : _THEME_DIR_NAMES) {
			File dir = new File(themeRootDir, dirName);

			themeDirs.add(dir);
		}

		return _project.files(themeDirs);
	}

	@Input
	@Optional
	public String getThemeParent() {
		return GradleUtil.toString(_themeParent);
	}

	public Project getThemeParentProject() {
		String themeParent = getThemeParent();

		if (Validator.isNull(themeParent) ||
			ArrayUtil.contains(_PORTAL_THEMES, themeParent)) {

			return null;
		}

		if (_themeParentProject == null) {
			File themeParentDir = _project.file(themeParent);

			_themeParentProject = GradleUtil.getProject(
				_project.getRootProject(), themeParentDir);
		}

		return _themeParentProject;
	}

	public File getThemeRootDir() {
		return GradleUtil.toFile(_project, _themeRootDir);
	}

	@Input
	public Set<String> getThemeTypes() {
		return _themeTypes;
	}

	public void setDiffsDir(Object diffsDir) {
		_diffsDir = diffsDir;
	}

	public void setFrontendThemesDir(Object frontendThemesDir) {
		_frontendThemesDir = frontendThemesDir;
	}

	public void setFrontendThemesFile(Object frontendThemesFile) {
		_frontendThemesFile = frontendThemesFile;
	}

	public void setThemeParent(Object themeParent) {
		_themeParent = themeParent;
		_themeParentProject = null;
	}

	public void setThemeRootDir(Object themeRootDir) {
		_themeRootDir = themeRootDir;
	}

	public void setThemeTypes(Iterable<String> themeTypes) {
		_themeTypes.clear();

		themeTypes(themeTypes);
	}

	public CompileThemeTask themeTypes(Iterable<String> themeTypes) {
		GUtil.addToCollection(_themeTypes, themeTypes);

		return this;
	}

	public CompileThemeTask themeTypes(String ... themeTypes) {
		return themeTypes(Arrays.asList(themeTypes));
	}

	protected void copyDiffs() {
		final File diffsDir = getDiffsDir();

		if ((diffsDir == null) || !diffsDir.exists()) {
			return;
		}

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				copySpec.from(diffsDir);
				copySpec.into(getThemeRootDir());
			}

		};

		_project.copy(closure);
	}

	protected void copyPortalThemeDir(
		String theme, String[] excludes, String include) {

		copyPortalThemeDir(theme, excludes, new String[] {include});
	}

	protected void copyPortalThemeDir(
		String theme, final String[] excludes, final String[] includes) {

		final String prefix = "/" + theme + "/";

		final File themeResourcesDir = getThemeResourcesDir(theme);
		final File frontendThemesFile = getFrontendThemesFile();
		final File themeRootDir = getThemeRootDir();

		if (themeResourcesDir != null) {
			Closure<Void> closure = new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(new File(themeResourcesDir, prefix));

					if (ArrayUtil.isNotEmpty(excludes)) {
						copySpec.exclude(excludes);
					}

					copySpec.include(includes);
					copySpec.into(themeRootDir);
				}

			};

			_project.copy(closure);
		}
		else if (frontendThemesFile != null) {
			String jarPrefix = "META-INF/resources" + prefix;

			final String[] prefixedExcludes = StringUtil.prepend(
				excludes, jarPrefix);
			final String[] prefixedIncludes = StringUtil.prepend(
				includes, jarPrefix);

			Closure<Void> closure = new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(5));

					if (ArrayUtil.isNotEmpty(prefixedExcludes)) {
						copySpec.exclude(prefixedExcludes);
					}

					copySpec.from(_project.zipTree(frontendThemesFile));
					copySpec.include(prefixedIncludes);
					copySpec.into(themeRootDir);
					copySpec.setIncludeEmptyDirs(false);
				}

			};

			_project.copy(closure);
		}
		else {
			throw new GradleException("Unable to find frontend themes web");
		}
	}

	protected void copyThemeParent() {
		String themeParent = getThemeParent();

		if (Validator.isNull(themeParent)) {
			return;
		}

		if (themeParent.equals("_styled") || themeParent.equals("_unstyled")) {
			copyThemeParentUnstyled();
		}

		if (themeParent.equals("_styled")) {
			copyThemeParentStyled();
		}
		else if (themeParent.equals("admin") || themeParent.equals("classic")) {
			copyThemeParentPortal();
		}
		else if (!themeParent.equals("_unstyled")) {
			copyThemeParentProject();
		}
	}

	protected void copyThemeParentPortal() {
		String themeParent = getThemeParent();

		copyPortalThemeDir(
			themeParent, new String[] {
				"**/.sass-cache/**", "_diffs/**", "templates/**"
			},
			"**");

		Set<String> themeTypes = getThemeTypes();

		String[] includes = StringUtil.prepend(
			themeTypes.toArray(new String[themeTypes.size()]), "templates/*.");

		copyPortalThemeDir(themeParent, null, includes);
	}

	protected void copyThemeParentProject() {
		Project themeParentProject = getThemeParentProject();

		CompileThemeTask compileParentThemeTask =
			(CompileThemeTask)GradleUtil.getTask(
				themeParentProject, LiferayThemePlugin.COMPILE_THEME_TASK_NAME);

		final File parentThemeRootDir =
			compileParentThemeTask.getThemeRootDir();

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				copySpec.from(parentThemeRootDir);

				for (String dirName : _THEME_DIR_NAMES) {
					copySpec.include(dirName + "/**");
				}

				copySpec.into(getThemeRootDir());
			}

		};

		_project.copy(closure);
	}

	protected void copyThemeParentStyled() {
		copyPortalThemeDir(
			"_styled",
			new String[] {"**/*.css", "npm-debug.log", "package.json"}, "**");
	}

	protected void copyThemeParentUnstyled() {
		copyPortalThemeDir(
			"_unstyled",
			new String[] {
				"**/*.css", "npm-debug.log", "package.json", "templates/**"
			},
			"**");

		Set<String> themeTypes = getThemeTypes();

		String[] themeTypesArray = themeTypes.toArray(
			new String[themeTypes.size()]);

		String[] includes = StringUtil.prepend(
			themeTypesArray, "templates/**/*.");

		copyPortalThemeDir("_unstyled", null, includes);
	}

	protected File getThemeResourcesDir(String theme) {
		String themeDir = "frontend-themes-";

		if (theme.equals("_styled") || theme.equals("_unstyled")) {
			themeDir += theme.substring(1);
		}
		else {
			themeDir += theme + "-web";
		}

		return new File(
			getFrontendThemesDir(), themeDir + "/src/META-INF/resources");
	}

	private static final String[] _PORTAL_THEMES = {
		"_styled", "_unstyled", "admin", "classic"
	};

	private static final String[] _THEME_DIR_NAMES = {
		"css", "images", "js", "templates"
	};

	private Object _diffsDir;
	private Object _frontendThemesDir;
	private Object _frontendThemesFile;
	private final Project _project;
	private Object _themeParent;
	private Project _themeParentProject;
	private Object _themeRootDir;
	private final Set<String> _themeTypes = new HashSet<>();

}