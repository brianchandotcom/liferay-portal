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

package com.liferay.frontend.icons.web.internal.validator;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsPackValidator {

	/**
	 * We allow only alphanumeric characters, underscores, and dashes.
	 */
	public static boolean isValidName(String name) {
		String trimmedName = name.trim();

		if (trimmedName.length() != name.length()) {
			return false;
		}

		name = StringUtil.removeChars(name, CharPool.DASH, CharPool.UNDERLINE);

		if (!Validator.isAlphanumericName(name)) {
			return false;
		}

		return true;
	}

}