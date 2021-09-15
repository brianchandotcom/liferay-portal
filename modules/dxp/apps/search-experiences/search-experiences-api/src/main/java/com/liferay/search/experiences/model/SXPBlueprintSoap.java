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

package com.liferay.search.experiences.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.search.experiences.service.http.SXPBlueprintServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SXPBlueprintSoap implements Serializable {

	public static SXPBlueprintSoap toSoapModel(SXPBlueprint model) {
		SXPBlueprintSoap soapModel = new SXPBlueprintSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSXPBlueprintId(model.getSXPBlueprintId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setConfigurationJSON(model.getConfigurationJSON());
		soapModel.setSelectedElementsJSON(model.getSelectedElementsJSON());

		return soapModel;
	}

	public static SXPBlueprintSoap[] toSoapModels(SXPBlueprint[] models) {
		SXPBlueprintSoap[] soapModels = new SXPBlueprintSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SXPBlueprintSoap[][] toSoapModels(SXPBlueprint[][] models) {
		SXPBlueprintSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SXPBlueprintSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SXPBlueprintSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SXPBlueprintSoap[] toSoapModels(List<SXPBlueprint> models) {
		List<SXPBlueprintSoap> soapModels = new ArrayList<SXPBlueprintSoap>(
			models.size());

		for (SXPBlueprint model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SXPBlueprintSoap[soapModels.size()]);
	}

	public SXPBlueprintSoap() {
	}

	public long getPrimaryKey() {
		return _sxpBlueprintId;
	}

	public void setPrimaryKey(long pk) {
		setSXPBlueprintId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSXPBlueprintId() {
		return _sxpBlueprintId;
	}

	public void setSXPBlueprintId(long sxpBlueprintId) {
		_sxpBlueprintId = sxpBlueprintId;
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

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getConfigurationJSON() {
		return _configurationJSON;
	}

	public void setConfigurationJSON(String configurationJSON) {
		_configurationJSON = configurationJSON;
	}

	public String getSelectedElementsJSON() {
		return _selectedElementsJSON;
	}

	public void setSelectedElementsJSON(String selectedElementsJSON) {
		_selectedElementsJSON = selectedElementsJSON;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _sxpBlueprintId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private String _title;
	private String _description;
	private String _configurationJSON;
	private String _selectedElementsJSON;

}