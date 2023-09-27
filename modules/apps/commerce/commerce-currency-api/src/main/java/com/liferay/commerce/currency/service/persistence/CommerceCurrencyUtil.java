/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.service.persistence;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce currency service. This utility wraps <code>com.liferay.commerce.currency.service.persistence.impl.CommerceCurrencyPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyPersistence
 * @generated
 */
public class CommerceCurrencyUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CommerceCurrency commerceCurrency) {
		getPersistence().clearCache(commerceCurrency);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommerceCurrency> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceCurrency> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCurrency> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCurrency> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCurrency update(CommerceCurrency commerceCurrency) {
		return getPersistence().update(commerceCurrency);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCurrency update(
		CommerceCurrency commerceCurrency, ServiceContext serviceContext) {

		return getPersistence().update(commerceCurrency, serviceContext);
	}

	/**
	 * Returns all the commerce currencies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce currencies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByUuid_First(
			String uuid, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByUuid_First(
		String uuid, OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByUuid_Last(
			String uuid, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where uuid = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency[] findByUuid_PrevAndNext(
			long commerceCurrencyId, String uuid,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceCurrencyId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce currencies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce currencies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce currencies
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency[] findByUuid_C_PrevAndNext(
			long commerceCurrencyId, String uuid, long companyId,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceCurrencyId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce currencies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce currencies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce currencies
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce currencies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce currencies
	 */
	public static List<CommerceCurrency> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByCompanyId_Last(
		long companyId, OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency[] findByCompanyId_PrevAndNext(
			long commerceCurrencyId, long companyId,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceCurrencyId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce currencies where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce currencies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce currencies
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the commerce currency where companyId = &#63; and code = &#63; or throws a <code>NoSuchCurrencyException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_C(long companyId, String code)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_C(companyId, code);
	}

	/**
	 * Returns the commerce currency where companyId = &#63; and code = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_C(long companyId, String code) {
		return getPersistence().fetchByC_C(companyId, code);
	}

	/**
	 * Returns the commerce currency where companyId = &#63; and code = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_C(
		long companyId, String code, boolean useFinderCache) {

		return getPersistence().fetchByC_C(companyId, code, useFinderCache);
	}

	/**
	 * Removes the commerce currency where companyId = &#63; and code = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the commerce currency that was removed
	 */
	public static CommerceCurrency removeByC_C(long companyId, String code)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().removeByC_C(companyId, code);
	}

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and code = &#63;.
	 *
	 * @param companyId the company ID
	 * @param code the code
	 * @return the number of matching commerce currencies
	 */
	public static int countByC_C(long companyId, String code) {
		return getPersistence().countByC_C(companyId, code);
	}

	/**
	 * Returns all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @return the matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P(
		long companyId, boolean primary) {

		return getPersistence().findByC_P(companyId, primary);
	}

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P(
		long companyId, boolean primary, int start, int end) {

		return getPersistence().findByC_P(companyId, primary, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P(
		long companyId, boolean primary, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findByC_P(
			companyId, primary, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P(
		long companyId, boolean primary, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_P(
			companyId, primary, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_P_First(
			long companyId, boolean primary,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_P_First(
			companyId, primary, orderByComparator);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_P_First(
		long companyId, boolean primary,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByC_P_First(
			companyId, primary, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_P_Last(
			long companyId, boolean primary,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_P_Last(
			companyId, primary, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_P_Last(
		long companyId, boolean primary,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByC_P_Last(
			companyId, primary, orderByComparator);
	}

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63; and primary = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency[] findByC_P_PrevAndNext(
			long commerceCurrencyId, long companyId, boolean primary,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_P_PrevAndNext(
			commerceCurrencyId, companyId, primary, orderByComparator);
	}

	/**
	 * Removes all the commerce currencies where companyId = &#63; and primary = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 */
	public static void removeByC_P(long companyId, boolean primary) {
		getPersistence().removeByC_P(companyId, primary);
	}

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and primary = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @return the number of matching commerce currencies
	 */
	public static int countByC_P(long companyId, boolean primary) {
		return getPersistence().countByC_P(companyId, primary);
	}

	/**
	 * Returns all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_A(
		long companyId, boolean active) {

		return getPersistence().findByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency[] findByC_A_PrevAndNext(
			long commerceCurrencyId, long companyId, boolean active,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_A_PrevAndNext(
			commerceCurrencyId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the commerce currencies where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce currencies
	 */
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	 * Returns all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @return the matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active) {

		return getPersistence().findByC_P_A(companyId, primary, active);
	}

	/**
	 * Returns a range of all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active, int start, int end) {

		return getPersistence().findByC_P_A(
			companyId, primary, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findByC_P_A(
			companyId, primary, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce currencies
	 */
	public static List<CommerceCurrency> findByC_P_A(
		long companyId, boolean primary, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_P_A(
			companyId, primary, active, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_P_A_First(
			long companyId, boolean primary, boolean active,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_P_A_First(
			companyId, primary, active, orderByComparator);
	}

	/**
	 * Returns the first commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_P_A_First(
		long companyId, boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByC_P_A_First(
			companyId, primary, active, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency
	 * @throws NoSuchCurrencyException if a matching commerce currency could not be found
	 */
	public static CommerceCurrency findByC_P_A_Last(
			long companyId, boolean primary, boolean active,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_P_A_Last(
			companyId, primary, active, orderByComparator);
	}

	/**
	 * Returns the last commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	 */
	public static CommerceCurrency fetchByC_P_A_Last(
		long companyId, boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().fetchByC_P_A_Last(
			companyId, primary, active, orderByComparator);
	}

	/**
	 * Returns the commerce currencies before and after the current commerce currency in the ordered set where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param commerceCurrencyId the primary key of the current commerce currency
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency[] findByC_P_A_PrevAndNext(
			long commerceCurrencyId, long companyId, boolean primary,
			boolean active,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByC_P_A_PrevAndNext(
			commerceCurrencyId, companyId, primary, active, orderByComparator);
	}

	/**
	 * Removes all the commerce currencies where companyId = &#63; and primary = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 */
	public static void removeByC_P_A(
		long companyId, boolean primary, boolean active) {

		getPersistence().removeByC_P_A(companyId, primary, active);
	}

	/**
	 * Returns the number of commerce currencies where companyId = &#63; and primary = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param active the active
	 * @return the number of matching commerce currencies
	 */
	public static int countByC_P_A(
		long companyId, boolean primary, boolean active) {

		return getPersistence().countByC_P_A(companyId, primary, active);
	}

	/**
	 * Caches the commerce currency in the entity cache if it is enabled.
	 *
	 * @param commerceCurrency the commerce currency
	 */
	public static void cacheResult(CommerceCurrency commerceCurrency) {
		getPersistence().cacheResult(commerceCurrency);
	}

	/**
	 * Caches the commerce currencies in the entity cache if it is enabled.
	 *
	 * @param commerceCurrencies the commerce currencies
	 */
	public static void cacheResult(List<CommerceCurrency> commerceCurrencies) {
		getPersistence().cacheResult(commerceCurrencies);
	}

	/**
	 * Creates a new commerce currency with the primary key. Does not add the commerce currency to the database.
	 *
	 * @param commerceCurrencyId the primary key for the new commerce currency
	 * @return the new commerce currency
	 */
	public static CommerceCurrency create(long commerceCurrencyId) {
		return getPersistence().create(commerceCurrencyId);
	}

	/**
	 * Removes the commerce currency with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCurrencyId the primary key of the commerce currency
	 * @return the commerce currency that was removed
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency remove(long commerceCurrencyId)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().remove(commerceCurrencyId);
	}

	public static CommerceCurrency updateImpl(
		CommerceCurrency commerceCurrency) {

		return getPersistence().updateImpl(commerceCurrency);
	}

	/**
	 * Returns the commerce currency with the primary key or throws a <code>NoSuchCurrencyException</code> if it could not be found.
	 *
	 * @param commerceCurrencyId the primary key of the commerce currency
	 * @return the commerce currency
	 * @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency findByPrimaryKey(long commerceCurrencyId)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {

		return getPersistence().findByPrimaryKey(commerceCurrencyId);
	}

	/**
	 * Returns the commerce currency with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCurrencyId the primary key of the commerce currency
	 * @return the commerce currency, or <code>null</code> if a commerce currency with the primary key could not be found
	 */
	public static CommerceCurrency fetchByPrimaryKey(long commerceCurrencyId) {
		return getPersistence().fetchByPrimaryKey(commerceCurrencyId);
	}

	/**
	 * Returns all the commerce currencies.
	 *
	 * @return the commerce currencies
	 */
	public static List<CommerceCurrency> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce currencies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @return the range of commerce currencies
	 */
	public static List<CommerceCurrency> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce currencies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce currencies
	 */
	public static List<CommerceCurrency> findAll(
		int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce currencies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCurrencyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce currencies
	 * @param end the upper bound of the range of commerce currencies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce currencies
	 */
	public static List<CommerceCurrency> findAll(
		int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce currencies from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce currencies.
	 *
	 * @return the number of commerce currencies
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceCurrencyPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CommerceCurrencyPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CommerceCurrencyPersistence _persistence;

}