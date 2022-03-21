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

import com.liferay.account.exception.NoSuchRoleException;
import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the account role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountRoleUtil
 * @generated
 */
@ProviderType
public interface AccountRolePersistence extends BasePersistence<AccountRole> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountRoleUtil} to access the account role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the account roles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching account roles
	 */
	public java.util.List<AccountRole> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the account roles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles
	 */
	public java.util.List<AccountRole> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the account roles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account roles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns the first account role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the last account role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns the last account role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the account roles before and after the current account role in the ordered set where companyId = &#63;.
	 *
	 * @param accountRoleId the primary key of the current account role
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole[] findByCompanyId_PrevAndNext(
			long accountRoleId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns all the account roles that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the account roles that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the account roles that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the account roles before and after the current account role in the ordered set of account roles that the user has permission to view where companyId = &#63;.
	 *
	 * @param accountRoleId the primary key of the current account role
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole[] filterFindByCompanyId_PrevAndNext(
			long accountRoleId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Removes all the account roles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of account roles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching account roles
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of account roles that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching account roles that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the account roles where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long accountEntryId);

	/**
	 * Returns a range of all the account roles where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the account roles where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account roles where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account role in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByAccountEntryId_First(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns the first account role in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByAccountEntryId_First(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the last account role in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByAccountEntryId_Last(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns the last account role in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByAccountEntryId_Last(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the account roles before and after the current account role in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountRoleId the primary key of the current account role
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole[] findByAccountEntryId_PrevAndNext(
			long accountRoleId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns all the account roles that the user has permission to view where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByAccountEntryId(
		long accountEntryId);

	/**
	 * Returns a range of all the account roles that the user has permission to view where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByAccountEntryId(
		long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the account roles that the user has permissions to view where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the account roles before and after the current account role in the ordered set of account roles that the user has permission to view where accountEntryId = &#63;.
	 *
	 * @param accountRoleId the primary key of the current account role
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole[] filterFindByAccountEntryId_PrevAndNext(
			long accountRoleId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns all the account roles that the user has permission to view where accountEntryId = any &#63;.
	 *
	 * @param accountEntryIds the account entry IDs
	 * @return the matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByAccountEntryId(
		long[] accountEntryIds);

	/**
	 * Returns a range of all the account roles that the user has permission to view where accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByAccountEntryId(
		long[] accountEntryIds, int start, int end);

	/**
	 * Returns an ordered range of all the account roles that the user has permission to view where accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByAccountEntryId(
		long[] accountEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns all the account roles where accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryIds the account entry IDs
	 * @return the matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long[] accountEntryIds);

	/**
	 * Returns a range of all the account roles where accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long[] accountEntryIds, int start, int end);

	/**
	 * Returns an ordered range of all the account roles where accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long[] accountEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account roles where accountEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByAccountEntryId(
		long[] accountEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account roles where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public void removeByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of account roles where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account roles
	 */
	public int countByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of account roles where accountEntryId = any &#63;.
	 *
	 * @param accountEntryIds the account entry IDs
	 * @return the number of matching account roles
	 */
	public int countByAccountEntryId(long[] accountEntryIds);

	/**
	 * Returns the number of account roles that the user has permission to view where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account roles that the user has permission to view
	 */
	public int filterCountByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of account roles that the user has permission to view where accountEntryId = any &#63;.
	 *
	 * @param accountEntryIds the account entry IDs
	 * @return the number of matching account roles that the user has permission to view
	 */
	public int filterCountByAccountEntryId(long[] accountEntryIds);

	/**
	 * Returns the account role where roleId = &#63; or throws a <code>NoSuchRoleException</code> if it could not be found.
	 *
	 * @param roleId the role ID
	 * @return the matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByRoleId(long roleId) throws NoSuchRoleException;

	/**
	 * Returns the account role where roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param roleId the role ID
	 * @return the matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByRoleId(long roleId);

	/**
	 * Returns the account role where roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param roleId the role ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByRoleId(long roleId, boolean useFinderCache);

	/**
	 * Removes the account role where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @return the account role that was removed
	 */
	public AccountRole removeByRoleId(long roleId) throws NoSuchRoleException;

	/**
	 * Returns the number of account roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching account roles
	 */
	public int countByRoleId(long roleId);

	/**
	 * Returns all the account roles where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long accountEntryId);

	/**
	 * Returns a range of all the account roles where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the account roles where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account roles where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account role in the ordered set where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByC_A_First(
			long companyId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns the first account role in the ordered set where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByC_A_First(
		long companyId, long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the last account role in the ordered set where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account role
	 * @throws NoSuchRoleException if a matching account role could not be found
	 */
	public AccountRole findByC_A_Last(
			long companyId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns the last account role in the ordered set where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account role, or <code>null</code> if a matching account role could not be found
	 */
	public AccountRole fetchByC_A_Last(
		long companyId, long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the account roles before and after the current account role in the ordered set where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param accountRoleId the primary key of the current account role
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole[] findByC_A_PrevAndNext(
			long accountRoleId, long companyId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns all the account roles that the user has permission to view where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByC_A(
		long companyId, long accountEntryId);

	/**
	 * Returns a range of all the account roles that the user has permission to view where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByC_A(
		long companyId, long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the account roles that the user has permissions to view where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByC_A(
		long companyId, long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns the account roles before and after the current account role in the ordered set of account roles that the user has permission to view where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param accountRoleId the primary key of the current account role
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole[] filterFindByC_A_PrevAndNext(
			long accountRoleId, long companyId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
				orderByComparator)
		throws NoSuchRoleException;

	/**
	 * Returns all the account roles that the user has permission to view where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @return the matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByC_A(
		long companyId, long[] accountEntryIds);

	/**
	 * Returns a range of all the account roles that the user has permission to view where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByC_A(
		long companyId, long[] accountEntryIds, int start, int end);

	/**
	 * Returns an ordered range of all the account roles that the user has permission to view where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles that the user has permission to view
	 */
	public java.util.List<AccountRole> filterFindByC_A(
		long companyId, long[] accountEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns all the account roles where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @return the matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long[] accountEntryIds);

	/**
	 * Returns a range of all the account roles where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long[] accountEntryIds, int start, int end);

	/**
	 * Returns an ordered range of all the account roles where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long[] accountEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account roles where companyId = &#63; and accountEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account roles
	 */
	public java.util.List<AccountRole> findByC_A(
		long companyId, long[] accountEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account roles where companyId = &#63; and accountEntryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 */
	public void removeByC_A(long companyId, long accountEntryId);

	/**
	 * Returns the number of account roles where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account roles
	 */
	public int countByC_A(long companyId, long accountEntryId);

	/**
	 * Returns the number of account roles where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @return the number of matching account roles
	 */
	public int countByC_A(long companyId, long[] accountEntryIds);

	/**
	 * Returns the number of account roles that the user has permission to view where companyId = &#63; and accountEntryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account roles that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, long accountEntryId);

	/**
	 * Returns the number of account roles that the user has permission to view where companyId = &#63; and accountEntryId = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param accountEntryIds the account entry IDs
	 * @return the number of matching account roles that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, long[] accountEntryIds);

	/**
	 * Caches the account role in the entity cache if it is enabled.
	 *
	 * @param accountRole the account role
	 */
	public void cacheResult(AccountRole accountRole);

	/**
	 * Caches the account roles in the entity cache if it is enabled.
	 *
	 * @param accountRoles the account roles
	 */
	public void cacheResult(java.util.List<AccountRole> accountRoles);

	/**
	 * Creates a new account role with the primary key. Does not add the account role to the database.
	 *
	 * @param accountRoleId the primary key for the new account role
	 * @return the new account role
	 */
	public AccountRole create(long accountRoleId);

	/**
	 * Removes the account role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountRoleId the primary key of the account role
	 * @return the account role that was removed
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole remove(long accountRoleId) throws NoSuchRoleException;

	public AccountRole updateImpl(AccountRole accountRole);

	/**
	 * Returns the account role with the primary key or throws a <code>NoSuchRoleException</code> if it could not be found.
	 *
	 * @param accountRoleId the primary key of the account role
	 * @return the account role
	 * @throws NoSuchRoleException if a account role with the primary key could not be found
	 */
	public AccountRole findByPrimaryKey(long accountRoleId)
		throws NoSuchRoleException;

	/**
	 * Returns the account role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountRoleId the primary key of the account role
	 * @return the account role, or <code>null</code> if a account role with the primary key could not be found
	 */
	public AccountRole fetchByPrimaryKey(long accountRoleId);

	/**
	 * Returns all the account roles.
	 *
	 * @return the account roles
	 */
	public java.util.List<AccountRole> findAll();

	/**
	 * Returns a range of all the account roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of account roles
	 */
	public java.util.List<AccountRole> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the account roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account roles
	 */
	public java.util.List<AccountRole> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account roles
	 */
	public java.util.List<AccountRole> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account roles from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of account roles.
	 *
	 * @return the number of account roles
	 */
	public int countAll();

}