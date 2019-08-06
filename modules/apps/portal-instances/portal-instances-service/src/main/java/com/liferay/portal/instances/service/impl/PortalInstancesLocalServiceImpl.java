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

package com.liferay.portal.instances.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.instances.service.base.PortalInstancesLocalServiceBaseImpl;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PortalInstances;

import java.sql.SQLException;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @see    PortalInstancesLocalServiceBaseImpl
 * @see    com.liferay.portal.instances.service.PortalInstancesLocalServiceUtil
 */
@Component(
	property = "model.class.name=com.liferay.portal.util.PortalInstances",
	service = AopService.class
)
public class PortalInstancesLocalServiceImpl
	extends PortalInstancesLocalServiceBaseImpl {

	@Override
	public void addCompanyId(long companyId) {
		PortalInstances.addCompanyId(companyId);
	}

	@Override
	public long getCompanyId(HttpServletRequest httpServletRequest) {
		return PortalInstances.getCompanyId(httpServletRequest);
	}

	@Override
	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	@Override
	public long[] getCompanyIdsBySQL() throws SQLException {
		return PortalInstances.getCompanyIdsBySQL();
	}

	@Override
	public long getDefaultCompanyId() {
		return PortalInstances.getDefaultCompanyId();
	}

	@Override
	public String[] getWebIds() {
		return PortalInstances.getWebIds();
	}

	@Override
	public void initializePortalInstance(
		ServletContext servletContext, String webId) {

		PortalInstances.initCompany(servletContext, webId);
	}

	@Override
	public boolean isAutoLoginIgnoreHost(String host) {
		return PortalInstances.isAutoLoginIgnoreHost(host);
	}

	@Override
	public boolean isAutoLoginIgnorePath(String path) {
		return PortalInstances.isAutoLoginIgnorePath(path);
	}

	@Override
	public boolean isCompanyActive(long companyId) {
		return PortalInstances.isCompanyActive(companyId);
	}

	@Override
	public boolean isVirtualHostsIgnoreHost(String host) {
		return PortalInstances.isVirtualHostsIgnoreHost(host);
	}

	@Override
	public boolean isVirtualHostsIgnorePath(String path) {
		return PortalInstances.isVirtualHostsIgnorePath(path);
	}

	@Override
	public void reload(ServletContext servletContext) {
		PortalInstances.reload(servletContext);
	}

	@Override
	public void removeCompany(long companyId) {
		PortalInstances.removeCompany(companyId);
	}

	@Clusterable
	@Override
	public void synchronizePortalInstances() {
		try {
			long[] initializedCompanyIds = _portal.getCompanyIds();

			List<Long> removeableCompanyIds = ListUtil.toList(
				initializedCompanyIds);

			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				long companyId = company.getCompanyId();

				removeableCompanyIds.remove(companyId);

				if (ArrayUtil.contains(initializedCompanyIds, companyId)) {
					continue;
				}

				ServletContext portalContext = ServletContextPool.get(
					_portal.getPathContext());

				PortalInstances.initCompany(portalContext, company.getWebId());
			}

			for (long companyId : removeableCompanyIds) {
				PortalInstances.removeCompany(companyId);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstancesLocalServiceImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private Portal _portal;

}