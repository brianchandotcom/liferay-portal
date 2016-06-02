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

package com.liferay.css.builder;

import com.liferay.portal.kernel.model.ModelHintsConstants;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class CSSBuilderUtil {

	public static File getOutputFile(String fileName, String outputDirName) {
		return getOutputFile(fileName, outputDirName, StringPool.BLANK);
	}

	public static File getOutputFile(
		String fileName, String outputDirName, String suffix) {

		return new File(getOutputFileName(fileName, outputDirName, suffix));
	}

	public static String getOutputFileName(
		String fileName, String outputDirName, String suffix) {

		String cacheFileName = StringUtil.replace(
			fileName, CharPool.BACK_SLASH, CharPool.SLASH);

		int x = cacheFileName.lastIndexOf(CharPool.SLASH);
		int y = cacheFileName.lastIndexOf(CharPool.PERIOD);

		if (cacheFileName.endsWith(".scss")) {
			cacheFileName = cacheFileName.substring(0, y + 1) + "css";
		}

		return cacheFileName.substring(0, x + 1) + outputDirName +
			cacheFileName.substring(x + 1, y) + suffix +
				cacheFileName.substring(y);
	}

	public static String getRtlCustomFileName(String fileName) {
		int pos = fileName.lastIndexOf(CharPool.PERIOD);

		return fileName.substring(0, pos) + "_rtl" + fileName.substring(pos);
	}

	public static String parseStaticTokens(String content) {
		return StringUtil.replace(
			content,
			new String[] {
				"@model_hints_constants_text_display_height@",
				"@model_hints_constants_text_display_width@",
				"@model_hints_constants_textarea_display_height@",
				"@model_hints_constants_textarea_display_width@"
			},
			new String[] {
				ModelHintsConstants.TEXT_DISPLAY_HEIGHT,
				ModelHintsConstants.TEXT_DISPLAY_WIDTH,
				ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT,
				ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH
			});
	}

}