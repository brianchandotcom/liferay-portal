/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ImportReportEntryLocalService}.
 *
 * @author Carlos Correa
 * @see ImportReportEntryLocalService
 * @generated
 */
public class ImportReportEntryLocalServiceWrapper
	implements ImportReportEntryLocalService,
			   ServiceWrapper<ImportReportEntryLocalService> {

	public ImportReportEntryLocalServiceWrapper() {
		this(null);
	}

	public ImportReportEntryLocalServiceWrapper(
		ImportReportEntryLocalService importReportEntryLocalService) {

		_importReportEntryLocalService = importReportEntryLocalService;
	}

	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		addErrorImportReportEntry(
			long companyId, long groupId, long classNameId, long classPK,
			long entityClassNameId, String entityExternalReferenceCode,
			String error, String errorStacktrace) {

		return _importReportEntryLocalService.addErrorImportReportEntry(
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
	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		addImportReportEntry(
			com.liferay.exportimport.report.model.ImportReportEntry
				importReportEntry) {

		return _importReportEntryLocalService.addImportReportEntry(
			importReportEntry);
	}

	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		addIncompleteImportReportEntry(
			long companyId, long groupId, long classNameId, long classPK,
			long entityClassNameId, String entityExternalReferenceCode) {

		return _importReportEntryLocalService.addIncompleteImportReportEntry(
			companyId, groupId, classNameId, classPK, entityClassNameId,
			entityExternalReferenceCode);
	}

	/**
	 * Creates a new import report entry with the primary key. Does not add the import report entry to the database.
	 *
	 * @param importReportEntryId the primary key for the new import report entry
	 * @return the new import report entry
	 */
	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		createImportReportEntry(long importReportEntryId) {

		return _importReportEntryLocalService.createImportReportEntry(
			importReportEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _importReportEntryLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		deleteImportReportEntry(
			com.liferay.exportimport.report.model.ImportReportEntry
				importReportEntry) {

		return _importReportEntryLocalService.deleteImportReportEntry(
			importReportEntry);
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
	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
			deleteImportReportEntry(long importReportEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _importReportEntryLocalService.deleteImportReportEntry(
			importReportEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _importReportEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _importReportEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _importReportEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _importReportEntryLocalService.dynamicQuery();
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

		return _importReportEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _importReportEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _importReportEntryLocalService.dynamicQuery(
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

		return _importReportEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _importReportEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		fetchImportReportEntry(long importReportEntryId) {

		return _importReportEntryLocalService.fetchImportReportEntry(
			importReportEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _importReportEntryLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.exportimport.report.model.ImportReportEntry>
			getImportReportEntries(int start, int end) {

		return _importReportEntryLocalService.getImportReportEntries(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.exportimport.report.model.ImportReportEntry>
			getImportReportEntries(
				long companyId, long classNameId, long classPK) {

		return _importReportEntryLocalService.getImportReportEntries(
			companyId, classNameId, classPK);
	}

	/**
	 * Returns the number of import report entries.
	 *
	 * @return the number of import report entries
	 */
	@Override
	public int getImportReportEntriesCount() {
		return _importReportEntryLocalService.getImportReportEntriesCount();
	}

	/**
	 * Returns the import report entry with the primary key.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry
	 * @throws PortalException if a import report entry with the primary key could not be found
	 */
	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
			getImportReportEntry(long importReportEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _importReportEntryLocalService.getImportReportEntry(
			importReportEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _importReportEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _importReportEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _importReportEntryLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.exportimport.report.model.ImportReportEntry
		updateImportReportEntry(
			com.liferay.exportimport.report.model.ImportReportEntry
				importReportEntry) {

		return _importReportEntryLocalService.updateImportReportEntry(
			importReportEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _importReportEntryLocalService.getBasePersistence();
	}

	@Override
	public ImportReportEntryLocalService getWrappedService() {
		return _importReportEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ImportReportEntryLocalService importReportEntryLocalService) {

		_importReportEntryLocalService = importReportEntryLocalService;
	}

	private ImportReportEntryLocalService _importReportEntryLocalService;

}