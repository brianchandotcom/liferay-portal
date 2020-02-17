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

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_fragment_entry_link"
	},
	service = MVCResourceCommand.class
)
public class GetFragmentEntryLinkMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fragmentEntryLinkId = ParamUtil.getLong(
			resourceRequest, "fragmentEntryLinkId");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentEntryLink != null) {
			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			defaultFragmentRendererContext.setLocale(themeDisplay.getLocale());

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			long segmentsExperienceId = ParamUtil.getLong(
				resourceRequest, "segmentsExperienceId");

			defaultFragmentRendererContext.setSegmentsExperienceIds(
				new long[] {segmentsExperienceId});

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			String content = _fragmentRendererController.render(
				defaultFragmentRendererContext, httpServletRequest,
				_portal.getHttpServletResponse(resourceResponse));

			jsonObject.put(
				"content", content
			).put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues())
			);

			if (SessionErrors.contains(
					httpServletRequest, "fragmentEntryContentInvalid")) {

				jsonObject.put("error", true);

				SessionErrors.clear(httpServletRequest);
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Portal _portal;

}