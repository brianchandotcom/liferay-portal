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
 * This class is used by SOAP remote services, specifically {@link com.liferay.search.experiences.service.http.SXPElementServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SXPElementSoap implements Serializable {

	public static SXPElementSoap toSoapModel(SXPElement model) {
		SXPElementSoap soapModel = new SXPElementSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSXPElementId(model.getSXPElementId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setConfigurationJSON(model.getConfigurationJSON());
		soapModel.setHidden(model.isHidden());
		soapModel.setReadOnly(model.isReadOnly());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static SXPElementSoap[] toSoapModels(SXPElement[] models) {
		SXPElementSoap[] soapModels = new SXPElementSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SXPElementSoap[][] toSoapModels(SXPElement[][] models) {
		SXPElementSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SXPElementSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SXPElementSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SXPElementSoap[] toSoapModels(List<SXPElement> models) {
		List<SXPElementSoap> soapModels = new ArrayList<SXPElementSoap>(
			models.size());

		for (SXPElement model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SXPElementSoap[soapModels.size()]);
	}

	public SXPElementSoap() {
	}

	public long getPrimaryKey() {
		return _sxpElementId;
	}

	public void setPrimaryKey(long pk) {
		setSXPElementId(pk);
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

	public long getSXPElementId() {
		return _sxpElementId;
	}

	public void setSXPElementId(long sxpElementId) {
		_sxpElementId = sxpElementId;
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

	public boolean getHidden() {
		return _hidden;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _sxpElementId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private int _status;
	private String _title;
	private String _description;
	private String _configurationJSON;
	private boolean _hidden;
	private boolean _readOnly;
	private int _type;

}