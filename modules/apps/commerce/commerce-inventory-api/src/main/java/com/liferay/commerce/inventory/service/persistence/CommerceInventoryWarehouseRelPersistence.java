/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence;

import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseRelException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce inventory warehouse rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseRelUtil
 * @generated
 */
@ProviderType
public interface CommerceInventoryWarehouseRelPersistence
	extends BasePersistence<CommerceInventoryWarehouseRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceInventoryWarehouseRelUtil} to access the commerce inventory warehouse rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(long commerceInventoryWarehouseId);

	/**
	 * Returns a range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel
			findByCommerceInventoryWarehouseId_First(
				long commerceInventoryWarehouseId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel
		fetchByCommerceInventoryWarehouseId_First(
			long commerceInventoryWarehouseId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel
			findByCommerceInventoryWarehouseId_Last(
				long commerceInventoryWarehouseId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel
		fetchByCommerceInventoryWarehouseId_Last(
			long commerceInventoryWarehouseId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns the commerce inventory warehouse rels before and after the current commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the current commerce inventory warehouse rel
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public CommerceInventoryWarehouseRel[]
			findByCommerceInventoryWarehouseId_PrevAndNext(
				long commerceInventoryWarehouseRelId,
				long commerceInventoryWarehouseId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Removes all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	public void removeByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId);

	/**
	 * Returns the number of commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	public int countByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId);

	/**
	 * Returns all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId);

	/**
	 * Returns a range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouseRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel findByC_C_First(
			long classNameId, long commerceInventoryWarehouseId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel fetchByC_C_First(
		long classNameId, long commerceInventoryWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel findByC_C_Last(
			long classNameId, long commerceInventoryWarehouseId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel fetchByC_C_Last(
		long classNameId, long commerceInventoryWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns the commerce inventory warehouse rels before and after the current commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the current commerce inventory warehouse rel
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public CommerceInventoryWarehouseRel[] findByC_C_PrevAndNext(
			long commerceInventoryWarehouseRelId, long classNameId,
			long commerceInventoryWarehouseId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouseRel> orderByComparator)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Removes all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	public void removeByC_C(
		long classNameId, long commerceInventoryWarehouseId);

	/**
	 * Returns the number of commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	public int countByC_C(long classNameId, long commerceInventoryWarehouseId);

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or throws a <code>NoSuchInventoryWarehouseRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel findByC_C_CIWI(
			long classNameId, long classPK, long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel fetchByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId);

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public CommerceInventoryWarehouseRel fetchByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId,
		boolean useFinderCache);

	/**
	 * Removes the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the commerce inventory warehouse rel that was removed
	 */
	public CommerceInventoryWarehouseRel removeByC_C_CIWI(
			long classNameId, long classPK, long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the number of commerce inventory warehouse rels where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	public int countByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId);

	/**
	 * Caches the commerce inventory warehouse rel in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseRel the commerce inventory warehouse rel
	 */
	public void cacheResult(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel);

	/**
	 * Caches the commerce inventory warehouse rels in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseRels the commerce inventory warehouse rels
	 */
	public void cacheResult(
		java.util.List<CommerceInventoryWarehouseRel>
			commerceInventoryWarehouseRels);

	/**
	 * Creates a new commerce inventory warehouse rel with the primary key. Does not add the commerce inventory warehouse rel to the database.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key for the new commerce inventory warehouse rel
	 * @return the new commerce inventory warehouse rel
	 */
	public CommerceInventoryWarehouseRel create(
		long commerceInventoryWarehouseRelId);

	/**
	 * Removes the commerce inventory warehouse rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel that was removed
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public CommerceInventoryWarehouseRel remove(
			long commerceInventoryWarehouseRelId)
		throws NoSuchInventoryWarehouseRelException;

	public CommerceInventoryWarehouseRel updateImpl(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel);

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or throws a <code>NoSuchInventoryWarehouseRelException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public CommerceInventoryWarehouseRel findByPrimaryKey(
			long commerceInventoryWarehouseRelId)
		throws NoSuchInventoryWarehouseRelException;

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel, or <code>null</code> if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public CommerceInventoryWarehouseRel fetchByPrimaryKey(
		long commerceInventoryWarehouseRelId);

	/**
	 * Returns all the commerce inventory warehouse rels.
	 *
	 * @return the commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findAll();

	/**
	 * Returns a range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouseRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouse rels
	 */
	public java.util.List<CommerceInventoryWarehouseRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouseRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce inventory warehouse rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce inventory warehouse rels.
	 *
	 * @return the number of commerce inventory warehouse rels
	 */
	public int countAll();

}