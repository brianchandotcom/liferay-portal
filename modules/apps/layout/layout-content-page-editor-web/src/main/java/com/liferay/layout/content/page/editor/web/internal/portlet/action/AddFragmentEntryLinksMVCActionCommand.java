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
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/add_fragment_entry_links"
	},
	service = MVCActionCommand.class
)
public class AddFragmentEntryLinksMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _processAddFragmentEntryLinks(
			actionRequest, actionResponse);

		SessionMessages.add(actionRequest, "fragmentEntryLinkAdded");

		return jsonObject;
	}

	@Override
	protected JSONObject processException(
		ActionRequest actionRequest, Exception exception) {

		String errorMessage = "an-unexpected-error-occurred";

		if (exception instanceof NoSuchEntryException) {
			errorMessage =
				"the-fragment-can-no-longer-be-added-because-it-has-been-" +
					"deleted";
		}

		return JSONUtil.put(
			"error",
			LanguageUtil.get(
				_portal.getHttpServletRequest(actionRequest), errorMessage));
	}

	private JSONObject _processAddFragmentEntryLinks(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String fragmentEntryKey = ParamUtil.getString(
			actionRequest, "fragmentEntryKey");

		FragmentComposition fragmentComposition =
			_fragmentCollectionContributorTracker.getFragmentComposition(
				fragmentEntryKey);

		if (fragmentComposition == null) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			fragmentComposition =
				_fragmentCompositionService.fetchFragmentComposition(
					groupId, fragmentEntryKey);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperienceId);

		String parentItemId = ParamUtil.getString(
			actionRequest, "parentItemId");

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(parentItemId);

		JSONObject fragmentEntryLinksJSONObject =
			JSONFactoryUtil.createJSONObject();

		int position = ParamUtil.getInteger(actionRequest, "position");

		List<FragmentEntryLink> fragmentEntryLinks =
			_layoutPageTemplatesImporter.importPageElement(
				themeDisplay.getLayout(), layoutStructure, parentItemId,
				fragmentComposition.getData(), position, segmentsExperienceId);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			fragmentEntryLinksJSONObject.put(
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
				FragmentEntryLinkUtil.getFragmentEntryLinkJSONObject(
					actionRequest, actionResponse,
					_fragmentEntryConfigurationParser, fragmentEntryLink,
					_fragmentCollectionContributorTracker,
					_fragmentRendererController, _fragmentRendererTracker,
					_itemSelector,
					editableValuesJSONObject.getString("portletId")));

			List<ContentPageEditorListener> contentPageEditorListeners =
				_contentPageEditorListenerTracker.
					getContentPageEditorListeners();

			for (ContentPageEditorListener contentPageEditorListener :
					contentPageEditorListeners) {

				contentPageEditorListener.onAddFragmentEntryLink(
					fragmentEntryLink);
			}
		}

		layoutStructure = LayoutStructureUtil.getLayoutStructure(
			themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
			segmentsExperienceId);

		return JSONUtil.put(
			"addedItemId",
			() -> {
				List<String> childrenItemIds =
					layoutStructureItem.getChildrenItemIds();

				return childrenItemIds.get(position);
			}
		).put(
			"fragmentEntryLinks", fragmentEntryLinksJSONObject
		).put(
			"layoutData", layoutStructure.toJSONObject()
		);
	}

	@Reference
	private ContentPageEditorListenerTracker _contentPageEditorListenerTracker;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private Portal _portal;

}