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