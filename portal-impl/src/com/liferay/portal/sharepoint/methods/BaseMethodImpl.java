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

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointException;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;

import java.util.List;

/**
 * @author Bruno Farache
 */
public abstract class BaseMethodImpl implements Method {

	public String getRootPath(SharepointRequest sharepointRequest) {
		return StringPool.BLANK;
	}

	public void process(SharepointRequest sharepointRequest)
		throws SharepointException {

		try {
			doProcess(sharepointRequest);
		}
		catch (Exception e) {
			throw new SharepointException(e);
		}
	}

	protected void doProcess(SharepointRequest sharepointRequest)
		throws Exception {

		ServletResponseUtil.write(
			sharepointRequest.getHttpServletResponse(),
			getResponseBuffer(sharepointRequest).toString());
	}

	protected abstract List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception;

	protected StringBuilder getResponseBuffer(
			SharepointRequest sharepointRequest)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		SharepointUtil.addTop(sb, getMethodName());

		List<ResponseElement> elements = getElements(sharepointRequest);

		for (ResponseElement element : elements) {
			sb.append(element.parse());
		}

		SharepointUtil.addBottom(sb);

		return sb;
	}

}