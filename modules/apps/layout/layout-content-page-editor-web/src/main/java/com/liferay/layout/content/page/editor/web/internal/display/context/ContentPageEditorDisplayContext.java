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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.DownloadURLItemSelectorReturnType;
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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletCategoryComparator;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.segments.constants.SegmentsConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Eudaldo Alonso
 */
public class ContentPageEditorDisplayContext {

	public ContentPageEditorDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK,
		FragmentRendererController fragmentRendererController) {

		this.request = request;
		_renderResponse = renderResponse;
		this.classPK = classPK;

		classNameId = PortalUtil.getClassNameId(className);
		infoDisplayContributorTracker =
			(InfoDisplayContributorTracker)request.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER);
		themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_fragmentRendererController = fragmentRendererController;
		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)request.getAttribute(
				ContentPageEditorWebKeys.
					FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_fragmentRendererTracker =
			(FragmentRendererTracker)request.getAttribute(
				FragmentActionKeys.FRAGMENT_RENDERER_TRACKER);
		_itemSelector = (ItemSelector)request.getAttribute(
			ContentPageEditorWebKeys.ITEM_SELECTOR);
	}

	public SoyContext getEditorSoyContext() throws Exception {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			getFragmentEntryActionURL("/content_layout/add_fragment_entry_link")
		).put(
			"addPortletURL",
			getFragmentEntryActionURL("/content_layout/add_portlet")
		).put(
			"assetBrowserLinks", _getAssetBrowserLinksSoyContexts()
		).put(
			"availableLanguages", _getAvailableLanguagesSoyContext()
		).put(
			"classNameId", classNameId
		).put(
			"classPK", classPK
		).put(
			"defaultEditorConfigurations", _getDefaultConfigurations()
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale())
		).put(
			"deleteFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/delete_fragment_entry_link")
		);

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			soyContext.put(
				"discardDraftRedirectURL", themeDisplay.getURLCurrent()
			).put(
				"discardDraftURL",
				getFragmentEntryActionURL(
					"/content_layout/discard_draft_layout")
			);
		}

		soyContext.put(
			"editFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/edit_fragment_entry_link")
		).put(
			"elements",
			_getSoyContextFragmentCollections(FragmentConstants.TYPE_COMPONENT)
		).put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks()
		);

		ResourceURL getAssetFieldValueURL = _renderResponse.createResourceURL();

		getAssetFieldValueURL.setResourceID(
			"/content_layout/get_asset_field_value");

		soyContext.put(
			"getAssetFieldValueURL", getAssetFieldValueURL.toString());

		ResourceURL getAssetMappingFieldsURL =
			_renderResponse.createResourceURL();

		getAssetMappingFieldsURL.setResourceID(
			"/content_layout/get_asset_mapping_fields");

		soyContext.put(
			"getAssetMappingFieldsURL", getAssetMappingFieldsURL.toString()
		).put(
			"imageSelectorURL", _getItemSelectorURL()
		).put(
			"languageId", themeDisplay.getLanguageId()
		).put(
			"layoutData", JSONFactoryUtil.createJSONObject(_getLayoutData())
		).put(
			"mappedAssetEntries", _getMappedAssetEntriesSoyContexts()
		).put(
			"portletNamespace", _renderResponse.getNamespace()
		);

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			soyContext.put(
				"publishURL",
				getFragmentEntryActionURL("/content_layout/publish_layout"));
		}

		soyContext.put(
			"renderFragmentEntryURL",
			getFragmentEntryActionURL("/content_layout/render_fragment_entry")
		).put(
			"redirectURL", _getRedirect()
		).put(
			"sections",
			_getSoyContextFragmentCollections(FragmentConstants.TYPE_SECTION)
		).put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"themeColorsCssClasses", _getThemeColorsCssClasses()
		).put(
			"updateLayoutPageTemplateDataURL",
			getFragmentEntryActionURL(
				"/content_layout/update_layout_page_template_data")
		).put(
			"widgets", _getWidgetsSoyContexts()
		);

		return soyContext;
	}

	public SoyContext getFragmentsEditorToolbarSoyContext()
		throws PortalException {

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"availableLanguages", _getAvailableLanguagesSoyContext()
		).put(
			"classPK", themeDisplay.getPlid()
		).put(
			"defaultLanguageId", themeDisplay.getLanguageId()
		);

		boolean draft = false;

		Layout draftLayout = LayoutLocalServiceUtil.getLayout(classPK);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			draftLayout.getClassPK());

		Date modifiedDate = draftLayout.getModifiedDate();

		Date publishDate = layout.getPublishDate();

		if (publishDate == null) {
			publishDate = modifiedDate;
		}

		draft = modifiedDate.after(publishDate);

		soyContext.put(
			"draft", draft
		).put(
			"lastSaveDate", StringPool.BLANK
		).put(
			"portletNamespace", _renderResponse.getNamespace()
		).put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		);

		return soyContext;
	}

	protected String getFragmentEntryActionURL(String action) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return HttpUtil.addParameter(
			actionURL.toString(), "p_l_mode", Constants.EDIT);
	}

	protected long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			request, "groupId", themeDisplay.getScopeGroupId());

		return _groupId;
	}

	protected List<SoyContext> getSidebarPanelSoyContexts(boolean showMapping) {
		if (_sidebarPanelSoyContexts != null) {
			return _sidebarPanelSoyContexts;
		}

		List<SoyContext> soyContexts = new ArrayList<>();

		SoyContext availableSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put("icon", "cards-full");

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		availableSoyContext.put(
			"label", LanguageUtil.get(resourceBundle, "sections")
		).put(
			"sidebarPanelId", "sections"
		);

		soyContexts.add(availableSoyContext);

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put(
			"icon", "cards2"
		).put(
			"label", LanguageUtil.get(resourceBundle, "section-builder")
		).put(
			"sidebarPanelId", "elements"
		);

		soyContexts.add(availableSoyContext);

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put(
			"icon", "square-hole"
		).put(
			"label", LanguageUtil.get(resourceBundle, "widgets")
		).put(
			"sidebarPanelId", "widgets"
		);

		soyContexts.add(availableSoyContext);

		if (showMapping) {
			availableSoyContext = SoyContextFactoryUtil.createSoyContext();

			availableSoyContext.put(
				"icon", "bolt"
			).put(
				"label", LanguageUtil.get(themeDisplay.getLocale(), "mapping")
			).put(
				"sidebarPanelId", "mapping"
			);

			soyContexts.add(availableSoyContext);
		}

		availableSoyContext = SoyContextFactoryUtil.createSoyContext();

		availableSoyContext.put(
			"icon", "pages-tree"
		).put(
			"label", LanguageUtil.get(resourceBundle, "page-structure")
		).put(
			"sidebarPanelId", "page-structure"
		);

		soyContexts.add(availableSoyContext);

		_sidebarPanelSoyContexts = soyContexts;

		return _sidebarPanelSoyContexts;
	}

	protected final long classNameId;
	protected final long classPK;
	protected final InfoDisplayContributorTracker infoDisplayContributorTracker;
	protected final HttpServletRequest request;
	protected final ThemeDisplay themeDisplay;

	private List<SoyContext> _getAssetBrowserLinksSoyContexts()
		throws Exception {

		if (_assetBrowserLinksSoyContexts != null) {
			return _assetBrowserLinksSoyContexts;
		}

		List<SoyContext> soyContexts = new ArrayList<>();

		List<InfoDisplayContributor> infoDisplayContributors =
			infoDisplayContributorTracker.getInfoDisplayContributors();

		for (InfoDisplayContributor infoDisplayContributor :
				infoDisplayContributors) {

			if (infoDisplayContributor == null) {
				continue;
			}

			PortletURL assetBrowserURL = PortletProviderUtil.getPortletURL(
				request, infoDisplayContributor.getClassName(),
				PortletProvider.Action.BROWSE);

			if (assetBrowserURL == null) {
				continue;
			}

			SoyContext assetBrowserSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			assetBrowserURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
			assetBrowserURL.setParameter(
				"selectedGroupIds",
				String.valueOf(themeDisplay.getScopeGroupId()));
			assetBrowserURL.setParameter(
				"typeSelection", infoDisplayContributor.getClassName());
			assetBrowserURL.setParameter(
				"showNonindexable", String.valueOf(Boolean.TRUE));
			assetBrowserURL.setParameter(
				"showScheduled", String.valueOf(Boolean.TRUE));
			assetBrowserURL.setParameter(
				"eventName", _renderResponse.getNamespace() + "selectAsset");
			assetBrowserURL.setPortletMode(PortletMode.VIEW);
			assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

			assetBrowserSoyContext.put(
				"href", assetBrowserURL.toString()
			).put(
				"typeName",
				infoDisplayContributor.getLabel(themeDisplay.getLocale())
			);

			soyContexts.add(assetBrowserSoyContext);
		}

		_assetBrowserLinksSoyContexts = soyContexts;

		return _assetBrowserLinksSoyContexts;
	}

	private SoyContext _getAvailableLanguagesSoyContext() {
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

	private Map<String, Object> _getDefaultConfigurations() {
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

	private List<SoyContext> _getFragmentEntriesSoyContext(
		List<FragmentEntry> fragmentEntries) {

		List<SoyContext> soyContexts = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
			).put(
				"imagePreviewURL",
				fragmentEntry.getImagePreviewURL(themeDisplay)
			).put(
				"name", fragmentEntry.getName()
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private SoyContext _getFragmentEntrySoyContext(
		FragmentEntry fragmentEntry, String content) {

		if (fragmentEntry != null) {
			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId()
			).put(
				"name", fragmentEntry.getName()
			);

			return soyContext;
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"fragmentEntryId", 0
		).put(
			"name", StringPool.BLANK
		);

		String portletId = _getPortletId(content);

		PortletConfig portletConfig = PortletConfigFactoryUtil.get(portletId);

		if (portletConfig == null) {
			return soyContext;
		}

		soyContext.put(
			"name",
			PortalUtil.getPortletTitle(portletId, themeDisplay.getLocale())
		).put(
			"portletId", portletId
		);

		return soyContext;
	}

	private ItemSelectorCriterion _getImageItemSelectorCriterion() {
		if (_imageItemSelectorCriterion != null) {
			return _imageItemSelectorCriterion;
		}

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new DownloadURLItemSelectorReturnType());

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_imageItemSelectorCriterion = imageItemSelectorCriterion;

		return _imageItemSelectorCriterion;
	}

	private String _getItemSelectorURL() {
		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(request),
			_renderResponse.getNamespace() + "selectImage",
			_getImageItemSelectorCriterion(), _getURLItemSelectorCriterion());

		return itemSelectorURL.toString();
	}

	private String _getLayoutData() throws PortalException {
		if (_layoutData != null) {
			return _layoutData;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), classNameId, classPK, true);

		_layoutData = layoutPageTemplateStructure.getData(
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		return _layoutData;
	}

	private Set<SoyContext> _getMappedAssetEntriesSoyContexts()
		throws Exception {

		Set<SoyContext> mappedAssetEntriesSoyContexts = new HashSet<>();

		List<Long> mappedClassPKs = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				getGroupId(), classNameId, classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			Iterator<String> keysIterator = editableValuesJSONObject.keys();

			while (keysIterator.hasNext()) {
				String key = keysIterator.next();

				JSONObject editableProcessorJSONObject =
					editableValuesJSONObject.getJSONObject(key);

				if (editableProcessorJSONObject == null) {
					continue;
				}

				Iterator<String> editableKeysIterator =
					editableProcessorJSONObject.keys();

				while (editableKeysIterator.hasNext()) {
					String editableKey = editableKeysIterator.next();

					JSONObject editableJSONObject =
						editableProcessorJSONObject.getJSONObject(editableKey);

					if (!editableJSONObject.has("classNameId") ||
						!editableJSONObject.has("classPK") ||
						!editableJSONObject.has("fieldId")) {

						continue;
					}

					long classPK = editableJSONObject.getLong("classPK");

					if (mappedClassPKs.contains(classPK)) {
						continue;
					}

					mappedClassPKs.add(classPK);

					long classNameId = editableJSONObject.getLong(
						"classNameId");

					AssetEntry assetEntry =
						AssetEntryLocalServiceUtil.fetchEntry(
							classNameId, classPK);

					if (assetEntry == null) {
						continue;
					}

					SoyContext mappedAssetEntrySoyContext =
						SoyContextFactoryUtil.createSoyContext();

					mappedAssetEntrySoyContext.put(
						"classNameId", classNameId
					).put(
						"classPK", classPK
					).put(
						"title", assetEntry.getTitle(themeDisplay.getLocale())
					);

					mappedAssetEntriesSoyContexts.add(
						mappedAssetEntrySoyContext);
				}
			}
		}

		return mappedAssetEntriesSoyContexts;
	}

	private String _getPortletCategoryTitle(PortletCategory portletCategory) {
		String title = LanguageUtil.get(request, portletCategory.getName());

		if (Validator.isNotNull(title)) {
			return title;
		}

		for (String portletId :
				PortletCategoryUtil.getFirstChildPortletIds(portletCategory)) {

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			PortletApp portletApp = portlet.getPortletApp();

			if (!portletApp.isWARFile()) {
				continue;
			}

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, request.getServletContext());

			ResourceBundle portletResourceBundle =
				portletConfig.getResourceBundle(themeDisplay.getLocale());

			return ResourceBundleUtil.getString(
				portletResourceBundle, portletCategory.getName());
		}

		return StringPool.BLANK;
	}

	private String _getPortletId(String content) {
		Document document = Jsoup.parse(content);

		Elements elements = document.getElementsByAttributeValueStarting(
			"id", "portlet_");

		if (elements.size() != 1) {
			return StringPool.BLANK;
		}

		Element element = elements.get(0);

		String id = element.id();

		return PortletIdCodec.decodePortletName(id.substring(8));
	}

	private List<SoyContext> _getPortletsContexts(
		PortletCategory portletCategory) {

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		return stream.map(
			portletId -> PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId)
		).filter(
			portlet -> {
				if (portlet == null) {
					return false;
				}

				try {
					return PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getLayout(), portlet,
						ActionKeys.ADD_TO_PAGE);
				}
				catch (PortalException pe) {
					_log.error(
						"Unable to check portlet permissions for " +
							portlet.getPortletId(),
						pe);

					return false;
				}
			}
		).sorted(
			new PortletTitleComparator(servletContext, themeDisplay.getLocale())
		).map(
			portlet -> {
				SoyContext portletSoyContext =
					SoyContextFactoryUtil.createSoyContext();

				portletSoyContext.put(
					"instanceable", portlet.isInstanceable()
				).put(
					"portletId", portlet.getPortletId()
				).put(
					"title",
					PortalUtil.getPortletTitle(
						portlet, servletContext, themeDisplay.getLocale())
				).put(
					"used", _isUsed(portlet, themeDisplay.getPlid())
				);

				return portletSoyContext;
			}
		).collect(
			Collectors.toList()
		);
	}

	private String _getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(request, "redirect");

		if (Validator.isNull(_redirect)) {
			_redirect = ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(request), "p_l_back_url",
				themeDisplay.getURLCurrent());
		}

		return _redirect;
	}

	private List<SoyContext> _getSoyContextContributedFragmentCollections(
		int type) {

		List<SoyContext> soyContexts = new ArrayList<>();

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			List<FragmentEntry> fragmentEntries =
				fragmentCollectionContributor.getFragmentEntries(type);

			if (ListUtil.isEmpty(fragmentEntries)) {
				continue;
			}

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentCollectionId",
				fragmentCollectionContributor.getFragmentCollectionKey()
			).put(
				"fragmentEntries",
				_getFragmentEntriesSoyContext(fragmentEntries)
			).put(
				"name", fragmentCollectionContributor.getName()
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private SoyContext _getSoyContextDynamicFragments(int type) {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		List<SoyContext> soyContextDynamicFragments = new ArrayList<>();

		List<FragmentRenderer> fragmentRenderers =
			_fragmentRendererTracker.getFragmentRenderers(type);

		for (FragmentRenderer fragmentRenderer : fragmentRenderers) {
			if (!fragmentRenderer.isSelectable()) {
				continue;
			}

			SoyContext soyContextDynamicFragment =
				SoyContextFactoryUtil.createSoyContext();

			soyContextDynamicFragment.put(
				"fragmentEntryKey", fragmentRenderer.getKey()
			).put(
				"imagePreviewURL",
				fragmentRenderer.getImagePreviewURL(null, request)
			).put(
				"name", fragmentRenderer.getLabel(null)
			);

			soyContextDynamicFragments.add(soyContextDynamicFragment);
		}

		if (soyContextDynamicFragments.isEmpty()) {
			return soyContext;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		soyContext.put(
			"fragmentCollectionId", "dynamic-collection"
		).put(
			"fragmentEntries", soyContextDynamicFragments
		).put(
			"name", LanguageUtil.get(resourceBundle, "dynamic-collection")
		);

		return soyContext;
	}

	private List<SoyContext> _getSoyContextFragmentCollections(int type) {
		List<SoyContext> soyContexts =
			_getSoyContextContributedFragmentCollections(type);

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
				fragmentCollection.getFragmentCollectionId()
			).put(
				"fragmentEntries",
				_getFragmentEntriesSoyContext(fragmentEntries)
			).put(
				"name", fragmentCollection.getName()
			);

			soyContexts.add(soyContext);
		}

		SoyContext soyContextDynamicFragments =
			_getSoyContextDynamicFragments(type);

		if(!soyContextDynamicFragments.isEmpty()) {
			soyContexts.add(_getSoyContextDynamicFragments(type));
		}

		return soyContexts;
	}

	private SoyContext _getSoyContextFragmentEntryLinks()
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

		long[] segmentsExperienceIds = {
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT
		};

		try {
			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				FragmentEntry fragmentEntry =
					FragmentEntryServiceUtil.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				SoyContext soyContext =
					SoyContextFactoryUtil.createSoyContext();

				DefaultFragmentRendererContext fragmentRendererContext =
					new DefaultFragmentRendererContext(fragmentEntryLink);

				fragmentRendererContext.setLocale(themeDisplay.getLocale());
				fragmentRendererContext.setMode(
					FragmentEntryLinkConstants.EDIT);
				fragmentRendererContext.setSegmentsExperienceIds(
					segmentsExperienceIds);

				String content = _fragmentRendererController.render(
					fragmentRendererContext, request,
					PortalUtil.getHttpServletResponse(_renderResponse));

				JSONObject editableValuesJSONObject =
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues());

				boolean error = false;

				if (SessionErrors.contains(
						request, "fragmentEntryInvalidContent")) {

					error = true;

					SessionErrors.clear(request);
				}

				soyContext.putHTML(
					"content", content
				).put(
					"editableValues", editableValuesJSONObject
				).put(
					"error", error
				).put(
					"fragmentEntryLinkId",
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
				).putAll(
					_getFragmentEntrySoyContext(fragmentEntry, content)
				);

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

	private String[] _getThemeColorsCssClasses() {
		Theme theme = themeDisplay.getTheme();

		String colorPalette = theme.getSetting("color-palette");

		if (Validator.isNotNull(colorPalette)) {
			return StringUtil.split(colorPalette);
		}

		return new String[] {
			"primary", "success", "danger", "warning", "info", "dark",
			"gray-dark", "secondary", "light", "lighter", "white"
		};
	}

	private ItemSelectorCriterion _getURLItemSelectorCriterion() {
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

	private List<SoyContext> _getWidgetCategoriesContexts(
		PortletCategory portletCategory) {

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		Stream<PortletCategory> stream = portletCategories.stream();

		return stream.sorted(
			new PortletCategoryComparator(themeDisplay.getLocale())
		).filter(
			category -> !category.isHidden()
		).map(
			category -> {
				SoyContext categoryContext =
					SoyContextFactoryUtil.createSoyContext();

				categoryContext.put(
					"categories", _getWidgetCategoriesContexts(category)
				).put(
					"path",
					StringUtil.replace(
						category.getPath(), new String[] {"/", "."},
						new String[] {"-", "-"})
				).put(
					"portlets", _getPortletsContexts(category)
				).put(
					"title", _getPortletCategoryTitle(category)
				);

				return categoryContext;
			}
		).collect(
			Collectors.toList()
		);
	}

	private List<SoyContext> _getWidgetsSoyContexts() throws Exception {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = PortletCategoryUtil.getRelevantPortletCategory(
			themeDisplay.getPermissionChecker(), themeDisplay.getCompanyId(),
			themeDisplay.getLayout(), portletCategory,
			themeDisplay.getLayoutTypePortlet());

		return _getWidgetCategoriesContexts(portletCategory);
	}

	private boolean _isUsed(Portlet portlet, long plid) {
		if (portlet.isInstanceable()) {
			return false;
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
				portlet.getPortletId());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentPageEditorDisplayContext.class);

	private List<SoyContext> _assetBrowserLinksSoyContexts;
	private Map<String, Object> _defaultConfigurations;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentRendererController _fragmentRendererController;
	private final FragmentRendererTracker _fragmentRendererTracker;
	private Long _groupId;
	private ItemSelectorCriterion _imageItemSelectorCriterion;
	private final ItemSelector _itemSelector;
	private String _layoutData;
	private String _redirect;
	private final RenderResponse _renderResponse;
	private List<SoyContext> _sidebarPanelSoyContexts;
	private SoyContext _soyContextFragmentEntryLinksSoyContext;
	private ItemSelectorCriterion _urlItemSelectorCriterion;

}