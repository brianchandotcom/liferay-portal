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

import com.liferay.account.model.AccountEntryOrganizationRel;
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
 * The persistence utility for the account entry organization rel service. This utility wraps <code>com.liferay.account.service.persistence.impl.AccountEntryOrganizationRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelPersistence
 * @generated
 */
public class AccountEntryOrganizationRelUtil {

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
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		getPersistence().clearCache(accountEntryOrganizationRel);
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
	public static Map<Serializable, AccountEntryOrganizationRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AccountEntryOrganizationRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AccountEntryOrganizationRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AccountEntryOrganizationRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AccountEntryOrganizationRel update(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		return getPersistence().update(accountEntryOrganizationRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AccountEntryOrganizationRel update(
		AccountEntryOrganizationRel accountEntryOrganizationRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			accountEntryOrganizationRel, serviceContext);
	}

	/**
	 * Returns all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId) {

		return getPersistence().findByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns a range of all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @return the range of matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the first account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the account entry organization rels before and after the current account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the current account entry organization rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public static AccountEntryOrganizationRel[]
			findByAccountEntryId_PrevAndNext(
				long accountEntryOrganizationRelId, long accountEntryId,
				OrderByComparator<AccountEntryOrganizationRel>
					orderByComparator)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByAccountEntryId_PrevAndNext(
			accountEntryOrganizationRelId, accountEntryId, orderByComparator);
	}

	/**
	 * Removes all the account entry organization rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public static void removeByAccountEntryId(long accountEntryId) {
		getPersistence().removeByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns the number of account entry organization rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry organization rels
	 */
	public static int countByAccountEntryId(long accountEntryId) {
		return getPersistence().countByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns all the account entry organization rels where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByOrganizationId(
		long organizationId) {

		return getPersistence().findByOrganizationId(organizationId);
	}

	/**
	 * Returns a range of all the account entry organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @return the range of matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end) {

		return getPersistence().findByOrganizationId(
			organizationId, start, end);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().findByOrganizationId(
			organizationId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOrganizationId(
			organizationId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account entry organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel findByOrganizationId_First(
			long organizationId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByOrganizationId_First(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the first account entry organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel fetchByOrganizationId_First(
		long organizationId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().fetchByOrganizationId_First(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the last account entry organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel findByOrganizationId_Last(
			long organizationId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByOrganizationId_Last(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the last account entry organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel fetchByOrganizationId_Last(
		long organizationId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().fetchByOrganizationId_Last(
			organizationId, orderByComparator);
	}

	/**
	 * Returns the account entry organization rels before and after the current account entry organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the current account entry organization rel
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public static AccountEntryOrganizationRel[]
			findByOrganizationId_PrevAndNext(
				long accountEntryOrganizationRelId, long organizationId,
				OrderByComparator<AccountEntryOrganizationRel>
					orderByComparator)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByOrganizationId_PrevAndNext(
			accountEntryOrganizationRelId, organizationId, orderByComparator);
	}

	/**
	 * Removes all the account entry organization rels where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 */
	public static void removeByOrganizationId(long organizationId) {
		getPersistence().removeByOrganizationId(organizationId);
	}

	/**
	 * Returns the number of account entry organization rels where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching account entry organization rels
	 */
	public static int countByOrganizationId(long organizationId) {
		return getPersistence().countByOrganizationId(organizationId);
	}

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or throws a <code>NoSuchEntryOrganizationRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel findByA_O(
			long accountEntryId, long organizationId)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByA_O(accountEntryId, organizationId);
	}

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel fetchByA_O(
		long accountEntryId, long organizationId) {

		return getPersistence().fetchByA_O(accountEntryId, organizationId);
	}

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public static AccountEntryOrganizationRel fetchByA_O(
		long accountEntryId, long organizationId, boolean useFinderCache) {

		return getPersistence().fetchByA_O(
			accountEntryId, organizationId, useFinderCache);
	}

	/**
	 * Removes the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the account entry organization rel that was removed
	 */
	public static AccountEntryOrganizationRel removeByA_O(
			long accountEntryId, long organizationId)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().removeByA_O(accountEntryId, organizationId);
	}

	/**
	 * Returns the number of account entry organization rels where accountEntryId = &#63; and organizationId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the number of matching account entry organization rels
	 */
	public static int countByA_O(long accountEntryId, long organizationId) {
		return getPersistence().countByA_O(accountEntryId, organizationId);
	}

	/**
	 * Caches the account entry organization rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 */
	public static void cacheResult(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		getPersistence().cacheResult(accountEntryOrganizationRel);
	}

	/**
	 * Caches the account entry organization rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryOrganizationRels the account entry organization rels
	 */
	public static void cacheResult(
		List<AccountEntryOrganizationRel> accountEntryOrganizationRels) {

		getPersistence().cacheResult(accountEntryOrganizationRels);
	}

	/**
	 * Creates a new account entry organization rel with the primary key. Does not add the account entry organization rel to the database.
	 *
	 * @param accountEntryOrganizationRelId the primary key for the new account entry organization rel
	 * @return the new account entry organization rel
	 */
	public static AccountEntryOrganizationRel create(
		long accountEntryOrganizationRelId) {

		return getPersistence().create(accountEntryOrganizationRelId);
	}

	/**
	 * Removes the account entry organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel that was removed
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public static AccountEntryOrganizationRel remove(
			long accountEntryOrganizationRelId)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().remove(accountEntryOrganizationRelId);
	}

	public static AccountEntryOrganizationRel updateImpl(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		return getPersistence().updateImpl(accountEntryOrganizationRel);
	}

	/**
	 * Returns the account entry organization rel with the primary key or throws a <code>NoSuchEntryOrganizationRelException</code> if it could not be found.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public static AccountEntryOrganizationRel findByPrimaryKey(
			long accountEntryOrganizationRelId)
		throws com.liferay.account.exception.
			NoSuchEntryOrganizationRelException {

		return getPersistence().findByPrimaryKey(accountEntryOrganizationRelId);
	}

	/**
	 * Returns the account entry organization rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel, or <code>null</code> if a account entry organization rel with the primary key could not be found
	 */
	public static AccountEntryOrganizationRel fetchByPrimaryKey(
		long accountEntryOrganizationRelId) {

		return getPersistence().fetchByPrimaryKey(
			accountEntryOrganizationRelId);
	}

	/**
	 * Returns all the account entry organization rels.
	 *
	 * @return the account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @return the range of account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry organization rels
	 */
	public static List<AccountEntryOrganizationRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the account entry organization rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of account entry organization rels.
	 *
	 * @return the number of account entry organization rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AccountEntryOrganizationRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountEntryOrganizationRelPersistence,
		 AccountEntryOrganizationRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountEntryOrganizationRelPersistence.class);

		ServiceTracker
			<AccountEntryOrganizationRelPersistence,
			 AccountEntryOrganizationRelPersistence> serviceTracker =
				new ServiceTracker
					<AccountEntryOrganizationRelPersistence,
					 AccountEntryOrganizationRelPersistence>(
						 bundle.getBundleContext(),
						 AccountEntryOrganizationRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}