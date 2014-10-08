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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointException;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;

import java.util.List;

/**
 * @author Bruno Farache
 */
public abstract class BaseMethodImpl implements Method {

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		return StringPool.BLANK;
	}

	@Override
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
			getResponse(sharepointRequest, false));
	}

	protected abstract List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception;

	protected String getResponse(
			SharepointRequest sharepointRequest, boolean appendNewline)
		throws Exception {

		List<ResponseElement> elements = getElements(sharepointRequest);

		StringBundler sb = new StringBundler(elements.size() + 9);

		sb.append("<html><head><title>vermeer RPC packet</title></head>");
		sb.append(StringPool.NEW_LINE);
		sb.append("<body>");
		sb.append(StringPool.NEW_LINE);

		Property property = new Property(
			"method", getMethodName() + ":" + SharepointUtil.VERSION);

		sb.append(property.parse());

		for (ResponseElement element : elements) {
			sb.append(element.parse());
		}

		sb.append("</body>");
		sb.append(StringPool.NEW_LINE);
		sb.append("</html>");

		if (appendNewline) {
			sb.append(StringPool.NEW_LINE);
		}

		String html = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Response HTML\n" + html);
		}

		return html;
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseMethodImpl.class);

}