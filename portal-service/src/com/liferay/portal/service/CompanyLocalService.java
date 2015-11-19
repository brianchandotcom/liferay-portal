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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for Company. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyLocalServiceUtil
 * @see com.liferay.portal.service.base.CompanyLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.CompanyLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CompanyLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CompanyLocalServiceUtil} to access the company local service. Add custom service methods to {@link com.liferay.portal.service.impl.CompanyLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the company to the database. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @return the company that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.model.Company addCompany(
		com.liferay.portal.model.Company company);

	/**
	* Adds a company.
	*
	* @param webId the the company's web domain
	* @param virtualHostname the company's virtual host name
	* @param mx the company's mail domain
	* @param system whether the company is the very first company (i.e., the
	super company)
	* @param maxUsers the max number of company users (optionally
	<code>0</code>)
	* @param active whether the company is active
	* @return the company
	*/
	public com.liferay.portal.model.Company addCompany(java.lang.String webId,
		java.lang.String virtualHostname, java.lang.String mx, boolean system,
		int maxUsers, boolean active) throws PortalException;

	/**
	* Returns the company with the web domain.
	*
	* The method sets mail domain to the web domain to the default name set in
	* portal.properties
	*
	* @param webId the company's web domain
	* @return the company with the web domain
	*/
	public com.liferay.portal.model.Company checkCompany(java.lang.String webId)
		throws PortalException;

	/**
	* Returns the company with the web domain and mail domain. If no such
	* company exits, the method will create a new company.
	*
	* The method goes through a series of checks to ensure that the company
	* contains default users, groups, etc.
	*
	* @param webId the company's web domain
	* @param mx the company's mail domain
	* @return the company with the web domain and mail domain
	*/
	public com.liferay.portal.model.Company checkCompany(
		java.lang.String webId, java.lang.String mx) throws PortalException;

	/**
	* Checks if the company has an encryption key. It will create a key if one
	* does not exist.
	*
	* @param companyId the primary key of the company
	*/
	public void checkCompanyKey(long companyId) throws PortalException;

	/**
	* Creates a new company with the primary key. Does not add the company to the database.
	*
	* @param companyId the primary key for the new company
	* @return the new company
	*/
	public com.liferay.portal.model.Company createCompany(long companyId);

	/**
	* Deletes the company from the database. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @return the company that was removed
	* @throws PortalException
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.model.Company deleteCompany(
		com.liferay.portal.model.Company company) throws PortalException;

	/**
	* Deletes the company with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the company
	* @return the company that was removed
	* @throws PortalException if a company with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.model.Company deleteCompany(long companyId)
		throws PortalException;

	/**
	* Deletes the company's logo.
	*
	* @param companyId the primary key of the company
	* @return the deleted logo's company
	*/
	public com.liferay.portal.model.Company deleteLogo(long companyId)
		throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company fetchCompany(long companyId);

	/**
	* Returns the company with the primary key.
	*
	* @param companyId the primary key of the company
	* @return the company with the primary key, <code>null</code> if a company
	with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company fetchCompanyById(long companyId);

	/**
	* Returns the company with the virtual host name.
	*
	* @param virtualHostname the virtual host name
	* @return the company with the virtual host name, <code>null</code> if a
	company with the virtual host could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company fetchCompanyByVirtualHost(
		java.lang.String virtualHostname);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns all the companies.
	*
	* @return the companies
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Company> getCompanies();

	/**
	* Returns a range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.CompanyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @return the range of companies
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		int start, int end);

	/**
	* Returns all the companies used by WSRP.
	*
	* @param system whether the company is the very first company (i.e., the
	super company)
	* @return the companies used by WSRP
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		boolean system);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		boolean system, int start, int end);

	/**
	* Returns the number of companies.
	*
	* @return the number of companies
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompaniesCount();

	/**
	* Returns the number of companies used by WSRP.
	*
	* @param system whether the company is the very first company (i.e., the
	super company)
	* @return the number of companies used by WSRP
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompaniesCount(boolean system);

	/**
	* Returns the company with the primary key.
	*
	* @param companyId the primary key of the company
	* @return the company
	* @throws PortalException if a company with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company getCompany(long companyId)
		throws PortalException;

	/**
	* Returns the company with the primary key.
	*
	* @param companyId the primary key of the company
	* @return the company with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company getCompanyById(long companyId)
		throws PortalException;

	/**
	* Returns the company with the logo.
	*
	* @param logoId the ID of the company's logo
	* @return the company with the logo
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company getCompanyByLogoId(long logoId)
		throws PortalException;

	/**
	* Returns the company with the mail domain.
	*
	* @param mx the company's mail domain
	* @return the company with the mail domain
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company getCompanyByMx(java.lang.String mx)
		throws PortalException;

	/**
	* Returns the company with the virtual host name.
	*
	* @param virtualHostname the company's virtual host name
	* @return the company with the virtual host name
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHostname) throws PortalException;

	/**
	* Returns the company with the web domain.
	*
	* @param webId the company's web domain
	* @return the company with the web domain
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId) throws PortalException;

	/**
	* Returns the user's company.
	*
	* @param userId the primary key of the user
	* @return Returns the first company if there is only one company or the
	user's company if there are more than one company; <code>0</code>
	otherwise
	* @throws Exception if a user with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getCompanyIdByUserId(long userId) throws java.lang.Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	/**
	* Removes the values that match the keys of the company's preferences.
	*
	* This method is called by {@link
	* com.liferay.portlet.portalsettings.action.EditLDAPServerAction} remotely
	* through {@link com.liferay.portal.service.CompanyService}.
	*
	* @param companyId the primary key of the company
	* @param keys the company's preferences keys to be remove
	*/
	public void removePreferences(long companyId, java.lang.String[] keys);

	/**
	* Returns an ordered range of all assets that match the keywords in the
	* company.
	*
	* The method is called in {@link
	* com.liferay.portal.search.PortalOpenSearchImpl} which is not longer used
	* by the Search portlet.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param keywords the keywords (space separated),which may occur in assets
	in the company (optionally <code>null</code>)
	* @param start the lower bound of the range of assets to return
	* @param end the upper bound of the range of assets to return (not
	inclusive)
	* @return the matching assets in the company
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String keywords, int start, int end);

	/**
	* Returns an ordered range of all assets that match the keywords in the
	* portlet within the company.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param portletId the primary key of the portlet (optionally
	<code>null</code>)
	* @param groupId the primary key of the group (optionally <code>0</code>)
	* @param type the mime type of assets to return(optionally
	<code>null</code>)
	* @param keywords the keywords (space separated), which may occur in any
	assets in the portlet (optionally <code>null</code>)
	* @param start the lower bound of the range of assets to return
	* @param end the upper bound of the range of assets to return (not
	inclusive)
	* @return the matching assets in the portlet within the company
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String portletId, long groupId,
		java.lang.String type, java.lang.String keywords, int start, int end);

	/**
	* Updates the company in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @return the company that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company);

	/**
	* Update the company with additional account information.
	*
	* @param companyId the primary key of the company
	* @param virtualHostname the company's virtual host name
	* @param mx the company's mail domain
	* @param homeURL the company's home URL (optionally <code>null</code>)
	* @param logo whether to update the company's logo
	* @param logoBytes the new logo image data
	* @param name the company's account name(optionally <code>null</code>)
	* @param legalName the company's account legal name (optionally
	<code>null</code>)
	* @param legalId the company's account legal ID (optionally
	<code>null</code>)
	* @param legalType the company's account legal type (optionally
	<code>null</code>)
	* @param sicCode the company's account SIC code (optionally
	<code>null</code>)
	* @param tickerSymbol the company's account ticker symbol (optionally
	<code>null</code>)
	* @param industry the company's account industry (optionally
	<code>null</code>)
	* @param type the company's account type (optionally <code>null</code>)
	* @param size the company's account size (optionally <code>null</code>)
	* @return the company with the primary key
	*/
	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHostname, java.lang.String mx,
		java.lang.String homeURL, boolean logo, byte[] logoBytes,
		java.lang.String name, java.lang.String legalName,
		java.lang.String legalId, java.lang.String legalType,
		java.lang.String sicCode, java.lang.String tickerSymbol,
		java.lang.String industry, java.lang.String type, java.lang.String size)
		throws PortalException;

	/**
	* Update the company with additional account information.
	*
	* @param companyId the primary key of the company
	* @param virtualHostname the company's virtual host name
	* @param mx the company's mail domain
	* @param homeURL the company's home URL (optionally <code>null</code>)
	* @param name the company's account name(optionally <code>null</code>)
	* @param legalName the company's account legal name (optionally
	<code>null</code>)
	* @param legalId the company's account legal ID (optionally
	<code>null</code>)
	* @param legalType the company's account legal type (optionally
	<code>null</code>)
	* @param sicCode the company's account SIC code (optionally
	<code>null</code>)
	* @param tickerSymbol the company's account ticker symbol (optionally
	<code>null</code>)
	* @param industry the company's account industry (optionally
	<code>null</code>)
	* @param type the company's account type (optionally
	<code>null</code>)
	* @param size the company's account size (optionally
	<code>null</code>)
	* @return the company with the primary key
	* @deprecated As of 7.0.0, replaced by {@link #updateCompany(long, String,
	String, String, boolean, byte[], String, String, String,
	String, String, String, String, String, String)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHostname, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size) throws PortalException;

	/**
	* Updates the company.
	*
	* @param companyId the primary key of the company
	* @param virtualHostname the company's virtual host name
	* @param mx the company's mail domain
	* @param maxUsers the max number of company users (optionally
	<code>0</code>)
	* @param active whether the company is active
	* @return the company with the primary key
	*/
	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHostname, java.lang.String mx, int maxUsers,
		boolean active) throws PortalException;

	/**
	* Update the company's display.
	*
	* @param companyId the primary key of the company
	* @param languageId the ID of the company's default user's language
	* @param timeZoneId the ID of the company's default user's time zone
	*/
	public void updateDisplay(long companyId, java.lang.String languageId,
		java.lang.String timeZoneId) throws PortalException;

	/**
	* Updates the company's logo.
	*
	* @param companyId the primary key of the company
	* @param bytes the bytes of the company's logo image
	* @return the company with the primary key
	*/
	public com.liferay.portal.model.Company updateLogo(long companyId,
		byte[] bytes) throws PortalException;

	/**
	* Updates the company's logo.
	*
	* @param companyId the primary key of the company
	* @param file the file of the company's logo image
	* @return the company with the primary key
	*/
	public com.liferay.portal.model.Company updateLogo(long companyId,
		java.io.File file) throws PortalException;

	/**
	* Update the company's logo.
	*
	* @param companyId the primary key of the company
	* @param is the input stream of the company's logo image
	* @return the company with the primary key
	*/
	public com.liferay.portal.model.Company updateLogo(long companyId,
		java.io.InputStream is) throws PortalException;

	/**
	* Updates the company's preferences. The company's default properties are
	* found in portal.properties.
	*
	* @param companyId the primary key of the company
	* @param properties the company's properties. See {@link UnicodeProperties}
	*/
	public void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws PortalException;

	/**
	* Updates the company's security properties.
	*
	* @param companyId the primary key of the company
	* @param authType the company's method of authenticating users
	* @param autoLogin whether to allow users to select the "remember me"
	feature
	* @param sendPassword whether to allow users to ask the company to send
	their password
	* @param strangers whether to allow strangers to create accounts register
	themselves in the company
	* @param strangersWithMx whether to allow strangers to create accounts with
	email addresses that match the company mail suffix
	* @param strangersVerify whether to require strangers who create accounts
	to be verified via email
	* @param siteLogo whether to allow site administrators to use their own
	logo instead of the enterprise logo
	*/
	public void updateSecurity(long companyId, java.lang.String authType,
		boolean autoLogin, boolean sendPassword, boolean strangers,
		boolean strangersWithMx, boolean strangersVerify, boolean siteLogo);
}