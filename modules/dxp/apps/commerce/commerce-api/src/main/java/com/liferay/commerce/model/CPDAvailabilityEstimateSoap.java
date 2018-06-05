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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CPDAvailabilityEstimateServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CPDAvailabilityEstimateServiceSoap
 * @generated
 */
@ProviderType
public class CPDAvailabilityEstimateSoap implements Serializable {
	public static CPDAvailabilityEstimateSoap toSoapModel(
		CPDAvailabilityEstimate model) {
		CPDAvailabilityEstimateSoap soapModel = new CPDAvailabilityEstimateSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDAvailabilityEstimateId(model.getCPDAvailabilityEstimateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCommerceAvailabilityEstimateId(model.getCommerceAvailabilityEstimateId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPDAvailabilityEstimateSoap[] toSoapModels(
		CPDAvailabilityEstimate[] models) {
		CPDAvailabilityEstimateSoap[] soapModels = new CPDAvailabilityEstimateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDAvailabilityEstimateSoap[][] toSoapModels(
		CPDAvailabilityEstimate[][] models) {
		CPDAvailabilityEstimateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDAvailabilityEstimateSoap[models.length][models[0].length];
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
		List<CPDAvailabilityEstimateSoap> soapModels = new ArrayList<CPDAvailabilityEstimateSoap>(models.size());

		for (CPDAvailabilityEstimate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDAvailabilityEstimateSoap[soapModels.size()]);
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public long getCommerceAvailabilityEstimateId() {
		return _commerceAvailabilityEstimateId;
	}

	public void setCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {
		_commerceAvailabilityEstimateId = commerceAvailabilityEstimateId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPDAvailabilityEstimateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _commerceAvailabilityEstimateId;
	private Date _lastPublishDate;
}