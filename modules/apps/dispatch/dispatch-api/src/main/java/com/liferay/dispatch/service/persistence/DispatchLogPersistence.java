/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.service.persistence;

import com.liferay.dispatch.exception.NoSuchLogException;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dispatch log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matija Petanjek
 * @see DispatchLogUtil
 * @generated
 */
@ProviderType
public interface DispatchLogPersistence extends BasePersistence<DispatchLog> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DispatchLogUtil} to access the dispatch log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @return the matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId);

	/**
	 * Returns a range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end);

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public DispatchLog findByDispatchTriggerId_First(
			long dispatchTriggerId,
			com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public DispatchLog fetchByDispatchTriggerId_First(
		long dispatchTriggerId,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public DispatchLog findByDispatchTriggerId_Last(
			long dispatchTriggerId,
			com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public DispatchLog fetchByDispatchTriggerId_Last(
		long dispatchTriggerId,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns the dispatch logs before and after the current dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchLogId the primary key of the current dispatch log
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public DispatchLog[] findByDispatchTriggerId_PrevAndNext(
			long dispatchLogId, long dispatchTriggerId,
			com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Removes all the dispatch logs where dispatchTriggerId = &#63; from the database.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 */
	public void removeByDispatchTriggerId(long dispatchTriggerId);

	/**
	 * Returns the number of dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @return the number of matching dispatch logs
	 */
	public int countByDispatchTriggerId(long dispatchTriggerId);

	/**
	 * Returns all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @return the matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status);

	/**
	 * Returns a range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch logs
	 */
	public java.util.List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public DispatchLog findByDTI_S_First(
			long dispatchTriggerId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public DispatchLog fetchByDTI_S_First(
		long dispatchTriggerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public DispatchLog findByDTI_S_Last(
			long dispatchTriggerId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public DispatchLog fetchByDTI_S_Last(
		long dispatchTriggerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns the dispatch logs before and after the current dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchLogId the primary key of the current dispatch log
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public DispatchLog[] findByDTI_S_PrevAndNext(
			long dispatchLogId, long dispatchTriggerId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Removes all the dispatch logs where dispatchTriggerId = &#63; and status = &#63; from the database.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 */
	public void removeByDTI_S(long dispatchTriggerId, int status);

	/**
	 * Returns the number of dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @return the number of matching dispatch logs
	 */
	public int countByDTI_S(long dispatchTriggerId, int status);

	/**
	 * Caches the dispatch log in the entity cache if it is enabled.
	 *
	 * @param dispatchLog the dispatch log
	 */
	public void cacheResult(DispatchLog dispatchLog);

	/**
	 * Caches the dispatch logs in the entity cache if it is enabled.
	 *
	 * @param dispatchLogs the dispatch logs
	 */
	public void cacheResult(java.util.List<DispatchLog> dispatchLogs);

	/**
	 * Creates a new dispatch log with the primary key. Does not add the dispatch log to the database.
	 *
	 * @param dispatchLogId the primary key for the new dispatch log
	 * @return the new dispatch log
	 */
	public DispatchLog create(long dispatchLogId);

	/**
	 * Removes the dispatch log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log that was removed
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public DispatchLog remove(long dispatchLogId) throws NoSuchLogException;

	public DispatchLog updateImpl(DispatchLog dispatchLog);

	/**
	 * Returns the dispatch log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public DispatchLog findByPrimaryKey(long dispatchLogId)
		throws NoSuchLogException;

	/**
	 * Returns the dispatch log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log, or <code>null</code> if a dispatch log with the primary key could not be found
	 */
	public DispatchLog fetchByPrimaryKey(long dispatchLogId);

	/**
	 * Returns all the dispatch logs.
	 *
	 * @return the dispatch logs
	 */
	public java.util.List<DispatchLog> findAll();

	/**
	 * Returns a range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of dispatch logs
	 */
	public java.util.List<DispatchLog> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dispatch logs
	 */
	public java.util.List<DispatchLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dispatch logs
	 */
	public java.util.List<DispatchLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DispatchLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dispatch logs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dispatch logs.
	 *
	 * @return the number of dispatch logs
	 */
	public int countAll();

}