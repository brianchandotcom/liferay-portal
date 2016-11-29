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

package com.liferay.petra.content;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Peter Fellwock
 * @see    com.liferay.util.ContentUtil
 */
public class ContentUtil {

	public static String get(ClassLoader classLoader, String location) {
		return _instance._get(classLoader, location, false);
	}

	public static String get(
		ClassLoader classLoader, String location, boolean all) {

		return _instance._get(classLoader, location, all);
	}

	private ContentUtil() {
		_contentPool = new HashMap<>();
	}

	private String _get(ClassLoader classLoader, String location, boolean all) {
		String content = _contentPool.get(location);

		if (content == null) {
			try {
				content = StringUtil.read(classLoader, location, all);

				_put(location, content);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return content;
	}

	private void _put(String location, String content) {
		_contentPool.put(location, content);
	}

	private static final Log _log = LogFactoryUtil.getLog(ContentUtil.class);

	private static final ContentUtil _instance = new ContentUtil();

	private final Map<String, String> _contentPool;

}