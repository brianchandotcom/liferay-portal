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

package com.liferay.portlet.expando.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ExpandoRow. This utility wraps
 * {@link com.liferay.portlet.expando.service.impl.ExpandoRowLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowLocalService
 * @see com.liferay.portlet.expando.service.base.ExpandoRowLocalServiceBaseImpl
 * @see com.liferay.portlet.expando.service.impl.ExpandoRowLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExpandoRowLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.expando.service.impl.ExpandoRowLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the expando row to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was added
	*/
	public static com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return getService().addExpandoRow(expandoRow);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow addRow(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addRow(tableId, classPK);
	}

	/**
	* Creates a new expando row with the primary key. Does not add the expando row to the database.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	public static com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId) {
		return getService().createExpandoRow(rowId);
	}

	/**
	* Deletes the expando row from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was removed
	*/
	public static com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return getService().deleteExpandoRow(expandoRow);
	}

	/**
	* Deletes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row that was removed
	* @throws PortalException if a expando row with the primary key could not be found
	*/
	public static com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		long rowId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteExpandoRow(rowId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRow(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRow(companyId, className, tableName, classPK);
	}

	public static void deleteRow(long companyId, long classNameId,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRow(companyId, classNameId, tableName, classPK);
	}

	public static void deleteRow(
		com.liferay.portlet.expando.model.ExpandoRow row) {
		getService().deleteRow(row);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRow(rowId);
	}

	public static void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRow(tableId, classPK);
	}

	public static void deleteRows(long classPK) {
		getService().deleteRows(classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portlet.expando.model.ExpandoRow fetchExpandoRow(
		long rowId) {
		return getService().fetchExpandoRow(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow fetchRow(
		long tableId, long classPK) {
		return getService().fetchRow(tableId, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, java.lang.String className, int start, int end) {
		return getService().getDefaultTableRows(companyId, className, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, long classNameId, int start, int end) {
		return getService()
				   .getDefaultTableRows(companyId, classNameId, start, end);
	}

	public static int getDefaultTableRowsCount(long companyId,
		java.lang.String className) {
		return getService().getDefaultTableRowsCount(companyId, className);
	}

	public static int getDefaultTableRowsCount(long companyId, long classNameId) {
		return getService().getDefaultTableRowsCount(companyId, classNameId);
	}

	/**
	* Returns the expando row with the primary key.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row
	* @throws PortalException if a expando row with the primary key could not be found
	*/
	public static com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getExpandoRow(rowId);
	}

	/**
	* Returns a range of all the expando rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @return the range of expando rows
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end) {
		return getService().getExpandoRows(start, end);
	}

	/**
	* Returns the number of expando rows.
	*
	* @return the number of expando rows
	*/
	public static int getExpandoRowsCount() {
		return getService().getExpandoRowsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long companyId, java.lang.String className, java.lang.String tableName,
		long classPK) {
		return getService().getRow(companyId, className, tableName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long companyId, long classNameId, java.lang.String tableName,
		long classPK) {
		return getService().getRow(companyId, classNameId, tableName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long rowId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRow(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRow(tableId, classPK);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRows(long, String,
	String, int, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) {
		return getService().getRows(className, tableName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, java.lang.String className, java.lang.String tableName,
		int start, int end) {
		return getService().getRows(companyId, className, tableName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, long classNameId, java.lang.String tableName,
		int start, int end) {
		return getService()
				   .getRows(companyId, classNameId, tableName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end) {
		return getService().getRows(tableId, start, end);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRowsCount(long, String,
	String)}
	*/
	@Deprecated
	public static int getRowsCount(java.lang.String className,
		java.lang.String tableName) {
		return getService().getRowsCount(className, tableName);
	}

	public static int getRowsCount(long companyId, java.lang.String className,
		java.lang.String tableName) {
		return getService().getRowsCount(companyId, className, tableName);
	}

	public static int getRowsCount(long companyId, long classNameId,
		java.lang.String tableName) {
		return getService().getRowsCount(companyId, classNameId, tableName);
	}

	public static int getRowsCount(long tableId) {
		return getService().getRowsCount(tableId);
	}

	/**
	* Updates the expando row in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was updated
	*/
	public static com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return getService().updateExpandoRow(expandoRow);
	}

	public static ExpandoRowLocalService getService() {
		if (_service == null) {
			_service = (ExpandoRowLocalService)PortalBeanLocatorUtil.locate(ExpandoRowLocalService.class.getName());

			ReferenceRegistry.registerReference(ExpandoRowLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ExpandoRowLocalService _service;
}