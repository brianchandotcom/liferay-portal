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

package com.liferay.gradle.plugins.patcher;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.ReplaceLeadingPathAction;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.tools.ant.filters.FixCrLfFilter;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecResult;
import org.gradle.process.ExecSpec;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class PatchTask extends DefaultTask {

	public static final String PATCHED_SRC_DIR_MAPPING_DEFAULT_EXTENSION = "*";

	public PatchTask() {
		_originalLibConfigurationName = new Callable<String>() {

			@Override
			public String call() throws Exception {
				Project project = getProject();

				ConfigurationContainer configurationContainer =
					project.getConfigurations();

				Configuration configuration = configurationContainer.findByName(
					"compileClasspath");

				if (configuration != null) {
					return configuration.getName();
				}

				return JavaPlugin.COMPILE_CONFIGURATION_NAME;
			}

		};

		_originalLibFile = new Callable<File>() {

			@Override
			public File call() throws Exception {
				return getOriginalLibModuleFile();
			}

		};

		_originalLibSrcFile = new Callable<File>() {

			@Override
			public File call() throws Exception {
				return FileUtil.get(getProject(), getOriginalLibSrcUrl());
			}

		};

		args("--no-backup-if-mismatch", "--strip=1");
	}

	public PatchTask args(Iterable<Object> args) {
		GUtil.addToCollection(_args, args);

		return this;
	}

	public PatchTask args(Object... args) {
		return args(Arrays.asList(args));
	}

	public PatchTask fileNames(Iterable<Object> fileNames) {
		GUtil.addToCollection(_fileNames, fileNames);

		return this;
	}

	public PatchTask fileNames(Object... fileNames) {
		return fileNames(Arrays.asList(fileNames));
	}

	public List<String> getArgs() {
		return GradleUtil.toStringList(_args);
	}

	@Input
	public List<String> getFileNames() {
		return GradleUtil.toStringList(_fileNames);
	}

	public String getOriginalLibConfigurationName() {
		return GradleUtil.toString(_originalLibConfigurationName);
	}

	@InputFile
	public File getOriginalLibFile() {
		return GradleUtil.toFile(getProject(), _originalLibFile);
	}

	public String getOriginalLibModuleGroup() {
		Dependency dependency = getOriginalLibDependency();

		return dependency.getGroup();
	}

	public String getOriginalLibModuleName() {
		return GradleUtil.toString(_originalLibModuleName);
	}

	public String getOriginalLibModuleVersion() {
		Dependency dependency = getOriginalLibDependency();

		return dependency.getVersion();
	}

	public String getOriginalLibSrcBaseUrl() {
		return GradleUtil.toString(_originalLibSrcBaseUrl);
	}

	@Input
	public String getOriginalLibSrcDirName() {
		return GradleUtil.toString(_originalLibSrcDirName);
	}

	@InputFile
	public File getOriginalLibSrcFile() {
		return GradleUtil.toFile(getProject(), _originalLibSrcFile);
	}

	public Map<String, File> getPatchedSrcDirMappings() {
		Map<String, File> patchedSrcDirMappings = new HashMap<>();

		for (Map.Entry<String, Object> entry :
				_patchedSrcDirMappings.entrySet()) {

			String extension = entry.getKey();
			File dir = GradleUtil.toFile(getProject(), entry.getValue());

			patchedSrcDirMappings.put(extension, dir);
		}

		return patchedSrcDirMappings;
	}

	@OutputFiles
	public FileCollection getPatchedSrcFiles() {
		Project project = getProject();

		Map<File, ConfigurableFileTree> patchedSrcFileTreeMap = new HashMap<>();

		for (String fileName : getFileNames()) {
			File patchedDir = getPatchedSrcDir(fileName);

			ConfigurableFileTree configurableFileTree =
				patchedSrcFileTreeMap.get(patchedDir);

			if (configurableFileTree == null) {
				configurableFileTree = project.fileTree(patchedDir);

				patchedSrcFileTreeMap.put(patchedDir, configurableFileTree);
			}

			configurableFileTree.include(fileName);
		}

		Collection<ConfigurableFileTree> patchedSrcFileTrees =
			patchedSrcFileTreeMap.values();

		return project.files(patchedSrcFileTrees.toArray());
	}

	public File getPatchesDir() {
		return GradleUtil.toFile(getProject(), _patchesDir);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getPatchFiles() {
		Project project = getProject();

		if (!_patchFiles.isEmpty()) {
			return project.files(_patchFiles);
		}

		return project.fileTree(_patchesDir);
	}

	public boolean isCopyOriginalLibClasses() {
		return _copyOriginalLibClasses;
	}

	@TaskAction
	public void patch() throws Exception {
		final Project project = getProject();

		File patchesTemporaryDir = fixPatchFiles();
		final File srcTemporaryDir = fixSrcFiles();

		for (final File patchFile : getSortedFiles(patchesTemporaryDir)) {
			final ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			ExecResult execResult = project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						execSpec.args(getArgs());

						execSpec.args(
							"--input=" +
								FileUtil.relativize(
									patchFile, srcTemporaryDir));

						execSpec.setExecutable("patch");
						execSpec.setIgnoreExitValue(true);
						execSpec.setStandardOutput(byteArrayOutputStream);
						execSpec.setWorkingDir(srcTemporaryDir);
					}

				});

			System.out.println(byteArrayOutputStream.toString());

			execResult.rethrowFailure();

			execResult.assertNormalExitValue();
		}

		FileTree fileTree = project.fileTree(srcTemporaryDir);

		for (File file : fileTree) {
			File patchedSrcDir = getPatchedSrcDir(file.getName());

			if (patchedSrcDir == null) {
				continue;
			}

			Path patchedSrcDirPath = patchedSrcDir.toPath();

			String relativePath = FileUtil.relativize(file, srcTemporaryDir);

			patchedSrcDirPath = patchedSrcDirPath.resolve(relativePath);

			Files.createDirectories(patchedSrcDirPath.getParent());

			Files.move(
				file.toPath(), patchedSrcDirPath,
				StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public PatchTask patchedSrcDirMapping(String extension, Object dir) {
		_patchedSrcDirMappings.put(extension, dir);

		return this;
	}

	public PatchTask patchFiles(Iterable<Object> patchFiles) {
		GUtil.addToCollection(_patchFiles, patchFiles);

		return this;
	}

	public PatchTask patchFiles(Object... patchFiles) {
		return patchFiles(Arrays.asList(patchFiles));
	}

	public void setArgs(Iterable<Object> args) {
		_args.clear();

		args(args);
	}

	public void setArgs(Object... args) {
		setArgs(Arrays.asList(args));
	}

	public void setCopyOriginalLibClasses(boolean copyOriginalLibClasses) {
		_copyOriginalLibClasses = copyOriginalLibClasses;
	}

	public void setFileNames(Iterable<Object> fileNames) {
		_fileNames.clear();

		fileNames(fileNames);
	}

	public void setOriginalLibConfigurationName(
		Object originalLibConfigurationName) {

		_originalLibConfigurationName = originalLibConfigurationName;
	}

	public void setOriginalLibFile(Object originalLibFile) {
		_originalLibFile = originalLibFile;
	}

	public void setOriginalLibModuleName(Object originalLibModuleName) {
		_originalLibModuleName = originalLibModuleName;
	}

	public void setOriginalLibSrcBaseUrl(Object originalLibSrcBaseUrl) {
		_originalLibSrcBaseUrl = originalLibSrcBaseUrl;
	}

	public void setOriginalLibSrcDirName(Object originalLibSrcDirName) {
		_originalLibSrcDirName = originalLibSrcDirName;
	}

	public void setOriginalLibSrcFile(Object originalLibSrcFile) {
		_originalLibSrcFile = originalLibSrcFile;
	}

	public void setPatchedSrcDirMappings(
		Map<String, Object> patchedSrcDirMappings) {

		_patchedSrcDirMappings.clear();

		_patchedSrcDirMappings.putAll(patchedSrcDirMappings);
	}

	public void setPatchesDir(Object patchesDir) {
		_patchesDir = patchesDir;
	}

	public void setPatchFiles(Iterable<Object> patchFiles) {
		_patchFiles.clear();

		patchFiles(patchFiles);
	}

	protected File fixPatchFiles() {
		final Project project = getProject();

		final File temporaryDir = new File(getTemporaryDir(), "patches");

		project.delete(temporaryDir);

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.filter(_fixCrLfArgs, FixCrLfFilter.class);
					copySpec.from(getPatchFiles());
					copySpec.into(temporaryDir);
					copySpec.setIncludeEmptyDirs(false);
				}

			});

		return temporaryDir;
	}

	protected File fixSrcFiles() {
		final Project project = getProject();

		final File temporaryDir = new File(getTemporaryDir(), "src");

		project.delete(temporaryDir);

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					String originalLibSrcDirName = getOriginalLibSrcDirName();

					if (!originalLibSrcDirName.equals(".")) {
						Map<Object, Object> leadingPathReplacementsMap =
							new HashMap<>();

						leadingPathReplacementsMap.put(
							originalLibSrcDirName, "");

						copySpec.eachFile(
							new ReplaceLeadingPathAction(
								leadingPathReplacementsMap));
					}

					copySpec.filter(_fixCrLfArgs, FixCrLfFilter.class);
					copySpec.from(project.zipTree(getOriginalLibSrcFile()));
					copySpec.include(getFileNames());
					copySpec.into(temporaryDir);
					copySpec.setIncludeEmptyDirs(false);
				}

			});

		return temporaryDir;
	}

	protected Dependency getOriginalLibDependency() {
		Configuration configuration = GradleUtil.getConfiguration(
			getProject(), getOriginalLibConfigurationName());

		ResolvableDependencies resolvableDependencies =
			configuration.getIncoming();

		String moduleName = getOriginalLibModuleName();

		for (Dependency dependency : resolvableDependencies.getDependencies()) {
			if (moduleName.equals(dependency.getName())) {
				return dependency;
			}
		}

		throw new GradleException("Unable to find original lib " + moduleName);
	}

	protected File getOriginalLibModuleFile() {
		String configurationName = getOriginalLibConfigurationName();
		String moduleGroup = getOriginalLibModuleGroup();
		String moduleName = getOriginalLibModuleName();
		String moduleVersion = getOriginalLibModuleVersion();

		if (Validator.isNull(configurationName) ||
			Validator.isNull(moduleGroup) || Validator.isNull(moduleName) ||
			Validator.isNull(moduleVersion)) {

			return null;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			getProject(), configurationName);

		ResolvedConfiguration resolvedConfiguration =
			configuration.getResolvedConfiguration();

		for (ResolvedArtifact resolvedArtifact :
				resolvedConfiguration.getResolvedArtifacts()) {

			ResolvedModuleVersion resolvedModuleVersion =
				resolvedArtifact.getModuleVersion();

			ModuleVersionIdentifier moduleVersionIdentifier =
				resolvedModuleVersion.getId();

			if (moduleGroup.equals(moduleVersionIdentifier.getGroup()) &&
				moduleName.equals(moduleVersionIdentifier.getName()) &&
				moduleVersion.equals(moduleVersionIdentifier.getVersion())) {

				return resolvedArtifact.getFile();
			}
		}

		return null;
	}

	protected String getOriginalLibSrcUrl() {
		StringBuilder sb = new StringBuilder();

		String baseUrl = getOriginalLibSrcBaseUrl();

		if (Validator.isNotNull(baseUrl)) {
			sb.append(baseUrl);

			if (baseUrl.charAt(baseUrl.length() - 1) != '/') {
				sb.append('/');
			}
		}
		else {
			sb.append(_BASE_URL);

			String moduleGroup = getOriginalLibModuleGroup();

			sb.append(moduleGroup.replace('.', '/'));

			sb.append('/');
		}

		sb.append(getOriginalLibModuleName());
		sb.append('/');
		sb.append(getOriginalLibModuleVersion());
		sb.append('/');
		sb.append(getOriginalLibModuleName());
		sb.append('-');
		sb.append(getOriginalLibModuleVersion());
		sb.append("-sources.jar");

		return sb.toString();
	}

	protected File getPatchedSrcDir(String fileName) {
		String extension = PATCHED_SRC_DIR_MAPPING_DEFAULT_EXTENSION;

		int pos = fileName.indexOf('.');

		if (pos != -1) {
			extension = fileName.substring(pos + 1);
		}

		Object patchedSrcDir = _patchedSrcDirMappings.get(extension);

		if (patchedSrcDir == null) {
			patchedSrcDir = _patchedSrcDirMappings.get(
				PATCHED_SRC_DIR_MAPPING_DEFAULT_EXTENSION);
		}

		return GradleUtil.toFile(getProject(), patchedSrcDir);
	}

	protected List<File> getSortedFiles(File dir) {
		List<File> sortedFiles = new ArrayList<>();

		Project project = getProject();

		FileTree fileTree = project.fileTree(dir);

		GUtil.addToCollection(sortedFiles, fileTree);

		Collections.sort(sortedFiles);

		return sortedFiles;
	}

	private static final String _BASE_URL =
		"http://repo.maven.apache.org/maven2/";

	private static final Map<String, Object> _fixCrLfArgs =
		new HashMap<String, Object>() {
			{
				put("eof", FixCrLfFilter.AddAsisRemove.newInstance("remove"));
				put("eol", FixCrLfFilter.CrLf.newInstance("lf"));
				put("fixlast", false);
			}
		};

	private final List<Object> _args = new ArrayList<>();
	private boolean _copyOriginalLibClasses = true;
	private final List<Object> _fileNames = new ArrayList<>();
	private Object _originalLibConfigurationName;
	private Object _originalLibFile;
	private Object _originalLibModuleName;
	private Object _originalLibSrcBaseUrl;
	private Object _originalLibSrcDirName = ".";
	private Object _originalLibSrcFile;
	private final Map<String, Object> _patchedSrcDirMappings = new HashMap<>();
	private Object _patchesDir = "patches";
	private final List<Object> _patchFiles = new ArrayList<>();

}