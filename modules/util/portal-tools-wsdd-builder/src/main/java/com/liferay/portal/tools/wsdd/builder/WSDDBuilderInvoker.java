/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.wsdd.builder;

import com.liferay.petra.string.CharPool;
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
			file.getAbsolutePath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}