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

package com.liferay.comments.web.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.messageboards.asset.MBMessageAssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Jorge Ferrer
 */
public class MBDiscussionAssetRendererFactory
	extends MBMessageAssetRendererFactory {

	public static final String TYPE = "discussion";

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException {

		MBMessage message = MBMessageLocalServiceUtil.getMessage(classPK);

		MBDiscussionAssetRenderer mbDiscussionAssetRenderer =
			new MBDiscussionAssetRenderer(message);

		mbDiscussionAssetRenderer.setAssetRendererType(type);

		return mbDiscussionAssetRenderer;
	}

	@Override
	public String getClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				PortletKeys.MESSAGE_BOARDS, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean isSelectable() {
		return _SELECTABLE;
	}

	private static final boolean _SELECTABLE = false;

}