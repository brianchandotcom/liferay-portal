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

import aQute.bnd.annotation.ProviderType;

import com.liferay.polls.exception.NoSuchVoteException;
import com.liferay.polls.model.PollsVote;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the polls vote service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.polls.service.persistence.impl.PollsVotePersistenceImpl
 * @see PollsVoteUtil
 * @generated
 */
@ProviderType
public interface PollsVotePersistence extends BasePersistence<PollsVote> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PollsVoteUtil} to access the polls vote persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the polls votes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the polls votes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the polls votes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns an ordered range of all the polls votes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first polls vote in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the first polls vote in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the last polls vote in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the last polls vote in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the polls votes before and after the current polls vote in the ordered set where uuid = &#63;.
	*
	* @param voteId the primary key of the current polls vote
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls vote
	* @throws NoSuchVoteException if a polls vote with the primary key could not be found
	*/
	public PollsVote[] findByUuid_PrevAndNext(long voteId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Removes all the polls votes where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of polls votes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching polls votes
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the polls vote where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchVoteException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchVoteException;

	/**
	* Returns the polls vote where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the polls vote where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the polls vote where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the polls vote that was removed
	*/
	public PollsVote removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchVoteException;

	/**
	* Returns the number of polls votes where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching polls votes
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the polls votes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the polls votes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the polls votes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns an ordered range of all the polls votes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the first polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the last polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the last polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

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
	public PollsVote[] findByUuid_C_PrevAndNext(long voteId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Removes all the polls votes where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of polls votes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching polls votes
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the polls votes where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the matching polls votes
	*/
	public java.util.List<PollsVote> findByQuestionId(long questionId);

	/**
	* Returns a range of all the polls votes where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	*/
	public java.util.List<PollsVote> findByQuestionId(long questionId,
		int start, int end);

	/**
	* Returns an ordered range of all the polls votes where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByQuestionId(long questionId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns an ordered range of all the polls votes where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByQuestionId(long questionId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first polls vote in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByQuestionId_First(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the first polls vote in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByQuestionId_First(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the last polls vote in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByQuestionId_Last(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the last polls vote in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByQuestionId_Last(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the polls votes before and after the current polls vote in the ordered set where questionId = &#63;.
	*
	* @param voteId the primary key of the current polls vote
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls vote
	* @throws NoSuchVoteException if a polls vote with the primary key could not be found
	*/
	public PollsVote[] findByQuestionId_PrevAndNext(long voteId,
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Removes all the polls votes where questionId = &#63; from the database.
	*
	* @param questionId the question ID
	*/
	public void removeByQuestionId(long questionId);

	/**
	* Returns the number of polls votes where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the number of matching polls votes
	*/
	public int countByQuestionId(long questionId);

	/**
	* Returns all the polls votes where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @return the matching polls votes
	*/
	public java.util.List<PollsVote> findByChoiceId(long choiceId);

	/**
	* Returns a range of all the polls votes where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param choiceId the choice ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	*/
	public java.util.List<PollsVote> findByChoiceId(long choiceId, int start,
		int end);

	/**
	* Returns an ordered range of all the polls votes where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param choiceId the choice ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByChoiceId(long choiceId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns an ordered range of all the polls votes where choiceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param choiceId the choice ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByChoiceId(long choiceId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first polls vote in the ordered set where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByChoiceId_First(long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the first polls vote in the ordered set where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByChoiceId_First(long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the last polls vote in the ordered set where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByChoiceId_Last(long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the last polls vote in the ordered set where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByChoiceId_Last(long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the polls votes before and after the current polls vote in the ordered set where choiceId = &#63;.
	*
	* @param voteId the primary key of the current polls vote
	* @param choiceId the choice ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls vote
	* @throws NoSuchVoteException if a polls vote with the primary key could not be found
	*/
	public PollsVote[] findByChoiceId_PrevAndNext(long voteId, long choiceId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Removes all the polls votes where choiceId = &#63; from the database.
	*
	* @param choiceId the choice ID
	*/
	public void removeByChoiceId(long choiceId);

	/**
	* Returns the number of polls votes where choiceId = &#63;.
	*
	* @param choiceId the choice ID
	* @return the number of matching polls votes
	*/
	public int countByChoiceId(long choiceId);

	/**
	* Returns all the polls votes where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @return the matching polls votes
	*/
	public java.util.List<PollsVote> findByQ_U(long questionId, long userId);

	/**
	* Returns a range of all the polls votes where questionId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of matching polls votes
	*/
	public java.util.List<PollsVote> findByQ_U(long questionId, long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the polls votes where questionId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByQ_U(long questionId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns an ordered range of all the polls votes where questionId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching polls votes
	*/
	public java.util.List<PollsVote> findByQ_U(long questionId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByQ_U_First(long questionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the first polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByQ_U_First(long questionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns the last polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote
	* @throws NoSuchVoteException if a matching polls vote could not be found
	*/
	public PollsVote findByQ_U_Last(long questionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Returns the last polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public PollsVote fetchByQ_U_Last(long questionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

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
	public PollsVote[] findByQ_U_PrevAndNext(long voteId, long questionId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException;

	/**
	* Removes all the polls votes where questionId = &#63; and userId = &#63; from the database.
	*
	* @param questionId the question ID
	* @param userId the user ID
	*/
	public void removeByQ_U(long questionId, long userId);

	/**
	* Returns the number of polls votes where questionId = &#63; and userId = &#63;.
	*
	* @param questionId the question ID
	* @param userId the user ID
	* @return the number of matching polls votes
	*/
	public int countByQ_U(long questionId, long userId);

	/**
	* Caches the polls vote in the entity cache if it is enabled.
	*
	* @param pollsVote the polls vote
	*/
	public void cacheResult(PollsVote pollsVote);

	/**
	* Caches the polls votes in the entity cache if it is enabled.
	*
	* @param pollsVotes the polls votes
	*/
	public void cacheResult(java.util.List<PollsVote> pollsVotes);

	/**
	* Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	*
	* @param voteId the primary key for the new polls vote
	* @return the new polls vote
	*/
	public PollsVote create(long voteId);

	/**
	* Removes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote that was removed
	* @throws NoSuchVoteException if a polls vote with the primary key could not be found
	*/
	public PollsVote remove(long voteId) throws NoSuchVoteException;

	public PollsVote updateImpl(PollsVote pollsVote);

	/**
	* Returns the polls vote with the primary key or throws a {@link NoSuchVoteException} if it could not be found.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote
	* @throws NoSuchVoteException if a polls vote with the primary key could not be found
	*/
	public PollsVote findByPrimaryKey(long voteId) throws NoSuchVoteException;

	/**
	* Returns the polls vote with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote, or <code>null</code> if a polls vote with the primary key could not be found
	*/
	public PollsVote fetchByPrimaryKey(long voteId);

	@Override
	public java.util.Map<java.io.Serializable, PollsVote> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the polls votes.
	*
	* @return the polls votes
	*/
	public java.util.List<PollsVote> findAll();

	/**
	* Returns a range of all the polls votes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of polls votes
	*/
	public java.util.List<PollsVote> findAll(int start, int end);

	/**
	* Returns an ordered range of all the polls votes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of polls votes
	*/
	public java.util.List<PollsVote> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator);

	/**
	* Returns an ordered range of all the polls votes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of polls votes
	*/
	public java.util.List<PollsVote> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsVote> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the polls votes from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of polls votes.
	*
	* @return the number of polls votes
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}