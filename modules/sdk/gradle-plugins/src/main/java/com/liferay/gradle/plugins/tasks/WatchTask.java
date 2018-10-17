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

import aQute.bnd.osgi.Constants;

import com.liferay.gogo.shell.client.GogoShellClient;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;
import org.gradle.api.tasks.incremental.InputFileDetails;
import org.gradle.util.GUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.dto.BundleDTO;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 */
public class WatchTask extends DefaultTask {

	public WatchTask() {
		classLoaderFileExtensions(".class", ".jsp", ".jspf", ".properties");
		ignoredManifestKeys(Constants.BND_LASTMODIFIED);
	}

	@SuppressWarnings("unchecked")
	public WatchTask classLoaderFileExtensions(
		Iterable<String> classLoaderFileExtensions) {

		GUtil.addToCollection(
			_classLoaderFileExtensions, classLoaderFileExtensions);

		return this;
	}

	public WatchTask classLoaderFileExtensions(
		String... classLoaderFileExtensions) {

		return classLoaderFileExtensions(
			Arrays.asList(classLoaderFileExtensions));
	}

	@InputDirectory
	public File getBundleDir() {
		return GradleUtil.toFile(getProject(), _bundleDir);
	}

	@Input
	public String getBundleSymbolicName() {
		return GradleUtil.toString(_bundleSymbolicName);
	}

	@Input
	public Set<String> getClassLoaderFileExtensions() {
		return _classLoaderFileExtensions;
	}

	@InputFiles
	@Optional
	public FileCollection getFragments() {
		return _fragmentsFileCollection;
	}

	@Input
	public Set<String> getIgnoredManifestKeys() {
		return _ignoredManifestKeys;
	}

	@OutputFile
	public File getOutputFile() {
		Project project = getProject();

		return new File(project.getBuildDir(), "installedBundleId");
	}

	@SuppressWarnings("unchecked")
	public WatchTask ignoredManifestKeys(Iterable<String> ignoredManifestKeys) {
		GUtil.addToCollection(_ignoredManifestKeys, ignoredManifestKeys);

		return this;
	}

	public WatchTask ignoredManifestKeys(String... ignoredManifestKeys) {
		return ignoredManifestKeys(Arrays.asList(ignoredManifestKeys));
	}

	public void setBundleDir(Object bundleDir) {
		_bundleDir = bundleDir;
	}

	public void setBundleSymbolicName(Object bundleSymbolicName) {
		_bundleSymbolicName = bundleSymbolicName;
	}

	public void setClassLoaderFileExtensions(
		Iterable<String> classLoaderFileExtensions) {

		_classLoaderFileExtensions.clear();

		classLoaderFileExtensions(classLoaderFileExtensions);
	}

	public void setClassLoaderFileExtensions(
		String... classLoaderFileExtensions) {

		setClassLoaderFileExtensions(Arrays.asList(classLoaderFileExtensions));
	}

	public void setFragments(FileCollection fragmentsFileCollection) {
		_fragmentsFileCollection = fragmentsFileCollection;
	}

	public void setIgnoredManifestKeys(Iterable<String> ignoredManifestKeys) {
		_ignoredManifestKeys.clear();

		ignoredManifestKeys(ignoredManifestKeys);
	}

	public void setIgnoredManifestKeys(String... ignoredManifestKeys) {
		setIgnoredManifestKeys(Arrays.asList(ignoredManifestKeys));
	}

	@TaskAction
	public void watch(IncrementalTaskInputs incrementalTaskInputs)
		throws IOException {

		Project project = getProject();

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		if (!startParameter.isContinuous()) {
			throw new GradleException(
				"Task must be executed in continuous mode: gradle watch (-t " +
					"| --continuous)");
		}

		Logger logger = getLogger();

		long installedBundleId = -1;

		try (GogoShellClient gogoShellClient = new GogoShellClient()) {
			installedBundleId = _getInstalledBundleId(gogoShellClient);

			if ((installedBundleId < 1) ||
				!incrementalTaskInputs.isIncremental()) {

				_installOrUpdateBundle(installedBundleId, gogoShellClient);

				return;
			}

			List<File> modifiedFiles = _getModifiedFiles(incrementalTaskInputs);

			if (_isClassLoaderFileChanged(modifiedFiles) ||
				_isManifestChanged(modifiedFiles)) {

				_refreshBundle(installedBundleId, gogoShellClient);

				return;
			}

			if (logger.isQuietEnabled()) {
				if (modifiedFiles.isEmpty()) {
					logger.quiet("No files changed. Skipping bundle refresh.");
				}
				else {
					logger.quiet(
						"Only resources changed. Skipping bundle refresh.");
				}
			}
		}
	}

	private static long _getBundleId(
			String bundleSymbolicName, GogoShellClient gogoShellClient)
		throws IOException {

		String command = String.format("lb -s %s", bundleSymbolicName);

		String response = _sendGogoShellCommand(gogoShellClient, command);

		try (Scanner scanner = new Scanner(response)) {
			List<String> lines = new ArrayList<>();

			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}

			if (lines.size() > 2) {
				String gogoLine = lines.get(2);

				BundleDTO bundleDTO = _parseBundleDTO(gogoLine);

				if ((bundleDTO != null) &&
					bundleSymbolicName.equals(bundleDTO.symbolicName)) {

					return bundleDTO.id;
				}
			}
		}

		return -1;
	}

	private static <K, V> Map<K, V> _getDifferences(
		Map<? extends K, ? extends V> leftMap,
		Map<? extends K, ? extends V> rightMap) {

		Map<K, V> differences = new HashMap<>();

		differences.putAll(leftMap);
		differences.putAll(rightMap);

		Set<Map.Entry<K, V>> entrySet = differences.entrySet();

		if (leftMap.size() <= rightMap.size()) {
			entrySet.removeAll(leftMap.entrySet());
		}
		else {
			entrySet.removeAll(rightMap.entrySet());
		}

		return differences;
	}

	private static final int _getState(String state) {
		String bundleState = state.toUpperCase();

		if ("ACTIVE".equals(bundleState)) {
			return Bundle.ACTIVE;
		}
		else if ("INSTALLED".equals(bundleState)) {
			return Bundle.INSTALLED;
		}
		else if ("RESOLVED".equals(bundleState)) {
			return Bundle.RESOLVED;
		}
		else if ("STARTING".equals(bundleState)) {
			return Bundle.STARTING;
		}
		else if ("STOPPING".equals(bundleState)) {
			return Bundle.STOPPING;
		}
		else if ("UNINSTALLED".equals(bundleState)) {
			return Bundle.UNINSTALLED;
		}

		return 0;
	}

	private static boolean _isWarDir(File file) {
		if (!file.isDirectory()) {
			return false;
		}

		File webInfDir = new File(file, "WEB-INF");

		return webInfDir.exists();
	}

	private static final BundleDTO _newBundleDTO(
		Long id, int state, String symbolicName) {

		BundleDTO bundle = new BundleDTO();

		bundle.id = id;
		bundle.state = state;
		bundle.symbolicName = symbolicName;

		return bundle;
	}

	private static final BundleDTO _parseBundleDTO(String line) {
		String[] fields = line.split("\\|");

		Long id = Long.parseLong(fields[0].trim());

		int state = _getState(fields[1].trim());

		String symbolicName = fields[3];

		return _newBundleDTO(id, state, symbolicName);
	}

	private static String _sendGogoShellCommand(
			GogoShellClient gogoShellClient, String command)
		throws IOException {

		String response = gogoShellClient.send(command);

		if (response.startsWith(command)) {
			response = response.substring(command.length());

			response = response.trim();
		}

		return response;
	}

	private long _getInstalledBundleId(GogoShellClient gogoShellClient)
		throws IOException {

		File outputFile = getOutputFile();

		if (outputFile.exists()) {
			try {
				String installedBundleID = new String(
					Files.readAllBytes(outputFile.toPath()));

				return Long.parseLong(installedBundleID);
			}
			catch (Exception e) {
			}
		}

		String bundleSymbolicName = getBundleSymbolicName();

		if (bundleSymbolicName == null) {
			File manifestFile = new File(
				getBundleDir(), "META-INF/MANIFEST.MF");

			bundleSymbolicName = FileUtil.readManifestAttribute(
				manifestFile, Constants.BUNDLE_SYMBOLICNAME);
		}

		return _getBundleId(bundleSymbolicName, gogoShellClient);
	}

	private List<File> _getModifiedFiles(
		IncrementalTaskInputs incrementalTaskInputs) {

		final List<File> modifiedFiles = new ArrayList<>();

		incrementalTaskInputs.outOfDate(
			new Action<InputFileDetails>() {

				@Override
				public void execute(InputFileDetails inputFileDetails) {
					if (inputFileDetails.isAdded() ||
						inputFileDetails.isModified()) {

						modifiedFiles.add(inputFileDetails.getFile());
					}
				}

			});

		incrementalTaskInputs.removed(
			new Action<InputFileDetails>() {

				@Override
				public void execute(InputFileDetails inputFileDetails) {
					if (inputFileDetails.isRemoved()) {
						modifiedFiles.add(inputFileDetails.getFile());
					}
				}

			});

		return modifiedFiles;
	}

	private String _getReferenceInstallURL(File file) {
		URI uri = file.toURI();

		if (_isWarDir(file)) {
			StringBuilder sb = new StringBuilder();

			sb.append("webbundledir:%s");
			sb.append("?Bundle-SymbolicName=%s");
			sb.append("&amp\\;");
			sb.append("Web-ContextPath=/%s");

			return String.format(
				sb.toString(), uri.toASCIIString(), getBundleSymbolicName(),
				getBundleSymbolicName());
		}

		return "reference:" + uri.toASCIIString();
	}

	private long _installBundle(
			File file, GogoShellClient gogoShellClient, boolean start)
		throws IOException {

		long bundleId = -1;

		String url = _getReferenceInstallURL(file);

		String response = _sendGogoShellCommand(
			gogoShellClient, "install " + url);

		Matcher matcher = _installResponsePattern.matcher(response);

		Logger logger = getLogger();

		if (matcher.matches()) {
			if (logger.isQuietEnabled()) {
				logger.quiet("Installed bundle at {}", file);
			}

			String bundleIdString = matcher.group(1);

			bundleId = Long.parseLong(bundleIdString);
		}

		if (start) {
			_startBundle(bundleId, gogoShellClient);
		}

		if (bundleId < 0) {
			logger.error("Unable to install bundle: {}", response);
		}

		return bundleId;
	}

	private void _installOrUpdateBundle(
			long bundleId, GogoShellClient gogoShellClient)
		throws IOException {

		File bundleDir = getBundleDir();

		if (bundleId > 0) {
			_updateBundle(bundleDir, bundleId, gogoShellClient);
		}
		else {
			bundleId = _installBundle(bundleDir, gogoShellClient, true);
		}

		File manifestFile = new File(bundleDir, "META-INF/MANIFEST.MF");

		try (InputStream inputStream = new FileInputStream(manifestFile)) {
			Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();

			_installedAttributes.put(bundleDir, attributes);
		}

		FileCollection fileCollection = getFragments();

		boolean installedFragment = false;

		if (fileCollection != null) {
			Set<File> files = fileCollection.getFiles();

			for (File file : files) {
				long fragmentBundleId = _installBundle(
					file, gogoShellClient, false);

				if (fragmentBundleId > 0) {
					installedFragment = true;
				}
			}
		}

		String bundleIdString = String.valueOf(bundleId);
		File outputFile = getOutputFile();

		Files.write(
			outputFile.toPath(),
			bundleIdString.getBytes(StandardCharsets.UTF_8));

		if (installedFragment) {
			_refreshBundle(bundleId, gogoShellClient);
		}
	}

	private boolean _isClassLoaderFileChanged(List<File> modifiedFiles) {
		for (File file : modifiedFiles) {
			String extension = FileUtil.getExtension(file);

			if (_classLoaderFileExtensions.contains(extension)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isManifestChanged(List<File> modifiedFiles)
		throws IOException {

		File manifestFile = null;

		for (File file : modifiedFiles) {
			String absolutePath = FileUtil.getAbsolutePath(file);

			if (absolutePath.endsWith("/META-INF/MANIFEST.MF")) {
				manifestFile = file;

				break;
			}
		}

		if (manifestFile == null) {
			return false;
		}

		Map<Object, Object> differences = null;

		try (InputStream inputStream = new FileInputStream(manifestFile)) {
			Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();

			differences = _getDifferences(
				attributes, _installedAttributes.get(getBundleDir()));
		}

		Set<Map.Entry<Object, Object>> entrySet = differences.entrySet();

		Iterator<Map.Entry<Object, Object>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Object, Object> entry = iterator.next();

			String key = String.valueOf(entry.getKey());

			if (_ignoredManifestKeys.contains(key)) {
				iterator.remove();
			}
		}

		if (differences.isEmpty()) {
			return false;
		}

		Logger logger = getLogger();

		if (logger.isQuietEnabled()) {
			logger.quiet("Detected differences in manifest: {}", differences);
		}

		return true;
	}

	private void _refreshBundle(long bundleId, GogoShellClient gogoShellClient)
		throws IOException {

		Logger logger = getLogger();

		_sendGogoShellCommand(gogoShellClient, "refresh " + bundleId);

		if (logger.isQuietEnabled()) {
			logger.quiet("Refreshed bundle {}", bundleId);
		}
	}

	private void _startBundle(long bundleId, GogoShellClient gogoShellClient)
		throws IOException {

		String command = String.format("start %s", bundleId);

		String response = _sendGogoShellCommand(gogoShellClient, command);

		Logger logger = getLogger();

		if (logger.isQuietEnabled()) {
			logger.quiet("Bundle {} started. {}", bundleId, response);
		}
	}

	private void _updateBundle(
			File bundleDir, long bundleId, GogoShellClient gogoShellClient)
		throws IOException {

		Logger logger = getLogger();

		if (logger.isQuietEnabled()) {
			logger.quiet("Updating bundle {} from {}", bundleId, bundleDir);
		}

		String url = _getReferenceInstallURL(bundleDir);

		String command = String.format("update %s %s", bundleId, url);

		String response = _sendGogoShellCommand(gogoShellClient, command);

		if (logger.isQuietEnabled()) {
			logger.quiet("Bundle {} updated.\n{}\n", bundleId, response);
		}
	}

	private static final Map<File, Attributes> _installedAttributes =
		new HashMap<>();
	private static final Pattern _installResponsePattern = Pattern.compile(
		".*Bundle ID: (.*$).*", Pattern.DOTALL | Pattern.MULTILINE);

	private Object _bundleDir;
	private Object _bundleSymbolicName;
	private final Set<String> _classLoaderFileExtensions =
		new LinkedHashSet<>();
	private FileCollection _fragmentsFileCollection;
	private final Set<String> _ignoredManifestKeys = new LinkedHashSet<>();

}