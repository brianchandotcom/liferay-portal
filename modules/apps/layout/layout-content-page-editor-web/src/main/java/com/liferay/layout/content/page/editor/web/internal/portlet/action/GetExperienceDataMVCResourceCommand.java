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

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_experience_data"
	},
	service = MVCResourceCommand.class
)
public class GetExperienceDataMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid());

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		portletPreferencesList.forEach(
			portletPreferences -> jsonArray.put(
				portletPreferences.getPortletId()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (!ParamUtil.getBoolean(resourceRequest, "loadFragmentEntryLinks")) {
			long segmentsExperienceId = ParamUtil.getLong(
				resourceRequest, "segmentsExperienceId");

			List<FragmentEntryLink> fragmentEntryLinks =
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						themeDisplay.getScopeGroupId(), segmentsExperienceId,
						themeDisplay.getPlid());

			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				DefaultFragmentRendererContext defaultFragmentRendererContext =
					new DefaultFragmentRendererContext(fragmentEntryLink);

				defaultFragmentRendererContext.
					setCollectionStyledLayoutStructureItemIds(
						LayoutStructureUtil.
							getCollectionStyledLayoutStructureItemIds(
								fragmentEntryLink.getFragmentEntryLinkId(),
								LayoutStructureUtil.getLayoutStructure(
									themeDisplay.getScopeGroupId(),
									themeDisplay.getPlid(),
									segmentsExperienceId)));

				jsonObject.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
					FragmentEntryLinkUtil.getFragmentEntryLinkJSONObject(
						defaultFragmentRendererContext,
						_fragmentEntryConfigurationParser, fragmentEntryLink,
						_fragmentCollectionContributorTracker,
						_fragmentRendererController, _fragmentRendererTracker,
						_portal.getHttpServletRequest(resourceRequest),
						_portal.getHttpServletResponse(resourceResponse),
						_itemSelector, StringPool.BLANK));
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"fragmentEntryLinks", jsonObject
			).put(
				"portletIds", jsonArray
			));
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}