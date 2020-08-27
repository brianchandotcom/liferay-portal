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
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogService;
import com.liferay.commerce.data.integration.web.internal.display.context.util.CommerceDataIntegrationRequestHelper;
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
public class CommerceDataIntegrationProcessLogDisplayContext {

	public CommerceDataIntegrationProcessLogDisplayContext(
		CommerceDataIntegrationProcessLogService
			commerceDataIntegrationProcessLogService,
		RenderRequest renderRequest) {

		_commerceDataIntegrationProcessLogService =
			commerceDataIntegrationProcessLogService;

		_commerceDataIntegrationRequestHelper =
			new CommerceDataIntegrationRequestHelper(renderRequest);

		_dateFormat = DateFormat.getDateTimeInstance(
			SimpleDateFormat.SHORT, SimpleDateFormat.LONG,
			_commerceDataIntegrationRequestHelper.getLocale());
	}

	public CommerceDataIntegrationProcess getCommerceDataIntegrationProcess() {
		return _commerceDataIntegrationRequestHelper.
			getCommerceDataIntegrationProcess();
	}

	public CommerceDataIntegrationProcessLog
			getCommerceDataIntegrationProcessLog()
		throws PortalException {

		long cDataIntegrationProcessLogId = ParamUtil.getLong(
			_commerceDataIntegrationRequestHelper.getRequest(),
			"cDataIntegrationProcessLogId");

		if (cDataIntegrationProcessLogId == 0) {
			return null;
		}

		return _commerceDataIntegrationProcessLogService.
			getCommerceDataIntegrationProcessLog(cDataIntegrationProcessLogId);
	}

	public String getFormattedDate(Date date) {
		if (date != null) {
			return _dateFormat.format(date);
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

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(
				_commerceDataIntegrationRequestHelper.
					getLiferayPortletResponse());
		}

		return _rowChecker;
	}

	public SearchContainer<CommerceDataIntegrationProcessLog>
			getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			getCommerceDataIntegrationProcess();

		_searchContainer = new SearchContainer<>(
			_commerceDataIntegrationRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");
		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(null);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceDataIntegrationProcessLogService.
				getCommerceDataIntegrationProcessLogsCount(
					commerceDataIntegrationProcess.
						getCommerceDataIntegrationProcessId());

		_searchContainer.setTotal(total);

		List<CommerceDataIntegrationProcessLog> results =
			_commerceDataIntegrationProcessLogService.
				getCommerceDataIntegrationProcessLogs(
					commerceDataIntegrationProcess.
						getCommerceDataIntegrationProcessId(),
					_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private final CommerceDataIntegrationProcessLogService
		_commerceDataIntegrationProcessLogService;
	private final CommerceDataIntegrationRequestHelper
		_commerceDataIntegrationRequestHelper;
	private final DateFormat _dateFormat;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceDataIntegrationProcessLog> _searchContainer;

}