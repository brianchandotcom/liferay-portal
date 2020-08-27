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

import com.liferay.commerce.account.exception.NoSuchAccountGroupCommerceAccountRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce account group commerce account rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelUtil
 * @generated
 */
@ProviderType
public interface CommerceAccountGroupCommerceAccountRelPersistence
	extends BasePersistence<CommerceAccountGroupCommerceAccountRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAccountGroupCommerceAccountRelUtil} to access the commerce account group commerce account rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns a range of all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceAccountGroupCommerceAccountRel> orderByComparator)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel
			findByCommerceAccountGroupId_Last(
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceAccountGroupCommerceAccountRel> orderByComparator)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns the commerce account group commerce account rels before and after the current commerce account group commerce account rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the current commerce account group commerce account rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceAccountGroupCommerceAccountRelId,
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceAccountGroupCommerceAccountRel> orderByComparator)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Removes all the commerce account group commerce account rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns the number of commerce account group commerce account rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group commerce account rels
	 */
	public int countByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns a range of all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(long commerceAccountId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(
			long commerceAccountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel>
		findByCommerceAccountId(
			long commerceAccountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel findByCommerceAccountId_First(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the first commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel
		fetchByCommerceAccountId_First(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel findByCommerceAccountId_Last(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupCommerceAccountRel> orderByComparator)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the last commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel fetchByCommerceAccountId_Last(
		long commerceAccountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns the commerce account group commerce account rels before and after the current commerce account group commerce account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the current commerce account group commerce account rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel[]
			findByCommerceAccountId_PrevAndNext(
				long commerceAccountGroupCommerceAccountRelId,
				long commerceAccountId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceAccountGroupCommerceAccountRel> orderByComparator)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Removes all the commerce account group commerce account rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	public void removeByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns the number of commerce account group commerce account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce account group commerce account rels
	 */
	public int countByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; or throws a <code>NoSuchAccountGroupCommerceAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel findByC_C(
			long commerceAccountGroupId, long commerceAccountId)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel fetchByC_C(
		long commerceAccountGroupId, long commerceAccountId);

	/**
	 * Returns the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel fetchByC_C(
		long commerceAccountGroupId, long commerceAccountId,
		boolean useFinderCache);

	/**
	 * Removes the commerce account group commerce account rel where commerceAccountGroupId = &#63; and commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the commerce account group commerce account rel that was removed
	 */
	public CommerceAccountGroupCommerceAccountRel removeByC_C(
			long commerceAccountGroupId, long commerceAccountId)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the number of commerce account group commerce account rels where commerceAccountGroupId = &#63; and commerceAccountId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce account group commerce account rels
	 */
	public int countByC_C(long commerceAccountGroupId, long commerceAccountId);

	/**
	 * Returns the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountGroupCommerceAccountRelException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce account group commerce account rel where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account group commerce account rel that was removed
	 */
	public CommerceAccountGroupCommerceAccountRel removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the number of commerce account group commerce account rels where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce account group commerce account rels
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce account group commerce account rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupCommerceAccountRel the commerce account group commerce account rel
	 */
	public void cacheResult(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel);

	/**
	 * Caches the commerce account group commerce account rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupCommerceAccountRels the commerce account group commerce account rels
	 */
	public void cacheResult(
		java.util.List<CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels);

	/**
	 * Creates a new commerce account group commerce account rel with the primary key. Does not add the commerce account group commerce account rel to the database.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key for the new commerce account group commerce account rel
	 * @return the new commerce account group commerce account rel
	 */
	public CommerceAccountGroupCommerceAccountRel create(
		long commerceAccountGroupCommerceAccountRelId);

	/**
	 * Removes the commerce account group commerce account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel that was removed
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel remove(
			long commerceAccountGroupCommerceAccountRelId)
		throws NoSuchAccountGroupCommerceAccountRelException;

	public CommerceAccountGroupCommerceAccountRel updateImpl(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel);

	/**
	 * Returns the commerce account group commerce account rel with the primary key or throws a <code>NoSuchAccountGroupCommerceAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel
	 * @throws NoSuchAccountGroupCommerceAccountRelException if a commerce account group commerce account rel with the primary key could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel findByPrimaryKey(
			long commerceAccountGroupCommerceAccountRelId)
		throws NoSuchAccountGroupCommerceAccountRelException;

	/**
	 * Returns the commerce account group commerce account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel, or <code>null</code> if a commerce account group commerce account rel with the primary key could not be found
	 */
	public CommerceAccountGroupCommerceAccountRel fetchByPrimaryKey(
		long commerceAccountGroupCommerceAccountRelId);

	/**
	 * Returns all the commerce account group commerce account rels.
	 *
	 * @return the commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel> findAll();

	/**
	 * Returns a range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupCommerceAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account group commerce account rels
	 */
	public java.util.List<CommerceAccountGroupCommerceAccountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupCommerceAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce account group commerce account rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce account group commerce account rels.
	 *
	 * @return the number of commerce account group commerce account rels
	 */
	public int countAll();

}