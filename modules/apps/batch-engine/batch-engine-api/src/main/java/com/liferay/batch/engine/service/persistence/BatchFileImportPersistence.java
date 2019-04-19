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

package com.liferay.batch.engine.service.persistence;

import com.liferay.batch.engine.exception.NoSuchFileImportException;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch file import service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchFileImportUtil
 * @generated
 */
@ProviderType
public interface BatchFileImportPersistence
	extends BasePersistence<BatchFileImport> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchFileImportUtil} to access the batch file import persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch file imports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid(String uuid);

	/**
	 * Returns a range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public BatchFileImport[] findByUuid_PrevAndNext(
			long batchFileImportId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Removes all the batch file imports where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of batch file imports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch file imports
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public BatchFileImport[] findByUuid_C_PrevAndNext(
			long batchFileImportId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Removes all the batch file imports where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch file imports
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or throws a <code>NoSuchFileImportException</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByBatchJobExecutionId(long batchJobExecutionId)
		throws NoSuchFileImportException;

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByBatchJobExecutionId(long batchJobExecutionId);

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByBatchJobExecutionId(
		long batchJobExecutionId, boolean retrieveFromCache);

	/**
	 * Removes the batch file import where batchJobExecutionId = &#63; from the database.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the batch file import that was removed
	 */
	public BatchFileImport removeByBatchJobExecutionId(long batchJobExecutionId)
		throws NoSuchFileImportException;

	/**
	 * Returns the number of batch file imports where batchJobExecutionId = &#63;.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the number of matching batch file imports
	 */
	public int countByBatchJobExecutionId(long batchJobExecutionId);

	/**
	 * Returns all the batch file imports where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByStatus(String status);

	/**
	 * Returns a range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByStatus(
		String status, int start, int end);

	/**
	 * Returns an ordered range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByStatus(
		String status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	public java.util.List<BatchFileImport> findByStatus(
		String status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByStatus_First(
			String status,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Returns the first batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByStatus_First(
		String status,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns the last batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public BatchFileImport findByStatus_Last(
			String status,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Returns the last batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public BatchFileImport fetchByStatus_Last(
		String status,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where status = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public BatchFileImport[] findByStatus_PrevAndNext(
			long batchFileImportId, String status,
			com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
				orderByComparator)
		throws NoSuchFileImportException;

	/**
	 * Removes all the batch file imports where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	public void removeByStatus(String status);

	/**
	 * Returns the number of batch file imports where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching batch file imports
	 */
	public int countByStatus(String status);

	/**
	 * Caches the batch file import in the entity cache if it is enabled.
	 *
	 * @param batchFileImport the batch file import
	 */
	public void cacheResult(BatchFileImport batchFileImport);

	/**
	 * Caches the batch file imports in the entity cache if it is enabled.
	 *
	 * @param batchFileImports the batch file imports
	 */
	public void cacheResult(java.util.List<BatchFileImport> batchFileImports);

	/**
	 * Creates a new batch file import with the primary key. Does not add the batch file import to the database.
	 *
	 * @param batchFileImportId the primary key for the new batch file import
	 * @return the new batch file import
	 */
	public BatchFileImport create(long batchFileImportId);

	/**
	 * Removes the batch file import with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import that was removed
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public BatchFileImport remove(long batchFileImportId)
		throws NoSuchFileImportException;

	public BatchFileImport updateImpl(BatchFileImport batchFileImport);

	/**
	 * Returns the batch file import with the primary key or throws a <code>NoSuchFileImportException</code> if it could not be found.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public BatchFileImport findByPrimaryKey(long batchFileImportId)
		throws NoSuchFileImportException;

	/**
	 * Returns the batch file import with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import, or <code>null</code> if a batch file import with the primary key could not be found
	 */
	public BatchFileImport fetchByPrimaryKey(long batchFileImportId);

	/**
	 * Returns all the batch file imports.
	 *
	 * @return the batch file imports
	 */
	public java.util.List<BatchFileImport> findAll();

	/**
	 * Returns a range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of batch file imports
	 */
	public java.util.List<BatchFileImport> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch file imports
	 */
	public java.util.List<BatchFileImport> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch file imports
	 */
	public java.util.List<BatchFileImport> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchFileImport>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the batch file imports from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch file imports.
	 *
	 * @return the number of batch file imports
	 */
	public int countAll();

}