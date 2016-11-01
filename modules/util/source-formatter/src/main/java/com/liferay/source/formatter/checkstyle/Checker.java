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

package com.liferay.source.formatter.checkstyle;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.SortedSet;

/**
 * @author Hugo Huijser
 */
public class Checker extends com.puppycrawl.tools.checkstyle.Checker {

	@Override
	public void fireErrors(
		String fileName, SortedSet<LocalizedMessage> errors) {

		super.fireErrors(_normalizeFileName(fileName), errors);
	}

	@Override
	public void fireFileFinished(String fileName) {
		super.fireFileFinished(_normalizeFileName(fileName));
	}

	@Override
	public void fireFileStarted(String fileName) {
		super.fireFileStarted(_normalizeFileName(fileName));
	}

	private String _normalizeFileName(String fileName) {
		Path filePath = Paths.get(fileName);

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}