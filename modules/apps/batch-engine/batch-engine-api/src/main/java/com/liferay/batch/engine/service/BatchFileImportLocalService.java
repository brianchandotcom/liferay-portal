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

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.exception.NoSuchFileImportException;
import com.liferay.batch.engine.model.BatchFileImport;
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

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for BatchFileImport. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Ivica Cardic
 * @see BatchFileImportLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface BatchFileImportLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchFileImportLocalServiceUtil} to access the batch file import local service. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchFileImportLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the batch file import to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImport the batch file import
	 * @return the batch file import that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BatchFileImport addBatchFileImport(BatchFileImport batchFileImport);

	public BatchFileImport addBatchFileImport(
		long fileEntryId, String domainName, String version,
		BatchFileImportOperation batchFileImportOperation, String callbackURL,
		String columnNames, BatchStatus batchStatus);

	public int countBatchFileImports(BatchStatus batchStatus);

	/**
	 * Creates a new batch file import with the primary key. Does not add the batch file import to the database.
	 *
	 * @param batchFileImportId the primary key for the new batch file import
	 * @return the new batch file import
	 */
	@Transactional(enabled = false)
	public BatchFileImport createBatchFileImport(long batchFileImportId);

	/**
	 * Deletes the batch file import from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImport the batch file import
	 * @return the batch file import that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public BatchFileImport deleteBatchFileImport(
		BatchFileImport batchFileImport);

	/**
	 * Deletes the batch file import with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import that was removed
	 * @throws PortalException if a batch file import with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public BatchFileImport deleteBatchFileImport(long batchFileImportId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public BatchFileImport fetchBatchFileImport(long batchFileImportId);

	/**
	 * Returns the batch file import with the matching UUID and company.
	 *
	 * @param uuid the batch file import's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchFileImport fetchBatchFileImportByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the batch file import with the primary key.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import
	 * @throws PortalException if a batch file import with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchFileImport getBatchFileImport(long batchFileImportId)
		throws PortalException;

	/**
	 * Returns the batch file import with the matching UUID and company.
	 *
	 * @param uuid the batch file import's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch file import
	 * @throws PortalException if a matching batch file import could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchFileImport getBatchFileImportByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchFileImport> getBatchFileImports(BatchStatus batchStatus);

	/**
	 * Returns a range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of batch file imports
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchFileImport> getBatchFileImports(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BatchFileImport getBatchFileImports(long batchJobExecutionId)
		throws NoSuchFileImportException;

	/**
	 * Returns the number of batch file imports.
	 *
	 * @return the number of batch file imports
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBatchFileImportsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BatchFileImport> getFirstBatchFileImports(
		BatchStatus batchStatus, int size);

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
	 * Updates the batch file import in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImport the batch file import
	 * @return the batch file import that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BatchFileImport updateBatchFileImport(
		BatchFileImport batchFileImport);

}