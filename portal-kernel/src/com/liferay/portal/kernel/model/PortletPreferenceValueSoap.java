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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class PortletPreferenceValueSoap implements Serializable {

	public static PortletPreferenceValueSoap toSoapModel(
		PortletPreferenceValue model) {

		PortletPreferenceValueSoap soapModel = new PortletPreferenceValueSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setPortletPreferenceValueId(
			model.getPortletPreferenceValueId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setPortletPreferencesId(model.getPortletPreferencesId());
		soapModel.setName(model.getName());
		soapModel.setIndex(model.getIndex());
		soapModel.setSmallValue(model.getSmallValue());
		soapModel.setLargeValue(model.getLargeValue());
		soapModel.setReadOnly(model.isReadOnly());

		return soapModel;
	}

	public static PortletPreferenceValueSoap[] toSoapModels(
		PortletPreferenceValue[] models) {

		PortletPreferenceValueSoap[] soapModels =
			new PortletPreferenceValueSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortletPreferenceValueSoap[][] toSoapModels(
		PortletPreferenceValue[][] models) {

		PortletPreferenceValueSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new PortletPreferenceValueSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortletPreferenceValueSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortletPreferenceValueSoap[] toSoapModels(
		List<PortletPreferenceValue> models) {

		List<PortletPreferenceValueSoap> soapModels =
			new ArrayList<PortletPreferenceValueSoap>(models.size());

		for (PortletPreferenceValue model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new PortletPreferenceValueSoap[soapModels.size()]);
	}

	public PortletPreferenceValueSoap() {
	}

	public long getPrimaryKey() {
		return _portletPreferenceValueId;
	}

	public void setPrimaryKey(long pk) {
		setPortletPreferenceValueId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getPortletPreferenceValueId() {
		return _portletPreferenceValueId;
	}

	public void setPortletPreferenceValueId(long portletPreferenceValueId) {
		_portletPreferenceValueId = portletPreferenceValueId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getPortletPreferencesId() {
		return _portletPreferencesId;
	}

	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferencesId = portletPreferencesId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public String getSmallValue() {
		return _smallValue;
	}

	public void setSmallValue(String smallValue) {
		_smallValue = smallValue;
	}

	public String getLargeValue() {
		return _largeValue;
	}

	public void setLargeValue(String largeValue) {
		_largeValue = largeValue;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _portletPreferenceValueId;
	private long _companyId;
	private long _portletPreferencesId;
	private String _name;
	private int _index;
	private String _smallValue;
	private String _largeValue;
	private boolean _readOnly;

}