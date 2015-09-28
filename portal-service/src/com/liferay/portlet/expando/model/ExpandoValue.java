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

package com.liferay.portlet.expando.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the ExpandoValue service. Represents a row in the &quot;ExpandoValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueModel
 * @see com.liferay.portlet.expando.model.impl.ExpandoValueImpl
 * @see com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl
 * @generated
 */
@ProviderType
public interface ExpandoValue extends ExpandoValueModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.expando.model.impl.ExpandoValueImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoValue, Long> VALUE_ID_ACCESSOR = new Accessor<ExpandoValue, Long>() {
			@Override
			public Long get(ExpandoValue expandoValue) {
				return expandoValue.getValueId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ExpandoValue> getTypeClass() {
				return ExpandoValue.class;
			}
		};

	public java.util.List<java.util.Locale> getAvailableLocales()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean getBoolean()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean[] getBooleanArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.expando.model.ExpandoColumn getColumn()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Date getDate()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Date[] getDateArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Locale getDefaultLocale()
		throws com.liferay.portal.kernel.exception.PortalException;

	public double getDouble()
		throws com.liferay.portal.kernel.exception.PortalException;

	public double[] getDoubleArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public float getFloat()
		throws com.liferay.portal.kernel.exception.PortalException;

	public float[] getFloatArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getInteger()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int[] getIntegerArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getLong()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long[] getLongArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.Number getNumber()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.Number[] getNumberArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getSerializable()
		throws com.liferay.portal.kernel.exception.PortalException;

	public short getShort()
		throws com.liferay.portal.kernel.exception.PortalException;

	public short[] getShortArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getString()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getString(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String[] getStringArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String[] getStringArray(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<java.util.Locale, java.lang.String[]> getStringArrayMap()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<java.util.Locale, java.lang.String> getStringMap()
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setBoolean(boolean data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column);

	public void setDate(java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setDateArray(java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setDouble(double data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setDoubleArray(double[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setFloat(float data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setFloatArray(float[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setInteger(int data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setIntegerArray(int[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setLong(long data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setLongArray(long[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setNumber(java.lang.Number data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setNumberArray(java.lang.Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setShort(short data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setShortArray(short[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setString(java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setString(java.lang.String data, java.util.Locale locale,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringArray(java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringArray(java.lang.String[] data,
		java.util.Locale locale, java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringArrayMap(
		java.util.Map<java.util.Locale, java.lang.String[]> dataMap,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringMap(
		java.util.Map<java.util.Locale, java.lang.String> dataMap,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;
}