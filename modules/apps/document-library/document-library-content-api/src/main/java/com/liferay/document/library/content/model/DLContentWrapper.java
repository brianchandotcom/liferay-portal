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

package com.liferay.document.library.content.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.sql.Blob;

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
public class DLContentWrapper extends BaseModelWrapper<DLContent>
	implements DLContent, ModelWrapper<DLContent> {
	public DLContentWrapper(DLContent dlContent) {
		super(dlContent);
	}

	/**
	* Returns the company ID of this document library content.
	*
	* @return the company ID of this document library content
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the content ID of this document library content.
	*
	* @return the content ID of this document library content
	*/
	@Override
	public long getContentId() {
		return model.getContentId();
	}

	/**
	* Returns the data of this document library content.
	*
	* @return the data of this document library content
	*/
	@Override
	public Blob getData() {
		return model.getData();
	}

	/**
	* Returns the group ID of this document library content.
	*
	* @return the group ID of this document library content
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the path of this document library content.
	*
	* @return the path of this document library content
	*/
	@Override
	public String getPath() {
		return model.getPath();
	}

	/**
	* Returns the primary key of this document library content.
	*
	* @return the primary key of this document library content
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the repository ID of this document library content.
	*
	* @return the repository ID of this document library content
	*/
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	* Returns the size of this document library content.
	*
	* @return the size of this document library content
	*/
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	* Returns the version of this document library content.
	*
	* @return the version of this document library content
	*/
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this document library content.
	*
	* @param companyId the company ID of this document library content
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the content ID of this document library content.
	*
	* @param contentId the content ID of this document library content
	*/
	@Override
	public void setContentId(long contentId) {
		model.setContentId(contentId);
	}

	/**
	* Sets the data of this document library content.
	*
	* @param data the data of this document library content
	*/
	@Override
	public void setData(Blob data) {
		model.setData(data);
	}

	/**
	* Sets the group ID of this document library content.
	*
	* @param groupId the group ID of this document library content
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the path of this document library content.
	*
	* @param path the path of this document library content
	*/
	@Override
	public void setPath(String path) {
		model.setPath(path);
	}

	/**
	* Sets the primary key of this document library content.
	*
	* @param primaryKey the primary key of this document library content
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the repository ID of this document library content.
	*
	* @param repositoryId the repository ID of this document library content
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	* Sets the size of this document library content.
	*
	* @param size the size of this document library content
	*/
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	* Sets the version of this document library content.
	*
	* @param version the version of this document library content
	*/
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	protected DLContentWrapper wrap(DLContent dlContent) {
		return new DLContentWrapper(dlContent);
	}
}