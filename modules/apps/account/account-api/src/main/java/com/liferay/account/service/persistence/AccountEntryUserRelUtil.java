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

package com.liferay.account.service.persistence;

import com.liferay.account.model.AccountEntryUserRel;
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
 * The persistence utility for the account entry user rel service. This utility wraps <code>com.liferay.account.service.persistence.impl.AccountEntryUserRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelPersistence
 * @generated
 */
public class AccountEntryUserRelUtil {

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
	public static void clearCache(AccountEntryUserRel accountEntryUserRel) {
		getPersistence().clearCache(accountEntryUserRel);
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
	public static Map<Serializable, AccountEntryUserRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AccountEntryUserRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AccountEntryUserRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AccountEntryUserRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AccountEntryUserRel update(
		AccountEntryUserRel accountEntryUserRel) {

		return getPersistence().update(accountEntryUserRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AccountEntryUserRel update(
		AccountEntryUserRel accountEntryUserRel,
		ServiceContext serviceContext) {

		return getPersistence().update(accountEntryUserRel, serviceContext);
	}

	/**
	 * Returns all the account entry user rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId) {

		return getPersistence().findByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns a range of all the account entry user rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the first account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the account entry user rels before and after the current account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryUserRelId the primary key of the current account entry user rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public static AccountEntryUserRel[] findByAccountEntryId_PrevAndNext(
			long accountEntryUserRelId, long accountEntryId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAccountEntryId_PrevAndNext(
			accountEntryUserRelId, accountEntryId, orderByComparator);
	}

	/**
	 * Removes all the account entry user rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public static void removeByAccountEntryId(long accountEntryId) {
		getPersistence().removeByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns the number of account entry user rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry user rels
	 */
	public static int countByAccountEntryId(long accountEntryId) {
		return getPersistence().countByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns all the account entry user rels where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId) {

		return getPersistence().findByAccountUserId(accountUserId);
	}

	/**
	 * Returns a range of all the account entry user rels where accountUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountUserId the account user ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end) {

		return getPersistence().findByAccountUserId(accountUserId, start, end);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountUserId the account user ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().findByAccountUserId(
			accountUserId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountUserId the account user ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry user rels
	 */
	public static List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccountUserId(
			accountUserId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel findByAccountUserId_First(
			long accountUserId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAccountUserId_First(
			accountUserId, orderByComparator);
	}

	/**
	 * Returns the first account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel fetchByAccountUserId_First(
		long accountUserId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().fetchByAccountUserId_First(
			accountUserId, orderByComparator);
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel findByAccountUserId_Last(
			long accountUserId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAccountUserId_Last(
			accountUserId, orderByComparator);
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel fetchByAccountUserId_Last(
		long accountUserId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().fetchByAccountUserId_Last(
			accountUserId, orderByComparator);
	}

	/**
	 * Returns the account entry user rels before and after the current account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountEntryUserRelId the primary key of the current account entry user rel
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public static AccountEntryUserRel[] findByAccountUserId_PrevAndNext(
			long accountEntryUserRelId, long accountUserId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAccountUserId_PrevAndNext(
			accountEntryUserRelId, accountUserId, orderByComparator);
	}

	/**
	 * Removes all the account entry user rels where accountUserId = &#63; from the database.
	 *
	 * @param accountUserId the account user ID
	 */
	public static void removeByAccountUserId(long accountUserId) {
		getPersistence().removeByAccountUserId(accountUserId);
	}

	/**
	 * Returns the number of account entry user rels where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @return the number of matching account entry user rels
	 */
	public static int countByAccountUserId(long accountUserId) {
		return getPersistence().countByAccountUserId(accountUserId);
	}

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel findByAEI_AUI(
			long accountEntryId, long accountUserId)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByAEI_AUI(accountEntryId, accountUserId);
	}

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel fetchByAEI_AUI(
		long accountEntryId, long accountUserId) {

		return getPersistence().fetchByAEI_AUI(accountEntryId, accountUserId);
	}

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public static AccountEntryUserRel fetchByAEI_AUI(
		long accountEntryId, long accountUserId, boolean useFinderCache) {

		return getPersistence().fetchByAEI_AUI(
			accountEntryId, accountUserId, useFinderCache);
	}

	/**
	 * Removes the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the account entry user rel that was removed
	 */
	public static AccountEntryUserRel removeByAEI_AUI(
			long accountEntryId, long accountUserId)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().removeByAEI_AUI(accountEntryId, accountUserId);
	}

	/**
	 * Returns the number of account entry user rels where accountEntryId = &#63; and accountUserId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the number of matching account entry user rels
	 */
	public static int countByAEI_AUI(long accountEntryId, long accountUserId) {
		return getPersistence().countByAEI_AUI(accountEntryId, accountUserId);
	}

	/**
	 * Caches the account entry user rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 */
	public static void cacheResult(AccountEntryUserRel accountEntryUserRel) {
		getPersistence().cacheResult(accountEntryUserRel);
	}

	/**
	 * Caches the account entry user rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRels the account entry user rels
	 */
	public static void cacheResult(
		List<AccountEntryUserRel> accountEntryUserRels) {

		getPersistence().cacheResult(accountEntryUserRels);
	}

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelId the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	public static AccountEntryUserRel create(long accountEntryUserRelId) {
		return getPersistence().create(accountEntryUserRelId);
	}

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public static AccountEntryUserRel remove(long accountEntryUserRelId)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().remove(accountEntryUserRelId);
	}

	public static AccountEntryUserRel updateImpl(
		AccountEntryUserRel accountEntryUserRel) {

		return getPersistence().updateImpl(accountEntryUserRel);
	}

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public static AccountEntryUserRel findByPrimaryKey(
			long accountEntryUserRelId)
		throws com.liferay.account.exception.NoSuchEntryUserRelException {

		return getPersistence().findByPrimaryKey(accountEntryUserRelId);
	}

	/**
	 * Returns the account entry user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel, or <code>null</code> if a account entry user rel with the primary key could not be found
	 */
	public static AccountEntryUserRel fetchByPrimaryKey(
		long accountEntryUserRelId) {

		return getPersistence().fetchByPrimaryKey(accountEntryUserRelId);
	}

	/**
	 * Returns all the account entry user rels.
	 *
	 * @return the account entry user rels
	 */
	public static List<AccountEntryUserRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of account entry user rels
	 */
	public static List<AccountEntryUserRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry user rels
	 */
	public static List<AccountEntryUserRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry user rels
	 */
	public static List<AccountEntryUserRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the account entry user rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AccountEntryUserRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountEntryUserRelPersistence, AccountEntryUserRelPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountEntryUserRelPersistence.class);

		ServiceTracker
			<AccountEntryUserRelPersistence, AccountEntryUserRelPersistence>
				serviceTracker =
					new ServiceTracker
						<AccountEntryUserRelPersistence,
						 AccountEntryUserRelPersistence>(
							 bundle.getBundleContext(),
							 AccountEntryUserRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}