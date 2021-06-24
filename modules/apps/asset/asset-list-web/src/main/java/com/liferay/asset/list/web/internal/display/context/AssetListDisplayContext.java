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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.constants.AssetListActionKeys;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.asset.list.web.internal.security.permission.resource.AssetListPermission;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetListDisplayContext {

	public AssetListDisplayContext(
		AssetRendererFactoryClassProvider assetRendererFactoryClassProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_assetRendererFactoryClassProvider = assetRendererFactoryClassProvider;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getAddAssetListEntryDropdownItems() {
		return DropdownItemListBuilder.add(
			_getAddAssetListEntryDropdownItemUnsafeConsumer(
				AssetListEntryTypeConstants.TYPE_MANUAL_LABEL,
				"manual-collection", AssetListEntryTypeConstants.TYPE_MANUAL)
		).add(
			_getAddAssetListEntryDropdownItemUnsafeConsumer(
				AssetListEntryTypeConstants.TYPE_DYNAMIC_LABEL,
				"dynamic-collection", AssetListEntryTypeConstants.TYPE_DYNAMIC)
		).build();
	}

	public int getAssetListEntriesCount() {
		if (_assetListEntriesCount != null) {
			return _assetListEntriesCount;
		}

		_assetListEntriesCount =
			AssetListEntryServiceUtil.getAssetListEntriesCount(
				_themeDisplay.getScopeGroupId());

		return _assetListEntriesCount;
	}

	public SearchContainer<AssetListEntry>
		getAssetListEntriesSearchContainer() {

		if (_assetListEntriesSearchContainer != null) {
			return _assetListEntriesSearchContainer;
		}

		SearchContainer<AssetListEntry> assetListEntriesSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-collections");

		assetListEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<AssetListEntry> orderByComparator =
			AssetListPortletUtil.getAssetListEntryOrderByComparator(
				_getOrderByCol(), getOrderByType());

		assetListEntriesSearchContainer.setOrderByCol(_getOrderByCol());
		assetListEntriesSearchContainer.setOrderByComparator(orderByComparator);
		assetListEntriesSearchContainer.setOrderByType(getOrderByType());

		List<AssetListEntry> assetListEntries = null;

		int assetListEntriesCount = 0;

		if (_isSearch()) {
			assetListEntries = AssetListEntryServiceUtil.getAssetListEntries(
				_themeDisplay.getScopeGroupId(), _getKeywords(),
				assetListEntriesSearchContainer.getStart(),
				assetListEntriesSearchContainer.getEnd(), orderByComparator);

			assetListEntriesCount =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					_themeDisplay.getScopeGroupId(), _getKeywords());
		}
		else {
			assetListEntries = AssetListEntryServiceUtil.getAssetListEntries(
				_themeDisplay.getScopeGroupId(),
				assetListEntriesSearchContainer.getStart(),
				assetListEntriesSearchContainer.getEnd(), orderByComparator);

			assetListEntriesCount = getAssetListEntriesCount();
		}

		assetListEntriesSearchContainer.setResults(assetListEntries);

		assetListEntriesSearchContainer.setTotal(assetListEntriesCount);

		_assetListEntriesSearchContainer = assetListEntriesSearchContainer;

		return _assetListEntriesSearchContainer;
	}

	public AssetListEntry getAssetListEntry() {
		if (_assetListEntry != null) {
			return _assetListEntry;
		}

		_assetListEntry = AssetListEntryLocalServiceUtil.fetchAssetListEntry(
			getAssetListEntryId());

		return _assetListEntry;
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(
			_httpServletRequest, "assetListEntryId");

		return _assetListEntryId;
	}

	public String getAssetListEntryTitle() {
		AssetListEntry assetListEntry = getAssetListEntry();

		if (assetListEntry != null) {
			return assetListEntry.getTitle();
		}

		String title = StringPool.BLANK;

		if (getAssetListEntryType() ==
				AssetListEntryTypeConstants.TYPE_DYNAMIC) {

			title = "new-dynamic-collection";
		}
		else if (getAssetListEntryType() ==
					AssetListEntryTypeConstants.TYPE_MANUAL) {

			title = "new-manual-collection";
		}

		return LanguageUtil.get(_httpServletRequest, title);
	}

	public int getAssetListEntryType() {
		if (_assetListEntryType != null) {
			return _assetListEntryType;
		}

		AssetListEntry assetListEntry = getAssetListEntry();

		int assetListEntryType = ParamUtil.getInteger(
			_httpServletRequest, "assetListEntryType");

		if (assetListEntry != null) {
			assetListEntryType = assetListEntry.getType();
		}

		_assetListEntryType = assetListEntryType;

		return _assetListEntryType;
	}

	public String getClassName(AssetRendererFactory<?> assetRendererFactory) {
		Class<? extends AssetRendererFactory> clazz =
			_assetRendererFactoryClassProvider.getClass(assetRendererFactory);

		String className = clazz.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		return className.substring(pos + 1);
	}

	public ClassType getClassType(
		ClassTypeReader classTypeReader, long classTypeId) {

		try {
			return classTypeReader.getClassType(
				classTypeId, _themeDisplay.getLocale());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get class type", portalException);
			}
		}

		return null;
	}

	public String getEmptyResultMessageDescription() {
		if (isShowAddAssetListEntryAction()) {
			return LanguageUtil.get(
				_httpServletRequest,
				"fortunately-it-is-very-easy-to-add-new-ones");
		}

		return StringPool.BLANK;
	}

	public List<NavigationItem> getNavigationItems(String currentItem) {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(currentItem.equals("collections"));
				navigationItem.setHref(_renderResponse.createRenderURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "collections"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					currentItem.equals("collection-providers"));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcPath",
					"/view_info_collection_providers.jsp");
				navigationItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "collection-providers"));
			}
		).build();
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				AssetListPortletKeys.ASSET_LIST, "order-by-col", "create-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					AssetListPortletKeys.ASSET_LIST, "order-by-col",
					_orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				AssetListPortletKeys.ASSET_LIST, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					AssetListPortletKeys.ASSET_LIST, "order-by-type",
					_orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public long getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(
			_httpServletRequest, "segmentsEntryId",
			SegmentsEntryConstants.ID_DEFAULT);

		return _segmentsEntryId;
	}

	public boolean isShowAddAssetListEntryAction() {
		Group group = _themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isLiveGroup(group) &&
			stagingGroupHelper.isStagedPortlet(
				group, AssetListPortletKeys.ASSET_LIST)) {

			return false;
		}

		return AssetListPermission.contains(
			_themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroupId(),
			AssetListActionKeys.ADD_ASSET_LIST_ENTRY);
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddAssetListEntryDropdownItemUnsafeConsumer(
			String title, String label, int type) {

		return dropdownItem -> {
			dropdownItem.putData("action", "addAssetListEntry");
			dropdownItem.putData(
				"addAssetListEntryURL", _getAddAssetListEntryURL(type));
			dropdownItem.putData("title", _getAddAssetListTitle(title));
			dropdownItem.setLabel(LanguageUtil.get(_httpServletRequest, label));
		};
	}

	private String _getAddAssetListEntryURL(int type) {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/asset_list/add_asset_list_entry"
		).setParameter(
			"type", type
		).buildString();
	}

	private String _getAddAssetListTitle(String title) {
		return LanguageUtil.format(
			_httpServletRequest, "add-x-collection", title, true);
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListDisplayContext.class);

	private Integer _assetListEntriesCount;
	private SearchContainer<AssetListEntry> _assetListEntriesSearchContainer;
	private AssetListEntry _assetListEntry;
	private Long _assetListEntryId;
	private Integer _assetListEntryType;
	private final AssetRendererFactoryClassProvider
		_assetRendererFactoryClassProvider;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Long _segmentsEntryId;
	private final ThemeDisplay _themeDisplay;

}