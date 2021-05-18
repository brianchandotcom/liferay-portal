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
 * Provides a wrapper for {@link ObjectLayoutBoxLocalService}.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxLocalService
 * @generated
 */
public class ObjectLayoutBoxLocalServiceWrapper
	implements ObjectLayoutBoxLocalService,
			   ServiceWrapper<ObjectLayoutBoxLocalService> {

	public ObjectLayoutBoxLocalServiceWrapper(
		ObjectLayoutBoxLocalService objectLayoutBoxLocalService) {

		_objectLayoutBoxLocalService = objectLayoutBoxLocalService;
	}

	/**
	 * Adds the object layout box to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBox the object layout box
	 * @return the object layout box that was added
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox addObjectLayoutBox(
		com.liferay.object.model.ObjectLayoutBox objectLayoutBox) {

		return _objectLayoutBoxLocalService.addObjectLayoutBox(objectLayoutBox);
	}

	/**
	 * Creates a new object layout box with the primary key. Does not add the object layout box to the database.
	 *
	 * @param objectLayoutBoxId the primary key for the new object layout box
	 * @return the new object layout box
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox createObjectLayoutBox(
		long objectLayoutBoxId) {

		return _objectLayoutBoxLocalService.createObjectLayoutBox(
			objectLayoutBoxId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the object layout box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box that was removed
	 * @throws PortalException if a object layout box with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox deleteObjectLayoutBox(
			long objectLayoutBoxId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxLocalService.deleteObjectLayoutBox(
			objectLayoutBoxId);
	}

	/**
	 * Deletes the object layout box from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBox the object layout box
	 * @return the object layout box that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox deleteObjectLayoutBox(
		com.liferay.object.model.ObjectLayoutBox objectLayoutBox) {

		return _objectLayoutBoxLocalService.deleteObjectLayoutBox(
			objectLayoutBox);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectLayoutBoxLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectLayoutBoxLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectLayoutBoxLocalService.dynamicQuery();
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

		return _objectLayoutBoxLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
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

		return _objectLayoutBoxLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
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

		return _objectLayoutBoxLocalService.dynamicQuery(
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

		return _objectLayoutBoxLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectLayoutBoxLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectLayoutBox fetchObjectLayoutBox(
		long objectLayoutBoxId) {

		return _objectLayoutBoxLocalService.fetchObjectLayoutBox(
			objectLayoutBoxId);
	}

	/**
	 * Returns the object layout box with the matching UUID and company.
	 *
	 * @param uuid the object layout box's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox
		fetchObjectLayoutBoxByUuidAndCompanyId(String uuid, long companyId) {

		return _objectLayoutBoxLocalService.
			fetchObjectLayoutBoxByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectLayoutBoxLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectLayoutBoxLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectLayoutBoxLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object layout box with the primary key.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box
	 * @throws PortalException if a object layout box with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox getObjectLayoutBox(
			long objectLayoutBoxId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxLocalService.getObjectLayoutBox(
			objectLayoutBoxId);
	}

	/**
	 * Returns the object layout box with the matching UUID and company.
	 *
	 * @param uuid the object layout box's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box
	 * @throws PortalException if a matching object layout box could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox
			getObjectLayoutBoxByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxLocalService.
			getObjectLayoutBoxByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of object layout boxes
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectLayoutBox>
		getObjectLayoutBoxes(int start, int end) {

		return _objectLayoutBoxLocalService.getObjectLayoutBoxes(start, end);
	}

	/**
	 * Returns the number of object layout boxes.
	 *
	 * @return the number of object layout boxes
	 */
	@Override
	public int getObjectLayoutBoxesCount() {
		return _objectLayoutBoxLocalService.getObjectLayoutBoxesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectLayoutBoxLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutBoxLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the object layout box in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBox the object layout box
	 * @return the object layout box that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectLayoutBox updateObjectLayoutBox(
		com.liferay.object.model.ObjectLayoutBox objectLayoutBox) {

		return _objectLayoutBoxLocalService.updateObjectLayoutBox(
			objectLayoutBox);
	}

	@Override
	public ObjectLayoutBoxLocalService getWrappedService() {
		return _objectLayoutBoxLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectLayoutBoxLocalService objectLayoutBoxLocalService) {

		_objectLayoutBoxLocalService = objectLayoutBoxLocalService;
	}

	private ObjectLayoutBoxLocalService _objectLayoutBoxLocalService;

}