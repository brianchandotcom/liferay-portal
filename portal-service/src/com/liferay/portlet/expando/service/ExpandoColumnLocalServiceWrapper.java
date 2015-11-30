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
 * Provides a wrapper for {@link ExpandoColumnLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnLocalService
 * @generated
 */
@ProviderType
public class ExpandoColumnLocalServiceWrapper
	implements ExpandoColumnLocalService,
		ServiceWrapper<ExpandoColumnLocalService> {
	public ExpandoColumnLocalServiceWrapper(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.addColumn(tableId, name, type);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.addColumn(tableId, name, type,
			defaultData);
	}

	/**
	* Adds the expando column to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was added
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn addExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		return _expandoColumnLocalService.addExpandoColumn(expandoColumn);
	}

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn createExpandoColumn(
		long columnId) {
		return _expandoColumnLocalService.createExpandoColumn(columnId);
	}

	@Override
	public void deleteColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column) {
		_expandoColumnLocalService.deleteColumn(column);
	}

	@Override
	public void deleteColumn(long columnId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoColumnLocalService.deleteColumn(columnId);
	}

	@Override
	public void deleteColumn(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoColumnLocalService.deleteColumn(companyId, className,
			tableName, name);
	}

	@Override
	public void deleteColumn(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoColumnLocalService.deleteColumn(companyId, classNameId,
			tableName, name);
	}

	@Override
	public void deleteColumn(long tableId, java.lang.String name) {
		_expandoColumnLocalService.deleteColumn(tableId, name);
	}

	@Override
	public void deleteColumns(long companyId, java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoColumnLocalService.deleteColumns(companyId, className, tableName);
	}

	@Override
	public void deleteColumns(long companyId, long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoColumnLocalService.deleteColumns(companyId, classNameId,
			tableName);
	}

	@Override
	public void deleteColumns(long tableId) {
		_expandoColumnLocalService.deleteColumns(tableId);
	}

	/**
	* Deletes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws PortalException if a expando column with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn deleteExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.deleteExpandoColumn(columnId);
	}

	/**
	* Deletes the expando column from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was removed
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		return _expandoColumnLocalService.deleteExpandoColumn(expandoColumn);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _expandoColumnLocalService.dynamicQuery();
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _expandoColumnLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _expandoColumnLocalService.dynamicQueryCount(dynamicQuery);
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
		return _expandoColumnLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn fetchExpandoColumn(
		long columnId) {
		return _expandoColumnLocalService.fetchExpandoColumn(columnId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _expandoColumnLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.getColumn(columnId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String name) {
		return _expandoColumnLocalService.getColumn(companyId, className,
			tableName, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String name) {
		return _expandoColumnLocalService.getColumn(companyId, classNameId,
			tableName, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name) {
		return _expandoColumnLocalService.getColumn(tableId, name);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, java.lang.String className, java.lang.String tableName) {
		return _expandoColumnLocalService.getColumns(companyId, className,
			tableName);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.util.Collection<java.lang.String> columnNames) {
		return _expandoColumnLocalService.getColumns(companyId, className,
			tableName, columnNames);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, long classNameId, java.lang.String tableName) {
		return _expandoColumnLocalService.getColumns(companyId, classNameId,
			tableName);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long companyId, long classNameId, java.lang.String tableName,
		java.util.Collection<java.lang.String> names) {
		return _expandoColumnLocalService.getColumns(companyId, classNameId,
			tableName, names);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId) {
		return _expandoColumnLocalService.getColumns(tableId);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId, java.util.Collection<java.lang.String> names) {
		return _expandoColumnLocalService.getColumns(tableId, names);
	}

	@Override
	public int getColumnsCount(long companyId, java.lang.String className,
		java.lang.String tableName) {
		return _expandoColumnLocalService.getColumnsCount(companyId, className,
			tableName);
	}

	@Override
	public int getColumnsCount(long companyId, long classNameId,
		java.lang.String tableName) {
		return _expandoColumnLocalService.getColumnsCount(companyId,
			classNameId, tableName);
	}

	@Override
	public int getColumnsCount(long tableId) {
		return _expandoColumnLocalService.getColumnsCount(tableId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, java.lang.String className, java.lang.String name) {
		return _expandoColumnLocalService.getDefaultTableColumn(companyId,
			className, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long companyId, long classNameId, java.lang.String name) {
		return _expandoColumnLocalService.getDefaultTableColumn(companyId,
			classNameId, name);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, java.lang.String className) {
		return _expandoColumnLocalService.getDefaultTableColumns(companyId,
			className);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long companyId, long classNameId) {
		return _expandoColumnLocalService.getDefaultTableColumns(companyId,
			classNameId);
	}

	@Override
	public int getDefaultTableColumnsCount(long companyId,
		java.lang.String className) {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(companyId,
			className);
	}

	@Override
	public int getDefaultTableColumnsCount(long companyId, long classNameId) {
		return _expandoColumnLocalService.getDefaultTableColumnsCount(companyId,
			classNameId);
	}

	/**
	* Returns the expando column with the primary key.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws PortalException if a expando column with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getExpandoColumn(
		long columnId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.getExpandoColumn(columnId);
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
	*/
	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getExpandoColumns(
		int start, int end) {
		return _expandoColumnLocalService.getExpandoColumns(start, end);
	}

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	*/
	@Override
	public int getExpandoColumnsCount() {
		return _expandoColumnLocalService.getExpandoColumnsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _expandoColumnLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _expandoColumnLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type,
		java.lang.Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.updateColumn(columnId, name, type,
			defaultData);
	}

	/**
	* Updates the expando column in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was updated
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn updateTypeSettings(
		long columnId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoColumnLocalService.updateTypeSettings(columnId,
			typeSettings);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExpandoColumnLocalService getWrappedExpandoColumnLocalService() {
		return _expandoColumnLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	@Override
	public ExpandoColumnLocalService getWrappedService() {
		return _expandoColumnLocalService;
	}

	@Override
	public void setWrappedService(
		ExpandoColumnLocalService expandoColumnLocalService) {
		_expandoColumnLocalService = expandoColumnLocalService;
	}

	private ExpandoColumnLocalService _expandoColumnLocalService;
}