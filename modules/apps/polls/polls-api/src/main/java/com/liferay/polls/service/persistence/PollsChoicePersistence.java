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

import com.liferay.polls.exception.NoSuchChoiceException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the polls choice service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoiceUtil
 * @generated
 */
@ProviderType
public interface PollsChoicePersistence extends BasePersistence<PollsChoice> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PollsChoiceUtil} to access the polls choice persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the polls choices where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid(String uuid);

	/**
	 * Returns a range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns the polls choices before and after the current polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param choiceId the primary key of the current polls choice
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls choice
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	public PollsChoice[] findByUuid_PrevAndNext(
			long choiceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Removes all the polls choices where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of polls choices where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching polls choices
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchChoiceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByUUID_G(String uuid, long groupId)
		throws NoSuchChoiceException;

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the polls choice where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the polls choice that was removed
	 */
	public PollsChoice removeByUUID_G(String uuid, long groupId)
		throws NoSuchChoiceException;

	/**
	 * Returns the number of polls choices where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching polls choices
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns the polls choices before and after the current polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param choiceId the primary key of the current polls choice
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls choice
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	public PollsChoice[] findByUuid_C_PrevAndNext(
			long choiceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Removes all the polls choices where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching polls choices
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the polls choices where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching polls choices
	 */
	public java.util.List<PollsChoice> findByQuestionId(long questionId);

	/**
	 * Returns a range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByQuestionId(
		long questionId, int start, int end);

	/**
	 * Returns an ordered range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls choices
	 */
	public java.util.List<PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByQuestionId_First(
			long questionId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Returns the first polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByQuestionId_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns the last polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByQuestionId_Last(
			long questionId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Returns the last polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByQuestionId_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns the polls choices before and after the current polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param choiceId the primary key of the current polls choice
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls choice
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	public PollsChoice[] findByQuestionId_PrevAndNext(
			long choiceId, long questionId,
			com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
				orderByComparator)
		throws NoSuchChoiceException;

	/**
	 * Removes all the polls choices where questionId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 */
	public void removeByQuestionId(long questionId);

	/**
	 * Returns the number of polls choices where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the number of matching polls choices
	 */
	public int countByQuestionId(long questionId);

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or throws a <code>NoSuchChoiceException</code> if it could not be found.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	public PollsChoice findByQ_N(long questionId, String name)
		throws NoSuchChoiceException;

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByQ_N(long questionId, String name);

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	public PollsChoice fetchByQ_N(
		long questionId, String name, boolean useFinderCache);

	/**
	 * Removes the polls choice where questionId = &#63; and name = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the polls choice that was removed
	 */
	public PollsChoice removeByQ_N(long questionId, String name)
		throws NoSuchChoiceException;

	/**
	 * Returns the number of polls choices where questionId = &#63; and name = &#63;.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the number of matching polls choices
	 */
	public int countByQ_N(long questionId, String name);

	/**
	 * Caches the polls choice in the entity cache if it is enabled.
	 *
	 * @param pollsChoice the polls choice
	 */
	public void cacheResult(PollsChoice pollsChoice);

	/**
	 * Caches the polls choices in the entity cache if it is enabled.
	 *
	 * @param pollsChoices the polls choices
	 */
	public void cacheResult(java.util.List<PollsChoice> pollsChoices);

	/**
	 * Creates a new polls choice with the primary key. Does not add the polls choice to the database.
	 *
	 * @param choiceId the primary key for the new polls choice
	 * @return the new polls choice
	 */
	public PollsChoice create(long choiceId);

	/**
	 * Removes the polls choice with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice that was removed
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	public PollsChoice remove(long choiceId) throws NoSuchChoiceException;

	public PollsChoice updateImpl(PollsChoice pollsChoice);

	/**
	 * Returns the polls choice with the primary key or throws a <code>NoSuchChoiceException</code> if it could not be found.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	public PollsChoice findByPrimaryKey(long choiceId)
		throws NoSuchChoiceException;

	/**
	 * Returns the polls choice with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice, or <code>null</code> if a polls choice with the primary key could not be found
	 */
	public PollsChoice fetchByPrimaryKey(long choiceId);

	/**
	 * Returns all the polls choices.
	 *
	 * @return the polls choices
	 */
	public java.util.List<PollsChoice> findAll();

	/**
	 * Returns a range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of polls choices
	 */
	public java.util.List<PollsChoice> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of polls choices
	 */
	public java.util.List<PollsChoice> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator);

	/**
	 * Returns an ordered range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of polls choices
	 */
	public java.util.List<PollsChoice> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PollsChoice>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the polls choices from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of polls choices.
	 *
	 * @return the number of polls choices
	 */
	public int countAll();

}