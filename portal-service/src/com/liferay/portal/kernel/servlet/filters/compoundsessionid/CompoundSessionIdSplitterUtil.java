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

package com.liferay.portal.kernel.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 */
public class CompoundSessionIdSplitterUtil {

	public static CompoundSessionIdSplitter getCompoundSessionIdSplitter() {
		PortalRuntimePermission.checkGetBeanProperty(
			CompoundSessionIdSplitterUtil.class);

		return _compoundSessionIdSplitter;
	}

	public static String getSessionIdDelimiter() {
		return getCompoundSessionIdSplitter().getSessionIdDelimiter();
	}

	public static boolean hasSessionDelimiter() {
		return getCompoundSessionIdSplitter().hasSessionDelimiter();
	}

	public static String parseSessionId(String sessionId) {
		return getCompoundSessionIdSplitter().parseSessionId(sessionId);
	}

	public void setCompoundSessionIdSplitter(
		CompoundSessionIdSplitter compoundSessionIdSplitter) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_compoundSessionIdSplitter = compoundSessionIdSplitter;
	}

	private static CompoundSessionIdSplitter _compoundSessionIdSplitter;

}