/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link BatchEngineImportReportEntryLocalService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportReportEntryLocalService
 * @generated
 */
public class BatchEngineImportReportEntryLocalServiceWrapper
	implements BatchEngineImportReportEntryLocalService,
			   ServiceWrapper<BatchEngineImportReportEntryLocalService> {

	public BatchEngineImportReportEntryLocalServiceWrapper() {
		this(null);
	}

	public BatchEngineImportReportEntryLocalServiceWrapper(
		BatchEngineImportReportEntryLocalService
			batchEngineImportReportEntryLocalService) {

		_batchEngineImportReportEntryLocalService =
			batchEngineImportReportEntryLocalService;
	}

	/**
	 * Adds the batch engine import report entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportReportEntry the batch engine import report entry
	 * @return the batch engine import report entry that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
		addBatchEngineImportReportEntry(
			com.liferay.batch.engine.model.BatchEngineImportReportEntry
				batchEngineImportReportEntry) {

		return _batchEngineImportReportEntryLocalService.
			addBatchEngineImportReportEntry(batchEngineImportReportEntry);
	}

	/**
	 * Creates a new batch engine import report entry with the primary key. Does not add the batch engine import report entry to the database.
	 *
	 * @param batchEngineImportReportEntryId the primary key for the new batch engine import report entry
	 * @return the new batch engine import report entry
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
		createBatchEngineImportReportEntry(
			long batchEngineImportReportEntryId) {

		return _batchEngineImportReportEntryLocalService.
			createBatchEngineImportReportEntry(batchEngineImportReportEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportReportEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the batch engine import report entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportReportEntry the batch engine import report entry
	 * @return the batch engine import report entry that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
		deleteBatchEngineImportReportEntry(
			com.liferay.batch.engine.model.BatchEngineImportReportEntry
				batchEngineImportReportEntry) {

		return _batchEngineImportReportEntryLocalService.
			deleteBatchEngineImportReportEntry(batchEngineImportReportEntry);
	}

	/**
	 * Deletes the batch engine import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry that was removed
	 * @throws PortalException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
			deleteBatchEngineImportReportEntry(
				long batchEngineImportReportEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportReportEntryLocalService.
			deleteBatchEngineImportReportEntry(batchEngineImportReportEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportReportEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _batchEngineImportReportEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _batchEngineImportReportEntryLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchEngineImportReportEntryLocalService.dynamicQuery();
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

		return _batchEngineImportReportEntryLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryModelImpl</code>.
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

		return _batchEngineImportReportEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryModelImpl</code>.
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

		return _batchEngineImportReportEntryLocalService.dynamicQuery(
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

		return _batchEngineImportReportEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _batchEngineImportReportEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
		fetchBatchEngineImportReportEntry(long batchEngineImportReportEntryId) {

		return _batchEngineImportReportEntryLocalService.
			fetchBatchEngineImportReportEntry(batchEngineImportReportEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchEngineImportReportEntryLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of batch engine import report entries
	 */
	@Override
	public java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportReportEntry>
			getBatchEngineImportReportEntries(int start, int end) {

		return _batchEngineImportReportEntryLocalService.
			getBatchEngineImportReportEntries(start, end);
	}

	/**
	 * Returns the number of batch engine import report entries.
	 *
	 * @return the number of batch engine import report entries
	 */
	@Override
	public int getBatchEngineImportReportEntriesCount() {
		return _batchEngineImportReportEntryLocalService.
			getBatchEngineImportReportEntriesCount();
	}

	/**
	 * Returns the batch engine import report entry with the primary key.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry
	 * @throws PortalException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
			getBatchEngineImportReportEntry(long batchEngineImportReportEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportReportEntryLocalService.
			getBatchEngineImportReportEntry(batchEngineImportReportEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchEngineImportReportEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineImportReportEntryLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineImportReportEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the batch engine import report entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchEngineImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchEngineImportReportEntry the batch engine import report entry
	 * @return the batch engine import report entry that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineImportReportEntry
		updateBatchEngineImportReportEntry(
			com.liferay.batch.engine.model.BatchEngineImportReportEntry
				batchEngineImportReportEntry) {

		return _batchEngineImportReportEntryLocalService.
			updateBatchEngineImportReportEntry(batchEngineImportReportEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _batchEngineImportReportEntryLocalService.getBasePersistence();
	}

	@Override
	public BatchEngineImportReportEntryLocalService getWrappedService() {
		return _batchEngineImportReportEntryLocalService;
	}

	@Override
	public void setWrappedService(
		BatchEngineImportReportEntryLocalService
			batchEngineImportReportEntryLocalService) {

		_batchEngineImportReportEntryLocalService =
			batchEngineImportReportEntryLocalService;
	}

	private BatchEngineImportReportEntryLocalService
		_batchEngineImportReportEntryLocalService;

}