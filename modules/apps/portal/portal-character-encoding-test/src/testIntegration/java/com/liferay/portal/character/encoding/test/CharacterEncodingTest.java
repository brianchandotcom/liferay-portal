/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.character.encoding.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.character.encoding.test.servlet.filter.CharacterEncodingFilter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class CharacterEncodingTest {

	@Test
	public void testWithCharacterEncoding() throws IOException {
		Assert.assertEquals(_JAPANESE_TEST, _testCharacterEncoding(true));
	}

	@Test
	public void testWithoutCharacterEncoding() throws IOException {
		String output = _testCharacterEncoding(false);

		Assert.assertNotEquals(_JAPANESE_TEST, output);

		String serverSideString = new String(
			_JAPANESE_TEST.getBytes(_CHARSET), StringPool.UTF8);

		String clientSideString = new String(
			serverSideString.getBytes(StringPool.UTF8), _CHARSET);

		Assert.assertEquals(clientSideString, output);
	}

	private String _testCharacterEncoding(boolean addCharacterEncoding)
		throws IOException {

		URL url = new URL("http://localhost:8080");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		String contentType = "application/x-www-form-urlencoded";

		if (addCharacterEncoding) {
			contentType = contentType + "; charset=" + _CHARSET;
		}

		httpURLConnection.addRequestProperty("Content-Type", contentType);

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");

		try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
			outputStream.write(_PARAMETER_STRING.getBytes(_CHARSET));
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new InputStreamReader(
						httpURLConnection.getInputStream(), _CHARSET))) {

			return unsyncBufferedReader.readLine();
		}
		finally {
			httpURLConnection.disconnect();
		}
	}

	private static final Charset _CHARSET = Charset.forName("Shift_JIS");

	private static final String _JAPANESE_TEST = "テスト";

	private static final String _PARAMETER_STRING =
		CharacterEncodingFilter.REQUEST_PARAMETER_NAME + StringPool.EQUAL +
			_JAPANESE_TEST;

}