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

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 * @deprecated As of 2.1.0, replaced by
 *                          #{@link com.liferay.css.builder.internal.util.CSSBuilderUtil}
 */
@Deprecated
public class CSSBuilderUtil
	extends com.liferay.css.builder.internal.util.CSSBuilderUtil {

	public static String parseStaticTokens(String content) {
		content = content.replace(
			"@model_hints_constants_text_display_height@",
			_TEXT_DISPLAY_HEIGHT);
		content = content.replace(
			"@model_hints_constants_text_display_width@", _TEXT_DISPLAY_WIDTH);
		content = content.replace(
			"@model_hints_constants_textarea_display_height@",
			_TEXTAREA_DISPLAY_HEIGHT);
		content = content.replace(
			"@model_hints_constants_textarea_display_width@",
			_TEXTAREA_DISPLAY_WIDTH);

		return content;
	}

	private static final String _TEXT_DISPLAY_HEIGHT = "15";

	private static final String _TEXT_DISPLAY_WIDTH = "210";

	private static final String _TEXTAREA_DISPLAY_HEIGHT = "100";

	private static final String _TEXTAREA_DISPLAY_WIDTH = "500";

}