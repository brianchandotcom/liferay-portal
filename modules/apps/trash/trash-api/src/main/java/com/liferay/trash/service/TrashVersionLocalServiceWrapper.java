/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.trash.model.TrashVersion;

/**
 * Provides a wrapper for {@link TrashVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersionLocalService
 * @generated
 */
public class TrashVersionLocalServiceWrapper
	implements ServiceWrapper<TrashVersionLocalService>,
			   TrashVersionLocalService {

	public TrashVersionLocalServiceWrapper() {
		this(null);
	}

	public TrashVersionLocalServiceWrapper(
		TrashVersionLocalService trashVersionLocalService) {

		_trashVersionLocalService = trashVersionLocalService;
	}

	@Override
	public TrashVersion addTrashVersion(
		long trashEntryId, String className, long classPK, int status,
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties) {

		return _trashVersionLocalService.addTrashVersion(
			trashEntryId, className, classPK, status,
			typeSettingsUnicodeProperties);
	}

	/**
	 * Adds the trash version to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashVersion the trash version
	 * @return the trash version that was added
	 */
	@Override
	public TrashVersion addTrashVersion(TrashVersion trashVersion) {
		return _trashVersionLocalService.addTrashVersion(trashVersion);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashVersionLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new trash version with the primary key. Does not add the trash version to the database.
	 *
	 * @param versionId the primary key for the new trash version
	 * @return the new trash version
	 */
	@Override
	public TrashVersion createTrashVersion(long versionId) {
		return _trashVersionLocalService.createTrashVersion(versionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashVersionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version that was removed
	 * @throws PortalException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion deleteTrashVersion(long versionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashVersionLocalService.deleteTrashVersion(versionId);
	}

	@Override
	public TrashVersion deleteTrashVersion(String className, long classPK) {
		return _trashVersionLocalService.deleteTrashVersion(className, classPK);
	}

	/**
	 * Deletes the trash version from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashVersion the trash version
	 * @return the trash version that was removed
	 */
	@Override
	public TrashVersion deleteTrashVersion(TrashVersion trashVersion) {
		return _trashVersionLocalService.deleteTrashVersion(trashVersion);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _trashVersionLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _trashVersionLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _trashVersionLocalService.dynamicQuery();
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

		return _trashVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashVersionModelImpl</code>.
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

		return _trashVersionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashVersionModelImpl</code>.
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

		return _trashVersionLocalService.dynamicQuery(
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

		return _trashVersionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _trashVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public TrashVersion fetchTrashVersion(long versionId) {
		return _trashVersionLocalService.fetchTrashVersion(versionId);
	}

	@Override
	public TrashVersion fetchVersion(String className, long classPK) {
		return _trashVersionLocalService.fetchVersion(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _trashVersionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _trashVersionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _trashVersionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the trash version with the primary key.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version
	 * @throws PortalException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion getTrashVersion(long versionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashVersionLocalService.getTrashVersion(versionId);
	}

	/**
	 * Returns a range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of trash versions
	 */
	@Override
	public java.util.List<TrashVersion> getTrashVersions(int start, int end) {
		return _trashVersionLocalService.getTrashVersions(start, end);
	}

	/**
	 * Returns the number of trash versions.
	 *
	 * @return the number of trash versions
	 */
	@Override
	public int getTrashVersionsCount() {
		return _trashVersionLocalService.getTrashVersionsCount();
	}

	@Override
	public java.util.List<TrashVersion> getVersions(long entryId) {
		return _trashVersionLocalService.getVersions(entryId);
	}

	@Override
	public java.util.List<TrashVersion> getVersions(
		long entryId, String className) {

		return _trashVersionLocalService.getVersions(entryId, className);
	}

	/**
	 * Updates the trash version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashVersion the trash version
	 * @return the trash version that was updated
	 */
	@Override
	public TrashVersion updateTrashVersion(TrashVersion trashVersion) {
		return _trashVersionLocalService.updateTrashVersion(trashVersion);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _trashVersionLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<TrashVersion> getCTPersistence() {
		return _trashVersionLocalService.getCTPersistence();
	}

	@Override
	public Class<TrashVersion> getModelClass() {
		return _trashVersionLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<TrashVersion>, R, E>
				updateUnsafeFunction)
		throws E {

		return _trashVersionLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public TrashVersionLocalService getWrappedService() {
		return _trashVersionLocalService;
	}

	@Override
	public void setWrappedService(
		TrashVersionLocalService trashVersionLocalService) {

		_trashVersionLocalService = trashVersionLocalService;
	}

	private TrashVersionLocalService _trashVersionLocalService;

}