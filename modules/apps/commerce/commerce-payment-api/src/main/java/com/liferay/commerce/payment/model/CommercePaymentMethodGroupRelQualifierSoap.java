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

package com.liferay.commerce.payment.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.payment.service.http.CommercePaymentMethodGroupRelQualifierServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePaymentMethodGroupRelQualifierSoap
	implements Serializable {

	public static CommercePaymentMethodGroupRelQualifierSoap toSoapModel(
		CommercePaymentMethodGroupRelQualifier model) {

		CommercePaymentMethodGroupRelQualifierSoap soapModel =
			new CommercePaymentMethodGroupRelQualifierSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCommercePaymentMethodGroupRelQualifierId(
			model.getCommercePaymentMethodGroupRelQualifierId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCommercePaymentMethodGroupRelId(
			model.getCommercePaymentMethodGroupRelId());

		return soapModel;
	}

	public static CommercePaymentMethodGroupRelQualifierSoap[] toSoapModels(
		CommercePaymentMethodGroupRelQualifier[] models) {

		CommercePaymentMethodGroupRelQualifierSoap[] soapModels =
			new CommercePaymentMethodGroupRelQualifierSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePaymentMethodGroupRelQualifierSoap[][] toSoapModels(
		CommercePaymentMethodGroupRelQualifier[][] models) {

		CommercePaymentMethodGroupRelQualifierSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePaymentMethodGroupRelQualifierSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePaymentMethodGroupRelQualifierSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePaymentMethodGroupRelQualifierSoap[] toSoapModels(
		List<CommercePaymentMethodGroupRelQualifier> models) {

		List<CommercePaymentMethodGroupRelQualifierSoap> soapModels =
			new ArrayList<CommercePaymentMethodGroupRelQualifierSoap>(
				models.size());

		for (CommercePaymentMethodGroupRelQualifier model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePaymentMethodGroupRelQualifierSoap[soapModels.size()]);
	}

	public CommercePaymentMethodGroupRelQualifierSoap() {
	}

	public long getPrimaryKey() {
		return _commercePaymentMethodGroupRelQualifierId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePaymentMethodGroupRelQualifierId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCommercePaymentMethodGroupRelQualifierId() {
		return _commercePaymentMethodGroupRelQualifierId;
	}

	public void setCommercePaymentMethodGroupRelQualifierId(
		long commercePaymentMethodGroupRelQualifierId) {

		_commercePaymentMethodGroupRelQualifierId =
			commercePaymentMethodGroupRelQualifierId;
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

	public long getCommercePaymentMethodGroupRelId() {
		return _CommercePaymentMethodGroupRelId;
	}

	public void setCommercePaymentMethodGroupRelId(
		long CommercePaymentMethodGroupRelId) {

		_CommercePaymentMethodGroupRelId = CommercePaymentMethodGroupRelId;
	}

	private long _mvccVersion;
	private long _commercePaymentMethodGroupRelQualifierId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _CommercePaymentMethodGroupRelId;

}