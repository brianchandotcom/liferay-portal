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
 * This class is a wrapper for {@link FriendlyURLEntryMapping}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMapping
 * @generated
 */
@ProviderType
public class FriendlyURLEntryMappingWrapper implements FriendlyURLEntryMapping,
	ModelWrapper<FriendlyURLEntryMapping> {
	public FriendlyURLEntryMappingWrapper(
		FriendlyURLEntryMapping friendlyURLEntryMapping) {
		_friendlyURLEntryMapping = friendlyURLEntryMapping;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLEntryMapping.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLEntryMapping.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("friendlyURLEntryMappingId",
			getFriendlyURLEntryMappingId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("friendlyURLEntryId", getFriendlyURLEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long friendlyURLEntryMappingId = (Long)attributes.get(
				"friendlyURLEntryMappingId");

		if (friendlyURLEntryMappingId != null) {
			setFriendlyURLEntryMappingId(friendlyURLEntryMappingId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long friendlyURLEntryId = (Long)attributes.get("friendlyURLEntryId");

		if (friendlyURLEntryId != null) {
			setFriendlyURLEntryId(friendlyURLEntryId);
		}
	}

	@Override
	public Object clone() {
		return new FriendlyURLEntryMappingWrapper((FriendlyURLEntryMapping)_friendlyURLEntryMapping.clone());
	}

	@Override
	public int compareTo(FriendlyURLEntryMapping friendlyURLEntryMapping) {
		return _friendlyURLEntryMapping.compareTo(friendlyURLEntryMapping);
	}

	/**
	* Returns the fully qualified class name of this friendly url entry mapping.
	*
	* @return the fully qualified class name of this friendly url entry mapping
	*/
	@Override
	public String getClassName() {
		return _friendlyURLEntryMapping.getClassName();
	}

	/**
	* Returns the class name ID of this friendly url entry mapping.
	*
	* @return the class name ID of this friendly url entry mapping
	*/
	@Override
	public long getClassNameId() {
		return _friendlyURLEntryMapping.getClassNameId();
	}

	/**
	* Returns the class pk of this friendly url entry mapping.
	*
	* @return the class pk of this friendly url entry mapping
	*/
	@Override
	public long getClassPK() {
		return _friendlyURLEntryMapping.getClassPK();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURLEntryMapping.getExpandoBridge();
	}

	/**
	* Returns the friendly url entry ID of this friendly url entry mapping.
	*
	* @return the friendly url entry ID of this friendly url entry mapping
	*/
	@Override
	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryMapping.getFriendlyURLEntryId();
	}

	/**
	* Returns the friendly url entry mapping ID of this friendly url entry mapping.
	*
	* @return the friendly url entry mapping ID of this friendly url entry mapping
	*/
	@Override
	public long getFriendlyURLEntryMappingId() {
		return _friendlyURLEntryMapping.getFriendlyURLEntryMappingId();
	}

	/**
	* Returns the mvcc version of this friendly url entry mapping.
	*
	* @return the mvcc version of this friendly url entry mapping
	*/
	@Override
	public long getMvccVersion() {
		return _friendlyURLEntryMapping.getMvccVersion();
	}

	/**
	* Returns the primary key of this friendly url entry mapping.
	*
	* @return the primary key of this friendly url entry mapping
	*/
	@Override
	public long getPrimaryKey() {
		return _friendlyURLEntryMapping.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLEntryMapping.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _friendlyURLEntryMapping.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURLEntryMapping.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURLEntryMapping.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _friendlyURLEntryMapping.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURLEntryMapping.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_friendlyURLEntryMapping.setClassName(className);
	}

	/**
	* Sets the class name ID of this friendly url entry mapping.
	*
	* @param classNameId the class name ID of this friendly url entry mapping
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_friendlyURLEntryMapping.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this friendly url entry mapping.
	*
	* @param classPK the class pk of this friendly url entry mapping
	*/
	@Override
	public void setClassPK(long classPK) {
		_friendlyURLEntryMapping.setClassPK(classPK);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURLEntryMapping.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURLEntryMapping.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURLEntryMapping.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url entry ID of this friendly url entry mapping.
	*
	* @param friendlyURLEntryId the friendly url entry ID of this friendly url entry mapping
	*/
	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntryMapping.setFriendlyURLEntryId(friendlyURLEntryId);
	}

	/**
	* Sets the friendly url entry mapping ID of this friendly url entry mapping.
	*
	* @param friendlyURLEntryMappingId the friendly url entry mapping ID of this friendly url entry mapping
	*/
	@Override
	public void setFriendlyURLEntryMappingId(long friendlyURLEntryMappingId) {
		_friendlyURLEntryMapping.setFriendlyURLEntryMappingId(friendlyURLEntryMappingId);
	}

	/**
	* Sets the mvcc version of this friendly url entry mapping.
	*
	* @param mvccVersion the mvcc version of this friendly url entry mapping
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_friendlyURLEntryMapping.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURLEntryMapping.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url entry mapping.
	*
	* @param primaryKey the primary key of this friendly url entry mapping
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_friendlyURLEntryMapping.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURLEntryMapping.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURLEntryMapping> toCacheModel() {
		return _friendlyURLEntryMapping.toCacheModel();
	}

	@Override
	public FriendlyURLEntryMapping toEscapedModel() {
		return new FriendlyURLEntryMappingWrapper(_friendlyURLEntryMapping.toEscapedModel());
	}

	@Override
	public String toString() {
		return _friendlyURLEntryMapping.toString();
	}

	@Override
	public FriendlyURLEntryMapping toUnescapedModel() {
		return new FriendlyURLEntryMappingWrapper(_friendlyURLEntryMapping.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _friendlyURLEntryMapping.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryMappingWrapper)) {
			return false;
		}

		FriendlyURLEntryMappingWrapper friendlyURLEntryMappingWrapper = (FriendlyURLEntryMappingWrapper)obj;

		if (Objects.equals(_friendlyURLEntryMapping,
					friendlyURLEntryMappingWrapper._friendlyURLEntryMapping)) {
			return true;
		}

		return false;
	}

	@Override
	public FriendlyURLEntryMapping getWrappedModel() {
		return _friendlyURLEntryMapping;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURLEntryMapping.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURLEntryMapping.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURLEntryMapping.resetOriginalValues();
	}

	private final FriendlyURLEntryMapping _friendlyURLEntryMapping;
}