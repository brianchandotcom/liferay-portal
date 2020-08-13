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
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class WebDAVPropsSoap implements Serializable {

	public static WebDAVPropsSoap toSoapModel(WebDAVProps model) {
		WebDAVPropsSoap soapModel = new WebDAVPropsSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setWebDavPropsId(model.getWebDavPropsId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setProps(model.getProps());

		return soapModel;
	}

	public static WebDAVPropsSoap[] toSoapModels(WebDAVProps[] models) {
		WebDAVPropsSoap[] soapModels = new WebDAVPropsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WebDAVPropsSoap[][] toSoapModels(WebDAVProps[][] models) {
		WebDAVPropsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WebDAVPropsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WebDAVPropsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WebDAVPropsSoap[] toSoapModels(List<WebDAVProps> models) {
		List<WebDAVPropsSoap> soapModels = new ArrayList<WebDAVPropsSoap>(
			models.size());

		for (WebDAVProps model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WebDAVPropsSoap[soapModels.size()]);
	}

	public WebDAVPropsSoap() {
	}

	public long getPrimaryKey() {
		return _webDavPropsId;
	}

	public void setPrimaryKey(long pk) {
		setWebDavPropsId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getWebDavPropsId() {
		return _webDavPropsId;
	}

	public void setWebDavPropsId(long webDavPropsId) {
		_webDavPropsId = webDavPropsId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getProps() {
		return _props;
	}

	public void setProps(String props) {
		_props = props;
	}

	private long _mvccVersion;
	private long _webDavPropsId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _props;

}