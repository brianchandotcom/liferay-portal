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
import com.liferay.scheduler.exception.NoSuchProcessException;
import com.liferay.scheduler.model.SchedulerProcess;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the scheduler process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessUtil
 * @generated
 */
@ProviderType
public interface SchedulerProcessPersistence
	extends BasePersistence<SchedulerProcess> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SchedulerProcessUtil} to access the scheduler process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the scheduler processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public SchedulerProcess findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public SchedulerProcess fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public SchedulerProcess findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public SchedulerProcess fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns the scheduler processes before and after the current scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param schedulerProcessId the primary key of the current scheduler process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public SchedulerProcess[] findByCompanyId_PrevAndNext(
			long schedulerProcessId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Removes all the scheduler processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of scheduler processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching scheduler processes
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public SchedulerProcess findByC_N(long companyId, String name)
		throws NoSuchProcessException;

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public SchedulerProcess fetchByC_N(long companyId, String name);

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public SchedulerProcess fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the scheduler process where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the scheduler process that was removed
	 */
	public SchedulerProcess removeByC_N(long companyId, String name)
		throws NoSuchProcessException;

	/**
	 * Returns the number of scheduler processes where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching scheduler processes
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByC_T(
		long companyId, String type);

	/**
	 * Returns a range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler processes
	 */
	public java.util.List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public SchedulerProcess findByC_T_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public SchedulerProcess fetchByC_T_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public SchedulerProcess findByC_T_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public SchedulerProcess fetchByC_T_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns the scheduler processes before and after the current scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param schedulerProcessId the primary key of the current scheduler process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public SchedulerProcess[] findByC_T_PrevAndNext(
			long schedulerProcessId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Removes all the scheduler processes where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, String type);

	/**
	 * Returns the number of scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching scheduler processes
	 */
	public int countByC_T(long companyId, String type);

	/**
	 * Caches the scheduler process in the entity cache if it is enabled.
	 *
	 * @param schedulerProcess the scheduler process
	 */
	public void cacheResult(SchedulerProcess schedulerProcess);

	/**
	 * Caches the scheduler processes in the entity cache if it is enabled.
	 *
	 * @param schedulerProcesses the scheduler processes
	 */
	public void cacheResult(
		java.util.List<SchedulerProcess> schedulerProcesses);

	/**
	 * Creates a new scheduler process with the primary key. Does not add the scheduler process to the database.
	 *
	 * @param schedulerProcessId the primary key for the new scheduler process
	 * @return the new scheduler process
	 */
	public SchedulerProcess create(long schedulerProcessId);

	/**
	 * Removes the scheduler process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process that was removed
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public SchedulerProcess remove(long schedulerProcessId)
		throws NoSuchProcessException;

	public SchedulerProcess updateImpl(SchedulerProcess schedulerProcess);

	/**
	 * Returns the scheduler process with the primary key or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public SchedulerProcess findByPrimaryKey(long schedulerProcessId)
		throws NoSuchProcessException;

	/**
	 * Returns the scheduler process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process, or <code>null</code> if a scheduler process with the primary key could not be found
	 */
	public SchedulerProcess fetchByPrimaryKey(long schedulerProcessId);

	/**
	 * Returns all the scheduler processes.
	 *
	 * @return the scheduler processes
	 */
	public java.util.List<SchedulerProcess> findAll();

	/**
	 * Returns a range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of scheduler processes
	 */
	public java.util.List<SchedulerProcess> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of scheduler processes
	 */
	public java.util.List<SchedulerProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of scheduler processes
	 */
	public java.util.List<SchedulerProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchedulerProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the scheduler processes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of scheduler processes.
	 *
	 * @return the number of scheduler processes
	 */
	public int countAll();

}