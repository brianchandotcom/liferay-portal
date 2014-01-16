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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.OrgLaborServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.http.OrgLaborServiceSoap
 * @generated
 */
public class OrgLaborSoap implements Serializable {
	public static OrgLaborSoap toSoapModel(OrgLabor model) {
		OrgLaborSoap soapModel = new OrgLaborSoap();

		soapModel.setOrgLaborId(model.getOrgLaborId());
		soapModel.setOrganizationId(model.getOrganizationId());
		soapModel.setTypeId(model.getTypeId());
		soapModel.setSunOpen(model.getSunOpen());
		soapModel.setSunClose(model.getSunClose());
		soapModel.setMonOpen(model.getMonOpen());
		soapModel.setMonClose(model.getMonClose());
		soapModel.setTueOpen(model.getTueOpen());
		soapModel.setTueClose(model.getTueClose());
		soapModel.setWedOpen(model.getWedOpen());
		soapModel.setWedClose(model.getWedClose());
		soapModel.setThuOpen(model.getThuOpen());
		soapModel.setThuClose(model.getThuClose());
		soapModel.setFriOpen(model.getFriOpen());
		soapModel.setFriClose(model.getFriClose());
		soapModel.setSatOpen(model.getSatOpen());
		soapModel.setSatClose(model.getSatClose());
		soapModel.setMvccVersion(model.getMvccVersion());

		return soapModel;
	}

	public static OrgLaborSoap[] toSoapModels(OrgLabor[] models) {
		OrgLaborSoap[] soapModels = new OrgLaborSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OrgLaborSoap[][] toSoapModels(OrgLabor[][] models) {
		OrgLaborSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OrgLaborSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OrgLaborSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OrgLaborSoap[] toSoapModels(List<OrgLabor> models) {
		List<OrgLaborSoap> soapModels = new ArrayList<OrgLaborSoap>(models.size());

		for (OrgLabor model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OrgLaborSoap[soapModels.size()]);
	}

	public OrgLaborSoap() {
	}

	public long getPrimaryKey() {
		return _orgLaborId;
	}

	public void setPrimaryKey(long pk) {
		setOrgLaborId(pk);
	}

	public long getOrgLaborId() {
		return _orgLaborId;
	}

	public void setOrgLaborId(long orgLaborId) {
		_orgLaborId = orgLaborId;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public int getTypeId() {
		return _typeId;
	}

	public void setTypeId(int typeId) {
		_typeId = typeId;
	}

	public int getSunOpen() {
		return _sunOpen;
	}

	public void setSunOpen(int sunOpen) {
		_sunOpen = sunOpen;
	}

	public int getSunClose() {
		return _sunClose;
	}

	public void setSunClose(int sunClose) {
		_sunClose = sunClose;
	}

	public int getMonOpen() {
		return _monOpen;
	}

	public void setMonOpen(int monOpen) {
		_monOpen = monOpen;
	}

	public int getMonClose() {
		return _monClose;
	}

	public void setMonClose(int monClose) {
		_monClose = monClose;
	}

	public int getTueOpen() {
		return _tueOpen;
	}

	public void setTueOpen(int tueOpen) {
		_tueOpen = tueOpen;
	}

	public int getTueClose() {
		return _tueClose;
	}

	public void setTueClose(int tueClose) {
		_tueClose = tueClose;
	}

	public int getWedOpen() {
		return _wedOpen;
	}

	public void setWedOpen(int wedOpen) {
		_wedOpen = wedOpen;
	}

	public int getWedClose() {
		return _wedClose;
	}

	public void setWedClose(int wedClose) {
		_wedClose = wedClose;
	}

	public int getThuOpen() {
		return _thuOpen;
	}

	public void setThuOpen(int thuOpen) {
		_thuOpen = thuOpen;
	}

	public int getThuClose() {
		return _thuClose;
	}

	public void setThuClose(int thuClose) {
		_thuClose = thuClose;
	}

	public int getFriOpen() {
		return _friOpen;
	}

	public void setFriOpen(int friOpen) {
		_friOpen = friOpen;
	}

	public int getFriClose() {
		return _friClose;
	}

	public void setFriClose(int friClose) {
		_friClose = friClose;
	}

	public int getSatOpen() {
		return _satOpen;
	}

	public void setSatOpen(int satOpen) {
		_satOpen = satOpen;
	}

	public int getSatClose() {
		return _satClose;
	}

	public void setSatClose(int satClose) {
		_satClose = satClose;
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	private long _orgLaborId;
	private long _organizationId;
	private int _typeId;
	private int _sunOpen;
	private int _sunClose;
	private int _monOpen;
	private int _monClose;
	private int _tueOpen;
	private int _tueClose;
	private int _wedOpen;
	private int _wedClose;
	private int _thuOpen;
	private int _thuClose;
	private int _friOpen;
	private int _friClose;
	private int _satOpen;
	private int _satClose;
	private long _mvccVersion;
}