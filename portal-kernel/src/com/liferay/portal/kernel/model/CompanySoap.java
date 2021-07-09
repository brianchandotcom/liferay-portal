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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.CompanyServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CompanySoap implements Serializable {

	public static CompanySoap toSoapModel(Company model) {
		CompanySoap soapModel = new CompanySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setWebId(model.getWebId());
		soapModel.setMx(model.getMx());
		soapModel.setHomeURL(model.getHomeURL());
		soapModel.setLogoId(model.getLogoId());
		soapModel.setSystem(model.isSystem());
		soapModel.setMaxUsers(model.getMaxUsers());
		soapModel.setActive(model.isActive());
		soapModel.setName(model.getName());
		soapModel.setLegalName(model.getLegalName());
		soapModel.setLegalId(model.getLegalId());
		soapModel.setLegalType(model.getLegalType());
		soapModel.setSicCode(model.getSicCode());
		soapModel.setTickerSymbol(model.getTickerSymbol());
		soapModel.setIndustry(model.getIndustry());
		soapModel.setType(model.getType());
		soapModel.setSize(model.getSize());

		return soapModel;
	}

	public static CompanySoap[] toSoapModels(Company[] models) {
		CompanySoap[] soapModels = new CompanySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CompanySoap[][] toSoapModels(Company[][] models) {
		CompanySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CompanySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CompanySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CompanySoap[] toSoapModels(List<Company> models) {
		List<CompanySoap> soapModels = new ArrayList<CompanySoap>(
			models.size());

		for (Company model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CompanySoap[soapModels.size()]);
	}

	public CompanySoap() {
	}

	public long getPrimaryKey() {
		return _companyId;
	}

	public void setPrimaryKey(long pk) {
		setCompanyId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
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

	public String getWebId() {
		return _webId;
	}

	public void setWebId(String webId) {
		_webId = webId;
	}

	public String getMx() {
		return _mx;
	}

	public void setMx(String mx) {
		_mx = mx;
	}

	public String getHomeURL() {
		return _homeURL;
	}

	public void setHomeURL(String homeURL) {
		_homeURL = homeURL;
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	public boolean getSystem() {
		return _system;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	public int getMaxUsers() {
		return _maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		_maxUsers = maxUsers;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getLegalName() {
		return _legalName;
	}

	public void setLegalName(String legalName) {
		_legalName = legalName;
	}

	public String getLegalId() {
		return _legalId;
	}

	public void setLegalId(String legalId) {
		_legalId = legalId;
	}

	public String getLegalType() {
		return _legalType;
	}

	public void setLegalType(String legalType) {
		_legalType = legalType;
	}

	public String getSicCode() {
		return _sicCode;
	}

	public void setSicCode(String sicCode) {
		_sicCode = sicCode;
	}

	public String getTickerSymbol() {
		return _tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		_tickerSymbol = tickerSymbol;
	}

	public String getIndustry() {
		return _industry;
	}

	public void setIndustry(String industry) {
		_industry = industry;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getSize() {
		return _size;
	}

	public void setSize(String size) {
		_size = size;
	}

	private long _mvccVersion;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _webId;
	private String _mx;
	private String _homeURL;
	private long _logoId;
	private boolean _system;
	private int _maxUsers;
	private boolean _active;
	private String _name;
	private String _legalName;
	private String _legalId;
	private String _legalType;
	private String _sicCode;
	private String _tickerSymbol;
	private String _industry;
	private String _type;
	private String _size;

}