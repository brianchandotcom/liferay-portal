/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.portal.instances.internal.resource.v1_0;

import com.liferay.headless.portal.instances.dto.v1_0.Admin;
import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.resource.v1_0.PortalInstanceResource;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
		Company company = _companyService.getCompanyByWebId(portalInstanceId);

		_companyService.deleteCompany(company.getCompanyId());

		_portalInstancesLocalService.synchronizePortalInstances();
	}

	@Override
	public PortalInstance getPortalInstance(String portalInstanceId)
		throws Exception {

		return _toPortalInstance(
			_companyService.getCompanyByWebId(portalInstanceId));
	}

	@Override
	public Page<PortalInstance> getPortalInstancesPage(Boolean skipDefault)
		throws Exception {

		boolean finalSkipDefault = GetterUtil.getBoolean(skipDefault);

		List<PortalInstance> portalInstances = new ArrayList<>();

		_companyService.forEachCompany(
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

		Company company = _companyService.getCompanyByWebId(portalInstanceId);

		String virtualHostname = GetterUtil.getString(
			portalInstance.getVirtualHost(), company.getVirtualHostname());
		String domain = GetterUtil.getString(
			portalInstance.getDomain(), company.getMx());

		return _toPortalInstance(
			_companyService.updateCompany(
				company.getCompanyId(), virtualHostname, domain,
				company.getMaxUsers(), company.isActive()));
	}

	@Override
	public PortalInstance postPortalInstance(PortalInstance portalInstance)
		throws Exception {

		Admin admin = portalInstance.getAdmin();

		if (admin != null) {
			_validateAdmin(admin);
		}

		Long companyId = portalInstance.getCompanyId();

		if (companyId == null) {
			companyId = 0L;
		}

		Company company = _companyService.addCompany(
			companyId, portalInstance.getPortalInstanceId(),
			portalInstance.getVirtualHost(), portalInstance.getDomain(), 0,
			true);

		if (admin != null) {
			User defaultAdminUser = _userLocalService.getUserByEmailAddress(
				company.getCompanyId(),
				PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" +
					company.getMx());

			Contact contact = defaultAdminUser.getContact();

			Calendar calendar = CalendarFactoryUtil.getCalendar();

			calendar.setTime(contact.getBirthday());

			_userLocalService.updateUser(
				defaultAdminUser.getUserId(), null, null, null, false,
				defaultAdminUser.getReminderQueryQuestion(),
				defaultAdminUser.getReminderQueryAnswer(),
				defaultAdminUser.getScreenName(), admin.getEmailAddress(), true,
				null, defaultAdminUser.getLanguageId(),
				defaultAdminUser.getTimeZoneId(),
				defaultAdminUser.getGreeting(), defaultAdminUser.getComments(),
				admin.getGivenName(), defaultAdminUser.getMiddleName(),
				admin.getFamilyName(), contact.getPrefixListTypeId(),
				contact.getSuffixListTypeId(), defaultAdminUser.isMale(),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(),
				contact.getJobTitle(), null, null, null, null, null, null);
		}

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					company.getCompanyId())) {

			_portalInstancesLocalService.initializePortalInstance(
				company.getCompanyId(), portalInstance.getSiteInitializerKey());
		}

		_portalInstancesLocalService.synchronizePortalInstances();

		return _toPortalInstance(company);
	}

	@Override
	public void putPortalInstanceActivate(String portalInstanceId)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(portalInstanceId);

		_companyService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), true);
	}

	@Override
	public void putPortalInstanceDeactivate(String portalInstanceId)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(portalInstanceId);

		_companyService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), false);
	}

	private PortalInstance _toPortalInstance(Company company) {
		return new PortalInstance() {
			{
				setActive(company::isActive);
				setCompanyId(company::getCompanyId);
				setDomain(company::getMx);
				setPortalInstanceId(company::getWebId);
				setVirtualHost(company::getVirtualHostname);
			}
		};
	}

	private void _validateAdmin(Admin admin) throws Exception {
		if (Validator.isNull(admin.getEmailAddress()) ||
			Validator.isNull(admin.getFamilyName()) ||
			Validator.isNull(admin.getGivenName())) {

			throw new UserScreenNameException.MustNotBeNull();
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		if (!emailAddressValidator.validate(0, admin.getEmailAddress())) {
			throw new UserEmailAddressException.MustValidate(
				admin.getEmailAddress(), emailAddressValidator);
		}
	}

	@Reference
	private CompanyService _companyService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference
	private UserLocalService _userLocalService;

}