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

package com.liferay.portlet.configuration.css.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletSetupUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationCSSPortletDisplayContext {

	public PortletConfigurationCSSPortletDisplayContext(
			RenderRequest renderRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(
			renderRequest, "portletResource");

		PortletPreferences portletSetup =
			themeDisplay.getStrictLayoutPortletSetup(
				themeDisplay.getLayout(), portletResource);

		JSONObject portletSetupJSONObject = PortletSetupUtil.cssToJSONObject(
			portletSetup);

		_renderRequest = renderRequest;
		_portletResource = portletResource;
		_portletSetup = portletSetup;
		_portletSetupJSONObject = portletSetupJSONObject;
	}

	public String getBackgroundColor() {
		JSONObject bgDataJSONObject = _portletSetupJSONObject.getJSONObject(
			"bgData");

		if (bgDataJSONObject == null) {
			return StringPool.BLANK;
		}

		return bgDataJSONObject.getString("backgroundColor");
	}

	public String getBorderProperty(String position, String property) {
		JSONObject borderDataJSONObject = _portletSetupJSONObject.getJSONObject(
			"borderData");

		if (borderDataJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject borderPropertySONObject = borderDataJSONObject.getJSONObject(
			property);

		return borderPropertySONObject.getString(position);
	}

	public String getBorderWidthProperty(String position, String property) {
		JSONObject borderDataJSONObject = _portletSetupJSONObject.getJSONObject(
			"borderData");

		if (borderDataJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject borderWidthSONObject = borderDataJSONObject.getJSONObject(
			"borderWidth");

		JSONObject borderWidthPositionJSONObject =
			borderWidthSONObject.getJSONObject(position);

		return borderWidthPositionJSONObject.getString(property);
	}

	public String getCustomCSS() {
		JSONObject advancedDataJSONObject =
			_portletSetupJSONObject.getJSONObject("advancedData");

		if (advancedDataJSONObject == null) {
			return StringPool.BLANK;
		}

		return advancedDataJSONObject.getString("customCSS");
	}

	public String getCustomCSSClassName() {
		JSONObject advancedDataJSONObject =
			_portletSetupJSONObject.getJSONObject("advancedData");

		if (advancedDataJSONObject == null) {
			return StringPool.BLANK;
		}

		return advancedDataJSONObject.getString("customCSSClassName");
	}

	public String getCustomTitleXML() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		HttpSession session = httpServletRequest.getSession();

		ServletContext servletContext = session.getServletContext();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_portletResource);

		Map<Locale, String> customTitleMap = new HashMap<>();

		for (Locale curLocale :
				LanguageUtil.getAvailableLocales(
					themeDisplay.getSiteGroupId())) {

			String languageId = LocaleUtil.toLanguageId(curLocale);

			String portletTitle = PortalUtil.getPortletTitle(
				portlet, servletContext, curLocale);

			String portletSetupTitle = _portletSetup.getValue(
				"portletSetupTitle_" + languageId, portletTitle);

			customTitleMap.put(curLocale, portletSetupTitle);
		}

		return LocalizationUtil.updateLocalization(
			customTitleMap, StringPool.BLANK, "customTitle",
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
	}

	public DecimalFormat getDecimalFormat() {
		if (_decimalFormat != null) {
			return _decimalFormat;
		}

		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

		decimalFormatSymbols.setDecimalSeparator('.');

		_decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

		return _decimalFormat;
	}

	public String getMarginProperty(String position, String property) {
		JSONObject spacingDataJSONObject =
			_portletSetupJSONObject.getJSONObject("spacingData");

		if (spacingDataJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject marginSONObject = spacingDataJSONObject.getJSONObject(
			"margin");

		JSONObject marginPositionJSONObject = marginSONObject.getJSONObject(
			position);

		return marginPositionJSONObject.getString(property);
	}

	public String getPaddingProperty(String position, String property) {
		JSONObject spacingDataJSONObject =
			_portletSetupJSONObject.getJSONObject("spacingData");

		if (spacingDataJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject paddingSONObject = spacingDataJSONObject.getJSONObject(
			"padding");

		JSONObject paddingPositionJSONObject = paddingSONObject.getJSONObject(
			position);

		return paddingPositionJSONObject.getString(property);
	}

	public String getPortletDecoratorId() {
		if (_portletDecoratorId != null) {
			return _portletDecoratorId;
		}

		_portletDecoratorId = _portletSetup.getValue(
			"portletSetupPortletDecoratorId", _getDefaultDecoratorId());

		return _portletDecoratorId;
	}

	public String getPortletResource() {
		return _portletResource;
	}

	public String getTextDataProperty(String property) {
		JSONObject textDataJSONObject = _portletSetupJSONObject.getJSONObject(
			"textData");

		if (textDataJSONObject == null) {
			return StringPool.BLANK;
		}

		return textDataJSONObject.getString(property);
	}

	public boolean hasAccess() throws PortalException {
		if (Validator.isNull(getPortletResource())) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				getPortletResource(), ActionKeys.CONFIGURATION)) {

			return false;
		}

		return true;
	}

	public boolean isBorderSameForAll(String property) {
		JSONObject borderDataJSONObject = _portletSetupJSONObject.getJSONObject(
			"borderData");

		if (borderDataJSONObject == null) {
			return false;
		}

		JSONObject borderPropertyJSONObject =
			borderDataJSONObject.getJSONObject(property);

		return borderPropertyJSONObject.getBoolean("sameForAll");
	}

	public boolean isSpacingSameForAll(String property) {
		JSONObject spacingDataJSONObject =
			_portletSetupJSONObject.getJSONObject("spacingData");

		if (spacingDataJSONObject == null) {
			return false;
		}

		JSONObject spacingPropertySONObject =
			spacingDataJSONObject.getJSONObject(property);

		return spacingPropertySONObject.getBoolean("sameForAll");
	}

	public boolean isUseCustomTitle() {
		if (_useCustomTitle != null) {
			return _useCustomTitle;
		}

		_useCustomTitle = GetterUtil.getBoolean(
			_portletSetup.getValue(
				"portletSetupUseCustomTitle", StringPool.BLANK));

		return _useCustomTitle;
	}

	private String _getDefaultDecoratorId() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();

		List<PortletDecorator> portletDecorators = theme.getPortletDecorators();

		Stream<PortletDecorator> portletDecoratorsStream =
			portletDecorators.stream();

		List<PortletDecorator> filteredPortletDecorators =
			portletDecoratorsStream.filter(
				portletDecorator -> portletDecorator.isDefaultPortletDecorator()
			).collect(
				Collectors.toList()
			);

		if (ListUtil.isEmpty(filteredPortletDecorators)) {
			return StringPool.BLANK;
		}

		PortletDecorator defaultPortletDecorator =
			filteredPortletDecorators.get(0);

		return defaultPortletDecorator.getPortletDecoratorId();
	}

	private DecimalFormat _decimalFormat;
	private String _portletDecoratorId;
	private final String _portletResource;
	private final PortletPreferences _portletSetup;
	private final JSONObject _portletSetupJSONObject;
	private final RenderRequest _renderRequest;
	private Boolean _useCustomTitle;

}