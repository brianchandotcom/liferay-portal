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

import com.liferay.object.model.ObjectState;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ObjectState. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectStateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see ObjectStateLocalService
 * @generated
 */
public class ObjectStateLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectStateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectState addObjectState(
			long userId, long listTypeEntryId, long objectStateFlowId)
		throws PortalException {

		return getService().addObjectState(
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
	public static ObjectState addObjectState(ObjectState objectState) {
		return getService().addObjectState(objectState);
	}

	/**
	 * Creates a new object state with the primary key. Does not add the object state to the database.
	 *
	 * @param objectStateId the primary key for the new object state
	 * @return the new object state
	 */
	public static ObjectState createObjectState(long objectStateId) {
		return getService().createObjectState(objectStateId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteListTypeEntryObjectStates(long listTypeEntryId) {
		getService().deleteListTypeEntryObjectStates(listTypeEntryId);
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
	public static ObjectState deleteObjectState(long objectStateId)
		throws PortalException {

		return getService().deleteObjectState(objectStateId);
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
	public static ObjectState deleteObjectState(ObjectState objectState) {
		return getService().deleteObjectState(objectState);
	}

	public static void deleteObjectStateFlowObjectStates(
		long objectStateFlowId) {

		getService().deleteObjectStateFlowObjectStates(objectStateFlowId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static ObjectState fetchObjectState(long objectStateId) {
		return getService().fetchObjectState(objectStateId);
	}

	/**
	 * Returns the object state with the matching UUID and company.
	 *
	 * @param uuid the object state's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchObjectStateByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchObjectStateByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static List<ObjectState> getNextObjectStates(
		long sourceObjectStateId) {

		return getService().getNextObjectStates(sourceObjectStateId);
	}

	/**
	 * Returns the object state with the primary key.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state
	 * @throws PortalException if a object state with the primary key could not be found
	 */
	public static ObjectState getObjectState(long objectStateId)
		throws PortalException {

		return getService().getObjectState(objectStateId);
	}

	/**
	 * Returns the object state with the matching UUID and company.
	 *
	 * @param uuid the object state's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state
	 * @throws PortalException if a matching object state could not be found
	 */
	public static ObjectState getObjectStateByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getObjectStateByUuidAndCompanyId(uuid, companyId);
	}

	public static List<ObjectState> getObjectStateFlowObjectStates(
		long objectStateFlowId) {

		return getService().getObjectStateFlowObjectStates(objectStateFlowId);
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
	public static List<ObjectState> getObjectStates(int start, int end) {
		return getService().getObjectStates(start, end);
	}

	public static ObjectState
			getObjectStatesByListTypeEntryIdAndObjectStateFlowId(
				long listTypeEntryId, long objectStateFlowId)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getService().
			getObjectStatesByListTypeEntryIdAndObjectStateFlowId(
				listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Returns the number of object states.
	 *
	 * @return the number of object states
	 */
	public static int getObjectStatesCount() {
		return getService().getObjectStatesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static ObjectState updateObjectState(ObjectState objectState) {
		return getService().updateObjectState(objectState);
	}

	public static void updateObjectStateTransitions(
			long objectStateId,
			List<com.liferay.object.model.ObjectStateTransition>
				objectStateTransitions)
		throws PortalException {

		getService().updateObjectStateTransitions(
			objectStateId, objectStateTransitions);
	}

	public static ObjectStateLocalService getService() {
		return _service;
	}

	private static volatile ObjectStateLocalService _service;

}