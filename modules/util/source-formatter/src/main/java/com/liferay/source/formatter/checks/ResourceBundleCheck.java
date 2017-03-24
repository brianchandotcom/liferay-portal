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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class ResourceBundleCheck extends BaseFileCheck {

	public ResourceBundleCheck(List<String> excludes) {
		_excludes = excludes;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("ResourceBundleCheck.java") ||
			fileName.endsWith("ResourceBundleUtil.java") ||
			isExcludedPath(_excludes, absolutePath)) {

			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		int pos = content.indexOf("ResourceBundle.getBundle(");

		if (pos != -1) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Use ResourceBundleUtil.getBundle instead of " +
					"ResourceBundle.getBundle, see LPS-58529",
				getLineCount(content, pos));
		}

		pos = content.indexOf("resourceBundle.getString(");

		if (pos != -1) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Use ResourceBundleUtil.getString instead of " +
					"resourceBundle.getString, see LPS-58529",
				getLineCount(content, pos));
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private final List<String> _excludes;

}