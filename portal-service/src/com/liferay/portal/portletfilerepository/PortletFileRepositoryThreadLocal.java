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

package com.liferay.portal.portletfilerepository;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Kenneth Chang
 * @author Mate Thurzo
 */
public class PortletFileRepositoryThreadLocal {

	public static boolean isFileMaxSizeCheckEnabled() {
		return _fileMaxSizeCheckEnabled.get();
	}

	public static void setFileMaxSizeCheckEnabled(
		boolean fileMaxSizeCheckEnabled) {

		_fileMaxSizeCheckEnabled.set(fileMaxSizeCheckEnabled);
	}

	private static ThreadLocal<Boolean> _fileMaxSizeCheckEnabled =
		new AutoResetThreadLocal<Boolean>(
			PortletFileRepositoryThreadLocal.class +
				"._fileMaxSizeCheckEnabled", true);

}