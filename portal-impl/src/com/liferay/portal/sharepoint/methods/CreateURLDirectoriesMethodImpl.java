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

package com.liferay.portal.sharepoint.methods;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class CreateURLDirectoriesMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		String urlDirs = sharepointRequest.getParameterValue("urldirs");

		urlDirs = urlDirs.substring(2, urlDirs.length() - 2);

		String[] urls = urlDirs.split(StringPool.SEMICOLON);

		return urls[0].substring(4);
	}

	@Override
	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<>();

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		storage.createFolder(sharepointRequest);

		elements.add(new Property("message", StringPool.BLANK));

		return elements;
	}

	private static final String _METHOD_NAME = "create url-directories";

}