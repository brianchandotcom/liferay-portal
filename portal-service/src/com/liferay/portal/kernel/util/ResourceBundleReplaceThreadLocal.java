/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Shuyang Zhou
 */
public class ResourceBundleReplaceThreadLocal {

	public static Boolean isReplace() {
		return _resourceBundleReplaceThreadLocal.get();
	}

	public static void removeReplace() {
		_resourceBundleReplaceThreadLocal.remove();
	}

	public static void setReplace(Boolean logoutSession) {
		_resourceBundleReplaceThreadLocal.set(logoutSession);
	}

	private static final ThreadLocal<Boolean>
		_resourceBundleReplaceThreadLocal =
			new AutoResetThreadLocal<Boolean>(
				ResourceBundleReplaceThreadLocal.class +
					"._resourceBundleReplaceThreadLocal", Boolean.FALSE);

}