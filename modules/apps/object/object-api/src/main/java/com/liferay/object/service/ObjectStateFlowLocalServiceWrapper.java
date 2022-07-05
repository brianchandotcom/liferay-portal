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
 * Provides a wrapper for {@link ObjectStateFlowLocalService}.
 *
 * @author Marco Leo
 * @see ObjectStateFlowLocalService
 * @generated
 */
public class ObjectStateFlowLocalServiceWrapper
	implements ObjectStateFlowLocalService,
			   ServiceWrapper<ObjectStateFlowLocalService> {

	public ObjectStateFlowLocalServiceWrapper() {
		this(null);
	}

	public ObjectStateFlowLocalServiceWrapper(
		ObjectStateFlowLocalService objectStateFlowLocalService) {

		_objectStateFlowLocalService = objectStateFlowLocalService;
	}

	@Override
	public com.liferay.object.model.ObjectStateFlow addDefaultObjectStateFlow(
			com.liferay.object.model.ObjectField objectField)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.addDefaultObjectStateFlow(
			objectField);
	}

	/**
	 * Adds the object state flow to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateFlowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectStateFlow the object state flow
	 * @return the object state flow that was added
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow addObjectStateFlow(
		com.liferay.object.model.ObjectStateFlow objectStateFlow) {

		return _objectStateFlowLocalService.addObjectStateFlow(objectStateFlow);
	}

	/**
	 * Creates a new object state flow with the primary key. Does not add the object state flow to the database.
	 *
	 * @param objectStateFlowId the primary key for the new object state flow
	 * @return the new object state flow
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow createObjectStateFlow(
		long objectStateFlowId) {

		return _objectStateFlowLocalService.createObjectStateFlow(
			objectStateFlowId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteObjectFieldObjectStateFlow(long objectFieldId)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectFieldId);
	}

	/**
	 * Deletes the object state flow with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateFlowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow that was removed
	 * @throws PortalException if a object state flow with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow deleteObjectStateFlow(
			long objectStateFlowId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.deleteObjectStateFlow(
			objectStateFlowId);
	}

	/**
	 * Deletes the object state flow from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateFlowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectStateFlow the object state flow
	 * @return the object state flow that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow deleteObjectStateFlow(
		com.liferay.object.model.ObjectStateFlow objectStateFlow) {

		return _objectStateFlowLocalService.deleteObjectStateFlow(
			objectStateFlow);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectStateFlowLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectStateFlowLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectStateFlowLocalService.dynamicQuery();
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

		return _objectStateFlowLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateFlowModelImpl</code>.
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

		return _objectStateFlowLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateFlowModelImpl</code>.
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

		return _objectStateFlowLocalService.dynamicQuery(
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

		return _objectStateFlowLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectStateFlowLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectStateFlow fetchObjectStateFlow(
		long objectStateFlowId) {

		return _objectStateFlowLocalService.fetchObjectStateFlow(
			objectStateFlowId);
	}

	/**
	 * Returns the object state flow with the matching UUID and company.
	 *
	 * @param uuid the object state flow's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow
		fetchObjectStateFlowByUuidAndCompanyId(String uuid, long companyId) {

		return _objectStateFlowLocalService.
			fetchObjectStateFlowByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectStateFlowLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectStateFlowLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectStateFlowLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.object.model.ObjectStateFlow
		getObjectFieldObjectStateFlow(long objectFieldId) {

		return _objectStateFlowLocalService.getObjectFieldObjectStateFlow(
			objectFieldId);
	}

	/**
	 * Returns the object state flow with the primary key.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow
	 * @throws PortalException if a object state flow with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow getObjectStateFlow(
			long objectStateFlowId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.getObjectStateFlow(
			objectStateFlowId);
	}

	/**
	 * Returns the object state flow with the matching UUID and company.
	 *
	 * @param uuid the object state flow's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state flow
	 * @throws PortalException if a matching object state flow could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow
			getObjectStateFlowByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.
			getObjectStateFlowByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of object state flows
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectStateFlow>
		getObjectStateFlows(int start, int end) {

		return _objectStateFlowLocalService.getObjectStateFlows(start, end);
	}

	/**
	 * Returns the number of object state flows.
	 *
	 * @return the number of object state flows
	 */
	@Override
	public int getObjectStateFlowsCount() {
		return _objectStateFlowLocalService.getObjectStateFlowsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectStateFlowLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateFlowLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the object state flow in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateFlowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectStateFlow the object state flow
	 * @return the object state flow that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectStateFlow updateObjectStateFlow(
		com.liferay.object.model.ObjectStateFlow objectStateFlow) {

		return _objectStateFlowLocalService.updateObjectStateFlow(
			objectStateFlow);
	}

	@Override
	public void updateObjectStateTransitions(
			com.liferay.object.model.ObjectStateFlow objectStateFlow)
		throws com.liferay.portal.kernel.exception.PortalException {

		_objectStateFlowLocalService.updateObjectStateTransitions(
			objectStateFlow);
	}

	@Override
	public ObjectStateFlowLocalService getWrappedService() {
		return _objectStateFlowLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectStateFlowLocalService objectStateFlowLocalService) {

		_objectStateFlowLocalService = objectStateFlowLocalService;
	}

	private ObjectStateFlowLocalService _objectStateFlowLocalService;

}