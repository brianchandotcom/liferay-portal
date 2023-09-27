/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

/**
 * @author Manuel de la Peña
 */
public class DependenciesTestUtil {

	public static File getDependencyAsFile(Class<?> clazz, String name)
		throws Exception {

		URL url = clazz.getResource("dependencies/" + name);

		return new File(url.toURI());
	}

	public static InputStream getDependencyAsInputStream(
			Class<?> clazz, String name)
		throws Exception {

		return clazz.getResourceAsStream("dependencies/" + name);
	}

}