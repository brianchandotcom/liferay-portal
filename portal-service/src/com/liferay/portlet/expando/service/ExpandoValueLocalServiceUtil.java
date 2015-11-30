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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ExpandoValue. This utility wraps
 * {@link com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueLocalService
 * @see com.liferay.portlet.expando.service.base.ExpandoValueLocalServiceBaseImpl
 * @see com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExpandoValueLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the expando value to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was added
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue addExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return getService().addExpandoValue(expandoValue);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, Object)}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #addValue(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(className, tableName, columnName, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long classNameId, long tableId, long columnId, long classPK,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(classNameId, tableId, columnId, classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Number data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, data);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.util.Map<java.util.Locale, ?> dataMap,
		java.util.Locale defautlLocale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addValue(companyId, className, tableName, columnName,
			classPK, dataMap, defautlLocale);
	}

	public static void addValues(long classNameId, long tableId,
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> columns,
		long classPK, java.util.Map<java.lang.String, java.lang.String> data)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addValues(classNameId, tableId, columns, classPK, data);
	}

	public static void addValues(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> attributes)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addValues(companyId, className, tableName, classPK, attributes);
	}

	public static void addValues(long companyId, long classNameId,
		java.lang.String tableName, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> attributes)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addValues(companyId, classNameId, tableName, classPK, attributes);
	}

	/**
	* Creates a new expando value with the primary key. Does not add the expando value to the database.
	*
	* @param valueId the primary key for the new expando value
	* @return the new expando value
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue createExpandoValue(
		long valueId) {
		return getService().createExpandoValue(valueId);
	}

	public static void deleteColumnValues(long columnId) {
		getService().deleteColumnValues(columnId);
	}

	/**
	* Deletes the expando value from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was removed
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue deleteExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return getService().deleteExpandoValue(expandoValue);
	}

	/**
	* Deletes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value that was removed
	* @throws PortalException if a expando value with the primary key could not be found
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue deleteExpandoValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteExpandoValue(valueId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRowValues(long rowId) {
		getService().deleteRowValues(rowId);
	}

	public static void deleteTableValues(long tableId) {
		getService().deleteTableValues(tableId);
	}

	public static void deleteValue(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteValue(columnId, rowId);
	}

	public static void deleteValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteValue(companyId, className, tableName, columnName, classPK);
	}

	public static void deleteValue(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteValue(companyId, classNameId, tableName, columnName, classPK);
	}

	public static void deleteValue(
		com.liferay.portlet.expando.model.ExpandoValue value) {
		getService().deleteValue(value);
	}

	public static void deleteValue(long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteValue(valueId);
	}

	public static void deleteValues(java.lang.String className, long classPK) {
		getService().deleteValues(className, classPK);
	}

	public static void deleteValues(long classNameId, long classPK) {
		getService().deleteValues(classNameId, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchExpandoValue(
		long valueId) {
		return getService().fetchExpandoValue(valueId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getColumnValues(long,
	String, String, String, String, int, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end) {
		return getService()
				   .getColumnValues(className, tableName, columnName, data,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long columnId, int start, int end) {
		return getService().getColumnValues(columnId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end) {
		return getService()
				   .getColumnValues(companyId, className, tableName,
			columnName, data, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int start, int end) {
		return getService()
				   .getColumnValues(companyId, className, tableName,
			columnName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end) {
		return getService()
				   .getColumnValues(companyId, classNameId, tableName,
			columnName, data, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String columnName, int start, int end) {
		return getService()
				   .getColumnValues(companyId, classNameId, tableName,
			columnName, start, end);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getColumnValuesCount(long,
	String, String, String, String)}
	*/
	@Deprecated
	public static int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data) {
		return getService()
				   .getColumnValuesCount(className, tableName, columnName, data);
	}

	public static int getColumnValuesCount(long columnId) {
		return getService().getColumnValuesCount(columnId);
	}

	public static int getColumnValuesCount(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName) {
		return getService()
				   .getColumnValuesCount(companyId, className, tableName,
			columnName);
	}

	public static int getColumnValuesCount(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data) {
		return getService()
				   .getColumnValuesCount(companyId, className, tableName,
			columnName, data);
	}

	public static int getColumnValuesCount(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName) {
		return getService()
				   .getColumnValuesCount(companyId, classNameId, tableName,
			columnName);
	}

	public static int getColumnValuesCount(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data) {
		return getService()
				   .getColumnValuesCount(companyId, classNameId, tableName,
			columnName, data);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long)}
	*/
	@Deprecated
	public static java.io.Serializable getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getData(className, tableName, columnName, classPK);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	public static float[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	public static int[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, int[])}
	*/
	@Deprecated
	public static int getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	public static short[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	public static double[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	public static boolean getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, float[])}
	*/
	@Deprecated
	public static float getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	public static long getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	public static java.lang.String[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, double[])}
	*/
	@Deprecated
	public static double getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, long[])}
	*/
	@Deprecated
	public static long[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, short[])}
	*/
	@Deprecated
	public static short getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	public static java.util.Date[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, boolean[])}
	*/
	@Deprecated
	public static boolean[] getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, Date[])}
	*/
	@Deprecated
	public static java.util.Date getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getData(long, String,
	String, String, long, String[])}
	*/
	@Deprecated
	public static java.lang.String getData(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(className, tableName, columnName, classPK,
			defaultData);
	}

	public static java.io.Serializable getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName, classPK);
	}

	public static java.lang.String getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static boolean getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.util.Date[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static double[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.lang.Number[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.Number[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static short getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static float getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static float[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.util.Map<?, ?> getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.util.Map<?, ?> defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.lang.String[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static short[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static long[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static boolean[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static double getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static long getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static int[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.util.Date getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static int getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.lang.Number getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Number defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnName,
			classPK, defaultData);
	}

	public static java.util.Map<java.lang.String, java.io.Serializable> getData(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.util.Collection<java.lang.String> columnNames, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getData(companyId, className, tableName, columnNames,
			classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		long companyId, java.lang.String className,
		java.lang.String columnName, int start, int end) {
		return getService()
				   .getDefaultTableColumnValues(companyId, className,
			columnName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		long companyId, long classNameId, java.lang.String columnName,
		int start, int end) {
		return getService()
				   .getDefaultTableColumnValues(companyId, classNameId,
			columnName, start, end);
	}

	public static int getDefaultTableColumnValuesCount(long companyId,
		java.lang.String className, java.lang.String columnName) {
		return getService()
				   .getDefaultTableColumnValuesCount(companyId, className,
			columnName);
	}

	public static int getDefaultTableColumnValuesCount(long companyId,
		long classNameId, java.lang.String columnName) {
		return getService()
				   .getDefaultTableColumnValuesCount(companyId, classNameId,
			columnName);
	}

	/**
	* Returns the expando value with the primary key.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value
	* @throws PortalException if a expando value with the primary key could not be found
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue getExpandoValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getExpandoValue(valueId);
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
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getExpandoValues(
		int start, int end) {
		return getService().getExpandoValues(start, end);
	}

	/**
	* Returns the number of expando values.
	*
	* @return the number of expando values
	*/
	public static int getExpandoValuesCount() {
		return getService().getExpandoValuesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long companyId, java.lang.String className, java.lang.String tableName,
		long classPK, int start, int end) {
		return getService()
				   .getRowValues(companyId, className, tableName, classPK,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long companyId, long classNameId, java.lang.String tableName,
		long classPK, int start, int end) {
		return getService()
				   .getRowValues(companyId, classNameId, tableName, classPK,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId) {
		return getService().getRowValues(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		long rowId, int start, int end) {
		return getService().getRowValues(rowId, start, end);
	}

	public static int getRowValuesCount(long companyId,
		java.lang.String className, java.lang.String tableName, long classPK) {
		return getService()
				   .getRowValuesCount(companyId, className, tableName, classPK);
	}

	public static int getRowValuesCount(long companyId, long classNameId,
		java.lang.String tableName, long classPK) {
		return getService()
				   .getRowValuesCount(companyId, classNameId, tableName, classPK);
	}

	public static int getRowValuesCount(long rowId) {
		return getService().getRowValuesCount(rowId);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getValue(long, String,
	String, String, long)}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return getService().getValue(className, tableName, columnName, classPK);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #getValue(long, long, String,
	String, long)}
	*/
	@Deprecated
	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return getService().getValue(classNameId, tableName, columnName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getValue(columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return getService()
				   .getValue(companyId, className, tableName, columnName,
			classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long companyId, long classNameId, java.lang.String tableName,
		java.lang.String columnName, long classPK) {
		return getService()
				   .getValue(companyId, classNameId, tableName, columnName,
			classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long tableId, long columnId, long classPK) {
		return getService().getValue(tableId, columnId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		long valueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getValue(valueId);
	}

	/**
	* Updates the expando value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was updated
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue updateExpandoValue(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return getService().updateExpandoValue(expandoValue);
	}

	public static ExpandoValueLocalService getService() {
		if (_service == null) {
			_service = (ExpandoValueLocalService)PortalBeanLocatorUtil.locate(ExpandoValueLocalService.class.getName());

			ReferenceRegistry.registerReference(ExpandoValueLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ExpandoValueLocalService service) {
	}

	private static ExpandoValueLocalService _service;
}