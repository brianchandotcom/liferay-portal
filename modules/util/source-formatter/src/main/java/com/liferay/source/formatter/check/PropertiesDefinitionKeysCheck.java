/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

/**
 * @author Hugo Huijser
 */
public class PropertiesDefinitionKeysCheck extends BaseDefinitionKeysCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/gradle.properties") ||
			fileName.endsWith("/liferay-plugin-package.properties") ||
			fileName.endsWith("/TLiferayBatchFileProperties.properties")) {

			content = sortDefinitionKeys(
				content, getDefinitions(content),
				new NaturalOrderStringComparator());
		}

		return content;
	}

}