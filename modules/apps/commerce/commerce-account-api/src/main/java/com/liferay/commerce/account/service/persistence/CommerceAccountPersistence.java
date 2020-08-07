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

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce account service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountUtil
 * @generated
 */
@ProviderType
public interface CommerceAccountPersistence
	extends BasePersistence<CommerceAccount> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAccountUtil} to access the commerce account persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce accounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce accounts
	 */
	public java.util.List<CommerceAccount> findByCompanyId(long companyId);

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
	public java.util.List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

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
	public java.util.List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public CommerceAccount findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Returns the first commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public CommerceAccount fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

	/**
	 * Returns the last commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public CommerceAccount findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Returns the last commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public CommerceAccount fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public CommerceAccount[] findByCompanyId_PrevAndNext(
			long commerceAccountId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Returns all the commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce accounts that the user has permission to view
	 */
	public java.util.List<CommerceAccount> filterFindByCompanyId(
		long companyId);

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
	public java.util.List<CommerceAccount> filterFindByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<CommerceAccount> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set of commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public CommerceAccount[] filterFindByCompanyId_PrevAndNext(
			long commerceAccountId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Removes all the commerce accounts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce accounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce accounts
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce accounts that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce accounts
	 */
	public java.util.List<CommerceAccount> findByU_T(long userId, int type);

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
	public java.util.List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end);

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
	public java.util.List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

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
	public java.util.List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public CommerceAccount findByU_T_First(
			long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Returns the first commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public CommerceAccount fetchByU_T_First(
		long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

	/**
	 * Returns the last commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public CommerceAccount findByU_T_Last(
			long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Returns the last commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public CommerceAccount fetchByU_T_Last(
		long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

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
	public CommerceAccount[] findByU_T_PrevAndNext(
			long commerceAccountId, long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Returns all the commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce accounts that the user has permission to view
	 */
	public java.util.List<CommerceAccount> filterFindByU_T(
		long userId, int type);

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
	public java.util.List<CommerceAccount> filterFindByU_T(
		long userId, int type, int start, int end);

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
	public java.util.List<CommerceAccount> filterFindByU_T(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

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
	public CommerceAccount[] filterFindByU_T_PrevAndNext(
			long commerceAccountId, long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
				orderByComparator)
		throws NoSuchAccountException;

	/**
	 * Removes all the commerce accounts where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 */
	public void removeByU_T(long userId, int type);

	/**
	 * Returns the number of commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce accounts
	 */
	public int countByU_T(long userId, int type);

	/**
	 * Returns the number of commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce accounts that the user has permission to view
	 */
	public int filterCountByU_T(long userId, int type);

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	public CommerceAccount findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountException;

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public CommerceAccount fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public CommerceAccount fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce account where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account that was removed
	 */
	public CommerceAccount removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountException;

	/**
	 * Returns the number of commerce accounts where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce accounts
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce account in the entity cache if it is enabled.
	 *
	 * @param commerceAccount the commerce account
	 */
	public void cacheResult(CommerceAccount commerceAccount);

	/**
	 * Caches the commerce accounts in the entity cache if it is enabled.
	 *
	 * @param commerceAccounts the commerce accounts
	 */
	public void cacheResult(java.util.List<CommerceAccount> commerceAccounts);

	/**
	 * Creates a new commerce account with the primary key. Does not add the commerce account to the database.
	 *
	 * @param commerceAccountId the primary key for the new commerce account
	 * @return the new commerce account
	 */
	public CommerceAccount create(long commerceAccountId);

	/**
	 * Removes the commerce account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account that was removed
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public CommerceAccount remove(long commerceAccountId)
		throws NoSuchAccountException;

	public CommerceAccount updateImpl(CommerceAccount commerceAccount);

	/**
	 * Returns the commerce account with the primary key or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	public CommerceAccount findByPrimaryKey(long commerceAccountId)
		throws NoSuchAccountException;

	/**
	 * Returns the commerce account with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account, or <code>null</code> if a commerce account with the primary key could not be found
	 */
	public CommerceAccount fetchByPrimaryKey(long commerceAccountId);

	/**
	 * Returns all the commerce accounts.
	 *
	 * @return the commerce accounts
	 */
	public java.util.List<CommerceAccount> findAll();

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
	public java.util.List<CommerceAccount> findAll(int start, int end);

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
	public java.util.List<CommerceAccount> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator);

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
	public java.util.List<CommerceAccount> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce accounts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce accounts.
	 *
	 * @return the number of commerce accounts
	 */
	public int countAll();

}