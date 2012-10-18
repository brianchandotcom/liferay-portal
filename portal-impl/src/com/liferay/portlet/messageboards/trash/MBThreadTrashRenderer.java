/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Zsolt Berentey
 */
public class MBThreadTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "message_thread";

	public MBThreadTrashRenderer(MBThread thread)
		throws PortalException, SystemException {

		_rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			thread.getRootMessageId());
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/conversation.png";
	}

	public String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

	public String getSummary(Locale locale) {
		return null;
	}

	public String getTitle(Locale locale) {
		return HtmlUtil.stripHtml(_rootMessage.getSubject());
	}

	public String getType() {
		return TYPE;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(AssetRenderer.TEMPLATE_ABSTRACT) ||
			template.equals(AssetRenderer.TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.MESSAGE_BOARDS_MESSAGE, _rootMessage);

			return "/html/portlet/message_boards/asset/" + template + ".jsp";
		}

		return null;
	}

	private MBMessage _rootMessage;

}