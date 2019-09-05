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
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.OrganizationServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OrganizationSoap implements Serializable {

	public static OrganizationSoap toSoapModel(Organization model) {
		OrganizationSoap soapModel = new OrganizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setOrganizationId(model.getOrganizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setParentOrganizationId(model.getParentOrganizationId());
		soapModel.setTreePath(model.getTreePath());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setRecursable(model.isRecursable());
		soapModel.setRegionId(model.getRegionId());
		soapModel.setCountryId(model.getCountryId());
		soapModel.setStatusId(model.getStatusId());
		soapModel.setComments(model.getComments());
		soapModel.setLogoId(model.getLogoId());

		return soapModel;
	}

	public static OrganizationSoap[] toSoapModels(Organization[] models) {
		OrganizationSoap[] soapModels = new OrganizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OrganizationSoap[][] toSoapModels(Organization[][] models) {
		OrganizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OrganizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OrganizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OrganizationSoap[] toSoapModels(List<Organization> models) {
		List<OrganizationSoap> soapModels = new ArrayList<OrganizationSoap>(
			models.size());

		for (Organization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OrganizationSoap[soapModels.size()]);
	}

	public OrganizationSoap() {
	}

	public long getPrimaryKey() {
		return _organizationId;
	}

	public void setPrimaryKey(long pk) {
		setOrganizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	public String getTreePath() {
		return _treePath;
	}

	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean getRecursable() {
		return _recursable;
	}

	public boolean isRecursable() {
		return _recursable;
	}

	public void setRecursable(boolean recursable) {
		_recursable = recursable;
	}

	public long getRegionId() {
		return _regionId;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	public long getCountryId() {
		return _countryId;
	}

	public void setCountryId(long countryId) {
		_countryId = countryId;
	}

	public long getStatusId() {
		return _statusId;
	}

	public void setStatusId(long statusId) {
		_statusId = statusId;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	private long _mvccVersion;
	private String _uuid;
	private String _externalReferenceCode;
	private long _organizationId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentOrganizationId;
	private String _treePath;
	private String _name;
	private String _type;
	private boolean _recursable;
	private long _regionId;
	private long _countryId;
	private long _statusId;
	private String _comments;
	private long _logoId;

}