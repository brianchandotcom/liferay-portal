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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class BNDLiferayRelengCategoryCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/app.bnd")) {
			return content;
		}

		String liferayRelengCategory = BNDSourceUtil.getDefinitionValue(
			content, "Liferay-Releng-Category");

		if (Validator.isNull(liferayRelengCategory)) {
			return content;
		}

		List<String> allowedLiferayRelengCategoryNames = getAttributeValues(
			_ALLOWED_LIFERAY_RELENG_CATEGORY_NAMES_KEY, absolutePath);

		if (!allowedLiferayRelengCategoryNames.isEmpty() &&
			!allowedLiferayRelengCategoryNames.contains(
				liferayRelengCategory)) {

			String message = StringBundler.concat(
				"The value for 'Liferay-Releng-Category' can be either blank ",
				"or one of the following values '",
				StringUtil.merge(allowedLiferayRelengCategoryNames, ", "), "'");

			addMessage(fileName, message);
		}

		return content;
	}

	private static final String _ALLOWED_LIFERAY_RELENG_CATEGORY_NAMES_KEY =
		"allowedLiferayRelengCategoryNames";

}