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
 * Provides a wrapper for {@link ExpandoValueLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueLocalService
 * @generated
 */
@ProviderType
public class ExpandoValueLocalServiceWrapper implements ExpandoValueLocalService,
	ServiceWrapper<ExpandoValueLocalService> {
	public ExpandoValueLocalServiceWrapper(
		ExpandoValueLocalService expandoValueLocalService) {
		_expandoValueLocalService = expandoValueLocalService;
	}

	/**
	* Adds the expando value to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was added
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return _expandoValueLocalService.addExpandoValue(expandoValue);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, Object)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(className, tableName,
			columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long classNameId, long tableId, long columnId, long classPK,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(classNameId, tableId,
			columnId, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Number data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, data);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.util.Map<java.util.Locale, ?> dataMap,
		java.util.Locale defautlLocale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.addValue(companyId, className,
			tableName, columnName, classPK, dataMap, defautlLocale);
	}

	@Override
	public void addValues(long classNameId, long tableId,
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> columns,
		long classPK, java.util.Map<java.lang.String, java.lang.String> data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.addValues(classNameId, tableId, columns,
			classPK, data);
	}

	@Override
	public void addValues(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> attributes)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.addValues(companyId, className, tableName,
			classPK, attributes);
	}

	@Override
	public void addValues(long companyId, long classNameId,
		java.lang.String tableName, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> attributes)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.addValues(companyId, classNameId, tableName,
			classPK, attributes);
	}

	/**
	* Creates a new expando value with the primary key. Does not add the expando value to the database.
	*
	* @param valueId the primary key for the new expando value
	* @return the new expando value
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue createExpandoValue(
		long valueId) {
		return _expandoValueLocalService.createExpandoValue(valueId);
	}

	@Override
	public void deleteColumnValues(long columnId) {
		_expandoValueLocalService.deleteColumnValues(columnId);
	}

	/**
	* Deletes the expando value from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was removed
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue deleteExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return _expandoValueLocalService.deleteExpandoValue(expandoValue);
	}

	/**
	* Deletes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value that was removed
	* @throws PortalException if a expando value with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue deleteExpandoValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.deleteExpandoValue(valueId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteRowValues(long rowId) {
		_expandoValueLocalService.deleteRowValues(rowId);
	}

	@Override
	public void deleteTableValues(long tableId) {
		_expandoValueLocalService.deleteTableValues(tableId);
	}

	@Override
	public void deleteValue(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.deleteValue(columnId, rowId);
	}

	@Override
	public void deleteValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.deleteValue(companyId, className, tableName,
			columnName, classPK);
	}

	@Override
	public void deleteValue(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.deleteValue(companyId, classNameId,
			tableName, columnName, classPK);
	}

	@Override
	public void deleteValue(
		com.liferay.portlet.expando.model.ExpandoValue value) {
		_expandoValueLocalService.deleteValue(value);
	}

	@Override
	public void deleteValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueLocalService.deleteValue(valueId);
	}

	@Override
	public void deleteValues(java.lang.String className, long classPK) {
		_expandoValueLocalService.deleteValues(className, classPK);
	}

	@Override
	public void deleteValues(long classNameId, long classPK) {
		_expandoValueLocalService.deleteValues(classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _expandoValueLocalService.dynamicQuery();
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
		return _expandoValueLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _expandoValueLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _expandoValueLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _expandoValueLocalService.dynamicQueryCount(dynamicQuery);
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
		return _expandoValueLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue fetchExpandoValue(
		long valueId) {
		return _expandoValueLocalService.fetchExpandoValue(valueId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _expandoValueLocalService.getActionableDynamicQuery();
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getColumnValues(long,
	String, String, String, String, int, int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end) {
		return _expandoValueLocalService.getColumnValues(className, tableName,
			columnName, data, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long columnId, int start, int end) {
		return _expandoValueLocalService.getColumnValues(columnId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end) {
		return _expandoValueLocalService.getColumnValues(companyId, className,
			tableName, columnName, data, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int start, int end) {
		return _expandoValueLocalService.getColumnValues(companyId, className,
			tableName, columnName, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end) {
		return _expandoValueLocalService.getColumnValues(companyId,
			classNameId, tableName, columnName, data, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String columnName, int start, int end) {
		return _expandoValueLocalService.getColumnValues(companyId,
			classNameId, tableName, columnName, start, end);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getColumnValuesCount(long,
	String, String, String, String)}
	*/
	@Deprecated
	@Override
	public int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data) {
		return _expandoValueLocalService.getColumnValuesCount(className,
			tableName, columnName, data);
	}

	@Override
	public int getColumnValuesCount(long columnId) {
		return _expandoValueLocalService.getColumnValuesCount(columnId);
	}

	@Override
	public int getColumnValuesCount(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName) {
		return _expandoValueLocalService.getColumnValuesCount(companyId,
			className, tableName, columnName);
	}

	@Override
	public int getColumnValuesCount(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data) {
		return _expandoValueLocalService.getColumnValuesCount(companyId,
			className, tableName, columnName, data);
	}

	@Override
	public int getColumnValuesCount(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName) {
		return _expandoValueLocalService.getColumnValuesCount(companyId,
			classNameId, tableName, columnName);
	}

	@Override
	public int getColumnValuesCount(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data) {
		return _expandoValueLocalService.getColumnValuesCount(companyId,
			classNameId, tableName, columnName, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long)}
	*/
	@Deprecated
	@Override
	public java.io.Serializable getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	@Override
	public float[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	@Override
	public int[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	@Override
	public int getData(java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	@Override
	public short[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	@Override
	public double[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	@Override
	public boolean getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	@Override
	public float getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	@Override
	public long getData(java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	@Override
	public java.lang.String[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	@Override
	public double getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	@Override
	public long[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	@Override
	public short getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	@Override
	public java.util.Date[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	@Override
	public boolean[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	@Override
	public java.util.Date getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	@Override
	public java.lang.String getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(className, tableName,
			columnName, classPK, defaultData);
	}

	@Override
	public java.io.Serializable getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK);
	}

	@Override
	public java.lang.String getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public boolean getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.util.Date[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public double[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.lang.Number[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.Number[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public short getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public float getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public float[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.util.Map<?, ?> getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.util.Map<?, ?> defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.lang.String[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public short[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public long[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public boolean[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public double getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public long getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public int[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.util.Date getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public int getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.lang.Number getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.Number defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnName, classPK, defaultData);
	}

	@Override
	public java.util.Map<java.lang.String, java.io.Serializable> getData(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.util.Collection<java.lang.String> columnNames, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getData(companyId, className,
			tableName, columnNames, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		long companyId, java.lang.String className,
		java.lang.String columnName, int start, int end) {
		return _expandoValueLocalService.getDefaultTableColumnValues(companyId,
			className, columnName, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		long companyId, long classNameId, java.lang.String columnName,
		int start, int end) {
		return _expandoValueLocalService.getDefaultTableColumnValues(companyId,
			classNameId, columnName, start, end);
	}

	@Override
	public int getDefaultTableColumnValuesCount(long companyId,
		java.lang.String className, java.lang.String columnName) {
		return _expandoValueLocalService.getDefaultTableColumnValuesCount(companyId,
			className, columnName);
	}

	@Override
	public int getDefaultTableColumnValuesCount(long companyId,
		long classNameId, java.lang.String columnName) {
		return _expandoValueLocalService.getDefaultTableColumnValuesCount(companyId,
			classNameId, columnName);
	}

	/**
	* Returns the expando value with the primary key.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value
	* @throws PortalException if a expando value with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getExpandoValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getExpandoValue(valueId);
	}

	/**
	* Returns a range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of expando values
	*/
	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getExpandoValues(
		int start, int end) {
		return _expandoValueLocalService.getExpandoValues(start, end);
	}

	/**
	* Returns the number of expando values.
	*
	* @return the number of expando values
	*/
	@Override
	public int getExpandoValuesCount() {
		return _expandoValueLocalService.getExpandoValuesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _expandoValueLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _expandoValueLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long companyId, java.lang.String className, java.lang.String tableName,
		long classPK, int start, int end) {
		return _expandoValueLocalService.getRowValues(companyId, className,
			tableName, classPK, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long companyId, long classNameId, java.lang.String tableName,
		long classPK, int start, int end) {
		return _expandoValueLocalService.getRowValues(companyId, classNameId,
			tableName, classPK, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId) {
		return _expandoValueLocalService.getRowValues(rowId);
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId, int start, int end) {
		return _expandoValueLocalService.getRowValues(rowId, start, end);
	}

	@Override
	public int getRowValuesCount(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK) {
		return _expandoValueLocalService.getRowValuesCount(companyId,
			className, tableName, classPK);
	}

	@Override
	public int getRowValuesCount(long companyId, long classNameId,
		java.lang.String tableName, long classPK) {
		return _expandoValueLocalService.getRowValuesCount(companyId,
			classNameId, tableName, classPK);
	}

	@Override
	public int getRowValuesCount(long rowId) {
		return _expandoValueLocalService.getRowValuesCount(rowId);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getValue(long, String,
	String, String, long)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return _expandoValueLocalService.getValue(className, tableName,
			columnName, classPK);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getValue(long, long, String,
	String, long)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return _expandoValueLocalService.getValue(classNameId, tableName,
			columnName, classPK);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getValue(columnId, rowId);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return _expandoValueLocalService.getValue(companyId, className,
			tableName, columnName, classPK);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return _expandoValueLocalService.getValue(companyId, classNameId,
			tableName, columnName, classPK);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(
		long tableId, long columnId, long classPK) {
		return _expandoValueLocalService.getValue(tableId, columnId, classPK);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue getValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueLocalService.getValue(valueId);
	}

	/**
	* Updates the expando value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was updated
	*/
	@Override
	public com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return _expandoValueLocalService.updateExpandoValue(expandoValue);
	}

	@Override
	public ExpandoValueLocalService getWrappedService() {
		return _expandoValueLocalService;
	}

	@Override
	public void setWrappedService(
		ExpandoValueLocalService expandoValueLocalService) {
		_expandoValueLocalService = expandoValueLocalService;
	}

	private ExpandoValueLocalService _expandoValueLocalService;
}