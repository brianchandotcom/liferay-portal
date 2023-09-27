/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

/**
 * @author Shuyang Zhou
 */
public class TldURIUtil {

	public static String getTldURI(URL url) throws IOException {
		try (InputStream inputStream = url.openStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(inputStreamReader)) {

			StringBundler sb = null;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (sb == null) {
					int x = line.indexOf("<uri>");

					if (x < 0) {
						continue;
					}

					x += 5;

					int y = line.indexOf("</uri>", x);

					if (y >= 0) {
						return line.substring(x, y);
					}

					sb = new StringBundler(line.substring(x));
				}
				else {
					int y = line.indexOf("</uri>");

					if (y >= 0) {
						sb.append(line.substring(0, y));

						return sb.toString();
					}

					sb.append(line);
				}
			}

			return null;
		}
	}

}