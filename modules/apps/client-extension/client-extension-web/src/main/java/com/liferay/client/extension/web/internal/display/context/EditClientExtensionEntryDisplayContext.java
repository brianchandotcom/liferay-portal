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
import com.liferay.client.extension.exception.ClientExtensionEntryCustomElementCSSURLsException;
import com.liferay.client.extension.exception.ClientExtensionEntryCustomElementHTMLElementNameException;
import com.liferay.client.extension.exception.ClientExtensionEntryInvalidURLException;
import com.liferay.client.extension.exception.ClientExtensionEntryInvalidURLsException;
import com.liferay.client.extension.model.ClientExtensionEntry;
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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.servlet.MultiSessionErrors;
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
		PortletRequest portletRequest) {

		_cetFactory = cetFactory;
		_clientExtensionEntry = clientExtensionEntry;
		_portletRequest = portletRequest;
	}

	public String getCmd() {
		if (_clientExtensionEntry == null) {
			return Constants.ADD;
		}

		return Constants.UPDATE;
	}

	public String[] getCustomElementCSSURLs() {
		String[] customElementCSSURLs = StringPool.EMPTY_ARRAY;

		CETCustomElement cetCustomElement = _getCETCustomElement();

		if (cetCustomElement != null) {
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

	public String getCustomElementHTMLName() {
		CETCustomElement cetCustomElement = _getCETCustomElement();

		return ParamUtil.getString(
			_getHttpServletRequest(), "customElementHTMLName",
			cetCustomElement.getHTMLElementName());
	}

	public String[] getCustomElementURLs() {
		String[] customElementURLs = StringPool.EMPTY_ARRAY;

		CETCustomElement cetCustomElement = _getCETCustomElement();

		if (cetCustomElement != null) {
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

	public String getFriendlyURLMapping() {
		String friendlyURLMapping = StringPool.BLANK;

		String type = getType();

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			CETCustomElement cetCustomElement = _getCETCustomElement();

			friendlyURLMapping = cetCustomElement.getFriendlyURLMapping();
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			CETIFrame cetiFrame = _getCETIFrame();

			friendlyURLMapping = cetiFrame.getFriendlyURLMapping();
		}

		return friendlyURLMapping;
	}

	public String getGlobalCSSURL() {
		CETGlobalCSS cetGlobalCSS = _getCETGlobalCSS();

		return ParamUtil.getString(
			_portletRequest, "globalCSSURL", cetGlobalCSS.getURL());
	}

	public String getGlobalJSURL() {
		CETGlobalJS cetGlobalJS = _getCETGlobalJS();

		return ParamUtil.getString(
			_portletRequest, "globalJSURL", cetGlobalJS.getURL());
	}

	public String getIFrameURL() {
		CETIFrame cetIFrame = _getCETIFrame();

		return ParamUtil.getString(
			_portletRequest, "iFrameURL", cetIFrame.getURL());
	}

	public String getName() {
		return BeanParamUtil.getString(
			_clientExtensionEntry, _portletRequest, "name");
	}

	public String getPortletCategoryName() {
		String portletCategoryName = StringPool.BLANK;

		String type = getType();

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			CETCustomElement cetCustomElement = _getCETCustomElement();

			portletCategoryName = cetCustomElement.getPortletCategoryName();
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			CETIFrame cetiFrame = _getCETIFrame();

			portletCategoryName = cetiFrame.getPortletCategoryName();
		}

		if (Validator.isNull(portletCategoryName)) {
			portletCategoryName = "category.remote-apps";
		}

		return portletCategoryName;
	}

	public List<SelectOption> getPortletCategoryNameSelectOptions() {
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

	public String getThemeCSSClayURL() {
		CETThemeCSS cetThemeCSS = _getCETThemeCSS();

		return ParamUtil.getString(
			_portletRequest, "themeCSSClayURL", cetThemeCSS.getClayURL());
	}

	public String getThemeCSSMainURL() {
		CETThemeCSS cetThemeCSS = _getCETThemeCSS();

		return ParamUtil.getString(
			_portletRequest, "themeCSSMainURL", cetThemeCSS.getMainURL());
	}

	public String getThemeFaviconURL() {
		CETThemeFavicon cetThemeFavicon = _getCETThemeFavicon();

		return ParamUtil.getString(
			_portletRequest, "themeFaviconURL", cetThemeFavicon.getURL());
	}

	public String getThemeJSURL() {
		CETThemeJS cetThemeJS = _getCETThemeJS();

		return ParamUtil.getString(
			_portletRequest, "themeJSURL", cetThemeJS.getURL());
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

	public boolean isCustomElementUseESM() {
		CETCustomElement cetCustomElement = _getCETCustomElement();

		return ParamUtil.getBoolean(
			_getHttpServletRequest(), "customElementUseESM",
			cetCustomElement.isUseESM());
	}

	public boolean isEditingClientExtensionEntryType(String... types) {
		for (String type : types) {
			if (Objects.equals(type, _getClientExtensionEntryType())) {
				return true;
			}
		}

		return false;
	}

	public boolean isInstanceable() {
		boolean instanceable = false;

		String type = getType();

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			CETCustomElement cetCustomElement = _getCETCustomElement();

			instanceable = cetCustomElement.isInstanceable();
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			CETIFrame cetiFrame = _getCETIFrame();

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

	private CETCustomElement _getCETCustomElement() {
		if (_cetCustomElement == null) {
			_cetCustomElement = _cetFactory.cetCustomElement(
				_clientExtensionEntry);
		}

		return _cetCustomElement;
	}

	private CETGlobalCSS _getCETGlobalCSS() {
		if (_cetGlobalCSS == null) {
			_cetGlobalCSS = _cetFactory.cetGlobalCSS(_clientExtensionEntry);
		}

		return _cetGlobalCSS;
	}

	private CETGlobalJS _getCETGlobalJS() {
		if (_cetGlobalJS == null) {
			_cetGlobalJS = _cetFactory.cetGlobalJS(_clientExtensionEntry);
		}

		return _cetGlobalJS;
	}

	private CETIFrame _getCETIFrame() {
		if (_cetIFrame == null) {
			_cetIFrame = _cetFactory.cetIFrame(_clientExtensionEntry);
		}

		return _cetIFrame;
	}

	private CETThemeCSS _getCETThemeCSS() {
		if (_cetThemeCSS == null) {
			_cetThemeCSS = _cetFactory.cetThemeCSS(_clientExtensionEntry);
		}

		return _cetThemeCSS;
	}

	private CETThemeFavicon _getCETThemeFavicon() {
		if (_cetThemeFavicon == null) {
			_cetThemeFavicon = _cetFactory.cetThemeFavicon(
				_clientExtensionEntry);
		}

		return _cetThemeFavicon;
	}

	private CETThemeJS _getCETThemeJS() {
		if (_cetThemeJS == null) {
			_cetThemeJS = _cetFactory.cetThemeJS(_clientExtensionEntry);
		}

		return _cetThemeJS;
	}

	private String _getClientExtensionEntryType() {
		String errorSection = _getErrorSection();

		if (errorSection != null) {
			return errorSection;
		}

		return getType();
	}

	private String _getErrorSection() {
		if (MultiSessionErrors.contains(
				_portletRequest,
				ClientExtensionEntryInvalidURLException.class.getName())) {

			return ClientExtensionEntryConstants.TYPE_IFRAME;
		}

		if (MultiSessionErrors.contains(
				_portletRequest,
				ClientExtensionEntryCustomElementCSSURLsException.class.
					getName()) ||
			MultiSessionErrors.contains(
				_portletRequest,
				ClientExtensionEntryCustomElementHTMLElementNameException.class.
					getName()) ||
			MultiSessionErrors.contains(
				_portletRequest,
				ClientExtensionEntryInvalidURLsException.class.getName())) {

			return ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT;
		}

		return null;
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_portletRequest);
	}

	private ThemeDisplay _getThemeDisplay() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private CETCustomElement _cetCustomElement;
	private final CETFactory _cetFactory;
	private CETGlobalCSS _cetGlobalCSS;
	private CETGlobalJS _cetGlobalJS;
	private CETIFrame _cetIFrame;
	private CETThemeCSS _cetThemeCSS;
	private CETThemeFavicon _cetThemeFavicon;
	private CETThemeJS _cetThemeJS;
	private final ClientExtensionEntry _clientExtensionEntry;
	private final PortletRequest _portletRequest;

}