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
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

/**
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public class FileAggregateContext extends BaseAggregateContext {

	public FileAggregateContext(String docroot, String resourcePath) {
		int pos = resourcePath.lastIndexOf(StringPool.SLASH);

		if (pos > -1) {
			resourcePath = resourcePath.substring(0, pos + 1);
		}

		pushPath(docroot);
		pushPath(resourcePath);
	}

	@Override
	public String getContent(String path) {
		try {
			pushPath(path);

			String listPath = getFullPath(StringPool.BLANK);

			popPath();

			return FileUtil.read(listPath);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return null;
	}

	@Override
	public String getResourcePath(String path) {
		String docroot = shiftPath();

		String listPath = getFullPath(StringPool.BLANK);

		unshiftPath(docroot);

		return listPath.concat(path);
	}

	private static Log _log = LogFactoryUtil.getLog(FileAggregateContext.class);

}