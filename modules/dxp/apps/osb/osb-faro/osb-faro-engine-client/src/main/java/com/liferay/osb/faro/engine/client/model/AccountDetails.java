/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;
import java.util.List;

/**
 * @author Caio Pinheiro
 */
public class AccountDetails {

	public String getAccountName() {
		return _accountName;
	}

	public String getAccountType() {
		return _accountType;
	}

	public Double getAnnualRevenue() {
		return _annualRevenue;
	}

	public String getCountry() {
		return _country;
	}

	public Date getCreateDate() {
		if (_createDate == null) {
			return null;
		}

		return new Date(_createDate.getTime());
	}

	public String getCurrencyCode() {
		return _currencyCode;
	}

	public List<Field> getFields() {
		return _fields;
	}

	public String getId() {
		return _id;
	}

	public String getIndustry() {
		return _industry;
	}

	public Date getLastActivityDate() {
		if (_lastActivityDate == null) {
			return null;
		}

		return new Date(_lastActivityDate.getTime());
	}

	public Date getModifiedDate() {
		if (_modifiedDate == null) {
			return null;
		}

		return new Date(_modifiedDate.getTime());
	}

	public Integer getNumberOfEmployees() {
		return _numberOfEmployees;
	}

	public String getState() {
		return _state;
	}

	public String getYearStarted() {
		return _yearStarted;
	}

	public void setAccountName(String accountName) {
		_accountName = accountName;
	}

	public void setAccountType(String accountType) {
		_accountType = accountType;
	}

	public void setAnnualRevenue(Double annualRevenue) {
		_annualRevenue = annualRevenue;
	}

	public void setCountry(String country) {
		_country = country;
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null) {
			_createDate = new Date(createDate.getTime());
		}
	}

	public void setCurrencyCode(String currencyCode) {
		_currencyCode = currencyCode;
	}

	public void setFields(List<Field> fields) {
		_fields = fields;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndustry(String industry) {
		_industry = industry;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		if (lastActivityDate != null) {
			_lastActivityDate = new Date(lastActivityDate.getTime());
		}
	}

	public void setModifiedDate(Date modifiedDate) {
		if (modifiedDate != null) {
			_modifiedDate = new Date(modifiedDate.getTime());
		}
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		_numberOfEmployees = numberOfEmployees;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setYearStarted(String yearStarted) {
		_yearStarted = yearStarted;
	}

	public static class Field {

		public String getDataSourceName() {
			return _dataSourceName;
		}

		public String getName() {
			return _name;
		}

		public String getValue() {
			return _value;
		}

		public void setDataSourceName(String dataSourceName) {
			_dataSourceName = dataSourceName;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setValue(String value) {
			_value = value;
		}

		private String _dataSourceName;
		private String _name;
		private String _value;

	}

	private String _accountName;
	private String _accountType;
	private Double _annualRevenue;
	private String _country;
	private Date _createDate;
	private String _currencyCode;
	private List<Field> _fields;
	private String _id;
	private String _industry;
	private Date _lastActivityDate;
	private Date _modifiedDate;
	private Integer _numberOfEmployees;
	private String _state;
	private String _yearStarted;

}