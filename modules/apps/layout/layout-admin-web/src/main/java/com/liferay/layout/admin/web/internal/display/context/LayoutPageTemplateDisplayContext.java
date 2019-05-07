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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateCollectionPermission;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateDisplayContext {

	public LayoutPageTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/layout/edit_layout_page_template_collection",
							"redirect", _themeDisplay.getURLCurrent());
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "new"));
					});
			}
		};
	}

	public List<DropdownItem> getCollectionsDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (LayoutPageTemplateCollectionPermission.contains(
						_themeDisplay.getPermissionChecker(),
						getLayoutPageTemplateCollectionId(),
						ActionKeys.DELETE)) {

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteCollections");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						});
				}
			}
		};
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public LayoutPageTemplateCollection getLayoutPageTemplateCollection()
		throws PortalException {

		if (_layoutPageTemplateCollection != null) {
			return _layoutPageTemplateCollection;
		}

		_layoutPageTemplateCollection =
			LayoutPageTemplateCollectionServiceUtil.
				fetchLayoutPageTemplateCollection(
					getLayoutPageTemplateCollectionId());

		return _layoutPageTemplateCollection;
	}

	public long getLayoutPageTemplateCollectionId() {
		if (Validator.isNotNull(_layoutPageTemplateCollectionId)) {
			return _layoutPageTemplateCollectionId;
		}

		long defaultLayoutPageTemplateCollectionId = 0;

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			getLayoutPageTemplateCollections();

		if (ListUtil.isNotEmpty(layoutPageTemplateCollections)) {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				layoutPageTemplateCollections.get(0);

			defaultLayoutPageTemplateCollectionId =
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
		}

		_layoutPageTemplateCollectionId = ParamUtil.getLong(
			_httpServletRequest, "layoutPageTemplateCollectionId",
			defaultLayoutPageTemplateCollectionId);

		return _layoutPageTemplateCollectionId;
	}

	public List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections() {

		if (_layoutPageTemplateCollections != null) {
			return _layoutPageTemplateCollections;
		}

		_layoutPageTemplateCollections =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollections(
					_themeDisplay.getScopeGroupId());

		return _layoutPageTemplateCollections;
	}

	public SearchContainer getLayoutPageTemplateEntriesSearchContainer() {
		if (_layoutPageTemplateEntriesSearchContainer != null) {
			return _layoutPageTemplateEntriesSearchContainer;
		}

		SearchContainer layoutPageTemplateEntriesSearchContainer =
			new SearchContainer(
				_renderRequest, getPortletURL(), null,
				"there-are-no-page-templates");

		layoutPageTemplateEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		layoutPageTemplateEntriesSearchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<LayoutPageTemplateEntry> orderByComparator =
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateEntryOrderByComparator(
					getOrderByCol(), getOrderByType());

		layoutPageTemplateEntriesSearchContainer.setOrderByComparator(
			orderByComparator);

		layoutPageTemplateEntriesSearchContainer.setOrderByType(
			getOrderByType());

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries = null;
		int layoutPageTemplateEntriesCount = 0;

		if (isSearch()) {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					_themeDisplay.getScopeGroupId(),
					getLayoutPageTemplateCollectionId(), getKeywords(),
					layoutPageTemplateEntriesSearchContainer.getStart(),
					layoutPageTemplateEntriesSearchContainer.getEnd(),
					orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						getLayoutPageTemplateCollectionId(), getKeywords());
		}
		else {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					_themeDisplay.getScopeGroupId(),
					getLayoutPageTemplateCollectionId(),
					layoutPageTemplateEntriesSearchContainer.getStart(),
					layoutPageTemplateEntriesSearchContainer.getEnd(),
					orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						getLayoutPageTemplateCollectionId());
		}

		layoutPageTemplateEntriesSearchContainer.setResults(
			layoutPageTemplateEntries);
		layoutPageTemplateEntriesSearchContainer.setTotal(
			layoutPageTemplateEntriesCount);

		_layoutPageTemplateEntriesSearchContainer =
			layoutPageTemplateEntriesSearchContainer;

		return _layoutPageTemplateEntriesSearchContainer;
	}

	public LayoutPageTemplateEntry getLayoutPageTemplateEntry()
		throws PortalException {

		if (_layoutPageTemplateEntry != null) {
			return _layoutPageTemplateEntry;
		}

		_layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(
				getLayoutPageTemplateEntryId());

		return _layoutPageTemplateEntry;
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
			_httpServletRequest, "orderByCol", "create-date");

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

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("tabs1", "page-templates");
		portletURL.setParameter("redirect", _themeDisplay.getURLCurrent());

		long layoutPageTemplateCollectionId =
			getLayoutPageTemplateCollectionId();

		if (layoutPageTemplateCollectionId > 0) {
			portletURL.setParameter(
				"layoutPageTemplateCollectionId",
				String.valueOf(layoutPageTemplateCollectionId));
		}

		String keywords = getKeywords();

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

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton(String actionId) {
		if (LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getSiteGroupId(), actionId)) {

			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private LayoutPageTemplateCollection _layoutPageTemplateCollection;
	private Long _layoutPageTemplateCollectionId;
	private List<LayoutPageTemplateCollection> _layoutPageTemplateCollections;
	private SearchContainer _layoutPageTemplateEntriesSearchContainer;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private Long _layoutPageTemplateEntryId;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}