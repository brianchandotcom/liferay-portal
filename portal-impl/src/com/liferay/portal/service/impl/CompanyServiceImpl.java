/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.base.CompanyServiceBaseImpl;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.InputStream;

import java.util.List;

/**
 * Provides the local service for accessing, adding, checking, and updating
 * companies. Its methods include permission checks. Each company refers to a
 * separate portal instance.
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@JSONWebService
public class CompanyServiceImpl extends CompanyServiceBaseImpl {

	/**
	 * Adds a company.
	 *
	 * @param  webId the company's web domain
	 * @param  virtualHost the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  shardName the company's shard
	 * @param  system whether the company is the very first company (i.e., the
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @return the company
	 * @throws PortalException if the web domain, virtual host name, or mail
	 *         domain was invalid or if the user was not a universal
	 *         administrator
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company addCompany(
			String webId, String virtualHost, String mx, String shardName,
			boolean system, int maxUsers, boolean active)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.addCompany(
			webId, virtualHost, mx, shardName, system, maxUsers, active);
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company deleteCompany(long companyId)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.deleteCompany(companyId);
	}

	/**
	 * Deletes the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if the company with the primary key could not be
	 *         found or if the company's logo could not be found or if the user
	 *         was not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteLogo(long companyId)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.deleteLogo(companyId);
	}

	/**
	 * Returns the company with the primary key.
	 *
	 * @param  companyId the primary key of the company
	 * @return Returns the company with the primary key
	 * @throws PortalException if a company with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyById(long companyId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyById(companyId);
	}

	/**
	 * Returns the company with the logo.
	 *
	 * @param  logoId the ID of the company's logo
	 * @return Returns the company with the logo
	 * @throws PortalException if the company with the logo could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByLogoId(long logoId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByLogoId(logoId);
	}

	/**
	 * Returns the company with the mail domian.
	 *
	 * @param  mx the company's mail domain
	 * @return Returns the company with the mail domain
	 * @throws PortalException if the company with the mail domain could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByMx(String mx)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByMx(mx);
	}

	/**
	 * Returns the company with the virtual host name.
	 *
	 * @param  virtualHost the company's virtual host name
	 * @return Returns the company with the virtual host name
	 * @throws PortalException if the company with the virtual host name could
	 *         not be found or if the virtual host was not associated with a
	 *         company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByVirtualHost(String virtualHost)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByVirtualHost(virtualHost);
	}

	/**
	 * Returns the company with the web domain.
	 *
	 * @param  webId the company's web domain
	 * @return Returns the company with the web domain
	 * @throws PortalException if the company with the web domain could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByWebId(String webId)
		throws PortalException, SystemException {

		return companyLocalService.getCompanyByWebId(webId);
	}

	/**
	 * Removes the values that match the keys of the company's preferences.
	 *
	 * This method is called by {@link
	 * com.liferay.portlet.portalsettings.action.EditLDAPServerAction} remotely
	 * through {@link com.liferay.portal.service.CompanyService}.
	 *
	 * @param  companyId the primary key of the company
	 * @param  keys the company's preferences keys to be remove
	 * @throws PortalException if the user was not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void removePreferences(long companyId, String[] keys)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.removePreferences(companyId, keys);
	}

	/**
	 * Updates the company
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHost the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @return the company with the primary key
	 * @throws PortalException if a company with the primary key could not be
	 *         found or if the new information was invalid or if the user was
	 *         not a universal administrator
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHost, String mx, int maxUsers,
			boolean active)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(
			companyId, virtualHost, mx, maxUsers, active);
	}

	/**
	 * Updates the company with additional account information.
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHost the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  homeURL the company's home URL (optionally <code>null</code>)
	 * @param  name the company's account name (optionally <code>null</code>)
	 * @param  legalName the company's account legal name (optionally
	 *         <code>null</code>)
	 * @param  legalId the company's account legal ID (optionally
	 *         <code>null</code>)
	 * @param  legalType the company's account legal type (optionally
	 *         <code>null</code>)
	 * @param  sicCode the company's account SIC code (optionally
	 *         <code>null</code>)
	 * @param  tickerSymbol the company's account ticker symbol (optionally
	 *         <code>null</code>)
	 * @param  industry the the company's account industry (optionally
	 *         <code>null</code>)
	 * @param  type the company's account type (optionally <code>null</code>)
	 * @param  size the company's account size (optionally <code>null</code>)
	 * @return the the company with the primary key
	 * @throws PortalException if a company with the primary key could not be
	 *         found or if the new information was invalid or if the user was
	 *         not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Updates the company with addition information.
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHost the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  homeURL the company's home URL (optionally <code>null</code>)
	 * @param  name the company's account name (optionally <code>null</code>)
	 * @param  legalName the company's account legal name (optionally
	 *         <code>null</code>)
	 * @param  legalId the company's accout legal ID (optionally
	 *         <code>null</code>)
	 * @param  legalType the company's account legal type (optionally
	 *         <code>null</code>)
	 * @param  sicCode the company's account SIC code (optionally
	 *         <code>null</code>)
	 * @param  tickerSymbol the company's account ticker symbol (optionally
	 *         <code>null</code>)
	 * @param  industry the the company's account industry (optionally
	 *         <code>null</code>)
	 * @param  type the company's account type (optionally <code>null</code>)
	 * @param  size the company's account size (optionally <code>null</code>)
	 * @param  languageId the ID of the company's default user's language
	 * @param  timeZoneId the ID of the company's default user's time zone
	 * @param  addresses the company's addresses
	 * @param  emailAddresses the company's email addresses
	 * @param  phones the company's phone numbers
	 * @param  websites the company's websites
	 * @param  properties the company's properties
	 * @return the company with the primary key
	 * @throws PortalException the company with the primary key could not be
	 *         found or if the new information was invalid or if the user was
	 *         not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
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

	/**
	 * Update the company's display.
	 *
	 * @param  companyId the primary key of the company
	 * @param  languageId the ID of the company's default user's language
	 * @param  timeZoneId the ID of the company's default user's time zone
	 * @throws PortalException if the company's default user could not be found
	 *         or if the user was not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateDisplay(companyId, languageId, timeZoneId);
	}

	/**
	 * Updates the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  bytes the bytes of the company's logo image
	 * @return the company with the primary key
	 * @throws PortalException if the company's logo ID could not be found or if
	 *         the logo's image was corrupted or if the user was an
	 *         administrator
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateLogo(long companyId, byte[] bytes)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		return companyLocalService.updateLogo(companyId, bytes);
	}

	/**
	 * Updates the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  inputStream the input stream of the company's logo image
	 * @return the company with the primary key
	 * @throws PortalException if the company's logo ID could not be found or if
	 *         the logo's image was corrupted or if the user was an
	 *         administrator
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company updateLogo(long companyId, InputStream inputStream)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		return companyLocalService.updateLogo(companyId, inputStream);
	}

	/**
	 * Updates the company's preferences. The company's default properties are
	 * found in portal.properties.
	 *
	 * @param  companyId the primary key of the company
	 * @param  properties the company's properties. See {@link
	 *         com.liferay.portal.kernel.util.UnicodeProperties}
	 * @throws PortalException if the user was not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void updatePreferences(long companyId, UnicodeProperties properties)
		throws PortalException, SystemException {

		if (!roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updatePreferences(companyId, properties);
	}

	/**
	 * Updates the company's security properties.
	 *
	 * @param  companyId the primary key of the company
	 * @param  authType the company's method of authenticating users
	 * @param  autoLogin whether to allow users to select the "remember me"
	 *         feature
	 * @param  sendPassword whether to allow users to ask the company to send
	 *         their passwords
	 * @param  strangers whether to allow strangers to create accounts to
	 *         register themselves in the company
	 * @param  strangersWithMx whether to allow strangers to create accounts
	 *         with email addresses that match the company mail suffix
	 * @param  strangersVerify whether to require strangers who create accounts
	 *         to be verified via email
	 * @param  siteLogo whether to to allow site administrators to use their own
	 *         logo instead of the enterprise logo
	 * @throws PortalException if the user was not an administrator
	 * @throws SystemException if a system exception occurred
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
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