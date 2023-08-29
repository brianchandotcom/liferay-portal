/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration;
import com.liferay.portal.vulcan.pagination.InvalidPaginationException;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
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

		int page = GetterUtil.getInteger(
			httpServletRequest.getParameter("page"), 1);

		if (page <= 0) {
			throw new InvalidPaginationException(
				"Page " + page + " is not a number greater than or equal to 1");
		}

		int pageSize = GetterUtil.getInteger(
			httpServletRequest.getParameter("pageSize"), 20);

		try {
			HeadlessAPICompanyConfiguration headlessAPICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					HeadlessAPICompanyConfiguration.class,
					_portal.getCompanyId(httpServletRequest));

			int maxPageSize =
				headlessAPICompanyConfiguration.pageSizeLimit();

			if (maxPageSize <= 0) {
				return Pagination.of(
					page, (pageSize <= 0) ? QueryUtil.ALL_POS : pageSize);
			}

			if ((pageSize > maxPageSize) || (pageSize <= 0)) {
				return Pagination.of(page, maxPageSize);
			}

			return Pagination.of(page, pageSize);
		}
		catch (ConfigurationException configurationException) {
			throw new ServerErrorException(
				configurationException.getMessage(),
				Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	private final ConfigurationProvider _configurationProvider;
	private final Portal _portal;

}