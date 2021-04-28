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

package com.liferay.commerce.shop.by.diagram.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CPDefinitionDiagramSettingsServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionDiagramSettingsSoap implements Serializable {

	public static CPDefinitionDiagramSettingsSoap toSoapModel(
		CPDefinitionDiagramSettings model) {

		CPDefinitionDiagramSettingsSoap soapModel =
			new CPDefinitionDiagramSettingsSoap();

		soapModel.setCPDefinitionDiagramSettingsId(
			model.getCPDefinitionDiagramSettingsId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setAssetCategoryId(model.getAssetCategoryId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static CPDefinitionDiagramSettingsSoap[] toSoapModels(
		CPDefinitionDiagramSettings[] models) {

		CPDefinitionDiagramSettingsSoap[] soapModels =
			new CPDefinitionDiagramSettingsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramSettingsSoap[][] toSoapModels(
		CPDefinitionDiagramSettings[][] models) {

		CPDefinitionDiagramSettingsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionDiagramSettingsSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionDiagramSettingsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramSettingsSoap[] toSoapModels(
		List<CPDefinitionDiagramSettings> models) {

		List<CPDefinitionDiagramSettingsSoap> soapModels =
			new ArrayList<CPDefinitionDiagramSettingsSoap>(models.size());

		for (CPDefinitionDiagramSettings model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDefinitionDiagramSettingsSoap[soapModels.size()]);
	}

	public CPDefinitionDiagramSettingsSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionDiagramSettingsId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionDiagramSettingsId(pk);
	}

	public long getCPDefinitionDiagramSettingsId() {
		return _CPDefinitionDiagramSettingsId;
	}

	public void setCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		_CPDefinitionDiagramSettingsId = CPDefinitionDiagramSettingsId;
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

	public long getAssetCategoryId() {
		return _assetCategoryId;
	}

	public void setAssetCategoryId(long assetCategoryId) {
		_assetCategoryId = assetCategoryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _CPDefinitionDiagramSettingsId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _assetCategoryId;
	private String _name;

}