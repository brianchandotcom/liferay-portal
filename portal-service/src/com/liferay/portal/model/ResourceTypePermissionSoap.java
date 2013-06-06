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
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ResourceTypePermissionSoap implements Serializable {
	public static ResourceTypePermissionSoap toSoapModel(
		ResourceTypePermission model) {
		ResourceTypePermissionSoap soapModel = new ResourceTypePermissionSoap();

		soapModel.setResourceTypePermissionId(model.getResourceTypePermissionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setName(model.getName());
		soapModel.setRoleId(model.getRoleId());
		soapModel.setActionIds(model.getActionIds());

		return soapModel;
	}

	public static ResourceTypePermissionSoap[] toSoapModels(
		ResourceTypePermission[] models) {
		ResourceTypePermissionSoap[] soapModels = new ResourceTypePermissionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ResourceTypePermissionSoap[][] toSoapModels(
		ResourceTypePermission[][] models) {
		ResourceTypePermissionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ResourceTypePermissionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ResourceTypePermissionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ResourceTypePermissionSoap[] toSoapModels(
		List<ResourceTypePermission> models) {
		List<ResourceTypePermissionSoap> soapModels = new ArrayList<ResourceTypePermissionSoap>(models.size());

		for (ResourceTypePermission model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourceTypePermissionSoap[soapModels.size()]);
	}

	public ResourceTypePermissionSoap() {
	}

	public long getPrimaryKey() {
		return _resourceTypePermissionId;
	}

	public void setPrimaryKey(long pk) {
		setResourceTypePermissionId(pk);
	}

	public long getResourceTypePermissionId() {
		return _resourceTypePermissionId;
	}

	public void setResourceTypePermissionId(long resourceTypePermissionId) {
		_resourceTypePermissionId = resourceTypePermissionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
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

	private long _resourceTypePermissionId;
	private long _companyId;
	private long _groupId;
	private String _name;
	private long _roleId;
	private long _actionIds;
}