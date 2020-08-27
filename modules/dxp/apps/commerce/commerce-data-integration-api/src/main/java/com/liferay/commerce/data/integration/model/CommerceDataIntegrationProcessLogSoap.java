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

package com.liferay.commerce.data.integration.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.data.integration.service.http.CommerceDataIntegrationProcessLogServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceDataIntegrationProcessLogSoap implements Serializable {

	public static CommerceDataIntegrationProcessLogSoap toSoapModel(
		CommerceDataIntegrationProcessLog model) {

		CommerceDataIntegrationProcessLogSoap soapModel =
			new CommerceDataIntegrationProcessLogSoap();

		soapModel.setCommerceDataIntegrationProcessLogId(
			model.getCommerceDataIntegrationProcessLogId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCDataIntegrationProcessId(
			model.getCDataIntegrationProcessId());
		soapModel.setError(model.getError());
		soapModel.setOutput(model.getOutput());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static CommerceDataIntegrationProcessLogSoap[] toSoapModels(
		CommerceDataIntegrationProcessLog[] models) {

		CommerceDataIntegrationProcessLogSoap[] soapModels =
			new CommerceDataIntegrationProcessLogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDataIntegrationProcessLogSoap[][] toSoapModels(
		CommerceDataIntegrationProcessLog[][] models) {

		CommerceDataIntegrationProcessLogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceDataIntegrationProcessLogSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDataIntegrationProcessLogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDataIntegrationProcessLogSoap[] toSoapModels(
		List<CommerceDataIntegrationProcessLog> models) {

		List<CommerceDataIntegrationProcessLogSoap> soapModels =
			new ArrayList<CommerceDataIntegrationProcessLogSoap>(models.size());

		for (CommerceDataIntegrationProcessLog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceDataIntegrationProcessLogSoap[soapModels.size()]);
	}

	public CommerceDataIntegrationProcessLogSoap() {
	}

	public long getPrimaryKey() {
		return _commerceDataIntegrationProcessLogId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDataIntegrationProcessLogId(pk);
	}

	public long getCommerceDataIntegrationProcessLogId() {
		return _commerceDataIntegrationProcessLogId;
	}

	public void setCommerceDataIntegrationProcessLogId(
		long commerceDataIntegrationProcessLogId) {

		_commerceDataIntegrationProcessLogId =
			commerceDataIntegrationProcessLogId;
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

	public long getCDataIntegrationProcessId() {
		return _CDataIntegrationProcessId;
	}

	public void setCDataIntegrationProcessId(long CDataIntegrationProcessId) {
		_CDataIntegrationProcessId = CDataIntegrationProcessId;
	}

	public String getError() {
		return _error;
	}

	public void setError(String error) {
		_error = error;
	}

	public String getOutput() {
		return _output;
	}

	public void setOutput(String output) {
		_output = output;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _commerceDataIntegrationProcessLogId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CDataIntegrationProcessId;
	private String _error;
	private String _output;
	private Date _startDate;
	private Date _endDate;
	private int _status;

}