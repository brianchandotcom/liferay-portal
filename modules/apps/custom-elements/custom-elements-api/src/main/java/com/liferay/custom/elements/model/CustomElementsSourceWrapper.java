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
 * This class is a wrapper for {@link CustomElementsSource}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsSource
 * @generated
 */
public class CustomElementsSourceWrapper
	extends BaseModelWrapper<CustomElementsSource>
	implements CustomElementsSource, ModelWrapper<CustomElementsSource> {

	public CustomElementsSourceWrapper(
		CustomElementsSource customElementsSource) {

		super(customElementsSource);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("customElementsSourceId", getCustomElementsSourceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("htmlElementName", getHTMLElementName());
		attributes.put("name", getName());
		attributes.put("urls", getURLs());

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

		Long customElementsSourceId = (Long)attributes.get(
			"customElementsSourceId");

		if (customElementsSourceId != null) {
			setCustomElementsSourceId(customElementsSourceId);
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

		String htmlElementName = (String)attributes.get("htmlElementName");

		if (htmlElementName != null) {
			setHTMLElementName(htmlElementName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String urls = (String)attributes.get("urls");

		if (urls != null) {
			setURLs(urls);
		}
	}

	/**
	 * Returns the company ID of this custom elements source.
	 *
	 * @return the company ID of this custom elements source
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this custom elements source.
	 *
	 * @return the create date of this custom elements source
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the custom elements source ID of this custom elements source.
	 *
	 * @return the custom elements source ID of this custom elements source
	 */
	@Override
	public long getCustomElementsSourceId() {
		return model.getCustomElementsSourceId();
	}

	/**
	 * Returns the html element name of this custom elements source.
	 *
	 * @return the html element name of this custom elements source
	 */
	@Override
	public String getHTMLElementName() {
		return model.getHTMLElementName();
	}

	/**
	 * Returns the modified date of this custom elements source.
	 *
	 * @return the modified date of this custom elements source
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this custom elements source.
	 *
	 * @return the mvcc version of this custom elements source
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this custom elements source.
	 *
	 * @return the name of this custom elements source
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this custom elements source.
	 *
	 * @return the primary key of this custom elements source
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the urls of this custom elements source.
	 *
	 * @return the urls of this custom elements source
	 */
	@Override
	public String getURLs() {
		return model.getURLs();
	}

	/**
	 * Returns the user ID of this custom elements source.
	 *
	 * @return the user ID of this custom elements source
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this custom elements source.
	 *
	 * @return the user name of this custom elements source
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this custom elements source.
	 *
	 * @return the user uuid of this custom elements source
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this custom elements source.
	 *
	 * @return the uuid of this custom elements source
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this custom elements source.
	 *
	 * @param companyId the company ID of this custom elements source
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this custom elements source.
	 *
	 * @param createDate the create date of this custom elements source
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the custom elements source ID of this custom elements source.
	 *
	 * @param customElementsSourceId the custom elements source ID of this custom elements source
	 */
	@Override
	public void setCustomElementsSourceId(long customElementsSourceId) {
		model.setCustomElementsSourceId(customElementsSourceId);
	}

	/**
	 * Sets the html element name of this custom elements source.
	 *
	 * @param htmlElementName the html element name of this custom elements source
	 */
	@Override
	public void setHTMLElementName(String htmlElementName) {
		model.setHTMLElementName(htmlElementName);
	}

	/**
	 * Sets the modified date of this custom elements source.
	 *
	 * @param modifiedDate the modified date of this custom elements source
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this custom elements source.
	 *
	 * @param mvccVersion the mvcc version of this custom elements source
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this custom elements source.
	 *
	 * @param name the name of this custom elements source
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this custom elements source.
	 *
	 * @param primaryKey the primary key of this custom elements source
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the urls of this custom elements source.
	 *
	 * @param urls the urls of this custom elements source
	 */
	@Override
	public void setURLs(String urls) {
		model.setURLs(urls);
	}

	/**
	 * Sets the user ID of this custom elements source.
	 *
	 * @param userId the user ID of this custom elements source
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this custom elements source.
	 *
	 * @param userName the user name of this custom elements source
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this custom elements source.
	 *
	 * @param userUuid the user uuid of this custom elements source
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this custom elements source.
	 *
	 * @param uuid the uuid of this custom elements source
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
	protected CustomElementsSourceWrapper wrap(
		CustomElementsSource customElementsSource) {

		return new CustomElementsSourceWrapper(customElementsSource);
	}

}