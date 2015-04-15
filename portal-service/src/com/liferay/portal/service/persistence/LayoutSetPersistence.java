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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.LayoutSet;

/**
 * The persistence interface for the layout set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.LayoutSetPersistenceImpl
 * @see LayoutSetUtil
 * @generated
 */
@ProviderType
public interface LayoutSetPersistence extends BasePersistence<LayoutSet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetUtil} to access the layout set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the layout set where groupId = &#63; or throws a {@link NoSuchLayoutSetException} if it could not be found.
	*
	* @param groupId the group ID
	* @return the matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByGroupId(long groupId)
		throws com.liferay.portal.NoSuchLayoutSetException;

	/**
	* Returns the layout set where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByGroupId(long groupId);

	/**
	* Returns the layout set where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByGroupId(long groupId, boolean retrieveFromCache);

	/**
	* Removes the layout set where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @return the layout set that was removed
	*/
	public LayoutSet removeByGroupId(long groupId)
		throws com.liferay.portal.NoSuchLayoutSetException;

	/**
	* Returns the number of layout sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout sets
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid);

	/**
	* Returns a range of all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid, int start, int end);

	/**
	* Returns an ordered range of all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the first layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByLayoutSetPrototypeUuid_First(
		java.lang.String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetException;

	/**
	* Returns the first layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByLayoutSetPrototypeUuid_First(
		java.lang.String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the last layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByLayoutSetPrototypeUuid_Last(
		java.lang.String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetException;

	/**
	* Returns the last layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByLayoutSetPrototypeUuid_Last(
		java.lang.String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the layout sets before and after the current layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetId the primary key of the current layout set
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetId, java.lang.String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetException;

	/**
	* Removes all the layout sets where layoutSetPrototypeUuid = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	*/
	public void removeByLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid);

	/**
	* Returns the number of layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the number of matching layout sets
	*/
	public int countByLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid);

	/**
	* Caches the layout set in the entity cache if it is enabled.
	*
	* @param layoutSet the layout set
	*/
	public void cacheResult(LayoutSet layoutSet);

	/**
	* Caches the layout sets in the entity cache if it is enabled.
	*
	* @param layoutSets the layout sets
	*/
	public void cacheResult(java.util.List<LayoutSet> layoutSets);

	/**
	* Creates a new layout set with the primary key. Does not add the layout set to the database.
	*
	* @param layoutSetId the primary key for the new layout set
	* @return the new layout set
	*/
	public LayoutSet create(long layoutSetId);

	/**
	* Removes the layout set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set that was removed
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet remove(long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException;

	public LayoutSet updateImpl(LayoutSet layoutSet);

	/**
	* Returns the layout set with the primary key or throws a {@link NoSuchLayoutSetException} if it could not be found.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet findByPrimaryKey(long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException;

	/**
	* Returns the layout set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set, or <code>null</code> if a layout set with the primary key could not be found
	*/
	public LayoutSet fetchByPrimaryKey(long layoutSetId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutSet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout sets.
	*
	* @return the layout sets
	*/
	public java.util.List<LayoutSet> findAll();

	/**
	* Returns a range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of layout sets
	*/
	public java.util.List<LayoutSet> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout sets
	*/
	public java.util.List<LayoutSet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Removes all the layout sets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout sets.
	*
	* @return the number of layout sets
	*/
	public int countAll();
}