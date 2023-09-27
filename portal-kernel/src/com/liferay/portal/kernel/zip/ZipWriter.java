/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@ProviderType
public interface ZipWriter {

	public void addEntry(String name, byte[] bytes) throws IOException;

	public void addEntry(String name, InputStream inputStream)
		throws IOException;

	public void addEntry(String name, String s) throws IOException;

	public void addEntry(String name, StringBuilder sb) throws IOException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFile()}
	 */
	@Deprecated
	public byte[] finish() throws IOException;

	public File getFile();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFile()}
	 */
	@Deprecated
	public String getPath();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFile()}
	 */
	@Deprecated
	public void umount();

}