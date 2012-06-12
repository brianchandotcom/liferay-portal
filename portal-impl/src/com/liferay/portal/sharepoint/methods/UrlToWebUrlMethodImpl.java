/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class UrlToWebUrlMethodImpl extends BaseMethodImpl {

	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	protected List<ResponseElement> getElements(
		SharepointRequest sharepointRequest) {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		String url = sharepointRequest.getParameterValue("url");

		if (_log.isInfoEnabled()) {
			_log.info("URL is " + url);
		}

		if (Validator.isNotNull(url)) {
			elements.add(new Property("webUrl", "/sharepoint"));

			url = url.substring(1);

			elements.add(new Property("fileUrl", StringPool.BLANK));
		}

		return elements;
	}

	private static final String _METHOD_NAME = "url to web url";

	private static Log _log = LogFactoryUtil.getLog(
		UrlToWebUrlMethodImpl.class);

}