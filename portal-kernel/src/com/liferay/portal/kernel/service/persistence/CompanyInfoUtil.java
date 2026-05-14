/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the company info service. This utility wraps <code>com.liferay.portal.service.persistence.impl.CompanyInfoPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyInfoPersistence
 * @generated
 */
public class CompanyInfoUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<CompanyInfo> companyInfos) {
		getPersistence().cacheResult(companyInfos);
	}

	/**
	 * @see BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(CompanyInfo companyInfo) {
		getPersistence().cacheResult(companyInfo);
	}

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CompanyInfo companyInfo) {
		getPersistence().clearCache(companyInfo);
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
	public static Map<Serializable, CompanyInfo> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CompanyInfo> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CompanyInfo> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CompanyInfo> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CompanyInfo> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CompanyInfo update(CompanyInfo companyInfo) {
		return getPersistence().update(companyInfo);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CompanyInfo update(
		CompanyInfo companyInfo, ServiceContext serviceContext) {

		return getPersistence().update(companyInfo, serviceContext);
	}

	/**
	 * Returns the company info where companyId = &#63; or throws a <code>NoSuchCompanyInfoException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @return the matching company info
	 * @throws NoSuchCompanyInfoException if a matching company info could not be found
	 */
	public static CompanyInfo findByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyInfoException {

		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns the company info where companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company info, or <code>null</code> if a matching company info could not be found
	 */
	public static CompanyInfo fetchByCompanyId(
		long companyId, boolean useFinderCache) {

		return getPersistence().fetchByCompanyId(companyId, useFinderCache);
	}

	/**
	 * Removes the company info where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @return the company info that was removed
	 */
	public static CompanyInfo removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyInfoException {

		return getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of company infos where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching company infos
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Creates a new company info with the primary key. Does not add the company info to the database.
	 *
	 * @param companyInfoId the primary key for the new company info
	 * @return the new company info
	 */
	public static CompanyInfo create(long companyInfoId) {
		return getPersistence().create(companyInfoId);
	}

	/**
	 * Removes the company info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info that was removed
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	public static CompanyInfo remove(long companyInfoId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyInfoException {

		return getPersistence().remove(companyInfoId);
	}

	public static CompanyInfo updateImpl(CompanyInfo companyInfo) {
		return getPersistence().updateImpl(companyInfo);
	}

	/**
	 * Returns the company info with the primary key or throws a <code>NoSuchCompanyInfoException</code> if it could not be found.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info
	 * @throws NoSuchCompanyInfoException if a company info with the primary key could not be found
	 */
	public static CompanyInfo findByPrimaryKey(long companyInfoId)
		throws com.liferay.portal.kernel.exception.NoSuchCompanyInfoException {

		return getPersistence().findByPrimaryKey(companyInfoId);
	}

	/**
	 * Returns the company info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info, or <code>null</code> if a company info with the primary key could not be found
	 */
	public static CompanyInfo fetchByPrimaryKey(long companyInfoId) {
		return getPersistence().fetchByPrimaryKey(companyInfoId);
	}

	/**
	 * Returns the company info where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching company info, or <code>null</code> if a matching company info could not be found
	 */
	public static CompanyInfo fetchByCompanyId(long companyId) {
		return getPersistence().fetchByCompanyId(companyId);
	}

	public static CompanyInfoPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CompanyInfoPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CompanyInfoPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-855650401