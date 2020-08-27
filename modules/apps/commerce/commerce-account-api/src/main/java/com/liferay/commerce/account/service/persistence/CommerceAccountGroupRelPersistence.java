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

import com.liferay.commerce.account.exception.NoSuchAccountGroupRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelUtil
 * @generated
 */
@ProviderType
public interface CommerceAccountGroupRelPersistence
	extends BasePersistence<CommerceAccountGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAccountGroupRelUtil} to access the commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId);

	/**
	 * Returns a range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel findByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the first commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel fetchByCommerceAccountGroupId_First(
		long commerceAccountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the last commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel findByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the last commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel fetchByCommerceAccountGroupId_Last(
		long commerceAccountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the commerce account group rels before and after the current commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupRelId the primary key of the current commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public CommerceAccountGroupRel[] findByCommerceAccountGroupId_PrevAndNext(
			long commerceAccountGroupRelId, long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException;

	/**
	 * Removes all the commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns the number of commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group rels
	 */
	public int countByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the first commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the last commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the last commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the commerce account group rels before and after the current commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAccountGroupRelId the primary key of the current commerce account group rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public CommerceAccountGroupRel[] findByC_C_PrevAndNext(
			long commerceAccountGroupRelId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException;

	/**
	 * Removes all the commerce account group rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce account group rels
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchAccountGroupRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel findByC_C_C(
			long classNameId, long classPK, long commerceAccountGroupId)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel fetchByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId);

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public CommerceAccountGroupRel fetchByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId,
		boolean useFinderCache);

	/**
	 * Removes the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce account group rel that was removed
	 */
	public CommerceAccountGroupRel removeByC_C_C(
			long classNameId, long classPK, long commerceAccountGroupId)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the number of commerce account group rels where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group rels
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId);

	/**
	 * Caches the commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 */
	public void cacheResult(CommerceAccountGroupRel commerceAccountGroupRel);

	/**
	 * Caches the commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupRels the commerce account group rels
	 */
	public void cacheResult(
		java.util.List<CommerceAccountGroupRel> commerceAccountGroupRels);

	/**
	 * Creates a new commerce account group rel with the primary key. Does not add the commerce account group rel to the database.
	 *
	 * @param commerceAccountGroupRelId the primary key for the new commerce account group rel
	 * @return the new commerce account group rel
	 */
	public CommerceAccountGroupRel create(long commerceAccountGroupRelId);

	/**
	 * Removes the commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel that was removed
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public CommerceAccountGroupRel remove(long commerceAccountGroupRelId)
		throws NoSuchAccountGroupRelException;

	public CommerceAccountGroupRel updateImpl(
		CommerceAccountGroupRel commerceAccountGroupRel);

	/**
	 * Returns the commerce account group rel with the primary key or throws a <code>NoSuchAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public CommerceAccountGroupRel findByPrimaryKey(
			long commerceAccountGroupRelId)
		throws NoSuchAccountGroupRelException;

	/**
	 * Returns the commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel, or <code>null</code> if a commerce account group rel with the primary key could not be found
	 */
	public CommerceAccountGroupRel fetchByPrimaryKey(
		long commerceAccountGroupRelId);

	/**
	 * Returns all the commerce account group rels.
	 *
	 * @return the commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findAll();

	/**
	 * Returns a range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account group rels
	 */
	public java.util.List<CommerceAccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce account group rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce account group rels.
	 *
	 * @return the number of commerce account group rels
	 */
	public int countAll();

}