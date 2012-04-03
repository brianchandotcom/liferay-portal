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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalArticleResource;

/**
 * The persistence interface for the journal article resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResourcePersistenceImpl
 * @see JournalArticleResourceUtil
 * @generated
 */
public interface JournalArticleResourcePersistence extends BasePersistence<JournalArticleResource> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleResourceUtil} to access the journal article resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the journal article resource in the entity cache if it is enabled.
	*
	* @param journalArticleResource the journal article resource
	*/
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource);

	/**
	* Caches the journal article resources in the entity cache if it is enabled.
	*
	* @param journalArticleResources the journal article resources
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> journalArticleResources);

	/**
	* Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	*
	* @param resourcePrimKey the primary key for the new journal article resource
	* @return the new journal article resource
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource create(
		long resourcePrimKey);

	/**
	* Removes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource that was removed
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource remove(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	public com.liferay.portlet.journal.model.JournalArticleResource updateImpl(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal article resource with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleResourceException} if it could not be found.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource, or <code>null</code> if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource fetchByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal article resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource[] findByUuid_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleResourceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal article resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal article resource in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the last journal article resource in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleResourceException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal article resource
	* @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource fetchByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource fetchByG_A(
		long groupId, java.lang.String articleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal article resources.
	*
	* @return the journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal article resources where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal article resource where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal article resource that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Removes all the journal article resources where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal article resource where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the journal article resource that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource removeByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException;

	/**
	* Removes all the journal article resources from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal article resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal article resources where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal article resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal article resources where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal article resources.
	*
	* @return the number of journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}