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

package com.liferay.portal.util;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ProgressStatusConstants;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.concurrent.TimeUnit;

/**
 * @author Alexander Chow
 */
public class JarUtil {

	public static void downloadAndInstallJar(
			boolean globalClassPath, String url, String name,
			ProgressTracker progressTracker)
		throws Exception {

		setProgressStatus(progressTracker, ProgressStatusConstants.DOWNLOADING);

		if (_log.isInfoEnabled()) {
			_log.info("Downloading " + url);
		}

		byte[] bytes = HttpUtil.URLtoByteArray(url);

		setProgressStatus(progressTracker, ProgressStatusConstants.COPYING);

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			try {
				DLStoreUtil.deleteFile(
					_REPOSITORY, _REPOSITORY, _FILE_PATH + name);
			}
			catch (Exception e) {
			}

			DLStoreUtil.addFile(
				_REPOSITORY, _REPOSITORY, _FILE_PATH + name, bytes);

			try {
				ClusterRequest clusterRequest =
					ClusterRequest.createMulticastRequest(
						new MethodHandler(
							_installJarKey, globalClassPath, name));

				FutureClusterResponses futureClusterResponses =
					ClusterExecutorUtil.execute(clusterRequest);

				futureClusterResponses.get(30, TimeUnit.SECONDS);
			}
			finally {
				try {
					DLStoreUtil.deleteFile(
						_REPOSITORY, _REPOSITORY, _FILE_PATH + name);
				}
				catch (Exception e) {
				}
			}
		}
		else {
			setProgressStatus(progressTracker, ProgressStatusConstants.COPYING);

			installJar(bytes, globalClassPath, name);
		}
	}

	public static void installJar(boolean globalClassPath, String name)
		throws Exception {

		installJar(null, globalClassPath, name);
	}

	protected static void addJarFileToClassLoader(File file) throws Exception {
		Class<?> clazz = URLClassLoader.class;

		Method method = clazz.getDeclaredMethod(
			"addURL", new Class[] {URL.class});

		method.setAccessible(true);

		URI uri = file.toURI();

		method.invoke(
			ClassLoader.getSystemClassLoader(), new Object[] {uri.toURL()});
	}

	protected static void installJar(
			byte[] bytes, boolean globalClassPath, String name)
		throws Exception {

		String libPath = PropsValues.LIFERAY_LIB_PORTAL_DIR;

		if (globalClassPath) {
			libPath = PropsValues.LIFERAY_LIB_GLOBAL_DIR;
		}

		File file = new File(libPath + StringPool.SLASH + name);

		InputStream is = null;

		try {
			if (_log.isInfoEnabled()) {
				_log.info("Writing " + file);
			}

			if (bytes != null) {
				FileUtil.write(file, bytes);
			}
			else {
				is = DLStoreUtil.getFileAsStream(
					_REPOSITORY, _REPOSITORY, _FILE_PATH + name);

				FileUtil.write(file, is);
			}
		}
		finally {
			if (is != null) {
				is.close();
			}
		}

		addJarFileToClassLoader(file);
	}

	protected static void setProgressStatus(
		ProgressTracker progressTracker, int status) {

		if (progressTracker == null) {
			return;
		}

		progressTracker.setStatus(status);
	}

	private static final String _FILE_PATH = "jar_temp/";

	private static final long _REPOSITORY = CompanyConstants.SYSTEM;

	private static Log _log = LogFactoryUtil.getLog(JarUtil.class);

	private static MethodKey _installJarKey = new MethodKey(
		JarUtil.class, "installJar", boolean.class, String.class);

}