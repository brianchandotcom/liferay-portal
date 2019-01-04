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

package com.liferay.adaptive.media.image.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link AMImageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntry
 * @generated
 */
@ProviderType
public class AMImageEntryWrapper extends BaseModelWrapper<AMImageEntry>
	implements AMImageEntry, ModelWrapper<AMImageEntry> {
	public AMImageEntryWrapper(AMImageEntry amImageEntry) {
		super(amImageEntry);
	}

	/**
	* Returns the am image entry ID of this am image entry.
	*
	* @return the am image entry ID of this am image entry
	*/
	@Override
	public long getAmImageEntryId() {
		return model.getAmImageEntryId();
	}

	/**
	* Returns the company ID of this am image entry.
	*
	* @return the company ID of this am image entry
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the configuration uuid of this am image entry.
	*
	* @return the configuration uuid of this am image entry
	*/
	@Override
	public String getConfigurationUuid() {
		return model.getConfigurationUuid();
	}

	/**
	* Returns the create date of this am image entry.
	*
	* @return the create date of this am image entry
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the file version ID of this am image entry.
	*
	* @return the file version ID of this am image entry
	*/
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	/**
	* Returns the group ID of this am image entry.
	*
	* @return the group ID of this am image entry
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the height of this am image entry.
	*
	* @return the height of this am image entry
	*/
	@Override
	public int getHeight() {
		return model.getHeight();
	}

	/**
	* Returns the mime type of this am image entry.
	*
	* @return the mime type of this am image entry
	*/
	@Override
	public String getMimeType() {
		return model.getMimeType();
	}

	/**
	* Returns the primary key of this am image entry.
	*
	* @return the primary key of this am image entry
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the size of this am image entry.
	*
	* @return the size of this am image entry
	*/
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	* Returns the uuid of this am image entry.
	*
	* @return the uuid of this am image entry
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns the width of this am image entry.
	*
	* @return the width of this am image entry
	*/
	@Override
	public int getWidth() {
		return model.getWidth();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the am image entry ID of this am image entry.
	*
	* @param amImageEntryId the am image entry ID of this am image entry
	*/
	@Override
	public void setAmImageEntryId(long amImageEntryId) {
		model.setAmImageEntryId(amImageEntryId);
	}

	/**
	* Sets the company ID of this am image entry.
	*
	* @param companyId the company ID of this am image entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the configuration uuid of this am image entry.
	*
	* @param configurationUuid the configuration uuid of this am image entry
	*/
	@Override
	public void setConfigurationUuid(String configurationUuid) {
		model.setConfigurationUuid(configurationUuid);
	}

	/**
	* Sets the create date of this am image entry.
	*
	* @param createDate the create date of this am image entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the file version ID of this am image entry.
	*
	* @param fileVersionId the file version ID of this am image entry
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the group ID of this am image entry.
	*
	* @param groupId the group ID of this am image entry
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the height of this am image entry.
	*
	* @param height the height of this am image entry
	*/
	@Override
	public void setHeight(int height) {
		model.setHeight(height);
	}

	/**
	* Sets the mime type of this am image entry.
	*
	* @param mimeType the mime type of this am image entry
	*/
	@Override
	public void setMimeType(String mimeType) {
		model.setMimeType(mimeType);
	}

	/**
	* Sets the primary key of this am image entry.
	*
	* @param primaryKey the primary key of this am image entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the size of this am image entry.
	*
	* @param size the size of this am image entry
	*/
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	* Sets the uuid of this am image entry.
	*
	* @param uuid the uuid of this am image entry
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	* Sets the width of this am image entry.
	*
	* @param width the width of this am image entry
	*/
	@Override
	public void setWidth(int width) {
		model.setWidth(width);
	}

	@Override
	protected AMImageEntryWrapper wrap(AMImageEntry amImageEntry) {
		return new AMImageEntryWrapper(amImageEntry);
	}
}