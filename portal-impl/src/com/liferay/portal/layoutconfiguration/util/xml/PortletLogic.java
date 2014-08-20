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

import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletParameterUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PrefixPredicateFilter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 */
public class PortletLogic extends RuntimeLogic {

	public static final String CLOSE_1_TAG = "</runtime-portlet>";

	public static final String CLOSE_2_TAG = "/>";

	public static final String OPEN_TAG = "<runtime-portlet";

	public PortletLogic(
		HttpServletRequest request, HttpServletResponse response) {

		_request = request;
		_response = response;
	}

	@Override
	public String getClose1Tag() {
		return CLOSE_1_TAG;
	}

	@Override
	public String getOpenTag() {
		return OPEN_TAG;
	}

	@Override
	public String processXML(String xml) throws Exception {
		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		String portletId = rootElement.attributeValue("name");
		String instanceId = rootElement.attributeValue("instance");
		String queryString = rootElement.attributeValue("queryString");

		if (Validator.isNotNull(instanceId)) {
			portletId = PortletConstants.assemblePortletId(
				portletId, instanceId);
		}

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(_response);

		queryString = PortletParameterUtil.addNamespace(portletId, queryString);

		Map<String, String[]> parameterMap = _request.getParameterMap();

		if (!portletId.equals(_request.getParameter("p_p_id"))) {
			parameterMap = MapUtil.filter(
				parameterMap, new HashMap<String, String[]>(),
				new PrefixPredicateFilter("p_p_"));
		}

		HttpServletRequest request = DynamicServletRequest.addQueryString(
			_request, parameterMap, queryString, false);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = getPortlet(themeDisplay, portletId);

		PortletContainerUtil.render(
			request, bufferCacheServletResponse, portlet);

		return bufferCacheServletResponse.getString();
	}

	/**
	 * @see com.liferay.portal.model.impl.LayoutTypePortletImpl#getStaticPortlets(
	 *      String)
	 */
	protected Portlet getPortlet(ThemeDisplay themeDisplay, String portletId)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		// See LayoutTypePortletImpl#getStaticPortlets for why we only clone
		// non-instanceable portlets

		if (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
				portletId) < 1) {

			PortletPreferencesFactoryUtil.getPortletSetup(_request, portletId);

			PortletLayoutListener portletLayoutListener =
				portlet.getPortletLayoutListenerInstance();

			if (portletLayoutListener != null) {
				portletLayoutListener.onAddToLayout(
					portletId, themeDisplay.getPlid());
			}
		}

		if (!portlet.isInstanceable()) {
			portlet = (Portlet)portlet.clone();
		}

		portlet.setStatic(true);

		return portlet;
	}

	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

}