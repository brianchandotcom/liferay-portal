/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install;

import java.io.File;

import java.net.URL;

/**
 * @author Matthew Tambara
 */
public interface FileInstaller {

	public boolean canTransformURL(File file);

	public URL transformURL(File file) throws Exception;

	public void uninstall(File file) throws Exception;

}