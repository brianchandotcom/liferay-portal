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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Subscription}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Subscription
 * @deprecated
 * @generated
 */
@Deprecated
public class SubscriptionWrapper
	extends BaseModelWrapper<Subscription>
	implements ModelWrapper<Subscription>, Subscription {

	public SubscriptionWrapper(Subscription subscription) {
		super(subscription);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("subscriptionId", getSubscriptionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("frequency", getFrequency());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long subscriptionId = (Long)attributes.get("subscriptionId");

		if (subscriptionId != null) {
			setSubscriptionId(subscriptionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String frequency = (String)attributes.get("frequency");

		if (frequency != null) {
			setFrequency(frequency);
		}
	}

	/**
	 * Returns the fully qualified class name of this subscription.
	 *
	 * @return the fully qualified class name of this subscription
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this subscription.
	 *
	 * @return the class name ID of this subscription
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this subscription.
	 *
	 * @return the class pk of this subscription
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this subscription.
	 *
	 * @return the company ID of this subscription
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this subscription.
	 *
	 * @return the create date of this subscription
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the frequency of this subscription.
	 *
	 * @return the frequency of this subscription
	 */
	@Override
	public String getFrequency() {
		return model.getFrequency();
	}

	/**
	 * Returns the group ID of this subscription.
	 *
	 * @return the group ID of this subscription
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this subscription.
	 *
	 * @return the modified date of this subscription
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this subscription.
	 *
	 * @return the mvcc version of this subscription
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this subscription.
	 *
	 * @return the primary key of this subscription
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the subscription ID of this subscription.
	 *
	 * @return the subscription ID of this subscription
	 */
	@Override
	public long getSubscriptionId() {
		return model.getSubscriptionId();
	}

	/**
	 * Returns the user ID of this subscription.
	 *
	 * @return the user ID of this subscription
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this subscription.
	 *
	 * @return the user name of this subscription
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this subscription.
	 *
	 * @return the user uuid of this subscription
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a subscription model instance should use the <code>Subscription</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this subscription.
	 *
	 * @param classNameId the class name ID of this subscription
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this subscription.
	 *
	 * @param classPK the class pk of this subscription
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this subscription.
	 *
	 * @param companyId the company ID of this subscription
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this subscription.
	 *
	 * @param createDate the create date of this subscription
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the frequency of this subscription.
	 *
	 * @param frequency the frequency of this subscription
	 */
	@Override
	public void setFrequency(String frequency) {
		model.setFrequency(frequency);
	}

	/**
	 * Sets the group ID of this subscription.
	 *
	 * @param groupId the group ID of this subscription
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this subscription.
	 *
	 * @param modifiedDate the modified date of this subscription
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this subscription.
	 *
	 * @param mvccVersion the mvcc version of this subscription
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this subscription.
	 *
	 * @param primaryKey the primary key of this subscription
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the subscription ID of this subscription.
	 *
	 * @param subscriptionId the subscription ID of this subscription
	 */
	@Override
	public void setSubscriptionId(long subscriptionId) {
		model.setSubscriptionId(subscriptionId);
	}

	/**
	 * Sets the user ID of this subscription.
	 *
	 * @param userId the user ID of this subscription
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this subscription.
	 *
	 * @param userName the user name of this subscription
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this subscription.
	 *
	 * @param userUuid the user uuid of this subscription
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SubscriptionWrapper wrap(Subscription subscription) {
		return new SubscriptionWrapper(subscription);
	}

}