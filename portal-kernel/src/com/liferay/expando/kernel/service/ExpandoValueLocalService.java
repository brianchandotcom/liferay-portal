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
import com.liferay.expando.kernel.model.ExpandoValue;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for ExpandoValue. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueLocalServiceUtil
 * @see com.liferay.portlet.expando.service.base.ExpandoValueLocalServiceBaseImpl
 * @see com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExpandoValueLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoValueLocalServiceUtil} to access the expando value local service. Add custom service methods to {@link com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] defaultData) throws PortalException;

	/**
	* Adds the expando value to the database. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ExpandoValue addExpandoValue(ExpandoValue expandoValue);

	public ExpandoValue addValue(long classNameId, long tableId, long columnId,
		long classPK, java.lang.String data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		JSONObject data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.Number data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.Number[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.Object data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		Date data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		Date[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		Map<Locale, ?> dataMap, Locale defautlLocale) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short data) throws PortalException;

	public ExpandoValue addValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] data) throws PortalException;

	/**
	* Creates a new expando value with the primary key. Does not add the expando value to the database.
	*
	* @param valueId the primary key for the new expando value
	* @return the new expando value
	*/
	public ExpandoValue createExpandoValue(long valueId);

	/**
	* Deletes the expando value from the database. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ExpandoValue deleteExpandoValue(ExpandoValue expandoValue);

	/**
	* Deletes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value that was removed
	* @throws PortalException if a expando value with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ExpandoValue deleteExpandoValue(long valueId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue fetchExpandoValue(long valueId);

	/**
	* Returns the expando value with the primary key.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value
	* @throws PortalException if a expando value with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue getExpandoValue(long valueId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue getValue(long columnId, long rowId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue getValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue getValue(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue getValue(long tableId, long columnId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpandoValue getValue(long valueId) throws PortalException;

	/**
	* Updates the expando value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param expandoValue the expando value
	* @return the expando value that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ExpandoValue updateExpandoValue(ExpandoValue expandoValue);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONObject getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		JSONObject defaultData) throws PortalException;

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
	public double getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public float getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public float[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnValuesCount(long columnId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnValuesCount(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnValuesCount(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnValuesCount(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getColumnValuesCount(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefaultTableColumnValuesCount(long companyId,
		java.lang.String className, java.lang.String columnName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefaultTableColumnValuesCount(long companyId,
		long classNameId, java.lang.String columnName);

	/**
	* Returns the number of expando values.
	*
	* @return the number of expando values
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExpandoValuesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowValuesCount(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowValuesCount(long companyId, long classNameId,
		java.lang.String tableName, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRowValuesCount(long rowId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Serializable getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.Number getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.Number defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.Number[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.Number[] defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String defaultData) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.String[] defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Date getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		Date defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Date[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		Date[] defaultData) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<ExpandoValue> getColumnValues(long columnId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getColumnValues(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getColumnValues(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getColumnValues(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getColumnValues(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName,
		java.lang.String data, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getDefaultTableColumnValues(long companyId,
		java.lang.String className, java.lang.String columnName, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getDefaultTableColumnValues(long companyId,
		long classNameId, java.lang.String columnName, int start, int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getExpandoValues(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getRowValues(long companyId,
		java.lang.String className, java.lang.String tableName, long classPK,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getRowValues(long companyId, long classNameId,
		java.lang.String tableName, long classPK, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getRowValues(long rowId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ExpandoValue> getRowValues(long rowId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<?, ?> getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		Map<?, ?> defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<java.lang.String, Serializable> getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		Collection<java.lang.String> columnNames, long classPK)
		throws PortalException;

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public short getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short defaultData) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public short[] getData(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] defaultData) throws PortalException;

	public void addValues(long classNameId, long tableId,
		List<ExpandoColumn> columns, long classPK,
		Map<java.lang.String, java.lang.String> data) throws PortalException;

	public void addValues(long companyId, java.lang.String className,
		java.lang.String tableName, long classPK,
		Map<java.lang.String, Serializable> attributes)
		throws PortalException;

	public void addValues(long companyId, long classNameId,
		java.lang.String tableName, long classPK,
		Map<java.lang.String, Serializable> attributes)
		throws PortalException;

	public void deleteColumnValues(long columnId);

	public void deleteRowValues(long rowId);

	public void deleteTableValues(long tableId);

	public void deleteValue(ExpandoValue value);

	public void deleteValue(long columnId, long rowId)
		throws PortalException;

	public void deleteValue(long companyId, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws PortalException;

	public void deleteValue(long companyId, long classNameId,
		java.lang.String tableName, java.lang.String columnName, long classPK)
		throws PortalException;

	public void deleteValue(long valueId) throws PortalException;

	public void deleteValues(java.lang.String className, long classPK);

	public void deleteValues(long classNameId, long classPK);
}