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

package com.liferay.commerce.account.service.persistence;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce account service. This utility wraps <code>com.liferay.commerce.account.service.persistence.impl.CommerceAccountPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountPersistence
 * @generated
 */
public class CommerceAccountUtil {

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
	public static void clearCache(CommerceAccount commerceAccount) {
		getPersistence().clearCache(commerceAccount);
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
	public static Map<Serializable, CommerceAccount> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceAccount> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAccount> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAccount> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAccount update(CommerceAccount commerceAccount) {
		return getPersistence().update(commerceAccount);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAccount update(
		CommerceAccount commerceAccount, ServiceContext serviceContext) {

		return getPersistence().update(commerceAccount, serviceContext);
	}

	/**
	 * Returns all the commerce accounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce accounts
	 */
	public static List<CommerceAccount> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce accounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts
	 */
	public static List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts
	 */
	public static List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce accounts
	 */
	public static List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public static CommerceAccount findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static CommerceAccount fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public static CommerceAccount findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static CommerceAccount fetchByCompanyId_Last(
		long companyId, OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount[] findByCompanyId_PrevAndNext(
			long commerceAccountId, long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceAccountId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce accounts that the user has permission to view
	 */
	public static List<CommerceAccount> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts that the user has permission to view
	 */
	public static List<CommerceAccount> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce accounts that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts that the user has permission to view
	 */
	public static List<CommerceAccount> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set of commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount[] filterFindByCompanyId_PrevAndNext(
			long commerceAccountId, long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceAccountId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce accounts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce accounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce accounts
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce accounts that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce accounts
	 */
	public static List<CommerceAccount> findByU_T(long userId, int type) {
		return getPersistence().findByU_T(userId, type);
	}

	/**
	 * Returns a range of all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts
	 */
	public static List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end) {

		return getPersistence().findByU_T(userId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts
	 */
	public static List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().findByU_T(
			userId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce accounts
	 */
	public static List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_T(
			userId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public static CommerceAccount findByU_T_First(
			long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByU_T_First(
			userId, type, orderByComparator);
	}

	/**
	 * Returns the first commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static CommerceAccount fetchByU_T_First(
		long userId, int type,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().fetchByU_T_First(
			userId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public static CommerceAccount findByU_T_Last(
			long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByU_T_Last(userId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static CommerceAccount fetchByU_T_Last(
		long userId, int type,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().fetchByU_T_Last(
			userId, type, orderByComparator);
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount[] findByU_T_PrevAndNext(
			long commerceAccountId, long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByU_T_PrevAndNext(
			commerceAccountId, userId, type, orderByComparator);
	}

	/**
	 * Returns all the commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce accounts that the user has permission to view
	 */
	public static List<CommerceAccount> filterFindByU_T(long userId, int type) {
		return getPersistence().filterFindByU_T(userId, type);
	}

	/**
	 * Returns a range of all the commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts that the user has permission to view
	 */
	public static List<CommerceAccount> filterFindByU_T(
		long userId, int type, int start, int end) {

		return getPersistence().filterFindByU_T(userId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce accounts that the user has permissions to view where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts that the user has permission to view
	 */
	public static List<CommerceAccount> filterFindByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().filterFindByU_T(
			userId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set of commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount[] filterFindByU_T_PrevAndNext(
			long commerceAccountId, long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().filterFindByU_T_PrevAndNext(
			commerceAccountId, userId, type, orderByComparator);
	}

	/**
	 * Removes all the commerce accounts where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 */
	public static void removeByU_T(long userId, int type) {
		getPersistence().removeByU_T(userId, type);
	}

	/**
	 * Returns the number of commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce accounts
	 */
	public static int countByU_T(long userId, int type) {
		return getPersistence().countByU_T(userId, type);
	}

	/**
	 * Returns the number of commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce accounts that the user has permission to view
	 */
	public static int filterCountByU_T(long userId, int type) {
		return getPersistence().filterCountByU_T(userId, type);
	}

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public static CommerceAccount findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static CommerceAccount fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static CommerceAccount fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce account where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account that was removed
	 */
	public static CommerceAccount removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce accounts where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce accounts
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce account in the entity cache if it is enabled.
	 *
	 * @param commerceAccount the commerce account
	 */
	public static void cacheResult(CommerceAccount commerceAccount) {
		getPersistence().cacheResult(commerceAccount);
	}

	/**
	 * Caches the commerce accounts in the entity cache if it is enabled.
	 *
	 * @param commerceAccounts the commerce accounts
	 */
	public static void cacheResult(List<CommerceAccount> commerceAccounts) {
		getPersistence().cacheResult(commerceAccounts);
	}

	/**
	 * Creates a new commerce account with the primary key. Does not add the commerce account to the database.
	 *
	 * @param commerceAccountId the primary key for the new commerce account
	 * @return the new commerce account
	 */
	public static CommerceAccount create(long commerceAccountId) {
		return getPersistence().create(commerceAccountId);
	}

	/**
	 * Removes the commerce account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account that was removed
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount remove(long commerceAccountId)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().remove(commerceAccountId);
	}

	public static CommerceAccount updateImpl(CommerceAccount commerceAccount) {
		return getPersistence().updateImpl(commerceAccount);
	}

	/**
	 * Returns the commerce account with the primary key or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount findByPrimaryKey(long commerceAccountId)
		throws com.liferay.commerce.account.exception.NoSuchAccountException {

		return getPersistence().findByPrimaryKey(commerceAccountId);
	}

	/**
	 * Returns the commerce account with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account, or <code>null</code> if a commerce account with the primary key could not be found
	 */
	public static CommerceAccount fetchByPrimaryKey(long commerceAccountId) {
		return getPersistence().fetchByPrimaryKey(commerceAccountId);
	}

	/**
	 * Returns all the commerce accounts.
	 *
	 * @return the commerce accounts
	 */
	public static List<CommerceAccount> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of commerce accounts
	 */
	public static List<CommerceAccount> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce accounts
	 */
	public static List<CommerceAccount> findAll(
		int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce accounts
	 */
	public static List<CommerceAccount> findAll(
		int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce accounts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce accounts.
	 *
	 * @return the number of commerce accounts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceAccountPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountPersistence, CommerceAccountPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountPersistence.class);

		ServiceTracker<CommerceAccountPersistence, CommerceAccountPersistence>
			serviceTracker =
				new ServiceTracker
					<CommerceAccountPersistence, CommerceAccountPersistence>(
						bundle.getBundleContext(),
						CommerceAccountPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}