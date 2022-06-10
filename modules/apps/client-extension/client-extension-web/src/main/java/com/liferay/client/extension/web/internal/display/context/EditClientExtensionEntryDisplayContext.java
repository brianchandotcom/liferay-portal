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

package com.liferay.client.extension.web.internal.display.context;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.CETGlobalCSS;
import com.liferay.client.extension.type.CETGlobalJS;
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.CETThemeJS;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.WebAppPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class EditClientExtensionEntryDisplayContext {

	public EditClientExtensionEntryDisplayContext(
			CETFactory cetFactory, ClientExtensionEntry clientExtensionEntry,
			PortletRequest portletRequest)
		throws PortalException {

		if (clientExtensionEntry == null) {
			_cet = cetFactory.cet(
				portletRequest,
				ParamUtil.getString(
					portletRequest, "type",
					ClientExtensionEntryConstants.TYPE_IFRAME));
		}
		else {
			_cet = cetFactory.cet(clientExtensionEntry);
		}

		_clientExtensionEntry = clientExtensionEntry;
		_portletRequest = portletRequest;
	}

	public String getCmd() {
		if (_clientExtensionEntry == null) {
			return Constants.ADD;
		}

		return Constants.UPDATE;
	}

	public String[] getCustomElementCSSURLs() throws PortalException {
		String[] customElementCSSURLs = StringPool.EMPTY_ARRAY;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			String cssURLsString = cetCustomElement.getCSSURLs();

			customElementCSSURLs = cssURLsString.split(StringPool.NEW_LINE);
		}

		customElementCSSURLs = ParamUtil.getStringValues(
			_portletRequest, "customElementCSSURLs", customElementCSSURLs);

		if (customElementCSSURLs.length == 0) {
			customElementCSSURLs = new String[1];
		}

		return customElementCSSURLs;
	}

	public String getCustomElementHTMLName() throws PortalException {
		String htmlElementName = StringPool.BLANK;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			htmlElementName = cetCustomElement.getHTMLElementName();
		}

		return ParamUtil.getString(
			_getHttpServletRequest(), "customElementHTMLName", htmlElementName);
	}

	public String[] getCustomElementURLs() throws PortalException {
		String[] customElementURLs = StringPool.EMPTY_ARRAY;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			String urlsString = cetCustomElement.getURLs();

			customElementURLs = urlsString.split(StringPool.NEW_LINE);
		}

		customElementURLs = ParamUtil.getStringValues(
			_portletRequest, "customElementURLs", customElementURLs);

		if (customElementURLs.length == 0) {
			customElementURLs = new String[1];
		}

		return customElementURLs;
	}

	public String getDescription() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "description");
	}

	public String getExternalReferenceCode() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "externalReferenceCode");
	}

	public String getFriendlyURLMapping() throws PortalException {
		String friendlyURLMapping = StringPool.BLANK;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			friendlyURLMapping = cetCustomElement.getFriendlyURLMapping();
		}
		else if (_cet instanceof CETIFrame) {
			CETIFrame cetiFrame = (CETIFrame)_cet;

			friendlyURLMapping = cetiFrame.getFriendlyURLMapping();
		}

		return friendlyURLMapping;
	}

	public String getGlobalCSSURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETGlobalCSS) {
			CETGlobalCSS cetGlobalCSS = (CETGlobalCSS)_cet;

			url = cetGlobalCSS.getURL();
		}

		return ParamUtil.getString(_portletRequest, "globalCSSURL", url);
	}

	public String getGlobalJSURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETGlobalJS) {
			CETGlobalJS cetGlobalJS = (CETGlobalJS)_cet;

			url = cetGlobalJS.getURL();
		}

		return ParamUtil.getString(_portletRequest, "globalJSURL", url);
	}

	public String getIFrameURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETIFrame) {
			CETIFrame cetiFrame = (CETIFrame)_cet;

			url = cetiFrame.getURL();
		}

		return ParamUtil.getString(_portletRequest, "iFrameURL", url);
	}

	public String getName() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "name");
	}

	public String getPortletCategoryName() throws PortalException {
		String portletCategoryName = StringPool.BLANK;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			portletCategoryName = cetCustomElement.getPortletCategoryName();
		}
		else if (_cet instanceof CETIFrame) {
			CETIFrame cetiFrame = (CETIFrame)_cet;

			portletCategoryName = cetiFrame.getPortletCategoryName();
		}

		if (Validator.isNull(portletCategoryName)) {
			portletCategoryName = "category.remote-apps";
		}

		return portletCategoryName;
	}

	public List<SelectOption> getPortletCategoryNameSelectOptions()
		throws PortalException {

		List<SelectOption> selectOptions = new ArrayList<>();

		boolean found = false;
		String portletCategoryName = getPortletCategoryName();

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletCategory rootPortletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		for (PortletCategory portletCategory :
				rootPortletCategory.getCategories()) {

			selectOptions.add(
				new SelectOption(
					LanguageUtil.get(
						themeDisplay.getLocale(), portletCategory.getName()),
					portletCategory.getName(),
					portletCategoryName.equals(portletCategory.getName())));

			if (Objects.equals(
					portletCategory.getName(), "category.remote-apps")) {

				found = true;
			}
		}

		if (!found) {
			selectOptions.add(
				new SelectOption(
					LanguageUtil.get(
						themeDisplay.getLocale(), "category.remote-apps"),
					"category.remote-apps",
					Objects.equals(
						portletCategoryName, "category.remote-apps")));
		}

		return ListUtil.sort(
			selectOptions,
			new Comparator<SelectOption>() {

				@Override
				public int compare(
					SelectOption selectOption1, SelectOption selectOption2) {

					String label1 = selectOption1.getLabel();
					String label2 = selectOption2.getLabel();

					return label1.compareTo(label2);
				}

			});
	}

	public String getProperties() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "properties");
	}

	public String getRedirect() {
		return ParamUtil.getString(_portletRequest, "redirect");
	}

	public String getThemeCSSClayURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETThemeCSS) {
			CETThemeCSS cetThemeCSS = (CETThemeCSS)_cet;

			url = cetThemeCSS.getClayURL();
		}

		return ParamUtil.getString(_portletRequest, "themeCSSMainURL", url);
	}

	public String getThemeCSSMainURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETThemeCSS) {
			CETThemeCSS cetThemeCSS = (CETThemeCSS)_cet;

			url = cetThemeCSS.getMainURL();
		}

		return ParamUtil.getString(_portletRequest, "themeCSSMainURL", url);
	}

	public String getThemeFaviconURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETThemeFavicon) {
			CETThemeFavicon cetThemeFavicon = (CETThemeFavicon)_cet;

			url = cetThemeFavicon.getURL();
		}

		return ParamUtil.getString(_portletRequest, "themeFaviconURL", url);
	}

	public String getThemeJSURL() throws PortalException {
		String url = StringPool.BLANK;

		if (_cet instanceof CETThemeJS) {
			CETThemeJS cetThemeJS = (CETThemeJS)_cet;

			url = cetThemeJS.getURL();
		}

		return ParamUtil.getString(_portletRequest, "themeJSURL", url);
	}

	public String getTitle() {
		if (_clientExtensionEntry == null) {
			return LanguageUtil.get(_getHttpServletRequest(), "new-remote-app");
		}

		ThemeDisplay themeDisplay = _getThemeDisplay();

		return _clientExtensionEntry.getName(themeDisplay.getLocale());
	}

	public String getType() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "type",
			ClientExtensionEntryConstants.TYPE_IFRAME);
	}

	public List<SelectOption> getTypeSelectOptions() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		String type = getType();

		return Arrays.asList(
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "custom-element"),
				ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT, type)),
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "global-css"),
				ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_GLOBAL_CSS, type)),
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "global-js"),
				ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_GLOBAL_JS, type)),
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "iframe"),
				ClientExtensionEntryConstants.TYPE_IFRAME,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_IFRAME, type)),
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "theme-css"),
				ClientExtensionEntryConstants.TYPE_THEME_CSS,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_THEME_CSS, type)),
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "theme-favicon"),
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_THEME_FAVICON, type)),
			new SelectOption(
				LanguageUtil.get(httpServletRequest, "theme-js"),
				ClientExtensionEntryConstants.TYPE_THEME_JS,
				Objects.equals(
					ClientExtensionEntryConstants.TYPE_THEME_JS, type)));
	}

	public boolean isCustomElementUseESM() throws PortalException {
		boolean useESM = false;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			useESM = cetCustomElement.isUseESM();
		}

		return ParamUtil.getBoolean(
			_getHttpServletRequest(), "customElementUseESM", useESM);
	}

	public boolean isEditingClientExtensionEntryType(String... types) {
		for (String type : types) {
			if (Objects.equals(getType(), type)) {
				return true;
			}
		}

		return false;
	}

	public boolean isInstanceable() throws PortalException {
		boolean instanceable = false;

		if (_cet instanceof CETCustomElement) {
			CETCustomElement cetCustomElement = (CETCustomElement)_cet;

			instanceable = cetCustomElement.isInstanceable();
		}
		else if (_cet instanceof CETIFrame) {
			CETIFrame cetiFrame = (CETIFrame)_cet;

			instanceable = cetiFrame.isInstanceable();
		}

		return ParamUtil.getBoolean(
			_getHttpServletRequest(), "instanceable", instanceable);
	}

	public boolean isInstanceableDisabled() {
		if (_clientExtensionEntry != null) {
			return true;
		}

		return false;
	}

	public boolean isTypeDisabled() {
		if (_clientExtensionEntry != null) {
			return true;
		}

		return false;
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_portletRequest);
	}

	private ThemeDisplay _getThemeDisplay() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private final CET _cet;
	private final ClientExtensionEntry _clientExtensionEntry;
	private final PortletRequest _portletRequest;

}