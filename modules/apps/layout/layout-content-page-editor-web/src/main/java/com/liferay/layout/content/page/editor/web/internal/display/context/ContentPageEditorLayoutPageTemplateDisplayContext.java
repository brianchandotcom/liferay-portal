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

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.fragment.constants.FragmentEntryTypeConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ContentPageEditorLayoutPageTemplateDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageEditorLayoutPageTemplateDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK, boolean showMapping) {

		super(request, renderResponse);

		_classPK = classPK;
		_showMapping = showMapping;

		_assetDisplayContributorTracker =
			(AssetDisplayContributorTracker)request.getAttribute(
				ContentPageEditorWebKeys.ASSET_DISPLAY_CONTRIBUTOR_TRACKER);
		_classNameId = PortalUtil.getClassNameId(className);
	}

	public SoyContext getEditorContext() throws PortalException {
		if (_soyContext != null) {
			return _soyContext;
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			getFragmentEntryActionURL("/layout/add_fragment_entry_link"));
		soyContext.put("availableLanguages", getAvailableLanguagesSoyContext());
		soyContext.put("classNameId", _classNameId);
		soyContext.put("classPK", _classPK);
		soyContext.put(
			"defaultEditorConfigurations", getDefaultConfigurations());
		soyContext.put("defaultLanguageId", themeDisplay.getLanguageId());
		soyContext.put(
			"deleteFragmentEntryLinkURL",
			getFragmentEntryActionURL("/layout/delete_fragment_entry_link"));
		soyContext.put(
			"editFragmentEntryLinkURL",
			getFragmentEntryActionURL("/layout/edit_fragment_entry_link"));
		soyContext.put(
			"elements",
			getSoyContextFragmentCollections(
				FragmentEntryTypeConstants.TYPE_ELEMENT));
		soyContext.put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks());
		soyContext.put(
			"getAssetDisplayContributorsURL",
			getFragmentEntryActionURL(
				"/layout/get_asset_display_contributors"));
		soyContext.put(
			"getAssetClassTypesURL",
			getFragmentEntryActionURL("/layout/get_asset_class_types"));
		soyContext.put("imageSelectorURL", getItemSelectorURL());
		soyContext.put("languageId", themeDisplay.getLanguageId());
		soyContext.put("lastSaveDate", StringPool.BLANK);
		soyContext.put(
			"layoutData", JSONFactoryUtil.createJSONObject(_getLayoutData()));

		if (_showMapping) {
			soyContext.put(
				"mappingFieldsURL",
				getFragmentEntryActionURL("/layout/get_mapping_fields"));
		}

		soyContext.put("panels", _getSoyContextPanels());
		soyContext.put("portletNamespace", renderResponse.getNamespace());

		if (_classNameId == PortalUtil.getClassNameId(
				LayoutPageTemplateEntry.class)) {

			soyContext.put(
				"publishLayoutPageTemplateEntryURL",
				getFragmentEntryActionURL(
					"/layout/publish_layout_page_template_entry"));
		}

		soyContext.put(
			"renderFragmentEntryURL",
			getFragmentEntryActionURL("/layout/render_fragment_entry"));
		soyContext.put(
			"sections",
			getSoyContextFragmentCollections(
				FragmentEntryTypeConstants.TYPE_SECTION));

		if (_showMapping) {
			soyContext.put("selectedMappingTypes", _getSelectedMappingTypes());
		}

		soyContext.put("redirectURL", getRedirect());
		soyContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry != null) &&
			(layoutPageTemplateEntry.getStatus() !=
				WorkflowConstants.STATUS_APPROVED)) {

			String statusLabel = WorkflowConstants.getStatusLabel(
				layoutPageTemplateEntry.getStatus());

			soyContext.put("status", LanguageUtil.get(request, statusLabel));
		}

		soyContext.put("themeColors", getThemeColors());
		soyContext.put(
			"updateLayoutPageTemplateDataURL",
			getFragmentEntryActionURL(
				"/layout/update_layout_page_template_data"));
		soyContext.put(
			"updateLayoutPageTemplateEntryAssetTypeURL",
			getFragmentEntryActionURL(
				"/layout/update_layout_page_template_entry_asset_type"));

		_soyContext = soyContext;

		return _soyContext;
	}

	private String _getLayoutData() throws PortalException {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), _classNameId, _classPK,
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
			layoutPageTemplateEntry.getClassTypeId(), themeDisplay.getLocale());

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

		return assetDisplayContributor.getLabel(themeDisplay.getLocale());
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

	private SoyContext _getSoyContextFragmentEntryLinks()
		throws PortalException {

		SoyContext soyContexts = SoyContextFactoryUtil.createSoyContext();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				getGroupId(), _classNameId, _classPK);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

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
						fragmentEntryLink, request,
						PortalUtil.getHttpServletResponse(renderResponse)));
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
			themeDisplay.setIsolated(isolated);
		}

		return soyContexts;
	}

	private List<SoyContext> _getSoyContextPanels() {
		List<SoyContext> soyContexts = new ArrayList<>();

		SoyContext availableSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "cards");
		availableSoyContext.put(
			"label", LanguageUtil.get(themeDisplay.getLocale(), "sections"));
		availableSoyContext.put("panelId", "sections");

		soyContexts.add(availableSoyContext);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", request.getLocale(), getClass());

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
				"label", LanguageUtil.get(themeDisplay.getLocale(), "mapping"));
			availableSoyContext.put("panelId", "mapping");

			soyContexts.add(availableSoyContext);
		}

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "pages-tree");
		availableSoyContext.put(
			"label", LanguageUtil.get(themeDisplay.getLocale(), "structure"));
		availableSoyContext.put("panelId", "structure");

		soyContexts.add(availableSoyContext);

		return soyContexts;
	}

	private final AssetDisplayContributorTracker
		_assetDisplayContributorTracker;
	private final long _classNameId;
	private final long _classPK;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final boolean _showMapping;
	private SoyContext _soyContext;

}