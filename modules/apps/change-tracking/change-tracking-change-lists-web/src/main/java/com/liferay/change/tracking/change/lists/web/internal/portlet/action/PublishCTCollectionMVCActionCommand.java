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

package com.liferay.change.tracking.change.lists.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS,
		"mvc.command.name=/change_lists/publish_ct_collection"
	},
	service = MVCActionCommand.class
)
public class PublishCTCollectionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");
		String name = ParamUtil.getString(actionRequest, "name");

		_ctEngineManager.publishCTCollection(
			themeDisplay.getUserId(), ctCollectionId, true);

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				themeDisplay.getCompanyId());

		productionCTCollectionOptional.ifPresent(
			ctCollection -> _ctEngineManager.checkoutCTCollection(
				themeDisplay.getUserId(), ctCollection.getCtCollectionId()));

		hideDefaultSuccessMessage(actionRequest);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		SessionMessages.add(
			httpServletRequest, "requestProcessed",
			LanguageUtil.format(
				httpServletRequest, "publishing-x-has-started-successfully",
				new Object[] {name}, false));

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, CTPortletKeys.CHANGE_LISTS_HISTORY,
			PortletRequest.RENDER_PHASE);

		sendRedirect(actionRequest, actionResponse, portletURL.toString());
	}

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private Portal _portal;

}