/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upload;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;
public class LiferayFileUploadUtil {

	public static LiferayFileUploadException getFileUploadException(
		ActionRequest actionRequest) {

		return (LiferayFileUploadException) actionRequest.getAttribute(
			FILE_UPLOAD_EXCEPTION_ATTR_NAME);
	}

	public static void storeFileUploadException(
		HttpServletRequest request, Exception exception) {

		LiferayFileUploadException liferayFileUploadException =
			new LiferayFileUploadException(exception);

		request.setAttribute(FILE_UPLOAD_EXCEPTION_ATTR_NAME,
			liferayFileUploadException);
	}

	private static final String FILE_UPLOAD_EXCEPTION_ATTR_NAME =
		LiferayFileUploadUtil.class.getName() + ".upload.exception";

}