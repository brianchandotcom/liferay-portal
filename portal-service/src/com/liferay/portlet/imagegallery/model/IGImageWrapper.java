/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.model;

/**
 * <p>
 * This class is a wrapper for {@link IGImage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImage
 * @generated
 */
public class IGImageWrapper implements IGImage {
	public IGImageWrapper(IGImage igImage) {
		_igImage = igImage;
	}

	public Class<?> getModelClass() {
		return IGImage.class;
	}

	public String getModelClassName() {
		return IGImage.class.getName();
	}

	/**
	* Returns the primary key of this i g image.
	*
	* @return the primary key of this i g image
	*/
	public long getPrimaryKey() {
		return _igImage.getPrimaryKey();
	}

	/**
	* Sets the primary key of this i g image.
	*
	* @param primaryKey the primary key of this i g image
	*/
	public void setPrimaryKey(long primaryKey) {
		_igImage.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this i g image.
	*
	* @return the uuid of this i g image
	*/
	public java.lang.String getUuid() {
		return _igImage.getUuid();
	}

	/**
	* Sets the uuid of this i g image.
	*
	* @param uuid the uuid of this i g image
	*/
	public void setUuid(java.lang.String uuid) {
		_igImage.setUuid(uuid);
	}

	/**
	* Returns the image ID of this i g image.
	*
	* @return the image ID of this i g image
	*/
	public long getImageId() {
		return _igImage.getImageId();
	}

	/**
	* Sets the image ID of this i g image.
	*
	* @param imageId the image ID of this i g image
	*/
	public void setImageId(long imageId) {
		_igImage.setImageId(imageId);
	}

	/**
	* Returns the group ID of this i g image.
	*
	* @return the group ID of this i g image
	*/
	public long getGroupId() {
		return _igImage.getGroupId();
	}

	/**
	* Sets the group ID of this i g image.
	*
	* @param groupId the group ID of this i g image
	*/
	public void setGroupId(long groupId) {
		_igImage.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this i g image.
	*
	* @return the company ID of this i g image
	*/
	public long getCompanyId() {
		return _igImage.getCompanyId();
	}

	/**
	* Sets the company ID of this i g image.
	*
	* @param companyId the company ID of this i g image
	*/
	public void setCompanyId(long companyId) {
		_igImage.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this i g image.
	*
	* @return the user ID of this i g image
	*/
	public long getUserId() {
		return _igImage.getUserId();
	}

	/**
	* Sets the user ID of this i g image.
	*
	* @param userId the user ID of this i g image
	*/
	public void setUserId(long userId) {
		_igImage.setUserId(userId);
	}

	/**
	* Returns the user uuid of this i g image.
	*
	* @return the user uuid of this i g image
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImage.getUserUuid();
	}

	/**
	* Sets the user uuid of this i g image.
	*
	* @param userUuid the user uuid of this i g image
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_igImage.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this i g image.
	*
	* @return the user name of this i g image
	*/
	public java.lang.String getUserName() {
		return _igImage.getUserName();
	}

	/**
	* Sets the user name of this i g image.
	*
	* @param userName the user name of this i g image
	*/
	public void setUserName(java.lang.String userName) {
		_igImage.setUserName(userName);
	}

	/**
	* Returns the create date of this i g image.
	*
	* @return the create date of this i g image
	*/
	public java.util.Date getCreateDate() {
		return _igImage.getCreateDate();
	}

	/**
	* Sets the create date of this i g image.
	*
	* @param createDate the create date of this i g image
	*/
	public void setCreateDate(java.util.Date createDate) {
		_igImage.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this i g image.
	*
	* @return the modified date of this i g image
	*/
	public java.util.Date getModifiedDate() {
		return _igImage.getModifiedDate();
	}

	/**
	* Sets the modified date of this i g image.
	*
	* @param modifiedDate the modified date of this i g image
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_igImage.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the folder ID of this i g image.
	*
	* @return the folder ID of this i g image
	*/
	public long getFolderId() {
		return _igImage.getFolderId();
	}

	/**
	* Sets the folder ID of this i g image.
	*
	* @param folderId the folder ID of this i g image
	*/
	public void setFolderId(long folderId) {
		_igImage.setFolderId(folderId);
	}

	/**
	* Returns the name of this i g image.
	*
	* @return the name of this i g image
	*/
	public java.lang.String getName() {
		return _igImage.getName();
	}

	/**
	* Sets the name of this i g image.
	*
	* @param name the name of this i g image
	*/
	public void setName(java.lang.String name) {
		_igImage.setName(name);
	}

	/**
	* Returns the description of this i g image.
	*
	* @return the description of this i g image
	*/
	public java.lang.String getDescription() {
		return _igImage.getDescription();
	}

	/**
	* Sets the description of this i g image.
	*
	* @param description the description of this i g image
	*/
	public void setDescription(java.lang.String description) {
		_igImage.setDescription(description);
	}

	/**
	* Returns the small image ID of this i g image.
	*
	* @return the small image ID of this i g image
	*/
	public long getSmallImageId() {
		return _igImage.getSmallImageId();
	}

	/**
	* Sets the small image ID of this i g image.
	*
	* @param smallImageId the small image ID of this i g image
	*/
	public void setSmallImageId(long smallImageId) {
		_igImage.setSmallImageId(smallImageId);
	}

	/**
	* Returns the large image ID of this i g image.
	*
	* @return the large image ID of this i g image
	*/
	public long getLargeImageId() {
		return _igImage.getLargeImageId();
	}

	/**
	* Sets the large image ID of this i g image.
	*
	* @param largeImageId the large image ID of this i g image
	*/
	public void setLargeImageId(long largeImageId) {
		_igImage.setLargeImageId(largeImageId);
	}

	/**
	* Returns the custom1 image ID of this i g image.
	*
	* @return the custom1 image ID of this i g image
	*/
	public long getCustom1ImageId() {
		return _igImage.getCustom1ImageId();
	}

	/**
	* Sets the custom1 image ID of this i g image.
	*
	* @param custom1ImageId the custom1 image ID of this i g image
	*/
	public void setCustom1ImageId(long custom1ImageId) {
		_igImage.setCustom1ImageId(custom1ImageId);
	}

	/**
	* Returns the custom2 image ID of this i g image.
	*
	* @return the custom2 image ID of this i g image
	*/
	public long getCustom2ImageId() {
		return _igImage.getCustom2ImageId();
	}

	/**
	* Sets the custom2 image ID of this i g image.
	*
	* @param custom2ImageId the custom2 image ID of this i g image
	*/
	public void setCustom2ImageId(long custom2ImageId) {
		_igImage.setCustom2ImageId(custom2ImageId);
	}

	public boolean isNew() {
		return _igImage.isNew();
	}

	public void setNew(boolean n) {
		_igImage.setNew(n);
	}

	public boolean isCachedModel() {
		return _igImage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_igImage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _igImage.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_igImage.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _igImage.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_igImage.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _igImage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_igImage.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new IGImageWrapper((IGImage)_igImage.clone());
	}

	public int compareTo(com.liferay.portlet.imagegallery.model.IGImage igImage) {
		return _igImage.compareTo(igImage);
	}

	@Override
	public int hashCode() {
		return _igImage.hashCode();
	}

	public com.liferay.portlet.imagegallery.model.IGImage toEscapedModel() {
		return new IGImageWrapper(_igImage.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _igImage.toString();
	}

	public java.lang.String toXmlString() {
		return _igImage.toXmlString();
	}

	public void save()
		throws com.liferay.portal.kernel.exception.SystemException {
		_igImage.save();
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder() {
		return _igImage.getFolder();
	}

	public int getImageSize() {
		return _igImage.getImageSize();
	}

	public java.lang.String getImageType() {
		return _igImage.getImageType();
	}

	public java.lang.String getNameWithExtension() {
		return _igImage.getNameWithExtension();
	}

	public void setImageType(java.lang.String imageType) {
		_igImage.setImageType(imageType);
	}

	public IGImage getWrappedIGImage() {
		return _igImage;
	}

	public void resetOriginalValues() {
		_igImage.resetOriginalValues();
	}

	private IGImage _igImage;
}