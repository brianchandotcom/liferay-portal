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

package com.liferay.expando.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for ExpandoTable. This utility wraps
 * <code>com.liferay.portlet.expando.service.impl.ExpandoTableLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableLocalService
 * @generated
 */
public class ExpandoTableLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.expando.service.impl.ExpandoTableLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable addDefaultTable(
			long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDefaultTable(companyId, classNameId);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable addDefaultTable(
			long companyId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDefaultTable(companyId, className);
	}

	/**
	 * Adds the expando table to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ExpandoTableLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param expandoTable the expando table
	 * @return the expando table that was added
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable addExpandoTable(
		com.liferay.expando.kernel.model.ExpandoTable expandoTable) {

		return getService().addExpandoTable(expandoTable);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable addTable(
			long companyId, long classNameId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTable(companyId, classNameId, name);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable addTable(
			long companyId, String className, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTable(companyId, className, name);
	}

	/**
	 * Creates a new expando table with the primary key. Does not add the expando table to the database.
	 *
	 * @param tableId the primary key for the new expando table
	 * @return the new expando table
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable
		createExpandoTable(long tableId) {

		return getService().createExpandoTable(tableId);
	}

	/**
	 * Deletes the expando table from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ExpandoTableLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param expandoTable the expando table
	 * @return the expando table that was removed
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable
		deleteExpandoTable(
			com.liferay.expando.kernel.model.ExpandoTable expandoTable) {

		return getService().deleteExpandoTable(expandoTable);
	}

	/**
	 * Deletes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ExpandoTableLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table that was removed
	 * @throws PortalException if a expando table with the primary key could not be found
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable
			deleteExpandoTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteExpandoTable(tableId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteTable(
		com.liferay.expando.kernel.model.ExpandoTable table) {

		getService().deleteTable(table);
	}

	public static void deleteTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTable(tableId);
	}

	public static void deleteTable(
			long companyId, long classNameId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTable(companyId, classNameId, name);
	}

	public static void deleteTable(
			long companyId, String className, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTable(companyId, className, name);
	}

	public static void deleteTables(long companyId, long classNameId) {
		getService().deleteTables(companyId, classNameId);
	}

	public static void deleteTables(long companyId, String className) {
		getService().deleteTables(companyId, className);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable
		fetchDefaultTable(long companyId, long classNameId) {

		return getService().fetchDefaultTable(companyId, classNameId);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable
		fetchDefaultTable(long companyId, String className) {

		return getService().fetchDefaultTable(companyId, className);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable
		fetchExpandoTable(long tableId) {

		return getService().fetchExpandoTable(tableId);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable fetchTable(
		long companyId, long classNameId, String name) {

		return getService().fetchTable(companyId, classNameId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.expando.kernel.model.ExpandoTable getDefaultTable(
			long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDefaultTable(companyId, classNameId);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable getDefaultTable(
			long companyId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDefaultTable(companyId, className);
	}

	/**
	 * Returns the expando table with the primary key.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table
	 * @throws PortalException if a expando table with the primary key could not be found
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable getExpandoTable(
			long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExpandoTable(tableId);
	}

	/**
	 * Returns a range of all the expando tables.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando tables
	 * @param end the upper bound of the range of expando tables (not inclusive)
	 * @return the range of expando tables
	 */
	public static java.util.List<com.liferay.expando.kernel.model.ExpandoTable>
		getExpandoTables(int start, int end) {

		return getService().getExpandoTables(start, end);
	}

	/**
	 * Returns the number of expando tables.
	 *
	 * @return the number of expando tables
	 */
	public static int getExpandoTablesCount() {
		return getService().getExpandoTablesCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable getTable(
			long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTable(tableId);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable getTable(
			long companyId, long classNameId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTable(companyId, classNameId, name);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable getTable(
			long companyId, String className, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTable(companyId, className, name);
	}

	public static java.util.List<com.liferay.expando.kernel.model.ExpandoTable>
		getTables(long companyId, long classNameId) {

		return getService().getTables(companyId, classNameId);
	}

	public static java.util.List<com.liferay.expando.kernel.model.ExpandoTable>
		getTables(long companyId, String className) {

		return getService().getTables(companyId, className);
	}

	/**
	 * Updates the expando table in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ExpandoTableLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param expandoTable the expando table
	 * @return the expando table that was updated
	 */
	public static com.liferay.expando.kernel.model.ExpandoTable
		updateExpandoTable(
			com.liferay.expando.kernel.model.ExpandoTable expandoTable) {

		return getService().updateExpandoTable(expandoTable);
	}

	public static com.liferay.expando.kernel.model.ExpandoTable updateTable(
			long tableId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTable(tableId, name);
	}

	public static ExpandoTableLocalService getService() {
		if (_service == null) {
			_service = (ExpandoTableLocalService)PortalBeanLocatorUtil.locate(
				ExpandoTableLocalService.class.getName());
		}

		return _service;
	}

	private static ExpandoTableLocalService _service;

}