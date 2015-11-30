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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for ExpandoRow. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowLocalServiceUtil
 * @see com.liferay.portlet.expando.service.base.ExpandoRowLocalServiceBaseImpl
 * @see com.liferay.portlet.expando.service.impl.ExpandoRowLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExpandoRowLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoRowLocalServiceUtil} to access the expando row local service. Add custom service methods to {@link com.liferay.portlet.expando.service.impl.ExpandoRowLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the expando row to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow);

	public com.liferay.portlet.expando.model.ExpandoRow addRow(long tableId,
		long classPK) throws PortalException;

	/**
	* Creates a new expando row with the primary key. Does not add the expando row to the database.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	public com.liferay.portlet.expando.model.ExpandoRow createExpandoRow(
		long rowId);

	/**
	* Deletes the expando row from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow);

	/**
	* Deletes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row that was removed
	* @throws PortalException if a expando row with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.expando.model.ExpandoRow deleteExpandoRow(
		long rowId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public void deleteRow(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK) throws PortalException;

	public void deleteRow(long companyId, long classNameId,
		java.lang.String tableName, long classPK) throws PortalException;

	public void deleteRow(com.liferay.portlet.expando.model.ExpandoRow row);

	public void deleteRow(long rowId) throws PortalException;

	public void deleteRow(long tableId, long classPK) throws PortalException;

	public void deleteRows(long classPK);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow fetchExpandoRow(
		long rowId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow fetchRow(long tableId,
		long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, java.lang.String className, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long companyId, long classNameId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefaultTableRowsCount(long companyId,
		java.lang.String className);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefaultTableRowsCount(long companyId, long classNameId);

	/**
	* Returns the expando row with the primary key.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row
	* @throws PortalException if a expando row with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getExpandoRows(
		int start, int end);

	/**
	* Returns the number of expando rows.
	*
	* @return the number of expando rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExpandoRowsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		java.lang.String className, java.lang.String tableName, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long companyId,
		long classNameId, java.lang.String tableName, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long rowId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.expando.model.ExpandoRow getRow(long tableId,
		long classPK) throws PortalException;

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRows(long, String,
	String, int, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, java.lang.String className, java.lang.String tableName,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long companyId, long classNameId, java.lang.String tableName,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end);

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getRowsCount(long, String,
	String)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowsCount(java.lang.String className,
		java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowsCount(long companyId, java.lang.String className,
		java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowsCount(long companyId, long classNameId,
		java.lang.String tableName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowsCount(long tableId);

	/**
	* Updates the expando row in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoRow the expando row
	* @return the expando row that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow);
}