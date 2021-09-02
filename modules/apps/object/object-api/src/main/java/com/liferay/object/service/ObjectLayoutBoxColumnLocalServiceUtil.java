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

import com.liferay.object.model.ObjectLayoutBoxColumn;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ObjectLayoutBoxColumn. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectLayoutBoxColumnLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxColumnLocalService
 * @generated
 */
public class ObjectLayoutBoxColumnLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectLayoutBoxColumnLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the object layout box column to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 * @return the object layout box column that was added
	 */
	public static ObjectLayoutBoxColumn addObjectLayoutBoxColumn(
		ObjectLayoutBoxColumn objectLayoutBoxColumn) {

		return getService().addObjectLayoutBoxColumn(objectLayoutBoxColumn);
	}

	/**
	 * Creates a new object layout box column with the primary key. Does not add the object layout box column to the database.
	 *
	 * @param objectLayoutBoxColumnId the primary key for the new object layout box column
	 * @return the new object layout box column
	 */
	public static ObjectLayoutBoxColumn createObjectLayoutBoxColumn(
		long objectLayoutBoxColumnId) {

		return getService().createObjectLayoutBoxColumn(
			objectLayoutBoxColumnId);
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
	 * Deletes the object layout box column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column that was removed
	 * @throws PortalException if a object layout box column with the primary key could not be found
	 */
	public static ObjectLayoutBoxColumn deleteObjectLayoutBoxColumn(
			long objectLayoutBoxColumnId)
		throws PortalException {

		return getService().deleteObjectLayoutBoxColumn(
			objectLayoutBoxColumnId);
	}

	/**
	 * Deletes the object layout box column from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 * @return the object layout box column that was removed
	 */
	public static ObjectLayoutBoxColumn deleteObjectLayoutBoxColumn(
		ObjectLayoutBoxColumn objectLayoutBoxColumn) {

		return getService().deleteObjectLayoutBoxColumn(objectLayoutBoxColumn);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl</code>.
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

	public static ObjectLayoutBoxColumn fetchObjectLayoutBoxColumn(
		long objectLayoutBoxColumnId) {

		return getService().fetchObjectLayoutBoxColumn(objectLayoutBoxColumnId);
	}

	/**
	 * Returns the object layout box column with the matching UUID and company.
	 *
	 * @param uuid the object layout box column's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	public static ObjectLayoutBoxColumn
		fetchObjectLayoutBoxColumnByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchObjectLayoutBoxColumnByUuidAndCompanyId(
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
	 * Returns the object layout box column with the primary key.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column
	 * @throws PortalException if a object layout box column with the primary key could not be found
	 */
	public static ObjectLayoutBoxColumn getObjectLayoutBoxColumn(
			long objectLayoutBoxColumnId)
		throws PortalException {

		return getService().getObjectLayoutBoxColumn(objectLayoutBoxColumnId);
	}

	/**
	 * Returns the object layout box column with the matching UUID and company.
	 *
	 * @param uuid the object layout box column's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object layout box column
	 * @throws PortalException if a matching object layout box column could not be found
	 */
	public static ObjectLayoutBoxColumn
			getObjectLayoutBoxColumnByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getObjectLayoutBoxColumnByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of object layout box columns
	 */
	public static List<ObjectLayoutBoxColumn> getObjectLayoutBoxColumns(
		int start, int end) {

		return getService().getObjectLayoutBoxColumns(start, end);
	}

	/**
	 * Returns the number of object layout box columns.
	 *
	 * @return the number of object layout box columns
	 */
	public static int getObjectLayoutBoxColumnsCount() {
		return getService().getObjectLayoutBoxColumnsCount();
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
	 * Updates the object layout box column in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectLayoutBoxColumnLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 * @return the object layout box column that was updated
	 */
	public static ObjectLayoutBoxColumn updateObjectLayoutBoxColumn(
		ObjectLayoutBoxColumn objectLayoutBoxColumn) {

		return getService().updateObjectLayoutBoxColumn(objectLayoutBoxColumn);
	}

	public static ObjectLayoutBoxColumnLocalService getService() {
		return _service;
	}

	private static volatile ObjectLayoutBoxColumnLocalService _service;

}