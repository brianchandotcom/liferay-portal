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
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Akos Thurzo
 */
public class StagedExpandoTableImpl implements StagedExpandoTable {

	public StagedExpandoTableImpl() {
	}

	public StagedExpandoTableImpl(ExpandoTable expandoTable) {
		_className = expandoTable.getClassName();
		_expandoTable = expandoTable;
	}

	@Override
	public Object clone() {
		return new StagedExpandoTableImpl(_expandoTable);
	}

	@Override
	public int compareTo(ExpandoTable expandoTable) {
		return _expandoTable.compareTo(expandoTable);
	}

	@Override
	public String getClassName() {
		if (!Validator.isBlank(_className)) {
			return _className;
		}

		_className = _expandoTable.getClassName();

		return _className;
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(getClassName());
	}

	@Override
	public long getCompanyId() {
		return _expandoTable.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _expandoTable.getModelAttributes();
	}

	@Override
	public Class<?> getModelClass() {
		return _expandoTable.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _expandoTable.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public String getName() {
		return _expandoTable.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _expandoTable.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _expandoTable.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedExpandoTable.class);
	}

	@Override
	public long getTableId() {
		return _expandoTable.getTableId();
	}

	@Override
	public String getUuid() {
		return getClassName() + StringPool.POUND + getName();
	}

	@Override
	public boolean isCachedModel() {
		return _expandoTable.isCachedModel();
	}

	@Override
	public boolean isDefaultTable() {
		return _expandoTable.isDefaultTable();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _expandoTable.isEntityCacheEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoTable.isEscapedModel();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _expandoTable.isFinderCacheEnabled();
	}

	@Override
	public boolean isNew() {
		return _expandoTable.isNew();
	}

	@Override
	public void persist() {
		_expandoTable.persist();
	}

	@Override
	public void resetOriginalValues() {
		_expandoTable.resetOriginalValues();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoTable.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_expandoTable.setClassName(className);
	}

	@Override
	public void setClassNameId(long classNameId) {
		_expandoTable.setClassNameId(classNameId);
	}

	@Override
	public void setCompanyId(long companyId) {
		_expandoTable.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_expandoTable.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_expandoTable.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_expandoTable.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		_expandoTable.setModelAttributes(attributes);
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setName(String name) {
		_expandoTable.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_expandoTable.setNew(n);
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_expandoTable.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_expandoTable.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setTableId(long tableId) {
		_expandoTable.setTableId(tableId);
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CacheModel<ExpandoTable> toCacheModel() {
		return _expandoTable.toCacheModel();
	}

	@Override
	public ExpandoTable toEscapedModel() {
		return _expandoTable.toEscapedModel();
	}

	@Override
	public ExpandoTable toUnescapedModel() {
		return _expandoTable.toUnescapedModel();
	}

	@Override
	public String toXmlString() {
		return _expandoTable.toXmlString();
	}

	private String _className;
	private ExpandoTable _expandoTable;

}