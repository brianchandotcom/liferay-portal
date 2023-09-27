/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for DDMFormInstanceRecord. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordLocalService
 * @generated
 */
public class DDMFormInstanceRecordLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static DDMFormInstanceRecord addDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return getService().addDDMFormInstanceRecord(ddmFormInstanceRecord);
	}

	public static DDMFormInstanceRecord addFormInstanceRecord(
			long userId, long groupId, long ddmFormInstanceId,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormInstanceRecord(
			userId, groupId, ddmFormInstanceId, ddmFormValues, serviceContext);
	}

	/**
	 * Creates a new ddm form instance record with the primary key. Does not add the ddm form instance record to the database.
	 *
	 * @param formInstanceRecordId the primary key for the new ddm form instance record
	 * @return the new ddm form instance record
	 */
	public static DDMFormInstanceRecord createDDMFormInstanceRecord(
		long formInstanceRecordId) {

		return getService().createDDMFormInstanceRecord(formInstanceRecordId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static DDMFormInstanceRecord deleteDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return getService().deleteDDMFormInstanceRecord(ddmFormInstanceRecord);
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
	public static DDMFormInstanceRecord deleteDDMFormInstanceRecord(
			long formInstanceRecordId)
		throws PortalException {

		return getService().deleteDDMFormInstanceRecord(formInstanceRecordId);
	}

	public static DDMFormInstanceRecord deleteFormInstanceRecord(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		return getService().deleteFormInstanceRecord(ddmFormInstanceRecord);
	}

	public static DDMFormInstanceRecord deleteFormInstanceRecord(
			long ddmFormInstanceRecordId)
		throws PortalException {

		return getService().deleteFormInstanceRecord(ddmFormInstanceRecordId);
	}

	public static void deleteFormInstanceRecords(long ddmFormInstanceId)
		throws PortalException {

		getService().deleteFormInstanceRecords(ddmFormInstanceId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static DDMFormInstanceRecord fetchDDMFormInstanceRecord(
		long formInstanceRecordId) {

		return getService().fetchDDMFormInstanceRecord(formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record matching the UUID and group.
	 *
	 * @param uuid the ddm form instance record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord
		fetchDDMFormInstanceRecordByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchDDMFormInstanceRecordByUuidAndGroupId(
			uuid, groupId);
	}

	public static DDMFormInstanceRecord fetchFormInstanceRecord(
		long ddmFormInstanceRecordId) {

		return getService().fetchFormInstanceRecord(ddmFormInstanceRecordId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance record with the primary key.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws PortalException if a ddm form instance record with the primary key could not be found
	 */
	public static DDMFormInstanceRecord getDDMFormInstanceRecord(
			long formInstanceRecordId)
		throws PortalException {

		return getService().getDDMFormInstanceRecord(formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record matching the UUID and group.
	 *
	 * @param uuid the ddm form instance record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance record
	 * @throws PortalException if a matching ddm form instance record could not be found
	 */
	public static DDMFormInstanceRecord
			getDDMFormInstanceRecordByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return getService().getDDMFormInstanceRecordByUuidAndGroupId(
			uuid, groupId);
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
	public static List<DDMFormInstanceRecord> getDDMFormInstanceRecords(
		int start, int end) {

		return getService().getDDMFormInstanceRecords(start, end);
	}

	/**
	 * Returns all the ddm form instance records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instance records
	 * @param companyId the primary key of the company
	 * @return the matching ddm form instance records, or an empty list if no matches were found
	 */
	public static List<DDMFormInstanceRecord>
		getDDMFormInstanceRecordsByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getDDMFormInstanceRecordsByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<DDMFormInstanceRecord>
		getDDMFormInstanceRecordsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getService().getDDMFormInstanceRecordsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddm form instance records.
	 *
	 * @return the number of ddm form instance records
	 */
	public static int getDDMFormInstanceRecordsCount() {
		return getService().getDDMFormInstanceRecordsCount();
	}

	public static com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues(
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				long storageId, String storageType)
		throws com.liferay.dynamic.data.mapping.exception.StorageException {

		return getService().getDDMFormValues(ddmForm, storageId, storageType);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getDDMFormValues(DDMForm, long, String)}
	 */
	@Deprecated
	public static com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues(
				long storageId,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm)
		throws com.liferay.dynamic.data.mapping.exception.StorageException {

		return getService().getDDMFormValues(storageId, ddmForm);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static DDMFormInstanceRecord getFormInstanceRecord(
			long formInstanceRecordId)
		throws PortalException {

		return getService().getFormInstanceRecord(formInstanceRecordId);
	}

	public static List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId) {

		return getService().getFormInstanceRecords(ddmFormInstanceId);
	}

	public static List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getService().getFormInstanceRecords(
			ddmFormInstanceId, status, start, end, orderByComparator);
	}

	public static List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, long userId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return getService().getFormInstanceRecords(
			ddmFormInstanceId, userId, start, end, orderByComparator);
	}

	public static int getFormInstanceRecordsCount(long ddmFormInstanceId) {
		return getService().getFormInstanceRecordsCount(ddmFormInstanceId);
	}

	public static int getFormInstanceRecordsCount(
		long ddmFormInstanceId, int status) {

		return getService().getFormInstanceRecordsCount(
			ddmFormInstanceId, status);
	}

	public static int getFormInstanceRecordsCount(
		long ddmFormInstanceId, long userId) {

		return getService().getFormInstanceRecordsCount(
			ddmFormInstanceId, userId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void revertFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().revertFormInstanceRecord(
			userId, ddmFormInstanceRecordId, version, serviceContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<DDMFormInstanceRecord> searchFormInstanceRecords(
				long formInstanceId, String[] notEmptyFields, int status,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchFormInstanceRecords(
			formInstanceId, notEmptyFields, status, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<DDMFormInstanceRecord> searchFormInstanceRecords(
			com.liferay.portal.kernel.search.SearchContext searchContext) {

		return getService().searchFormInstanceRecords(searchContext);
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
	public static DDMFormInstanceRecord updateDDMFormInstanceRecord(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return getService().updateDDMFormInstanceRecord(ddmFormInstanceRecord);
	}

	public static DDMFormInstanceRecord updateFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, boolean majorVersion,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFormInstanceRecord(
			userId, ddmFormInstanceRecordId, majorVersion, ddmFormValues,
			serviceContext);
	}

	public static DDMFormInstanceRecord updateStatus(
			long userId, long recordVersionId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateStatus(
			userId, recordVersionId, status, serviceContext);
	}

	public static DDMFormInstanceRecordLocalService getService() {
		return _service;
	}

	public static void setService(DDMFormInstanceRecordLocalService service) {
		_service = service;
	}

	private static volatile DDMFormInstanceRecordLocalService _service;

}