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

package com.liferay.portal.layoutconfiguration.util.velocity;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletJSONUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.layoutconfiguration.util.PortletRenderer;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

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
 * @author Oliver Teichmann
 */
public class TemplateProcessor implements ColumnProcessor {

	public TemplateProcessor(
		HttpServletRequest request, HttpServletResponse response,
		String portletId) {

		_request = request;
		_response = response;

		if (Validator.isNotNull(portletId)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);
		}
		else {
			_portlet = null;
		}

		_portletAjaxRender = GetterUtil.getBoolean(
			request.getAttribute(WebKeys.PORTLET_AJAX_RENDER));

		_portletRenderers = new TreeMap<>(_renderWeightComparator);
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

		StringBundler sb = new StringBundler(portlets.size() * 3 + 11);

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

			Integer columnCount = Integer.valueOf(portlets.size());
			Integer columnPos = Integer.valueOf(i);

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
					portletRenderers = new ArrayList<>();

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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #processMax()}
	 */
	@Deprecated
	@Override
	public String processMax(String classNames) throws Exception {
		return processMax();
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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		PortletJSONUtil.populatePortletJSONObject(
			_request, StringPool.BLANK, portlet, jsonObject);

		try {
			PortletJSONUtil.writeHeaderPaths(_response, jsonObject);

			PortletContainerUtil.render(
				_request, bufferCacheServletResponse, portlet);

			PortletJSONUtil.writeFooterPaths(_response, jsonObject);

			return bufferCacheServletResponse.getString();
		}
		finally {
			_request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
		}
	}

	@Override
	public String processPortlet(
			String portletId, Map<String, ?> defaultSettingsMap)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Settings settings = SettingsFactoryUtil.getSettings(
			new PortletInstanceSettingsLocator(
				themeDisplay.getLayout(), portletId));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, ?> entry : defaultSettingsMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof String) {
				modifiableSettings.setValue(key, (String)value);
			}
			else if (value instanceof String[]) {
				modifiableSettings.setValues(key, (String[])value);
			}
			else {
				throw new IllegalArgumentException(
					"Key " + key + " has unsupported value of type " +
						ClassUtil.getClassName(value.getClass()));
			}
		}

		modifiableSettings.store();

		return processPortlet(portletId);
	}

	@Override
	public String processPortlet(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction)
		throws Exception {

		String portletId = PortletProviderUtil.getPortletId(
			portletProviderClassName, portletProviderAction);

		return processPortlet(portletId);
	}

	private static final RenderWeightComparator _renderWeightComparator =
		new RenderWeightComparator();

	private final Portlet _portlet;
	private final boolean _portletAjaxRender;
	private final Map<Integer, List<PortletRenderer>> _portletRenderers;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

	private static class RenderWeightComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer renderWeight1, Integer renderWeight2) {
			return renderWeight2.intValue() - renderWeight1.intValue();
		}

	}

}