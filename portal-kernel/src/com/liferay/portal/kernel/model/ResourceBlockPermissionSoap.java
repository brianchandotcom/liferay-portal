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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class ResourceBlockPermissionSoap implements Serializable {
	public static ResourceBlockPermissionSoap toSoapModel(
		ResourceBlockPermission model) {
		ResourceBlockPermissionSoap soapModel = new ResourceBlockPermissionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setResourceBlockPermissionId(model.getResourceBlockPermissionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setResourceBlockId(model.getResourceBlockId());
		soapModel.setRoleId(model.getRoleId());
		soapModel.setActionIds(model.getActionIds());

		return soapModel;
	}

	public static ResourceBlockPermissionSoap[] toSoapModels(
		ResourceBlockPermission[] models) {
		ResourceBlockPermissionSoap[] soapModels = new ResourceBlockPermissionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ResourceBlockPermissionSoap[][] toSoapModels(
		ResourceBlockPermission[][] models) {
		ResourceBlockPermissionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ResourceBlockPermissionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ResourceBlockPermissionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ResourceBlockPermissionSoap[] toSoapModels(
		List<ResourceBlockPermission> models) {
		List<ResourceBlockPermissionSoap> soapModels = new ArrayList<ResourceBlockPermissionSoap>(models.size());

		for (ResourceBlockPermission model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourceBlockPermissionSoap[soapModels.size()]);
	}

	public ResourceBlockPermissionSoap() {
	}

	public long getPrimaryKey() {
		return _resourceBlockPermissionId;
	}

	public void setPrimaryKey(long pk) {
		setResourceBlockPermissionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getResourceBlockPermissionId() {
		return _resourceBlockPermissionId;
	}

	public void setResourceBlockPermissionId(long resourceBlockPermissionId) {
		_resourceBlockPermissionId = resourceBlockPermissionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getResourceBlockId() {
		return _resourceBlockId;
	}

	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlockId = resourceBlockId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public long getActionIds() {
		return _actionIds;
	}

	public void setActionIds(long actionIds) {
		_actionIds = actionIds;
	}

	private long _mvccVersion;
	private long _resourceBlockPermissionId;
	private long _companyId;
	private long _resourceBlockId;
	private long _roleId;
	private long _actionIds;
}