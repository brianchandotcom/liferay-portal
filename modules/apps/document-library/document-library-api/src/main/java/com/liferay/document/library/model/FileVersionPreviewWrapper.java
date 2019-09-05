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

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FileVersionPreview}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FileVersionPreview
 * @generated
 */
public class FileVersionPreviewWrapper extends BaseModelWrapper<FileVersionPreview>
	implements FileVersionPreview, ModelWrapper<FileVersionPreview> {
	public FileVersionPreviewWrapper(FileVersionPreview fileVersionPreview) {
		super(fileVersionPreview);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("fileVersionPreviewId", getFileVersionPreviewId());
		attributes.put("groupId", getGroupId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("previewStatus", getPreviewStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long fileVersionPreviewId = (Long)attributes.get("fileVersionPreviewId");

		if (fileVersionPreviewId != null) {
			setFileVersionPreviewId(fileVersionPreviewId);
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

	/**
	* Returns the file entry ID of this file version preview.
	*
	* @return the file entry ID of this file version preview
	*/
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	* Returns the file version ID of this file version preview.
	*
	* @return the file version ID of this file version preview
	*/
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	/**
	* Returns the file version preview ID of this file version preview.
	*
	* @return the file version preview ID of this file version preview
	*/
	@Override
	public long getFileVersionPreviewId() {
		return model.getFileVersionPreviewId();
	}

	/**
	* Returns the group ID of this file version preview.
	*
	* @return the group ID of this file version preview
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the preview status of this file version preview.
	*
	* @return the preview status of this file version preview
	*/
	@Override
	public int getPreviewStatus() {
		return model.getPreviewStatus();
	}

	/**
	* Returns the primary key of this file version preview.
	*
	* @return the primary key of this file version preview
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the file entry ID of this file version preview.
	*
	* @param fileEntryId the file entry ID of this file version preview
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file version ID of this file version preview.
	*
	* @param fileVersionId the file version ID of this file version preview
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the file version preview ID of this file version preview.
	*
	* @param fileVersionPreviewId the file version preview ID of this file version preview
	*/
	@Override
	public void setFileVersionPreviewId(long fileVersionPreviewId) {
		model.setFileVersionPreviewId(fileVersionPreviewId);
	}

	/**
	* Sets the group ID of this file version preview.
	*
	* @param groupId the group ID of this file version preview
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the preview status of this file version preview.
	*
	* @param previewStatus the preview status of this file version preview
	*/
	@Override
	public void setPreviewStatus(int previewStatus) {
		model.setPreviewStatus(previewStatus);
	}

	/**
	* Sets the primary key of this file version preview.
	*
	* @param primaryKey the primary key of this file version preview
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected FileVersionPreviewWrapper wrap(
		FileVersionPreview fileVersionPreview) {
		return new FileVersionPreviewWrapper(fileVersionPreview);
	}
}