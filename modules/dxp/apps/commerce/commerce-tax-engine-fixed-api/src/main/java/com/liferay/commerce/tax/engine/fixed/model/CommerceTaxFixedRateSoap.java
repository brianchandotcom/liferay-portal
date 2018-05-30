/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.tax.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.tax.engine.fixed.service.http.CommerceTaxFixedRateServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.tax.engine.fixed.service.http.CommerceTaxFixedRateServiceSoap
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateSoap implements Serializable {
	public static CommerceTaxFixedRateSoap toSoapModel(
		CommerceTaxFixedRate model) {
		CommerceTaxFixedRateSoap soapModel = new CommerceTaxFixedRateSoap();

		soapModel.setCommerceTaxFixedRateId(model.getCommerceTaxFixedRateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPTaxCategoryId(model.getCPTaxCategoryId());
		soapModel.setCommerceTaxMethodId(model.getCommerceTaxMethodId());
		soapModel.setRate(model.getRate());

		return soapModel;
	}

	public static CommerceTaxFixedRateSoap[] toSoapModels(
		CommerceTaxFixedRate[] models) {
		CommerceTaxFixedRateSoap[] soapModels = new CommerceTaxFixedRateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxFixedRateSoap[][] toSoapModels(
		CommerceTaxFixedRate[][] models) {
		CommerceTaxFixedRateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceTaxFixedRateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTaxFixedRateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxFixedRateSoap[] toSoapModels(
		List<CommerceTaxFixedRate> models) {
		List<CommerceTaxFixedRateSoap> soapModels = new ArrayList<CommerceTaxFixedRateSoap>(models.size());

		for (CommerceTaxFixedRate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceTaxFixedRateSoap[soapModels.size()]);
	}

	public CommerceTaxFixedRateSoap() {
	}

	public long getPrimaryKey() {
		return _commerceTaxFixedRateId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTaxFixedRateId(pk);
	}

	public long getCommerceTaxFixedRateId() {
		return _commerceTaxFixedRateId;
	}

	public void setCommerceTaxFixedRateId(long commerceTaxFixedRateId) {
		_commerceTaxFixedRateId = commerceTaxFixedRateId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getCPTaxCategoryId() {
		return _CPTaxCategoryId;
	}

	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		_CPTaxCategoryId = CPTaxCategoryId;
	}

	public long getCommerceTaxMethodId() {
		return _commerceTaxMethodId;
	}

	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxMethodId = commerceTaxMethodId;
	}

	public double getRate() {
		return _rate;
	}

	public void setRate(double rate) {
		_rate = rate;
	}

	private long _commerceTaxFixedRateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPTaxCategoryId;
	private long _commerceTaxMethodId;
	private double _rate;
}