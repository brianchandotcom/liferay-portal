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

package com.liferay.portlet.trash.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TrashVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersion
 * @generated
 */
public class TrashVersionWrapper implements TrashVersion,
	ModelWrapper<TrashVersion> {
	public TrashVersionWrapper(TrashVersion trashVersion) {
		_trashVersion = trashVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return TrashVersion.class;
	}

	@Override
	public String getModelClassName() {
		return TrashVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("versionId", getVersionId());
		attributes.put("entryId", getEntryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long versionId = (Long)attributes.get("versionId");

		if (versionId != null) {
			setVersionId(versionId);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	* Returns the primary key of this trash version.
	*
	* @return the primary key of this trash version
	*/
	@Override
	public long getPrimaryKey() {
		return _trashVersion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this trash version.
	*
	* @param primaryKey the primary key of this trash version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_trashVersion.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the version ID of this trash version.
	*
	* @return the version ID of this trash version
	*/
	@Override
	public long getVersionId() {
		return _trashVersion.getVersionId();
	}

	/**
	* Sets the version ID of this trash version.
	*
	* @param versionId the version ID of this trash version
	*/
	@Override
	public void setVersionId(long versionId) {
		_trashVersion.setVersionId(versionId);
	}

	/**
	* Returns the entry ID of this trash version.
	*
	* @return the entry ID of this trash version
	*/
	@Override
	public long getEntryId() {
		return _trashVersion.getEntryId();
	}

	/**
	* Sets the entry ID of this trash version.
	*
	* @param entryId the entry ID of this trash version
	*/
	@Override
	public void setEntryId(long entryId) {
		_trashVersion.setEntryId(entryId);
	}

	/**
	* Returns the fully qualified class name of this trash version.
	*
	* @return the fully qualified class name of this trash version
	*/
	@Override
	public java.lang.String getClassName() {
		return _trashVersion.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_trashVersion.setClassName(className);
	}

	/**
	* Returns the class name ID of this trash version.
	*
	* @return the class name ID of this trash version
	*/
	@Override
	public long getClassNameId() {
		return _trashVersion.getClassNameId();
	}

	/**
	* Sets the class name ID of this trash version.
	*
	* @param classNameId the class name ID of this trash version
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_trashVersion.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this trash version.
	*
	* @return the class p k of this trash version
	*/
	@Override
	public long getClassPK() {
		return _trashVersion.getClassPK();
	}

	/**
	* Sets the class p k of this trash version.
	*
	* @param classPK the class p k of this trash version
	*/
	@Override
	public void setClassPK(long classPK) {
		_trashVersion.setClassPK(classPK);
	}

	/**
	* Returns the status of this trash version.
	*
	* @return the status of this trash version
	*/
	@Override
	public int getStatus() {
		return _trashVersion.getStatus();
	}

	/**
	* Sets the status of this trash version.
	*
	* @param status the status of this trash version
	*/
	@Override
	public void setStatus(int status) {
		_trashVersion.setStatus(status);
	}

	@Override
	public boolean isNew() {
		return _trashVersion.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_trashVersion.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _trashVersion.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_trashVersion.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _trashVersion.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _trashVersion.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_trashVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _trashVersion.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_trashVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_trashVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_trashVersion.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new TrashVersionWrapper((TrashVersion)_trashVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.trash.model.TrashVersion trashVersion) {
		return _trashVersion.compareTo(trashVersion);
	}

	@Override
	public int hashCode() {
		return _trashVersion.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.trash.model.TrashVersion> toCacheModel() {
		return _trashVersion.toCacheModel();
	}

	@Override
	public com.liferay.portlet.trash.model.TrashVersion toEscapedModel() {
		return new TrashVersionWrapper(_trashVersion.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.trash.model.TrashVersion toUnescapedModel() {
		return new TrashVersionWrapper(_trashVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _trashVersion.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _trashVersion.toXmlString();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public TrashVersion getWrappedTrashVersion() {
		return _trashVersion;
	}

	@Override
	public TrashVersion getWrappedModel() {
		return _trashVersion;
	}

	@Override
	public void resetOriginalValues() {
		_trashVersion.resetOriginalValues();
	}

	private TrashVersion _trashVersion;
}