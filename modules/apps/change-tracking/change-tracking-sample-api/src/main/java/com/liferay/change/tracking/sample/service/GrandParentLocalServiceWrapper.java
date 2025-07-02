/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link GrandParentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GrandParentLocalService
 * @generated
 */
public class GrandParentLocalServiceWrapper
	implements GrandParentLocalService,
			   ServiceWrapper<GrandParentLocalService> {

	public GrandParentLocalServiceWrapper() {
		this(null);
	}

	public GrandParentLocalServiceWrapper(
		GrandParentLocalService grandParentLocalService) {

		_grandParentLocalService = grandParentLocalService;
	}

	/**
	 * Adds the grand parent to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GrandParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param grandParent the grand parent
	 * @return the grand parent that was added
	 */
	@Override
	public com.liferay.change.tracking.sample.model.GrandParent addGrandParent(
		com.liferay.change.tracking.sample.model.GrandParent grandParent) {

		return _grandParentLocalService.addGrandParent(grandParent);
	}

	@Override
	public com.liferay.change.tracking.sample.model.GrandParent addGrandParent(
		long companyId) {

		return _grandParentLocalService.addGrandParent(companyId);
	}

	@Override
	public com.liferay.change.tracking.sample.model.GrandParent addGrandParent(
		long companyId, long parentGrandParentId) {

		return _grandParentLocalService.addGrandParent(
			companyId, parentGrandParentId);
	}

	/**
	 * Creates a new grand parent with the primary key. Does not add the grand parent to the database.
	 *
	 * @param grandParentId the primary key for the new grand parent
	 * @return the new grand parent
	 */
	@Override
	public com.liferay.change.tracking.sample.model.GrandParent
		createGrandParent(long grandParentId) {

		return _grandParentLocalService.createGrandParent(grandParentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _grandParentLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the grand parent from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GrandParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param grandParent the grand parent
	 * @return the grand parent that was removed
	 */
	@Override
	public com.liferay.change.tracking.sample.model.GrandParent
		deleteGrandParent(
			com.liferay.change.tracking.sample.model.GrandParent grandParent) {

		return _grandParentLocalService.deleteGrandParent(grandParent);
	}

	/**
	 * Deletes the grand parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GrandParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent that was removed
	 * @throws PortalException if a grand parent with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.sample.model.GrandParent
			deleteGrandParent(long grandParentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _grandParentLocalService.deleteGrandParent(grandParentId);
	}

	@Override
	public void deleteGrandParents(long companyId) {
		_grandParentLocalService.deleteGrandParents(companyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _grandParentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _grandParentLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _grandParentLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _grandParentLocalService.dynamicQuery();
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

		return _grandParentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.GrandParentModelImpl</code>.
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

		return _grandParentLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.GrandParentModelImpl</code>.
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

		return _grandParentLocalService.dynamicQuery(
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

		return _grandParentLocalService.dynamicQueryCount(dynamicQuery);
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

		return _grandParentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.change.tracking.sample.model.GrandParent
		fetchGrandParent(long grandParentId) {

		return _grandParentLocalService.fetchGrandParent(grandParentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _grandParentLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the grand parent with the primary key.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent
	 * @throws PortalException if a grand parent with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.sample.model.GrandParent getGrandParent(
			long grandParentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _grandParentLocalService.getGrandParent(grandParentId);
	}

	/**
	 * Returns a range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of grand parents
	 */
	@Override
	public java.util.List<com.liferay.change.tracking.sample.model.GrandParent>
		getGrandParents(int start, int end) {

		return _grandParentLocalService.getGrandParents(start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.sample.model.GrandParent>
		getGrandParents(long companyId) {

		return _grandParentLocalService.getGrandParents(companyId);
	}

	/**
	 * Returns the number of grand parents.
	 *
	 * @return the number of grand parents
	 */
	@Override
	public int getGrandParentsCount() {
		return _grandParentLocalService.getGrandParentsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _grandParentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _grandParentLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _grandParentLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the grand parent in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GrandParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param grandParent the grand parent
	 * @return the grand parent that was updated
	 */
	@Override
	public com.liferay.change.tracking.sample.model.GrandParent
		updateGrandParent(
			com.liferay.change.tracking.sample.model.GrandParent grandParent) {

		return _grandParentLocalService.updateGrandParent(grandParent);
	}

	@Override
	public com.liferay.change.tracking.sample.model.GrandParent
			updateGrandParent(long grandParentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _grandParentLocalService.updateGrandParent(grandParentId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _grandParentLocalService.getBasePersistence();
	}

	@Override
	public GrandParentLocalService getWrappedService() {
		return _grandParentLocalService;
	}

	@Override
	public void setWrappedService(
		GrandParentLocalService grandParentLocalService) {

		_grandParentLocalService = grandParentLocalService;
	}

	private GrandParentLocalService _grandParentLocalService;

}