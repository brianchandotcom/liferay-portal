/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.faro.engine.client.model.AccountDetails;

import java.util.Date;
import java.util.List;

/**
 * @author Caio Pinheiro
 */
public class AccountDetailsDisplay {

	public AccountDetailsDisplay() {
	}

	public AccountDetailsDisplay(AccountDetails accountDetails) {
		_accountName = accountDetails.getAccountName();
		_accountType = accountDetails.getAccountType();
		_annualRevenue = accountDetails.getAnnualRevenue();
		_country = accountDetails.getCountry();
		_createDate = accountDetails.getCreateDate();
		_currencyCode = accountDetails.getCurrencyCode();
		_fields = accountDetails.getFields();
		_id = accountDetails.getId();
		_industry = accountDetails.getIndustry();
		_lastActivityDate = accountDetails.getLastActivityDate();
		_modifiedDate = accountDetails.getModifiedDate();
		_numberOfEmployees = accountDetails.getNumberOfEmployees();
		_state = accountDetails.getState();
		_yearStarted = accountDetails.getYearStarted();
	}

	private String _accountName;
	private String _accountType;
	private Double _annualRevenue;
	private String _country;
	private Date _createDate;
	private String _currencyCode;
	private List<AccountDetails.Field> _fields;
	private String _id;
	private String _industry;

	@JsonProperty("lastActive")
	private Date _lastActivityDate;

	@JsonProperty("lastEnriched")
	private Date _modifiedDate;

	private Integer _numberOfEmployees;
	private String _state;
	private String _yearStarted;

}