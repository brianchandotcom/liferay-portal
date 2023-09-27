/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			langBuilderArgs.getLangFileName(),
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