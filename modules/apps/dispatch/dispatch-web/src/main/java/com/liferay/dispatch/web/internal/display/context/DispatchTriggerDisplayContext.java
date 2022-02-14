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

package com.liferay.dispatch.web.internal.display.context;

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.executor.DispatchTaskExecutorRegistry;
import com.liferay.dispatch.metadata.DispatchTriggerMetadata;
import com.liferay.dispatch.metadata.DispatchTriggerMetadataProvider;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
public class DispatchTriggerDisplayContext extends BaseDisplayContext {

	public DispatchTriggerDisplayContext(
		DispatchTaskExecutorRegistry dispatchTaskExecutorRegistry,
		DispatchTriggerLocalService dispatchTriggerLocalService,
		DispatchTriggerMetadataProvider dispatchTriggerMetadataProvider,
		RenderRequest renderRequest) {

		super(renderRequest);

		_dispatchTaskExecutorRegistry = dispatchTaskExecutorRegistry;
		_dispatchTriggerLocalService = dispatchTriggerLocalService;
		_dispatchTriggerMetadataProvider = dispatchTriggerMetadataProvider;
	}

	public String getDispatchTaskExecutorName(
		String dispatchTaskExecutorType, Locale locale) {

		return LanguageUtil.get(
			locale,
			_dispatchTaskExecutorRegistry.fetchDispatchTaskExecutorName(
				dispatchTaskExecutorType));
	}

	public Set<String> getDispatchTaskExecutorTypes() {
		Set<String> dispatchTaskExecutorTypes =
			_dispatchTaskExecutorRegistry.getDispatchTaskExecutorTypes();

		Stream<String> stream = dispatchTaskExecutorTypes.parallelStream();

		return stream.filter(
			type -> !_dispatchTaskExecutorRegistry.isHiddenInUI(type)
		).collect(
			Collectors.toSet()
		);
	}

	public DispatchTrigger getDispatchTrigger() {
		return dispatchRequestHelper.getDispatchTrigger();
	}

	public DispatchTriggerMetadata getDispatchTriggerMetadata(
		long dispatchTriggerId) {

		return _dispatchTriggerMetadataProvider.getDispatchTriggerMetadata(
			dispatchTriggerId);
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			dispatchRequestHelper.getRequest(), DispatchPortletKeys.DISPATCH,
			"trigger-order-by-col", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			dispatchRequestHelper.getRequest(), DispatchPortletKeys.DISPATCH,
			"trigger-order-by-type", "desc");

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			dispatchRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			dispatchRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			dispatchRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(
				dispatchRequestHelper.getLiferayPortletResponse());
		}

		return _rowChecker;
	}

	public SearchContainer<DispatchTrigger> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			dispatchRequestHelper.getLiferayPortletRequest(), getPortletURL(),
			null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");
		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(null);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setResultsAndTotal(
			() -> _dispatchTriggerLocalService.getDispatchTriggers(
				dispatchRequestHelper.getCompanyId(),
				_searchContainer.getStart(), _searchContainer.getEnd()),
			_dispatchTriggerLocalService.getDispatchTriggersCount(
				dispatchRequestHelper.getCompanyId()));
		_searchContainer.setRowChecker(getRowChecker());

		return _searchContainer;
	}

	private final DispatchTaskExecutorRegistry _dispatchTaskExecutorRegistry;
	private final DispatchTriggerLocalService _dispatchTriggerLocalService;
	private final DispatchTriggerMetadataProvider
		_dispatchTriggerMetadataProvider;
	private String _orderByCol;
	private String _orderByType;
	private RowChecker _rowChecker;
	private SearchContainer<DispatchTrigger> _searchContainer;

}