/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.item.selector.web.internal.display.context;

import com.liferay.asset.display.page.item.selector.criterion.AssetDisplayPageSelectorCriterion;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionLayoutPageTemplateEntryNameComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryCreateDateComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryNameComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yurena Cabrera
 */
public class AssetDisplayPagesItemSelectorCustomViewDisplayContext {

	public AssetDisplayPagesItemSelectorCustomViewDisplayContext(
		HttpServletRequest httpServletRequest,
		AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion,
		PortletURL portletURL, boolean search) {

		_httpServletRequest = httpServletRequest;
		_assetDisplayPageSelectorCriterion = assetDisplayPageSelectorCriterion;
		_portletURL = portletURL;
		_search = search;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<?> getAssetDisplayPageSearchContainer() {
		if (_assetDisplayPageSearchContainer != null) {
			return _assetDisplayPageSearchContainer;
		}

		if (_search) {
			SearchContainer<LayoutPageTemplateEntry>
				assetDisplayPageSearchContainer = new SearchContainer<>(
					_portletRequest, _portletURL, null,
					"there-are-no-display-page-templates");

			assetDisplayPageSearchContainer.setOrderByCol(_getOrderByCol());
			assetDisplayPageSearchContainer.setOrderByComparator(
				_getLayoutPageTemplateEntryOrderByComparatorWithKeywords(
					_getOrderByCol(), getOrderByType()));
			assetDisplayPageSearchContainer.setOrderByType(getOrderByType());
			assetDisplayPageSearchContainer.setResultsAndTotal(
				() ->
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntries(
							_getGroupId(),
							_assetDisplayPageSelectorCriterion.getClassNameId(),
							_assetDisplayPageSelectorCriterion.getClassTypeId(),
							_getKeywords(),
							LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
							WorkflowConstants.STATUS_APPROVED,
							assetDisplayPageSearchContainer.getStart(),
							assetDisplayPageSearchContainer.getEnd(),
							assetDisplayPageSearchContainer.
								getOrderByComparator()),
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_getGroupId(),
						_assetDisplayPageSelectorCriterion.getClassNameId(),
						_assetDisplayPageSelectorCriterion.getClassTypeId(),
						_getKeywords(),
						LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
						WorkflowConstants.STATUS_APPROVED));

			_assetDisplayPageSearchContainer = assetDisplayPageSearchContainer;

			return _assetDisplayPageSearchContainer;
		}

		SearchContainer<Object> assetDisplayPageSearchContainer =
			new SearchContainer<>(
				_portletRequest, _portletURL, null,
				"there-are-no-display-page-templates");

		assetDisplayPageSearchContainer.setOrderByCol(_getOrderByCol());
		assetDisplayPageSearchContainer.setOrderByComparator(
			_getLayoutPageTemplateEntryOrderByComparator(
				_getOrderByCol(), getOrderByType()));
		assetDisplayPageSearchContainer.setOrderByType(getOrderByType());

		assetDisplayPageSearchContainer.setResultsAndTotal(
			() ->
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageCollectionsAndLayoutPageTemplateEntries(
						_themeDisplay.getScopeGroupId(),
						ParamUtil.getLong(
							_httpServletRequest,
							"layoutPageTemplateCollectionId",
							LayoutPageTemplateConstants.
								PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT),
						LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
						assetDisplayPageSearchContainer.getStart(),
						assetDisplayPageSearchContainer.getEnd(),
						assetDisplayPageSearchContainer.getOrderByComparator()),
			LayoutPageTemplateEntryServiceUtil.
				getLayoutPageCollectionsAndLayoutPageTemplateEntriesCount(
					_getGroupId(),
					ParamUtil.getLong(
						_httpServletRequest, "layoutPageTemplateCollectionId",
						LayoutPageTemplateConstants.
							PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT),
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE));

		_assetDisplayPageSearchContainer = assetDisplayPageSearchContainer;

		return _assetDisplayPageSearchContainer;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			_httpServletRequest, "groupId", _themeDisplay.getScopeGroupId());

		return _groupId;
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private OrderByComparator<Object>
		_getLayoutPageTemplateEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Object> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator(
					orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator =
				new LayoutPageTemplateCollectionLayoutPageTemplateEntryNameComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

	private OrderByComparator<LayoutPageTemplateEntry>
		_getLayoutPageTemplateEntryOrderByComparatorWithKeywords(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutPageTemplateEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new LayoutPageTemplateEntryCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new LayoutPageTemplateEntryNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private SearchContainer<?> _assetDisplayPageSearchContainer;
	private final AssetDisplayPageSelectorCriterion
		_assetDisplayPageSelectorCriterion;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletRequest _portletRequest;
	private final PortletURL _portletURL;
	private final boolean _search;
	private final ThemeDisplay _themeDisplay;

}