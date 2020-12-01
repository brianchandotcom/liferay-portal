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
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link PortletPreferenceValue}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferenceValue
 * @generated
 */
public class PortletPreferenceValueWrapper
	extends BaseModelWrapper<PortletPreferenceValue>
	implements ModelWrapper<PortletPreferenceValue>, PortletPreferenceValue {

	public PortletPreferenceValueWrapper(
		PortletPreferenceValue portletPreferenceValue) {

		super(portletPreferenceValue);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"portletPreferenceValueId", getPortletPreferenceValueId());
		attributes.put("companyId", getCompanyId());
		attributes.put("portletPreferencesId", getPortletPreferencesId());
		attributes.put("index", getIndex());
		attributes.put("largeValue", getLargeValue());
		attributes.put("name", getName());
		attributes.put("readOnly", isReadOnly());
		attributes.put("smallValue", getSmallValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long portletPreferenceValueId = (Long)attributes.get(
			"portletPreferenceValueId");

		if (portletPreferenceValueId != null) {
			setPortletPreferenceValueId(portletPreferenceValueId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long portletPreferencesId = (Long)attributes.get(
			"portletPreferencesId");

		if (portletPreferencesId != null) {
			setPortletPreferencesId(portletPreferencesId);
		}

		Integer index = (Integer)attributes.get("index");

		if (index != null) {
			setIndex(index);
		}

		String largeValue = (String)attributes.get("largeValue");

		if (largeValue != null) {
			setLargeValue(largeValue);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean readOnly = (Boolean)attributes.get("readOnly");

		if (readOnly != null) {
			setReadOnly(readOnly);
		}

		String smallValue = (String)attributes.get("smallValue");

		if (smallValue != null) {
			setSmallValue(smallValue);
		}
	}

	/**
	 * Returns the company ID of this portlet preference value.
	 *
	 * @return the company ID of this portlet preference value
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this portlet preference value.
	 *
	 * @return the ct collection ID of this portlet preference value
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the index of this portlet preference value.
	 *
	 * @return the index of this portlet preference value
	 */
	@Override
	public int getIndex() {
		return model.getIndex();
	}

	/**
	 * Returns the large value of this portlet preference value.
	 *
	 * @return the large value of this portlet preference value
	 */
	@Override
	public String getLargeValue() {
		return model.getLargeValue();
	}

	/**
	 * Returns the mvcc version of this portlet preference value.
	 *
	 * @return the mvcc version of this portlet preference value
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this portlet preference value.
	 *
	 * @return the name of this portlet preference value
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the portlet preferences ID of this portlet preference value.
	 *
	 * @return the portlet preferences ID of this portlet preference value
	 */
	@Override
	public long getPortletPreferencesId() {
		return model.getPortletPreferencesId();
	}

	/**
	 * Returns the portlet preference value ID of this portlet preference value.
	 *
	 * @return the portlet preference value ID of this portlet preference value
	 */
	@Override
	public long getPortletPreferenceValueId() {
		return model.getPortletPreferenceValueId();
	}

	/**
	 * Returns the primary key of this portlet preference value.
	 *
	 * @return the primary key of this portlet preference value
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the read only of this portlet preference value.
	 *
	 * @return the read only of this portlet preference value
	 */
	@Override
	public boolean getReadOnly() {
		return model.getReadOnly();
	}

	/**
	 * Returns the small value of this portlet preference value.
	 *
	 * @return the small value of this portlet preference value
	 */
	@Override
	public String getSmallValue() {
		return model.getSmallValue();
	}

	@Override
	public String getValue() {
		return model.getValue();
	}

	/**
	 * Returns <code>true</code> if this portlet preference value is read only.
	 *
	 * @return <code>true</code> if this portlet preference value is read only; <code>false</code> otherwise
	 */
	@Override
	public boolean isReadOnly() {
		return model.isReadOnly();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this portlet preference value.
	 *
	 * @param companyId the company ID of this portlet preference value
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this portlet preference value.
	 *
	 * @param ctCollectionId the ct collection ID of this portlet preference value
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the index of this portlet preference value.
	 *
	 * @param index the index of this portlet preference value
	 */
	@Override
	public void setIndex(int index) {
		model.setIndex(index);
	}

	/**
	 * Sets the large value of this portlet preference value.
	 *
	 * @param largeValue the large value of this portlet preference value
	 */
	@Override
	public void setLargeValue(String largeValue) {
		model.setLargeValue(largeValue);
	}

	/**
	 * Sets the mvcc version of this portlet preference value.
	 *
	 * @param mvccVersion the mvcc version of this portlet preference value
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this portlet preference value.
	 *
	 * @param name the name of this portlet preference value
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the portlet preferences ID of this portlet preference value.
	 *
	 * @param portletPreferencesId the portlet preferences ID of this portlet preference value
	 */
	@Override
	public void setPortletPreferencesId(long portletPreferencesId) {
		model.setPortletPreferencesId(portletPreferencesId);
	}

	/**
	 * Sets the portlet preference value ID of this portlet preference value.
	 *
	 * @param portletPreferenceValueId the portlet preference value ID of this portlet preference value
	 */
	@Override
	public void setPortletPreferenceValueId(long portletPreferenceValueId) {
		model.setPortletPreferenceValueId(portletPreferenceValueId);
	}

	/**
	 * Sets the primary key of this portlet preference value.
	 *
	 * @param primaryKey the primary key of this portlet preference value
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this portlet preference value is read only.
	 *
	 * @param readOnly the read only of this portlet preference value
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		model.setReadOnly(readOnly);
	}

	/**
	 * Sets the small value of this portlet preference value.
	 *
	 * @param smallValue the small value of this portlet preference value
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
	public Map<String, Function<PortletPreferenceValue, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<PortletPreferenceValue, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected PortletPreferenceValueWrapper wrap(
		PortletPreferenceValue portletPreferenceValue) {

		return new PortletPreferenceValueWrapper(portletPreferenceValue);
	}

}