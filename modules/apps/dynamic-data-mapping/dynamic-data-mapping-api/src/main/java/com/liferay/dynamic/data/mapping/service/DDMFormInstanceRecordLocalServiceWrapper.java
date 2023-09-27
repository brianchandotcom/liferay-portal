/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link DDMFormInstanceRecordLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordLocalService
 * @generated
 */
public class DDMFormInstanceRecordLocalServiceWrapper
	implements DDMFormInstanceRecordLocalService,
			   ServiceWrapper<DDMFormInstanceRecordLocalService> {

	public DDMFormInstanceRecordLocalServiceWrapper() {
		this(null);
	}

	public DDMFormInstanceRecordLocalServiceWrapper(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	/**
	 * Adds the ddm form instance record to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFormInstanceRecordLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 * @return the ddm form instance record that was added
	 */
	@Override
	public DDMFormInstanceRecord addDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return _ddmFormInstanceRecordLocalService.addDDMFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	@Override
	public DDMFormInstanceRecord addFormInstanceRecord(
			long userId, long groupId, long ddmFormInstanceId,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.addFormInstanceRecord(
			userId, groupId, ddmFormInstanceId, ddmFormValues, serviceContext);
	}

	/**
	 * Creates a new ddm form instance record with the primary key. Does not add the ddm form instance record to the database.
	 *
	 * @param formInstanceRecordId the primary key for the new ddm form instance record
	 * @return the new ddm form instance record
	 */
	@Override
	public DDMFormInstanceRecord createDDMFormInstanceRecord(
		long formInstanceRecordId) {

		return _ddmFormInstanceRecordLocalService.createDDMFormInstanceRecord(
			formInstanceRecordId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the ddm form instance record from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFormInstanceRecordLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 * @return the ddm form instance record that was removed
	 */
	@Override
	public DDMFormInstanceRecord deleteDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return _ddmFormInstanceRecordLocalService.deleteDDMFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	/**
	 * Deletes the ddm form instance record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFormInstanceRecordLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record that was removed
	 * @throws PortalException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord deleteDDMFormInstanceRecord(
			long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.deleteDDMFormInstanceRecord(
			formInstanceRecordId);
	}

	@Override
	public DDMFormInstanceRecord deleteFormInstanceRecord(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	@Override
	public DDMFormInstanceRecord deleteFormInstanceRecord(
			long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
			ddmFormInstanceRecordId);
	}

	@Override
	public void deleteFormInstanceRecords(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceRecordLocalService.deleteFormInstanceRecords(
			ddmFormInstanceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ddmFormInstanceRecordLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _ddmFormInstanceRecordLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmFormInstanceRecordLocalService.dynamicQuery();
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

		return _ddmFormInstanceRecordLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl</code>.
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

		return _ddmFormInstanceRecordLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl</code>.
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

		return _ddmFormInstanceRecordLocalService.dynamicQuery(
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

		return _ddmFormInstanceRecordLocalService.dynamicQueryCount(
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

		return _ddmFormInstanceRecordLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public DDMFormInstanceRecord fetchDDMFormInstanceRecord(
		long formInstanceRecordId) {

		return _ddmFormInstanceRecordLocalService.fetchDDMFormInstanceRecord(
			formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record matching the UUID and group.
	 *
	 * @param uuid the ddm form instance record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchDDMFormInstanceRecordByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmFormInstanceRecordLocalService.
			fetchDDMFormInstanceRecordByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public DDMFormInstanceRecord fetchFormInstanceRecord(
		long ddmFormInstanceRecordId) {

		return _ddmFormInstanceRecordLocalService.fetchFormInstanceRecord(
			ddmFormInstanceRecordId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddmFormInstanceRecordLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance record with the primary key.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws PortalException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord getDDMFormInstanceRecord(
			long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.getDDMFormInstanceRecord(
			formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record matching the UUID and group.
	 *
	 * @param uuid the ddm form instance record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance record
	 * @throws PortalException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord getDDMFormInstanceRecordByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.
			getDDMFormInstanceRecordByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of ddm form instance records
	 */
	@Override
	public java.util.List<DDMFormInstanceRecord> getDDMFormInstanceRecords(
		int start, int end) {

		return _ddmFormInstanceRecordLocalService.getDDMFormInstanceRecords(
			start, end);
	}

	/**
	 * Returns all the ddm form instance records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instance records
	 * @param companyId the primary key of the company
	 * @return the matching ddm form instance records, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<DDMFormInstanceRecord>
		getDDMFormInstanceRecordsByUuidAndCompanyId(
			String uuid, long companyId) {

		return _ddmFormInstanceRecordLocalService.
			getDDMFormInstanceRecordsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of ddm form instance records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instance records
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm form instance records, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<DDMFormInstanceRecord>
		getDDMFormInstanceRecordsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<DDMFormInstanceRecord> orderByComparator) {

		return _ddmFormInstanceRecordLocalService.
			getDDMFormInstanceRecordsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddm form instance records.
	 *
	 * @return the number of ddm form instance records
	 */
	@Override
	public int getDDMFormInstanceRecordsCount() {
		return _ddmFormInstanceRecordLocalService.
			getDDMFormInstanceRecordsCount();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues(
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				long storageId, String storageType)
		throws com.liferay.dynamic.data.mapping.exception.StorageException {

		return _ddmFormInstanceRecordLocalService.getDDMFormValues(
			ddmForm, storageId, storageType);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getDDMFormValues(DDMForm, long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues(
				long storageId,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm)
		throws com.liferay.dynamic.data.mapping.exception.StorageException {

		return _ddmFormInstanceRecordLocalService.getDDMFormValues(
			storageId, ddmForm);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _ddmFormInstanceRecordLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public DDMFormInstanceRecord getFormInstanceRecord(
			long formInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.getFormInstanceRecord(
			formInstanceRecordId);
	}

	@Override
	public java.util.List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId) {

		return _ddmFormInstanceRecordLocalService.getFormInstanceRecords(
			ddmFormInstanceId);
	}

	@Override
	public java.util.List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceRecord>
			orderByComparator) {

		return _ddmFormInstanceRecordLocalService.getFormInstanceRecords(
			ddmFormInstanceId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceRecord>
			orderByComparator) {

		return _ddmFormInstanceRecordLocalService.getFormInstanceRecords(
			ddmFormInstanceId, userId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceRecordsCount(long ddmFormInstanceId) {
		return _ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
			ddmFormInstanceId);
	}

	@Override
	public int getFormInstanceRecordsCount(long ddmFormInstanceId, int status) {
		return _ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
			ddmFormInstanceId, status);
	}

	@Override
	public int getFormInstanceRecordsCount(
		long ddmFormInstanceId, long userId) {

		return _ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
			ddmFormInstanceId, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddmFormInstanceRecordLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmFormInstanceRecordLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public void revertFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceRecordLocalService.revertFormInstanceRecord(
			userId, ddmFormInstanceRecordId, version, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<DDMFormInstanceRecord> searchFormInstanceRecords(
				long formInstanceId, String[] notEmptyFields, int status,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
			formInstanceId, notEmptyFields, status, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<DDMFormInstanceRecord> searchFormInstanceRecords(
			com.liferay.portal.kernel.search.SearchContext searchContext) {

		return _ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
			searchContext);
	}

	/**
	 * Updates the ddm form instance record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFormInstanceRecordLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 * @return the ddm form instance record that was updated
	 */
	@Override
	public DDMFormInstanceRecord updateDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return _ddmFormInstanceRecordLocalService.updateDDMFormInstanceRecord(
			ddmFormInstanceRecord);
	}

	@Override
	public DDMFormInstanceRecord updateFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, boolean majorVersion,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
			userId, ddmFormInstanceRecordId, majorVersion, ddmFormValues,
			serviceContext);
	}

	@Override
	public DDMFormInstanceRecord updateStatus(
			long userId, long recordVersionId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordLocalService.updateStatus(
			userId, recordVersionId, status, serviceContext);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFormInstanceRecordLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<DDMFormInstanceRecord> getCTPersistence() {
		return _ddmFormInstanceRecordLocalService.getCTPersistence();
	}

	@Override
	public Class<DDMFormInstanceRecord> getModelClass() {
		return _ddmFormInstanceRecordLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<DDMFormInstanceRecord>, R, E>
				updateUnsafeFunction)
		throws E {

		return _ddmFormInstanceRecordLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public DDMFormInstanceRecordLocalService getWrappedService() {
		return _ddmFormInstanceRecordLocalService;
	}

	@Override
	public void setWrappedService(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}