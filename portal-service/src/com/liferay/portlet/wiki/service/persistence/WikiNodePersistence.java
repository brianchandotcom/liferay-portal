/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.wiki.model.WikiNode;

/**
 * The persistence interface for the wiki node service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodePersistenceImpl
 * @see WikiNodeUtil
 * @generated
 */
public interface WikiNodePersistence extends BasePersistence<WikiNode> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiNodeUtil} to access the wiki node persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the wiki node in the entity cache if it is enabled.
	*
	* @param wikiNode the wiki node
	*/
	public void cacheResult(com.liferay.portlet.wiki.model.WikiNode wikiNode);

	/**
	* Caches the wiki nodes in the entity cache if it is enabled.
	*
	* @param wikiNodes the wiki nodes
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiNode> wikiNodes);

	/**
	* Creates a new wiki node with the primary key. Does not add the wiki node to the database.
	*
	* @param nodeId the primary key for the new wiki node
	* @return the new wiki node
	*/
	public com.liferay.portlet.wiki.model.WikiNode create(long nodeId);

	/**
	* Removes the wiki node with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node that was removed
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode remove(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode updateImpl(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the wiki node with the primary key or throws a {@link com.liferay.portlet.wiki.NoSuchNodeException} if it could not be found.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByPrimaryKey(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki node with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node, or <code>null</code> if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode fetchByPrimaryKey(
		long nodeId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the wiki nodes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki nodes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki nodes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first wiki node in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the last wiki node in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the primary key of the current wiki node
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode[] findByUuid_PrevAndNext(
		long nodeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki node where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchNodeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki node where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the wiki node where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the wiki nodes where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki nodes where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki nodes where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first wiki node in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the last wiki node in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the primary key of the current wiki node
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode[] findByGroupId_PrevAndNext(
		long nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns all the wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching wiki nodes that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki nodes that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set of wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode[] filterFindByGroupId_PrevAndNext(
		long nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns all the wiki nodes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki nodes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki nodes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first wiki node in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the last wiki node in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the primary key of the current wiki node
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode[] findByCompanyId_PrevAndNext(
		long nodeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki node where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchNodeException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching wiki node
	* @throws com.liferay.portlet.wiki.NoSuchNodeException if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Returns the wiki node where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode fetchByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the wiki node where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the wiki nodes.
	*
	* @return the wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki nodes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki nodes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the wiki nodes where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the wiki node where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the wiki node that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Removes all the wiki nodes where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the wiki nodes where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the wiki node where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the wiki node that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiNode removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	/**
	* Removes all the wiki nodes from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching wiki nodes that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki nodes.
	*
	* @return the number of wiki nodes
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}