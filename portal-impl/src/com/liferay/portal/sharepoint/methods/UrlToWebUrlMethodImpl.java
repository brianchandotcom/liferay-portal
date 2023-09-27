/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class UrlToWebUrlMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	protected List<ResponseElement> getElements(
		SharepointRequest sharepointRequest) {

		List<ResponseElement> elements = new ArrayList<>();

		String url = sharepointRequest.getParameterValue("url");

		if (Validator.isNotNull(url)) {
			if (_log.isInfoEnabled()) {
				_log.info("Original URL " + url);
			}

			url = SharepointUtil.stripService(url, false);

			if (_log.isInfoEnabled()) {
				_log.info("Modified URL " + url);
			}

			elements.add(new Property("fileUrl", url));
			elements.add(new Property("webUrl", "/sharepoint"));
		}
		else if (_log.isInfoEnabled()) {
			_log.info("URL is " + url);
		}

		return elements;
	}

	private static final String _METHOD_NAME = "url to web url";

	private static final Log _log = LogFactoryUtil.getLog(
		UrlToWebUrlMethodImpl.class);

}