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
import com.liferay.fragment.constants.FragmentEntryTypeConstants;
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
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentsEditorDisplayContext {

	public FragmentsEditorDisplayContext(
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
		if (_soyContext != null) {
			return _soyContext;
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/add_fragment_entry_link"));

		SoyContext availableLanguagesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		String[] languageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(_themeDisplay.getSiteGroupId()));

		for (String languageId : languageIds) {
			SoyContext languageSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			String languageIcon = StringUtil.toLowerCase(
				languageId.replace(StringPool.UNDERLINE, StringPool.DASH));

			languageSoyContext.put("languageIcon", languageIcon);

			String languageLabel = languageId.replace(
				StringPool.UNDERLINE, StringPool.DASH);

			languageSoyContext.put("languageLabel", languageLabel);

			availableLanguagesSoyContext.put(languageId, languageSoyContext);
		}

		soyContext.put("availableLanguages", availableLanguagesSoyContext);

		soyContext.put("classNameId", _classNameId);
		soyContext.put("classPK", _classPK);
		soyContext.put(
			"defaultEditorConfigurations", _getDefaultConfigurations());
		soyContext.put("defaultLanguageId", _themeDisplay.getLanguageId());
		soyContext.put(
			"deleteFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/delete_fragment_entry_link"));
		soyContext.put(
			"editFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/edit_fragment_entry_link"));
		soyContext.put(
			"elements",
			_getSoyContextFragmentCollections(
				FragmentEntryTypeConstants.TYPE_ELEMENT));
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

		soyContext.put("languageId", _themeDisplay.getLanguageId());
		soyContext.put("lastSaveDate", StringPool.BLANK);
		soyContext.put(
			"layoutData", JSONFactoryUtil.createJSONObject(_getLayoutData()));

		if (_showMapping) {
			soyContext.put(
				"mappingFieldsURL",
				_getFragmentEntryActionURL("/layout/get_mapping_fields"));
		}

		soyContext.put("panels", _getSoyContextPanels());
		soyContext.put("portletNamespace", _renderResponse.getNamespace());

		if (_classNameId == PortalUtil.getClassNameId(
				LayoutPageTemplateEntry.class)) {

			soyContext.put(
				"publishLayoutPageTemplateEntryURL",
				_getFragmentEntryActionURL(
					"/layout/publish_layout_page_template_entry"));
		}

		soyContext.put(
			"renderFragmentEntryURL",
			_getFragmentEntryActionURL("/layout/render_fragment_entry"));
		soyContext.put(
			"sections",
			_getSoyContextFragmentCollections(
				FragmentEntryTypeConstants.TYPE_SECTION));

		if (_showMapping) {
			soyContext.put("selectedMappingTypes", _getSelectedMappingTypes());
		}

		String redirect = ParamUtil.getString(_request, "redirect");

		soyContext.put("redirectURL", redirect);

		soyContext.put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry != null) &&
			(layoutPageTemplateEntry.getStatus() !=
				WorkflowConstants.STATUS_APPROVED)) {

			String statusLabel = WorkflowConstants.getStatusLabel(
				layoutPageTemplateEntry.getStatus());

			soyContext.put("status", LanguageUtil.get(_request, statusLabel));
		}

		soyContext.put("themeColors", _getThemeColors());
		soyContext.put(
			"updateLayoutPageTemplateDataURL",
			_getFragmentEntryActionURL(
				"/layout/update_layout_page_template_data"));
		soyContext.put(
			"updateLayoutPageTemplateEntryAssetTypeURL",
			_getFragmentEntryActionURL(
				"/layout/update_layout_page_template_entry_asset_type"));

		_soyContext = soyContext;

		return _soyContext;
	}

	private Map<String, Object> _getDefaultConfigurations() {
		Map<String, Object> configurations = new HashMap<>();

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		EditorConfiguration richTextEditorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				PortletIdCodec.decodePortletName(portletDisplay.getId()),
				"fragmenEntryLinkRichTextEditor", StringPool.BLANK,
				Collections.emptyMap(), _themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(_request));

		configurations.put("rich-text", richTextEditorConfiguration.getData());

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				PortletIdCodec.decodePortletName(portletDisplay.getId()),
				"fragmenEntryLinkEditor", StringPool.BLANK,
				Collections.emptyMap(), _themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(_request));

		configurations.put("text", editorConfiguration.getData());

		return configurations;
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

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			_request, "groupId", _themeDisplay.getScopeGroupId());

		return _groupId;
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

	private String _getLayoutData() throws PortalException {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_themeDisplay.getScopeGroupId(), _classNameId, _classPK,
					true);

		return layoutPageTemplateStructure.getData();
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

	private SoyContext _getSelectedMappingTypes() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getClassNameId() <= 0)) {

			return SoyContextFactoryUtil.createSoyContext();
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		SoyContext typeSoyContext = SoyContextFactoryUtil.createSoyContext();

		typeSoyContext.put("id", layoutPageTemplateEntry.getClassNameId());
		typeSoyContext.put("label", _getMappingTypeLabel());

		soyContext.put("type", typeSoyContext);

		if (layoutPageTemplateEntry.getClassTypeId() > 0) {
			SoyContext subtypeSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			subtypeSoyContext.put(
				"id", layoutPageTemplateEntry.getClassTypeId());
			subtypeSoyContext.put("label", _getMappingSubtypeLabel());

			soyContext.put("subtype", subtypeSoyContext);
		}

		return soyContext;
	}

	private List<SoyContext> _getSoyContextFragmentCollections(int type) {
		List<SoyContext> soyContexts = new ArrayList<>();

		long groupId = _getGroupId();

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(groupId);

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			List<FragmentEntry> fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntriesByType(
					groupId, fragmentCollection.getFragmentCollectionId(), type,
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

	private SoyContext _getSoyContextFragmentEntryLinks()
		throws PortalException {

		SoyContext soyContexts = SoyContextFactoryUtil.createSoyContext();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				_getGroupId(), _classNameId, _classPK);

		boolean isolated = _themeDisplay.isIsolated();

		_themeDisplay.setIsolated(true);

		try {
			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				FragmentEntry fragmentEntry =
					FragmentEntryServiceUtil.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				SoyContext soyContext =
					SoyContextFactoryUtil.createSoyContext();

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
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));
				soyContext.put("name", fragmentEntry.getName());

				soyContexts.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
					soyContext);
			}
		}
		finally {
			_themeDisplay.setIsolated(isolated);
		}

		return soyContexts;
	}

	private List<SoyContext> _getSoyContextPanels() {
		List<SoyContext> soyContexts = new ArrayList<>();

		SoyContext availableSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "cards");
		availableSoyContext.put(
			"label", LanguageUtil.get(_themeDisplay.getLocale(), "sections"));
		availableSoyContext.put("panelId", "sections");

		soyContexts.add(availableSoyContext);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _request.getLocale(), getClass());

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "page-template");
		availableSoyContext.put(
			"label", LanguageUtil.get(resourceBundle, "section-builder"));
		availableSoyContext.put("panelId", "elements");

		soyContexts.add(availableSoyContext);

		if (_showMapping) {
			availableSoyContext = SoyContextFactoryUtil.createSoyContext();

			availableSoyContext.put("icon", "simulation-menu");
			availableSoyContext.put(
				"label",
				LanguageUtil.get(_themeDisplay.getLocale(), "mapping"));
			availableSoyContext.put("panelId", "mapping");

			soyContexts.add(availableSoyContext);
		}

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "pages-tree");
		availableSoyContext.put(
			"label", LanguageUtil.get(_themeDisplay.getLocale(), "structure"));
		availableSoyContext.put("panelId", "structure");

		soyContexts.add(availableSoyContext);

		return soyContexts;
	}

	private String[] _getThemeColors() {
		return new String[] {
			"#393a4a", "#6b6c7e", "#a7a9bc", "#cdced8", "#e7e7ed", "#f4f5f8",
			"#435ffe", "#41a967", "#f35f60", "#f6bb54"
		};
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
	private Long _groupId;
	private final ItemSelector _itemSelector;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final boolean _showMapping;
	private SoyContext _soyContext;
	private final ThemeDisplay _themeDisplay;

}