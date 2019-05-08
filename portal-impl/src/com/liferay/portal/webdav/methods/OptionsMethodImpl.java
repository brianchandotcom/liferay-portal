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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.methods.Method;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class OptionsMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) {
		HttpServletResponse httpServletResponse =
			webDAVRequest.getHttpServletResponse();

		WebDAVStorage webDAVStorage = webDAVRequest.getWebDAVStorage();

		if (webDAVStorage.isSupportsClassTwo()) {
			httpServletResponse.addHeader("DAV", "1,2");
		}
		else {
			httpServletResponse.addHeader("DAV", "1");
		}

		httpServletResponse.addHeader(
			"Allow", StringUtil.merge(Method.SUPPORTED_METHOD_NAMES));
		httpServletResponse.addHeader("MS-Author-Via", "DAV");

		return HttpServletResponse.SC_OK;
	}

}