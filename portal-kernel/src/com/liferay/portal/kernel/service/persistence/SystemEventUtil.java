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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the system event service. This utility wraps <code>com.liferay.portal.service.persistence.impl.SystemEventPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SystemEventPersistence
 * @generated
 */
@ProviderType
public class SystemEventUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(SystemEvent systemEvent) {
		getPersistence().clearCache(systemEvent);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, SystemEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SystemEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SystemEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SystemEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SystemEvent update(SystemEvent systemEvent) {
		return getPersistence().update(systemEvent);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SystemEvent update(SystemEvent systemEvent,
		ServiceContext serviceContext) {
		return getPersistence().update(systemEvent, serviceContext);
	}

	/**
	* Returns all the system events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching system events
	*/
	public static List<SystemEvent> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the system events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public static List<SystemEvent> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByGroupId_First(long groupId,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByGroupId_First(long groupId,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByGroupId_Last(long groupId,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByGroupId_Last(long groupId,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public static SystemEvent[] findByGroupId_PrevAndNext(long systemEventId,
		long groupId, OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(systemEventId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the system events where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of system events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching system events
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @return the matching system events
	*/
	public static List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey) {
		return getPersistence().findByG_S(groupId, systemEventSetKey);
	}

	/**
	* Returns a range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public static List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey, int start, int end) {
		return getPersistence().findByG_S(groupId, systemEventSetKey, start, end);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .findByG_S(groupId, systemEventSetKey, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_S(groupId, systemEventSetKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByG_S_First(long groupId,
		long systemEventSetKey, OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_S_First(groupId, systemEventSetKey,
			orderByComparator);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByG_S_First(long groupId,
		long systemEventSetKey, OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_First(groupId, systemEventSetKey,
			orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByG_S_Last(long groupId,
		long systemEventSetKey, OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_S_Last(groupId, systemEventSetKey, orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByG_S_Last(long groupId,
		long systemEventSetKey, OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_Last(groupId, systemEventSetKey,
			orderByComparator);
	}

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public static SystemEvent[] findByG_S_PrevAndNext(long systemEventId,
		long groupId, long systemEventSetKey,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_S_PrevAndNext(systemEventId, groupId,
			systemEventSetKey, orderByComparator);
	}

	/**
	* Removes all the system events where groupId = &#63; and systemEventSetKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	*/
	public static void removeByG_S(long groupId, long systemEventSetKey) {
		getPersistence().removeByG_S(groupId, systemEventSetKey);
	}

	/**
	* Returns the number of system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @return the number of matching system events
	*/
	public static int countByG_S(long groupId, long systemEventSetKey) {
		return getPersistence().countByG_S(groupId, systemEventSetKey);
	}

	/**
	* Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching system events
	*/
	public static List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK) {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public static List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public static SystemEvent[] findByG_C_C_PrevAndNext(long systemEventId,
		long groupId, long classNameId, long classPK,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(systemEventId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByG_C_C(long groupId, long classNameId,
		long classPK) {
		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching system events
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @return the matching system events
	*/
	public static List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public static List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type, int start, int end) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type, start,
			end);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public static List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByG_C_C_T_First(long groupId,
		long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_C_C_T_First(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByG_C_C_T_First(long groupId,
		long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_T_First(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public static SystemEvent findByG_C_C_T_Last(long groupId,
		long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_C_C_T_Last(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public static SystemEvent fetchByG_C_C_T_Last(long groupId,
		long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_T_Last(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public static SystemEvent[] findByG_C_C_T_PrevAndNext(long systemEventId,
		long groupId, long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence()
				   .findByG_C_C_T_PrevAndNext(systemEventId, groupId,
			classNameId, classPK, type, orderByComparator);
	}

	/**
	* Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	*/
	public static void removeByG_C_C_T(long groupId, long classNameId,
		long classPK, int type) {
		getPersistence().removeByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param type the type
	* @return the number of matching system events
	*/
	public static int countByG_C_C_T(long groupId, long classNameId,
		long classPK, int type) {
		return getPersistence()
				   .countByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Caches the system event in the entity cache if it is enabled.
	*
	* @param systemEvent the system event
	*/
	public static void cacheResult(SystemEvent systemEvent) {
		getPersistence().cacheResult(systemEvent);
	}

	/**
	* Caches the system events in the entity cache if it is enabled.
	*
	* @param systemEvents the system events
	*/
	public static void cacheResult(List<SystemEvent> systemEvents) {
		getPersistence().cacheResult(systemEvents);
	}

	/**
	* Creates a new system event with the primary key. Does not add the system event to the database.
	*
	* @param systemEventId the primary key for the new system event
	* @return the new system event
	*/
	public static SystemEvent create(long systemEventId) {
		return getPersistence().create(systemEventId);
	}

	/**
	* Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param systemEventId the primary key of the system event
	* @return the system event that was removed
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public static SystemEvent remove(long systemEventId)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence().remove(systemEventId);
	}

	public static SystemEvent updateImpl(SystemEvent systemEvent) {
		return getPersistence().updateImpl(systemEvent);
	}

	/**
	* Returns the system event with the primary key or throws a <code>NoSuchSystemEventException</code> if it could not be found.
	*
	* @param systemEventId the primary key of the system event
	* @return the system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public static SystemEvent findByPrimaryKey(long systemEventId)
		throws com.liferay.portal.kernel.exception.NoSuchSystemEventException {
		return getPersistence().findByPrimaryKey(systemEventId);
	}

	/**
	* Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param systemEventId the primary key of the system event
	* @return the system event, or <code>null</code> if a system event with the primary key could not be found
	*/
	public static SystemEvent fetchByPrimaryKey(long systemEventId) {
		return getPersistence().fetchByPrimaryKey(systemEventId);
	}

	/**
	* Returns all the system events.
	*
	* @return the system events
	*/
	public static List<SystemEvent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the system events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of system events
	*/
	public static List<SystemEvent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the system events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of system events
	*/
	public static List<SystemEvent> findAll(int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the system events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of system events
	*/
	public static List<SystemEvent> findAll(int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the system events from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of system events.
	*
	* @return the number of system events
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SystemEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SystemEventPersistence)PortalBeanLocatorUtil.locate(SystemEventPersistence.class.getName());

			ReferenceRegistry.registerReference(SystemEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SystemEventPersistence _persistence;
}