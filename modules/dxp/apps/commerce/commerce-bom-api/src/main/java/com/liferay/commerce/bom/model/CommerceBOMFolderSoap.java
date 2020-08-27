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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.bom.service.http.CommerceBOMFolderServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceBOMFolderSoap implements Serializable {

	public static CommerceBOMFolderSoap toSoapModel(CommerceBOMFolder model) {
		CommerceBOMFolderSoap soapModel = new CommerceBOMFolderSoap();

		soapModel.setCommerceBOMFolderId(model.getCommerceBOMFolderId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setParentCommerceBOMFolderId(
			model.getParentCommerceBOMFolderId());
		soapModel.setName(model.getName());
		soapModel.setLogoId(model.getLogoId());
		soapModel.setTreePath(model.getTreePath());

		return soapModel;
	}

	public static CommerceBOMFolderSoap[] toSoapModels(
		CommerceBOMFolder[] models) {

		CommerceBOMFolderSoap[] soapModels =
			new CommerceBOMFolderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceBOMFolderSoap[][] toSoapModels(
		CommerceBOMFolder[][] models) {

		CommerceBOMFolderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceBOMFolderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceBOMFolderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceBOMFolderSoap[] toSoapModels(
		List<CommerceBOMFolder> models) {

		List<CommerceBOMFolderSoap> soapModels =
			new ArrayList<CommerceBOMFolderSoap>(models.size());

		for (CommerceBOMFolder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceBOMFolderSoap[soapModels.size()]);
	}

	public CommerceBOMFolderSoap() {
	}

	public long getPrimaryKey() {
		return _commerceBOMFolderId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceBOMFolderId(pk);
	}

	public long getCommerceBOMFolderId() {
		return _commerceBOMFolderId;
	}

	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		_commerceBOMFolderId = commerceBOMFolderId;
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

	public long getParentCommerceBOMFolderId() {
		return _parentCommerceBOMFolderId;
	}

	public void setParentCommerceBOMFolderId(long parentCommerceBOMFolderId) {
		_parentCommerceBOMFolderId = parentCommerceBOMFolderId;
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

	public String getTreePath() {
		return _treePath;
	}

	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	private long _commerceBOMFolderId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentCommerceBOMFolderId;
	private String _name;
	private long _logoId;
	private String _treePath;

}