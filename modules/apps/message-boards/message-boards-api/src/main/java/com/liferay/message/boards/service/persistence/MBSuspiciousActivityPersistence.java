/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.persistence;

import com.liferay.message.boards.exception.NoSuchSuspiciousActivityException;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the message boards suspicious activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivityUtil
 * @generated
 */
@ProviderType
public interface MBSuspiciousActivityPersistence
	extends BasePersistence<MBSuspiciousActivity>,
			CTPersistence<MBSuspiciousActivity> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBSuspiciousActivityUtil} to access the message boards suspicious activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the message boards suspicious activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid(String uuid);

	/**
	 * Returns a range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where uuid = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity[] findByUuid_PrevAndNext(
			long suspiciousActivityId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Removes all the message boards suspicious activities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByUUID_G(String uuid, long groupId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the message boards suspicious activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the message boards suspicious activity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards suspicious activity that was removed
	 */
	public MBSuspiciousActivity removeByUUID_G(String uuid, long groupId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the first message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the last message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity[] findByUuid_C_PrevAndNext(
			long suspiciousActivityId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Removes all the message boards suspicious activities where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of message boards suspicious activities where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the message boards suspicious activities where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByMessageId(long messageId);

	/**
	 * Returns a range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByMessageId(
		long messageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByMessageId_First(
			long messageId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the first message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByMessageId_First(
		long messageId,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the last message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByMessageId_Last(
			long messageId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the last message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByMessageId_Last(
		long messageId,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where messageId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity[] findByMessageId_PrevAndNext(
			long suspiciousActivityId, long messageId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Removes all the message boards suspicious activities where messageId = &#63; from the database.
	 *
	 * @param messageId the message ID
	 */
	public void removeByMessageId(long messageId);

	/**
	 * Returns the number of message boards suspicious activities where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByMessageId(long messageId);

	/**
	 * Returns all the message boards suspicious activities where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByThreadId(long threadId);

	/**
	 * Returns a range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards suspicious activities where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByThreadId_First(
			long threadId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the first message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByThreadId_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the last message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByThreadId_Last(
			long threadId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the last message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByThreadId_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns the message boards suspicious activities before and after the current message boards suspicious activity in the ordered set where threadId = &#63;.
	 *
	 * @param suspiciousActivityId the primary key of the current message boards suspicious activity
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity[] findByThreadId_PrevAndNext(
			long suspiciousActivityId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MBSuspiciousActivity> orderByComparator)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Removes all the message boards suspicious activities where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 */
	public void removeByThreadId(long threadId);

	/**
	 * Returns the number of message boards suspicious activities where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByThreadId(long threadId);

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByU_M(long userId, long messageId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByU_M(long userId, long messageId);

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByU_M(
		long userId, long messageId, boolean useFinderCache);

	/**
	 * Removes the message boards suspicious activity where userId = &#63; and messageId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the message boards suspicious activity that was removed
	 */
	public MBSuspiciousActivity removeByU_M(long userId, long messageId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the number of message boards suspicious activities where userId = &#63; and messageId = &#63;.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByU_M(long userId, long messageId);

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity findByU_T(long userId, long threadId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByU_T(long userId, long threadId);

	/**
	 * Returns the message boards suspicious activity where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards suspicious activity, or <code>null</code> if a matching message boards suspicious activity could not be found
	 */
	public MBSuspiciousActivity fetchByU_T(
		long userId, long threadId, boolean useFinderCache);

	/**
	 * Removes the message boards suspicious activity where userId = &#63; and threadId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the message boards suspicious activity that was removed
	 */
	public MBSuspiciousActivity removeByU_T(long userId, long threadId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the number of message boards suspicious activities where userId = &#63; and threadId = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the number of matching message boards suspicious activities
	 */
	public int countByU_T(long userId, long threadId);

	/**
	 * Caches the message boards suspicious activity in the entity cache if it is enabled.
	 *
	 * @param mbSuspiciousActivity the message boards suspicious activity
	 */
	public void cacheResult(MBSuspiciousActivity mbSuspiciousActivity);

	/**
	 * Caches the message boards suspicious activities in the entity cache if it is enabled.
	 *
	 * @param mbSuspiciousActivities the message boards suspicious activities
	 */
	public void cacheResult(
		java.util.List<MBSuspiciousActivity> mbSuspiciousActivities);

	/**
	 * Creates a new message boards suspicious activity with the primary key. Does not add the message boards suspicious activity to the database.
	 *
	 * @param suspiciousActivityId the primary key for the new message boards suspicious activity
	 * @return the new message boards suspicious activity
	 */
	public MBSuspiciousActivity create(long suspiciousActivityId);

	/**
	 * Removes the message boards suspicious activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity that was removed
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity remove(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException;

	public MBSuspiciousActivity updateImpl(
		MBSuspiciousActivity mbSuspiciousActivity);

	/**
	 * Returns the message boards suspicious activity with the primary key or throws a <code>NoSuchSuspiciousActivityException</code> if it could not be found.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity
	 * @throws NoSuchSuspiciousActivityException if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity findByPrimaryKey(long suspiciousActivityId)
		throws NoSuchSuspiciousActivityException;

	/**
	 * Returns the message boards suspicious activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param suspiciousActivityId the primary key of the message boards suspicious activity
	 * @return the message boards suspicious activity, or <code>null</code> if a message boards suspicious activity with the primary key could not be found
	 */
	public MBSuspiciousActivity fetchByPrimaryKey(long suspiciousActivityId);

	/**
	 * Returns all the message boards suspicious activities.
	 *
	 * @return the message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findAll();

	/**
	 * Returns a range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @return the range of message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message boards suspicious activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBSuspiciousActivityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards suspicious activities
	 * @param end the upper bound of the range of message boards suspicious activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards suspicious activities
	 */
	public java.util.List<MBSuspiciousActivity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBSuspiciousActivity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the message boards suspicious activities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of message boards suspicious activities.
	 *
	 * @return the number of message boards suspicious activities
	 */
	public int countAll();

}