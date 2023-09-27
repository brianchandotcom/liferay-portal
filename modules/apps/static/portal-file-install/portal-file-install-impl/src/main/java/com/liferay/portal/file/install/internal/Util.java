/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * // TODO Temporary class needs to be removed once the refactor is complete
 *
 * @author Matthew Tambara
 */
public class Util {

	public static String getFilePath(String dir) {
		File file = new File(dir);

		try {
			file = file.getCanonicalFile();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		URI uri = file.toURI();

		uri = uri.normalize();

		return uri.getPath();
	}

	public static long loadChecksum(
		Bundle bundle, BundleContext bundleContext) {

		String key = _getBundleKey(bundle);

		File file = bundleContext.getDataFile(key.concat(_CHECKSUM_SUFFIX));

		if (!file.exists()) {
			return Long.MIN_VALUE;
		}

		try (InputStream inputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(
				inputStream)) {

			return dataInputStream.readLong();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Long.MIN_VALUE;
		}
	}

	public static void storeChecksum(
		Bundle bundle, long checksum, BundleContext bundleContext) {

		String key = _getBundleKey(bundle);

		File file = bundleContext.getDataFile(key.concat(_CHECKSUM_SUFFIX));

		try (OutputStream outputStream = new FileOutputStream(file);
			DataOutputStream dataOutputStream = new DataOutputStream(
				outputStream)) {

			dataOutputStream.writeLong(checksum);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static String _getBundleKey(Bundle bundle) {
		return String.valueOf(bundle.getBundleId());
	}

	private static final String _CHECKSUM_SUFFIX = ".checksum";

	private static final Log _log = LogFactoryUtil.getLog(Util.class);

}