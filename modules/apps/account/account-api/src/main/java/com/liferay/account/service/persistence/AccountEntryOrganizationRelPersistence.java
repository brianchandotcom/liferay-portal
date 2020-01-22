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

import com.liferay.account.exception.NoSuchEntryOrganizationRelException;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the account entry organization rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelUtil
 * @generated
 */
@ProviderType
public interface AccountEntryOrganizationRelPersistence
	extends BasePersistence<AccountEntryOrganizationRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountEntryOrganizationRelUtil} to access the account entry organization rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry organization rels
	 */
	public java.util.List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId);

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
	public java.util.List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end);

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
	public java.util.List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountEntryOrganizationRel> orderByComparator);

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
	public java.util.List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountEntryOrganizationRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel findByAccountEntryId_First(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryOrganizationRel> orderByComparator)
		throws NoSuchEntryOrganizationRelException;

	/**
	 * Returns the first account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel fetchByAccountEntryId_First(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountEntryOrganizationRel> orderByComparator);

	/**
	 * Returns the last account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel findByAccountEntryId_Last(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryOrganizationRel> orderByComparator)
		throws NoSuchEntryOrganizationRelException;

	/**
	 * Returns the last account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel fetchByAccountEntryId_Last(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountEntryOrganizationRel> orderByComparator);

	/**
	 * Returns the account entry organization rels before and after the current account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the current account entry organization rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public AccountEntryOrganizationRel[] findByAccountEntryId_PrevAndNext(
			long accountEntryOrganizationRelId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AccountEntryOrganizationRel> orderByComparator)
		throws NoSuchEntryOrganizationRelException;

	/**
	 * Removes all the account entry organization rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public void removeByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of account entry organization rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry organization rels
	 */
	public int countByAccountEntryId(long accountEntryId);

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or throws a <code>NoSuchEntryOrganizationRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel findByA_O(
			long accountEntryId, long organizationId)
		throws NoSuchEntryOrganizationRelException;

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel fetchByA_O(
		long accountEntryId, long organizationId);

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	public AccountEntryOrganizationRel fetchByA_O(
		long accountEntryId, long organizationId, boolean useFinderCache);

	/**
	 * Removes the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the account entry organization rel that was removed
	 */
	public AccountEntryOrganizationRel removeByA_O(
			long accountEntryId, long organizationId)
		throws NoSuchEntryOrganizationRelException;

	/**
	 * Returns the number of account entry organization rels where accountEntryId = &#63; and organizationId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the number of matching account entry organization rels
	 */
	public int countByA_O(long accountEntryId, long organizationId);

	/**
	 * Caches the account entry organization rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 */
	public void cacheResult(
		AccountEntryOrganizationRel accountEntryOrganizationRel);

	/**
	 * Caches the account entry organization rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryOrganizationRels the account entry organization rels
	 */
	public void cacheResult(
		java.util.List<AccountEntryOrganizationRel>
			accountEntryOrganizationRels);

	/**
	 * Creates a new account entry organization rel with the primary key. Does not add the account entry organization rel to the database.
	 *
	 * @param accountEntryOrganizationRelId the primary key for the new account entry organization rel
	 * @return the new account entry organization rel
	 */
	public AccountEntryOrganizationRel create(
		long accountEntryOrganizationRelId);

	/**
	 * Removes the account entry organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel that was removed
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public AccountEntryOrganizationRel remove(
			long accountEntryOrganizationRelId)
		throws NoSuchEntryOrganizationRelException;

	public AccountEntryOrganizationRel updateImpl(
		AccountEntryOrganizationRel accountEntryOrganizationRel);

	/**
	 * Returns the account entry organization rel with the primary key or throws a <code>NoSuchEntryOrganizationRelException</code> if it could not be found.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	public AccountEntryOrganizationRel findByPrimaryKey(
			long accountEntryOrganizationRelId)
		throws NoSuchEntryOrganizationRelException;

	/**
	 * Returns the account entry organization rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel, or <code>null</code> if a account entry organization rel with the primary key could not be found
	 */
	public AccountEntryOrganizationRel fetchByPrimaryKey(
		long accountEntryOrganizationRelId);

	/**
	 * Returns all the account entry organization rels.
	 *
	 * @return the account entry organization rels
	 */
	public java.util.List<AccountEntryOrganizationRel> findAll();

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
	public java.util.List<AccountEntryOrganizationRel> findAll(
		int start, int end);

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
	public java.util.List<AccountEntryOrganizationRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountEntryOrganizationRel> orderByComparator);

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
	public java.util.List<AccountEntryOrganizationRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AccountEntryOrganizationRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account entry organization rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of account entry organization rels.
	 *
	 * @return the number of account entry organization rels
	 */
	public int countAll();

}