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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link CompanyService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyService
 * @generated
 */
public class CompanyServiceWrapper implements CompanyService {
	public CompanyServiceWrapper(CompanyService companyService) {
		_companyService = companyService;
	}

	public com.liferay.portal.model.Company addCompany(java.lang.String webId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String shardName, boolean system, int maxUsers, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.addCompany(webId, virtualHost, mx, shardName,
			system, maxUsers, active);
	}

	public void deleteLogo(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyService.deleteLogo(companyId);
	}

	public com.liferay.portal.model.Company getCompanyById(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.getCompanyById(companyId);
	}

	public com.liferay.portal.model.Company getCompanyByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.getCompanyByLogoId(logoId);
	}

	public com.liferay.portal.model.Company getCompanyByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.getCompanyByMx(mx);
	}

	public com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.getCompanyByVirtualHost(virtualHost);
	}

	public com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.getCompanyByWebId(webId);
	}

	public void removePreferences(long companyId, java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyService.removePreferences(companyId, keys);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx, int maxUsers,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.updateCompany(companyId, virtualHost, mx,
			maxUsers, active);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.updateCompany(companyId, virtualHost, mx,
			homeURL, name, legalName, legalId, legalType, sicCode,
			tickerSymbol, industry, type, size);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHost, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyService.updateCompany(companyId, virtualHost, mx,
			homeURL, name, legalName, legalId, legalType, sicCode,
			tickerSymbol, industry, type, size, languageId, timeZoneId,
			addresses, emailAddresses, phones, websites, properties);
	}

	public void updateDisplay(long companyId, java.lang.String languageId,
		java.lang.String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyService.updateDisplay(companyId, languageId, timeZoneId);
	}

	public void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyService.updateLogo(companyId, file);
	}

	public void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyService.updatePreferences(companyId, properties);
	}

	public void updateSecurity(long companyId, java.lang.String authType,
		boolean autoLogin, boolean sendPassword, boolean strangers,
		boolean strangersWithMx, boolean strangersVerify, boolean communityLogo)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyService.updateSecurity(companyId, authType, autoLogin,
			sendPassword, strangers, strangersWithMx, strangersVerify,
			communityLogo);
	}

	public CompanyService getWrappedCompanyService() {
		return _companyService;
	}

	public void setWrappedCompanyService(CompanyService companyService) {
		_companyService = companyService;
	}

	private CompanyService _companyService;
}