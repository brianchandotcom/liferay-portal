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

package com.liferay.journal.content.user.tool.conversions;

import com.liferay.journal.content.web.util.UserToolEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.BaseJSPSelectableEntry;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public abstract class BaseConvertionSelectableEntry
	extends BaseJSPSelectableEntry implements UserToolEntry {

	public abstract String getExtension();

	@Override
	public String getJSPPath() {
		return _JSP_PATH;
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute("extension", getExtension());

		super.include(request, response);
	}

	@Override
	public boolean isEnabled() {
		if (!PrefsPropsUtil.getBoolean(
				PropsKeys.OPENOFFICE_SERVER_ENABLED,
				PropsValues.OPENOFFICE_SERVER_ENABLED)) {

			return false;
		}

		if (!ArrayUtil.contains(
				DocumentConversionUtil.getConversions("html"),
				getExtension())) {

			return false;
		}

		return super.isEnabled();
	}

	private static final String _JSP_PATH =
		"/META-INF/resources/conversions.jsp";

}