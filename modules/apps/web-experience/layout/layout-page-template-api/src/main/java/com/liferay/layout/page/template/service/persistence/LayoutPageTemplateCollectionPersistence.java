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

package com.liferay.layout.page.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the layout page template collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateCollectionPersistenceImpl
 * @see LayoutPageTemplateCollectionUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateCollectionPersistence extends BasePersistence<LayoutPageTemplateCollection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateCollectionUtil} to access the layout page template collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout page template collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the layout page template collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template collections where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the first layout page template collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the last layout page template collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the last layout page template collection in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63;.
	*
	* @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection[] findByGroupId_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns all the layout page template collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63;.
	*
	* @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Removes all the layout page template collections where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout page template collections where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template collections
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of layout page template collections that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template collections that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the layout page template collection where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateCollectionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByG_N(long groupId,
		java.lang.String name) throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the layout page template collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByG_N(long groupId,
		java.lang.String name);

	/**
	* Returns the layout page template collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the layout page template collection where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the layout page template collection that was removed
	*/
	public LayoutPageTemplateCollection removeByG_N(long groupId,
		java.lang.String name) throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the number of layout page template collections where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template collections
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, java.lang.String name);

	/**
	* Returns a range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByG_LikeN_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the first layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByG_LikeN_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the last layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByG_LikeN_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the last layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByG_LikeN_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection[] findByG_LikeN_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns all the layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name);

	/**
	* Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection[] filterFindByG_LikeN_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Removes all the layout page template collections where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of layout page template collections where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template collections
	*/
	public int countByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns the number of layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template collections that the user has permission to view
	*/
	public int filterCountByG_LikeN(long groupId, java.lang.String name);

	/**
	* Returns all the layout page template collections where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_T(
		long groupId, int type);

	/**
	* Returns a range of all the layout page template collections where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_T(
		long groupId, int type, int start, int end);

	/**
	* Returns an ordered range of all the layout page template collections where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template collections where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the first layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByG_T_First(long groupId,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the last layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection findByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the last layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	*/
	public LayoutPageTemplateCollection fetchByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection[] findByG_T_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns all the layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_T(
		long groupId, int type);

	/**
	* Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_T(
		long groupId, int type, int start, int end);

	/**
	* Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template collections that the user has permission to view
	*/
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection[] filterFindByG_T_PrevAndNext(
		long layoutPageTemplateCollectionId, long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Removes all the layout page template collections where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public void removeByG_T(long groupId, int type);

	/**
	* Returns the number of layout page template collections where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching layout page template collections
	*/
	public int countByG_T(long groupId, int type);

	/**
	* Returns the number of layout page template collections that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching layout page template collections that the user has permission to view
	*/
	public int filterCountByG_T(long groupId, int type);

	/**
	* Caches the layout page template collection in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateCollection the layout page template collection
	*/
	public void cacheResult(
		LayoutPageTemplateCollection layoutPageTemplateCollection);

	/**
	* Caches the layout page template collections in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateCollections the layout page template collections
	*/
	public void cacheResult(
		java.util.List<LayoutPageTemplateCollection> layoutPageTemplateCollections);

	/**
	* Creates a new layout page template collection with the primary key. Does not add the layout page template collection to the database.
	*
	* @param layoutPageTemplateCollectionId the primary key for the new layout page template collection
	* @return the new layout page template collection
	*/
	public LayoutPageTemplateCollection create(
		long layoutPageTemplateCollectionId);

	/**
	* Removes the layout page template collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	* @return the layout page template collection that was removed
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection remove(
		long layoutPageTemplateCollectionId)
		throws NoSuchPageTemplateCollectionException;

	public LayoutPageTemplateCollection updateImpl(
		LayoutPageTemplateCollection layoutPageTemplateCollection);

	/**
	* Returns the layout page template collection with the primary key or throws a {@link NoSuchPageTemplateCollectionException} if it could not be found.
	*
	* @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	* @return the layout page template collection
	* @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection findByPrimaryKey(
		long layoutPageTemplateCollectionId)
		throws NoSuchPageTemplateCollectionException;

	/**
	* Returns the layout page template collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	* @return the layout page template collection, or <code>null</code> if a layout page template collection with the primary key could not be found
	*/
	public LayoutPageTemplateCollection fetchByPrimaryKey(
		long layoutPageTemplateCollectionId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutPageTemplateCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout page template collections.
	*
	* @return the layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findAll();

	/**
	* Returns a range of all the layout page template collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @return the range of layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the layout page template collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator);

	/**
	* Returns an ordered range of all the layout page template collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template collections
	* @param end the upper bound of the range of layout page template collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout page template collections
	*/
	public java.util.List<LayoutPageTemplateCollection> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutPageTemplateCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout page template collections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout page template collections.
	*
	* @return the number of layout page template collections
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}