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

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
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
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorCriterion;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
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
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryNameComparator;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.constants.LayoutConverterTypeConstants;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
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
import com.liferay.portal.kernel.service.PortletItemLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
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
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
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
		PortletRequest portletRequest, RenderResponse renderResponse) {

		_commentManager = commentManager;
		_contentPageEditorSidebarPanels = contentPageEditorSidebarPanels;
		_ffLayoutContentPageEditorConfiguration =
			ffLayoutContentPageEditorConfiguration;
		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererController = fragmentRendererController;
		_fragmentRendererTracker = fragmentRendererTracker;
		_itemSelector = itemSelector;
		_pageEditorConfiguration = pageEditorConfiguration;
		_renderResponse = renderResponse;

		this.httpServletRequest = httpServletRequest;
		this.infoItemServiceTracker = infoItemServiceTracker;
		this.portletRequest = portletRequest;

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
					"/content_layout/add_fragment_composition")
			).put(
				"addFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/content_layout/add_fragment_entry_link_comment")
			).put(
				"addFragmentEntryLinksURL",
				getFragmentEntryActionURL(
					"/content_layout/add_fragment_entry_links")
			).put(
				"addFragmentEntryLinkURL",
				getFragmentEntryActionURL(
					"/content_layout/add_fragment_entry_link")
			).put(
				"addItemURL",
				getFragmentEntryActionURL("/content_layout/add_item")
			).put(
				"addPortletURL",
				getFragmentEntryActionURL("/content_layout/add_portlet")
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
					"/content_layout/change_master_layout")
			).put(
				"changeStyleBookEntryURL",
				getFragmentEntryActionURL(
					"/content_layout/change_style_book_entry")
			).put(
				"collectionSelectorURL", _getCollectionSelectorURL()
			).put(
				"containerItemFlexEnabled",
				_ffLayoutContentPageEditorConfiguration.
					containerItemFlexEnabled()
			).put(
				"defaultEditorConfigurations", _getDefaultConfigurations()
			).put(
				"defaultLanguageId",
				LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale())
			).put(
				"defaultStyleBookEntryName",
				() -> _getDefaultStyleBookEntryName()
			).put(
				"deleteFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/content_layout/delete_fragment_entry_link_comment")
			).put(
				"deleteItemURL",
				getFragmentEntryActionURL("/content_layout/delete_item")
			).put(
				"discardDraftRedirectURL", themeDisplay.getURLCurrent()
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
				getFragmentEntryActionURL("/content_layout/duplicate_item")
			).put(
				"editFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/content_layout/edit_fragment_entry_link_comment",
					Constants.UPDATE)
			).put(
				"editFragmentEntryLinkURL",
				getFragmentEntryActionURL(
					"/content_layout/edit_fragment_entry_link")
			).put(
				"getAvailableListItemRenderersURL",
				getResourceURL(
					"/content_layout/get_available_list_item_renderers")
			).put(
				"getAvailableListRenderersURL",
				getResourceURL("/content_layout/get_available_list_renderers")
			).put(
				"getAvailableTemplatesURL",
				getResourceURL("/content_layout/get_available_templates")
			).put(
				"getCollectionFieldURL",
				getResourceURL("/content_layout/get_collection_field")
			).put(
				"getCollectionMappingFieldsURL",
				getResourceURL("/content_layout/get_collection_mapping_fields")
			).put(
				"getExperienceUsedPortletsURL",
				getResourceURL("/content_layout/get_experience_used_portlets")
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
				getResourceURL("/content_layout/get_info_item_field_value")
			).put(
				"getInfoItemMappingFieldsURL",
				getResourceURL("/content_layout/get_info_item_mapping_fields")
			).put(
				"getPageContentsURL",
				getResourceURL("/content_layout/get_page_contents")
			).put(
				"imageSelectorURL", _getItemSelectorURL()
			).put(
				"infoItemSelectorURL", _getInfoItemSelectorURL()
			).put(
				"infoListSelectorURL", _getInfoListSelectorURL()
			).put(
				"languageDirection", _getLanguageDirection()
			).put(
				"layoutConversionWarningMessages",
				MultiSessionMessages.get(
					portletRequest, "layoutConversionWarningMessages")
			).put(
				"layoutType", String.valueOf(_getLayoutType())
			).put(
				"lookAndFeelURL", _getLookAndFeelURL()
			).put(
				"mappingFieldsURL",
				getResourceURL("/content_layout/get_mapping_fields")
			).put(
				"markItemForDeletionURL",
				getFragmentEntryActionURL(
					"/content_layout/mark_item_for_deletion")
			).put(
				"masterLayouts", _getMasterLayouts()
			).put(
				"masterUsed", _isMasterUsed()
			).put(
				"moveItemURL",
				getFragmentEntryActionURL(
					"/content_layout/move_fragment_entry_link")
			).put(
				"paddingOptions",
				() -> {
					Set<Map.Entry<Integer, Integer>> entrySet =
						PaddingConverter.externalToInternalValuesMap.entrySet();

					List<Map<String, String>> list = new ArrayList<>();

					for (Map.Entry<Integer, Integer> entry : entrySet) {
						list.add(
							HashMapBuilder.put(
								"label", String.valueOf(entry.getKey())
							).put(
								"value", String.valueOf(entry.getValue())
							).build());
					}

					return list;
				}
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
				getResourceURL(
					"/content_layout/get_page_preview", !isLayoutPageTemplate())
			).put(
				"publishURL", getPublishURL()
			).put(
				"redirectURL", _getRedirect()
			).put(
				"renderFragmentEntryURL",
				getResourceURL("/content_layout/get_fragment_entry_link")
			).put(
				"sidebarPanels", getSidebarPanels()
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
					"/content_layout/unmark_item_for_deletion")
			).put(
				"updateConfigurationValuesURL",
				getFragmentEntryActionURL(
					"/content_layout/update_configuration_values")
			).put(
				"updateItemConfigURL",
				getFragmentEntryActionURL("/content_layout/update_item_config")
			).put(
				"updateLayoutPageTemplateDataURL",
				getFragmentEntryActionURL(
					"/content_layout/update_layout_page_template_data")
			).put(
				"updateRowColumnsURL",
				getFragmentEntryActionURL("/content_layout/update_row_columns")
			).put(
				"updateSegmentsExperiencePriorityURL",
				getFragmentEntryActionURL(
					"/content_layout/update_segments_experience_priority")
			).put(
				"updateSegmentsExperienceURL",
				getFragmentEntryActionURL(
					"/content_layout/update_segments_experience")
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
				"mappedInfoItems", _getMappedInfoItems()
			).put(
				"masterLayout", _getMasterLayoutJSONObject()
			).put(
				"pageContents",
				ContentUtil.getPageContentsJSONArray(
					themeDisplay.getPlid(), httpServletRequest)
			).put(
				"permissions",
				HashMapBuilder.<String, Object>put(
					ContentPageEditorActionKeys.UPDATE, _hasUpdatePermissions()
				).put(
					ContentPageEditorActionKeys.UPDATE_LAYOUT_CONTENT,
					_hasUpdateContentPermissions()
				).build()
			).put(
				"widgets", _getWidgets()
			).build()
		).build();
	}

	public String getPortletNamespace() {
		return _renderResponse.getNamespace();
	}

	public String getPublishURL() {
		return getFragmentEntryActionURL("/content_layout/publish_layout");
	}

	public List<Map<String, Object>> getSidebarPanels() {
		return getSidebarPanels(_getLayoutType());
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

	protected String getFragmentEntryActionURL(String action) {
		return getFragmentEntryActionURL(action, null);
	}

	protected String getFragmentEntryActionURL(String action, String command) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		if (Validator.isNotNull(command)) {
			actionURL.setParameter(Constants.CMD, command);
		}

		return HttpUtil.addParameter(
			actionURL.toString(), "p_l_mode", Constants.EDIT);
	}

	protected long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			httpServletRequest, "groupId", themeDisplay.getScopeGroupId());

		return _groupId;
	}

	protected String getResourceURL(String resourceID) {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID(resourceID);

		return HttpUtil.addParameter(
			resourceURL.toString(), "p_l_mode", Constants.EDIT);
	}

	protected String getResourceURL(String resourceID, boolean doAsGuest)
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

	protected long getSegmentsExperienceId() {
		return SegmentsExperienceConstants.ID_DEFAULT;
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

	protected boolean isLayoutPageTemplate() {
		Layout publishedLayout = _getPublishedLayout();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(publishedLayout.getPlid());

		if (layoutPageTemplateEntry != null) {
			return true;
		}

		return false;
	}

	protected final HttpServletRequest httpServletRequest;
	protected final InfoItemServiceTracker infoItemServiceTracker;
	protected final PortletRequest portletRequest;
	protected final ThemeDisplay themeDisplay;

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
						StringUtil.replace(
							languageId, CharPool.UNDERLINE, CharPool.DASH))
				).put(
					"languageLabel",
					StringUtil.replace(
						languageId, CharPool.UNDERLINE, CharPool.DASH)
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
		InfoListItemSelectorCriterion infoListItemSelectorCriterion =
			new InfoListItemSelectorCriterion();

		infoListItemSelectorCriterion.setItemTypes(
			_getInfoItemFormProviderClassNames());

		infoListItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListItemSelectorReturnType());

		InfoListProviderItemSelectorCriterion
			infoListProviderItemSelectorCriterion =
				new InfoListProviderItemSelectorCriterion();

		infoListProviderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListProviderItemSelectorReturnType());
		infoListProviderItemSelectorCriterion.setItemTypes(
			_getInfoItemFormProviderClassNames());
		infoListProviderItemSelectorCriterion.setPlid(themeDisplay.getPlid());

		PortletURL infoListSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectInfoList",
			infoListItemSelectorCriterion,
			infoListProviderItemSelectorCriterion);

		if (infoListSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoListSelectorURL.toString();
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

	private String _getDefaultStyleBookEntryName() {
		StyleBookEntry styleBookEntry = null;

		Layout layout = themeDisplay.getLayout();

		if (layout.getStyleBookEntryId() > 0) {
			styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
				layout.getStyleBookEntryId());
		}

		if ((styleBookEntry == null) && (layout.getMasterLayoutPlid() > 0)) {
			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getMasterLayoutPlid());

			styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
				masterLayout.getStyleBookEntryId());
		}

		if (styleBookEntry == null) {
			styleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
					layout.getGroupId());
		}

		if (styleBookEntry != null) {
			return styleBookEntry.getName();
		}

		return null;
	}

	private String _getDiscardDraftURL() {
		Layout publishedLayout = _getPublishedLayout();

		if (!Objects.equals(
				publishedLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

			PortletURL discardDraftURL = PortletURLFactoryUtil.create(
				httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.ACTION_PHASE);

			discardDraftURL.setParameter(
				ActionRequest.ACTION_NAME, "/layout/discard_draft_layout");
			discardDraftURL.setParameter(
				"redirect", themeDisplay.getURLCurrent());
			discardDraftURL.setParameter(
				"selPlid", String.valueOf(themeDisplay.getPlid()));

			return discardDraftURL.toString();
		}

		PortletURL deleteLayoutURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.ACTION_PHASE);

		deleteLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/delete_layout");

		PortletURL redirectURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		redirectURL.setParameter(
			"selPlid", String.valueOf(publishedLayout.getPlid()));

		deleteLayoutURL.setParameter("redirect", redirectURL.toString());

		deleteLayoutURL.setParameter(
			"selPlid", String.valueOf(themeDisplay.getPlid()));

		return deleteLayoutURL.toString();
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
			if (!fragmentRenderer.isSelectable(httpServletRequest)) {
				continue;
			}

			if (!_isAllowedFragmentEntryKey(fragmentRenderer.getKey())) {
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

			FragmentRenderer fragmentRenderer =
				fragmentCollectionFragmentRenderers.get(entry.getKey());

			dynamicFragments.add(
				HashMapBuilder.<String, Object>put(
					"fragmentCollectionId", entry.getKey()
				).put(
					"fragmentEntries", entry.getValue()
				).put(
					"name",
					LanguageUtil.get(
						ResourceBundleUtil.getBundle(
							themeDisplay.getLocale(),
							fragmentRenderer.getClass()),
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

			List<FragmentEntry> fragmentEntries =
				fragmentCollectionContributor.getFragmentEntries(
					themeDisplay.getLocale());

			if (ListUtil.isEmpty(fragmentEntries)) {
				continue;
			}

			List<Map<String, Object>> filteredFragmentEntries =
				_getFragmentEntries(fragmentEntries);

			if (ListUtil.isEmpty(filteredFragmentEntries)) {
				continue;
			}

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

		long[] groupIds = {
			themeDisplay.getCompanyGroupId(), getGroupId(),
			CompanyConstants.SYSTEM
		};

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(groupIds);

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

		long[] segmentsExperienceIds = {getSegmentsExperienceId()};

		try {
			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				FragmentEntry fragmentEntry =
					FragmentEntryServiceUtil.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				DefaultFragmentRendererContext fragmentRendererContext =
					new DefaultFragmentRendererContext(fragmentEntryLink);

				fragmentRendererContext.setLocale(themeDisplay.getLocale());
				fragmentRendererContext.setMode(
					FragmentEntryLinkConstants.EDIT);
				fragmentRendererContext.setSegmentsExperienceIds(
					segmentsExperienceIds);

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
					).putAll(
						_getFragmentEntry(
							fragmentEntryLink, fragmentEntry, content)
					).build();

				fragmentEntryLinksMap.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
					fragmentEntryLinkMap);
			}
		}
		finally {
			themeDisplay.setIsolated(isolated);
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
			infoItemClassNames.remove(FileEntry.class.getName());
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

		InfoListProviderItemSelectorCriterion
			infoListProviderItemSelectorCriterion =
				new InfoListProviderItemSelectorCriterion();

		infoListProviderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListProviderItemSelectorReturnType());
		infoListProviderItemSelectorCriterion.setPlid(themeDisplay.getPlid());

		PortletURL infoListSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectInfoList",
			infoListItemSelectorCriterion,
			infoListProviderItemSelectorCriterion);

		if (infoListSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoListSelectorURL.toString();
	}

	private String _getItemSelectorURL() {
		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			_renderResponse.getNamespace() + "selectImage",
			_getImageItemSelectorCriterion(), _getURLItemSelectorCriterion());

		return itemSelectorURL.toString();
	}

	private Map<String, String> _getLanguageDirection() {
		Map<String, String> languageDirection = new HashMap<>();

		for (Locale curLocale :
				LanguageUtil.getAvailableLocales(getGroupId())) {

			languageDirection.put(
				LocaleUtil.toLanguageId(curLocale),
				LanguageUtil.get(curLocale, "lang.dir"));
		}

		return languageDirection;
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
			_layoutType = LayoutPageTemplateEntryTypeConstants.TYPE_BASIC;
		}
		else {
			_layoutType = layoutPageTemplateEntry.getType();
		}

		return _layoutType;
	}

	private Object _getLookAndFeelURL() {
		PortletURL lookAndFeelURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		lookAndFeelURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout");

		lookAndFeelURL.setParameter(
			"redirect",
			ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				"p_l_back_url"));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		lookAndFeelURL.setParameter("backURL", themeDisplay.getURLCurrent());

		Layout layout = themeDisplay.getLayout();

		lookAndFeelURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		lookAndFeelURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		lookAndFeelURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return lookAndFeelURL.toString();
	}

	private Set<Map<String, Object>> _getMappedInfoItems() throws Exception {
		Set<Map<String, Object>> mappedInfoItems = new HashSet<>();

		Set<InfoDisplayObjectProvider<?>> infoDisplayObjectProviders =
			ContentUtil.getMappedInfoDisplayObjectProviders(
				getGroupId(), themeDisplay.getPlid());

		for (InfoDisplayObjectProvider<?> infoDisplayObjectProvider :
				infoDisplayObjectProviders) {

			mappedInfoItems.add(
				HashMapBuilder.<String, Object>put(
					"classNameId", infoDisplayObjectProvider.getClassNameId()
				).put(
					"classPK", infoDisplayObjectProvider.getClassPK()
				).put(
					"title",
					infoDisplayObjectProvider.getTitle(themeDisplay.getLocale())
				).build());
		}

		return mappedInfoItems;
	}

	private JSONObject _getMasterLayoutJSONObject() {
		Layout layout = themeDisplay.getLayout();

		LayoutStructure masterLayoutStructure = _getMasterLayoutStructure();

		return JSONUtil.put(
			"masterLayoutData",
			Optional.ofNullable(
				masterLayoutStructure
			).map(
				LayoutStructure::toJSONObject
			).orElse(
				null
			)
		).put(
			"masterLayoutPlid", layout.getMasterLayoutPlid()
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

	private String _getPortletCategoryTitle(PortletCategory portletCategory) {
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
				portlet, httpServletRequest.getServletContext());

			ResourceBundle portletResourceBundle =
				portletConfig.getResourceBundle(themeDisplay.getLocale());

			String title = ResourceBundleUtil.getString(
				portletResourceBundle, portletCategory.getName());

			if (Validator.isNotNull(title)) {
				return title;
			}
		}

		return LanguageUtil.get(httpServletRequest, portletCategory.getName());
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

	private List<Map<String, Object>> _getPortletItems(Portlet portlet) {
		List<PortletItem> portletItems =
			PortletItemLocalServiceUtil.getPortletItems(
				themeDisplay.getScopeGroupId(), portlet.getPortletId(),
				PortletPreferences.class.getName());

		if (ListUtil.isEmpty(portletItems)) {
			return Collections.emptyList();
		}

		Stream<PortletItem> stream = portletItems.stream();

		return stream.map(
			portletItem -> HashMapBuilder.<String, Object>put(
				"instanceable", portlet.isInstanceable()
			).put(
				"portletId", portlet.getPortletId()
			).put(
				"portletItemId", portletItem.getPortletItemId()
			).put(
				"title", HtmlUtil.escape(portletItem.getName())
			).put(
				"used", _isUsed(portlet)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getPortlets(
		PortletCategory portletCategory) {

		HttpSession httpSession = httpServletRequest.getSession();

		ServletContext servletContext = httpSession.getServletContext();

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		return stream.map(
			portletId -> PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId)
		).filter(
			portlet -> {
				if (portlet == null) {
					return false;
				}

				if (ArrayUtil.contains(
						_UNSUPPORTED_PORTLETS_NAMES,
						portlet.getPortletName())) {

					return false;
				}

				try {
					return PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getLayout(), portlet,
						ActionKeys.ADD_TO_PAGE);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to check portlet permissions for " +
							portlet.getPortletId(),
						portalException);

					return false;
				}
			}
		).sorted(
			new PortletTitleComparator(servletContext, themeDisplay.getLocale())
		).map(
			portlet -> HashMapBuilder.<String, Object>put(
				"instanceable", portlet.isInstanceable()
			).put(
				"portletId", portlet.getPortletId()
			).put(
				"portletItems", _getPortletItems(portlet)
			).put(
				"title",
				PortalUtil.getPortletTitle(
					portlet, servletContext, themeDisplay.getLocale())
			).put(
				"used", _isUsed(portlet)
			).build()
		).collect(
			Collectors.toList()
		);
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

	private List<Map<String, Object>> _getWidgetCategories(
		PortletCategory portletCategory) {

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		Stream<PortletCategory> stream = portletCategories.stream();

		return stream.sorted(
			new PortletCategoryComparator(themeDisplay.getLocale())
		).filter(
			currentPortletCategory -> !currentPortletCategory.isHidden()
		).map(
			currentPortletCategory -> HashMapBuilder.<String, Object>put(
				"categories", _getWidgetCategories(currentPortletCategory)
			).put(
				"path",
				StringUtil.replace(
					currentPortletCategory.getPath(), new String[] {"/", "."},
					new String[] {"-", "-"})
			).put(
				"portlets", _getPortlets(currentPortletCategory)
			).put(
				"title", _getPortletCategoryTitle(currentPortletCategory)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getWidgets() throws Exception {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = PortletCategoryUtil.getRelevantPortletCategory(
			themeDisplay.getPermissionChecker(), themeDisplay.getCompanyId(),
			themeDisplay.getLayout(), portletCategory,
			themeDisplay.getLayoutTypePortlet());

		return _getWidgetCategories(portletCategory);
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
				_log.debug(exception, exception);
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
				_log.debug(exception, exception);
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

	private boolean _isUsed(Portlet portlet) {
		if (portlet.isInstanceable()) {
			return false;
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
				portlet.getPortletId());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static final String[] _UNSUPPORTED_PORTLETS_NAMES = {
		"com_liferay_nested_portlets_web_portlet_NestedPortletsPortlet"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ContentPageEditorDisplayContext.class);

	private Boolean _allowNewFragmentEntries;
	private final CommentManager _commentManager;
	private final List<ContentPageEditorSidebarPanel>
		_contentPageEditorSidebarPanels;
	private Map<String, Object> _defaultConfigurations;
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
	private List<Map<String, Object>> _sidebarPanels;
	private ItemSelectorCriterion _urlItemSelectorCriterion;

}