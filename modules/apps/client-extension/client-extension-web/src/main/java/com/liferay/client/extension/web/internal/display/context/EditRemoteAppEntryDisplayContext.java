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

package com.liferay.remote.app.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.servlet.MultiSessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.WebAppPool;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementCSSURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementHTMLElementNameException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryIFrameURLException;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class EditRemoteAppEntryDisplayContext {

	public EditRemoteAppEntryDisplayContext(
		PortletRequest portletRequest, RemoteAppEntry remoteAppEntry) {

		_portletRequest = portletRequest;
		_remoteAppEntry = remoteAppEntry;
	}

	public String getCmd() {
		if (_remoteAppEntry == null) {
			return Constants.ADD;
		}

		return Constants.UPDATE;
	}

	public String[] getCustomElementCSSURLs() {
		String[] customElementCSSURLs = StringPool.EMPTY_ARRAY;

		if (_remoteAppEntry != null) {
			String customElementCSSURLsString =
				_remoteAppEntry.getCustomElementCSSURLs();

			customElementCSSURLs = customElementCSSURLsString.split(
				StringPool.NEW_LINE);
		}

		customElementCSSURLs = ParamUtil.getStringValues(
			_portletRequest, "customElementCSSURLs", customElementCSSURLs);

		if (customElementCSSURLs.length == 0) {
			customElementCSSURLs = new String[1];
		}

		return customElementCSSURLs;
	}

	public String[] getCustomElementURLs() {
		String[] customElementURLs = StringPool.EMPTY_ARRAY;

		if (_remoteAppEntry != null) {
			String customElementURLsString =
				_remoteAppEntry.getCustomElementURLs();

			customElementURLs = customElementURLsString.split(
				StringPool.NEW_LINE);
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
			_remoteAppEntry, _portletRequest, "description");
	}

	public String getName() {
		return BeanParamUtil.getString(
			_remoteAppEntry, _portletRequest, "name");
	}

	public List<SelectOption> getPortletCategoryNameSelectOptions()
		throws Exception {

		List<SelectOption> selectOptions = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletCategory rootPortletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		String portletCategoryName = BeanPropertiesUtil.getString(
			_remoteAppEntry, "portletCategoryName", "category.remote-apps");

		boolean found = false;

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

	public String getRedirect() {
		return ParamUtil.getString(_portletRequest, "redirect");
	}

	public RemoteAppEntry getRemoteAppEntry() {
		return _remoteAppEntry;
	}

	public long getRemoteAppEntryId() {
		return BeanParamUtil.getLong(
			_remoteAppEntry, _portletRequest, "remoteAppEntryId");
	}

	public String getTitle() {
		if (_remoteAppEntry == null) {
			return LanguageUtil.get(_getHttpServletRequest(), "new-remote-app");
		}

		ThemeDisplay themeDisplay = _getThemeDisplay();

		return _remoteAppEntry.getName(themeDisplay.getLocale());
	}

	public boolean isCustomElementUseESM() {
		return BeanParamUtil.getBoolean(
			_remoteAppEntry, _getHttpServletRequest(), "customElementUseESM");
	}

	public boolean isEditingRemoteAppEntryType(String type) {
		return type.equals(_getRemoteAppEntryType());
	}

	public boolean isInstanceable() {
		return BeanParamUtil.getBoolean(
			_remoteAppEntry, _getHttpServletRequest(), "instanceable");
	}

	public boolean isInstanceableDisabled() {
		if (_remoteAppEntry != null) {
			return true;
		}

		return false;
	}

	public boolean isTypeDisabled() {
		if (_remoteAppEntry != null) {
			return true;
		}

		return false;
	}

	private String _getErrorSection() {
		if (MultiSessionErrors.contains(
				_portletRequest,
				RemoteAppEntryIFrameURLException.class.getName())) {

			return RemoteAppConstants.TYPE_IFRAME;
		}

		if (MultiSessionErrors.contains(
				_portletRequest,
				RemoteAppEntryCustomElementCSSURLsException.class.getName()) ||
			MultiSessionErrors.contains(
				_portletRequest,
				RemoteAppEntryCustomElementHTMLElementNameException.class.
					getName()) ||
			MultiSessionErrors.contains(
				_portletRequest,
				RemoteAppEntryCustomElementURLsException.class.getName())) {

			return RemoteAppConstants.TYPE_CUSTOM_ELEMENT;
		}

		return null;
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_portletRequest);
	}

	private String _getRemoteAppEntryType() {
		String errorSection = _getErrorSection();

		if (errorSection != null) {
			return errorSection;
		}

		if (_remoteAppEntry == null) {
			return RemoteAppConstants.TYPE_IFRAME;
		}

		return _remoteAppEntry.getType();
	}

	private ThemeDisplay _getThemeDisplay() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private final PortletRequest _portletRequest;
	private final RemoteAppEntry _remoteAppEntry;

}