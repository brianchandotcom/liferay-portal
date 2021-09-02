/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectLayoutBoxRowLocalService}.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxRowLocalService
 * @generated
 */
public class ObjectLayoutBoxRowLocalServiceWrapper
	implements ObjectLayoutBoxRowLocalService,
			   ServiceWrapper<ObjectLayoutBoxRowLocalService> {

	public ObjectLayoutBoxRowLocalServiceWrapper(
		ObjectLayoutBoxRowLocalService objectLayoutBoxRowLocalService) {

		_objectLayoutBoxRowLocalService = objectLayoutBoxRowLocalService;
	}

	/**
	 * Adds the object layout box row to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxRowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxRow the object layout box row
	 * @return the object layout box row that was added
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow addObjectLayoutBoxRow(
		com.liferay.object.model.ObjectLayoutBoxRow objectLayoutBoxRow) {

		return _objectLayoutBoxRowLocalService.addObjectLayoutBoxRow(
			objectLayoutBoxRow);
	}

	/**
	 * Creates a new object layout box row with the primary key. Does not add the object layout box row to the database.
	 *
	 * @param objectLayoutBoxRowId the primary key for the new object layout box row
	 * @return the new object layout box row
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow createObjectLayoutBoxRow(
		long objectLayoutBoxRowId) {

		return _objectLayoutBoxRowLocalService.createObjectLayoutBoxRow(
			objectLayoutBoxRowId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxRowLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the object layout box row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxRowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row that was removed
	 * @throws PortalException if a object layout box row with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow deleteObjectLayoutBoxRow(
			long objectLayoutBoxRowId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxRowLocalService.deleteObjectLayoutBoxRow(
			objectLayoutBoxRowId);
	}

	/**
	 * Deletes the object layout box row from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxRowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxRow the object layout box row
	 * @return the object layout box row that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow deleteObjectLayoutBoxRow(
		com.liferay.object.model.ObjectLayoutBoxRow objectLayoutBoxRow) {

		return _objectLayoutBoxRowLocalService.deleteObjectLayoutBoxRow(
			objectLayoutBoxRow);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxRowLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectLayoutBoxRowLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectLayoutBoxRowLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectLayoutBoxRowLocalService.dynamicQuery();
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

		return _objectLayoutBoxRowLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxRowModelImpl</code>.
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

		return _objectLayoutBoxRowLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxRowModelImpl</code>.
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

		return _objectLayoutBoxRowLocalService.dynamicQuery(
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

		return _objectLayoutBoxRowLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectLayoutBoxRowLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow fetchObjectLayoutBoxRow(
		long objectLayoutBoxRowId) {

		return _objectLayoutBoxRowLocalService.fetchObjectLayoutBoxRow(
			objectLayoutBoxRowId);
	}

	/**
	 * Returns the object layout box row with the matching UUID and company.
	 *
	 * @param uuid the object layout box row's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow
		fetchObjectLayoutBoxRowByUuidAndCompanyId(String uuid, long companyId) {

		return _objectLayoutBoxRowLocalService.
			fetchObjectLayoutBoxRowByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectLayoutBoxRowLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectLayoutBoxRowLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectLayoutBoxRowLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object layout box row with the primary key.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row
	 * @throws PortalException if a object layout box row with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow getObjectLayoutBoxRow(
			long objectLayoutBoxRowId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxRowLocalService.getObjectLayoutBoxRow(
			objectLayoutBoxRowId);
	}

	/**
	 * Returns the object layout box row with the matching UUID and company.
	 *
	 * @param uuid the object layout box row's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box row
	 * @throws PortalException if a matching object layout box row could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow
			getObjectLayoutBoxRowByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxRowLocalService.
			getObjectLayoutBoxRowByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of object layout box rows
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectLayoutBoxRow>
		getObjectLayoutBoxRows(int start, int end) {

		return _objectLayoutBoxRowLocalService.getObjectLayoutBoxRows(
			start, end);
	}

	/**
	 * Returns the number of object layout box rows.
	 *
	 * @return the number of object layout box rows
	 */
	@Override
	public int getObjectLayoutBoxRowsCount() {
		return _objectLayoutBoxRowLocalService.getObjectLayoutBoxRowsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectLayoutBoxRowLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxRowLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the object layout box row in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxRowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxRow the object layout box row
	 * @return the object layout box row that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBoxRow updateObjectLayoutBoxRow(
		com.liferay.object.model.ObjectLayoutBoxRow objectLayoutBoxRow) {

		return _objectLayoutBoxRowLocalService.updateObjectLayoutBoxRow(
			objectLayoutBoxRow);
	}

	@Override
	public ObjectLayoutBoxRowLocalService getWrappedService() {
		return _objectLayoutBoxRowLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectLayoutBoxRowLocalService objectLayoutBoxRowLocalService) {

		_objectLayoutBoxRowLocalService = objectLayoutBoxRowLocalService;
	}

	private ObjectLayoutBoxRowLocalService _objectLayoutBoxRowLocalService;

}