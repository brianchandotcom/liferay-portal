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
 * @author Eduardo García
 */
public class SegmentsConstants {

	public static final String RESOURCE_NAME = "com.liferay.segments";

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static final long SEGMENTS_ENTRY_ID_DEFAULT = 0;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static final long SEGMENTS_EXPERIENCE_ID_DEFAULT = 0;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static final String SEGMENTS_EXPERIENCE_ID_PREFIX =
		"segments-experience-id-";

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static final int SEGMENTS_EXPERIENCE_PRIORITY_DEFAULT = -1;

	public static final String SERVICE_NAME = "com.liferay.segments";

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static final String SOURCE_ASAH_FARO_BACKEND = "ASAH_FARO_BACKEND";

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static final String SOURCE_DEFAULT = "DEFAULT";

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static String getDefaultSegmentsEntryName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, SegmentsConstants.class);

		return LanguageUtil.get(resourceBundle, "default-segment-name");
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static String getDefaultSegmentsExperienceName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, SegmentsConstants.class);

		return LanguageUtil.get(resourceBundle, "default-experience-name");
	}

}