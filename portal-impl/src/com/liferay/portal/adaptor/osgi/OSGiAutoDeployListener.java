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

package com.liferay.portal.adaptor.osgi;

import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;

/**
 * @author Raymond Augé
 */
public class OSGiAutoDeployListener implements AutoDeployListener {

	public void deploy(File file, String context) throws AutoDeployException {
		Adaptor adaptor = null;

		try {
			adaptor = AdaptorUtil.getAdaptor();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if ((adaptor == null) || !(adaptor instanceof OSGiAdaptor)) {
			return;
		}

		OSGiAdaptor osgiAdaptor = (OSGiAdaptor)adaptor;

		String fileName = file.getName().toLowerCase();

		if (file.isDirectory() ||
			(!fileName.endsWith(".jar") && !fileName.endsWith(".war"))) {

			return;
		}

		// Check to see if the file is an OSGi bundle

		ZipReader reader = ZipReaderFactoryUtil.getZipReader(file);

		InputStream inputStream = reader.getEntryAsInputStream(
			"/META-INF/MANIFEST.MF");

		if (inputStream == null) {
			return;
		}

		try {
			Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymoblicName = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			if (Validator.isNotNull(bundleSymoblicName)) {
				installBundle(osgiAdaptor, file);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			cleanUp(reader, inputStream);
		}
	}

	protected void cleanUp(ZipReader reader, InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			}
			catch (IOException e) {
				_log.error(e, e);
			}

			inputStream = null;
		}

		if (reader != null) {
			reader.close();
		}
	}

	protected void installBundle(OSGiAdaptor osgiAdaptor, File file)
		throws Exception {

		Framework framework = osgiAdaptor.getFramework();

		BundleContext bundleContext = framework.getBundleContext();

		try {
			Bundle bundle = bundleContext.installBundle(file.toURI().toString());

			if (bundle.getState() == Bundle.INSTALLED) {
				bundle.start();
			}
		}
		catch (BundleException be) {
			_log.warn(be.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OSGiAutoDeployListener.class);

}