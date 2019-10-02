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

package com.liferay.expando.exportimport.internal.model.adapter;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Akos Thurzo
 */
public class StagedExpandoColumnImpl implements StagedExpandoColumn {

	public StagedExpandoColumnImpl() {
	}

	public StagedExpandoColumnImpl(ExpandoColumn expandoColumn) {
		_expandoColumn = expandoColumn;

		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getExpandoTable(
				expandoColumn.getTableId());
		}
		catch (PortalException pe) {
			throw new RuntimeException(
				"Could not find expando table for tableId=" +
					expandoColumn.getTableId(),
				pe);
		}

		_expandoTableClassName = expandoTable.getClassName();
		_expandoTableName = expandoTable.getName();
	}

	@Override
	public Object clone() {
		StagedExpandoColumnImpl stagedExpandoColumnImpl =
			new StagedExpandoColumnImpl();

		stagedExpandoColumnImpl._expandoColumn =
			(ExpandoColumn)_expandoColumn.clone();
		stagedExpandoColumnImpl._expandoTableClassName = _expandoTableClassName;
		stagedExpandoColumnImpl._expandoTableName = _expandoTableName;

		return stagedExpandoColumnImpl;
	}

	@Override
	public int compareTo(ExpandoColumn expandoColumn) {
		return _expandoColumn.compareTo(expandoColumn);
	}

	@Override
	public long getColumnId() {
		return _expandoColumn.getColumnId();
	}

	@Override
	public long getCompanyId() {
		return _expandoColumn.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public String getDefaultData() {
		return _expandoColumn.getDefaultData();
	}

	@Override
	public Serializable getDefaultValue() {
		return _expandoColumn.getDefaultValue();
	}

	@Override
	public String getDisplayName(Locale locale) {
		return _expandoColumn.getDisplayName(locale);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _expandoColumn.getModelAttributes();
	}

	@Override
	public Class<?> getModelClass() {
		return _expandoColumn.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _expandoColumn.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public String getName() {
		return _expandoColumn.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _expandoColumn.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _expandoColumn.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedExpandoColumn.class);
	}

	@Override
	public long getTableId() {
		return _expandoColumn.getTableId();
	}

	@Override
	public int getType() {
		return _expandoColumn.getType();
	}

	@Override
	public String getTypeSettings() {
		return _expandoColumn.getTypeSettings();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		return _expandoColumn.getTypeSettingsProperties();
	}

	@Override
	public String getUuid() {
		return StringBundler.concat(
			_expandoTableClassName, StringPool.POUND, _expandoTableName,
			StringPool.POUND, getName());
	}

	@Override
	public boolean isCachedModel() {
		return _expandoColumn.isCachedModel();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _expandoColumn.isEntityCacheEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoColumn.isEscapedModel();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _expandoColumn.isFinderCacheEnabled();
	}

	@Override
	public boolean isNew() {
		return _expandoColumn.isNew();
	}

	@Override
	public void persist() {
		_expandoColumn.persist();
	}

	@Override
	public void resetOriginalValues() {
		_expandoColumn.resetOriginalValues();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoColumn.setCachedModel(cachedModel);
	}

	@Override
	public void setColumnId(long columnId) {
		_expandoColumn.setColumnId(columnId);
	}

	@Override
	public void setCompanyId(long companyId) {
		_expandoColumn.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDefaultData(String defaultData) {
		_expandoColumn.setDefaultData(defaultData);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_expandoColumn.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_expandoColumn.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_expandoColumn.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		_expandoColumn.setModelAttributes(attributes);
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setName(String name) {
		_expandoColumn.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_expandoColumn.setNew(n);
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_expandoColumn.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_expandoColumn.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setTableId(long tableId) {
		_expandoColumn.setTableId(tableId);
	}

	@Override
	public void setType(int type) {
		_expandoColumn.setType(type);
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_expandoColumn.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_expandoColumn.setTypeSettingsProperties(typeSettingsProperties);
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CacheModel<ExpandoColumn> toCacheModel() {
		return _expandoColumn.toCacheModel();
	}

	@Override
	public ExpandoColumn toEscapedModel() {
		return _expandoColumn.toEscapedModel();
	}

	@Override
	public ExpandoColumn toUnescapedModel() {
		return _expandoColumn.toUnescapedModel();
	}

	@Override
	public String toXmlString() {
		return _expandoColumn.toXmlString();
	}

	private ExpandoColumn _expandoColumn;
	private String _expandoTableClassName;
	private String _expandoTableName;

}