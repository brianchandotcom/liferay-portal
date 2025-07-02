/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service;

import com.liferay.change.tracking.sample.model.Parent;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link ParentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ParentLocalService
 * @generated
 */
public class ParentLocalServiceWrapper
	implements ParentLocalService, ServiceWrapper<ParentLocalService> {

	public ParentLocalServiceWrapper() {
		this(null);
	}

	public ParentLocalServiceWrapper(ParentLocalService parentLocalService) {
		_parentLocalService = parentLocalService;
	}

	@Override
	public Parent addParent(long companyId, long grandParentId) {
		return _parentLocalService.addParent(companyId, grandParentId);
	}

	/**
	 * Adds the parent to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param parent the parent
	 * @return the parent that was added
	 */
	@Override
	public Parent addParent(Parent parent) {
		return _parentLocalService.addParent(parent);
	}

	/**
	 * Creates a new parent with the primary key. Does not add the parent to the database.
	 *
	 * @param parentId the primary key for the new parent
	 * @return the new parent
	 */
	@Override
	public Parent createParent(long parentId) {
		return _parentLocalService.createParent(parentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _parentLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent that was removed
	 * @throws PortalException if a parent with the primary key could not be found
	 */
	@Override
	public Parent deleteParent(long parentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _parentLocalService.deleteParent(parentId);
	}

	/**
	 * Deletes the parent from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param parent the parent
	 * @return the parent that was removed
	 */
	@Override
	public Parent deleteParent(Parent parent) {
		return _parentLocalService.deleteParent(parent);
	}

	@Override
	public void deleteParents(long companyId) {
		_parentLocalService.deleteParents(companyId);
	}

	@Override
	public void deleteParentsByGrandParentId(
		long companyId, long grandParentId) {

		_parentLocalService.deleteParentsByGrandParentId(
			companyId, grandParentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _parentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _parentLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _parentLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _parentLocalService.dynamicQuery();
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

		return _parentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.ParentModelImpl</code>.
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

		return _parentLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.ParentModelImpl</code>.
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

		return _parentLocalService.dynamicQuery(
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

		return _parentLocalService.dynamicQueryCount(dynamicQuery);
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

		return _parentLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public Parent fetchParent(long parentId) {
		return _parentLocalService.fetchParent(parentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _parentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _parentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _parentLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns the parent with the primary key.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent
	 * @throws PortalException if a parent with the primary key could not be found
	 */
	@Override
	public Parent getParent(long parentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _parentLocalService.getParent(parentId);
	}

	/**
	 * Returns a range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of parents
	 */
	@Override
	public java.util.List<Parent> getParents(int start, int end) {
		return _parentLocalService.getParents(start, end);
	}

	@Override
	public java.util.List<Parent> getParents(long companyId) {
		return _parentLocalService.getParents(companyId);
	}

	/**
	 * Returns the number of parents.
	 *
	 * @return the number of parents
	 */
	@Override
	public int getParentsCount() {
		return _parentLocalService.getParentsCount();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _parentLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public Parent updateParent(long parentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _parentLocalService.updateParent(parentId);
	}

	/**
	 * Updates the parent in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ParentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param parent the parent
	 * @return the parent that was updated
	 */
	@Override
	public Parent updateParent(Parent parent) {
		return _parentLocalService.updateParent(parent);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _parentLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<Parent> getCTPersistence() {
		return _parentLocalService.getCTPersistence();
	}

	@Override
	public Class<Parent> getModelClass() {
		return _parentLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<Parent>, R, E> updateUnsafeFunction)
		throws E {

		return _parentLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public ParentLocalService getWrappedService() {
		return _parentLocalService;
	}

	@Override
	public void setWrappedService(ParentLocalService parentLocalService) {
		_parentLocalService = parentLocalService;
	}

	private ParentLocalService _parentLocalService;

}