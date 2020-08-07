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

import com.liferay.commerce.account.exception.NoSuchAccountOrganizationRelException;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce account organization rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelUtil
 * @generated
 */
@ProviderType
public interface CommerceAccountOrganizationRelPersistence
	extends BasePersistence<CommerceAccountOrganizationRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAccountOrganizationRelUtil} to access the commerce account organization rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel>
		findByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns a range of all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel>
		findByCommerceAccountId(long commerceAccountId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel>
		findByCommerceAccountId(
			long commerceAccountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel>
		findByCommerceAccountId(
			long commerceAccountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel findByCommerceAccountId_First(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Returns the first commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel fetchByCommerceAccountId_First(
		long commerceAccountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns the last commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel findByCommerceAccountId_Last(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Returns the last commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel fetchByCommerceAccountId_Last(
		long commerceAccountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns the commerce account organization rels before and after the current commerce account organization rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the current commerce account organization rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public CommerceAccountOrganizationRel[] findByCommerceAccountId_PrevAndNext(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK,
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Removes all the commerce account organization rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	public void removeByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns the number of commerce account organization rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce account organization rels
	 */
	public int countByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns all the commerce account organization rels where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId);

	/**
	 * Returns a range of all the commerce account organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account organization rels where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findByOrganizationId(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel findByOrganizationId_First(
			long organizationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Returns the first commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel fetchByOrganizationId_First(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns the last commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel findByOrganizationId_Last(
			long organizationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Returns the last commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account organization rel, or <code>null</code> if a matching commerce account organization rel could not be found
	 */
	public CommerceAccountOrganizationRel fetchByOrganizationId_Last(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns the commerce account organization rels before and after the current commerce account organization rel in the ordered set where organizationId = &#63;.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the current commerce account organization rel
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public CommerceAccountOrganizationRel[] findByOrganizationId_PrevAndNext(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK,
			long organizationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountOrganizationRel> orderByComparator)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Removes all the commerce account organization rels where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 */
	public void removeByOrganizationId(long organizationId);

	/**
	 * Returns the number of commerce account organization rels where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching commerce account organization rels
	 */
	public int countByOrganizationId(long organizationId);

	/**
	 * Caches the commerce account organization rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountOrganizationRel the commerce account organization rel
	 */
	public void cacheResult(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel);

	/**
	 * Caches the commerce account organization rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountOrganizationRels the commerce account organization rels
	 */
	public void cacheResult(
		java.util.List<CommerceAccountOrganizationRel>
			commerceAccountOrganizationRels);

	/**
	 * Creates a new commerce account organization rel with the primary key. Does not add the commerce account organization rel to the database.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key for the new commerce account organization rel
	 * @return the new commerce account organization rel
	 */
	public CommerceAccountOrganizationRel create(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK);

	/**
	 * Removes the commerce account organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel that was removed
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public CommerceAccountOrganizationRel remove(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws NoSuchAccountOrganizationRelException;

	public CommerceAccountOrganizationRel updateImpl(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel);

	/**
	 * Returns the commerce account organization rel with the primary key or throws a <code>NoSuchAccountOrganizationRelException</code> if it could not be found.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel
	 * @throws NoSuchAccountOrganizationRelException if a commerce account organization rel with the primary key could not be found
	 */
	public CommerceAccountOrganizationRel findByPrimaryKey(
			CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK)
		throws NoSuchAccountOrganizationRelException;

	/**
	 * Returns the commerce account organization rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel, or <code>null</code> if a commerce account organization rel with the primary key could not be found
	 */
	public CommerceAccountOrganizationRel fetchByPrimaryKey(
		CommerceAccountOrganizationRelPK commerceAccountOrganizationRelPK);

	/**
	 * Returns all the commerce account organization rels.
	 *
	 * @return the commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findAll();

	/**
	 * Returns a range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account organization rels
	 */
	public java.util.List<CommerceAccountOrganizationRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountOrganizationRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce account organization rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce account organization rels.
	 *
	 * @return the number of commerce account organization rels
	 */
	public int countAll();

	public Set<String> getCompoundPKColumnNames();

}