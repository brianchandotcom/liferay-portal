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

package com.liferay.marketplace.internal.bundle;

import com.liferay.marketplace.bundle.BundleManager;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.lpkg.deployer.LPKGVerifier;
import com.liferay.portal.util.ShutdownUtil;

import java.io.File;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joan Kim
 * @author Ryan Park
 */
@Component(
	immediate = true, service = {BundleManager.class, BundleManagerImpl.class}
)
public class BundleManagerImpl implements BundleManager {

	@Override
	public Bundle getBundle(String symbolicName, String versionString) {
		Version version = Version.parseVersion(versionString);

		for (Bundle bundle : getBundles()) {
			if (symbolicName.equals(bundle.getSymbolicName()) &&
				version.equals(bundle.getVersion())) {

				return bundle;
			}
		}

		return null;
	}

	@Override
	public List<Bundle> getBundles() {
		return ListUtil.fromArray(_bundleContext.getBundles());
	}

	@Override
	public List<Bundle> getInstalledBundles() {
		List<Bundle> bundles = getBundles();

		Iterator<Bundle> itr = bundles.iterator();

		while (itr.hasNext()) {
			Bundle bundle = itr.next();

			if (!isInstalled(bundle)) {
				itr.remove();
			}
		}

		return bundles;
	}

	public Manifest getManifest(File file) {
		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");

			if (zipEntry == null) {
				return null;
			}

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				return new Manifest(inputStream);
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	public void installLPKG(File file) throws Exception {
		File installFile = new File(getInstallDirName(), file.getName());

		Files.move(
			file.toPath(), installFile.toPath(),
			StandardCopyOption.REPLACE_EXISTING);

		if (isRestartRequired(installFile)) {
			ShutdownUtil.shutdown(0);
		}
	}

	public boolean isInstalled(Bundle bundle) {
		if (ArrayUtil.contains(_INSTALLED_BUNDLE_STATES, bundle.getState())) {
			return true;
		}

		return false;
	}

	public boolean isInstalled(String symbolicName, String version) {
		Bundle bundle = getBundle(symbolicName, version);

		if (bundle == null) {
			return false;
		}

		return isInstalled(bundle);
	}

	public void uninstallBundle(Bundle bundle) {
		try {
			bundle.uninstall();
		}
		catch (BundleException be) {
			_log.error(be, be);
		}
	}

	public void uninstallBundle(String symbolicName, String version) {
		Bundle bundle = getBundle(symbolicName, version);

		if (bundle == null) {
			return;
		}

		uninstallBundle(bundle);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected String getInstallDirName() throws Exception {
		String[] autoDeployDirNames = PropsUtil.getArray(
			PropsKeys.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS);

		if (ArrayUtil.isEmpty(autoDeployDirNames)) {
			throw new AutoDeployException(
				"The portal property \"" +
					PropsKeys.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS +
						"\" is not set");
		}

		String autoDeployDirName = autoDeployDirNames[0];

		for (String curDirName : autoDeployDirNames) {
			if (curDirName.endsWith("/marketplace")) {
				autoDeployDirName = curDirName;

				break;
			}
		}

		return autoDeployDirName;
	}

	protected boolean isRestartRequired(File file) {
		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				return false;
			}

			Properties properties = new Properties();

			properties.load(zipFile.getInputStream(zipEntry));

			return GetterUtil.getBoolean(
				properties.getProperty("restart-required"), true);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to read liferay-marketplace.properties from " +
						file.getName());
			}
		}

		return false;
	}

	private static final int[] _INSTALLED_BUNDLE_STATES = {
		Bundle.ACTIVE, Bundle.INSTALLED, Bundle.RESOLVED
	};

	private static final Log _log = LogFactoryUtil.getLog(
		BundleManagerImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private LPKGVerifier _lpkgVerifier;

}