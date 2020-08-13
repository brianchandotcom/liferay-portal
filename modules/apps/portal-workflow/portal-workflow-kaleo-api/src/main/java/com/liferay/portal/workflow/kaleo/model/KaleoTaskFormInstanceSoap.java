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

package com.liferay.portal.workflow.kaleo.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class KaleoTaskFormInstanceSoap implements Serializable {

	public static KaleoTaskFormInstanceSoap toSoapModel(
		KaleoTaskFormInstance model) {

		KaleoTaskFormInstanceSoap soapModel = new KaleoTaskFormInstanceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setKaleoTaskFormInstanceId(
			model.getKaleoTaskFormInstanceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setKaleoDefinitionId(model.getKaleoDefinitionId());
		soapModel.setKaleoDefinitionVersionId(
			model.getKaleoDefinitionVersionId());
		soapModel.setKaleoInstanceId(model.getKaleoInstanceId());
		soapModel.setKaleoTaskId(model.getKaleoTaskId());
		soapModel.setKaleoTaskInstanceTokenId(
			model.getKaleoTaskInstanceTokenId());
		soapModel.setKaleoTaskFormId(model.getKaleoTaskFormId());
		soapModel.setFormValues(model.getFormValues());
		soapModel.setFormValueEntryGroupId(model.getFormValueEntryGroupId());
		soapModel.setFormValueEntryId(model.getFormValueEntryId());
		soapModel.setFormValueEntryUuid(model.getFormValueEntryUuid());
		soapModel.setMetadata(model.getMetadata());

		return soapModel;
	}

	public static KaleoTaskFormInstanceSoap[] toSoapModels(
		KaleoTaskFormInstance[] models) {

		KaleoTaskFormInstanceSoap[] soapModels =
			new KaleoTaskFormInstanceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static KaleoTaskFormInstanceSoap[][] toSoapModels(
		KaleoTaskFormInstance[][] models) {

		KaleoTaskFormInstanceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new KaleoTaskFormInstanceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new KaleoTaskFormInstanceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static KaleoTaskFormInstanceSoap[] toSoapModels(
		List<KaleoTaskFormInstance> models) {

		List<KaleoTaskFormInstanceSoap> soapModels =
			new ArrayList<KaleoTaskFormInstanceSoap>(models.size());

		for (KaleoTaskFormInstance model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new KaleoTaskFormInstanceSoap[soapModels.size()]);
	}

	public KaleoTaskFormInstanceSoap() {
	}

	public long getPrimaryKey() {
		return _kaleoTaskFormInstanceId;
	}

	public void setPrimaryKey(long pk) {
		setKaleoTaskFormInstanceId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getKaleoTaskFormInstanceId() {
		return _kaleoTaskFormInstanceId;
	}

	public void setKaleoTaskFormInstanceId(long kaleoTaskFormInstanceId) {
		_kaleoTaskFormInstanceId = kaleoTaskFormInstanceId;
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

	public long getKaleoDefinitionId() {
		return _kaleoDefinitionId;
	}

	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		_kaleoDefinitionId = kaleoDefinitionId;
	}

	public long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
	}

	public long getKaleoInstanceId() {
		return _kaleoInstanceId;
	}

	public void setKaleoInstanceId(long kaleoInstanceId) {
		_kaleoInstanceId = kaleoInstanceId;
	}

	public long getKaleoTaskId() {
		return _kaleoTaskId;
	}

	public void setKaleoTaskId(long kaleoTaskId) {
		_kaleoTaskId = kaleoTaskId;
	}

	public long getKaleoTaskInstanceTokenId() {
		return _kaleoTaskInstanceTokenId;
	}

	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		_kaleoTaskInstanceTokenId = kaleoTaskInstanceTokenId;
	}

	public long getKaleoTaskFormId() {
		return _kaleoTaskFormId;
	}

	public void setKaleoTaskFormId(long kaleoTaskFormId) {
		_kaleoTaskFormId = kaleoTaskFormId;
	}

	public String getFormValues() {
		return _formValues;
	}

	public void setFormValues(String formValues) {
		_formValues = formValues;
	}

	public long getFormValueEntryGroupId() {
		return _formValueEntryGroupId;
	}

	public void setFormValueEntryGroupId(long formValueEntryGroupId) {
		_formValueEntryGroupId = formValueEntryGroupId;
	}

	public long getFormValueEntryId() {
		return _formValueEntryId;
	}

	public void setFormValueEntryId(long formValueEntryId) {
		_formValueEntryId = formValueEntryId;
	}

	public String getFormValueEntryUuid() {
		return _formValueEntryUuid;
	}

	public void setFormValueEntryUuid(String formValueEntryUuid) {
		_formValueEntryUuid = formValueEntryUuid;
	}

	public String getMetadata() {
		return _metadata;
	}

	public void setMetadata(String metadata) {
		_metadata = metadata;
	}

	private long _mvccVersion;
	private long _kaleoTaskFormInstanceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _kaleoDefinitionId;
	private long _kaleoDefinitionVersionId;
	private long _kaleoInstanceId;
	private long _kaleoTaskId;
	private long _kaleoTaskInstanceTokenId;
	private long _kaleoTaskFormId;
	private String _formValues;
	private long _formValueEntryGroupId;
	private long _formValueEntryId;
	private String _formValueEntryUuid;
	private String _metadata;

}