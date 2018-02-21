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

package com.liferay.portal.kernel.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.servlet.TransferHeadersHelperUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseLayoutTypeControllerImpl
	implements LayoutTypeController {

	@Override
	public String[] getConfigurationActionDelete() {
		return StringPool.EMPTY_ARRAY;
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return StringPool.EMPTY_ARRAY;
	}

	@Override
	public String getType() {
		return StringPool.BLANK;
	}

	@Override
	public String includeEditContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		RequestDispatcher requestDispatcher =
			TransferHeadersHelperUtil.getTransferHeadersRequestDispatcher(
				servletContext.getRequestDispatcher(getEditPage()));

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			response, unsyncStringWriter);

		try {
			addAttributes(request);

			requestDispatcher.include(request, servletResponse);
		}
		finally {
			removeAttributes(request);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		RequestDispatcher requestDispatcher =
			TransferHeadersHelperUtil.getTransferHeadersRequestDispatcher(
				servletContext.getRequestDispatcher(getViewPage()));

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			response, unsyncStringWriter);

		String contentType = servletResponse.getContentType();

		String includeServletPath = (String)request.getAttribute(
			RequestDispatcher.INCLUDE_SERVLET_PATH);

		try {
			addAttributes(request);

			requestDispatcher.include(request, servletResponse);
		}
		finally {
			removeAttributes(request);

			request.setAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH, includeServletPath);
		}

		if (contentType != null) {
			response.setContentType(contentType);
		}

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());

		return false;
	}

	@Override
	public boolean isBrowsable() {
		return true;
	}

	@Override
	public boolean isCheckLayoutViewPermission() {
		return true;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return false;
	}

	@Override
	public boolean isInstanceable() {
		return true;
	}

	@Override
	public boolean matches(
		HttpServletRequest request, String friendlyURL, Layout layout) {

		try {
			Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

			Collection<String> values = friendlyURLMap.values();

			return values.contains(friendlyURL);
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}
	}

	protected void addAttributes(HttpServletRequest request) {
	}

	protected abstract ServletResponse createServletResponse(
		HttpServletResponse response, UnsyncStringWriter unsyncStringWriter);

	protected abstract String getEditPage();

	protected abstract String getViewPage();

	protected void removeAttributes(HttpServletRequest request) {
	}

	protected ServletContext servletContext;

}