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

import com.liferay.object.model.ObjectStateFlow;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ObjectStateFlow. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectStateFlowLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see ObjectStateFlowLocalService
 * @generated
 */
public class ObjectStateFlowLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectStateFlowLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectStateFlow addDefaultObjectStateFlow(
			com.liferay.object.model.ObjectField objectField)
		throws PortalException {

		return getService().addDefaultObjectStateFlow(objectField);
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
	public static ObjectStateFlow addObjectStateFlow(
		ObjectStateFlow objectStateFlow) {

		return getService().addObjectStateFlow(objectStateFlow);
	}

	/**
	 * Creates a new object state flow with the primary key. Does not add the object state flow to the database.
	 *
	 * @param objectStateFlowId the primary key for the new object state flow
	 * @return the new object state flow
	 */
	public static ObjectStateFlow createObjectStateFlow(
		long objectStateFlowId) {

		return getService().createObjectStateFlow(objectStateFlowId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteObjectFieldObjectStateFlow(long objectFieldId)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		getService().deleteObjectFieldObjectStateFlow(objectFieldId);
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
	public static ObjectStateFlow deleteObjectStateFlow(long objectStateFlowId)
		throws PortalException {

		return getService().deleteObjectStateFlow(objectStateFlowId);
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
	public static ObjectStateFlow deleteObjectStateFlow(
		ObjectStateFlow objectStateFlow) {

		return getService().deleteObjectStateFlow(objectStateFlow);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateFlowModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectStateFlowModelImpl</code>.
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

	public static ObjectStateFlow fetchObjectStateFlow(long objectStateFlowId) {
		return getService().fetchObjectStateFlow(objectStateFlowId);
	}

	/**
	 * Returns the object state flow with the matching UUID and company.
	 *
	 * @param uuid the object state flow's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchObjectStateFlowByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchObjectStateFlowByUuidAndCompanyId(
			uuid, companyId);
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

	public static ObjectStateFlow getObjectFieldObjectStateFlow(
		long objectFieldId) {

		return getService().getObjectFieldObjectStateFlow(objectFieldId);
	}

	/**
	 * Returns the object state flow with the primary key.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow
	 * @throws PortalException if a object state flow with the primary key could not be found
	 */
	public static ObjectStateFlow getObjectStateFlow(long objectStateFlowId)
		throws PortalException {

		return getService().getObjectStateFlow(objectStateFlowId);
	}

	/**
	 * Returns the object state flow with the matching UUID and company.
	 *
	 * @param uuid the object state flow's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object state flow
	 * @throws PortalException if a matching object state flow could not be found
	 */
	public static ObjectStateFlow getObjectStateFlowByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getObjectStateFlowByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<ObjectStateFlow> getObjectStateFlows(
		int start, int end) {

		return getService().getObjectStateFlows(start, end);
	}

	/**
	 * Returns the number of object state flows.
	 *
	 * @return the number of object state flows
	 */
	public static int getObjectStateFlowsCount() {
		return getService().getObjectStateFlowsCount();
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
	 * Updates the object state flow in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectStateFlowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectStateFlow the object state flow
	 * @return the object state flow that was updated
	 */
	public static ObjectStateFlow updateObjectStateFlow(
		ObjectStateFlow objectStateFlow) {

		return getService().updateObjectStateFlow(objectStateFlow);
	}

	public static void updateObjectStateTransitions(
			ObjectStateFlow objectStateFlow)
		throws PortalException {

		getService().updateObjectStateTransitions(objectStateFlow);
	}

	public static ObjectStateFlowLocalService getService() {
		return _service;
	}

	private static volatile ObjectStateFlowLocalService _service;

}