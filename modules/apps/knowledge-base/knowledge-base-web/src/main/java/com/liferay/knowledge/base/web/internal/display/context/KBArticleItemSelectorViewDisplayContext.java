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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.util.comparator.KBObjectsModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsPriorityComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsTitleComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsViewCountComparator;
import com.liferay.knowledge.base.web.internal.constants.KBField;
import com.liferay.knowledge.base.web.internal.search.KBSearcher;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class KBArticleItemSelectorViewDisplayContext {

	public KBArticleItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest,
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL, boolean search) {

		_httpServletRequest = httpServletRequest;
		_infoItemItemSelectorCriterion = infoItemItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;
		_search = search;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_portletResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
			"item-selector-display-style", "descriptive");

		return _displayStyle;
	}

	public String getGroupCssIcon(long groupId) throws PortalException {
		Group group = GroupServiceUtil.getGroup(groupId);

		return group.getIconCssClass();
	}

	public String getGroupLabel(long groupId, Locale locale)
		throws PortalException {

		Group group = GroupServiceUtil.getGroup(groupId);

		return group.getDescriptiveName(locale);
	}

	public Map<String, Object> getKBArticleContext(KBArticle kbArticle) {
		return HashMapBuilder.<String, Object>put(
			"returnType", InfoItemItemSelectorReturnType.class.getName()
		).put(
			"value",
			() -> JSONUtil.put(
				"className", KBArticle.class.getName()
			).put(
				"classNameId",
				PortalUtil.getClassNameId(KBArticle.class.getName())
			).put(
				"classPK", kbArticle.getResourcePrimKey()
			).put(
				"title", kbArticle.getTitle()
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					_themeDisplay.getLocale(), KBArticle.class.getName())
			).toString()
		).build();
	}

	public String getKBArticleDataReturnType() {
		return InfoItemItemSelectorReturnType.class.getName();
	}

	public String getKBArticleDataValue(KBArticle kbArticle) {
		return JSONUtil.put(
			"className", KBArticle.class.getName()
		).put(
			"classNameId", PortalUtil.getClassNameId(KBArticle.class.getName())
		).put(
			"classPK", kbArticle.getResourcePrimKey()
		).put(
			"title", kbArticle.getTitle()
		).put(
			"type",
			ResourceActionsUtil.getModelResource(
				_themeDisplay.getLocale(), KBArticle.class.getName())
		).toString();
	}

	public String getKBArticleRowURL(KBArticle kbArticle)
		throws PortletException {

		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"groupId", kbArticle.getGroupId()
		).setParameter(
			"kbFolderId", kbArticle.getResourcePrimKey()
		).setParameter(
			"parentResourceClassNameId", kbArticle.getClassNameId()
		).setParameter(
			"parentResourcePrimKey", kbArticle.getResourcePrimKey()
		).buildString();
	}

	public String getKBFolderRowURL(long groupId, long kbFolderId)
		throws PortletException {

		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"groupId", groupId
		).setParameter(
			"kbFolderId", kbFolderId
		).buildString();
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public KBArticle getLatestArticle(KBArticle kbArticle) {
		KBArticle latestArticle =
			KBArticleLocalServiceUtil.fetchLatestKBArticle(
				kbArticle.getGroupId(), kbArticle.getResourcePrimKey());

		if (latestArticle != null) {
			return latestArticle;
		}

		return kbArticle;
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		breadcrumbEntries.add(_getSiteBreadcrumb());

		breadcrumbEntries.add(_getHomeBreadcrumb());

		KBFolder kbFolder = _getKBFolder();

		if (kbFolder == null) {
			return breadcrumbEntries;
		}

		KBFolder parentKBFolder = kbFolder.getParentKBFolder();

		PortletURL portletURL = PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"kbFolderId", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID
		).buildPortletURL();

		BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

		if (parentKBFolder == null) {
			folderBreadcrumbEntry.setTitle(
				LanguageUtil.get(_httpServletRequest, "home"));
			folderBreadcrumbEntry.setURL(portletURL.toString());
		}
		else {
			folderBreadcrumbEntry.setTitle(parentKBFolder.getName());

			portletURL.setParameter(
				"kbFolderId", String.valueOf(parentKBFolder.getKbFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());
		}

		breadcrumbEntries.add(folderBreadcrumbEntry);

		if (kbFolder.getKbFolderId() !=
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			folderBreadcrumbEntry = new BreadcrumbEntry();

			KBFolder unescapedFolder = kbFolder.toUnescapedModel();

			folderBreadcrumbEntry.setTitle(unescapedFolder.getName());

			portletURL.setParameter(
				"kbFolderId", String.valueOf(kbFolder.getKbFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		return breadcrumbEntries;
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(
				_portletURL,
				PortalUtil.getLiferayPortletResponse(_portletResponse))
		).setParameter(
			"displayStyle", getDisplayStyle()
		).buildPortletURL();
	}

	public SearchContainer<?> getSearchContainer() throws Exception {
		if (_articleSearchContainer != null) {
			return _articleSearchContainer;
		}

		PortletURL portletURL = PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"kbFolderId", _getKBFolderId()
		).buildPortletURL();

		SearchContainer<Object> articleAndFolderSearchContainer =
			new SearchContainer<>(_portletRequest, portletURL, null, null);

		articleAndFolderSearchContainer.setOrderByCol(_getOrderByCol());
		articleAndFolderSearchContainer.setOrderByType(_getOrderByType());

		if (isSearch()) {
			List<Long> kbFolderIds = new ArrayList<>(1);

			if (_getKBFolderId() !=
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				kbFolderIds.add(_getKBFolderId());
			}

			boolean orderByAsc = false;

			if (Objects.equals(_getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			Sort sort = null;

			if (Objects.equals(_getOrderByCol(), "id")) {
				sort = new Sort(
					Field.getSortableFieldName(KBField.KB_ARTICLE_ID),
					Sort.STRING_TYPE, !orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "modified-date")) {
				sort = new Sort(
					Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "relevance")) {
				sort = new Sort(null, Sort.SCORE_TYPE, false);
			}
			else if (Objects.equals(_getOrderByCol(), "title")) {
				sort = new Sort(Field.TITLE, Sort.STRING_TYPE, !orderByAsc);
			}

			Indexer<?> indexer = KBSearcher.getInstance();

			SearchContext searchContext = buildSearchContext(
				kbFolderIds, articleAndFolderSearchContainer.getStart(),
				articleAndFolderSearchContainer.getEnd(), sort);

			Hits hits = indexer.search(searchContext);

			List<Object> results = new ArrayList<>();

			Document[] documents = hits.getDocs();

			for (Document document : documents) {
				String className = document.get(Field.ENTRY_CLASS_NAME);
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				if (className.equals(KBArticle.class.getName())) {
					results.add(
						KBArticleLocalServiceUtil.fetchLatestKBArticle(
							classPK, WorkflowConstants.STATUS_APPROVED));
				}
				else if (className.equals(KBFolder.class.getName())) {
					results.add(KBFolderLocalServiceUtil.getKBFolder(classPK));
				}
			}

			articleAndFolderSearchContainer.setResultsAndTotal(
				() -> results, hits.getLength());
		}
		else {
			articleAndFolderSearchContainer.setResultsAndTotal(
				() -> {
					OrderByComparator<Object> kbObjectOrderByComparator = null;

					boolean orderByAsc = false;

					if (Objects.equals(_getOrderByType(), "asc")) {
						orderByAsc = true;
					}

					if (Objects.equals(_getOrderByCol(), "priority")) {
						kbObjectOrderByComparator =
							new KBObjectsPriorityComparator<>(orderByAsc);
					}
					else if (Objects.equals(
								_getOrderByCol(), "modified-date")) {

						kbObjectOrderByComparator =
							new KBObjectsModifiedDateComparator<>(orderByAsc);
					}
					else if (Objects.equals(_getOrderByCol(), "title")) {
						kbObjectOrderByComparator =
							new KBObjectsTitleComparator<>(orderByAsc);
					}
					else if (Objects.equals(_getOrderByCol(), "view-count")) {
						kbObjectOrderByComparator =
							new KBObjectsViewCountComparator<>(orderByAsc);
					}

					return KBFolderServiceUtil.getKBFoldersAndKBArticles(
						_getGroupId(), _getKBFolderId(),
						_infoItemItemSelectorCriterion.getStatus(),
						articleAndFolderSearchContainer.getStart(),
						articleAndFolderSearchContainer.getEnd(),
						kbObjectOrderByComparator);
				},
				KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(
					_getGroupId(), _getKBFolderId(),
					_infoItemItemSelectorCriterion.getStatus()));
		}

		_articleSearchContainer = articleAndFolderSearchContainer;

		return _articleSearchContainer;
	}

	public boolean isSearch() {
		return _search;
	}

	protected SearchContext buildSearchContext(
			List<Long> kbFolderIds, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);
		searchContext.setAttribute(Field.CONTENT, getKeywords());
		searchContext.setAttribute(Field.DESCRIPTION, getKeywords());
		searchContext.setAttribute(
			Field.STATUS, _infoItemItemSelectorCriterion.getStatus());
		searchContext.setAttribute(Field.TITLE, getKeywords());
		searchContext.setAttribute(KBField.KB_ARTICLE_ID, getKeywords());
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute("latest", Boolean.TRUE);
		searchContext.setAttribute(
			"params",
			LinkedHashMapBuilder.<String, Object>put(
				"expandoAttributes", getKeywords()
			).put(
				"keywords", getKeywords()
			).build());
		searchContext.setAttribute("showNonindexable", Boolean.TRUE);
		searchContext.setCompanyId(_themeDisplay.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setFolderIds(kbFolderIds);
		searchContext.setGroupIds(_getGroupIds());
		searchContext.setIncludeInternalAssetCategories(true);
		searchContext.setKeywords(getKeywords());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private long _getGroupId() {
		return ParamUtil.getLong(
			_portletRequest, "groupId", _themeDisplay.getScopeGroupId());
	}

	private long[] _getGroupIds() throws PortalException {
		return PortalUtil.getCurrentAndAncestorSiteGroupIds(
			_themeDisplay.getScopeGroupId());
	}

	private BreadcrumbEntry _getHomeBreadcrumb() throws Exception {
		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		Group group = GroupLocalServiceUtil.getGroup(_getGroupId());

		breadcrumbEntry.setTitle(
			group.getDescriptiveName(_themeDisplay.getLocale()));

		breadcrumbEntry.setURL(
			PortletURLBuilder.create(
				getPortletURL()
			).setParameter(
				"kbFolderId", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID
			).buildString());

		return breadcrumbEntry;
	}

	private KBFolder _getKBFolder() {
		if (_kbFolder != null) {
			return _kbFolder;
		}

		_kbFolder = KBFolderLocalServiceUtil.fetchKBFolder(
			ParamUtil.getLong(_httpServletRequest, "kbFolderId"));

		return _kbFolder;
	}

	private long _getKBFolderId() {
		if (_kbFolderId != null) {
			return _kbFolderId;
		}

		_kbFolderId = BeanParamUtil.getLong(
			_getKBFolder(), _httpServletRequest, "kbFolderId",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _kbFolderId;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		String defaultOrderByCol = "modified-date";

		if (isSearch()) {
			defaultOrderByCol = "relevance";
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
			"item-selector-order-by-col", defaultOrderByCol);

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		if (Objects.equals(_getOrderByCol(), "priority")) {
			return "desc";
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
			"item-selector-order-by-type", "asc");

		return _orderByType;
	}

	private BreadcrumbEntry _getSiteBreadcrumb() throws Exception {
		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites-and-libraries"));
		breadcrumbEntry.setURL(
			PortletURLBuilder.create(
				getPortletURL()
			).setParameter(
				"groupType", "site"
			).setParameter(
				"showGroupSelector", true
			).buildString());

		return breadcrumbEntry;
	}

	private SearchContainer<?> _articleSearchContainer;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemItemSelectorCriterion _infoItemItemSelectorCriterion;
	private final String _itemSelectedEventName;
	private KBFolder _kbFolder;
	private Long _kbFolderId;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;
	private final boolean _search;
	private final ThemeDisplay _themeDisplay;

}