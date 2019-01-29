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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_portlet"
	},
	service = MVCActionCommand.class
)
public class AddPortletMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		String portletId = ParamUtil.getString(actionRequest, "portletId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), 0, classNameId, classPK,
					StringPool.BLANK,
					_getPortletFragmentEntryLinkHTML(portletId),
					StringPool.BLANK, null, 0, serviceContext);

			jsonObject.put(
				"content",
				FragmentEntryRenderUtil.renderFragmentEntryLink(
					fragmentEntryLink,
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse)));
			jsonObject.put(
				"editableValues", fragmentEntryLink.getEditableValues());
			jsonObject.put(
				"fragmentEntryLinkId",
				fragmentEntryLink.getFragmentEntryLinkId());

			jsonObject.put(
				"name",
				_portal.getPortletTitle(portletId, themeDisplay.getLocale()));

			SessionMessages.add(actionRequest, "fragmentEntryLinkAdded");
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			String errorMessage = "an-unexpected-error-occurred";

			jsonObject.put(
				"error",
				LanguageUtil.get(themeDisplay.getRequest(), errorMessage));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _getPortletFragmentEntryLinkHTML(String portletId) {
		Element runtimeTagElement = new Element(
			"@liferay_portlet.runtime", true);

		Portlet portlet = _portletLocalService.getPortletById(portletId);

		runtimeTagElement.attr(
			"defaultPreferences", portlet.getDefaultPreferences());

		if (portlet.isInstanceable()) {
			runtimeTagElement.attr(
				"instanceId", PortletIdCodec.generateInstanceId());
		}

		runtimeTagElement.attr("persistSettings=false", true);
		runtimeTagElement.attr("portletName", portletId);

		return runtimeTagElement.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddPortletMVCActionCommand.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}