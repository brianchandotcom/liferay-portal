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

package com.liferay.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.exception.NoSuchArticleResourceException;
import com.liferay.journal.model.JournalArticleResource;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the journal article resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.journal.service.persistence.impl.JournalArticleResourcePersistenceImpl
 * @see JournalArticleResourceUtil
 * @generated
 */
@ProviderType
public interface JournalArticleResourcePersistence extends BasePersistence<JournalArticleResource> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleResourceUtil} to access the journal article resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal article resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where uuid = &#63;.
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public JournalArticleResource[] findByUuid_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Removes all the journal article resources where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of journal article resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal article resources
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchArticleResourceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchArticleResourceException;

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the journal article resource where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal article resource that was removed
	*/
	public JournalArticleResource removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchArticleResourceException;

	/**
	* Returns the number of journal article resources where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal article resources
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public JournalArticleResource[] findByUuid_C_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Removes all the journal article resources where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal article resources
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the journal article resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByGroupId(long groupId);

	/**
	* Returns a range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns an ordered range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article resources
	*/
	public java.util.List<JournalArticleResource> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Returns the first journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns the last journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Returns the last journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where groupId = &#63;.
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException;

	/**
	* Removes all the journal article resources where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of journal article resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal article resources
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or throws a {@link NoSuchArticleResourceException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal article resource
	* @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public JournalArticleResource findByG_A(long groupId,
		java.lang.String articleId) throws NoSuchArticleResourceException;

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByG_A(long groupId,
		java.lang.String articleId);

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public JournalArticleResource fetchByG_A(long groupId,
		java.lang.String articleId, boolean retrieveFromCache);

	/**
	* Removes the journal article resource where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the journal article resource that was removed
	*/
	public JournalArticleResource removeByG_A(long groupId,
		java.lang.String articleId) throws NoSuchArticleResourceException;

	/**
	* Returns the number of journal article resources where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal article resources
	*/
	public int countByG_A(long groupId, java.lang.String articleId);

	/**
	* Caches the journal article resource in the entity cache if it is enabled.
	*
	* @param journalArticleResource the journal article resource
	*/
	public void cacheResult(JournalArticleResource journalArticleResource);

	/**
	* Caches the journal article resources in the entity cache if it is enabled.
	*
	* @param journalArticleResources the journal article resources
	*/
	public void cacheResult(
		java.util.List<JournalArticleResource> journalArticleResources);

	/**
	* Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	*
	* @param resourcePrimKey the primary key for the new journal article resource
	* @return the new journal article resource
	*/
	public JournalArticleResource create(long resourcePrimKey);

	/**
	* Removes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource that was removed
	* @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public JournalArticleResource remove(long resourcePrimKey)
		throws NoSuchArticleResourceException;

	public JournalArticleResource updateImpl(
		JournalArticleResource journalArticleResource);

	/**
	* Returns the journal article resource with the primary key or throws a {@link NoSuchArticleResourceException} if it could not be found.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource
	* @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public JournalArticleResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchArticleResourceException;

	/**
	* Returns the journal article resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource, or <code>null</code> if a journal article resource with the primary key could not be found
	*/
	public JournalArticleResource fetchByPrimaryKey(long resourcePrimKey);

	@Override
	public java.util.Map<java.io.Serializable, JournalArticleResource> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the journal article resources.
	*
	* @return the journal article resources
	*/
	public java.util.List<JournalArticleResource> findAll();

	/**
	* Returns a range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of journal article resources
	*/
	public java.util.List<JournalArticleResource> findAll(int start, int end);

	/**
	* Returns an ordered range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal article resources
	*/
	public java.util.List<JournalArticleResource> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator);

	/**
	* Returns an ordered range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal article resources
	*/
	public java.util.List<JournalArticleResource> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal article resources from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal article resources.
	*
	* @return the number of journal article resources
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}