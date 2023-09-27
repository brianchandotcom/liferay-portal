/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ExpandoValue service. Represents a row in the &quot;ExpandoValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.expando.model.impl.ExpandoValueImpl"
)
@ProviderType
public interface ExpandoValue extends ExpandoValueModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.expando.model.impl.ExpandoValueImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoValue, Long> VALUE_ID_ACCESSOR =
		new Accessor<ExpandoValue, Long>() {

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

	public ExpandoColumn getColumn()
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

	public com.liferay.portal.kernel.json.JSONObject getGeolocationJSONObject()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getInteger()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int[] getIntegerArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getLong()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long[] getLongArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Number getNumber()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Number[] getNumberArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getSerializable()
		throws com.liferay.portal.kernel.exception.PortalException;

	public short getShort()
		throws com.liferay.portal.kernel.exception.PortalException;

	public short[] getShortArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getString()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getString(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String[] getStringArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String[] getStringArray(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<java.util.Locale, String[]> getStringArrayMap()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<java.util.Locale, String> getStringMap()
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setBoolean(boolean data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setColumn(ExpandoColumn column);

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

	public void setGeolocationJSONObject(
			com.liferay.portal.kernel.json.JSONObject dataJSONObject)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setInteger(int data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setIntegerArray(int[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setLong(long data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setLongArray(long[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setNumber(Number data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setNumberArray(Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setShort(short data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setShortArray(short[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setString(String data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setString(
			String data, java.util.Locale locale,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringArray(String[] data)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringArray(
			String[] data, java.util.Locale locale,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringArrayMap(
			java.util.Map<java.util.Locale, String[]> dataMap,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setStringMap(
			java.util.Map<java.util.Locale, String> dataMap,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException;

}