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

package com.liferay.source.formatter.checks;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CompatClassImportsCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_compatClassNamesMap = getCompatClassNamesMap();
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (isPortalSource() || isSubrepository() ||
			absolutePath.contains("/ext-") ||
			absolutePath.contains("/portal-compat-shared/")) {

			return content;
		}

		return _fixCompatClassImports(content);
	}

	private String _fixCompatClassImports(String content) {
		for (Map.Entry<String, String> entry :
				_compatClassNamesMap.entrySet()) {

			String compatClassName = entry.getKey();
			String extendedClassName = entry.getValue();

			Pattern pattern = Pattern.compile(extendedClassName + "\\W");

			while (true) {
				Matcher matcher = pattern.matcher(content);

				if (!matcher.find()) {
					break;
				}

				content =
					content.substring(0, matcher.start()) + compatClassName +
						content.substring(matcher.end() - 1);
			}
		}

		return content;
	}

	private Map<String, String> _compatClassNamesMap;

}