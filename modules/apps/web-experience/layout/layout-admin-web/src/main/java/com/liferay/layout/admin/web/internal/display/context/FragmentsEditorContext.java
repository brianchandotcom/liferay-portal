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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.util.SoyContextFactoryUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.utils.SoyContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentsEditorContext {

	public FragmentsEditorContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK, boolean showMapping) {

		_request = request;
		_renderResponse = renderResponse;
		_classPK = classPK;
		_showMapping = showMapping;

		_assetDisplayContributorTracker =
			(AssetDisplayContributorTracker)request.getAttribute(
				LayoutAdminWebKeys.ASSET_DISPLAY_CONTRIBUTOR_TRACKER);
		_classNameId = PortalUtil.getClassNameId(className);
		_itemSelector = (ItemSelector)request.getAttribute(
			LayoutAdminWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SoyContext getEditorContext() throws PortalException {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/add_fragment_entry_link"));
		soyContext.put("classNameId", _classNameId);
		soyContext.put("classPK", _classPK);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				PortletIdCodec.decodePortletName(portletDisplay.getId()),
				"fragmenEntryLinkEditor", StringPool.BLANK,
				Collections.emptyMap(), _themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(_request));

		soyContext.put(
			"defaultEditorConfiguration", editorConfiguration.getData());

		soyContext.put(
			"deleteFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/delete_fragment_entry_link"));
		soyContext.put(
			"editFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/edit_fragment_entry_link"));
		soyContext.put(
			"fragmentCollections", _getSoyContextFragmentCollections());
		soyContext.put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks());
		soyContext.put(
			"getAssetDisplayContributorsURL",
			_getFragmentEntryActionURL(
				"/layout/get_asset_display_contributors"));
		soyContext.put(
			"getAssetClassTypesURL",
			_getFragmentEntryActionURL("/layout/get_asset_class_types"));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_request),
			_renderResponse.getNamespace() + "selectImage",
			_getImageItemSelectorCriterion(), _getURLItemSelectorCriterion());

		soyContext.put("imageSelectorURL", itemSelectorURL.toString());

		soyContext.put("portletNamespace", _renderResponse.getNamespace());
		soyContext.put(
			"renderFragmentEntryURL",
			_getFragmentEntryActionURL("/layout/render_fragment_entry"));

		if (_showMapping) {
			soyContext.put(
				"selectedMappingTypeLabel", _getSelectedMappingTypeLabel());
		}

		soyContext.put("sidebarTabs", _getSidebarTabs());
		soyContext.put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		soyContext.put(
			"updateFragmentEntryLinksURL",
			_getFragmentEntryActionURL("/layout/update_fragment_entry_links"));
		soyContext.put(
			"updateLayoutPageTemplateEntryAssetTypeURL",
			_getFragmentEntryActionURL(
				"/layout/update_layout_page_template_entry_asset_type"));

		return soyContext;
	}

	private List<SoyContext> _getFragmentEntriesSoyContext(
		List<FragmentEntry> fragmentEntries) {

		List<SoyContext> soyContexts = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());
			soyContext.put(
				"imagePreviewURL",
				fragmentEntry.getImagePreviewURL(_themeDisplay));
			soyContext.put("name", fragmentEntry.getName());

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private String _getFragmentEntryActionURL(String action) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return actionURL.toString();
	}

	private ItemSelectorCriterion _getImageItemSelectorCriterion() {
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return imageItemSelectorCriterion;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry()
		throws PortalException {

		if (_layoutPageTemplateEntry != null) {
			return _layoutPageTemplateEntry;
		}

		_layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(
				_classPK);

		return _layoutPageTemplateEntry;
	}

	private String _getMappingSubtypeLabel() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutPageTemplateEntry.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			layoutPageTemplateEntry.getClassTypeId(),
			_themeDisplay.getLocale());

		return classType.getName();
	}

	private String _getMappingTypeLabel() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				layoutPageTemplateEntry.getClassName());

		if (assetDisplayContributor == null) {
			return null;
		}

		return assetDisplayContributor.getLabel(_themeDisplay.getLocale());
	}

	private SoyContext _getSelectedMappingTypeLabel() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getClassNameId() <= 0)) {

			return SoyContextFactoryUtil.createSoyContext();
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put("type", _getMappingTypeLabel());

		if (layoutPageTemplateEntry.getClassTypeId() > 0) {
			soyContext.put("subtype", _getMappingSubtypeLabel());
		}

		return soyContext;
	}

	private SoyContext _getSidebarTab(String label) {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put("id", label);
		soyContext.put(
			"label", LanguageUtil.get(_themeDisplay.getLocale(), label));

		return soyContext;
	}

	private List<SoyContext> _getSidebarTabs() {
		List<SoyContext> soyContexts = new ArrayList<>();

		soyContexts.add(_getSidebarTab("available"));

		if (_showMapping) {
			soyContexts.add(_getSidebarTab("mapping"));
		}

		return soyContexts;
	}

	private List<SoyContext> _getSoyContextFragmentCollections() {
		List<SoyContext> soyContexts = new ArrayList<>();

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(
				_themeDisplay.getScopeGroupId());

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			List<FragmentEntry> fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntries(
					fragmentCollection.getFragmentCollectionId(),
					WorkflowConstants.STATUS_APPROVED);

			if (ListUtil.isEmpty(fragmentEntries)) {
				continue;
			}

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentCollectionId",
				fragmentCollection.getFragmentCollectionId());
			soyContext.put(
				"fragmentEntries",
				_getFragmentEntriesSoyContext(fragmentEntries));
			soyContext.put("name", fragmentCollection.getName());

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private List<SoyContext> _getSoyContextFragmentEntryLinks()
		throws PortalException {

		List<SoyContext> soyContexts = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				_themeDisplay.getScopeGroupId(), _classNameId, _classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			FragmentEntry fragmentEntry =
				FragmentEntryServiceUtil.fetchFragmentEntry(
					fragmentEntryLink.getFragmentEntryId());

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.putHTML(
				"content",
				FragmentEntryRenderUtil.renderFragmentEntryLink(
					fragmentEntryLink, _request,
					PortalUtil.getHttpServletResponse(_renderResponse)));
			soyContext.put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues()));
			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());
			soyContext.put(
				"fragmentEntryLinkId",
				fragmentEntryLink.getFragmentEntryLinkId());
			soyContext.put("name", fragmentEntry.getName());
			soyContext.put("position", fragmentEntryLink.getPosition());

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private ItemSelectorCriterion _getURLItemSelectorCriterion() {
		ItemSelectorCriterion urlItemSelectorCriterion =
			new URLItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		urlItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return urlItemSelectorCriterion;
	}

	private final AssetDisplayContributorTracker
		_assetDisplayContributorTracker;
	private final long _classNameId;
	private final long _classPK;
	private final ItemSelector _itemSelector;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final boolean _showMapping;
	private final ThemeDisplay _themeDisplay;

}