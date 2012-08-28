/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.aspectj;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.ProtectionDomain;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class WeavingClassLoader extends URLClassLoader {

	public WeavingClassLoader(
		URL[] urls, Class<?>[] aspectClasses, File dumpFolder) {

		super(urls, null);

		_weavingAdaptor = new URLWeavingAdaptor(urls, aspectClasses);

		_dumpFolder = dumpFolder;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String resourcePath = name.replace('.', '/');

		resourcePath = resourcePath.concat(".class");

		InputStream inputStream = getResourceAsStream(resourcePath);

		byte[] data = null;

		try {
			if (inputStream == null) {
				// On missing could be a generated inner class
				data = _weavingAdaptor.removeGeneratedClassDate(name);
			}
			else {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				StreamUtil.transfer(
					inputStream, unsyncByteArrayOutputStream, true);

				data = unsyncByteArrayOutputStream.toByteArray();
			}

			if (data != null) {
				byte[] oldData = data;

				data = _weavingAdaptor.weaveClass(name, data, false);

				if (!Arrays.equals(oldData, data)) {
					if (_dumpFolder != null) {
						File dumpFile = new File(_dumpFolder, resourcePath);

						File folder = dumpFile.getParentFile();

						folder.mkdirs();

						FileOutputStream fileOutputStream =
							new FileOutputStream(dumpFile);

						fileOutputStream.write(data);

						fileOutputStream.close();

						if (_log.isInfoEnabled()) {
							_log.info(
								"Woven class " + name +
									", dump woven result into " +
										dumpFile.getCanonicalPath());
						}
					}
					else {
						if (_log.isInfoEnabled()) {
							_log.info("Woven class " + name);
						}
					}
				}

				return _generateClass(name, data);
			}
			else {
				throw new ClassNotFoundException(name);
			}
		}
		catch (IOException ioe) {
			throw new ClassNotFoundException(name, ioe);
		}
	}

	private Class<?> _generateClass(String name, byte[] data) {
		Class<?> clazz = defineClass(
			name, data, 0, data.length, (ProtectionDomain)null);

		String packageName = null;

		int index = name.lastIndexOf('.');

		if (index != -1) {
			packageName = name.substring(0, index);
		}

		if (packageName != null) {
			Package pkg = getPackage(packageName);

			if (pkg == null) {
				definePackage(
					packageName, null, null, null, null, null, null, null);
			}
		}

		return clazz;
	}

	private static Log _log = LogFactoryUtil.getLog(WeavingClassLoader.class);

	private final File _dumpFolder;

	private final URLWeavingAdaptor _weavingAdaptor;

}