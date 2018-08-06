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

package com.liferay.layout.type.controller.content.internal.display.context;

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
import com.liferay.layout.type.controller.content.internal.constants.ContentLayoutTypeControllerWebKeys;
import com.liferay.layout.type.controller.content.internal.util.SoyContextFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.utils.SoyContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentsEditorDisplayContext {

	public FragmentsEditorDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;

		_itemSelector = (ItemSelector)request.getAttribute(
			ContentLayoutTypeControllerWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SoyContext getEditorContext() throws PortalException {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			_getFragmentEntryActionURL(
				"/content_layout/add_fragment_entry_link"));

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

		soyContext.put(
			"classNameId", PortalUtil.getClassNameId(Layout.class.getName()));
		soyContext.put("classPK", _themeDisplay.getPlid());
		soyContext.put(
			"defaultEditorConfigurations", _getDefaultConfigurations());
		soyContext.put("defaultLanguageId", _themeDisplay.getLanguageId());
		soyContext.put(
			"deleteFragmentEntryLinkURL",
			_getFragmentEntryActionURL(
				"/content_layout/delete_fragment_entry_link"));
		soyContext.put(
			"editFragmentEntryLinkURL",
			_getFragmentEntryActionURL(
				"/content_layout/edit_fragment_entry_link"));
		soyContext.put(
			"fragmentCollections", _getSoyContextFragmentCollections());
		soyContext.put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_request),
			_renderResponse.getNamespace() + "selectImage",
			_getImageItemSelectorCriterion(), _getURLItemSelectorCriterion());

		soyContext.put("imageSelectorURL", itemSelectorURL.toString());

		soyContext.put("languageId", _themeDisplay.getLanguageId());
		soyContext.put("portletNamespace", _renderResponse.getNamespace());
		soyContext.put(
			"renderFragmentEntryURL",
			_getFragmentEntryActionURL(
				"/content_layout/render_fragment_entry"));
		soyContext.put("sidebarTabs", _getSidebarTabs());

		String redirect = ParamUtil.getString(_request, "redirect");

		soyContext.put("redirectURL", redirect);

		soyContext.put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		soyContext.put(
			"updateFragmentEntryLinksURL",
			_getFragmentEntryActionURL(
				"/content_layout/update_fragment_entry_links"));

		return soyContext;
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

	private List<SoyContext> _getSidebarTabs() {
		List<SoyContext> soyContexts = new ArrayList<>();

		SoyContext availableSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("enabled", true);
		availableSoyContext.put("id", "available");
		availableSoyContext.put(
			"label", LanguageUtil.get(_themeDisplay.getLocale(), "available"));

		soyContexts.add(availableSoyContext);

		SoyContext addedSoyContext = SoyContextFactoryUtil.createSoyContext();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				_themeDisplay.getPlid());

		addedSoyContext.put("enabled", !fragmentEntryLinks.isEmpty());

		addedSoyContext.put("id", "added");
		addedSoyContext.put(
			"label", LanguageUtil.get(_themeDisplay.getLocale(), "added"));

		soyContexts.add(addedSoyContext);

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
					_themeDisplay.getScopeGroupId(),
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
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				_themeDisplay.getPlid());

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
					fragmentEntryLink.getFragmentEntryLinkId());
				soyContext.put("name", fragmentEntry.getName());
				soyContext.put("position", fragmentEntryLink.getPosition());

				soyContexts.add(soyContext);
			}
		}
		finally {
			_themeDisplay.setIsolated(isolated);
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

	private final ItemSelector _itemSelector;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}