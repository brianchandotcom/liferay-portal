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

import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the account entry user rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelUtil
 * @generated
 */
@ProviderType
public interface AccountEntryUserRelPersistence
	extends BasePersistence<AccountEntryUserRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountEntryUserRelUtil} to access the account entry user rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the account entry user rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId);

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
	public java.util.List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end);

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
	public java.util.List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

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
	public java.util.List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel findByAccountEntryId_First(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the first account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel fetchByAccountEntryId_First(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

	/**
	 * Returns the last account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel findByAccountEntryId_Last(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the last account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel fetchByAccountEntryId_Last(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

	/**
	 * Returns the account entry user rels before and after the current account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryUserRelId the primary key of the current account entry user rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel[] findByAccountEntryId_PrevAndNext(
			long accountEntryUserRelId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException;

	/**
	 * Removes all the account entry user rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public void removeByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of account entry user rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry user rels
	 */
	public int countByAccountEntryId(long accountEntryId);

	/**
	 * Returns all the account entry user rels where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId);

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
	public java.util.List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end);

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
	public java.util.List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

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
	public java.util.List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel findByAccountUserId_First(
			long accountUserId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the first account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel fetchByAccountUserId_First(
		long accountUserId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

	/**
	 * Returns the last account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel findByAccountUserId_Last(
			long accountUserId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the last account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel fetchByAccountUserId_Last(
		long accountUserId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

	/**
	 * Returns the account entry user rels before and after the current account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountEntryUserRelId the primary key of the current account entry user rel
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel[] findByAccountUserId_PrevAndNext(
			long accountEntryUserRelId, long accountUserId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException;

	/**
	 * Removes all the account entry user rels where accountUserId = &#63; from the database.
	 *
	 * @param accountUserId the account user ID
	 */
	public void removeByAccountUserId(long accountUserId);

	/**
	 * Returns the number of account entry user rels where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @return the number of matching account entry user rels
	 */
	public int countByAccountUserId(long accountUserId);

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel findByAEI_AUI(
			long accountEntryId, long accountUserId)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel fetchByAEI_AUI(
		long accountEntryId, long accountUserId);

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	public AccountEntryUserRel fetchByAEI_AUI(
		long accountEntryId, long accountUserId, boolean useFinderCache);

	/**
	 * Removes the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the account entry user rel that was removed
	 */
	public AccountEntryUserRel removeByAEI_AUI(
			long accountEntryId, long accountUserId)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the number of account entry user rels where accountEntryId = &#63; and accountUserId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the number of matching account entry user rels
	 */
	public int countByAEI_AUI(long accountEntryId, long accountUserId);

	/**
	 * Caches the account entry user rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 */
	public void cacheResult(AccountEntryUserRel accountEntryUserRel);

	/**
	 * Caches the account entry user rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRels the account entry user rels
	 */
	public void cacheResult(
		java.util.List<AccountEntryUserRel> accountEntryUserRels);

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelId the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	public AccountEntryUserRel create(long accountEntryUserRelId);

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel remove(long accountEntryUserRelId)
		throws NoSuchEntryUserRelException;

	public AccountEntryUserRel updateImpl(
		AccountEntryUserRel accountEntryUserRel);

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel findByPrimaryKey(long accountEntryUserRelId)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the account entry user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel, or <code>null</code> if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel fetchByPrimaryKey(long accountEntryUserRelId);

	/**
	 * Returns all the account entry user rels.
	 *
	 * @return the account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findAll();

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
	public java.util.List<AccountEntryUserRel> findAll(int start, int end);

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
	public java.util.List<AccountEntryUserRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

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
	public java.util.List<AccountEntryUserRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account entry user rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
	 */
	public int countAll();

}