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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DLFileVersionPreview}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPreview
 * @generated
 */
public class DLFileVersionPreviewWrapper
	implements DLFileVersionPreview, ModelWrapper<DLFileVersionPreview> {

	public DLFileVersionPreviewWrapper(
		DLFileVersionPreview dlFileVersionPreview) {

		_dlFileVersionPreview = dlFileVersionPreview;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileVersionPreview.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileVersionPreview.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dlFileVersionPreviewId", getDlFileVersionPreviewId());
		attributes.put("groupId", getGroupId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("previewStatus", getPreviewStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dlFileVersionPreviewId = (Long)attributes.get(
			"dlFileVersionPreviewId");

		if (dlFileVersionPreviewId != null) {
			setDlFileVersionPreviewId(dlFileVersionPreviewId);
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

		Integer previewStatus = (Integer)attributes.get("previewStatus");

		if (previewStatus != null) {
			setPreviewStatus(previewStatus);
		}
	}

	@Override
	public Object clone() {
		return new DLFileVersionPreviewWrapper(
			(DLFileVersionPreview)_dlFileVersionPreview.clone());
	}

	@Override
	public int compareTo(DLFileVersionPreview dlFileVersionPreview) {
		return _dlFileVersionPreview.compareTo(dlFileVersionPreview);
	}

	/**
	 * Returns the dl file version preview ID of this dl file version preview.
	 *
	 * @return the dl file version preview ID of this dl file version preview
	 */
	@Override
	public long getDlFileVersionPreviewId() {
		return _dlFileVersionPreview.getDlFileVersionPreviewId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileVersionPreview.getExpandoBridge();
	}

	/**
	 * Returns the file entry ID of this dl file version preview.
	 *
	 * @return the file entry ID of this dl file version preview
	 */
	@Override
	public long getFileEntryId() {
		return _dlFileVersionPreview.getFileEntryId();
	}

	/**
	 * Returns the file version ID of this dl file version preview.
	 *
	 * @return the file version ID of this dl file version preview
	 */
	@Override
	public long getFileVersionId() {
		return _dlFileVersionPreview.getFileVersionId();
	}

	/**
	 * Returns the group ID of this dl file version preview.
	 *
	 * @return the group ID of this dl file version preview
	 */
	@Override
	public long getGroupId() {
		return _dlFileVersionPreview.getGroupId();
	}

	/**
	 * Returns the preview status of this dl file version preview.
	 *
	 * @return the preview status of this dl file version preview
	 */
	@Override
	public int getPreviewStatus() {
		return _dlFileVersionPreview.getPreviewStatus();
	}

	/**
	 * Returns the primary key of this dl file version preview.
	 *
	 * @return the primary key of this dl file version preview
	 */
	@Override
	public long getPrimaryKey() {
		return _dlFileVersionPreview.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlFileVersionPreview.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _dlFileVersionPreview.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileVersionPreview.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileVersionPreview.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _dlFileVersionPreview.isNew();
	}

	@Override
	public void persist() {
		_dlFileVersionPreview.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileVersionPreview.setCachedModel(cachedModel);
	}

	/**
	 * Sets the dl file version preview ID of this dl file version preview.
	 *
	 * @param dlFileVersionPreviewId the dl file version preview ID of this dl file version preview
	 */
	@Override
	public void setDlFileVersionPreviewId(long dlFileVersionPreviewId) {
		_dlFileVersionPreview.setDlFileVersionPreviewId(dlFileVersionPreviewId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_dlFileVersionPreview.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_dlFileVersionPreview.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_dlFileVersionPreview.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the file entry ID of this dl file version preview.
	 *
	 * @param fileEntryId the file entry ID of this dl file version preview
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileVersionPreview.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the file version ID of this dl file version preview.
	 *
	 * @param fileVersionId the file version ID of this dl file version preview
	 */
	@Override
	public void setFileVersionId(long fileVersionId) {
		_dlFileVersionPreview.setFileVersionId(fileVersionId);
	}

	/**
	 * Sets the group ID of this dl file version preview.
	 *
	 * @param groupId the group ID of this dl file version preview
	 */
	@Override
	public void setGroupId(long groupId) {
		_dlFileVersionPreview.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileVersionPreview.setNew(n);
	}

	/**
	 * Sets the preview status of this dl file version preview.
	 *
	 * @param previewStatus the preview status of this dl file version preview
	 */
	@Override
	public void setPreviewStatus(int previewStatus) {
		_dlFileVersionPreview.setPreviewStatus(previewStatus);
	}

	/**
	 * Sets the primary key of this dl file version preview.
	 *
	 * @param primaryKey the primary key of this dl file version preview
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileVersionPreview.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlFileVersionPreview.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DLFileVersionPreview>
		toCacheModel() {

		return _dlFileVersionPreview.toCacheModel();
	}

	@Override
	public DLFileVersionPreview toEscapedModel() {
		return new DLFileVersionPreviewWrapper(
			_dlFileVersionPreview.toEscapedModel());
	}

	@Override
	public String toString() {
		return _dlFileVersionPreview.toString();
	}

	@Override
	public DLFileVersionPreview toUnescapedModel() {
		return new DLFileVersionPreviewWrapper(
			_dlFileVersionPreview.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _dlFileVersionPreview.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DLFileVersionPreviewWrapper)) {
			return false;
		}

		DLFileVersionPreviewWrapper dlFileVersionPreviewWrapper =
			(DLFileVersionPreviewWrapper)object;

		if (Objects.equals(
				_dlFileVersionPreview,
				dlFileVersionPreviewWrapper._dlFileVersionPreview)) {

			return true;
		}

		return false;
	}

	@Override
	public DLFileVersionPreview getWrappedModel() {
		return _dlFileVersionPreview;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileVersionPreview.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileVersionPreview.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileVersionPreview.resetOriginalValues();
	}

	private final DLFileVersionPreview _dlFileVersionPreview;

}