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

import com.liferay.dispatch.core.scheduler.ScheduledTaskDispatchTrigger;
import com.liferay.dispatch.core.scheduler.ScheduledTaskDispatchTriggerHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Format;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Matija Petanjek
 */
public class ScheduledTaskDispatchTriggerDisplayContext
	extends BaseDispatchTriggerDisplayContext {

	public ScheduledTaskDispatchTriggerDisplayContext(
		RenderRequest renderRequest,
		ScheduledTaskDispatchTriggerHelper scheduledTaskDispatchTriggerHelper) {

		super(renderRequest);

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			dispatchRequestHelper.getLocale());

		_scheduledTaskDispatchTriggerHelper =
			scheduledTaskDispatchTriggerHelper;
	}

	public String getNextFireDateString(
			ScheduledTaskDispatchTrigger scheduledTaskDispatchTrigger)
		throws SchedulerException {

		Date nextFireDate =
			_scheduledTaskDispatchTriggerHelper.
				getScheduledTaskDispatchTriggerNextFireDate(
					scheduledTaskDispatchTrigger.getName(),
					scheduledTaskDispatchTrigger.getGroupName(),
					scheduledTaskDispatchTrigger.getStorageType());

		if (nextFireDate != null) {
			return _dateFormatDateTime.format(nextFireDate);
		}

		return StringPool.BLANK;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			dispatchRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "start-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			dispatchRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	public PortletURL getPortletURL() {
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

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/dispatch/edit_scheduled_task_dispatch_trigger");

		portletURL.setParameter("tabs1", "scheduled-task");

		String redirect = ParamUtil.getString(
			dispatchRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		return portletURL;
	}

	public SearchContainer<ScheduledTaskDispatchTrigger> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			dispatchRequestHelper.getLiferayPortletRequest(), getPortletURL(),
			null, "no-items-were-found");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(null);
		_searchContainer.setOrderByType(getOrderByType());

		int total =
			_scheduledTaskDispatchTriggerHelper.
				getScheduledTaskDispatchTriggerCount();

		_searchContainer.setTotal(total);

		List<ScheduledTaskDispatchTrigger> results =
			_scheduledTaskDispatchTriggerHelper.
				getScheduledTaskDispatchTriggers(
					_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public TriggerState getTriggerState(
			ScheduledTaskDispatchTrigger scheduledTaskDispatchTrigger)
		throws SchedulerException {

		return _scheduledTaskDispatchTriggerHelper.getTriggerState(
			scheduledTaskDispatchTrigger.getName(),
			scheduledTaskDispatchTrigger.getGroupName(),
			scheduledTaskDispatchTrigger.getStorageType());
	}

	private final Format _dateFormatDateTime;
	private final ScheduledTaskDispatchTriggerHelper
		_scheduledTaskDispatchTriggerHelper;
	private SearchContainer<ScheduledTaskDispatchTrigger> _searchContainer;

}