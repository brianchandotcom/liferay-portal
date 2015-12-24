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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLSyncEvent;

import java.util.List;

/**
 * The persistence utility for the d l sync event service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.persistence.impl.DLSyncEventPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLSyncEventPersistenceImpl
 * @generated
 */
@ProviderType
public class DLSyncEventUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(DLSyncEvent dlSyncEvent) {
		getPersistence().clearCache(dlSyncEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLSyncEvent update(DLSyncEvent dlSyncEvent) {
		return getPersistence().update(dlSyncEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLSyncEvent update(DLSyncEvent dlSyncEvent,
		ServiceContext serviceContext) {
		return getPersistence().update(dlSyncEvent, serviceContext);
	}

	/**
	* Returns all the d l sync events where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @return the matching d l sync events
	*/
	public static List<DLSyncEvent> findByModifiedTime(long modifiedTime) {
		return getPersistence().findByModifiedTime(modifiedTime);
	}

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
	public static List<DLSyncEvent> findByModifiedTime(long modifiedTime,
		int start, int end) {
		return getPersistence().findByModifiedTime(modifiedTime, start, end);
	}

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
	public static List<DLSyncEvent> findByModifiedTime(long modifiedTime,
		int start, int end, OrderByComparator<DLSyncEvent> orderByComparator) {
		return getPersistence()
				   .findByModifiedTime(modifiedTime, start, end,
			orderByComparator);
	}

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
	public static List<DLSyncEvent> findByModifiedTime(long modifiedTime,
		int start, int end, OrderByComparator<DLSyncEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByModifiedTime(modifiedTime, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event
	* @throws NoSuchSyncEventException if a matching d l sync event could not be found
	*/
	public static DLSyncEvent findByModifiedTime_First(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence()
				   .findByModifiedTime_First(modifiedTime, orderByComparator);
	}

	/**
	* Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public static DLSyncEvent fetchByModifiedTime_First(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		return getPersistence()
				   .fetchByModifiedTime_First(modifiedTime, orderByComparator);
	}

	/**
	* Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event
	* @throws NoSuchSyncEventException if a matching d l sync event could not be found
	*/
	public static DLSyncEvent findByModifiedTime_Last(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence()
				   .findByModifiedTime_Last(modifiedTime, orderByComparator);
	}

	/**
	* Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public static DLSyncEvent fetchByModifiedTime_Last(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		return getPersistence()
				   .fetchByModifiedTime_Last(modifiedTime, orderByComparator);
	}

	/**
	* Returns the d l sync events before and after the current d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param syncEventId the primary key of the current d l sync event
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l sync event
	* @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	*/
	public static DLSyncEvent[] findByModifiedTime_PrevAndNext(
		long syncEventId, long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence()
				   .findByModifiedTime_PrevAndNext(syncEventId, modifiedTime,
			orderByComparator);
	}

	/**
	* Removes all the d l sync events where modifiedTime &gt; &#63; from the database.
	*
	* @param modifiedTime the modified time
	*/
	public static void removeByModifiedTime(long modifiedTime) {
		getPersistence().removeByModifiedTime(modifiedTime);
	}

	/**
	* Returns the number of d l sync events where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @return the number of matching d l sync events
	*/
	public static int countByModifiedTime(long modifiedTime) {
		return getPersistence().countByModifiedTime(modifiedTime);
	}

	/**
	* Returns the d l sync event where typePK = &#63; or throws a {@link NoSuchSyncEventException} if it could not be found.
	*
	* @param typePK the type p k
	* @return the matching d l sync event
	* @throws NoSuchSyncEventException if a matching d l sync event could not be found
	*/
	public static DLSyncEvent findByTypePK(long typePK)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().findByTypePK(typePK);
	}

	/**
	* Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param typePK the type p k
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public static DLSyncEvent fetchByTypePK(long typePK) {
		return getPersistence().fetchByTypePK(typePK);
	}

	/**
	* Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param typePK the type p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	*/
	public static DLSyncEvent fetchByTypePK(long typePK,
		boolean retrieveFromCache) {
		return getPersistence().fetchByTypePK(typePK, retrieveFromCache);
	}

	/**
	* Removes the d l sync event where typePK = &#63; from the database.
	*
	* @param typePK the type p k
	* @return the d l sync event that was removed
	*/
	public static DLSyncEvent removeByTypePK(long typePK)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().removeByTypePK(typePK);
	}

	/**
	* Returns the number of d l sync events where typePK = &#63;.
	*
	* @param typePK the type p k
	* @return the number of matching d l sync events
	*/
	public static int countByTypePK(long typePK) {
		return getPersistence().countByTypePK(typePK);
	}

	/**
	* Caches the d l sync event in the entity cache if it is enabled.
	*
	* @param dlSyncEvent the d l sync event
	*/
	public static void cacheResult(DLSyncEvent dlSyncEvent) {
		getPersistence().cacheResult(dlSyncEvent);
	}

	/**
	* Caches the d l sync events in the entity cache if it is enabled.
	*
	* @param dlSyncEvents the d l sync events
	*/
	public static void cacheResult(List<DLSyncEvent> dlSyncEvents) {
		getPersistence().cacheResult(dlSyncEvents);
	}

	/**
	* Creates a new d l sync event with the primary key. Does not add the d l sync event to the database.
	*
	* @param syncEventId the primary key for the new d l sync event
	* @return the new d l sync event
	*/
	public static DLSyncEvent create(long syncEventId) {
		return getPersistence().create(syncEventId);
	}

	/**
	* Removes the d l sync event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event that was removed
	* @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	*/
	public static DLSyncEvent remove(long syncEventId)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().remove(syncEventId);
	}

	public static DLSyncEvent updateImpl(DLSyncEvent dlSyncEvent) {
		return getPersistence().updateImpl(dlSyncEvent);
	}

	/**
	* Returns the d l sync event with the primary key or throws a {@link NoSuchSyncEventException} if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event
	* @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	*/
	public static DLSyncEvent findByPrimaryKey(long syncEventId)
		throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().findByPrimaryKey(syncEventId);
	}

	/**
	* Returns the d l sync event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event, or <code>null</code> if a d l sync event with the primary key could not be found
	*/
	public static DLSyncEvent fetchByPrimaryKey(long syncEventId) {
		return getPersistence().fetchByPrimaryKey(syncEventId);
	}

	public static java.util.Map<java.io.Serializable, DLSyncEvent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d l sync events.
	*
	* @return the d l sync events
	*/
	public static List<DLSyncEvent> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<DLSyncEvent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<DLSyncEvent> findAll(int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<DLSyncEvent> findAll(int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d l sync events from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d l sync events.
	*
	* @return the number of d l sync events
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DLSyncEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLSyncEventPersistence)PortalBeanLocatorUtil.locate(DLSyncEventPersistence.class.getName());

			ReferenceRegistry.registerReference(DLSyncEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static DLSyncEventPersistence _persistence;
}