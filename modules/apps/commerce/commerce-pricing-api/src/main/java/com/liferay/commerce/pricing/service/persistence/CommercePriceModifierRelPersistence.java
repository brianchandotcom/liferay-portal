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

package com.liferay.commerce.pricing.service.persistence;

import com.liferay.commerce.pricing.exception.NoSuchPriceModifierRelException;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price modifier rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceModifierRelPersistence
	extends BasePersistence<CommercePriceModifierRel>,
			CTPersistence<CommercePriceModifierRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceModifierRelUtil} to access the commerce price modifier rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @return the matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel>
		findByCommercePriceModifierId(long commercePriceModifierId);

	/**
	 * Returns a range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel>
		findByCommercePriceModifierId(
			long commercePriceModifierId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel>
		findByCommercePriceModifierId(
			long commercePriceModifierId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel>
		findByCommercePriceModifierId(
			long commercePriceModifierId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCommercePriceModifierId_First(
			long commercePriceModifierId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCommercePriceModifierId_First(
		long commercePriceModifierId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCommercePriceModifierId_Last(
			long commercePriceModifierId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCommercePriceModifierId_Last(
		long commercePriceModifierId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public CommercePriceModifierRel[] findByCommercePriceModifierId_PrevAndNext(
			long commercePriceModifierRelId, long commercePriceModifierId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Removes all the commerce price modifier rels where commercePriceModifierId = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 */
	public void removeByCommercePriceModifierId(long commercePriceModifierId);

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @return the number of matching commerce price modifier rels
	 */
	public int countByCommercePriceModifierId(long commercePriceModifierId);

	/**
	 * Returns all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @return the matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId);

	/**
	 * Returns a range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCPM_CN_First(
			long commercePriceModifierId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCPM_CN_First(
		long commercePriceModifierId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCPM_CN_Last(
			long commercePriceModifierId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCPM_CN_Last(
		long commercePriceModifierId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public CommercePriceModifierRel[] findByCPM_CN_PrevAndNext(
			long commercePriceModifierRelId, long commercePriceModifierId,
			long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Removes all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 */
	public void removeByCPM_CN(long commercePriceModifierId, long classNameId);

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @return the number of matching commerce price modifier rels
	 */
	public int countByCPM_CN(long commercePriceModifierId, long classNameId);

	/**
	 * Returns all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCN_CPK_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the first commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCN_CPK_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns the last commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCN_CPK_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the last commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCN_CPK_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public CommercePriceModifierRel[] findByCN_CPK_PrevAndNext(
			long commercePriceModifierRelId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException;

	/**
	 * Removes all the commerce price modifier rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByCN_CPK(long classNameId, long classPK);

	/**
	 * Returns the number of commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price modifier rels
	 */
	public int countByCN_CPK(long classNameId, long classPK);

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPriceModifierRelException</code> if it could not be found.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel findByCPM_CN_CPK(
			long commercePriceModifierId, long classNameId, long classPK)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK);

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public CommercePriceModifierRel fetchByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK,
		boolean useFinderCache);

	/**
	 * Removes the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the commerce price modifier rel that was removed
	 */
	public CommercePriceModifierRel removeByCPM_CN_CPK(
			long commercePriceModifierId, long classNameId, long classPK)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price modifier rels
	 */
	public int countByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK);

	/**
	 * Caches the commerce price modifier rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifierRel the commerce price modifier rel
	 */
	public void cacheResult(CommercePriceModifierRel commercePriceModifierRel);

	/**
	 * Caches the commerce price modifier rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifierRels the commerce price modifier rels
	 */
	public void cacheResult(
		java.util.List<CommercePriceModifierRel> commercePriceModifierRels);

	/**
	 * Creates a new commerce price modifier rel with the primary key. Does not add the commerce price modifier rel to the database.
	 *
	 * @param commercePriceModifierRelId the primary key for the new commerce price modifier rel
	 * @return the new commerce price modifier rel
	 */
	public CommercePriceModifierRel create(long commercePriceModifierRelId);

	/**
	 * Removes the commerce price modifier rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel that was removed
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public CommercePriceModifierRel remove(long commercePriceModifierRelId)
		throws NoSuchPriceModifierRelException;

	public CommercePriceModifierRel updateImpl(
		CommercePriceModifierRel commercePriceModifierRel);

	/**
	 * Returns the commerce price modifier rel with the primary key or throws a <code>NoSuchPriceModifierRelException</code> if it could not be found.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public CommercePriceModifierRel findByPrimaryKey(
			long commercePriceModifierRelId)
		throws NoSuchPriceModifierRelException;

	/**
	 * Returns the commerce price modifier rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel, or <code>null</code> if a commerce price modifier rel with the primary key could not be found
	 */
	public CommercePriceModifierRel fetchByPrimaryKey(
		long commercePriceModifierRelId);

	/**
	 * Returns all the commerce price modifier rels.
	 *
	 * @return the commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findAll();

	/**
	 * Returns a range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price modifier rels
	 */
	public java.util.List<CommercePriceModifierRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price modifier rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price modifier rels.
	 *
	 * @return the number of commerce price modifier rels
	 */
	public int countAll();

}