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
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;

import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/duplicate_segments_experience"
	},
	service = MVCActionCommand.class
)
public class DuplicateSegmentsExperienceMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperienceId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		SegmentsExperience duplicatedSegmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				serviceContext.getScopeGroupId(),
				segmentsExperience.getSegmentsEntryId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(),
					LanguageUtil.format(
						themeDisplay.getLocale(), "copy-of-x",
						segmentsExperience.getName(
							LocaleUtil.getSiteDefault()))),
				segmentsExperience.isActive(), new UnicodeProperties(true),
				serviceContext);

		SegmentsExperienceUtil.copySegmentsExperienceData(
			themeDisplay.getPlid(), _commentManager,
			themeDisplay.getScopeGroupId(), _portletRegistry,
			segmentsExperienceId,
			duplicatedSegmentsExperience.getSegmentsExperienceId(),
			className -> serviceContext, themeDisplay.getUserId());

		return JSONUtil.put(
			"fragmentEntryLinks",
			_getFragmentEntryLinksJSONObject(
				actionRequest, actionResponse, themeDisplay.getPlid(),
				themeDisplay.getScopeGroupId(),
				duplicatedSegmentsExperience.getSegmentsExperienceId())
		).put(
			"layoutData",
			_getLayoutDataJSONObject(
				themeDisplay.getPlid(), themeDisplay.getScopeGroupId(),
				duplicatedSegmentsExperience.getSegmentsExperienceId())
		).put(
			"segmentsExperience",
			SegmentsExperienceUtil.getSegmentsExperienceJSONObject(
				duplicatedSegmentsExperience)
		);
	}

	private JSONObject _getFragmentEntryLinksJSONObject(
			ActionRequest actionRequest, ActionResponse actionResponse,
			long plid, long groupId, long segmentExperienceId)
		throws Exception {

		JSONObject fragmentEntryLinksJSONObject =
			JSONFactoryUtil.createJSONObject();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					groupId, segmentExperienceId, plid);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLinksJSONObject.put(
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
				FragmentEntryLinkUtil.getFragmentEntryLinkJSONObject(
					actionRequest, actionResponse,
					_fragmentEntryConfigurationParser, fragmentEntryLink,
					_fragmentCollectionContributorTracker,
					_fragmentRendererController, _fragmentRendererTracker,
					_itemSelector, StringPool.BLANK));
		}

		return fragmentEntryLinksJSONObject;
	}

	private JSONObject _getLayoutDataJSONObject(
			long classPK, long groupId, long segmentsExperienceId)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(groupId, classPK, true);

		return JSONFactoryUtil.createJSONObject(
			layoutPageTemplateStructure.getData(segmentsExperienceId));
	}

	@Reference
	private CommentManager _commentManager;

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
	private Language _language;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

}