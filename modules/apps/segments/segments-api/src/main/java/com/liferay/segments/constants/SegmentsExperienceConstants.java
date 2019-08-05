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

package com.liferay.segments.constants;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sarai Díaz
 */
public class SegmentsExperienceConstants {

	public static final long ID_DEFAULT = 0;

	public static final String ID_PREFIX = "segments-experience-id-";

	public static final String KEY_DEFAULT = "DEFAULT";

	public static final int PRIORITY_DEFAULT = -1;

	public static String getDefaultSegmentsExperienceName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, SegmentsExperienceConstants.class);

		return LanguageUtil.get(resourceBundle, "default-experience-name");
	}

}