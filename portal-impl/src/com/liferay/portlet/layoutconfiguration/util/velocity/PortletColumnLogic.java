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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
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
 */
public class PortletColumnLogic extends RuntimeLogic {

	public PortletColumnLogic(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_portletsMap = new TreeMap<Portlet, Object[]>(
			new PortletRenderWeightComparator());

		_parallelRenderEnable = PropsValues.LAYOUT_PARALLEL_RENDER_ENABLE;

		if (_parallelRenderEnable) {
			if (PropsValues.SESSION_DISABLED) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Parallel rendering should be disabled if sessions " +
							"are disabled");
				}
			}
		}

		if (_parallelRenderEnable) {
			Boolean portletParallelRender =
				(Boolean)request.getAttribute(WebKeys.PORTLET_PARALLEL_RENDER);

			if ((portletParallelRender != null) &&
				(portletParallelRender.booleanValue() == false)) {

				_parallelRenderEnable = false;
			}
		}
		else {
			request.removeAttribute(WebKeys.PORTLET_PARALLEL_RENDER);
		}
	}

	public Map<Portlet, Object[]> getPortletsMap() {
		return _portletsMap;
	}

	@Override
	public String processContent(Map<String, String> attributes)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			_themeDisplay.getLayoutTypePortlet();

		String columnId = attributes.get("id");

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(columnId);

		String columnCssClass = "portlet-dropzone";

		if (layoutTypePortlet.isCustomizable() &&
			layoutTypePortlet.isColumnDisabled(columnId)) {

			columnCssClass += " portlet-dropzone-disabled";
		}

		if (layoutTypePortlet.isColumnCustomizable(columnId)) {

			columnCssClass += " customizable";
		}

		if (portlets.size() == 0) {
			columnCssClass += " empty";
		}

		String additionalClassNames = attributes.get("classNames");

		if (Validator.isNotNull(additionalClassNames)) {
			columnCssClass += " " + additionalClassNames;
		}

		StringBundler sb = new StringBundler();

		sb.append("<div class=\"");
		sb.append(columnCssClass);
		sb.append("\" id=\"layout-column_");
		sb.append(columnId);
		sb.append("\">");

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			String queryString = null;
			Integer columnPos = new Integer(i);
			Integer columnCount = new Integer(portlets.size());
			String path = null;

			if (_parallelRenderEnable) {
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

	private static Log _log = LogFactoryUtil.getLog(PortletColumnLogic.class);

	private boolean _parallelRenderEnable;
	private Map<Portlet, Object[]> _portletsMap;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;
	private ThemeDisplay _themeDisplay;

}