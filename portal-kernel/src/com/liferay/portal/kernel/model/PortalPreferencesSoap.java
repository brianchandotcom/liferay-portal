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
public class PortalPreferencesSoap implements Serializable {

	public static PortalPreferencesSoap toSoapModel(PortalPreferences model) {
		PortalPreferencesSoap soapModel = new PortalPreferencesSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setPortalPreferencesId(model.getPortalPreferencesId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setOwnerType(model.getOwnerType());

		return soapModel;
	}

	public static PortalPreferencesSoap[] toSoapModels(
		PortalPreferences[] models) {

		PortalPreferencesSoap[] soapModels =
			new PortalPreferencesSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortalPreferencesSoap[][] toSoapModels(
		PortalPreferences[][] models) {

		PortalPreferencesSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new PortalPreferencesSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortalPreferencesSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortalPreferencesSoap[] toSoapModels(
		List<PortalPreferences> models) {

		List<PortalPreferencesSoap> soapModels =
			new ArrayList<PortalPreferencesSoap>(models.size());

		for (PortalPreferences model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PortalPreferencesSoap[soapModels.size()]);
	}

	public PortalPreferencesSoap() {
	}

	public long getPrimaryKey() {
		return _portalPreferencesId;
	}

	public void setPrimaryKey(long pk) {
		setPortalPreferencesId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getPortalPreferencesId() {
		return _portalPreferencesId;
	}

	public void setPortalPreferencesId(long portalPreferencesId) {
		_portalPreferencesId = portalPreferencesId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(long ownerId) {
		_ownerId = ownerId;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public void setOwnerType(int ownerType) {
		_ownerType = ownerType;
	}

	private long _mvccVersion;
	private long _portalPreferencesId;
	private long _companyId;
	private long _ownerId;
	private int _ownerType;

}