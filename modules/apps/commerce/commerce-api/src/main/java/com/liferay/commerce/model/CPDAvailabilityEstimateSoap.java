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

package com.liferay.commerce.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CPDAvailabilityEstimateServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDAvailabilityEstimateSoap implements Serializable {

	public static CPDAvailabilityEstimateSoap toSoapModel(
		CPDAvailabilityEstimate model) {

		CPDAvailabilityEstimateSoap soapModel =
			new CPDAvailabilityEstimateSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDAvailabilityEstimateId(
			model.getCPDAvailabilityEstimateId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceAvailabilityEstimateId(
			model.getCommerceAvailabilityEstimateId());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPDAvailabilityEstimateSoap[] toSoapModels(
		CPDAvailabilityEstimate[] models) {

		CPDAvailabilityEstimateSoap[] soapModels =
			new CPDAvailabilityEstimateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDAvailabilityEstimateSoap[][] toSoapModels(
		CPDAvailabilityEstimate[][] models) {

		CPDAvailabilityEstimateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDAvailabilityEstimateSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CPDAvailabilityEstimateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDAvailabilityEstimateSoap[] toSoapModels(
		List<CPDAvailabilityEstimate> models) {

		List<CPDAvailabilityEstimateSoap> soapModels =
			new ArrayList<CPDAvailabilityEstimateSoap>(models.size());

		for (CPDAvailabilityEstimate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDAvailabilityEstimateSoap[soapModels.size()]);
	}

	public CPDAvailabilityEstimateSoap() {
	}

	public long getPrimaryKey() {
		return _CPDAvailabilityEstimateId;
	}

	public void setPrimaryKey(long pk) {
		setCPDAvailabilityEstimateId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDAvailabilityEstimateId() {
		return _CPDAvailabilityEstimateId;
	}

	public void setCPDAvailabilityEstimateId(long CPDAvailabilityEstimateId) {
		_CPDAvailabilityEstimateId = CPDAvailabilityEstimateId;
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

	public long getCommerceAvailabilityEstimateId() {
		return _commerceAvailabilityEstimateId;
	}

	public void setCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		_commerceAvailabilityEstimateId = commerceAvailabilityEstimateId;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPDAvailabilityEstimateId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceAvailabilityEstimateId;
	private long _CProductId;
	private Date _lastPublishDate;

}