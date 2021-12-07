/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.internal.download;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(enabled = false, immediate = true, service = Downloader.class)
public class DownloaderImpl implements Downloader {

	@Override
	public String download(String address) {
		try {
			URL url = new URL(address);

			URLConnection urlConnection = url.openConnection();

			urlConnection.addRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 " +
					"Firefox/25.0");

			return StringUtil.read(urlConnection.getInputStream());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}