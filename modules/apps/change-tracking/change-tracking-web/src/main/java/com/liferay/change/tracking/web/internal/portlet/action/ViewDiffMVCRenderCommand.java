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
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.display.CTDisplayRegistry;
import com.liferay.change.tracking.web.internal.display.CTEntryDiffDisplay;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS,
		"mvc.command.name=/change_lists/view_diff"
	},
	service = MVCRenderCommand.class
)
public class ViewDiffMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		String changeType = ParamUtil.getString(renderRequest, "changeType");

		long ctEntryId = ParamUtil.getLong(renderRequest, "ctEntryId");

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(ctEntryId);

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctEntry.getCtCollectionId());

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		String name = _ctDisplayRegistry.getTypeName(
			_portal.getLocale(httpServletRequest), ctEntry);

		CTEntryDiffDisplay ctEntryDiffDisplay = new CTEntryDiffDisplay(
			httpServletRequest, _portal.getHttpServletResponse(renderResponse),
			changeType, ctCollection, _ctDisplayRegistry, ctEntry, _language,
			name);

		renderRequest.setAttribute("CT_ENTRY_DIFF_DISPLAY", ctEntryDiffDisplay);

		return "/change_lists/diff.jsp";
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRegistry _ctDisplayRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActions _resourceActions;

}