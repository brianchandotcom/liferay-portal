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
 * This class is a wrapper for {@link PortalPreferenceValue}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferenceValue
 * @generated
 */
public class PortalPreferenceValueWrapper
	extends BaseModelWrapper<PortalPreferenceValue>
	implements ModelWrapper<PortalPreferenceValue>, PortalPreferenceValue {

	public PortalPreferenceValueWrapper(
		PortalPreferenceValue portalPreferenceValue) {

		super(portalPreferenceValue);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("portalPreferenceValueId", getPortalPreferenceValueId());
		attributes.put("companyId", getCompanyId());
		attributes.put("portalPreferencesId", getPortalPreferencesId());
		attributes.put("index", getIndex());
		attributes.put("key", getKey());
		attributes.put("largeValue", getLargeValue());
		attributes.put("namespace", getNamespace());
		attributes.put("smallValue", getSmallValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long portalPreferenceValueId = (Long)attributes.get(
			"portalPreferenceValueId");

		if (portalPreferenceValueId != null) {
			setPortalPreferenceValueId(portalPreferenceValueId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long portalPreferencesId = (Long)attributes.get("portalPreferencesId");

		if (portalPreferencesId != null) {
			setPortalPreferencesId(portalPreferencesId);
		}

		Integer index = (Integer)attributes.get("index");

		if (index != null) {
			setIndex(index);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String largeValue = (String)attributes.get("largeValue");

		if (largeValue != null) {
			setLargeValue(largeValue);
		}

		String namespace = (String)attributes.get("namespace");

		if (namespace != null) {
			setNamespace(namespace);
		}

		String smallValue = (String)attributes.get("smallValue");

		if (smallValue != null) {
			setSmallValue(smallValue);
		}
	}

	/**
	 * Returns the company ID of this portal preference value.
	 *
	 * @return the company ID of this portal preference value
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the index of this portal preference value.
	 *
	 * @return the index of this portal preference value
	 */
	@Override
	public int getIndex() {
		return model.getIndex();
	}

	/**
	 * Returns the key of this portal preference value.
	 *
	 * @return the key of this portal preference value
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the large value of this portal preference value.
	 *
	 * @return the large value of this portal preference value
	 */
	@Override
	public String getLargeValue() {
		return model.getLargeValue();
	}

	/**
	 * Returns the mvcc version of this portal preference value.
	 *
	 * @return the mvcc version of this portal preference value
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the namespace of this portal preference value.
	 *
	 * @return the namespace of this portal preference value
	 */
	@Override
	public String getNamespace() {
		return model.getNamespace();
	}

	/**
	 * Returns the portal preferences ID of this portal preference value.
	 *
	 * @return the portal preferences ID of this portal preference value
	 */
	@Override
	public long getPortalPreferencesId() {
		return model.getPortalPreferencesId();
	}

	/**
	 * Returns the portal preference value ID of this portal preference value.
	 *
	 * @return the portal preference value ID of this portal preference value
	 */
	@Override
	public long getPortalPreferenceValueId() {
		return model.getPortalPreferenceValueId();
	}

	/**
	 * Returns the primary key of this portal preference value.
	 *
	 * @return the primary key of this portal preference value
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the small value of this portal preference value.
	 *
	 * @return the small value of this portal preference value
	 */
	@Override
	public String getSmallValue() {
		return model.getSmallValue();
	}

	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this portal preference value.
	 *
	 * @param companyId the company ID of this portal preference value
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the index of this portal preference value.
	 *
	 * @param index the index of this portal preference value
	 */
	@Override
	public void setIndex(int index) {
		model.setIndex(index);
	}

	/**
	 * Sets the key of this portal preference value.
	 *
	 * @param key the key of this portal preference value
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the large value of this portal preference value.
	 *
	 * @param largeValue the large value of this portal preference value
	 */
	@Override
	public void setLargeValue(String largeValue) {
		model.setLargeValue(largeValue);
	}

	/**
	 * Sets the mvcc version of this portal preference value.
	 *
	 * @param mvccVersion the mvcc version of this portal preference value
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the namespace of this portal preference value.
	 *
	 * @param namespace the namespace of this portal preference value
	 */
	@Override
	public void setNamespace(String namespace) {
		model.setNamespace(namespace);
	}

	/**
	 * Sets the portal preferences ID of this portal preference value.
	 *
	 * @param portalPreferencesId the portal preferences ID of this portal preference value
	 */
	@Override
	public void setPortalPreferencesId(long portalPreferencesId) {
		model.setPortalPreferencesId(portalPreferencesId);
	}

	/**
	 * Sets the portal preference value ID of this portal preference value.
	 *
	 * @param portalPreferenceValueId the portal preference value ID of this portal preference value
	 */
	@Override
	public void setPortalPreferenceValueId(long portalPreferenceValueId) {
		model.setPortalPreferenceValueId(portalPreferenceValueId);
	}

	/**
	 * Sets the primary key of this portal preference value.
	 *
	 * @param primaryKey the primary key of this portal preference value
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the small value of this portal preference value.
	 *
	 * @param smallValue the small value of this portal preference value
	 */
	@Override
	public void setSmallValue(String smallValue) {
		model.setSmallValue(smallValue);
	}

	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	protected PortalPreferenceValueWrapper wrap(
		PortalPreferenceValue portalPreferenceValue) {

		return new PortalPreferenceValueWrapper(portalPreferenceValue);
	}

}