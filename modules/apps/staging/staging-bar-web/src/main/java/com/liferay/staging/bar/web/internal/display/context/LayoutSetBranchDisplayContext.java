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

package com.liferay.staging.bar.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class LayoutSetBranchDisplayContext {

	public LayoutSetBranchDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getLayoutSetBranchDisplayName(
		LayoutSetBranch layoutSetBranch) {

		return getLayoutSetBranchDisplayName(layoutSetBranch.getName());
	}

	public String getLayoutSetBranchDisplayName(String layoutSetBranchName) {
		if (_shouldTranslateLayoutSetBranchName(layoutSetBranchName)) {
			return LanguageUtil.get(_httpServletRequest, layoutSetBranchName);
		}

		return layoutSetBranchName;
	}

	private boolean _shouldTranslateLayoutSetBranchName(
		LayoutSetBranch layoutSetBranch) {

		return _shouldTranslateLayoutSetBranchName(layoutSetBranch.getName());
	}

	private boolean _shouldTranslateLayoutSetBranchName(
		String layoutSetBranchName) {

		return LayoutSetBranchConstants.MASTER_BRANCH_NAME.equals(
			layoutSetBranchName);
	}

	private final HttpServletRequest _httpServletRequest;

}