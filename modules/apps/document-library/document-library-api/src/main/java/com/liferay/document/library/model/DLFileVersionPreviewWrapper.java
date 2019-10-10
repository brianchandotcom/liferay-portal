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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<DLFileVersionPreview>
	implements DLFileVersionPreview, ModelWrapper<DLFileVersionPreview> {

	public DLFileVersionPreviewWrapper(
		DLFileVersionPreview dlFileVersionPreview) {

		super(dlFileVersionPreview);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dlFileVersionPreviewId", getDlFileVersionPreviewId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
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

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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
	 * Returns the company ID of this dl file version preview.
	 *
	 * @return the company ID of this dl file version preview
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the dl file version preview ID of this dl file version preview.
	 *
	 * @return the dl file version preview ID of this dl file version preview
	 */
	@Override
	public long getDlFileVersionPreviewId() {
		return model.getDlFileVersionPreviewId();
	}

	/**
	 * Returns the file entry ID of this dl file version preview.
	 *
	 * @return the file entry ID of this dl file version preview
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the file version ID of this dl file version preview.
	 *
	 * @return the file version ID of this dl file version preview
	 */
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	/**
	 * Returns the group ID of this dl file version preview.
	 *
	 * @return the group ID of this dl file version preview
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the preview status of this dl file version preview.
	 *
	 * @return the preview status of this dl file version preview
	 */
	@Override
	public int getPreviewStatus() {
		return model.getPreviewStatus();
	}

	/**
	 * Returns the primary key of this dl file version preview.
	 *
	 * @return the primary key of this dl file version preview
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a dl file version preview model instance should use the <code>DLFileVersionPreview</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this dl file version preview.
	 *
	 * @param companyId the company ID of this dl file version preview
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the dl file version preview ID of this dl file version preview.
	 *
	 * @param dlFileVersionPreviewId the dl file version preview ID of this dl file version preview
	 */
	@Override
	public void setDlFileVersionPreviewId(long dlFileVersionPreviewId) {
		model.setDlFileVersionPreviewId(dlFileVersionPreviewId);
	}

	/**
	 * Sets the file entry ID of this dl file version preview.
	 *
	 * @param fileEntryId the file entry ID of this dl file version preview
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the file version ID of this dl file version preview.
	 *
	 * @param fileVersionId the file version ID of this dl file version preview
	 */
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	 * Sets the group ID of this dl file version preview.
	 *
	 * @param groupId the group ID of this dl file version preview
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the preview status of this dl file version preview.
	 *
	 * @param previewStatus the preview status of this dl file version preview
	 */
	@Override
	public void setPreviewStatus(int previewStatus) {
		model.setPreviewStatus(previewStatus);
	}

	/**
	 * Sets the primary key of this dl file version preview.
	 *
	 * @param primaryKey the primary key of this dl file version preview
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected DLFileVersionPreviewWrapper wrap(
		DLFileVersionPreview dlFileVersionPreview) {

		return new DLFileVersionPreviewWrapper(dlFileVersionPreview);
	}

}