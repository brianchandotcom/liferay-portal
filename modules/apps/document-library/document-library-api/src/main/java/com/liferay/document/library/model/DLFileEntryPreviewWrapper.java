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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

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
public class DLFileEntryPreviewWrapper extends BaseModelWrapper<DLFileEntryPreview>
	implements DLFileEntryPreview, ModelWrapper<DLFileEntryPreview> {
	public DLFileEntryPreviewWrapper(DLFileEntryPreview dlFileEntryPreview) {
		super(dlFileEntryPreview);
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

	/**
	* Returns the file entry ID of this dl file entry preview.
	*
	* @return the file entry ID of this dl file entry preview
	*/
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	* Returns the file entry preview ID of this dl file entry preview.
	*
	* @return the file entry preview ID of this dl file entry preview
	*/
	@Override
	public long getFileEntryPreviewId() {
		return model.getFileEntryPreviewId();
	}

	/**
	* Returns the file version ID of this dl file entry preview.
	*
	* @return the file version ID of this dl file entry preview
	*/
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	/**
	* Returns the group ID of this dl file entry preview.
	*
	* @return the group ID of this dl file entry preview
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the preview type of this dl file entry preview.
	*
	* @return the preview type of this dl file entry preview
	*/
	@Override
	public int getPreviewType() {
		return model.getPreviewType();
	}

	/**
	* Returns the primary key of this dl file entry preview.
	*
	* @return the primary key of this dl file entry preview
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
	* Sets the file entry ID of this dl file entry preview.
	*
	* @param fileEntryId the file entry ID of this dl file entry preview
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file entry preview ID of this dl file entry preview.
	*
	* @param fileEntryPreviewId the file entry preview ID of this dl file entry preview
	*/
	@Override
	public void setFileEntryPreviewId(long fileEntryPreviewId) {
		model.setFileEntryPreviewId(fileEntryPreviewId);
	}

	/**
	* Sets the file version ID of this dl file entry preview.
	*
	* @param fileVersionId the file version ID of this dl file entry preview
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the group ID of this dl file entry preview.
	*
	* @param groupId the group ID of this dl file entry preview
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the preview type of this dl file entry preview.
	*
	* @param previewType the preview type of this dl file entry preview
	*/
	@Override
	public void setPreviewType(int previewType) {
		model.setPreviewType(previewType);
	}

	/**
	* Sets the primary key of this dl file entry preview.
	*
	* @param primaryKey the primary key of this dl file entry preview
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected DLFileEntryPreviewWrapper wrap(
		DLFileEntryPreview dlFileEntryPreview) {
		return new DLFileEntryPreviewWrapper(dlFileEntryPreview);
	}
}