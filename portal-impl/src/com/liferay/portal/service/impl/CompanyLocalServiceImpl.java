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

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.CompanyWebIdException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.RequiredCompanyException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.base.CompanyLocalServiceBaseImpl;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

/**
 * Provides the local service for adding, checking, and updating companies. Each
 * company refers to a separate portal instance.
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class CompanyLocalServiceImpl extends CompanyLocalServiceBaseImpl {

	/**
	 * Adds a company.
	 *
	 * @param  webId the the company's web domain
	 * @param  virtualHostname the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  shardName the company's shard
	 * @param  system whether the company is the very first company (i.e., the
	 *         super company)
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @return the company
	 * @throws PortalException if the web domain, virtual host name, or mail
	 *         domain was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company addCompany(
			String webId, String virtualHostname, String mx, String shardName,
			boolean system, int maxUsers, boolean active)
		throws PortalException, SystemException {

		// Company

		virtualHostname = virtualHostname.trim().toLowerCase();

		if (Validator.isNull(webId) ||
			webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID) ||
			(companyPersistence.fetchByWebId(webId) != null)) {

			throw new CompanyWebIdException();
		}

		validate(webId, virtualHostname, mx);

		Company company = checkCompany(webId, mx, shardName);

		company.setMx(mx);
		company.setSystem(system);
		company.setMaxUsers(maxUsers);
		company.setActive(active);

		companyPersistence.update(company);

		// Virtual host

		updateVirtualHost(company.getCompanyId(), virtualHostname);

		return company;
	}

	/**
	 * Returns the company with the web domain.
	 *
	 * The method sets mail domain to the web domain, and the shard name to
	 * the default name set in portal.properties
	 *
	 * @param  webId the company's web domain
	 * @return the company with the web domain
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company checkCompany(String webId)
		throws PortalException, SystemException {

		String mx = webId;

		return companyLocalService.checkCompany(
			webId, mx, PropsValues.SHARD_DEFAULT_NAME);
	}

	/**
	 * Returns the company with the web domain, mail domain, and shard. If no
	 * such company exits, the method will create a new company.
	 *
	 * The method goes through a series of checks to ensure that the company
	 * contains default users, groups, etc.
	 *
	 * @param  webId the company's web domain
	 * @param  mx the company's mail domain
	 * @param  shardName the company's shard
	 * @return the company with the web domain, mail domain, and shard
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company checkCompany(String webId, String mx, String shardName)
		throws PortalException, SystemException {

		// Company

		Date now = new Date();

		Company company = companyPersistence.fetchByWebId(webId);

		if (company == null) {
			long companyId = counterLocalService.increment();

			company = companyPersistence.create(companyId);

			try {
				company.setKey(Base64.objectToString(Encryptor.generateKey()));
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}

			company.setWebId(webId);
			company.setMx(mx);
			company.setActive(true);

			companyPersistence.update(company);

			// Shard

			shardLocalService.addShard(
				Company.class.getName(), companyId, shardName);

			// Account

			String name = webId;

			if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
				name = PropsValues.COMPANY_DEFAULT_NAME;
			}

			String legalName = null;
			String legalId = null;
			String legalType = null;
			String sicCode = null;
			String tickerSymbol = null;
			String industry = null;
			String type = null;
			String size = null;

			updateAccount(
				company, name, legalName, legalId, legalType, sicCode,
				tickerSymbol, industry, type, size);

			// Virtual host

			if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
				updateVirtualHost(companyId, _DEFAULT_VIRTUAL_HOST);
			}

			// Demo settings

			if (webId.equals("liferay.net")) {
				company = companyPersistence.findByWebId(webId);

				updateVirtualHost(companyId, "demo.liferay.net");

				updateSecurity(
					companyId, CompanyConstants.AUTH_TYPE_EA, true, true, true,
					true, false, true);

				PortletPreferences preferences = PrefsPropsUtil.getPreferences(
					companyId);

				try {
					preferences.setValue(
						PropsKeys.ADMIN_EMAIL_FROM_NAME, "Liferay Demo");
					preferences.setValue(
						PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.net");

					preferences.store();
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}
			}
		}
		else {
			try {
				shardLocalService.getShard(
					Company.class.getName(), company.getCompanyId());
			}
			catch (NoSuchShardException nsse) {
				shardLocalService.addShard(
					Company.class.getName(), company.getCompanyId(), shardName);
			}
		}

		long companyId = company.getCompanyId();

		// Key

		checkCompanyKey(companyId);

		// Default user

		User defaultUser = userPersistence.fetchByC_DU(companyId, true);

		if (defaultUser != null) {
			if (!defaultUser.isAgreedToTermsOfUse()) {
				defaultUser.setAgreedToTermsOfUse(true);

				userPersistence.update(defaultUser);
			}
		}
		else {
			long userId = counterLocalService.increment();

			defaultUser = userPersistence.create(userId);

			defaultUser.setCompanyId(companyId);
			defaultUser.setCreateDate(now);
			defaultUser.setModifiedDate(now);
			defaultUser.setDefaultUser(true);
			defaultUser.setContactId(counterLocalService.increment());
			defaultUser.setPassword("password");
			defaultUser.setScreenName(String.valueOf(defaultUser.getUserId()));
			defaultUser.setEmailAddress("default@" + company.getMx());

			if (Validator.isNotNull(PropsValues.COMPANY_DEFAULT_LOCALE)) {
				defaultUser.setLanguageId(PropsValues.COMPANY_DEFAULT_LOCALE);
			}
			else {
				Locale locale = LocaleUtil.getDefault();

				defaultUser.setLanguageId(locale.toString());
			}

			if (Validator.isNotNull(PropsValues.COMPANY_DEFAULT_TIME_ZONE)) {
				defaultUser.setTimeZoneId(
					PropsValues.COMPANY_DEFAULT_TIME_ZONE);
			}
			else {
				TimeZone timeZone = TimeZoneUtil.getDefault();

				defaultUser.setTimeZoneId(timeZone.getID());
			}

			defaultUser.setGreeting(
				LanguageUtil.format(
					defaultUser.getLocale(), "welcome-x", StringPool.BLANK,
					false));
			defaultUser.setLoginDate(now);
			defaultUser.setFailedLoginAttempts(0);
			defaultUser.setAgreedToTermsOfUse(true);
			defaultUser.setStatus(WorkflowConstants.STATUS_APPROVED);

			userPersistence.update(defaultUser);

			// Contact

			Contact defaultContact = contactPersistence.create(
				defaultUser.getContactId());

			defaultContact.setCompanyId(defaultUser.getCompanyId());
			defaultContact.setUserId(defaultUser.getUserId());
			defaultContact.setUserName(StringPool.BLANK);
			defaultContact.setCreateDate(now);
			defaultContact.setModifiedDate(now);
			defaultContact.setClassName(User.class.getName());
			defaultContact.setClassPK(defaultUser.getUserId());
			defaultContact.setAccountId(company.getAccountId());
			defaultContact.setParentContactId(
				ContactConstants.DEFAULT_PARENT_CONTACT_ID);
			defaultContact.setEmailAddress(defaultUser.getEmailAddress());
			defaultContact.setFirstName(StringPool.BLANK);
			defaultContact.setMiddleName(StringPool.BLANK);
			defaultContact.setLastName(StringPool.BLANK);
			defaultContact.setMale(true);
			defaultContact.setBirthday(now);

			contactPersistence.update(defaultContact);
		}

		// System roles

		roleLocalService.checkSystemRoles(companyId);

		// System groups

		groupLocalService.checkSystemGroups(companyId);

		// Company group

		groupLocalService.checkCompanyGroup(companyId);

		// Default password policy

		passwordPolicyLocalService.checkDefaultPasswordPolicy(companyId);

		// Default user must have the Guest role

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		roleLocalService.setUserRoles(
			defaultUser.getUserId(), new long[] {guestRole.getRoleId()});

		// Default admin

		if (userPersistence.countByCompanyId(companyId) == 1) {
			String emailAddress =
				PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" + mx;

			userLocalService.addDefaultAdminUser(
				companyId, PropsValues.DEFAULT_ADMIN_SCREEN_NAME, emailAddress,
				defaultUser.getLocale(), PropsValues.DEFAULT_ADMIN_FIRST_NAME,
				PropsValues.DEFAULT_ADMIN_MIDDLE_NAME,
				PropsValues.DEFAULT_ADMIN_LAST_NAME);
		}

		// Portlets

		portletLocalService.checkPortlets(companyId);

		return company;
	}

	/**
	 * Checks if the company has an encryption key. It will create a key if one
	 * does not exist.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if a company with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void checkCompanyKey(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (Validator.isNull(company.getKey()) &&
			(company.getKeyObj() == null)) {

			try {
				company.setKey(Base64.objectToString(Encryptor.generateKey()));
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}

			companyPersistence.update(company);
		}
	}

	@Override
	public Company deleteCompany(long companyId)
		throws PortalException, SystemException {

		if (companyId == PortalInstances.getDefaultCompanyId()) {
			throw new RequiredCompanyException();
		}

		Long currentCompanyId = CompanyThreadLocal.getCompanyId();
		boolean deleteInProcess = CompanyThreadLocal.isDeleteInProcess();

		try {
			CompanyThreadLocal.setCompanyId(companyId);
			CompanyThreadLocal.setDeleteInProcess(true);

			return doDeleteCompany(companyId);
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
			CompanyThreadLocal.setDeleteInProcess(deleteInProcess);
		}
	}

	/**
	 * Deletes the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @throws PortalException if the company with the primary key could not be
	 *         found or if the company's logo could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteLogo(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		long logoId = company.getLogoId();

		if (logoId > 0) {
			company.setLogoId(0);

			companyPersistence.update(company);

			imageLocalService.deleteImage(logoId);
		}
	}

	/**
	 * Returns the company with the primary key.
	 *
	 * @param  companyId the primary key of the company
	 * @return the company with the primary key, <code>null</code> if a company
	 *         with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company fetchCompanyById(long companyId) throws SystemException {
		return companyPersistence.fetchByPrimaryKey(companyId);
	}

	/**
	 * Returns the company with the virtual host name.
	 *
	 * @param  virtualHostname the virtual host name
	 * @return the company with the virtual host name, <code>null</code> if a
	 *         company with the virtual host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company fetchCompanyByVirtualHost(String virtualHostname)
		throws SystemException {

		virtualHostname = virtualHostname.trim().toLowerCase();

		VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
			virtualHostname);

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() != 0)) {
			return null;
		}

		return companyPersistence.fetchByPrimaryKey(virtualHost.getCompanyId());
	}

	/**
	 * Returns all the companies.
	 *
	 * @return the companies
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Company> getCompanies() throws SystemException {
		return companyPersistence.findAll();
	}

	/**
	 * Returns all the companies used by WSRP.
	 *
	 * @param  system whether the company is the very first company (i.e., the
	 *         super company)
	 * @return the companies used by WSRP
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Company> getCompanies(boolean system) throws SystemException {
		return companyPersistence.findBySystem(system);
	}

	/**
	 * Returns the number of companies used by WSRP.
	 *
	 * @param  system whether the company is the very first company (i.e., the
	 *         super company)
	 * @return the number of companies used by WSRP
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getCompaniesCount(boolean system) throws SystemException {
		return companyPersistence.countBySystem(system);
	}

	/**
	 * Returns the company with the primary key.
	 *
	 * @param  companyId the primary key of the company
	 * @return the company with the primary key
	 * @throws PortalException if a company with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyById(long companyId)
		throws PortalException, SystemException {

		return companyPersistence.findByPrimaryKey(companyId);
	}

	/**
	 * Returns the company with the logo.
	 *
	 * @param  logoId the ID of the company's logo
	 * @return the company with the logo
	 * @throws PortalException if the company with the logo could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByLogoId(long logoId)
		throws PortalException, SystemException {

		return companyPersistence.findByLogoId(logoId);
	}

	/**
	 * Returns the company with the mail domain.
	 *
	 * @param  mx the company's mail domain
	 * @return the company with the mail domain
	 * @throws PortalException if the company with the mail domain could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByMx(String mx)
		throws PortalException, SystemException {

		return companyPersistence.findByMx(mx);
	}

	/**
	 * Returns the company with the virtual host name.
	 *
	 * @param  virtualHostname the company's virtual host name
	 * @return the company with the virtual host name
	 * @throws PortalException if the company with the virtual host name could
	 *         not be found or if the virtual host was not associated with a
	 *         company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByVirtualHost(String virtualHostname)
		throws PortalException, SystemException {

		try {
			virtualHostname = virtualHostname.trim().toLowerCase();

			VirtualHost virtualHost = virtualHostPersistence.findByHostname(
				virtualHostname);

			if (virtualHost.getLayoutSetId() != 0) {
				throw new CompanyVirtualHostException(
					"Virtual host is associated with layout set " +
						virtualHost.getLayoutSetId());
			}

			return companyPersistence.findByPrimaryKey(
				virtualHost.getCompanyId());
		}
		catch (NoSuchVirtualHostException nsvhe) {
			throw new CompanyVirtualHostException(nsvhe);
		}
	}

	/**
	 * Returns the company with the web domain.
	 *
	 * @param  webId the company's web domain
	 * @return the company with the web domain
	 * @throws PortalException if the company with the web domain could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company getCompanyByWebId(String webId)
		throws PortalException, SystemException {

		return companyPersistence.findByWebId(webId);
	}

	/**
	 * Returns the user's company.
	 *
	 * @param  userId the primary key of the user
	 * @return Returns the first company if there is only one company or the
	 *         user's company if there are more than one company; <code>0</code>
	 *         otherwise
	 * @throws Exception if a user with the primary key could not be found
	 */
	@Override
	public long getCompanyIdByUserId(long userId) throws Exception {
		long[] companyIds = PortalInstances.getCompanyIds();

		long companyId = 0;

		if (companyIds.length == 1) {
			companyId = companyIds[0];
		}
		else if (companyIds.length > 1) {
			try {
				User user = userPersistence.findByPrimaryKey(userId);

				companyId = user.getCompanyId();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get the company id for user " + userId, e);
				}
			}
		}

		return companyId;
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removePreferences(long companyId, String[] keys)
		throws SystemException {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			for (String key : keys) {
				preferences.reset(key);
			}

			preferences.store();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns an ordered range of all assets that match the keywords in the
	 * company.
	 *
	 * The method is called in {@link
	 * com.liferay.portal.search.PortalOpenSearchImpl} which is not longer used
	 * by the Search portlet.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  keywords the keywords (space separated),which may occur in assets
	 *         in the company (optionally <code>null</code>)
	 * @param  start the lower bound of the range of assets to return
	 * @param  end the upper bound of the range of assets to return (not
	 *         inclusive)
	 * @return the matching assets in the company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Hits search(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		return search(companyId, userId, null, 0, null, keywords, start, end);
	}

	/**
	 * Returns an ordered range of all assets that match the keywords in the
	 * portlet within the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  portletId the primary key of the portlet (optionally
	 *         <code>null</code>)
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  type the mime type of assets to return(optionally
	 *         <code>null</code>)
	 * @param  keywords the keywords (space separated), which may occur in any
	 *         assets in the portlet (optionally <code>null</code>)
	 * @param  start the lower bound of the range of assets to return
	 * @param  end the upper bound of the range of assets to return (not
	 *         inclusive)
	 * @return the matching assets in the portlet within the company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Hits search(
			long companyId, long userId, String portletId, long groupId,
			String type, String keywords, int start, int end)
		throws SystemException {

		try {

			// Search context

			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setEntryClassNames(
				SearchEngineUtil.getEntryClassNames());

			if (groupId > 0) {
				searchContext.setGroupIds(new long[] {groupId});
			}

			searchContext.setKeywords(keywords);

			if (Validator.isNotNull(portletId)) {
				searchContext.setPortletIds(new String[] {portletId});
			}

			searchContext.setStart(start);
			searchContext.setUserId(userId);

			// Always add facets as late as possible so that the search context
			// fields can be considered by the facets

			Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

			assetEntriesFacet.setStatic(true);

			searchContext.addFacet(assetEntriesFacet);

			Facet scopeFacet = new ScopeFacet(searchContext);

			scopeFacet.setStatic(true);

			searchContext.addFacet(scopeFacet);

			// Search

			Indexer indexer = FacetedSearcher.getInstance();

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Updates the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHostname the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @return the company with the primary key
	 * @throws PortalException if a company with primary key could not be found
	 *         or if the new information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHostname, String mx, int maxUsers,
			boolean active)
		throws PortalException, SystemException {

		// Company

		virtualHostname = virtualHostname.trim().toLowerCase();

		if (!active) {
			if (companyId == PortalInstances.getDefaultCompanyId()) {
				active = true;
			}
		}

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validate(company.getWebId(), virtualHostname, mx);

		if (PropsValues.MAIL_MX_UPDATE) {
			company.setMx(mx);
		}

		company.setMaxUsers(maxUsers);
		company.setActive(active);

		companyPersistence.update(company);

		// Virtual host

		updateVirtualHost(companyId, virtualHostname);

		return company;
	}

	/**
	 * Update the company with additional account information.
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHostname the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  homeURL the company's home URL (optionally <code>null</code>)
	 * @param  name the company's account name(optionally <code>null</code>)
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
	 * @param  industry the company's account industry (optionally
	 *         <code>null</code>)
	 * @param  type the company's account type (optionally <code>null</code>)
	 * @param  size the company's account size (optionally <code>null</code>)
	 * @return the company with the primary key
	 * @throws PortalException if a company with the primary key could not be
	 *         found or if the new information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHostname, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		// Company

		virtualHostname = virtualHostname.trim().toLowerCase();

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validate(company.getWebId(), virtualHostname, mx);
		validate(companyId, name);

		if (PropsValues.MAIL_MX_UPDATE) {
			company.setMx(mx);
		}

		company.setHomeURL(homeURL);

		companyPersistence.update(company);

		// Account

		updateAccount(
			company, name, legalName, legalId, legalType, sicCode, tickerSymbol,
			industry, type, size);

		// Virtual host

		updateVirtualHost(companyId, virtualHostname);

		return company;
	}

	/**
	 * Update the company's display.
	 *
	 * @param  companyId the primary key of the company
	 * @param  languageId the ID of the company's default user's language
	 * @param  timeZoneId the ID of the company's default user's time zone
	 * @throws PortalException if the company's default user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		User user = userLocalService.getDefaultUser(companyId);

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);

		userPersistence.update(user);
	}

	/**
	 * Updates the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  bytes the bytes of the company's logo image
	 * @return the company with the primary key
	 * @throws PortalException if the company's logo ID could not be found or if
	 *         the logo's image was corrupted
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateLogo(long companyId, byte[] bytes)
		throws PortalException, SystemException {

		Company company = checkLogo(companyId);

		imageLocalService.updateImage(company.getLogoId(), bytes);

		return company;
	}

	/**
	 * Updates the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  file the file of the company's logo image
	 * @return the company with the primary key
	 * @throws PortalException the company's logo ID could not be found or if
	 *         the logo's image was corrupted
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateLogo(long companyId, File file)
		throws PortalException, SystemException {

		Company company = checkLogo(companyId);

		imageLocalService.updateImage(company.getLogoId(), file);

		return company;
	}

	/**
	 * Update the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  is the input stream of the company's logo image
	 * @return the company with the primary key
	 * @throws PortalException if the company's logo ID could not be found or if
	 *         the company's logo image was corrupted
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Company updateLogo(long companyId, InputStream is)
		throws PortalException, SystemException {

		Company company = checkLogo(companyId);

		imageLocalService.updateImage(company.getLogoId(), is);

		return company;
	}

	/**
	 * Updates the company's preferences. The company's default properties are
	 * found in portal.properties.
	 *
	 * @param  companyId the primary key of the company
	 * @param  properties the company's properties. See {@link
	 *         com.liferay.portal.kernel.util.UnicodeProperties}
	 * @throws PortalException if the properties contained new locales that were
	 *         not supported
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updatePreferences(long companyId, UnicodeProperties properties)
		throws PortalException, SystemException {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			String newLocales = properties.getProperty(PropsKeys.LOCALES);

			if (newLocales != null) {
				String oldLocales = preferences.getValue(
					PropsKeys.LOCALES, StringPool.BLANK);

				if (!Validator.equals(oldLocales, newLocales)) {
					validateLocales(newLocales);

					LanguageUtil.resetAvailableLocales(companyId);
				}
			}

			List<String> resetKeys = new ArrayList<String>();

			for (Map.Entry<String, String> entry : properties.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				if (value.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
					continue;
				}

				String propsUtilValue = PropsUtil.get(key);

				if (!value.equals(propsUtilValue)) {
					preferences.setValue(key, value);
				}
				else {
					String preferencesValue = preferences.getValue(key, null);

					if (preferencesValue != null) {
						resetKeys.add(key);
					}
				}
			}

			for (String key : resetKeys) {
				preferences.reset(key);
			}

			preferences.store();
		}
		catch (LocaleException le) {
			throw le;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Updates the company's security properties.
	 *
	 * @param  companyId the primary key of the company
	 * @param  authType the company's method of authenticating users
	 * @param  autoLogin whether to allow users to select the "remember me"
	 *         feature
	 * @param  sendPassword whether to allow users to ask the company to send
	 *         their password
	 * @param  strangers whether to allow strangers to create accounts register
	 *         themselves in the company
	 * @param  strangersWithMx whether to allow strangers to create accounts
	 *         with email addresses that match the company mail suffix
	 * @param  strangersVerify whether to require strangers who create accounts
	 *         to be verified via email
	 * @param  siteLogo whether to allow site administrators to use their own
	 *         logo instead of the enterprise logo
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateSecurity(
			long companyId, String authType, boolean autoLogin,
			boolean sendPassword, boolean strangers, boolean strangersWithMx,
			boolean strangersVerify, boolean siteLogo)
		throws SystemException {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_AUTH_TYPE, authType);
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
				String.valueOf(autoLogin));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_SEND_PASSWORD,
				String.valueOf(sendPassword));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_STRANGERS,
				String.valueOf(strangers));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
				String.valueOf(strangersWithMx));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY,
				String.valueOf(strangersVerify));
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_SITE_LOGO, String.valueOf(siteLogo));

			preferences.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortletException pe) {
			throw new SystemException(pe);
		}
	}

	protected Company checkLogo(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		long logoId = company.getLogoId();

		if (logoId <= 0) {
			logoId = counterLocalService.increment();

			company.setLogoId(logoId);

			company = companyPersistence.update(company);
		}

		return company;
	}

	protected Company doDeleteCompany(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		company.setActive(false);

		companyPersistence.update(company);

		return company;
	}

	protected void updateAccount(
			Company company, String name, String legalName, String legalId,
			String legalType, String sicCode, String tickerSymbol,
			String industry, String type, String size)
		throws SystemException {

		Date now = new Date();

		Account account = accountPersistence.fetchByPrimaryKey(
			company.getAccountId());

		if (account == null) {
			long accountId = counterLocalService.increment();

			account = accountPersistence.create(accountId);

			account.setCompanyId(company.getCompanyId());
			account.setCreateDate(now);
			account.setUserId(0);
			account.setUserName(StringPool.BLANK);

			company.setAccountId(accountId);

			companyPersistence.update(company);
		}

		account.setModifiedDate(now);
		account.setName(name);
		account.setLegalName(legalName);
		account.setLegalId(legalId);
		account.setLegalType(legalType);
		account.setSicCode(sicCode);
		account.setTickerSymbol(tickerSymbol);
		account.setIndustry(industry);
		account.setType(type);
		account.setSize(size);

		accountPersistence.update(account);
	}

	protected void updateVirtualHost(long companyId, String virtualHostname)
		throws CompanyVirtualHostException, SystemException {

		if (Validator.isNotNull(virtualHostname)) {
			VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
				virtualHostname);

			if (virtualHost == null) {
				virtualHostLocalService.updateVirtualHost(
					companyId, 0, virtualHostname);
			}
			else {
				if ((virtualHost.getCompanyId() != companyId) ||
					(virtualHost.getLayoutSetId() != 0)) {

					throw new CompanyVirtualHostException();
				}
			}
		}
		else {
			VirtualHost virtualHost = virtualHostPersistence.fetchByC_L(
				companyId, 0);

			if (virtualHost != null) {
				virtualHostPersistence.remove(virtualHost);
			}
		}
	}

	protected void validate(long companyId, String name)
		throws PortalException, SystemException {

		Group group = groupLocalService.fetchGroup(companyId, name);

		if ((group != null) || Validator.isNull(name)) {
			throw new AccountNameException();
		}
	}

	protected void validate(String webId, String virtualHostname, String mx)
		throws PortalException, SystemException {

		if (Validator.isNull(virtualHostname)) {
			throw new CompanyVirtualHostException();
		}
		else if (virtualHostname.equals(_DEFAULT_VIRTUAL_HOST) &&
				 !webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			throw new CompanyVirtualHostException();
		}
		else if (!Validator.isDomain(virtualHostname)) {
			throw new CompanyVirtualHostException();
		}
		else {
			try {
				VirtualHost virtualHost = virtualHostPersistence.findByHostname(
					virtualHostname);

				long companyId = virtualHost.getCompanyId();

				Company virtualHostnameCompany =
					companyPersistence.findByPrimaryKey(companyId);

				if (!virtualHostnameCompany.getWebId().equals(webId)) {
					throw new CompanyVirtualHostException();
				}
			}
			catch (NoSuchVirtualHostException nsvhe) {
			}
		}

		if (Validator.isNull(mx)) {
			throw new CompanyMxException();
		}
		else if (!Validator.isDomain(mx)) {
			throw new CompanyMxException();
		}
	}

	protected void validateLocales(String locales) throws PortalException {
		String[] localesArray = StringUtil.split(locales, StringPool.COMMA);

		for (String locale : localesArray) {
			if (!ArrayUtil.contains(PropsValues.LOCALES, locale)) {
				throw new LocaleException();
			}
		}
	}

	private static final String _DEFAULT_VIRTUAL_HOST = "localhost";

	private static Log _log = LogFactoryUtil.getLog(
		CompanyLocalServiceImpl.class);

}