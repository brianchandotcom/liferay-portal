/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Zoltán Takács
 */
@Provider
public class PaginationContextProvider implements ContextProvider<Pagination> {

	public PaginationContextProvider(
		ConfigurationProvider configurationProvider, Portal portal) {

		_configurationProvider = configurationProvider;
		_portal = portal;
	}

	@Override
	public Pagination createContext(Message message) {
		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		int requestPage = GetterUtil.getInteger(
			httpServletRequest.getParameter("page"), 1);

		int requestPageSize = GetterUtil.getInteger(
			httpServletRequest.getParameter("pageSize"), 20);

		int pageSizeLimit = _getPageSizeLimit(
			_portal.getCompanyId(httpServletRequest));

		if (_isUnlimited(requestPage, requestPageSize)) {
			if (_isUnlimited(pageSizeLimit)) {
				return Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}

			return Pagination.of(1, pageSizeLimit);
		}

		if (_isUnlimited(pageSizeLimit)) {
			return Pagination.of(requestPage, requestPageSize);
		}

		return Pagination.of(
			requestPage, Math.min(requestPageSize, pageSizeLimit));
	}

	private int _getPageSizeLimit(long companyId) {
		try {
			HeadlessAPICompanyConfiguration headlessAPICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					HeadlessAPICompanyConfiguration.class, companyId);

			return headlessAPICompanyConfiguration.pageSizeLimit();
		}
		catch (ConfigurationException configurationException) {
			throw new InternalServerErrorException(
				configurationException.getMessage());
		}
	}

	private boolean _isUnlimited(Integer... values) {
		for (Integer value : values) {
			if (value <= 0) {
				return true;
			}
		}

		return false;
	}

	private final ConfigurationProvider _configurationProvider;
	private final Portal _portal;

}