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

import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.encryptor.EncryptorException;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyNameException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredCompanyException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.instance.lifecycle.PortalInstanceLifecycleManager;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManagerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.service.persistence.CompanyInfoPersistence;
import com.liferay.portal.kernel.service.persistence.ContactPersistence;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesPersistence;
import com.liferay.portal.kernel.service.persistence.PortletPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.VirtualHostPersistence;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;
import com.liferay.portal.service.base.CompanyLocalServiceBaseImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.IDN;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Provides the local service for adding, checking, and updating companies. Each
 * company refers to a separate portal instance.
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class CompanyLocalServiceImpl extends CompanyLocalServiceBaseImpl {

	public CompanyLocalServiceImpl() {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, PortalInstanceLifecycleManager.class,
			new PortalInstanceLifecycleManagerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public Company addCompany(Company company) {
		_companyInfoPersistence.update(company.getCompanyInfo());

		return super.addCompany(company);
	}

	/**
	 * Adds a company with the primary key.
	 *
	 * @param  companyId the primary key of the company (optionally <code>null</code> or
	 *         <code>0</code> to generate a key automatically)
	 * @param  webId the the company's web domain
	 * @param  virtualHostname the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @return the company
	 */
	@Override
	public Company addCompany(
			Long companyId, String webId, String virtualHostname, String mx,
			int maxUsers, boolean active)
		throws PortalException {

		return addCompany(
			companyId, webId, virtualHostname, mx, maxUsers, active, null, null,
			null, null, null);
	}

	/**
	 * Adds a company with the primary key.
	 *
	 * @param  companyId the primary key of the company (optionally <code>null</code> or
	 *         <code>0</code> to generate a key automatically)
	 * @param  webId the the company's web domain
	 * @param  virtualHostname the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  maxUsers the max number of company users (optionally
	 *         <code>0</code>)
	 * @param  active whether the company is active
	 * @param  defaultAdminPassword Password set to the admin user of the company
	 * @param  defaultAdminEmailAddress Email set to the admin user of the company
	 * @return the company
	 */
	public Company addCompany(
			Long companyId, String webId, String virtualHostname, String mx,
			int maxUsers, boolean active, String defaultAdminPassword,
			String defaultAdminScreenName, String defaultAdminEmailAddress,
			String defaultAdminFirstName, String defaultAdminLastName)
		throws PortalException {

		// Company

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		validateWebId(webId);
		validateVirtualHost(webId, virtualHostname);
		validateMx(-1, mx);

		if ((companyId == null) || (companyId == 0)) {
			companyId = counterLocalService.increment();
		}

		Company company = companyPersistence.create(companyId);

		company.setUserId(0);
		company.setUserName(StringPool.BLANK);
		company.setCreateDate(new Date());
		company.setModifiedDate(new Date());

		if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			DBPartitionUtil.setDefaultCompanyId(company.getCompanyId());
		}

		boolean newDBPartitionAdded = DBPartitionUtil.addDBPartition(
			company.getCompanyId());

		SafeCloseable safeCloseable =
			CompanyThreadLocal.setInitializingCompanyIdWithSafeCloseable(
				company.getCompanyId());

		try {
			company.setWebId(webId);
			company.setMx(mx);
			company.setMaxUsers(maxUsers);
			company.setActive(active);

			company = companyPersistence.update(company);

			// Virtual host

			updateVirtualHostname(company.getCompanyId(), virtualHostname);

			if (newDBPartitionAdded) {
				_dlFileEntryTypeLocalService.
					createBasicDocumentDLFileEntryType();
			}

			String name = webId;

			if (webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
				name = PropsValues.COMPANY_DEFAULT_NAME;
			}

			company.setName(name);

			// Company info

			try {
				company.setKey(
					EncryptorUtil.serializeKey(EncryptorUtil.generateKey()));
			}
			catch (EncryptorException encryptorException) {
				throw new SystemException(encryptorException);
			}

			_companyInfoPersistence.update(company.getCompanyInfo());

			// Demo settings

			if (webId.equals("liferay.net")) {
				_addDemoSettings(company);
			}

			_addGuestUser(company);

			company = _checkCompany(
				company, mx, defaultAdminPassword, defaultAdminScreenName,
				defaultAdminEmailAddress, defaultAdminFirstName,
				defaultAdminLastName);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					safeCloseable.close();

					return null;
				});

			return company;
		}
		catch (Exception exception) {
			safeCloseable.close();

			if (newDBPartitionAdded) {
				DBPartitionUtil.removeDBPartition(companyId);
			}

			throw exception;
		}
	}

	/**
	 * Adds a company.
	 *
	 * @param      webId the the company's web domain
	 * @param      virtualHostname the company's virtual host name
	 * @param      mx the company's mail domain
	 * @param      maxUsers the max number of company users (optionally
	 *             <code>0</code>)
	 * @param      active whether the company is active
	 * @return     the company
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addCompany(Long, String, String, String, boolean, int,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public Company addCompany(
			String webId, String virtualHostname, String mx, int maxUsers,
			boolean active)
		throws PortalException {

		return addCompany(null, webId, virtualHostname, mx, maxUsers, active);
	}

	/**
	 * Returns the company with the web domain.
	 *
	 * The method sets mail domain to the web domain to the default name set in
	 * portal.properties
	 *
	 * @param  webId the company's web domain
	 * @return the company with the web domain
	 */
	@Override
	public Company checkCompany(String webId) throws PortalException {
		String mx = webId;

		return checkCompany(webId, mx);
	}

	/**
	 * Returns the company with the web domain and mail domain.
	 *
	 * The method goes through a series of checks to ensure that the company
	 * contains default users, groups, etc.
	 *
	 * @param  webId the company's web domain
	 * @param  mx the company's mail domain
	 * @return the company with the web domain and mail domain
	 */
	@Override
	public Company checkCompany(String webId, String mx)
		throws PortalException {

		Company company = getCompanyByWebId(webId);

		return _checkCompany(company, mx, null, null, null, null, null);
	}

	/**
	 * Checks if the company has an encryption key. It will create a key if one
	 * does not exist.
	 *
	 * @param companyId the primary key of the company
	 */
	@Override
	public void checkCompanyKey(long companyId) throws PortalException {
		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (company.getKeyObj() != null) {
			return;
		}

		try {
			company.setKey(
				EncryptorUtil.serializeKey(EncryptorUtil.generateKey()));
		}
		catch (EncryptorException encryptorException) {
			throw new SystemException(encryptorException);
		}

		_companyInfoPersistence.update(company.getCompanyInfo());
	}

	@Override
	public Company deleteCompany(Company company) throws PortalException {
		return deleteCompany(company.getCompanyId());
	}

	@Override
	public Company deleteCompany(long companyId) throws PortalException {
		if (companyId == PortalInstances.getDefaultCompanyId()) {
			throw new RequiredCompanyException(
				"Select another default company before deleting company " +
					companyId);
		}

		try (SafeCloseable safeCloseable1 =
				CompanyThreadLocal.setWithSafeCloseable(companyId);
			SafeCloseable safeCloseable2 =
				PortalInstances.setCompanyInDeletionProcess(companyId)) {

			return doDeleteCompany(companyId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw portalException;
		}
	}

	/**
	 * Deletes the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @return the deleted logo's company
	 */
	@Override
	public Company deleteLogo(long companyId) throws PortalException {
		Company company = companyPersistence.findByPrimaryKey(companyId);

		PortalUtil.updateImageId(company, false, null, "logoId", 0, 0, 0);

		return company;
	}

	/**
	 * Returns the company with the primary key.
	 *
	 * @param  companyId the primary key of the company
	 * @return the company with the primary key, <code>null</code> if a company
	 *         with the primary key could not be found
	 */
	@Override
	public Company fetchCompanyById(long companyId) {
		return companyPersistence.fetchByPrimaryKey(companyId);
	}

	/**
	 * Returns the company with the virtual host name.
	 *
	 * @param  virtualHostname the virtual host name
	 * @return the company with the virtual host name, <code>null</code> if a
	 *         company with the virtual host could not be found
	 */
	@Override
	public Company fetchCompanyByVirtualHost(String virtualHostname) {
		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		VirtualHost virtualHost = _virtualHostPersistence.fetchByHostname(
			virtualHostname);

		if ((virtualHost == null) && virtualHostname.contains("xn--")) {
			virtualHost = _virtualHostPersistence.fetchByHostname(
				IDN.toUnicode(virtualHostname));
		}

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() != 0)) {
			return null;
		}

		return companyPersistence.fetchByPrimaryKey(virtualHost.getCompanyId());
	}

	@Override
	@Transactional(enabled = false)
	public <E extends Exception> void forEachCompany(
			UnsafeConsumer<Company, E> unsafeConsumer)
		throws E {

		List<Company> companies = null;

		if (!CompanyThreadLocal.isLocked()) {
			companies = companyLocalService.getCompanies();
		}

		forEachCompany(unsafeConsumer, companies);
	}

	@Override
	@Transactional(enabled = false)
	public <E extends Exception> void forEachCompany(
			UnsafeConsumer<Company, E> unsafeConsumer, List<Company> companies)
		throws E {

		if (CompanyThreadLocal.isLocked()) {
			unsafeConsumer.accept(
				companyLocalService.fetchCompanyById(
					CompanyThreadLocal.getCompanyId()));

			return;
		}

		for (Company company : companies) {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(
						company.getCompanyId())) {

				unsafeConsumer.accept(company);
			}
		}
	}

	@Override
	@Transactional(enabled = false)
	public <E extends Exception> void forEachCompanyId(
			UnsafeConsumer<Long, E> unsafeConsumer)
		throws E {

		long[] companyIds = null;

		if (!CompanyThreadLocal.isLocked()) {
			companyIds = ListUtil.toLongArray(
				companyLocalService.getCompanies(), Company::getCompanyId);
		}

		forEachCompanyId(unsafeConsumer, companyIds);
	}

	@Override
	@Transactional(enabled = false)
	public <E extends Exception> void forEachCompanyId(
			UnsafeConsumer<Long, E> unsafeConsumer, long[] companyIds)
		throws E {

		if (CompanyThreadLocal.isLocked()) {
			unsafeConsumer.accept(CompanyThreadLocal.getCompanyId());

			return;
		}

		for (long companyId : companyIds) {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(companyId)) {

				unsafeConsumer.accept(companyId);
			}
		}
	}

	/**
	 * Returns all the companies.
	 *
	 * @return the companies
	 */
	@Override
	public List<Company> getCompanies() {
		return companyPersistence.findAll();
	}

	/**
	 * Returns the company with the primary key.
	 *
	 * @param  companyId the primary key of the company
	 * @return the company with the primary key
	 */
	@Override
	public Company getCompanyById(long companyId) throws PortalException {
		return companyPersistence.findByPrimaryKey(companyId);
	}

	/**
	 * Returns the company with the logo.
	 *
	 * @param  logoId the ID of the company's logo
	 * @return the company with the logo
	 */
	@Override
	public Company getCompanyByLogoId(long logoId) throws PortalException {
		return companyPersistence.findByLogoId(logoId);
	}

	/**
	 * Returns the company with the mail domain.
	 *
	 * @param  mx the company's mail domain
	 * @return the company with the mail domain
	 */
	@Override
	public Company getCompanyByMx(String mx) throws PortalException {
		return companyPersistence.findByMx(mx);
	}

	/**
	 * Returns the company with the virtual host name.
	 *
	 * @param  virtualHostname the company's virtual host name
	 * @return the company with the virtual host name
	 */
	@Override
	public Company getCompanyByVirtualHost(String virtualHostname)
		throws PortalException {

		try {
			virtualHostname = StringUtil.toLowerCase(
				StringUtil.trim(virtualHostname));

			VirtualHost virtualHost = _virtualHostLocalService.fetchVirtualHost(
				virtualHostname);

			if ((virtualHost == null) && virtualHostname.contains("xn--")) {
				virtualHost = _virtualHostPersistence.findByHostname(
					IDN.toUnicode(virtualHostname));
			}

			if (virtualHost.getLayoutSetId() != 0) {
				throw new CompanyVirtualHostException(
					"Virtual host is associated with layout set " +
						virtualHost.getLayoutSetId());
			}

			return companyPersistence.findByPrimaryKey(
				virtualHost.getCompanyId());
		}
		catch (NoSuchVirtualHostException noSuchVirtualHostException) {
			throw new CompanyVirtualHostException(noSuchVirtualHostException);
		}
	}

	/**
	 * Returns the company with the web domain.
	 *
	 * @param  webId the company's web domain
	 * @return the company with the web domain
	 */
	@Override
	public Company getCompanyByWebId(String webId) throws PortalException {
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
				User user = _userPersistence.findByPrimaryKey(userId);

				companyId = user.getCompanyId();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get the company ID for user " + userId,
						exception);
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
	 * through {@link com.liferay.portal.kernel.service.CompanyService}.
	 *
	 * @param companyId the primary key of the company
	 * @param keys the company's preferences keys to be remove
	 */
	@Override
	public void removePreferences(long companyId, String[] keys) {
		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			for (String key : keys) {
				preferences.reset(key);
			}

			preferences.store();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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
	 */
	@Override
	public Hits search(
		long companyId, long userId, String keywords, int start, int end) {

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
	 */
	@Override
	public Hits search(
		long companyId, long userId, String portletId, long groupId,
		String type, String keywords, int start, int end) {

		FacetedSearcher facetedSearcher =
			FacetedSearcherManagerUtil.createFacetedSearcher();

		SearchContext searchContext = createSearchContext(
			companyId, userId, portletId, groupId, keywords, start, end);

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
		}
	}

	@Override
	public Company updateCompany(Company company) {
		_companyInfoPersistence.update(company.getCompanyInfo());

		return super.updateCompany(company);
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
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHostname, String mx, int maxUsers,
			boolean active)
		throws PortalException {

		// Company

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		if (!active && (companyId == PortalInstances.getDefaultCompanyId())) {
			throw new RequiredCompanyException(
				"Select another default company before deactivating company " +
					companyId);
		}

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validateVirtualHost(company.getWebId(), virtualHostname);

		if (PropsValues.MAIL_MX_UPDATE) {
			validateMx(companyId, mx);

			company.setMx(mx);
		}

		company.setMaxUsers(maxUsers);
		company.setActive(active);

		companyPersistence.update(company);

		// Virtual host

		return updateVirtualHostname(companyId, virtualHostname);
	}

	/**
	 * Update the company with additional account information.
	 *
	 * @param  companyId the primary key of the company
	 * @param  virtualHostname the company's virtual host name
	 * @param  mx the company's mail domain
	 * @param  homeURL the company's home URL (optionally <code>null</code>)
	 * @param  hasLogo if the company has a custom logo
	 * @param  logoBytes the new logo image data
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
	 */
	@Override
	public Company updateCompany(
			long companyId, String virtualHostname, String mx, String homeURL,
			boolean hasLogo, byte[] logoBytes, String name, String legalName,
			String legalId, String legalType, String sicCode,
			String tickerSymbol, String industry, String type, String size)
		throws PortalException {

		// Company

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		Company company = companyPersistence.findByPrimaryKey(companyId);

		validateVirtualHost(company.getWebId(), virtualHostname);

		if (PropsValues.MAIL_MX_UPDATE) {
			validateMx(companyId, mx);
		}

		validateName(companyId, name);

		if (PropsValues.MAIL_MX_UPDATE) {
			company.setMx(mx);
		}

		company.setHomeURL(homeURL);

		PortalUtil.updateImageId(
			company, hasLogo, logoBytes, "logoId", 0, 0, 0);

		company.setName(name);
		company.setLegalName(legalName);
		company.setLegalId(legalId);
		company.setLegalType(legalType);
		company.setSicCode(sicCode);
		company.setTickerSymbol(tickerSymbol);
		company.setIndustry(industry);
		company.setType(type);
		company.setSize(size);

		companyPersistence.update(company);

		// Virtual host

		return updateVirtualHostname(companyId, virtualHostname);
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

		User user = _userLocalService.getGuestUser(companyId);

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);

		_userPersistence.update(user);

		updateDisplayGroupNames(companyId);
	}

	@Async
	@Override
	public void updateDisplayGroupNames(long companyId) throws PortalException {
		User user = _userLocalService.getGuestUser(companyId);

		Locale locale = user.getLocale();

		if (locale.equals(LocaleUtil.getDefault())) {
			return;
		}

		ActionableDynamicQuery groupActionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property activeProperty = PropertyFactoryUtil.forName("active");

				dynamicQuery.add(activeProperty.eq(Boolean.TRUE));

				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.isNotNull());
			});
		groupActionableDynamicQuery.setCompanyId(user.getCompanyId());
		groupActionableDynamicQuery.setPerformActionMethod(
			(Group group) -> {
				Map<Locale, String> nameMap = group.getNameMap();

				if (MapUtil.isEmpty(nameMap)) {
					return;
				}

				String groupDefaultName = nameMap.get(locale);

				if (Validator.isNotNull(groupDefaultName)) {
					return;
				}

				String oldGroupDefaultName = nameMap.get(
					LocaleUtil.getDefault());

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"No name was found for locale ", locale,
							". Using \"", oldGroupDefaultName,
							"\" as the name instead."));
				}

				nameMap.put(locale, oldGroupDefaultName);

				group.setNameMap(nameMap);

				_groupLocalService.updateGroup(group);
			});

		groupActionableDynamicQuery.performActions();
	}

	@Override
	public Company updateIndexNameNext(long companyId, String indexNameNext)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		company.setIndexNameNext(indexNameNext);

		return companyPersistence.update(company);
	}

	@Override
	public Company updateIndexNames(
			long companyId, String indexNameCurrent, String indexNameNext)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		company.setIndexNameCurrent(indexNameCurrent);
		company.setIndexNameNext(indexNameNext);

		return companyPersistence.update(company);
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

		Company company = checkLogo(companyId);

		_imageLocalService.updateImage(company.getLogoId(), bytes);

		return company;
	}

	/**
	 * Updates the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  file the file of the company's logo image
	 * @return the company with the primary key
	 */
	@Override
	public Company updateLogo(long companyId, File file)
		throws PortalException {

		Company company = checkLogo(companyId);

		_imageLocalService.updateImage(
			company.getCompanyId(), company.getLogoId(), file);

		return company;
	}

	/**
	 * Update the company's logo.
	 *
	 * @param  companyId the primary key of the company
	 * @param  inputStream the input stream of the company's logo image
	 * @return the company with the primary key
	 */
	@Override
	public Company updateLogo(long companyId, InputStream inputStream)
		throws PortalException {

		Company company = checkLogo(companyId);

		_imageLocalService.updateImage(
			company.getCompanyId(), company.getLogoId(), inputStream);

		return company;
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

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			String newLanguageIds = unicodeProperties.getProperty(
				PropsKeys.LOCALES);

			boolean invalidateLayoutSetTemplates = false;

			if (Validator.isNotNull(newLanguageIds)) {
				String oldLanguageIds = portletPreferences.getValue(
					PropsKeys.LOCALES, StringPool.BLANK);

				if (!Objects.equals(oldLanguageIds, newLanguageIds)) {
					validateLanguageIds(newLanguageIds);

					_updateGroupLanguageIds(
						companyId, newLanguageIds, oldLanguageIds);

					LanguageUtil.resetAvailableLocales(companyId);

					invalidateLayoutSetTemplates = true;
				}
			}

			List<String> resetKeys = new ArrayList<>();

			for (Map.Entry<String, String> entry :
					unicodeProperties.entrySet()) {

				String value = entry.getValue();

				if (value.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
					continue;
				}

				String key = entry.getKey();

				String propsUtilValue = PropsUtil.get(key);

				if (!value.equals(propsUtilValue)) {
					portletPreferences.setValue(key, value);
				}
				else {
					String portletPreferencesValue =
						portletPreferences.getValue(key, null);

					if (portletPreferencesValue != null) {
						resetKeys.add(key);
					}
				}
			}

			for (String key : resetKeys) {
				portletPreferences.reset(key);
			}

			portletPreferences.store();

			if (invalidateLayoutSetTemplates) {

				// Invalidate cache of all layout set prototypes that belong
				// to this company. See LPS-36403.

				Date date = new Date();

				for (LayoutSetPrototype layoutSetPrototype :
						_layoutSetPrototypeLocalService.getLayoutSetPrototypes(
							companyId)) {

					layoutSetPrototype.setModifiedDate(date);

					_layoutSetPrototypeLocalService.updateLayoutSetPrototype(
						layoutSetPrototype);
				}
			}
		}
		catch (LocaleException localeException) {
			throw localeException;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		_clearCompanyCache(companyId);
	}

	/**
	 * Updates the company's security properties.
	 *
	 * @param companyId the primary key of the company
	 * @param authType the company's method of authenticating users
	 * @param autoLogin whether to allow users to select the "remember me"
	 *        feature
	 * @param sendPassword whether to allow users to ask the company to send
	 *        their password
	 * @param strangers whether to allow strangers to create accounts register
	 *        themselves in the company
	 * @param strangersWithMx whether to allow strangers to create accounts with
	 *        email addresses that match the company mail suffix
	 * @param strangersVerify whether to require strangers who create accounts
	 *        to be verified via email
	 * @param siteLogo whether to allow site administrators to use their own
	 *        logo instead of the enterprise logo
	 */
	@Override
	public void updateSecurity(
		long companyId, String authType, boolean autoLogin,
		boolean sendPassword, boolean strangers, boolean strangersWithMx,
		boolean strangersVerify, boolean siteLogo) {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_AUTH_TYPE, authType);
			preferences.setValue(
				PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
				String.valueOf(autoLogin));
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
		catch (IOException | PortletException exception) {
			throw new SystemException(exception);
		}

		_clearCompanyCache(companyId);
	}

	protected void addAssetEntriesFacet(SearchContext searchContext) {
	}

	protected Company checkLogo(long companyId) throws PortalException {
		Company company = companyPersistence.findByPrimaryKey(companyId);

		long logoId = company.getLogoId();

		if (logoId <= 0) {
			logoId = counterLocalService.increment();

			company.setLogoId(logoId);

			company = companyPersistence.update(company);
		}

		return company;
	}

	protected SearchContext createSearchContext(
		long companyId, long userId, String portletId, long groupId,
		String keywords, int start, int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setEntryClassNames(
			SearchEngineHelperUtil.getEntryClassNames());

		if (groupId > 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}

		searchContext.setKeywords(keywords);

		if (Validator.isNotNull(portletId)) {
			searchContext.setPortletIds(new String[] {portletId});
		}

		searchContext.setStart(start);
		searchContext.setUserId(userId);

		return searchContext;
	}

	protected Company doDeleteCompany(long companyId) throws PortalException {

		// Company

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (DBPartitionUtil.isPartitionEnabled()) {
			_clearCompanyCache(companyId);
			_clearVirtualHostCache(companyId);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					PortalInstances.removeCompany(company.getCompanyId());

					unregisterCompany(company);

					return null;
				});

			DBPartitionUtil.removeDBPartition(companyId);

			return company;
		}

		preunregisterCompany(company);

		companyPersistence.remove(company);

		_companyInfoPersistence.remove(company.getCompanyInfo());

		// Expando

		DeleteExpandoColumnActionableDynamicQuery
			deleteExpandoColumnActionableDynamicQuery =
				new DeleteExpandoColumnActionableDynamicQuery(
					company.getCompanyId());

		deleteExpandoColumnActionableDynamicQuery.performActions();

		DeleteExpandoTableActionableDynamicQuery
			deleteExpandoTableActionableDynamicQuery =
				new DeleteExpandoTableActionableDynamicQuery(
					company.getCompanyId());

		deleteExpandoTableActionableDynamicQuery.performActions();

		// Group

		DeleteGroupActionableDynamicQuery deleteGroupActionableDynamicQuery =
			new DeleteGroupActionableDynamicQuery();

		deleteGroupActionableDynamicQuery.setCompanyId(companyId);

		deleteGroupActionableDynamicQuery.performActions();

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (String groupName : systemGroups) {
			deleteGroupActionableDynamicQuery.deleteGroup(
				_groupLocalService.getGroup(companyId, groupName));
		}

		deleteGroupActionableDynamicQuery.deleteGroup(
			_groupLocalService.getCompanyGroup(companyId));

		// Layout prototype

		ActionableDynamicQuery layoutPrototypeActionableDynamicQuery =
			_layoutPrototypeLocalService.getActionableDynamicQuery();

		layoutPrototypeActionableDynamicQuery.setCompanyId(companyId);
		layoutPrototypeActionableDynamicQuery.setPerformActionMethod(
			(LayoutPrototype layoutPrototype) ->
				_layoutPrototypeLocalService.deleteLayoutPrototype(
					layoutPrototype));

		layoutPrototypeActionableDynamicQuery.performActions();

		// Layout set prototype

		ActionableDynamicQuery layoutSetPrototypeActionableDynamicQuery =
			_layoutSetPrototypeLocalService.getActionableDynamicQuery();

		layoutSetPrototypeActionableDynamicQuery.setCompanyId(companyId);
		layoutSetPrototypeActionableDynamicQuery.setPerformActionMethod(
			(LayoutSetPrototype layoutSetPrototype) ->
				_layoutSetPrototypeLocalService.deleteLayoutSetPrototype(
					layoutSetPrototype));

		layoutSetPrototypeActionableDynamicQuery.performActions();

		// Organization

		DeleteOrganizationActionableDynamicQuery
			deleteOrganizationActionableDynamicQuery =
				new DeleteOrganizationActionableDynamicQuery();

		deleteOrganizationActionableDynamicQuery.setCompanyId(companyId);

		deleteOrganizationActionableDynamicQuery.performActions();

		// User group

		DeleteUserGroupActionableDynamicQuery
			deleteUserGroupActionableDynamicQuery =
				new DeleteUserGroupActionableDynamicQuery(
					company.getCompanyId());

		deleteUserGroupActionableDynamicQuery.performActions();

		// Password policy

		_passwordPolicyLocalService.deleteNondefaultPasswordPolicies(companyId);

		PasswordPolicy defaultPasswordPolicy =
			_passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);

		if (defaultPasswordPolicy != null) {
			_passwordPolicyLocalService.deletePasswordPolicy(
				defaultPasswordPolicy);
		}

		// Portal preferences

		PortalPreferences portalPreferences =
			_portalPreferencesPersistence.findByO_O(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		_portalPreferencesLocalService.deletePortalPreferences(
			portalPreferences);

		// User

		User guestUser = _userLocalService.getGuestUser(companyId);

		String name = PrincipalThreadLocal.getName();

		try {
			PrincipalThreadLocal.setName(guestUser.getUserId());

			ActionableDynamicQuery userActionableDynamicQuery =
				_userLocalService.getActionableDynamicQuery();

			userActionableDynamicQuery.setCompanyId(companyId);
			userActionableDynamicQuery.setPerformActionMethod(
				(User user) -> {
					if (!user.isGuestUser()) {
						_userLocalService.deleteUser(user.getUserId());
					}
				});

			userActionableDynamicQuery.performActions();
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}

		_userLocalService.deleteUser(guestUser);

		// Role

		ActionableDynamicQuery roleActionableDynamicQuery =
			_roleLocalService.getActionableDynamicQuery();

		roleActionableDynamicQuery.setCompanyId(companyId);
		roleActionableDynamicQuery.setPerformActionMethod(
			(Role role) -> _roleLocalService.deleteRole(role));

		roleActionableDynamicQuery.performActions();

		// System event

		DeleteSystemEventActionableDynamicQuery
			deleteSystemEventActionableDynamicQuery =
				new DeleteSystemEventActionableDynamicQuery(
					company.getCompanyId());

		deleteSystemEventActionableDynamicQuery.performActions();

		_deletePortalInstance(company);

		return company;
	}

	protected void preregisterCompany(Company company) {
		PortalInstanceLifecycleManager portalInstanceLifecycleManager =
			_serviceTracker.getService();

		if (portalInstanceLifecycleManager != null) {
			portalInstanceLifecycleManager.preregisterCompany(company);
		}
		else {
			synchronized (_preregisterPendingCompanies) {
				_preregisterPendingCompanies.add(company);
			}
		}
	}

	protected void preunregisterCompany(Company company) {
		PortalInstanceLifecycleManager portalInstanceLifecycleManager =
			_serviceTracker.getService();

		if (portalInstanceLifecycleManager != null) {
			portalInstanceLifecycleManager.preunregisterCompany(company);
		}
	}

	protected void registerCompany(Company company) {
		PortalInstanceLifecycleManager portalInstanceLifecycleManager =
			_serviceTracker.getService();

		if (portalInstanceLifecycleManager != null) {
			portalInstanceLifecycleManager.registerCompany(company);
		}
		else {
			synchronized (_pendingCompanies) {
				_pendingCompanies.add(company);
			}
		}
	}

	protected void unregisterCompany(Company company) {
		PortalInstanceLifecycleManager portalInstanceLifecycleManager =
			_serviceTracker.getService();

		if (portalInstanceLifecycleManager != null) {
			portalInstanceLifecycleManager.unregisterCompany(company);
		}
	}

	protected Company updateVirtualHostname(
			long companyId, String virtualHostname)
		throws CompanyVirtualHostException {

		if (Validator.isNotNull(virtualHostname)) {
			try {
				if (Validator.isIPv6Address(virtualHostname)) {
					Inet6Address address = (Inet6Address)InetAddress.getByName(
						virtualHostname);

					virtualHostname = address.getHostAddress();
				}
			}
			catch (UnknownHostException unknownHostException) {
				if (_log.isDebugEnabled()) {
					_log.debug(unknownHostException);
				}

				throw new CompanyVirtualHostException(
					"Virtual hostname is not a valid IPv6 address");
			}

			VirtualHost virtualHost = _virtualHostPersistence.fetchByHostname(
				virtualHostname);

			if (virtualHost == null) {
				_virtualHostLocalService.updateVirtualHosts(
					companyId, 0,
					TreeMapBuilder.put(
						virtualHostname, StringPool.BLANK
					).build());
			}
			else {
				if ((virtualHost.getCompanyId() != companyId) ||
					(virtualHost.getLayoutSetId() != 0)) {

					throw new CompanyVirtualHostException();
				}
			}
		}
		else {
			List<VirtualHost> virtualHosts = _virtualHostPersistence.findByC_L(
				companyId, 0);

			if (!virtualHosts.isEmpty()) {
				for (VirtualHost virtualHost : virtualHosts) {
					_virtualHostPersistence.remove(virtualHost);
				}
			}
		}

		return companyPersistence.fetchByPrimaryKey(companyId);
	}

	protected void validateLanguageIds(String languageIds)
		throws PortalException {

		String[] languageIdsArray = StringUtil.split(
			languageIds, StringPool.COMMA);

		for (String languageId : languageIdsArray) {
			if (!ArrayUtil.contains(PropsValues.LOCALES, languageId)) {
				LocaleException localeException = new LocaleException(
					LocaleException.TYPE_DISPLAY_SETTINGS);

				localeException.setSourceAvailableLanguageIds(
					Arrays.asList(PropsValues.LOCALES));
				localeException.setTargetAvailableLanguageIds(
					Arrays.asList(languageIdsArray));

				throw localeException;
			}
		}
	}

	protected void validateMx(long companyId, String mx)
		throws PortalException {

		if (Validator.isNull(mx) || !Validator.isDomain(mx)) {
			throw new CompanyMxException("Invalid domain " + mx);
		}

		String emailAddress =
			PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" + mx;

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		if (!emailAddressValidator.validate(companyId, emailAddress)) {
			throw new CompanyMxException(
				"Invalid email address " + emailAddress);
		}
	}

	protected void validateName(long companyId, String name)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(companyId, name);

		if ((group != null) || Validator.isNull(name)) {
			throw new CompanyNameException();
		}
	}

	protected void validateVirtualHost(String webId, String virtualHostname)
		throws PortalException {

		try {
			if (Validator.isNull(virtualHostname)) {
				throw new CompanyVirtualHostException(
					"Virtual hostname is null");
			}
			else if (virtualHostname.equals(_DEFAULT_VIRTUAL_HOST) &&
					 !webId.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {

				throw new CompanyVirtualHostException(
					"localhost can only be used with the default web ID " +
						webId);
			}
			else if (!Validator.isDomain(virtualHostname) &&
					 !Validator.isIPAddress(virtualHostname)) {

				throw new CompanyVirtualHostException(
					"Virtual hostname is invalid");
			}

			VirtualHost virtualHost = _virtualHostLocalService.fetchVirtualHost(
				virtualHostname);

			if (virtualHost == null) {
				return;
			}

			Company virtualHostnameCompany =
				companyPersistence.findByPrimaryKey(virtualHost.getCompanyId());

			if (!webId.equals(virtualHostnameCompany.getWebId())) {
				throw new CompanyVirtualHostException(
					"Duplicate virtual hostname " + virtualHostname);
			}
		}
		catch (CompanyVirtualHostException companyVirtualHostException) {
			if (_log.isWarnEnabled()) {
				_log.warn(companyVirtualHostException);
			}

			throw companyVirtualHostException;
		}
	}

	protected void validateWebId(String webId) throws CompanyWebIdException {
		if (Validator.isNull(webId)) {
			throw new CompanyWebIdException("Web ID is null");
		}

		if (companyPersistence.fetchByWebId(webId) != null) {
			throw new CompanyWebIdException("Duplicate web ID " + webId);
		}
	}

	protected class DeleteExpandoColumnActionableDynamicQuery {

		protected DeleteExpandoColumnActionableDynamicQuery(long companyId) {
			_actionableDynamicQuery =
				_expandoColumnLocalService.getActionableDynamicQuery();

			_actionableDynamicQuery.setCompanyId(companyId);
			_actionableDynamicQuery.setPerformActionMethod(
				(ExpandoColumn expandoColumn) ->
					_expandoColumnLocalService.deleteColumn(expandoColumn));
		}

		protected void performActions() throws PortalException {
			_actionableDynamicQuery.performActions();
		}

		private ActionableDynamicQuery _actionableDynamicQuery;

	}

	protected class DeleteExpandoTableActionableDynamicQuery {

		protected DeleteExpandoTableActionableDynamicQuery(long companyId) {
			_actionableDynamicQuery =
				_expandoTableLocalService.getActionableDynamicQuery();

			_actionableDynamicQuery.setCompanyId(companyId);
			_actionableDynamicQuery.setPerformActionMethod(
				(ExpandoTable expandoTable) ->
					_expandoTableLocalService.deleteExpandoTable(expandoTable));
		}

		protected void performActions() throws PortalException {
			_actionableDynamicQuery.performActions();
		}

		private ActionableDynamicQuery _actionableDynamicQuery;

	}

	protected class DeleteGroupActionableDynamicQuery {

		protected DeleteGroupActionableDynamicQuery() {
			_actionableDynamicQuery =
				_groupLocalService.getActionableDynamicQuery();

			_actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property parentGroupIdProperty =
						PropertyFactoryUtil.forName("parentGroupId");

					dynamicQuery.add(parentGroupIdProperty.eq(_parentGroupId));

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					Property siteProperty = PropertyFactoryUtil.forName("site");

					disjunction.add(siteProperty.eq(Boolean.TRUE));

					Property typeProperty = PropertyFactoryUtil.forName("type");

					disjunction.add(typeProperty.eq(GroupConstants.TYPE_DEPOT));

					dynamicQuery.add(disjunction);
				});
			_actionableDynamicQuery.setPerformActionMethod(
				(Group group) -> {
					if (!PortalUtil.isSystemGroup(group.getGroupKey()) &&
						!group.isCompany() && !group.isStagingGroup()) {

						deleteGroup(group);
					}
				});
		}

		protected void deleteGroup(Group group) throws PortalException {
			DeleteGroupActionableDynamicQuery
				deleteGroupActionableDynamicQuery =
					new DeleteGroupActionableDynamicQuery();

			deleteGroupActionableDynamicQuery.setCompanyId(
				group.getCompanyId());
			deleteGroupActionableDynamicQuery.setParentGroupId(
				group.getGroupId());

			deleteGroupActionableDynamicQuery.performActions();

			_groupLocalService.deleteGroup(group);

			LiveUsers.deleteGroup(group.getCompanyId(), group.getGroupId());
		}

		protected void performActions() throws PortalException {
			_actionableDynamicQuery.performActions();
		}

		protected void setCompanyId(long companyId) {
			_actionableDynamicQuery.setCompanyId(companyId);
		}

		protected void setParentGroupId(long parentGroupId) {
			_parentGroupId = parentGroupId;
		}

		private ActionableDynamicQuery _actionableDynamicQuery;
		private long _parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;

	}

	protected class DeleteOrganizationActionableDynamicQuery {

		public void setParentOrganizationId(long parentOrganizationId) {
			_parentOrganizationId = parentOrganizationId;
		}

		protected DeleteOrganizationActionableDynamicQuery() {
			_actionableDynamicQuery =
				_organizationLocalService.getActionableDynamicQuery();

			_actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property property = PropertyFactoryUtil.forName(
						"parentOrganizationId");

					dynamicQuery.add(property.eq(_parentOrganizationId));
				});
			_actionableDynamicQuery.setPerformActionMethod(
				(Organization organization) -> deleteOrganization(
					organization));
		}

		protected void deleteOrganization(Organization organization)
			throws PortalException {

			DeleteOrganizationActionableDynamicQuery
				deleteOrganizationActionableDynamicQuery =
					new DeleteOrganizationActionableDynamicQuery();

			deleteOrganizationActionableDynamicQuery.setCompanyId(
				organization.getCompanyId());
			deleteOrganizationActionableDynamicQuery.setParentOrganizationId(
				organization.getOrganizationId());

			deleteOrganizationActionableDynamicQuery.performActions();

			_organizationLocalService.deleteOrganization(organization);
		}

		protected void performActions() throws PortalException {
			_actionableDynamicQuery.performActions();
		}

		protected void setCompanyId(long companyId) {
			_actionableDynamicQuery.setCompanyId(companyId);
		}

		private ActionableDynamicQuery _actionableDynamicQuery;
		private long _parentOrganizationId =
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

	}

	protected class DeleteSystemEventActionableDynamicQuery {

		protected DeleteSystemEventActionableDynamicQuery(long companyId) {
			_actionableDynamicQuery =
				_systemEventLocalService.getActionableDynamicQuery();

			_actionableDynamicQuery.setCompanyId(companyId);
			_actionableDynamicQuery.setPerformActionMethod(
				(SystemEvent systemEvent) ->
					_systemEventLocalService.deleteSystemEvent(systemEvent));
		}

		protected void performActions() throws PortalException {
			_actionableDynamicQuery.performActions();
		}

		private ActionableDynamicQuery _actionableDynamicQuery;

	}

	protected class DeleteUserGroupActionableDynamicQuery {

		protected DeleteUserGroupActionableDynamicQuery(long companyId) {
			_actionableDynamicQuery =
				_userGroupLocalService.getActionableDynamicQuery();

			_actionableDynamicQuery.setCompanyId(companyId);
			_actionableDynamicQuery.setPerformActionMethod(
				(UserGroup userGroup) -> _userGroupLocalService.deleteUserGroup(
					userGroup));
		}

		protected void performActions() throws PortalException {
			_actionableDynamicQuery.performActions();
		}

		private ActionableDynamicQuery _actionableDynamicQuery;

	}

	private User _addDefaultServiceAccountUser(Company company)
		throws PortalException {

		Role adminRole = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		String userName = "default-service-account";

		User defaultServiceAccountUser = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, company.getCompanyId(), true, null,
			null, false, userName, userName + StringPool.AT + company.getMx(),
			LocaleUtil.fromLanguageId(PropsValues.COMPANY_DEFAULT_LOCALE),
			userName, StringPool.BLANK, userName, 0, 0, true, Calendar.JANUARY,
			1, 1970, StringPool.BLANK,
			UserConstants.TYPE_DEFAULT_SERVICE_ACCOUNT, null, null,
			new long[] {adminRole.getRoleId()}, null, false,
			new ServiceContext());

		defaultServiceAccountUser.setEmailAddressVerified(true);

		return _userLocalService.updateUser(defaultServiceAccountUser);
	}

	private void _addDemoSettings(Company company) throws PortalException {
		updateVirtualHostname(company.getCompanyId(), "demo.liferay.net");

		updateSecurity(
			company.getCompanyId(), CompanyConstants.AUTH_TYPE_EA, true, true,
			true, true, false, true);

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			company.getCompanyId());

		try {
			preferences.setValue(
				PropsKeys.ADMIN_EMAIL_FROM_NAME, "Liferay Demo");
			preferences.setValue(
				PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.net");

			preferences.store();
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
		catch (PortletException portletException) {
			throw new SystemException(portletException);
		}
	}

	private User _addGuestUser(Company company) throws PortalException {
		Date date = new Date();

		User guestUser = _userPersistence.create(
			counterLocalService.increment());

		guestUser.setCompanyId(company.getCompanyId());
		guestUser.setContactId(counterLocalService.increment());
		guestUser.setPassword("password");
		guestUser.setScreenName(String.valueOf(guestUser.getUserId()));
		guestUser.setEmailAddress("default@" + company.getMx());

		Locale locale = null;

		if (Validator.isNotNull(PropsValues.COMPANY_DEFAULT_LOCALE)) {
			locale = LocaleUtil.fromLanguageId(
				PropsValues.COMPANY_DEFAULT_LOCALE);
		}
		else {
			User defaultCompanyGuestUser = _userLocalService.fetchGuestUser(
				PortalUtil.getDefaultCompanyId());

			if (defaultCompanyGuestUser != null) {
				locale = defaultCompanyGuestUser.getLocale();
			}
		}

		guestUser.setLanguageId(LocaleUtil.toLanguageId(locale));

		if (Validator.isNotNull(PropsValues.COMPANY_DEFAULT_TIME_ZONE)) {
			guestUser.setTimeZoneId(PropsValues.COMPANY_DEFAULT_TIME_ZONE);
		}
		else {
			TimeZone timeZone = TimeZoneUtil.getDefault();

			guestUser.setTimeZoneId(timeZone.getID());
		}

		String greeting = LanguageUtil.format(
			guestUser.getLocale(), "welcome", null, false);

		guestUser.setGreeting(greeting + StringPool.EXCLAMATION);

		guestUser.setLoginDate(date);
		guestUser.setFailedLoginAttempts(0);
		guestUser.setAgreedToTermsOfUse(true);
		guestUser.setType(UserConstants.TYPE_GUEST);
		guestUser.setStatus(WorkflowConstants.STATUS_APPROVED);

		// Invoke updateImpl so that we do not trigger model listeners. See
		// LPS-108239.

		_userPersistence.updateImpl(guestUser);

		// Force update _guestUsers map

		guestUser = _userLocalService.getGuestUser(company.getCompanyId());

		// Contact

		Contact guestContact = _contactPersistence.create(
			guestUser.getContactId());

		guestContact.setCompanyId(guestUser.getCompanyId());
		guestContact.setUserId(guestUser.getUserId());
		guestContact.setUserName(StringPool.BLANK);
		guestContact.setClassName(User.class.getName());
		guestContact.setClassPK(guestUser.getUserId());
		guestContact.setParentContactId(
			ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		guestContact.setEmailAddress(guestUser.getEmailAddress());
		guestContact.setFirstName(StringPool.BLANK);
		guestContact.setMiddleName(StringPool.BLANK);
		guestContact.setLastName(StringPool.BLANK);
		guestContact.setMale(true);
		guestContact.setBirthday(date);

		_contactPersistence.update(guestContact);

		return guestUser;
	}

	private Company _checkCompany(
			Company company, String mx, String defaultAdminPassword,
			String defaultAdminScreenName, String defaultAdminEmailAddress,
			String defaultAdminFirstName, String defaultAdminLastName)
		throws PortalException {

		Locale localeThreadLocalDefaultLocale =
			LocaleThreadLocal.getDefaultLocale();
		Locale localeThreadSiteDefaultLocale =
			LocaleThreadLocal.getSiteDefaultLocale();

		try {
			preregisterCompany(company);

			Locale companyDefaultLocale = LocaleUtil.fromLanguageId(
				PropsValues.COMPANY_DEFAULT_LOCALE);

			LocaleThreadLocal.setDefaultLocale(companyDefaultLocale);

			LocaleThreadLocal.setSiteDefaultLocale(null);

			// Key

			checkCompanyKey(company.getCompanyId());

			// Guest user

			User guestUser = _userPersistence.fetchByC_T_First(
				company.getCompanyId(), UserConstants.TYPE_GUEST, null);

			if (guestUser != null) {
				if (!guestUser.isAgreedToTermsOfUse()) {
					guestUser.setAgreedToTermsOfUse(true);

					guestUser = _userPersistence.update(guestUser);
				}
			}
			else {
				guestUser = _addGuestUser(company);
			}

			// System roles

			_roleLocalService.checkSystemRoles(company.getCompanyId());

			// System groups

			_groupLocalService.checkSystemGroups(company.getCompanyId());

			// Company group

			_groupLocalService.checkCompanyGroup(company.getCompanyId());

			// Default password policy

			_passwordPolicyLocalService.checkDefaultPasswordPolicy(
				company.getCompanyId());

			// Guest user must have the Guest role

			Role guestRole = _roleLocalService.getRole(
				company.getCompanyId(), RoleConstants.GUEST);

			_roleLocalService.setUserRoles(
				guestUser.getUserId(), new long[] {guestRole.getRoleId()});

			// Default admin

			if (_userPersistence.countByCompanyId(company.getCompanyId()) ==
					0) {

				if (Validator.isNull(defaultAdminEmailAddress)) {
					defaultAdminEmailAddress =
						PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" +
							mx;
				}

				if (Validator.isNull(defaultAdminScreenName)) {
					defaultAdminScreenName =
						PropsValues.DEFAULT_ADMIN_SCREEN_NAME;
				}

				if (Validator.isNull(defaultAdminFirstName)) {
					defaultAdminFirstName =
						PropsValues.DEFAULT_ADMIN_FIRST_NAME;
				}

				if (Validator.isNull(defaultAdminLastName)) {
					defaultAdminLastName = PropsValues.DEFAULT_ADMIN_LAST_NAME;
				}

				_userLocalService.addDefaultAdminUser(
					company.getCompanyId(), defaultAdminPassword,
					defaultAdminScreenName, defaultAdminEmailAddress,
					guestUser.getLocale(), defaultAdminFirstName,
					PropsValues.DEFAULT_ADMIN_MIDDLE_NAME,
					defaultAdminLastName);
			}

			// Default service account

			if (ListUtil.isEmpty(
					_userPersistence.findByC_T(
						company.getCompanyId(),
						UserConstants.TYPE_DEFAULT_SERVICE_ACCOUNT))) {

				_addDefaultServiceAccountUser(company);
			}

			// Portlets

			_portletLocalService.checkPortlets(company.getCompanyId());

			Company finalCompany = company;

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					registerCompany(finalCompany);

					return null;
				});
		}
		finally {
			LocaleThreadLocal.setDefaultLocale(localeThreadLocalDefaultLocale);
			LocaleThreadLocal.setSiteDefaultLocale(
				localeThreadSiteDefaultLocale);
		}

		return company;
	}

	private void _clearCompanyCache(long companyId) {
		Company company = companyPersistence.fetchByPrimaryKey(companyId);

		if (company != null) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					EntityCacheUtil.removeResult(
						company.getClass(), company.getPrimaryKeyObj());

					PortalCacheHelperUtil.removePortalCaches(
						PortalCacheManagerNames.MULTI_VM, companyId);

					return null;
				});

			companyPersistence.clearCache(company);
		}
	}

	private void _clearVirtualHostCache(long companyId) {
		Company company = companyPersistence.fetchByPrimaryKey(companyId);

		if (company != null) {
			VirtualHost virtualHost = _virtualHostPersistence.fetchByHostname(
				company.getVirtualHostname());

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					EntityCacheUtil.removeResult(
						virtualHost.getClass(), virtualHost.getPrimaryKeyObj());

					return null;
				});

			_virtualHostPersistence.clearCache(virtualHost);
		}
	}

	private void _deletePortalInstance(Company company) throws PortalException {

		// Portlet

		List<Portlet> portlets = _portletPersistence.findByCompanyId(
			company.getCompanyId());

		for (Portlet portlet : portlets) {
			_portletLocalService.deletePortlet(portlet.getId());
		}

		_portletLocalService.removeCompanyPortletsPool(company.getCompanyId());

		// Virtual host

		VirtualHost companyVirtualHost =
			_virtualHostLocalService.fetchVirtualHost(
				company.getCompanyId(), 0);

		_virtualHostLocalService.deleteVirtualHost(companyVirtualHost);

		// Portal instance

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				PortalInstances.removeCompany(company.getCompanyId());

				unregisterCompany(company);

				return null;
			});
	}

	private void _updateGroupLanguageIds(
		long companyId, String newLanguageIds, String oldLanguageIds) {

		String[] oldLanguageIdsArray = StringUtil.split(oldLanguageIds);

		if (ArrayUtil.isEmpty(oldLanguageIdsArray)) {
			oldLanguageIdsArray = LocaleUtil.toLanguageIds(
				LanguageUtil.getCompanyAvailableLocales(companyId));
		}

		List<String> removedLanguageIds = ListUtil.remove(
			ListUtil.fromArray(oldLanguageIdsArray),
			ListUtil.fromArray(StringUtil.split(newLanguageIds)));

		if (ListUtil.isEmpty(removedLanguageIds)) {
			return;
		}

		List<Group> groups = _groupLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				GroupTable.INSTANCE
			).from(
				GroupTable.INSTANCE
			).where(
				GroupTable.INSTANCE.companyId.eq(
					companyId
				).and(
					GroupTable.INSTANCE.active.eq(true)
				).and(
					GroupTable.INSTANCE.site.eq(true)
				).and(
					GroupTable.INSTANCE.typeSettings.like(
						"%inheritLocales=false%")
				)
			));

		for (Group group : groups) {
			UnicodeProperties groupTypeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			String[] groupLanguageIds = StringUtil.split(
				groupTypeSettingsUnicodeProperties.getProperty(
					PropsKeys.LOCALES));

			boolean updateLocales = false;

			for (String removedLanguageId : removedLanguageIds) {
				if (ArrayUtil.contains(groupLanguageIds, removedLanguageId)) {
					groupLanguageIds = ArrayUtil.remove(
						groupLanguageIds, removedLanguageId);

					updateLocales = true;
				}
			}

			if (updateLocales) {
				LanguageUtil.resetAvailableGroupLocales(group.getGroupId());

				groupTypeSettingsUnicodeProperties.setProperty(
					PropsKeys.LOCALES,
					StringUtil.merge(groupLanguageIds, StringPool.COMMA));

				_groupLocalService.updateGroup(group);
			}
		}
	}

	private static final String _DEFAULT_VIRTUAL_HOST = "localhost";

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyLocalServiceImpl.class);

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	@BeanReference(type = CompanyInfoPersistence.class)
	private CompanyInfoPersistence _companyInfoPersistence;

	@BeanReference(type = ContactPersistence.class)
	private ContactPersistence _contactPersistence;

	@BeanReference(type = DLFileEntryTypeLocalService.class)
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@BeanReference(type = ExpandoColumnLocalService.class)
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@BeanReference(type = ExpandoTableLocalService.class)
	private ExpandoTableLocalService _expandoTableLocalService;

	@BeanReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@BeanReference(type = ImageLocalService.class)
	private ImageLocalService _imageLocalService;

	@BeanReference(type = LayoutPrototypeLocalService.class)
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@BeanReference(type = LayoutSetPrototypeLocalService.class)
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@BeanReference(type = OrganizationLocalService.class)
	private OrganizationLocalService _organizationLocalService;

	@BeanReference(type = PasswordPolicyLocalService.class)
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	private final Set<Company> _pendingCompanies = new HashSet<>();

	@BeanReference(type = PortalPreferencesLocalService.class)
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@BeanReference(type = PortalPreferencesPersistence.class)
	private PortalPreferencesPersistence _portalPreferencesPersistence;

	@BeanReference(type = PortletLocalService.class)
	private PortletLocalService _portletLocalService;

	@BeanReference(type = PortletPersistence.class)
	private PortletPersistence _portletPersistence;

	private final Set<Company> _preregisterPendingCompanies = new HashSet<>();

	@BeanReference(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

	private final ServiceTracker
		<PortalInstanceLifecycleManager, PortalInstanceLifecycleManager>
			_serviceTracker;

	@BeanReference(type = SystemEventLocalService.class)
	private SystemEventLocalService _systemEventLocalService;

	@BeanReference(type = UserGroupLocalService.class)
	private UserGroupLocalService _userGroupLocalService;

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

	@BeanReference(type = VirtualHostLocalService.class)
	private VirtualHostLocalService _virtualHostLocalService;

	@BeanReference(type = VirtualHostPersistence.class)
	private VirtualHostPersistence _virtualHostPersistence;

	private class PortalInstanceLifecycleManagerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortalInstanceLifecycleManager, PortalInstanceLifecycleManager> {

		@Override
		public PortalInstanceLifecycleManager addingService(
			ServiceReference<PortalInstanceLifecycleManager> serviceReference) {

			PortalInstanceLifecycleManager portalInstanceLifecycleManager =
				_bundleContext.getService(serviceReference);

			synchronized (_preregisterPendingCompanies) {
				forEachCompany(
					company ->
						portalInstanceLifecycleManager.preregisterCompany(
							company),
					new ArrayList<Company>(_preregisterPendingCompanies));

				_preregisterPendingCompanies.clear();
			}

			synchronized (_pendingCompanies) {
				forEachCompany(
					company -> portalInstanceLifecycleManager.registerCompany(
						company),
					new ArrayList<Company>(_pendingCompanies));

				_pendingCompanies.clear();
			}

			return portalInstanceLifecycleManager;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortalInstanceLifecycleManager> serviceReference,
			PortalInstanceLifecycleManager portalInstanceLifecycleManager) {

			removedService(serviceReference, portalInstanceLifecycleManager);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<PortalInstanceLifecycleManager> serviceReference,
			PortalInstanceLifecycleManager portalInstanceLifecycleManager) {

			_bundleContext.ungetService(serviceReference);
		}

	}

}