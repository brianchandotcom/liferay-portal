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

package com.liferay.scheduler.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.scheduler.exception.NoSuchProcessLogException;
import com.liferay.scheduler.model.SchedulerProcessLog;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the scheduler process log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessLogUtil
 * @generated
 */
@ProviderType
public interface SchedulerProcessLogPersistence
	extends BasePersistence<SchedulerProcessLog> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SchedulerProcessLogUtil} to access the scheduler process log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @return the matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId);

	/**
	 * Returns a range of all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId, int start, int end);

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog findBySchedulerProcessId_First(
			long schedulerProcessId,
			com.liferay.portal.kernel.util.OrderByComparator
				<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException;

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog fetchBySchedulerProcessId_First(
		long schedulerProcessId,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog findBySchedulerProcessId_Last(
			long schedulerProcessId,
			com.liferay.portal.kernel.util.OrderByComparator
				<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException;

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog fetchBySchedulerProcessId_Last(
		long schedulerProcessId,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns the scheduler process logs before and after the current scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessLogId the primary key of the current scheduler process log
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	public SchedulerProcessLog[] findBySchedulerProcessId_PrevAndNext(
			long schedulerProcessLogId, long schedulerProcessId,
			com.liferay.portal.kernel.util.OrderByComparator
				<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException;

	/**
	 * Removes all the scheduler process logs where schedulerProcessId = &#63; from the database.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 */
	public void removeBySchedulerProcessId(long schedulerProcessId);

	/**
	 * Returns the number of scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @return the number of matching scheduler process logs
	 */
	public int countBySchedulerProcessId(long schedulerProcessId);

	/**
	 * Returns all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @return the matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status);

	/**
	 * Returns a range of all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog findByS_S_First(
			long schedulerProcessId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException;

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog fetchByS_S_First(
		long schedulerProcessId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog findByS_S_Last(
			long schedulerProcessId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException;

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	public SchedulerProcessLog fetchByS_S_Last(
		long schedulerProcessId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns the scheduler process logs before and after the current scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessLogId the primary key of the current scheduler process log
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	public SchedulerProcessLog[] findByS_S_PrevAndNext(
			long schedulerProcessLogId, long schedulerProcessId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException;

	/**
	 * Removes all the scheduler process logs where schedulerProcessId = &#63; and status = &#63; from the database.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 */
	public void removeByS_S(long schedulerProcessId, int status);

	/**
	 * Returns the number of scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @return the number of matching scheduler process logs
	 */
	public int countByS_S(long schedulerProcessId, int status);

	/**
	 * Caches the scheduler process log in the entity cache if it is enabled.
	 *
	 * @param schedulerProcessLog the scheduler process log
	 */
	public void cacheResult(SchedulerProcessLog schedulerProcessLog);

	/**
	 * Caches the scheduler process logs in the entity cache if it is enabled.
	 *
	 * @param schedulerProcessLogs the scheduler process logs
	 */
	public void cacheResult(
		java.util.List<SchedulerProcessLog> schedulerProcessLogs);

	/**
	 * Creates a new scheduler process log with the primary key. Does not add the scheduler process log to the database.
	 *
	 * @param schedulerProcessLogId the primary key for the new scheduler process log
	 * @return the new scheduler process log
	 */
	public SchedulerProcessLog create(long schedulerProcessLogId);

	/**
	 * Removes the scheduler process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log that was removed
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	public SchedulerProcessLog remove(long schedulerProcessLogId)
		throws NoSuchProcessLogException;

	public SchedulerProcessLog updateImpl(
		SchedulerProcessLog schedulerProcessLog);

	/**
	 * Returns the scheduler process log with the primary key or throws a <code>NoSuchProcessLogException</code> if it could not be found.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	public SchedulerProcessLog findByPrimaryKey(long schedulerProcessLogId)
		throws NoSuchProcessLogException;

	/**
	 * Returns the scheduler process log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log, or <code>null</code> if a scheduler process log with the primary key could not be found
	 */
	public SchedulerProcessLog fetchByPrimaryKey(long schedulerProcessLogId);

	/**
	 * Returns all the scheduler process logs.
	 *
	 * @return the scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findAll();

	/**
	 * Returns a range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of scheduler process logs
	 */
	public java.util.List<SchedulerProcessLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcessLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the scheduler process logs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of scheduler process logs.
	 *
	 * @return the number of scheduler process logs
	 */
	public int countAll();

}