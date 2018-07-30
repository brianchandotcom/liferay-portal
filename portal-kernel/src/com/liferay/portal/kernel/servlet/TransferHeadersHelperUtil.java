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

package com.liferay.portal.kernel.servlet;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 */
public class TransferHeadersHelperUtil {

	public static RequestDispatcher getTransferHeadersRequestDispatcher(
		RequestDispatcher requestDispatcher) {

		return _getTransferHeadersHelper().getTransferHeadersRequestDispatcher(
			requestDispatcher);
	}

	public static void transferHeaders(
		Map<String, Object[]> headers,
		HttpServletResponse httpServletResponse) {

		_getTransferHeadersHelper().transferHeaders(
			headers, httpServletResponse);
	}

	public void setTransferHeadersHelper(
		TransferHeadersHelper transferHeadersHelper) {

		_transferHeadersHelper = transferHeadersHelper;
	}

	private static TransferHeadersHelper _getTransferHeadersHelper() {
		return _transferHeadersHelper;
	}

	private static TransferHeadersHelper _transferHeadersHelper;

}