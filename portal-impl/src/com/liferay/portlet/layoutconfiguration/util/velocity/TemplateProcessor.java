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

package com.liferay.portlet.layoutconfiguration.util.velocity;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.layoutconfiguration.util.PortletRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class TemplateProcessor implements ColumnProcessor {

	public TemplateProcessor(
			HttpServletRequest request, HttpServletResponse response,
			String portletId)
		throws SystemException {

		_ajaxableRenderEnable = GetterUtil.getBoolean(
			request.getAttribute(WebKeys.PORTLET_AJAXABLE_RENDER));

		_request = request;
		_response = response;
		_portletRenderersMap =
			new TreeMap<Integer, List<PortletRenderer>>(
				new Comparator<Integer>() {

					public int compare(
						Integer renderWeight1, Integer renderWeight2) {

						return renderWeight2.compareTo(renderWeight1);
					}

				});

		if (Validator.isNotNull(portletId)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				com.liferay.portal.util.WebKeys.THEME_DISPLAY);

			_portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);
		}
	}

	public Map<Integer, List<PortletRenderer>> getPortletRenderersMap() {

		return _portletRenderersMap;
	}

	public boolean isAjaxableRenderEnable() {
		return _ajaxableRenderEnable;
	}

	public String processColumn(String columnId) throws Exception {
		return processColumn(columnId, StringPool.BLANK);
	}

	public String processColumn(String columnId, String classNames)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(columnId);

		StringBundler sb = new StringBundler(portlets.size() + 11);

		sb.append("<div class=\"");
		sb.append("portlet-dropzone");

		if (layoutTypePortlet.isCustomizable() &&
			layoutTypePortlet.isColumnDisabled(columnId)) {

			sb.append(" portlet-dropzone-disabled");
		}

		if (layoutTypePortlet.isColumnCustomizable(columnId)) {
			sb.append(" customizable");
		}

		if (portlets.isEmpty()) {
			sb.append(" empty");
		}

		if (Validator.isNotNull(classNames)) {
			sb.append(" ");
			sb.append(classNames);
		}

		sb.append("\" id=\"layout-column_");
		sb.append(columnId);
		sb.append("\">");

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			Integer columnPos = new Integer(i);
			Integer columnCount = new Integer(portlets.size());

			Integer renderWeight = portlet.getRenderWeight();

			PortletRenderer portletRenderer =
				new PortletRenderer(portlet, columnId, columnCount, columnPos);

			if (_ajaxableRenderEnable && (portlet.getRenderWeight() < 1)) {
				StringBundler renderResult = portletRenderer.renderAjax(
					_request, _response);

				sb.append(renderResult);
			}
			else {
				List<PortletRenderer> portletRenderers =
					_portletRenderersMap.get(renderWeight);

				if (portletRenderers == null) {
					portletRenderers = new ArrayList<PortletRenderer>();

					_portletRenderersMap.put(renderWeight, portletRenderers);
				}

				portletRenderers.add(portletRenderer);

				sb.append("[$TEMPLATE_PORTLET_");
				sb.append(portlet.getPortletId());
				sb.append("$]");
			}
		}

		sb.append("</div>");

		return sb.toString();
	}

	public String processMax() throws Exception {
		StringServletResponse stringServletResponse =
			new StringServletResponse(_response);

		PortletContainerUtil.render(_request, stringServletResponse, _portlet);

		return stringServletResponse.getString();
	}

	public String processPortlet(String portletId) throws Exception {
		try {
			_request.setAttribute(
				WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			StringServletResponse stringServletResponse =
				new StringServletResponse(_response);

			PortletContainerUtil.render(
				_request, stringServletResponse, _portlet);

			return stringServletResponse.getString();
		}
		finally {
			_request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
		}
	}

	private boolean _ajaxableRenderEnable;
	private Portlet _portlet;
	private Map<Integer, List<PortletRenderer>> _portletRenderersMap;
	private HttpServletRequest _request;
	private HttpServletResponse _response;

}