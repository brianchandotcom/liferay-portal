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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AnalyticsMessage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessage
 * @generated
 */
public class AnalyticsMessageWrapper
	implements AnalyticsMessage, ModelWrapper<AnalyticsMessage> {

	public AnalyticsMessageWrapper(AnalyticsMessage analyticsMessage) {
		_analyticsMessage = analyticsMessage;
	}

	@Override
	public Class<?> getModelClass() {
		return AnalyticsMessage.class;
	}

	@Override
	public String getModelClassName() {
		return AnalyticsMessage.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("analyticsMessageId", getAnalyticsMessageId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("body", getBody());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long analyticsMessageId = (Long)attributes.get("analyticsMessageId");

		if (analyticsMessageId != null) {
			setAnalyticsMessageId(analyticsMessageId);
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

		Blob body = (Blob)attributes.get("body");

		if (body != null) {
			setBody(body);
		}
	}

	@Override
	public Object clone() {
		return new AnalyticsMessageWrapper(
			(AnalyticsMessage)_analyticsMessage.clone());
	}

	@Override
	public int compareTo(AnalyticsMessage analyticsMessage) {
		return _analyticsMessage.compareTo(analyticsMessage);
	}

	/**
	 * Returns the analytics message ID of this analytics message.
	 *
	 * @return the analytics message ID of this analytics message
	 */
	@Override
	public long getAnalyticsMessageId() {
		return _analyticsMessage.getAnalyticsMessageId();
	}

	/**
	 * Returns the body of this analytics message.
	 *
	 * @return the body of this analytics message
	 */
	@Override
	public Blob getBody() {
		return _analyticsMessage.getBody();
	}

	/**
	 * Returns the company ID of this analytics message.
	 *
	 * @return the company ID of this analytics message
	 */
	@Override
	public long getCompanyId() {
		return _analyticsMessage.getCompanyId();
	}

	/**
	 * Returns the create date of this analytics message.
	 *
	 * @return the create date of this analytics message
	 */
	@Override
	public Date getCreateDate() {
		return _analyticsMessage.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _analyticsMessage.getExpandoBridge();
	}

	/**
	 * Returns the mvcc version of this analytics message.
	 *
	 * @return the mvcc version of this analytics message
	 */
	@Override
	public long getMvccVersion() {
		return _analyticsMessage.getMvccVersion();
	}

	/**
	 * Returns the primary key of this analytics message.
	 *
	 * @return the primary key of this analytics message
	 */
	@Override
	public long getPrimaryKey() {
		return _analyticsMessage.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _analyticsMessage.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this analytics message.
	 *
	 * @return the user ID of this analytics message
	 */
	@Override
	public long getUserId() {
		return _analyticsMessage.getUserId();
	}

	/**
	 * Returns the user name of this analytics message.
	 *
	 * @return the user name of this analytics message
	 */
	@Override
	public String getUserName() {
		return _analyticsMessage.getUserName();
	}

	/**
	 * Returns the user uuid of this analytics message.
	 *
	 * @return the user uuid of this analytics message
	 */
	@Override
	public String getUserUuid() {
		return _analyticsMessage.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _analyticsMessage.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _analyticsMessage.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _analyticsMessage.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _analyticsMessage.isNew();
	}

	@Override
	public void persist() {
		_analyticsMessage.persist();
	}

	/**
	 * Sets the analytics message ID of this analytics message.
	 *
	 * @param analyticsMessageId the analytics message ID of this analytics message
	 */
	@Override
	public void setAnalyticsMessageId(long analyticsMessageId) {
		_analyticsMessage.setAnalyticsMessageId(analyticsMessageId);
	}

	/**
	 * Sets the body of this analytics message.
	 *
	 * @param body the body of this analytics message
	 */
	@Override
	public void setBody(Blob body) {
		_analyticsMessage.setBody(body);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_analyticsMessage.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this analytics message.
	 *
	 * @param companyId the company ID of this analytics message
	 */
	@Override
	public void setCompanyId(long companyId) {
		_analyticsMessage.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this analytics message.
	 *
	 * @param createDate the create date of this analytics message
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_analyticsMessage.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_analyticsMessage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_analyticsMessage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_analyticsMessage.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the mvcc version of this analytics message.
	 *
	 * @param mvccVersion the mvcc version of this analytics message
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_analyticsMessage.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_analyticsMessage.setNew(n);
	}

	/**
	 * Sets the primary key of this analytics message.
	 *
	 * @param primaryKey the primary key of this analytics message
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_analyticsMessage.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_analyticsMessage.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this analytics message.
	 *
	 * @param userId the user ID of this analytics message
	 */
	@Override
	public void setUserId(long userId) {
		_analyticsMessage.setUserId(userId);
	}

	/**
	 * Sets the user name of this analytics message.
	 *
	 * @param userName the user name of this analytics message
	 */
	@Override
	public void setUserName(String userName) {
		_analyticsMessage.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this analytics message.
	 *
	 * @param userUuid the user uuid of this analytics message
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_analyticsMessage.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AnalyticsMessage>
		toCacheModel() {

		return _analyticsMessage.toCacheModel();
	}

	@Override
	public AnalyticsMessage toEscapedModel() {
		return new AnalyticsMessageWrapper(_analyticsMessage.toEscapedModel());
	}

	@Override
	public String toString() {
		return _analyticsMessage.toString();
	}

	@Override
	public AnalyticsMessage toUnescapedModel() {
		return new AnalyticsMessageWrapper(
			_analyticsMessage.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _analyticsMessage.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AnalyticsMessageWrapper)) {
			return false;
		}

		AnalyticsMessageWrapper analyticsMessageWrapper =
			(AnalyticsMessageWrapper)object;

		if (Objects.equals(
				_analyticsMessage, analyticsMessageWrapper._analyticsMessage)) {

			return true;
		}

		return false;
	}

	@Override
	public AnalyticsMessage getWrappedModel() {
		return _analyticsMessage;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _analyticsMessage.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _analyticsMessage.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_analyticsMessage.resetOriginalValues();
	}

	private final AnalyticsMessage _analyticsMessage;

}