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

package com.liferay.batch.engine.service;

import com.liferay.batch.engine.exception.JobExecutionAlreadyRunningException;
import com.liferay.batch.engine.exception.JobInstanceAlreadyCompleteException;
import com.liferay.batch.engine.exception.JobRestartException;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for BatchJobExecution. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Ivica Cardic
 * @see BatchJobExecutionLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface BatchJobExecutionLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchJobExecutionLocalServiceUtil} to access the batch job execution local service. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchJobExecutionLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the batch job execution to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecution the batch job execution
	 * @return the batch job execution that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BatchJobExecution addBatchJobExecution(
		BatchJobExecution batchJobExecution);

	public BatchJobExecution addBatchJobExecution(
			String jobName, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException;

	/**
	 * Creates a new batch job execution with the primary key. Does not add the batch job execution to the database.
	 *
	 * @param batchJobExecutionId the primary key for the new batch job execution
	 * @return the new batch job execution
	 */
	@Transactional(enabled = false)
	public BatchJobExecution createBatchJobExecution(long batchJobExecutionId);

	/**
	 * Deletes the batch job execution from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecution the batch job execution
	 * @return the batch job execution that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public BatchJobExecution deleteBatchJobExecution(
		BatchJobExecution batchJobExecution);

	/**
	 * Deletes the batch job execution with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution that was removed
	 * @throws PortalException if a batch job execution with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public BatchJobExecution deleteBatchJobExecution(long batchJobExecutionId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchJobExecution fetchBatchJobExecution(long batchJobExecutionId);

	/**
	 * Returns the batch job execution with the matching UUID and company.
	 *
	 * @param uuid the batch job execution's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchJobExecution fetchBatchJobExecutionByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchJobExecution fetchLastBatchJobExecution(
		long batchJobInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchJobExecution fetchLastBatchJobExecution(
		String batchJobName, UnicodeProperties jobSettingsProperties);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the batch job execution with the primary key.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution
	 * @throws PortalException if a batch job execution with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchJobExecution getBatchJobExecution(long batchJobExecutionId)
		throws PortalException;

	/**
	 * Returns the batch job execution with the matching UUID and company.
	 *
	 * @param uuid the batch job execution's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch job execution
	 * @throws PortalException if a matching batch job execution could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchJobExecution getBatchJobExecutionByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of batch job executions
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchJobExecution> getBatchJobExecutions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchJobExecution> getBatchJobExecutions(String status);

	/**
	 * Returns the number of batch job executions.
	 *
	 * @return the number of batch job executions
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBatchJobExecutionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the batch job execution in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecution the batch job execution
	 * @return the batch job execution that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BatchJobExecution updateBatchJobExecution(
		BatchJobExecution batchJobExecution);

}