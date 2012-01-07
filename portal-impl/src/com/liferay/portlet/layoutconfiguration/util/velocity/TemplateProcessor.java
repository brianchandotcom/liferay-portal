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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.comparator.PortletRenderWeightComparator;

import java.util.HashMap;
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

		Map<String, String> attributes = new HashMap<String, String>();

		attributes.put("id", columnId);
		attributes.put("classNames", classNames);

		PortletColumnLogic logic = new PortletColumnLogic(
			_servletContext, _request, _response);

		String content = logic.processContent(attributes);

		_portletsMap.putAll(logic.getPortletsMap());

		return content;
	}

	public String processMax() throws Exception {
		return processMax(StringPool.BLANK);
	}

	public String processMax(String classNames) throws Exception {
		Map<String, String> attributes = new HashMap<String, String>();

		attributes.put("classNames", classNames);

		RuntimeLogic logic = new PortletLogic(
			_servletContext, _request, _response, _portletId);

		return logic.processContent(attributes);
	}

	public String processPortlet(String portletId) throws Exception {
		try {
			_request.setAttribute(
				WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			RuntimeLogic logic = new PortletLogic(
				_servletContext, _request, _response, portletId);

			return logic.processContent(new HashMap<String, String>());
		}
		finally {
			_request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
		}
	}

	private ServletContext _servletContext;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private String _portletId;
	private Map<Portlet, Object[]> _portletsMap;

}