/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExpandoRowLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRowLocalService
 * @generated
 */
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
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.addExpandoRow(expandoRow);
	}

	/**
	* Creates a new expando row with the primary key. Does not add the expando row to the database.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	public com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId) {
		return _expandoRowLocalService.createExpandoRow(rowId);
	}

	/**
	* Deletes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row that was removed
	* @throws PortalException if a expando row with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.deleteExpandoRow(rowId);
	}

	/**
	* Deletes the expando row from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _expandoRowLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.expando.model.ExpandoRow fetchExpandoRow(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.fetchExpandoRow(rowId);
	}

	/**
	* Returns the expando row with the primary key.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row
	* @throws PortalException if a expando row with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getExpandoRow(rowId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getExpandoRows(start, end);
	}

	/**
	* Returns the number of expando rows.
	*
	* @return the number of expando rows
	* @throws SystemException if a system exception occurred
	*/
	public int getExpandoRowsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getExpandoRowsCount();
	}

	/**
	* Updates the expando row in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.updateExpandoRow(expandoRow);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _expandoRowLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_expandoRowLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.addRow(tableId, classPK);
	}

	public void deleteRow(com.liferay.portlet.expando.model.ExpandoRow row)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(row);
	}

	public void deleteRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(rowId);
	}

	public void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(tableId, classPK);
	}

	public void deleteRow(long companyId, long classNameId,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(companyId, classNameId, tableName,
			classPK);
	}

	public void deleteRow(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoRowLocalService.deleteRow(companyId, className, tableName,
			classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRows(companyId,
			classNameId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, java.lang.String className, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRows(companyId,
			className, start, end);
	}

	public int getDefaultTableRowsCount(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRowsCount(companyId,
			classNameId);
	}

	public int getDefaultTableRowsCount(long companyId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getDefaultTableRowsCount(companyId,
			className);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long rowId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(rowId);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long tableId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(tableId, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(companyId, classNameId,
			tableName, classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRow(companyId, className, tableName,
			classPK);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(tableId, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, long classNameId, java.lang.String tableName,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(companyId, classNameId,
			tableName, start, end);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, java.lang.String className, java.lang.String tableName,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(companyId, className, tableName,
			start, end);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRows(long, String,
	String, int, int)}
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRows(className, tableName, start, end);
	}

	public int getRowsCount(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(tableId);
	}

	public int getRowsCount(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(companyId, classNameId,
			tableName);
	}

	public int getRowsCount(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(companyId, className,
			tableName);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRowsCount(long, String,
	String)}
	*/
	public int getRowsCount(java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoRowLocalService.getRowsCount(className, tableName);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ExpandoRowLocalService getWrappedExpandoRowLocalService() {
		return _expandoRowLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedExpandoRowLocalService(
		ExpandoRowLocalService expandoRowLocalService) {
		_expandoRowLocalService = expandoRowLocalService;
	}

	public ExpandoRowLocalService getWrappedService() {
		return _expandoRowLocalService;
	}

	public void setWrappedService(ExpandoRowLocalService expandoRowLocalService) {
		_expandoRowLocalService = expandoRowLocalService;
	}

	private ExpandoRowLocalService _expandoRowLocalService;
}