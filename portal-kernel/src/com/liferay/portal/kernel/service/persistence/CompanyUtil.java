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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the company service. This utility wraps <code>com.liferay.portal.service.persistence.impl.CompanyPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyPersistence
 * @generated
 */
@ProviderType
public class CompanyUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Company company) {
		getPersistence().clearCache(company);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Company> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Company> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Company> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Company> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Company> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Company update(Company company) {
		return getPersistence().update(company);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Company update(Company company, ServiceContext serviceContext) {
		return getPersistence().update(company, serviceContext);
	}

	/**
	* Returns the company where webId = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	*
	* @param webId the web ID
	* @return the matching company
	* @throws NoSuchCompanyException if a matching company could not be found
	*/
	public static Company findByWebId(String webId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().findByWebId(webId);
	}

	/**
	* Returns the company where webId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param webId the web ID
	* @return the matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchByWebId(String webId) {
		return getPersistence().fetchByWebId(webId);
	}

	/**
	* Returns the company where webId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param webId the web ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchByWebId(String webId, boolean retrieveFromCache) {
		return getPersistence().fetchByWebId(webId, retrieveFromCache);
	}

	/**
	* Removes the company where webId = &#63; from the database.
	*
	* @param webId the web ID
	* @return the company that was removed
	*/
	public static Company removeByWebId(String webId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().removeByWebId(webId);
	}

	/**
	* Returns the number of companies where webId = &#63;.
	*
	* @param webId the web ID
	* @return the number of matching companies
	*/
	public static int countByWebId(String webId) {
		return getPersistence().countByWebId(webId);
	}

	/**
	* Returns the company where mx = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	*
	* @param mx the mx
	* @return the matching company
	* @throws NoSuchCompanyException if a matching company could not be found
	*/
	public static Company findByMx(String mx)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().findByMx(mx);
	}

	/**
	* Returns the company where mx = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mx the mx
	* @return the matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchByMx(String mx) {
		return getPersistence().fetchByMx(mx);
	}

	/**
	* Returns the company where mx = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mx the mx
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchByMx(String mx, boolean retrieveFromCache) {
		return getPersistence().fetchByMx(mx, retrieveFromCache);
	}

	/**
	* Removes the company where mx = &#63; from the database.
	*
	* @param mx the mx
	* @return the company that was removed
	*/
	public static Company removeByMx(String mx)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().removeByMx(mx);
	}

	/**
	* Returns the number of companies where mx = &#63;.
	*
	* @param mx the mx
	* @return the number of matching companies
	*/
	public static int countByMx(String mx) {
		return getPersistence().countByMx(mx);
	}

	/**
	* Returns the company where logoId = &#63; or throws a <code>NoSuchCompanyException</code> if it could not be found.
	*
	* @param logoId the logo ID
	* @return the matching company
	* @throws NoSuchCompanyException if a matching company could not be found
	*/
	public static Company findByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().findByLogoId(logoId);
	}

	/**
	* Returns the company where logoId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param logoId the logo ID
	* @return the matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchByLogoId(long logoId) {
		return getPersistence().fetchByLogoId(logoId);
	}

	/**
	* Returns the company where logoId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param logoId the logo ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchByLogoId(long logoId, boolean retrieveFromCache) {
		return getPersistence().fetchByLogoId(logoId, retrieveFromCache);
	}

	/**
	* Removes the company where logoId = &#63; from the database.
	*
	* @param logoId the logo ID
	* @return the company that was removed
	*/
	public static Company removeByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().removeByLogoId(logoId);
	}

	/**
	* Returns the number of companies where logoId = &#63;.
	*
	* @param logoId the logo ID
	* @return the number of matching companies
	*/
	public static int countByLogoId(long logoId) {
		return getPersistence().countByLogoId(logoId);
	}

	/**
	* Returns all the companies where system = &#63;.
	*
	* @param system the system
	* @return the matching companies
	*/
	public static List<Company> findBySystem(boolean system) {
		return getPersistence().findBySystem(system);
	}

	/**
	* Returns a range of all the companies where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param system the system
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @return the range of matching companies
	*/
	public static List<Company> findBySystem(boolean system, int start, int end) {
		return getPersistence().findBySystem(system, start, end);
	}

	/**
	* Returns an ordered range of all the companies where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param system the system
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching companies
	*/
	public static List<Company> findBySystem(boolean system, int start,
		int end, OrderByComparator<Company> orderByComparator) {
		return getPersistence()
				   .findBySystem(system, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the companies where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param system the system
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching companies
	*/
	public static List<Company> findBySystem(boolean system, int start,
		int end, OrderByComparator<Company> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBySystem(system, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first company in the ordered set where system = &#63;.
	*
	* @param system the system
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching company
	* @throws NoSuchCompanyException if a matching company could not be found
	*/
	public static Company findBySystem_First(boolean system,
		OrderByComparator<Company> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().findBySystem_First(system, orderByComparator);
	}

	/**
	* Returns the first company in the ordered set where system = &#63;.
	*
	* @param system the system
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchBySystem_First(boolean system,
		OrderByComparator<Company> orderByComparator) {
		return getPersistence().fetchBySystem_First(system, orderByComparator);
	}

	/**
	* Returns the last company in the ordered set where system = &#63;.
	*
	* @param system the system
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching company
	* @throws NoSuchCompanyException if a matching company could not be found
	*/
	public static Company findBySystem_Last(boolean system,
		OrderByComparator<Company> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().findBySystem_Last(system, orderByComparator);
	}

	/**
	* Returns the last company in the ordered set where system = &#63;.
	*
	* @param system the system
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching company, or <code>null</code> if a matching company could not be found
	*/
	public static Company fetchBySystem_Last(boolean system,
		OrderByComparator<Company> orderByComparator) {
		return getPersistence().fetchBySystem_Last(system, orderByComparator);
	}

	/**
	* Returns the companies before and after the current company in the ordered set where system = &#63;.
	*
	* @param companyId the primary key of the current company
	* @param system the system
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next company
	* @throws NoSuchCompanyException if a company with the primary key could not be found
	*/
	public static Company[] findBySystem_PrevAndNext(long companyId,
		boolean system, OrderByComparator<Company> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence()
				   .findBySystem_PrevAndNext(companyId, system,
			orderByComparator);
	}

	/**
	* Removes all the companies where system = &#63; from the database.
	*
	* @param system the system
	*/
	public static void removeBySystem(boolean system) {
		getPersistence().removeBySystem(system);
	}

	/**
	* Returns the number of companies where system = &#63;.
	*
	* @param system the system
	* @return the number of matching companies
	*/
	public static int countBySystem(boolean system) {
		return getPersistence().countBySystem(system);
	}

	/**
	* Caches the company in the entity cache if it is enabled.
	*
	* @param company the company
	*/
	public static void cacheResult(Company company) {
		getPersistence().cacheResult(company);
	}

	/**
	* Caches the companies in the entity cache if it is enabled.
	*
	* @param companies the companies
	*/
	public static void cacheResult(List<Company> companies) {
		getPersistence().cacheResult(companies);
	}

	/**
	* Creates a new company with the primary key. Does not add the company to the database.
	*
	* @param companyId the primary key for the new company
	* @return the new company
	*/
	public static Company create(long companyId) {
		return getPersistence().create(companyId);
	}

	/**
	* Removes the company with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the company
	* @return the company that was removed
	* @throws NoSuchCompanyException if a company with the primary key could not be found
	*/
	public static Company remove(long companyId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().remove(companyId);
	}

	public static Company updateImpl(Company company) {
		return getPersistence().updateImpl(company);
	}

	/**
	* Returns the company with the primary key or throws a <code>NoSuchCompanyException</code> if it could not be found.
	*
	* @param companyId the primary key of the company
	* @return the company
	* @throws NoSuchCompanyException if a company with the primary key could not be found
	*/
	public static Company findByPrimaryKey(long companyId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyException {
		return getPersistence().findByPrimaryKey(companyId);
	}

	/**
	* Returns the company with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param companyId the primary key of the company
	* @return the company, or <code>null</code> if a company with the primary key could not be found
	*/
	public static Company fetchByPrimaryKey(long companyId) {
		return getPersistence().fetchByPrimaryKey(companyId);
	}

	/**
	* Returns all the companies.
	*
	* @return the companies
	*/
	public static List<Company> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @return the range of companies
	*/
	public static List<Company> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of companies
	*/
	public static List<Company> findAll(int start, int end,
		OrderByComparator<Company> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CompanyModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of companies
	* @param end the upper bound of the range of companies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of companies
	*/
	public static List<Company> findAll(int start, int end,
		OrderByComparator<Company> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the companies from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of companies.
	*
	* @return the number of companies
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CompanyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CompanyPersistence)PortalBeanLocatorUtil.locate(CompanyPersistence.class.getName());

			ReferenceRegistry.registerReference(CompanyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static CompanyPersistence _persistence;
}