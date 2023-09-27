/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.zip.ZipInputStream;

/**
 * @author Igor Beslic
 */
public class ZipInputStreamUtil {

	public static InputStream asZipInputStream(InputStream inputStream)
		throws IOException {

		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		zipInputStream.getNextEntry();

		return zipInputStream;
	}

}