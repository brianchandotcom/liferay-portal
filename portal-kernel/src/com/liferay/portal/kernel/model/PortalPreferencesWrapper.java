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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class PortalPreferencesWrapper
	extends BaseModelWrapper<PortalPreferences>
	implements ModelWrapper<PortalPreferences>, PortalPreferences {

	public PortalPreferencesWrapper(PortalPreferences portalPreferences) {
		super(portalPreferences);
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

	/**
	 * Returns the mvcc version of this portal preferences.
	 *
	 * @return the mvcc version of this portal preferences
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the owner ID of this portal preferences.
	 *
	 * @return the owner ID of this portal preferences
	 */
	@Override
	public long getOwnerId() {
		return model.getOwnerId();
	}

	/**
	 * Returns the owner type of this portal preferences.
	 *
	 * @return the owner type of this portal preferences
	 */
	@Override
	public int getOwnerType() {
		return model.getOwnerType();
	}

	/**
	 * Returns the portal preferences ID of this portal preferences.
	 *
	 * @return the portal preferences ID of this portal preferences
	 */
	@Override
	public long getPortalPreferencesId() {
		return model.getPortalPreferencesId();
	}

	/**
	 * Returns the preferences of this portal preferences.
	 *
	 * @return the preferences of this portal preferences
	 */
	@Override
	public String getPreferences() {
		return model.getPreferences();
	}

	/**
	 * Returns the primary key of this portal preferences.
	 *
	 * @return the primary key of this portal preferences
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a portal preferences model instance should use the <code>PortalPreferences</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the mvcc version of this portal preferences.
	 *
	 * @param mvccVersion the mvcc version of this portal preferences
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the owner ID of this portal preferences.
	 *
	 * @param ownerId the owner ID of this portal preferences
	 */
	@Override
	public void setOwnerId(long ownerId) {
		model.setOwnerId(ownerId);
	}

	/**
	 * Sets the owner type of this portal preferences.
	 *
	 * @param ownerType the owner type of this portal preferences
	 */
	@Override
	public void setOwnerType(int ownerType) {
		model.setOwnerType(ownerType);
	}

	/**
	 * Sets the portal preferences ID of this portal preferences.
	 *
	 * @param portalPreferencesId the portal preferences ID of this portal preferences
	 */
	@Override
	public void setPortalPreferencesId(long portalPreferencesId) {
		model.setPortalPreferencesId(portalPreferencesId);
	}

	/**
	 * Sets the preferences of this portal preferences.
	 *
	 * @param preferences the preferences of this portal preferences
	 */
	@Override
	public void setPreferences(String preferences) {
		model.setPreferences(preferences);
	}

	/**
	 * Sets the primary key of this portal preferences.
	 *
	 * @param primaryKey the primary key of this portal preferences
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected PortalPreferencesWrapper wrap(
		PortalPreferences portalPreferences) {

		return new PortalPreferencesWrapper(portalPreferences);
	}

}