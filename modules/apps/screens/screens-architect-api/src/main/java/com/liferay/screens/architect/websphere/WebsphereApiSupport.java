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

package com.liferay.screens.architect.websphere;

import java.io.InputStream;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * @author Carlos Sierra Andrés
 */
public class WebsphereApiSupport implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		if (_isWebSphere()) {
			Bundle bundle = context.getBundle();

			URL resource = bundle.getResource(
				"/dependencies/javax.annotation-api.jar");

			try (InputStream input = resource.openStream()) {
				_internalBundle = context.installBundle(
					"javax.annotation API", input);
			}
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			_internalBundle.uninstall();
		}
		catch (BundleException be) {
			be.printStackTrace();
		}
	}

	private boolean _isWebSphere() {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		try {
			systemClassLoader.loadClass(
				"/com/ibm/websphere/product/VersionInfo.class");
		}
		catch (ClassNotFoundException cnfe) {
			return false;
		}

		return true;
	}

	private Bundle _internalBundle;

}