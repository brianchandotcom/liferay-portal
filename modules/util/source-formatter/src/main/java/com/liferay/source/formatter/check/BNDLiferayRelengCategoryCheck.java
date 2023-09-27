/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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