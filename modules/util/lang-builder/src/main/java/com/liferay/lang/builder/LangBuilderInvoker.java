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

package com.liferay.lang.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderInvoker {

	public static LangBuilder invoke(
			File baseDir, LangBuilderArgs langBuilderArgs)
		throws Exception {

		return new LangBuilder(
			langBuilderArgs.getExcludedLanguageIds(),
			_getAbsolutePath(baseDir, langBuilderArgs.getLangDirName()),
			langBuilderArgs.getLangFileName(), langBuilderArgs.isPlugin(),
			langBuilderArgs.getPortalLanguagePropertiesFileName(),
			langBuilderArgs.isTitleCapitalization(),
			langBuilderArgs.isTranslate(),
			langBuilderArgs.getTranslateSubscriptionKey());
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getAbsolutePath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}