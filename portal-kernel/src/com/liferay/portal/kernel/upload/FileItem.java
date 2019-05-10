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

package com.liferay.portal.kernel.upload;

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

	public void delete();

	public String getContentType();

	public String getEncodedString();

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

	public boolean isFormField();

	public boolean isInMemory();

	public void setString(String encode);

	public void write(File file) throws Exception;

}