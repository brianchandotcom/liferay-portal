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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.MultiSessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementCSSURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementHTMLElementNameException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryIFrameURLException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminWebKeys;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppAdminDisplayContext {

	public RemoteAppAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		RemoteAppEntryLocalService remoteAppEntryLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCRenderCommandName(
						"/remote_app_admin/edit_remote_app_entry"
					).setRedirect(
						_getRedirect()
					).buildPortletURL());

				dropdownItem.setLabel(_getLabel("add-remote-web-app"));
			}
		).build();
	}

	public PortletURL getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	public List<SelectOption> getPortletCategoryNameSelectOptions()
		throws Exception {

		List<SelectOption> selectOptions = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletCategory rootPortletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		rootPortletCategory = PortletCategoryUtil.getRelevantPortletCategory(
			themeDisplay.getPermissionChecker(), themeDisplay.getCompanyId(),
			themeDisplay.getLayout(), rootPortletCategory,
			themeDisplay.getLayoutTypePortlet());

		RemoteAppEntry remoteAppEntry =
			(RemoteAppEntry)_renderRequest.getAttribute(
				RemoteAppAdminWebKeys.REMOTE_APP_ENTRY);

		String portletCategoryName = BeanPropertiesUtil.getString(
			remoteAppEntry, "portletCategoryName", "category.remote-apps");

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

	public boolean isEditingRemoteAppEntryType(String type) {
		return type.equals(_getCurrentRemoteAppEntryType());
	}

	private String _getCurrentRemoteAppEntryType() {
		String errorSection = _getErrorSection();

		if (errorSection != null) {
			return errorSection;
		}

		RemoteAppEntry remoteAppEntry =
			(RemoteAppEntry)_renderRequest.getAttribute(
				RemoteAppAdminWebKeys.REMOTE_APP_ENTRY);

		if (remoteAppEntry == null) {
			return RemoteAppConstants.TYPE_IFRAME;
		}

		return remoteAppEntry.getType();
	}

	private String _getErrorSection() {
		if (MultiSessionErrors.contains(
				_renderRequest,
				RemoteAppEntryIFrameURLException.class.getName())) {

			return RemoteAppConstants.TYPE_IFRAME;
		}

		if (MultiSessionErrors.contains(
				_renderRequest,
				RemoteAppEntryCustomElementCSSURLsException.class.getName()) ||
			MultiSessionErrors.contains(
				_renderRequest,
				RemoteAppEntryCustomElementHTMLElementNameException.class.
					getName()) ||
			MultiSessionErrors.contains(
				_renderRequest,
				RemoteAppEntryCustomElementURLsException.class.getName())) {

			return RemoteAppConstants.TYPE_CUSTOM_ELEMENT;
		}

		return null;
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getLabel(String label) {
		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}