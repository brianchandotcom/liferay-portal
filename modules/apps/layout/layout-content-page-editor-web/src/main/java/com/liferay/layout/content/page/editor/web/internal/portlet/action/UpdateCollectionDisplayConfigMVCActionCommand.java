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

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.ContentUtil;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkManager;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_collection_display_config"
	},
	service = MVCActionCommand.class
)
public class UpdateCollectionDisplayConfigMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String itemConfig = ParamUtil.getString(actionRequest, "itemConfig");
		String itemId = ParamUtil.getString(actionRequest, "itemId");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		JSONArray fragmentEntryLinksJSONArray = _jsonFactory.createJSONArray();

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>(
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					themeDisplay.getScopeGroupId(), segmentsExperienceId,
					themeDisplay.getPlid(),
					_KEY_COLLECTION_FILTER_FRAGMENT_RENDERER));

		fragmentEntryLinks.addAll(
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					themeDisplay.getScopeGroupId(), segmentsExperienceId,
					themeDisplay.getPlid(),
					_KEY_COLLECTION_APPLIED_FILTERS_FRAGMENT_RENDERER));

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

			String configuration = editableValuesJSONObject.getString(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

			if ((configuration == null) || !JSONUtil.isValid(configuration)) {
				continue;
			}

			JSONObject configurationJSONObject =
				editableValuesJSONObject.getJSONObject(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

			if (!configurationJSONObject.has("targetCollections")) {
				continue;
			}

			List<String> targetCollections = JSONUtil.toStringList(
				configurationJSONObject.getJSONArray("targetCollections"));

			if (!targetCollections.contains(itemId)) {
				continue;
			}

			targetCollections.remove(itemId);

			configurationJSONObject.put(
				"targetCollections",
				JSONUtil.toJSONArray(
					targetCollections,
					targetCollectionItemId -> targetCollectionItemId));

			if (targetCollections.isEmpty()) {
				configurationJSONObject.put("filterKey", StringPool.BLANK);
			}

			editableValuesJSONObject.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				configurationJSONObject);

			long fragmentEntryLinkId =
				fragmentEntryLink.getFragmentEntryLinkId();

			fragmentEntryLink =
				_fragmentEntryLinkLocalService.updateFragmentEntryLink(
					fragmentEntryLinkId, editableValuesJSONObject.toString());

			fragmentEntryLinksJSONArray.put(
				_fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
					fragmentEntryLink,
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse),
					layoutStructure));
		}

		try {
			jsonObject.put(
				"fragmentEntryLinks", fragmentEntryLinksJSONArray
			).put(
				"layoutData",
				LayoutStructureUtil.updateLayoutPageTemplateData(
					themeDisplay.getScopeGroupId(), segmentsExperienceId,
					themeDisplay.getPlid(),
					curLayoutStructure -> curLayoutStructure.updateItemConfig(
						_jsonFactory.createJSONObject(itemConfig), itemId))
			).put(
				"pageContents",
				ContentUtil.getPageContentsJSONArray(
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse),
					themeDisplay.getPlid(), segmentsExperienceId)
			);
		}
		catch (Exception exception) {
			_log.error(exception);

			jsonObject.put(
				"error",
				_language.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final String
		_KEY_COLLECTION_APPLIED_FILTERS_FRAGMENT_RENDERER =
			"com.liferay.fragment.renderer.collection.filter.internal." +
				"CollectionAppliedFiltersFragmentRenderer";

	private static final String _KEY_COLLECTION_FILTER_FRAGMENT_RENDERER =
		"com.liferay.fragment.renderer.collection.filter.internal." +
			"CollectionFilterFragmentRenderer";

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateCollectionDisplayConfigMVCActionCommand.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}