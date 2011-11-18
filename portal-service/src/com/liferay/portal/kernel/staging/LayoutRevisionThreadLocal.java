/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 *
 * @author Eudaldo Alonso
 */
public class LayoutRevisionThreadLocal {

	public static Long getLayoutRevisionId() {
		Long layoutRevisionId = _layoutRevisionId.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getLayoutRevisionId " + layoutRevisionId);
		}

		return layoutRevisionId;
	}

	public static void setLayoutRevisionId(int layoutRevisionId) {
		setLayoutRevisionId(Long.valueOf(layoutRevisionId));
	}

	public static void setLayoutRevisionId(Long layoutRevisionId) {
		if (_log.isDebugEnabled()) {
			_log.debug("setLayoutRevisionId " + layoutRevisionId);
		}

		if (layoutRevisionId > 0) {
			_layoutRevisionId.set(layoutRevisionId);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutRevisionThreadLocal.class);

	private static ThreadLocal<Long> _layoutRevisionId =
		new AutoResetThreadLocal<Long>(
			LayoutRevisionThreadLocal.class + "._layoutRevisionId", 0L);
}
