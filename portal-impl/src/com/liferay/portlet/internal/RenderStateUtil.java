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

package com.liferay.portlet.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portlet.RenderParametersPool;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Neil Griffin
 */
public class RenderStateUtil {

	public static String generateJSON(
		HttpServletRequest request, ThemeDisplay themeDisplay) {

		return generateJSON(request, themeDisplay, Collections.emptyMap());
	}

	public static String generateJSON(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		Map<String, RenderData> renderDataMap) {

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		if (layoutTypePortlet != null) {
			JSONObject pageState = _getPageState(
				request, themeDisplay, layoutTypePortlet, renderDataMap);

			return pageState.toString();
		}

		return StringPool.BLANK;
	}

	private static String _createActionURL(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		LiferayPortletURL liferayPortletURL = _createLiferayPortletURL(
			request, layout, portlet, PortletRequest.ACTION_PHASE,
			MimeResponse.Copy.NONE);

		return liferayPortletURL.toString();
	}

	private static LiferayPortletURL _createLiferayPortletURL(
		HttpServletRequest request, Layout layout, Portlet portlet,
		String lifecycle, MimeResponse.Copy copy) {

		return DoPrivilegedUtil.wrap(
			new LiferayPortletURLPrivilegedAction(
				portlet.getPortletId(), lifecycle, copy, layout, portlet,
				request));
	}

	private static String _createRenderURL(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		LiferayPortletURL liferayPortletURL = _createLiferayPortletURL(
			request, layout, portlet, PortletRequest.RENDER_PHASE,
			MimeResponse.Copy.NONE);

		return liferayPortletURL.toString();
	}

	private static String _createResourceURL(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		LiferayPortletURL liferayPortletURL = _createLiferayPortletURL(
			request, layout, portlet, PortletRequest.RESOURCE_PHASE,
			MimeResponse.Copy.NONE);

		liferayPortletURL.setCacheability(ResourceURL.PAGE);

		String resourceURL = liferayPortletURL.toString();

		resourceURL = resourceURL.replaceAll(
			"[&]p_p_mode=[a-zA-Z]+", StringPool.BLANK);

		resourceURL = resourceURL.replaceAll(
			"[&]p_p_state=[a-zA-Z]+", StringPool.BLANK);

		resourceURL = resourceURL.replaceAll(
			"[&]p_p_cacheability=cacheLevelPage", StringPool.BLANK);

		return resourceURL;
	}

	private static JSONArray _getAllowedPortletModes(Portlet portlet) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Set<String> allPortletModes = portlet.getAllPortletModes();

		for (String portletMode : allPortletModes) {
			jsonArray.put(portletMode);
		}

		return jsonArray;
	}

	private static JSONArray _getAllowedWindowStates(Portlet portlet) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Set<String> allWindowStates = portlet.getAllWindowStates();

		for (String windowState : allWindowStates) {
			jsonArray.put(windowState);
		}

		return jsonArray;
	}

	private static Map<String, String[]> _getChangedPublicRenderParameters(
		HttpServletRequest request, long plid, List<Portlet> portlets) {

		Map<String, String[]> publicRenderParameterMap = new HashMap<>();

		for (Portlet portlet : portlets) {
			Map<String, String[]> privateRenderParameterMap =
				RenderParametersPool.get(request, plid, portlet.getPortletId());

			if (privateRenderParameterMap != null) {
				Set<PublicRenderParameter> publicRenderParameters =
					portlet.getPublicRenderParameters();

				for (PublicRenderParameter publicRenderParameter :
						publicRenderParameters) {

					if (privateRenderParameterMap.containsKey(
							publicRenderParameter.getIdentifier())) {

						publicRenderParameterMap.put(
							publicRenderParameter.getIdentifier(),
							privateRenderParameterMap.get(
								publicRenderParameter.getIdentifier()));
					}
				}
			}
		}

		return publicRenderParameterMap;
	}

	private static JSONObject _getPageState(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		LayoutTypePortlet layoutTypePortlet,
		Map<String, RenderData> renderDataMap) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		jsonObject.put(
			"portlets",
			_getPortlets(
				request, themeDisplay, layoutTypePortlet, portlets,
				renderDataMap));

		jsonObject.put(
			"encodedCurrentURL",
			URLCodec.encodeURL(PortalUtil.getCurrentCompleteURL(request)));

		jsonObject.put("prpMap", _getPRPGroups(portlets));

		return jsonObject;
	}

	private static JSONObject _getPortlet(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		LayoutTypePortlet layoutTypePortlet, Portlet portlet,
		RenderData renderData,
		Map<String, String[]> changedPublicRenderParameters) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("allowedPM", _getAllowedPortletModes(portlet));
		jsonObject.put("allowedWS", _getAllowedWindowStates(portlet));
		jsonObject.put("pubParms", _getPortletPRP(portlet));
		jsonObject.put("renderData", _getRenderData(renderData));
		jsonObject.put(
			"state",
			_getPortletState(
				request, themeDisplay, layoutTypePortlet, portlet,
				changedPublicRenderParameters));

		jsonObject.put(
			"encodedActionURL",
			URLCodec.encodeURL(
				_createActionURL(request, themeDisplay.getLayout(), portlet)));

		jsonObject.put(
			"encodedRenderURL",
			URLCodec.encodeURL(
				_createRenderURL(request, themeDisplay.getLayout(), portlet)));

		jsonObject.put(
			"encodedResourceURL",
			URLCodec.encodeURL(
				_createResourceURL(
					request, themeDisplay.getLayout(), portlet)));

		return jsonObject;
	}

	private static PortletMode _getPortletMode(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		if (layoutTypePortlet.hasModeAboutPortletId(portletId)) {
			return LiferayPortletMode.ABOUT;
		}

		if (layoutTypePortlet.hasModeConfigPortletId(portletId)) {
			return LiferayPortletMode.CONFIG;
		}

		if (layoutTypePortlet.hasModeEditDefaultsPortletId(portletId)) {
			return LiferayPortletMode.EDIT_DEFAULTS;
		}

		if (layoutTypePortlet.hasModeEditGuestPortletId(portletId)) {
			return LiferayPortletMode.EDIT_GUEST;
		}

		if (layoutTypePortlet.hasModeEditPortletId(portletId)) {
			return LiferayPortletMode.EDIT;
		}

		if (layoutTypePortlet.hasModeHelpPortletId(portletId)) {
			return LiferayPortletMode.HELP;
		}

		if (layoutTypePortlet.hasModePreviewPortletId(portletId)) {
			return LiferayPortletMode.PREVIEW;
		}

		if (layoutTypePortlet.hasModePrintPortletId(portletId)) {
			return LiferayPortletMode.PRINT;
		}

		String customPortletMode =
			layoutTypePortlet.getAddedCustomPortletMode();

		if (customPortletMode != null) {
			return new PortletMode(customPortletMode);
		}

		return LiferayPortletMode.VIEW;
	}

	private static JSONObject _getPortletParameters(
		HttpServletRequest request, long plid, Portlet portlet,
		Map<String, String[]> changedPublicRenderParameters) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, String[]> privateRenderParameters =
			RenderParametersPool.get(request, plid, portlet.getPortletId());

		if (privateRenderParameters != null) {
			for (Map.Entry<String, String[]> entry :
					privateRenderParameters.entrySet()) {

				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}

		Set<PublicRenderParameter> publicRenderParameters =
			portlet.getPublicRenderParameters();

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			if (changedPublicRenderParameters.containsKey(
					publicRenderParameter.getIdentifier())) {

				jsonObject.put(
					publicRenderParameter.getIdentifier(),
					changedPublicRenderParameters.get(
						publicRenderParameter.getIdentifier()));
			}
		}

		return jsonObject;
	}

	private static JSONObject _getPortletPRP(Portlet portlet) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Set<PublicRenderParameter> publicRenderParameters =
			portlet.getPublicRenderParameters();

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			jsonObject.put(
				publicRenderParameter.getIdentifier(),
				PortletQNameUtil.getPublicRenderParameterName(
					publicRenderParameter.getQName()));
		}

		return jsonObject;
	}

	private static JSONObject _getPortlets(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		LayoutTypePortlet layoutTypePortlet, List<Portlet> portlets,
		Map<String, RenderData> renderDataMap) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, String[]> changedPublicRenderParameters =
			_getChangedPublicRenderParameters(
				request, themeDisplay.getPlid(), portlets);

		for (Portlet portlet : portlets) {
			String portletNamespace = PortalUtil.getPortletNamespace(
				portlet.getPortletId());

			jsonObject.put(
				portletNamespace,
				_getPortlet(
					request, themeDisplay, layoutTypePortlet, portlet,
					renderDataMap.get(portlet.getPortletId()),
					changedPublicRenderParameters));
		}

		return jsonObject;
	}

	private static JSONObject _getPortletState(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		LayoutTypePortlet layoutTypePortlet, Portlet portlet,
		Map<String, String[]> changedPublicRenderParameters) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"parameters",
			_getPortletParameters(
				request, themeDisplay.getPlid(), portlet,
				changedPublicRenderParameters));
		jsonObject.put(
			"portletMode",
			_getPortletMode(layoutTypePortlet, portlet.getPortletId()));
		jsonObject.put(
			"windowState",
			_getWindowState(layoutTypePortlet, portlet.getPortletId()));

		return jsonObject;
	}

	private static JSONObject _getPRPGroups(List<Portlet> portlets) {
		Map<String, PRPGroup> map = new LinkedHashMap<>();

		for (Portlet portlet : portlets) {
			Set<PublicRenderParameter> publicRenderParameters =
				portlet.getPublicRenderParameters();

			for (PublicRenderParameter publicRenderParameter :
					publicRenderParameters) {

				String publicRenderParameterName =
					PortletQNameUtil.getPublicRenderParameterName(
						publicRenderParameter.getQName());

				PRPGroup prpGroup = map.get(publicRenderParameterName);

				if (prpGroup == null) {
					prpGroup = new PRPGroup(
						publicRenderParameter.getIdentifier(), new HashSet<>());
				}

				Set<String> portletIds = prpGroup.getPortletIds();

				portletIds.add(
					PortalUtil.getPortletNamespace(portlet.getPortletId()));

				map.put(publicRenderParameterName, prpGroup);
			}
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, PRPGroup> entry : map.entrySet()) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			PRPGroup prpGroup = entry.getValue();

			for (String portletId : prpGroup.getPortletIds()) {
				StringBundler sb = new StringBundler(3);

				sb.append(portletId);
				sb.append(StringPool.PIPE);
				sb.append(prpGroup.getIdentifier());

				jsonArray.put(sb.toString());
			}

			jsonObject.put(entry.getKey(), jsonArray);
		}

		return jsonObject;
	}

	private static JSONObject _getRenderData(RenderData renderData) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (renderData == null) {
			jsonObject.put("content", StringPool.BLANK);
			jsonObject.put("mimeType", StringPool.BLANK);
		}
		else {
			jsonObject.put("content", renderData.getContent());
			jsonObject.put("mimeType", renderData.getContentType());
		}

		return jsonObject;
	}

	private static WindowState _getWindowState(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		if (layoutTypePortlet.hasStateMaxPortletId(portletId)) {
			return WindowState.MAXIMIZED;
		}

		if (layoutTypePortlet.hasStateMinPortletId(portletId)) {
			return WindowState.MINIMIZED;
		}

		return WindowState.NORMAL;
	}

	private static class PRPGroup {

		public String getIdentifier() {
			return _identifier;
		}

		public Set<String> getPortletIds() {
			return _portletIds;
		}

		private PRPGroup(String identifier, Set<String> portletIds) {
			_identifier = identifier;
			_portletIds = portletIds;
		}

		private final String _identifier;
		private final Set<String> _portletIds;

	}

}