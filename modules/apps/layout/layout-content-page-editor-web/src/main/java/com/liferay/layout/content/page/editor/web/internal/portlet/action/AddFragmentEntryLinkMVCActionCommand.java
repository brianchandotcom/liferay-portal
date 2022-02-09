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
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
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
 * @author Víctor Galán
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/add_fragment_entry_link"
	},
	service = MVCActionCommand.class
)
public class AddFragmentEntryLinkMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	protected FragmentEntryLink addFragmentEntryLink(
			ActionRequest actionRequest)
		throws PortalException {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String fragmentEntryKey = ParamUtil.getString(
			actionRequest, "fragmentEntryKey");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		FragmentEntry fragmentEntry = FragmentEntryLinkUtil.getFragmentEntry(
			groupId, _fragmentCollectionContributorTracker, fragmentEntryKey,
			serviceContext.getLocale());

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(fragmentEntryKey);

		if ((fragmentEntry == null) && (fragmentRenderer == null)) {
			throw new NoSuchEntryException();
		}

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");

		if (fragmentEntry != null) {
			String contributedRendererKey = null;

			if (fragmentEntry.getFragmentEntryId() == 0) {
				contributedRendererKey = fragmentEntryKey;
			}

			return _fragmentEntryLinkService.addFragmentEntryLink(
				serviceContext.getScopeGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				serviceContext.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), null, StringPool.BLANK, 0,
				contributedRendererKey, serviceContext);
		}

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(null);

		return _fragmentEntryLinkService.addFragmentEntryLink(
			serviceContext.getScopeGroupId(), 0, 0, segmentsExperienceId,
			serviceContext.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK,
			fragmentRenderer.getConfiguration(defaultFragmentRendererContext),
			StringPool.BLANK, StringPool.BLANK, 0, fragmentEntryKey,
			serviceContext);
	}

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _processAddFragmentEntryLink(
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

	private JSONObject _addFragmentEntryLinkToLayoutDataJSONObject(
			ActionRequest actionRequest, FragmentEntryLink fragmentEntryLink)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String parentItemId = ParamUtil.getString(
			actionRequest, "parentItemId");
		int position = ParamUtil.getInteger(actionRequest, "position");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		LayoutStructureUtil.updateLayoutPageTemplateData(
			themeDisplay.getScopeGroupId(), segmentsExperienceId,
			themeDisplay.getPlid(),
			layoutStructure -> {
				LayoutStructureItem layoutStructureItem =
					layoutStructure.addFragmentStyledLayoutStructureItem(
						fragmentEntryLink.getFragmentEntryLinkId(),
						parentItemId, position);

				jsonObject.put("addedItemId", layoutStructureItem.getItemId());
			});

		List<ContentPageEditorListener> contentPageEditorListeners =
			_contentPageEditorListenerTracker.getContentPageEditorListeners();

		for (ContentPageEditorListener contentPageEditorListener :
				contentPageEditorListeners) {

			contentPageEditorListener.onAddFragmentEntryLink(fragmentEntryLink);
		}

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				fragmentEntryLink.getSegmentsExperienceId());

		return jsonObject.put("layoutData", layoutStructure.toJSONObject());
	}

	private JSONObject _processAddFragmentEntryLink(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = addFragmentEntryLink(
			actionRequest);

		JSONObject jsonObject = _addFragmentEntryLinkToLayoutDataJSONObject(
			actionRequest, fragmentEntryLink);

		return jsonObject.put(
			"fragmentEntryLink",
			FragmentEntryLinkUtil.getFragmentEntryLinkJSONObject(
				actionRequest, actionResponse,
				_fragmentEntryConfigurationParser, fragmentEntryLink,
				_fragmentCollectionContributorTracker,
				_fragmentRendererController, _fragmentRendererTracker,
				_itemSelector, StringPool.BLANK));
	}

	@Reference
	private ContentPageEditorListenerTracker _contentPageEditorListenerTracker;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}