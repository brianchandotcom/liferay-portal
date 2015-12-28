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
 * Provides a wrapper for {@link ExpandoTableLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableLocalService
 * @generated
 */
@ProviderType
public class ExpandoTableLocalServiceWrapper implements ExpandoTableLocalService,
	ServiceWrapper<ExpandoTableLocalService> {
	public ExpandoTableLocalServiceWrapper(
		ExpandoTableLocalService expandoTableLocalService) {
		_expandoTableLocalService = expandoTableLocalService;
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.addDefaultTable(companyId, className);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.addDefaultTable(companyId, classNameId);
	}

	/**
	* Adds the expando table to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoTable the expando table
	* @return the expando table that was added
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		return _expandoTableLocalService.addExpandoTable(expandoTable);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addTable(long, String,
	String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.addTable(className, name);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addTable(long, long,
	String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.addTable(classNameId, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.addTable(companyId, className, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.addTable(companyId, classNameId, name);
	}

	/**
	* Creates a new expando table with the primary key. Does not add the expando table to the database.
	*
	* @param tableId the primary key for the new expando table
	* @return the new expando table
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable createExpandoTable(
		long tableId) {
		return _expandoTableLocalService.createExpandoTable(tableId);
	}

	/**
	* Deletes the expando table from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoTable the expando table
	* @return the expando table that was removed
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		return _expandoTableLocalService.deleteExpandoTable(expandoTable);
	}

	/**
	* Deletes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table that was removed
	* @throws PortalException if a expando table with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable deleteExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.deleteExpandoTable(tableId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteTable(long companyId, java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoTableLocalService.deleteTable(companyId, className, name);
	}

	@Override
	public void deleteTable(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoTableLocalService.deleteTable(companyId, classNameId, name);
	}

	@Override
	public void deleteTable(
		com.liferay.portlet.expando.model.ExpandoTable table) {
		_expandoTableLocalService.deleteTable(table);
	}

	@Override
	public void deleteTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoTableLocalService.deleteTable(tableId);
	}

	@Override
	public void deleteTables(long companyId, java.lang.String className) {
		_expandoTableLocalService.deleteTables(companyId, className);
	}

	@Override
	public void deleteTables(long companyId, long classNameId) {
		_expandoTableLocalService.deleteTables(companyId, classNameId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _expandoTableLocalService.dynamicQuery();
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
		return _expandoTableLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _expandoTableLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _expandoTableLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _expandoTableLocalService.dynamicQueryCount(dynamicQuery);
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
		return _expandoTableLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable fetchDefaultTable(
		long companyId, java.lang.String className) {
		return _expandoTableLocalService.fetchDefaultTable(companyId, className);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable fetchDefaultTable(
		long companyId, long classNameId) {
		return _expandoTableLocalService.fetchDefaultTable(companyId,
			classNameId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable fetchExpandoTable(
		long tableId) {
		return _expandoTableLocalService.fetchExpandoTable(tableId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable fetchTable(
		long companyId, long classNameId, java.lang.String name) {
		return _expandoTableLocalService.fetchTable(companyId, classNameId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _expandoTableLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getDefaultTable(companyId, className);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getDefaultTable(companyId, classNameId);
	}

	/**
	* Returns the expando table with the primary key.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table
	* @throws PortalException if a expando table with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getExpandoTable(tableId);
	}

	/**
	* Returns a range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of expando tables
	*/
	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end) {
		return _expandoTableLocalService.getExpandoTables(start, end);
	}

	/**
	* Returns the number of expando tables.
	*
	* @return the number of expando tables
	*/
	@Override
	public int getExpandoTablesCount() {
		return _expandoTableLocalService.getExpandoTablesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _expandoTableLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _expandoTableLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getTable(long, String,
	String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getTable(className, name);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getTable(long, long,
	String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getTable(classNameId, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getTable(companyId, className, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getTable(companyId, classNameId, name);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable getTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.getTable(tableId);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, java.lang.String className) {
		return _expandoTableLocalService.getTables(companyId, className);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, long classNameId) {
		return _expandoTableLocalService.getTables(companyId, classNameId);
	}

	/**
	* Updates the expando table in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoTable the expando table
	* @return the expando table that was updated
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		return _expandoTableLocalService.updateExpandoTable(expandoTable);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoTableLocalService.updateTable(tableId, name);
	}

	@Override
	public ExpandoTableLocalService getWrappedService() {
		return _expandoTableLocalService;
	}

	@Override
	public void setWrappedService(
		ExpandoTableLocalService expandoTableLocalService) {
		_expandoTableLocalService = expandoTableLocalService;
	}

	private ExpandoTableLocalService _expandoTableLocalService;
}