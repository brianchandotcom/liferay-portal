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
public class PortalPreferenceValueSoap implements Serializable {

	public static PortalPreferenceValueSoap toSoapModel(
		PortalPreferenceValue model) {

		PortalPreferenceValueSoap soapModel = new PortalPreferenceValueSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setPortalPreferenceValueId(
			model.getPortalPreferenceValueId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setPortalPreferencesId(model.getPortalPreferencesId());
		soapModel.setIndex(model.getIndex());
		soapModel.setKey(model.getKey());
		soapModel.setLargeValue(model.getLargeValue());
		soapModel.setNamespace(model.getNamespace());
		soapModel.setSmallValue(model.getSmallValue());

		return soapModel;
	}

	public static PortalPreferenceValueSoap[] toSoapModels(
		PortalPreferenceValue[] models) {

		PortalPreferenceValueSoap[] soapModels =
			new PortalPreferenceValueSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortalPreferenceValueSoap[][] toSoapModels(
		PortalPreferenceValue[][] models) {

		PortalPreferenceValueSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new PortalPreferenceValueSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortalPreferenceValueSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortalPreferenceValueSoap[] toSoapModels(
		List<PortalPreferenceValue> models) {

		List<PortalPreferenceValueSoap> soapModels =
			new ArrayList<PortalPreferenceValueSoap>(models.size());

		for (PortalPreferenceValue model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new PortalPreferenceValueSoap[soapModels.size()]);
	}

	public PortalPreferenceValueSoap() {
	}

	public long getPrimaryKey() {
		return _portalPreferenceValueId;
	}

	public void setPrimaryKey(long pk) {
		setPortalPreferenceValueId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getPortalPreferenceValueId() {
		return _portalPreferenceValueId;
	}

	public void setPortalPreferenceValueId(long portalPreferenceValueId) {
		_portalPreferenceValueId = portalPreferenceValueId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getPortalPreferencesId() {
		return _portalPreferencesId;
	}

	public void setPortalPreferencesId(long portalPreferencesId) {
		_portalPreferencesId = portalPreferencesId;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getLargeValue() {
		return _largeValue;
	}

	public void setLargeValue(String largeValue) {
		_largeValue = largeValue;
	}

	public String getNamespace() {
		return _namespace;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public String getSmallValue() {
		return _smallValue;
	}

	public void setSmallValue(String smallValue) {
		_smallValue = smallValue;
	}

	private long _mvccVersion;
	private long _portalPreferenceValueId;
	private long _companyId;
	private long _portalPreferencesId;
	private int _index;
	private String _key;
	private String _largeValue;
	private String _namespace;
	private String _smallValue;

}