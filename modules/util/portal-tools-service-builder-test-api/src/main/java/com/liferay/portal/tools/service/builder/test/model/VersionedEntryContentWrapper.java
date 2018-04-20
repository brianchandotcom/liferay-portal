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
 * This class is a wrapper for {@link VersionedEntryContent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContent
 * @generated
 */
@ProviderType
public class VersionedEntryContentWrapper implements VersionedEntryContent,
	ModelWrapper<VersionedEntryContent> {
	public VersionedEntryContentWrapper(
		VersionedEntryContent versionedEntryContent) {
		_versionedEntryContent = versionedEntryContent;
	}

	@Override
	public Class<?> getModelClass() {
		return VersionedEntryContent.class;
	}

	@Override
	public String getModelClassName() {
		return VersionedEntryContent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("versionedEntryContentId", getVersionedEntryContentId());
		attributes.put("versionedEntryId", getVersionedEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("content", getContent());
		attributes.put("headId", getHeadId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
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

		Long headId = (Long)attributes.get("headId");

		if (headId != null) {
			setHeadId(headId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new VersionedEntryContentWrapper((VersionedEntryContent)_versionedEntryContent.clone());
	}

	@Override
	public int compareTo(VersionedEntryContent versionedEntryContent) {
		return _versionedEntryContent.compareTo(versionedEntryContent);
	}

	/**
	* Returns the content of this versioned entry content.
	*
	* @return the content of this versioned entry content
	*/
	@Override
	public java.lang.String getContent() {
		return _versionedEntryContent.getContent();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _versionedEntryContent.getExpandoBridge();
	}

	/**
	* Returns the head ID of this versioned entry content.
	*
	* @return the head ID of this versioned entry content
	*/
	@Override
	public long getHeadId() {
		return _versionedEntryContent.getHeadId();
	}

	/**
	* Returns the language ID of this versioned entry content.
	*
	* @return the language ID of this versioned entry content
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _versionedEntryContent.getLanguageId();
	}

	/**
	* Returns the mvcc version of this versioned entry content.
	*
	* @return the mvcc version of this versioned entry content
	*/
	@Override
	public long getMvccVersion() {
		return _versionedEntryContent.getMvccVersion();
	}

	/**
	* Returns the primary key of this versioned entry content.
	*
	* @return the primary key of this versioned entry content
	*/
	@Override
	public long getPrimaryKey() {
		return _versionedEntryContent.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _versionedEntryContent.getPrimaryKeyObj();
	}

	/**
	* Returns the versioned entry content ID of this versioned entry content.
	*
	* @return the versioned entry content ID of this versioned entry content
	*/
	@Override
	public long getVersionedEntryContentId() {
		return _versionedEntryContent.getVersionedEntryContentId();
	}

	/**
	* Returns the versioned entry ID of this versioned entry content.
	*
	* @return the versioned entry ID of this versioned entry content
	*/
	@Override
	public long getVersionedEntryId() {
		return _versionedEntryContent.getVersionedEntryId();
	}

	@Override
	public int hashCode() {
		return _versionedEntryContent.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _versionedEntryContent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _versionedEntryContent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _versionedEntryContent.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_versionedEntryContent.setCachedModel(cachedModel);
	}

	/**
	* Sets the content of this versioned entry content.
	*
	* @param content the content of this versioned entry content
	*/
	@Override
	public void setContent(java.lang.String content) {
		_versionedEntryContent.setContent(content);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_versionedEntryContent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_versionedEntryContent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_versionedEntryContent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the head ID of this versioned entry content.
	*
	* @param headId the head ID of this versioned entry content
	*/
	@Override
	public void setHeadId(long headId) {
		_versionedEntryContent.setHeadId(headId);
	}

	/**
	* Sets the language ID of this versioned entry content.
	*
	* @param languageId the language ID of this versioned entry content
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_versionedEntryContent.setLanguageId(languageId);
	}

	/**
	* Sets the mvcc version of this versioned entry content.
	*
	* @param mvccVersion the mvcc version of this versioned entry content
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_versionedEntryContent.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_versionedEntryContent.setNew(n);
	}

	/**
	* Sets the primary key of this versioned entry content.
	*
	* @param primaryKey the primary key of this versioned entry content
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_versionedEntryContent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_versionedEntryContent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the versioned entry content ID of this versioned entry content.
	*
	* @param versionedEntryContentId the versioned entry content ID of this versioned entry content
	*/
	@Override
	public void setVersionedEntryContentId(long versionedEntryContentId) {
		_versionedEntryContent.setVersionedEntryContentId(versionedEntryContentId);
	}

	/**
	* Sets the versioned entry ID of this versioned entry content.
	*
	* @param versionedEntryId the versioned entry ID of this versioned entry content
	*/
	@Override
	public void setVersionedEntryId(long versionedEntryId) {
		_versionedEntryContent.setVersionedEntryId(versionedEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<VersionedEntryContent> toCacheModel() {
		return _versionedEntryContent.toCacheModel();
	}

	@Override
	public VersionedEntryContent toEscapedModel() {
		return new VersionedEntryContentWrapper(_versionedEntryContent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _versionedEntryContent.toString();
	}

	@Override
	public VersionedEntryContent toUnescapedModel() {
		return new VersionedEntryContentWrapper(_versionedEntryContent.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _versionedEntryContent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryContentWrapper)) {
			return false;
		}

		VersionedEntryContentWrapper versionedEntryContentWrapper = (VersionedEntryContentWrapper)obj;

		if (Objects.equals(_versionedEntryContent,
					versionedEntryContentWrapper._versionedEntryContent)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDraft() {
		return _versionedEntryContent.isDraft();
	}

	@Override
	public void populateVersionModel(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		_versionedEntryContent.populateVersionModel(versionedEntryContentVersion);
	}

	@Override
	public VersionedEntryContent getWrappedModel() {
		return _versionedEntryContent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _versionedEntryContent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _versionedEntryContent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_versionedEntryContent.resetOriginalValues();
	}

	private final VersionedEntryContent _versionedEntryContent;
}