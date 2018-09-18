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

import java.io.File;

import java.util.List;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 */
@Component(service = {})
public class BundleManagerUtil {

	public static Bundle getBundle(String symbolicName, String version) {
		return _bundleManagerImpl.getBundle(symbolicName, version);
	}

	public static List<Bundle> getBundles() {
		return _bundleManagerImpl.getBundles();
	}

	public static List<Bundle> getInstalledBundles() {
		return _bundleManagerImpl.getInstalledBundles();
	}

	public static Manifest getManifest(File file) {
		return _bundleManagerImpl.getManifest(file);
	}

	public static void installLPKG(File file) throws Exception {
		_bundleManagerImpl.installLPKG(file);
	}

	public static boolean isInstalled(Bundle bundle) {
		return _bundleManagerImpl.isInstalled(bundle);
	}

	public static boolean isInstalled(String symbolicName, String version) {
		return _bundleManagerImpl.isInstalled(symbolicName, version);
	}

	public static void uninstallBundle(Bundle bundle) {
		_bundleManagerImpl.uninstallBundle(bundle);
	}

	public static void uninstallBundle(String symbolicName, String version) {
		_bundleManagerImpl.uninstallBundle(symbolicName, version);
	}

	@Reference(unbind = "-")
	protected void setBundleManager(BundleManagerImpl bundleManagerImpl) {
		_bundleManagerImpl = bundleManagerImpl;
	}

	private static BundleManagerImpl _bundleManagerImpl;

}