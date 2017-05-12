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

package com.liferay.trash.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides an implementation of <code>ViewPortletProvider</code> (in
 * <code>com.liferay.portal.kernel</code>) for the Recycle Bin portlet. This
 * implementation is aimed to generate instances of <code>PortletURL</code> (in
 * <code>javax.portlet</code> entities to provide URLs to view Recycle Bin
 * entries.
 *
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.trash.kernel.model.TrashEntry"},
	service = ViewPortletProvider.class
)
public class TrashViewPortletProvider
	extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletName() {
		return TrashPortletKeys.TRASH;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = PortletProviderUtil.getPortletId(
			TrashEntry.class.getName(), PortletProvider.Action.VIEW);

		if (!themeDisplay.isSignedIn() ||
			!_trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId()) ||
			!PortletPermissionUtil.hasControlPanelAccessPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portletId)) {

			return null;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			request, portletId, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		return portletURL;
	}

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}