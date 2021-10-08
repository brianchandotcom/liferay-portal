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

import com.liferay.commerce.order.rule.exception.NoSuchCOREntryRelException;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cor entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see COREntryRelUtil
 * @generated
 */
@ProviderType
public interface COREntryRelPersistence extends BasePersistence<COREntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link COREntryRelUtil} to access the cor entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cor entry rels where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByCOREntryId(long COREntryId);

	/**
	 * Returns a range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end);

	/**
	 * Returns an ordered range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public COREntryRel findByCOREntryId_First(
			long COREntryId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
				orderByComparator)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the first cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public COREntryRel fetchByCOREntryId_First(
		long COREntryId,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns the last cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public COREntryRel findByCOREntryId_Last(
			long COREntryId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
				orderByComparator)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the last cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public COREntryRel fetchByCOREntryId_Last(
		long COREntryId,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns the cor entry rels before and after the current cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryRelId the primary key of the current cor entry rel
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public COREntryRel[] findByCOREntryId_PrevAndNext(
			long COREntryRelId, long COREntryId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
				orderByComparator)
		throws NoSuchCOREntryRelException;

	/**
	 * Removes all the cor entry rels where COREntryId = &#63; from the database.
	 *
	 * @param COREntryId the cor entry ID
	 */
	public void removeByCOREntryId(long COREntryId);

	/**
	 * Returns the number of cor entry rels where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	public int countByCOREntryId(long COREntryId);

	/**
	 * Returns all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByC_C(
		long classNameId, long COREntryId);

	/**
	 * Returns a range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end);

	/**
	 * Returns an ordered range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entry rels
	 */
	public java.util.List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public COREntryRel findByC_C_First(
			long classNameId, long COREntryId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
				orderByComparator)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the first cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public COREntryRel fetchByC_C_First(
		long classNameId, long COREntryId,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns the last cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public COREntryRel findByC_C_Last(
			long classNameId, long COREntryId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
				orderByComparator)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the last cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public COREntryRel fetchByC_C_Last(
		long classNameId, long COREntryId,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns the cor entry rels before and after the current cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param COREntryRelId the primary key of the current cor entry rel
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public COREntryRel[] findByC_C_PrevAndNext(
			long COREntryRelId, long classNameId, long COREntryId,
			com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
				orderByComparator)
		throws NoSuchCOREntryRelException;

	/**
	 * Removes all the cor entry rels where classNameId = &#63; and COREntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 */
	public void removeByC_C(long classNameId, long COREntryId);

	/**
	 * Returns the number of cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	public int countByC_C(long classNameId, long COREntryId);

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or throws a <code>NoSuchCOREntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public COREntryRel findByC_C_C(
			long classNameId, long classPK, long COREntryId)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public COREntryRel fetchByC_C_C(
		long classNameId, long classPK, long COREntryId);

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public COREntryRel fetchByC_C_C(
		long classNameId, long classPK, long COREntryId,
		boolean useFinderCache);

	/**
	 * Removes the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the cor entry rel that was removed
	 */
	public COREntryRel removeByC_C_C(
			long classNameId, long classPK, long COREntryId)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the number of cor entry rels where classNameId = &#63; and classPK = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	public int countByC_C_C(long classNameId, long classPK, long COREntryId);

	/**
	 * Caches the cor entry rel in the entity cache if it is enabled.
	 *
	 * @param corEntryRel the cor entry rel
	 */
	public void cacheResult(COREntryRel corEntryRel);

	/**
	 * Caches the cor entry rels in the entity cache if it is enabled.
	 *
	 * @param corEntryRels the cor entry rels
	 */
	public void cacheResult(java.util.List<COREntryRel> corEntryRels);

	/**
	 * Creates a new cor entry rel with the primary key. Does not add the cor entry rel to the database.
	 *
	 * @param COREntryRelId the primary key for the new cor entry rel
	 * @return the new cor entry rel
	 */
	public COREntryRel create(long COREntryRelId);

	/**
	 * Removes the cor entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel that was removed
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public COREntryRel remove(long COREntryRelId)
		throws NoSuchCOREntryRelException;

	public COREntryRel updateImpl(COREntryRel corEntryRel);

	/**
	 * Returns the cor entry rel with the primary key or throws a <code>NoSuchCOREntryRelException</code> if it could not be found.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public COREntryRel findByPrimaryKey(long COREntryRelId)
		throws NoSuchCOREntryRelException;

	/**
	 * Returns the cor entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel, or <code>null</code> if a cor entry rel with the primary key could not be found
	 */
	public COREntryRel fetchByPrimaryKey(long COREntryRelId);

	/**
	 * Returns all the cor entry rels.
	 *
	 * @return the cor entry rels
	 */
	public java.util.List<COREntryRel> findAll();

	/**
	 * Returns a range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of cor entry rels
	 */
	public java.util.List<COREntryRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cor entry rels
	 */
	public java.util.List<COREntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cor entry rels
	 */
	public java.util.List<COREntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<COREntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cor entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cor entry rels.
	 *
	 * @return the number of cor entry rels
	 */
	public int countAll();

}