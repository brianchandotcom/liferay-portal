/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Raymond Augé
 * @author Neil Griffin
 */
@ProviderType
public interface FileItem {

	public static final long THRESHOLD_SIZE = GetterUtil.getLong(
		PropsUtil.get(FileItem.class.getName() + ".threshold.size"));

	public void delete();

	public String getContentType();

	public String getFieldName();

	public String getFileName();

	public String getFileNameExtension();

	public String getFullFileName();

	public String getHeader(String name);

	public Collection<String> getHeaderNames();

	public Collection<String> getHeaders(String name);

	public InputStream getInputStream() throws IOException;

	public long getSize();

	public int getSizeThreshold();

	public File getStoreLocation();

	public String getString();

	public File getTempFile();

	public boolean isFormField();

	public boolean isInMemory();

	public void write(File file) throws Exception;

}