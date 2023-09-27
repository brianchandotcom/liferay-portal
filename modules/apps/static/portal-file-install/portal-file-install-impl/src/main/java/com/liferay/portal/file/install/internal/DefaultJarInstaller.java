/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal;

import com.liferay.portal.file.install.FileInstaller;

import java.io.File;

import java.net.URI;
import java.net.URL;

/**
 * @author Matthew Tambara
 */
public class DefaultJarInstaller implements FileInstaller {

	@Override
	public boolean canTransformURL(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".jar")) {
			return true;
		}

		return false;
	}

	@Override
	public URL transformURL(File file) throws Exception {
		URI uri = file.toURI();

		return uri.toURL();
	}

	@Override
	public void uninstall(File file) {
	}

}