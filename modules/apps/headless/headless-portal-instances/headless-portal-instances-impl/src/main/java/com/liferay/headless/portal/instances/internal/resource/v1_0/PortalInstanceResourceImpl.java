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

package com.liferay.headless.portal.instances.internal.resource.v1_0;

import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.resource.v1_0.PortalInstanceResource;
import com.liferay.portal.instances.initializer.PortalInstanceInitializer;
import com.liferay.portal.instances.initializer.PortalInstanceInitializerRegistry;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import javax.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alberto Chaparro
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/portal-instance.properties",
	scope = ServiceScope.PROTOTYPE, service = PortalInstanceResource.class
)
public class PortalInstanceResourceImpl extends BasePortalInstanceResourceImpl {

	@Override
	public void deletePortalInstance(String portalInstanceId) throws Exception {
		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		_companyLocalService.deleteCompany(company.getCompanyId());

		_portalInstancesLocalService.synchronizePortalInstances();
	}

	@Override
	public PortalInstance getPortalInstance(String portalInstanceId)
		throws Exception {

		return _toPortalInstance(
			_companyLocalService.getCompanyByWebId(portalInstanceId));
	}

	@Override
	public Page<PortalInstance> getPortalInstancesPage(Boolean skipDefault)
		throws Exception {

		skipDefault = GetterUtil.getBoolean(skipDefault);

		List<PortalInstance> portalInstances = new ArrayList<>();

		for (Company company : _companyLocalService.getCompanies(false)) {
			if (skipDefault &&
				(_portalInstancesLocalService.getDefaultCompanyId() ==
					company.getCompanyId())) {

				continue;
			}

			portalInstances.add(_toPortalInstance(company));
		}

		return Page.of(portalInstances);
	}

	@Override
	public PortalInstance patchPortalInstance(
			String portalInstanceId, PortalInstance portalInstance)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		String virtualHostname = GetterUtil.getString(
			portalInstance.getVirtualHost(), company.getVirtualHostname());
		String domain = GetterUtil.getString(
			portalInstance.getDomain(), company.getMx());

		return _toPortalInstance(
			_companyLocalService.updateCompany(
				company.getCompanyId(), virtualHostname, domain,
				company.getMaxUsers(), company.isActive()));
	}

	@Override
	public PortalInstance postPortalInstance(PortalInstance portalInstance)
		throws Exception {

		PortalInstanceInitializer portalInstanceInitializer = null;

		if (Validator.isNotNull(
				portalInstance.getPortalInstanceInitializerKey())) {

			portalInstanceInitializer =
				_portalInstanceInitializerRegistry.getPortalInstanceInitializer(
					portalInstance.getPortalInstanceInitializerKey());

			if (portalInstanceInitializer == null) {
				throw new ValidationException("Invalid initializer key");
			}
		}

		Company company = _companyLocalService.addCompany(
			portalInstance.getCompanyId(), portalInstance.getPortalInstanceId(),
			portalInstance.getVirtualHost(), portalInstance.getDomain(), false,
			0, true);

		_portalInstancesLocalService.initializePortalInstance(
			_servletContext, company.getWebId());

		_portalInstancesLocalService.synchronizePortalInstances();

		if (portalInstanceInitializer != null) {
			portalInstanceInitializer.initialize(
				company.getCompanyId(), contextHttpServletRequest,
				portalInstance.getPortalInstanceInitializerPayload());
		}

		return _toPortalInstance(company);
	}

	@Override
	public void putPortalInstanceActivate(String portalInstanceId)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		_companyLocalService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), true);
	}

	@Override
	public void putPortalInstanceDeactivate(String portalInstanceId)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(
			portalInstanceId);

		_companyLocalService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), false);
	}

	private PortalInstance _toPortalInstance(Company company) {
		return new PortalInstance() {
			{
				active = company.isActive();
				companyId = company.getCompanyId();
				domain = company.getMx();
				portalInstanceId = company.getWebId();
				virtualHost = company.getVirtualHostname();
			}
		};
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstanceInitializerRegistry
		_portalInstanceInitializerRegistry;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference(
		target = "(&(original.bean=true)(bean.id=javax.servlet.ServletContext))"
	)
	private ServletContext _servletContext;

}