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

package com.liferay.portal.kernel.io;

import java.io.File;

/**
 * @author Vilmos Papp
 */
public class S3TempDirectoryFilter implements java.io.FileFilter {

	public S3TempDirectoryFilter(long lastModifiedDateToKeep) {
		_lastModifiedDateToKeep = lastModifiedDateToKeep;
	}

	public boolean accept(File file) {
		if (file.isDirectory() &&
			(file.lastModified() < _lastModifiedDateToKeep)) {

			return true;
		}

		return false;
	}

	private long _lastModifiedDateToKeep;

}