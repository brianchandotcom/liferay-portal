/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.generator;

import java.io.File;
import java.io.IOException;

import java.util.Map;

/**
 * @author Gregory Amerson
 */
public interface WabGenerator {

	public File generate(
			ClassLoader classLoader, File file,
			Map<String, String[]> parameters)
		throws IOException;

}