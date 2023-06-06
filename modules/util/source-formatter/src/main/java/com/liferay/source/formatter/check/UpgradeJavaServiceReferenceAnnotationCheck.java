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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJavaServiceReferenceAnnotationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		String newContent = content;

		Matcher serviceReferenceMatcher = _serviceReferencePattern.matcher(
			newContent);

		boolean replaced = false;

		while (serviceReferenceMatcher.find()) {
			newContent = StringUtil.replace(
				newContent,
				JavaSourceUtil.getMethodCall(
					content, serviceReferenceMatcher.start()),
				"@Reference");

			replaced = true;
		}

		if (replaced) {
			newContent = StringUtil.replace(
				newContent,
				"import com.liferay.portal.spring.extender.service." +
					"ServiceReference;",
				"import org.osgi.service.component.annotations.Reference;");
		}

		return newContent;
	}

	private static final Pattern _serviceReferencePattern = Pattern.compile(
		"\\@ServiceReference\\(");

}