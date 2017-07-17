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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link FriendlyURLMapping}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLMapping
 * @generated
 */
@ProviderType
public class FriendlyURLMappingWrapper implements FriendlyURLMapping,
	ModelWrapper<FriendlyURLMapping> {
	public FriendlyURLMappingWrapper(FriendlyURLMapping friendlyURLMapping) {
		_friendlyURLMapping = friendlyURLMapping;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLMapping.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLMapping.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("friendlyURLId", getFriendlyURLId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long friendlyURLId = (Long)attributes.get("friendlyURLId");

		if (friendlyURLId != null) {
			setFriendlyURLId(friendlyURLId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new FriendlyURLMappingWrapper((FriendlyURLMapping)_friendlyURLMapping.clone());
	}

	@Override
	public int compareTo(FriendlyURLMapping friendlyURLMapping) {
		return _friendlyURLMapping.compareTo(friendlyURLMapping);
	}

	/**
	* Returns the fully qualified class name of this friendly url mapping.
	*
	* @return the fully qualified class name of this friendly url mapping
	*/
	@Override
	public java.lang.String getClassName() {
		return _friendlyURLMapping.getClassName();
	}

	/**
	* Returns the class name ID of this friendly url mapping.
	*
	* @return the class name ID of this friendly url mapping
	*/
	@Override
	public long getClassNameId() {
		return _friendlyURLMapping.getClassNameId();
	}

	/**
	* Returns the class pk of this friendly url mapping.
	*
	* @return the class pk of this friendly url mapping
	*/
	@Override
	public long getClassPK() {
		return _friendlyURLMapping.getClassPK();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURLMapping.getExpandoBridge();
	}

	/**
	* Returns the friendly url ID of this friendly url mapping.
	*
	* @return the friendly url ID of this friendly url mapping
	*/
	@Override
	public long getFriendlyURLId() {
		return _friendlyURLMapping.getFriendlyURLId();
	}

	/**
	* Returns the primary key of this friendly url mapping.
	*
	* @return the primary key of this friendly url mapping
	*/
	@Override
	public com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK getPrimaryKey() {
		return _friendlyURLMapping.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLMapping.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _friendlyURLMapping.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURLMapping.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURLMapping.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _friendlyURLMapping.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURLMapping.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_friendlyURLMapping.setClassName(className);
	}

	/**
	* Sets the class name ID of this friendly url mapping.
	*
	* @param classNameId the class name ID of this friendly url mapping
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_friendlyURLMapping.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this friendly url mapping.
	*
	* @param classPK the class pk of this friendly url mapping
	*/
	@Override
	public void setClassPK(long classPK) {
		_friendlyURLMapping.setClassPK(classPK);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURLMapping.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURLMapping.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURLMapping.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url ID of this friendly url mapping.
	*
	* @param friendlyURLId the friendly url ID of this friendly url mapping
	*/
	@Override
	public void setFriendlyURLId(long friendlyURLId) {
		_friendlyURLMapping.setFriendlyURLId(friendlyURLId);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURLMapping.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url mapping.
	*
	* @param primaryKey the primary key of this friendly url mapping
	*/
	@Override
	public void setPrimaryKey(
		com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK primaryKey) {
		_friendlyURLMapping.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURLMapping.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURLMapping> toCacheModel() {
		return _friendlyURLMapping.toCacheModel();
	}

	@Override
	public FriendlyURLMapping toEscapedModel() {
		return new FriendlyURLMappingWrapper(_friendlyURLMapping.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _friendlyURLMapping.toString();
	}

	@Override
	public FriendlyURLMapping toUnescapedModel() {
		return new FriendlyURLMappingWrapper(_friendlyURLMapping.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _friendlyURLMapping.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLMappingWrapper)) {
			return false;
		}

		FriendlyURLMappingWrapper friendlyURLMappingWrapper = (FriendlyURLMappingWrapper)obj;

		if (Objects.equals(_friendlyURLMapping,
					friendlyURLMappingWrapper._friendlyURLMapping)) {
			return true;
		}

		return false;
	}

	@Override
	public FriendlyURLMapping getWrappedModel() {
		return _friendlyURLMapping;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURLMapping.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURLMapping.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURLMapping.resetOriginalValues();
	}

	private final FriendlyURLMapping _friendlyURLMapping;
}