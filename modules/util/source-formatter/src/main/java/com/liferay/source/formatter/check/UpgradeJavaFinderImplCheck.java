/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaFinderImplCheck
	extends BaseAddComponentAnnotationCheck {

	@Override
	protected String getAnnotationContent(
		String absolutePath, String className, String content,
		JavaClass javaClass) {

		return String.format(
			"@Component(service = %s.class)",
			StringUtil.extractFirst(className, "BaseImpl"));
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"org.osgi.service.component.annotations.Component"
		};
	}

	@Override
	protected boolean isValidClassName(String className) {
		return className.contains("FinderBaseImpl");
	}

}