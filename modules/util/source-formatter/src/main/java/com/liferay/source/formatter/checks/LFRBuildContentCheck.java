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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Shin
 */
public class LFRBuildContentCheck extends BaseFileCheck {

	public void setNonemptyMarkerFileNames(String nonemptyMarkerFileNames) {
		_nonemptyMarkerFileNames = ListUtil.fromString(
			nonemptyMarkerFileNames, StringPool.COMMA);
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (_isNonemptyMarkerFileName(fileName)) {
			content = StringUtil.trim(content);
		}
		else {
			content = StringPool.BLANK;
		}

		return content;
	}

	private boolean _isNonemptyMarkerFileName(String fileName) {
		for (String nonemptyMarkerFileName : _nonemptyMarkerFileNames) {
			if (fileName.endsWith(nonemptyMarkerFileName)) {
				return true;
			}
		}

		return false;
	}

	private List<String> _nonemptyMarkerFileNames = new ArrayList<>();

}