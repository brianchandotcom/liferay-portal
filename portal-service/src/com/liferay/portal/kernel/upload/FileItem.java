/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Raymond Augé
 */
public interface FileItem {

	public String getEncodedString();

	public String getFileName();

	public String getFileNameExtension();

	public String getFullFileName();

	public int getSizeThreshold();

	public String getString();

	public void setString(String encode);

	public boolean isFormField();

	public String getFieldName();

	public void delete();

	public String getContentType();

	public File getStoreLocation();

	public boolean isInMemory();

	public InputStream getInputStream() throws IOException;

	public long getSize();

}