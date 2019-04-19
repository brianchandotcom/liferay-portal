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

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link BatchFileImportLocalService}.
 *
 * @author Ivica Cardic
 * @see BatchFileImportLocalService
 * @generated
 */
@ProviderType
public class BatchFileImportLocalServiceWrapper
	implements BatchFileImportLocalService,
			   ServiceWrapper<BatchFileImportLocalService> {

	public BatchFileImportLocalServiceWrapper(
		BatchFileImportLocalService batchFileImportLocalService) {

		_batchFileImportLocalService = batchFileImportLocalService;
	}

	/**
	 * Adds the batch file import to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImport the batch file import
	 * @return the batch file import that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport addBatchFileImport(
		com.liferay.batch.engine.model.BatchFileImport batchFileImport) {

		return _batchFileImportLocalService.addBatchFileImport(batchFileImport);
	}

	@Override
	public com.liferay.batch.engine.model.BatchFileImport addBatchFileImport(
		long fileEntryId, String domainName, String version,
		com.liferay.batch.engine.BatchFileImportOperation
			batchFileImportOperation,
		String callbackURL, String columnNames,
		com.liferay.batch.engine.BatchStatus batchStatus) {

		return _batchFileImportLocalService.addBatchFileImport(
			fileEntryId, domainName, version, batchFileImportOperation,
			callbackURL, columnNames, batchStatus);
	}

	@Override
	public int countBatchFileImports(
		com.liferay.batch.engine.BatchStatus batchStatus) {

		return _batchFileImportLocalService.countBatchFileImports(batchStatus);
	}

	/**
	 * Creates a new batch file import with the primary key. Does not add the batch file import to the database.
	 *
	 * @param batchFileImportId the primary key for the new batch file import
	 * @return the new batch file import
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport createBatchFileImport(
		long batchFileImportId) {

		return _batchFileImportLocalService.createBatchFileImport(
			batchFileImportId);
	}

	/**
	 * Deletes the batch file import from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImport the batch file import
	 * @return the batch file import that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport deleteBatchFileImport(
		com.liferay.batch.engine.model.BatchFileImport batchFileImport) {

		return _batchFileImportLocalService.deleteBatchFileImport(
			batchFileImport);
	}

	/**
	 * Deletes the batch file import with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import that was removed
	 * @throws PortalException if a batch file import with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport deleteBatchFileImport(
			long batchFileImportId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchFileImportLocalService.deleteBatchFileImport(
			batchFileImportId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchFileImportLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchFileImportLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _batchFileImportLocalService.dynamicQuery(dynamicQuery);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _batchFileImportLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _batchFileImportLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _batchFileImportLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _batchFileImportLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchFileImport fetchBatchFileImport(
		long batchFileImportId) {

		return _batchFileImportLocalService.fetchBatchFileImport(
			batchFileImportId);
	}

	/**
	 * Returns the batch file import with the matching UUID and company.
	 *
	 * @param uuid the batch file import's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport
		fetchBatchFileImportByUuidAndCompanyId(String uuid, long companyId) {

		return _batchFileImportLocalService.
			fetchBatchFileImportByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchFileImportLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch file import with the primary key.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import
	 * @throws PortalException if a batch file import with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport getBatchFileImport(
			long batchFileImportId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchFileImportLocalService.getBatchFileImport(
			batchFileImportId);
	}

	/**
	 * Returns the batch file import with the matching UUID and company.
	 *
	 * @param uuid the batch file import's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch file import
	 * @throws PortalException if a matching batch file import could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport
			getBatchFileImportByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchFileImportLocalService.
			getBatchFileImportByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchFileImport>
		getBatchFileImports(com.liferay.batch.engine.BatchStatus batchStatus) {

		return _batchFileImportLocalService.getBatchFileImports(batchStatus);
	}

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
	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchFileImport>
		getBatchFileImports(int start, int end) {

		return _batchFileImportLocalService.getBatchFileImports(start, end);
	}

	@Override
	public com.liferay.batch.engine.model.BatchFileImport getBatchFileImports(
			long batchJobExecutionId)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return _batchFileImportLocalService.getBatchFileImports(
			batchJobExecutionId);
	}

	/**
	 * Returns the number of batch file imports.
	 *
	 * @return the number of batch file imports
	 */
	@Override
	public int getBatchFileImportsCount() {
		return _batchFileImportLocalService.getBatchFileImportsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _batchFileImportLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchFileImport>
		getFirstBatchFileImports(
			com.liferay.batch.engine.BatchStatus batchStatus, int size) {

		return _batchFileImportLocalService.getFirstBatchFileImports(
			batchStatus, size);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchFileImportLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchFileImportLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchFileImportLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch file import in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImport the batch file import
	 * @return the batch file import that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchFileImport updateBatchFileImport(
		com.liferay.batch.engine.model.BatchFileImport batchFileImport) {

		return _batchFileImportLocalService.updateBatchFileImport(
			batchFileImport);
	}

	@Override
	public BatchFileImportLocalService getWrappedService() {
		return _batchFileImportLocalService;
	}

	@Override
	public void setWrappedService(
		BatchFileImportLocalService batchFileImportLocalService) {

		_batchFileImportLocalService = batchFileImportLocalService;
	}

	private BatchFileImportLocalService _batchFileImportLocalService;

}