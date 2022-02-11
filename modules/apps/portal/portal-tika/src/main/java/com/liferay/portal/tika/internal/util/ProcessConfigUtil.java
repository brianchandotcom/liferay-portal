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

package com.liferay.portal.tika.internal.util;

import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PortalClassPathUtil;

import java.io.File;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.apache.tika.Tika;

/**
 * @author Shuyang Zhou
 */
public class ProcessConfigUtil {

	public static ProcessConfig getProcessConfig() {
		return _processConfig;
	}

	private static void _appendBundleClassPath(StringBundler sb) {
		ProtectionDomain protectionDomain = Tika.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		File file = new File(url.getFile());

		File cpLibFolder = file.getParentFile();

		for (File cpLibFile : cpLibFolder.listFiles()) {
			sb.append(cpLibFile.getAbsolutePath());
			sb.append(File.pathSeparator);
		}
	}

	private static String _getSelfJarPath() {
		ProtectionDomain protectionDomain =
			ProcessConfigUtil.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return url.getFile();
	}

	private static final ProcessConfig _processConfig;

	static {
		ProcessConfig portalProcessConfig =
			PortalClassPathUtil.getPortalProcessConfig();

		ProcessConfig.Builder builder = new ProcessConfig.Builder(
			portalProcessConfig);

		StringBundler sb = new StringBundler();

		sb.append(_getSelfJarPath());
		sb.append(File.pathSeparator);

		_appendBundleClassPath(sb);

		sb.append(portalProcessConfig.getRuntimeClassPath());

		builder.setRuntimeClassPath(sb.toString());

		builder.setReactClassLoader(
			AggregateClassLoader.getAggregateClassLoader(
				PortalClassLoaderUtil.getClassLoader(),
				ProcessConfigUtil.class.getClassLoader()));

		_processConfig = builder.build();
	}

}