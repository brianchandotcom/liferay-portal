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
 * This class is a wrapper for {@link PortletPreferences}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferences
 * @generated
 */
public class PortletPreferencesWrapper implements PortletPreferences,
	ModelWrapper<PortletPreferences> {
	public PortletPreferencesWrapper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	@Override
	public Class<?> getModelClass() {
		return PortletPreferences.class;
	}

	@Override
	public String getModelClassName() {
		return PortletPreferences.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("portletPreferencesId", getPortletPreferencesId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());
		attributes.put("preferences", getPreferences());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long portletPreferencesId = (Long)attributes.get("portletPreferencesId");

		if (portletPreferencesId != null) {
			setPortletPreferencesId(portletPreferencesId);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		Integer ownerType = (Integer)attributes.get("ownerType");

		if (ownerType != null) {
			setOwnerType(ownerType);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		String preferences = (String)attributes.get("preferences");

		if (preferences != null) {
			setPreferences(preferences);
		}
	}

	/**
	* Returns the primary key of this portlet preferences.
	*
	* @return the primary key of this portlet preferences
	*/
	@Override
	public long getPrimaryKey() {
		return _portletPreferences.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portlet preferences.
	*
	* @param primaryKey the primary key of this portlet preferences
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_portletPreferences.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the portlet preferences ID of this portlet preferences.
	*
	* @return the portlet preferences ID of this portlet preferences
	*/
	@Override
	public long getPortletPreferencesId() {
		return _portletPreferences.getPortletPreferencesId();
	}

	/**
	* Sets the portlet preferences ID of this portlet preferences.
	*
	* @param portletPreferencesId the portlet preferences ID of this portlet preferences
	*/
	@Override
	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferences.setPortletPreferencesId(portletPreferencesId);
	}

	/**
	* Returns the owner ID of this portlet preferences.
	*
	* @return the owner ID of this portlet preferences
	*/
	@Override
	public long getOwnerId() {
		return _portletPreferences.getOwnerId();
	}

	/**
	* Sets the owner ID of this portlet preferences.
	*
	* @param ownerId the owner ID of this portlet preferences
	*/
	@Override
	public void setOwnerId(long ownerId) {
		_portletPreferences.setOwnerId(ownerId);
	}

	/**
	* Returns the owner type of this portlet preferences.
	*
	* @return the owner type of this portlet preferences
	*/
	@Override
	public int getOwnerType() {
		return _portletPreferences.getOwnerType();
	}

	/**
	* Sets the owner type of this portlet preferences.
	*
	* @param ownerType the owner type of this portlet preferences
	*/
	@Override
	public void setOwnerType(int ownerType) {
		_portletPreferences.setOwnerType(ownerType);
	}

	/**
	* Returns the plid of this portlet preferences.
	*
	* @return the plid of this portlet preferences
	*/
	@Override
	public long getPlid() {
		return _portletPreferences.getPlid();
	}

	/**
	* Sets the plid of this portlet preferences.
	*
	* @param plid the plid of this portlet preferences
	*/
	@Override
	public void setPlid(long plid) {
		_portletPreferences.setPlid(plid);
	}

	/**
	* Returns the portlet ID of this portlet preferences.
	*
	* @return the portlet ID of this portlet preferences
	*/
	@Override
	public java.lang.String getPortletId() {
		return _portletPreferences.getPortletId();
	}

	/**
	* Sets the portlet ID of this portlet preferences.
	*
	* @param portletId the portlet ID of this portlet preferences
	*/
	@Override
	public void setPortletId(java.lang.String portletId) {
		_portletPreferences.setPortletId(portletId);
	}

	/**
	* Returns the preferences of this portlet preferences.
	*
	* @return the preferences of this portlet preferences
	*/
	@Override
	public java.lang.String getPreferences() {
		return _portletPreferences.getPreferences();
	}

	/**
	* Sets the preferences of this portlet preferences.
	*
	* @param preferences the preferences of this portlet preferences
	*/
	@Override
	public void setPreferences(java.lang.String preferences) {
		_portletPreferences.setPreferences(preferences);
	}

	@Override
	public boolean isNew() {
		return _portletPreferences.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_portletPreferences.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _portletPreferences.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portletPreferences.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _portletPreferences.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _portletPreferences.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portletPreferences.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portletPreferences.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_portletPreferences.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_portletPreferences.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portletPreferences.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PortletPreferencesWrapper((PortletPreferences)_portletPreferences.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferences.compareTo(portletPreferences);
	}

	@Override
	public int hashCode() {
		return _portletPreferences.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.PortletPreferences> toCacheModel() {
		return _portletPreferences.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.PortletPreferences toEscapedModel() {
		return new PortletPreferencesWrapper(_portletPreferences.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.PortletPreferences toUnescapedModel() {
		return new PortletPreferencesWrapper(_portletPreferences.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portletPreferences.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _portletPreferences.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portletPreferences.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public PortletPreferences getWrappedPortletPreferences() {
		return _portletPreferences;
	}

	@Override
	public PortletPreferences getWrappedModel() {
		return _portletPreferences;
	}

	@Override
	public void resetOriginalValues() {
		_portletPreferences.resetOriginalValues();
	}

	private PortletPreferences _portletPreferences;
}