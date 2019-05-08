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

import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletJSONUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.layoutconfiguration.util.PortletRenderer;

import java.util.ArrayList;
import java.util.Collection;
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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String portletId) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		if (Validator.isNotNull(portletId)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);
		}
		else {
			_portlet = null;
		}

		_portletAjaxRender = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(WebKeys.PORTLET_AJAX_RENDER));

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		return _processColumn(
			columnId, classNames, layoutTypePortlet,
			layoutTypePortlet.getAllPortlets(columnId));
	}

	@Override
	public String processDynamicColumn(String columnId, String classNames)
		throws Exception {

		List<Portlet> portlets = new ArrayList<>();

		String portletId = ParamUtil.getString(_httpServletRequest, "p_p_id");

		try {
			portlets.add(PortletLocalServiceUtil.getPortletById(portletId));
		}
		catch (NullPointerException npe) {
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _processColumn(
			columnId, classNames, themeDisplay.getLayoutTypePortlet(),
			portlets);
	}

	@Override
	public String processMax() throws Exception {
		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(_httpServletResponse);

		PortletContainerUtil.renderHeaders(
			_httpServletRequest, bufferCacheServletResponse, _portlet);

		PortletContainerUtil.render(
			_httpServletRequest, bufferCacheServletResponse, _portlet);

		return bufferCacheServletResponse.getString();
	}

	@Override
	public String processPortlet(String portletId) throws Exception {
		return processPortlet(portletId, (Map<String, ?>)null);
	}

	@Override
	public String processPortlet(
			String portletId, Map<String, ?> defaultSettingsMap)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		if (layout.isTypePortlet()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (!layoutTypePortlet.hasPortletId(portletId, true) &&
				!layout.isPortletEmbedded(portletId, layout.getGroupId())) {

				String defaultPreferences = portlet.getDefaultPreferences();

				Settings currentSettings = SettingsFactoryUtil.getSettings(
					new PortletInstanceSettingsLocator(layout, portletId));

				ModifiableSettings currentModifiableSettings =
					currentSettings.getModifiableSettings();

				Collection<String> currentModifiableSettingsKeys =
					currentModifiableSettings.getModifiedKeys();

				if (!currentModifiableSettingsKeys.isEmpty()) {
					StringBundler sb = new StringBundler();

					sb.append("<portlet-preferences>");

					for (String key : currentModifiableSettingsKeys) {
						String[] values = currentModifiableSettings.getValues(
							key, null);

						if (values == null) {
							continue;
						}

						sb.append("<preference><name>");
						sb.append(key);
						sb.append("</name>");

						for (String value : values) {
							sb.append("<value>");
							sb.append(XMLUtil.toCompactSafe(value));
							sb.append("</value>");
						}

						sb.append("</preference>");
					}

					sb.append("</portlet-preferences>");

					defaultPreferences = sb.toString();
				}

				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout.getCompanyId(), layout.getGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					portletId, defaultPreferences);
			}
		}

		if (defaultSettingsMap != null) {
			Settings settings = SettingsFactoryUtil.getSettings(
				new PortletInstanceSettingsLocator(layout, portletId));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			boolean modified = false;

			for (Map.Entry<String, ?> entry : defaultSettingsMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (value instanceof String) {
					Object storedValue = modifiableSettings.getValue(key, null);

					if (storedValue == null) {
						modifiableSettings.setValue(key, (String)value);

						modified = true;
					}
				}
				else if (value instanceof String[]) {
					Object[] storedValues = modifiableSettings.getValues(
						key, null);

					if (storedValues == null) {
						modifiableSettings.setValues(key, (String[])value);

						modified = true;
					}
				}
				else {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"Key ", key, " has unsupported value of type ",
							ClassUtil.getClassName(value.getClass())));
				}
			}

			if (modified) {
				modifiableSettings.store();
			}
		}

		_httpServletRequest.setAttribute(
			WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(_httpServletResponse);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		PortletJSONUtil.populatePortletJSONObject(
			_httpServletRequest, StringPool.BLANK, portlet, jsonObject);

		try {
			PortletJSONUtil.writeHeaderPaths(_httpServletResponse, jsonObject);

			HttpServletRequest httpServletRequest =
				PortletContainerUtil.setupOptionalRenderParameters(
					_httpServletRequest, null, null, null, null);

			PortletContainerUtil.render(
				httpServletRequest, bufferCacheServletResponse, portlet);

			PortletJSONUtil.writeFooterPaths(_httpServletResponse, jsonObject);

			return bufferCacheServletResponse.getString();
		}
		finally {
			_httpServletRequest.removeAttribute(
				WebKeys.RENDER_PORTLET_RESOURCE);
		}
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

	private String _processColumn(
			String columnId, String classNames,
			LayoutTypePortlet layoutTypePortlet, List<Portlet> portlets)
		throws PortletContainerException {

		StringBundler sb = new StringBundler(portlets.size() * 3 + 11);

		sb.append("<div class=\"");

		if (layoutTypePortlet.isColumnCustomizable(columnId)) {
			sb.append("customizable ");
		}

		if (portlets.isEmpty()) {
			sb.append("empty ");
		}

		sb.append("portlet-dropzone ");

		if (layoutTypePortlet.isColumnDisabled(columnId) &&
			layoutTypePortlet.isCustomizable()) {

			sb.append("portlet-dropzone-disabled");
		}

		if (Validator.isNotNull(classNames)) {
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
				StringBundler renderResultSB = portletRenderer.renderAjax(
					_httpServletRequest, _httpServletResponse);

				sb.append(renderResultSB);
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

	private static final RenderWeightComparator _renderWeightComparator =
		new RenderWeightComparator();

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Portlet _portlet;
	private final boolean _portletAjaxRender;
	private final Map<Integer, List<PortletRenderer>> _portletRenderers;

	private static class RenderWeightComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer renderWeight1, Integer renderWeight2) {
			return renderWeight2.intValue() - renderWeight1.intValue();
		}

	}

}