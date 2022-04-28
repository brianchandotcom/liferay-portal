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

package com.liferay.remote.app.web.internal.frontend.data.set.provider;

import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminFDSNames;
import com.liferay.remote.app.web.internal.frontend.data.set.model.RemoteAppFDSEntry;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "fds.data.provider.key=" + RemoteAppAdminFDSNames.REMOTE_APP_ENTRIES,
	service = FDSActionProvider.class
)
public class RemoteAppEntryFDSActionProvider implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		RemoteAppFDSEntry remoteAppFDSEntry = (RemoteAppFDSEntry)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> _buildEditRemoteAppEntryAction(
				dropdownItem, httpServletRequest, remoteAppFDSEntry)
		).add(
			dropdownItem -> _buildDeleteRemoteAppEntryAction(
				dropdownItem, httpServletRequest, remoteAppFDSEntry)
		).build();
	}

	private void _buildDeleteRemoteAppEntryAction(
		DropdownItem dropdownItem, HttpServletRequest httpServletRequest,
		RemoteAppFDSEntry remoteAppFDSEntry) {

		dropdownItem.setHref(
			PortletURLBuilder.create(
				_getActionURL(httpServletRequest)
			).setActionName(
				"/remote_app_admin/delete_remote_app_entry"
			).setParameter(
				"remoteAppEntryId", remoteAppFDSEntry.getRemoteAppEntryId()
			).buildString());

		dropdownItem.setIcon("times-circle");
		dropdownItem.setLabel(_getMessage(httpServletRequest, "delete"));
	}

	private void _buildEditRemoteAppEntryAction(
		DropdownItem dropdownItem, HttpServletRequest httpServletRequest,
		RemoteAppFDSEntry remoteAppFDSEntry) {

		PortletURL editRemoteAppEntryURL = PortletURLBuilder.create(
			_getRenderURL(httpServletRequest)
		).setMVCRenderCommandName(
			"/remote_app_admin/edit_remote_app_entry"
		).setParameter(
			"remoteAppEntryId", remoteAppFDSEntry.getRemoteAppEntryId()
		).buildPortletURL();

		String currentURL = ParamUtil.getString(
			httpServletRequest, "currentURL",
			_portal.getCurrentURL(httpServletRequest));

		editRemoteAppEntryURL.setParameter("redirect", currentURL);

		dropdownItem.setHref(editRemoteAppEntryURL);
		dropdownItem.setLabel(_getMessage(httpServletRequest, "edit"));
	}

	private PortletURL _getActionURL(HttpServletRequest httpServletRequest) {
		String portletId = _getPortletId(httpServletRequest);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return requestBackedPortletURLFactory.createActionURL(portletId);
	}

	private String _getMessage(
		HttpServletRequest httpServletRequest, String key) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		return LanguageUtil.get(resourceBundle, key);
	}

	private String _getPortletId(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getId();
	}

	private PortletURL _getRenderURL(HttpServletRequest httpServletRequest) {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return requestBackedPortletURLFactory.createRenderURL(
			_getPortletId(httpServletRequest));
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}