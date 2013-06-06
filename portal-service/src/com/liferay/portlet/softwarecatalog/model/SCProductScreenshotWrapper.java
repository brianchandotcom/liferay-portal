/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SCProductScreenshot}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshot
 * @generated
 */
public class SCProductScreenshotWrapper implements SCProductScreenshot,
	ModelWrapper<SCProductScreenshot> {
	public SCProductScreenshotWrapper(SCProductScreenshot scProductScreenshot) {
		_scProductScreenshot = scProductScreenshot;
	}

	@Override
	public Class<?> getModelClass() {
		return SCProductScreenshot.class;
	}

	@Override
	public String getModelClassName() {
		return SCProductScreenshot.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("productScreenshotId", getProductScreenshotId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("productEntryId", getProductEntryId());
		attributes.put("thumbnailId", getThumbnailId());
		attributes.put("fullImageId", getFullImageId());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long productScreenshotId = (Long)attributes.get("productScreenshotId");

		if (productScreenshotId != null) {
			setProductScreenshotId(productScreenshotId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
		}

		Long thumbnailId = (Long)attributes.get("thumbnailId");

		if (thumbnailId != null) {
			setThumbnailId(thumbnailId);
		}

		Long fullImageId = (Long)attributes.get("fullImageId");

		if (fullImageId != null) {
			setFullImageId(fullImageId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	/**
	* Returns the primary key of this s c product screenshot.
	*
	* @return the primary key of this s c product screenshot
	*/
	@Override
	public long getPrimaryKey() {
		return _scProductScreenshot.getPrimaryKey();
	}

	/**
	* Sets the primary key of this s c product screenshot.
	*
	* @param primaryKey the primary key of this s c product screenshot
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_scProductScreenshot.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the product screenshot ID of this s c product screenshot.
	*
	* @return the product screenshot ID of this s c product screenshot
	*/
	@Override
	public long getProductScreenshotId() {
		return _scProductScreenshot.getProductScreenshotId();
	}

	/**
	* Sets the product screenshot ID of this s c product screenshot.
	*
	* @param productScreenshotId the product screenshot ID of this s c product screenshot
	*/
	@Override
	public void setProductScreenshotId(long productScreenshotId) {
		_scProductScreenshot.setProductScreenshotId(productScreenshotId);
	}

	/**
	* Returns the company ID of this s c product screenshot.
	*
	* @return the company ID of this s c product screenshot
	*/
	@Override
	public long getCompanyId() {
		return _scProductScreenshot.getCompanyId();
	}

	/**
	* Sets the company ID of this s c product screenshot.
	*
	* @param companyId the company ID of this s c product screenshot
	*/
	@Override
	public void setCompanyId(long companyId) {
		_scProductScreenshot.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this s c product screenshot.
	*
	* @return the group ID of this s c product screenshot
	*/
	@Override
	public long getGroupId() {
		return _scProductScreenshot.getGroupId();
	}

	/**
	* Sets the group ID of this s c product screenshot.
	*
	* @param groupId the group ID of this s c product screenshot
	*/
	@Override
	public void setGroupId(long groupId) {
		_scProductScreenshot.setGroupId(groupId);
	}

	/**
	* Returns the product entry ID of this s c product screenshot.
	*
	* @return the product entry ID of this s c product screenshot
	*/
	@Override
	public long getProductEntryId() {
		return _scProductScreenshot.getProductEntryId();
	}

	/**
	* Sets the product entry ID of this s c product screenshot.
	*
	* @param productEntryId the product entry ID of this s c product screenshot
	*/
	@Override
	public void setProductEntryId(long productEntryId) {
		_scProductScreenshot.setProductEntryId(productEntryId);
	}

	/**
	* Returns the thumbnail ID of this s c product screenshot.
	*
	* @return the thumbnail ID of this s c product screenshot
	*/
	@Override
	public long getThumbnailId() {
		return _scProductScreenshot.getThumbnailId();
	}

	/**
	* Sets the thumbnail ID of this s c product screenshot.
	*
	* @param thumbnailId the thumbnail ID of this s c product screenshot
	*/
	@Override
	public void setThumbnailId(long thumbnailId) {
		_scProductScreenshot.setThumbnailId(thumbnailId);
	}

	/**
	* Returns the full image ID of this s c product screenshot.
	*
	* @return the full image ID of this s c product screenshot
	*/
	@Override
	public long getFullImageId() {
		return _scProductScreenshot.getFullImageId();
	}

	/**
	* Sets the full image ID of this s c product screenshot.
	*
	* @param fullImageId the full image ID of this s c product screenshot
	*/
	@Override
	public void setFullImageId(long fullImageId) {
		_scProductScreenshot.setFullImageId(fullImageId);
	}

	/**
	* Returns the priority of this s c product screenshot.
	*
	* @return the priority of this s c product screenshot
	*/
	@Override
	public int getPriority() {
		return _scProductScreenshot.getPriority();
	}

	/**
	* Sets the priority of this s c product screenshot.
	*
	* @param priority the priority of this s c product screenshot
	*/
	@Override
	public void setPriority(int priority) {
		_scProductScreenshot.setPriority(priority);
	}

	@Override
	public boolean isNew() {
		return _scProductScreenshot.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_scProductScreenshot.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _scProductScreenshot.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_scProductScreenshot.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _scProductScreenshot.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductScreenshot.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_scProductScreenshot.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductScreenshot.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_scProductScreenshot.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_scProductScreenshot.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductScreenshot.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SCProductScreenshotWrapper((SCProductScreenshot)_scProductScreenshot.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		return _scProductScreenshot.compareTo(scProductScreenshot);
	}

	@Override
	public int hashCode() {
		return _scProductScreenshot.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> toCacheModel() {
		return _scProductScreenshot.toCacheModel();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot toEscapedModel() {
		return new SCProductScreenshotWrapper(_scProductScreenshot.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot toUnescapedModel() {
		return new SCProductScreenshotWrapper(_scProductScreenshot.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _scProductScreenshot.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _scProductScreenshot.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductScreenshot.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public SCProductScreenshot getWrappedSCProductScreenshot() {
		return _scProductScreenshot;
	}

	@Override
	public SCProductScreenshot getWrappedModel() {
		return _scProductScreenshot;
	}

	@Override
	public void resetOriginalValues() {
		_scProductScreenshot.resetOriginalValues();
	}

	private SCProductScreenshot _scProductScreenshot;
}