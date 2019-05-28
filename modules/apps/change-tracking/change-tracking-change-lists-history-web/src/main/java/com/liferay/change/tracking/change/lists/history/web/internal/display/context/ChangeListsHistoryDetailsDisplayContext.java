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

package com.liferay.change.tracking.change.lists.history.web.internal.display.context;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Máté Thurzó
 */
public class ChangeListsHistoryDetailsDisplayContext {

	public ChangeListsHistoryDetailsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_ctEngineManager = _ctEngineManagerServiceTracker.getService();
		_ctManager = _ctManagerServiceTracker.getService();
	}

	public int getAffectsCount(CTEntry ctEntry) {
		return _ctManager.getRelatedOwnerCTEntriesCount(ctEntry.getCtEntryId());
	}

	public String getChangeType(int changeType) {
		if (changeType == CTConstants.CT_CHANGE_TYPE_DELETION) {
			return "deleted";
		}
		else if (changeType == CTConstants.CT_CHANGE_TYPE_MODIFICATION) {
			return "modified";
		}

		return "added";
	}

	public SearchContainer<CTEntry> getCTCollectionSearchContainer(
		CTCollection ctCollection) {

		SearchContainer<CTEntry> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, 0, SearchContainer.DEFAULT_DELTA,
			_getIteratorURL(), null, "no-changes-were-found");

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		String keywords = displayTerms.getKeywords();

		OrderByComparator<CTEntry> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"CTEntry", _getOrderByCol(), getOrderByType().equals("asc"));

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_DRAFT, true, searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		queryDefinition.setEnd(searchContainer.getEnd());
		queryDefinition.setOrderByComparator(orderByComparator);
		queryDefinition.setStart(searchContainer.getStart());
		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		searchContainer.setResults(
			_ctEngineManager.getCTEntries(
				ctCollection, keywords, queryDefinition));
		searchContainer.setTotal(
			_ctEngineManager.getCTEntriesCount(
				ctCollection, keywords, queryDefinition));

		return searchContainer;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "desc");

		return _orderByType;
	}

	public String getSearchActionURL(long ctCollectionId) {
		PortletURL portletURL = _getIteratorURL();

		portletURL.setParameter(
			"ctCollectionId", String.valueOf(ctCollectionId));
		portletURL.setParameter(
			"mvcRenderCommandName", "/change_lists_history/view_details");

		return portletURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getIteratorURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	private PortletURL _getIteratorURL() {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		long ctCollectionId = ParamUtil.getLong(
			_renderRequest, "ctCollectionId");

		String backURL = ParamUtil.getString(_renderRequest, "backURL");

		PortletURL iteratorURL = _renderResponse.createRenderURL();

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			iteratorURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			iteratorURL.setParameter("orderByType", orderByType);
		}

		iteratorURL.setParameter(
			"ctCollectionId", String.valueOf(ctCollectionId));
		iteratorURL.setParameter("backURL", backURL);
		iteratorURL.setParameter("displayStyle", "list");
		iteratorURL.setParameter(
			"mvcRenderCommandName", "/change_lists_history/view_details");
		iteratorURL.setParameter("redirect", currentURL.toString());

		return iteratorURL;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "title");

		return _orderByCol;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "title"));
						dropdownItem.setHref(
							_getIteratorURL(), "orderByCol", "title");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "name"));
					});
			}
		};
	}

	private static ServiceTracker<CTEngineManager, CTEngineManager>
		_ctEngineManagerServiceTracker;
	private static ServiceTracker<CTManager, CTManager>
		_ctManagerServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTEngineManager.class);

		ServiceTracker<CTEngineManager, CTEngineManager>
			ctEngineManagerServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), CTEngineManager.class, null);

		ctEngineManagerServiceTracker.open();

		_ctEngineManagerServiceTracker = ctEngineManagerServiceTracker;

		ServiceTracker<CTManager, CTManager> ctManagerServiceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), CTManager.class, null);

		ctManagerServiceTracker.open();

		_ctManagerServiceTracker = ctManagerServiceTracker;
	}

	private final CTEngineManager _ctEngineManager;
	private final CTManager _ctManager;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}