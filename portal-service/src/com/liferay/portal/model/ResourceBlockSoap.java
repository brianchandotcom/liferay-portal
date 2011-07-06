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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ResourceBlockSoap implements Serializable {
	public static ResourceBlockSoap toSoapModel(ResourceBlock model) {
		ResourceBlockSoap soapModel = new ResourceBlockSoap();

		soapModel.setResourceBlockId(model.getResourceBlockId());
		soapModel.setPermissionsHash(model.getPermissionsHash());
		soapModel.setReferenceCount(model.getReferenceCount());

		return soapModel;
	}

	public static ResourceBlockSoap[] toSoapModels(ResourceBlock[] models) {
		ResourceBlockSoap[] soapModels = new ResourceBlockSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ResourceBlockSoap[][] toSoapModels(ResourceBlock[][] models) {
		ResourceBlockSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ResourceBlockSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ResourceBlockSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ResourceBlockSoap[] toSoapModels(List<ResourceBlock> models) {
		List<ResourceBlockSoap> soapModels = new ArrayList<ResourceBlockSoap>(models.size());

		for (ResourceBlock model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourceBlockSoap[soapModels.size()]);
	}

	public ResourceBlockSoap() {
	}

	public long getPrimaryKey() {
		return _resourceBlockId;
	}

	public void setPrimaryKey(long pk) {
		setResourceBlockId(pk);
	}

	public long getResourceBlockId() {
		return _resourceBlockId;
	}

	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlockId = resourceBlockId;
	}

	public String getPermissionsHash() {
		return _permissionsHash;
	}

	public void setPermissionsHash(String permissionsHash) {
		_permissionsHash = permissionsHash;
	}

	public long getReferenceCount() {
		return _referenceCount;
	}

	public void setReferenceCount(long referenceCount) {
		_referenceCount = referenceCount;
	}

	private long _resourceBlockId;
	private String _permissionsHash;
	private long _referenceCount;
}