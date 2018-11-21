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

package com.liferay.portal.kernel.language;

/**
 * @author Drew Brokke
 */
public class LanguageConstants {

	public static final String KEY_DIR = "lang.dir";

	public static final String KEY_LINE_BEGIN = "lang.line.begin";

	public static final String KEY_LINE_END = "lang.line.end";

	public static final String KEY_USER_DEFAULT_PORTRAIT =
		"lang.user.default.portrait";

	public static final String KEY_USER_INITIALS_FIELD_NAMES =
		"lang.user.initials.field.names";

	public static final String KEY_USER_NAME_FIELD_NAMES =
		"lang.user.name.field.names";

	public static final String KEY_USER_NAME_PREFIX_VALUES =
		"lang.user.name.prefix.values";

	public static final String KEY_USER_NAME_REQUIRED_FIELD_NAMES =
		"lang.user.name.required.field.names";

	public static final String KEY_USER_NAME_SUFFIX_VALUES =
		"lang.user.name.suffix.values";

	public static final String[] KEYS_SPECIAL_PROPERTIES = {
		KEY_DIR, KEY_LINE_BEGIN, KEY_LINE_END, KEY_USER_DEFAULT_PORTRAIT,
		KEY_USER_INITIALS_FIELD_NAMES, KEY_USER_NAME_FIELD_NAMES,
		KEY_USER_NAME_PREFIX_VALUES, KEY_USER_NAME_REQUIRED_FIELD_NAMES,
		KEY_USER_NAME_SUFFIX_VALUES
	};

	public static final String VALUE_FIRST_NAME = "first-name";

	public static final String VALUE_IMAGE = "image";

	public static final String VALUE_INITIALS = "initials";

	public static final String VALUE_LAST_NAME = "last-name";

	public static final String VALUE_LEFT = "left";

	public static final String VALUE_LTR = "ltr";

	public static final String VALUE_MIDDLE_NAME = "middle-name";

	public static final String VALUE_PREFIX = "prefix";

	public static final String VALUE_RIGHT = "right";

	public static final String VALUE_RTL = "rtl";

	public static final String VALUE_SUFFIX = "suffix";

	public static final String[] VALUES_DIR = {VALUE_LTR, VALUE_RTL};

	public static final String[] VALUES_LINE = {VALUE_LEFT, VALUE_RIGHT};

	public static final String[] VALUES_USER_DEFAULT_PORTRAIT = {
		VALUE_IMAGE, VALUE_INITIALS
	};

	public static final String[] VALUES_USER_INITIALS_FIELD_NAMES = {
		VALUE_FIRST_NAME, VALUE_LAST_NAME, VALUE_MIDDLE_NAME
	};

	public static final String[] VALUES_USER_NAME_FIELD_NAMES = {
		VALUE_PREFIX, VALUE_FIRST_NAME, VALUE_MIDDLE_NAME, VALUE_LAST_NAME,
		VALUE_SUFFIX
	};

}