/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.display.context;

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.web.internal.security.permisison.resource.SchedulerResponsePermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;

import java.text.Format;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Matija Petanjek
 */
public class SchedulerResponseDisplayContext extends BaseDisplayContext {

	public SchedulerResponseDisplayContext(
		RenderRequest renderRequest,
		SchedulerEngineHelper schedulerEngineHelper) {

		super(renderRequest);

		_schedulerEngineHelper = schedulerEngineHelper;

		_dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			dispatchRequestHelper.getLocale());
	}

	public String getNextFireDateString(SchedulerResponse schedulerResponse)
		throws SchedulerException {

		Date nextFireDate = _getNextFireDate(
			schedulerResponse.getJobName(), schedulerResponse.getGroupName(),
			schedulerResponse.getStorageType());

		if (nextFireDate != null) {
			return _dateTimeFormat.format(nextFireDate);
		}

		return StringPool.BLANK;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			dispatchRequestHelper.getRequest(), DispatchPortletKeys.DISPATCH,
			"scheduler-response-order-by-col", "start-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			dispatchRequestHelper.getRequest(), DispatchPortletKeys.DISPATCH,
			"scheduler-response-order-by-type", "desc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			dispatchRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/dispatch/edit_scheduler_response"
		).setTabs1(
			"scheduler-response"
		).buildPortletURL();

		String redirect = ParamUtil.getString(
			dispatchRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

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

	public SearchContainer<SchedulerResponse> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			dispatchRequestHelper.getLiferayPortletRequest(), getPortletURL(),
			null, "no-items-were-found");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(null);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setResultsAndTotal(
			() -> _getSchedulerResponses(
				dispatchRequestHelper.getPermissionChecker(),
				_searchContainer.getStart(), _searchContainer.getEnd()),
			_getSchedulerResponsesCount(
				dispatchRequestHelper.getPermissionChecker()));

		return _searchContainer;
	}

	public String getSimpleName(String jobName) {
		return jobName.substring(jobName.lastIndexOf(StringPool.PERIOD) + 1);
	}

	public TriggerState getTriggerState(SchedulerResponse schedulerResponse)
		throws SchedulerException {

		return _getTriggerState(
			schedulerResponse.getJobName(), schedulerResponse.getGroupName(),
			schedulerResponse.getStorageType());
	}

	public boolean isSystem(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		if (message.getLong("companyId") == CompanyConstants.SYSTEM) {
			return true;
		}

		return false;
	}

	private Date _getNextFireDate(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, groupName, storageType);

		if (schedulerResponse == null) {
			return null;
		}

		return _schedulerEngineHelper.getNextFireDate(schedulerResponse);
	}

	private List<SchedulerResponse> _getSchedulerResponses(
		PermissionChecker permissionChecker) {

		List<SchedulerResponse> schedulerResponses = null;

		try {
			schedulerResponses = _schedulerEngineHelper.getScheduledJobs();
		}
		catch (SchedulerException schedulerException) {
			_log.error("Unable to get scheduler responses", schedulerException);

			return Collections.emptyList();
		}

		return ListUtil.filter(
			schedulerResponses,
			schedulerResponse -> SchedulerResponsePermission.contains(
				permissionChecker, schedulerResponse));
	}

	private List<SchedulerResponse> _getSchedulerResponses(
		PermissionChecker permissionChecker, int start, int end) {

		List<SchedulerResponse> schedulerResponses = _getSchedulerResponses(
			permissionChecker);

		Collections.sort(
			schedulerResponses,
			Comparator.comparing(
				schedulerResponse -> getSimpleName(
					schedulerResponse.getJobName())));

		return schedulerResponses.subList(
			start, Math.min(end, schedulerResponses.size()));
	}

	private int _getSchedulerResponsesCount(
		PermissionChecker permissionChecker) {

		List<SchedulerResponse> schedulerResponses = _getSchedulerResponses(
			permissionChecker);

		return schedulerResponses.size();
	}

	private TriggerState _getTriggerState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, groupName, storageType);

		if (schedulerResponse == null) {
			return null;
		}

		return _schedulerEngineHelper.getJobState(schedulerResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerResponseDisplayContext.class);

	private final Format _dateTimeFormat;
	private String _orderByCol;
	private String _orderByType;
	private final SchedulerEngineHelper _schedulerEngineHelper;
	private SearchContainer<SchedulerResponse> _searchContainer;

}