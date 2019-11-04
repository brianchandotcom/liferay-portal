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

package com.liferay.depot.service.persistence;

import com.liferay.depot.exception.NoSuchEntryGroupRelException;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the depot entry group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelUtil
 * @generated
 */
@ProviderType
public interface DepotEntryGroupRelPersistence
	extends BasePersistence<DepotEntryGroupRel> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DepotEntryGroupRelUtil} to access the depot entry group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId);

	/**
	 * Returns a range of all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel findByDepotEntryId_First(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
				orderByComparator)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the first depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel fetchByDepotEntryId_First(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns the last depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel findByDepotEntryId_Last(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
				orderByComparator)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the last depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel fetchByDepotEntryId_Last(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns the depot entry group rels before and after the current depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryGroupRelId the primary key of the current depot entry group rel
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	public DepotEntryGroupRel[] findByDepotEntryId_PrevAndNext(
			long depotEntryGroupRelId, long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
				orderByComparator)
		throws NoSuchEntryGroupRelException;

	/**
	 * Removes all the depot entry group rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public void removeByDepotEntryId(long depotEntryId);

	/**
	 * Returns the number of depot entry group rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry group rels
	 */
	public int countByDepotEntryId(long depotEntryId);

	/**
	 * Returns all the depot entry group rels where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @return the matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByToGroupId(long toGroupId);

	/**
	 * Returns a range of all the depot entry group rels where toGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param toGroupId the to group ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByToGroupId(
		long toGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the depot entry group rels where toGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param toGroupId the to group ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByToGroupId(
		long toGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry group rels where toGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param toGroupId the to group ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findByToGroupId(
		long toGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel findByToGroupId_First(
			long toGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
				orderByComparator)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the first depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel fetchByToGroupId_First(
		long toGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns the last depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel findByToGroupId_Last(
			long toGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
				orderByComparator)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the last depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel fetchByToGroupId_Last(
		long toGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns the depot entry group rels before and after the current depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param depotEntryGroupRelId the primary key of the current depot entry group rel
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	public DepotEntryGroupRel[] findByToGroupId_PrevAndNext(
			long depotEntryGroupRelId, long toGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
				orderByComparator)
		throws NoSuchEntryGroupRelException;

	/**
	 * Removes all the depot entry group rels where toGroupId = &#63; from the database.
	 *
	 * @param toGroupId the to group ID
	 */
	public void removeByToGroupId(long toGroupId);

	/**
	 * Returns the number of depot entry group rels where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @return the number of matching depot entry group rels
	 */
	public int countByToGroupId(long toGroupId);

	/**
	 * Returns the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; or throws a <code>NoSuchEntryGroupRelException</code> if it could not be found.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel findByD_TGI(long depotEntryId, long toGroupId)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel fetchByD_TGI(long depotEntryId, long toGroupId);

	/**
	 * Returns the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	public DepotEntryGroupRel fetchByD_TGI(
		long depotEntryId, long toGroupId, boolean useFinderCache);

	/**
	 * Removes the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the depot entry group rel that was removed
	 */
	public DepotEntryGroupRel removeByD_TGI(long depotEntryId, long toGroupId)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the number of depot entry group rels where depotEntryId = &#63; and toGroupId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the number of matching depot entry group rels
	 */
	public int countByD_TGI(long depotEntryId, long toGroupId);

	/**
	 * Caches the depot entry group rel in the entity cache if it is enabled.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 */
	public void cacheResult(DepotEntryGroupRel depotEntryGroupRel);

	/**
	 * Caches the depot entry group rels in the entity cache if it is enabled.
	 *
	 * @param depotEntryGroupRels the depot entry group rels
	 */
	public void cacheResult(
		java.util.List<DepotEntryGroupRel> depotEntryGroupRels);

	/**
	 * Creates a new depot entry group rel with the primary key. Does not add the depot entry group rel to the database.
	 *
	 * @param depotEntryGroupRelId the primary key for the new depot entry group rel
	 * @return the new depot entry group rel
	 */
	public DepotEntryGroupRel create(long depotEntryGroupRelId);

	/**
	 * Removes the depot entry group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel that was removed
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	public DepotEntryGroupRel remove(long depotEntryGroupRelId)
		throws NoSuchEntryGroupRelException;

	public DepotEntryGroupRel updateImpl(DepotEntryGroupRel depotEntryGroupRel);

	/**
	 * Returns the depot entry group rel with the primary key or throws a <code>NoSuchEntryGroupRelException</code> if it could not be found.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	public DepotEntryGroupRel findByPrimaryKey(long depotEntryGroupRelId)
		throws NoSuchEntryGroupRelException;

	/**
	 * Returns the depot entry group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel, or <code>null</code> if a depot entry group rel with the primary key could not be found
	 */
	public DepotEntryGroupRel fetchByPrimaryKey(long depotEntryGroupRelId);

	/**
	 * Returns all the depot entry group rels.
	 *
	 * @return the depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findAll();

	/**
	 * Returns a range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot entry group rels
	 */
	public java.util.List<DepotEntryGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the depot entry group rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of depot entry group rels.
	 *
	 * @return the number of depot entry group rels
	 */
	public int countAll();

}