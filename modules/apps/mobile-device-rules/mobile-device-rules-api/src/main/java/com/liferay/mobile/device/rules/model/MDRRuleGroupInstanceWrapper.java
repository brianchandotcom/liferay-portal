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

package com.liferay.mobile.device.rules.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleGroupInstance}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstance
 * @generated
 */
public class MDRRuleGroupInstanceWrapper
	extends BaseModelWrapper<MDRRuleGroupInstance>
	implements MDRRuleGroupInstance, ModelWrapper<MDRRuleGroupInstance> {

	public MDRRuleGroupInstanceWrapper(
		MDRRuleGroupInstance mdrRuleGroupInstance) {

		super(mdrRuleGroupInstance);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("ruleGroupInstanceId", getRuleGroupInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("ruleGroupId", getRuleGroupId());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long ruleGroupInstanceId = (Long)attributes.get("ruleGroupInstanceId");

		if (ruleGroupInstanceId != null) {
			setRuleGroupInstanceId(ruleGroupInstanceId);
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

		Long ruleGroupId = (Long)attributes.get("ruleGroupId");

		if (ruleGroupId != null) {
			setRuleGroupId(ruleGroupId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.util.List<MDRAction> getActions() {
		return model.getActions();
	}

	/**
	 * Returns the fully qualified class name of this mdr rule group instance.
	 *
	 * @return the fully qualified class name of this mdr rule group instance
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this mdr rule group instance.
	 *
	 * @return the class name ID of this mdr rule group instance
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this mdr rule group instance.
	 *
	 * @return the class pk of this mdr rule group instance
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this mdr rule group instance.
	 *
	 * @return the company ID of this mdr rule group instance
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this mdr rule group instance.
	 *
	 * @return the create date of this mdr rule group instance
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this mdr rule group instance.
	 *
	 * @return the group ID of this mdr rule group instance
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this mdr rule group instance.
	 *
	 * @return the last publish date of this mdr rule group instance
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this mdr rule group instance.
	 *
	 * @return the modified date of this mdr rule group instance
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this mdr rule group instance.
	 *
	 * @return the mvcc version of this mdr rule group instance
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this mdr rule group instance.
	 *
	 * @return the primary key of this mdr rule group instance
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this mdr rule group instance.
	 *
	 * @return the priority of this mdr rule group instance
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	@Override
	public MDRRuleGroup getRuleGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRuleGroup();
	}

	/**
	 * Returns the rule group ID of this mdr rule group instance.
	 *
	 * @return the rule group ID of this mdr rule group instance
	 */
	@Override
	public long getRuleGroupId() {
		return model.getRuleGroupId();
	}

	/**
	 * Returns the rule group instance ID of this mdr rule group instance.
	 *
	 * @return the rule group instance ID of this mdr rule group instance
	 */
	@Override
	public long getRuleGroupInstanceId() {
		return model.getRuleGroupInstanceId();
	}

	/**
	 * Returns the user ID of this mdr rule group instance.
	 *
	 * @return the user ID of this mdr rule group instance
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this mdr rule group instance.
	 *
	 * @return the user name of this mdr rule group instance
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this mdr rule group instance.
	 *
	 * @return the user uuid of this mdr rule group instance
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this mdr rule group instance.
	 *
	 * @return the uuid of this mdr rule group instance
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a mdr rule group instance model instance should use the <code>MDRRuleGroupInstance</code> interface instead.
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
	 * Sets the class name ID of this mdr rule group instance.
	 *
	 * @param classNameId the class name ID of this mdr rule group instance
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this mdr rule group instance.
	 *
	 * @param classPK the class pk of this mdr rule group instance
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this mdr rule group instance.
	 *
	 * @param companyId the company ID of this mdr rule group instance
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this mdr rule group instance.
	 *
	 * @param createDate the create date of this mdr rule group instance
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this mdr rule group instance.
	 *
	 * @param groupId the group ID of this mdr rule group instance
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this mdr rule group instance.
	 *
	 * @param lastPublishDate the last publish date of this mdr rule group instance
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this mdr rule group instance.
	 *
	 * @param modifiedDate the modified date of this mdr rule group instance
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this mdr rule group instance.
	 *
	 * @param mvccVersion the mvcc version of this mdr rule group instance
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this mdr rule group instance.
	 *
	 * @param primaryKey the primary key of this mdr rule group instance
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this mdr rule group instance.
	 *
	 * @param priority the priority of this mdr rule group instance
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the rule group ID of this mdr rule group instance.
	 *
	 * @param ruleGroupId the rule group ID of this mdr rule group instance
	 */
	@Override
	public void setRuleGroupId(long ruleGroupId) {
		model.setRuleGroupId(ruleGroupId);
	}

	/**
	 * Sets the rule group instance ID of this mdr rule group instance.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID of this mdr rule group instance
	 */
	@Override
	public void setRuleGroupInstanceId(long ruleGroupInstanceId) {
		model.setRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	 * Sets the user ID of this mdr rule group instance.
	 *
	 * @param userId the user ID of this mdr rule group instance
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this mdr rule group instance.
	 *
	 * @param userName the user name of this mdr rule group instance
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this mdr rule group instance.
	 *
	 * @param userUuid the user uuid of this mdr rule group instance
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this mdr rule group instance.
	 *
	 * @param uuid the uuid of this mdr rule group instance
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
	protected MDRRuleGroupInstanceWrapper wrap(
		MDRRuleGroupInstance mdrRuleGroupInstance) {

		return new MDRRuleGroupInstanceWrapper(mdrRuleGroupInstance);
	}

}