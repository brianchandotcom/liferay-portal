/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.display.context;

import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Stefano Motta
 */
public class InviteMemberDisplayContext {

	public InviteMemberDisplayContext(
		long digitalSalesRoomId, String emailAddress, Group group,
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay,
		String ticketKey) {

		_digitalSalesRoomId = digitalSalesRoomId;
		_emailAddress = emailAddress;
		_group = group;
		_httpServletRequest = httpServletRequest;
		_themeDisplay = themeDisplay;
		_ticketKey = ticketKey;

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(false);
	}

	public String getBackURL() throws PortalException {
		return PortalUtil.getPortalURL(_themeDisplay);
	}

	public long getDigitalSalesRoomId() {
		return _digitalSalesRoomId;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getRedirect() throws PortalException {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_httpServletRequest, LoginPortletKeys.LOGIN,
				PortalUtil.getPlidFromPortletId(
					_digitalSalesRoomId, LoginPortletKeys.LOGIN),
				PortletRequest.RENDER_PHASE)
		).setRedirect(
			_group.getDisplayURL(_themeDisplay) + "/onboarding"
		).buildString();
	}

	public String getTicketKey() {
		return _ticketKey;
	}

	private final long _digitalSalesRoomId;
	private final String _emailAddress;
	private final Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;
	private final String _ticketKey;

}