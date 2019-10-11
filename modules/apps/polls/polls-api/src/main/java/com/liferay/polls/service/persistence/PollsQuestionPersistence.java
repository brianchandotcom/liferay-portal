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

import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the polls question service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsQuestionUtil
 * @generated
 */
@ProviderType
public interface PollsQuestionPersistence
	extends BasePersistence<PollsQuestion> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PollsQuestionUtil} to access the polls question persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the polls questions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid(String uuid);

	/**
	 * Returns a range of all the polls questions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @return the range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the polls questions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls questions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first polls question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns the first polls question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the last polls question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns the last polls question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the polls questions before and after the current polls question in the ordered set where uuid = &#63;.
	 *
	 * @param questionId the primary key of the current polls question
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls question
	 * @throws NoSuchQuestionException if a polls question with the primary key could not be found
	 */
	public PollsQuestion[] findByUuid_PrevAndNext(
			long questionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Removes all the polls questions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of polls questions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching polls questions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the polls question where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchQuestionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByUUID_G(String uuid, long groupId)
		throws NoSuchQuestionException;

	/**
	 * Returns the polls question where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the polls question where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the polls question where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the polls question that was removed
	 */
	public PollsQuestion removeByUUID_G(String uuid, long groupId)
		throws NoSuchQuestionException;

	/**
	 * Returns the number of polls questions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching polls questions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the polls questions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the polls questions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @return the range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the polls questions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls questions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first polls question in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns the first polls question in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the last polls question in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns the last polls question in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the polls questions before and after the current polls question in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param questionId the primary key of the current polls question
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls question
	 * @throws NoSuchQuestionException if a polls question with the primary key could not be found
	 */
	public PollsQuestion[] findByUuid_C_PrevAndNext(
			long questionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Removes all the polls questions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of polls questions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching polls questions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the polls questions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching polls questions
	 */
	public java.util.List<PollsQuestion> findByGroupId(long groupId);

	/**
	 * Returns a range of all the polls questions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @return the range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the polls questions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls questions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls questions
	 */
	public java.util.List<PollsQuestion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first polls question in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns the first polls question in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the last polls question in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls question
	 * @throws NoSuchQuestionException if a matching polls question could not be found
	 */
	public PollsQuestion findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns the last polls question in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls question, or <code>null</code> if a matching polls question could not be found
	 */
	public PollsQuestion fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the polls questions before and after the current polls question in the ordered set where groupId = &#63;.
	 *
	 * @param questionId the primary key of the current polls question
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls question
	 * @throws NoSuchQuestionException if a polls question with the primary key could not be found
	 */
	public PollsQuestion[] findByGroupId_PrevAndNext(
			long questionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Returns all the polls questions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching polls questions that the user has permission to view
	 */
	public java.util.List<PollsQuestion> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the polls questions that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @return the range of matching polls questions that the user has permission to view
	 */
	public java.util.List<PollsQuestion> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the polls questions that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls questions that the user has permission to view
	 */
	public java.util.List<PollsQuestion> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns the polls questions before and after the current polls question in the ordered set of polls questions that the user has permission to view where groupId = &#63;.
	 *
	 * @param questionId the primary key of the current polls question
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls question
	 * @throws NoSuchQuestionException if a polls question with the primary key could not be found
	 */
	public PollsQuestion[] filterFindByGroupId_PrevAndNext(
			long questionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
				orderByComparator)
		throws NoSuchQuestionException;

	/**
	 * Removes all the polls questions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of polls questions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching polls questions
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of polls questions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching polls questions that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Caches the polls question in the entity cache if it is enabled.
	 *
	 * @param pollsQuestion the polls question
	 */
	public void cacheResult(PollsQuestion pollsQuestion);

	/**
	 * Caches the polls questions in the entity cache if it is enabled.
	 *
	 * @param pollsQuestions the polls questions
	 */
	public void cacheResult(java.util.List<PollsQuestion> pollsQuestions);

	/**
	 * Creates a new polls question with the primary key. Does not add the polls question to the database.
	 *
	 * @param questionId the primary key for the new polls question
	 * @return the new polls question
	 */
	public PollsQuestion create(long questionId);

	/**
	 * Removes the polls question with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param questionId the primary key of the polls question
	 * @return the polls question that was removed
	 * @throws NoSuchQuestionException if a polls question with the primary key could not be found
	 */
	public PollsQuestion remove(long questionId) throws NoSuchQuestionException;

	public PollsQuestion updateImpl(PollsQuestion pollsQuestion);

	/**
	 * Returns the polls question with the primary key or throws a <code>NoSuchQuestionException</code> if it could not be found.
	 *
	 * @param questionId the primary key of the polls question
	 * @return the polls question
	 * @throws NoSuchQuestionException if a polls question with the primary key could not be found
	 */
	public PollsQuestion findByPrimaryKey(long questionId)
		throws NoSuchQuestionException;

	/**
	 * Returns the polls question with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param questionId the primary key of the polls question
	 * @return the polls question, or <code>null</code> if a polls question with the primary key could not be found
	 */
	public PollsQuestion fetchByPrimaryKey(long questionId);

	/**
	 * Returns all the polls questions.
	 *
	 * @return the polls questions
	 */
	public java.util.List<PollsQuestion> findAll();

	/**
	 * Returns a range of all the polls questions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @return the range of polls questions
	 */
	public java.util.List<PollsQuestion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the polls questions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of polls questions
	 */
	public java.util.List<PollsQuestion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls questions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsQuestionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls questions
	 * @param end the upper bound of the range of polls questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of polls questions
	 */
	public java.util.List<PollsQuestion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsQuestion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the polls questions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of polls questions.
	 *
	 * @return the number of polls questions
	 */
	public int countAll();

}