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
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.ContentUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
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
	immediate = true,
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
		String languageId = ParamUtil.getString(
			actionRequest, "languageId", themeDisplay.getLanguageId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray fragmentEntryLinksJSONArray =
			JSONFactoryUtil.createJSONArray();

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

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			if (!JSONUtil.isValid(
					editableValuesJSONObject.getString(
						_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR))) {

				continue;
			}

			JSONObject configurationJSONObject =
				editableValuesJSONObject.getJSONObject(
					_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

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
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				configurationJSONObject);

			long fragmentEntryLinkId =
				fragmentEntryLink.getFragmentEntryLinkId();

			fragmentEntryLink =
				_fragmentEntryLinkLocalService.updateFragmentEntryLink(
					fragmentEntryLinkId, editableValuesJSONObject.toString());

			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			defaultFragmentRendererContext.setLocale(
				LocaleUtil.fromLanguageId(languageId));

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			fragmentEntryLinksJSONArray.put(
				JSONUtil.put(
					"content",
					_fragmentRendererController.render(
						defaultFragmentRendererContext,
						_portal.getHttpServletRequest(actionRequest),
						_portal.getHttpServletResponse(actionResponse))
				).put(
					"editableValues", editableValuesJSONObject
				).put(
					"fragmentEntryLinkId", String.valueOf(fragmentEntryLinkId)
				));
		}

		try {
			jsonObject.put(
				"fragmentEntryLinks", fragmentEntryLinksJSONArray
			).put(
				"layoutData",
				LayoutStructureUtil.updateLayoutPageTemplateData(
					themeDisplay.getScopeGroupId(), segmentsExperienceId,
					themeDisplay.getPlid(),
					layoutStructure -> layoutStructure.updateItemConfig(
						JSONFactoryUtil.createJSONObject(itemConfig), itemId))
			).put(
				"pageContents",
				ContentUtil.getPageContentsJSONArray(
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse),
					themeDisplay.getPlid())
			);
		}
		catch (Exception exception) {
			_log.error(exception);

			jsonObject.put(
				"error",
				LanguageUtil.get(
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

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateCollectionDisplayConfigMVCActionCommand.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Portal _portal;

}