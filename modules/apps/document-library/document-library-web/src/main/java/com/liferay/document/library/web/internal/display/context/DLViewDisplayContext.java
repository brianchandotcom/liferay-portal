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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLViewDisplayContext {

	public DLViewDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public boolean isFileEntryMetadataSetsNavigation() {
		if (Objects.equals(_getNavigation(), "file_entry_metadata_sets")) {
			return true;
		}

		return false;
	}

	public boolean isFileEntryTypesNavigation() {
		if (Objects.equals(_getNavigation(), "file_entry_types")) {
			return true;
		}

		return false;
	}

	private String _getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_httpServletRequest, "navigation");

		return _navigation;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _navigation;

}