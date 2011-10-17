/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.osgi.service.OSGiServiceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URI;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;

/**
 * @author Raymond Augé
 */
public class OSGiAutoDeployListener implements AutoDeployListener {

	public void deploy(File file, String context) throws AutoDeployException {
		try {
			doDeploy(file, context);
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	protected void cleanUp(ZipReader zipReader, InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			}
			catch (Exception e) {
			}

			inputStream = null;
		}

		if (zipReader != null) {
			try {
				zipReader.close();
			}
			catch (Exception e) {
			}
		}
	}

	protected void doDeploy(File file, String context) throws Exception {
		Framework framework = OSGiServiceUtil.getFramework();

		if (framework == null) {
			return;
		}

		String fileName = file.getName();

		fileName = fileName.toLowerCase();

		if (file.isDirectory() ||
			(!fileName.endsWith(".jar") && !fileName.endsWith(".war"))) {

			return;
		}

		ZipReader zipReader = null;

		InputStream inputStream = null;

		try {
			zipReader = ZipReaderFactoryUtil.getZipReader(file);

			inputStream = zipReader.getEntryAsInputStream(
				"/META-INF/MANIFEST.MF");

			if (inputStream == null) {
				return;
			}

			Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymbolicName = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			if (Validator.isNotNull(bundleSymbolicName)) {
				installBundle(framework, file, manifest);
			}
		}
		finally {
			cleanUp(zipReader, inputStream);
		}
	}

	protected void installBundle(
			Framework framework, File file, Manifest manifest)
		throws Exception {

		BundleContext bundleContext = framework.getBundleContext();

		URI uri = file.toURI();

		Bundle bundle = bundleContext.getBundle(uri.toString());

		InputStream inputStream = new FileInputStream(file);

		if (bundle != null) {
			bundle.update(inputStream);
		}
		else {
			bundle = bundleContext.installBundle(uri.toString(), inputStream);
		}

		bundle.start();
	}

}