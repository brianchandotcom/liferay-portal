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
 * Provides a wrapper for {@link ObjectStateLocalService}.
 *
 * @author Marco Leo
 * @see ObjectStateLocalService
 * @generated
 */
public class ObjectStateLocalServiceWrapper
	implements ObjectStateLocalService,
			   ServiceWrapper<ObjectStateLocalService> {

	public ObjectStateLocalServiceWrapper() {
		this(null);
	}

	public ObjectStateLocalServiceWrapper(
		ObjectStateLocalService objectStateLocalService) {

		_objectStateLocalService = objectStateLocalService;
	}

	@Override
	public com.liferay.object.model.ObjectState addObjectState(
			long userId, long listTypeEntryId, long objectStateFlowId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.addObjectState(
			userId, listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Adds the object state to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectState the object state
	 * @return the object state that was added
	 */
	@Override
	public com.liferay.object.model.ObjectState addObjectState(
		com.liferay.object.model.ObjectState objectState) {

		return _objectStateLocalService.addObjectState(objectState);
	}

	/**
	 * Creates a new object state with the primary key. Does not add the object state to the database.
	 *
	 * @param objectStateId the primary key for the new object state
	 * @return the new object state
	 */
	@Override
	public com.liferay.object.model.ObjectState createObjectState(
		long objectStateId) {

		return _objectStateLocalService.createObjectState(objectStateId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteListTypeEntryObjectStates(long listTypeEntryId) {
		_objectStateLocalService.deleteListTypeEntryObjectStates(
			listTypeEntryId);
	}

	/**
	 * Deletes the object state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state that was removed
	 * @throws PortalException if a object state with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectState deleteObjectState(
			long objectStateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.deleteObjectState(objectStateId);
	}

	/**
	 * Deletes the object state from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectState the object state
	 * @return the object state that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectState deleteObjectState(
		com.liferay.object.model.ObjectState objectState) {

		return _objectStateLocalService.deleteObjectState(objectState);
	}

	@Override
	public void deleteObjectStateFlowObjectStates(long objectStateFlowId) {
		_objectStateLocalService.deleteObjectStateFlowObjectStates(
			objectStateFlowId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectStateLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectStateLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectStateLocalService.dynamicQuery();
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

		return _objectStateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateModelImpl</code>.
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

		return _objectStateLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateModelImpl</code>.
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

		return _objectStateLocalService.dynamicQuery(
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

		return _objectStateLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectStateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectState fetchObjectState(
		long objectStateId) {

		return _objectStateLocalService.fetchObjectState(objectStateId);
	}

	/**
	 * Returns the object state with the matching UUID and company.
	 *
	 * @param uuid the object state's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectState
		fetchObjectStateByUuidAndCompanyId(String uuid, long companyId) {

		return _objectStateLocalService.fetchObjectStateByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectStateLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectStateLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectStateLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectState>
		getNextObjectStates(long sourceObjectStateId) {

		return _objectStateLocalService.getNextObjectStates(
			sourceObjectStateId);
	}

	/**
	 * Returns the object state with the primary key.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state
	 * @throws PortalException if a object state with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectState getObjectState(
			long objectStateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.getObjectState(objectStateId);
	}

	/**
	 * Returns the object state with the matching UUID and company.
	 *
	 * @param uuid the object state's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state
	 * @throws PortalException if a matching object state could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectState
			getObjectStateByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.getObjectStateByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectState>
		getObjectStateFlowObjectStates(long objectStateFlowId) {

		return _objectStateLocalService.getObjectStateFlowObjectStates(
			objectStateFlowId);
	}

	/**
	 * Returns a range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of object states
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectState> getObjectStates(
		int start, int end) {

		return _objectStateLocalService.getObjectStates(start, end);
	}

	@Override
	public com.liferay.object.model.ObjectState
			getObjectStatesByListTypeEntryIdAndObjectStateFlowId(
				long listTypeEntryId, long objectStateFlowId)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return _objectStateLocalService.
			getObjectStatesByListTypeEntryIdAndObjectStateFlowId(
				listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Returns the number of object states.
	 *
	 * @return the number of object states
	 */
	@Override
	public int getObjectStatesCount() {
		return _objectStateLocalService.getObjectStatesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectStateLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectStateLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the object state in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectState the object state
	 * @return the object state that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectState updateObjectState(
		com.liferay.object.model.ObjectState objectState) {

		return _objectStateLocalService.updateObjectState(objectState);
	}

	@Override
	public void updateObjectStateTransitions(
			long objectStateId,
			java.util.List<com.liferay.object.model.ObjectStateTransition>
				objectStateTransitions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_objectStateLocalService.updateObjectStateTransitions(
			objectStateId, objectStateTransitions);
	}

	@Override
	public ObjectStateLocalService getWrappedService() {
		return _objectStateLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectStateLocalService objectStateLocalService) {

		_objectStateLocalService = objectStateLocalService;
	}

	private ObjectStateLocalService _objectStateLocalService;

}