/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util.internal.constants;

import java.io.File;

/**
 * @author Drew Brokke
 */
public class ReleaseConstants {

	public static final File CACHE_DIR = new File(
		System.getProperty("user.home"), ".liferay/workspace");

	public static final long DEFAULT_MAX_AGE = 7;

}