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

package com.liferay.document.library.model;

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
 * This class is a wrapper for {@link DLFileEntryPreview}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPreview
 * @generated
 */
@ProviderType
public class DLFileEntryPreviewWrapper implements DLFileEntryPreview,
	ModelWrapper<DLFileEntryPreview> {
	public DLFileEntryPreviewWrapper(DLFileEntryPreview dlFileEntryPreview) {
		_dlFileEntryPreview = dlFileEntryPreview;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileEntryPreview.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileEntryPreview.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("fileEntryPreviewId", getFileEntryPreviewId());
		attributes.put("groupId", getGroupId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("previewType", getPreviewType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long fileEntryPreviewId = (Long)attributes.get("fileEntryPreviewId");

		if (fileEntryPreviewId != null) {
			setFileEntryPreviewId(fileEntryPreviewId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
		}

		Integer previewType = (Integer)attributes.get("previewType");

		if (previewType != null) {
			setPreviewType(previewType);
		}
	}

	@Override
	public Object clone() {
		return new DLFileEntryPreviewWrapper((DLFileEntryPreview)_dlFileEntryPreview.clone());
	}

	@Override
	public int compareTo(DLFileEntryPreview dlFileEntryPreview) {
		return _dlFileEntryPreview.compareTo(dlFileEntryPreview);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileEntryPreview.getExpandoBridge();
	}

	/**
	* Returns the file entry ID of this dl file entry preview.
	*
	* @return the file entry ID of this dl file entry preview
	*/
	@Override
	public long getFileEntryId() {
		return _dlFileEntryPreview.getFileEntryId();
	}

	/**
	* Returns the file entry preview ID of this dl file entry preview.
	*
	* @return the file entry preview ID of this dl file entry preview
	*/
	@Override
	public long getFileEntryPreviewId() {
		return _dlFileEntryPreview.getFileEntryPreviewId();
	}

	/**
	* Returns the file version ID of this dl file entry preview.
	*
	* @return the file version ID of this dl file entry preview
	*/
	@Override
	public long getFileVersionId() {
		return _dlFileEntryPreview.getFileVersionId();
	}

	/**
	* Returns the group ID of this dl file entry preview.
	*
	* @return the group ID of this dl file entry preview
	*/
	@Override
	public long getGroupId() {
		return _dlFileEntryPreview.getGroupId();
	}

	/**
	* Returns the preview type of this dl file entry preview.
	*
	* @return the preview type of this dl file entry preview
	*/
	@Override
	public int getPreviewType() {
		return _dlFileEntryPreview.getPreviewType();
	}

	/**
	* Returns the primary key of this dl file entry preview.
	*
	* @return the primary key of this dl file entry preview
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileEntryPreview.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlFileEntryPreview.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _dlFileEntryPreview.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntryPreview.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntryPreview.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _dlFileEntryPreview.isNew();
	}

	@Override
	public void persist() {
		_dlFileEntryPreview.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntryPreview.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_dlFileEntryPreview.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_dlFileEntryPreview.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_dlFileEntryPreview.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this dl file entry preview.
	*
	* @param fileEntryId the file entry ID of this dl file entry preview
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntryPreview.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file entry preview ID of this dl file entry preview.
	*
	* @param fileEntryPreviewId the file entry preview ID of this dl file entry preview
	*/
	@Override
	public void setFileEntryPreviewId(long fileEntryPreviewId) {
		_dlFileEntryPreview.setFileEntryPreviewId(fileEntryPreviewId);
	}

	/**
	* Sets the file version ID of this dl file entry preview.
	*
	* @param fileVersionId the file version ID of this dl file entry preview
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_dlFileEntryPreview.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the group ID of this dl file entry preview.
	*
	* @param groupId the group ID of this dl file entry preview
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlFileEntryPreview.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntryPreview.setNew(n);
	}

	/**
	* Sets the preview type of this dl file entry preview.
	*
	* @param previewType the preview type of this dl file entry preview
	*/
	@Override
	public void setPreviewType(int previewType) {
		_dlFileEntryPreview.setPreviewType(previewType);
	}

	/**
	* Sets the primary key of this dl file entry preview.
	*
	* @param primaryKey the primary key of this dl file entry preview
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntryPreview.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlFileEntryPreview.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DLFileEntryPreview> toCacheModel() {
		return _dlFileEntryPreview.toCacheModel();
	}

	@Override
	public DLFileEntryPreview toEscapedModel() {
		return new DLFileEntryPreviewWrapper(_dlFileEntryPreview.toEscapedModel());
	}

	@Override
	public String toString() {
		return _dlFileEntryPreview.toString();
	}

	@Override
	public DLFileEntryPreview toUnescapedModel() {
		return new DLFileEntryPreviewWrapper(_dlFileEntryPreview.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _dlFileEntryPreview.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryPreviewWrapper)) {
			return false;
		}

		DLFileEntryPreviewWrapper dlFileEntryPreviewWrapper = (DLFileEntryPreviewWrapper)obj;

		if (Objects.equals(_dlFileEntryPreview,
					dlFileEntryPreviewWrapper._dlFileEntryPreview)) {
			return true;
		}

		return false;
	}

	@Override
	public DLFileEntryPreview getWrappedModel() {
		return _dlFileEntryPreview;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileEntryPreview.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileEntryPreview.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntryPreview.resetOriginalValues();
	}

	private final DLFileEntryPreview _dlFileEntryPreview;
}