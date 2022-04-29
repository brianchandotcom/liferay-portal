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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.service.LayoutClassedModelUsageLocalServiceUtil;
import com.liferay.layout.util.structure.LayoutStructureItemCSSUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkUtil {

	public static void deleteFragmentEntryLink(
			ContentPageEditorListenerTracker contentPageEditorListenerTracker,
			long fragmentEntryLinkId, long plid)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		if (fragmentEntryLink == null) {
			LayoutClassedModelUsageLocalServiceUtil.
				deleteLayoutClassedModelUsages(
					String.valueOf(fragmentEntryLinkId),
					PortalUtil.getClassNameId(FragmentEntryLink.class), plid);

			return;
		}

		FragmentEntryLinkServiceUtil.deleteFragmentEntryLink(
			fragmentEntryLinkId);

		LayoutClassedModelUsageLocalServiceUtil.deleteLayoutClassedModelUsages(
			String.valueOf(fragmentEntryLinkId),
			PortalUtil.getClassNameId(FragmentEntryLink.class), plid);

		List<ContentPageEditorListener> contentPageEditorListeners =
			contentPageEditorListenerTracker.getContentPageEditorListeners();

		for (ContentPageEditorListener contentPageEditorListener :
				contentPageEditorListeners) {

			contentPageEditorListener.onDeleteFragmentEntryLink(
				fragmentEntryLink);
		}
	}

	public static FragmentEntry getFragmentEntry(
		long groupId,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		String fragmentEntryKey, Locale locale) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionContributorTracker.getFragmentEntries(locale);

		return fragmentEntries.get(fragmentEntryKey);
	}

	public static JSONObject getFragmentEntryLinkJSONObject(
			PortletRequest portletRequest, PortletResponse portletResponse,
			FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
			FragmentEntryLink fragmentEntryLink,
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentRendererController fragmentRendererController,
			FragmentRendererTracker fragmentRendererTracker,
			ItemSelector itemSelector, String portletId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		try {
			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			String languageId = ParamUtil.getString(
				portletRequest, "languageId", themeDisplay.getLanguageId());

			defaultFragmentRendererContext.setLocale(
				LocaleUtil.fromLanguageId(languageId));

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			String configuration = fragmentRendererController.getConfiguration(
				defaultFragmentRendererContext);

			FragmentEntry fragmentEntry = _getFragmentEntry(
				fragmentEntryLink, fragmentCollectionContributorTracker,
				themeDisplay.getLocale());

			String fragmentEntryKey = null;
			int fragmentEntryType = FragmentConstants.TYPE_COMPONENT;
			String icon = null;
			String name = null;

			if (fragmentEntry != null) {
				fragmentEntryKey = fragmentEntry.getFragmentEntryKey();
				fragmentEntryType = fragmentEntry.getType();
				icon = fragmentEntry.getIcon();
				name = fragmentEntry.getName();
			}
			else {
				String rendererKey = fragmentEntryLink.getRendererKey();

				if (Validator.isNull(rendererKey)) {
					rendererKey =
						FragmentRendererConstants.
							FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
				}

				FragmentRenderer fragmentRenderer =
					fragmentRendererTracker.getFragmentRenderer(rendererKey);

				if (fragmentRenderer != null) {
					fragmentEntryKey = fragmentRenderer.getKey();

					name = fragmentRenderer.getLabel(themeDisplay.getLocale());
				}

				if (Validator.isNotNull(portletId)) {
					name = PortalUtil.getPortletTitle(
						portletId, themeDisplay.getLocale());
				}
			}

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

			FragmentEntryLinkItemSelectorUtil.
				addFragmentEntryLinkFieldsSelectorURL(
					itemSelector,
					PortalUtil.getHttpServletRequest(portletRequest),
					PortalUtil.getLiferayPortletResponse(portletResponse),
					configurationJSONObject);

			String content = fragmentRendererController.render(
				defaultFragmentRendererContext,
				PortalUtil.getHttpServletRequest(portletRequest),
				PortalUtil.getHttpServletResponse(portletResponse));

			return JSONUtil.put(
				"configuration", configurationJSONObject
			).put(
				"content", content
			).put(
				"cssClass",
				LayoutStructureItemCSSUtil.getFragmentEntryLinkCssClass(
					fragmentEntryLink)
			).put(
				"defaultConfigurationValues",
				fragmentEntryConfigurationParser.
					getConfigurationDefaultValuesJSONObject(configuration)
			).put(
				"editableTypes",
				EditableFragmentEntryProcessorUtil.getEditableTypes(content)
			).put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues())
			).put(
				"fragmentEntryKey", fragmentEntryKey
			).put(
				"fragmentEntryLinkId",
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
			).put(
				"fragmentEntryType",
				FragmentConstants.getTypeLabel(fragmentEntryType)
			).put(
				"icon", icon
			).put(
				"name", name
			).put(
				"portletId", portletId
			).put(
				"segmentsExperienceId",
				String.valueOf(fragmentEntryLink.getSegmentsExperienceId())
			);
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}
	}

	private static FragmentEntry _getFragmentEntry(
		FragmentEntryLink fragmentEntryLink,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		Locale locale) {

		if (fragmentEntryLink.getFragmentEntryId() <= 0) {
			return getFragmentEntry(
				fragmentEntryLink.getGroupId(),
				fragmentCollectionContributorTracker,
				fragmentEntryLink.getRendererKey(), locale);
		}

		return FragmentEntryLocalServiceUtil.fetchFragmentEntry(
			fragmentEntryLink.getFragmentEntryId());
	}

}