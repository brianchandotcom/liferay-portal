/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.ResourceBlockRoleActionPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ResourceBlockRoleActionSoap implements Serializable {
	public static ResourceBlockRoleActionSoap toSoapModel(
		ResourceBlockRoleAction model) {
		ResourceBlockRoleActionSoap soapModel = new ResourceBlockRoleActionSoap();

		soapModel.setActionIds(model.getActionIds());
		soapModel.setResourceBlockId(model.getResourceBlockId());
		soapModel.setRoleId(model.getRoleId());

		return soapModel;
	}

	public static ResourceBlockRoleActionSoap[] toSoapModels(
		ResourceBlockRoleAction[] models) {
		ResourceBlockRoleActionSoap[] soapModels = new ResourceBlockRoleActionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ResourceBlockRoleActionSoap[][] toSoapModels(
		ResourceBlockRoleAction[][] models) {
		ResourceBlockRoleActionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ResourceBlockRoleActionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ResourceBlockRoleActionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ResourceBlockRoleActionSoap[] toSoapModels(
		List<ResourceBlockRoleAction> models) {
		List<ResourceBlockRoleActionSoap> soapModels = new ArrayList<ResourceBlockRoleActionSoap>(models.size());

		for (ResourceBlockRoleAction model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourceBlockRoleActionSoap[soapModels.size()]);
	}

	public ResourceBlockRoleActionSoap() {
	}

	public ResourceBlockRoleActionPK getPrimaryKey() {
		return new ResourceBlockRoleActionPK(_actionIds, _resourceBlockId,
			_roleId);
	}

	public void setPrimaryKey(ResourceBlockRoleActionPK pk) {
		setActionIds(pk.actionIds);
		setResourceBlockId(pk.resourceBlockId);
		setRoleId(pk.roleId);
	}

	public long getActionIds() {
		return _actionIds;
	}

	public void setActionIds(long actionIds) {
		_actionIds = actionIds;
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

	private long _actionIds;
	private long _resourceBlockId;
	private long _roleId;
}