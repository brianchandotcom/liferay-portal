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

package com.liferay.powwow.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import com.liferay.powwow.model.PowwowMeeting;

import java.util.List;

/**
 * The persistence utility for the powwow meeting service. This utility wraps {@link com.liferay.powwow.service.persistence.impl.PowwowMeetingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowMeetingPersistence
 * @see com.liferay.powwow.service.persistence.impl.PowwowMeetingPersistenceImpl
 * @generated
 */
@ProviderType
public class PowwowMeetingUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(PowwowMeeting powwowMeeting) {
		getPersistence().clearCache(powwowMeeting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PowwowMeeting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PowwowMeeting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PowwowMeeting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PowwowMeeting update(PowwowMeeting powwowMeeting) {
		return getPersistence().update(powwowMeeting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PowwowMeeting update(PowwowMeeting powwowMeeting,
		ServiceContext serviceContext) {
		return getPersistence().update(powwowMeeting, serviceContext);
	}

	/**
	* Returns all the powwow meetings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching powwow meetings
	*/
	public static List<PowwowMeeting> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the powwow meetings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByGroupId(long groupId, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow meetings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByGroupId(long groupId, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByGroupId_First(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByGroupId_First(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByGroupId_Last(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByGroupId_Last(long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting[] findByGroupId_PrevAndNext(
		long powwowMeetingId, long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(powwowMeetingId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching powwow meetings that the user has permission to view
	*/
	public static List<PowwowMeeting> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings that the user has permission to view
	*/
	public static List<PowwowMeeting> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings that the user has permission to view
	*/
	public static List<PowwowMeeting> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set of powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting[] filterFindByGroupId_PrevAndNext(
		long powwowMeetingId, long groupId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(powwowMeetingId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the powwow meetings where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of powwow meetings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching powwow meetings
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching powwow meetings that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the powwow meetings where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @return the matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPowwowServerId(long powwowServerId) {
		return getPersistence().findByPowwowServerId(powwowServerId);
	}

	/**
	* Returns a range of all the powwow meetings where powwowServerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPowwowServerId(
		long powwowServerId, int start, int end) {
		return getPersistence().findByPowwowServerId(powwowServerId, start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings where powwowServerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPowwowServerId(
		long powwowServerId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .findByPowwowServerId(powwowServerId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow meetings where powwowServerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPowwowServerId(
		long powwowServerId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPowwowServerId(powwowServerId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByPowwowServerId_First(
		long powwowServerId, OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByPowwowServerId_First(powwowServerId, orderByComparator);
	}

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByPowwowServerId_First(
		long powwowServerId, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .fetchByPowwowServerId_First(powwowServerId,
			orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByPowwowServerId_Last(long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByPowwowServerId_Last(powwowServerId, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByPowwowServerId_Last(
		long powwowServerId, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .fetchByPowwowServerId_Last(powwowServerId, orderByComparator);
	}

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting[] findByPowwowServerId_PrevAndNext(
		long powwowMeetingId, long powwowServerId,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByPowwowServerId_PrevAndNext(powwowMeetingId,
			powwowServerId, orderByComparator);
	}

	/**
	* Removes all the powwow meetings where powwowServerId = &#63; from the database.
	*
	* @param powwowServerId the powwow server ID
	*/
	public static void removeByPowwowServerId(long powwowServerId) {
		getPersistence().removeByPowwowServerId(powwowServerId);
	}

	/**
	* Returns the number of powwow meetings where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @return the number of matching powwow meetings
	*/
	public static int countByPowwowServerId(long powwowServerId) {
		return getPersistence().countByPowwowServerId(powwowServerId);
	}

	/**
	* Returns all the powwow meetings where status = &#63;.
	*
	* @param status the status
	* @return the matching powwow meetings
	*/
	public static List<PowwowMeeting> findByStatus(int status) {
		return getPersistence().findByStatus(status);
	}

	/**
	* Returns a range of all the powwow meetings where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByStatus(int status, int start,
		int end) {
		return getPersistence().findByStatus(status, start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByStatus(int status, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .findByStatus(status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow meetings where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByStatus(int status, int start,
		int end, OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByStatus(status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByStatus_First(int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().findByStatus_First(status, orderByComparator);
	}

	/**
	* Returns the first powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByStatus_First(int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence().fetchByStatus_First(status, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByStatus_Last(int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().findByStatus_Last(status, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByStatus_Last(int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence().fetchByStatus_Last(status, orderByComparator);
	}

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where status = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting[] findByStatus_PrevAndNext(
		long powwowMeetingId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByStatus_PrevAndNext(powwowMeetingId, status,
			orderByComparator);
	}

	/**
	* Removes all the powwow meetings where status = &#63; from the database.
	*
	* @param status the status
	*/
	public static void removeByStatus(int status) {
		getPersistence().removeByStatus(status);
	}

	/**
	* Returns the number of powwow meetings where status = &#63;.
	*
	* @param status the status
	* @return the number of matching powwow meetings
	*/
	public static int countByStatus(int status) {
		return getPersistence().countByStatus(status);
	}

	/**
	* Returns all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the matching powwow meetings
	*/
	public static List<PowwowMeeting> findByU_S(long userId, int status) {
		return getPersistence().findByU_S(userId, status);
	}

	/**
	* Returns a range of all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByU_S(long userId, int status,
		int start, int end) {
		return getPersistence().findByU_S(userId, status, start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByU_S(long userId, int status,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .findByU_S(userId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByU_S(long userId, int status,
		int start, int end, OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_S(userId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByU_S_First(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByU_S_First(userId, status, orderByComparator);
	}

	/**
	* Returns the first powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByU_S_First(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .fetchByU_S_First(userId, status, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByU_S_Last(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().findByU_S_Last(userId, status, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByU_S_Last(long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .fetchByU_S_Last(userId, status, orderByComparator);
	}

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting[] findByU_S_PrevAndNext(long powwowMeetingId,
		long userId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByU_S_PrevAndNext(powwowMeetingId, userId, status,
			orderByComparator);
	}

	/**
	* Removes all the powwow meetings where userId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByU_S(long userId, int status) {
		getPersistence().removeByU_S(userId, status);
	}

	/**
	* Returns the number of powwow meetings where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the number of matching powwow meetings
	*/
	public static int countByU_S(long userId, int status) {
		return getPersistence().countByU_S(userId, status);
	}

	/**
	* Returns all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @return the matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPSI_S(long powwowServerId,
		int status) {
		return getPersistence().findByPSI_S(powwowServerId, status);
	}

	/**
	* Returns a range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPSI_S(long powwowServerId,
		int status, int start, int end) {
		return getPersistence().findByPSI_S(powwowServerId, status, start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPSI_S(long powwowServerId,
		int status, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .findByPSI_S(powwowServerId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow meetings
	*/
	public static List<PowwowMeeting> findByPSI_S(long powwowServerId,
		int status, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPSI_S(powwowServerId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByPSI_S_First(long powwowServerId,
		int status, OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByPSI_S_First(powwowServerId, status, orderByComparator);
	}

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByPSI_S_First(long powwowServerId,
		int status, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .fetchByPSI_S_First(powwowServerId, status, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws NoSuchMeetingException if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting findByPSI_S_Last(long powwowServerId,
		int status, OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByPSI_S_Last(powwowServerId, status, orderByComparator);
	}

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	*/
	public static PowwowMeeting fetchByPSI_S_Last(long powwowServerId,
		int status, OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence()
				   .fetchByPSI_S_Last(powwowServerId, status, orderByComparator);
	}

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting[] findByPSI_S_PrevAndNext(
		long powwowMeetingId, long powwowServerId, int status,
		OrderByComparator<PowwowMeeting> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence()
				   .findByPSI_S_PrevAndNext(powwowMeetingId, powwowServerId,
			status, orderByComparator);
	}

	/**
	* Removes all the powwow meetings where powwowServerId = &#63; and status = &#63; from the database.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	*/
	public static void removeByPSI_S(long powwowServerId, int status) {
		getPersistence().removeByPSI_S(powwowServerId, status);
	}

	/**
	* Returns the number of powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @return the number of matching powwow meetings
	*/
	public static int countByPSI_S(long powwowServerId, int status) {
		return getPersistence().countByPSI_S(powwowServerId, status);
	}

	/**
	* Caches the powwow meeting in the entity cache if it is enabled.
	*
	* @param powwowMeeting the powwow meeting
	*/
	public static void cacheResult(PowwowMeeting powwowMeeting) {
		getPersistence().cacheResult(powwowMeeting);
	}

	/**
	* Caches the powwow meetings in the entity cache if it is enabled.
	*
	* @param powwowMeetings the powwow meetings
	*/
	public static void cacheResult(List<PowwowMeeting> powwowMeetings) {
		getPersistence().cacheResult(powwowMeetings);
	}

	/**
	* Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	*
	* @param powwowMeetingId the primary key for the new powwow meeting
	* @return the new powwow meeting
	*/
	public static PowwowMeeting create(long powwowMeetingId) {
		return getPersistence().create(powwowMeetingId);
	}

	/**
	* Removes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting that was removed
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting remove(long powwowMeetingId)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().remove(powwowMeetingId);
	}

	public static PowwowMeeting updateImpl(PowwowMeeting powwowMeeting) {
		return getPersistence().updateImpl(powwowMeeting);
	}

	/**
	* Returns the powwow meeting with the primary key or throws a {@link NoSuchMeetingException} if it could not be found.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting
	* @throws NoSuchMeetingException if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting findByPrimaryKey(long powwowMeetingId)
		throws com.liferay.powwow.exception.NoSuchMeetingException {
		return getPersistence().findByPrimaryKey(powwowMeetingId);
	}

	/**
	* Returns the powwow meeting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting, or <code>null</code> if a powwow meeting with the primary key could not be found
	*/
	public static PowwowMeeting fetchByPrimaryKey(long powwowMeetingId) {
		return getPersistence().fetchByPrimaryKey(powwowMeetingId);
	}

	public static java.util.Map<java.io.Serializable, PowwowMeeting> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the powwow meetings.
	*
	* @return the powwow meetings
	*/
	public static List<PowwowMeeting> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the powwow meetings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of powwow meetings
	*/
	public static List<PowwowMeeting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the powwow meetings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of powwow meetings
	*/
	public static List<PowwowMeeting> findAll(int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow meetings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of powwow meetings
	*/
	public static List<PowwowMeeting> findAll(int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the powwow meetings from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of powwow meetings.
	*
	* @return the number of powwow meetings
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PowwowMeetingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PowwowMeetingPersistence)PortletBeanLocatorUtil.locate(com.liferay.powwow.service.ClpSerializer.getServletContextName(),
					PowwowMeetingPersistence.class.getName());

			ReferenceRegistry.registerReference(PowwowMeetingUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static PowwowMeetingPersistence _persistence;
}