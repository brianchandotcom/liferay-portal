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

package com.liferay.portal.language;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class LanguageImplTest {

	private static final Double _BIG_DOUBLE = 1234567890.12D;

	private static final Float _BIG_FLOAT = 1234567.85F;

	private static final Integer _BIG_INTEGER = 1234567890;

	private static final String _LANG_KEY_WITH_ARGUMENT = "x-hours";

	private static final String _LANG_KEY_WITH_ARGUMENTS =
		"x-has-invited-you-to-join-x";

	private static LanguageImpl _languageImpl;

}