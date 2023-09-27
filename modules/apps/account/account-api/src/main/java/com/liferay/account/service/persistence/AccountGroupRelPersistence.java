/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.persistence;

import com.liferay.account.exception.NoSuchGroupRelException;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRelUtil
 * @generated
 */
@ProviderType
public interface AccountGroupRelPersistence
	extends BasePersistence<AccountGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountGroupRelUtil} to access the account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the account group rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the matching account group rels
	 */
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId);

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
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end);

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
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

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
	public java.util.List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByAccountGroupId_First(
			long accountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByAccountGroupId_First(
		long accountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByAccountGroupId_Last(
			long accountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByAccountGroupId_Last(
		long accountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

	/**
	 * Returns the account group rels before and after the current account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupRelId the primary key of the current account group rel
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public AccountGroupRel[] findByAccountGroupId_PrevAndNext(
			long accountGroupRelId, long accountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Removes all the account group rels where accountGroupId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 */
	public void removeByAccountGroupId(long accountGroupId);

	/**
	 * Returns the number of account group rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the number of matching account group rels
	 */
	public int countByAccountGroupId(long accountGroupId);

	/**
	 * Returns all the account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @return the matching account group rels
	 */
	public java.util.List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId);

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
	public java.util.List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId, int start, int end);

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
	public java.util.List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

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
	public java.util.List<AccountGroupRel> findByA_C(
		long accountGroupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByA_C_First(
			long accountGroupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByA_C_First(
		long accountGroupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByA_C_Last(
			long accountGroupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByA_C_Last(
		long accountGroupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

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
	public AccountGroupRel[] findByA_C_PrevAndNext(
			long accountGroupRelId, long accountGroupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Removes all the account group rels where accountGroupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 */
	public void removeByA_C(long accountGroupId, long classNameId);

	/**
	 * Returns the number of account group rels where accountGroupId = &#63; and classNameId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @return the number of matching account group rels
	 */
	public int countByA_C(long accountGroupId, long classNameId);

	/**
	 * Returns all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rels
	 */
	public java.util.List<AccountGroupRel> findByC_C(
		long classNameId, long classPK);

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
	public java.util.List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end);

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
	public java.util.List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

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
	public java.util.List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Returns the first account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

	/**
	 * Returns the last account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Returns the last account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

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
	public AccountGroupRel[] findByC_C_PrevAndNext(
			long accountGroupRelId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
				orderByComparator)
		throws NoSuchGroupRelException;

	/**
	 * Removes all the account group rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching account group rels
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchGroupRelException</code> if it could not be found.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	public AccountGroupRel findByA_C_C(
			long accountGroupId, long classNameId, long classPK)
		throws NoSuchGroupRelException;

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByA_C_C(
		long accountGroupId, long classNameId, long classPK);

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	public AccountGroupRel fetchByA_C_C(
		long accountGroupId, long classNameId, long classPK,
		boolean useFinderCache);

	/**
	 * Removes the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the account group rel that was removed
	 */
	public AccountGroupRel removeByA_C_C(
			long accountGroupId, long classNameId, long classPK)
		throws NoSuchGroupRelException;

	/**
	 * Returns the number of account group rels where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching account group rels
	 */
	public int countByA_C_C(
		long accountGroupId, long classNameId, long classPK);

	/**
	 * Caches the account group rel in the entity cache if it is enabled.
	 *
	 * @param accountGroupRel the account group rel
	 */
	public void cacheResult(AccountGroupRel accountGroupRel);

	/**
	 * Caches the account group rels in the entity cache if it is enabled.
	 *
	 * @param accountGroupRels the account group rels
	 */
	public void cacheResult(java.util.List<AccountGroupRel> accountGroupRels);

	/**
	 * Creates a new account group rel with the primary key. Does not add the account group rel to the database.
	 *
	 * @param accountGroupRelId the primary key for the new account group rel
	 * @return the new account group rel
	 */
	public AccountGroupRel create(long accountGroupRelId);

	/**
	 * Removes the account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel that was removed
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public AccountGroupRel remove(long accountGroupRelId)
		throws NoSuchGroupRelException;

	public AccountGroupRel updateImpl(AccountGroupRel accountGroupRel);

	/**
	 * Returns the account group rel with the primary key or throws a <code>NoSuchGroupRelException</code> if it could not be found.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	public AccountGroupRel findByPrimaryKey(long accountGroupRelId)
		throws NoSuchGroupRelException;

	/**
	 * Returns the account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel, or <code>null</code> if a account group rel with the primary key could not be found
	 */
	public AccountGroupRel fetchByPrimaryKey(long accountGroupRelId);

	/**
	 * Returns all the account group rels.
	 *
	 * @return the account group rels
	 */
	public java.util.List<AccountGroupRel> findAll();

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
	public java.util.List<AccountGroupRel> findAll(int start, int end);

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
	public java.util.List<AccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator);

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
	public java.util.List<AccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account group rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of account group rels.
	 *
	 * @return the number of account group rels
	 */
	public int countAll();

}