/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.polls.model.PollsVote;

import java.util.List;

/**
 * The persistence utility for the polls vote service. This utility wraps {@link PollsVotePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsVotePersistence
 * @see PollsVotePersistenceImpl
 * @generated
 */
public class PollsVoteUtil {
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
	public static void clearCache(PollsVote pollsVote) {
		getPersistence().clearCache(pollsVote);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PollsVote> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PollsVote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PollsVote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PollsVote update(PollsVote pollsVote, boolean merge)
		throws SystemException {
		return getPersistence().update(pollsVote, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static PollsVote update(PollsVote pollsVote, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(pollsVote, merge, serviceContext);
	}

	/**
	* Caches the polls vote in the entity cache if it is enabled.
	*
	* @param pollsVote the polls vote
	*/
	public static void cacheResult(
		com.liferay.portlet.polls.model.PollsVote pollsVote) {
		getPersistence().cacheResult(pollsVote);
	}

	/**
	* Caches the polls votes in the entity cache if it is enabled.
	*
	* @param pollsVotes the polls votes
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsVote> pollsVotes) {
		getPersistence().cacheResult(pollsVotes);
	}

	/**
	* Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	*
	* @param voteId the primary key for the new polls vote
	* @return the new polls vote
	*/
	public static com.liferay.portlet.polls.model.PollsVote create(long voteId) {
		return getPersistence().create(voteId);
	}

	/**
	* Removes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote that was removed
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a polls vote with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote remove(long voteId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().remove(voteId);
	}

	public static com.liferay.portlet.polls.model.PollsVote updateImpl(
		com.liferay.portlet.polls.model.PollsVote pollsVote, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(pollsVote, merge);
	}

	/**
	* Returns the polls vote with the primary key or throws a {@link com.liferay.portlet.polls.NoSuchVoteException} if it could not be found.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a polls vote with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote findByPrimaryKey(
		long voteId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByPrimaryKey(voteId);
	}

	/**
	* Returns the polls vote with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote, or <code>null</code> if a polls vote with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote fetchByPrimaryKey(
		long voteId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(voteId);
	}

	/**
	* Returns all the polls votes where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId);
	}

	/**
	* Returns a range of all the polls votes where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end);
	}

	/**
	* Returns an ordered range of all the polls votes where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByQuestionId(questionId, start, end, orderByComparator);
	}

	/**
	* Returns the first polls vote in the ordered set where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote findByQuestionId_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence()
				   .findByQuestionId_First(questionId, orderByComparator);
	}

	/**
	* Returns the last polls vote in the ordered set where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote findByQuestionId_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence()
				   .findByQuestionId_Last(questionId, orderByComparator);
	}

	/**
	* Returns the polls votes before and after the current polls vote in the ordered set where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param voteId the primary key of the current polls vote
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a polls vote with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote[] findByQuestionId_PrevAndNext(
		long voteId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence()
				   .findByQuestionId_PrevAndNext(voteId, questionId,
			orderByComparator);
	}

	/**
	* Returns all the polls votes where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @return the matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByChoiceId(choiceId);
	}

	/**
	* Returns a range of all the polls votes where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param choiceId the choice ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByChoiceId(choiceId, start, end);
	}

	/**
	* Returns an ordered range of all the polls votes where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param choiceId the choice ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByChoiceId(choiceId, start, end, orderByComparator);
	}

	/**
	* Returns the first polls vote in the ordered set where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote findByChoiceId_First(
		long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByChoiceId_First(choiceId, orderByComparator);
	}

	/**
	* Returns the last polls vote in the ordered set where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote findByChoiceId_Last(
		long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByChoiceId_Last(choiceId, orderByComparator);
	}

	/**
	* Returns the polls votes before and after the current polls vote in the ordered set where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param voteId the primary key of the current polls vote
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a polls vote with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote[] findByChoiceId_PrevAndNext(
		long voteId, long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence()
				   .findByChoiceId_PrevAndNext(voteId, choiceId,
			orderByComparator);
	}

	/**
	* Returns the polls vote where questionId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.polls.NoSuchVoteException} if it could not be found.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @return the matching polls vote
	* @throws com.liferay.portlet.polls.NoSuchVoteException if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote findByQ_U(
		long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().findByQ_U(questionId, userId);
	}

	/**
	* Returns the polls vote where questionId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote fetchByQ_U(
		long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByQ_U(questionId, userId);
	}

	/**
	* Returns the polls vote where questionId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote fetchByQ_U(
		long questionId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByQ_U(questionId, userId, retrieveFromCache);
	}

	/**
	* Returns all the polls votes.
	*
	* @return the polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the polls votes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the polls votes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the polls votes where questionId = &#63; from the database.
	*
	* @param questionId the question ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	/**
	* Removes all the polls votes where choiceId = &#63; from the database.
	*
	* @param choiceId the choice ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByChoiceId(long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByChoiceId(choiceId);
	}

	/**
	* Removes the polls vote where questionId = &#63; and userId = &#63; from the database.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @return the polls vote that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsVote removeByQ_U(
		long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchVoteException {
		return getPersistence().removeByQ_U(questionId, userId);
	}

	/**
	* Removes all the polls votes from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of polls votes where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the number of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static int countByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	/**
	* Returns the number of polls votes where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @return the number of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static int countByChoiceId(long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByChoiceId(choiceId);
	}

	/**
	* Returns the number of polls votes where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @return the number of matching polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static int countByQ_U(long questionId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQ_U(questionId, userId);
	}

	/**
	* Returns the number of polls votes.
	*
	* @return the number of polls votes
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PollsVotePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PollsVotePersistence)PortalBeanLocatorUtil.locate(PollsVotePersistence.class.getName());

			ReferenceRegistry.registerReference(PollsVoteUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(PollsVotePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(PollsVoteUtil.class, "_persistence");
	}

	private static PollsVotePersistence _persistence;
}