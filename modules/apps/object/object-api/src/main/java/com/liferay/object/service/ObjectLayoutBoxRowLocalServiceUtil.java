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

import com.liferay.object.model.ObjectLayoutBoxRow;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ObjectLayoutBoxRow. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectLayoutBoxRowLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxRowLocalService
 * @generated
 */
public class ObjectLayoutBoxRowLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectLayoutBoxRowLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static ObjectLayoutBoxRow addObjectLayoutBoxRow(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		return getService().addObjectLayoutBoxRow(objectLayoutBoxRow);
	}

	/**
	 * Creates a new object layout box row with the primary key. Does not add the object layout box row to the database.
	 *
	 * @param objectLayoutBoxRowId the primary key for the new object layout box row
	 * @return the new object layout box row
	 */
	public static ObjectLayoutBoxRow createObjectLayoutBoxRow(
		long objectLayoutBoxRowId) {

		return getService().createObjectLayoutBoxRow(objectLayoutBoxRowId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static ObjectLayoutBoxRow deleteObjectLayoutBoxRow(
			long objectLayoutBoxRowId)
		throws PortalException {

		return getService().deleteObjectLayoutBoxRow(objectLayoutBoxRowId);
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
	public static ObjectLayoutBoxRow deleteObjectLayoutBoxRow(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		return getService().deleteObjectLayoutBoxRow(objectLayoutBoxRow);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxRowModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxRowModelImpl</code>.
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

	public static ObjectLayoutBoxRow fetchObjectLayoutBoxRow(
		long objectLayoutBoxRowId) {

		return getService().fetchObjectLayoutBoxRow(objectLayoutBoxRowId);
	}

	/**
	 * Returns the object layout box row with the matching UUID and company.
	 *
	 * @param uuid the object layout box row's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow fetchObjectLayoutBoxRowByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchObjectLayoutBoxRowByUuidAndCompanyId(
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

	/**
	 * Returns the object layout box row with the primary key.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row
	 * @throws PortalException if a object layout box row with the primary key could not be found
	 */
	public static ObjectLayoutBoxRow getObjectLayoutBoxRow(
			long objectLayoutBoxRowId)
		throws PortalException {

		return getService().getObjectLayoutBoxRow(objectLayoutBoxRowId);
	}

	/**
	 * Returns the object layout box row with the matching UUID and company.
	 *
	 * @param uuid the object layout box row's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box row
	 * @throws PortalException if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow getObjectLayoutBoxRowByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getObjectLayoutBoxRowByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<ObjectLayoutBoxRow> getObjectLayoutBoxRows(
		int start, int end) {

		return getService().getObjectLayoutBoxRows(start, end);
	}

	/**
	 * Returns the number of object layout box rows.
	 *
	 * @return the number of object layout box rows
	 */
	public static int getObjectLayoutBoxRowsCount() {
		return getService().getObjectLayoutBoxRowsCount();
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
	 * Updates the object layout box row in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxRowLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxRow the object layout box row
	 * @return the object layout box row that was updated
	 */
	public static ObjectLayoutBoxRow updateObjectLayoutBoxRow(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		return getService().updateObjectLayoutBoxRow(objectLayoutBoxRow);
	}

	public static ObjectLayoutBoxRowLocalService getService() {
		return _service;
	}

	private static volatile ObjectLayoutBoxRowLocalService _service;

}