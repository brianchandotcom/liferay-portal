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

package com.liferay.asset.browser.web.internal.display.context;

import com.liferay.asset.browser.web.internal.configuration.AssetBrowserWebConfigurationValues;
import com.liferay.asset.browser.web.internal.constants.AssetBrowserPortletKeys;
import com.liferay.asset.browser.web.internal.search.AssetBrowserSearch;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetBrowserDisplayContext {

	public AssetBrowserDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_request = PortalUtil.getHttpServletRequest(renderRequest);
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getAddButtonLabel() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory.isSupportsClassTypes() &&
			(getSubtypeSelectionId() > 0)) {

			return assetRendererFactory.getTypeName(
				themeDisplay.getLocale(), getSubtypeSelectionId());
		}

		return assetRendererFactory.getTypeName(themeDisplay.getLocale());
	}

	public String getAddButtonURL() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = getGroupId();

		if (groupId == 0) {
			groupId = themeDisplay.getScopeGroupId();
		}

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(_renderRequest);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_renderResponse);

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		PortletURL addPortletURL = null;

		if (assetRendererFactory.isSupportsClassTypes() &&
			(getSubtypeSelectionId() > 0)) {

			addPortletURL = AssetUtil.getAddPortletURL(
				liferayPortletRequest, liferayPortletResponse, groupId,
				getTypeSelection(), getSubtypeSelectionId(), null, null,
				getPortletURL().toString());
		}
		else {
			addPortletURL = AssetUtil.getAddPortletURL(
				liferayPortletRequest, liferayPortletResponse, groupId,
				getTypeSelection(), 0, null, null, getPortletURL().toString());
		}

		if (addPortletURL == null) {
			return StringPool.BLANK;
		}

		addPortletURL.setParameter("groupId", String.valueOf(groupId));

		return HttpUtil.addParameter(
			addPortletURL.toString(), "refererPlid", themeDisplay.getPlid());
	}

	public AssetBrowserSearch getAssetBrowserSearch() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetBrowserSearch assetBrowserSearch = new AssetBrowserSearch(
			_renderRequest, getPortletURL());

		if (Validator.isNull(getKeywords())) {
			if (Validator.isNotNull(getAddButtonURL())) {
				assetBrowserSearch.setEmptyResultsMessageCssClass(
					"taglib-empty-result-message-header-has-plus-btn");
			}
		}
		else {
			assetBrowserSearch.setSearch(true);
		}

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		int total = getTotal();

		assetBrowserSearch.setTotal(total);

		if (AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE) {
			List<AssetEntry> assetEntries =
				AssetEntryLocalServiceUtil.getEntries(
					getFilterGroupIds(),
					new long[] {assetRendererFactory.getClassNameId()},
					getKeywords(), getKeywords(), getKeywords(), getKeywords(),
					getListable(), false, false, assetBrowserSearch.getStart(),
					assetBrowserSearch.getEnd(), "modifiedDate",
					StringPool.BLANK, getOrderByType(), StringPool.BLANK);

			assetBrowserSearch.setResults(assetEntries);
		}
		else {
			Sort sort = null;

			boolean orderByAsc = false;

			if (Objects.equals(getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			if (Objects.equals(getOrderByCol(), "modified-date")) {
				sort = new Sort(
					Field.MODIFIED_DATE, Sort.LONG_TYPE, orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "title")) {
				String sortFieldName = DocumentImpl.getSortableFieldName(
					"localized_title_".concat(themeDisplay.getLanguageId()));

				sort = new Sort(sortFieldName, Sort.STRING_TYPE, orderByAsc);
			}

			Hits hits = AssetEntryLocalServiceUtil.search(
				themeDisplay.getCompanyId(), getFilterGroupIds(),
				themeDisplay.getUserId(), assetRendererFactory.getClassName(),
				getSubtypeSelectionId(), getKeywords(), isShowNonindexable(),
				getStatuses(), assetBrowserSearch.getStart(),
				assetBrowserSearch.getEnd(), sort);

			List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(hits);

			assetBrowserSearch.setResults(assetEntries);
		}

		return assetBrowserSearch;
	}

	public AssetRendererFactory getAssetRendererFactory() {
		if (_assetRendererFactory != null) {
			return _assetRendererFactory;
		}

		_assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getTypeSelection());

		return _assetRendererFactory;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			AssetBrowserPortletKeys.ASSET_BROWSER, "display-style", "list");

		return _displayStyle;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_request, "eventName",
			_renderResponse.getNamespace() + "selectAsset");

		return _eventName;
	}

	public long[] getFilterGroupIds() {
		long[] filterGroupIds = getSelectedGroupIds();

		if (getGroupId() > 0) {
			filterGroupIds = new long[] {getGroupId()};
		}

		return filterGroupIds;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_request, "groupId");

		return _groupId;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public Boolean getListable() {
		Boolean listable = null;

		String listableValue = ParamUtil.getString(_request, "listable", null);

		if (Validator.isNotNull(listableValue)) {
			listable = ParamUtil.getBoolean(_request, "listable", true);
		}

		return listable;
	}

	public List<ManagementBarFilterItem> getManagementBarFilterItem()
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>();

		long[] selectedGroupIds = ArrayUtil.append(
			new long[] {0}, getSelectedGroupIds());

		for (long curGroupId : selectedGroupIds) {
			Group curGroup = GroupLocalServiceUtil.fetchGroup(curGroupId);

			if ((curGroup == null) && (curGroupId > 0)) {
				continue;
			}

			boolean active = false;

			if (getGroupId() == curGroupId) {
				active = true;
			}

			String label = StringPool.BLANK;

			if (curGroup != null) {
				label = HtmlUtil.escape(
					curGroup.getDescriptiveName(themeDisplay.getLocale()));
			}
			else {
				label = LanguageUtil.get(_request, "all");
			}

			PortletURL groupURL = getPortletURL();

			groupURL.setParameter("groupId", String.valueOf(curGroupId));

			ManagementBarFilterItem managementBarFilterItem =
				new ManagementBarFilterItem(active, label, groupURL.toString());

			managementBarFilterItems.add(managementBarFilterItem);
		}

		return managementBarFilterItems;
	}

	public String getManagementBarFilterLabel() throws PortalException {
		if (getGroupId() <= 0) {
			return LanguageUtil.get(_request, "all");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = GroupLocalServiceUtil.fetchGroup(getGroupId());

		return group.getDescriptiveName(themeDisplay.getLocale());
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public String[] getOrderColumns() {
		if (AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE) {
			return new String[] {"modified-date"};
		}

		return new String[] {"modified-date", "title"};
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("groupId", String.valueOf(getGroupId()));
		portletURL.setParameter(
			"selectedGroupIds", StringUtil.merge(getSelectedGroupIds()));
		portletURL.setParameter(
			"refererAssetEntryId", String.valueOf(getRefererAssetEntryId()));
		portletURL.setParameter("typeSelection", getTypeSelection());
		portletURL.setParameter(
			"subtypeSelectionId", String.valueOf(getSubtypeSelectionId()));
		portletURL.setParameter(
			"showNonindexable", String.valueOf(isShowNonindexable()));
		portletURL.setParameter(
			"showScheduled", String.valueOf(isShowScheduled()));

		if (getListable() != null) {
			portletURL.setParameter("listable", String.valueOf(getListable()));
		}

		portletURL.setParameter("eventName", getEventName());

		return portletURL;
	}

	public long getRefererAssetEntryId() {
		if (_refererAssetEntryId != null) {
			return _refererAssetEntryId;
		}

		_refererAssetEntryId = ParamUtil.getLong(
			_request, "refererAssetEntryId");

		return _refererAssetEntryId;
	}

	public long[] getSelectedGroupIds() {
		long[] selectedGroupIds = StringUtil.split(
			ParamUtil.getString(_request, "selectedGroupIds"), 0L);

		return selectedGroupIds;
	}

	public int[] getStatuses() {
		int[] statuses = {WorkflowConstants.STATUS_APPROVED};

		if (isShowScheduled()) {
			statuses = new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_SCHEDULED
			};
		}

		return statuses;
	}

	public long getSubtypeSelectionId() {
		if (_subtypeSelectionId != null) {
			return _subtypeSelectionId;
		}

		_subtypeSelectionId = ParamUtil.getLong(_request, "subtypeSelectionId");

		return _subtypeSelectionId;
	}

	public int getTotal() {
		return getTotal(getFilterGroupIds());
	}

	public int getTotal(long[] groupIds) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		int total = 0;

		if (AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE) {
			total = AssetEntryLocalServiceUtil.getEntriesCount(
				groupIds, new long[] {assetRendererFactory.getClassNameId()},
				getKeywords(), getKeywords(), getKeywords(), getKeywords(),
				getListable(), false, false);
		}
		else {
			total = (int)AssetEntryLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), groupIds, themeDisplay.getUserId(),
				assetRendererFactory.getClassName(), getSubtypeSelectionId(),
				getKeywords(), isShowNonindexable(), getStatuses());
		}

		return total;
	}

	public String getTypeSelection() {
		if (_typeSelection != null) {
			return _typeSelection;
		}

		_typeSelection = ParamUtil.getString(_request, "typeSelection");

		return _typeSelection;
	}

	public boolean isDisabledManagementBar() {
		if (getTotal(getSelectedGroupIds()) > 0) {
			return false;
		}

		if (Validator.isNotNull(getKeywords())) {
			return false;
		}

		return true;
	}

	public boolean isShowNonindexable() {
		if (_showNonindexable != null) {
			return _showNonindexable;
		}

		_showNonindexable = ParamUtil.getBoolean(_request, "showNonindexable");

		return _showNonindexable;
	}

	public boolean isShowScheduled() {
		if (_showScheduled != null) {
			return _showScheduled;
		}

		_showScheduled = ParamUtil.getBoolean(_request, "showScheduled");

		return _showScheduled;
	}

	private AssetRendererFactory _assetRendererFactory;
	private String _displayStyle;
	private String _eventName;
	private Long _groupId;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private Long _refererAssetEntryId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private Boolean _showNonindexable;
	private Boolean _showScheduled;
	private Long _subtypeSelectionId;
	private String _typeSelection;

}