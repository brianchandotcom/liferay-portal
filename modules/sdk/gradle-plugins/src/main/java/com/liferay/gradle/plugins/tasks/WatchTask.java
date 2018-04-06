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

import com.liferay.gogo.shell.client.GogoShellClient;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;
import org.gradle.api.tasks.incremental.InputFileDetails;

/**
 * @author Gregory Amerson
 */
public class WatchTask extends DefaultTask {

	@InputDirectory
	public File getBundleDir() {
		return GradleUtil.toFile(getProject(), _bundleDir);
	}

	@InputFiles
	@org.gradle.api.tasks.Optional
	public FileCollection getFragments() {
		return _fragments;
	}

	public void setBundleDir(Object bundleDir) {
		_bundleDir = bundleDir;
	}

	public void setFragments(FileCollection fragments) {
		_fragments = fragments;
	}

	@TaskAction
	public void watch(IncrementalTaskInputs inputs) throws IOException {
		Project project = getProject();

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		if (!startParameter.isContinuous()) {
			throw new GradleException(
				"Task must be executed in continuous mode: gradle watch (-t " +
					"| --continuous)");
		}

		Logger logger = getLogger();

		Long installedBundleId = _installedBundleIds.get(getBundleDir());

		if ((installedBundleId == null) || (installedBundleId < 1)) {
			_fullInstall();
		}
		else if (inputs.isIncremental()) {
			List<File> modifiedFiles = _getModifiedFiles(inputs);

			if (modifiedFiles.isEmpty()) {
				logger.quiet("No files changed. Skipping bundle refresh.");
			}
			else if (_manifestChanged(modifiedFiles)) {
				_refresh();
			}
			else if (_filesChangedByExtension(
						modifiedFiles, _classloaderFileExtensions)) {

				_refresh();
			}
			else {
				logger.quiet(
					"Only resources changed. Skipping bundle refresh.");
			}
		}
		else {
			_fullInstall();
		}
	}

	private static String _getExtension(File file) {
		String name = file.getName();

		int index = name.indexOf('.');

		String extension = (index > 0) ? name.substring(index) : name;

		return extension.toLowerCase();
	}

	private static <K, V> Map<K, V> _mapDifference(
		Map<? extends K, ? extends V> left,
		Map<? extends K, ? extends V> right) {

		Map<K, V> difference = new HashMap<>();

		difference.putAll(left);
		difference.putAll(right);

		Set<Entry<K, V>> entrySet = difference.entrySet();

		entrySet.removeAll(
			left.size() <= right.size() ? left.entrySet() : right.entrySet());

		return difference;
	}

	private static String _send(GogoShellClient gogoShellClient, String command)
		throws IOException {

		String response = gogoShellClient.send(command);

		if (response.startsWith(command)) {
			String substring = response.substring(command.length());

			return substring.trim();
		}
		else {
			return response;
		}
	}

	private boolean _filesChangedByExtension(
		Collection<File> files, Collection<String> extensions) {

		Stream<File> fileStream = files.stream();

		Map<String, List<File>> filesByExtension = fileStream.collect(
			Collectors.groupingBy(WatchTask::_getExtension));

		Set<String> extensionKeys = filesByExtension.keySet();

		Stream<String> extensionKeysStream = extensionKeys.stream();

		return extensionKeysStream.filter(
			extension -> extensions.contains(extension)
		).findAny(
		).isPresent();
	}

	private void _fullInstall() throws IOException {
		try (GogoShellClient gogoShellClient = new GogoShellClient()) {
			File manifestFile = new File(
				getBundleDir(), "META-INF/MANIFEST.MF");

			try (InputStream inputStream =
					Files.newInputStream(manifestFile.toPath())) {

				Manifest manifest = new Manifest(inputStream);

				long installedBundleId = _install(
					getBundleDir(), gogoShellClient);

				_installedAttributes.put(
					getBundleDir(), manifest.getMainAttributes());

				_installedBundleIds.put(getBundleDir(), installedBundleId);

				FileCollection fileCollection = getFragments();

				Set<File> fragments = fileCollection.getFiles();

				for (File fragment : fragments) {
					_install(fragment, gogoShellClient, false);
				}

				_refresh();
			}
		}
	}

	private List<File> _getModifiedFiles(IncrementalTaskInputs inputs) {
		List<File> modifiedFiles = new ArrayList<>();

		inputs.outOfDate(
			new Action<InputFileDetails>() {

				@Override
				public void execute(InputFileDetails inputFileDetails) {
					if (inputFileDetails.isAdded() ||
						inputFileDetails.isModified()) {

						modifiedFiles.add(inputFileDetails.getFile());
					}
				}

			});

		inputs.removed(
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

	private long _install(File file, GogoShellClient gogoShellClient)
		throws IOException {

		return _install(file, gogoShellClient, true);
	}

	private long _install(
			File file, GogoShellClient gogoShellClient, boolean start)
		throws IOException {

		Logger logger = getLogger();

		long bundleId = -1;

		URI uri = file.toURI();

		String url = "reference:" + uri.toASCIIString();

		String response = _send(gogoShellClient, "install " + url);

		if (start) {
			Matcher matcher = _installResponsePattern.matcher(response);

			if (matcher.matches()) {
				logger.quiet("Installed bundle at dir: " + file);

				String bundleIdString = matcher.group(1);

				bundleId = Long.parseLong(bundleIdString);

				_start(bundleId, gogoShellClient);
			}
			else {
				logger.error("Unable to install bundle: {}", response);
			}
		}

		return bundleId;
	}

	private boolean _manifestChanged(List<File> modifiedFiles)
		throws IOException {

		Stream<File> stream = modifiedFiles.stream();

		Optional<File> modifiedManifestFile = stream.filter(
			file -> {
				String absolutePath = file.getAbsolutePath();

				return absolutePath.endsWith("META-INF/MANIFEST.MF");
			}
		).findFirst(
		).map(
			manifestFile -> {
				try (InputStream inputStream =
						Files.newInputStream(manifestFile.toPath())) {

					Manifest manifest = new Manifest(inputStream);

					Attributes attributes = manifest.getMainAttributes();

					Map<Object, Object> difference = _mapDifference(
						attributes, _installedAttributes.get(getBundleDir()));

					Set<Entry<Object, Object>> entrySet = difference.entrySet();

					entrySet.removeIf(
						entry -> {
							Object key = entry.getKey();

							return _ignoredManifestKeys.contains(
								key.toString());
						});

					if (difference.isEmpty()) {
						return null;
					}

					getLogger().quiet(
						"Detected differences in manifest: " + difference);
				}
				catch (IOException ioe) {
				}

				return manifestFile;
			}
		);

		return modifiedManifestFile.isPresent();
	}

	private void _refresh() throws IOException {
		try (GogoShellClient gogoShellClient = new GogoShellClient()) {
			Long installedBundleId = _installedBundleIds.get(getBundleDir());

			_send(
				gogoShellClient,
				String.format("refresh %s", installedBundleId));

			getLogger().quiet("Refreshed {}", installedBundleId);
		}
	}

	private String _start(long bundleId, GogoShellClient gogoShellClient)
		throws IOException {

		Logger logger = getLogger();
		String response = _send(gogoShellClient, "start " + bundleId);

		if (Validator.isNotNull(response)) {
			logger.error("Start error: {}", response);
		}
		else {
			logger.quiet("Started " + bundleId);
		}

		return response;
	}

	private static final List<String> _classloaderFileExtensions =
		Arrays.asList(".class", ".jsp", ".jspf");
	private static final List<String> _ignoredManifestKeys = Arrays.asList(
		"Bnd-LastModified");
	private static final Map<File, Attributes> _installedAttributes =
		new HashMap<>();
	private static final Map<File, Long> _installedBundleIds = new HashMap<>();
	private static final Pattern _installResponsePattern = Pattern.compile(
		".*Bundle ID: (.*$).*", Pattern.DOTALL | Pattern.MULTILINE);

	private Object _bundleDir;
	private FileCollection _fragments;

}