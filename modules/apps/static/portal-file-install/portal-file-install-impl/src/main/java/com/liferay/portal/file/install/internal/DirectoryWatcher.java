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

package com.liferay.portal.file.install.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.internal.manifest.Clause;
import com.liferay.portal.file.install.internal.manifest.Parser;
import com.liferay.portal.file.install.internal.version.VersionRange;
import com.liferay.portal.file.install.internal.version.VersionTable;
import com.liferay.portal.file.install.listener.ArtifactInstaller;
import com.liferay.portal.file.install.listener.ArtifactListener;
import com.liferay.portal.file.install.listener.ArtifactTransformer;
import com.liferay.portal.file.install.listener.ArtifactUrlTransformer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.wiring.BundleRevision;

/**
 * @author Matthew Tambara
 */
public class DirectoryWatcher extends Thread implements BundleListener {

	public static final String ACTIVE_LEVEL = "felix.fileinstall.active.level";

	public static final String CONFIG_ENCODING =
		"felix.fileinstall.configEncoding";

	public static final String DIR = "felix.fileinstall.dir";

	public static final String DISABLE_CONFIG_SAVE =
		"felix.fileinstall.disableConfigSave";

	public static final String DISABLE_NIO2 = "felix.fileinstall.disableNio2";

	public static final String ENABLE_CONFIG_SAVE =
		"felix.fileinstall.enableConfigSave";

	public static final String FILENAME = "felix.fileinstall.filename";

	public static final String FILTER = "felix.fileinstall.filter";

	public static final String FRAGMENT_SCOPE =
		"felix.fileinstall.fragmentRefreshScope";

	public static final String LOG_DEFAULT = "felix.fileinstall.log.default";

	public static final String LOG_JUL = "jul";

	public static final String LOG_LEVEL = "felix.fileinstall.log.level";

	public static final String LOG_STDOUT = "stdout";

	public static final String NO_INITIAL_DELAY =
		"felix.fileinstall.noInitialDelay";

	public static final String OPTIONAL_SCOPE =
		"felix.fileinstall.optionalImportRefreshScope";

	public static final String POLL = "felix.fileinstall.poll";

	public static final String SCOPE_ALL = "all";

	public static final String SCOPE_MANAGED = "managed";

	public static final String SCOPE_NONE = "none";

	public static final String START_LEVEL = "felix.fileinstall.start.level";

	public static final String START_NEW_BUNDLES =
		"felix.fileinstall.bundles.new.start";

	public static final String SUBDIR_MODE = "felix.fileinstall.subdir.mode";

	public static final String TMPDIR = "felix.fileinstall.tmpdir";

	public static final String UPDATE_WITH_LISTENERS =
		"felix.fileinstall.bundles.updateWithListeners";

	public static final String USE_START_ACTIVATION_POLICY =
		"felix.fileinstall.bundles.startActivationPolicy";

	public static final String USE_START_TRANSIENT =
		"felix.fileinstall.bundles.startTransient";

	public static final String WEB_START_LEVEL =
		"felix.fileinstall.web.start.level";

	public static String getThreadName(Map<String, String> properties) {
		String name = properties.get(DIR);

		if (name != null) {
			return name;
		}

		return "./load";
	}

	public DirectoryWatcher(
		FileInstall fileInstall, Map<String, String> properties,
		BundleContext bundleContext) {

		super("fileinstall-" + getThreadName(properties));

		_fileInstall = fileInstall;
		_properties = properties;
		_bundleContext = bundleContext;
		_systemBundle = bundleContext.getBundle(
			Constants.SYSTEM_BUNDLE_LOCATION);
		_poll = _getLong(properties, POLL, 2000);
		_logLevel = _getInt(
			properties, LOG_LEVEL, Util.getGlobalLogLevel(bundleContext));
		_watchedDirectory = _getFile(properties, DIR, new File("./load"));
		_verifyWatchedDir();
		_tmpDir = _getFile(properties, TMPDIR, null);
		_prepareTempDir();
		_startBundles = _getBoolean(properties, START_NEW_BUNDLES, true);
		_useStartTransient = _getBoolean(
			properties, USE_START_TRANSIENT, false);
		_useStartActivationPolicy = _getBoolean(
			properties, USE_START_ACTIVATION_POLICY, true);
		_filter = properties.get(FILTER);
		_noInitialDelay = _getBoolean(properties, NO_INITIAL_DELAY, false);
		_startLevel = _getInt(properties, START_LEVEL, 0);
		_activeLevel = _getInt(properties, ACTIVE_LEVEL, 0);
		_updateWithListeners = _getBoolean(
			properties, UPDATE_WITH_LISTENERS, false);
		_fragmentScope = properties.get(FRAGMENT_SCOPE);
		_optionalScope = properties.get(OPTIONAL_SCOPE);
		_disableNio2 = _getBoolean(properties, DISABLE_NIO2, false);
		_webStartLevel = _getInt(properties, WEB_START_LEVEL, _startLevel);
		_bundleContext.addBundleListener(this);

		if (_disableNio2) {
			scanner = new Scanner(
				_watchedDirectory, _filter, properties.get(SUBDIR_MODE));
		}
		else {
			try {
				scanner = new WatcherScanner(
					bundleContext, _watchedDirectory, _filter,
					properties.get(SUBDIR_MODE));
			}
			catch (Throwable throwable) {
				scanner = new Scanner(
					_watchedDirectory, _filter, properties.get(SUBDIR_MODE));
			}
		}
	}

	public void addListener(ArtifactListener listener, long timeStamp) {
		if (_updateWithListeners) {
			for (Artifact artifact : _getArtifacts()) {
				long bundleId = artifact.getBundleId();

				if ((artifact.getListener() == null) && (bundleId > 0)) {
					Bundle bundle = _bundleContext.getBundle(bundleId);

					if ((bundle != null) &&
						(bundle.getLastModified() < timeStamp)) {

						File path = artifact.getPath();

						if (listener.canHandle(path)) {
							synchronized (_processingFailures) {
								_processingFailures.add(path);
							}
						}
					}
				}
			}
		}

		synchronized (this) {
			notifyAll();
		}
	}

	@Override
	public void bundleChanged(BundleEvent bundleEvent) {
		int type = bundleEvent.getType();

		if (type == BundleEvent.UNINSTALLED) {
			List<Artifact> artifacts = _getArtifacts();

			Iterator<?> iterator = artifacts.iterator();

			while (iterator.hasNext()) {
				Artifact artifact = (Artifact)iterator.next();

				Bundle bundle = bundleEvent.getBundle();

				long bundleId = bundle.getBundleId();

				if (artifact.getBundleId() == bundleId) {
					_log(
						Util.Logger.LOG_DEBUG,
						StringBundler.concat(
							"Bundle ", bundleId, " has been uninstalled"),
						null);

					iterator.remove();

					break;
				}
			}
		}

		if ((type == BundleEvent.INSTALLED) || (type == BundleEvent.RESOLVED) ||
			(type == BundleEvent.UNINSTALLED) ||
			(type == BundleEvent.UNRESOLVED) || (type == BundleEvent.UPDATED)) {

			_setStateChanged(true);
		}
	}

	public void close() {
		_bundleContext.removeBundleListener(this);

		interrupt();

		for (Artifact artifact : _getArtifacts()) {
			_deleteTransformedFile(artifact);

			_deleteJaredDirectory(artifact);
		}

		try {
			scanner.close();
		}
		catch (IOException ioException) {
		}

		try {
			join(10000);
		}
		catch (InterruptedException interruptedException) {
		}
	}

	public Map<String, String> getProperties() {
		return _properties;
	}

	public void removeListener(ArtifactListener listener) {
		for (Artifact artifact : _getArtifacts()) {
			if (artifact.getListener() == listener) {
				artifact.setListener(null);
			}
		}

		synchronized (this) {
			notifyAll();
		}
	}

	public void run() {
		Lock readLock = _fileInstall.getReadLock();

		try {
			readLock.lockInterruptibly();
		}
		catch (InterruptedException interruptedException) {
			Thread currentThread = Thread.currentThread();

			currentThread.interrupt();

			_log(
				Util.Logger.LOG_INFO,
				StringBundler.concat(
					"Watcher for ", _watchedDirectory,
					" exiting because of interruption."),
				interruptedException);

			return;
		}

		try {
			_log(
				Util.Logger.LOG_DEBUG,
				StringBundler.concat(
					"{", POLL, " (ms) = ", _poll, ", ", DIR, " = ",
					_watchedDirectory.getAbsolutePath(), ", ", LOG_LEVEL, " = ",
					_logLevel, ", ", START_NEW_BUNDLES, " = ", _startBundles,
					", ", TMPDIR, " = ", _tmpDir, ", ", FILTER, " = ", _filter,
					", ", START_LEVEL, " = ", _startLevel, "}"),
				null);

			if (!_noInitialDelay) {
				try {
					Thread.sleep(_poll);
				}
				catch (InterruptedException interruptedException) {
					_log(
						Util.Logger.LOG_DEBUG,
						StringBundler.concat(
							"Watcher for ", _watchedDirectory,
							" was interrupted while waiting ", _poll,
							" milliseconds for initial directory scan."),
						interruptedException);

					return;
				}

				_initializeCurrentManagedBundles();
			}
		}
		finally {
			readLock.unlock();
		}

		while (!interrupted()) {
			try {
				FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
					FrameworkStartLevel.class);

				if ((frameworkStartLevel.getStartLevel() >= _activeLevel) &&
					(_systemBundle.getState() == Bundle.ACTIVE)) {

					Set<File> files = scanner.scan(false);

					if (files != null) {
						_process(files);
					}
				}

				synchronized (this) {
					wait(_poll);
				}
			}
			catch (InterruptedException interruptedException) {
				interrupt();

				return;
			}
			catch (Throwable throwable) {
				try {
					_bundleContext.getBundle();
				}
				catch (IllegalStateException illegalStateException) {
					return;
				}

				_log(
					Util.Logger.LOG_ERROR,
					"In main loop, we have serious trouble", throwable);
			}
		}
	}

	public void start() {
		if (_noInitialDelay) {
			_log(Util.Logger.LOG_DEBUG, "Starting initial scan", null);

			_initializeCurrentManagedBundles();

			Set<File> files = scanner.scan(true);

			if (files != null) {
				try {
					_process(files);
				}
				catch (InterruptedException interruptedException) {
					throw new RuntimeException(interruptedException);
				}
			}
		}

		super.start();
	}

	public final Scanner scanner;

	protected void findBundlesWithFragmentsToRefresh(Set<Bundle> toRefresh) {
		Set<Bundle> fragments = new HashSet<>();

		Set<Bundle> bundles = getScopedBundles(_fragmentScope);

		for (Bundle bundle : toRefresh) {
			if (bundle.getState() != Bundle.UNINSTALLED) {
				Dictionary<String, String> headers = bundle.getHeaders(
					StringPool.BLANK);

				String hostHeader = headers.get(Constants.FRAGMENT_HOST);

				if (hostHeader != null) {
					Clause[] clauses = Parser.parseHeader(hostHeader);

					if ((clauses != null) && (clauses.length > 0)) {
						Clause clause = clauses[0];

						for (Bundle hostBundle : bundles) {
							String hostSymbolicName =
								hostBundle.getSymbolicName();

							if ((hostSymbolicName != null) &&
								Objects.equals(
									hostSymbolicName, clause.getName())) {

								String versionString = clause.getAttribute(
									Constants.BUNDLE_VERSION_ATTRIBUTE);

								if (versionString != null) {
									VersionRange versionRange =
										VersionRange.parseVersionRange(
											versionString);

									headers = hostBundle.getHeaders(
										StringPool.BLANK);

									if (versionRange.contains(
											VersionTable.getVersion(
												headers.get(
													Constants.
														BUNDLE_VERSION)))) {

										fragments.add(hostBundle);
									}
								}
								else {
									fragments.add(hostBundle);
								}
							}
						}
					}
				}
			}
		}

		toRefresh.addAll(fragments);
	}

	protected void findBundlesWithOptionalPackagesToRefresh(
		Set<Bundle> toRefresh) {

		Set<Bundle> bundles = getScopedBundles(_optionalScope);

		bundles.removeAll(toRefresh);

		if (bundles.isEmpty()) {
			return;
		}

		Map<Bundle, List<Clause>> imports = new HashMap<>();

		Iterator<Bundle> iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			Dictionary<String, String> header = bundle.getHeaders(
				StringPool.BLANK);

			String importsString = header.get(Constants.IMPORT_PACKAGE);

			List<Clause> importsList = getOptionalImports(importsString);

			if (importsList.isEmpty()) {
				iterator.remove();
			}
			else {
				imports.put(bundle, importsList);
			}
		}

		if (bundles.isEmpty()) {
			return;
		}

		List<Clause> exports = new ArrayList<>();

		for (Bundle bundle : toRefresh) {
			if (bundle.getState() != Bundle.UNINSTALLED) {
				Dictionary<String, String> headers = bundle.getHeaders(
					StringPool.BLANK);

				String exportsString = headers.get(Constants.EXPORT_PACKAGE);

				if (exportsString != null) {
					Clause[] exportsList = Parser.parseHeader(exportsString);

					Collections.addAll(exports, exportsList);
				}
			}
		}

		iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			List<Clause> importsList = imports.get(bundle);

			Iterator<Clause> importIterator = importsList.iterator();

			while (importIterator.hasNext()) {
				Clause importClause = importIterator.next();

				boolean matching = false;

				for (Clause exportClause : exports) {
					if (Objects.equals(
							importClause.getName(), exportClause.getName())) {

						String exportVersionString = exportClause.getAttribute(
							Constants.VERSION_ATTRIBUTE);

						String importVersionString = importClause.getAttribute(
							Constants.VERSION_ATTRIBUTE);

						Version exported = Version.emptyVersion;

						if (exportVersionString != null) {
							exported = Version.parseVersion(
								exportVersionString);
						}

						VersionRange imported = VersionRange.ANY_VERSION;

						if (importVersionString != null) {
							imported = VersionRange.parseVersionRange(
								importVersionString);
						}

						if (imported.contains(exported)) {
							matching = true;

							break;
						}
					}
				}

				if (!matching) {
					importIterator.remove();
				}
			}

			if (importsList.isEmpty()) {
				iterator.remove();
			}
		}

		toRefresh.addAll(bundles);
	}

	protected List<Clause> getOptionalImports(String importsString) {
		Clause[] imports = Parser.parseHeader(importsString);

		List<Clause> result = new LinkedList<>();

		for (Clause importClause : imports) {
			String resolution = importClause.getDirective(
				Constants.RESOLUTION_DIRECTIVE);

			if (Constants.RESOLUTION_OPTIONAL.equals(resolution)) {
				result.add(importClause);
			}
		}

		return result;
	}

	protected Set<Bundle> getScopedBundles(String scope) {
		if (SCOPE_NONE.equals(scope)) {
			return new HashSet<>();
		}
		else if (SCOPE_MANAGED.equals(scope)) {
			Set<Bundle> bundles = new HashSet<>();

			for (Artifact artifact : _getArtifacts()) {
				long bundleId = artifact.getBundleId();

				if (bundleId > 0) {
					Bundle bundle = _bundleContext.getBundle(bundleId);

					if (bundle != null) {
						bundles.add(bundle);
					}
				}
			}

			return bundles;
		}
		else {
			return new HashSet<>(Arrays.asList(_bundleContext.getBundles()));
		}
	}

	private void _deleteJaredDirectory(Artifact artifact) {
		File directory = artifact.getJaredDirectory();

		if ((directory != null) && !directory.equals(artifact.getPath()) &&
			!directory.delete()) {

			_log(
				Util.Logger.LOG_WARNING,
				"Unable to delete jared artifact: " +
					directory.getAbsolutePath(),
				null);
		}
	}

	private void _deleteTransformedFile(Artifact artifact) {
		File file = artifact.getTransformed();

		if ((file != null) && !file.equals(artifact.getPath()) &&
			!file.delete()) {

			_log(
				Util.Logger.LOG_WARNING,
				"Unable to delete transformed artifact: " +
					file.getAbsolutePath(),
				null);
		}
	}

	private void _doProcess(Set<File> files) throws InterruptedException {
		List<ArtifactListener> listeners = _fileInstall.getListeners();
		List<Artifact> deleted = new ArrayList<>();
		List<Artifact> modified = new ArrayList<>();
		List<Artifact> created = new ArrayList<>();

		synchronized (_processingFailures) {
			files.addAll(_processingFailures);
			_processingFailures.clear();
		}

		for (File file : files) {
			boolean exists = file.exists();

			Artifact artifact = _getArtifact(file);

			if (!exists) {
				if (artifact != null) {
					_deleteJaredDirectory(artifact);
					_deleteTransformedFile(artifact);
					deleted.add(artifact);
				}
			}
			else {
				File jar = file;

				URL jaredUrl = null;

				try {
					URI uri = file.toURI();

					jaredUrl = uri.toURL();
				}
				catch (MalformedURLException malformedURLException) {
				}

				if (file.isDirectory()) {
					_prepareTempDir();

					try {
						jar = new File(_tmpDir, file.getName() + ".jar");

						Util.jarDir(file, jar);

						jaredUrl = new URL(
							JarDirUrlHandler.PROTOCOL, null, file.getPath());
					}
					catch (IOException ioException) {
						_log(
							Util.Logger.LOG_ERROR,
							"Unable to create jar for: " +
								file.getAbsolutePath(),
							ioException);

						continue;
					}
				}

				if (artifact != null) {
					artifact.setChecksum(scanner.getChecksum(file));

					if (artifact.getListener() == null) {
						ArtifactListener listener = _findListener(
							jar, listeners);

						if (listener == null) {
							synchronized (_processingFailures) {
								_processingFailures.add(file);
							}

							continue;
						}

						artifact.setListener(listener);
					}

					ArtifactListener artifactListener = artifact.getListener();

					if (!listeners.contains(artifactListener) ||
						!artifactListener.canHandle(jar)) {

						deleted.add(artifact);
					}
					else {
						_deleteTransformedFile(artifact);

						artifact.setJaredDirectory(jar);
						artifact.setJaredUrl(jaredUrl);

						if (_transformArtifact(artifact)) {
							modified.add(artifact);
						}
						else {
							_deleteJaredDirectory(artifact);

							deleted.add(artifact);
						}
					}
				}
				else {
					ArtifactListener listener = _findListener(jar, listeners);

					if (listener == null) {
						synchronized (_processingFailures) {
							_processingFailures.add(file);
						}

						continue;
					}

					artifact = new Artifact();

					artifact.setPath(file);
					artifact.setJaredDirectory(jar);
					artifact.setJaredUrl(jaredUrl);
					artifact.setListener(listener);
					artifact.setChecksum(scanner.getChecksum(file));

					if (_transformArtifact(artifact)) {
						created.add(artifact);
					}
					else {
						_deleteJaredDirectory(artifact);
					}
				}
			}
		}

		Collection<Bundle> uninstalledBundles = _uninstall(deleted);

		Collection<Bundle> updatedBundles = _update(modified);

		Collection<Bundle> installedBundles = _install(created);

		if (!uninstalledBundles.isEmpty() || !updatedBundles.isEmpty() ||
			!installedBundles.isEmpty()) {

			Set<Bundle> toRefresh = new HashSet<>();

			toRefresh.addAll(uninstalledBundles);

			toRefresh.addAll(updatedBundles);

			toRefresh.addAll(installedBundles);

			findBundlesWithFragmentsToRefresh(toRefresh);

			findBundlesWithOptionalPackagesToRefresh(toRefresh);

			if (!toRefresh.isEmpty()) {
				_refresh(toRefresh);

				_setStateChanged(true);
			}
		}

		if (_startBundles) {
			FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
				FrameworkStartLevel.class);

			int startLevel = frameworkStartLevel.getStartLevel();

			if (_isStateChanged() || (startLevel != _frameworkStartLevel)) {
				_frameworkStartLevel = startLevel;

				_startAllBundles();

				_delayedStart.addAll(installedBundles);
				_delayedStart.removeAll(uninstalledBundles);

				_startBundles(_delayedStart);

				_consistentlyFailingBundles.clear();
				_consistentlyFailingBundles.addAll(_delayedStart);

				_setStateChanged(false);
			}
		}
	}

	private ArtifactListener _findListener(
		File artifact, List<ArtifactListener> listeners) {

		for (ArtifactListener listener : listeners) {
			if (listener.canHandle(artifact)) {
				return listener;
			}
		}

		return null;
	}

	private Artifact _getArtifact(File file) {
		synchronized (_currentManagedArtifacts) {
			return _currentManagedArtifacts.get(file);
		}
	}

	private List<Artifact> _getArtifacts() {
		synchronized (_currentManagedArtifacts) {
			return new ArrayList<>(_currentManagedArtifacts.values());
		}
	}

	private boolean _getBoolean(
		Map<String, String> properties, String property, boolean defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			return Boolean.valueOf(value);
		}

		return defaultValue;
	}

	private File _getFile(
		Map<String, String> properties, String property, File defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			return new File(value);
		}

		return defaultValue;
	}

	private int _getInt(
		Map<String, String> properties, String property, int defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			try {
				return Integer.parseInt(value);
			}
			catch (Exception exception) {
				_log(
					Util.Logger.LOG_WARNING,
					StringBundler.concat(
						property, " set, but not a int: ", value),
					null);
			}
		}

		return defaultValue;
	}

	private long _getLong(
		Map<String, String> properties, String property, long defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			try {
				return Long.parseLong(value);
			}
			catch (Exception exception) {
				_log(
					Util.Logger.LOG_WARNING,
					StringBundler.concat(
						property, " set, but not a long: ", value),
					null);
			}
		}

		return defaultValue;
	}

	private void _initializeCurrentManagedBundles() {
		Bundle[] bundles = _bundleContext.getBundles();

		URI uri = _watchedDirectory.toURI();

		uri = uri.normalize();

		String watchedDirPath = uri.getPath();

		Map<File, Long> checksums = new HashMap<>();

		Pattern filePattern = null;

		if ((_filter != null) && !_filter.isEmpty()) {
			filePattern = Pattern.compile(_filter);
		}

		for (Bundle bundle : bundles) {
			String location = bundle.getLocation();

			uri = null;

			try {
				uri = new URI(location);
				uri = uri.normalize();
			}
			catch (URISyntaxException uriSyntaxException) {
				File file = new File(location);

				uri = file.toURI();

				uri = uri.normalize();
			}

			String locationPath = uri.getPath();

			if (locationPath == null) {
				continue;
			}

			String path = null;

			if ((location != null) && locationPath.contains(watchedDirPath)) {
				String schemeSpecificPart = uri.getSchemeSpecificPart();

				if (uri.isOpaque() && (schemeSpecificPart != null)) {
					int lastIndexOfFileProtocol =
						schemeSpecificPart.lastIndexOf("file:");

					int offsetFileProtocol = 0;

					if (lastIndexOfFileProtocol >= 0) {
						offsetFileProtocol =
							lastIndexOfFileProtocol + "file:".length();
					}

					int firstIndexOfDollar = schemeSpecificPart.indexOf("$");

					int endOfPath = schemeSpecificPart.length();

					if (firstIndexOfDollar >= 0) {
						endOfPath = firstIndexOfDollar;
					}

					path = schemeSpecificPart.substring(
						offsetFileProtocol, endOfPath);
				}
				else {
					path = uri.getPath();
				}
			}

			if (path == null) {
				continue;
			}

			int index = path.lastIndexOf('/');

			if ((index != -1) && path.startsWith(watchedDirPath)) {
				String fileName = path.substring(index + 1);

				if (filePattern != null) {
					Matcher matcher = filePattern.matcher(fileName);

					if (!matcher.matches()) {
						continue;
					}
				}

				Artifact artifact = new Artifact();

				artifact.setBundleId(bundle.getBundleId());
				artifact.setChecksum(Util.loadChecksum(bundle, _bundleContext));
				artifact.setListener(null);
				artifact.setPath(new File(path));

				_setArtifact(new File(path), artifact);

				checksums.put(new File(path), artifact.getChecksum());
			}
		}

		scanner.initialize(checksums);
	}

	private Bundle _install(Artifact artifact) {
		File path = artifact.getPath();

		Bundle bundle = null;

		AtomicBoolean modified = new AtomicBoolean();

		try {
			ArtifactListener artifactListener = artifact.getListener();

			long checksum = artifact.getChecksum();

			if (artifactListener instanceof ArtifactInstaller) {
				ArtifactInstaller artifactInstaller =
					(ArtifactInstaller)artifactListener;

				artifactInstaller.install(path);
			}
			else if (artifactListener instanceof ArtifactUrlTransformer) {
				Artifact badArtifact = _installationFailures.get(path);

				if ((badArtifact != null) &&
					(badArtifact.getChecksum() == checksum)) {

					return null;
				}

				URL transformed = artifact.getTransformedUrl();

				String location = transformed.toString();

				try (BufferedInputStream inputStream = new BufferedInputStream(
						transformed.openStream())) {

					bundle = _installOrUpdateBundle(
						location, inputStream, checksum, modified);

					artifact.setBundleId(bundle.getBundleId());
				}
			}
			else if (artifactListener instanceof ArtifactTransformer) {
				Artifact badArtifact = _installationFailures.get(path);

				if ((badArtifact != null) &&
					(badArtifact.getChecksum() == checksum)) {

					return null;
				}

				File transformed = artifact.getTransformed();

				URI uri = path.toURI();

				uri = uri.normalize();

				String location = uri.toString();

				File file = path;

				if (transformed != null) {
					file = transformed;
				}

				try (InputStream inputStream1 = new FileInputStream(file);
					BufferedInputStream inputStream2 = new BufferedInputStream(
						inputStream1)) {

					bundle = _installOrUpdateBundle(
						location, inputStream2, checksum, modified);

					artifact.setBundleId(bundle.getBundleId());
				}
			}

			_installationFailures.remove(path);
			_setArtifact(path, artifact);
		}
		catch (Exception exception) {
			_log(
				Util.Logger.LOG_ERROR, "Failed to install artifact: " + path,
				exception);

			_installationFailures.put(path, artifact);
		}

		if (modified.get()) {
			return bundle;
		}

		return null;
	}

	private Collection<Bundle> _install(Collection<Artifact> artifacts) {
		List<Bundle> bundles = new ArrayList<>();

		for (Artifact artifact : artifacts) {
			Bundle bundle = _install(artifact);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

	private Bundle _installOrUpdateBundle(
			String bundleLocation, BufferedInputStream bufferedInputStream,
			long checksum, AtomicBoolean modified)
		throws Exception {

		Bundle bundle = _bundleContext.getBundle(bundleLocation);

		if ((bundle != null) &&
			(Util.loadChecksum(bundle, _bundleContext) != checksum)) {

			bundle.update(bufferedInputStream);

			Util.storeChecksum(bundle, checksum, _bundleContext);

			return bundle;
		}

		bufferedInputStream.mark(256 * 1024);

		try (JarInputStream jarInputStream = new JarInputStream(
				bufferedInputStream)) {

			Manifest manifest = jarInputStream.getManifest();

			if (manifest == null) {
				throw new BundleException(
					StringBundler.concat(
						"The bundle ", bundleLocation, " does not have a ",
						"META-INF/MANIFEST.MF! Make sure, META-INF and ",
						"MANIFEST.MF are the first 2 entries in your JAR!"));
			}

			Attributes attributes = manifest.getMainAttributes();

			String symbolicName = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			String versionString = attributes.getValue(
				Constants.BUNDLE_VERSION);

			Version version = Version.emptyVersion;

			if (versionString != null) {
				version = Version.parseVersion(versionString);
			}

			for (Bundle currentBundle : _bundleContext.getBundles()) {
				String currentSymbolicName = currentBundle.getSymbolicName();

				if ((currentSymbolicName != null) &&
					Objects.equals(currentSymbolicName, symbolicName)) {

					Dictionary<String, String> headers =
						currentBundle.getHeaders(StringPool.BLANK);

					versionString = headers.get(Constants.BUNDLE_VERSION);

					Version currentVersion = Version.emptyVersion;

					if (versionString != null) {
						currentVersion = Version.parseVersion(versionString);
					}

					if (version.equals(currentVersion)) {
						bufferedInputStream.reset();

						if (Util.loadChecksum(currentBundle, _bundleContext) !=
								checksum) {

							_log(
								Util.Logger.LOG_WARNING,
								StringBundler.concat(
									"A bundle with the same symbolic name (",
									symbolicName, ") and version (",
									versionString, ") is already installed. ",
									"Updating this bundle instead."),
								null);

							_stopTransient(currentBundle);

							Util.storeChecksum(
								currentBundle, checksum, _bundleContext);

							currentBundle.update(bufferedInputStream);

							modified.set(true);
						}

						return currentBundle;
					}
				}
			}

			bufferedInputStream.reset();
			Util.log(
				_bundleContext, Util.Logger.LOG_INFO,
				StringBundler.concat(
					"Installing bundle ", symbolicName, " / ", version),
				null);

			bundle = _bundleContext.installBundle(
				bundleLocation, bufferedInputStream);

			if (bundle.getState() == Bundle.UNINSTALLED) {
				return bundle;
			}

			Util.storeChecksum(bundle, checksum, _bundleContext);

			modified.set(true);

			Dictionary<String, String> headers = bundle.getHeaders("");

			String header = headers.get("Web-ContextPath");

			BundleStartLevel bundleStartLevel = bundle.adapt(
				BundleStartLevel.class);

			if (header != null) {
				bundleStartLevel.setStartLevel(_webStartLevel);
			}
			else if (_startLevel != 0) {
				bundleStartLevel.setStartLevel(_startLevel);
			}

			return bundle;
		}
	}

	private boolean _isFragment(Bundle bundle) {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) != 0) {
			return true;
		}

		return false;
	}

	private boolean _isStateChanged() {
		return _stateChanged.get();
	}

	private void _log(int messageLevel, String message, Throwable throwable) {
		Util.log(_bundleContext, _logLevel, messageLevel, message, throwable);
	}

	private void _prepareDir(File dir) {
		if (!dir.exists() && !dir.mkdirs()) {
			_log(
				Util.Logger.LOG_ERROR,
				StringBundler.concat(
					"Cannot create folder ", dir,
					". Is the folder write-protected?"),
				null);

			throw new RuntimeException("Cannot create folder: " + dir);
		}

		if (!dir.isDirectory()) {
			_log(
				Util.Logger.LOG_ERROR,
				StringBundler.concat(
					"Cannot use ", dir, " because it is not a directory"),
				null);

			throw new RuntimeException(
				"Cannot start FileInstall using something that is not a " +
					"directory");
		}
	}

	private void _prepareTempDir() {
		if (_tmpDir == null) {
			File javaIoTmpdir = new File(System.getProperty("java.io.tmpdir"));

			if (!javaIoTmpdir.exists() && !javaIoTmpdir.mkdirs()) {
				throw new IllegalStateException(
					"Unable to create temporary directory " + javaIoTmpdir);
			}

			Random random = new Random();

			while (_tmpDir == null) {
				File file = new File(
					javaIoTmpdir,
					"fileinstall-" + String.valueOf(random.nextLong()));

				if (!file.exists() && file.mkdirs()) {
					_tmpDir = file;

					_tmpDir.deleteOnExit();
				}
			}
		}
		else {
			_prepareDir(_tmpDir);
		}
	}

	private void _process(Set<File> files) throws InterruptedException {
		Lock readLock = _fileInstall.getReadLock();

		readLock.lockInterruptibly();

		try {
			_doProcess(files);
		}
		finally {
			readLock.unlock();
		}
	}

	private void _refresh(Collection<Bundle> bundles)
		throws InterruptedException {

		FileInstall.refresh(_systemBundle, bundles);
	}

	private void _removeArtifact(File file) {
		synchronized (_currentManagedArtifacts) {
			_currentManagedArtifacts.remove(file);
		}
	}

	private void _setArtifact(File file, Artifact artifact) {
		synchronized (_currentManagedArtifacts) {
			_currentManagedArtifacts.put(file, artifact);
		}
	}

	private void _setStateChanged(boolean changed) {
		_stateChanged.set(changed);
	}

	private void _startAllBundles() {
		FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
			FrameworkStartLevel.class);

		Set<Bundle> bundles = new LinkedHashSet<>();

		for (Artifact artifact : _getArtifacts()) {
			long bundleId = artifact.getBundleId();

			if (bundleId > 0) {
				Bundle bundle = _bundleContext.getBundle(bundleId);

				if (bundle != null) {
					int state = bundle.getState();

					BundleStartLevel bundleStartLevel = bundle.adapt(
						BundleStartLevel.class);

					if ((state != Bundle.STARTING) &&
						(state != Bundle.ACTIVE) &&
						(_useStartTransient ||
						 bundleStartLevel.isPersistentlyStarted()) &&
						(frameworkStartLevel.getStartLevel() >=
							bundleStartLevel.getStartLevel())) {

						bundles.add(bundle);
					}
				}
			}
		}

		_startBundles(bundles);
	}

	private boolean _startBundle(Bundle bundle, boolean logFailures) {
		BundleStartLevel bundleStartLevel = bundle.adapt(
			BundleStartLevel.class);

		if (_startBundles && (bundle.getState() != Bundle.UNINSTALLED) &&
			!_isFragment(bundle) &&
			(_frameworkStartLevel >= bundleStartLevel.getStartLevel())) {

			try {
				int options = 0;

				if (_useStartTransient) {
					options = Bundle.START_TRANSIENT;
				}

				if (_useStartActivationPolicy) {
					options |= Bundle.START_ACTIVATION_POLICY;
				}

				bundle.start(options);

				_log(
					Util.Logger.LOG_INFO,
					"Started bundle: " + bundle.getLocation(), null);

				return true;
			}
			catch (IllegalStateException illegalStateException) {
				if (bundle.getState() == Bundle.UNINSTALLED) {
					return true;
				}

				throw illegalStateException;
			}
			catch (BundleException bundleException) {
				if (logFailures) {
					_log(
						Util.Logger.LOG_ERROR,
						"Error while starting bundle: " + bundle.getLocation(),
						bundleException);
				}
			}
		}

		return false;
	}

	private void _startBundles(Set<Bundle> bundles) {
		Iterator<Bundle> iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			if (_startBundle(bundle, true)) {
				iterator.remove();
			}
		}
	}

	private void _stopTransient(Bundle bundle) throws BundleException {
		if (_startBundles && !_isFragment(bundle)) {
			bundle.stop(Bundle.STOP_TRANSIENT);
		}
	}

	private boolean _transformArtifact(Artifact artifact) {
		ArtifactListener artifactListener = artifact.getListener();

		if (artifactListener instanceof ArtifactTransformer) {
			_prepareTempDir();

			try {
				ArtifactTransformer artifactTransformer =
					(ArtifactTransformer)artifactListener;

				File transformed = artifactTransformer.transform(
					artifact.getJaredDirectory(), _tmpDir);

				if (transformed != null) {
					artifact.setTransformed(transformed);

					return true;
				}
			}
			catch (Exception exception) {
				File file = artifact.getPath();

				_log(
					Util.Logger.LOG_WARNING,
					"Unable to transform artifact: " + file.getAbsolutePath(),
					exception);
			}

			return false;
		}
		else if (artifactListener instanceof ArtifactUrlTransformer) {
			try {
				URL url = artifact.getJaredUrl();

				ArtifactUrlTransformer artifactUrlTransformer =
					(ArtifactUrlTransformer)artifactListener;

				URL transformed = artifactUrlTransformer.transform(url);

				if (transformed != null) {
					artifact.setTransformedUrl(transformed);

					return true;
				}
			}
			catch (Exception exception) {
				File file = artifact.getPath();

				_log(
					Util.Logger.LOG_WARNING,
					"Unable to transform artifact: " + file.getAbsolutePath(),
					exception);
			}

			return false;
		}

		return true;
	}

	private Bundle _uninstall(Artifact artifact) {
		Bundle bundle = null;

		try {
			File path = artifact.getPath();

			if (artifact.getListener() == null) {
				artifact.setListener(
					_findListener(path, _fileInstall.getListeners()));
			}

			_removeArtifact(path);

			_deleteTransformedFile(artifact);

			ArtifactListener artifactListener = artifact.getListener();

			long bundleId = artifact.getBundleId();

			if (artifactListener instanceof ArtifactInstaller) {
				ArtifactInstaller artifactInstaller =
					(ArtifactInstaller)artifactListener;

				artifactInstaller.uninstall(path);
			}
			else if (bundleId != 0) {
				bundle = _bundleContext.getBundle(bundleId);

				if (bundle == null) {
					StringBundler sb = new StringBundler(5);

					sb.append("Failed to uninstall bundle: ");
					sb.append(path);
					sb.append(" with id: ");
					sb.append(bundleId);
					sb.append(". The bundle has already been uninstalled");

					_log(Util.Logger.LOG_WARNING, sb.toString(), null);

					return null;
				}

				_log(
					Util.Logger.LOG_INFO,
					StringBundler.concat(
						"Uninstalling bundle ", bundle.getBundleId(), " (",
						bundle.getSymbolicName(), ")"),
					null);

				bundle.uninstall();
			}
		}
		catch (Exception exception) {
			_log(
				Util.Logger.LOG_WARNING,
				"Failed to uninstall artifact: " + artifact.getPath(),
				exception);
		}

		return bundle;
	}

	private Collection<Bundle> _uninstall(Collection<Artifact> artifacts) {
		List<Bundle> bundles = new ArrayList<>();

		for (Artifact artifact : artifacts) {
			Bundle bundle = _uninstall(artifact);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

	private Bundle _update(Artifact artifact) {
		Bundle bundle = null;

		try {
			File path = artifact.getPath();

			ArtifactListener artifactListener = artifact.getListener();

			long checksum = artifact.getChecksum();

			long bundleId = artifact.getBundleId();

			if (artifactListener instanceof ArtifactInstaller) {
				ArtifactInstaller artifactInstaller =
					(ArtifactInstaller)artifactListener;

				artifactInstaller.update(path);
			}
			else if (artifactListener instanceof ArtifactUrlTransformer) {
				bundle = _bundleContext.getBundle(bundleId);

				if (bundle == null) {
					StringBundler sb = new StringBundler(5);

					sb.append("Failed to update bundle: ");
					sb.append(path);
					sb.append(" with ID ");
					sb.append(bundleId);
					sb.append(". The bundle has been uninstalled");

					_log(Util.Logger.LOG_WARNING, sb.toString(), null);

					return null;
				}

				Util.log(
					_bundleContext, Util.Logger.LOG_INFO,
					StringBundler.concat(
						"Updating bundle ", bundle.getSymbolicName(), " / ",
						bundle.getVersion()),
					null);

				_stopTransient(bundle);

				Util.storeChecksum(bundle, checksum, _bundleContext);

				URL transformed = artifact.getTransformedUrl();

				if (transformed != null) {
					try (InputStream inputStream = transformed.openStream()) {
						bundle.update(inputStream);
					}
				}
				else {
					try (InputStream inputStream = new FileInputStream(path)) {
						bundle.update(inputStream);
					}
				}
			}
			else if (artifactListener instanceof ArtifactTransformer) {
				File transformed = artifact.getTransformed();

				bundle = _bundleContext.getBundle(bundleId);

				if (bundle == null) {
					StringBundler sb = new StringBundler(5);

					sb.append("Failed to update bundle: ");
					sb.append(path);
					sb.append(" with ID ");
					sb.append(bundleId);
					sb.append(". The bundle has been uninstalled");

					_log(Util.Logger.LOG_WARNING, sb.toString(), null);

					return null;
				}

				Util.log(
					_bundleContext, Util.Logger.LOG_INFO,
					StringBundler.concat(
						"Updating bundle ", bundle.getSymbolicName(), " / ",
						bundle.getVersion()),
					null);

				_stopTransient(bundle);

				Util.storeChecksum(bundle, checksum, _bundleContext);

				File file = path;

				if (transformed != null) {
					file = transformed;
				}

				try (InputStream inputStream = new FileInputStream(file)) {
					bundle.update(inputStream);
				}
			}
		}
		catch (Throwable throwable) {
			_log(
				Util.Logger.LOG_WARNING,
				"Failed to update artifact " + artifact.getPath(), throwable);
		}

		return bundle;
	}

	private Collection<Bundle> _update(Collection<Artifact> artifacts) {
		List<Bundle> bundles = new ArrayList<>();

		for (Artifact artifact : artifacts) {
			Bundle bundle = _update(artifact);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

	private void _verifyWatchedDir() {
		if (!_watchedDirectory.exists()) {
			_log(
				Util.Logger.LOG_WARNING,
				_watchedDirectory + " does not exist, please create it.", null);
		}
		else if (!_watchedDirectory.isDirectory()) {
			_log(
				Util.Logger.LOG_ERROR,
				StringBundler.concat(
					"Cannot use ", _watchedDirectory,
					" because it is not a directory"),
				null);

			throw new RuntimeException(
				StringBundler.concat(
					"File Install cannot monitor ", _watchedDirectory,
					" because it is not a directory"));
		}
	}

	private final int _activeLevel;
	private final BundleContext _bundleContext;
	private final Set<Bundle> _consistentlyFailingBundles = new HashSet<>();
	private final Map<File, Artifact> _currentManagedArtifacts =
		new HashMap<>();
	private final Set<Bundle> _delayedStart = new HashSet<>();
	private final boolean _disableNio2;
	private final FileInstall _fileInstall;
	private final String _filter;
	private final String _fragmentScope;
	private int _frameworkStartLevel;
	private final Map<File, Artifact> _installationFailures = new HashMap<>();
	private final int _logLevel;
	private final boolean _noInitialDelay;
	private final String _optionalScope;
	private final long _poll;
	private final Set<File> _processingFailures = new HashSet<>();
	private final Map<String, String> _properties;
	private final boolean _startBundles;
	private final int _startLevel;
	private final AtomicBoolean _stateChanged = new AtomicBoolean();
	private final Bundle _systemBundle;
	private File _tmpDir;
	private final boolean _updateWithListeners;
	private final boolean _useStartActivationPolicy;
	private final boolean _useStartTransient;
	private final File _watchedDirectory;
	private final int _webStartLevel;

}