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

package com.liferay.asset.tags.item.selector.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.asset.tags.item.selector.criterion.AssetTagsItemSelectorCriterion;
import com.liferay.asset.tags.item.selector.web.internal.constants.AssetTagsSelectorPortletKeys;
import com.liferay.asset.tags.item.selector.web.internal.search.EntriesChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsDisplayContext {

	public AssetTagsDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		RenderRequest renderRequest, RenderResponse renderResponse,
		AssetTagsItemSelectorCriterion assetTagsItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_assetTagsItemSelectorCriterion = assetTagsItemSelectorCriterion;

		_rowChecker = false;
	}

	public SearchContainer<AssetTag> getTagSearchContainer() {
		if (_tagsSearchContainer != null) {
			return _tagsSearchContainer;
		}

		SearchContainer<AssetTag> tagsSearchContainer = new SearchContainer(
			_renderRequest, _portletURL, null, "there-are-no-tags");

		tagsSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = _getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		tagsSearchContainer.setOrderByComparator(
			new AssetTagNameComparator(orderByAsc));
		tagsSearchContainer.setOrderByType(orderByType);
		tagsSearchContainer.setResultsAndTotal(
			() -> AssetTagServiceUtil.getTags(
				_getGroupIds(), _getKeywords(), tagsSearchContainer.getStart(),
				tagsSearchContainer.getEnd(),
				tagsSearchContainer.getOrderByComparator()),
			AssetTagServiceUtil.getTagsCount(_getGroupIds(), _getKeywords()));

		if (_rowChecker) {
			tagsSearchContainer.setRowChecker(
				new EntriesChecker(_renderRequest, _renderResponse));
		}

		_tagsSearchContainer = tagsSearchContainer;

		return _tagsSearchContainer;
	}

	private long[] _getGroupIds() {
		if (ArrayUtil.isNotEmpty(_groupIds)) {
			return _groupIds;
		}

		if (_assetTagsItemSelectorCriterion.isMultiSelection()) {
			return _assetTagsItemSelectorCriterion.getGroupIds();
		}

		long[] groupIds = StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "groupIds"), 0L);

		if (ArrayUtil.isEmpty(groupIds)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			groupIds = new long[] {themeDisplay.getScopeGroupId()};
		}

		for (long groupId : groupIds) {
			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if ((group == null) || !group.isLayout() ||
				ArrayUtil.contains(groupIds, group.getParentGroupId())) {

				continue;
			}

			try {
				groupIds = ArrayUtil.append(
					groupIds,
					PortalUtil.getCurrentAndAncestorSiteGroupIds(
						group.getParentGroupId()));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		_groupIds = groupIds;

		return _groupIds;
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords", null);

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			AssetTagsSelectorPortletKeys.ASSET_TAGS_SELECTOR, "name");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			AssetTagsSelectorPortletKeys.ASSET_TAGS_SELECTOR, "asc");

		return _orderByType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagsDisplayContext.class);

	private final AssetTagsItemSelectorCriterion
		_assetTagsItemSelectorCriterion;
	private long[] _groupIds;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final boolean _rowChecker;
	private SearchContainer<AssetTag> _tagsSearchContainer;

}