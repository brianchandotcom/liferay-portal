/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.model;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Samuel Kong
 */
public class DLFileEntryConstants {

	public static final int DEFAULT_READ_COUNT = 0;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		PropsUtil.get("lock.expiration.time." + getClassName()));

	public static final String PRIVATE_WORKING_COPY_VERSION = "PWC";

	public static final String VERSION_DEFAULT = "1.0";

	public static String getClassName() {
		return DLFileEntry.class.getName();
	}

}