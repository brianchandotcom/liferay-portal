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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.struts.FindActionHelper;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portlet.messageboards.model.MBMessage",
	service = FindActionHelper.class
)
public class MessageFindActionHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		MBMessage message = _mbMessageLocalService.getMessage(primaryKey);

		return message.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "messageId";
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter(
			getPrimaryKeyParameterName(), String.valueOf(primaryKey));
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_message");
	}

	@Override
	protected PortletLayoutFinder getPortletLayoutFinder() {
		return _portletPageFinder;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portlet.messageboards.model.MBMessage)",
		unbind = "-"
	)
	protected void setPortletLayoutFinder(
		PortletLayoutFinder portletPageFinder) {

		_portletPageFinder = portletPageFinder;
	}

	private MBMessageLocalService _mbMessageLocalService;
	private PortletLayoutFinder _portletPageFinder;

}