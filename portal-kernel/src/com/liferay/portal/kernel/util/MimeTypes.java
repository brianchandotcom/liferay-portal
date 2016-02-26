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

package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.InputStream;

import java.util.Set;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public interface MimeTypes {

	public String getContentType(File file);

	public String getContentType(File file, String fileName);

	public String getContentType(InputStream inputStream, String fileName);

	public String getContentType(String fileName);

	public String getExtensionContentType(String extension);

	public Set<String> getExtensions(String contentType);

	public boolean isWebImage(String mimeType);

}