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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExpandoTableLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableLocalService
 * @generated
 */
public class ExpandoTableLocalServiceWrapper
	implements ExpandoTableLocalService,
			   ServiceWrapper<ExpandoTableLocalService> {

	public ExpandoTableLocalServiceWrapper(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable addDefaultTable(
			long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.addDefaultTable(
			companyId, classNameId);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable addDefaultTable(
			long companyId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.addDefaultTable(companyId, className);
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
	@Override
	public com.liferay.expando.kernel.model.ExpandoTable addExpandoTable(
		com.liferay.expando.kernel.model.ExpandoTable expandoTable) {

		return _expandoTableLocalService.addExpandoTable(expandoTable);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable addTable(
			long companyId, long classNameId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.addTable(companyId, classNameId, name);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable addTable(
			long companyId, String className, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.addTable(companyId, className, name);
	}

	/**
	 * Creates a new expando table with the primary key. Does not add the expando table to the database.
	 *
	 * @param tableId the primary key for the new expando table
	 * @return the new expando table
	 */
	@Override
	public com.liferay.expando.kernel.model.ExpandoTable createExpandoTable(
		long tableId) {

		return _expandoTableLocalService.createExpandoTable(tableId);
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
	@Override
	public com.liferay.expando.kernel.model.ExpandoTable deleteExpandoTable(
		com.liferay.expando.kernel.model.ExpandoTable expandoTable) {

		return _expandoTableLocalService.deleteExpandoTable(expandoTable);
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
	@Override
	public com.liferay.expando.kernel.model.ExpandoTable deleteExpandoTable(
			long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.deleteExpandoTable(tableId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteTable(
		com.liferay.expando.kernel.model.ExpandoTable table) {

		_expandoTableLocalService.deleteTable(table);
	}

	@Override
	public void deleteTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_expandoTableLocalService.deleteTable(tableId);
	}

	@Override
	public void deleteTable(long companyId, long classNameId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		_expandoTableLocalService.deleteTable(companyId, classNameId, name);
	}

	@Override
	public void deleteTable(long companyId, String className, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		_expandoTableLocalService.deleteTable(companyId, className, name);
	}

	@Override
	public void deleteTables(long companyId, long classNameId) {
		_expandoTableLocalService.deleteTables(companyId, classNameId);
	}

	@Override
	public void deleteTables(long companyId, String className) {
		_expandoTableLocalService.deleteTables(companyId, className);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl</code>.
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

		return _expandoTableLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _expandoTableLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable fetchDefaultTable(
		long companyId, long classNameId) {

		return _expandoTableLocalService.fetchDefaultTable(
			companyId, classNameId);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable fetchDefaultTable(
		long companyId, String className) {

		return _expandoTableLocalService.fetchDefaultTable(
			companyId, className);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable fetchExpandoTable(
		long tableId) {

		return _expandoTableLocalService.fetchExpandoTable(tableId);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable fetchTable(
		long companyId, long classNameId, String name) {

		return _expandoTableLocalService.fetchTable(
			companyId, classNameId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _expandoTableLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable getDefaultTable(
			long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getDefaultTable(
			companyId, classNameId);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable getDefaultTable(
			long companyId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getDefaultTable(companyId, className);
	}

	/**
	 * Returns the expando table with the primary key.
	 *
	 * @param tableId the primary key of the expando table
	 * @return the expando table
	 * @throws PortalException if a expando table with the primary key could not be found
	 */
	@Override
	public com.liferay.expando.kernel.model.ExpandoTable getExpandoTable(
			long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getExpandoTable(tableId);
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
	@Override
	public java.util.List<com.liferay.expando.kernel.model.ExpandoTable>
		getExpandoTables(int start, int end) {

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
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _expandoTableLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _expandoTableLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable getTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getTable(tableId);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable getTable(
			long companyId, long classNameId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getTable(companyId, classNameId, name);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable getTable(
			long companyId, String className, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoTableLocalService.getTable(companyId, className, name);
	}

	@Override
	public java.util.List<com.liferay.expando.kernel.model.ExpandoTable>
		getTables(long companyId, long classNameId) {

		return _expandoTableLocalService.getTables(companyId, classNameId);
	}

	@Override
	public java.util.List<com.liferay.expando.kernel.model.ExpandoTable>
		getTables(long companyId, String className) {

		return _expandoTableLocalService.getTables(companyId, className);
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
	@Override
	public com.liferay.expando.kernel.model.ExpandoTable updateExpandoTable(
		com.liferay.expando.kernel.model.ExpandoTable expandoTable) {

		return _expandoTableLocalService.updateExpandoTable(expandoTable);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoTable updateTable(
			long tableId, String name)
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