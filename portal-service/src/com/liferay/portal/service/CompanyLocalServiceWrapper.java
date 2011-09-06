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
 * This class is a wrapper for {@link CompanyLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyLocalService
 * @generated
 */
public class CompanyLocalServiceWrapper implements CompanyLocalService {
	public CompanyLocalServiceWrapper(CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}

	/**
	* Adds the company to the database. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @return the company that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company addCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.addCompany(company);
	}

	/**
	* Creates a new company with the primary key. Does not add the company to the database.
	*
	* @param companyId the primary key for the new company
	* @return the new company
	*/
	public com.liferay.portal.model.Company createCompany(long companyId) {
		return _companyLocalService.createCompany(companyId);
	}

	/**
	* Deletes the company with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the company
	* @throws PortalException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteCompany(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.deleteCompany(companyId);
	}

	/**
	* Deletes the company from the database. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @throws SystemException if a system exception occurred
	*/
	public void deleteCompany(com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.deleteCompany(company);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the company with the primary key.
	*
	* @param companyId the primary key of the company
	* @return the company
	* @throws PortalException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company getCompany(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompany(companyId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @return the range of companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanies(start, end);
	}

	/**
	* Returns the number of companies.
	*
	* @return the number of companies
	* @throws SystemException if a system exception occurred
	*/
	public int getCompaniesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompaniesCount();
	}

	/**
	* Updates the company in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @return the company that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.updateCompany(company);
	}

	/**
	* Updates the company in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param company the company
	* @param merge whether to merge the company with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the company that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company updateCompany(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.updateCompany(company, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _companyLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_companyLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Company addCompany(java.lang.String webId,
		java.lang.String virtualHostname, java.lang.String mx,
		java.lang.String shardName, boolean system, int maxUsers, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.addCompany(webId, virtualHostname, mx,
			shardName, system, maxUsers, active);
	}

	public com.liferay.portal.model.Company checkCompany(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.checkCompany(webId);
	}

	public com.liferay.portal.model.Company checkCompany(
		java.lang.String webId, java.lang.String mx, java.lang.String shardName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.checkCompany(webId, mx, shardName);
	}

	public void checkCompanyKey(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.checkCompanyKey(companyId);
	}

	public void deleteLogo(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.deleteLogo(companyId);
	}

	public com.liferay.portal.model.Company fetchCompany(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.fetchCompany(companyId);
	}

	public com.liferay.portal.model.Company fetchCompanyByVirtualHost(
		java.lang.String virtualHostname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.fetchCompanyByVirtualHost(virtualHostname);
	}

	public java.util.List<com.liferay.portal.model.Company> getCompanies()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanies();
	}

	public java.util.List<com.liferay.portal.model.Company> getCompanies(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanies(system);
	}

	public int getCompaniesCount(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompaniesCount(system);
	}

	public com.liferay.portal.model.Company getCompanyById(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanyById(companyId);
	}

	public com.liferay.portal.model.Company getCompanyByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanyByLogoId(logoId);
	}

	public com.liferay.portal.model.Company getCompanyByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanyByMx(mx);
	}

	public com.liferay.portal.model.Company getCompanyByVirtualHost(
		java.lang.String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanyByVirtualHost(virtualHostname);
	}

	public com.liferay.portal.model.Company getCompanyByWebId(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.getCompanyByWebId(webId);
	}

	public void removePreferences(long companyId, java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.removePreferences(companyId, keys);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.search(companyId, userId, keywords, start,
			end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long userId, java.lang.String portletId, long groupId,
		java.lang.String type, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.search(companyId, userId, portletId,
			groupId, type, keywords, start, end);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHostname, java.lang.String mx, int maxUsers,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.updateCompany(companyId, virtualHostname,
			mx, maxUsers, active);
	}

	public com.liferay.portal.model.Company updateCompany(long companyId,
		java.lang.String virtualHostname, java.lang.String mx,
		java.lang.String homeURL, java.lang.String name,
		java.lang.String legalName, java.lang.String legalId,
		java.lang.String legalType, java.lang.String sicCode,
		java.lang.String tickerSymbol, java.lang.String industry,
		java.lang.String type, java.lang.String size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _companyLocalService.updateCompany(companyId, virtualHostname,
			mx, homeURL, name, legalName, legalId, legalType, sicCode,
			tickerSymbol, industry, type, size);
	}

	public void updateDisplay(long companyId, java.lang.String languageId,
		java.lang.String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.updateDisplay(companyId, languageId, timeZoneId);
	}

	public void updateLogo(long companyId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.updateLogo(companyId, bytes);
	}

	public void updateLogo(long companyId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.updateLogo(companyId, file);
	}

	public void updateLogo(long companyId, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.updateLogo(companyId, is);
	}

	public void updatePreferences(long companyId,
		com.liferay.portal.kernel.util.UnicodeProperties properties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.updatePreferences(companyId, properties);
	}

	public void updateSecurity(long companyId, java.lang.String authType,
		boolean autoLogin, boolean sendPassword, boolean strangers,
		boolean strangersWithMx, boolean strangersVerify, boolean siteLogo)
		throws com.liferay.portal.kernel.exception.SystemException {
		_companyLocalService.updateSecurity(companyId, authType, autoLogin,
			sendPassword, strangers, strangersWithMx, strangersVerify, siteLogo);
	}

	public CompanyLocalService getWrappedCompanyLocalService() {
		return _companyLocalService;
	}

	public void setWrappedCompanyLocalService(
		CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}

	private CompanyLocalService _companyLocalService;
}