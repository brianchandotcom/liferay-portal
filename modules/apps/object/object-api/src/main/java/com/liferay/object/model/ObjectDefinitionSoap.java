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

package com.liferay.object.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectDefinitionServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectDefinitionSoap implements Serializable {

	public static ObjectDefinitionSoap toSoapModel(ObjectDefinition model) {
		ObjectDefinitionSoap soapModel = new ObjectDefinitionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectDefinitionId(model.getObjectDefinitionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDescriptionObjectFieldId(
			model.getDescriptionObjectFieldId());
		soapModel.setTitleObjectFieldId(model.getTitleObjectFieldId());
		soapModel.setActive(model.isActive());
		soapModel.setDBTableName(model.getDBTableName());
		soapModel.setLabel(model.getLabel());
		soapModel.setClassName(model.getClassName());
		soapModel.setName(model.getName());
		soapModel.setPanelAppOrder(model.getPanelAppOrder());
		soapModel.setPanelCategoryKey(model.getPanelCategoryKey());
		soapModel.setPKObjectFieldDBColumnName(
			model.getPKObjectFieldDBColumnName());
		soapModel.setPKObjectFieldName(model.getPKObjectFieldName());
		soapModel.setPluralLabel(model.getPluralLabel());
		soapModel.setScope(model.getScope());
		soapModel.setSystem(model.isSystem());
		soapModel.setVersion(model.getVersion());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static ObjectDefinitionSoap[] toSoapModels(
		ObjectDefinition[] models) {

		ObjectDefinitionSoap[] soapModels =
			new ObjectDefinitionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectDefinitionSoap[][] toSoapModels(
		ObjectDefinition[][] models) {

		ObjectDefinitionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectDefinitionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectDefinitionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectDefinitionSoap[] toSoapModels(
		List<ObjectDefinition> models) {

		List<ObjectDefinitionSoap> soapModels =
			new ArrayList<ObjectDefinitionSoap>(models.size());

		for (ObjectDefinition model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectDefinitionSoap[soapModels.size()]);
	}

	public ObjectDefinitionSoap() {
	}

	public long getPrimaryKey() {
		return _objectDefinitionId;
	}

	public void setPrimaryKey(long pk) {
		setObjectDefinitionId(pk);
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

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
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

	public long getDescriptionObjectFieldId() {
		return _descriptionObjectFieldId;
	}

	public void setDescriptionObjectFieldId(long descriptionObjectFieldId) {
		_descriptionObjectFieldId = descriptionObjectFieldId;
	}

	public long getTitleObjectFieldId() {
		return _titleObjectFieldId;
	}

	public void setTitleObjectFieldId(long titleObjectFieldId) {
		_titleObjectFieldId = titleObjectFieldId;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getDBTableName() {
		return _dbTableName;
	}

	public void setDBTableName(String dbTableName) {
		_dbTableName = dbTableName;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getPanelAppOrder() {
		return _panelAppOrder;
	}

	public void setPanelAppOrder(String panelAppOrder) {
		_panelAppOrder = panelAppOrder;
	}

	public String getPanelCategoryKey() {
		return _panelCategoryKey;
	}

	public void setPanelCategoryKey(String panelCategoryKey) {
		_panelCategoryKey = panelCategoryKey;
	}

	public String getPKObjectFieldDBColumnName() {
		return _pkObjectFieldDBColumnName;
	}

	public void setPKObjectFieldDBColumnName(String pkObjectFieldDBColumnName) {
		_pkObjectFieldDBColumnName = pkObjectFieldDBColumnName;
	}

	public String getPKObjectFieldName() {
		return _pkObjectFieldName;
	}

	public void setPKObjectFieldName(String pkObjectFieldName) {
		_pkObjectFieldName = pkObjectFieldName;
	}

	public String getPluralLabel() {
		return _pluralLabel;
	}

	public void setPluralLabel(String pluralLabel) {
		_pluralLabel = pluralLabel;
	}

	public String getScope() {
		return _scope;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public boolean getSystem() {
		return _system;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	public int getVersion() {
		return _version;
	}

	public void setVersion(int version) {
		_version = version;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectDefinitionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _descriptionObjectFieldId;
	private long _titleObjectFieldId;
	private boolean _active;
	private String _dbTableName;
	private String _label;
	private String _className;
	private String _name;
	private String _panelAppOrder;
	private String _panelCategoryKey;
	private String _pkObjectFieldDBColumnName;
	private String _pkObjectFieldName;
	private String _pluralLabel;
	private String _scope;
	private boolean _system;
	private int _version;
	private int _status;

}