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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.asset.util.comparator.AssetTagAssetCountComparator;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class AssetTagsDisplayContext {

	public AssetTagsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetTitle() {
		AssetTag tag = getTag();

		if (tag == null) {
			return LanguageUtil.get(_httpServletRequest, "new-tag");
		}

		return tag.getName();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public long getFullTagsCount(AssetTag tag) {
		int[] statuses = {
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_PENDING,
			WorkflowConstants.STATUS_SCHEDULED
		};

		Hits hits = AssetEntryLocalServiceUtil.search(
			tag.getCompanyId(), new long[] {_themeDisplay.getScopeGroupId()},
			_themeDisplay.getUserId(), null, 0, null, null, null, null,
			tag.getName(), true, statuses, false, 0, 1);

		return hits.getLength();
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords", null);

		return _keywords;
	}

	public List<String> getMergeTagNames() {
		if (_mergeTagNames != null) {
			return _mergeTagNames;
		}

		long[] mergeTagIds = StringUtil.split(
			ParamUtil.getString(_renderRequest, "mergeTagIds"), 0L);

		List<String> mergeTagNames = new ArrayList<>();

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

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
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

		_tagId = ParamUtil.getLong(_httpServletRequest, "tagId");

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
					new long[] {_themeDisplay.getScopeGroupId()}, keywords,
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

			long scopeGroupId = _themeDisplay.getScopeGroupId();

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

	public boolean isShowTagsActions() {
		if (_showTagsActions != null) {
			return _showTagsActions;
		}

		boolean showTagsActions = true;

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		Group group = _themeDisplay.getScopeGroup();

		if (stagingGroupHelper.isLocalLiveGroup(group) ||
			stagingGroupHelper.isRemoteLiveGroup(group)) {

			showTagsActions = false;
		}

		_showTagsActions = showTagsActions;

		return _showTagsActions;
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private List<String> _mergeTagNames;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showTagsActions;
	private AssetTag _tag;
	private Long _tagId;
	private SearchContainer _tagsSearchContainer;
	private final ThemeDisplay _themeDisplay;

}