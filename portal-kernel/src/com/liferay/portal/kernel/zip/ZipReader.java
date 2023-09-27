/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.zip;

import java.io.InputStream;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@ProviderType
public interface ZipReader {

	public void close();

	public List<String> getEntries();

	public byte[] getEntryAsByteArray(String name);

	public InputStream getEntryAsInputStream(String name);

	public String getEntryAsString(String name);

	public List<String> getFolderEntries(String path);

}