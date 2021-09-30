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

package com.liferay.commerce.order.rule.service.persistence;

import com.liferay.commerce.order.rule.exception.NoSuchOrderRuleEntryRelException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce order rule entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryRelUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderRuleEntryRelPersistence
	extends BasePersistence<CommerceOrderRuleEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderRuleEntryRelUtil} to access the commerce order rule entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(long commerceOrderRuleEntryId);

	/**
	 * Returns a range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel>
		findByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel findByCommerceOrderRuleEntryId_First(
			long commerceOrderRuleEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByCommerceOrderRuleEntryId_First(
		long commerceOrderRuleEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel findByCommerceOrderRuleEntryId_Last(
			long commerceOrderRuleEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByCommerceOrderRuleEntryId_Last(
		long commerceOrderRuleEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns the commerce order rule entry rels before and after the current commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the current commerce order rule entry rel
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public CommerceOrderRuleEntryRel[]
			findByCommerceOrderRuleEntryId_PrevAndNext(
				long commerceOrderRuleEntryRelId, long commerceOrderRuleEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Removes all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 */
	public void removeByCommerceOrderRuleEntryId(long commerceOrderRuleEntryId);

	/**
	 * Returns the number of commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	public int countByCommerceOrderRuleEntryId(long commerceOrderRuleEntryId);

	/**
	 * Returns all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId);

	/**
	 * Returns a range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel findByC_C_First(
			long classNameId, long commerceOrderRuleEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByC_C_First(
		long classNameId, long commerceOrderRuleEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel findByC_C_Last(
			long classNameId, long commerceOrderRuleEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByC_C_Last(
		long classNameId, long commerceOrderRuleEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns the commerce order rule entry rels before and after the current commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the current commerce order rule entry rel
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public CommerceOrderRuleEntryRel[] findByC_C_PrevAndNext(
			long commerceOrderRuleEntryRelId, long classNameId,
			long commerceOrderRuleEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Removes all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 */
	public void removeByC_C(long classNameId, long commerceOrderRuleEntryId);

	/**
	 * Returns the number of commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	public int countByC_C(long classNameId, long commerceOrderRuleEntryId);

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or throws a <code>NoSuchOrderRuleEntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel findByC_C_C(
			long classNameId, long classPK, long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId);

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId,
		boolean useFinderCache);

	/**
	 * Removes the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the commerce order rule entry rel that was removed
	 */
	public CommerceOrderRuleEntryRel removeByC_C_C(
			long classNameId, long classPK, long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the number of commerce order rule entry rels where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId);

	/**
	 * Caches the commerce order rule entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntryRel the commerce order rule entry rel
	 */
	public void cacheResult(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel);

	/**
	 * Caches the commerce order rule entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntryRels the commerce order rule entry rels
	 */
	public void cacheResult(
		java.util.List<CommerceOrderRuleEntryRel> commerceOrderRuleEntryRels);

	/**
	 * Creates a new commerce order rule entry rel with the primary key. Does not add the commerce order rule entry rel to the database.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key for the new commerce order rule entry rel
	 * @return the new commerce order rule entry rel
	 */
	public CommerceOrderRuleEntryRel create(long commerceOrderRuleEntryRelId);

	/**
	 * Removes the commerce order rule entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was removed
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public CommerceOrderRuleEntryRel remove(long commerceOrderRuleEntryRelId)
		throws NoSuchOrderRuleEntryRelException;

	public CommerceOrderRuleEntryRel updateImpl(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel);

	/**
	 * Returns the commerce order rule entry rel with the primary key or throws a <code>NoSuchOrderRuleEntryRelException</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	public CommerceOrderRuleEntryRel findByPrimaryKey(
			long commerceOrderRuleEntryRelId)
		throws NoSuchOrderRuleEntryRelException;

	/**
	 * Returns the commerce order rule entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel, or <code>null</code> if a commerce order rule entry rel with the primary key could not be found
	 */
	public CommerceOrderRuleEntryRel fetchByPrimaryKey(
		long commerceOrderRuleEntryRelId);

	/**
	 * Returns all the commerce order rule entry rels.
	 *
	 * @return the commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findAll();

	/**
	 * Returns a range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order rule entry rels
	 */
	public java.util.List<CommerceOrderRuleEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceOrderRuleEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce order rule entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce order rule entry rels.
	 *
	 * @return the number of commerce order rule entry rels
	 */
	public int countAll();

}