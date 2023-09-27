/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.exception.NoSuchDeleteMessageException;
import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the analytics delete message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsDeleteMessageUtil
 * @generated
 */
@ProviderType
public interface AnalyticsDeleteMessagePersistence
	extends BasePersistence<AnalyticsDeleteMessage>,
			CTPersistence<AnalyticsDeleteMessage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnalyticsDeleteMessageUtil} to access the analytics delete message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the analytics delete messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException;

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException;

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns the analytics delete messages before and after the current analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsDeleteMessageId the primary key of the current analytics delete message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public AnalyticsDeleteMessage[] findByCompanyId_PrevAndNext(
			long analyticsDeleteMessageId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException;

	/**
	 * Removes all the analytics delete messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of analytics delete messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics delete messages
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate);

	/**
	 * Returns a range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end);

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage findByC_GtM_First(
			long companyId, Date modifiedDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException;

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage fetchByC_GtM_First(
		long companyId, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage findByC_GtM_Last(
			long companyId, Date modifiedDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException;

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public AnalyticsDeleteMessage fetchByC_GtM_Last(
		long companyId, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns the analytics delete messages before and after the current analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param analyticsDeleteMessageId the primary key of the current analytics delete message
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public AnalyticsDeleteMessage[] findByC_GtM_PrevAndNext(
			long analyticsDeleteMessageId, long companyId, Date modifiedDate,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsDeleteMessage> orderByComparator)
		throws NoSuchDeleteMessageException;

	/**
	 * Removes all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 */
	public void removeByC_GtM(long companyId, Date modifiedDate);

	/**
	 * Returns the number of analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the number of matching analytics delete messages
	 */
	public int countByC_GtM(long companyId, Date modifiedDate);

	/**
	 * Caches the analytics delete message in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessage the analytics delete message
	 */
	public void cacheResult(AnalyticsDeleteMessage analyticsDeleteMessage);

	/**
	 * Caches the analytics delete messages in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessages the analytics delete messages
	 */
	public void cacheResult(
		java.util.List<AnalyticsDeleteMessage> analyticsDeleteMessages);

	/**
	 * Creates a new analytics delete message with the primary key. Does not add the analytics delete message to the database.
	 *
	 * @param analyticsDeleteMessageId the primary key for the new analytics delete message
	 * @return the new analytics delete message
	 */
	public AnalyticsDeleteMessage create(long analyticsDeleteMessageId);

	/**
	 * Removes the analytics delete message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message that was removed
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public AnalyticsDeleteMessage remove(long analyticsDeleteMessageId)
		throws NoSuchDeleteMessageException;

	public AnalyticsDeleteMessage updateImpl(
		AnalyticsDeleteMessage analyticsDeleteMessage);

	/**
	 * Returns the analytics delete message with the primary key or throws a <code>NoSuchDeleteMessageException</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public AnalyticsDeleteMessage findByPrimaryKey(
			long analyticsDeleteMessageId)
		throws NoSuchDeleteMessageException;

	/**
	 * Returns the analytics delete message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message, or <code>null</code> if a analytics delete message with the primary key could not be found
	 */
	public AnalyticsDeleteMessage fetchByPrimaryKey(
		long analyticsDeleteMessageId);

	/**
	 * Returns all the analytics delete messages.
	 *
	 * @return the analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findAll();

	/**
	 * Returns a range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics delete messages
	 */
	public java.util.List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsDeleteMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the analytics delete messages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of analytics delete messages.
	 *
	 * @return the number of analytics delete messages
	 */
	public int countAll();

}