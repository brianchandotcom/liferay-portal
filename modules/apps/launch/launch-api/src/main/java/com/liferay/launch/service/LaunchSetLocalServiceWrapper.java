/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link LaunchSetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchSetLocalService
 * @generated
 */
public class LaunchSetLocalServiceWrapper
	implements LaunchSetLocalService, ServiceWrapper<LaunchSetLocalService> {

	public LaunchSetLocalServiceWrapper() {
		this(null);
	}

	public LaunchSetLocalServiceWrapper(
		LaunchSetLocalService launchSetLocalService) {

		_launchSetLocalService = launchSetLocalService;
	}

	/**
	 * Adds the launch set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was added
	 */
	@Override
	public com.liferay.launch.model.LaunchSet addLaunchSet(
		com.liferay.launch.model.LaunchSet launchSet) {

		return _launchSetLocalService.addLaunchSet(launchSet);
	}

	@Override
	public com.liferay.launch.model.LaunchSet addLaunchSet(
			String externalReferenceCode, long userId, String description,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.addLaunchSet(
			externalReferenceCode, userId, description, name);
	}

	/**
	 * Creates a new launch set with the primary key. Does not add the launch set to the database.
	 *
	 * @param launchSetId the primary key for the new launch set
	 * @return the new launch set
	 */
	@Override
	public com.liferay.launch.model.LaunchSet createLaunchSet(
		long launchSetId) {

		return _launchSetLocalService.createLaunchSet(launchSetId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the launch set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was removed
	 */
	@Override
	public com.liferay.launch.model.LaunchSet deleteLaunchSet(
		com.liferay.launch.model.LaunchSet launchSet) {

		return _launchSetLocalService.deleteLaunchSet(launchSet);
	}

	/**
	 * Deletes the launch set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set that was removed
	 * @throws PortalException if a launch set with the primary key could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchSet deleteLaunchSet(long launchSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.deleteLaunchSet(launchSetId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _launchSetLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _launchSetLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _launchSetLocalService.dynamicQuery();
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

		return _launchSetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
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

		return _launchSetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
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

		return _launchSetLocalService.dynamicQuery(
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

		return _launchSetLocalService.dynamicQueryCount(dynamicQuery);
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

		return _launchSetLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.launch.model.LaunchSet fetchLaunchSet(long launchSetId) {
		return _launchSetLocalService.fetchLaunchSet(launchSetId);
	}

	@Override
	public com.liferay.launch.model.LaunchSet
		fetchLaunchSetByExternalReferenceCode(
			String externalReferenceCode, long companyId) {

		return _launchSetLocalService.fetchLaunchSetByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch set with the matching UUID and company.
	 *
	 * @param uuid the launch set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchSet fetchLaunchSetByUuidAndCompanyId(
		String uuid, long companyId) {

		return _launchSetLocalService.fetchLaunchSetByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _launchSetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _launchSetLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _launchSetLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the launch set with the primary key.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set
	 * @throws PortalException if a launch set with the primary key could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchSet getLaunchSet(long launchSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.getLaunchSet(launchSetId);
	}

	@Override
	public com.liferay.launch.model.LaunchSet
			getLaunchSetByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.getLaunchSetByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch set with the matching UUID and company.
	 *
	 * @param uuid the launch set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch set
	 * @throws PortalException if a matching launch set could not be found
	 */
	@Override
	public com.liferay.launch.model.LaunchSet getLaunchSetByUuidAndCompanyId(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.getLaunchSetByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the launch sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of launch sets
	 */
	@Override
	public java.util.List<com.liferay.launch.model.LaunchSet> getLaunchSets(
		int start, int end) {

		return _launchSetLocalService.getLaunchSets(start, end);
	}

	/**
	 * Returns the number of launch sets.
	 *
	 * @return the number of launch sets
	 */
	@Override
	public int getLaunchSetsCount() {
		return _launchSetLocalService.getLaunchSetsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _launchSetLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _launchSetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the launch set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was updated
	 */
	@Override
	public com.liferay.launch.model.LaunchSet updateLaunchSet(
		com.liferay.launch.model.LaunchSet launchSet) {

		return _launchSetLocalService.updateLaunchSet(launchSet);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _launchSetLocalService.getBasePersistence();
	}

	@Override
	public LaunchSetLocalService getWrappedService() {
		return _launchSetLocalService;
	}

	@Override
	public void setWrappedService(LaunchSetLocalService launchSetLocalService) {
		_launchSetLocalService = launchSetLocalService;
	}

	private LaunchSetLocalService _launchSetLocalService;

}
// SB-Hash:219864680