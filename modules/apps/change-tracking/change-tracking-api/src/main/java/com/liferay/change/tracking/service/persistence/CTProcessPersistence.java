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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchProcessException;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTProcessUtil
 * @generated
 */
@ProviderType
public interface CTProcessPersistence extends BasePersistence<CTProcess> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTProcessUtil} to access the ct process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct processes
	 */
	public java.util.List<CTProcess> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of matching ct processes
	 */
	public java.util.List<CTProcess> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct processes
	 */
	public java.util.List<CTProcess> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct processes
	 */
	public java.util.List<CTProcess> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public CTProcess findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the first ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public CTProcess fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns the last ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public CTProcess findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the last ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public CTProcess fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns the ct processes before and after the current ct process in the ordered set where companyId = &#63;.
	 *
	 * @param ctProcessId the primary key of the current ct process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public CTProcess[] findByCompanyId_PrevAndNext(
			long ctProcessId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Removes all the ct processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ct processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct processes
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the ct processes where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct processes
	 */
	public java.util.List<CTProcess> findByCtCollectionId(long ctCollectionId);

	/**
	 * Returns a range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of matching ct processes
	 */
	public java.util.List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct processes
	 */
	public java.util.List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct processes
	 */
	public java.util.List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public CTProcess findByCtCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the first ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public CTProcess fetchByCtCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns the last ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public CTProcess findByCtCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Returns the last ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public CTProcess fetchByCtCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns the ct processes before and after the current ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctProcessId the primary key of the current ct process
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public CTProcess[] findByCtCollectionId_PrevAndNext(
			long ctProcessId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
				orderByComparator)
		throws NoSuchProcessException;

	/**
	 * Removes all the ct processes where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCtCollectionId(long ctCollectionId);

	/**
	 * Returns the number of ct processes where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct processes
	 */
	public int countByCtCollectionId(long ctCollectionId);

	/**
	 * Caches the ct process in the entity cache if it is enabled.
	 *
	 * @param ctProcess the ct process
	 */
	public void cacheResult(CTProcess ctProcess);

	/**
	 * Caches the ct processes in the entity cache if it is enabled.
	 *
	 * @param ctProcesses the ct processes
	 */
	public void cacheResult(java.util.List<CTProcess> ctProcesses);

	/**
	 * Creates a new ct process with the primary key. Does not add the ct process to the database.
	 *
	 * @param ctProcessId the primary key for the new ct process
	 * @return the new ct process
	 */
	public CTProcess create(long ctProcessId);

	/**
	 * Removes the ct process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process that was removed
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public CTProcess remove(long ctProcessId) throws NoSuchProcessException;

	public CTProcess updateImpl(CTProcess ctProcess);

	/**
	 * Returns the ct process with the primary key or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public CTProcess findByPrimaryKey(long ctProcessId)
		throws NoSuchProcessException;

	/**
	 * Returns the ct process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process, or <code>null</code> if a ct process with the primary key could not be found
	 */
	public CTProcess fetchByPrimaryKey(long ctProcessId);

	/**
	 * Returns all the ct processes.
	 *
	 * @return the ct processes
	 */
	public java.util.List<CTProcess> findAll();

	/**
	 * Returns a range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of ct processes
	 */
	public java.util.List<CTProcess> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct processes
	 */
	public java.util.List<CTProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct processes
	 */
	public java.util.List<CTProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct processes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct processes.
	 *
	 * @return the number of ct processes
	 */
	public int countAll();

}