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

package com.liferay.portal.file.install.internal;

import com.liferay.portal.file.install.FileInstaller;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Matthew Tambara
 */
public class BundleInstaller implements FileInstaller {

	@Override
	public boolean canTransformURL(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".lpkg") || name.endsWith(".txt") ||
			name.endsWith(".xml") || name.endsWith(".properties") ||
			name.endsWith(".cfg") || !artifact.canRead()) {

			return false;
		}

		try (JarFile jarFile = new JarFile(artifact)) {
			Manifest manifest = jarFile.getManifest();

			if (manifest != null) {
				Attributes attributes = manifest.getMainAttributes();

				if (attributes.getValue("Bundle-SymbolicName") != null) {
					return true;
				}
			}
		}
		catch (IOException ioException) {
		}

		return false;
	}

	@Override
	public URL transform(URL url) {
		return url;
	}

}