/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Zongliang Li
 * @author Harry Mark
 */
public interface UploadServletRequest extends HttpServletRequest {

	public void cleanUp();

	public String getContentType(String name);

	public File getFile(String name);

	public File getFile(String name, boolean forceCreate);

	public InputStream getFileAsStream(String name) throws IOException;

	public InputStream getFileAsStream(String name, boolean deleteOnClose)
		throws IOException;

	public String getFileName(String name);

	public String[] getFileNames(String name);

	public File[] getFiles(String name);

	public InputStream[] getFilesAsStream(String name) throws IOException;

	public InputStream[] getFilesAsStream(String name, boolean deleteOnClose)
		throws IOException;

	public String getFullFileName(String name);

	public Long getSize(String name);

	public Boolean isFormField(String name);

}