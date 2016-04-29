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

package com.liferay.lpkg.deployer.internal;

import com.liferay.lpkg.deployer.LPKGVerifier;
import com.liferay.lpkg.deployer.LPKGVerifyException;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class LPKGVerifierImpl implements LPKGVerifier {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public List<Bundle> verify(File lpkeFile) {
		try (ZipFile zipFile = new ZipFile(lpkeFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				throw new LPKGVerifyException(
					lpkeFile + " does not have liferay-marketplace.properties");
			}

			Properties properties = new Properties();

			properties.load(zipFile.getInputStream(zipEntry));

			String symbolicName = properties.getProperty("title");

			if (Validator.isNull(symbolicName)) {
				throw new LPKGVerifyException(
					lpkeFile + " does not have a valid symbolicName");
			}

			String versionString = properties.getProperty("version");

			Version version = null;

			try {
				version = new Version(versionString);
			}
			catch (IllegalArgumentException iae) {
				throw new LPKGVerifyException(
					lpkeFile + " does not have a valid version :" +
						versionString,
					iae);
			}

			List<Bundle> oldBundles = new ArrayList<>();

			for (Bundle bundle : _bundleContext.getBundles()) {
				if (!symbolicName.equals(bundle.getSymbolicName())) {
					continue;
				}

				int result = version.compareTo(bundle.getVersion());

				if (result > 0) {
					oldBundles.add(bundle);
				}
				else if (result == 0) {
					String path = lpkeFile.getCanonicalPath();

					if (path.equals(bundle.getLocation())) {
						continue;
					}

					throw new LPKGVerifyException(
						"LPKG bundle " + bundle + " has the same symbolic " +
							"name and version as LPKG file :" + lpkeFile);
				}
				else {
					throw new LPKGVerifyException(
						"A newer version LPKG bundle " + bundle +
							" has been installed comparing to " + lpkeFile);
				}
			}

			return oldBundles;
		}
		catch (IOException ioe) {
			throw new LPKGVerifyException(ioe);
		}
	}

	private BundleContext _bundleContext;

}