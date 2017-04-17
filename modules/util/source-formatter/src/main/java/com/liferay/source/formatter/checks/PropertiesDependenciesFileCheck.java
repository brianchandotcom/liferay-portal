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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class PropertiesDependenciesFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/dependencies.properties")) {
			return _formatDependenciesProperties(content);
		}

		return content;
	}

	private String _formatDependenciesProperties(String content) {
		List<String> lines = ListUtil.fromString(content);

		lines = ListUtil.sort(lines);

		StringBundler sb = new StringBundler(content.length() * 2);

		for (String line : lines) {
			line = StringUtil.removeChar(line, CharPool.SPACE);

			if (Validator.isNotNull(line) &&
				(line.charAt(0) != CharPool.POUND)) {

				sb.append(line);
				sb.append(CharPool.NEW_LINE);
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

}