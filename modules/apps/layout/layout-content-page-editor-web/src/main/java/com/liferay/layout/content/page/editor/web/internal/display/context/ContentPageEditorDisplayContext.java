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

import com.liferay.fragment.constants.FragmentActionKeys;
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
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.comment.CommentUtil;
import com.liferay.layout.content.page.editor.web.internal.configuration.ContentPageEditorTypeConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.util.ContentCreationContentPageEditorConfigurationUtil;
import com.liferay.layout.content.page.editor.web.internal.util.ContentUtil;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkItemSelectorUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.layout.util.constants.LayoutConverterTypeConstants;
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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		CommentManager commentManager,
		List<ContentPageEditorSidebarPanel> contentPageEditorSidebarPanels,
		FragmentRendererController fragmentRendererController,
		PortletRequest portletRequest) {

		this.httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
		_commentManager = commentManager;
		_contentPageEditorSidebarPanels = contentPageEditorSidebarPanels;
		_fragmentRendererController = fragmentRendererController;
		_portletRequest = portletRequest;

		contentPageEditorTypeConfiguration =
			(ContentPageEditorTypeConfiguration)httpServletRequest.getAttribute(
				ContentPageEditorTypeConfiguration.class.getName());
		infoDisplayContributorTracker =
			(InfoDisplayContributorTracker)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER);
		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)
				httpServletRequest.getAttribute(
					ContentPageEditorWebKeys.
						FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_fragmentEntryConfigurationParser =
			(FragmentEntryConfigurationParser)httpServletRequest.getAttribute(
				FragmentEntryConfigurationParser.class.getName());
		_fragmentRendererTracker =
			(FragmentRendererTracker)httpServletRequest.getAttribute(
				FragmentActionKeys.FRAGMENT_RENDERER_TRACKER);
		_itemSelector = (ItemSelector)httpServletRequest.getAttribute(
			ContentPageEditorWebKeys.ITEM_SELECTOR);
	}

	public String getDiscardDraftURL() throws PortalException {
		Layout layout = _getPublishedLayout();

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			return getFragmentEntryActionURL(
				"/content_layout/discard_draft_layout");
		}

		PortletURL deleteLayoutURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.ACTION_PHASE);

		deleteLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/delete_layout");

		PortletURL redirectURL = PortalUtil.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		redirectURL.setParameter("selPlid", String.valueOf(layout.getPlid()));

		deleteLayoutURL.setParameter("redirect", redirectURL.toString());

		Layout draftLayout = themeDisplay.getLayout();

		deleteLayoutURL.setParameter(
			"selPlid", String.valueOf(draftLayout.getPlid()));

		return deleteLayoutURL.toString();
	}

	public Map<String, Object> getEditorReactContext(
			String npmResolvedPackageName)
		throws Exception {

		SoyContext editorSoyContext = getEditorSoyContext();

		return HashMapBuilder.<String, Object>put(
			"config",
			HashMapBuilder.<String, Object>put(
				"addFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/content_layout/add_fragment_entry_link_comment")
			).put(
				"addFragmentEntryLinkURL",
				getFragmentEntryActionURL(
					"/content_layout/add_fragment_entry_link_react")
			).put(
				"addItemURL",
				getFragmentEntryActionURL("/content_layout/add_item_react")
			).put(
				"addPortletURL",
				getFragmentEntryActionURL("/content_layout/add_portlet_react")
			).put(
				"addSegmentsExperienceURL",
				editorSoyContext.get("addSegmentsExperienceURL")
			).put(
				"availableLanguages", editorSoyContext.get("availableLanguages")
			).put(
				"availableSegmentsEntries",
				editorSoyContext.get("availableSegmentsEntries")
			).put(
				"classNameId", editorSoyContext.get("classNameId")
			).put(
				"classPK", editorSoyContext.get("classPK")
			).put(
				"defaultEditorConfigurations", _getDefaultConfigurations()
			).put(
				"defaultLanguageId", editorSoyContext.get("defaultLanguageId")
			).put(
				"defaultSegmentsEntryId",
				editorSoyContext.get("defaultSegmentsEntryId")
			).put(
				"defaultSegmentsExperienceId",
				editorSoyContext.get("defaultSegmentsExperienceId")
			).put(
				"deleteFragmentEntryLinkCommentURL",
				getFragmentEntryActionURL(
					"/content_layout/delete_fragment_entry_link_comment")
			).put(
				"deleteItemURL",
				getFragmentEntryActionURL("/content_layout/delete_item_react")
			).put(
				"deleteSegmentsExperienceURL",
				getFragmentEntryActionURL(
					"/content_layout/delete_segments_experience")
			).put(
				"discardDraftRedirectURL",
				editorSoyContext.get("discardDraftRedirectURL")
			).put(
				"discardDraftURL", editorSoyContext.get("discardDraftURL")
			).put(
				"duplicateItemURL",
				getFragmentEntryActionURL(
					"/content_layout/duplicate_item_react")
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
				"editSegmentsEntryURL",
				editorSoyContext.get("editSegmentsEntryURL")
			).put(
				"fragments", editorSoyContext.get("elements")
			).put(
				"getAssetFieldValueURL",
				editorSoyContext.get("getAssetFieldValueURL")
			).put(
				"getAssetMappingFieldsURL",
				editorSoyContext.get("getAssetMappingFieldsURL")
			).put(
				"getAvailableTemplatesURL",
				editorSoyContext.get("getAvailableTemplatesURL")
			).put(
				"getExperienceUsedPortletsURL",
				editorSoyContext.get("getExperienceUsedPortletsURL")
			).put(
				"hasEditSegmentsEntryPermission",
				editorSoyContext.get("hasEditSegmentsEntryPermission")
			).put(
				"hasUpdateContentPermissions",
				editorSoyContext.get("hasUpdateContentPermissions")
			).put(
				"hasUpdatePermissions",
				editorSoyContext.get("hasUpdatePermissions")
			).put(
				"imageSelectorURL", editorSoyContext.get("imageSelectorURL")
			).put(
				"infoItemSelectorURL",
				editorSoyContext.get("infoItemSelectorURL")
			).put(
				"lookAndFeelURL", _getLookAndFeelURL()
			).put(
				"mappingFieldsURL", editorSoyContext.get("mappingFieldsURL")
			).put(
				"moveItemURL",
				getFragmentEntryActionURL(
					"/content_layout/move_fragment_entry_link_react")
			).put(
				"pageType", editorSoyContext.get("pageType")
			).put(
				"pluginsRootPath",
				npmResolvedPackageName + "/page_editor/plugins"
			).put(
				"portletNamespace", editorSoyContext.get("portletNamespace")
			).put(
				"publishURL", editorSoyContext.get("publishURL")
			).put(
				"redirectURL", editorSoyContext.get("redirectURL")
			).put(
				"renderFragmentEntryURL",
				editorSoyContext.get("renderFragmentEntryURL")
			).put(
				"selectedMappingTypes",
				editorSoyContext.get("selectedMappingTypes")
			).put(
				"sidebarPanels", editorSoyContext.get("sidebarPanels")
			).put(
				"singleSegmentsExperienceMode",
				editorSoyContext.get("singleSegmentsExperienceMode")
			).put(
				"themeColorsCssClasses", _getThemeColorsCssClasses()
			).put(
				"updateItemConfigURL",
				getFragmentEntryActionURL(
					"/content_layout/update_item_config_react")
			).put(
				"updateLayoutPageTemplateDataURL",
				editorSoyContext.get("updateLayoutPageTemplateDataURL")
			).put(
				"updateRowColumnsURL",
				getFragmentEntryActionURL(
					"/content_layout/update_row_columns_react")
			).put(
				"updateSegmentsExperiencePriorityURL",
				getFragmentEntryActionURL(
					"/content_layout/update_segments_experience_priority")
			).put(
				"updateSegmentsExperienceURL",
				getFragmentEntryActionURL(
					"/content_layout/update_segments_experience")
			).build()
		).put(
			"state",
			HashMapBuilder.<String, Object>put(
				"availableSegmentsExperiences",
				editorSoyContext.get("availableSegmentsExperiences")
			).put(
				"fragmentEntryLinks", editorSoyContext.get("fragmentEntryLinks")
			).put(
				"hasLockedSegmentsExperiment",
				editorSoyContext.get("hasLockedSegmentsExperiment")
			).put(
				"languageId", editorSoyContext.get("languageId")
			).put(
				"layoutData", editorSoyContext.get("layoutData")
			).put(
				"layoutDataList", editorSoyContext.get("layoutDataList")
			).put(
				"mappedInfoItems", editorSoyContext.get("mappedInfoItems")
			).put(
				"masterLayoutData", editorSoyContext.get("masterLayoutData")
			).put(
				"pageContents", editorSoyContext.get("pageContents")
			).put(
				"segmentsExperienceId",
				editorSoyContext.get("segmentsExperienceId")
			).put(
				"showResolvedComments",
				GetterUtil.getBoolean(
					editorSoyContext.get("showResolvedComments"))
			).put(
				"widgets", editorSoyContext.get("widgets")
			).build()
		).build();
	}

	public SoyContext getEditorSoyContext() throws Exception {
		if (_editorSoyContext != null) {
			return _editorSoyContext;
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkCommentURL",
			getFragmentEntryActionURL(
				"/content_layout/add_fragment_entry_link_comment")
		).put(
			"addFragmentEntryLinkURL",
			getFragmentEntryActionURL("/content_layout/add_fragment_entry_link")
		).put(
			"addPortletURL",
			getFragmentEntryActionURL("/content_layout/add_portlet")
		).put(
			"addStructuredContentURL",
			getFragmentEntryActionURL("/content_layout/add_structured_content")
		).put(
			"availableLanguages", _getAvailableLanguagesSoyContext()
		).put(
			"classNameId", PortalUtil.getClassNameId(Layout.class.getName())
		).put(
			"classPK", themeDisplay.getPlid()
		).put(
			"contentCreationEnabled",
			ContentCreationContentPageEditorConfigurationUtil.
				isContentCreationEnabled(themeDisplay.getCompanyId())
		).put(
			"defaultEditorConfigurations", _getDefaultConfigurations()
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale())
		).put(
			"deleteFragmentEntryLinkCommentURL",
			getFragmentEntryActionURL(
				"/content_layout/delete_fragment_entry_link_comment")
		).put(
			"deleteFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/delete_fragment_entry_link")
		).put(
			"discardDraftRedirectURL", themeDisplay.getURLCurrent()
		).put(
			"discardDraftURL", getDiscardDraftURL()
		).put(
			"duplicateFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/duplicate_fragment_entry_link")
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
			"elements", _getFragmentCollectionsSoyContexts()
		).put(
			"fragmentEntryLinks", _getFragmentEntryLinksSoyContext()
		).put(
			"getAssetFieldValueURL",
			_getResourceURL("/content_layout/get_asset_field_value")
		).put(
			"getAssetMappingFieldsURL",
			_getResourceURL("/content_layout/get_asset_mapping_fields")
		).put(
			"getAvailableTemplatesURL",
			_getResourceURL("/content_layout/get_available_templates")
		).put(
			"getContentStructureMappingFieldsURL",
			_getResourceURL(
				"/content_layout/get_content_structure_mapping_fields")
		).put(
			"getContentStructuresURL",
			_getResourceURL("/content_layout/get_content_structures")
		).put(
			"getExperienceUsedPortletsURL",
			_getResourceURL("/content_layout/get_experience_used_portlets")
		).put(
			"getPageContentsURL",
			_getResourceURL("/content_layout/get_page_contents")
		).put(
			"hasUpdateContentPermissions", _hasUpdateContentPermissions()
		).put(
			"hasUpdatePermissions", _hasUpdatePermissions()
		).put(
			"imageSelectorURL", _getItemSelectorURL()
		).put(
			"infoItemSelectorURL", _getInfoItemSelectorURL()
		).put(
			"languageId", themeDisplay.getLanguageId()
		).put(
			"layoutConversionWarningMessages",
			MultiSessionMessages.get(
				_portletRequest, "layoutConversionWarningMessages")
		).put(
			"layoutData", JSONFactoryUtil.createJSONObject(_getLayoutData())
		).put(
			"lookAndFeelURL", _getLookAndFeelURL()
		).put(
			"mappedInfoItems", _getMappedInfoItemsSoyContexts()
		).put(
			"masterLayoutData",
			JSONFactoryUtil.createJSONObject(_getMasterLayoutData())
		).put(
			"masterUsed", _isMasterUsed()
		).put(
			"pageContents",
			ContentUtil.getPageContentsJSONArray(
				themeDisplay.getPlid(), httpServletRequest)
		).put(
			"pageType", String.valueOf(_getPageType())
		).put(
			"pending",
			_publishedLayout.getStatus() == WorkflowConstants.STATUS_PENDING
		).put(
			"portletNamespace", getPortletNamespace()
		).put(
			"publishURL", getPublishURL()
		).put(
			"redirectURL", _getRedirect()
		).put(
			"renderFragmentEntryURL",
			getFragmentEntryActionURL("/content_layout/render_fragment_entry")
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
		).put(
			"workflowEnabled",
			WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_publishedLayout.getCompanyId(), _publishedLayout.getGroupId(),
				Layout.class.getName())
		);

		_editorSoyContext = soyContext;

		return _editorSoyContext;
	}

	public String getEditorType() {
		return contentPageEditorTypeConfiguration.type();
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

		Layout draftLayout = themeDisplay.getLayout();

		Layout layout = _getPublishedLayout();

		soyContext.put(
			"draft", draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT
		).put(
			"lastSaveDate", StringPool.BLANK
		).put(
			"masterUsed", _isMasterUsed()
		).put(
			"pageType", String.valueOf(_getPageType())
		).put(
			"pending", layout.getStatus() == WorkflowConstants.STATUS_PENDING
		).put(
			"portletNamespace", _renderResponse.getNamespace()
		).put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"workflowEnabled",
			WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				layout.getCompanyId(), layout.getGroupId(),
				Layout.class.getName())
		);

		return soyContext;
	}

	public String getPortletNamespace() {
		return _renderResponse.getNamespace();
	}

	public String getPublishURL() {
		return getFragmentEntryActionURL("/content_layout/publish_layout");
	}

	public boolean isSingleSegmentsExperienceMode() {
		return false;
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

	protected long getSegmentsExperienceId() {
		return SegmentsExperienceConstants.ID_DEFAULT;
	}

	protected List<SoyContext> getSidebarPanelSoyContexts(
		boolean pageIsDisplayPage) {

		if (_sidebarPanelSoyContexts != null) {
			return _sidebarPanelSoyContexts;
		}

		List<SoyContext> soyContexts = new ArrayList<>();

		for (ContentPageEditorSidebarPanel contentPageEditorSidebarPanel :
				_contentPageEditorSidebarPanels) {

			if (!contentPageEditorSidebarPanel.isVisible(pageIsDisplayPage) ||
				!contentPageEditorSidebarPanel.isVisible(
					themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
					pageIsDisplayPage)) {

				continue;
			}

			if (contentPageEditorSidebarPanel.includeSeparator() &&
				!soyContexts.isEmpty()) {

				SoyContext availableSoyContext =
					SoyContextFactoryUtil.createSoyContext();

				availableSoyContext.put("sidebarPanelId", "separator");

				soyContexts.add(availableSoyContext);
			}

			SoyContext availableSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			availableSoyContext.put(
				"icon", contentPageEditorSidebarPanel.getIcon()
			).put(
				"label",
				contentPageEditorSidebarPanel.getLabel(themeDisplay.getLocale())
			).put(
				"sidebarPanelId", contentPageEditorSidebarPanel.getId()
			);

			soyContexts.add(availableSoyContext);
		}

		_sidebarPanelSoyContexts = soyContexts;

		return _sidebarPanelSoyContexts;
	}

	protected final ContentPageEditorTypeConfiguration
		contentPageEditorTypeConfiguration;
	protected final HttpServletRequest httpServletRequest;
	protected final InfoDisplayContributorTracker infoDisplayContributorTracker;
	protected final ThemeDisplay themeDisplay;

	private SoyContext _getAvailableLanguagesSoyContext() {
		SoyContext availableLanguagesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		String[] languageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()));

		for (String languageId : languageIds) {
			SoyContext languageSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			String languageIcon = StringUtil.toLowerCase(
				StringUtil.replace(
					languageId, CharPool.UNDERLINE, CharPool.DASH));

			languageSoyContext.put("languageIcon", languageIcon);

			String languageLabel = StringUtil.replace(
				languageId, CharPool.UNDERLINE, CharPool.DASH);

			languageSoyContext.put("languageLabel", languageLabel);

			availableLanguagesSoyContext.put(languageId, languageSoyContext);
		}

		return availableLanguagesSoyContext;
	}

	private SoyContext _getContributedFragmentEntrySoyContext(
		String rendererKey) {

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(
				themeDisplay.getLocale());

		FragmentEntry fragmentEntry = fragmentEntries.get(rendererKey);

		if (fragmentEntry != null) {
			soyContext.put(
				"fragmentEntryId", 0
			).put(
				"name", fragmentEntry.getName()
			);
		}

		return soyContext;
	}

	private Map<String, Object> _getDefaultConfigurations() {
		if (_defaultConfigurations != null) {
			return _defaultConfigurations;
		}

		Map<String, Object> configurations = HashMapBuilder.<String, Object>put(
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

		_defaultConfigurations = configurations;

		return _defaultConfigurations;
	}

	private JSONObject _getDropZoneConfigJSONObject(
		JSONObject masterLayoutDataJSONObject) {

		if (!LayoutDataConverter.isLatestVersion(masterLayoutDataJSONObject)) {
			return masterLayoutDataJSONObject;
		}

		JSONObject itemsJSONObject = masterLayoutDataJSONObject.getJSONObject(
			"items");

		JSONObject rootItemsJSONObject =
			masterLayoutDataJSONObject.getJSONObject("rootItems");

		String dropZoneId = rootItemsJSONObject.getString("dropZone");

		JSONObject dropZoneJSONObject = itemsJSONObject.getJSONObject(
			dropZoneId);

		if (dropZoneJSONObject == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return dropZoneJSONObject.getJSONObject("config");
	}

	private List<SoyContext> _getDynamicFragmentsSoyContexts() {
		List<SoyContext> soyContexts = new ArrayList<>();

		Map<String, List<SoyContext>> fragmentCollectionSoyContextsMap =
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

			SoyContext dynamicFragmentSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			dynamicFragmentSoyContext.put(
				"fragmentEntryKey", fragmentRenderer.getKey()
			).put(
				"imagePreviewURL",
				fragmentRenderer.getImagePreviewURL(httpServletRequest)
			).put(
				"name", fragmentRenderer.getLabel(themeDisplay.getLocale())
			);

			List<SoyContext> fragmentCollectionSoyContexts =
				fragmentCollectionSoyContextsMap.get(
					fragmentRenderer.getCollectionKey());

			if (fragmentCollectionSoyContexts == null) {
				List<SoyContext> dynamicFragmentSoyContexts =
					fragmentCollectionSoyContextsMap.computeIfAbsent(
						fragmentRenderer.getCollectionKey(),
						key -> new ArrayList<>());

				dynamicFragmentSoyContexts.add(dynamicFragmentSoyContext);

				fragmentCollectionSoyContextsMap.put(
					fragmentRenderer.getCollectionKey(),
					dynamicFragmentSoyContexts);

				fragmentCollectionFragmentRenderers.put(
					fragmentRenderer.getCollectionKey(), fragmentRenderer);
			}
			else {
				fragmentCollectionSoyContexts.add(dynamicFragmentSoyContext);
			}
		}

		for (Map.Entry<String, List<SoyContext>> entry :
				fragmentCollectionSoyContextsMap.entrySet()) {

			FragmentRenderer fragmentRenderer =
				fragmentCollectionFragmentRenderers.get(entry.getKey());

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentCollectionId", entry.getKey()
			).put(
				"fragmentEntries", entry.getValue()
			).put(
				"name",
				LanguageUtil.get(
					ResourceBundleUtil.getBundle(
						themeDisplay.getLocale(), fragmentRenderer.getClass()),
					"fragment.collection.label." + entry.getKey())
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private List<SoyContext> _getFragmentCollectionContributorSoyContexts() {
		List<SoyContext> soyContexts = new ArrayList<>();

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

			List<SoyContext> fragmentEntriesSoyContexts =
				_getFragmentEntriesSoyContexts(fragmentEntries);

			if (ListUtil.isEmpty(fragmentEntriesSoyContexts)) {
				continue;
			}

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentCollectionId",
				fragmentCollectionContributor.getFragmentCollectionKey()
			).put(
				"fragmentEntries", fragmentEntriesSoyContexts
			).put(
				"name",
				fragmentCollectionContributor.getName(themeDisplay.getLocale())
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private List<SoyContext> _getFragmentCollectionsSoyContexts() {
		List<SoyContext> soyContexts =
			_getFragmentCollectionContributorSoyContexts();

		soyContexts.addAll(_getDynamicFragmentsSoyContexts());

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

			if (ListUtil.isEmpty(fragmentEntries)) {
				continue;
			}

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			List<SoyContext> fragmentEntriesSoyContexts =
				_getFragmentEntriesSoyContexts(fragmentEntries);

			if (ListUtil.isEmpty(fragmentEntriesSoyContexts)) {
				continue;
			}

			soyContext.put(
				"fragmentCollectionId",
				fragmentCollection.getFragmentCollectionId()
			).put(
				"fragmentEntries", fragmentEntriesSoyContexts
			).put(
				"name", fragmentCollection.getName()
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private List<SoyContext> _getFragmentEntriesSoyContexts(
		List<FragmentEntry> fragmentEntries) {

		List<SoyContext> soyContexts = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			if (!_isAllowedFragmentEntryKey(
					fragmentEntry.getFragmentEntryKey())) {

				continue;
			}

			soyContext.put(
				"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
			).put(
				"groupId", fragmentEntry.getGroupId()
			).put(
				"imagePreviewURL",
				fragmentEntry.getImagePreviewURL(themeDisplay)
			).put(
				"name", fragmentEntry.getName()
			).put(
				"type", fragmentEntry.getType()
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private List<String> _getFragmentEntryKeys() {
		if (_fragmentEntryKeys != null) {
			return _fragmentEntryKeys;
		}

		String masterLayoutData = _getMasterLayoutData();

		if (Validator.isNull(masterLayoutData)) {
			_fragmentEntryKeys = Collections.emptyList();

			return _fragmentEntryKeys;
		}

		try {
			List<String> fragmentEntryKeys = new ArrayList<>();

			JSONObject masterLayoutDataJSONObject =
				JSONFactoryUtil.createJSONObject(masterLayoutData);

			JSONObject dropZoneJSONObject = _getDropZoneConfigJSONObject(
				masterLayoutDataJSONObject);

			JSONArray fragmentEntryKeysJSONArray =
				dropZoneJSONObject.getJSONArray("fragmentEntryKeys");

			Iterator<String> iteratorFragmentEntryKeys =
				fragmentEntryKeysJSONArray.iterator();

			iteratorFragmentEntryKeys.forEachRemaining(
				fragmentEntryKey -> fragmentEntryKeys.add(fragmentEntryKey));

			_fragmentEntryKeys = fragmentEntryKeys;

			return _fragmentEntryKeys;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get structure JSON array", exception);
			}
		}

		_fragmentEntryKeys = Collections.emptyList();

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

	private SoyContext _getFragmentEntryLinksSoyContext()
		throws PortalException {

		if (_soyContextFragmentEntryLinksSoyContext != null) {
			return _soyContextFragmentEntryLinksSoyContext;
		}

		SoyContext soyContexts = SoyContextFactoryUtil.createSoyContext();

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>(
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				getGroupId(), PortalUtil.getClassNameId(Layout.class.getName()),
				themeDisplay.getPlid()));

		Layout layout = themeDisplay.getLayout();

		if (layout.getMasterLayoutPlid() > 0) {
			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						layout.getMasterLayoutPlid());

			fragmentEntryLinks.addAll(
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
					getGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					masterLayoutPageTemplateEntry.getPlid()));
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
					fragmentRendererContext, httpServletRequest,
					PortalUtil.getHttpServletResponse(_renderResponse));

				JSONObject editableValuesJSONObject =
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues());

				boolean error = false;

				if (SessionErrors.contains(
						httpServletRequest, "fragmentEntryContentInvalid")) {

					error = true;

					SessionErrors.clear(httpServletRequest);
				}

				soyContext.put(
					"comments",
					_getFragmentEntryLinkCommentsJSONArray(fragmentEntryLink));

				String configuration =
					_fragmentRendererController.getConfiguration(
						fragmentRendererContext);

				JSONObject configurationJSONObject =
					JSONFactoryUtil.createJSONObject(configuration);

				FragmentEntryLinkItemSelectorUtil.
					addFragmentEntryLinkFieldsSelectorURL(
						_itemSelector, httpServletRequest,
						liferayPortletResponse, configurationJSONObject);

				soyContext.put(
					"configuration", configurationJSONObject
				).putHTML(
					"content", content
				).put(
					"defaultConfigurationValues",
					_fragmentEntryConfigurationParser.
						getConfigurationDefaultValuesJSONObject(configuration)
				).put(
					"editableValues", editableValuesJSONObject
				).put(
					"error", error
				).put(
					"fragmentEntryLinkId",
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
				).put(
					"masterLayout",
					layout.getMasterLayoutPlid() ==
						fragmentEntryLink.getClassPK()
				).putAll(
					_getFragmentEntrySoyContext(
						fragmentEntryLink, fragmentEntry, content)
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

	private SoyContext _getFragmentEntrySoyContext(
		FragmentEntryLink fragmentEntryLink, FragmentEntry fragmentEntry,
		String content) {

		if (fragmentEntry != null) {
			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId()
			).put(
				"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
			).put(
				"name", fragmentEntry.getName()
			);

			return soyContext;
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNotNull(rendererKey)) {
			SoyContext soyContext = _getContributedFragmentEntrySoyContext(
				rendererKey);

			if (!soyContext.isEmpty()) {
				return soyContext;
			}

			soyContext = SoyContextFactoryUtil.createSoyContext();

			FragmentRenderer fragmentRenderer =
				_fragmentRendererTracker.getFragmentRenderer(
					fragmentEntryLink.getRendererKey());

			if (fragmentRenderer != null) {
				soyContext.put(
					"fragmentEntryId", 0
				).put(
					"fragmentEntryKey", fragmentRenderer.getKey()
				).put(
					"name", fragmentRenderer.getLabel(themeDisplay.getLocale())
				);
			}

			if (!soyContext.isEmpty()) {
				return soyContext;
			}
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

		ItemSelectorCriterion itemSelectorCriterion =
			new ImageItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		_imageItemSelectorCriterion = itemSelectorCriterion;

		return _imageItemSelectorCriterion;
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

	private String _getItemSelectorURL() {
		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
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
					themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					themeDisplay.getPlid(), true);

		String layoutData = layoutPageTemplateStructure.getData(
			getSegmentsExperienceId());

		JSONObject layoutDataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutData);

		if (Objects.equals(
				contentPageEditorTypeConfiguration.type(), "react") &&
			!LayoutDataConverter.isLatestVersion(layoutDataJSONObject)) {

			layoutData = LayoutDataConverter.convert(layoutData);

			LayoutPageTemplateStructureLocalServiceUtil.
				updateLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					themeDisplay.getPlid(), getSegmentsExperienceId(),
					layoutData);
		}

		_layoutData = layoutData;

		return _layoutData;
	}

	private String _getLookAndFeelURL() {
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

	private Set<SoyContext> _getMappedInfoItemsSoyContexts()
		throws PortalException {

		Set<SoyContext> mappedInfoItemsSoyContexts = new HashSet<>();

		Set<InfoDisplayObjectProvider> infoDisplayObjectProviders =
			ContentUtil.getMappedInfoDisplayObjectProviders(
				_groupId, themeDisplay.getPlid());

		for (InfoDisplayObjectProvider infoDisplayObjectProvider :
				infoDisplayObjectProviders) {

			SoyContext mappedInfoItemSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			mappedInfoItemSoyContext.put(
				"classNameId", infoDisplayObjectProvider.getClassNameId()
			).put(
				"classPK", infoDisplayObjectProvider.getClassPK()
			).put(
				"title",
				infoDisplayObjectProvider.getTitle(themeDisplay.getLocale())
			);

			mappedInfoItemsSoyContexts.add(mappedInfoItemSoyContext);
		}

		return mappedInfoItemsSoyContexts;
	}

	private String _getMasterLayoutData() {
		if (_masterLayoutData != null) {
			return _masterLayoutData;
		}

		_masterLayoutData = StringPool.BLANK;

		Layout layout = themeDisplay.getLayout();

		if (layout.getMasterLayoutPlid() <= 0) {
			return _masterLayoutData;
		}

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(
					layout.getMasterLayoutPlid());

		if (masterLayoutPageTemplateEntry == null) {
			return _masterLayoutData;
		}

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(
						getGroupId(),
						PortalUtil.getClassNameId(Layout.class.getName()),
						masterLayoutPageTemplateEntry.getPlid(), true);

			String layoutData = layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT);

			JSONObject layoutDataJSONObject = JSONFactoryUtil.createJSONObject(
				layoutData);

			if (Objects.equals(
					contentPageEditorTypeConfiguration.type(), "react") &&
				!LayoutDataConverter.isLatestVersion(layoutDataJSONObject)) {

				layoutData = LayoutDataConverter.convert(layoutData);

				LayoutPageTemplateStructureLocalServiceUtil.
					updateLayoutPageTemplateStructure(
						themeDisplay.getScopeGroupId(),
						PortalUtil.getClassNameId(Layout.class.getName()),
						masterLayoutPageTemplateEntry.getPlid(),
						SegmentsExperienceConstants.ID_DEFAULT, layoutData);
			}

			_masterLayoutData = layoutData;

			return _masterLayoutData;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get master layout data", exception);
			}
		}

		return _masterLayoutData;
	}

	private int _getPageType() throws PortalException {
		if (_pageType != null) {
			return _pageType;
		}

		Layout publishedLayout = _getPublishedLayout();

		if (Objects.equals(
				publishedLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

			_pageType = LayoutConverterTypeConstants.TYPE_CONVERSION;

			return _pageType;
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
			_pageType = LayoutPageTemplateEntryTypeConstants.TYPE_BASIC;
		}
		else {
			_pageType = layoutPageTemplateEntry.getType();
		}

		return _pageType;
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

	private List<SoyContext> _getPortletsSoyContexts(
		PortletCategory portletCategory) {

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		HttpSession session = httpServletRequest.getSession();

		ServletContext servletContext = session.getServletContext();

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

	private Layout _getPublishedLayout() throws PortalException {
		if (_publishedLayout != null) {
			return _publishedLayout;
		}

		Layout draftLayout = themeDisplay.getLayout();

		_publishedLayout = LayoutLocalServiceUtil.getLayout(
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

		return resourceURL.toString();
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

	private List<SoyContext> _getWidgetCategoriesSoyContexts(
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
					"categories", _getWidgetCategoriesSoyContexts(category)
				).put(
					"path",
					StringUtil.replace(
						category.getPath(), new String[] {"/", "."},
						new String[] {"-", "-"})
				).put(
					"portlets", _getPortletsSoyContexts(category)
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

		return _getWidgetCategoriesSoyContexts(portletCategory);
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

		String masterLayoutData = _getMasterLayoutData();

		try {
			JSONObject masterLayoutDataJSONObject =
				JSONFactoryUtil.createJSONObject(masterLayoutData);

			JSONObject dropZoneJSONObject = _getDropZoneConfigJSONObject(
				masterLayoutDataJSONObject);

			_allowNewFragmentEntries = dropZoneJSONObject.getBoolean(
				"allowNewFragmentEntries", true);

			return _allowNewFragmentEntries;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get structure JSON array", exception);
			}
		}

		return _allowNewFragmentEntries;
	}

	private boolean _isMasterUsed() throws PortalException {
		if (_getPageType() !=
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) {

			return false;
		}

		Layout layout = _getPublishedLayout();

		int masterUsagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			themeDisplay.getScopeGroupId(), layout.getPlid());

		if (masterUsagesCount > 0) {
			return true;
		}

		return false;
	}

	private boolean _isUsed(Portlet portlet, long plid) {
		if (portlet.isInstanceable()) {
			return false;
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
				PortletIdCodec.encode(
					portlet.getPortletId(), String.valueOf(CharPool.NUMBER_0)));

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
	private SoyContext _editorSoyContext;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private List<String> _fragmentEntryKeys;
	private final FragmentRendererController _fragmentRendererController;
	private final FragmentRendererTracker _fragmentRendererTracker;
	private Long _groupId;
	private ItemSelectorCriterion _imageItemSelectorCriterion;
	private final ItemSelector _itemSelector;
	private String _layoutData;
	private String _masterLayoutData;
	private Integer _pageType;
	private final PortletRequest _portletRequest;
	private Layout _publishedLayout;
	private String _redirect;
	private final RenderResponse _renderResponse;
	private List<SoyContext> _sidebarPanelSoyContexts;
	private SoyContext _soyContextFragmentEntryLinksSoyContext;
	private ItemSelectorCriterion _urlItemSelectorCriterion;

}
