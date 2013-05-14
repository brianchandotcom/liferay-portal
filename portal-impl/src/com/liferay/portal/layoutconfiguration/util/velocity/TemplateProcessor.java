/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.layoutconfiguration.util.velocity;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.layoutconfiguration.util.PortletRenderer;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

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

		_request = request;
		_response = response;

		if (Validator.isNotNull(portletId)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);
		}

		_portletAjaxRender = GetterUtil.getBoolean(
			request.getAttribute(WebKeys.PORTLET_AJAX_RENDER));

		_portletRenderers = new TreeMap<Integer, List<PortletRenderer>>(
			new Comparator<Integer>() {

				public int compare(
					Integer renderWeight1, Integer renderWeight2) {

					return renderWeight2.compareTo(renderWeight1);
				}

			});
	}

	public Map<Integer, List<PortletRenderer>> getPortletRenderers() {
		return _portletRenderers;
	}

	public boolean isPortletAjaxRender() {
		return _portletAjaxRender;
	}

	@Override
	public String processColumn(String columnId) throws Exception {
		return processColumn(columnId, StringPool.BLANK);
	}

	@Override
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

			Integer columnCount = new Integer(portlets.size());
			Integer columnPos = new Integer(i);

			PortletRenderer portletRenderer = new PortletRenderer(
				portlet, columnId, columnCount, columnPos);

			if (_portletAjaxRender && (portlet.getRenderWeight() < 1)) {
				StringBundler renderResult = portletRenderer.renderAjax(
					_request, _response);

				sb.append(renderResult);
			}
			else {
				Integer renderWeight = portlet.getRenderWeight();

				List<PortletRenderer> portletRenderers = _portletRenderers.get(
					renderWeight);

				if (portletRenderers == null) {
					portletRenderers = new ArrayList<PortletRenderer>();

					_portletRenderers.put(renderWeight, portletRenderers);
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

	@Override
	public String processMax() throws Exception {
		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(_response);

		PortletContainerUtil.render(
			_request, bufferCacheServletResponse, _portlet);

		return bufferCacheServletResponse.getString();
	}

	@Override
	public String processPortlet(String portletId) throws Exception {
		_request.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(_response);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		try {
			PortletContainerUtil.render(
				_request, bufferCacheServletResponse, portlet);

			return bufferCacheServletResponse.getString();
		}
		finally {
			_request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
		}
	}

	private Portlet _portlet;
	private boolean _portletAjaxRender;
	private Map<Integer, List<PortletRenderer>> _portletRenderers;
	private HttpServletRequest _request;
	private HttpServletResponse _response;

}