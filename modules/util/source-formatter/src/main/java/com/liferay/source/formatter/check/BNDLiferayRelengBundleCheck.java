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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.io.File;

import java.util.List;

/**
 * @author Alan Huang
 */
public class BNDLiferayRelengBundleCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/app.bnd") ||
			!absolutePath.contains("/modules/dxp/apps/")) {

			return content;
		}

		List<String> allowedLiferayRelengBundleNames = getAttributeValues(
			_ALLOWED_LIFERAY_RELENG_BUNDLE_NAMES, absolutePath);

		for (String allowedLiferayRelengBundleName :
				allowedLiferayRelengBundleNames) {

			if (absolutePath.contains(allowedLiferayRelengBundleName)) {
				return content;
			}
		}

		String liferayRelengBundle = BNDSourceUtil.getDefinitionValue(
			content, "Liferay-Releng-Bundle");

		if (Validator.isNull(liferayRelengBundle) ||
			liferayRelengBundle.equals("false")) {

			return content;
		}

		int pos = absolutePath.lastIndexOf(StringPool.SLASH);

		File file = new File(
			absolutePath.substring(0, pos + 1) + ".lfrbuild-release-src");

		if (!file.exists()) {
			addMessage(
				fileName,
				StringBundler.concat(
					"DXP modules that have a 'app.bnd' file that contains ",
					"'Liferay-Releng-Bundle: true' should have a ",
					"'.lfrbuild-release-src' file"));
		}

		return content;
	}

	private static final String _ALLOWED_LIFERAY_RELENG_BUNDLE_NAMES =
		"allowedLiferayRelengBundleNames";

}