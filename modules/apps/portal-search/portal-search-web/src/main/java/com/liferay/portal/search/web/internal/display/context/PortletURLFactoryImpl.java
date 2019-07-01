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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.portlet.shared.search.NullPortletURL;

import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author André de Oliveira
 */
public class PortletURLFactoryImpl implements PortletURLFactory {

	public PortletURLFactoryImpl(
		PortletRequest portletRequest, MimeResponse mimeResponse) {

		_portletRequest = portletRequest;
	}

	@Override
	public PortletURL getPortletURL() throws PortletException {
		return new NullPortletURL() {

			@Override
			public void setParameter(String name, String value) {
				String portalURL = PortalUtil.getPortalURL(
					PortalUtil.getHttpServletRequest(_portletRequest));
				String currentURL = (String)_portletRequest.getAttribute(
					WebKeys.CURRENT_URL);

				_url = portalURL.concat(currentURL);

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					_url);

				String[] values = parameterMap.get(name);

				if (!ArrayUtil.contains(values, value)) {
					_url = HttpUtil.addParameter(_url, name, value);
				}
			}

			@Override
			public String toString() {
				return _url;
			}

			private String _url;

		};
	}

	private final PortletRequest _portletRequest;

}