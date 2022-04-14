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

package com.liferay.analytics.message.storage.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnalyticsDeleteMessage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsDeleteMessage
 * @generated
 */
public class AnalyticsDeleteMessageWrapper
	extends BaseModelWrapper<AnalyticsDeleteMessage>
	implements AnalyticsDeleteMessage, ModelWrapper<AnalyticsDeleteMessage> {

	public AnalyticsDeleteMessageWrapper(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		super(analyticsDeleteMessage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"analyticsDeleteMessageId", getAnalyticsDeleteMessageId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long analyticsDeleteMessageId = (Long)attributes.get(
			"analyticsDeleteMessageId");

		if (analyticsDeleteMessageId != null) {
			setAnalyticsDeleteMessageId(analyticsDeleteMessageId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public AnalyticsDeleteMessage cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the analytics delete message ID of this analytics delete message.
	 *
	 * @return the analytics delete message ID of this analytics delete message
	 */
	@Override
	public long getAnalyticsDeleteMessageId() {
		return model.getAnalyticsDeleteMessageId();
	}

	/**
	 * Returns the class name of this analytics delete message.
	 *
	 * @return the class name of this analytics delete message
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class pk of this analytics delete message.
	 *
	 * @return the class pk of this analytics delete message
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this analytics delete message.
	 *
	 * @return the company ID of this analytics delete message
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this analytics delete message.
	 *
	 * @return the create date of this analytics delete message
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this analytics delete message.
	 *
	 * @return the modified date of this analytics delete message
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this analytics delete message.
	 *
	 * @return the mvcc version of this analytics delete message
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this analytics delete message.
	 *
	 * @return the primary key of this analytics delete message
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this analytics delete message.
	 *
	 * @return the user ID of this analytics delete message
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this analytics delete message.
	 *
	 * @return the user uuid of this analytics delete message
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the analytics delete message ID of this analytics delete message.
	 *
	 * @param analyticsDeleteMessageId the analytics delete message ID of this analytics delete message
	 */
	@Override
	public void setAnalyticsDeleteMessageId(long analyticsDeleteMessageId) {
		model.setAnalyticsDeleteMessageId(analyticsDeleteMessageId);
	}

	/**
	 * Sets the class name of this analytics delete message.
	 *
	 * @param className the class name of this analytics delete message
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class pk of this analytics delete message.
	 *
	 * @param classPK the class pk of this analytics delete message
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this analytics delete message.
	 *
	 * @param companyId the company ID of this analytics delete message
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this analytics delete message.
	 *
	 * @param createDate the create date of this analytics delete message
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this analytics delete message.
	 *
	 * @param modifiedDate the modified date of this analytics delete message
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this analytics delete message.
	 *
	 * @param mvccVersion the mvcc version of this analytics delete message
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this analytics delete message.
	 *
	 * @param primaryKey the primary key of this analytics delete message
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this analytics delete message.
	 *
	 * @param userId the user ID of this analytics delete message
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this analytics delete message.
	 *
	 * @param userUuid the user uuid of this analytics delete message
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AnalyticsDeleteMessageWrapper wrap(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		return new AnalyticsDeleteMessageWrapper(analyticsDeleteMessage);
	}

}