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

package com.liferay.gradle.plugins.css.builder;

import com.liferay.css.builder.CSSBuilderArgs;
import com.liferay.gradle.util.ArrayUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSTask extends JavaExec {

	public BuildCSSTask() {
		_project = getProject();
	}

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		String[] dirNames = getDirNames();

		if (dirNames.length == 1) {
			args.add("sass.dir=/" + _removeLeadingSlash(dirNames[0]));
		}
		else {
			for (int i = 0; i < dirNames.length; i++) {
				args.add(
					"sass.dir." + i + "=/" + _removeLeadingSlash(dirNames[i]));
			}
		}

		args.add(
			"sass.docroot.dir=" + _removeTrailingSlash(getDocrootDirName()));
		args.add("sass.portal.common.dir=" + getPortalCommonDirName());
		args.add(
			"sass.rtl.excluded.path.regexps=" +
				StringUtil.merge(getRtlExcludedPathRegexps(), ","));
		args.add("sass.compiler.class.name=" + getSassCompilerClassName());

		return args;
	}

	@Override
	public FileCollection getClasspath() {
		return GradleUtil.getConfiguration(
			_project, CSSBuilderPlugin.CSS_BUILDER_CONFIGURATION_NAME);
	}

	@OutputDirectories
	public FileCollection getCSSCacheDirs() {
		Set<File> cssCacheDirs = new HashSet<>();

		FileCollection cssFiles = getCSSFiles();

		for (File cssFile : cssFiles) {
			File cssCacheDir = _project.file(cssFile + "/../.sass-cache");

			cssCacheDirs.add(cssCacheDir);
		}

		return _project.files(cssCacheDirs);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getCSSFiles() {
		String[] dirNames = getDirNames();

		if (ArrayUtil.isEmpty(dirNames)) {
			return _project.files();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", _project.file(getDocrootDirName()));
		args.put("exclude", "**/.sass-cache/**");

		for (String dirName : dirNames) {
			dirName = dirName.replace('\\', '/');

			if (dirName.equals("/")) {
				dirName = "";
			}
			else {
				dirName = _removeLeadingSlash(dirName);
				dirName = _removeTrailingSlash(dirName);

				dirName += "/";
			}

			List<String> includes = new ArrayList<>(2);

			includes.add(dirName + "**/*.css");
			includes.add(dirName + "**/*.scss");

			args.put("includes", includes);
		}

		return _project.fileTree(args);
	}

	public String[] getDirNames() {
		return _cssBuilderArgs.getDirNames();
	}

	public String getDocrootDirName() {
		return _cssBuilderArgs.getDocrootDirName();
	}

	@Override
	public String getMain() {
		return "com.liferay.css.builder.CSSBuilder";
	}

	@InputDirectory
	public File getPortalCommonDir() {
		return _project.file(getPortalCommonDirName());
	}

	public String getPortalCommonDirName() {
		return _cssBuilderArgs.getPortalCommonDirName();
	}

	@Input
	public String[] getRtlExcludedPathRegexps() {
		return _cssBuilderArgs.getRtlExcludedPathRegexps();
	}

	@Input
	@Optional
	public String getSassCompilerClassName() {
		return _cssBuilderArgs.getSassCompilerClassName();
	}

	@Override
	public File getWorkingDir() {
		return _project.getProjectDir();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setDirNames(String ... dirNames) {
		_cssBuilderArgs.setDirNames(dirNames);
	}

	public void setDocrootDirName(String docrootDirName) {
		_cssBuilderArgs.setDocrootDirName(docrootDirName);
	}

	public void setPortalCommonDirName(String portalCommonDirName) {
		_cssBuilderArgs.setPortalCommonDirName(portalCommonDirName);
	}

	public void setRtlExcludedPathRegexps(String ... rtlExcludedPathRegexps) {
		_cssBuilderArgs.setRtlExcludedPathRegexps(rtlExcludedPathRegexps);
	}

	public void setSassCompilerClassName(String sassCompilerClassName) {
		_cssBuilderArgs.setSassCompilerClassName(sassCompilerClassName);
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private String _removeLeadingSlash(String path) {
		path = path.replace('\\', '/');

		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}

		return path;
	}

	private String _removeTrailingSlash(String path) {
		path = path.replace('\\', '/');

		if (path.charAt(path.length() - 1) == '/') {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	private final CSSBuilderArgs _cssBuilderArgs = new CSSBuilderArgs();
	private final Project _project;

}