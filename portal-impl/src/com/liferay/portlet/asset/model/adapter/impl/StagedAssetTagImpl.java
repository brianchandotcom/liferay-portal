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

package com.liferay.portlet.asset.model.adapter.impl;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.adapter.StagedAssetTag;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class StagedAssetTagImpl implements StagedAssetTag {

	public StagedAssetTagImpl(AssetTag assetTag) {
		_assetTag = assetTag;
	}

	@Override
	public Object clone() {
		return new StagedAssetTagImpl(_assetTag);
	}

	@Override
	public int compareTo(AssetTag assetTag) {
		return _assetTag.compareTo(assetTag);
	}

	@Override
	public int getAssetCount() {
		return _assetTag.getAssetCount();
	}

	@Override
	public long getCompanyId() {
		return _assetTag.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _assetTag.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetTag.getExpandoBridge();
	}

	@Override
	public long getGroupId() {
		return _assetTag.getGroupId();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _assetTag.getModelAttributes();
	}

	@Override
	public Class<?> getModelClass() {
		return StagedAssetTag.class;
	}

	@Override
	public String getModelClassName() {
		return StagedAssetTag.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _assetTag.getModifiedDate();
	}

	@Override
	public String getName() {
		return _assetTag.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _assetTag.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetTag.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedAssetTag.class);
	}

	@Override
	public long getTagId() {
		return _assetTag.getTagId();
	}

	@Override
	public long getUserId() {
		return _assetTag.getUserId();
	}

	@Override
	public String getUserName() {
		return _assetTag.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _assetTag.getUserUuid();
	}

	@Override
	public String getUuid() {
		return getName();
	}

	@Override
	public boolean isCachedModel() {
		return false;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return false;
	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return false;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void persist() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetOriginalValues() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAssetCount(int assetCount) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCompanyId(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCreateDate(Date createDate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setGroupId(long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNew(boolean n) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTagId(long tagId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserId(long userId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserName(String userName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserUuid(String userUuid) {
		_assetTag.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CacheModel<AssetTag> toCacheModel() {
		return _assetTag.toCacheModel();
	}

	@Override
	public AssetTag toEscapedModel() {
		return _assetTag.toEscapedModel();
	}

	@Override
	public AssetTag toUnescapedModel() {
		return _assetTag.toUnescapedModel();
	}

	@Override
	public String toXmlString() {
		return _assetTag.toXmlString();
	}

	private final AssetTag _assetTag;

}