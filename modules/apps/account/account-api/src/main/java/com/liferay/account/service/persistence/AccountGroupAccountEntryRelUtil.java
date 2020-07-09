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

import com.liferay.account.model.AccountGroupAccountEntryRel;
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
 * The persistence utility for the account group account entry rel service. This utility wraps <code>com.liferay.account.service.persistence.impl.AccountGroupAccountEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupAccountEntryRelPersistence
 * @generated
 */
public class AccountGroupAccountEntryRelUtil {

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
	public static void clearCache(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		getPersistence().clearCache(accountGroupAccountEntryRel);
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
	public static Map<Serializable, AccountGroupAccountEntryRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AccountGroupAccountEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AccountGroupAccountEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AccountGroupAccountEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AccountGroupAccountEntryRel update(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		return getPersistence().update(accountGroupAccountEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AccountGroupAccountEntryRel update(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			accountGroupAccountEntryRel, serviceContext);
	}

	/**
	 * Returns all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId) {

		return getPersistence().findByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns a range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId, int start, int end) {

		return getPersistence().findByAccountGroupId(
			accountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().findByAccountGroupId(
			accountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccountGroupId(
			accountGroupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel findByAccountGroupId_First(
			long accountGroupId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAccountGroupId_First(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the first account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByAccountGroupId_First(
		long accountGroupId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().fetchByAccountGroupId_First(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel findByAccountGroupId_Last(
			long accountGroupId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAccountGroupId_Last(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByAccountGroupId_Last(
		long accountGroupId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().fetchByAccountGroupId_Last(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the account group account entry rels before and after the current account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the current account group account entry rel
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public static AccountGroupAccountEntryRel[]
			findByAccountGroupId_PrevAndNext(
				long AccountGroupAccountEntryRelId, long accountGroupId,
				OrderByComparator<AccountGroupAccountEntryRel>
					orderByComparator)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAccountGroupId_PrevAndNext(
			AccountGroupAccountEntryRelId, accountGroupId, orderByComparator);
	}

	/**
	 * Removes all the account group account entry rels where accountGroupId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 */
	public static void removeByAccountGroupId(long accountGroupId) {
		getPersistence().removeByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns the number of account group account entry rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the number of matching account group account entry rels
	 */
	public static int countByAccountGroupId(long accountGroupId) {
		return getPersistence().countByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId) {

		return getPersistence().findByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns a range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the first account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the account group account entry rels before and after the current account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the current account group account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public static AccountGroupAccountEntryRel[]
			findByAccountEntryId_PrevAndNext(
				long AccountGroupAccountEntryRelId, long accountEntryId,
				OrderByComparator<AccountGroupAccountEntryRel>
					orderByComparator)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAccountEntryId_PrevAndNext(
			AccountGroupAccountEntryRelId, accountEntryId, orderByComparator);
	}

	/**
	 * Removes all the account group account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public static void removeByAccountEntryId(long accountEntryId) {
		getPersistence().removeByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns the number of account group account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account group account entry rels
	 */
	public static int countByAccountEntryId(long accountEntryId) {
		return getPersistence().countByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or throws a <code>NoSuchGroupAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel findByAGI_AEI(
			long accountGroupId, long accountEntryId)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByAGI_AEI(accountGroupId, accountEntryId);
	}

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByAGI_AEI(
		long accountGroupId, long accountEntryId) {

		return getPersistence().fetchByAGI_AEI(accountGroupId, accountEntryId);
	}

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByAGI_AEI(
		long accountGroupId, long accountEntryId, boolean useFinderCache) {

		return getPersistence().fetchByAGI_AEI(
			accountGroupId, accountEntryId, useFinderCache);
	}

	/**
	 * Removes the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the account group account entry rel that was removed
	 */
	public static AccountGroupAccountEntryRel removeByAGI_AEI(
			long accountGroupId, long accountEntryId)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().removeByAGI_AEI(accountGroupId, accountEntryId);
	}

	/**
	 * Returns the number of account group account entry rels where accountGroupId = &#63; and accountEntryId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account group account entry rels
	 */
	public static int countByAGI_AEI(long accountGroupId, long accountEntryId) {
		return getPersistence().countByAGI_AEI(accountGroupId, accountEntryId);
	}

	/**
	 * Caches the account group account entry rel in the entity cache if it is enabled.
	 *
	 * @param accountGroupAccountEntryRel the account group account entry rel
	 */
	public static void cacheResult(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		getPersistence().cacheResult(accountGroupAccountEntryRel);
	}

	/**
	 * Caches the account group account entry rels in the entity cache if it is enabled.
	 *
	 * @param accountGroupAccountEntryRels the account group account entry rels
	 */
	public static void cacheResult(
		List<AccountGroupAccountEntryRel> accountGroupAccountEntryRels) {

		getPersistence().cacheResult(accountGroupAccountEntryRels);
	}

	/**
	 * Creates a new account group account entry rel with the primary key. Does not add the account group account entry rel to the database.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key for the new account group account entry rel
	 * @return the new account group account entry rel
	 */
	public static AccountGroupAccountEntryRel create(
		long AccountGroupAccountEntryRelId) {

		return getPersistence().create(AccountGroupAccountEntryRelId);
	}

	/**
	 * Removes the account group account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel that was removed
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public static AccountGroupAccountEntryRel remove(
			long AccountGroupAccountEntryRelId)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().remove(AccountGroupAccountEntryRelId);
	}

	public static AccountGroupAccountEntryRel updateImpl(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		return getPersistence().updateImpl(accountGroupAccountEntryRel);
	}

	/**
	 * Returns the account group account entry rel with the primary key or throws a <code>NoSuchGroupAccountEntryRelException</code> if it could not be found.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	public static AccountGroupAccountEntryRel findByPrimaryKey(
			long AccountGroupAccountEntryRelId)
		throws com.liferay.account.exception.
			NoSuchGroupAccountEntryRelException {

		return getPersistence().findByPrimaryKey(AccountGroupAccountEntryRelId);
	}

	/**
	 * Returns the account group account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel, or <code>null</code> if a account group account entry rel with the primary key could not be found
	 */
	public static AccountGroupAccountEntryRel fetchByPrimaryKey(
		long AccountGroupAccountEntryRelId) {

		return getPersistence().fetchByPrimaryKey(
			AccountGroupAccountEntryRelId);
	}

	/**
	 * Returns all the account group account entry rels.
	 *
	 * @return the account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account group account entry rels
	 */
	public static List<AccountGroupAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the account group account entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of account group account entry rels.
	 *
	 * @return the number of account group account entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AccountGroupAccountEntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountGroupAccountEntryRelPersistence,
		 AccountGroupAccountEntryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountGroupAccountEntryRelPersistence.class);

		ServiceTracker
			<AccountGroupAccountEntryRelPersistence,
			 AccountGroupAccountEntryRelPersistence> serviceTracker =
				new ServiceTracker
					<AccountGroupAccountEntryRelPersistence,
					 AccountGroupAccountEntryRelPersistence>(
						 bundle.getBundleContext(),
						 AccountGroupAccountEntryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}