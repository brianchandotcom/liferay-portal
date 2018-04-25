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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.DebugUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;

/**
 * @author Hugo Huijser
 */
public abstract class BaseCheck extends AbstractCheck {

	@Override
	public int[] getAcceptableTokens() {
		return getDefaultTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setRunOutsidePortalExcludes(String runOutsidePortalExcludes) {
		_runOutsidePortalExcludes = ArrayUtil.append(
			_runOutsidePortalExcludes,
			StringUtil.split(runOutsidePortalExcludes));
	}

	public void setShowDebugInformation(boolean showDebugInformation) {
		_showDebugInformation = showDebugInformation;
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!_enabled) {
			return;
		}

		if (!_showDebugInformation) {
			doVisitToken(detailAST);

			return;
		}

		long startTime = System.currentTimeMillis();

		doVisitToken(detailAST);

		long endTime = System.currentTimeMillis();

		Class<?> clazz = getClass();

		DebugUtil.increaseProcessingTime(
			clazz.getSimpleName(), endTime - startTime);
	}

	protected abstract void doVisitToken(DetailAST detailAST);

	protected boolean isRunOutsidePortalExclude() {
		if (ArrayUtil.isEmpty(_runOutsidePortalExcludes)) {
			return false;
		}

		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		for (String exclude : _runOutsidePortalExcludes) {
			if (Validator.isNull(exclude)) {
				continue;
			}

			if (exclude.startsWith("**")) {
				exclude = exclude.substring(2);
			}

			if (exclude.endsWith("**")) {
				exclude = exclude.substring(0, exclude.length() - 2);

				if (absolutePath.contains(exclude)) {
					return true;
				}

				continue;
			}

			if (absolutePath.endsWith(exclude)) {
				return true;
			}
		}

		return false;
	}

	private boolean _enabled = true;
	private String[] _runOutsidePortalExcludes = new String[0];
	private boolean _showDebugInformation;

}