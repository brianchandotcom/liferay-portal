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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.web.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplay;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;
import com.liferay.sharing.web.internal.util.SharingUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARING,
		"mvc.command.name=/", "mvc.command.name=/sharing/share"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Template template = getTemplate(renderRequest);

		PortletURL shareActionURL = renderResponse.createActionURL();

		shareActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/sharing/share");

		long classNameId = ParamUtil.getLong(renderRequest, "classNameId");
		long classPK = ParamUtil.getLong(renderRequest, "classPK");

		template.put("classNameId", classNameId);
		template.put("classPK", classPK);

		template.put("portletNamespace", renderResponse.getNamespace());

		String refererPortletNamespace = ParamUtil.getString(
			renderRequest, "refererPortletNamespace");

		template.put("refererPortletNamespace", refererPortletNamespace);

		template.put("shareActionURL", shareActionURL.toString());
		template.put(
			"sharingDialogId", refererPortletNamespace + "sharingDialogId");

		List<SharingEntryPermissionDisplay> sharingEntryPermissionDisplays =
			_sharingUtil.getSharingEntryPermissionDisplays(
				themeDisplay.getPermissionChecker(), classNameId, classPK,
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale());

		template.put(
			"sharingEntryPermissionDisplays", sharingEntryPermissionDisplays);

		template.put(
			"sharingEntryPermissionDisplayActionId",
			SharingEntryPermissionDisplayAction.VIEW.getActionId());

		return "Sharing";
	}

	protected Template getTemplate(RenderRequest renderRequest) {
		return (Template)renderRequest.getAttribute(WebKeys.TEMPLATE);
	}

	@Reference
	private SharingUtil _sharingUtil;

}