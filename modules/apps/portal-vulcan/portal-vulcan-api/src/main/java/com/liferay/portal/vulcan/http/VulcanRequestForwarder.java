/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.http;

import com.liferay.portal.kernel.model.User;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public interface VulcanRequestForwarder {

	public Response forward(
			HttpServletRequest httpServletRequest, Request request)
		throws Exception;

	public interface Request {

		public String getPath();

		public User getUser();

	}

	public interface Response {

		public String getContent();

		public String getContentType();

	}

}