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
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSTask extends JavaExec {

	public BuildCSSTask() {
		setDirNames("/");
		setMain("com.liferay.css.builder.CSSBuilder");
	}

	public BuildCSSTask dirNames(Iterable<Object> dirNames) {
		GUtil.addToCollection(_dirNames, dirNames);

		return this;
	}

	public BuildCSSTask dirNames(Object ... dirNames) {
		return dirNames(Arrays.asList(dirNames));
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@OutputDirectories
	public FileCollection getCSSCacheDirs() {
		Project project = getProject();

		Set<File> cssCacheDirs = new HashSet<>();

		FileCollection cssFiles = getCSSFiles();

		for (File cssFile : cssFiles) {
			File cssCacheDir = project.file(cssFile + "/../.sass-cache");

			cssCacheDirs.add(cssCacheDir);
		}

		return project.files(cssCacheDirs);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getCSSFiles() {
		Project project = getProject();

		List<String> dirNames = getDirNames();
		File docrootDir = getDocrootDir();

		if (dirNames.isEmpty() || (docrootDir == null)) {
			return project.files();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", docrootDir);
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

		return project.fileTree(args);
	}

	public List<String> getDirNames() {
		return GradleUtil.toStringList(_dirNames);
	}

	public File getDocrootDir() {
		return GradleUtil.toFile(getProject(), _docrootDir);
	}

	@InputDirectory
	public File getPortalCommonDir() {
		return GradleUtil.toFile(getProject(), _portalCommonDir);
	}

	@Input
	public int getPrecision() {
		return GradleUtil.toInteger(_precision);
	}

	@Input
	public List<String> getRtlExcludedPathRegexps() {
		return GradleUtil.toStringList(_rtlExcludedPathRegexps);
	}

	@Input
	@Optional
	public String getSassCompilerClassName() {
		return GradleUtil.toString(_sassCompilerClassName);
	}

	@OutputFiles
	public FileCollection getSourceMapFiles() {
		Project project = getProject();

		List<File> sourceMapFiles = new ArrayList<>();

		if (isGenerateSourceMap()) {
			FileCollection cssFiles = getCSSFiles();

			for (File cssFile : cssFiles) {
				File sourceMapFile = project.file(cssFile + ".map");

				sourceMapFiles.add(sourceMapFile);
			}
		}

		return project.files(sourceMapFiles);
	}

	@Input
	public boolean isGenerateSourceMap() {
		return _generateSourceMap;
	}

	public BuildCSSTask rtlExcludedPathRegexps(
		Iterable<Object> rtlExcludedPathRegexps) {

		GUtil.addToCollection(_rtlExcludedPathRegexps, rtlExcludedPathRegexps);

		return this;
	}

	public BuildCSSTask rtlExcludedPathRegexps(
		Object ... rtlExcludedPathRegexps) {

		return rtlExcludedPathRegexps(Arrays.asList(rtlExcludedPathRegexps));
	}

	public void setDirNames(Iterable<Object> dirNames) {
		_dirNames.clear();

		dirNames(dirNames);
	}

	public void setDirNames(Object ... dirNames) {
		setDirNames(Arrays.asList(dirNames));
	}

	public void setDocrootDir(Object docrootDir) {
		_docrootDir = docrootDir;
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_generateSourceMap = generateSourceMap;
	}

	public void setPortalCommonDir(Object portalCommonDir) {
		_portalCommonDir = portalCommonDir;
	}

	public void setPrecision(Object precision) {
		_precision = precision;
	}

	public void setRtlExcludedPathRegexps(
		Iterable<Object> rtlExcludedPathRegexps) {

		_rtlExcludedPathRegexps.clear();

		rtlExcludedPathRegexps(rtlExcludedPathRegexps);
	}

	public void setRtlExcludedPathRegexps(Object ... rtlExcludedPathRegexps) {
		setRtlExcludedPathRegexps(Arrays.asList(rtlExcludedPathRegexps));
	}

	public void setSassCompilerClassName(Object sassCompilerClassName) {
		_sassCompilerClassName = sassCompilerClassName;
	}

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		List<String> dirNames = getDirNames();

		if (dirNames.size() == 1) {
			args.add("sass.dir=/" + _removeLeadingSlash(dirNames.get(0)));
		}
		else {
			for (int i = 0; i < dirNames.size(); i++) {
				String dirName = dirNames.get(i);

				args.add("sass.dir." + i + "=/" + _removeLeadingSlash(dirName));
			}
		}

		String docrootDirName = FileUtil.getAbsolutePath(getDocrootDir());

		args.add("sass.docroot.dir=" + _removeTrailingSlash(docrootDirName));

		args.add("sass.generate.source.map=" + isGenerateSourceMap());

		String portalCommonDirName = FileUtil.getAbsolutePath(
			getPortalCommonDir());

		args.add("sass.portal.common.dir=" + portalCommonDirName);

		args.add("sass.precision=" + getPrecision());

		String rtlExcludedPathRegexps = CollectionUtils.join(
			",", getRtlExcludedPathRegexps());

		args.add("sass.rtl.excluded.path.regexps=" + rtlExcludedPathRegexps);

		String sassCompilerClassName = getSassCompilerClassName();

		if (Validator.isNotNull(sassCompilerClassName)) {
			args.add("sass.compiler.class.name=" + sassCompilerClassName);
		}

		return args;
	}

	private String _removeLeadingSlash(String path) {
		if (Validator.isNull(path)) {
			return path;
		}

		path = path.replace('\\', '/');

		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}

		return path;
	}

	private String _removeTrailingSlash(String path) {
		if (Validator.isNull(path)) {
			return path;
		}

		path = path.replace('\\', '/');

		if (path.charAt(path.length() - 1) == '/') {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	private final Set<Object> _dirNames = new LinkedHashSet<>();
	private Object _docrootDir;
	private boolean _generateSourceMap;
	private Object _portalCommonDir;
	private Object _precision = CSSBuilderArgs.PRECISION;
	private final Set<Object> _rtlExcludedPathRegexps = new LinkedHashSet<>();
	private Object _sassCompilerClassName;

}