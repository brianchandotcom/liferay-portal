/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class InputStreamUtil {

	public static BufferedInputStream toBufferedInputStream(
		InputStream inputStream) {

		if (inputStream instanceof BufferedInputStream) {
			return (BufferedInputStream)inputStream;
		}

		return new BufferedInputStream(inputStream);
	}

}