/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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