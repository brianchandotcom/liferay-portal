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
import com.liferay.dispatch.web.internal.display.context.util.DispatchRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matija Petanjek
 */
public class ScheduledTaskDispatchTriggerDisplayContext {

	public ScheduledTaskDispatchTriggerDisplayContext(
		RenderRequest renderRequest,
		ScheduledTaskDispatchTriggerHelper scheduledTaskDispatchTriggerHelper) {

		_dispatchRequestHelper = new DispatchRequestHelper(renderRequest);

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			_dispatchRequestHelper.getLocale());

		_scheduledTaskDispatchTriggerHelper =
			scheduledTaskDispatchTriggerHelper;
	}

	public List<NavigationItem> getNavigationItems() {
		HttpServletRequest httpServletRequest =
			_dispatchRequestHelper.getRequest();

		LiferayPortletResponse liferayPortletResponse =
			_dispatchRequestHelper.getLiferayPortletResponse();

		String tabs1 = ParamUtil.getString(
			httpServletRequest, "tabs1", "dispatch-trigger");

		return NavigationItemList.of(
			() -> {
				NavigationItem navigationItem = new NavigationItem();

				navigationItem.setActive(tabs1.equals("dispatch-trigger"));
				navigationItem.setHref(
					liferayPortletResponse.createRenderURL(), "tabs1",
					"dispatch-trigger");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "dispatch-triggers"));

				return navigationItem;
			},
			() -> {
				NavigationItem navigationItem = new NavigationItem();

				navigationItem.setActive(
					tabs1.equals("liferay-scheduled-task"));
				navigationItem.setHref(
					liferayPortletResponse.createRenderURL(), "tabs1",
					"liferay-scheduled-task", "mvcRenderCommandName",
					"/dispatch/edit_scheduled_task_dispatch_trigger");
				navigationItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "liferay-scheduled-tasks"));

				return navigationItem;
			});
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
			_dispatchRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "start-date");
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

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/dispatch/edit_scheduled_task_dispatch_trigger");

		portletURL.setParameter("tabs1", "liferay-scheduled-task");

		String redirect = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "redirect");

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
			_dispatchRequestHelper.getLiferayPortletRequest(), getPortletURL(),
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
	private final DispatchRequestHelper _dispatchRequestHelper;
	private final ScheduledTaskDispatchTriggerHelper
		_scheduledTaskDispatchTriggerHelper;
	private SearchContainer<ScheduledTaskDispatchTrigger> _searchContainer;

}