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
 * Provides a wrapper for {@link ExpandoColumnLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnLocalService
 * @generated
 */
public class ExpandoColumnLocalServiceWrapper
	implements ExpandoColumnLocalService,
		ServiceWrapper<ExpandoColumnLocalService> {
	public ExpandoColumnLocalServiceWrapper(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	/**
	* Adds the expando column to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn addExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.addExpandoColumn(expandoColumn);
	}

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn createExpandoColumn(
		long columnId) {
		return _expandoColumnLocalService.createExpandoColumn(columnId);
	}

	/**
	* Deletes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws PortalException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn deleteExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.deleteExpandoColumn(columnId);
	}

	/**
	* Deletes the expando column from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.deleteExpandoColumn(expandoColumn);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _expandoColumnLocalService.dynamicQuery();
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
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _expandoColumnLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn fetchExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.fetchExpandoColumn(columnId);
	}

	/**
	* Returns the expando column with the primary key.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws PortalException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn getExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getExpandoColumn(columnId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of expando columns
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getExpandoColumns(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getExpandoColumns(start, end);
	}

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	* @throws SystemException if a system exception occurred
	*/
	public int getExpandoColumnsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getExpandoColumnsCount();
	}

	/**
	* Updates the expando column in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _expandoColumnLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_expandoColumnLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.addColumn(tableId, name, type,
			defaultData);
	}

	public void deleteColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(column);
	}

	public void deleteColumn(long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(columnId);
	}

	public void deleteColumn(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(companyId, classNameId,
			tableName, name);
	}

	public void deleteColumn(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(tableId, name);
	}

	public void deleteColumn(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumn(companyId, className,
			tableName, name);
	}

	public void deleteColumns(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumns(tableId);
	}

	public void deleteColumns(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumns(companyId, classNameId,
			tableName);
	}

	public void deleteColumns(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoColumnLocalService.deleteColumns(companyId, className, tableName);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(columnId);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(companyId, classNameId,
			tableName, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(tableId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumn(companyId, className,
			tableName, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(tableId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId, java.util.Collection<java.lang.String> names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(tableId, names);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, long classNameId, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(companyId, classNameId,
			tableName);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, long classNameId, java.lang.String tableName,
		java.util.Collection<java.lang.String> names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(companyId, classNameId,
			tableName, names);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(companyId, className,
			tableName);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.util.Collection<java.lang.String> columnNames)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumns(companyId, className,
			tableName, columnNames);
	}

	public int getColumnsCount(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumnsCount(tableId);
	}

	public int getColumnsCount(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumnsCount(companyId,
			classNameId, tableName);
	}

	public int getColumnsCount(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getColumnsCount(companyId, className,
			tableName);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumn(companyId,
			classNameId, name);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumn(companyId,
			className, name);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumns(companyId,
			classNameId);
	}

	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumns(companyId,
			className);
	}

	public int getDefaultTableColumnsCount(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(companyId,
			classNameId);
	}

	public int getDefaultTableColumnsCount(long companyId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(companyId,
			className);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type,
			defaultData);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoColumnLocalService.updateTypeSettings(columnId,
			typeSettings);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ExpandoColumnLocalService getWrappedExpandoColumnLocalService() {
		return _expandoColumnLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	public ExpandoColumnLocalService getWrappedService() {
		return _expandoColumnLocalService;
	}

	public void setWrappedService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	private ExpandoColumnLocalService _expandoColumnLocalService;
}