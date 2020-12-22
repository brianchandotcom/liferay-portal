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

package com.liferay.document.library.model;

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
public class DLStorageQuotaSoap implements Serializable {

	public static DLStorageQuotaSoap toSoapModel(DLStorageQuota model) {
		DLStorageQuotaSoap soapModel = new DLStorageQuotaSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setDlStorageQuotaId(model.getDlStorageQuotaId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setStorageSize(model.getStorageSize());

		return soapModel;
	}

	public static DLStorageQuotaSoap[] toSoapModels(DLStorageQuota[] models) {
		DLStorageQuotaSoap[] soapModels = new DLStorageQuotaSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLStorageQuotaSoap[][] toSoapModels(
		DLStorageQuota[][] models) {

		DLStorageQuotaSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DLStorageQuotaSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLStorageQuotaSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLStorageQuotaSoap[] toSoapModels(
		List<DLStorageQuota> models) {

		List<DLStorageQuotaSoap> soapModels = new ArrayList<DLStorageQuotaSoap>(
			models.size());

		for (DLStorageQuota model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLStorageQuotaSoap[soapModels.size()]);
	}

	public DLStorageQuotaSoap() {
	}

	public long getPrimaryKey() {
		return _dlStorageQuotaId;
	}

	public void setPrimaryKey(long pk) {
		setDlStorageQuotaId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getDlStorageQuotaId() {
		return _dlStorageQuotaId;
	}

	public void setDlStorageQuotaId(long dlStorageQuotaId) {
		_dlStorageQuotaId = dlStorageQuotaId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getStorageSize() {
		return _storageSize;
	}

	public void setStorageSize(long storageSize) {
		_storageSize = storageSize;
	}

	private long _mvccVersion;
	private long _dlStorageQuotaId;
	private long _companyId;
	private long _storageSize;

}