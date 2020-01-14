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

package com.liferay.depot.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.depot.service.http.DepotAppCustomizationServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DepotAppCustomizationSoap implements Serializable {

	public static DepotAppCustomizationSoap toSoapModel(
		DepotAppCustomization model) {

		DepotAppCustomizationSoap soapModel = new DepotAppCustomizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setDepotAppCustomizationId(
			model.getDepotAppCustomizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setDepotEntryId(model.getDepotEntryId());
		soapModel.setEnabled(model.isEnabled());
		soapModel.setPortletId(model.getPortletId());

		return soapModel;
	}

	public static DepotAppCustomizationSoap[] toSoapModels(
		DepotAppCustomization[] models) {

		DepotAppCustomizationSoap[] soapModels =
			new DepotAppCustomizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DepotAppCustomizationSoap[][] toSoapModels(
		DepotAppCustomization[][] models) {

		DepotAppCustomizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DepotAppCustomizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DepotAppCustomizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DepotAppCustomizationSoap[] toSoapModels(
		List<DepotAppCustomization> models) {

		List<DepotAppCustomizationSoap> soapModels =
			new ArrayList<DepotAppCustomizationSoap>(models.size());

		for (DepotAppCustomization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DepotAppCustomizationSoap[soapModels.size()]);
	}

	public DepotAppCustomizationSoap() {
	}

	public long getPrimaryKey() {
		return _depotAppCustomizationId;
	}

	public void setPrimaryKey(long pk) {
		setDepotAppCustomizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getDepotAppCustomizationId() {
		return _depotAppCustomizationId;
	}

	public void setDepotAppCustomizationId(long depotAppCustomizationId) {
		_depotAppCustomizationId = depotAppCustomizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getDepotEntryId() {
		return _depotEntryId;
	}

	public void setDepotEntryId(long depotEntryId) {
		_depotEntryId = depotEntryId;
	}

	public boolean getEnabled() {
		return _enabled;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public String getPortletId() {
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	private long _mvccVersion;
	private long _depotAppCustomizationId;
	private long _companyId;
	private long _depotEntryId;
	private boolean _enabled;
	private String _portletId;

}