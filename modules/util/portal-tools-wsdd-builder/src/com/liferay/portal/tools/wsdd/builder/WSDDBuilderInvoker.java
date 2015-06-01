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

package com.liferay.portal.tools.wsdd.builder;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderInvoker {

	public static WSDDBuilder invoke(
			File baseDir, WSDDBuilderArgs wsddBuilderArgs)
		throws Exception {

		WSDDBuilder wsddBuilder = new WSDDBuilder();

		wsddBuilder.setClassPath(wsddBuilderArgs.getClassPath());
		wsddBuilder.setFileName(
			_getAbsolutePath(baseDir, wsddBuilderArgs.getFileName()));
		wsddBuilder.setOutputPath(
			_getAbsolutePath(baseDir, wsddBuilderArgs.getOutputPath()) + "/");
		wsddBuilder.setServerConfigFileName(
			_getAbsolutePath(
				baseDir, wsddBuilderArgs.getServerConfigFileName()));
		wsddBuilder.setServiceNamespace(wsddBuilderArgs.getServiceNamespace());

		wsddBuilder.build();

		return wsddBuilder;
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getAbsolutePath(), StringPool.BACK_SLASH, StringPool.SLASH);
	}

}