/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.base.CompanyServiceBaseImpl;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.File;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class CompanyServiceImpl extends CompanyServiceBaseImpl {

	public Company addCompany(
			String webId, String virtualHost, String mx, String shardName,
			boolean system, int maxUsers, boolean active)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.addCompany(
			webId, virtualHost, mx, shardName, system, maxUsers, active);
	}

	public void deleteLogo(long companyId)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.deleteLogo(companyId);
	}

	public Company getCompanyById(long companyId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyById(companyId);
	}

	public Company getCompanyByLogoId(long logoId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByLogoId(logoId);
	}

	public Company getCompanyByMx(String mx)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByMx(mx);
	}

	public Company getCompanyByVirtualHost(String virtualHost)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByVirtualHost(virtualHost);
	}

	public Company getCompanyByWebId(String webId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByWebId(webId);
	}

	public void removePreferences(long companyId, String[] keys)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.removePreferences(companyId, keys);
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, int maxUsers,
			boolean active)
		throws PortalException, SystemException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(
			companyId, virtualHost, mx, maxUsers, active);
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(
			companyId, virtualHost, mx, homeURL, name, legalName, legalId,
			legalType, sicCode, tickerSymbol, industry, type, size);
	}

	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size, String languageId, String timeZoneId,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<Phone> phones, List<Website> websites,
			UnicodeProperties properties)
		throws PortalException, SystemException {

		Company company = updateCompany(
			companyId, virtualHost, mx, homeURL, name, legalName, legalId,
			legalType, sicCode, tickerSymbol, industry, type, size);

		updateDisplay(company.getCompanyId(), languageId, timeZoneId);

		updatePreferences(company.getCompanyId(), properties);

		UsersAdminUtil.updateAddresses(
			Account.class.getName(), company.getAccountId(), addresses);

		UsersAdminUtil.updateEmailAddresses(
			Account.class.getName(), company.getAccountId(), emailAddresses);

		UsersAdminUtil.updatePhones(
			Account.class.getName(), company.getAccountId(), phones);

		UsersAdminUtil.updateWebsites(
			Account.class.getName(), company.getAccountId(), websites);

		return company;
	}

	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateDisplay(companyId, languageId, timeZoneId);
	}

	public void updateLogo(long companyId, File file)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateLogo(companyId, file);
	}

	public void updatePreferences(long companyId, UnicodeProperties properties)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updatePreferences(companyId, properties);
	}

	public void updateSecurity(
			long companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers, boolean strangersWithMx,
			boolean strangersVerify, boolean siteLogo)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateSecurity(
			companyId, authType, autoLogin, sendPassword, strangers,
			strangersWithMx, strangersVerify, siteLogo);
	}

}