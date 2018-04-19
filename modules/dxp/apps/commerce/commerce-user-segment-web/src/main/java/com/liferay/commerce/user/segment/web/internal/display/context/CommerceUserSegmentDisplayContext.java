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

package com.liferay.commerce.user.segment.web.internal.display.context;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributor;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributorRegistry;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.commerce.user.segment.web.internal.display.context.util.CommerceUserSegmentRequestHelper;
import com.liferay.commerce.user.segment.web.internal.util.CommerceUserSegmentPortletUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentDisplayContext {

	public CommerceUserSegmentDisplayContext(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService,
		CommerceUserSegmentCriterionTypeRegistry
			commerceUserSegmentCriterionTypeRegistry,
		CommerceUserSegmentEntryService commerceUserSegmentEntryService,
		HttpServletRequest httpServletRequest) {

		_commerceUserSegmentCriterionService =
			commerceUserSegmentCriterionService;
		_commerceUserSegmentCriterionTypeRegistry =
			commerceUserSegmentCriterionTypeRegistry;
		_commerceUserSegmentEntryService = commerceUserSegmentEntryService;

		_commerceUserSegmentRequestHelper =
			new CommerceUserSegmentRequestHelper(httpServletRequest);
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	public SearchContainer<CommerceUserSegmentCriterion>
		getCommerceUserSegmentCriteriaSearchContainer() throws PortalException {

		if (_commerceUserSegmentCriteriaSearchContainer != null) {
			return _commerceUserSegmentCriteriaSearchContainer;
		}

		_commerceUserSegmentCriteriaSearchContainer = new SearchContainer<>(
			_commerceUserSegmentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-criteria");

		setOrderByColAndType(
			CommerceUserSegmentCriterion.class,
			_commerceUserSegmentCriteriaSearchContainer, "priority", "asc");

		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator =
			CommerceUserSegmentPortletUtil.
				getCommerceUserSegmentCriterionOrderByComparator(
					_commerceUserSegmentCriteriaSearchContainer.getOrderByCol(),
					_commerceUserSegmentCriteriaSearchContainer.
						getOrderByType());

		_commerceUserSegmentCriteriaSearchContainer.setOrderByComparator(
			orderByComparator);

		_commerceUserSegmentCriteriaSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceUserSegmentRequestHelper.getLiferayPortletResponse()));

		int total =
			_commerceUserSegmentCriterionService.
				getCommerceUserSegmentCriteriaCount(
					getCommerceUserSegmentEntryId());

		_commerceUserSegmentCriteriaSearchContainer.setTotal(total);

		List<CommerceUserSegmentCriterion> results =
			_commerceUserSegmentCriterionService.getCommerceUserSegmentCriteria(
				getCommerceUserSegmentEntryId(),
				_commerceUserSegmentCriteriaSearchContainer.getStart(),
				_commerceUserSegmentCriteriaSearchContainer.getEnd(),
				orderByComparator);

		_commerceUserSegmentCriteriaSearchContainer.setResults(results);

		return _commerceUserSegmentCriteriaSearchContainer;
	}

	public CommerceUserSegmentCriterion getCommerceUserSegmentCriterion()
		throws PortalException {

		if (_commerceUserSegmentCriterion != null) {
			return _commerceUserSegmentCriterion;
		}

		HttpServletRequest httpServletRequest =
			_commerceUserSegmentRequestHelper.getRequest();

		long commerceUserSegmentCriterionId = ParamUtil.getLong(
			httpServletRequest, "commerceUserSegmentCriterionId");

		if (commerceUserSegmentCriterionId > 0) {
			_commerceUserSegmentCriterion =
				_commerceUserSegmentCriterionService.
					getCommerceUserSegmentCriterion(
						commerceUserSegmentCriterionId);
		}

		return _commerceUserSegmentCriterion;
	}

	public long getCommerceUserSegmentCriterionId() throws PortalException {
		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			getCommerceUserSegmentCriterion();

		if (commerceUserSegmentCriterion == null) {
			return 0;
		}

		return commerceUserSegmentCriterion.getCommerceUserSegmentCriterionId();
	}

	public CommerceUserSegmentCriterionTypeJSPContributor
		getCommerceUserSegmentCriterionTypeJSPContributor(String key) {

		return _commerceUserSegmentCriterionTypeJSPContributorRegistry.
			getCommerceUserSegmentCriterionTypeJSPContributor(key);
	}

	public List<CommerceUserSegmentCriterionType>
		getCommerceUserSegmentCriterionTypes() {

		return _commerceUserSegmentCriterionTypeRegistry.
			getCommerceUserSegmentCriterionTypes();
	}

	public CommerceUserSegmentEntry getCommerceUserSegmentEntry()
		throws PortalException {

		if (_commerceUserSegmentEntry != null) {
			return _commerceUserSegmentEntry;
		}

		HttpServletRequest httpServletRequest =
			_commerceUserSegmentRequestHelper.getRequest();

		long commerceUserSegmentEntryId = ParamUtil.getLong(
			httpServletRequest, "commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId > 0) {
			_commerceUserSegmentEntry =
				_commerceUserSegmentEntryService.getCommerceUserSegmentEntry(
					commerceUserSegmentEntryId);
		}

		return _commerceUserSegmentEntry;
	}

	public long getCommerceUserSegmentEntryId() throws PortalException {
		CommerceUserSegmentEntry commerceUserSegmentEntry =
			getCommerceUserSegmentEntry();

		if (commerceUserSegmentEntry == null) {
			return 0;
		}

		return commerceUserSegmentEntry.getCommerceUserSegmentEntryId();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceUserSegmentRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		long commerceUserSegmentEntryId = ParamUtil.getLong(
			_commerceUserSegmentRequestHelper.getRequest(),
			"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId > 0) {
			portletURL.setParameter(
				"commerceUserSegmentEntryId",
				String.valueOf(commerceUserSegmentEntryId));
		}

		return portletURL;
	}

	public SearchContainer<CommerceUserSegmentEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceUserSegmentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-entries");

		setOrderByColAndType(
			CommerceUserSegmentEntry.class, _searchContainer, "priority",
			"asc");

		OrderByComparator<CommerceUserSegmentEntry> orderByComparator =
			CommerceUserSegmentPortletUtil.
				getCommerceUserSegmentEntryOrderByComparator(
					_searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType());

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceUserSegmentRequestHelper.getLiferayPortletResponse()));

		long groupId = _commerceUserSegmentRequestHelper.getScopeGroupId();

		if (isSearch()) {
			Sort sort =
				CommerceUserSegmentPortletUtil.getCommerceUserSegmentEntrySort(
					_searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType());

			BaseModelSearchResult<CommerceUserSegmentEntry>
				baseModelSearchResult =
					_commerceUserSegmentEntryService.
						searchCommerceUserSegmentEntries(
							_commerceUserSegmentRequestHelper.getCompanyId(),
							groupId, getKeywords(), _searchContainer.getStart(),
							_searchContainer.getEnd(), sort);

			_searchContainer.setTotal(baseModelSearchResult.getLength());
			_searchContainer.setResults(baseModelSearchResult.getBaseModels());
		}
		else {
			int total =
				_commerceUserSegmentEntryService.
					getCommerceUserSegmentEntriesCount(groupId);

			_searchContainer.setTotal(total);

			List<CommerceUserSegmentEntry> results =
				_commerceUserSegmentEntryService.getCommerceUserSegmentEntries(
					groupId, _searchContainer.getStart(),
					_searchContainer.getEnd(), orderByComparator);

			_searchContainer.setResults(results);
		}

		return _searchContainer;
	}

	protected String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_commerceUserSegmentRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected <T> void setOrderByColAndType(
		Class<T> clazz, SearchContainer<T> searchContainer,
		String defaultOrderByCol, String defaultOrderByType) {

		HttpServletRequest httpServletRequest =
			_commerceUserSegmentRequestHelper.getRequest();

		String orderByCol = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByColParam());
		String orderByType = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByTypeParam());

		String namespace = _commerceUserSegmentRequestHelper.getPortletId();
		String prefix = TextFormatter.format(
			clazz.getSimpleName(), TextFormatter.K);

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			_portalPreferences.setValue(
				namespace, prefix + "-order-by-col", orderByCol);
			_portalPreferences.setValue(
				namespace, prefix + "-order-by-type", orderByType);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				namespace, prefix + "-order-by-col", defaultOrderByCol);
			orderByType = _portalPreferences.getValue(
				namespace, prefix + "-order-by-type", defaultOrderByType);
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	private SearchContainer<CommerceUserSegmentCriterion>
		_commerceUserSegmentCriteriaSearchContainer;
	private CommerceUserSegmentCriterion _commerceUserSegmentCriterion;
	private final CommerceUserSegmentCriterionService
		_commerceUserSegmentCriterionService;
	private CommerceUserSegmentCriterionTypeJSPContributorRegistry
		_commerceUserSegmentCriterionTypeJSPContributorRegistry;
	private final CommerceUserSegmentCriterionTypeRegistry
		_commerceUserSegmentCriterionTypeRegistry;
	private CommerceUserSegmentEntry _commerceUserSegmentEntry;
	private final CommerceUserSegmentEntryService
		_commerceUserSegmentEntryService;
	private final CommerceUserSegmentRequestHelper
		_commerceUserSegmentRequestHelper;
	private String _keywords;
	private final PortalPreferences _portalPreferences;
	private SearchContainer<CommerceUserSegmentEntry> _searchContainer;

}