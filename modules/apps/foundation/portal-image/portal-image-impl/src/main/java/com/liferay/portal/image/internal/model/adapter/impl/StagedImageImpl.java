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

package com.liferay.portal.image.internal.model.adapter.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.image.model.adapter.StagedImage;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Akos Thurzo
 */
public class StagedImageImpl implements StagedImage {

	public StagedImageImpl() {
	}

	public StagedImageImpl(Image image) {
		Objects.requireNonNull(
			image, "Unable to create a new staged image for a null image");

		_image = image;
	}

	@Override
	public Object clone() {
		return new StagedImageImpl((Image)_image.clone());
	}

	@Override
	public int compareTo(Image image) {
		return _image.compareTo(image);
	}

	@Override
	public long getCompanyId() {
		return _image.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _image.getModifiedDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _image.getExpandoBridge();
	}

	@Override
	public int getHeight() {
		return _image.getHeight();
	}

	@Override
	public long getImageId() {
		return _image.getImageId();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _image.getModelAttributes();
	}

	@Override
	public Class<?> getModelClass() {
		return _image.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _image.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _image.getModifiedDate();
	}

	@Override
	public long getMvccVersion() {
		return _image.getMvccVersion();
	}

	@Override
	public long getPrimaryKey() {
		return _image.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _image.getPrimaryKeyObj();
	}

	@Override
	public int getSize() {
		return _image.getSize();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedImage.class);
	}

	@Override
	public byte[] getTextObj() {
		return _image.getTextObj();
	}

	@Override
	public String getType() {
		return _image.getType();
	}

	@Override
	public String getUuid() {
		return String.valueOf(_image.getPrimaryKey());
	}

	@Override
	public int getWidth() {
		return _image.getWidth();
	}

	@Override
	public boolean isCachedModel() {
		return _image.isCachedModel();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _image.isEntityCacheEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _image.isEscapedModel();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _image.isFinderCacheEnabled();
	}

	@Override
	public boolean isNew() {
		return _image.isNew();
	}

	@Override
	public void persist() {
		_image.persist();
	}

	@Override
	public void resetOriginalValues() {
		_image.resetOriginalValues();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_image.setCachedModel(cachedModel);
	}

	@Override
	public void setCompanyId(long companyId) {
		_image.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_image.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_image.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_image.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setHeight(int height) {
		_image.setHeight(height);
	}

	public void setImage(Image image) {
		_image = image;
	}

	@Override
	public void setImageId(long imageId) {
		_image.setImageId(imageId);
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		_image.setModelAttributes(attributes);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_image.setModifiedDate(modifiedDate);
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_image.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_image.setNew(n);
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_image.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_image.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setSize(int size) {
		_image.setSize(size);
	}

	@Override
	public void setTextObj(byte[] textObj) {
		_image.setTextObj(textObj);
	}

	@Override
	public void setType(String type) {
		_image.setType(type);
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWidth(int width) {
		_image.setWidth(width);
	}

	@Override
	public CacheModel<Image> toCacheModel() {
		return _image.toCacheModel();
	}

	@Override
	public Image toEscapedModel() {
		return _image.toEscapedModel();
	}

	@Override
	public Image toUnescapedModel() {
		return _image.toUnescapedModel();
	}

	@Override
	public String toXmlString() {
		return _image.toXmlString();
	}

	private Image _image;

}