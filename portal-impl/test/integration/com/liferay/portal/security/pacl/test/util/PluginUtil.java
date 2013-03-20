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

package com.liferay.portal.security.pacl.test.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginUtil {

	public static final String TEST_FIELD_1 = "TEST_FIELD_1";

	private static final String TEST_FIELD_2 = "TEST_FIELD_2";

	private static Log _log = LogFactoryUtil.getLog(PluginUtil.class);

	static {

		// Prevent compiler from removing the unused fields

		if (_log.isDebugEnabled()) {
			_log.debug(TEST_FIELD_2);
		}
	}

}