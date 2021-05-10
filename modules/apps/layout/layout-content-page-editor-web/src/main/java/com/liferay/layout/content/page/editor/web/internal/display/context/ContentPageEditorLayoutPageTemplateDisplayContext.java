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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorCriterion;
import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.util.MappingContentUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
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
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry,
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
			fragmentRendererTracker, frontendTokenDefinitionRegistry,
			httpServletRequest, infoItemServiceTracker, itemSelector,
			pageEditorConfiguration, portletRequest, renderResponse);

		_itemSelector = itemSelector;
		_pageIsDisplayPage = pageIsDisplayPage;
		_renderResponse = renderResponse;
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

		configContext.put(
			"infoItemPreviewSelectorURL", _getInfoItemPreviewSelectorURL());

		Map<String, Object> stateContext =
			(Map<String, Object>)editorContext.get("state");

		stateContext.put(
			"mappingFields",
			_addDisplayPageMappingFields(
				(JSONObject)stateContext.get("mappingFields")));

		configContext.put("selectedMappingTypes", _getSelectedMappingTypes());

		return editorContext;
	}

	@Override
	public String getPublishURL() {
		return getFragmentEntryActionURL(
			"/layout_content_page_editor/publish_layout_page_template_entry");
	}

	@Override
	public boolean isWorkflowEnabled() {
		return false;
	}

	@Override
	protected List<ItemSelectorCriterion>
		getCollectionItemSelectorCriterions() {

		List<ItemSelectorCriterion> collectionItemSelectorCriterions =
			super.getCollectionItemSelectorCriterions();

		if (!_pageIsDisplayPage) {
			return collectionItemSelectorCriterions;
		}

		InfoItemRelatedListProviderItemSelectorCriterion
			infoItemRelatedListProviderItemSelectorCriterion =
				new InfoItemRelatedListProviderItemSelectorCriterion();

		infoItemRelatedListProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoItemRelatedListProviderItemSelectorReturnType());

		List<String> sourceItemTypes = new ArrayList<>();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		sourceItemTypes.add(layoutPageTemplateEntry.getClassName());

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutPageTemplateEntry.getClassName());

		if (assetRendererFactory != null) {
			sourceItemTypes.add(AssetEntry.class.getName());
		}

		infoItemRelatedListProviderItemSelectorCriterion.setSourceItemTypes(
			sourceItemTypes);

		return ListUtil.concat(
			collectionItemSelectorCriterions,
			Collections.singletonList(
				infoItemRelatedListProviderItemSelectorCriterion));
	}

	private JSONObject _addDisplayPageMappingFields(
			JSONObject mappingFieldsJSONObject)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		String key =
			layoutPageTemplateEntry.getClassNameId() + StringPool.DASH +
				layoutPageTemplateEntry.getClassTypeId();

		if (!mappingFieldsJSONObject.has(key)) {
			mappingFieldsJSONObject.put(
				key,
				MappingContentUtil.getMappingFieldsJSONArray(
					String.valueOf(layoutPageTemplateEntry.getClassTypeId()),
					themeDisplay.getScopeGroupId(), infoItemServiceTracker,
					layoutPageTemplateEntry.getClassName(),
					themeDisplay.getLocale()));
		}

		return mappingFieldsJSONObject;
	}

	private String _getInfoItemPreviewSelectorURL() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getClassNameId() <= 0)) {

			return StringPool.BLANK;
		}

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());
		itemSelectorCriterion.setItemType(
			layoutPageTemplateEntry.getClassName());
		itemSelectorCriterion.setItemSubtype(
			_getItemSubtype(layoutPageTemplateEntry.getClassTypeId()));

		PortletURL infoItemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectInfoItem",
			itemSelectorCriterion);

		if (infoItemSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoItemSelectorURL.toString();
	}

	private String _getItemSubtype(long classTypeId) {
		if (classTypeId <= 0) {
			return StringPool.BLANK;
		}

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructure(classTypeId);

		if (ddmStructure != null) {
			return ddmStructure.getStructureKey();
		}

		return String.valueOf(classTypeId);
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
		}

		return null;
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

	private final ItemSelector _itemSelector;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final boolean _pageIsDisplayPage;
	private final RenderResponse _renderResponse;

}