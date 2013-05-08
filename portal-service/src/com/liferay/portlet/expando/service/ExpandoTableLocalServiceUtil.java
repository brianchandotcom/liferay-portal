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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ExpandoTable. This utility wraps
 * {@link com.liferay.portlet.expando.service.impl.ExpandoTableLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableLocalService
 * @see com.liferay.portlet.expando.service.base.ExpandoTableLocalServiceBaseImpl
 * @see com.liferay.portlet.expando.service.impl.ExpandoTableLocalServiceImpl
 * @generated
 */
public class ExpandoTableLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.expando.service.impl.ExpandoTableLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the expando table to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoTable the expando table
	* @return the expando table that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable addExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addExpandoTable(expandoTable);
	}

	/**
	* Creates a new expando table with the primary key. Does not add the expando table to the database.
	*
	* @param tableId the primary key for the new expando table
	* @return the new expando table
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable createExpandoTable(
		long tableId) {
		return getService().createExpandoTable(tableId);
	}

	/**
	* Deletes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table that was removed
	* @throws PortalException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable deleteExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteExpandoTable(tableId);
	}

	/**
	* Deletes the expando table from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoTable the expando table
	* @return the expando table that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable deleteExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteExpandoTable(expandoTable);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchExpandoTable(tableId);
	}

	/**
	* Returns the expando table with the primary key.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table
	* @throws PortalException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable getExpandoTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoTable(tableId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getExpandoTables(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoTables(start, end);
	}

	/**
	* Returns the number of expando tables.
	*
	* @return the number of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int getExpandoTablesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExpandoTablesCount();
	}

	/**
	* Updates the expando table in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoTable the expando table
	* @return the expando table that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable updateExpandoTable(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateExpandoTable(expandoTable);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addDefaultTable(companyId, classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addDefaultTable(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(companyId, classNameId, name);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addTable(long, long,
	String)}
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(companyId, className, name);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addTable(long, String,
	String)}
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable addTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTable(className, name);
	}

	public static void deleteTable(
		com.liferay.portlet.expando.model.ExpandoTable table)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(table);
	}

	public static void deleteTable(long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(tableId);
	}

	public static void deleteTable(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(companyId, classNameId, name);
	}

	public static void deleteTable(long companyId, java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTable(companyId, className, name);
	}

	public static void deleteTables(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTables(companyId, classNameId);
	}

	public static void deleteTables(long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTables(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDefaultTable(companyId, classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDefaultTable(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTable(companyId, classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTable(companyId, classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getDefaultTable(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultTable(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long tableId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(companyId, classNameId, name);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getTable(long, long,
	String)}
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		long companyId, java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(companyId, className, name);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getTable(long, String,
	String)}
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable getTable(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTable(className, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTables(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> getTables(
		long companyId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTables(companyId, className);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateTable(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTable(tableId, name);
	}

	public static ExpandoTableLocalService getService() {
		if (_service == null) {
			_service = (ExpandoTableLocalService)PortalBeanLocatorUtil.locate(ExpandoTableLocalService.class.getName());

			ReferenceRegistry.registerReference(ExpandoTableLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ExpandoTableLocalService service) {
	}

	private static ExpandoTableLocalService _service;
}