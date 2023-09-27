/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.internal.util.test;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class SharepointConnectionTestUtil {

	public static String getSharepointVMHostname() {
		return _withRetries(
			30, SharepointConnectionTestUtil::_getSharepointVMHostname);
	}

	public static void releaseSharepointVM(String hostname) throws Exception {
		URL url = new URL(String.format(_VM_RELEASE_URL, hostname));

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestMethod(HttpMethods.GET);

		httpURLConnection.connect();

		if (httpURLConnection.getResponseCode() >= 400) {
			throw new SystemException(
				StringBundler.concat(
					"Got status code ", httpURLConnection.getResponseCode(),
					" while releasing Sharepoint VM: ",
					httpURLConnection.getResponseMessage()));
		}
	}

	private static String _getSharepointVMHostname() throws Exception {
		if (Validator.isNull(System.getenv("JENKINS_HOME"))) {
			return _LOCAL_SERVER_HOSTNAME;
		}

		URL url = new URL(_VM_ALLOCATION_URL);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestMethod(HttpMethods.GET);
		httpURLConnection.setRequestProperty(
			"Content-Type", ContentTypes.APPLICATION_JSON);

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(httpURLConnection.getInputStream()))) {

			StringBundler sb = new StringBundler();

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			String payload = sb.toString();

			Matcher matcher = _pattern.matcher(payload);

			if (!matcher.find()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"JSON payload did not contains Sharepoint hostname: " +
							payload);
				}

				return null;
			}

			return matcher.group(1);
		}
	}

	private static <T> T _withRetries(
		int tries, UnsafeSupplier<T, ? extends Exception> unsafeSupplier) {

		while (tries > 0) {
			try {
				tries--;

				T value = unsafeSupplier.get();

				if (value != null) {
					return value;
				}

				try {
					Thread.sleep(5000);
				}
				catch (InterruptedException interruptedException) {
				}
			}
			catch (Exception exception) {
				if (tries == 0) {
					_log.error(exception);

					throw new RuntimeException(exception);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Error while trying to get Sharepoint hostname, ",
							"will retry ", tries, " more times"),
						exception);
				}
			}
		}

		throw new RuntimeException(
			"Could not get Sharepoint VM hostname, reached maximum number of " +
				"retries");
	}

	private static final String _LEASE_TIME = String.valueOf(120000);

	private static final String _LOCAL_SERVER_HOSTNAME = "liferay-abb20d6";

	private static final String _VM_ALLOCATION_URL = StringBundler.concat(
		"http://it.liferay.com/osb-ici-controller-web/vm/allocation/borrow?",
		"leaseTime=", _LEASE_TIME, "&resourceType=qa.sharepoint2010");

	private static final String _VM_RELEASE_URL =
		"http://it.liferay.com/osb-ici-controller-web/vm/allocation/release?" +
			"hostname=%s&amp;resourceType=qa.sharepoint2010";

	private static final Log _log = LogFactoryUtil.getLog(
		SharepointConnectionTestUtil.class);

	private static final Pattern _pattern = Pattern.compile(
		"\"hostname\" *: *\"([^\"]+)\"");

}