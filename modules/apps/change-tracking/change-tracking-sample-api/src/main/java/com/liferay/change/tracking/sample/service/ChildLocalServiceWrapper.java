/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service;

import com.liferay.change.tracking.sample.model.Child;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link ChildLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChildLocalService
 * @generated
 */
public class ChildLocalServiceWrapper
	implements ChildLocalService, ServiceWrapper<ChildLocalService> {

	public ChildLocalServiceWrapper() {
		this(null);
	}

	public ChildLocalServiceWrapper(ChildLocalService childLocalService) {
		_childLocalService = childLocalService;
	}

	/**
	 * Adds the child to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChildLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param child the child
	 * @return the child that was added
	 */
	@Override
	public Child addChild(Child child) {
		return _childLocalService.addChild(child);
	}

	@Override
	public Child addChild(long companyId) {
		return _childLocalService.addChild(companyId);
	}

	@Override
	public Child addChild(
		long companyId, long parentChildId, long grandParentId,
		String parentName) {

		return _childLocalService.addChild(
			companyId, parentChildId, grandParentId, parentName);
	}

	/**
	 * Creates a new child with the primary key. Does not add the child to the database.
	 *
	 * @param childId the primary key for the new child
	 * @return the new child
	 */
	@Override
	public Child createChild(long childId) {
		return _childLocalService.createChild(childId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _childLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the child from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChildLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param child the child
	 * @return the child that was removed
	 */
	@Override
	public Child deleteChild(Child child) {
		return _childLocalService.deleteChild(child);
	}

	/**
	 * Deletes the child with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChildLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param childId the primary key of the child
	 * @return the child that was removed
	 * @throws PortalException if a child with the primary key could not be found
	 */
	@Override
	public Child deleteChild(long childId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _childLocalService.deleteChild(childId);
	}

	@Override
	public void deleteChildren(long companyId) {
		_childLocalService.deleteChildren(companyId);
	}

	@Override
	public void deleteChildrenByGrandParentId(
		long companyId, long grandParentId) {

		_childLocalService.deleteChildrenByGrandParentId(
			companyId, grandParentId);
	}

	@Override
	public void deleteChildrenByParentChildId(
		long companyId, long parentChildId) {

		_childLocalService.deleteChildrenByParentChildId(
			companyId, parentChildId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _childLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _childLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _childLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _childLocalService.dynamicQuery();
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

		return _childLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.ChildModelImpl</code>.
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

		return _childLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.ChildModelImpl</code>.
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

		return _childLocalService.dynamicQuery(
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

		return _childLocalService.dynamicQueryCount(dynamicQuery);
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

		return _childLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public Child fetchChild(long childId) {
		return _childLocalService.fetchChild(childId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _childLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the child with the primary key.
	 *
	 * @param childId the primary key of the child
	 * @return the child
	 * @throws PortalException if a child with the primary key could not be found
	 */
	@Override
	public Child getChild(long childId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _childLocalService.getChild(childId);
	}

	@Override
	public java.util.List<Child> getChildren(long companyId) {
		return _childLocalService.getChildren(companyId);
	}

	@Override
	public java.util.List<Child> getChildrenByGrandParentId(
		long grandParentId) {

		return _childLocalService.getChildrenByGrandParentId(grandParentId);
	}

	@Override
	public java.util.List<Child> getChildrenByParentChildId(long parentChildId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _childLocalService.getChildrenByParentChildId(parentChildId);
	}

	/**
	 * Returns a range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.sample.model.impl.ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of childs
	 */
	@Override
	public java.util.List<Child> getChilds(int start, int end) {
		return _childLocalService.getChilds(start, end);
	}

	/**
	 * Returns the number of childs.
	 *
	 * @return the number of childs
	 */
	@Override
	public int getChildsCount() {
		return _childLocalService.getChildsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _childLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _childLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _childLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the child in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ChildLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param child the child
	 * @return the child that was updated
	 */
	@Override
	public Child updateChild(Child child) {
		return _childLocalService.updateChild(child);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _childLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<Child> getCTPersistence() {
		return _childLocalService.getCTPersistence();
	}

	@Override
	public Class<Child> getModelClass() {
		return _childLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<Child>, R, E> updateUnsafeFunction)
		throws E {

		return _childLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public ChildLocalService getWrappedService() {
		return _childLocalService;
	}

	@Override
	public void setWrappedService(ChildLocalService childLocalService) {
		_childLocalService = childLocalService;
	}

	private ChildLocalService _childLocalService;

}