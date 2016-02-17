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

package com.liferay.marketplace.bundle;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Joan Kim
 * @author Ryan Park
 */
@Component(immediate = true, service = BundleManager.class)
public class BundleManager {

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

	public List<Bundle> getBundles() {
		return ListUtil.fromArray(_bundleContext.getBundles());
	}

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
		InputStream inputStream = null;
		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file);

			ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");

			if (zipEntry == null) {
				return null;
			}

			inputStream = zipFile.getInputStream(zipEntry);

			return new Manifest(inputStream);
		}
		catch (Exception e) {
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioe) {
				}
			}
		}

		return null;
	}

	public boolean isInstalled(Bundle bundle) {
		if (ArrayUtil.contains(_INSTALLED_BUNDLE_STATES, bundle.getState())) {
			return true;
		}
		else {
			return false;
		}
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

	private static final int[] _INSTALLED_BUNDLE_STATES = {
		Bundle.ACTIVE, Bundle.INSTALLED, Bundle.RESOLVED
	};

	private static final Log _log = LogFactoryUtil.getLog(BundleManager.class);

	private BundleContext _bundleContext;

}