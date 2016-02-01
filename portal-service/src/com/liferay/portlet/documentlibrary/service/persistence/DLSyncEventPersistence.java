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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.exception.NoSuchSyncEventException;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;

/**
 * The persistence interface for the d l sync event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLSyncEventPersistenceImpl
 * @see DLSyncEventUtil
 * @generated
 */
@ProviderType
public interface DLSyncEventPersistence extends BasePersistence<DLSyncEvent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLSyncEventUtil} to access the d l sync event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d l sync events where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @return the matching d l sync events
	*/
	public java.util.List<DLSyncEvent> findByModifiedTime(long modifiedTime);

	/**
	* Returns a range of all the d l sync events where modifiedTime &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedTime the modified time
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @return the range of matching d l sync events
	*/
	public java.util.List<DLSyncEvent> findByModifiedTime(long modifiedTime,
		int start, int end);

	/**
	* Returns an ordered range of all the d l sync events where modifiedTime &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedTime the modified time
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l sync events
	*/
	public java.util.List<DLSyncEvent> findByModifiedTime(long modifiedTime,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator);

	/**
	* Returns an ordered range of all the d l sync events where modifiedTime &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedTime the modified time
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d l sync events
	*/
	public java.util.List<DLSyncEvent> findByModifiedTime(long modifiedTime,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event
	* @throws NoSuchSyncEventException if a matching d l sync event could not be found
	*/
	public DLSyncEvent findByModifiedTime_First(long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchSyncEventException;

	/**
	* Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public DLSyncEvent fetchByModifiedTime_First(long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator);

	/**
	* Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event
	* @throws NoSuchSyncEventException if a matching d l sync event could not be found
	*/
	public DLSyncEvent findByModifiedTime_Last(long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchSyncEventException;

	/**
	* Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public DLSyncEvent fetchByModifiedTime_Last(long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator);

	/**
	* Returns the d l sync events before and after the current d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param syncEventId the primary key of the current d l sync event
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l sync event
	* @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	*/
	public DLSyncEvent[] findByModifiedTime_PrevAndNext(long syncEventId,
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchSyncEventException;

	/**
	* Removes all the d l sync events where modifiedTime &gt; &#63; from the database.
	*
	* @param modifiedTime the modified time
	*/
	public void removeByModifiedTime(long modifiedTime);

	/**
	* Returns the number of d l sync events where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @return the number of matching d l sync events
	*/
	public int countByModifiedTime(long modifiedTime);

	/**
	* Returns the d l sync event where typePK = &#63; or throws a {@link NoSuchSyncEventException} if it could not be found.
	*
	* @param typePK the type p k
	* @return the matching d l sync event
	* @throws NoSuchSyncEventException if a matching d l sync event could not be found
	*/
	public DLSyncEvent findByTypePK(long typePK)
		throws NoSuchSyncEventException;

	/**
	* Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param typePK the type p k
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public DLSyncEvent fetchByTypePK(long typePK);

	/**
	* Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param typePK the type p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public DLSyncEvent fetchByTypePK(long typePK, boolean retrieveFromCache);

	/**
	* Removes the d l sync event where typePK = &#63; from the database.
	*
	* @param typePK the type p k
	* @return the d l sync event that was removed
	*/
	public DLSyncEvent removeByTypePK(long typePK)
		throws NoSuchSyncEventException;

	/**
	* Returns the number of d l sync events where typePK = &#63;.
	*
	* @param typePK the type p k
	* @return the number of matching d l sync events
	*/
	public int countByTypePK(long typePK);

	/**
	* Caches the d l sync event in the entity cache if it is enabled.
	*
	* @param dlSyncEvent the d l sync event
	*/
	public void cacheResult(DLSyncEvent dlSyncEvent);

	/**
	* Caches the d l sync events in the entity cache if it is enabled.
	*
	* @param dlSyncEvents the d l sync events
	*/
	public void cacheResult(java.util.List<DLSyncEvent> dlSyncEvents);

	/**
	* Creates a new d l sync event with the primary key. Does not add the d l sync event to the database.
	*
	* @param syncEventId the primary key for the new d l sync event
	* @return the new d l sync event
	*/
	public DLSyncEvent create(long syncEventId);

	/**
	* Removes the d l sync event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event that was removed
	* @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	*/
	public DLSyncEvent remove(long syncEventId) throws NoSuchSyncEventException;

	public DLSyncEvent updateImpl(DLSyncEvent dlSyncEvent);

	/**
	* Returns the d l sync event with the primary key or throws a {@link NoSuchSyncEventException} if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event
	* @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	*/
	public DLSyncEvent findByPrimaryKey(long syncEventId)
		throws NoSuchSyncEventException;

	/**
	* Returns the d l sync event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event, or <code>null</code> if a d l sync event with the primary key could not be found
	*/
	public DLSyncEvent fetchByPrimaryKey(long syncEventId);

	@Override
	public java.util.Map<java.io.Serializable, DLSyncEvent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d l sync events.
	*
	* @return the d l sync events
	*/
	public java.util.List<DLSyncEvent> findAll();

	/**
	* Returns a range of all the d l sync events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @return the range of d l sync events
	*/
	public java.util.List<DLSyncEvent> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d l sync events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l sync events
	*/
	public java.util.List<DLSyncEvent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator);

	/**
	* Returns an ordered range of all the d l sync events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d l sync events
	*/
	public java.util.List<DLSyncEvent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d l sync events from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d l sync events.
	*
	* @return the number of d l sync events
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}