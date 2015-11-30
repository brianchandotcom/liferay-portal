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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExpandoRowLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowLocalService
 * @generated
 */
@ProviderType
public class ExpandoRowLocalServiceWrapper implements ExpandoRowLocalService,
	ServiceWrapper<ExpandoRowLocalService> {
	public ExpandoRowLocalServiceWrapper(
		ExpandoRowLocalService expandoRowLocalService) {
		_expandoRowLocalService = expandoRowLocalService;
	}

	/**
	* Adds the expando row to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was added
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return _expandoRowLocalService.addExpandoRow(expandoRow);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.addRow(tableId, classPK);
	}

	/**
	* Creates a new expando row with the primary key. Does not add the expando row to the database.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId) {
		return _expandoRowLocalService.createExpandoRow(rowId);
	}

	/**
	* Deletes the expando row from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was removed
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return _expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	/**
	* Deletes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row that was removed
	* @throws PortalException if a expando row with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		long rowId) throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.deleteExpandoRow(rowId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteRow(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoRowLocalService.deleteRow(companyId, className, tableName,
			classPK);
	}

	@Override
	public void deleteRow(long companyId, long classNameId,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoRowLocalService.deleteRow(companyId, classNameId, tableName,
			classPK);
	}

	@Override
	public void deleteRow(com.liferay.portlet.expando.model.ExpandoRow row) {
		_expandoRowLocalService.deleteRow(row);
	}

	@Override
	public void deleteRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoRowLocalService.deleteRow(rowId);
	}

	@Override
	public void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoRowLocalService.deleteRow(tableId, classPK);
	}

	@Override
	public void deleteRows(long classPK) {
		_expandoRowLocalService.deleteRows(classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _expandoRowLocalService.dynamicQuery();
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
		return _expandoRowLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _expandoRowLocalService.dynamicQueryCount(dynamicQuery);
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
		return _expandoRowLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow fetchExpandoRow(
		long rowId) {
		return _expandoRowLocalService.fetchExpandoRow(rowId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow fetchRow(long tableId,
		long classPK) {
		return _expandoRowLocalService.fetchRow(tableId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _expandoRowLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, java.lang.String className, int start, int end) {
		return _expandoRowLocalService.getDefaultTableRows(companyId,
			className, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, long classNameId, int start, int end) {
		return _expandoRowLocalService.getDefaultTableRows(companyId,
			classNameId, start, end);
	}

	@Override
	public int getDefaultTableRowsCount(long companyId,
		java.lang.String className) {
		return _expandoRowLocalService.getDefaultTableRowsCount(companyId,
			className);
	}

	@Override
	public int getDefaultTableRowsCount(long companyId, long classNameId) {
		return _expandoRowLocalService.getDefaultTableRowsCount(companyId,
			classNameId);
	}

	/**
	* Returns the expando row with the primary key.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row
	* @throws PortalException if a expando row with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId) throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.getExpandoRow(rowId);
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
	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end) {
		return _expandoRowLocalService.getExpandoRows(start, end);
	}

	/**
	* Returns the number of expando rows.
	*
	* @return the number of expando rows
	*/
	@Override
	public int getExpandoRowsCount() {
		return _expandoRowLocalService.getExpandoRowsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _expandoRowLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _expandoRowLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		java.lang.String className, java.lang.String tableName, long classPK) {
		return _expandoRowLocalService.getRow(companyId, className, tableName,
			classPK);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		long classNameId, java.lang.String tableName, long classPK) {
		return _expandoRowLocalService.getRow(companyId, classNameId,
			tableName, classPK);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.getRow(rowId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long tableId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoRowLocalService.getRow(tableId, classPK);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRows(long, String,
	String, int, int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) {
		return _expandoRowLocalService.getRows(className, tableName, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, java.lang.String className, java.lang.String tableName,
		int start, int end) {
		return _expandoRowLocalService.getRows(companyId, className, tableName,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, long classNameId, java.lang.String tableName,
		int start, int end) {
		return _expandoRowLocalService.getRows(companyId, classNameId,
			tableName, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end) {
		return _expandoRowLocalService.getRows(tableId, start, end);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRowsCount(long, String,
	String)}
	*/
	@Deprecated
	@Override
	public int getRowsCount(java.lang.String className,
		java.lang.String tableName) {
		return _expandoRowLocalService.getRowsCount(className, tableName);
	}

	@Override
	public int getRowsCount(long companyId, java.lang.String className,
		java.lang.String tableName) {
		return _expandoRowLocalService.getRowsCount(companyId, className,
			tableName);
	}

	@Override
	public int getRowsCount(long companyId, long classNameId,
		java.lang.String tableName) {
		return _expandoRowLocalService.getRowsCount(companyId, classNameId,
			tableName);
	}

	@Override
	public int getRowsCount(long tableId) {
		return _expandoRowLocalService.getRowsCount(tableId);
	}

	/**
	* Updates the expando row in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was updated
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return _expandoRowLocalService.updateExpandoRow(expandoRow);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExpandoRowLocalService getWrappedExpandoRowLocalService() {
		return _expandoRowLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExpandoRowLocalService(
		ExpandoRowLocalService expandoRowLocalService) {
		_expandoRowLocalService = expandoRowLocalService;
	}

	@Override
	public ExpandoRowLocalService getWrappedService() {
		return _expandoRowLocalService;
	}

	@Override
	public void setWrappedService(ExpandoRowLocalService expandoRowLocalService) {
		_expandoRowLocalService = expandoRowLocalService;
	}

	private ExpandoRowLocalService _expandoRowLocalService;
}