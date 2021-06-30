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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CPDefinitionDiagramSettingServiceSoap}.
 *
 * @author Andrea Sbarra
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionDiagramSettingSoap implements Serializable {

	public static CPDefinitionDiagramSettingSoap toSoapModel(
		CPDefinitionDiagramSetting model) {

		CPDefinitionDiagramSettingSoap soapModel =
			new CPDefinitionDiagramSettingSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionDiagramSettingId(
			model.getCPDefinitionDiagramSettingId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setRadius(model.getRadius());
		soapModel.setColor(model.getColor());

		return soapModel;
	}

	public static CPDefinitionDiagramSettingSoap[] toSoapModels(
		CPDefinitionDiagramSetting[] models) {

		CPDefinitionDiagramSettingSoap[] soapModels =
			new CPDefinitionDiagramSettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramSettingSoap[][] toSoapModels(
		CPDefinitionDiagramSetting[][] models) {

		CPDefinitionDiagramSettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDefinitionDiagramSettingSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionDiagramSettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramSettingSoap[] toSoapModels(
		List<CPDefinitionDiagramSetting> models) {

		List<CPDefinitionDiagramSettingSoap> soapModels =
			new ArrayList<CPDefinitionDiagramSettingSoap>(models.size());

		for (CPDefinitionDiagramSetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDefinitionDiagramSettingSoap[soapModels.size()]);
	}

	public CPDefinitionDiagramSettingSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionDiagramSettingId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionDiagramSettingId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionDiagramSettingId() {
		return _CPDefinitionDiagramSettingId;
	}

	public void setCPDefinitionDiagramSettingId(
		long CPDefinitionDiagramSettingId) {

		_CPDefinitionDiagramSettingId = CPDefinitionDiagramSettingId;
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

	public double getRadius() {
		return _radius;
	}

	public void setRadius(double radius) {
		_radius = radius;
	}

	public String getColor() {
		return _color;
	}

	public void setColor(String color) {
		_color = color;
	}

	private String _uuid;
	private long _CPDefinitionDiagramSettingId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private double _radius;
	private String _color;

}