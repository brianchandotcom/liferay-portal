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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.OrganizationServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.OrganizationServiceSoap
 * @generated
 */
public class OrganizationSoap implements Serializable {
	public static OrganizationSoap toSoapModel(Organization model) {
		OrganizationSoap soapModel = new OrganizationSoap();

		soapModel.setOrganizationId(model.getOrganizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setParentOrganizationId(model.getParentOrganizationId());
		soapModel.setTreePath(model.getTreePath());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setRecursable(model.getRecursable());
		soapModel.setRegionId(model.getRegionId());
		soapModel.setCountryId(model.getCountryId());
		soapModel.setStatusId(model.getStatusId());
		soapModel.setComments(model.getComments());

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
		List<OrganizationSoap> soapModels = new ArrayList<OrganizationSoap>(models.size());

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

	public int getStatusId() {
		return _statusId;
	}

	public void setStatusId(int statusId) {
		_statusId = statusId;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	private long _organizationId;
	private long _companyId;
	private long _parentOrganizationId;
	private String _treePath;
	private String _name;
	private String _type;
	private boolean _recursable;
	private long _regionId;
	private long _countryId;
	private int _statusId;
	private String _comments;
}