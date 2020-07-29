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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ContentPageEditorLayoutPageTemplateDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageEditorLayoutPageTemplateDisplayContext(
		CommentManager commentManager,
		List<ContentPageEditorSidebarPanel> contentPageEditorSidebarPanels,
		FFLayoutContentPageEditorConfiguration
			ffLayoutContentPageEditorConfiguration,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererController fragmentRendererController,
		FragmentRendererTracker fragmentRendererTracker,
		HttpServletRequest httpServletRequest,
		InfoItemServiceTracker infoItemServiceTracker,
		ItemSelector itemSelector,
		PageEditorConfiguration pageEditorConfiguration,
		boolean pageIsDisplayPage, PortletRequest portletRequest,
		RenderResponse renderResponse) {

		super(
			commentManager, contentPageEditorSidebarPanels,
			ffLayoutContentPageEditorConfiguration,
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererController,
			fragmentRendererTracker, httpServletRequest, infoItemServiceTracker,
			itemSelector, pageEditorConfiguration, portletRequest,
			renderResponse);

		_pageIsDisplayPage = pageIsDisplayPage;
	}

	@Override
	public Map<String, Object> getEditorContext(String npmResolvedPackageName)
		throws Exception {

		Map<String, Object> editorContext = super.getEditorContext(
			npmResolvedPackageName);

		if (!_pageIsDisplayPage) {
			return editorContext;
		}

		Map<String, Object> configContext =
			(Map<String, Object>)editorContext.get("config");

		configContext.put("selectedMappingTypes", _getSelectedMappingTypes());

		return editorContext;
	}

	@Override
	public String getPublishURL() {
		return getFragmentEntryActionURL(
			"/content_layout/publish_layout_page_template_entry");
	}

	@Override
	public boolean isWorkflowEnabled() {
		return false;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry() {
		if (_layoutPageTemplateEntry != null) {
			return _layoutPageTemplateEntry;
		}

		Layout draftLayout = themeDisplay.getLayout();

		_layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(draftLayout.getClassPK());

		return _layoutPageTemplateEntry;
	}

	private String _getMappingSubtypeLabel() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		InfoItemFormVariationsProvider infoItemFormVariationsProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider != null) {
			Collection<InfoItemFormVariation> infoItemFormVariations =
				infoItemFormVariationsProvider.getInfoItemFormVariations(
					layoutPageTemplateEntry.getGroupId());

			for (InfoItemFormVariation infoItemFormVariation :
					infoItemFormVariations) {

				String key = infoItemFormVariation.getKey();

				if (key.equals(
						String.valueOf(
							layoutPageTemplateEntry.getClassTypeId()))) {

					return infoItemFormVariation.getLabel(
						themeDisplay.getLocale());
				}
			}

			return null;
		}

		StringBuilder sb = new StringBuilder(4);

		sb.append(
			LanguageUtil.get(themeDisplay.getLocale(), "unknown-variation"));
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(layoutPageTemplateEntry.getClassTypeId());
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String _getMappingTypeLabel() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		InfoItemFormProvider<?> infoItemFormProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormProvider == null) {
			return null;
		}

		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemDetailsProvider == null) {
			return null;
		}

		InfoItemClassDetails infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		return infoItemClassDetails.getLabel(themeDisplay.getLocale());
	}

	private Map<String, Object> _getSelectedMappingTypes() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getClassNameId() <= 0)) {

			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"mappingDescription",
			LanguageUtil.get(
				httpServletRequest,
				"content-source-selected-for-this-display-page-template")
		).put(
			"type",
			HashMapBuilder.<String, Object>put(
				"groupTypeTitle",
				LanguageUtil.get(httpServletRequest, "content-type")
			).put(
				"id", layoutPageTemplateEntry.getClassNameId()
			).put(
				"label", _getMappingTypeLabel()
			).build()
		).put(
			"subtype",
			() -> {
				String subtypeLabel = _getMappingSubtypeLabel();

				if (Validator.isNull(subtypeLabel)) {
					return StringPool.BLANK;
				}

				return HashMapBuilder.<String, Object>put(
					"groupSubtypeTitle",
					LanguageUtil.get(httpServletRequest, "subtype")
				).put(
					"id", layoutPageTemplateEntry.getClassTypeId()
				).put(
					"label", subtypeLabel
				).build();
			}
		).build();
	}

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final boolean _pageIsDisplayPage;

}