/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Image}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Image
 * @generated
 */
public class ImageWrapper implements Image, ModelWrapper<Image> {
	public ImageWrapper(Image image) {
		_image = image;
	}

	public Class<?> getModelClass() {
		return Image.class;
	}

	public String getModelClassName() {
		return Image.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("imageId", getImageId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("type", getType());
		attributes.put("height", getHeight());
		attributes.put("width", getWidth());
		attributes.put("size", getSize());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long imageId = (Long)attributes.get("imageId");

		if (imageId != null) {
			setImageId(imageId);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer height = (Integer)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Integer width = (Integer)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Integer size = (Integer)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	/**
	* Returns the primary key of this image.
	*
	* @return the primary key of this image
	*/
	public long getPrimaryKey() {
		return _image.getPrimaryKey();
	}

	/**
	* Sets the primary key of this image.
	*
	* @param primaryKey the primary key of this image
	*/
	public void setPrimaryKey(long primaryKey) {
		_image.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the image ID of this image.
	*
	* @return the image ID of this image
	*/
	public long getImageId() {
		return _image.getImageId();
	}

	/**
	* Sets the image ID of this image.
	*
	* @param imageId the image ID of this image
	*/
	public void setImageId(long imageId) {
		_image.setImageId(imageId);
	}

	/**
	* Returns the modified date of this image.
	*
	* @return the modified date of this image
	*/
	public java.util.Date getModifiedDate() {
		return _image.getModifiedDate();
	}

	/**
	* Sets the modified date of this image.
	*
	* @param modifiedDate the modified date of this image
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_image.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the type of this image.
	*
	* @return the type of this image
	*/
	public java.lang.String getType() {
		return _image.getType();
	}

	/**
	* Sets the type of this image.
	*
	* @param type the type of this image
	*/
	public void setType(java.lang.String type) {
		_image.setType(type);
	}

	/**
	* Returns the height of this image.
	*
	* @return the height of this image
	*/
	public int getHeight() {
		return _image.getHeight();
	}

	/**
	* Sets the height of this image.
	*
	* @param height the height of this image
	*/
	public void setHeight(int height) {
		_image.setHeight(height);
	}

	/**
	* Returns the width of this image.
	*
	* @return the width of this image
	*/
	public int getWidth() {
		return _image.getWidth();
	}

	/**
	* Sets the width of this image.
	*
	* @param width the width of this image
	*/
	public void setWidth(int width) {
		_image.setWidth(width);
	}

	/**
	* Returns the size of this image.
	*
	* @return the size of this image
	*/
	public int getSize() {
		return _image.getSize();
	}

	/**
	* Sets the size of this image.
	*
	* @param size the size of this image
	*/
	public void setSize(int size) {
		_image.setSize(size);
	}

	public boolean isNew() {
		return _image.isNew();
	}

	public void setNew(boolean n) {
		_image.setNew(n);
	}

	public boolean isCachedModel() {
		return _image.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_image.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _image.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _image.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_image.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _image.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_image.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_image.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_image.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ImageWrapper((Image)_image.clone());
	}

	public int compareTo(com.liferay.portal.model.Image image) {
		return _image.compareTo(image);
	}

	@Override
	public int hashCode() {
		return _image.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Image> toCacheModel() {
		return _image.toCacheModel();
	}

	public com.liferay.portal.model.Image toEscapedModel() {
		return new ImageWrapper(_image.toEscapedModel());
	}

	public com.liferay.portal.model.Image toUnescapedModel() {
		return new ImageWrapper(_image.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _image.toString();
	}

	public java.lang.String toXmlString() {
		return _image.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_image.persist();
	}

	public byte[] getTextObj() {
		return _image.getTextObj();
	}

	public void setTextObj(byte[] textObj) {
		_image.setTextObj(textObj);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Image getWrappedImage() {
		return _image;
	}

	public Image getWrappedModel() {
		return _image;
	}

	public void resetOriginalValues() {
		_image.resetOriginalValues();
	}

	private Image _image;
}