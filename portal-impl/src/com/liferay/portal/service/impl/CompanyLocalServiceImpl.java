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

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.CompanyWebIdException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.ServiceContext;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class CompanyLocalServiceImpl extends CompanyLocalServiceBaseImpl {

	public Company addCompany(
			String webId, String virtualHostname, String mx, String shardName,
			boolean system, int maxUsers, boolean active)
		throws PortalException, SystemException {

		// Company

		virtualHostname = virtualHostname.trim().toLowerCase();

		if ((Validator.isNull(webId)) ||
			(webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) ||
			(companyPersistence.fetchByWebId(webId) != null)) {

			throw new CompanyWebIdException();
		}

		validate(webId, virtualHostname, mx);

		Company company = checkCompany(webId, mx, shardName);

		company.setMx(mx);
		company.setSystem(system);
		company.setMaxUsers(maxUsers);
		company.setActive(active);

		companyPersistence.update(company, false);

		// Virtual host

		updateVirtualHost(company.getCompanyId(), virtualHostname);

		return company;
	}

	public Company checkCompany(String webId)
		throws PortalException, SystemException {

		String mx = webId;

		return companyLocalService.checkCompany(
			webId, mx, PropsValues.SHARD_DEFAULT_NAME);
	}

	public Company checkCompany(String webId, String mx, String shardName)
		throws PortalException, SystemException {

		// Company

		Date now = new Date();

		Company company = companyPersistence.fetchByWebId(webId);

		if (company == null) {
			String virtualHostname = webId;

			if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
				virtualHostname = _DEFAULT_VIRTUAL_HOST;
			}

			String homeURL = null;
			String name = webId;
			String legalName = null;
			String legalId = null;
			String legalType = null;
			String sicCode = null;
			String tickerSymbol = null;
			String industry = null;
			String type = null;
			String size = null;

			long companyId = counterLocalService.increment();

			company = companyPersistence.create(companyId);

			try {
				company.setKeyObj(Encryptor.generateKey());
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}

			company.setWebId(webId);
			company.setMx(mx);
			company.setActive(true);

			companyPersistence.update(company, false);

			// Shard

			shardLocalService.addShard(
				Company.class.getName(), companyId, shardName);

			// Company

			updateCompany(
				companyId, virtualHostname, mx, homeURL, name, legalName,
				legalId, legalType, sicCode, tickerSymbol, industry, type,
				size);

			// Virtual host

			updateVirtualHost(companyId, virtualHostname);

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

		User defaultUser = null;

		try {
			defaultUser = userLocalService.getDefaultUser(companyId);

			if (!defaultUser.isAgreedToTermsOfUse()) {
				defaultUser.setAgreedToTermsOfUse(true);

				userPersistence.update(defaultUser, false);
			}
		}
		catch (NoSuchUserException nsue) {
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
			defaultUser.setLanguageId(LocaleUtil.getDefault().toString());
			defaultUser.setTimeZoneId(TimeZoneUtil.getDefault().getID());
			defaultUser.setGreeting(
				LanguageUtil.format(
					defaultUser.getLocale(), "welcome-x", StringPool.BLANK,
					false));
			defaultUser.setLoginDate(now);
			defaultUser.setFailedLoginAttempts(0);
			defaultUser.setAgreedToTermsOfUse(true);
			defaultUser.setStatus(WorkflowConstants.STATUS_APPROVED);

			userPersistence.update(defaultUser, false);

			// Contact

			Contact defaultContact = contactPersistence.create(
				defaultUser.getContactId());

			defaultContact.setCompanyId(defaultUser.getCompanyId());
			defaultContact.setUserId(defaultUser.getUserId());
			defaultContact.setUserName(StringPool.BLANK);
			defaultContact.setCreateDate(now);
			defaultContact.setModifiedDate(now);
			defaultContact.setAccountId(company.getAccountId());
			defaultContact.setParentContactId(
				ContactConstants.DEFAULT_PARENT_CONTACT_ID);
			defaultContact.setFirstName(StringPool.BLANK);
			defaultContact.setMiddleName(StringPool.BLANK);
			defaultContact.setLastName(StringPool.BLANK);
			defaultContact.setMale(true);
			defaultContact.setBirthday(now);

			contactPersistence.update(defaultContact, false);
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
			long creatorUserId = 0;
			boolean autoPassword = false;
			String password1 = PropsValues.DEFAULT_ADMIN_PASSWORD;
			String password2 = password1;
			boolean autoScreenName = false;
			String screenName = PropsValues.DEFAULT_ADMIN_SCREEN_NAME;
			String emailAddress =
				PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" + mx;
			long facebookId = 0;
			String openId = StringPool.BLANK;
			Locale locale = defaultUser.getLocale();
			String firstName = PropsValues.DEFAULT_ADMIN_FIRST_NAME;
			String middleName = PropsValues.DEFAULT_ADMIN_MIDDLE_NAME;
			String lastName = PropsValues.DEFAULT_ADMIN_LAST_NAME;
			int prefixId = 0;
			int suffixId = 0;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = StringPool.BLANK;

			Group guestGroup = groupLocalService.getGroup(
				companyId, GroupConstants.GUEST);

			long[] groupIds = new long[] {guestGroup.getGroupId()};

			long[] organizationIds = null;

			Role adminRole = roleLocalService.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			Role powerUserRole = roleLocalService.getRole(
				companyId, RoleConstants.POWER_USER);

			long[] roleIds = new long[] {
				adminRole.getRoleId(), powerUserRole.getRoleId()
			};

			long[] userGroupIds = null;
			boolean sendEmail = false;
			ServiceContext serviceContext = new ServiceContext();

			User defaultAdminUser = userLocalService.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);

			userLocalService.updateEmailAddressVerified(
				defaultAdminUser.getUserId(), true);

			userLocalService.updateLastLogin(
				defaultAdminUser.getUserId(), defaultAdminUser.getLoginIP());

			userLocalService.updatePasswordReset(
				defaultAdminUser.getUserId(), false);
		}

		// Portlets

		portletLocalService.checkPortlets(companyId);

		return company;
	}

	public void checkCompanyKey(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if ((Validator.isNull(company.getKey())) &&
			(company.getKeyObj() == null)) {

			try {
				company.setKeyObj(Encryptor.generateKey());
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}

			companyPersistence.update(company, false);
		}
	}

	public void deleteLogo(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		long logoId = company.getLogoId();

		if (logoId > 0) {
			company.setLogoId(0);

			companyPersistence.update(company, false);

			imageLocalService.deleteImage(logoId);
		}
	}

	public Company fetchCompany(long companyId) throws SystemException {
		return companyPersistence.fetchByPrimaryKey(companyId);
	}

	public Company fetchCompanyByVirtualHost(String virtualHostname)
		throws SystemException {

		virtualHostname = virtualHostname.trim().toLowerCase();

		VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
			virtualHostname);

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() != 0)) {
			return null;
		}

		return companyPersistence.fetchByPrimaryKey(
			virtualHost.getCompanyId());
	}

	public List<Company> getCompanies() throws SystemException {
		return companyPersistence.findAll();
	}

	public List<Company> getCompanies(boolean system) throws SystemException {
		return companyPersistence.findBySystem(system);
	}

	public int getCompaniesCount(boolean system) throws SystemException {
		return companyPersistence.countBySystem(system);
	}

	public Company getCompanyById(long companyId)
		throws PortalException, SystemException {

		return companyPersistence.findByPrimaryKey(companyId);
	}

	public Company getCompanyByLogoId(long logoId)
		throws PortalException, SystemException {

		return companyPersistence.findByLogoId(logoId);
	}

	public Company getCompanyByMx(String mx)
		throws PortalException, SystemException {

		return companyPersistence.findByMx(mx);
	}

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

	public Company getCompanyByWebId(String webId)
		throws PortalException, SystemException {

		return companyPersistence.findByWebId(webId);
	}

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

	public Hits search(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		return search(companyId, userId, null, 0, null, keywords, start, end);
	}

	public Hits search(
			long companyId, long userId, String portletId, long groupId,
			String type, String keywords, int start, int end)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			searchContext.setSearchEngineId(SearchEngineUtil.SYSTEM_ENGINE_ID);

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			contextQuery.addRequiredTerm(Field.COMPANY_ID, companyId);

			if (Validator.isNotNull(portletId)) {
				contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);
			}

			if (groupId > 0) {
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if (Validator.isNotNull(type)) {
				contextQuery.addRequiredTerm(Field.TYPE, type);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			searchQuery.addTerms(_KEYWORDS_FIELDS, keywords);

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, new long[] {groupId}, userId, null, fullQuery, start,
				end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

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

		companyPersistence.update(company, false);

		// Virtual host

		updateVirtualHost(companyId, virtualHostname);

		return company;
	}

	public Company updateCompany(
			long companyId, String virtualHostname, String mx, String homeURL,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		// Company

		virtualHostname = virtualHostname.trim().toLowerCase();
		Date now = new Date();

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validate(company.getWebId(), virtualHostname, mx);
		validate(name);

		if (PropsValues.MAIL_MX_UPDATE) {
			company.setMx(mx);
		}

		company.setHomeURL(homeURL);

		companyPersistence.update(company, false);

		// Account

		Account account = accountPersistence.fetchByPrimaryKey(
			company.getAccountId());

		if (account == null) {
			long accountId = counterLocalService.increment();

			account = accountPersistence.create(accountId);

			account.setCreateDate(now);
			account.setCompanyId(companyId);
			account.setUserId(0);
			account.setUserName(StringPool.BLANK);

			company.setAccountId(accountId);

			companyPersistence.update(company, false);
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

		accountPersistence.update(account, false);

		// Virtual host

		updateVirtualHost(companyId, virtualHostname);

		return company;
	}

	public void updateDisplay(
			long companyId, String languageId, String timeZoneId)
		throws PortalException, SystemException {

		User user = userLocalService.getDefaultUser(companyId);

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);

		userPersistence.update(user, false);
	}

	public void updateLogo(long companyId, byte[] bytes)
		throws PortalException, SystemException {

		long logoId = getLogoId(companyId);

		imageLocalService.updateImage(logoId, bytes);
	}

	public void updateLogo(long companyId, File file)
		throws PortalException, SystemException {

		long logoId = getLogoId(companyId);

		imageLocalService.updateImage(logoId, file);
	}

	public void updateLogo(long companyId, InputStream is)
		throws PortalException, SystemException {

		long logoId = getLogoId(companyId);

		imageLocalService.updateImage(logoId, is);
	}

	public void updatePreferences(long companyId, UnicodeProperties properties)
		throws SystemException {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			String newLocales = properties.getProperty(PropsKeys.LOCALES);

			if (newLocales != null) {
				String oldLocales = preferences.getValue(
					PropsKeys.LOCALES, StringPool.BLANK);

				if (!Validator.equals(oldLocales, newLocales)) {
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
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

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
				PropsKeys.COMPANY_SECURITY_SITE_LOGO,
				String.valueOf(siteLogo));

			preferences.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortletException pe) {
			throw new SystemException(pe);
		}
	}

	protected long getLogoId(long companyId)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		long logoId = company.getLogoId();

		if (logoId <= 0) {
			logoId = counterLocalService.increment();

			company.setLogoId(logoId);

			companyPersistence.update(company, false);
		}

		return logoId;
	}

	protected void updateVirtualHost(long companyId, String virtualHostname)
		throws CompanyVirtualHostException, SystemException {

		if (Validator.isNotNull(virtualHostname)) {
			try {
				VirtualHost virtualHost = virtualHostPersistence.findByHostname(
					virtualHostname);

				if ((virtualHost.getCompanyId() != companyId) ||
					(virtualHost.getLayoutSetId() != 0)) {

					throw new CompanyVirtualHostException();
				}
			}
			catch (NoSuchVirtualHostException nsvhe) {
				virtualHostLocalService.updateVirtualHost(
					companyId, 0, virtualHostname);
			}
		}
		else {
			try {
				virtualHostPersistence.removeByC_L(companyId, 0);
			}
			catch (NoSuchVirtualHostException nsvhe) {
			}
		}
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
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

	private static final String _DEFAULT_VIRTUAL_HOST = "localhost";

	private static final String[] _KEYWORDS_FIELDS = {
		Field.ASSET_TAG_NAMES, Field.CONTENT, Field.DESCRIPTION,
		Field.PROPERTIES, Field.TITLE
	};

}