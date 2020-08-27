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

package com.liferay.commerce.bom.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.bom.service.http.CommerceBOMDefinitionServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceBOMDefinitionSoap implements Serializable {

	public static CommerceBOMDefinitionSoap toSoapModel(
		CommerceBOMDefinition model) {

		CommerceBOMDefinitionSoap soapModel = new CommerceBOMDefinitionSoap();

		soapModel.setCommerceBOMDefinitionId(
			model.getCommerceBOMDefinitionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceBOMFolderId(model.getCommerceBOMFolderId());
		soapModel.setCPAttachmentFileEntryId(
			model.getCPAttachmentFileEntryId());
		soapModel.setName(model.getName());
		soapModel.setFriendlyUrl(model.getFriendlyUrl());

		return soapModel;
	}

	public static CommerceBOMDefinitionSoap[] toSoapModels(
		CommerceBOMDefinition[] models) {

		CommerceBOMDefinitionSoap[] soapModels =
			new CommerceBOMDefinitionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceBOMDefinitionSoap[][] toSoapModels(
		CommerceBOMDefinition[][] models) {

		CommerceBOMDefinitionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceBOMDefinitionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceBOMDefinitionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceBOMDefinitionSoap[] toSoapModels(
		List<CommerceBOMDefinition> models) {

		List<CommerceBOMDefinitionSoap> soapModels =
			new ArrayList<CommerceBOMDefinitionSoap>(models.size());

		for (CommerceBOMDefinition model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceBOMDefinitionSoap[soapModels.size()]);
	}

	public CommerceBOMDefinitionSoap() {
	}

	public long getPrimaryKey() {
		return _commerceBOMDefinitionId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceBOMDefinitionId(pk);
	}

	public long getCommerceBOMDefinitionId() {
		return _commerceBOMDefinitionId;
	}

	public void setCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		_commerceBOMDefinitionId = commerceBOMDefinitionId;
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

	public long getCommerceBOMFolderId() {
		return _commerceBOMFolderId;
	}

	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		_commerceBOMFolderId = commerceBOMFolderId;
	}

	public long getCPAttachmentFileEntryId() {
		return _CPAttachmentFileEntryId;
	}

	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		_CPAttachmentFileEntryId = CPAttachmentFileEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getFriendlyUrl() {
		return _friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		_friendlyUrl = friendlyUrl;
	}

	private long _commerceBOMDefinitionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceBOMFolderId;
	private long _CPAttachmentFileEntryId;
	private String _name;
	private String _friendlyUrl;

}