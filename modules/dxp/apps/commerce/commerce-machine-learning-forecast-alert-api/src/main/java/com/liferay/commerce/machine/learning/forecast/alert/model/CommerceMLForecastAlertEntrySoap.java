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

package com.liferay.commerce.machine.learning.forecast.alert.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.machine.learning.forecast.alert.service.http.CommerceMLForecastAlertEntryServiceSoap}.
 *
 * @author Riccardo Ferrari
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceMLForecastAlertEntrySoap implements Serializable {

	public static CommerceMLForecastAlertEntrySoap toSoapModel(
		CommerceMLForecastAlertEntry model) {

		CommerceMLForecastAlertEntrySoap soapModel =
			new CommerceMLForecastAlertEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceMLForecastAlertEntryId(
			model.getCommerceMLForecastAlertEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceAccountId(model.getCommerceAccountId());
		soapModel.setActual(model.getActual());
		soapModel.setForecast(model.getForecast());
		soapModel.setTimestamp(model.getTimestamp());
		soapModel.setRelativeChange(model.getRelativeChange());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static CommerceMLForecastAlertEntrySoap[] toSoapModels(
		CommerceMLForecastAlertEntry[] models) {

		CommerceMLForecastAlertEntrySoap[] soapModels =
			new CommerceMLForecastAlertEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceMLForecastAlertEntrySoap[][] toSoapModels(
		CommerceMLForecastAlertEntry[][] models) {

		CommerceMLForecastAlertEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceMLForecastAlertEntrySoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceMLForecastAlertEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceMLForecastAlertEntrySoap[] toSoapModels(
		List<CommerceMLForecastAlertEntry> models) {

		List<CommerceMLForecastAlertEntrySoap> soapModels =
			new ArrayList<CommerceMLForecastAlertEntrySoap>(models.size());

		for (CommerceMLForecastAlertEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceMLForecastAlertEntrySoap[soapModels.size()]);
	}

	public CommerceMLForecastAlertEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceMLForecastAlertEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceMLForecastAlertEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceMLForecastAlertEntryId() {
		return _commerceMLForecastAlertEntryId;
	}

	public void setCommerceMLForecastAlertEntryId(
		long commerceMLForecastAlertEntryId) {

		_commerceMLForecastAlertEntryId = commerceMLForecastAlertEntryId;
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

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	public double getActual() {
		return _actual;
	}

	public void setActual(double actual) {
		_actual = actual;
	}

	public double getForecast() {
		return _forecast;
	}

	public void setForecast(double forecast) {
		_forecast = forecast;
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public double getRelativeChange() {
		return _relativeChange;
	}

	public void setRelativeChange(double relativeChange) {
		_relativeChange = relativeChange;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private String _uuid;
	private long _commerceMLForecastAlertEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceAccountId;
	private double _actual;
	private double _forecast;
	private Date _timestamp;
	private double _relativeChange;
	private int _status;

}