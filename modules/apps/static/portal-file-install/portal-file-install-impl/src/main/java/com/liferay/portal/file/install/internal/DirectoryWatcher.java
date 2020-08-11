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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.manifest.Clause;
import com.liferay.portal.file.install.internal.manifest.Parser;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.nio.file.Files;

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
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
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
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Matthew Tambara
 */
public class DirectoryWatcher extends Thread implements BundleListener {

	public static final String ACTIVE_LEVEL = "file.install.active.level";

	public static final String CONFIG_ENCODING = "file.install.configEncoding";

	public static final String DIR = "file.install.dir";

	public static final String FILENAME = "felix.fileinstall.filename";

	public static final String FILTER = "file.install.filter";

	public static final String FRAGMENT_SCOPE =
		"file.install.fragmentRefreshScope";

	public static final String NO_INITIAL_DELAY = "file.install.noInitialDelay";

	public static final String OPTIONAL_SCOPE =
		"file.install.optionalImportRefreshScope";

	public static final String POLL = "file.install.poll";

	public static final String SCOPE_ALL = "all";

	public static final String SCOPE_MANAGED = "managed";

	public static final String SCOPE_NONE = "none";

	public static final String START_LEVEL = "file.install.start.level";

	public static final String START_NEW_BUNDLES =
		"file.install.bundles.new.start";

	public static final String SUBDIR_MODE = "file.install.subdir.mode";

	public static final String USE_START_ACTIVATION_POLICY =
		"file.install.bundles.startActivationPolicy";

	public static final String USE_START_TRANSIENT =
		"file.install.bundles.startTransient";

	public static final String WEB_START_LEVEL = "file.install.web.start.level";

	public DirectoryWatcher(String dirName, BundleContext bundleContext) {
		super("fileinstall-" + dirName);

		setDaemon(true);

		_bundleContext = bundleContext;

		_activeLevel = GetterUtil.getInteger(
			bundleContext.getProperty(ACTIVE_LEVEL));
		_filter = bundleContext.getProperty(FILTER);
		_fragmentScope = bundleContext.getProperty(FRAGMENT_SCOPE);
		_noInitialDelay = GetterUtil.getBoolean(
			bundleContext.getProperty(NO_INITIAL_DELAY));
		_optionalScope = bundleContext.getProperty(OPTIONAL_SCOPE);
		_poll = GetterUtil.getLong(bundleContext.getProperty(POLL), 2000);
		_startBundles = GetterUtil.getBoolean(
			bundleContext.getProperty(START_NEW_BUNDLES), true);
		_startLevel = GetterUtil.getInteger(
			bundleContext.getProperty(START_LEVEL));
		_systemBundle = bundleContext.getBundle(
			Constants.SYSTEM_BUNDLE_LOCATION);
		_useStartActivationPolicy = GetterUtil.getBoolean(
			bundleContext.getProperty(USE_START_ACTIVATION_POLICY), true);
		_useStartTransient = GetterUtil.getBoolean(
			bundleContext.getProperty(USE_START_TRANSIENT));

		_watchedDirectory = new File(dirName);

		if (!_watchedDirectory.exists()) {
			if (_log.isInfoEnabled()) {
				_log.info("Creating watched directory " + _watchedDirectory);
			}

			try {
				Files.createDirectories(_watchedDirectory.toPath());
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to create watched directory " + _watchedDirectory,
					ioException);
			}
		}
		else if (!_watchedDirectory.isDirectory()) {
			throw new RuntimeException(
				StringBundler.concat(
					"File Install cannot monitor ", _watchedDirectory,
					" because it is not a directory"));
		}

		_webStartLevel = GetterUtil.getInteger(
			bundleContext.getProperty(WEB_START_LEVEL), _startLevel);

		_fileInstallers = ServiceTrackerListFactory.open(
			_bundleContext, FileInstaller.class, null,
			new ServiceTrackerCustomizer<FileInstaller, FileInstaller>() {

				@Override
				public FileInstaller addingService(
					ServiceReference<FileInstaller> serviceReference) {

					return _bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<FileInstaller> serviceReference,
					FileInstaller fileInstaller) {
				}

				@Override
				public void removedService(
					ServiceReference<FileInstaller> serviceReference,
					FileInstaller fileInstaller) {

					_bundleContext.ungetService(serviceReference);

					_bundleContext.ungetService(serviceReference);

					for (Artifact artifact : _getArtifacts()) {
						if (artifact.getFileInstaller() == fileInstaller) {
							artifact.setFileInstaller(null);
						}
					}

					synchronized (this) {
						notifyAll();
					}
				}

			});

		_scanner = new Scanner(
			_watchedDirectory, _filter, bundleContext.getProperty(SUBDIR_MODE));

		_bundleContext.addBundleListener(this);
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

		try {
			join(10000);
		}
		catch (InterruptedException interruptedException) {
		}

		_fileInstallers.close();
	}

	public Scanner getScanner() {
		return _scanner;
	}

	@Override
	public void run() {
		if (!_noInitialDelay) {
			try {
				Thread.sleep(_poll);
			}
			catch (InterruptedException interruptedException) {
				return;
			}

			_initializeCurrentManagedBundles();
		}

		while (!interrupted()) {
			try {
				FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
					FrameworkStartLevel.class);

				if ((frameworkStartLevel.getStartLevel() >= _activeLevel) &&
					(_systemBundle.getState() == Bundle.ACTIVE)) {

					Set<File> files = _scanner.scan(false);

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

				_log.error(throwable, throwable);
			}
		}
	}

	@Override
	public void start() {
		if (_noInitialDelay) {
			_initializeCurrentManagedBundles();

			Set<File> files = _scanner.scan(true);

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

	protected void findBundlesWithFragmentsToRefresh(
		Set<Bundle> refreshBundles) {

		Set<Bundle> fragments = new HashSet<>();

		Set<Bundle> bundles = getScopedBundles(_fragmentScope);

		for (Bundle bundle : refreshBundles) {
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
										new VersionRange(versionString);

									headers = hostBundle.getHeaders(
										StringPool.BLANK);

									if (versionRange.includes(
											Version.parseVersion(
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

		refreshBundles.addAll(fragments);
	}

	protected void findBundlesWithOptionalPackagesToRefresh(
		Set<Bundle> refreshBundles) {

		Set<Bundle> bundles = getScopedBundles(_optionalScope);

		bundles.removeAll(refreshBundles);

		if (bundles.isEmpty()) {
			return;
		}

		Map<Bundle, List<Clause>> importClausesMap = new HashMap<>();

		Iterator<Bundle> iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			Dictionary<String, String> header = bundle.getHeaders(
				StringPool.BLANK);

			String imports = header.get(Constants.IMPORT_PACKAGE);

			List<Clause> importClauses = getOptionalImports(imports);

			if (importClauses.isEmpty()) {
				iterator.remove();
			}
			else {
				importClausesMap.put(bundle, importClauses);
			}
		}

		if (bundles.isEmpty()) {
			return;
		}

		List<Clause> exportClauses = new ArrayList<>();

		for (Bundle bundle : refreshBundles) {
			if (bundle.getState() != Bundle.UNINSTALLED) {
				Dictionary<String, String> headers = bundle.getHeaders(
					StringPool.BLANK);

				String exports = headers.get(Constants.EXPORT_PACKAGE);

				if (exports != null) {
					Collections.addAll(
						exportClauses, Parser.parseHeader(exports));
				}
			}
		}

		iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			List<Clause> importClauses = importClausesMap.get(bundle);

			Iterator<Clause> importIterator = importClauses.iterator();

			while (importIterator.hasNext()) {
				Clause importClause = importIterator.next();

				boolean matching = false;

				for (Clause exportClause : exportClauses) {
					if (Objects.equals(
							importClause.getName(), exportClause.getName())) {

						String importVersionString = importClause.getAttribute(
							Constants.VERSION_ATTRIBUTE);

						if (importVersionString == null) {
							matching = true;

							break;
						}

						Version exportedVersion = Version.emptyVersion;

						String exportVersionString = exportClause.getAttribute(
							Constants.VERSION_ATTRIBUTE);

						if (exportVersionString != null) {
							exportedVersion = Version.parseVersion(
								exportVersionString);
						}

						VersionRange importedVersionRange = new VersionRange(
							importVersionString);

						if (importedVersionRange.includes(exportedVersion)) {
							matching = true;

							break;
						}
					}
				}

				if (!matching) {
					importIterator.remove();
				}
			}

			if (importClauses.isEmpty()) {
				iterator.remove();
			}
		}

		refreshBundles.addAll(bundles);
	}

	protected List<Clause> getOptionalImports(String importsString) {
		Clause[] importClauses = Parser.parseHeader(importsString);

		List<Clause> clauses = new LinkedList<>();

		for (Clause importClause : importClauses) {
			String resolution = importClause.getDirective(
				Constants.RESOLUTION_DIRECTIVE);

			if (Constants.RESOLUTION_OPTIONAL.equals(resolution)) {
				clauses.add(importClause);
			}
		}

		return clauses;
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

	private FileInstaller _findFileInstaller(
		File file, Iterable<FileInstaller> iterable) {

		for (FileInstaller fileInstaller : iterable) {
			if (fileInstaller.canTransformURL(file)) {
				return fileInstaller;
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

					int firstIndexOfDollar = schemeSpecificPart.indexOf(
						StringPool.DOLLAR);

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

			int index = path.lastIndexOf(CharPool.SLASH);

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
				artifact.setFile(new File(path));

				_setArtifact(new File(path), artifact);

				checksums.put(new File(path), artifact.getChecksum());
			}
		}

		_scanner.initialize(checksums);
	}

	private Bundle _install(Artifact artifact) {
		File file = artifact.getFile();

		Bundle bundle = null;

		AtomicBoolean modified = new AtomicBoolean();

		try {
			FileInstaller fileInstaller = _findFileInstaller(
				file, _fileInstallers);

			if (fileInstaller == null) {
				_processingFailures.add(file);

				return null;
			}

			artifact.setFileInstaller(fileInstaller);

			long checksum = artifact.getChecksum();

			Artifact badArtifact = _installationFailures.get(file);

			if ((badArtifact != null) &&
				(badArtifact.getChecksum() == checksum)) {

				return null;
			}

			URL url = fileInstaller.transformURL(file);

			if (url != null) {
				String location = url.toString();

				try (BufferedInputStream inputStream = new BufferedInputStream(
						url.openStream())) {

					bundle = _installOrUpdateBundle(
						location, inputStream, checksum, modified);

					artifact.setBundleId(bundle.getBundleId());
				}
			}

			_installationFailures.remove(file);
			_setArtifact(file, artifact);
		}
		catch (Exception exception) {
			_log.error("Unable to install artifact: " + file, exception);

			_installationFailures.put(file, artifact);
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
			String location, BufferedInputStream bufferedInputStream,
			long checksum, AtomicBoolean modified)
		throws Exception {

		Bundle bundle = _bundleContext.getBundle(location);

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
						"The bundle ", location, " does not have a ",
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

							if (_log.isWarnEnabled()) {
								_log.warn(
									StringBundler.concat(
										"A bundle with the same symbolic name ",
										"(", symbolicName, ") and version (",
										versionString,
										") is already installed. Updating ",
										"this bundle instead."));
							}

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

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Installing bundle ", symbolicName, " / ", version));
			}

			bundle = _bundleContext.installBundle(
				location, bufferedInputStream);

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

	private void _process(Set<File> files) throws InterruptedException {
		List<Artifact> createdArtifacts = new ArrayList<>();
		List<Artifact> deletedArtifacts = new ArrayList<>();
		List<Artifact> modifiedArtifacts = new ArrayList<>();

		synchronized (_processingFailures) {
			files.addAll(_processingFailures);
			_processingFailures.clear();
		}

		for (File file : files) {
			Artifact artifact = _getArtifact(file);

			if (!file.exists()) {
				if (artifact != null) {
					deletedArtifacts.add(artifact);
				}
			}
			else {
				if (artifact != null) {
					artifact.setChecksum(_scanner.getChecksum(file));

					modifiedArtifacts.add(artifact);
				}
				else {
					artifact = new Artifact();

					artifact.setChecksum(_scanner.getChecksum(file));
					artifact.setFile(file);

					createdArtifacts.add(artifact);
				}
			}
		}

		Collection<Bundle> uninstalledBundles = _uninstall(deletedArtifacts);

		Collection<Bundle> updatedBundles = _update(modifiedArtifacts);

		Collection<Bundle> installedBundles = _install(createdArtifacts);

		if (!uninstalledBundles.isEmpty() || !updatedBundles.isEmpty() ||
			!installedBundles.isEmpty()) {

			Set<Bundle> bundles = new HashSet<>();

			bundles.addAll(uninstalledBundles);

			bundles.addAll(updatedBundles);

			bundles.addAll(installedBundles);

			findBundlesWithFragmentsToRefresh(bundles);

			findBundlesWithOptionalPackagesToRefresh(bundles);

			if (!bundles.isEmpty()) {
				_refresh(bundles);

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

	private void _refresh(Collection<Bundle> bundles)
		throws InterruptedException {

		FrameworkWiring frameworkWiring = _systemBundle.adapt(
			FrameworkWiring.class);

		DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkWiring.refreshBundles(
			bundles,
			frameworkEvent -> defaultNoticeableFuture.set(frameworkEvent));

		try {
			FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

			if (frameworkEvent.getType() != FrameworkEvent.PACKAGES_REFRESHED) {
				throw frameworkEvent.getThrowable();
			}
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}
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

				if (_log.isInfoEnabled()) {
					_log.info("Started bundle: " + bundle.getLocation());
				}

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
					_log.error(
						"Unable to start bundle: " + bundle.getLocation(),
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

	private Bundle _uninstall(Artifact artifact) {
		try {
			File file = artifact.getFile();

			_removeArtifact(file);

			FileInstaller fileInstaller = artifact.getFileInstaller();

			if (fileInstaller != null) {
				fileInstaller.uninstall(file);
			}

			long bundleId = artifact.getBundleId();

			if (bundleId > 0) {
				Bundle bundle = _bundleContext.getBundle(bundleId);

				if (bundle == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(5);

						sb.append("Unable to uninstall bundle: ");
						sb.append(file);
						sb.append(" with id: ");
						sb.append(bundleId);
						sb.append(". The bundle has already been uninstalled");

						_log.warn(sb.toString());
					}

					return null;
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Uninstalling bundle ", bundle.getBundleId(), " (",
							bundle.getSymbolicName(),
							StringPool.CLOSE_PARENTHESIS));
				}

				bundle.uninstall();

				return bundle;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to uninstall artifact: " + artifact.getFile(),
					exception);
			}
		}

		return null;
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
			File file = artifact.getFile();

			FileInstaller fileInstaller = _findFileInstaller(
				file, _fileInstallers);

			if (fileInstaller == null) {
				_processingFailures.add(file);

				return null;
			}

			artifact.setFileInstaller(fileInstaller);

			URL url = fileInstaller.transformURL(file);

			if (url == null) {
				return null;
			}

			long bundleId = artifact.getBundleId();

			bundle = _bundleContext.getBundle(bundleId);

			if (bundle == null) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("Unable to update bundle: ");
					sb.append(file);
					sb.append(" with ID ");
					sb.append(bundleId);
					sb.append(". The bundle has been uninstalled");

					_log.warn(sb.toString());
				}

				return null;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Updating bundle ", bundle.getSymbolicName(), " / ",
						bundle.getVersion()));
			}

			_stopTransient(bundle);

			Util.storeChecksum(bundle, artifact.getChecksum(), _bundleContext);

			try (InputStream inputStream = url.openStream()) {
				bundle.update(inputStream);
			}
		}
		catch (Throwable throwable) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update artifact " + artifact.getFile(),
					throwable);
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		DirectoryWatcher.class);

	private final int _activeLevel;
	private final BundleContext _bundleContext;
	private final Set<Bundle> _consistentlyFailingBundles = new HashSet<>();
	private final Map<File, Artifact> _currentManagedArtifacts =
		new HashMap<>();
	private final Set<Bundle> _delayedStart = new HashSet<>();
	private final ServiceTrackerList<FileInstaller, FileInstaller>
		_fileInstallers;
	private final String _filter;
	private final String _fragmentScope;
	private int _frameworkStartLevel;
	private final Map<File, Artifact> _installationFailures = new HashMap<>();
	private final boolean _noInitialDelay;
	private final String _optionalScope;
	private final long _poll;
	private final Set<File> _processingFailures = new HashSet<>();
	private final Scanner _scanner;
	private final boolean _startBundles;
	private final int _startLevel;
	private final AtomicBoolean _stateChanged = new AtomicBoolean();
	private final Bundle _systemBundle;
	private final boolean _useStartActivationPolicy;
	private final boolean _useStartTransient;
	private final File _watchedDirectory;
	private final int _webStartLevel;

}