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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PortalPreferences}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferences
 * @generated
 */
@ProviderType
public class PortalPreferencesWrapper implements PortalPreferences,
	ModelWrapper<PortalPreferences> {
	public PortalPreferencesWrapper(PortalPreferences portalPreferences) {
		_portalPreferences = portalPreferences;
	}

	@Override
	public Class<?> getModelClass() {
		return PortalPreferences.class;
	}

	@Override
	public String getModelClassName() {
		return PortalPreferences.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("portalPreferencesId", getPortalPreferencesId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("preferences", getPreferences());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long portalPreferencesId = (Long)attributes.get("portalPreferencesId");

		if (portalPreferencesId != null) {
			setPortalPreferencesId(portalPreferencesId);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		Integer ownerType = (Integer)attributes.get("ownerType");

		if (ownerType != null) {
			setOwnerType(ownerType);
		}

		String preferences = (String)attributes.get("preferences");

		if (preferences != null) {
			setPreferences(preferences);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new PortalPreferencesWrapper((PortalPreferences)_portalPreferences.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.PortalPreferences portalPreferences) {
		return _portalPreferences.compareTo(portalPreferences);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portalPreferences.getExpandoBridge();
	}

	/**
	* Returns the mvcc version of this portal preferences.
	*
	* @return the mvcc version of this portal preferences
	*/
	@Override
	public long getMvccVersion() {
		return _portalPreferences.getMvccVersion();
	}

	/**
	* Returns the owner ID of this portal preferences.
	*
	* @return the owner ID of this portal preferences
	*/
	@Override
	public long getOwnerId() {
		return _portalPreferences.getOwnerId();
	}

	/**
	* Returns the owner type of this portal preferences.
	*
	* @return the owner type of this portal preferences
	*/
	@Override
	public int getOwnerType() {
		return _portalPreferences.getOwnerType();
	}

	/**
	* Returns the portal preferences ID of this portal preferences.
	*
	* @return the portal preferences ID of this portal preferences
	*/
	@Override
	public long getPortalPreferencesId() {
		return _portalPreferences.getPortalPreferencesId();
	}

	/**
	* Returns the preferences of this portal preferences.
	*
	* @return the preferences of this portal preferences
	*/
	@Override
	public java.lang.String getPreferences() {
		return _portalPreferences.getPreferences();
	}

	/**
	* Returns the primary key of this portal preferences.
	*
	* @return the primary key of this portal preferences
	*/
	@Override
	public long getPrimaryKey() {
		return _portalPreferences.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _portalPreferences.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _portalPreferences.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _portalPreferences.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _portalPreferences.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _portalPreferences.isNew();
	}

	@Override
	public void persist() {
		_portalPreferences.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portalPreferences.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_portalPreferences.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_portalPreferences.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portalPreferences.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this portal preferences.
	*
	* @param mvccVersion the mvcc version of this portal preferences
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_portalPreferences.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_portalPreferences.setNew(n);
	}

	/**
	* Sets the owner ID of this portal preferences.
	*
	* @param ownerId the owner ID of this portal preferences
	*/
	@Override
	public void setOwnerId(long ownerId) {
		_portalPreferences.setOwnerId(ownerId);
	}

	/**
	* Sets the owner type of this portal preferences.
	*
	* @param ownerType the owner type of this portal preferences
	*/
	@Override
	public void setOwnerType(int ownerType) {
		_portalPreferences.setOwnerType(ownerType);
	}

	/**
	* Sets the portal preferences ID of this portal preferences.
	*
	* @param portalPreferencesId the portal preferences ID of this portal preferences
	*/
	@Override
	public void setPortalPreferencesId(long portalPreferencesId) {
		_portalPreferences.setPortalPreferencesId(portalPreferencesId);
	}

	/**
	* Sets the preferences of this portal preferences.
	*
	* @param preferences the preferences of this portal preferences
	*/
	@Override
	public void setPreferences(java.lang.String preferences) {
		_portalPreferences.setPreferences(preferences);
	}

	/**
	* Sets the primary key of this portal preferences.
	*
	* @param primaryKey the primary key of this portal preferences
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_portalPreferences.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portalPreferences.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public CacheModel<com.liferay.portal.model.PortalPreferences> toCacheModel() {
		return _portalPreferences.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.PortalPreferences toEscapedModel() {
		return new PortalPreferencesWrapper(_portalPreferences.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portalPreferences.toString();
	}

	@Override
	public com.liferay.portal.model.PortalPreferences toUnescapedModel() {
		return new PortalPreferencesWrapper(_portalPreferences.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _portalPreferences.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortalPreferencesWrapper)) {
			return false;
		}

		PortalPreferencesWrapper portalPreferencesWrapper = (PortalPreferencesWrapper)obj;

		if (Validator.equals(_portalPreferences,
					portalPreferencesWrapper._portalPreferences)) {
			return true;
		}

		return false;
	}

	@Override
	public PortalPreferences getWrappedModel() {
		return _portalPreferences;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _portalPreferences.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _portalPreferences.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_portalPreferences.resetOriginalValues();
	}

	private final PortalPreferences _portalPreferences;
}