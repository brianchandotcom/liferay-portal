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

package com.liferay.portal.layoutconfiguration.util.xml;

import com.liferay.portal.kernel.layoutconfiguration.util.xml.RuntimeLogic;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionURLLogic extends RuntimeLogic {

	public static final String CLOSE_1_TAG = "</runtime-action-url>";

	public static final String CLOSE_2_TAG = "/>";

	public static final String OPEN_TAG = "<runtime-action-url";

	public ActionURLLogic(RenderResponse renderResponse) {
		_liferayRenderResponse = (LiferayRenderResponse)renderResponse;
	}

	@Override
	public String getClose1Tag() {
		return CLOSE_1_TAG;
	}

	public String getLifecycle() {
		return _LIFECYCLE;
	}

	@Override
	public String getOpenTag() {
		return OPEN_TAG;
	}

	@Override
	public String processXML(String xml) throws Exception {
		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		LiferayPortletURL liferayPortletURL =
			_liferayRenderResponse.createLiferayPortletURL(getLifecycle());

		String portletId = root.attributeValue("portlet-name");

		if (portletId != null) {
			portletId = PortalUtil.getJsSafePortletId(portletId);

			liferayPortletURL.setPortletId(portletId);
		}

		for (int i = 1;; i++) {
			String paramName = root.attributeValue("param-name-" + i);
			String paramValue = root.attributeValue("param-value-" + i);

			if ((paramName == null) || (paramValue == null)) {
				break;
			}

			liferayPortletURL.setParameter(paramName, paramValue);
		}

		return liferayPortletURL.toString();
	}

	private static final String _LIFECYCLE = PortletRequest.ACTION_PHASE;

	private final LiferayRenderResponse _liferayRenderResponse;

}