/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.exception.NoSuchMessageException;
import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the analytics message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageUtil
 * @generated
 */
@ProviderType
public interface AnalyticsMessagePersistence
	extends BasePersistence<AnalyticsMessage>, CTPersistence<AnalyticsMessage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnalyticsMessageUtil} to access the analytics message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the analytics messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics messages
	 */
	public java.util.List<AnalyticsMessage> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of matching analytics messages
	 */
	public java.util.List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics messages
	 */
	public java.util.List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics messages
	 */
	public java.util.List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message
	 * @throws NoSuchMessageException if a matching analytics message could not be found
	 */
	public AnalyticsMessage findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message, or <code>null</code> if a matching analytics message could not be found
	 */
	public AnalyticsMessage fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
			orderByComparator);

	/**
	 * Returns the last analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message
	 * @throws NoSuchMessageException if a matching analytics message could not be found
	 */
	public AnalyticsMessage findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message, or <code>null</code> if a matching analytics message could not be found
	 */
	public AnalyticsMessage fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
			orderByComparator);

	/**
	 * Returns the analytics messages before and after the current analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsMessageId the primary key of the current analytics message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	public AnalyticsMessage[] findByCompanyId_PrevAndNext(
			long analyticsMessageId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the analytics messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of analytics messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics messages
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the analytics message in the entity cache if it is enabled.
	 *
	 * @param analyticsMessage the analytics message
	 */
	public void cacheResult(AnalyticsMessage analyticsMessage);

	/**
	 * Caches the analytics messages in the entity cache if it is enabled.
	 *
	 * @param analyticsMessages the analytics messages
	 */
	public void cacheResult(java.util.List<AnalyticsMessage> analyticsMessages);

	/**
	 * Creates a new analytics message with the primary key. Does not add the analytics message to the database.
	 *
	 * @param analyticsMessageId the primary key for the new analytics message
	 * @return the new analytics message
	 */
	public AnalyticsMessage create(long analyticsMessageId);

	/**
	 * Removes the analytics message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message that was removed
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	public AnalyticsMessage remove(long analyticsMessageId)
		throws NoSuchMessageException;

	public AnalyticsMessage updateImpl(AnalyticsMessage analyticsMessage);

	/**
	 * Returns the analytics message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	public AnalyticsMessage findByPrimaryKey(long analyticsMessageId)
		throws NoSuchMessageException;

	/**
	 * Returns the analytics message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message, or <code>null</code> if a analytics message with the primary key could not be found
	 */
	public AnalyticsMessage fetchByPrimaryKey(long analyticsMessageId);

	/**
	 * Returns all the analytics messages.
	 *
	 * @return the analytics messages
	 */
	public java.util.List<AnalyticsMessage> findAll();

	/**
	 * Returns a range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of analytics messages
	 */
	public java.util.List<AnalyticsMessage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics messages
	 */
	public java.util.List<AnalyticsMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics messages
	 */
	public java.util.List<AnalyticsMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the analytics messages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of analytics messages.
	 *
	 * @return the number of analytics messages
	 */
	public int countAll();

}