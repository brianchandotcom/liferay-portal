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

package com.liferay.gradle.util.copy;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;

/**
 * @author Andrea Di Giorgi
 */
public class ReplaceLeadingPathAction implements Action<FileCopyDetails> {

	public ReplaceLeadingPathAction(
		boolean excludeUnmatched,
		Map<Object, Object> leadingPathReplacementsMap) {

		_excludeUnmatched = excludeUnmatched;
		_leadingPathReplacementsMap = leadingPathReplacementsMap;
	}

	public ReplaceLeadingPathAction(
		Map<Object, Object> leadingPathReplacementsMap) {

		this(true, leadingPathReplacementsMap);
	}

	@Override
	public void execute(FileCopyDetails fileCopyDetails) {
		RelativePath relativePath = fileCopyDetails.getRelativePath();

		String path = relativePath.getPathString();

		for (Map.Entry<Object, Object> entry :
				_leadingPathReplacementsMap.entrySet()) {

			String oldLeadingPath = _fixLeadingPath(entry.getKey());

			if (path.startsWith(oldLeadingPath)) {
				String newLeadingPath = _fixLeadingPath(entry.getValue());

				path = newLeadingPath + path.substring(oldLeadingPath.length());

				fileCopyDetails.setRelativePath(RelativePath.parse(true, path));

				return;
			}
		}

		if (_excludeUnmatched) {
			fileCopyDetails.exclude();
		}
	}

	private String _fixLeadingPath(Object leadingPath) {
		String leadingPathString = GradleUtil.toString(leadingPath);

		if (Validator.isNull(leadingPathString)) {
			return "";
		}

		char lastChar = leadingPathString.charAt(
			leadingPathString.length() - 1);

		if (lastChar != '/') {
			leadingPathString += '/';
		}

		return leadingPathString;
	}

	private final boolean _excludeUnmatched;
	private final Map<Object, Object> _leadingPathReplacementsMap;

}