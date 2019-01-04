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

package com.liferay.sync.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link SyncDLFileVersionDiff}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLFileVersionDiff
 * @generated
 */
@ProviderType
public class SyncDLFileVersionDiffWrapper extends BaseModelWrapper<SyncDLFileVersionDiff>
	implements SyncDLFileVersionDiff, ModelWrapper<SyncDLFileVersionDiff> {
	public SyncDLFileVersionDiffWrapper(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {
		super(syncDLFileVersionDiff);
	}

	/**
	* Returns the data file entry ID of this sync dl file version diff.
	*
	* @return the data file entry ID of this sync dl file version diff
	*/
	@Override
	public long getDataFileEntryId() {
		return model.getDataFileEntryId();
	}

	/**
	* Returns the expiration date of this sync dl file version diff.
	*
	* @return the expiration date of this sync dl file version diff
	*/
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	* Returns the file entry ID of this sync dl file version diff.
	*
	* @return the file entry ID of this sync dl file version diff
	*/
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	* Returns the primary key of this sync dl file version diff.
	*
	* @return the primary key of this sync dl file version diff
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the size of this sync dl file version diff.
	*
	* @return the size of this sync dl file version diff
	*/
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	* Returns the source file version ID of this sync dl file version diff.
	*
	* @return the source file version ID of this sync dl file version diff
	*/
	@Override
	public long getSourceFileVersionId() {
		return model.getSourceFileVersionId();
	}

	/**
	* Returns the sync dl file version diff ID of this sync dl file version diff.
	*
	* @return the sync dl file version diff ID of this sync dl file version diff
	*/
	@Override
	public long getSyncDLFileVersionDiffId() {
		return model.getSyncDLFileVersionDiffId();
	}

	/**
	* Returns the target file version ID of this sync dl file version diff.
	*
	* @return the target file version ID of this sync dl file version diff
	*/
	@Override
	public long getTargetFileVersionId() {
		return model.getTargetFileVersionId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the data file entry ID of this sync dl file version diff.
	*
	* @param dataFileEntryId the data file entry ID of this sync dl file version diff
	*/
	@Override
	public void setDataFileEntryId(long dataFileEntryId) {
		model.setDataFileEntryId(dataFileEntryId);
	}

	/**
	* Sets the expiration date of this sync dl file version diff.
	*
	* @param expirationDate the expiration date of this sync dl file version diff
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	* Sets the file entry ID of this sync dl file version diff.
	*
	* @param fileEntryId the file entry ID of this sync dl file version diff
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the primary key of this sync dl file version diff.
	*
	* @param primaryKey the primary key of this sync dl file version diff
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the size of this sync dl file version diff.
	*
	* @param size the size of this sync dl file version diff
	*/
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	* Sets the source file version ID of this sync dl file version diff.
	*
	* @param sourceFileVersionId the source file version ID of this sync dl file version diff
	*/
	@Override
	public void setSourceFileVersionId(long sourceFileVersionId) {
		model.setSourceFileVersionId(sourceFileVersionId);
	}

	/**
	* Sets the sync dl file version diff ID of this sync dl file version diff.
	*
	* @param syncDLFileVersionDiffId the sync dl file version diff ID of this sync dl file version diff
	*/
	@Override
	public void setSyncDLFileVersionDiffId(long syncDLFileVersionDiffId) {
		model.setSyncDLFileVersionDiffId(syncDLFileVersionDiffId);
	}

	/**
	* Sets the target file version ID of this sync dl file version diff.
	*
	* @param targetFileVersionId the target file version ID of this sync dl file version diff
	*/
	@Override
	public void setTargetFileVersionId(long targetFileVersionId) {
		model.setTargetFileVersionId(targetFileVersionId);
	}

	@Override
	protected SyncDLFileVersionDiffWrapper wrap(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {
		return new SyncDLFileVersionDiffWrapper(syncDLFileVersionDiff);
	}
}