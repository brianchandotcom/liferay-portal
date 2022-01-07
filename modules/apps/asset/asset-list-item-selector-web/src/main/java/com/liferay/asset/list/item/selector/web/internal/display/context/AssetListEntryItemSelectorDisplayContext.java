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

package com.liferay.asset.list.item.selector.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetListEntryItemSelectorDisplayContext {

	public AssetListEntryItemSelectorDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		Language language, PortletURL portletURL,
		InfoListItemSelectorCriterion infoListItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_language = language;
		_portletURL = portletURL;
		_infoListItemSelectorCriterion = infoListItemSelectorCriterion;
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries(PortletURL currentURL)
		throws PortalException, PortletException {

		return Arrays.asList(
			_getGroupSelectorBreadcrumbEntry(currentURL),
			_getCurrentGroupBreadcrumbEntry(currentURL));
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getPayload(AssetListEntry assetListEntry) {
		return JSONUtil.put(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(AssetListEntry.class))
		).put(
			"classPK", assetListEntry.getAssetListEntryId()
		).put(
			"itemSubtype", assetListEntry.getAssetEntrySubtype()
		).put(
			"itemType", assetListEntry.getAssetEntryType()
		).put(
			"title", assetListEntry.getTitle()
		).toString();
	}

	public String getReturnType() {
		ItemSelectorReturnType itemSelectorReturnType =
			new InfoListItemSelectorReturnType();

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		return itemSelectorReturnTypeClass.getName();
	}

	public SearchContainer<AssetListEntry> getSearchContainer()
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<AssetListEntry> searchContainer = new SearchContainer<>(
			portletRequest, _portletURL, null,
			_language.get(
				themeDisplay.getLocale(), "there-are-no-collections"));

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");
		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		OrderByComparator<AssetListEntry> orderByComparator =
			AssetListPortletUtil.getAssetListEntryOrderByComparator(
				orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		List<AssetListEntry> assetListEntries = null;
		int assetListEntriesCount = 0;

		List<String> itemTypes = _infoListItemSelectorCriterion.getItemTypes();

		if (ListUtil.isEmpty(itemTypes)) {
			if (Validator.isNotNull(keywords)) {
				assetListEntries =
					AssetListEntryServiceUtil.getAssetListEntries(
						themeDisplay.getScopeGroupId(), keywords,
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());
				assetListEntriesCount =
					AssetListEntryServiceUtil.getAssetListEntriesCount(
						themeDisplay.getScopeGroupId(), keywords);
			}
			else {
				assetListEntries =
					AssetListEntryServiceUtil.getAssetListEntries(
						themeDisplay.getScopeGroupId(),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());
				assetListEntriesCount =
					AssetListEntryServiceUtil.getAssetListEntriesCount(
						themeDisplay.getScopeGroupId());
			}
		}
		else if (Validator.isNull(
					_infoListItemSelectorCriterion.getItemSubtype())) {

			if (Validator.isNotNull(keywords)) {
				assetListEntries =
					AssetListEntryServiceUtil.getAssetListEntries(
						new long[] {themeDisplay.getScopeGroupId()}, keywords,
						itemTypes.toArray(new String[0]),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());
				assetListEntriesCount =
					AssetListEntryServiceUtil.getAssetListEntriesCount(
						new long[] {themeDisplay.getScopeGroupId()}, keywords,
						itemTypes.toArray(new String[0]));
			}
			else {
				assetListEntries =
					AssetListEntryServiceUtil.getAssetListEntries(
						new long[] {themeDisplay.getScopeGroupId()},
						itemTypes.toArray(new String[0]),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());
				assetListEntriesCount =
					AssetListEntryServiceUtil.getAssetListEntriesCount(
						new long[] {themeDisplay.getScopeGroupId()},
						itemTypes.toArray(new String[0]));
			}
		}
		else {
			if (Validator.isNotNull(keywords)) {
				assetListEntries =
					AssetListEntryServiceUtil.getAssetListEntries(
						new long[] {themeDisplay.getScopeGroupId()}, keywords,
						_infoListItemSelectorCriterion.getItemSubtype(),
						_infoListItemSelectorCriterion.getItemType(),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());
				assetListEntriesCount =
					AssetListEntryServiceUtil.getAssetListEntriesCount(
						new long[] {themeDisplay.getScopeGroupId()}, keywords,
						_infoListItemSelectorCriterion.getItemSubtype(),
						_infoListItemSelectorCriterion.getItemType());
			}
			else {
				assetListEntries =
					AssetListEntryServiceUtil.getAssetListEntries(
						new long[] {themeDisplay.getScopeGroupId()},
						_infoListItemSelectorCriterion.getItemSubtype(),
						_infoListItemSelectorCriterion.getItemType(),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());
				assetListEntriesCount =
					AssetListEntryServiceUtil.getAssetListEntriesCount(
						new long[] {themeDisplay.getScopeGroupId()},
						_infoListItemSelectorCriterion.getItemSubtype(),
						_infoListItemSelectorCriterion.getItemType());
			}
		}

		searchContainer.setResults(assetListEntries);
		searchContainer.setTotal(assetListEntriesCount);

		return searchContainer;
	}

	public String getSubtype(AssetListEntry assetListEntry) {
		if (Validator.isNull(assetListEntry.getAssetEntrySubtype())) {
			return StringPool.BLANK;
		}

		String subtypeLabel = _getAssetEntrySubtypeSubtypeLabel(assetListEntry);

		if (Validator.isNull(subtypeLabel)) {
			return StringPool.BLANK;
		}

		return subtypeLabel;
	}

	public String getTitle(AssetListEntry assetListEntry, Locale locale) {
		try {
			return assetListEntry.getUnambiguousTitle(locale);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	public String getType(AssetListEntry assetListEntry, Locale locale) {
		return ResourceActionsUtil.getModelResource(
			locale, assetListEntry.getAssetEntryType());
	}

	public boolean isDescriptiveDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
			return true;
		}

		return false;
	}

	public boolean isIconDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "icon")) {
			return true;
		}

		return false;
	}

	private String _getAssetEntrySubtypeSubtypeLabel(
		AssetListEntry assetListEntry) {

		long classTypeId = GetterUtil.getLong(
			assetListEntry.getAssetEntrySubtype(), -1);

		if (classTypeId < 0) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetListEntry.getAssetEntryType());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			ClassType classType = classTypeReader.getClassType(
				classTypeId, themeDisplay.getLocale());

			return classType.getName();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return StringPool.BLANK;
	}

	private BreadcrumbEntry _getCurrentGroupBreadcrumbEntry(
			PortletURL currentURL)
		throws PortalException {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		breadcrumbEntry.setTitle(
			scopeGroup.getDescriptiveName(_httpServletRequest.getLocale()));

		breadcrumbEntry.setURL(currentURL.toString());

		return breadcrumbEntry;
	}

	private BreadcrumbEntry _getGroupSelectorBreadcrumbEntry(
			PortletURL currentURL)
		throws PortletException {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites-and-libraries"));

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		breadcrumbEntry.setURL(
			PortletURLBuilder.create(
				PortletURLUtil.clone(
					currentURL,
					PortalUtil.getLiferayPortletResponse(portletResponse))
			).setParameter(
				"groupType", "site"
			).setParameter(
				"showGroupSelector", true
			).buildString());

		return breadcrumbEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryItemSelectorDisplayContext.class);

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final InfoListItemSelectorCriterion _infoListItemSelectorCriterion;
	private final String _itemSelectedEventName;
	private final Language _language;
	private final PortletURL _portletURL;

}