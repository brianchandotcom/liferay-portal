/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link LaunchEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntryLocalService
 * @generated
 */
public class LaunchEntryLocalServiceWrapper
	implements LaunchEntryLocalService,
			   ServiceWrapper<LaunchEntryLocalService> {

	public LaunchEntryLocalServiceWrapper() {
		this(null);
	}

	public LaunchEntryLocalServiceWrapper(
		LaunchEntryLocalService launchEntryLocalService) {

		_launchEntryLocalService = launchEntryLocalService;
	}

	/**
	 * Adds the launch entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntry the launch entry
	 * @return the launch entry that was added
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry addLaunchEntry(
		com.liferay.launch.model.LaunchEntry launchEntry) {

		return _launchEntryLocalService.addLaunchEntry(launchEntry);
	}

	@Override
	public com.liferay.launch.model.LaunchEntry addLaunchEntry(
			String externalReferenceCode, long userId, long launchSetId,
			long classNameId, long classPK, String classVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.addLaunchEntry(
			externalReferenceCode, userId, launchSetId, classNameId, classPK,
			classVersion);
	}

	/**
	 * Creates a new launch entry with the primary key. Does not add the launch entry to the database.
	 *
	 * @param launchEntryId the primary key for the new launch entry
	 * @return the new launch entry
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry createLaunchEntry(
		long launchEntryId) {

		return _launchEntryLocalService.createLaunchEntry(launchEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the launch entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntry the launch entry
	 * @return the launch entry that was removed
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry deleteLaunchEntry(
		com.liferay.launch.model.LaunchEntry launchEntry) {

		return _launchEntryLocalService.deleteLaunchEntry(launchEntry);
	}

	/**
	 * Deletes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws PortalException if a launch entry with the primary key could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry deleteLaunchEntry(
			long launchEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.deleteLaunchEntry(launchEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _launchEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _launchEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _launchEntryLocalService.dynamicQuery();
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

		return _launchEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchEntryModelImpl</code>.
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

		return _launchEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchEntryModelImpl</code>.
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

		return _launchEntryLocalService.dynamicQuery(
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

		return _launchEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _launchEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.launch.model.LaunchEntry fetchLaunchEntry(
		long launchEntryId) {

		return _launchEntryLocalService.fetchLaunchEntry(launchEntryId);
	}

	@Override
	public com.liferay.launch.model.LaunchEntry fetchLaunchEntry(
		long classNameId, long classPK, String classVersion) {

		return _launchEntryLocalService.fetchLaunchEntry(
			classNameId, classPK, classVersion);
	}

	@Override
	public com.liferay.launch.model.LaunchEntry
		fetchLaunchEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId) {

		return _launchEntryLocalService.fetchLaunchEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch entry with the matching UUID and company.
	 *
	 * @param uuid the launch entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry
		fetchLaunchEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _launchEntryLocalService.fetchLaunchEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _launchEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _launchEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _launchEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of launch entries
	 */
	@Override
	public java.util.List<com.liferay.launch.model.LaunchEntry>
		getLaunchEntries(int start, int end) {

		return _launchEntryLocalService.getLaunchEntries(start, end);
	}

	/**
	 * Returns the number of launch entries.
	 *
	 * @return the number of launch entries
	 */
	@Override
	public int getLaunchEntriesCount() {
		return _launchEntryLocalService.getLaunchEntriesCount();
	}

	/**
	 * Returns the launch entry with the primary key.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry
	 * @throws PortalException if a launch entry with the primary key could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry getLaunchEntry(
			long launchEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.getLaunchEntry(launchEntryId);
	}

	@Override
	public com.liferay.launch.model.LaunchEntry
			getLaunchEntryByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.getLaunchEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch entry with the matching UUID and company.
	 *
	 * @param uuid the launch entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch entry
	 * @throws PortalException if a matching launch entry could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry
			getLaunchEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.getLaunchEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _launchEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the launch entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntry the launch entry
	 * @return the launch entry that was updated
	 */
	@Override
	public com.liferay.launch.model.LaunchEntry updateLaunchEntry(
		com.liferay.launch.model.LaunchEntry launchEntry) {

		return _launchEntryLocalService.updateLaunchEntry(launchEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _launchEntryLocalService.getBasePersistence();
	}

	@Override
	public LaunchEntryLocalService getWrappedService() {
		return _launchEntryLocalService;
	}

	@Override
	public void setWrappedService(
		LaunchEntryLocalService launchEntryLocalService) {

		_launchEntryLocalService = launchEntryLocalService;
	}

	private LaunchEntryLocalService _launchEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:2034482999