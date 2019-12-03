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

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.model.BatchEngineImportTaskContentBlobModel;
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

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for BatchEngineImportTask. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface BatchEngineImportTaskLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchEngineImportTaskLocalServiceUtil} to access the batch engine import task local service. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineImportTaskLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the batch engine import task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 * @return the batch engine import task that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BatchEngineImportTask addBatchEngineImportTask(
		BatchEngineImportTask batchEngineImportTask);

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BatchEngineImportTask addBatchEngineImportTask(
		long companyId, long userId, long batchSize, String callbackURL,
		String className, byte[] content, String contentType,
		String executeStatus, Map<String, String> fieldNameMappingMap,
		String operation, Map<String, Serializable> parameters, String version);

	/**
	 * Creates a new batch engine import task with the primary key. Does not add the batch engine import task to the database.
	 *
	 * @param batchEngineImportTaskId the primary key for the new batch engine import task
	 * @return the new batch engine import task
	 */
	@Transactional(enabled = false)
	public BatchEngineImportTask createBatchEngineImportTask(
		long batchEngineImportTaskId);

	/**
	 * Deletes the batch engine import task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 * @return the batch engine import task that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public BatchEngineImportTask deleteBatchEngineImportTask(
		BatchEngineImportTask batchEngineImportTask);

	/**
	 * Deletes the batch engine import task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task that was removed
	 * @throws PortalException if a batch engine import task with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public BatchEngineImportTask deleteBatchEngineImportTask(
			long batchEngineImportTaskId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl</code>.
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
	public BatchEngineImportTask fetchBatchEngineImportTask(
		long batchEngineImportTaskId);

	/**
	 * Returns the batch engine import task with the matching UUID and company.
	 *
	 * @param uuid the batch engine import task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchEngineImportTask fetchBatchEngineImportTaskByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the batch engine import task with the primary key.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task
	 * @throws PortalException if a batch engine import task with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchEngineImportTask getBatchEngineImportTask(
			long batchEngineImportTaskId)
		throws PortalException;

	/**
	 * Returns the batch engine import task with the matching UUID and company.
	 *
	 * @param uuid the batch engine import task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine import task
	 * @throws PortalException if a matching batch engine import task could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchEngineImportTask getBatchEngineImportTaskByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of batch engine import tasks
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchEngineImportTask> getBatchEngineImportTasks(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchEngineImportTask> getBatchEngineImportTasks(
		String executeStatus);

	/**
	 * Returns the number of batch engine import tasks.
	 *
	 * @return the number of batch engine import tasks
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBatchEngineImportTasksCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchEngineImportTaskContentBlobModel getContentBlobModel(
		Serializable primaryKey);

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

	@Transactional(readOnly = true)
	public InputStream openContentInputStream(long batchEngineImportTaskId);

	/**
	 * Updates the batch engine import task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 * @return the batch engine import task that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BatchEngineImportTask updateBatchEngineImportTask(
		BatchEngineImportTask batchEngineImportTask);

}