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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.base.CompanyServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.ratings.kernel.transformer.RatingsDataTransformerUtil;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.io.InputStream;

import java.util.List;

import javax.portlet.PortletPreferences;

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
	 * @param  system whether the company is the very first company (i.e., the
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @return the company
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company addCompany(
			String webId, String virtualHost, String mx, boolean system,
			int maxUsers, boolean active)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}

		return companyLocalService.addCompany(
			null, webId, virtualHost, mx, system, maxUsers, active);
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company deleteCompany(long companyId) throws PortalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}

		return companyLocalService.deleteCompany(companyId);
	}

	/**
	 * Deletes the company's logo.
	 *
	 * @param companyId the primary key of the company
	 */
	@Override
	public void deleteLogo(long companyId) throws PortalException {
		if (!_roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.deleteLogo(companyId);
	}

	/**
	 * Returns all the companies.
	 *
	 * @return the companies
	 */
	@Override
	public List<Company> getCompanies() {
		return companyLocalService.getCompanies();
	}

	/**
	 * Returns the company with the primary key.
	 *
	 * @param  companyId the primary key of the company
	 * @return Returns the company with the primary key
	 */
	@Override
	public Company getCompanyById(long companyId) throws PortalException {
		return companyLocalService.getCompanyById(companyId);
	}

	/**
	 * Returns the company with the logo.
	 *
	 * @param  logoId the ID of the company's logo
	 * @return Returns the company with the logo
	 */
	@Override
	public Company getCompanyByLogoId(long logoId) throws PortalException {
		return companyLocalService.getCompanyByLogoId(logoId);
	}

	/**
	 * Returns the company with the mail domian.
	 *
	 * @param  mx the company's mail domain
	 * @return Returns the company with the mail domain
	 */
	@Override
	public Company getCompanyByMx(String mx) throws PortalException {
		return companyLocalService.getCompanyByMx(mx);
	}

	/**
	 * Returns the company with the virtual host name.
	 *
	 * @param  virtualHost the company's virtual host name
	 * @return Returns the company with the virtual host name
	 */
	@Override
	public Company getCompanyByVirtualHost(String virtualHost)
		throws PortalException {

		return companyLocalService.getCompanyByVirtualHost(virtualHost);
	}

	/**
	 * Returns the company with the web domain.
	 *
	 * @param  webId the company's web domain
	 * @return Returns the company with the web domain
	 */
	@Override
	public Company getCompanyByWebId(String webId) throws PortalException {
		return companyLocalService.getCompanyByWebId(webId);
	}

	/**
	 * Removes the values that match the keys of the company's preferences.
	 *
	 * This method is called by {@link
	 * com.liferay.portlet.portalsettings.action.EditLDAPServerAction} remotely
	 * through {@link com.liferay.portal.kernel.service.CompanyService}.
	 *
	 * @param companyId the primary key of the company
	 * @param keys the company's preferences keys to be remove
	 */
	@Override
	public void removePreferences(long companyId, String[] keys)
		throws PortalException {

		if (!_roleLocalService.hasUserRole(
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
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHost, String mx, int maxUsers,
			boolean active)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
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
	 * @param  hasLogo if the company has a custom logo
	 * @param  logoBytes the new logo image data
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
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			boolean hasLogo, byte[] logoBytes, String name, String legalName,
			String legalId, String legalType, String sicCode,
			String tickerSymbol, String industry, String type, String size)
		throws PortalException {

		if (!_roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		return companyLocalService.updateCompany(
			companyId, virtualHost, mx, homeURL, hasLogo, logoBytes, name,
			legalName, legalId, legalType, sicCode, tickerSymbol, industry,
			type, size);
	}

	/**
	 * Updates the company with addition information.
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHost the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  homeURL the company's home URL (optionally <code>null</code>)
	 * @param  hasLogo if the company has a custom logo
	 * @param  logoBytes the new logo image data
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
	 * @param  unicodeProperties the company's properties
	 * @return the company with the primary key
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company updateCompany(
			long companyId, String virtualHost, String mx, String homeURL,
			boolean hasLogo, byte[] logoBytes, String name, String legalName,
			String legalId, String legalType, String sicCode,
			String tickerSymbol, String industry, String type, String size,
			String languageId, String timeZoneId, List<Address> addresses,
			List<EmailAddress> emailAddresses, List<Phone> phones,
			List<Website> websites, UnicodeProperties unicodeProperties)
		throws PortalException {

		PortletPreferences oldCompanyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId);

		Company company = updateCompany(
			companyId, virtualHost, mx, homeURL, hasLogo, logoBytes, name,
			legalName, legalId, legalType, sicCode, tickerSymbol, industry,
			type, size);

		updateDisplay(company.getCompanyId(), languageId, timeZoneId);

		updatePreferences(company.getCompanyId(), unicodeProperties);

		RatingsDataTransformerUtil.transformCompanyRatingsData(
			companyId, oldCompanyPortletPreferences, unicodeProperties);

		UsersAdminUtil.updateAddresses(
			Company.class.getName(), company.getCompanyId(), addresses);

		UsersAdminUtil.updateEmailAddresses(
			Company.class.getName(), company.getCompanyId(), emailAddresses);

		UsersAdminUtil.updatePhones(
			Company.class.getName(), company.getCompanyId(), phones);

		UsersAdminUtil.updateWebsites(
			Company.class.getName(), company.getCompanyId(), websites);

		return company;
	}

	/**
	 * Update the company's display.
	 *
	 * @param companyId the primary key of the company
	 * @param languageId the ID of the company's default user's language
	 * @param timeZoneId the ID of the company's default user's time zone
	 */
	@Override
	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException {

		if (!_roleLocalService.hasUserRole(
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
	 */
	@Override
	public Company updateLogo(long companyId, byte[] bytes)
		throws PortalException {

		if (!_roleLocalService.hasUserRole(
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
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public Company updateLogo(long companyId, InputStream inputStream)
		throws PortalException {

		if (!_roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		return companyLocalService.updateLogo(companyId, inputStream);
	}

	/**
	 * Updates the company's preferences. The company's default properties are
	 * found in portal.properties.
	 *
	 * @param companyId the primary key of the company
	 * @param unicodeProperties the company's properties. See {@link
	 *        UnicodeProperties}
	 */
	@Override
	public void updatePreferences(
			long companyId, UnicodeProperties unicodeProperties)
		throws PortalException {

		if (!(_roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true) ||
			  _roleLocalService.hasUserRole(
				  getUserId(), companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  true))) {

			throw new PrincipalException();
		}

		companyLocalService.updatePreferences(companyId, unicodeProperties);
	}

	/**
	 * Updates the company's security properties.
	 *
	 * @param companyId the primary key of the company
	 * @param authType the company's method of authenticating users
	 * @param autoLogin whether to allow users to select the "remember me"
	 *        feature
	 * @param sendPassword whether to allow users to ask the company to send
	 *        their passwords
	 * @param strangers whether to allow strangers to create accounts to
	 *        register themselves in the company
	 * @param strangersWithMx whether to allow strangers to create accounts with
	 *        email addresses that match the company mail suffix
	 * @param strangersVerify whether to require strangers who create accounts
	 *        to be verified via email
	 * @param siteLogo whether to to allow site administrators to use their own
	 *        logo instead of the enterprise logo
	 */
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void updateSecurity(
			long companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers, boolean strangersWithMx,
			boolean strangersVerify, boolean siteLogo)
		throws PortalException {

		if (!_roleLocalService.hasUserRole(
				getUserId(), companyId, RoleConstants.ADMINISTRATOR, true)) {

			throw new PrincipalException();
		}

		companyLocalService.updateSecurity(
			companyId, authType, autoLogin, sendPassword, strangers,
			strangersWithMx, strangersVerify, siteLogo);
	}

	@BeanReference(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

}