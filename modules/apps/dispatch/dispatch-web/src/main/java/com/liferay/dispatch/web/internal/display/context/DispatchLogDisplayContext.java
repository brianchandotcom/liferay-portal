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

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogService;
import com.liferay.dispatch.web.internal.display.context.util.DispatchRequestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
public class DispatchLogDisplayContext {

	public DispatchLogDisplayContext(
		DispatchLogService dispatchLogService, RenderRequest renderRequest) {

		_dispatchLogService = dispatchLogService;

		_dispatchRequestHelper = new DispatchRequestHelper(renderRequest);

		_dateFormat = DateFormat.getDateTimeInstance(
			SimpleDateFormat.SHORT, SimpleDateFormat.LONG,
			_dispatchRequestHelper.getLocale());
	}

	public String getDateString(Date date) {
		if (date != null) {
			return _dateFormat.format(date);
		}

		return StringPool.BLANK;
	}

	public DispatchLog getDispatchLog() throws PortalException {
		return _dispatchLogService.getDispatchLog(
			ParamUtil.getLong(
				_dispatchRequestHelper.getRequest(), "dispatchLogId"));
	}

	public DispatchTrigger getDispatchTrigger() {
		return _dispatchRequestHelper.getDispatchTrigger();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_dispatchRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_dispatchRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dispatchRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String dispatchTriggerId = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "dispatchTriggerId");

		if (Validator.isNotNull(dispatchTriggerId)) {
			portletURL.setParameter("dispatchTriggerId", dispatchTriggerId);
		}

		portletURL.setParameter(
			"mvcRenderCommandName", "/dispatch/edit_dispatch_trigger");

		String redirect = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			DispatchConstants.CATEGORY_KEY_DISPATCH_LOGS);

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(
				_dispatchRequestHelper.getLiferayPortletResponse());
		}

		return _rowChecker;
	}

	public SearchContainer<DispatchLog> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		DispatchTrigger dispatchTrigger = getDispatchTrigger();

		_searchContainer = new SearchContainer<>(
			_dispatchRequestHelper.getLiferayPortletRequest(), getPortletURL(),
			null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");
		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(null);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(getRowChecker());

		int total = _dispatchLogService.getDispatchLogsCount(
			dispatchTrigger.getDispatchTriggerId());

		_searchContainer.setTotal(total);

		List<DispatchLog> results = _dispatchLogService.getDispatchLogs(
			dispatchTrigger.getDispatchTriggerId(), _searchContainer.getStart(),
			_searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private final DateFormat _dateFormat;
	private final DispatchLogService _dispatchLogService;
	private final DispatchRequestHelper _dispatchRequestHelper;
	private RowChecker _rowChecker;
	private SearchContainer<DispatchLog> _searchContainer;

}