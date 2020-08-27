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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.application.service.http.CommerceApplicationModelServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceApplicationModelSoap implements Serializable {

	public static CommerceApplicationModelSoap toSoapModel(
		CommerceApplicationModel model) {

		CommerceApplicationModelSoap soapModel =
			new CommerceApplicationModelSoap();

		soapModel.setCommerceApplicationModelId(
			model.getCommerceApplicationModelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceApplicationBrandId(
			model.getCommerceApplicationBrandId());
		soapModel.setName(model.getName());
		soapModel.setYear(model.getYear());

		return soapModel;
	}

	public static CommerceApplicationModelSoap[] toSoapModels(
		CommerceApplicationModel[] models) {

		CommerceApplicationModelSoap[] soapModels =
			new CommerceApplicationModelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceApplicationModelSoap[][] toSoapModels(
		CommerceApplicationModel[][] models) {

		CommerceApplicationModelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceApplicationModelSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceApplicationModelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceApplicationModelSoap[] toSoapModels(
		List<CommerceApplicationModel> models) {

		List<CommerceApplicationModelSoap> soapModels =
			new ArrayList<CommerceApplicationModelSoap>(models.size());

		for (CommerceApplicationModel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceApplicationModelSoap[soapModels.size()]);
	}

	public CommerceApplicationModelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceApplicationModelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceApplicationModelId(pk);
	}

	public long getCommerceApplicationModelId() {
		return _commerceApplicationModelId;
	}

	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		_commerceApplicationModelId = commerceApplicationModelId;
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

	public long getCommerceApplicationBrandId() {
		return _commerceApplicationBrandId;
	}

	public void setCommerceApplicationBrandId(long commerceApplicationBrandId) {
		_commerceApplicationBrandId = commerceApplicationBrandId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getYear() {
		return _year;
	}

	public void setYear(String year) {
		_year = year;
	}

	private long _commerceApplicationModelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceApplicationBrandId;
	private String _name;
	private String _year;

}