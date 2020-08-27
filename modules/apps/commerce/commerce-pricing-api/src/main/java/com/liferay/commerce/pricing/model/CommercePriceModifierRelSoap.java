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

package com.liferay.commerce.pricing.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.pricing.service.http.CommercePriceModifierRelServiceSoap}.
 *
 * @author Riccardo Alberti
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceModifierRelSoap implements Serializable {

	public static CommercePriceModifierRelSoap toSoapModel(
		CommercePriceModifierRel model) {

		CommercePriceModifierRelSoap soapModel =
			new CommercePriceModifierRelSoap();

		soapModel.setCommercePriceModifierRelId(
			model.getCommercePriceModifierRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceModifierId(
			model.getCommercePriceModifierId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static CommercePriceModifierRelSoap[] toSoapModels(
		CommercePriceModifierRel[] models) {

		CommercePriceModifierRelSoap[] soapModels =
			new CommercePriceModifierRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceModifierRelSoap[][] toSoapModels(
		CommercePriceModifierRel[][] models) {

		CommercePriceModifierRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommercePriceModifierRelSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceModifierRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceModifierRelSoap[] toSoapModels(
		List<CommercePriceModifierRel> models) {

		List<CommercePriceModifierRelSoap> soapModels =
			new ArrayList<CommercePriceModifierRelSoap>(models.size());

		for (CommercePriceModifierRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePriceModifierRelSoap[soapModels.size()]);
	}

	public CommercePriceModifierRelSoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceModifierRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceModifierRelId(pk);
	}

	public long getCommercePriceModifierRelId() {
		return _commercePriceModifierRelId;
	}

	public void setCommercePriceModifierRelId(long commercePriceModifierRelId) {
		_commercePriceModifierRelId = commercePriceModifierRelId;
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

	public long getCommercePriceModifierId() {
		return _commercePriceModifierId;
	}

	public void setCommercePriceModifierId(long commercePriceModifierId) {
		_commercePriceModifierId = commercePriceModifierId;
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

	private long _commercePriceModifierRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceModifierId;
	private long _classNameId;
	private long _classPK;

}