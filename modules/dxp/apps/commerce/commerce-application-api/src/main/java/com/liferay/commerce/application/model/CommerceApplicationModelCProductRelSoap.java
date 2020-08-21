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

package com.liferay.commerce.application.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.application.service.http.CommerceApplicationModelCProductRelServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceApplicationModelCProductRelSoap implements Serializable {

	public static CommerceApplicationModelCProductRelSoap toSoapModel(
		CommerceApplicationModelCProductRel model) {

		CommerceApplicationModelCProductRelSoap soapModel =
			new CommerceApplicationModelCProductRelSoap();

		soapModel.setCommerceApplicationModelCProductRelId(
			model.getCommerceApplicationModelCProductRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceApplicationModelId(
			model.getCommerceApplicationModelId());
		soapModel.setCProductId(model.getCProductId());

		return soapModel;
	}

	public static CommerceApplicationModelCProductRelSoap[] toSoapModels(
		CommerceApplicationModelCProductRel[] models) {

		CommerceApplicationModelCProductRelSoap[] soapModels =
			new CommerceApplicationModelCProductRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceApplicationModelCProductRelSoap[][] toSoapModels(
		CommerceApplicationModelCProductRel[][] models) {

		CommerceApplicationModelCProductRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceApplicationModelCProductRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceApplicationModelCProductRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceApplicationModelCProductRelSoap[] toSoapModels(
		List<CommerceApplicationModelCProductRel> models) {

		List<CommerceApplicationModelCProductRelSoap> soapModels =
			new ArrayList<CommerceApplicationModelCProductRelSoap>(
				models.size());

		for (CommerceApplicationModelCProductRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceApplicationModelCProductRelSoap[soapModels.size()]);
	}

	public CommerceApplicationModelCProductRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceApplicationModelCProductRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceApplicationModelCProductRelId(pk);
	}

	public long getCommerceApplicationModelCProductRelId() {
		return _commerceApplicationModelCProductRelId;
	}

	public void setCommerceApplicationModelCProductRelId(
		long commerceApplicationModelCProductRelId) {

		_commerceApplicationModelCProductRelId =
			commerceApplicationModelCProductRelId;
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

	public long getCommerceApplicationModelId() {
		return _commerceApplicationModelId;
	}

	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		_commerceApplicationModelId = commerceApplicationModelId;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	private long _commerceApplicationModelCProductRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceApplicationModelId;
	private long _CProductId;

}