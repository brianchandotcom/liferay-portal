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

package com.liferay.portal.file.install.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * // TODO Temporary class needs to be removed once the refactor is complete
 *
 * @author Matthew Tambara
 */
public class Util {

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