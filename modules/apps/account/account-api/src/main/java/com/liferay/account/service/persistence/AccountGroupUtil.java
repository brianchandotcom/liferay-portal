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

import com.liferay.account.model.AccountGroup;
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
 * The persistence utility for the account group service. This utility wraps <code>com.liferay.account.service.persistence.impl.AccountGroupPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupPersistence
 * @generated
 */
public class AccountGroupUtil {

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
	public static void clearCache(AccountGroup accountGroup) {
		getPersistence().clearCache(accountGroup);
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
	public static Map<Serializable, AccountGroup> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AccountGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AccountGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AccountGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AccountGroup update(AccountGroup accountGroup) {
		return getPersistence().update(accountGroup);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AccountGroup update(
		AccountGroup accountGroup, ServiceContext serviceContext) {

		return getPersistence().update(accountGroup, serviceContext);
	}

	/**
	 * Returns all the account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching account groups
	 */
	public static List<AccountGroup> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @return the range of matching account groups
	 */
	public static List<AccountGroup> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account groups
	 */
	public static List<AccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account groups
	 */
	public static List<AccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	public static AccountGroup findByCompanyId_First(
			long companyId, OrderByComparator<AccountGroup> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static AccountGroup fetchByCompanyId_First(
		long companyId, OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	public static AccountGroup findByCompanyId_Last(
			long companyId, OrderByComparator<AccountGroup> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static AccountGroup fetchByCompanyId_Last(
		long companyId, OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the account groups before and after the current account group in the ordered set where companyId = &#63;.
	 *
	 * @param accountGroupId the primary key of the current account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	public static AccountGroup[] findByCompanyId_PrevAndNext(
			long accountGroupId, long companyId,
			OrderByComparator<AccountGroup> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByCompanyId_PrevAndNext(
			accountGroupId, companyId, orderByComparator);
	}

	/**
	 * Removes all the account groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching account groups
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the account groups where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @return the matching account groups
	 */
	public static List<AccountGroup> findByC_D(
		long companyId, boolean defaultAccountGroup) {

		return getPersistence().findByC_D(companyId, defaultAccountGroup);
	}

	/**
	 * Returns a range of all the account groups where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @return the range of matching account groups
	 */
	public static List<AccountGroup> findByC_D(
		long companyId, boolean defaultAccountGroup, int start, int end) {

		return getPersistence().findByC_D(
			companyId, defaultAccountGroup, start, end);
	}

	/**
	 * Returns an ordered range of all the account groups where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account groups
	 */
	public static List<AccountGroup> findByC_D(
		long companyId, boolean defaultAccountGroup, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().findByC_D(
			companyId, defaultAccountGroup, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account groups where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account groups
	 */
	public static List<AccountGroup> findByC_D(
		long companyId, boolean defaultAccountGroup, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_D(
			companyId, defaultAccountGroup, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first account group in the ordered set where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	public static AccountGroup findByC_D_First(
			long companyId, boolean defaultAccountGroup,
			OrderByComparator<AccountGroup> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByC_D_First(
			companyId, defaultAccountGroup, orderByComparator);
	}

	/**
	 * Returns the first account group in the ordered set where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static AccountGroup fetchByC_D_First(
		long companyId, boolean defaultAccountGroup,
		OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().fetchByC_D_First(
			companyId, defaultAccountGroup, orderByComparator);
	}

	/**
	 * Returns the last account group in the ordered set where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	public static AccountGroup findByC_D_Last(
			long companyId, boolean defaultAccountGroup,
			OrderByComparator<AccountGroup> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByC_D_Last(
			companyId, defaultAccountGroup, orderByComparator);
	}

	/**
	 * Returns the last account group in the ordered set where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static AccountGroup fetchByC_D_Last(
		long companyId, boolean defaultAccountGroup,
		OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().fetchByC_D_Last(
			companyId, defaultAccountGroup, orderByComparator);
	}

	/**
	 * Returns the account groups before and after the current account group in the ordered set where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param accountGroupId the primary key of the current account group
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	public static AccountGroup[] findByC_D_PrevAndNext(
			long accountGroupId, long companyId, boolean defaultAccountGroup,
			OrderByComparator<AccountGroup> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByC_D_PrevAndNext(
			accountGroupId, companyId, defaultAccountGroup, orderByComparator);
	}

	/**
	 * Removes all the account groups where companyId = &#63; and defaultAccountGroup = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 */
	public static void removeByC_D(
		long companyId, boolean defaultAccountGroup) {

		getPersistence().removeByC_D(companyId, defaultAccountGroup);
	}

	/**
	 * Returns the number of account groups where companyId = &#63; and defaultAccountGroup = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultAccountGroup the default account group
	 * @return the number of matching account groups
	 */
	public static int countByC_D(long companyId, boolean defaultAccountGroup) {
		return getPersistence().countByC_D(companyId, defaultAccountGroup);
	}

	/**
	 * Returns the account group where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	public static AccountGroup findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static AccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static AccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the account group where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the account group that was removed
	 */
	public static AccountGroup removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of account groups where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching account groups
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the account group in the entity cache if it is enabled.
	 *
	 * @param accountGroup the account group
	 */
	public static void cacheResult(AccountGroup accountGroup) {
		getPersistence().cacheResult(accountGroup);
	}

	/**
	 * Caches the account groups in the entity cache if it is enabled.
	 *
	 * @param accountGroups the account groups
	 */
	public static void cacheResult(List<AccountGroup> accountGroups) {
		getPersistence().cacheResult(accountGroups);
	}

	/**
	 * Creates a new account group with the primary key. Does not add the account group to the database.
	 *
	 * @param accountGroupId the primary key for the new account group
	 * @return the new account group
	 */
	public static AccountGroup create(long accountGroupId) {
		return getPersistence().create(accountGroupId);
	}

	/**
	 * Removes the account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group that was removed
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	public static AccountGroup remove(long accountGroupId)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().remove(accountGroupId);
	}

	public static AccountGroup updateImpl(AccountGroup accountGroup) {
		return getPersistence().updateImpl(accountGroup);
	}

	/**
	 * Returns the account group with the primary key or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	public static AccountGroup findByPrimaryKey(long accountGroupId)
		throws com.liferay.account.exception.NoSuchGroupException {

		return getPersistence().findByPrimaryKey(accountGroupId);
	}

	/**
	 * Returns the account group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group, or <code>null</code> if a account group with the primary key could not be found
	 */
	public static AccountGroup fetchByPrimaryKey(long accountGroupId) {
		return getPersistence().fetchByPrimaryKey(accountGroupId);
	}

	/**
	 * Returns all the account groups.
	 *
	 * @return the account groups
	 */
	public static List<AccountGroup> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @return the range of account groups
	 */
	public static List<AccountGroup> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account groups
	 */
	public static List<AccountGroup> findAll(
		int start, int end, OrderByComparator<AccountGroup> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account groups
	 */
	public static List<AccountGroup> findAll(
		int start, int end, OrderByComparator<AccountGroup> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the account groups from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of account groups.
	 *
	 * @return the number of account groups
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AccountGroupPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountGroupPersistence, AccountGroupPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AccountGroupPersistence.class);

		ServiceTracker<AccountGroupPersistence, AccountGroupPersistence>
			serviceTracker =
				new ServiceTracker
					<AccountGroupPersistence, AccountGroupPersistence>(
						bundle.getBundleContext(),
						AccountGroupPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}