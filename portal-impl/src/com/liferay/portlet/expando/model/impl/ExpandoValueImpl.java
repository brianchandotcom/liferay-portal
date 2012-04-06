/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoValueImpl extends ExpandoValueBaseImpl {

	public ExpandoValueImpl() {
	}

	public boolean getBoolean() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.BOOLEAN);

		return GetterUtil.getBoolean(getData());
	}

	public boolean[] getBooleanArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.BOOLEAN_ARRAY);

		return GetterUtil.getBooleanValues(StringUtil.split(getData()));
	}

	public ExpandoColumn getColumn() throws PortalException, SystemException {
		if (_column != null) {
			return _column;
		}

		long columnId = getColumnId();

		if (columnId <= 0) {
			return null;
		}

		return ExpandoColumnLocalServiceUtil.getColumn(columnId);
	}

	public Date getDate() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.DATE);

		return new Date(GetterUtil.getLong(getData()));
	}

	public Date[] getDateArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.DATE_ARRAY);

		String[] data = StringUtil.split(getData());

		Date[] dateArray = new Date[data.length];

		for (int i = 0; i < data.length; i++) {
			dateArray[i] = new Date(GetterUtil.getLong(data[i]));
		}

		return dateArray;
	}

	public double getDouble() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.DOUBLE);

		return GetterUtil.getDouble(getData());
	}

	public double[] getDoubleArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.DOUBLE_ARRAY);

		return GetterUtil.getDoubleValues(StringUtil.split(getData()));
	}

	public float getFloat() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.FLOAT);

		return GetterUtil.getFloat(getData());
	}

	public float[] getFloatArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.FLOAT_ARRAY);

		return GetterUtil.getFloatValues(StringUtil.split(getData()));
	}

	public int getInteger() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.INTEGER);

		return GetterUtil.getInteger(getData());
	}

	public int[] getIntegerArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.INTEGER_ARRAY);

		return GetterUtil.getIntegerValues(StringUtil.split(getData()));
	}

	public long getLong() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.LONG);

		return GetterUtil.getLong(getData());
	}

	public long[] getLongArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.LONG_ARRAY);

		return GetterUtil.getLongValues(StringUtil.split(getData()));
	}

	public Serializable getSerializable()
		throws PortalException, SystemException {

		ExpandoColumn column = getColumn();

		int type = column.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return getBoolean();
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return getBooleanArray();
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return getDate();
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return getDateArray();
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return getDouble();
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return getDoubleArray();
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return getFloat();
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return getFloatArray();
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return getInteger();
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return getIntegerArray();
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return getLong();
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return getLongArray();
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return getShort();
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return getShortArray();
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return getStringArray();
		}
		else {
			return getData();
		}
	}

	public short getShort() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.SHORT);

		return GetterUtil.getShort(getData());
	}

	public short[] getShortArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.SHORT_ARRAY);

		return GetterUtil.getShortValues(StringUtil.split(getData()));
	}

	public String getString() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.STRING);

		return getData();
	}

	public String[] getStringArray() throws PortalException, SystemException {
		validate(ExpandoColumnConstants.STRING_ARRAY);

		String[] dataArray = StringUtil.split(getData());

		for (int i = 0; i < dataArray.length; i++) {
			dataArray[i] = StringUtil.replace(
				dataArray[i], _EXPANDO_COMMA, StringPool.COMMA);
		}

		return dataArray;
	}

	public void setBoolean(boolean data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.BOOLEAN);

		setData(String.valueOf(data));
	}

	public void setBooleanArray(boolean[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.BOOLEAN_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setColumn(ExpandoColumn column) {
		_column = column;

		setColumnId(_column.getColumnId());
	}

	public void setDate(Date data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.DATE);

		setData(String.valueOf(data.getTime()));
	}

	public void setDateArray(Date[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.DATE_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setDouble(double data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.DOUBLE);

		setData(String.valueOf(data));
	}

	public void setDoubleArray(double[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.DOUBLE_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setFloat(float data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.FLOAT);

		setData(String.valueOf(data));
	}

	public void setFloatArray(float[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.FLOAT_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setInteger(int data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.INTEGER);

		setData(String.valueOf(data));
	}

	public void setIntegerArray(int[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.INTEGER_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setLong(long data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.LONG);

		setData(String.valueOf(data));
	}

	public void setLongArray(long[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.LONG_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setNumber(Number data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.NUMBER);

		setData(String.valueOf(data));
	}

	public void setNumberArray(Number[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.NUMBER_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setShort(short data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.SHORT);

		setData(String.valueOf(data));
	}

	public void setShortArray(short[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.SHORT_ARRAY);

		setData(StringUtil.merge(data));
	}

	public void setString(String data) throws PortalException, SystemException {
		validate(ExpandoColumnConstants.STRING);

		setData(data);
	}

	public void setStringArray(String[] data)
		throws PortalException, SystemException {

		validate(ExpandoColumnConstants.STRING_ARRAY);

		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				data[i] = StringUtil.replace(
					data[i], StringPool.COMMA, _EXPANDO_COMMA);
			}
		}

		setData(StringUtil.merge(data));
	}

	protected void validate(int type) throws PortalException, SystemException {
		ExpandoColumn column = getColumn();

		if (column == null) {
			return;
		}

		if (column.getType() == type) {
			return;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("Column ");
		sb.append(getColumnId());
		sb.append(" has type ");
		sb.append(ExpandoColumnConstants.getTypeLabel(column.getType()));
		sb.append(" and is not compatible with type ");
		sb.append(ExpandoColumnConstants.getTypeLabel(type));

		throw new ValueDataException(sb.toString());
	}

	private static final String _EXPANDO_COMMA = "[$LIFERAY_EXPANDO_COMMA$]";

	private transient ExpandoColumn _column;

}