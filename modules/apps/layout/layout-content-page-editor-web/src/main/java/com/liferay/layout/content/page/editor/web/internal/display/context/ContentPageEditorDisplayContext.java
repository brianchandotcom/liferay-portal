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

import com.liferay.asset.categories.item.selector.AssetCategoryTreeNodeItemSelectorReturnType;
import com.liferay.asset.categories.item.selector.criterion.AssetCategoryTreeNodeItemSelectorCriterion;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentCompositionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.VideoEmbeddableHTMLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.item.selector.criteria.video.criterion.VideoItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.comment.CommentUtil;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorActionKeys;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorConstants;
import com.liferay.layout.content.page.editor.web.internal.util.ContentUtil;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkItemSelectorUtil;
import com.liferay.layout.content.page.editor.web.internal.util.MappingContentUtil;
import com.liferay.layout.content.page.editor.web.internal.util.StyleBookEntryUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryNameComparator;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.constants.LayoutConverterTypeConstants;
import com.liferay.layout.util.structure.CommonStylesUtil;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.site.navigation.item.selector.SiteNavigationMenuItemSelectorReturnType;
import com.liferay.site.navigation.item.selector.criterion.SiteNavigationMenuItemSelectorCriterion;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Eudaldo Alonso
 */
public class ContentPageEditorDisplayContext {

	public ContentPageEditorDisplayContext(
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
		PortletRequest portletRequest, RenderResponse renderResponse,
		StagingGroupHelper stagingGroupHelper) {

		_commentManager = commentManager;
		_contentPageEditorSidebarPanels = contentPageEditorSidebarPanels;
		_ffLayoutContentPageEditorConfiguration =
			ffLayoutContentPageEditorConfiguration;
		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererController = fragmentRendererController;
		_fragmentRendererTracker = fragmentRendererTracker;
		_frontendTokenDefinitionRegistry = frontendTokenDefinitionRegistry;
		_itemSelector = itemSelector;
		_pageEditorConfiguration = pageEditorConfiguration;
		_renderResponse = renderResponse;

		this.httpServletRequest = httpServletRequest;
		this.infoItemServiceTracker = infoItemServiceTracker;
		this.portletRequest = portletRequest;
		this.stagingGroupHelper = stagingGroupHelper;

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getEditorContext(String npmResolvedPackageName)
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"config",
			HashMapBuilder.<String, Object>put(
				"addFragmentCompositionURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/add_fragment_composition")
			).put(
				"addFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/add_fragment_entry_link_comment")
			).put(
				"addFragmentEntryLinksURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/add_fragment_entry_links")
			).put(
				"addFragmentEntryLinkURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/add_fragment_entry_link")
			).put(
				"addItemURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/add_item")
			).put(
				"addPortletURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/add_portlet")
			).put(
				"assetCategoryTreeNodeItemSelectorURL",
				_getAssetCategoryTreeNodeItemSelectorURL()
			).put(
				"autoExtendSessionEnabled",
				_pageEditorConfiguration.autoExtendSessionEnabled()
			).put(
				"availableLanguages", _getAvailableLanguages()
			).put(
				"availableViewportSizes", _getAvailableViewportSizes()
			).put(
				"changeMasterLayoutURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/change_master_layout")
			).put(
				"changeStyleBookEntryURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/change_style_book_entry")
			).put(
				"collectionSelectorURL", _getCollectionSelectorURL()
			).put(
				"commonStyles",
				CommonStylesUtil.getCommonStylesJSONArray(
					LanguageResources.getResourceBundle(
						themeDisplay.getLocale()))
			).put(
				"createLayoutPageTemplateEntryURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/create_layout_page_template_entry")
			).put(
				"defaultEditorConfigurations", _getDefaultConfigurations()
			).put(
				"defaultLanguageId",
				LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale())
			).put(
				"defaultSegmentsExperienceId",
				SegmentsExperienceConstants.ID_DEFAULT
			).put(
				"defaultStyleBookEntryImagePreviewURL",
				() -> {
					StyleBookEntry defaultStyleBookEntry =
						_getDefaultMasterStyleBookEntry();

					if (defaultStyleBookEntry != null) {
						return defaultStyleBookEntry.getImagePreviewURL(
							themeDisplay);
					}

					return StringPool.BLANK;
				}
			).put(
				"defaultStyleBookEntryName",
				() -> {
					StyleBookEntry defaultStyleBookEntry =
						_getDefaultMasterStyleBookEntry();

					if (defaultStyleBookEntry != null) {
						return defaultStyleBookEntry.getName();
					}

					return null;
				}
			).put(
				"deleteFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/delete_fragment_entry_link_comment")
			).put(
				"discardDraftURL", _getDiscardDraftURL()
			).put(
				"draft",
				() -> {
					Layout layout = themeDisplay.getLayout();

					return layout.isDraft();
				}
			).put(
				"duplicateItemURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/duplicate_item")
			).put(
				"duplicateSegmentsExperienceURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/duplicate_segments_experience")
			).put(
				"editFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/edit_fragment_entry_link_comment",
					Constants.UPDATE)
			).put(
				"editFragmentEntryLinkURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/edit_fragment_entry_link")
			).put(
				"fragmentAdvancedOptionsEnabled",
				_ffLayoutContentPageEditorConfiguration.
					fragmentAdvancedOptionsEnabled()
			).put(
				"frontendTokens",
				() -> {
					LayoutSet layoutSet =
						LayoutSetLocalServiceUtil.fetchLayoutSet(
							themeDisplay.getSiteGroupId(), false);

					FrontendTokenDefinition frontendTokenDefinition =
						_frontendTokenDefinitionRegistry.
							getFrontendTokenDefinition(layoutSet.getThemeId());

					if (frontendTokenDefinition == null) {
						return JSONFactoryUtil.createJSONObject();
					}

					return StyleBookEntryUtil.getFrontendTokensValues(
						frontendTokenDefinition, themeDisplay.getLocale(),
						_getDefaultStyleBookEntry());
				}
			).put(
				"getAvailableImageConfigurationsURL",
				_getResourceURL(
					"/layout_content_page_editor" +
						"/get_available_image_configurations")
			).put(
				"getAvailableListItemRenderersURL",
				_getResourceURL(
					"/layout_content_page_editor" +
						"/get_available_list_item_renderers")
			).put(
				"getAvailableListRenderersURL",
				_getResourceURL(
					"/layout_content_page_editor/get_available_list_renderers")
			).put(
				"getAvailableTemplatesURL",
				_getResourceURL(
					"/layout_content_page_editor/get_available_templates")
			).put(
				"getCollectionConfigurationURL",
				_getResourceURL(
					"/layout_content_page_editor/get_collection_configuration")
			).put(
				"getCollectionFieldURL",
				_getResourceURL(
					"/layout_content_page_editor/get_collection_field")
			).put(
				"getCollectionFiltersURL",
				_getResourceURL(
					"/layout_content_page_editor/get_collection_filters")
			).put(
				"getCollectionItemCountURL",
				_getResourceURL(
					"/layout_content_page_editor/get_collection_item_count")
			).put(
				"getCollectionMappingFieldsURL",
				_getResourceURL(
					"/layout_content_page_editor/get_collection_mapping_fields")
			).put(
				"getCollectionSupportedFiltersURL",
				_getResourceURL(
					"/layout_content_page_editor" +
						"/get_collection_supported_filters")
			).put(
				"getExperienceUsedPortletsURL",
				_getResourceURL(
					"/layout_content_page_editor/get_experience_used_portlets")
			).put(
				"getFileEntryURL",
				_getResourceURL(
					"/layout_content_page_editor/get_file_entry_url")
			).put(
				"getIframeContentCssURL",
				PortalUtil.getStaticResourceURL(
					httpServletRequest,
					PortalUtil.getPathModule() +
						"/layout-content-page-editor-web/page_editor/app" +
							"/components/App.css")
			).put(
				"getIframeContentURL",
				() -> {
					String layoutURL = PortalUtil.getLayoutFriendlyURL(
						themeDisplay.getLayout(), themeDisplay);

					return HttpUtil.addParameter(
						layoutURL, "p_l_mode", Constants.PREVIEW);
				}
			).put(
				"getInfoItemFieldValueURL",
				_getResourceURL(
					"/layout_content_page_editor/get_info_item_field_value")
			).put(
				"getInfoItemMappingFieldsURL",
				_getResourceURL(
					"/layout_content_page_editor/get_info_item_mapping_fields")
			).put(
				"getLayoutFriendlyURL",
				_getResourceURL(
					"/layout_content_page_editor/get_layout_friendly_url")
			).put(
				"getLayoutPageTemplateCollectionsURL",
				_getResourceURL(
					"/layout_content_page_editor" +
						"/get_layout_page_template_collections")
			).put(
				"getPageContentsURL",
				_getResourceURL("/layout_content_page_editor/get_page_content")
			).put(
				"getWidgetsURL",
				_getResourceURL("/layout_content_page_editor/get_widgets")
			).put(
				"imageSelectorURL", _getItemSelectorURL()
			).put(
				"infoItemSelectorURL", _getInfoItemSelectorURL()
			).put(
				"infoListSelectorURL", _getInfoListSelectorURL()
			).put(
				"isPrivateLayoutsEnabled",
				() -> {
					Group group = themeDisplay.getScopeGroup();

					return group.isPrivateLayoutsEnabled();
				}
			).put(
				"layoutConversionWarningMessages",
				MultiSessionMessages.get(
					portletRequest, "layoutConversionWarningMessages")
			).put(
				"layoutItemSelectorURL", _getLayoutItemSelectorURL()
			).put(
				"layoutType", String.valueOf(_getLayoutType())
			).put(
				"lookAndFeelURL", _getLookAndFeelURL()
			).put(
				"mappingFieldsURL",
				_getResourceURL(
					"/layout_content_page_editor/get_mapping_fields")
			).put(
				"markItemForDeletionURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/mark_item_for_deletion")
			).put(
				"masterLayouts", _getMasterLayouts()
			).put(
				"masterUsed", _isMasterUsed()
			).put(
				"moveItemURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/move_fragment_entry_link")
			).put(
				"paddingOptions",
				() -> {
					Set<Map.Entry<String, String>> entrySet =
						PaddingConverter.externalToInternalValuesMap.entrySet();

					List<Map<String, String>> list = new ArrayList<>();

					for (Map.Entry<String, String> entry : entrySet) {
						list.add(
							HashMapBuilder.put(
								"label", entry.getKey()
							).put(
								"value", entry.getValue()
							).build());
					}

					return list;
				}
			).put(
				"paginationImprovementsEnabled",
				_ffLayoutContentPageEditorConfiguration.
					paginationImprovementsEnabled()
			).put(
				"pending",
				() -> {
					Layout publishedLayout = _getPublishedLayout();

					if (publishedLayout.isDenied() ||
						publishedLayout.isPending()) {

						return true;
					}

					return false;
				}
			).put(
				"pluginsRootPath",
				npmResolvedPackageName + "/page_editor/plugins"
			).put(
				"portletNamespace", getPortletNamespace()
			).put(
				"previewPageURL",
				_getResourceURL(
					"/layout_content_page_editor/get_page_preview",
					_isPreviewPageAsGuestUser())
			).put(
				"publishURL", getPublishURL()
			).put(
				"redirectURL", _getRedirect()
			).put(
				"renderFragmentEntryURL",
				_getResourceURL(
					"/layout_content_page_editor/get_fragment_entry_link")
			).put(
				"restoreCollectionDisplayConfigURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/restore_collection_display_config")
			).put(
				"searchContainerPageMaxDelta",
				PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA
			).put(
				"sidebarPanels", getSidebarPanels()
			).put(
				"siteNavigationMenuItemSelectorURL",
				_getSiteNavigationMenuItemSelectorURL()
			).put(
				"styleBookEnabled",
				() -> {
					Layout layout = themeDisplay.getLayout();

					LayoutSet layoutSet =
						LayoutSetLocalServiceUtil.fetchLayoutSet(
							themeDisplay.getSiteGroupId(), false);

					if (layout.isInheritLookAndFeel() ||
						Objects.equals(
							layout.getThemeId(), layoutSet.getThemeId())) {

						return true;
					}

					return false;
				}
			).put(
				"styleBookEntryId",
				() -> {
					Layout layout = themeDisplay.getLayout();

					return layout.getStyleBookEntryId();
				}
			).put(
				"styleBooks", _getStyleBooks()
			).put(
				"themeColorsCssClasses", _getThemeColorsCssClasses()
			).put(
				"unmarkItemForDeletionURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/unmark_item_for_deletion")
			).put(
				"updateCollectionDisplayConfigURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/update_collection_display_config")
			).put(
				"updateConfigurationValuesURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/update_configuration_values")
			).put(
				"updateItemConfigURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/update_item_config")
			).put(
				"updateLayoutPageTemplateDataURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/update_layout_page_template_data")
			).put(
				"updateRowColumnsURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/update_row_columns")
			).put(
				"updateSegmentsExperiencePriorityURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor" +
						"/update_segments_experience_priority")
			).put(
				"updateSegmentsExperienceURL",
				getFragmentEntryActionURL(
					"/layout_content_page_editor/update_segments_experience")
			).put(
				"videoItemSelectorURL", _getVideoItemSelectorURL()
			).put(
				"workflowEnabled", isWorkflowEnabled()
			).build()
		).put(
			"state",
			HashMapBuilder.<String, Object>put(
				"collections", _getFragmentCollections(true, false)
			).put(
				"fragmentEntryLinks", _getFragmentEntryLinks()
			).put(
				"fragments", _getFragmentCollections(false, true)
			).put(
				"languageId",
				LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale())
			).put(
				"layoutData",
				() -> {
					LayoutStructure layoutStructure = _getLayoutStructure();

					return layoutStructure.toJSONObject();
				}
			).put(
				"mappingFields", _getMappingFieldsJSONObject()
			).put(
				"masterLayout", _getMasterLayoutJSONObject()
			).put(
				"pageContents",
				ContentUtil.getPageContentsJSONArray(
					httpServletRequest,
					PortalUtil.getHttpServletResponse(_renderResponse),
					themeDisplay.getPlid())
			).put(
				"permissions",
				HashMapBuilder.<String, Object>put(
					ContentPageEditorActionKeys.UPDATE, _hasUpdatePermissions()
				).put(
					ContentPageEditorActionKeys.UPDATE_LAYOUT_CONTENT,
					_hasUpdateContentPermissions()
				).build()
			).put(
				"segmentsExperienceId", getSegmentsExperienceId()
			).build()
		).build();
	}

	public String getPortletNamespace() {
		return _renderResponse.getNamespace();
	}

	public String getPublishURL() {
		return getFragmentEntryActionURL(
			"/layout_content_page_editor/publish_layout");
	}

	public List<Map<String, Object>> getSidebarPanels() {
		return getSidebarPanels(_getLayoutType());
	}

	public boolean isContentLayout() {
		if (_getLayoutType() == -1) {
			return true;
		}

		return false;
	}

	public boolean isConversionDraft() {
		if (_getLayoutType() == LayoutConverterTypeConstants.TYPE_CONVERSION) {
			return true;
		}

		return false;
	}

	public boolean isMasterLayout() {
		if (_getLayoutType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) {

			return true;
		}

		return false;
	}

	public boolean isSingleSegmentsExperienceMode() {
		return false;
	}

	public boolean isWorkflowEnabled() {
		Layout publishedLayout = _getPublishedLayout();

		return WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
			publishedLayout.getCompanyId(), publishedLayout.getGroupId(),
			Layout.class.getName());
	}

	protected List<ItemSelectorCriterion>
		getCollectionItemSelectorCriterions() {

		InfoListItemSelectorCriterion infoListItemSelectorCriterion =
			new InfoListItemSelectorCriterion();

		infoListItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListItemSelectorReturnType());
		infoListItemSelectorCriterion.setItemTypes(
			_getInfoItemFormProviderClassNames());

		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion =
				new InfoCollectionProviderItemSelectorCriterion();

		infoCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());
		infoCollectionProviderItemSelectorCriterion.setItemTypes(
			_getInfoItemFormProviderClassNames());

		return Arrays.asList(
			infoListItemSelectorCriterion,
			infoCollectionProviderItemSelectorCriterion);
	}

	protected String getFragmentEntryActionURL(String action) {
		return getFragmentEntryActionURL(action, null);
	}

	protected String getFragmentEntryActionURL(String action, String command) {
		return HttpUtil.addParameter(
			PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				action
			).setCMD(
				() -> {
					if (Validator.isNotNull(command)) {
						return command;
					}

					return null;
				}
			).buildString(),
			"p_l_mode", Constants.EDIT);
	}

	protected long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			httpServletRequest, "groupId", themeDisplay.getScopeGroupId());

		return _groupId;
	}

	protected long getSegmentsExperienceId() {
		if (_segmentsExperienceId != null) {
			return _segmentsExperienceId;
		}

		Layout layout = themeDisplay.getLayout();

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		// LPS-131416

		_segmentsExperienceId = GetterUtil.getLong(
			unicodeProperties.getProperty("segmentsExperienceId"), -1);

		if (_segmentsExperienceId != -1) {
			_segmentsExperienceId = Optional.ofNullable(
				SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
					_segmentsExperienceId)
			).map(
				SegmentsExperience::getSegmentsExperienceId
			).orElse(
				-1L
			);
		}

		if (_segmentsExperienceId == -1) {
			long[] segmentsExperienceIds = GetterUtil.getLongValues(
				httpServletRequest.getAttribute(
					SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
				new long[] {SegmentsExperienceConstants.ID_DEFAULT});

			if (segmentsExperienceIds.length > 0) {
				_segmentsExperienceId = segmentsExperienceIds[0];
			}
			else {
				_segmentsExperienceId = SegmentsExperienceConstants.ID_DEFAULT;
			}
		}

		return _segmentsExperienceId;
	}

	protected List<Map<String, Object>> getSidebarPanels(int layoutType) {
		if (_sidebarPanels != null) {
			return _sidebarPanels;
		}

		List<Map<String, Object>> sidebarPanels = new ArrayList<>();

		for (ContentPageEditorSidebarPanel contentPageEditorSidebarPanel :
				_contentPageEditorSidebarPanels) {

			if (!contentPageEditorSidebarPanel.isVisible(
					themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
					layoutType)) {

				continue;
			}

			if (contentPageEditorSidebarPanel.includeSeparator() &&
				!sidebarPanels.isEmpty()) {

				sidebarPanels.add(
					HashMapBuilder.<String, Object>put(
						"sidebarPanelId", "separator"
					).build());
			}

			sidebarPanels.add(
				HashMapBuilder.<String, Object>put(
					"icon", contentPageEditorSidebarPanel.getIcon()
				).put(
					"isLink", contentPageEditorSidebarPanel.isLink()
				).put(
					"label",
					contentPageEditorSidebarPanel.getLabel(
						themeDisplay.getLocale())
				).put(
					"sidebarPanelId", contentPageEditorSidebarPanel.getId()
				).put(
					"url",
					contentPageEditorSidebarPanel.getURL(httpServletRequest)
				).build());
		}

		_sidebarPanels = sidebarPanels;

		return _sidebarPanels;
	}

	protected final HttpServletRequest httpServletRequest;
	protected final InfoItemServiceTracker infoItemServiceTracker;
	protected final PortletRequest portletRequest;
	protected final StagingGroupHelper stagingGroupHelper;
	protected final ThemeDisplay themeDisplay;

	private String _getAssetCategoryTreeNodeItemSelectorURL() {
		ItemSelectorCriterion itemSelectorCriterion =
			new AssetCategoryTreeNodeItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new AssetCategoryTreeNodeItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectAssetCategoryTreeNode",
			itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private Map<String, Object> _getAvailableLanguages() {
		Map<String, Object> availableLanguages = new HashMap<>();

		String[] languageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()));

		for (String languageId : languageIds) {
			availableLanguages.put(
				languageId,
				HashMapBuilder.<String, Object>put(
					"languageIcon",
					StringUtil.toLowerCase(
						LocaleUtil.toW3cLanguageId(languageId))
				).put(
					"w3cLanguageId", LocaleUtil.toW3cLanguageId(languageId)
				).build());
		}

		return availableLanguages;
	}

	private Map<String, Map<String, Object>> _getAvailableViewportSizes() {
		Map<String, Map<String, Object>> availableViewportSizesMap =
			new LinkedHashMap<>();

		EnumSet<ViewportSize> viewportSizes = EnumSet.allOf(ViewportSize.class);

		Stream<ViewportSize> stream = viewportSizes.stream();

		List<ViewportSize> viewportSizesList = stream.sorted(
			Comparator.comparingInt(ViewportSize::getOrder)
		).collect(
			Collectors.toList()
		);

		for (ViewportSize viewportSize : viewportSizesList) {
			availableViewportSizesMap.put(
				viewportSize.getViewportSizeId(),
				HashMapBuilder.<String, Object>put(
					"icon", viewportSize.getIcon()
				).put(
					"label",
					LanguageUtil.get(
						httpServletRequest, viewportSize.getLabel())
				).put(
					"maxWidth", viewportSize.getMaxWidth()
				).put(
					"minWidth", viewportSize.getMinWidth()
				).put(
					"sizeId", viewportSize.getViewportSizeId()
				).build());
		}

		return availableViewportSizesMap;
	}

	private String _getCollectionSelectorURL() {
		List<ItemSelectorCriterion> collectionItemSelectorCriterions =
			getCollectionItemSelectorCriterions();

		PortletURL infoListSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectInfoList",
			collectionItemSelectorCriterions.toArray(
				new ItemSelectorCriterion[0]));

		if (infoListSelectorURL == null) {
			return StringPool.BLANK;
		}

		return HttpUtil.addParameter(
			infoListSelectorURL.toString(), "refererPlid",
			themeDisplay.getPlid());
	}

	private Map<String, Object> _getContributedFragmentEntry(
		String rendererKey) {

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(
				themeDisplay.getLocale());

		FragmentEntry fragmentEntry = fragmentEntries.get(rendererKey);

		if (fragmentEntry != null) {
			return HashMapBuilder.<String, Object>put(
				"fragmentEntryId", 0
			).put(
				"name", fragmentEntry.getName()
			).build();
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _getDefaultConfigurations() {
		if (_defaultConfigurations != null) {
			return _defaultConfigurations;
		}

		_defaultConfigurations = HashMapBuilder.<String, Object>put(
			"comment",
			() -> {
				EditorConfiguration commentEditorConfiguration =
					EditorConfigurationFactoryUtil.getEditorConfiguration(
						ContentPageEditorPortletKeys.
							CONTENT_PAGE_EDITOR_PORTLET,
						"pageEditorCommentEditor", StringPool.BLANK,
						Collections.emptyMap(), themeDisplay,
						RequestBackedPortletURLFactoryUtil.create(
							httpServletRequest));

				return commentEditorConfiguration.getData();
			}
		).put(
			"rich-text",
			() -> {
				EditorConfiguration richTextEditorConfiguration =
					EditorConfigurationFactoryUtil.getEditorConfiguration(
						ContentPageEditorPortletKeys.
							CONTENT_PAGE_EDITOR_PORTLET,
						"fragmenEntryLinkRichTextEditor", StringPool.BLANK,
						Collections.emptyMap(), themeDisplay,
						RequestBackedPortletURLFactoryUtil.create(
							httpServletRequest));

				return richTextEditorConfiguration.getData();
			}
		).put(
			"text",
			() -> {
				EditorConfiguration editorConfiguration =
					EditorConfigurationFactoryUtil.getEditorConfiguration(
						ContentPageEditorPortletKeys.
							CONTENT_PAGE_EDITOR_PORTLET,
						"fragmenEntryLinkEditor", StringPool.BLANK,
						Collections.emptyMap(), themeDisplay,
						RequestBackedPortletURLFactoryUtil.create(
							httpServletRequest));

				return editorConfiguration.getData();
			}
		).build();

		return _defaultConfigurations;
	}

	private StyleBookEntry _getDefaultMasterStyleBookEntry() {
		if (_defaultMasterStyleBookEntry != null) {
			return _defaultMasterStyleBookEntry;
		}

		_defaultMasterStyleBookEntry =
			DefaultStyleBookEntryUtil.getDefaultMasterStyleBookEntry(
				themeDisplay.getLayout());

		return _defaultMasterStyleBookEntry;
	}

	private StyleBookEntry _getDefaultStyleBookEntry() {
		if (_defaultStyleBookEntry != null) {
			return _defaultStyleBookEntry;
		}

		_defaultStyleBookEntry =
			DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(
				themeDisplay.getLayout());

		return _defaultStyleBookEntry;
	}

	private String _getDiscardDraftURL() {
		Layout publishedLayout = _getPublishedLayout();

		if (!Objects.equals(
				publishedLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

			return PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
					PortletRequest.ACTION_PHASE)
			).setActionName(
				"/layout_admin/discard_draft_layout"
			).setRedirect(
				_getRedirect()
			).setParameter(
				"selPlid", themeDisplay.getPlid()
			).buildString();
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/layout_admin/delete_layout"
		).setRedirect(
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
					PortletRequest.RENDER_PHASE)
			).setParameter(
				"selPlid", publishedLayout.getPlid()
			).buildString()
		).setParameter(
			"selPlid", themeDisplay.getPlid()
		).buildString();
	}

	private List<Map<String, Object>> _getDynamicFragments() {
		List<Map<String, Object>> dynamicFragments = new ArrayList<>();

		Map<String, List<Map<String, Object>>> fragmentCollectionMap =
			new HashMap<>();
		Map<String, FragmentRenderer> fragmentCollectionFragmentRenderers =
			new HashMap<>();

		List<FragmentRenderer> fragmentRenderers =
			_fragmentRendererTracker.getFragmentRenderers();

		for (FragmentRenderer fragmentRenderer : fragmentRenderers) {
			if (!fragmentRenderer.isSelectable(httpServletRequest) ||
				!_isAllowedFragmentEntryKey(fragmentRenderer.getKey())) {

				continue;
			}

			Map<String, Object> dynamicFragment =
				HashMapBuilder.<String, Object>put(
					"fragmentEntryKey", fragmentRenderer.getKey()
				).put(
					"icon", fragmentRenderer.getIcon()
				).put(
					"imagePreviewURL",
					fragmentRenderer.getImagePreviewURL(httpServletRequest)
				).put(
					"name", fragmentRenderer.getLabel(themeDisplay.getLocale())
				).build();

			List<Map<String, Object>> fragmentCollections =
				fragmentCollectionMap.get(fragmentRenderer.getCollectionKey());

			if (fragmentCollections == null) {
				List<Map<String, Object>> filteredDynamicFragments =
					fragmentCollectionMap.computeIfAbsent(
						fragmentRenderer.getCollectionKey(),
						key -> new ArrayList<>());

				filteredDynamicFragments.add(dynamicFragment);

				fragmentCollectionMap.put(
					fragmentRenderer.getCollectionKey(),
					filteredDynamicFragments);

				fragmentCollectionFragmentRenderers.put(
					fragmentRenderer.getCollectionKey(), fragmentRenderer);
			}
			else {
				fragmentCollections.add(dynamicFragment);
			}
		}

		for (Map.Entry<String, List<Map<String, Object>>> entry :
				fragmentCollectionMap.entrySet()) {

			dynamicFragments.add(
				HashMapBuilder.<String, Object>put(
					"fragmentCollectionId", entry.getKey()
				).put(
					"fragmentEntries", entry.getValue()
				).put(
					"name",
					() -> LanguageUtil.get(
						themeDisplay.getLocale(),
						"fragment.collection.label." + entry.getKey())
				).build());
		}

		return dynamicFragments;
	}

	private List<Map<String, Object>> _getFragmentCollectionContributors() {
		List<Map<String, Object>> fragmentCollectionContributorsMap =
			new ArrayList<>();

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		Collections.sort(
			fragmentCollectionContributors,
			new FragmentCollectionContributorNameComparator(
				themeDisplay.getLocale()));

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			List<FragmentComposition> fragmentCompositions =
				fragmentCollectionContributor.getFragmentCompositions(
					themeDisplay.getLocale());
			List<FragmentEntry> fragmentEntries =
				fragmentCollectionContributor.getFragmentEntries(
					themeDisplay.getLocale());

			if (ListUtil.isEmpty(fragmentCompositions) &&
				ListUtil.isEmpty(fragmentEntries)) {

				continue;
			}

			List<Map<String, Object>> filteredFragmentEntries =
				_getFragmentEntries(fragmentEntries);

			filteredFragmentEntries.addAll(
				_getFragmentCompositions(fragmentCompositions));

			if (ListUtil.isEmpty(filteredFragmentEntries)) {
				continue;
			}

			filteredFragmentEntries.sort(
				(filteredFragmentEntry1, filteredFragmentEntry2) -> {
					String name1 = String.valueOf(
						filteredFragmentEntry1.get("name"));
					String name2 = String.valueOf(
						filteredFragmentEntry2.get("name"));

					return name1.compareTo(name2);
				});

			fragmentCollectionContributorsMap.add(
				HashMapBuilder.<String, Object>put(
					"fragmentCollectionId",
					fragmentCollectionContributor.getFragmentCollectionKey()
				).put(
					"fragmentEntries", filteredFragmentEntries
				).put(
					"name",
					fragmentCollectionContributor.getName(
						themeDisplay.getLocale())
				).build());
		}

		return fragmentCollectionContributorsMap;
	}

	private List<Map<String, Object>> _getFragmentCollections(
		boolean includeEmpty, boolean includeSystem) {

		List<Map<String, Object>> allFragmentCollections = new ArrayList<>();

		if (includeSystem) {
			allFragmentCollections.addAll(_getFragmentCollectionContributors());
			allFragmentCollections.addAll(_getDynamicFragments());
		}

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(
				new long[] {
					themeDisplay.getCompanyGroupId(), getGroupId(),
					CompanyConstants.SYSTEM
				});

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			List<FragmentEntry> fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntriesByStatus(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					WorkflowConstants.STATUS_APPROVED);

			List<Map<String, Object>> filteredFragmentEntries =
				_getFragmentEntries(fragmentEntries);

			List<FragmentComposition> fragmentCompositions =
				FragmentCompositionServiceUtil.getFragmentCompositions(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					WorkflowConstants.STATUS_APPROVED);

			filteredFragmentEntries.addAll(
				_getFragmentCompositions(fragmentCompositions));

			if (!includeEmpty && ListUtil.isEmpty(filteredFragmentEntries)) {
				continue;
			}

			if (!includeSystem &&
				(fragmentCollection.getGroupId() !=
					themeDisplay.getScopeGroupId())) {

				continue;
			}

			allFragmentCollections.add(
				HashMapBuilder.<String, Object>put(
					"fragmentCollectionId",
					fragmentCollection.getFragmentCollectionId()
				).put(
					"fragmentEntries", filteredFragmentEntries
				).put(
					"name", fragmentCollection.getName()
				).build());
		}

		return allFragmentCollections;
	}

	private List<Map<String, Object>> _getFragmentCompositions(
		List<FragmentComposition> fragmentCompositions) {

		List<Map<String, Object>> filteredFragmentCompositions =
			new ArrayList<>();

		for (FragmentComposition fragmentComposition : fragmentCompositions) {
			if (!_isAllowedFragmentEntryKey(
					fragmentComposition.getFragmentCompositionKey())) {

				continue;
			}

			filteredFragmentCompositions.add(
				HashMapBuilder.<String, Object>put(
					"fragmentEntryKey",
					fragmentComposition.getFragmentCompositionKey()
				).put(
					"groupId", fragmentComposition.getGroupId()
				).put(
					"icon", fragmentComposition.getIcon()
				).put(
					"imagePreviewURL",
					fragmentComposition.getImagePreviewURL(themeDisplay)
				).put(
					"name", fragmentComposition.getName()
				).put(
					"type", ContentPageEditorConstants.TYPE_COMPOSITION
				).build());
		}

		return filteredFragmentCompositions;
	}

	private List<Map<String, Object>> _getFragmentEntries(
		List<FragmentEntry> fragmentEntries) {

		List<Map<String, Object>> filteredFragmentEntries = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			if (!_isAllowedFragmentEntryKey(
					fragmentEntry.getFragmentEntryKey())) {

				continue;
			}

			filteredFragmentEntries.add(
				HashMapBuilder.<String, Object>put(
					"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
				).put(
					"groupId", fragmentEntry.getGroupId()
				).put(
					"icon", fragmentEntry.getIcon()
				).put(
					"imagePreviewURL",
					fragmentEntry.getImagePreviewURL(themeDisplay)
				).put(
					"name", fragmentEntry.getName()
				).put(
					"type", fragmentEntry.getType()
				).build());
		}

		return filteredFragmentEntries;
	}

	private Map<String, Object> _getFragmentEntry(
		FragmentEntryLink fragmentEntryLink, FragmentEntry fragmentEntry,
		String content) {

		if (fragmentEntry != null) {
			return HashMapBuilder.<String, Object>put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId()
			).put(
				"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
			).put(
				"name", fragmentEntry.getName()
			).build();
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNotNull(rendererKey)) {
			Map<String, Object> contributedFragmentEntries =
				_getContributedFragmentEntry(rendererKey);

			if (!contributedFragmentEntries.isEmpty()) {
				return contributedFragmentEntries;
			}

			FragmentRenderer fragmentRenderer =
				_fragmentRendererTracker.getFragmentRenderer(
					fragmentEntryLink.getRendererKey());

			if (fragmentRenderer != null) {
				return HashMapBuilder.<String, Object>put(
					"fragmentEntryId", 0
				).put(
					"fragmentEntryKey", fragmentRenderer.getKey()
				).put(
					"name", fragmentRenderer.getLabel(themeDisplay.getLocale())
				).build();
			}
		}

		String portletId = _getPortletId(content);

		PortletConfig portletConfig = PortletConfigFactoryUtil.get(portletId);

		if (portletConfig == null) {
			return HashMapBuilder.<String, Object>put(
				"fragmentEntryId", 0
			).put(
				"name", StringPool.BLANK
			).build();
		}

		return HashMapBuilder.<String, Object>put(
			"fragmentEntryId", 0
		).put(
			"name",
			PortalUtil.getPortletTitle(portletId, themeDisplay.getLocale())
		).put(
			"portletId", portletId
		).build();
	}

	private List<String> _getFragmentEntryKeys() {
		if (_fragmentEntryKeys != null) {
			return _fragmentEntryKeys;
		}

		LayoutStructure masterLayoutStructure = _getMasterLayoutStructure();

		if (masterLayoutStructure == null) {
			_fragmentEntryKeys = Collections.emptyList();

			return _fragmentEntryKeys;
		}

		LayoutStructureItem layoutStructureItem =
			masterLayoutStructure.getDropZoneLayoutStructureItem();

		if (layoutStructureItem == null) {
			_fragmentEntryKeys = Collections.emptyList();

			return _fragmentEntryKeys;
		}

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)layoutStructureItem;

		_fragmentEntryKeys = dropZoneLayoutStructureItem.getFragmentEntryKeys();

		return _fragmentEntryKeys;
	}

	private JSONArray _getFragmentEntryLinkCommentsJSONArray(
			FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (!_commentManager.hasDiscussion(
				FragmentEntryLink.class.getName(),
				fragmentEntryLink.getFragmentEntryLinkId())) {

			return jsonArray;
		}

		List<Comment> rootComments = _commentManager.getRootComments(
			FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Comment rootComment : rootComments) {
			JSONObject commentJSONObject = CommentUtil.getCommentJSONObject(
				rootComment, httpServletRequest);

			List<Comment> childComments = _commentManager.getChildComments(
				rootComment.getCommentId(), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			JSONArray childCommentsJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (Comment childComment : childComments) {
				childCommentsJSONArray.put(
					CommentUtil.getCommentJSONObject(
						childComment, httpServletRequest));
			}

			commentJSONObject.put("children", childCommentsJSONArray);

			jsonArray.put(commentJSONObject);
		}

		return jsonArray;
	}

	private Map<String, Object> _getFragmentEntryLinks() throws Exception {
		if (_fragmentEntryLinks != null) {
			return _fragmentEntryLinks;
		}

		Map<String, Object> fragmentEntryLinksMap = new HashMap<>();

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>(
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksByPlid(
				getGroupId(), themeDisplay.getPlid()));

		Layout layout = themeDisplay.getLayout();

		if (layout.getMasterLayoutPlid() > 0) {
			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						layout.getMasterLayoutPlid());

			fragmentEntryLinks.addAll(
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksByPlid(
					getGroupId(), masterLayoutPageTemplateEntry.getPlid()));
		}

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_renderResponse);

		try {
			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				DefaultFragmentRendererContext fragmentRendererContext =
					new DefaultFragmentRendererContext(fragmentEntryLink);

				fragmentRendererContext.
					setCollectionStyledLayoutStructureItemIds(
						LayoutStructureUtil.
							getCollectionStyledLayoutStructureItemIds(
								fragmentEntryLink.getFragmentEntryLinkId(),
								_getLayoutStructure()));
				fragmentRendererContext.setLocale(themeDisplay.getLocale());
				fragmentRendererContext.setMode(
					FragmentEntryLinkConstants.EDIT);

				String content = _fragmentRendererController.render(
					fragmentRendererContext, httpServletRequest,
					PortalUtil.getHttpServletResponse(_renderResponse));

				String configuration =
					_fragmentRendererController.getConfiguration(
						fragmentRendererContext);

				JSONObject configurationJSONObject =
					JSONFactoryUtil.createJSONObject(configuration);

				FragmentEntryLinkItemSelectorUtil.
					addFragmentEntryLinkFieldsSelectorURL(
						_itemSelector, httpServletRequest,
						liferayPortletResponse, configurationJSONObject);

				FragmentEntry fragmentEntry =
					FragmentEntryServiceUtil.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				if (fragmentEntry == null) {
					Map<String, FragmentEntry> fragmentEntries =
						_fragmentCollectionContributorTracker.
							getFragmentEntries(themeDisplay.getLocale());

					fragmentEntry = fragmentEntries.get(
						fragmentEntryLink.getRendererKey());
				}

				Map<String, Object> fragmentEntryLinkMap =
					HashMapBuilder.<String, Object>put(
						"comments",
						_getFragmentEntryLinkCommentsJSONArray(
							fragmentEntryLink)
					).put(
						"configuration", configurationJSONObject
					).put(
						"content", content
					).put(
						"defaultConfigurationValues",
						_fragmentEntryConfigurationParser.
							getConfigurationDefaultValuesJSONObject(
								configuration)
					).put(
						"editableTypes",
						EditableFragmentEntryProcessorUtil.getEditableTypes(
							content)
					).put(
						"editableValues",
						JSONFactoryUtil.createJSONObject(
							fragmentEntryLink.getEditableValues())
					).put(
						"error",
						() -> {
							if (SessionErrors.contains(
									httpServletRequest,
									"fragmentEntryContentInvalid")) {

								SessionErrors.clear(httpServletRequest);

								return true;
							}

							return false;
						}
					).put(
						"fragmentEntryLinkId",
						String.valueOf(
							fragmentEntryLink.getFragmentEntryLinkId())
					).put(
						"masterLayout",
						layout.getMasterLayoutPlid() ==
							fragmentEntryLink.getPlid()
					).put(
						"segmentsExperienceId",
						String.valueOf(
							fragmentEntryLink.getSegmentsExperienceId())
					).putAll(
						_getFragmentEntry(
							fragmentEntryLink, fragmentEntry, content)
					).build();

				if (fragmentEntry != null) {
					fragmentEntryLinkMap.put("icon", fragmentEntry.getIcon());
				}

				fragmentEntryLinksMap.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
					fragmentEntryLinkMap);
			}
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}

		LayoutStructure layoutStructure = _getLayoutStructure();

		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
			layoutStructure.getFragmentLayoutStructureItems();

		for (long fragmentEntryLinkId : fragmentLayoutStructureItems.keySet()) {
			if (fragmentEntryLinksMap.containsKey(
					String.valueOf(fragmentEntryLinkId))) {

				continue;
			}

			fragmentEntryLinksMap.put(
				String.valueOf(fragmentEntryLinkId),
				JSONUtil.put(
					"configuration", JSONFactoryUtil.createJSONObject()
				).put(
					"content", StringPool.BLANK
				).put(
					"defaultConfigurationValues",
					JSONFactoryUtil.createJSONObject()
				).put(
					"editableValues", JSONFactoryUtil.createJSONObject()
				).put(
					"error", Boolean.TRUE
				).put(
					"fragmentEntryLinkId", String.valueOf(fragmentEntryLinkId)
				));
		}

		_fragmentEntryLinks = fragmentEntryLinksMap;

		return _fragmentEntryLinks;
	}

	private ItemSelectorCriterion _getImageItemSelectorCriterion() {
		if (_imageItemSelectorCriterion != null) {
			return _imageItemSelectorCriterion;
		}

		ItemSelectorCriterion itemSelectorCriterion =
			new ImageItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		_imageItemSelectorCriterion = itemSelectorCriterion;

		return _imageItemSelectorCriterion;
	}

	private List<String> _getInfoItemFormProviderClassNames() {
		List<String> infoItemClassNames =
			infoItemServiceTracker.getInfoItemClassNames(
				InfoItemFormProvider.class);

		if (infoItemClassNames.contains(FileEntry.class.getName())) {
			infoItemClassNames.add(DLFileEntryConstants.getClassName());
		}

		return infoItemClassNames;
	}

	private String _getInfoItemSelectorURL() {
		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());

		PortletURL infoItemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectInfoItem",
			itemSelectorCriterion);

		if (infoItemSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoItemSelectorURL.toString();
	}

	private String _getInfoListSelectorURL() {
		InfoListItemSelectorCriterion infoListItemSelectorCriterion =
			new InfoListItemSelectorCriterion();

		infoListItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListItemSelectorReturnType());

		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion =
				new InfoCollectionProviderItemSelectorCriterion();

		infoCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());

		PortletURL infoListSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectInfoList",
			infoListItemSelectorCriterion,
			infoCollectionProviderItemSelectorCriterion);

		if (infoListSelectorURL == null) {
			return StringPool.BLANK;
		}

		return HttpUtil.addParameter(
			infoListSelectorURL.toString(), "refererPlid",
			themeDisplay.getPlid());
	}

	private String _getItemSelectorURL() {
		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectImage",
			_getImageItemSelectorCriterion(), _getURLItemSelectorCriterion());

		return itemSelectorURL.toString();
	}

	private String _getLayoutItemSelectorURL() {
		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new UUIDItemSelectorReturnType());
		layoutItemSelectorCriterion.setMultiSelection(false);
		layoutItemSelectorCriterion.setShowHiddenPages(true);
		layoutItemSelectorCriterion.setShowPrivatePages(true);

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectLayout",
			layoutItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private LayoutStructure _getLayoutStructure() throws Exception {
		if (_layoutStructure != null) {
			return _layoutStructure;
		}

		_layoutStructure = LayoutStructureUtil.getLayoutStructure(
			themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
			getSegmentsExperienceId());

		return _layoutStructure;
	}

	private int _getLayoutType() {
		if (_layoutType != null) {
			return _layoutType;
		}

		Layout publishedLayout = _getPublishedLayout();

		if (Objects.equals(
				publishedLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

			_layoutType = LayoutConverterTypeConstants.TYPE_CONVERSION;

			return _layoutType;
		}

		Layout layout = themeDisplay.getLayout();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(layout.getClassPK());
		}

		if (layoutPageTemplateEntry == null) {
			_layoutType = -1;
		}
		else {
			_layoutType = layoutPageTemplateEntry.getType();
		}

		return _layoutType;
	}

	private Object _getLookAndFeelURL() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).setRedirect(
			ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				"p_l_back_url")
		).setBackURL(
			themeDisplay.getURLCurrent()
		).setParameter(
			"groupId", layout.getGroupId()
		).setParameter(
			"privateLayout", layout.isPrivateLayout()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildString();
	}

	private JSONObject _getMappingFieldsJSONObject() throws Exception {
		JSONObject mappingFieldsJSONObject = JSONFactoryUtil.createJSONObject();

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders =
				ContentUtil.getMappedLayoutDisplayPageObjectProviders(
					getGroupId(), themeDisplay.getPlid());

		Layout layout = themeDisplay.getLayout();

		if (layout.getMasterLayoutPlid() > 0) {
			layoutDisplayPageObjectProviders.addAll(
				ContentUtil.getMappedLayoutDisplayPageObjectProviders(
					getGroupId(), layout.getMasterLayoutPlid()));
		}

		for (LayoutDisplayPageObjectProvider<?>
				layoutDisplayPageObjectProvider :
					layoutDisplayPageObjectProviders) {

			String uniqueMappingFieldKey =
				layoutDisplayPageObjectProvider.getClassNameId() +
					StringPool.DASH +
						layoutDisplayPageObjectProvider.getClassTypeId();

			if (mappingFieldsJSONObject.has(uniqueMappingFieldKey)) {
				continue;
			}

			mappingFieldsJSONObject.put(
				uniqueMappingFieldKey,
				MappingContentUtil.getMappingFieldsJSONArray(
					String.valueOf(
						layoutDisplayPageObjectProvider.getClassTypeId()),
					themeDisplay.getScopeGroupId(), infoItemServiceTracker,
					PortalUtil.getClassName(
						layoutDisplayPageObjectProvider.getClassNameId()),
					themeDisplay.getLocale()));
		}

		return mappingFieldsJSONObject;
	}

	private JSONObject _getMasterLayoutJSONObject() {
		return JSONUtil.put(
			"masterLayoutData",
			Optional.ofNullable(
				_getMasterLayoutStructure()
			).map(
				LayoutStructure::toJSONObject
			).orElse(
				null
			)
		).put(
			"masterLayoutPlid",
			() -> {
				Layout layout = themeDisplay.getLayout();

				return String.valueOf(layout.getMasterLayoutPlid());
			}
		);
	}

	private List<Map<String, Object>> _getMasterLayouts() {
		ArrayList<Map<String, Object>> masterLayouts = new ArrayList<>();

		masterLayouts.add(
			HashMapBuilder.<String, Object>put(
				"imagePreviewURL", StringPool.BLANK
			).put(
				"masterLayoutPlid", "0"
			).put(
				"name", LanguageUtil.get(httpServletRequest, "blank")
			).build());

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
				themeDisplay.getScopeGroupId(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new LayoutPageTemplateEntryNameComparator(true));

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			masterLayouts.add(
				HashMapBuilder.<String, Object>put(
					"imagePreviewURL",
					layoutPageTemplateEntry.getImagePreviewURL(themeDisplay)
				).put(
					"masterLayoutPlid",
					String.valueOf(layoutPageTemplateEntry.getPlid())
				).put(
					"name", layoutPageTemplateEntry.getName()
				).build());
		}

		return masterLayouts;
	}

	private LayoutStructure _getMasterLayoutStructure() {
		if (_masterLayoutStructure != null) {
			return _masterLayoutStructure;
		}

		Layout layout = themeDisplay.getLayout();

		if (layout.getMasterLayoutPlid() <= 0) {
			return _masterLayoutStructure;
		}

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(
					layout.getMasterLayoutPlid());

		if (masterLayoutPageTemplateEntry == null) {
			return _masterLayoutStructure;
		}

		try {
			_masterLayoutStructure = LayoutStructureUtil.getLayoutStructure(
				getGroupId(), masterLayoutPageTemplateEntry.getPlid(),
				SegmentsExperienceConstants.ID_DEFAULT);

			return _masterLayoutStructure;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get master layout structure", exception);
			}
		}

		return _masterLayoutStructure;
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

	private Layout _getPublishedLayout() {
		if (_publishedLayout != null) {
			return _publishedLayout;
		}

		Layout draftLayout = themeDisplay.getLayout();

		_publishedLayout = LayoutLocalServiceUtil.fetchLayout(
			draftLayout.getClassPK());

		return _publishedLayout;
	}

	private String _getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNull(_redirect)) {
			_redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(
					PortalUtil.getOriginalServletRequest(httpServletRequest),
					"p_l_back_url", themeDisplay.getURLCurrent()));
		}

		return _redirect;
	}

	private String _getResourceURL(String resourceID) {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID(resourceID);

		return HttpUtil.addParameter(
			resourceURL.toString(), "p_l_mode", Constants.EDIT);
	}

	private String _getResourceURL(String resourceID, boolean doAsGuest)
		throws Exception {

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_renderResponse);

		LiferayPortletURL resourceURL =
			liferayPortletResponse.createResourceURL(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

		if (doAsGuest) {
			User defaultUser = UserLocalServiceUtil.getDefaultUser(
				themeDisplay.getCompanyId());

			resourceURL.setDoAsUserId(defaultUser.getUserId());
		}

		resourceURL.setResourceID(resourceID);

		return resourceURL.toString();
	}

	private String _getSiteNavigationMenuItemSelectorURL() {
		ItemSelectorCriterion itemSelectorCriterion =
			new SiteNavigationMenuItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new SiteNavigationMenuItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectSiteNavigationMenu",
			itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private List<Map<String, Object>> _getStyleBooks() {
		ArrayList<Map<String, Object>> styleBooks = new ArrayList<>();

		List<StyleBookEntry> styleBookEntries =
			StyleBookEntryLocalServiceUtil.getStyleBookEntries(
				StagingUtil.getLiveGroupId(themeDisplay.getScopeGroupId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StyleBookEntryNameComparator(true));

		for (StyleBookEntry styleBookEntry : styleBookEntries) {
			styleBooks.add(
				HashMapBuilder.<String, Object>put(
					"imagePreviewURL",
					styleBookEntry.getImagePreviewURL(themeDisplay)
				).put(
					"name", styleBookEntry.getName()
				).put(
					"styleBookEntryId", styleBookEntry.getStyleBookEntryId()
				).build());
		}

		return styleBooks;
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

		ItemSelectorCriterion itemSelectorCriterion =
			new URLItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		_urlItemSelectorCriterion = itemSelectorCriterion;

		return _urlItemSelectorCriterion;
	}

	private String _getVideoItemSelectorURL() {
		VideoItemSelectorCriterion videoItemSelectorCriterion =
			new VideoItemSelectorCriterion();

		videoItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new VideoEmbeddableHTMLItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectVideo",
			videoItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private boolean _hasUpdateContentPermissions() {
		try {
			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
					ActionKeys.UPDATE_LAYOUT_CONTENT)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private boolean _hasUpdatePermissions() {
		try {
			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
					ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private boolean _isAllowedFragmentEntryKey(String fragmentEntryKey) {
		List<String> fragmentEntryKeys = _getFragmentEntryKeys();

		if (_isAllowNewFragmentEntries()) {
			if (ListUtil.isEmpty(fragmentEntryKeys) ||
				!fragmentEntryKeys.contains(fragmentEntryKey)) {

				return true;
			}

			return false;
		}

		if (ListUtil.isNotEmpty(fragmentEntryKeys) &&
			fragmentEntryKeys.contains(fragmentEntryKey)) {

			return true;
		}

		return false;
	}

	private boolean _isAllowNewFragmentEntries() {
		if (_allowNewFragmentEntries != null) {
			return _allowNewFragmentEntries;
		}

		LayoutStructure masterLayoutStructure = _getMasterLayoutStructure();

		if (masterLayoutStructure == null) {
			_allowNewFragmentEntries = true;

			return true;
		}

		LayoutStructureItem layoutStructureItem =
			masterLayoutStructure.getDropZoneLayoutStructureItem();

		if (layoutStructureItem == null) {
			_allowNewFragmentEntries = true;

			return true;
		}

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)layoutStructureItem;

		_allowNewFragmentEntries =
			dropZoneLayoutStructureItem.isAllowNewFragmentEntries();

		return _allowNewFragmentEntries;
	}

	private boolean _isMasterUsed() {
		if (_getLayoutType() !=
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) {

			return false;
		}

		Layout publishedLayout = _getPublishedLayout();

		int masterUsagesCount = LayoutLocalServiceUtil.getMasterLayoutsCount(
			themeDisplay.getScopeGroupId(), publishedLayout.getPlid());

		if (masterUsagesCount > 0) {
			return true;
		}

		return false;
	}

	private boolean _isPreviewPageAsGuestUser() {
		if (stagingGroupHelper.isLocalStagingGroup(
				themeDisplay.getScopeGroupId()) ||
			stagingGroupHelper.isRemoteStagingGroup(
				themeDisplay.getScopeGroupId())) {

			return false;
		}

		Layout publishedLayout = _getPublishedLayout();

		return !publishedLayout.isPrivateLayout();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentPageEditorDisplayContext.class);

	private Boolean _allowNewFragmentEntries;
	private final CommentManager _commentManager;
	private final List<ContentPageEditorSidebarPanel>
		_contentPageEditorSidebarPanels;
	private Map<String, Object> _defaultConfigurations;
	private StyleBookEntry _defaultMasterStyleBookEntry;
	private StyleBookEntry _defaultStyleBookEntry;
	private final FFLayoutContentPageEditorConfiguration
		_ffLayoutContentPageEditorConfiguration;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private List<String> _fragmentEntryKeys;
	private Map<String, Object> _fragmentEntryLinks;
	private final FragmentRendererController _fragmentRendererController;
	private final FragmentRendererTracker _fragmentRendererTracker;
	private final FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private Long _groupId;
	private ItemSelectorCriterion _imageItemSelectorCriterion;
	private final ItemSelector _itemSelector;
	private LayoutStructure _layoutStructure;
	private Integer _layoutType;
	private LayoutStructure _masterLayoutStructure;
	private final PageEditorConfiguration _pageEditorConfiguration;
	private Layout _publishedLayout;
	private String _redirect;
	private final RenderResponse _renderResponse;
	private Long _segmentsExperienceId;
	private List<Map<String, Object>> _sidebarPanels;
	private ItemSelectorCriterion _urlItemSelectorCriterion;

}