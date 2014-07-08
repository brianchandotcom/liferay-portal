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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the membership request service. This utility wraps {@link MembershipRequestPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestPersistence
 * @see MembershipRequestPersistenceImpl
 * @generated
 */
@ProviderType
public class MembershipRequestUtil {
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
	public static void clearCache(MembershipRequest membershipRequest) {
		getPersistence().clearCache(membershipRequest);
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
	public static List<MembershipRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MembershipRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MembershipRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MembershipRequest update(MembershipRequest membershipRequest) {
		return getPersistence().update(membershipRequest);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MembershipRequest update(
		MembershipRequest membershipRequest, ServiceContext serviceContext) {
		return getPersistence().update(membershipRequest, serviceContext);
	}

	/**
	* Returns all the membership requests where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the membership requests where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @return the range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the membership requests where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the membership requests before and after the current membership request in the ordered set where groupId = &#63;.
	*
	* @param membershipRequestId the primary key of the current membership request
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(membershipRequestId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the membership requests where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of membership requests where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching membership requests
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the membership requests where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the membership requests where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @return the range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the membership requests where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the membership requests before and after the current membership request in the ordered set where userId = &#63;.
	*
	* @param membershipRequestId the primary key of the current membership request
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByUserId_PrevAndNext(membershipRequestId, userId,
			orderByComparator);
	}

	/**
	* Removes all the membership requests where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of membership requests where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching membership requests
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the membership requests where groupId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @return the matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId) {
		return getPersistence().findByG_S(groupId, statusId);
	}

	/**
	* Returns a range of all the membership requests where groupId = &#63; and statusId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @return the range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end) {
		return getPersistence().findByG_S(groupId, statusId, start, end);
	}

	/**
	* Returns an ordered range of all the membership requests where groupId = &#63; and statusId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByG_S(groupId, statusId, start, end, orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByG_S_First(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByG_S_First(groupId, statusId, orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByG_S_First(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByG_S_First(groupId, statusId, orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByG_S_Last(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByG_S_Last(groupId, statusId, orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByG_S_Last(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByG_S_Last(groupId, statusId, orderByComparator);
	}

	/**
	* Returns the membership requests before and after the current membership request in the ordered set where groupId = &#63; and statusId = &#63;.
	*
	* @param membershipRequestId the primary key of the current membership request
	* @param groupId the group ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest[] findByG_S_PrevAndNext(
		long membershipRequestId, long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByG_S_PrevAndNext(membershipRequestId, groupId,
			statusId, orderByComparator);
	}

	/**
	* Removes all the membership requests where groupId = &#63; and statusId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	*/
	public static void removeByG_S(long groupId, int statusId) {
		getPersistence().removeByG_S(groupId, statusId);
	}

	/**
	* Returns the number of membership requests where groupId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param statusId the status ID
	* @return the number of matching membership requests
	*/
	public static int countByG_S(long groupId, int statusId) {
		return getPersistence().countByG_S(groupId, statusId);
	}

	/**
	* Returns all the membership requests where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @return the matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_U_S(
		long groupId, long userId, int statusId) {
		return getPersistence().findByG_U_S(groupId, userId, statusId);
	}

	/**
	* Returns a range of all the membership requests where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @return the range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_U_S(
		long groupId, long userId, int statusId, int start, int end) {
		return getPersistence()
				   .findByG_U_S(groupId, userId, statusId, start, end);
	}

	/**
	* Returns an ordered range of all the membership requests where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_U_S(
		long groupId, long userId, int statusId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByG_U_S(groupId, userId, statusId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByG_U_S_First(
		long groupId, long userId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByG_U_S_First(groupId, userId, statusId,
			orderByComparator);
	}

	/**
	* Returns the first membership request in the ordered set where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByG_U_S_First(
		long groupId, long userId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByG_U_S_First(groupId, userId, statusId,
			orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByG_U_S_Last(
		long groupId, long userId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByG_U_S_Last(groupId, userId, statusId,
			orderByComparator);
	}

	/**
	* Returns the last membership request in the ordered set where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching membership request, or <code>null</code> if a matching membership request could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByG_U_S_Last(
		long groupId, long userId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByG_U_S_Last(groupId, userId, statusId,
			orderByComparator);
	}

	/**
	* Returns the membership requests before and after the current membership request in the ordered set where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param membershipRequestId the primary key of the current membership request
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest[] findByG_U_S_PrevAndNext(
		long membershipRequestId, long groupId, long userId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence()
				   .findByG_U_S_PrevAndNext(membershipRequestId, groupId,
			userId, statusId, orderByComparator);
	}

	/**
	* Removes all the membership requests where groupId = &#63; and userId = &#63; and statusId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	*/
	public static void removeByG_U_S(long groupId, long userId, int statusId) {
		getPersistence().removeByG_U_S(groupId, userId, statusId);
	}

	/**
	* Returns the number of membership requests where groupId = &#63; and userId = &#63; and statusId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statusId the status ID
	* @return the number of matching membership requests
	*/
	public static int countByG_U_S(long groupId, long userId, int statusId) {
		return getPersistence().countByG_U_S(groupId, userId, statusId);
	}

	/**
	* Caches the membership request in the entity cache if it is enabled.
	*
	* @param membershipRequest the membership request
	*/
	public static void cacheResult(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		getPersistence().cacheResult(membershipRequest);
	}

	/**
	* Caches the membership requests in the entity cache if it is enabled.
	*
	* @param membershipRequests the membership requests
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.MembershipRequest> membershipRequests) {
		getPersistence().cacheResult(membershipRequests);
	}

	/**
	* Creates a new membership request with the primary key. Does not add the membership request to the database.
	*
	* @param membershipRequestId the primary key for the new membership request
	* @return the new membership request
	*/
	public static com.liferay.portal.model.MembershipRequest create(
		long membershipRequestId) {
		return getPersistence().create(membershipRequestId);
	}

	/**
	* Removes the membership request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request that was removed
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest remove(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().remove(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return getPersistence().updateImpl(membershipRequest);
	}

	/**
	* Returns the membership request with the primary key or throws a {@link com.liferay.portal.NoSuchMembershipRequestException} if it could not be found.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request
	* @throws com.liferay.portal.NoSuchMembershipRequestException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest findByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByPrimaryKey(membershipRequestId);
	}

	/**
	* Returns the membership request with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request, or <code>null</code> if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest fetchByPrimaryKey(
		long membershipRequestId) {
		return getPersistence().fetchByPrimaryKey(membershipRequestId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.MembershipRequest> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the membership requests.
	*
	* @return the membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the membership requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @return the range of membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the membership requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the membership requests from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of membership requests.
	*
	* @return the number of membership requests
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MembershipRequestPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MembershipRequestPersistence)PortalBeanLocatorUtil.locate(MembershipRequestPersistence.class.getName());

			ReferenceRegistry.registerReference(MembershipRequestUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(MembershipRequestPersistence persistence) {
	}

	private static MembershipRequestPersistence _persistence;
}