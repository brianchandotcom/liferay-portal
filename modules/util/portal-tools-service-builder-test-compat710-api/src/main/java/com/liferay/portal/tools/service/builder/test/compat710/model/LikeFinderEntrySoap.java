/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LikeFinderEntrySoap implements Serializable {

	public static LikeFinderEntrySoap toSoapModel(LikeFinderEntry model) {
		LikeFinderEntrySoap soapModel = new LikeFinderEntrySoap();

		soapModel.setLikeFinderEntryId(model.getLikeFinderEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setOwnerType(model.getOwnerType());
		soapModel.setPortletId(model.getPortletId());

		return soapModel;
	}

	public static LikeFinderEntrySoap[] toSoapModels(LikeFinderEntry[] models) {
		LikeFinderEntrySoap[] soapModels =
			new LikeFinderEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LikeFinderEntrySoap[][] toSoapModels(
		LikeFinderEntry[][] models) {

		LikeFinderEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LikeFinderEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LikeFinderEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LikeFinderEntrySoap[] toSoapModels(
		List<LikeFinderEntry> models) {

		List<LikeFinderEntrySoap> soapModels =
			new ArrayList<LikeFinderEntrySoap>(models.size());

		for (LikeFinderEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LikeFinderEntrySoap[soapModels.size()]);
	}

	public LikeFinderEntrySoap() {
	}

	public long getPrimaryKey() {
		return _likeFinderEntryId;
	}

	public void setPrimaryKey(long pk) {
		setLikeFinderEntryId(pk);
	}

	public long getLikeFinderEntryId() {
		return _likeFinderEntryId;
	}

	public void setLikeFinderEntryId(long likeFinderEntryId) {
		_likeFinderEntryId = likeFinderEntryId;
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

	public String getPortletId() {
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	private long _likeFinderEntryId;
	private long _companyId;
	private long _ownerId;
	private int _ownerType;
	private String _portletId;

}
// LIFERAY-SERVICE-BUILDER-HASH:604222700