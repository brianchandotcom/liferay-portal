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
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutBranchConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class LayoutBranchDisplayContext {

	public LayoutBranchDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getLayoutBranchDisplayName(LayoutBranch layoutBranch) {
		return getLayoutBranchDisplayName(layoutBranch.getName());
	}

	public String getLayoutBranchDisplayName(String layoutBranchName) {
		if (_shouldTranslateLayoutBranchName(layoutBranchName)) {
			return LanguageUtil.get(_httpServletRequest, layoutBranchName);
		}

		return layoutBranchName;
	}

	private boolean _shouldTranslateLayoutBranchName(
		LayoutBranch layoutBranch) {

		return _shouldTranslateLayoutBranchName(layoutBranch.getName());
	}

	private boolean _shouldTranslateLayoutBranchName(String layoutBranchName) {
		return LayoutBranchConstants.MASTER_BRANCH_NAME.equals(
			layoutBranchName);
	}

	private final HttpServletRequest _httpServletRequest;

}