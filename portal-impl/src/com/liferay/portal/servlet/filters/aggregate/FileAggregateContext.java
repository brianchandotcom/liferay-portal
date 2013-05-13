/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.aggregate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

/**
 * @author Raymond Augé
 */
public class FileAggregateContext implements AggregateContext {

	public FileAggregateContext(File file) {
		_file = file.getParentFile();
	}

	@Override
	public String getContent(String path) {
		try {
			File file = new File(_file, path);

			return FileUtil.read(file);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return null;
	}

	@Override
	public String getFullPath(String path) {
		String absolutePath = _file.getAbsolutePath();

		return absolutePath.concat(path);
	}

	@Override
	public void popPath(String path) {
		if (Validator.isNotNull(path)) {
			_file = _file.getParentFile();
		}
	}

	@Override
	public void pushPath(String path) {
		if (Validator.isNotNull(path)) {
			_file = new File(_file, path);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FileAggregateContext.class);

	private File _file;

}