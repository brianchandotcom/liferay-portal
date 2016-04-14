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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoColumn;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;

/**
 * Provides the local service interface for ExpandoColumn. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnLocalServiceUtil
 * @see com.liferay.portlet.expando.service.base.ExpandoColumnLocalServiceBaseImpl
 * @see com.liferay.portlet.expando.service.impl.ExpandoColumnLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExpandoColumnLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoColumnLocalServiceUtil} to access the expando column local service. Add custom service methods to {@link com.liferay.portlet.expando.service.impl.ExpandoColumnLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public ExpandoColumn addColumn(long tableId, java.lang.String name, int type)
		throws PortalException;

	public ExpandoColumn addColumn(long tableId, java.lang.String name,
		int type, java.lang.Object defaultData) throws PortalException;

	/**
	* Adds the expando column to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ExpandoColumn addExpandoColumn(ExpandoColumn expandoColumn);

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	public ExpandoColumn createExpandoColumn(long columnId);

	/**
	* Deletes the expando column from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ExpandoColumn deleteExpandoColumn(ExpandoColumn expandoColumn);

	/**
	* Deletes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws PortalException if a expando column with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ExpandoColumn deleteExpandoColumn(long columnId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn fetchExpandoColumn(long columnId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getColumn(long columnId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getColumn(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getColumn(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getColumn(long tableId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getDefaultTableColumn(long companyId,
		java.lang.String className, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getDefaultTableColumn(long companyId,
		long classNameId, java.lang.String name);

	/**
	* Returns the expando column with the primary key.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws PortalException if a expando column with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoColumn getExpandoColumn(long columnId)
		throws PortalException;

	public ExpandoColumn updateColumn(long columnId, java.lang.String name,
		int type) throws PortalException;

	public ExpandoColumn updateColumn(long columnId, java.lang.String name,
		int type, java.lang.Object defaultData) throws PortalException;

	/**
	* Updates the expando column in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoColumn the expando column
	* @return the expando column that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ExpandoColumn updateExpandoColumn(ExpandoColumn expandoColumn);

	public ExpandoColumn updateTypeSettings(long columnId,
		java.lang.String typeSettings) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnsCount(long companyId, java.lang.String className,
		java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnsCount(long companyId, long classNameId,
		java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnsCount(long tableId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefaultTableColumnsCount(long companyId,
		java.lang.String className);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefaultTableColumnsCount(long companyId, long classNameId);

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExpandoColumnsCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getColumns(long companyId,
		java.lang.String className, java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getColumns(long companyId,
		java.lang.String className, java.lang.String tableName,
		Collection<java.lang.String> columnNames);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getColumns(long companyId, long classNameId,
		java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getColumns(long companyId, long classNameId,
		java.lang.String tableName, Collection<java.lang.String> names);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getColumns(long tableId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getColumns(long tableId,
		Collection<java.lang.String> names);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getDefaultTableColumns(long companyId,
		java.lang.String className);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getDefaultTableColumns(long companyId,
		long classNameId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoColumn> getExpandoColumns(int start, int end);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void deleteColumn(ExpandoColumn column);

	public void deleteColumn(long columnId) throws PortalException;

	public void deleteColumn(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws PortalException;

	public void deleteColumn(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws PortalException;

	public void deleteColumn(long tableId, java.lang.String name);

	public void deleteColumns(long companyId, java.lang.String className,
		java.lang.String tableName) throws PortalException;

	public void deleteColumns(long companyId, long classNameId,
		java.lang.String tableName) throws PortalException;

	public void deleteColumns(long tableId);
}