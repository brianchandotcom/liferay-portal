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
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.security.auth.ScreenNameGenerator;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

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

		boolean finalSkipDefault = GetterUtil.getBoolean(skipDefault);

		List<PortalInstance> portalInstances = new ArrayList<>();

		_companyLocalService.forEachCompany(
			company -> {
				if (!finalSkipDefault ||
					(_portalInstancesLocalService.getDefaultCompanyId() !=
						company.getCompanyId())) {

					portalInstances.add(_toPortalInstance(company));
				}
			});

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

		return postPortalInstanceWithAdminInfo(
			null, null, null, portalInstance);
	}

	@Override
	public PortalInstance postPortalInstanceWithAdminInfo(
			String administratorEmailAddress, String administratorFirstName,
			String administratorLastName, PortalInstance portalInstance)
		throws Exception {

		Company company = _companyLocalService.addCompany(
			portalInstance.getCompanyId(), portalInstance.getPortalInstanceId(),
			portalInstance.getVirtualHost(), portalInstance.getDomain(), false,
			0, true);

		if (Validator.isNotNull(administratorEmailAddress) ||
			Validator.isNotNull(administratorFirstName) ||
			Validator.isNotNull(administratorLastName)) {

			_validateAdminInfo(
				company.getCompanyId(), administratorEmailAddress,
				administratorFirstName, administratorLastName);

			User defaultAdminUser = _userLocalService.getUserByEmailAddress(
				company.getCompanyId(),
				PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" +
					company.getMx());

			defaultAdminUser.setEmailAddress(administratorEmailAddress);
			defaultAdminUser.setFirstName(administratorFirstName);
			defaultAdminUser.setLastName(administratorLastName);

			ScreenNameGenerator screenNameGenerator =
				ScreenNameGeneratorFactory.getInstance();

			defaultAdminUser.setScreenName(
				screenNameGenerator.generate(
					company.getCompanyId(), defaultAdminUser.getUserId(),
					administratorEmailAddress));

			_userLocalService.updateUser(defaultAdminUser);
		}

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					company.getCompanyId())) {

			_portalInstancesLocalService.initializePortalInstance(
				company.getCompanyId(), portalInstance.getSiteInitializerKey(),
				_servletContext);
		}

		_portalInstancesLocalService.synchronizePortalInstances();

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

	private void _validateAdminInfo(
			long companyId, String emailAddress, String firstName,
			String lastName)
		throws Exception {

		if (Validator.isNull(emailAddress) || Validator.isNull(firstName) ||
			Validator.isNull(lastName)) {

			throw new UserScreenNameException.MustNotBeNull();
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		if (!emailAddressValidator.validate(companyId, emailAddress)) {
			throw new UserEmailAddressException.MustValidate(
				emailAddress, emailAddressValidator);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference(
		target = "(&(original.bean=true)(bean.id=javax.servlet.ServletContext))"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}