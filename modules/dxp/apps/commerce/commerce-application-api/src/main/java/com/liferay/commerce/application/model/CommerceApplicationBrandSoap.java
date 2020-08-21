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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.application.service.http.CommerceApplicationBrandServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceApplicationBrandSoap implements Serializable {

	public static CommerceApplicationBrandSoap toSoapModel(
		CommerceApplicationBrand model) {

		CommerceApplicationBrandSoap soapModel =
			new CommerceApplicationBrandSoap();

		soapModel.setCommerceApplicationBrandId(
			model.getCommerceApplicationBrandId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setLogoId(model.getLogoId());

		return soapModel;
	}

	public static CommerceApplicationBrandSoap[] toSoapModels(
		CommerceApplicationBrand[] models) {

		CommerceApplicationBrandSoap[] soapModels =
			new CommerceApplicationBrandSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceApplicationBrandSoap[][] toSoapModels(
		CommerceApplicationBrand[][] models) {

		CommerceApplicationBrandSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceApplicationBrandSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceApplicationBrandSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceApplicationBrandSoap[] toSoapModels(
		List<CommerceApplicationBrand> models) {

		List<CommerceApplicationBrandSoap> soapModels =
			new ArrayList<CommerceApplicationBrandSoap>(models.size());

		for (CommerceApplicationBrand model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceApplicationBrandSoap[soapModels.size()]);
	}

	public CommerceApplicationBrandSoap() {
	}

	public long getPrimaryKey() {
		return _commerceApplicationBrandId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceApplicationBrandId(pk);
	}

	public long getCommerceApplicationBrandId() {
		return _commerceApplicationBrandId;
	}

	public void setCommerceApplicationBrandId(long commerceApplicationBrandId) {
		_commerceApplicationBrandId = commerceApplicationBrandId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	private long _commerceApplicationBrandId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private long _logoId;

}