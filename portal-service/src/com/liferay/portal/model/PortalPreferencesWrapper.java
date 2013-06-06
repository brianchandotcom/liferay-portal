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

package com.liferay.portal.model;

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

		attributes.put("portalPreferencesId", getPortalPreferencesId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("preferences", getPreferences());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

	/**
	* Returns the primary key of this portal preferences.
	*
	* @return the primary key of this portal preferences
	*/
	@Override
	public long getPrimaryKey() {
		return _portalPreferences.getPrimaryKey();
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
	* Sets the portal preferences ID of this portal preferences.
	*
	* @param portalPreferencesId the portal preferences ID of this portal preferences
	*/
	@Override
	public void setPortalPreferencesId(long portalPreferencesId) {
		_portalPreferences.setPortalPreferencesId(portalPreferencesId);
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
	* Sets the owner ID of this portal preferences.
	*
	* @param ownerId the owner ID of this portal preferences
	*/
	@Override
	public void setOwnerId(long ownerId) {
		_portalPreferences.setOwnerId(ownerId);
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
	* Sets the owner type of this portal preferences.
	*
	* @param ownerType the owner type of this portal preferences
	*/
	@Override
	public void setOwnerType(int ownerType) {
		_portalPreferences.setOwnerType(ownerType);
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
	* Sets the preferences of this portal preferences.
	*
	* @param preferences the preferences of this portal preferences
	*/
	@Override
	public void setPreferences(java.lang.String preferences) {
		_portalPreferences.setPreferences(preferences);
	}

	@Override
	public boolean isNew() {
		return _portalPreferences.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_portalPreferences.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _portalPreferences.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portalPreferences.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _portalPreferences.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _portalPreferences.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portalPreferences.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portalPreferences.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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
	public int hashCode() {
		return _portalPreferences.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.PortalPreferences> toCacheModel() {
		return _portalPreferences.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.PortalPreferences toEscapedModel() {
		return new PortalPreferencesWrapper(_portalPreferences.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.PortalPreferences toUnescapedModel() {
		return new PortalPreferencesWrapper(_portalPreferences.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portalPreferences.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _portalPreferences.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portalPreferences.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public PortalPreferences getWrappedPortalPreferences() {
		return _portalPreferences;
	}

	@Override
	public PortalPreferences getWrappedModel() {
		return _portalPreferences;
	}

	@Override
	public void resetOriginalValues() {
		_portalPreferences.resetOriginalValues();
	}

	private PortalPreferences _portalPreferences;
}