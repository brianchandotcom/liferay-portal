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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLContent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLContent
 * @generated
 */
@ProviderType
public class DLContentWrapper implements DLContent, ModelWrapper<DLContent> {
	public DLContentWrapper(DLContent dlContent) {
		_dlContent = dlContent;
	}

	@Override
	public Class<?> getModelClass() {
		return DLContent.class;
	}

	@Override
	public String getModelClassName() {
		return DLContent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("contentId", getContentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("path", getPath());
		attributes.put("version", getVersion());
		attributes.put("data", getData());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long contentId = (Long)attributes.get("contentId");

		if (contentId != null) {
			setContentId(contentId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		String path = (String)attributes.get("path");

		if (path != null) {
			setPath(path);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Blob data = (Blob)attributes.get("data");

		if (data != null) {
			setData(data);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DLContentWrapper((DLContent)_dlContent.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent) {
		return _dlContent.compareTo(dlContent);
	}

	/**
	* Returns the company ID of this document library content.
	*
	* @return the company ID of this document library content
	*/
	@Override
	public long getCompanyId() {
		return _dlContent.getCompanyId();
	}

	/**
	* Returns the content ID of this document library content.
	*
	* @return the content ID of this document library content
	*/
	@Override
	public long getContentId() {
		return _dlContent.getContentId();
	}

	/**
	* Returns the data of this document library content.
	*
	* @return the data of this document library content
	*/
	@Override
	public Blob getData() {
		return _dlContent.getData();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlContent.getExpandoBridge();
	}

	/**
	* Returns the group ID of this document library content.
	*
	* @return the group ID of this document library content
	*/
	@Override
	public long getGroupId() {
		return _dlContent.getGroupId();
	}

	/**
	* Returns the path of this document library content.
	*
	* @return the path of this document library content
	*/
	@Override
	public java.lang.String getPath() {
		return _dlContent.getPath();
	}

	/**
	* Returns the primary key of this document library content.
	*
	* @return the primary key of this document library content
	*/
	@Override
	public long getPrimaryKey() {
		return _dlContent.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlContent.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this document library content.
	*
	* @return the repository ID of this document library content
	*/
	@Override
	public long getRepositoryId() {
		return _dlContent.getRepositoryId();
	}

	/**
	* Returns the size of this document library content.
	*
	* @return the size of this document library content
	*/
	@Override
	public long getSize() {
		return _dlContent.getSize();
	}

	/**
	* Returns the version of this document library content.
	*
	* @return the version of this document library content
	*/
	@Override
	public java.lang.String getVersion() {
		return _dlContent.getVersion();
	}

	@Override
	public int hashCode() {
		return _dlContent.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _dlContent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlContent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _dlContent.isNew();
	}

	@Override
	public void persist() {
		_dlContent.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlContent.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this document library content.
	*
	* @param companyId the company ID of this document library content
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlContent.setCompanyId(companyId);
	}

	/**
	* Sets the content ID of this document library content.
	*
	* @param contentId the content ID of this document library content
	*/
	@Override
	public void setContentId(long contentId) {
		_dlContent.setContentId(contentId);
	}

	/**
	* Sets the data of this document library content.
	*
	* @param data the data of this document library content
	*/
	@Override
	public void setData(Blob data) {
		_dlContent.setData(data);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlContent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlContent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlContent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this document library content.
	*
	* @param groupId the group ID of this document library content
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlContent.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_dlContent.setNew(n);
	}

	/**
	* Sets the path of this document library content.
	*
	* @param path the path of this document library content
	*/
	@Override
	public void setPath(java.lang.String path) {
		_dlContent.setPath(path);
	}

	/**
	* Sets the primary key of this document library content.
	*
	* @param primaryKey the primary key of this document library content
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlContent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlContent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this document library content.
	*
	* @param repositoryId the repository ID of this document library content
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_dlContent.setRepositoryId(repositoryId);
	}

	/**
	* Sets the size of this document library content.
	*
	* @param size the size of this document library content
	*/
	@Override
	public void setSize(long size) {
		_dlContent.setSize(size);
	}

	/**
	* Sets the version of this document library content.
	*
	* @param version the version of this document library content
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_dlContent.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLContent> toCacheModel() {
		return _dlContent.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLContent toEscapedModel() {
		return new DLContentWrapper(_dlContent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlContent.toString();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLContent toUnescapedModel() {
		return new DLContentWrapper(_dlContent.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlContent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLContentWrapper)) {
			return false;
		}

		DLContentWrapper dlContentWrapper = (DLContentWrapper)obj;

		if (Validator.equals(_dlContent, dlContentWrapper._dlContent)) {
			return true;
		}

		return false;
	}

	@Override
	public DLContent getWrappedModel() {
		return _dlContent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlContent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlContent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlContent.resetOriginalValues();
	}

	private final DLContent _dlContent;
}