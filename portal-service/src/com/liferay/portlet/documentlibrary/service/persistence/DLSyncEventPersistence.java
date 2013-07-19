/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLSyncEvent;

/**
 * The persistence interface for the d l sync event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventPersistenceImpl
 * @see DLSyncEventUtil
 * @generated
 */
public interface DLSyncEventPersistence extends BasePersistence<DLSyncEvent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLSyncEventUtil} to access the d l sync event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the d l sync event where typeId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncEventException} if it could not be found.
	*
	* @param typeId the type ID
	* @return the matching d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent findByTypeId(
		long typeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	/**
	* Returns the d l sync event where typeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param typeId the type ID
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByTypeId(
		long typeId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d l sync event where typeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param typeId the type ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByTypeId(
		long typeId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d l sync event where typeId = &#63; from the database.
	*
	* @param typeId the type ID
	* @return the d l sync event that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent removeByTypeId(
		long typeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	/**
	* Returns the number of d l sync events where typeId = &#63;.
	*
	* @param typeId the type ID
	* @return the number of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public int countByTypeId(long typeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d l sync events where modifiedDate &gt; &#63;.
	*
	* @param modifiedDate the modified date
	* @return the matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findByModifiedDate(
		long modifiedDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d l sync events where modifiedDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @return the range of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findByModifiedDate(
		long modifiedDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d l sync events where modifiedDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findByModifiedDate(
		long modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d l sync event in the ordered set where modifiedDate &gt; &#63;.
	*
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent findByModifiedDate_First(
		long modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	/**
	* Returns the first d l sync event in the ordered set where modifiedDate &gt; &#63;.
	*
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByModifiedDate_First(
		long modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last d l sync event in the ordered set where modifiedDate &gt; &#63;.
	*
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent findByModifiedDate_Last(
		long modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	/**
	* Returns the last d l sync event in the ordered set where modifiedDate &gt; &#63;.
	*
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByModifiedDate_Last(
		long modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d l sync events before and after the current d l sync event in the ordered set where modifiedDate &gt; &#63;.
	*
	* @param syncEventId the primary key of the current d l sync event
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent[] findByModifiedDate_PrevAndNext(
		long syncEventId, long modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	/**
	* Removes all the d l sync events where modifiedDate &gt; &#63; from the database.
	*
	* @param modifiedDate the modified date
	* @throws SystemException if a system exception occurred
	*/
	public void removeByModifiedDate(long modifiedDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d l sync events where modifiedDate &gt; &#63;.
	*
	* @param modifiedDate the modified date
	* @return the number of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public int countByModifiedDate(long modifiedDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the d l sync event in the entity cache if it is enabled.
	*
	* @param dlSyncEvent the d l sync event
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLSyncEvent dlSyncEvent);

	/**
	* Caches the d l sync events in the entity cache if it is enabled.
	*
	* @param dlSyncEvents the d l sync events
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> dlSyncEvents);

	/**
	* Creates a new d l sync event with the primary key. Does not add the d l sync event to the database.
	*
	* @param syncEventId the primary key for the new d l sync event
	* @return the new d l sync event
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent create(
		long syncEventId);

	/**
	* Removes the d l sync event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent remove(
		long syncEventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	public com.liferay.portlet.documentlibrary.model.DLSyncEvent updateImpl(
		com.liferay.portlet.documentlibrary.model.DLSyncEvent dlSyncEvent)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d l sync event with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncEventException} if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent findByPrimaryKey(
		long syncEventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException;

	/**
	* Returns the d l sync event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event, or <code>null</code> if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByPrimaryKey(
		long syncEventId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d l sync events.
	*
	* @return the d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d l sync events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @return the range of d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d l sync events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l sync events from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d l sync events.
	*
	* @return the number of d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}