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

package com.liferay.asset.tags.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.asset.tags.constants.AssetTagsAdminPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.asset.service.permission.AssetTagsPermission;
import com.liferay.portlet.asset.util.comparator.AssetTagAssetCountComparator;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class AssetTagsDisplayContext {

	public AssetTagsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "mergeTags");
						dropdownItem.setIcon("merge");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "merge"));
						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteTags");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAssetTitle() {
		AssetTag tag = getTag();

		if (tag == null) {
			return LanguageUtil.get(_request, "new-tag");
		}

		return tag.getName();
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _renderResponse.createRenderURL();

		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "mvcPath",
							"/edit_tag.jsp");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "add-tag"));
					});
			}
		};
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN, "display-style",
			"list");

		return _displayStyle;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public long getFullTagsCount(AssetTag tag) {
		int[] statuses = {
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_PENDING,
			WorkflowConstants.STATUS_SCHEDULED
		};

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return AssetEntryLocalServiceUtil.searchCount(
			tag.getCompanyId(), new long[] {themeDisplay.getScopeGroupId()},
			themeDisplay.getUserId(), null, 0, null, null, null, null,
			tag.getName(), true, true, statuses, false);
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords", null);

		return _keywords;
	}

	public List<String> getMergeTagNames() {
		if (_mergeTagNames != null) {
			return _mergeTagNames;
		}

		long[] mergeTagIds = StringUtil.split(
			ParamUtil.getString(_renderRequest, "mergeTagIds"), 0L);

		List<String> mergeTagNames = new ArrayList();

		for (long mergeTagId : mergeTagIds) {
			AssetTag tag = AssetTagLocalServiceUtil.fetchAssetTag(mergeTagId);

			if (tag == null) {
				continue;
			}

			mergeTagNames.add(tag.getName());
		}

		_mergeTagNames = mergeTagNames;

		return _mergeTagNames;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public String getSearchActionURL() {
		PortletURL searchTagURL = _renderResponse.createRenderURL();

		searchTagURL.setParameter("orderByCol", getOrderByCol());
		searchTagURL.setParameter("orderByType", getOrderByType());

		return searchTagURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = _renderResponse.createRenderURL();

		sortingURL.setParameter("keywords", getKeywords());
		sortingURL.setParameter("orderByCol", getOrderByCol());
		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public AssetTag getTag() {
		if (_tag != null) {
			return _tag;
		}

		long tagId = getTagId();

		AssetTag tag = null;

		if (tagId > 0) {
			tag = AssetTagLocalServiceUtil.fetchAssetTag(tagId);
		}

		_tag = tag;

		return _tag;
	}

	public Long getTagId() {
		if (_tagId != null) {
			return _tagId;
		}

		_tagId = ParamUtil.getLong(_request, "tagId");

		return _tagId;
	}

	public SearchContainer getTagsSearchContainer() throws PortalException {
		if (_tagsSearchContainer != null) {
			return _tagsSearchContainer;
		}

		SearchContainer tagsSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-tags");

		tagsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			Sort sort = null;

			String orderByCol = getOrderByCol();

			if (orderByCol.equals("name")) {
				sort = SortFactoryUtil.getSort(
					AssetTag.class, Sort.STRING_TYPE, Field.NAME,
					getOrderByType());
			}
			else if (orderByCol.equals("usages")) {
				sort = SortFactoryUtil.getSort(
					AssetTag.class, Sort.INT_TYPE, "assetCount_Number",
					getOrderByType());
			}

			BaseModelSearchResult<AssetTag> baseModelSearchResult =
				AssetTagLocalServiceUtil.searchTags(
					new long[] {themeDisplay.getScopeGroupId()}, keywords,
					tagsSearchContainer.getStart(),
					tagsSearchContainer.getEnd(), sort);

			tagsSearchContainer.setResults(
				baseModelSearchResult.getBaseModels());
			tagsSearchContainer.setTotal(baseModelSearchResult.getLength());
		}
		else {
			String orderByCol = getOrderByCol();

			tagsSearchContainer.setOrderByCol(orderByCol);

			OrderByComparator<AssetTag> orderByComparator = null;

			boolean orderByAsc = false;

			String orderByType = getOrderByType();

			if (orderByType.equals("asc")) {
				orderByAsc = true;
			}

			if (orderByCol.equals("name")) {
				orderByComparator = new AssetTagNameComparator(orderByAsc);
			}
			else if (orderByCol.equals("usages")) {
				orderByComparator = new AssetTagAssetCountComparator(
					orderByAsc);
			}

			tagsSearchContainer.setOrderByComparator(orderByComparator);

			tagsSearchContainer.setOrderByType(orderByType);

			long scopeGroupId = themeDisplay.getScopeGroupId();

			int tagsCount = AssetTagServiceUtil.getTagsCount(
				scopeGroupId, keywords);

			tagsSearchContainer.setTotal(tagsCount);

			List<AssetTag> tags = AssetTagServiceUtil.getTags(
				scopeGroupId, StringPool.BLANK, tagsSearchContainer.getStart(),
				tagsSearchContainer.getEnd(),
				tagsSearchContainer.getOrderByComparator());

			tagsSearchContainer.setResults(tags);
		}

		_tagsSearchContainer = tagsSearchContainer;

		return _tagsSearchContainer;
	}

	public int getTotalItems() throws PortalException {
		SearchContainer tagsSearchContainer = getTagsSearchContainer();

		return tagsSearchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(_request));

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isShowAddButton() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (AssetTagsPermission.contains(
				themeDisplay.getPermissionChecker(),
				AssetTagsPermission.RESOURCE_NAME,
				AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN,
				themeDisplay.getSiteGroupId(), ActionKeys.MANAGE_TAG)) {

			return isShowTagsActionMenu();
		}

		return false;
	}

	public boolean isShowSearch() throws PortalException {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		SearchContainer tagsSearchContainer = getTagsSearchContainer();

		if (tagsSearchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean isShowTagsActionMenu() {
		if (_showTagsActionMenu != null) {
			return _showTagsActionMenu;
		}

		boolean showTagsActionMenu = true;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.isStaged() && !group.isStagingGroup() &&
			!group.isStagedRemotely()) {

			showTagsActionMenu = false;
		}

		_showTagsActionMenu = showTagsActionMenu;

		return _showTagsActionMenu;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(_renderResponse.createRenderURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "keywords",
							getKeywords(), "orderByCol", "name", "orderByType",
							getOrderByType());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "name"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "keywords",
							getKeywords(), "orderByCol", "usages",
							"orderByType", getOrderByType());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "usages"));
					});
			}
		};
	}

	private String _displayStyle;
	private String _keywords;
	private List<String> _mergeTagNames;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private Boolean _showTagsActionMenu;
	private AssetTag _tag;
	private Long _tagId;
	private SearchContainer _tagsSearchContainer;

}