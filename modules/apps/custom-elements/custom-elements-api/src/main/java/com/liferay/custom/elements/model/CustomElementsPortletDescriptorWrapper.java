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

package com.liferay.custom.elements.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CustomElementsPortletDescriptor}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsPortletDescriptor
 * @generated
 */
public class CustomElementsPortletDescriptorWrapper
	extends BaseModelWrapper<CustomElementsPortletDescriptor>
	implements CustomElementsPortletDescriptor,
			   ModelWrapper<CustomElementsPortletDescriptor> {

	public CustomElementsPortletDescriptorWrapper(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		super(customElementsPortletDescriptor);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"customElementsPortletDescriptorId",
			getCustomElementsPortletDescriptorId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("cssURLs", getCssURLs());
		attributes.put("htmlElementName", getHtmlElementName());
		attributes.put("instanceable", isInstanceable());
		attributes.put("name", getName());
		attributes.put("properties", getProperties());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long customElementsPortletDescriptorId = (Long)attributes.get(
			"customElementsPortletDescriptorId");

		if (customElementsPortletDescriptorId != null) {
			setCustomElementsPortletDescriptorId(
				customElementsPortletDescriptorId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String cssURLs = (String)attributes.get("cssURLs");

		if (cssURLs != null) {
			setCssURLs(cssURLs);
		}

		String htmlElementName = (String)attributes.get("htmlElementName");

		if (htmlElementName != null) {
			setHtmlElementName(htmlElementName);
		}

		Boolean instanceable = (Boolean)attributes.get("instanceable");

		if (instanceable != null) {
			setInstanceable(instanceable);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String properties = (String)attributes.get("properties");

		if (properties != null) {
			setProperties(properties);
		}
	}

	/**
	 * Returns the company ID of this custom elements portlet descriptor.
	 *
	 * @return the company ID of this custom elements portlet descriptor
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this custom elements portlet descriptor.
	 *
	 * @return the create date of this custom elements portlet descriptor
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the css ur ls of this custom elements portlet descriptor.
	 *
	 * @return the css ur ls of this custom elements portlet descriptor
	 */
	@Override
	public String getCssURLs() {
		return model.getCssURLs();
	}

	/**
	 * Returns the custom elements portlet descriptor ID of this custom elements portlet descriptor.
	 *
	 * @return the custom elements portlet descriptor ID of this custom elements portlet descriptor
	 */
	@Override
	public long getCustomElementsPortletDescriptorId() {
		return model.getCustomElementsPortletDescriptorId();
	}

	/**
	 * Returns the html element name of this custom elements portlet descriptor.
	 *
	 * @return the html element name of this custom elements portlet descriptor
	 */
	@Override
	public String getHtmlElementName() {
		return model.getHtmlElementName();
	}

	/**
	 * Returns the instanceable of this custom elements portlet descriptor.
	 *
	 * @return the instanceable of this custom elements portlet descriptor
	 */
	@Override
	public boolean getInstanceable() {
		return model.getInstanceable();
	}

	/**
	 * Returns the modified date of this custom elements portlet descriptor.
	 *
	 * @return the modified date of this custom elements portlet descriptor
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this custom elements portlet descriptor.
	 *
	 * @return the mvcc version of this custom elements portlet descriptor
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this custom elements portlet descriptor.
	 *
	 * @return the name of this custom elements portlet descriptor
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this custom elements portlet descriptor.
	 *
	 * @return the primary key of this custom elements portlet descriptor
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the properties of this custom elements portlet descriptor.
	 *
	 * @return the properties of this custom elements portlet descriptor
	 */
	@Override
	public String getProperties() {
		return model.getProperties();
	}

	/**
	 * Returns the user ID of this custom elements portlet descriptor.
	 *
	 * @return the user ID of this custom elements portlet descriptor
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this custom elements portlet descriptor.
	 *
	 * @return the user name of this custom elements portlet descriptor
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this custom elements portlet descriptor.
	 *
	 * @return the user uuid of this custom elements portlet descriptor
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this custom elements portlet descriptor.
	 *
	 * @return the uuid of this custom elements portlet descriptor
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this custom elements portlet descriptor is instanceable.
	 *
	 * @return <code>true</code> if this custom elements portlet descriptor is instanceable; <code>false</code> otherwise
	 */
	@Override
	public boolean isInstanceable() {
		return model.isInstanceable();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this custom elements portlet descriptor.
	 *
	 * @param companyId the company ID of this custom elements portlet descriptor
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this custom elements portlet descriptor.
	 *
	 * @param createDate the create date of this custom elements portlet descriptor
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the css ur ls of this custom elements portlet descriptor.
	 *
	 * @param cssURLs the css ur ls of this custom elements portlet descriptor
	 */
	@Override
	public void setCssURLs(String cssURLs) {
		model.setCssURLs(cssURLs);
	}

	/**
	 * Sets the custom elements portlet descriptor ID of this custom elements portlet descriptor.
	 *
	 * @param customElementsPortletDescriptorId the custom elements portlet descriptor ID of this custom elements portlet descriptor
	 */
	@Override
	public void setCustomElementsPortletDescriptorId(
		long customElementsPortletDescriptorId) {

		model.setCustomElementsPortletDescriptorId(
			customElementsPortletDescriptorId);
	}

	/**
	 * Sets the html element name of this custom elements portlet descriptor.
	 *
	 * @param htmlElementName the html element name of this custom elements portlet descriptor
	 */
	@Override
	public void setHtmlElementName(String htmlElementName) {
		model.setHtmlElementName(htmlElementName);
	}

	/**
	 * Sets whether this custom elements portlet descriptor is instanceable.
	 *
	 * @param instanceable the instanceable of this custom elements portlet descriptor
	 */
	@Override
	public void setInstanceable(boolean instanceable) {
		model.setInstanceable(instanceable);
	}

	/**
	 * Sets the modified date of this custom elements portlet descriptor.
	 *
	 * @param modifiedDate the modified date of this custom elements portlet descriptor
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this custom elements portlet descriptor.
	 *
	 * @param mvccVersion the mvcc version of this custom elements portlet descriptor
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this custom elements portlet descriptor.
	 *
	 * @param name the name of this custom elements portlet descriptor
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this custom elements portlet descriptor.
	 *
	 * @param primaryKey the primary key of this custom elements portlet descriptor
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the properties of this custom elements portlet descriptor.
	 *
	 * @param properties the properties of this custom elements portlet descriptor
	 */
	@Override
	public void setProperties(String properties) {
		model.setProperties(properties);
	}

	/**
	 * Sets the user ID of this custom elements portlet descriptor.
	 *
	 * @param userId the user ID of this custom elements portlet descriptor
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this custom elements portlet descriptor.
	 *
	 * @param userName the user name of this custom elements portlet descriptor
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this custom elements portlet descriptor.
	 *
	 * @param userUuid the user uuid of this custom elements portlet descriptor
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this custom elements portlet descriptor.
	 *
	 * @param uuid the uuid of this custom elements portlet descriptor
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CustomElementsPortletDescriptorWrapper wrap(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		return new CustomElementsPortletDescriptorWrapper(
			customElementsPortletDescriptor);
	}

}