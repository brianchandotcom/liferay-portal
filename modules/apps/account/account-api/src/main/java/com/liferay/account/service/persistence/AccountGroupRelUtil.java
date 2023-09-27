/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.persistence;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the account group rel service. This utility wraps <code>com.liferay.account.service.persistence.impl.AccountGroupRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRelPersistence
 * @generated
 */
public class AccountGroupRelUtil {

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
	public static void clearCache(AccountGroupRel accountGroupRel) {
		getPersistence().clearCache(accountGroupRel);
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
	public static Map<Serializable, AccountGroupRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AccountGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AccountGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AccountGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AccountGroupRel update(AccountGroupRel accountGroupRel) {
		return getPersistence().update(accountGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AccountGroupRel update(
		AccountGroupRel accountGroupRel, ServiceContext serviceContext) {

		return getPersistence().update(accountGroupRel, serviceContext);
	}

	/**
	 * Returns all the account group rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the matching account group rels
	 */
	public static List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId) {

		return getPersistence().findByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns a range of all the account group rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of matching account group rels
	 */
	public static List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end) {

		return getPersistence().findByAccountGroupId(
			accountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the account group rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group rels
	 */
	public static List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().findByAccountGroupId(
			accountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group rels
	 */
	public static List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAccountGroupId(
			accountGroupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByAccountGroupId_First(
			long accountGroupId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByAccountGroupId_First(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByAccountGroupId_First(
		long accountGroupId,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().fetchByAccountGroupId_First(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByAccountGroupId_Last(
			long accountGroupId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByAccountGroupId_Last(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByAccountGroupId_Last(
		long accountGroupId,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().fetchByAccountGroupId_Last(
			accountGroupId, orderByComparator);
	}

	/**
	 * Returns the account group rels before and after the current account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupRelId the primary key of the current account group rel
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public static AccountGroupRel[] findByAccountGroupId_PrevAndNext(
			long accountGroupRelId, long accountGroupId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByAccountGroupId_PrevAndNext(
			accountGroupRelId, accountGroupId, orderByComparator);
	}

	/**
	 * Removes all the account group rels where accountGroupId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 */
	public static void removeByAccountGroupId(long accountGroupId) {
		getPersistence().removeByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns the number of account group rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the number of matching account group rels
	 */
	public static int countByAccountGroupId(long accountGroupId) {
		return getPersistence().countByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns all the account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @return the matching account group rels
	 */
	public static List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId) {

		return getPersistence().findByA_C(accountGroupId, classNameId);
	}

	/**
	 * Returns a range of all the account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of matching account group rels
	 */
	public static List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId, int start, int end) {

		return getPersistence().findByA_C(
			accountGroupId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group rels
	 */
	public static List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().findByA_C(
			accountGroupId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group rels
	 */
	public static List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByA_C(
			accountGroupId, classNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByA_C_First(
			long accountGroupId, long classNameId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByA_C_First(
			accountGroupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByA_C_First(
		long accountGroupId, long classNameId,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().fetchByA_C_First(
			accountGroupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByA_C_Last(
			long accountGroupId, long classNameId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByA_C_Last(
			accountGroupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByA_C_Last(
		long accountGroupId, long classNameId,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().fetchByA_C_Last(
			accountGroupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the account group rels before and after the current account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupRelId the primary key of the current account group rel
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public static AccountGroupRel[] findByA_C_PrevAndNext(
			long accountGroupRelId, long accountGroupId, long classNameId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByA_C_PrevAndNext(
			accountGroupRelId, accountGroupId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the account group rels where accountGroupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 */
	public static void removeByA_C(long accountGroupId, long classNameId) {
		getPersistence().removeByA_C(accountGroupId, classNameId);
	}

	/**
	 * Returns the number of account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @return the number of matching account group rels
	 */
	public static int countByA_C(long accountGroupId, long classNameId) {
		return getPersistence().countByA_C(accountGroupId, classNameId);
	}

	/**
	 * Returns all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rels
	 */
	public static List<AccountGroupRel> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of matching account group rels
	 */
	public static List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group rels
	 */
	public static List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group rels
	 */
	public static List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the account group rels before and after the current account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param accountGroupRelId the primary key of the current account group rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public static AccountGroupRel[] findByC_C_PrevAndNext(
			long accountGroupRelId, long classNameId, long classPK,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByC_C_PrevAndNext(
			accountGroupRelId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the account group rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching account group rels
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchGroupRelException</code> if it could not be found.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public static AccountGroupRel findByA_C_C(
			long accountGroupId, long classNameId, long classPK)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByA_C_C(
			accountGroupId, classNameId, classPK);
	}

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByA_C_C(
		long accountGroupId, long classNameId, long classPK) {

		return getPersistence().fetchByA_C_C(
			accountGroupId, classNameId, classPK);
	}

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public static AccountGroupRel fetchByA_C_C(
		long accountGroupId, long classNameId, long classPK,
		boolean useFinderCache) {

		return getPersistence().fetchByA_C_C(
			accountGroupId, classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the account group rel that was removed
	 */
	public static AccountGroupRel removeByA_C_C(
			long accountGroupId, long classNameId, long classPK)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().removeByA_C_C(
			accountGroupId, classNameId, classPK);
	}

	/**
	 * Returns the number of account group rels where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching account group rels
	 */
	public static int countByA_C_C(
		long accountGroupId, long classNameId, long classPK) {

		return getPersistence().countByA_C_C(
			accountGroupId, classNameId, classPK);
	}

	/**
	 * Caches the account group rel in the entity cache if it is enabled.
	 *
	 * @param accountGroupRel the account group rel
	 */
	public static void cacheResult(AccountGroupRel accountGroupRel) {
		getPersistence().cacheResult(accountGroupRel);
	}

	/**
	 * Caches the account group rels in the entity cache if it is enabled.
	 *
	 * @param accountGroupRels the account group rels
	 */
	public static void cacheResult(List<AccountGroupRel> accountGroupRels) {
		getPersistence().cacheResult(accountGroupRels);
	}

	/**
	 * Creates a new account group rel with the primary key. Does not add the account group rel to the database.
	 *
	 * @param accountGroupRelId the primary key for the new account group rel
	 * @return the new account group rel
	 */
	public static AccountGroupRel create(long accountGroupRelId) {
		return getPersistence().create(accountGroupRelId);
	}

	/**
	 * Removes the account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel that was removed
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public static AccountGroupRel remove(long accountGroupRelId)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().remove(accountGroupRelId);
	}

	public static AccountGroupRel updateImpl(AccountGroupRel accountGroupRel) {
		return getPersistence().updateImpl(accountGroupRel);
	}

	/**
	 * Returns the account group rel with the primary key or throws a <code>NoSuchGroupRelException</code> if it could not be found.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public static AccountGroupRel findByPrimaryKey(long accountGroupRelId)
		throws com.liferay.account.exception.NoSuchGroupRelException {

		return getPersistence().findByPrimaryKey(accountGroupRelId);
	}

	/**
	 * Returns the account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel, or <code>null</code> if a account group rel with the primary key could not be found
	 */
	public static AccountGroupRel fetchByPrimaryKey(long accountGroupRelId) {
		return getPersistence().fetchByPrimaryKey(accountGroupRelId);
	}

	/**
	 * Returns all the account group rels.
	 *
	 * @return the account group rels
	 */
	public static List<AccountGroupRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of account group rels
	 */
	public static List<AccountGroupRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account group rels
	 */
	public static List<AccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account group rels
	 */
	public static List<AccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the account group rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of account group rels.
	 *
	 * @return the number of account group rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AccountGroupRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AccountGroupRelPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AccountGroupRelPersistence _persistence;

}