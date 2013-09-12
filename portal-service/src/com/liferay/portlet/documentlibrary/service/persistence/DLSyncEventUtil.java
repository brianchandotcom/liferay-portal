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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLSyncEvent;

import java.util.List;

/**
 * The persistence utility for the d l sync event service. This utility wraps {@link DLSyncEventPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventPersistence
 * @see DLSyncEventPersistenceImpl
 * @generated
 */
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
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLSyncEvent update(DLSyncEvent dlSyncEvent)
		throws SystemException {
		return getPersistence().update(dlSyncEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLSyncEvent update(DLSyncEvent dlSyncEvent,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(dlSyncEvent, serviceContext);
	}

	/**
	* Returns all the d l sync events where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @return the matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findByModifiedTime(
		long modifiedTime)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByModifiedTime(modifiedTime);
	}

	/**
	* Returns a range of all the d l sync events where modifiedTime &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedTime the modified time
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @return the range of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByModifiedTime(modifiedTime, start, end);
	}

	/**
	* Returns an ordered range of all the d l sync events where modifiedTime &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param modifiedTime the modified time
	* @param start the lower bound of the range of d l sync events
	* @param end the upper bound of the range of d l sync events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByModifiedTime(modifiedTime, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent findByModifiedTime_First(
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence()
				   .findByModifiedTime_First(modifiedTime, orderByComparator);
	}

	/**
	* Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByModifiedTime_First(
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByModifiedTime_First(modifiedTime, orderByComparator);
	}

	/**
	* Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent findByModifiedTime_Last(
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence()
				   .findByModifiedTime_Last(modifiedTime, orderByComparator);
	}

	/**
	* Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByModifiedTime_Last(
		long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent[] findByModifiedTime_PrevAndNext(
		long syncEventId, long modifiedTime,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence()
				   .findByModifiedTime_PrevAndNext(syncEventId, modifiedTime,
			orderByComparator);
	}

	/**
	* Removes all the d l sync events where modifiedTime &gt; &#63; from the database.
	*
	* @param modifiedTime the modified time
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByModifiedTime(long modifiedTime)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByModifiedTime(modifiedTime);
	}

	/**
	* Returns the number of d l sync events where modifiedTime &gt; &#63;.
	*
	* @param modifiedTime the modified time
	* @return the number of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByModifiedTime(long modifiedTime)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByModifiedTime(modifiedTime);
	}

	/**
	* Returns the d l sync event where typePK = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncEventException} if it could not be found.
	*
	* @param typePK the type p k
	* @return the matching d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent findByTypePK(
		long typePK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().findByTypePK(typePK);
	}

	/**
	* Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param typePK the type p k
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByTypePK(
		long typePK) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByTypePK(typePK);
	}

	/**
	* Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param typePK the type p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByTypePK(
		long typePK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByTypePK(typePK, retrieveFromCache);
	}

	/**
	* Removes the d l sync event where typePK = &#63; from the database.
	*
	* @param typePK the type p k
	* @return the d l sync event that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent removeByTypePK(
		long typePK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().removeByTypePK(typePK);
	}

	/**
	* Returns the number of d l sync events where typePK = &#63;.
	*
	* @param typePK the type p k
	* @return the number of matching d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTypePK(long typePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTypePK(typePK);
	}

	/**
	* Caches the d l sync event in the entity cache if it is enabled.
	*
	* @param dlSyncEvent the d l sync event
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLSyncEvent dlSyncEvent) {
		getPersistence().cacheResult(dlSyncEvent);
	}

	/**
	* Caches the d l sync events in the entity cache if it is enabled.
	*
	* @param dlSyncEvents the d l sync events
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> dlSyncEvents) {
		getPersistence().cacheResult(dlSyncEvents);
	}

	/**
	* Creates a new d l sync event with the primary key. Does not add the d l sync event to the database.
	*
	* @param syncEventId the primary key for the new d l sync event
	* @return the new d l sync event
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent create(
		long syncEventId) {
		return getPersistence().create(syncEventId);
	}

	/**
	* Removes the d l sync event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent remove(
		long syncEventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().remove(syncEventId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent updateImpl(
		com.liferay.portlet.documentlibrary.model.DLSyncEvent dlSyncEvent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlSyncEvent);
	}

	/**
	* Returns the d l sync event with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncEventException} if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event
	* @throws com.liferay.portlet.documentlibrary.NoSuchSyncEventException if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent findByPrimaryKey(
		long syncEventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchSyncEventException {
		return getPersistence().findByPrimaryKey(syncEventId);
	}

	/**
	* Returns the d l sync event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param syncEventId the primary key of the d l sync event
	* @return the d l sync event, or <code>null</code> if a d l sync event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSyncEvent fetchByPrimaryKey(
		long syncEventId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(syncEventId);
	}

	/**
	* Returns all the d l sync events.
	*
	* @return the d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSyncEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d l sync events from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d l sync events.
	*
	* @return the number of d l sync events
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLSyncEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLSyncEventPersistence)PortalBeanLocatorUtil.locate(DLSyncEventPersistence.class.getName());

			ReferenceRegistry.registerReference(DLSyncEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(DLSyncEventPersistence persistence) {
	}

	private static DLSyncEventPersistence _persistence;
}