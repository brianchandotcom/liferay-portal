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

package com.liferay.portal.layoutconfiguration.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutTemplateConstants;

/**
 * @author Iván Zaera Avellón
 */
public class LayoutTemplateLocator {

	public LayoutTemplateLocator(String templateId) {
		String separator = LayoutTemplateConstants.CUSTOM_SEPARATOR;
		boolean standard = false;

		if (templateId.contains(LayoutTemplateConstants.STANDARD_SEPARATOR)) {
			separator = LayoutTemplateConstants.STANDARD_SEPARATOR;
			standard = true;
		}

		String layoutTemplateId = null;

		String themeId = null;

		int pos = templateId.indexOf(separator);

		if (pos != -1) {
			layoutTemplateId = templateId.substring(pos + separator.length());

			themeId = templateId.substring(0, pos);
		}

		pos = layoutTemplateId.indexOf(
			LayoutTemplateConstants.INSTANCE_SEPARATOR);

		if (pos != -1) {
			layoutTemplateId = layoutTemplateId.substring(
				pos + LayoutTemplateConstants.INSTANCE_SEPARATOR.length() + 1);

			pos = layoutTemplateId.indexOf(StringPool.UNDERLINE);

			layoutTemplateId = layoutTemplateId.substring(pos + 1);
		}

		_layoutTemplateId = layoutTemplateId;
		_standard = standard;
		_themeId = themeId;
	}

	public LayoutTemplateLocator(
		String layoutTemplateId, boolean standard, String themeId) {

		_layoutTemplateId = layoutTemplateId;
		_standard = standard;
		_themeId = themeId;
	}

	public String getLayoutTemplateId() {
		return _layoutTemplateId;
	}

	public String getThemeId() {
		return _themeId;
	}

	public boolean isStandard() {
		return _standard;
	}

	private final String _layoutTemplateId;
	private final boolean _standard;
	private final String _themeId;

}