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
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
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
 * @author Eudaldo Alonso
 */
public class ContentPageEditorDisplayContext {

	public ContentPageEditorDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK) {

		this.request = request;
		this.renderResponse = renderResponse;
		this.classPK = classPK;

		classNameId = PortalUtil.getClassNameId(className);
		themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_itemSelector = (ItemSelector)request.getAttribute(
			ContentPageEditorWebKeys.ITEM_SELECTOR);
	}

	protected SoyContext getAvailableLanguagesSoyContext() {
		SoyContext availableLanguagesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		String[] languageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()));

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

		return availableLanguagesSoyContext;
	}

	protected Map<String, Object> getDefaultConfigurations() {
		if (_defaultConfigurations != null) {
			return _defaultConfigurations;
		}

		Map<String, Object> configurations = new HashMap<>();

		EditorConfiguration richTextEditorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
				"fragmenEntryLinkRichTextEditor", StringPool.BLANK,
				Collections.emptyMap(), themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(request));

		configurations.put("rich-text", richTextEditorConfiguration.getData());

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
				"fragmenEntryLinkEditor", StringPool.BLANK,
				Collections.emptyMap(), themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(request));

		configurations.put("text", editorConfiguration.getData());

		_defaultConfigurations = configurations;

		return _defaultConfigurations;
	}

	protected String getFragmentEntryActionURL(String action) {
		PortletURL actionURL = renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return actionURL.toString();
	}

	protected SoyContext getFragmentEntrySoyContext(
		FragmentEntry fragmentEntry, String content) {

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put("fragmentEntryId", fragmentEntry.getFragmentEntryId());
		soyContext.put("name", fragmentEntry.getName());

		return soyContext;
	}

	protected long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			request, "groupId", themeDisplay.getScopeGroupId());

		return _groupId;
	}

	protected ItemSelectorCriterion getImageItemSelectorCriterion() {
		if (_imageItemSelectorCriterion != null) {
			return _imageItemSelectorCriterion;
		}

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_imageItemSelectorCriterion = imageItemSelectorCriterion;

		return _imageItemSelectorCriterion;
	}

	protected String getItemSelectorURL() {
		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(request),
			renderResponse.getNamespace() + "selectImage",
			getImageItemSelectorCriterion(), getURLItemSelectorCriterion());

		return itemSelectorURL.toString();
	}

	protected String getLayoutData() throws PortalException {
		if (_layoutData != null) {
			return _layoutData;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), classNameId, classPK, true);

		_layoutData = layoutPageTemplateStructure.getData();

		return _layoutData;
	}

	protected List<SoyContext> getPanelSoyContexts(
		boolean showMapping, boolean showWidgets) {

		if (_panelSoyContexts != null) {
			return _panelSoyContexts;
		}

		List<SoyContext> soyContexts = new ArrayList<>();

		SoyContext availableSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "cards");

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		availableSoyContext.put(
			"label", LanguageUtil.get(resourceBundle, "sections"));

		availableSoyContext.put("panelId", "sections");

		soyContexts.add(availableSoyContext);

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "page-template");
		availableSoyContext.put(
			"label", LanguageUtil.get(resourceBundle, "section-builder"));
		availableSoyContext.put("panelId", "elements");

		soyContexts.add(availableSoyContext);

		if (showMapping) {
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
			"label", LanguageUtil.get(resourceBundle, "structure"));
		availableSoyContext.put("panelId", "structure");

		soyContexts.add(availableSoyContext);

		if (showWidgets) {
			availableSoyContext = SoyContextFactoryUtil.createSoyContext();

			availableSoyContext.put("icon", "chip");
			availableSoyContext.put(
				"label", LanguageUtil.get(resourceBundle, "widgets"));
			availableSoyContext.put("panelId", "widgets");

			soyContexts.add(availableSoyContext);
		}

		_panelSoyContexts = soyContexts;

		return _panelSoyContexts;
	}

	protected String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(request, "redirect");

		return _redirect;
	}

	protected List<SoyContext> getSoyContextFragmentCollections(int type) {
		List<SoyContext> soyContexts = new ArrayList<>();

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(getGroupId());

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			List<FragmentEntry> fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntriesByType(
					getGroupId(), fragmentCollection.getFragmentCollectionId(),
					type, WorkflowConstants.STATUS_APPROVED);

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

	protected SoyContext getSoyContextFragmentEntryLinks()
		throws PortalException {

		if (_soyContextFragmentEntryLinksSoyContext != null) {
			return _soyContextFragmentEntryLinksSoyContext;
		}

		SoyContext soyContexts = SoyContextFactoryUtil.createSoyContext();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				getGroupId(), classNameId, classPK);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		try {
			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				FragmentEntry fragmentEntry =
					FragmentEntryServiceUtil.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				SoyContext soyContext =
					SoyContextFactoryUtil.createSoyContext();

				String content =
					FragmentEntryRenderUtil.renderFragmentEntryLink(
						fragmentEntryLink, request,
						PortalUtil.getHttpServletResponse(renderResponse));

				soyContext.putHTML("content", content);

				soyContext.put(
					"editableValues",
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues()));
				soyContext.put(
					"fragmentEntryLinkId",
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));

				soyContext.putAll(
					getFragmentEntrySoyContext(fragmentEntry, content));

				soyContexts.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
					soyContext);
			}
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}

		_soyContextFragmentEntryLinksSoyContext = soyContexts;

		return _soyContextFragmentEntryLinksSoyContext;
	}

	protected String[] getThemeColors() {
		return new String[] {
			"#393A4A", "#6B6C7E", "#A7A9BC", "#CDCED8", "#E7E7ED", "#F4F5F8",
			"#435FFE", "#41A967", "#F35F60", "#F6BB54"
		};
	}

	protected ItemSelectorCriterion getURLItemSelectorCriterion() {
		if (_urlItemSelectorCriterion != null) {
			return _urlItemSelectorCriterion;
		}

		ItemSelectorCriterion urlItemSelectorCriterion =
			new URLItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		urlItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_urlItemSelectorCriterion = urlItemSelectorCriterion;

		return _urlItemSelectorCriterion;
	}

	protected final long classNameId;
	protected final long classPK;
	protected final RenderResponse renderResponse;
	protected final HttpServletRequest request;
	protected final ThemeDisplay themeDisplay;

	private List<SoyContext> _getFragmentEntriesSoyContext(
		List<FragmentEntry> fragmentEntries) {

		List<SoyContext> soyContexts = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());
			soyContext.put(
				"imagePreviewURL",
				fragmentEntry.getImagePreviewURL(themeDisplay));
			soyContext.put("name", fragmentEntry.getName());

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private Map<String, Object> _defaultConfigurations;
	private Long _groupId;
	private ItemSelectorCriterion _imageItemSelectorCriterion;
	private final ItemSelector _itemSelector;
	private String _layoutData;
	private List<SoyContext> _panelSoyContexts;
	private String _redirect;
	private SoyContext _soyContextFragmentEntryLinksSoyContext;
	private ItemSelectorCriterion _urlItemSelectorCriterion;

}