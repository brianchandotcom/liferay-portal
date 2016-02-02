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

package com.liferay.portal.deploy.auto;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Constants;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

/**
 * @author Miguel Pastor
 * @author Manuel de la Peña
 */
public class ModuleAutoDeployListener extends BaseAutoDeployListener {

	@Override
	protected AutoDeployer buildAutoDeployer() {
		return new ThreadSafeAutoDeployer(new ModuleAutoDeployer());
	}

	@Override
	protected String getPluginPathInfoMessage(File file) {
		return "Copied module for " + file.getPath();
	}

	@Override
	protected String getSuccessMessage(File file) {
		return "Module for " + file.getPath() + " copied successfully";
	}

	@Override
	protected boolean isDeployable(File file) throws AutoDeployException {
		return isModule(file);
	}

	protected boolean isModule(File file) throws AutoDeployException {
		PluginAutoDeployListenerHelper pluginAutoDeployListenerHelper =
			new PluginAutoDeployListenerHelper(file);

		if (!pluginAutoDeployListenerHelper.isJarFile()) {
			return false;
		}

		JarInputStream jarInputStream = null;

		Manifest manifest = null;

		try {
			jarInputStream = new JarInputStream(new FileInputStream(file));

			manifest = jarInputStream.getManifest();
		}
		catch (IOException ioe) {
			throw new AutoDeployException(ioe);
		}
		finally {
			StreamUtil.cleanUp(jarInputStream);
		}

		if (manifest == null) {
			return false;
		}

		Attributes attributes = manifest.getMainAttributes();

		String bundleSymbolicNameAttributeValue = attributes.getValue(
			Constants.BUNDLE_SYMBOLICNAME);

		Parameters bundleSymbolicNameMap = OSGiHeader.parseHeader(
			bundleSymbolicNameAttributeValue);

		Set<String> bundleSymbolicNameSet = bundleSymbolicNameMap.keySet();

		if (bundleSymbolicNameSet.isEmpty()) {
			return false;
		}

		Iterator<String> bundleSymbolicNameIterator =
			bundleSymbolicNameSet.iterator();

		String bundleSymbolicName = bundleSymbolicNameIterator.next();

		return Validator.isNotNull(bundleSymbolicName);
	}

}