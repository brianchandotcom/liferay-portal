/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link PermissionPropagationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationLocalService
 * @generated
 */
public class PermissionPropagationLocalServiceWrapper
	implements PermissionPropagationLocalService,
			   ServiceWrapper<PermissionPropagationLocalService> {

	public PermissionPropagationLocalServiceWrapper() {
		this(null);
	}

	public PermissionPropagationLocalServiceWrapper(
		PermissionPropagationLocalService permissionPropagationLocalService) {

		_permissionPropagationLocalService = permissionPropagationLocalService;
	}

	@Override
	public PermissionPropagation addPermissionPropagation(
		long companyId, long groupId, String className, long classPK,
		boolean propagate) {

		return _permissionPropagationLocalService.addPermissionPropagation(
			companyId, groupId, className, classPK, propagate);
	}

	/**
	 * Adds the permission propagation to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagation the permission propagation
	 * @return the permission propagation that was added
	 */
	@Override
	public PermissionPropagation addPermissionPropagation(
		PermissionPropagation permissionPropagation) {

		return _permissionPropagationLocalService.addPermissionPropagation(
			permissionPropagation);
	}

	/**
	 * Creates a new permission propagation with the primary key. Does not add the permission propagation to the database.
	 *
	 * @param permissionPropagationId the primary key for the new permission propagation
	 * @return the new permission propagation
	 */
	@Override
	public PermissionPropagation createPermissionPropagation(
		long permissionPropagationId) {

		return _permissionPropagationLocalService.createPermissionPropagation(
			permissionPropagationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the permission propagation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation that was removed
	 * @throws PortalException if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation deletePermissionPropagation(
			long permissionPropagationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationLocalService.deletePermissionPropagation(
			permissionPropagationId);
	}

	/**
	 * Deletes the permission propagation from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagation the permission propagation
	 * @return the permission propagation that was removed
	 */
	@Override
	public PermissionPropagation deletePermissionPropagation(
		PermissionPropagation permissionPropagation) {

		return _permissionPropagationLocalService.deletePermissionPropagation(
			permissionPropagation);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _permissionPropagationLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _permissionPropagationLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _permissionPropagationLocalService.dynamicQuery();
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

		return _permissionPropagationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationModelImpl</code>.
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

		return _permissionPropagationLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationModelImpl</code>.
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

		return _permissionPropagationLocalService.dynamicQuery(
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

		return _permissionPropagationLocalService.dynamicQueryCount(
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

		return _permissionPropagationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public PermissionPropagation fetchPermissionPropagation(
		long permissionPropagationId) {

		return _permissionPropagationLocalService.fetchPermissionPropagation(
			permissionPropagationId);
	}

	@Override
	public PermissionPropagation fetchPermissionPropagation(
		long companyId, long groupId, String className, long classPK) {

		return _permissionPropagationLocalService.fetchPermissionPropagation(
			companyId, groupId, className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _permissionPropagationLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _permissionPropagationLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _permissionPropagationLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns the permission propagation with the primary key.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation
	 * @throws PortalException if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation getPermissionPropagation(
			long permissionPropagationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationLocalService.getPermissionPropagation(
			permissionPropagationId);
	}

	/**
	 * Returns a range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @return the range of permission propagations
	 */
	@Override
	public java.util.List<PermissionPropagation> getPermissionPropagations(
		int start, int end) {

		return _permissionPropagationLocalService.getPermissionPropagations(
			start, end);
	}

	/**
	 * Returns the number of permission propagations.
	 *
	 * @return the number of permission propagations
	 */
	@Override
	public int getPermissionPropagationsCount() {
		return _permissionPropagationLocalService.
			getPermissionPropagationsCount();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public PermissionPropagation updatePermissionPropagation(
		long companyId, long groupId, String className, long classPK,
		boolean propagate) {

		return _permissionPropagationLocalService.updatePermissionPropagation(
			companyId, groupId, className, classPK, propagate);
	}

	/**
	 * Updates the permission propagation in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagation the permission propagation
	 * @return the permission propagation that was updated
	 */
	@Override
	public PermissionPropagation updatePermissionPropagation(
		PermissionPropagation permissionPropagation) {

		return _permissionPropagationLocalService.updatePermissionPropagation(
			permissionPropagation);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _permissionPropagationLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<PermissionPropagation> getCTPersistence() {
		return _permissionPropagationLocalService.getCTPersistence();
	}

	@Override
	public Class<PermissionPropagation> getModelClass() {
		return _permissionPropagationLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<PermissionPropagation>, R, E>
				updateUnsafeFunction)
		throws E {

		return _permissionPropagationLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public PermissionPropagationLocalService getWrappedService() {
		return _permissionPropagationLocalService;
	}

	@Override
	public void setWrappedService(
		PermissionPropagationLocalService permissionPropagationLocalService) {

		_permissionPropagationLocalService = permissionPropagationLocalService;
	}

	private PermissionPropagationLocalService
		_permissionPropagationLocalService;

}