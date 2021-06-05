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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/publish_ct_collection"
	},
	service = MVCActionCommand.class
)
public class PublishCTCollectionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			_ctCollectionService.publishCTCollection(
				themeDisplay.getUserId(), ctCollectionId);
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, portalException.getClass());
		}

		SessionMessages.add(
			actionRequest, "requestProcessed",
			_language.format(
				_portal.getHttpServletRequest(actionRequest),
				"publishing-x-has-started-successfully", new Object[] {name},
				false));

		sendRedirect(
			actionRequest, actionResponse,
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					actionRequest, CTPortletKeys.PUBLICATIONS,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/view_history"
			).buildString());
	}

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}