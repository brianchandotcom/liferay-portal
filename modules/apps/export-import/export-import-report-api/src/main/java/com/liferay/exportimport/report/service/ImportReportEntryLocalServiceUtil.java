/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service;

import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ImportReportEntry. This utility wraps
 * <code>com.liferay.exportimport.report.service.impl.ImportReportEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Carlos Correa
 * @see ImportReportEntryLocalService
 * @generated
 */
public class ImportReportEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.exportimport.report.service.impl.ImportReportEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ImportReportEntry addErrorImportReportEntry(
		long companyId, long groupId, long classNameId, long classPK,
		long entityClassNameId, String entityExternalReferenceCode,
		String error, String errorStacktrace) {

		return getService().addErrorImportReportEntry(
			companyId, groupId, classNameId, classPK, entityClassNameId,
			entityExternalReferenceCode, error, errorStacktrace);
	}

	/**
	 * Adds the import report entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param importReportEntry the import report entry
	 * @return the import report entry that was added
	 */
	public static ImportReportEntry addImportReportEntry(
		ImportReportEntry importReportEntry) {

		return getService().addImportReportEntry(importReportEntry);
	}

	public static ImportReportEntry addIncompleteImportReportEntry(
		long companyId, long groupId, long classNameId, long classPK,
		long entityClassNameId, String entityExternalReferenceCode) {

		return getService().addIncompleteImportReportEntry(
			companyId, groupId, classNameId, classPK, entityClassNameId,
			entityExternalReferenceCode);
	}

	/**
	 * Creates a new import report entry with the primary key. Does not add the import report entry to the database.
	 *
	 * @param importReportEntryId the primary key for the new import report entry
	 * @return the new import report entry
	 */
	public static ImportReportEntry createImportReportEntry(
		long importReportEntryId) {

		return getService().createImportReportEntry(importReportEntryId);
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
	 * Deletes the import report entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param importReportEntry the import report entry
	 * @return the import report entry that was removed
	 */
	public static ImportReportEntry deleteImportReportEntry(
		ImportReportEntry importReportEntry) {

		return getService().deleteImportReportEntry(importReportEntry);
	}

	/**
	 * Deletes the import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry that was removed
	 * @throws PortalException if a import report entry with the primary key could not be found
	 */
	public static ImportReportEntry deleteImportReportEntry(
			long importReportEntryId)
		throws PortalException {

		return getService().deleteImportReportEntry(importReportEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.exportimport.report.model.impl.ImportReportEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.exportimport.report.model.impl.ImportReportEntryModelImpl</code>.
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

	public static ImportReportEntry fetchImportReportEntry(
		long importReportEntryId) {

		return getService().fetchImportReportEntry(importReportEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.exportimport.report.model.impl.ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of import report entries
	 */
	public static List<ImportReportEntry> getImportReportEntries(
		int start, int end) {

		return getService().getImportReportEntries(start, end);
	}

	public static List<ImportReportEntry> getImportReportEntries(
		long companyId, long classNameId, long classPK) {

		return getService().getImportReportEntries(
			companyId, classNameId, classPK);
	}

	/**
	 * Returns the number of import report entries.
	 *
	 * @return the number of import report entries
	 */
	public static int getImportReportEntriesCount() {
		return getService().getImportReportEntriesCount();
	}

	/**
	 * Returns the import report entry with the primary key.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry
	 * @throws PortalException if a import report entry with the primary key could not be found
	 */
	public static ImportReportEntry getImportReportEntry(
			long importReportEntryId)
		throws PortalException {

		return getService().getImportReportEntry(importReportEntryId);
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

	/**
	 * Updates the import report entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ImportReportEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param importReportEntry the import report entry
	 * @return the import report entry that was updated
	 */
	public static ImportReportEntry updateImportReportEntry(
		ImportReportEntry importReportEntry) {

		return getService().updateImportReportEntry(importReportEntry);
	}

	public static ImportReportEntryLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<ImportReportEntryLocalService>
		_serviceSnapshot = new Snapshot<>(
			ImportReportEntryLocalServiceUtil.class,
			ImportReportEntryLocalService.class);

}