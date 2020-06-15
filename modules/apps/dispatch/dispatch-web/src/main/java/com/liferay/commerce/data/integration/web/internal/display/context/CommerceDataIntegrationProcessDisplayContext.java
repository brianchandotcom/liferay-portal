/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.web.internal.display.context;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.process.type.ProcessType;
import com.liferay.commerce.data.integration.process.type.ProcessTypeRegistry;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessService;
import com.liferay.commerce.data.integration.trigger.CommerceDataIntegrationProcessTriggerHelper;
import com.liferay.commerce.data.integration.web.internal.display.context.util.CommerceDataIntegrationRequestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Format;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
public class CommerceDataIntegrationProcessDisplayContext {

	public CommerceDataIntegrationProcessDisplayContext(
		CommerceDataIntegrationProcessTriggerHelper
			commerceDataIntegrationProcessScheduledTaskHelper,
		CommerceDataIntegrationProcessService
			commerceDataIntegrationProcessService,
		ProcessTypeRegistry processTypeRegistry, RenderRequest renderRequest) {

		_commerceDataIntegrationProcessService =
			commerceDataIntegrationProcessService;
		_processTypeRegistry = processTypeRegistry;
		_commerceDataIntegrationProcessTriggerHelper =
			commerceDataIntegrationProcessScheduledTaskHelper;

		_commerceDataIntegrationRequestHelper =
			new CommerceDataIntegrationRequestHelper(renderRequest);

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			_commerceDataIntegrationRequestHelper.getLocale());
	}

	public CommerceDataIntegrationProcess getCommerceDataIntegrationProcess() {
		return _commerceDataIntegrationRequestHelper.
			getCommerceDataIntegrationProcess();
	}

	public String getNextFireDate(long commerceDataIntegrationProcessId) {
		Date nextRunDate =
			_commerceDataIntegrationProcessTriggerHelper.getNextFireTime(
				commerceDataIntegrationProcessId);

		if (nextRunDate != null) {
			return _dateFormatDateTime.format(nextRunDate);
		}

		return StringPool.BLANK;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_commerceDataIntegrationRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_commerceDataIntegrationRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceDataIntegrationRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			_commerceDataIntegrationRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_commerceDataIntegrationRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public List<ProcessType> getProcessTypes() {
		return _processTypeRegistry.getProcessTypes();
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(
				_commerceDataIntegrationRequestHelper.
					getLiferayPortletResponse());
		}

		return _rowChecker;
	}

	public SearchContainer<CommerceDataIntegrationProcess> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceDataIntegrationRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(null);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceDataIntegrationProcessService.
				getCommerceDataIntegrationProcessesCount(
					_commerceDataIntegrationRequestHelper.getCompanyId());

		_searchContainer.setTotal(total);

		List<CommerceDataIntegrationProcess> results =
			_commerceDataIntegrationProcessService.
				getCommerceDataIntegrationProcesses(
					_commerceDataIntegrationRequestHelper.getCompanyId(),
					_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private final CommerceDataIntegrationProcessService
		_commerceDataIntegrationProcessService;
	private final CommerceDataIntegrationProcessTriggerHelper
		_commerceDataIntegrationProcessTriggerHelper;
	private final CommerceDataIntegrationRequestHelper
		_commerceDataIntegrationRequestHelper;
	private final Format _dateFormatDateTime;
	private final ProcessTypeRegistry _processTypeRegistry;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceDataIntegrationProcess> _searchContainer;

}