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

package com.liferay.document.library.sync.service.persistence;

import com.liferay.document.library.sync.exception.NoSuchEventException;
import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dl sync event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventUtil
 * @generated
 */
@ProviderType
public interface DLSyncEventPersistence extends BasePersistence<DLSyncEvent> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLSyncEventUtil} to access the dl sync event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the matching dl sync events
	 */
	public java.util.List<DLSyncEvent> findByModifiedTime(long modifiedTime);

	/**
	 * Returns a range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of matching dl sync events
	 */
	public java.util.List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end);

	/**
	 * Returns an ordered range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl sync events
	 */
	public java.util.List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dl sync events
	 */
	public java.util.List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	public DLSyncEvent findByModifiedTime_First(
			long modifiedTime,
			com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
				orderByComparator)
		throws NoSuchEventException;

	/**
	 * Returns the first dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public DLSyncEvent fetchByModifiedTime_First(
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
			orderByComparator);

	/**
	 * Returns the last dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	public DLSyncEvent findByModifiedTime_Last(
			long modifiedTime,
			com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
				orderByComparator)
		throws NoSuchEventException;

	/**
	 * Returns the last dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public DLSyncEvent fetchByModifiedTime_Last(
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
			orderByComparator);

	/**
	 * Returns the dl sync events before and after the current dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param syncEventId the primary key of the current dl sync event
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	public DLSyncEvent[] findByModifiedTime_PrevAndNext(
			long syncEventId, long modifiedTime,
			com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
				orderByComparator)
		throws NoSuchEventException;

	/**
	 * Removes all the dl sync events where modifiedTime &gt; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 */
	public void removeByModifiedTime(long modifiedTime);

	/**
	 * Returns the number of dl sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the number of matching dl sync events
	 */
	public int countByModifiedTime(long modifiedTime);

	/**
	 * Returns the dl sync event where typePK = &#63; or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param typePK the type pk
	 * @return the matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	public DLSyncEvent findByTypePK(long typePK) throws NoSuchEventException;

	/**
	 * Returns the dl sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param typePK the type pk
	 * @return the matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public DLSyncEvent fetchByTypePK(long typePK);

	/**
	 * Returns the dl sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param typePK the type pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public DLSyncEvent fetchByTypePK(long typePK, boolean useFinderCache);

	/**
	 * Removes the dl sync event where typePK = &#63; from the database.
	 *
	 * @param typePK the type pk
	 * @return the dl sync event that was removed
	 */
	public DLSyncEvent removeByTypePK(long typePK) throws NoSuchEventException;

	/**
	 * Returns the number of dl sync events where typePK = &#63;.
	 *
	 * @param typePK the type pk
	 * @return the number of matching dl sync events
	 */
	public int countByTypePK(long typePK);

	/**
	 * Caches the dl sync event in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvent the dl sync event
	 */
	public void cacheResult(DLSyncEvent dlSyncEvent);

	/**
	 * Caches the dl sync events in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvents the dl sync events
	 */
	public void cacheResult(java.util.List<DLSyncEvent> dlSyncEvents);

	/**
	 * Creates a new dl sync event with the primary key. Does not add the dl sync event to the database.
	 *
	 * @param syncEventId the primary key for the new dl sync event
	 * @return the new dl sync event
	 */
	public DLSyncEvent create(long syncEventId);

	/**
	 * Removes the dl sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event that was removed
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	public DLSyncEvent remove(long syncEventId) throws NoSuchEventException;

	public DLSyncEvent updateImpl(DLSyncEvent dlSyncEvent);

	/**
	 * Returns the dl sync event with the primary key or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	public DLSyncEvent findByPrimaryKey(long syncEventId)
		throws NoSuchEventException;

	/**
	 * Returns the dl sync event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event, or <code>null</code> if a dl sync event with the primary key could not be found
	 */
	public DLSyncEvent fetchByPrimaryKey(long syncEventId);

	/**
	 * Returns all the dl sync events.
	 *
	 * @return the dl sync events
	 */
	public java.util.List<DLSyncEvent> findAll();

	/**
	 * Returns a range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of dl sync events
	 */
	public java.util.List<DLSyncEvent> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl sync events
	 */
	public java.util.List<DLSyncEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl sync events
	 */
	public java.util.List<DLSyncEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLSyncEvent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dl sync events from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dl sync events.
	 *
	 * @return the number of dl sync events
	 */
	public int countAll();

}