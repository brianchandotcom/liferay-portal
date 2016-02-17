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

import java.io.File;

import java.util.List;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 */
@Component
public class BundleManagerUtil {

	public static Bundle getBundle(String symbolicName, String version) {
		return _bundleManager.getBundle(symbolicName, version);
	}

	public static List<Bundle> getBundles() {
		return _bundleManager.getBundles();
	}

	public static List<Bundle> getInstalledBundles() {
		return _bundleManager.getInstalledBundles();
	}

	public static Manifest getManifest(File file) {
		return _bundleManager.getManifest(file);
	}

	public static boolean isInstalled(Bundle bundle) {
		return _bundleManager.isInstalled(bundle);
	}

	public static boolean isInstalled(String symbolicName, String version) {
		return _bundleManager.isInstalled(symbolicName, version);
	}

	public static void uninstallBundle(Bundle bundle) {
		_bundleManager.uninstallBundle(bundle);
	}

	public static void uninstallBundle(String symbolicName, String version) {
		_bundleManager.uninstallBundle(symbolicName, version);
	}

	@Reference(unbind = "-")
	protected void setBundleManager(BundleManager bundleManager) {
		_bundleManager = bundleManager;
	}

	private static BundleManager _bundleManager;

}