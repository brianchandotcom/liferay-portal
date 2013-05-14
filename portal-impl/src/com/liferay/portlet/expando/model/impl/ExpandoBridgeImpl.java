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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ExpandoBridgeImpl implements ExpandoBridge {

	public ExpandoBridgeImpl(long companyId, String className) {
		this(companyId, className, 0);
	}

	public ExpandoBridgeImpl(long companyId, String className, long classPK) {
		_companyId = companyId;

		if (_companyId == 0) {
			_companyId = CompanyThreadLocal.getCompanyId();
		}

		_className = className;
		_classPK = classPK;

		if (IndexerRegistryUtil.getIndexer(className) == null) {
			setIndexEnabled(true);
		}
	}

	@Override
	public void addAttribute(String name) throws PortalException {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		addAttribute(name, ExpandoColumnConstants.STRING, null, secure);
	}

	@Override
	public void addAttribute(String name, boolean secure)
		throws PortalException {

		addAttribute(name, ExpandoColumnConstants.STRING, null, secure);
	}

	@Override
	public void addAttribute(String name, int type) throws PortalException {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		addAttribute(name, type, null, secure);
	}

	@Override
	public void addAttribute(String name, int type, boolean secure)
		throws PortalException {

		addAttribute(name, type, null, secure);
	}

	@Override
	public void addAttribute(String name, int type, Serializable defaultValue)
		throws PortalException {

		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		addAttribute(name, type, defaultValue, secure);
	}

	@Override
	public void addAttribute(
			String name, int type, Serializable defaultValue, boolean secure)
		throws PortalException {

		try {
			ExpandoTable table = getTable();

			if (secure) {
				ExpandoColumnServiceUtil.addColumn(
					table.getTableId(), name, type, defaultValue);
			}
			else {
				ExpandoColumnLocalServiceUtil.addColumn(
					table.getTableId(), name, type, defaultValue);
			}
		}
		catch (Exception e) {
			if (e instanceof PortalException) {
				throw (PortalException)e;
			}
			else {
				_log.error(e, e);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExpandoBridgeImpl)) {
			return false;
		}

		ExpandoBridgeImpl expandoBridgeImpl = (ExpandoBridgeImpl)obj;

		try {
			ExpandoTable table1 = getTable();

			long tableId1 = table1.getTableId();

			ExpandoTable table2 = expandoBridgeImpl.getTable();

			long tableId2 = table2.getTableId();

			if (tableId1 != tableId2) {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}

		for (ExpandoColumn column : getAttributeColumns()) {
			Serializable attribute1 = getAttribute(column.getName());
			Serializable attribute2 = expandoBridgeImpl.getAttribute(
				column.getName());

			if (!equals(column.getType(), attribute1, attribute2)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Serializable getAttribute(String name) {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isExportInProcess()) {
			secure = false;
		}

		return getAttribute(name, secure);
	}

	@Override
	public Serializable getAttribute(String name, boolean secure) {
		Serializable data = null;

		try {
			if (secure) {
				data = ExpandoValueServiceUtil.getData(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, name, _classPK);
			}
			else {
				data = ExpandoValueLocalServiceUtil.getData(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, name, _classPK);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return data;
	}

	@Override
	public Serializable getAttributeDefault(String name) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_companyId, _className, name);

			return column.getDefaultValue();
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		List<String> columnNames = new ArrayList<String>();

		for (ExpandoColumn column : getAttributeColumns()) {
			columnNames.add(column.getName());
		}

		return Collections.enumeration(columnNames);
	}

	@Override
	public UnicodeProperties getAttributeProperties(String name) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_companyId, _className, name);

			return column.getTypeSettingsProperties();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Properties for " + name, e);
			}

			return new UnicodeProperties(true);
		}
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isExportInProcess()) {
			secure = false;
		}

		return getAttributes(secure);
	}

	@Override
	public Map<String, Serializable> getAttributes(boolean secure) {
		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		for (ExpandoColumn column : getAttributeColumns()) {
			attributes.put(
				column.getName(), getAttribute(column.getName(), secure));
		}

		return attributes;
	}

	@Override
	public Map<String, Serializable> getAttributes(Collection<String> names) {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isExportInProcess()) {
			secure = false;
		}

		return getAttributes(names, secure);
	}

	@Override
	public Map<String, Serializable> getAttributes(
		Collection<String> names, boolean secure) {

		Map<String, Serializable> attributeValues = null;

		try {
			if (secure) {
				attributeValues = ExpandoValueServiceUtil.getData(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, names, _classPK);
			}
			else {
				attributeValues = ExpandoValueLocalServiceUtil.getData(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, names, _classPK);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return attributeValues;
	}

	@Override
	public int getAttributeType(String name) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_companyId, _className, name);

			return column.getType();
		}
		catch (Exception e) {
			_log.error(e, e);

			return 0;
		}
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public boolean hasAttribute(String name) {
		ExpandoColumn column = null;

		try {
			column = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
				_companyId, _className, name);
		}
		catch (Exception e) {
		}

		if (column != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isIndexEnabled() {
		if (_indexEnabled && (_classPK > 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void reindex() {
		if (!isIndexEnabled()) {
			return;
		}

		Indexer indexer = IndexerRegistryUtil.getIndexer(_className);

		if (indexer != null) {
			try {
				indexer.reindex(_className, _classPK);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Override
	public void setAttribute(String name, Serializable value) {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		setAttribute(name, value, secure);
	}

	@Override
	public void setAttribute(String name, Serializable value, boolean secure) {
		if (_classPK <= 0) {
			throw new UnsupportedOperationException(
				"Class primary key is less than 0");
		}

		try {
			if (secure) {
				ExpandoValueServiceUtil.addValue(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, name, _classPK,
					value);
			}
			else {
				ExpandoValueLocalServiceUtil.addValue(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, name, _classPK,
					value);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void setAttributeDefault(String name, Serializable defaultValue) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_companyId, _className, name);

			ExpandoColumnServiceUtil.updateColumn(
				column.getColumnId(), column.getName(), column.getType(),
				defaultValue);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void setAttributeProperties(
		String name, UnicodeProperties properties) {

		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		setAttributeProperties(name, properties, secure);
	}

	@Override
	public void setAttributeProperties(
		String name, UnicodeProperties properties, boolean secure) {

		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_companyId, _className, name);

			if (secure) {
				ExpandoColumnServiceUtil.updateTypeSettings(
					column.getColumnId(), properties.toString());
			}
			else {
				ExpandoColumnLocalServiceUtil.updateTypeSettings(
					column.getColumnId(), properties.toString());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void setAttributes(Map<String, Serializable> attributes) {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		setAttributes(attributes, secure);
	}

	@Override
	public void setAttributes(
		Map<String, Serializable> attributes, boolean secure) {

		if (_classPK <= 0) {
			throw new UnsupportedOperationException(
				"Class primary key is less than 0");
		}

		if ((attributes == null) || attributes.isEmpty()) {
			return;
		}

		try {
			if (secure) {
				ExpandoValueServiceUtil.addValues(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, _classPK,
					attributes);
			}
			else {
				ExpandoValueLocalServiceUtil.addValues(
					_companyId, _className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, _classPK,
					attributes);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void setAttributes(ServiceContext serviceContext) {
		boolean secure =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_WRITE_CHECK_BY_DEFAULT;

		if (ExportImportThreadLocal.isImportInProcess()) {
			secure = false;
		}

		setAttributes(serviceContext, secure);
	}

	@Override
	public void setAttributes(ServiceContext serviceContext, boolean secure) {
		if (serviceContext == null) {
			return;
		}

		setAttributes(serviceContext.getExpandoBridgeAttributes(), secure);
	}

	@Override
	public void setClassName(String className) {
		_className = className;
	}

	@Override
	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setIndexEnabled(boolean indexEnabled) {
		_indexEnabled = indexEnabled;
	}

	protected boolean equals(
		int type, Serializable serializable1, Serializable serializable2) {

		if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			Boolean[] array1 = (Boolean[])serializable1;
			Boolean[] array2 = (Boolean[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			Date[] array1 = (Date[])serializable1;
			Date[] array2 = (Date[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			double[] array1 = (double[])serializable1;
			double[] array2 = (double[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			float[] array1 = (float[])serializable1;
			float[] array2 = (float[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			int[] array1 = (int[])serializable1;
			int[] array2 = (int[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			long[] array1 = (long[])serializable1;
			long[] array2 = (long[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			Number[] array1 = (Number[])serializable1;
			Number[] array2 = (Number[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			short[] array1 = (short[])serializable1;
			short[] array2 = (short[])serializable2;

			return Arrays.equals(array1, array2);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			String[] array1 = (String[])serializable1;
			String[] array2 = (String[])serializable2;

			return Arrays.equals(array1, array2);
		}

		return serializable1.equals(serializable2);
	}

	protected List<ExpandoColumn> getAttributeColumns() {
		List<ExpandoColumn> columns = new ArrayList<ExpandoColumn>();

		try {
			columns = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				_companyId, _className);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return columns;
	}

	protected ExpandoTable getTable() throws PortalException, SystemException {
		ExpandoTable table = ExpandoTableLocalServiceUtil.fetchDefaultTable(
			_companyId, _className);

		if (table == null) {
			table = ExpandoTableLocalServiceUtil.addDefaultTable(
				_companyId, _className);
		}

		return table;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoBridgeImpl.class);

	private String _className;
	private long _classPK;
	private long _companyId;
	private boolean _indexEnabled;

}