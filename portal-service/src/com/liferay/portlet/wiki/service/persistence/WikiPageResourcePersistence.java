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

import com.liferay.portlet.wiki.model.WikiPageResource;

/**
 * The persistence interface for the wiki page resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResourcePersistenceImpl
 * @see WikiPageResourceUtil
 * @generated
 */
public interface WikiPageResourcePersistence extends BasePersistence<WikiPageResource> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageResourceUtil} to access the wiki page resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the wiki page resource in the entity cache if it is enabled.
	*
	* @param wikiPageResource the wiki page resource
	*/
	public void cacheResult(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource);

	/**
	* Caches the wiki page resources in the entity cache if it is enabled.
	*
	* @param wikiPageResources the wiki page resources
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> wikiPageResources);

	/**
	* Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	*
	* @param resourcePrimKey the primary key for the new wiki page resource
	* @return the new wiki page resource
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource create(
		long resourcePrimKey);

	/**
	* Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource that was removed
	* @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource remove(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	public com.liferay.portlet.wiki.model.WikiPageResource updateImpl(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the wiki page resource with the primary key or throws a {@link com.liferay.portlet.wiki.NoSuchPageResourceException} if it could not be found.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource
	* @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource findByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	/**
	* Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource fetchByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the wiki page resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of matching wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki page resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first wiki page resource in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki page resource
	* @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	/**
	* Returns the last wiki page resource in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki page resource
	* @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	/**
	* Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the primary key of the current wiki page resource
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki page resource
	* @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource[] findByUuid_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchPageResourceException} if it could not be found.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki page resource
	* @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource findByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource fetchByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param nodeId the node ID
	* @param title the title
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource fetchByN_T(
		long nodeId, java.lang.String title, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the wiki page resources.
	*
	* @return the wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the wiki page resources where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the wiki page resource where nodeId = &#63; and title = &#63; from the database.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the wiki page resource that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPageResource removeByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageResourceException;

	/**
	* Removes all the wiki page resources from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki page resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki page resources where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID
	* @param title the title
	* @return the number of matching wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of wiki page resources.
	*
	* @return the number of wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}