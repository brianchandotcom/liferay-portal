/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.talend;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

/**
 * @author Shuyang Zhou
 */
public class TalendArchiveUtil {

	public static InputStream getInputStream() throws IOException {
		URL url = TalendArchiveUtil.class.getResource("/jobInfo.properties");

		String urlString = String.valueOf(url);

		URL zipURL = new URL(
			urlString.substring(
				"jar:".length(),
				urlString.length() - "!/jobInfo.properties".length()));

		return zipURL.openStream();
	}

}