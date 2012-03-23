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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.PortletRenderWeightComparator;
import com.liferay.portlet.layoutconfiguration.util.RuntimePortletUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class TemplateProcessor implements ColumnProcessor {

	public TemplateProcessor(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, String portletId) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_portletId = portletId;
		_portletsMap = new TreeMap<Portlet, Object[]>(
			new PortletRenderWeightComparator());
	}

	public Map<Portlet, Object[]> getPortletsMap() {
		return _portletsMap;
	}

	public String processColumn(String columnId) throws Exception {
		return processColumn(columnId, StringPool.BLANK);
	}

	public String processColumn(String columnId, String classNames)
		throws Exception {

		boolean parallelRenderEnable =
			PropsValues.LAYOUT_PARALLEL_RENDER_ENABLE;

		if (parallelRenderEnable) {
			if (PropsValues.SESSION_DISABLED) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Parallel rendering should be disabled if sessions " +
							"are disabled");
				}
			}

			Boolean portletParallelRender =
				(Boolean)_request.getAttribute(
					com.liferay.portal.util.WebKeys.PORTLET_PARALLEL_RENDER);

			if (Boolean.FALSE.equals(portletParallelRender)) {

				parallelRenderEnable = false;
			}
		}
		else {
			_request.removeAttribute(
				com.liferay.portal.util.WebKeys.PORTLET_PARALLEL_RENDER);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			com.liferay.portal.util.WebKeys.THEME_DISPLAY);

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

			String queryString = null;
			Integer columnPos = new Integer(i);
			Integer columnCount = new Integer(portlets.size());
			String path = null;

			if (parallelRenderEnable) {
				path = "/html/portal/load_render_portlet.jsp";

				if (portlet.getRenderWeight() >= 1) {
					_portletsMap.put(
						portlet,
						new Object[] {
							queryString, columnId, columnPos, columnCount
						});
				}
			}

			String content = RuntimePortletUtil.processPortlet(
				_servletContext, _request, _response, portlet, queryString,
				columnId, columnPos, columnCount, path, false);

			sb.append(content);
		}

		sb.append("</div>");

		return sb.toString();
	}

	public String processMax() throws Exception {
		return processMax(StringPool.BLANK);
	}

	public String processMax(String classNames) throws Exception {
		return RuntimePortletUtil.processPortlet(
			_servletContext, _request, _response, null, null, _portletId, null,
			false);
	}

	public String processPortlet(String portletId) throws Exception {
		try {
			_request.setAttribute(
				WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			return RuntimePortletUtil.processPortlet(
				_servletContext, _request, _response, null, null, _portletId,
				null, false);
		}
		finally {
			_request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TemplateProcessor.class);

	private String _portletId;
	private Map<Portlet, Object[]> _portletsMap;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;

}