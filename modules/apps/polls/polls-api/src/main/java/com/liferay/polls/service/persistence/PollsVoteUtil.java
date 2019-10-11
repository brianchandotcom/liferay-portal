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

package com.liferay.polls.service.persistence;

import com.liferay.polls.model.PollsVote;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the polls vote service. This utility wraps <code>com.liferay.polls.service.persistence.impl.PollsVotePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsVotePersistence
 * @generated
 */
public class PollsVoteUtil {

	/**
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
	public static void clearCache(PollsVote pollsVote) {
		getPersistence().clearCache(pollsVote);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, PollsVote> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PollsVote> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PollsVote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PollsVote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PollsVote update(PollsVote pollsVote) {
		return getPersistence().update(pollsVote);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PollsVote update(
		PollsVote pollsVote, ServiceContext serviceContext) {

		return getPersistence().update(pollsVote, serviceContext);
	}

	/**
	 * Returns all the polls votes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching polls votes
	 */
	public static List<PollsVote> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the polls votes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	public static List<PollsVote> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByUuid_First(
			String uuid, OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByUuid_First(
		String uuid, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByUuid_Last(
			String uuid, OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByUuid_Last(
		String uuid, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote[] findByUuid_PrevAndNext(
			long voteId, String uuid,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUuid_PrevAndNext(
			voteId, uuid, orderByComparator);
	}

	/**
	 * Removes all the polls votes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of polls votes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching polls votes
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the polls vote where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVoteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByUUID_G(String uuid, long groupId)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the polls vote where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the polls vote where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the polls vote where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the polls vote that was removed
	 */
	public static PollsVote removeByUUID_G(String uuid, long groupId)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of polls votes where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching polls votes
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching polls votes
	 */
	public static List<PollsVote> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	public static List<PollsVote> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote[] findByUuid_C_PrevAndNext(
			long voteId, String uuid, long companyId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByUuid_C_PrevAndNext(
			voteId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the polls votes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching polls votes
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the polls votes where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching polls votes
	 */
	public static List<PollsVote> findByQuestionId(long questionId) {
		return getPersistence().findByQuestionId(questionId);
	}

	/**
	 * Returns a range of all the polls votes where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	public static List<PollsVote> findByQuestionId(
		long questionId, int start, int end) {

		return getPersistence().findByQuestionId(questionId, start, end);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByQuestionId(
		long questionId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findByQuestionId(
			questionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByQuestionId(
		long questionId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByQuestionId(
			questionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByQuestionId_First(
			long questionId, OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByQuestionId_First(
			questionId, orderByComparator);
	}

	/**
	 * Returns the first polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByQuestionId_First(
		long questionId, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByQuestionId_First(
			questionId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByQuestionId_Last(
			long questionId, OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByQuestionId_Last(
			questionId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByQuestionId_Last(
		long questionId, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByQuestionId_Last(
			questionId, orderByComparator);
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote[] findByQuestionId_PrevAndNext(
			long voteId, long questionId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByQuestionId_PrevAndNext(
			voteId, questionId, orderByComparator);
	}

	/**
	 * Removes all the polls votes where questionId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 */
	public static void removeByQuestionId(long questionId) {
		getPersistence().removeByQuestionId(questionId);
	}

	/**
	 * Returns the number of polls votes where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the number of matching polls votes
	 */
	public static int countByQuestionId(long questionId) {
		return getPersistence().countByQuestionId(questionId);
	}

	/**
	 * Returns all the polls votes where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @return the matching polls votes
	 */
	public static List<PollsVote> findByChoiceId(long choiceId) {
		return getPersistence().findByChoiceId(choiceId);
	}

	/**
	 * Returns a range of all the polls votes where choiceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param choiceId the choice ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	public static List<PollsVote> findByChoiceId(
		long choiceId, int start, int end) {

		return getPersistence().findByChoiceId(choiceId, start, end);
	}

	/**
	 * Returns an ordered range of all the polls votes where choiceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param choiceId the choice ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findByChoiceId(
			choiceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the polls votes where choiceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param choiceId the choice ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByChoiceId(
			choiceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByChoiceId_First(
			long choiceId, OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByChoiceId_First(
			choiceId, orderByComparator);
	}

	/**
	 * Returns the first polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByChoiceId_First(
		long choiceId, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByChoiceId_First(
			choiceId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByChoiceId_Last(
			long choiceId, OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByChoiceId_Last(
			choiceId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByChoiceId_Last(
		long choiceId, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByChoiceId_Last(
			choiceId, orderByComparator);
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote[] findByChoiceId_PrevAndNext(
			long voteId, long choiceId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByChoiceId_PrevAndNext(
			voteId, choiceId, orderByComparator);
	}

	/**
	 * Removes all the polls votes where choiceId = &#63; from the database.
	 *
	 * @param choiceId the choice ID
	 */
	public static void removeByChoiceId(long choiceId) {
		getPersistence().removeByChoiceId(choiceId);
	}

	/**
	 * Returns the number of polls votes where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @return the number of matching polls votes
	 */
	public static int countByChoiceId(long choiceId) {
		return getPersistence().countByChoiceId(choiceId);
	}

	/**
	 * Returns all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @return the matching polls votes
	 */
	public static List<PollsVote> findByQ_U(long questionId, long userId) {
		return getPersistence().findByQ_U(questionId, userId);
	}

	/**
	 * Returns a range of all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	public static List<PollsVote> findByQ_U(
		long questionId, long userId, int start, int end) {

		return getPersistence().findByQ_U(questionId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByQ_U(
		long questionId, long userId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findByQ_U(
			questionId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	public static List<PollsVote> findByQ_U(
		long questionId, long userId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByQ_U(
			questionId, userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByQ_U_First(
			long questionId, long userId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByQ_U_First(
			questionId, userId, orderByComparator);
	}

	/**
	 * Returns the first polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByQ_U_First(
		long questionId, long userId,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByQ_U_First(
			questionId, userId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	public static PollsVote findByQ_U_Last(
			long questionId, long userId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByQ_U_Last(
			questionId, userId, orderByComparator);
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchByQ_U_Last(
		long questionId, long userId,
		OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().fetchByQ_U_Last(
			questionId, userId, orderByComparator);
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote[] findByQ_U_PrevAndNext(
			long voteId, long questionId, long userId,
			OrderByComparator<PollsVote> orderByComparator)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByQ_U_PrevAndNext(
			voteId, questionId, userId, orderByComparator);
	}

	/**
	 * Removes all the polls votes where questionId = &#63; and userId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 */
	public static void removeByQ_U(long questionId, long userId) {
		getPersistence().removeByQ_U(questionId, userId);
	}

	/**
	 * Returns the number of polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @return the number of matching polls votes
	 */
	public static int countByQ_U(long questionId, long userId) {
		return getPersistence().countByQ_U(questionId, userId);
	}

	/**
	 * Caches the polls vote in the entity cache if it is enabled.
	 *
	 * @param pollsVote the polls vote
	 */
	public static void cacheResult(PollsVote pollsVote) {
		getPersistence().cacheResult(pollsVote);
	}

	/**
	 * Caches the polls votes in the entity cache if it is enabled.
	 *
	 * @param pollsVotes the polls votes
	 */
	public static void cacheResult(List<PollsVote> pollsVotes) {
		getPersistence().cacheResult(pollsVotes);
	}

	/**
	 * Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	 *
	 * @param voteId the primary key for the new polls vote
	 * @return the new polls vote
	 */
	public static PollsVote create(long voteId) {
		return getPersistence().create(voteId);
	}

	/**
	 * Removes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote that was removed
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote remove(long voteId)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().remove(voteId);
	}

	public static PollsVote updateImpl(PollsVote pollsVote) {
		return getPersistence().updateImpl(pollsVote);
	}

	/**
	 * Returns the polls vote with the primary key or throws a <code>NoSuchVoteException</code> if it could not be found.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	public static PollsVote findByPrimaryKey(long voteId)
		throws com.liferay.polls.exception.NoSuchVoteException {

		return getPersistence().findByPrimaryKey(voteId);
	}

	/**
	 * Returns the polls vote with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote, or <code>null</code> if a polls vote with the primary key could not be found
	 */
	public static PollsVote fetchByPrimaryKey(long voteId) {
		return getPersistence().fetchByPrimaryKey(voteId);
	}

	/**
	 * Returns all the polls votes.
	 *
	 * @return the polls votes
	 */
	public static List<PollsVote> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of polls votes
	 */
	public static List<PollsVote> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of polls votes
	 */
	public static List<PollsVote> findAll(
		int start, int end, OrderByComparator<PollsVote> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of polls votes
	 */
	public static List<PollsVote> findAll(
		int start, int end, OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the polls votes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of polls votes.
	 *
	 * @return the number of polls votes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PollsVotePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<PollsVotePersistence, PollsVotePersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(PollsVotePersistence.class);

		ServiceTracker<PollsVotePersistence, PollsVotePersistence>
			serviceTracker =
				new ServiceTracker<PollsVotePersistence, PollsVotePersistence>(
					bundle.getBundleContext(), PollsVotePersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}