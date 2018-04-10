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

package com.liferay.portal.tools.service.builder.test.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link VersionedEntryContentVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContentVersion
 * @generated
 */
@ProviderType
public class VersionedEntryContentVersionWrapper
	implements VersionedEntryContentVersion,
		ModelWrapper<VersionedEntryContentVersion> {
	public VersionedEntryContentVersionWrapper(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		_versionedEntryContentVersion = versionedEntryContentVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return VersionedEntryContentVersion.class;
	}

	@Override
	public String getModelClassName() {
		return VersionedEntryContentVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("versionedEntryContentVersionId",
			getVersionedEntryContentVersionId());
		attributes.put("version", getVersion());
		attributes.put("versionedEntryContentId", getVersionedEntryContentId());
		attributes.put("versionedEntryId", getVersionedEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("content", getContent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long versionedEntryContentVersionId = (Long)attributes.get(
				"versionedEntryContentVersionId");

		if (versionedEntryContentVersionId != null) {
			setVersionedEntryContentVersionId(versionedEntryContentVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long versionedEntryContentId = (Long)attributes.get(
				"versionedEntryContentId");

		if (versionedEntryContentId != null) {
			setVersionedEntryContentId(versionedEntryContentId);
		}

		Long versionedEntryId = (Long)attributes.get("versionedEntryId");

		if (versionedEntryId != null) {
			setVersionedEntryId(versionedEntryId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new VersionedEntryContentVersionWrapper((VersionedEntryContentVersion)_versionedEntryContentVersion.clone());
	}

	@Override
	public int compareTo(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		return _versionedEntryContentVersion.compareTo(versionedEntryContentVersion);
	}

	/**
	* Returns the content of this versioned entry content version.
	*
	* @return the content of this versioned entry content version
	*/
	@Override
	public java.lang.String getContent() {
		return _versionedEntryContentVersion.getContent();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _versionedEntryContentVersion.getExpandoBridge();
	}

	/**
	* Returns the language ID of this versioned entry content version.
	*
	* @return the language ID of this versioned entry content version
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _versionedEntryContentVersion.getLanguageId();
	}

	/**
	* Returns the primary key of this versioned entry content version.
	*
	* @return the primary key of this versioned entry content version
	*/
	@Override
	public long getPrimaryKey() {
		return _versionedEntryContentVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _versionedEntryContentVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the version of this versioned entry content version.
	*
	* @return the version of this versioned entry content version
	*/
	@Override
	public int getVersion() {
		return _versionedEntryContentVersion.getVersion();
	}

	/**
	* Returns the versioned entry content ID of this versioned entry content version.
	*
	* @return the versioned entry content ID of this versioned entry content version
	*/
	@Override
	public long getVersionedEntryContentId() {
		return _versionedEntryContentVersion.getVersionedEntryContentId();
	}

	/**
	* Returns the versioned entry content version ID of this versioned entry content version.
	*
	* @return the versioned entry content version ID of this versioned entry content version
	*/
	@Override
	public long getVersionedEntryContentVersionId() {
		return _versionedEntryContentVersion.getVersionedEntryContentVersionId();
	}

	/**
	* Returns the versioned entry ID of this versioned entry content version.
	*
	* @return the versioned entry ID of this versioned entry content version
	*/
	@Override
	public long getVersionedEntryId() {
		return _versionedEntryContentVersion.getVersionedEntryId();
	}

	@Override
	public int hashCode() {
		return _versionedEntryContentVersion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _versionedEntryContentVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _versionedEntryContentVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _versionedEntryContentVersion.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_versionedEntryContentVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the content of this versioned entry content version.
	*
	* @param content the content of this versioned entry content version
	*/
	@Override
	public void setContent(java.lang.String content) {
		_versionedEntryContentVersion.setContent(content);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_versionedEntryContentVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_versionedEntryContentVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_versionedEntryContentVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the language ID of this versioned entry content version.
	*
	* @param languageId the language ID of this versioned entry content version
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_versionedEntryContentVersion.setLanguageId(languageId);
	}

	@Override
	public void setNew(boolean n) {
		_versionedEntryContentVersion.setNew(n);
	}

	/**
	* Sets the primary key of this versioned entry content version.
	*
	* @param primaryKey the primary key of this versioned entry content version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_versionedEntryContentVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_versionedEntryContentVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the version of this versioned entry content version.
	*
	* @param version the version of this versioned entry content version
	*/
	@Override
	public void setVersion(int version) {
		_versionedEntryContentVersion.setVersion(version);
	}

	/**
	* Sets the versioned entry content ID of this versioned entry content version.
	*
	* @param versionedEntryContentId the versioned entry content ID of this versioned entry content version
	*/
	@Override
	public void setVersionedEntryContentId(long versionedEntryContentId) {
		_versionedEntryContentVersion.setVersionedEntryContentId(versionedEntryContentId);
	}

	/**
	* Sets the versioned entry content version ID of this versioned entry content version.
	*
	* @param versionedEntryContentVersionId the versioned entry content version ID of this versioned entry content version
	*/
	@Override
	public void setVersionedEntryContentVersionId(
		long versionedEntryContentVersionId) {
		_versionedEntryContentVersion.setVersionedEntryContentVersionId(versionedEntryContentVersionId);
	}

	/**
	* Sets the versioned entry ID of this versioned entry content version.
	*
	* @param versionedEntryId the versioned entry ID of this versioned entry content version
	*/
	@Override
	public void setVersionedEntryId(long versionedEntryId) {
		_versionedEntryContentVersion.setVersionedEntryId(versionedEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<VersionedEntryContentVersion> toCacheModel() {
		return _versionedEntryContentVersion.toCacheModel();
	}

	@Override
	public VersionedEntryContentVersion toEscapedModel() {
		return new VersionedEntryContentVersionWrapper(_versionedEntryContentVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _versionedEntryContentVersion.toString();
	}

	@Override
	public VersionedEntryContentVersion toUnescapedModel() {
		return new VersionedEntryContentVersionWrapper(_versionedEntryContentVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _versionedEntryContentVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryContentVersionWrapper)) {
			return false;
		}

		VersionedEntryContentVersionWrapper versionedEntryContentVersionWrapper = (VersionedEntryContentVersionWrapper)obj;

		if (Objects.equals(_versionedEntryContentVersion,
					versionedEntryContentVersionWrapper._versionedEntryContentVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public long getVersionedModelId() {
		return _versionedEntryContentVersion.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		_versionedEntryContentVersion.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(
		VersionedEntryContent versionedEntryContent) {
		_versionedEntryContentVersion.populateVersionedModel(versionedEntryContent);
	}

	@Override
	public VersionedEntryContent toVersionedModel() {
		return _versionedEntryContentVersion.toVersionedModel();
	}

	@Override
	public VersionedEntryContentVersion getWrappedModel() {
		return _versionedEntryContentVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _versionedEntryContentVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _versionedEntryContentVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_versionedEntryContentVersion.resetOriginalValues();
	}

	private final VersionedEntryContentVersion _versionedEntryContentVersion;
}