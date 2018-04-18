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

package com.liferay.changeset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.model.Changeset;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the changeset service. This utility wraps {@link com.liferay.changeset.service.persistence.impl.ChangesetPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetPersistence
 * @see com.liferay.changeset.service.persistence.impl.ChangesetPersistenceImpl
 * @generated
 */
@ProviderType
public class ChangesetUtil {
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
	public static void clearCache(Changeset changeset) {
		getPersistence().clearCache(changeset);
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
	public static List<Changeset> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Changeset> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Changeset> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Changeset update(Changeset changeset) {
		return getPersistence().update(changeset);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Changeset update(Changeset changeset,
		ServiceContext serviceContext) {
		return getPersistence().update(changeset, serviceContext);
	}

	/**
	* Returns all the changesets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching changesets
	*/
	public static List<Changeset> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the changesets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public static List<Changeset> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the changesets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByGroupId(long groupId, int start,
		int end, OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the changesets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByGroupId(long groupId, int start,
		int end, OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByGroupId_First(long groupId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByGroupId_First(long groupId,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByGroupId_Last(long groupId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByGroupId_Last(long groupId,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the changesets before and after the current changeset in the ordered set where groupId = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public static Changeset[] findByGroupId_PrevAndNext(long changesetId,
		long groupId, OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(changesetId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the changesets where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of changesets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching changesets
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the changesets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching changesets
	*/
	public static List<Changeset> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the changesets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public static List<Changeset> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the changesets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the changesets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByCompanyId_First(long companyId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByCompanyId_First(long companyId,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByCompanyId_Last(long companyId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByCompanyId_Last(long companyId,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the changesets before and after the current changeset in the ordered set where companyId = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public static Changeset[] findByCompanyId_PrevAndNext(long changesetId,
		long companyId, OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(changesetId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the changesets where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of changesets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching changesets
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the changesets where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching changesets
	*/
	public static List<Changeset> findByG_U(long groupId, long userId) {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the changesets where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public static List<Changeset> findByG_U(long groupId, long userId,
		int start, int end) {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the changesets where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the changesets where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByG_U_First(long groupId, long userId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the first changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByG_U_First(long groupId, long userId,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByG_U_Last(long groupId, long userId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByG_U_Last(long groupId, long userId,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the changesets before and after the current changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public static Changeset[] findByG_U_PrevAndNext(long changesetId,
		long groupId, long userId,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByG_U_PrevAndNext(changesetId, groupId, userId,
			orderByComparator);
	}

	/**
	* Removes all the changesets where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public static void removeByG_U(long groupId, long userId) {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Returns the number of changesets where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching changesets
	*/
	public static int countByG_U(long groupId, long userId) {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Returns the changeset where groupId = &#63; and name = &#63; or throws a {@link NoSuchChangesetException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByG_N(long groupId, java.lang.String name)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the changeset where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByG_N(long groupId, java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the changeset where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the changeset where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the changeset that was removed
	*/
	public static Changeset removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of changesets where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching changesets
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the changesets where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching changesets
	*/
	public static List<Changeset> findByC_N(long companyId,
		java.lang.String name) {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns a range of all the changesets where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public static List<Changeset> findByC_N(long companyId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByC_N(companyId, name, start, end);
	}

	/**
	* Returns an ordered range of all the changesets where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByC_N(long companyId,
		java.lang.String name, int start, int end,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .findByC_N(companyId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the changesets where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public static List<Changeset> findByC_N(long companyId,
		java.lang.String name, int start, int end,
		OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_N(companyId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByC_N_First(long companyId,
		java.lang.String name, OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByC_N_First(companyId, name, orderByComparator);
	}

	/**
	* Returns the first changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByC_N_First(long companyId,
		java.lang.String name, OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .fetchByC_N_First(companyId, name, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public static Changeset findByC_N_Last(long companyId,
		java.lang.String name, OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByC_N_Last(companyId, name, orderByComparator);
	}

	/**
	* Returns the last changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public static Changeset fetchByC_N_Last(long companyId,
		java.lang.String name, OrderByComparator<Changeset> orderByComparator) {
		return getPersistence()
				   .fetchByC_N_Last(companyId, name, orderByComparator);
	}

	/**
	* Returns the changesets before and after the current changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public static Changeset[] findByC_N_PrevAndNext(long changesetId,
		long companyId, java.lang.String name,
		OrderByComparator<Changeset> orderByComparator)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence()
				   .findByC_N_PrevAndNext(changesetId, companyId, name,
			orderByComparator);
	}

	/**
	* Removes all the changesets where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	*/
	public static void removeByC_N(long companyId, java.lang.String name) {
		getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of changesets where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching changesets
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Caches the changeset in the entity cache if it is enabled.
	*
	* @param changeset the changeset
	*/
	public static void cacheResult(Changeset changeset) {
		getPersistence().cacheResult(changeset);
	}

	/**
	* Caches the changesets in the entity cache if it is enabled.
	*
	* @param changesets the changesets
	*/
	public static void cacheResult(List<Changeset> changesets) {
		getPersistence().cacheResult(changesets);
	}

	/**
	* Creates a new changeset with the primary key. Does not add the changeset to the database.
	*
	* @param changesetId the primary key for the new changeset
	* @return the new changeset
	*/
	public static Changeset create(long changesetId) {
		return getPersistence().create(changesetId);
	}

	/**
	* Removes the changeset with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset that was removed
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public static Changeset remove(long changesetId)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence().remove(changesetId);
	}

	public static Changeset updateImpl(Changeset changeset) {
		return getPersistence().updateImpl(changeset);
	}

	/**
	* Returns the changeset with the primary key or throws a {@link NoSuchChangesetException} if it could not be found.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public static Changeset findByPrimaryKey(long changesetId)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return getPersistence().findByPrimaryKey(changesetId);
	}

	/**
	* Returns the changeset with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset, or <code>null</code> if a changeset with the primary key could not be found
	*/
	public static Changeset fetchByPrimaryKey(long changesetId) {
		return getPersistence().fetchByPrimaryKey(changesetId);
	}

	public static java.util.Map<java.io.Serializable, Changeset> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the changesets.
	*
	* @return the changesets
	*/
	public static List<Changeset> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of changesets
	*/
	public static List<Changeset> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of changesets
	*/
	public static List<Changeset> findAll(int start, int end,
		OrderByComparator<Changeset> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of changesets
	*/
	public static List<Changeset> findAll(int start, int end,
		OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the changesets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of changesets.
	*
	* @return the number of changesets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ChangesetPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangesetPersistence, ChangesetPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangesetPersistence.class);

		ServiceTracker<ChangesetPersistence, ChangesetPersistence> serviceTracker =
			new ServiceTracker<ChangesetPersistence, ChangesetPersistence>(bundle.getBundleContext(),
				ChangesetPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}