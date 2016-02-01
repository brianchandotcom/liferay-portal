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

package com.liferay.wiki.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.wiki.exception.NoSuchPageResourceException;
import com.liferay.wiki.model.WikiPageResource;

/**
 * The persistence interface for the wiki page resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.wiki.service.persistence.impl.WikiPageResourcePersistenceImpl
 * @see WikiPageResourceUtil
 * @generated
 */
@ProviderType
public interface WikiPageResourcePersistence extends BasePersistence<WikiPageResource> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageResourceUtil} to access the wiki page resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the wiki page resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource
	* @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public WikiPageResource findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException;

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource
	* @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public WikiPageResource findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException;

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63;.
	*
	* @param resourcePrimKey the primary key of the current wiki page resource
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page resource
	* @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public WikiPageResource[] findByUuid_PrevAndNext(long resourcePrimKey,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException;

	/**
	* Removes all the wiki page resources where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of wiki page resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wiki page resources
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the wiki page resource where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPageResourceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki page resource
	* @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public WikiPageResource findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchPageResourceException;

	/**
	* Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the wiki page resource where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the wiki page resource that was removed
	*/
	public WikiPageResource removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchPageResourceException;

	/**
	* Returns the number of wiki page resources where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching wiki page resources
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wiki page resources
	*/
	public java.util.List<WikiPageResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource
	* @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public WikiPageResource findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException;

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource
	* @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public WikiPageResource findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException;

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param resourcePrimKey the primary key of the current wiki page resource
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page resource
	* @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public WikiPageResource[] findByUuid_C_PrevAndNext(long resourcePrimKey,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException;

	/**
	* Removes all the wiki page resources where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of wiki page resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wiki page resources
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or throws a {@link NoSuchPageResourceException} if it could not be found.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki page resource
	* @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	*/
	public WikiPageResource findByN_T(long nodeId, java.lang.String title)
		throws NoSuchPageResourceException;

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByN_T(long nodeId, java.lang.String title);

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	*/
	public WikiPageResource fetchByN_T(long nodeId, java.lang.String title,
		boolean retrieveFromCache);

	/**
	* Removes the wiki page resource where nodeId = &#63; and title = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the wiki page resource that was removed
	*/
	public WikiPageResource removeByN_T(long nodeId, java.lang.String title)
		throws NoSuchPageResourceException;

	/**
	* Returns the number of wiki page resources where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the number of matching wiki page resources
	*/
	public int countByN_T(long nodeId, java.lang.String title);

	/**
	* Caches the wiki page resource in the entity cache if it is enabled.
	*
	* @param wikiPageResource the wiki page resource
	*/
	public void cacheResult(WikiPageResource wikiPageResource);

	/**
	* Caches the wiki page resources in the entity cache if it is enabled.
	*
	* @param wikiPageResources the wiki page resources
	*/
	public void cacheResult(java.util.List<WikiPageResource> wikiPageResources);

	/**
	* Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	*
	* @param resourcePrimKey the primary key for the new wiki page resource
	* @return the new wiki page resource
	*/
	public WikiPageResource create(long resourcePrimKey);

	/**
	* Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource that was removed
	* @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public WikiPageResource remove(long resourcePrimKey)
		throws NoSuchPageResourceException;

	public WikiPageResource updateImpl(WikiPageResource wikiPageResource);

	/**
	* Returns the wiki page resource with the primary key or throws a {@link NoSuchPageResourceException} if it could not be found.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource
	* @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	*/
	public WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchPageResourceException;

	/**
	* Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	*/
	public WikiPageResource fetchByPrimaryKey(long resourcePrimKey);

	@Override
	public java.util.Map<java.io.Serializable, WikiPageResource> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the wiki page resources.
	*
	* @return the wiki page resources
	*/
	public java.util.List<WikiPageResource> findAll();

	/**
	* Returns a range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of wiki page resources
	*/
	public java.util.List<WikiPageResource> findAll(int start, int end);

	/**
	* Returns an ordered range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wiki page resources
	*/
	public java.util.List<WikiPageResource> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator);

	/**
	* Returns an ordered range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of wiki page resources
	*/
	public java.util.List<WikiPageResource> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WikiPageResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the wiki page resources from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of wiki page resources.
	*
	* @return the number of wiki page resources
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}