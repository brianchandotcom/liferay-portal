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

package com.liferay.mail.kernel.model;

import java.io.File;

/**
 * @author Barrie Selack
 * @author Brian Wing Shun Chan
 */
public class FileAttachment {

	public FileAttachment() {
	}

	public FileAttachment(File file, String fileName) {
		_file = file;
		_fileName = fileName;
	}

	public File getFile() {
		return _file;
	}

	public String getFileName() {
		return _fileName;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	private File _file;
	private String _fileName;

}