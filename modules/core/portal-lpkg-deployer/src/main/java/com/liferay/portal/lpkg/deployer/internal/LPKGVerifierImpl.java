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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lpkg.deployer.LPKGVerifier;
import com.liferay.portal.lpkg.deployer.LPKGVerifyException;
import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.IndexerFactory;
import com.liferay.portal.target.platform.indexer.ValidatorFactory;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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
import org.osgi.service.component.annotations.Reference;

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
	public List<Bundle> verify(File lpkgFile) {
		Path tmpDir = null;

		try {
			tmpDir = Files.createTempDirectory(null);

			Indexer indexer = _indexerFactory.create(lpkgFile);

			File tempIndexFile = indexer.index(tmpDir.toFile());

			List<URI> uris = new ArrayList<>();

			uris.add(tempIndexFile.toURI());

			com.liferay.portal.target.platform.indexer.Validator validator =
				_validatorFactory.create();

			List<String> errors = validator.validate(uris);

			if (!errors.isEmpty()) {
				StringBundler sb = new StringBundler((errors.size() * 4) + 2);

				sb.append("LPKG validation failed with {");

				for (String error : errors) {
					sb.append("[");
					sb.append(error);
					sb.append("]");
					sb.append(",");
				}

				sb.setIndex(sb.index() - 1);

				sb.append("}");

				throw new LPKGVerifyException(sb.toString());
			}

			File targetPlatformDir = new File(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR,
				Indexer.DIR_NAME_TARGET_PLATFORM);

			File indexFile = new File(
				targetPlatformDir, tempIndexFile.getName());

			Files.copy(tempIndexFile.toPath(), indexFile.toPath());
		}
		catch (Exception e) {
			if (e instanceof LPKGVerifyException) {
				throw (LPKGVerifyException)e;
			}

			throw new LPKGVerifyException(e);
		}
		finally {
			if (tmpDir != null) {
				try {
					Files.walkFileTree(
						tmpDir,
						new SimpleFileVisitor<Path>() {

							@Override
							public FileVisitResult postVisitDirectory(
									Path dir, IOException exc)
								throws IOException {

								Files.delete(dir);

								return FileVisitResult.CONTINUE;
							}

							@Override
							public FileVisitResult visitFile(
									Path file, BasicFileAttributes attrs)
								throws IOException {

								Files.delete(file);

								return FileVisitResult.CONTINUE;
							}

						});
				}
				catch (IOException ioe) {
					throw new LPKGVerifyException(ioe);
				}
			}
		}

		try (ZipFile zipFile = new ZipFile(lpkgFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				throw new LPKGVerifyException(
					lpkgFile + " does not have liferay-marketplace.properties");
			}

			Properties properties = new Properties();

			properties.load(zipFile.getInputStream(zipEntry));

			String symbolicName = properties.getProperty("title");

			if (Validator.isNull(symbolicName)) {
				throw new LPKGVerifyException(
					lpkgFile + " does not have a valid symbolic name");
			}

			Version version = null;

			String versionString = properties.getProperty("version");

			try {
				version = new Version(versionString);
			}
			catch (IllegalArgumentException iae) {
				throw new LPKGVerifyException(
					lpkgFile + " does not have a valid version: " +
						versionString,
					iae);
			}

			List<Bundle> oldBundles = new ArrayList<>();

			for (Bundle bundle : _bundleContext.getBundles()) {
				if (!symbolicName.equals(bundle.getSymbolicName())) {
					continue;
				}

				int value = version.compareTo(bundle.getVersion());

				if (value > 0) {
					oldBundles.add(bundle);
				}
				else if (value == 0) {
					String path = lpkgFile.getCanonicalPath();

					if (path.equals(bundle.getLocation())) {
						continue;
					}

					throw new LPKGVerifyException(
						"Existing LPKG bundle " + bundle + " has the same " +
							"symbolic name and version as LPKG file " +
								lpkgFile);
				}
				else {
					throw new LPKGVerifyException(
						"Existing LPKG bundle " + bundle +
							" is a newer version of LPKG file " + lpkgFile);
				}
			}

			return oldBundles;
		}
		catch (IOException ioe) {
			throw new LPKGVerifyException(ioe);
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private IndexerFactory _indexerFactory;

	@Reference
	private ValidatorFactory _validatorFactory;

}