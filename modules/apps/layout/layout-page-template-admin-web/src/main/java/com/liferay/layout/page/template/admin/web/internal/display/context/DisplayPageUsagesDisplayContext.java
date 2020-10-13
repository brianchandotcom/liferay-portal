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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryServiceUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.page.template.admin.web.internal.util.comparator.AssetDisplayPageEntryModifiedDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class DisplayPageUsagesDisplayContext {

	public DisplayPageUsagesDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_infoItemServiceTracker =
			(InfoItemServiceTracker)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_SERVICE_TRACKER);
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public long getClassNameId() {
		if (Validator.isNotNull(_classNameId)) {
			return _classNameId;
		}

		_classNameId = ParamUtil.getLong(_httpServletRequest, "classNameId");

		return _classNameId;
	}

	public long getClassTypeId() {
		if (Validator.isNotNull(_classTypeId)) {
			return _classTypeId;
		}

		_classTypeId = ParamUtil.getLong(_httpServletRequest, "classTypeId");

		return _classTypeId;
	}

	public long getLayoutPageTemplateEntryId() {
		if (Validator.isNotNull(_layoutPageTemplateEntryId)) {
			return _layoutPageTemplateEntryId;
		}

		_layoutPageTemplateEntryId = ParamUtil.getLong(
			_httpServletRequest, "layoutPageTemplateEntryId");

		return _layoutPageTemplateEntryId;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/layout_page_template/view_display_page_usages");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(getLayoutPageTemplateEntryId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<AssetDisplayPageEntry> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<AssetDisplayPageEntry> searchContainer =
			new SearchContainer<>(
				_renderRequest, getPortletURL(), null,
				"there-are-no-display-page-template-usages");

		searchContainer.setId(
			"assetDisplayPageEntries" + getLayoutPageTemplateEntryId());

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetDisplayPageEntry> orderByComparator =
			new AssetDisplayPageEntryModifiedDateComparator(orderByAsc);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		List<AssetDisplayPageEntry> assetDisplayPageEntries =
			AssetDisplayPageEntryServiceUtil.getAssetDisplayPageEntries(
				getClassNameId(), getClassTypeId(),
				getLayoutPageTemplateEntryId(), isDefaultTemplate(),
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

		searchContainer.setResults(assetDisplayPageEntries);

		int count =
			AssetDisplayPageEntryServiceUtil.getAssetDisplayPageEntriesCount(
				getClassNameId(), getClassTypeId(),
				getLayoutPageTemplateEntryId(), isDefaultTemplate());

		searchContainer.setTotal(count);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getTitle(
			AssetDisplayPageEntry assetDisplayPageEntry, Locale locale)
		throws PortalException {

		String className = assetDisplayPageEntry.getClassName();

		if (Objects.equals(className, FileEntry.class.getName())) {
			className = DLFileEntry.class.getName();
		}

		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetEntryServiceUtil.getEntry(
				className, assetDisplayPageEntry.getClassPK());

			return assetEntry.getTitle(locale);
		}
		catch (PortalException portalException) {
			if (!(portalException instanceof NoSuchEntryException)) {
				throw portalException;
			}
		}

		String title = StringPool.BLANK;

		InfoItemObjectProvider<?> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class,
				PortalUtil.getClassName(getClassNameId()));

		if (infoItemObjectProvider == null) {
			return title;
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				PortalUtil.getClassName(getClassNameId()));

		if (infoItemFieldValuesProvider == null) {
			return title;
		}

		Object infoItem = infoItemObjectProvider.getInfoItem(
			new ClassPKInfoItemIdentifier(assetDisplayPageEntry.getClassPK()));

		if (infoItem == null) {
			return title;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoItemFieldValue(
				infoItem, "title");

		if (infoFieldValue == null) {
			return title;
		}

		Object infoFieldValueValue = infoFieldValue.getValue(
			LocaleUtil.getMostRelevantLocale());

		if (infoFieldValueValue == null) {
			return title;
		}

		return String.valueOf(infoFieldValueValue);
	}

	public boolean isDefaultTemplate() {
		if (_defaultTemplate != null) {
			return _defaultTemplate;
		}

		_defaultTemplate = ParamUtil.getBoolean(
			_httpServletRequest, "defaultTemplate");

		return _defaultTemplate;
	}

	private Long _classNameId;
	private Long _classTypeId;
	private Boolean _defaultTemplate;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private Long _layoutPageTemplateEntryId;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<AssetDisplayPageEntry> _searchContainer;

}