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
 * @generated
 */
public class VirtualHostSoap implements Serializable {

	public static VirtualHostSoap toSoapModel(VirtualHost model) {
		VirtualHostSoap soapModel = new VirtualHostSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setVirtualHostId(model.getVirtualHostId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setLayoutSetId(model.getLayoutSetId());
		soapModel.setHostname(model.getHostname());

		return soapModel;
	}

	public static VirtualHostSoap[] toSoapModels(VirtualHost[] models) {
		VirtualHostSoap[] soapModels = new VirtualHostSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static VirtualHostSoap[][] toSoapModels(VirtualHost[][] models) {
		VirtualHostSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new VirtualHostSoap[models.length][models[0].length];
		}
		else {
			soapModels = new VirtualHostSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static VirtualHostSoap[] toSoapModels(List<VirtualHost> models) {
		List<VirtualHostSoap> soapModels = new ArrayList<VirtualHostSoap>(
			models.size());

		for (VirtualHost model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new VirtualHostSoap[soapModels.size()]);
	}

	public VirtualHostSoap() {
	}

	public long getPrimaryKey() {
		return _virtualHostId;
	}

	public void setPrimaryKey(long pk) {
		setVirtualHostId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getVirtualHostId() {
		return _virtualHostId;
	}

	public void setVirtualHostId(long virtualHostId) {
		_virtualHostId = virtualHostId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getLayoutSetId() {
		return _layoutSetId;
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSetId = layoutSetId;
	}

	public String getHostname() {
		return _hostname;
	}

	public void setHostname(String hostname) {
		_hostname = hostname;
	}

	private long _mvccVersion;
	private long _virtualHostId;
	private long _companyId;
	private long _layoutSetId;
	private String _hostname;

}